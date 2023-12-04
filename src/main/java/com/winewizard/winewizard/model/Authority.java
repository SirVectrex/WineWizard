package com.winewizard.winewizard.model;

import jakarta.persistence.*;

import java.util.Collection;

@jakarta.persistence.Entity
@Table(name="authority")
public class Authority {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	    
    @ManyToMany(mappedBy = "authorities")
    private Collection<Role> roles;
    
       
	
	
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
