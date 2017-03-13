package movierest.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Movie {

	@XmlTransient
	private int id;
	
	@XmlTransient
	private boolean hasId;
	
	@XmlElement
	private String title;
	
	@XmlElement
	private int year;
	
	@XmlElement
	private String director;

	@XmlElement()
	private String[] actor;
	
	
	public Movie() {
		this.hasId = false;
		this.actor = new String[] {};
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
		this.hasId = true;
	}
	
	public boolean hasId() {
		return this.hasId;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getDirector() {
		return this.director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public String[] getActors() {
		return this.actor;
	}
	
	public void setActors(String[] actor) {
		this.actor = actor;
	}
	
}
