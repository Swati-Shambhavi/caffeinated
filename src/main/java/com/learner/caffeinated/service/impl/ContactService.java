package com.learner.caffeinated.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.learner.caffeinated.service.IContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.learner.caffeinated.entity.Contact;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.repo.ContactRepo;

@Service
@AllArgsConstructor
public class ContactService implements IContactService {
	private ContactRepo repo;

	public ServiceResponse saveContact(Contact contact) {
		ServiceResponse response = ServiceResponse.builder().build();
		Contact savedContact;
		try {
			savedContact= repo.save(contact);
		}catch(Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("errorCode", "60001");
			error.put("errorMessage", e.getMessage());
			response.setError(error);
			return response;
		}

			response.setData(savedContact);
		return response;
	}

	public ServiceResponse updateContact(Contact contact, String contactId) {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Contact>contactInDB = repo.findById(Integer.parseInt(contactId));
		if(contactInDB.isPresent()) {
			mapData(contact, contactInDB.get());
			response.setData(repo.save(mapData(contact, contactInDB.get())));
			return response;
		}else {
			Map<String, String> error = new HashMap<>();
			error.put("errorCode", "60001");
			error.put("errorMessage", "No matching Contact detail found for id:"+contactId);
			response.setError(error);
			return response;
		}
	}

	private Contact mapData(Contact req, Contact contactInDB) {
		contactInDB.setEmail(req.getEmail());
		contactInDB.setName(req.getName());
		contactInDB.setMobileNumber(req.getMobileNumber());
		contactInDB.setSubject(req.getSubject());
		contactInDB.setMessage(req.getMessage());
		contactInDB.setStatus(contactInDB.getStatus());
		return contactInDB;
		
	}
	
	public ServiceResponse getAllContactDetails() {
		ServiceResponse response = ServiceResponse.builder().build();
		List<Contact> allContactDetails = repo.findAll();
		response.setData(allContactDetails);
		return response;
	}

	public ServiceResponse contactDetail(Integer contactId) {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Contact>contactInDB = repo.findById(contactId);
		if(contactInDB.isPresent()) {
			response.setData(contactInDB.get());
			return response;
		}else {
			Map<String, String> error = new HashMap<>();
			error.put("errorCode", "60001");
			error.put("errorMessage", "No matching Contact detail found for id:"+contactId);
			response.setError(error);
			return response;
		}
	}

	public ServiceResponse closeContact(Integer contactId) {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Contact>contactInDB = repo.findById(contactId);
		if(contactInDB.isPresent()) {
			Contact contact= contactInDB.get();
			contact.setStatus("CLOSE");
			response.setData(repo.save(contact));
			return response;
		}else {
			Map<String, String> error = new HashMap<>();
			error.put("errorCode", "60001");
			error.put("errorMessage", "No matching Contact detail found for id:"+contactId);
			response.setError(error);
			return response;
		}
	}

	public ServiceResponse deleteContact(Integer contactId) {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Contact>contactInDB = repo.findById(contactId);
		if(contactInDB.isPresent()) {
			repo.delete(contactInDB.get());
			response.setData("Successfully deleted your contact detail");
			return response;
		}else {
			Map<String, String> error = new HashMap<>();
			error.put("errorCode", "60001");
			error.put("errorMessage", "No matching Contact detail found for id:"+contactId);
			response.setError(error);
			return response;
		}
	}

}
