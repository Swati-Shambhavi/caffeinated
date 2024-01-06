package com.learner.caffeinated.controller;

import com.learner.caffeinated.service.IContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learner.caffeinated.entity.Contact;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.service.impl.ContactService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/public/contact")
@AllArgsConstructor
public class ContactController {
	private IContactService service;
	
	@GetMapping("/")
	public ServiceResponse getAllContact() {
		return service.getAllContactDetails();
	}
	
	@GetMapping("/{contactId}")
	public ServiceResponse getContact(@PathVariable Integer contactId) {
		return service.contactDetail(contactId);
	}
	
	@PostMapping("/")
	public ServiceResponse postContact(@RequestBody Contact contact) {
		log.info("Initial Request:", contact.toString());
		return service.saveContact(contact);
	}
	
	@PutMapping("/{contactId}")
	public ServiceResponse updateContact(@RequestBody Contact contact, @PathVariable String contactId) {
		return service.updateContact(contact, contactId);
	}
	
	@DeleteMapping("/{contactId}")
	public ServiceResponse deleteContact(@PathVariable Integer contactId) {
		boolean userAskedForDeletion = true;
		return service.closeContact(contactId, userAskedForDeletion);
	}
	
	@GetMapping("/close/{contactId}")
	public ServiceResponse closeAContact(@PathVariable Integer contactId) {
		boolean userAskedForDeletion = true;
		return service.closeContact(contactId, userAskedForDeletion);
	}
}
