package com.learner.caffeinated.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Contact extends BaseEntity{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
	private Integer contactId;
	private String name;
	private String mobileNumber;
	private String email;
	private String subject;
	private String message;
	private String status="OPEN";
}
