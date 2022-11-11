package com.polarbears.capstone.hmscontact.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmscontact.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactAddressesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactAddressesDTO.class);
        ContactAddressesDTO contactAddressesDTO1 = new ContactAddressesDTO();
        contactAddressesDTO1.setId(1L);
        ContactAddressesDTO contactAddressesDTO2 = new ContactAddressesDTO();
        assertThat(contactAddressesDTO1).isNotEqualTo(contactAddressesDTO2);
        contactAddressesDTO2.setId(contactAddressesDTO1.getId());
        assertThat(contactAddressesDTO1).isEqualTo(contactAddressesDTO2);
        contactAddressesDTO2.setId(2L);
        assertThat(contactAddressesDTO1).isNotEqualTo(contactAddressesDTO2);
        contactAddressesDTO1.setId(null);
        assertThat(contactAddressesDTO1).isNotEqualTo(contactAddressesDTO2);
    }
}
