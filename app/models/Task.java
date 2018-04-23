package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;	
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
public class Task {

	@Id
	@GeneratedValue
	private Long id;

	@play.data.validation.Constraints.Required
	@Column(name = "user_name",nullable = false)
	private String contents;

	public Long getID(){
		return id;
	}

	public void setID(Long id){
		this.id = id;
	}

	public String getContents(){
		return contents;
	}

	public void setContents(String contents){
		this.contents = contents;
	}

	public String toString(){
        String myString;
        myString = "Task " + this.getContents();
        return myString;
    }
}