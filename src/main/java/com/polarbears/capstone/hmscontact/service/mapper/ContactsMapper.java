package com.polarbears.capstone.hmscontact.service.mapper;

import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import com.polarbears.capstone.hmscontact.domain.Contacts;
import com.polarbears.capstone.hmscontact.service.dto.ContactAddressesDTO;
import com.polarbears.capstone.hmscontact.service.dto.ContactsDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contacts} and its DTO {@link ContactsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactsMapper extends EntityMapper<ContactsDTO, Contacts> {
    @Mapping(target = "contactAddresses", source = "contactAddresses", qualifiedByName = "contactAddressesNameSet")
    ContactsDTO toDto(Contacts s);

    @Mapping(target = "removeContactAddresses", ignore = true)
    Contacts toEntity(ContactsDTO contactsDTO);

    @Named("contactAddressesName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ContactAddressesDTO toDtoContactAddressesName(ContactAddresses contactAddresses);

    @Named("contactAddressesNameSet")
    default Set<ContactAddressesDTO> toDtoContactAddressesNameSet(Set<ContactAddresses> contactAddresses) {
        return contactAddresses.stream().map(this::toDtoContactAddressesName).collect(Collectors.toSet());
    }
}
