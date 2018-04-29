package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;	
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.MaxLength;

@Entity
@Table(name = "episode_table")
/**
 * an object that stores user-entered information for an episode
 **/
public class Episode {

	@Id
	@GeneratedValue
	private Long id;

	@Required
	@Column(name = "episodeName",nullable = false)
	@MinLength(value = 3)
	@MaxLength(value = 30)
	private String episodeName;
	
	@Required
	@Column(name = "doctorName",nullable = false)
	@MinLength(value = 4)
	@MaxLength(value = 20)
	private String doctorName;

	@Required
	@Column(name = "companionName",nullable = false)
	@MinLength(value = 4)
	@MaxLength(value = 30)
	private String companionName;
	
	@Required
	@Column(name = "plotDescription",nullable = false)
	@MinLength(value = 20)
	@MaxLength(value = 500)
	private String plotDescription;
	
	/**
	 * returns episode id
	 * @return Long id 
	 **/
	public Long getID(){
		return id;
	}

	/**
	 * sets episode id
	 * @param Long id 
	 **/		
	public void setID(Long id){
		this.id = id;
	}

	/**
	 * returns episode name
	 * @return String episodeName 
	 **/	
	public String getEpisodeName(){
		return episodeName;
	}

	/**
	 * sets episode name
	 * @param String episodeName 
	 **/		
	public void setEpisodeName(String episodeName){
		this.episodeNa
		me = episodeName;
	}

	/**
	 * returns doctor name
	 * @return String doctorName 
	 **/	
	public String getDoctorName(){
		return doctorName;
	}

	/**
	 * sets doctor name
	 * @param String doctorName 
	 **/		
	public void setDoctorName(String doctorName){
		this.doctorName = doctorName;
	}

	/**
	 * returns companion name
	 * @return String companionName 
	 **/	
	public String getCompanionName(){
		return companionName;
	}

	/**
	 * sets companion name
	 * @param String companionName 
	 **/	
	public void setCompanionName(String companionName){
		this.companionName = companionName;
	}

	/**
	 * returns plot description
	 * @return String plotDescription 
	 **/	
	public String getPlotDescription(){
		return plotDescription;
	}

	/**
	 * sets plot description
	 * @param String plotDescription 
	 **/		
	public void setPlotDescription(String plotDescription){
		this.plotDescription = plotDescription;
	}

}