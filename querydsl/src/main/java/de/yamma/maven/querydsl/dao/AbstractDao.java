/**
 * 
 */
package de.yamma.maven.querydsl.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;

import de.yamma.maven.querydsl.entities.IEntity;

/**
 * <P>
 * </P>
 * 
 * @author Christian Nefzger Cataneo GmbH
 * 
 *         Created 16.03.2016
 *
 */
public class AbstractDao<E extends IEntity>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);

	@Resource
	protected SQLQueryFactory queryFactory;

	@Resource(name = "copyingCacheManager")
	protected CacheManager cacheManager;

	@Resource(name = "cacheDependencyManager")
	protected CacheDependencyManager cacheDependencyManager;

	protected RelationalPathBase<E> entityType;
	protected String sequence;
	/** Enable caching for this type of entity. */
	protected boolean cache = true;
	/** Needed by historized entities to get the correct timestamps in the history tables. */
	protected boolean updateBeforeDelete = false;
	/** Needed if database defaults are applied. */
	protected boolean reloadAfterInsertOrUpdate = false;

	private final NumberPath<Long> ID;
	private final NumberPath<Long> LUPDATE;
	private final NumberPath<Long> LUSERID;

	public AbstractDao(RelationalPathBase<E> entityType)
	{
		this.entityType = entityType;

		ID = Expressions.numberPath(Long.class, entityType, "id");
		LUPDATE = Expressions.numberPath(Long.class, entityType, "lupdate");
		LUSERID = Expressions.numberPath(Long.class, entityType, "luserid");
	}

	public List<E> findAll()
		throws DatabaseException
	{
		return queryFactory.select(entityType).from(entityType).fetch();
	}

	public E findById(final Long id)
		throws DatabaseException
	{
		E entity = null;

		if ( id != null && id > 0 )
		{
			if ( cache )
			{
				Cache cache = retrieveCache();

				entity = cache.get(id, new Callable<E>()
				{
					@Override
					public E call()
						throws Exception
					{
						return findByIdRaw(id);
					}
				});
			}
			else
			{
				return findByIdRaw(id);
			}
		}

		return entity;
	}

	public void lockById(Long id)
		throws DatabaseException
	{
		if ( id == null || queryFactory.update(entityType).set(LUPDATE, LUPDATE).where(ID.eq(id)).execute() == 0 )
		{
			DatabaseException de = new DatabaseException("Datensatz existiert nicht");
			LOGGER.info("Record with type {} and id {} does not exist", entityType.toString(), id, de);
			throw de;
		}
	}

	// public void lock(E entity)
	// throws DatabaseException
	// {
	// long numUpdates = queryFactory.update(entityType).set(LUPDATE,
	// LUPDATE).where(ID.eq(entity.getId()).and(LUPDATE.loe(entity.getLupdate()))).execute();
	//
	// if ( numUpdates == 0 )
	// {
	// throw new TimestampException();
	// }
	// }

	public E insert(E entity)
		throws DatabaseException
	{
		entity.setLupdate(System.currentTimeMillis());
		entity.setLuserid(session.getUserID());

		Long id = queryFactory.insert(entityType).populate(entity).set(ID, SQLExpressions.nextval(sequence)).executeWithKey(ID);

		if ( reloadAfterInsertOrUpdate )
		{
			entity = findByIdRaw(id);
		}
		else
		{
			entity.setId(id);
		}

		if ( cache )
		{
			Cache cache = retrieveCache();
			cache.put(entity.getId(), entity);
		}

		return entity;
	}

	public E update(E entity)
		throws DatabaseException
	{
		long now = System.currentTimeMillis();

		long numUpdates = queryFactory.update(entityType).populate(entity, DefaultMapper.WITH_NULL_BINDINGS).set(LUPDATE, now).set(LUSERID, session.getUserID())
										.where(ID.eq(entity.getId()).and(LUPDATE.loe(entity.getLupdate()))).execute();

		if ( numUpdates > 0 )
		{
			if ( reloadAfterInsertOrUpdate )
			{
				entity = findByIdRaw(entity.getId());
			}
			else
			{
				entity.setLupdate(now);
				entity.setLuserid(session.getUserID());
			}

			if ( cache )
			{
				Cache cache = retrieveCache();
				cache.put(entity.getId(), entity);
			}

			return entity;
		}
		else
		{
			TimestampException te = new TimestampException("Datensatz existiert nicht oder ist veraltet");
			LOGGER.info("Record with type {} and id {} does not exist or is outdated", entityType.toString(), entity.getId(), te);
			throw te;
		}
	}

	public List<E> updateAll(List<E> entities)
		throws DatabaseException
	{
		// TODO Optimize using batch operations
		List<E> updatedEntities = new ArrayList<>();

		for (E entity : entities)
		{
			updatedEntities.add(update(entity));
		}

		return updatedEntities;
	}

	public void delete(E entity)
		throws DatabaseException
	{
		long now = System.currentTimeMillis();

		Predicate filter = ID.eq(entity.getId()).and(LUPDATE.loe(entity.getLupdate()));

		if ( updateBeforeDelete )
		{
			queryFactory.update(entityType).set(LUPDATE, now).set(LUSERID, session.getUserID()).where(filter).execute();
		}

		long numDeletes = queryFactory.delete(entityType).where(filter).execute();

		if ( numDeletes == 0 )
		{
			TimestampException te = new TimestampException("Datensatz existiert nicht oder ist veraltet");
			LOGGER.info("Record with type {} and id {} does not exist or is outdated", entityType.toString(), entity.getId(), te);
			throw te;
		}

		if ( cache )
		{
			Cache cache = retrieveCache();
			cache.evict(entity.getId());
		}
	}

	public void deleteAll(List<E> entities)
		throws DatabaseException
	{
		// TODO Optimize using batch operations
		for (E entity : entities)
		{
			delete(entity);
		}
	}

	public Long count()
		throws DatabaseException
	{
		return queryFactory.select(entityType).from(entityType).fetchCount();
	}

	public void setSequence(String sequence)
	{
		this.sequence = sequence;
	}

	public void setUpdateBeforeDelete(boolean updateBeforeDelete)
	{
		this.updateBeforeDelete = updateBeforeDelete;
	}

	public void setReloadAfterInsertOrUpdate(boolean reloadAfterInsertOrUpdate)
	{
		this.reloadAfterInsertOrUpdate = reloadAfterInsertOrUpdate;
	}

	public void setCache(boolean cache)
	{
		this.cache = cache;
	}

	protected E findByIdRaw(Long id)
		throws DatabaseException
	{
		E entity = null;

		List<E> results = queryFactory.select(entityType).from(entityType).where(ID.eq(id)).fetch();

		if ( !results.isEmpty() )
		{
			entity = results.get(0);
		}

		return entity;
	}

	public void init()
	{
	}

	/**
	 * <P>
	 * </P>
	 * 
	 * @author Christian Nefzger Cataneo GmbH
	 * 
	 *         Created 02.06.2016
	 *
	 * @return
	 */
	protected Cache retrieveCache()
	{
		String cacheId = entityType.getTableName() + ".ID";
		return cacheManager.getCache(cacheId);
	}
}
