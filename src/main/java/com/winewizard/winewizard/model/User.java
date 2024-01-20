package com.winewizard.winewizard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user")
@Inheritance(strategy=InheritanceType.JOINED)
//more about: https://stackabuse.com/guide-to-jpa-with-hibernate-inheritance-mapping/
public class User implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;
	@Getter
	@NotBlank(message = "username is mandatory")
	//TODO: check if contains blank
	@Size(min = 5, max = 50, message ="Username must have at least 5 characters")
	@Column(unique = true)
	private String login;
	@Getter
	@Transient
	private String zipCodeInput;
	@Getter
	@ManyToOne
	@JoinColumn(name="zip_code", nullable = false)
	private ZipCode zipCode;
	@NotBlank(message = "password is mandatory")
	private String password;
	@Getter
	@Transient
	private String passwordRepeat;
	@Getter
	@NotBlank(message = "Email is mandatory")
	//TODO: unique and check on sign in
	private String email;
	@Getter
	@NotBlank(message = "Phone Number is mandatory")
	//TODO: check validity
	private String phone;
	@Getter
	@Transient
	private boolean olderThanSixteen;
	@Getter
	@Transient
	private boolean wineryUser;
	@Getter
	private boolean active = true;

	@Transient //TODO: annotation übeprüfen
	private List<GrantedAuthority> authorities;
	
	@Getter
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="userrole",
			joinColumns = @JoinColumn(name="iduser"),
			inverseJoinColumns = @JoinColumn(name="idrole")
			)
	private List<Role> roles = new ArrayList<Role>();
	@Getter
	@Column()
	private String verificationCode ;
	@Getter
	private boolean verified;
	@Getter
	private String personalProfileId ;

	public User(User user) {

		this.login= user.getLogin();
		this.password= user.getPassword();
		this.email=user.getEmail();
		this.phone= user.getPhone();
		this.active = user.isActive();
		this.verified = user.isVerified();

		//getting authorities from the DB
		List<Role> myRoles = (List<Role>) user.getRoles();

		System.out.println("the user "+  user.getLogin() +" has "+
				myRoles.size() +" roles" + " and email:" + user.getEmail());

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

	public User() {
	}


	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setPersonalProfileId(String personalProfileId) {
		this.personalProfileId = personalProfileId;
	}

	public String getShareLink() {
		return "http://localhost:8080/profile/" + getPersonalProfileId();
	}

	public void setWineryUser(boolean wineryUser) {
		this.wineryUser = wineryUser;
	}
	
	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return this.login;
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
