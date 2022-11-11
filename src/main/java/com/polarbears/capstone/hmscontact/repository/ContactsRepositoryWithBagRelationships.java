package com.polarbears.capstone.hmscontact.repository;

import com.polarbears.capstone.hmscontact.domain.Contacts;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ContactsRepositoryWithBagRelationships {
    Optional<Contacts> fetchBagRelationships(Optional<Contacts> contacts);

    List<Contacts> fetchBagRelationships(List<Contacts> contacts);

    Page<Contacts> fetchBagRelationships(Page<Contacts> contacts);
}
