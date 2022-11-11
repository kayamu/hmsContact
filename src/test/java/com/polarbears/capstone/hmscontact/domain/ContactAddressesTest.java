package com.polarbears.capstone.hmscontact.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmscontact.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactAddressesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactAddresses.class);
        ContactAddresses contactAddresses1 = new ContactAddresses();
        contactAddresses1.setId(1L);
        ContactAddresses contactAddresses2 = new ContactAddresses();
        contactAddresses2.setId(contactAddresses1.getId());
        assertThat(contactAddresses1).isEqualTo(contactAddresses2);
        contactAddresses2.setId(2L);
        assertThat(contactAddresses1).isNotEqualTo(contactAddresses2);
        contactAddresses1.setId(null);
        assertThat(contactAddresses1).isNotEqualTo(contactAddresses2);
    }
}
