package com.learner.caffeinated.service;

import com.learner.caffeinated.entity.Contact;
import com.learner.caffeinated.entity.ServiceResponse;

public interface IContactService {
    ServiceResponse getAllContactDetails();
    ServiceResponse contactDetail(Integer contactId);
    ServiceResponse closeContact(Integer contactId, boolean userAskedForDeletion);
    ServiceResponse updateContact(Contact contact, String contactId);
    ServiceResponse saveContact(Contact contact);
}
