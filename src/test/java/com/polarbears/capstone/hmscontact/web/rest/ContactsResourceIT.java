package com.polarbears.capstone.hmscontact.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmscontact.IntegrationTest;
import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import com.polarbears.capstone.hmscontact.domain.Contacts;
import com.polarbears.capstone.hmscontact.domain.enumeration.CONTACTTYPE;
import com.polarbears.capstone.hmscontact.repository.ContactsRepository;
import com.polarbears.capstone.hmscontact.service.ContactsService;
import com.polarbears.capstone.hmscontact.service.criteria.ContactsCriteria;
import com.polarbears.capstone.hmscontact.service.dto.ContactsDTO;
import com.polarbears.capstone.hmscontact.service.mapper.ContactsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContactsResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final CONTACTTYPE DEFAULT_TYPE = CONTACTTYPE.CUSTOMER;
    private static final CONTACTTYPE UPDATED_TYPE = CONTACTTYPE.KITCHEN;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HST_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HST_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENDER = false;
    private static final Boolean UPDATED_GENDER = true;

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactsRepository contactsRepository;

    @Mock
    private ContactsRepository contactsRepositoryMock;

    @Autowired
    private ContactsMapper contactsMapper;

    @Mock
    private ContactsService contactsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactsMockMvc;

    private Contacts contacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacts createEntity(EntityManager em) {
        Contacts contacts = new Contacts()
            .userID(DEFAULT_USER_ID)
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .hstNumber(DEFAULT_HST_NUMBER)
            .detail(DEFAULT_DETAIL)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .gender(DEFAULT_GENDER)
            .birthdate(DEFAULT_BIRTHDATE)
            .createdDate(DEFAULT_CREATED_DATE);
        return contacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacts createUpdatedEntity(EntityManager em) {
        Contacts contacts = new Contacts()
            .userID(UPDATED_USER_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .hstNumber(UPDATED_HST_NUMBER)
            .detail(UPDATED_DETAIL)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER)
            .birthdate(UPDATED_BIRTHDATE)
            .createdDate(UPDATED_CREATED_DATE);
        return contacts;
    }

    @BeforeEach
    public void initTest() {
        contacts = createEntity(em);
    }

    @Test
    @Transactional
    void createContacts() throws Exception {
        int databaseSizeBeforeCreate = contactsRepository.findAll().size();
        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
        restContactsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
            .andExpect(status().isCreated());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeCreate + 1);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testContacts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContacts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContacts.getHstNumber()).isEqualTo(DEFAULT_HST_NUMBER);
        assertThat(testContacts.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testContacts.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContacts.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testContacts.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testContacts.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testContacts.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createContactsWithExistingId() throws Exception {
        // Create the Contacts with an existing ID
        contacts.setId(1L);
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        int databaseSizeBeforeCreate = contactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hstNumber").value(hasItem(DEFAULT_HST_NUMBER)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contactsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contactsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContactsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contactsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contactsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get the contacts
        restContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, contacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contacts.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hstNumber").value(DEFAULT_HST_NUMBER))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.booleanValue()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getContactsByIdFiltering() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        Long id = contacts.getId();

        defaultContactsShouldBeFound("id.equals=" + id);
        defaultContactsShouldNotBeFound("id.notEquals=" + id);

        defaultContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContactsByUserIDIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where userID equals to DEFAULT_USER_ID
        defaultContactsShouldBeFound("userID.equals=" + DEFAULT_USER_ID);

        // Get all the contactsList where userID equals to UPDATED_USER_ID
        defaultContactsShouldNotBeFound("userID.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllContactsByUserIDIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where userID in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultContactsShouldBeFound("userID.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the contactsList where userID equals to UPDATED_USER_ID
        defaultContactsShouldNotBeFound("userID.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllContactsByUserIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where userID is not null
        defaultContactsShouldBeFound("userID.specified=true");

        // Get all the contactsList where userID is null
        defaultContactsShouldNotBeFound("userID.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByUserIDContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where userID contains DEFAULT_USER_ID
        defaultContactsShouldBeFound("userID.contains=" + DEFAULT_USER_ID);

        // Get all the contactsList where userID contains UPDATED_USER_ID
        defaultContactsShouldNotBeFound("userID.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllContactsByUserIDNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where userID does not contain DEFAULT_USER_ID
        defaultContactsShouldNotBeFound("userID.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the contactsList where userID does not contain UPDATED_USER_ID
        defaultContactsShouldBeFound("userID.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllContactsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where type equals to DEFAULT_TYPE
        defaultContactsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the contactsList where type equals to UPDATED_TYPE
        defaultContactsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllContactsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultContactsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the contactsList where type equals to UPDATED_TYPE
        defaultContactsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllContactsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where type is not null
        defaultContactsShouldBeFound("type.specified=true");

        // Get all the contactsList where type is null
        defaultContactsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name equals to DEFAULT_NAME
        defaultContactsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactsList where name equals to UPDATED_NAME
        defaultContactsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactsList where name equals to UPDATED_NAME
        defaultContactsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name is not null
        defaultContactsShouldBeFound("name.specified=true");

        // Get all the contactsList where name is null
        defaultContactsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByNameContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name contains DEFAULT_NAME
        defaultContactsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactsList where name contains UPDATED_NAME
        defaultContactsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where name does not contain DEFAULT_NAME
        defaultContactsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactsList where name does not contain UPDATED_NAME
        defaultContactsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByHstNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where hstNumber equals to DEFAULT_HST_NUMBER
        defaultContactsShouldBeFound("hstNumber.equals=" + DEFAULT_HST_NUMBER);

        // Get all the contactsList where hstNumber equals to UPDATED_HST_NUMBER
        defaultContactsShouldNotBeFound("hstNumber.equals=" + UPDATED_HST_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactsByHstNumberIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where hstNumber in DEFAULT_HST_NUMBER or UPDATED_HST_NUMBER
        defaultContactsShouldBeFound("hstNumber.in=" + DEFAULT_HST_NUMBER + "," + UPDATED_HST_NUMBER);

        // Get all the contactsList where hstNumber equals to UPDATED_HST_NUMBER
        defaultContactsShouldNotBeFound("hstNumber.in=" + UPDATED_HST_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactsByHstNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where hstNumber is not null
        defaultContactsShouldBeFound("hstNumber.specified=true");

        // Get all the contactsList where hstNumber is null
        defaultContactsShouldNotBeFound("hstNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByHstNumberContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where hstNumber contains DEFAULT_HST_NUMBER
        defaultContactsShouldBeFound("hstNumber.contains=" + DEFAULT_HST_NUMBER);

        // Get all the contactsList where hstNumber contains UPDATED_HST_NUMBER
        defaultContactsShouldNotBeFound("hstNumber.contains=" + UPDATED_HST_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactsByHstNumberNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where hstNumber does not contain DEFAULT_HST_NUMBER
        defaultContactsShouldNotBeFound("hstNumber.doesNotContain=" + DEFAULT_HST_NUMBER);

        // Get all the contactsList where hstNumber does not contain UPDATED_HST_NUMBER
        defaultContactsShouldBeFound("hstNumber.doesNotContain=" + UPDATED_HST_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactsByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where detail equals to DEFAULT_DETAIL
        defaultContactsShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the contactsList where detail equals to UPDATED_DETAIL
        defaultContactsShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactsByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultContactsShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the contactsList where detail equals to UPDATED_DETAIL
        defaultContactsShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactsByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where detail is not null
        defaultContactsShouldBeFound("detail.specified=true");

        // Get all the contactsList where detail is null
        defaultContactsShouldNotBeFound("detail.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByDetailContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where detail contains DEFAULT_DETAIL
        defaultContactsShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the contactsList where detail contains UPDATED_DETAIL
        defaultContactsShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactsByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where detail does not contain DEFAULT_DETAIL
        defaultContactsShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the contactsList where detail does not contain UPDATED_DETAIL
        defaultContactsShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where email equals to DEFAULT_EMAIL
        defaultContactsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contactsList where email equals to UPDATED_EMAIL
        defaultContactsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContactsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contactsList where email equals to UPDATED_EMAIL
        defaultContactsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where email is not null
        defaultContactsShouldBeFound("email.specified=true");

        // Get all the contactsList where email is null
        defaultContactsShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByEmailContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where email contains DEFAULT_EMAIL
        defaultContactsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contactsList where email contains UPDATED_EMAIL
        defaultContactsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where email does not contain DEFAULT_EMAIL
        defaultContactsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contactsList where email does not contain UPDATED_EMAIL
        defaultContactsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phone equals to DEFAULT_PHONE
        defaultContactsShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the contactsList where phone equals to UPDATED_PHONE
        defaultContactsShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultContactsShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the contactsList where phone equals to UPDATED_PHONE
        defaultContactsShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phone is not null
        defaultContactsShouldBeFound("phone.specified=true");

        // Get all the contactsList where phone is null
        defaultContactsShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phone contains DEFAULT_PHONE
        defaultContactsShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the contactsList where phone contains UPDATED_PHONE
        defaultContactsShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllContactsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where phone does not contain DEFAULT_PHONE
        defaultContactsShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the contactsList where phone does not contain UPDATED_PHONE
        defaultContactsShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllContactsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where gender equals to DEFAULT_GENDER
        defaultContactsShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the contactsList where gender equals to UPDATED_GENDER
        defaultContactsShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllContactsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultContactsShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the contactsList where gender equals to UPDATED_GENDER
        defaultContactsShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllContactsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where gender is not null
        defaultContactsShouldBeFound("gender.specified=true");

        // Get all the contactsList where gender is null
        defaultContactsShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate equals to DEFAULT_BIRTHDATE
        defaultContactsShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the contactsList where birthdate equals to UPDATED_BIRTHDATE
        defaultContactsShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultContactsShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the contactsList where birthdate equals to UPDATED_BIRTHDATE
        defaultContactsShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate is not null
        defaultContactsShouldBeFound("birthdate.specified=true");

        // Get all the contactsList where birthdate is null
        defaultContactsShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate is greater than or equal to DEFAULT_BIRTHDATE
        defaultContactsShouldBeFound("birthdate.greaterThanOrEqual=" + DEFAULT_BIRTHDATE);

        // Get all the contactsList where birthdate is greater than or equal to UPDATED_BIRTHDATE
        defaultContactsShouldNotBeFound("birthdate.greaterThanOrEqual=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate is less than or equal to DEFAULT_BIRTHDATE
        defaultContactsShouldBeFound("birthdate.lessThanOrEqual=" + DEFAULT_BIRTHDATE);

        // Get all the contactsList where birthdate is less than or equal to SMALLER_BIRTHDATE
        defaultContactsShouldNotBeFound("birthdate.lessThanOrEqual=" + SMALLER_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsLessThanSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate is less than DEFAULT_BIRTHDATE
        defaultContactsShouldNotBeFound("birthdate.lessThan=" + DEFAULT_BIRTHDATE);

        // Get all the contactsList where birthdate is less than UPDATED_BIRTHDATE
        defaultContactsShouldBeFound("birthdate.lessThan=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllContactsByBirthdateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where birthdate is greater than DEFAULT_BIRTHDATE
        defaultContactsShouldNotBeFound("birthdate.greaterThan=" + DEFAULT_BIRTHDATE);

        // Get all the contactsList where birthdate is greater than SMALLER_BIRTHDATE
        defaultContactsShouldBeFound("birthdate.greaterThan=" + SMALLER_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultContactsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the contactsList where createdDate equals to UPDATED_CREATED_DATE
        defaultContactsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultContactsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the contactsList where createdDate equals to UPDATED_CREATED_DATE
        defaultContactsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate is not null
        defaultContactsShouldBeFound("createdDate.specified=true");

        // Get all the contactsList where createdDate is null
        defaultContactsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultContactsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the contactsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultContactsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultContactsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the contactsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultContactsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultContactsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the contactsList where createdDate is less than UPDATED_CREATED_DATE
        defaultContactsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        // Get all the contactsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultContactsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the contactsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultContactsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactsByContactAddressesIsEqualToSomething() throws Exception {
        ContactAddresses contactAddresses;
        if (TestUtil.findAll(em, ContactAddresses.class).isEmpty()) {
            contactsRepository.saveAndFlush(contacts);
            contactAddresses = ContactAddressesResourceIT.createEntity(em);
        } else {
            contactAddresses = TestUtil.findAll(em, ContactAddresses.class).get(0);
        }
        em.persist(contactAddresses);
        em.flush();
        contacts.addContactAddresses(contactAddresses);
        contactsRepository.saveAndFlush(contacts);
        Long contactAddressesId = contactAddresses.getId();

        // Get all the contactsList where contactAddresses equals to contactAddressesId
        defaultContactsShouldBeFound("contactAddressesId.equals=" + contactAddressesId);

        // Get all the contactsList where contactAddresses equals to (contactAddressesId + 1)
        defaultContactsShouldNotBeFound("contactAddressesId.equals=" + (contactAddressesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactsShouldBeFound(String filter) throws Exception {
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hstNumber").value(hasItem(DEFAULT_HST_NUMBER)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.booleanValue())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactsShouldNotBeFound(String filter) throws Exception {
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContacts() throws Exception {
        // Get the contacts
        restContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts
        Contacts updatedContacts = contactsRepository.findById(contacts.getId()).get();
        // Disconnect from session so that the updates on updatedContacts are not directly saved in db
        em.detach(updatedContacts);
        updatedContacts
            .userID(UPDATED_USER_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .hstNumber(UPDATED_HST_NUMBER)
            .detail(UPDATED_DETAIL)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER)
            .birthdate(UPDATED_BIRTHDATE)
            .createdDate(UPDATED_CREATED_DATE);
        ContactsDTO contactsDTO = contactsMapper.toDto(updatedContacts);

        restContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testContacts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getHstNumber()).isEqualTo(UPDATED_HST_NUMBER);
        assertThat(testContacts.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testContacts.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContacts.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testContacts.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testContacts.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testContacts.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactsWithPatch() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts using partial update
        Contacts partialUpdatedContacts = new Contacts();
        partialUpdatedContacts.setId(contacts.getId());

        partialUpdatedContacts.userID(UPDATED_USER_ID).name(UPDATED_NAME).hstNumber(UPDATED_HST_NUMBER).email(UPDATED_EMAIL);

        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
            )
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testContacts.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getHstNumber()).isEqualTo(UPDATED_HST_NUMBER);
        assertThat(testContacts.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testContacts.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContacts.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testContacts.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testContacts.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testContacts.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateContactsWithPatch() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();

        // Update the contacts using partial update
        Contacts partialUpdatedContacts = new Contacts();
        partialUpdatedContacts.setId(contacts.getId());

        partialUpdatedContacts
            .userID(UPDATED_USER_ID)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .hstNumber(UPDATED_HST_NUMBER)
            .detail(UPDATED_DETAIL)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER)
            .birthdate(UPDATED_BIRTHDATE)
            .createdDate(UPDATED_CREATED_DATE);

        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
            )
            .andExpect(status().isOk());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
        Contacts testContacts = contactsList.get(contactsList.size() - 1);
        assertThat(testContacts.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testContacts.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContacts.getHstNumber()).isEqualTo(UPDATED_HST_NUMBER);
        assertThat(testContacts.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testContacts.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContacts.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testContacts.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testContacts.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testContacts.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContacts() throws Exception {
        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
        contacts.setId(count.incrementAndGet());

        // Create the Contacts
        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacts in the database
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContacts() throws Exception {
        // Initialize the database
        contactsRepository.saveAndFlush(contacts);

        int databaseSizeBeforeDelete = contactsRepository.findAll().size();

        // Delete the contacts
        restContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, contacts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contacts> contactsList = contactsRepository.findAll();
        assertThat(contactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
