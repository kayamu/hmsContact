package com.polarbears.capstone.hmscontact.service.mapper;

import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import com.polarbears.capstone.hmscontact.service.dto.ContactAddressesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactAddresses} and its DTO {@link ContactAddressesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactAddressesMapper extends EntityMapper<ContactAddressesDTO, ContactAddresses> {}
