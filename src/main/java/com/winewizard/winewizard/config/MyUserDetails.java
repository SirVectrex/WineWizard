package com.winewizard.winewizard.config;

import com.winewizard.winewizard.model.Authority;
import com.winewizard.winewizard.model.Role;
import com.winewizard.winewizard.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String userName;
	private String password;
	private boolean active;
	@Getter
	private String email;
	@Getter
	private String phone;
	private List<GrantedAuthority> authorities;
	private List <Role> roles;
	
	
	public MyUserDetails(User user) {
		
		// TODO Auto-generated constructor stub
		this.userName= user.getLogin();
		this.password= user.getPassword();
		this.email=user.getEmail();
		this.phone= user.getPhone();
		System.out.println("password of the user is="+password);
		System.out.println("userName of the user is="+this.userName);
		this.active = user.isActive();
		
		//getting authorities from the DB 		
		List<Role> myRoles = (List<Role>) user.getRoles();
				
		System.out.println("the user "+  user.getLogin() +" has "+ 
				myRoles.size() +" roles" + "and email:" + user.getEmail());
		
		//authorities is required by Userdetails from Spring Security
		this.roles = myRoles;
		authorities = new ArrayList<>();
		
		//passing the authorities of each Profile from the DB to the Spring Security collection UserDetails.authorities
		for (int i=0; i< myRoles.size(); i++){
				List <Authority> myAuthsProfile = (List<Authority>) myRoles.get(i).getAuthorities();		
		        for (Authority auth : myAuthsProfile) {
		        	authorities.add(new SimpleGrantedAuthority(auth.getDescription().toUpperCase()));
		        		System.out.println("the authority" + i +" of the profile "+myRoles.get(i).getDescription()+" of the user " +user.getLogin() + " is "+ auth.getDescription());
		        }
		        
		  }
		
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.active;
	}

}
