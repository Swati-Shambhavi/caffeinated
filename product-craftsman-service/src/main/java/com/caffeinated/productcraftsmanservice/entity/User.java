package com.caffeinated.productcraftsmanservice.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
//@Data
@Getter
@Setter
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 64)
	@JsonIgnore
	private String password;

	@Column
	private String role;

	private String name;

	private String mobileNumber;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
	@JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
	private Address address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//	@JsonManagedReference
	private Cart cart;

	@Override
	public int hashCode() {
		return Objects.hash(id, email, password, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		User user = (User) obj;
		return Objects.equals(id, user.id) && Objects.equals(email, user.email)
				&& Objects.equals(password, user.password) && Objects.equals(role, user.role);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @JsonIgnore
	 * @OneToMany(mappedBy = "users", fetch = FetchType.EAGER) private
	 *                     Set<Authority> authorities;
	 **/
}
