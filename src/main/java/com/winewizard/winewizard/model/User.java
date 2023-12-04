package com.winewizard.winewizard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Inheritance(strategy=InheritanceType.JOINED)
//more about: https://stackabuse.com/guide-to-jpa-with-hibernate-inheritance-mapping/
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotBlank(message = "login is mandatory")
	private String login;
	
	@NotBlank(message = "password is mandatory")
	private String password;
		
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	private boolean active = true;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="userrole",
			joinColumns = @JoinColumn(name="iduser"),
			inverseJoinColumns = @JoinColumn(name="idrole")
			)	
	private List<Role> roles = new ArrayList<Role>();

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
