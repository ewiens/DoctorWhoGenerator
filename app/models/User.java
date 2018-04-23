package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;	
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@play.data.validation.Constraints.Required
	@Column(name = "username",nullable = false)
	private String username;

	@play.data.validation.Constraints.Required
	@Column(name = "password",nullable = false)
	private String password;

	
	public Long getID(){
		return id;
	}

	public void setID(Long id){
		this.id = id;
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}
	
	public String toString(){
	    return("User: "+getUsername());	
	}
	
}