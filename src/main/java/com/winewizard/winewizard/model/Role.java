package com.winewizard.winewizard.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roleauthority", 
        joinColumns = @JoinColumn(name = "idrole", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "idauthority", referencedColumnName = "id"))
    private Collection<Authority> authorities;

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

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}
    
    
    
}


