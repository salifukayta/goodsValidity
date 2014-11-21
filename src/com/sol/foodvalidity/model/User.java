package com.sol.foodvalidity.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String password;
	private String email;
	private String adress;
	private Date dateOfBirth;

	public User() {
	}

	public User(String name, String password, String email, String adresse) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.adress = adresse;
	}
	
	public User(long id, String name, String password, String email, String adresse, Date dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.adress = adresse;
		this.dateOfBirth = dateOfBirth;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", email=" + email + ", adress=" + adress + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
