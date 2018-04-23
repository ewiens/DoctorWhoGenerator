package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;	
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "episode_table")
public class Episode {

	@Id
	@GeneratedValue
	private Long id;

	@play.data.validation.Constraints.Required
	@Column(name = "episodeName",nullable = false)
	private String episodeName;
	
	@play.data.validation.Constraints.Required
	@Column(name = "doctorName",nullable = false)
	private String doctorName;

	@play.data.validation.Constraints.Required
	@Column(name = "companionName",nullable = false)
	private String companionName;
	
	@play.data.validation.Constraints.Required
	@Column(name = "plotDescription",nullable = false)
	private String plotDescription;
	
	public Long getID(){
		return id;
	}

	public void setID(Long id){
		this.id = id;
	}
	
	public String getEpisodeName(){
		return episodeName;
	}

	public void setID(String episodeName){
		this.episodeName = episodeName;
	}

	public String getDoctorName(){
		return doctorName;
	}

	public void setDoctorName(String doctorName){
		this.doctorName = doctorName;
	}

	public String getCompanionName(){
		return companionName;
	}

	public void setCompanionName(String companionName){
		this.companionName = companionName;
	}

	public String getPlotDescription(){
		return plotDescription;
	}
 
	public void setPlotDescription(String plotDescription){
		this.plotDescription = plotDescription;
	}

}