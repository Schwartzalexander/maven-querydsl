package de.yamma.maven.querydsl.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QSport is a Querydsl query type for QSport
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QSport extends com.querydsl.sql.RelationalPathBase<QSport> {

    private static final long serialVersionUID = -1864539492;

    public static final QSport sport = new QSport("sport");

    public final BooleanPath hasDistance = createBoolean("hasDistance");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DatePath<java.sql.Date> lastUpdate = createDate("lastUpdate", java.sql.Date.class);

    public final StringPath name = createString("name");

    public final com.querydsl.sql.PrimaryKey<QSport> primary = createPrimaryKey(id);

    public QSport(String variable) {
        super(QSport.class, forVariable(variable), "null", "sport");
        addMetadata();
    }

    public QSport(String variable, String schema, String table) {
        super(QSport.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QSport(String variable, String schema) {
        super(QSport.class, forVariable(variable), schema, "sport");
        addMetadata();
    }

    public QSport(Path<? extends QSport> path) {
        super(path.getType(), path.getMetadata(), "null", "sport");
        addMetadata();
    }

    public QSport(PathMetadata metadata) {
        super(QSport.class, metadata, "null", "sport");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(hasDistance, ColumnMetadata.named("HAS_DISTANCE").withIndex(3).ofType(Types.BIT));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(lastUpdate, ColumnMetadata.named("LAST_UPDATE").withIndex(4).ofType(Types.DATE).withSize(10));
        addMetadata(name, ColumnMetadata.named("NAME").withIndex(2).ofType(Types.LONGVARCHAR).withSize(65535).notNull());
    }

}

