package domain;


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
public class Sport {

	/**
	 * The ID of this sport.
	 */
	@Id
	private Long id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHasDistance() {
		return hasDistance;
	}

	public void setHasDistance(boolean hasDistance) {
		this.hasDistance = hasDistance;
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	// --------------------------------------------------
	// End of Setters and Getters
	// --------------------------------------------------

	@Override
	public String toString() {
		return "Sport{" + "id=" + id + ", name='" + name + '\'' + '}';
	}

	public boolean getHasDistance() {
		return this.hasDistance;
	}

	public boolean getUploaded() {
		return this.uploaded;
	}
}
