package de.yamma.maven.querydsl.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <P>
 * Holds types of sports
 * </P>
 * <P>
 * Created by Alexander on 04.05.2017.
 * </P>
 */
@Entity
public class Sport implements IEntity {

	/**
	 * The ID of this sport.
	 */
	@Id
	private long id = 0;
	/**
	 * The name of this sport.
	 */
	private String name;
	/**
	 * If true, the sport has a distance, otherwise it has not.
	 */
	private boolean hasDistance = true;
	/**
	 * If true, the sport has been uploaded, otherwise it has not.
	 */
	private boolean uploaded = false;
	/**
	 * The timestamp of the last update.
	 */
	private long lastUpdate = 0;
	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public Sport() {
	}

	public Sport(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	// --------------------------------------------------
	// End of constructors
	// --------------------------------------------------
	// --------------------------------------------------
	// Setters and Getters
	// --------------------------------------------------

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public long getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setHasDistance(boolean hasDistance) {
		this.hasDistance = hasDistance;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	public boolean getHasDistance() {
		return this.hasDistance;
	}

	public boolean getUploaded() {
		return this.uploaded;
	}

	// --------------------------------------------------
	// End of Setters and Getters
	// --------------------------------------------------

	@Override
	public String toString() {
		return "Sport{" + "id=" + id + ", name='" + name + '\'' + '}';
	}

}
