package com.polarbears.capstone.hmscontact.repository;

import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactAddresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactAddressesRepository extends JpaRepository<ContactAddresses, Long>, JpaSpecificationExecutor<ContactAddresses> {}
