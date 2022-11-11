package com.polarbears.capstone.hmscontact.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactAddressesMapperTest {

    private ContactAddressesMapper contactAddressesMapper;

    @BeforeEach
    public void setUp() {
        contactAddressesMapper = new ContactAddressesMapperImpl();
    }
}
