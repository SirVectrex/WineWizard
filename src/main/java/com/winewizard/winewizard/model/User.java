package com.winewizard.winewizard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
	
	@NotBlank(message = "username is mandatory")
	//TODO: check if contains blank
	@Size(min = 5, max = 50, message ="Username must have at least 5 characters")
	@Column(unique = true)
	private String login;


	@Transient
	private String zipCodeInput;


	@ManyToOne
	@JoinColumn(name="zip_code", nullable = false)
	private ZipCode zipCode;
	
	@NotBlank(message = "password is mandatory")
	private String password;

	@Transient
	private String passwordRepeat;
		
	@NotBlank(message = "Email is mandatory")
	//TODO: unique and check on sign in
	private String email;

	@NotBlank(message = "Phone Number is mandatory")
	private  String phone;

	@Transient
	private boolean olderThanSixteen;
	@Transient
	private boolean wineryUser;

	private boolean active = true;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="userrole",
			joinColumns = @JoinColumn(name="iduser"),
			inverseJoinColumns = @JoinColumn(name="idrole")
			)
	private List<Role> roles = new ArrayList<Role>();

	@Column(length = 64)
	private String verificationCode ;
	private boolean verified;

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isWineryUser() {
		return wineryUser;
	}

	public void setWineryUser(boolean wineryUser) {
		this.wineryUser = wineryUser;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public String getZipCodeInput() {
		return zipCodeInput;
	}

	public void setZipCodeInput(String zipCode) {
		this.zipCodeInput = zipCode;
	}

	public ZipCode getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isOlderThanSixteen() {
		return olderThanSixteen;
	}

	public void setOlderThanSixteen(boolean olderThanSixteen) {
		this.olderThanSixteen = olderThanSixteen;
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
