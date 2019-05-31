package com.revature.boot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
	@Id @GeneratedValue(generator="users_id_seq", strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Pattern(regexp="[a-zA-Z]+")
	@javax.validation.constraints.Size(min=2, max=15)
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String apikey;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public User(Long id, String name) {
		super();
		this.id = id;
		this.username = name;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setName(String name) {
		this.username = name;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + username + "]";
	}

}
