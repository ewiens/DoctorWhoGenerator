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
@Table(name = "user_table")
/**
 * an object that stores username and password for a user
 **/
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Required
	@Column(name = "username",nullable = false)
	@MinLength(value = 4)
	@MaxLength(value = 256)
	private String username;

	@Required
	@MinLength(value = 4)
    @MaxLength(value = 256)	
	@Column(name = "password",nullable = false)
	private String password;

	/**
	 * returns user id
	 * @return Long id
	 **/
	public Long getID(){
		return id;
	}

	/**
	 * sets user id
	 * @param Long id
	 **/		
	public void setID(Long id){
		this.id = id;
	}

	/**
	 * returns user's username
	 * @return String username
	 **/	
	public String getUsername(){
		return username;
	}

	/**
	 * sets user's username
	 * @param String username
	 **/		
	public void setUsername(String username){
		this.username = username;
	}

	/**
	 * returns user password
	 * @return String password
	 **/	
	public String getPassword(){
		return password;
	}

	/**
	 * sets user password
	 * @param String password
	 **/	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String toString(){
	    return("User: "+getUsername());	
	}
	
}