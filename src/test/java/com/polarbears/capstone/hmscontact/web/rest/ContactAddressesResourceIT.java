package com.polarbears.capstone.hmscontact.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmscontact.IntegrationTest;
import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import com.polarbears.capstone.hmscontact.domain.Contacts;
import com.polarbears.capstone.hmscontact.domain.enumeration.EMPLOYMENTTYPES;
import com.polarbears.capstone.hmscontact.repository.ContactAddressesRepository;
import com.polarbears.capstone.hmscontact.service.criteria.ContactAddressesCriteria;
import com.polarbears.capstone.hmscontact.service.dto.ContactAddressesDTO;
import com.polarbears.capstone.hmscontact.service.mapper.ContactAddressesMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactAddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactAddressesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSSINESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_BUSSINESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CONTRACT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTRACT_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTRACT_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_AGRREMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGRREMENT_ID = "BBBBBBBBBB";

    private static final EMPLOYMENTTYPES DEFAULT_EMPLOYMENT_TYPE = EMPLOYMENTTYPES.CONTRACTED;
    private static final EMPLOYMENTTYPES UPDATED_EMPLOYMENT_TYPE = EMPLOYMENTTYPES.FULLTIME;

    private static final Double DEFAULT_HOURLY_RATE = 1D;
    private static final Double UPDATED_HOURLY_RATE = 2D;
    private static final Double SMALLER_HOURLY_RATE = 1D - 1D;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/contact-addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactAddressesRepository contactAddressesRepository;

    @Autowired
    private ContactAddressesMapper contactAddressesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactAddressesMockMvc;

    private ContactAddresses contactAddresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactAddresses createEntity(EntityManager em) {
        ContactAddresses contactAddresses = new ContactAddresses()
            .name(DEFAULT_NAME)
            .bussinessName(DEFAULT_BUSSINESS_NAME)
            .bussinessId(DEFAULT_BUSSINESS_ID)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .province(DEFAULT_PROVINCE)
            .detail(DEFAULT_DETAIL)
            .active(DEFAULT_ACTIVE)
            .contractStartDate(DEFAULT_CONTRACT_START_DATE)
            .agrrementId(DEFAULT_AGRREMENT_ID)
            .employmentType(DEFAULT_EMPLOYMENT_TYPE)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .createdDate(DEFAULT_CREATED_DATE);
        return contactAddresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactAddresses createUpdatedEntity(EntityManager em) {
        ContactAddresses contactAddresses = new ContactAddresses()
            .name(UPDATED_NAME)
            .bussinessName(UPDATED_BUSSINESS_NAME)
            .bussinessId(UPDATED_BUSSINESS_ID)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .province(UPDATED_PROVINCE)
            .detail(UPDATED_DETAIL)
            .active(UPDATED_ACTIVE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .agrrementId(UPDATED_AGRREMENT_ID)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .createdDate(UPDATED_CREATED_DATE);
        return contactAddresses;
    }

    @BeforeEach
    public void initTest() {
        contactAddresses = createEntity(em);
    }

    @Test
    @Transactional
    void createContactAddresses() throws Exception {
        int databaseSizeBeforeCreate = contactAddressesRepository.findAll().size();
        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);
        restContactAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeCreate + 1);
        ContactAddresses testContactAddresses = contactAddressesList.get(contactAddressesList.size() - 1);
        assertThat(testContactAddresses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContactAddresses.getBussinessName()).isEqualTo(DEFAULT_BUSSINESS_NAME);
        assertThat(testContactAddresses.getBussinessId()).isEqualTo(DEFAULT_BUSSINESS_ID);
        assertThat(testContactAddresses.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testContactAddresses.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testContactAddresses.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testContactAddresses.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testContactAddresses.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testContactAddresses.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testContactAddresses.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testContactAddresses.getContractStartDate()).isEqualTo(DEFAULT_CONTRACT_START_DATE);
        assertThat(testContactAddresses.getAgrrementId()).isEqualTo(DEFAULT_AGRREMENT_ID);
        assertThat(testContactAddresses.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
        assertThat(testContactAddresses.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testContactAddresses.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createContactAddressesWithExistingId() throws Exception {
        // Create the ContactAddresses with an existing ID
        contactAddresses.setId(1L);
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        int databaseSizeBeforeCreate = contactAddressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactAddressesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContactAddresses() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList
        restContactAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bussinessName").value(hasItem(DEFAULT_BUSSINESS_NAME)))
            .andExpect(jsonPath("$.[*].bussinessId").value(hasItem(DEFAULT_BUSSINESS_ID)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agrrementId").value(hasItem(DEFAULT_AGRREMENT_ID)))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getContactAddresses() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get the contactAddresses
        restContactAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, contactAddresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactAddresses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bussinessName").value(DEFAULT_BUSSINESS_NAME))
            .andExpect(jsonPath("$.bussinessId").value(DEFAULT_BUSSINESS_ID))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.contractStartDate").value(DEFAULT_CONTRACT_START_DATE.toString()))
            .andExpect(jsonPath("$.agrrementId").value(DEFAULT_AGRREMENT_ID))
            .andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getContactAddressesByIdFiltering() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        Long id = contactAddresses.getId();

        defaultContactAddressesShouldBeFound("id.equals=" + id);
        defaultContactAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultContactAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultContactAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactAddressesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContactAddressesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where name equals to DEFAULT_NAME
        defaultContactAddressesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactAddressesList where name equals to UPDATED_NAME
        defaultContactAddressesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactAddressesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactAddressesList where name equals to UPDATED_NAME
        defaultContactAddressesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where name is not null
        defaultContactAddressesShouldBeFound("name.specified=true");

        // Get all the contactAddressesList where name is null
        defaultContactAddressesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByNameContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where name contains DEFAULT_NAME
        defaultContactAddressesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactAddressesList where name contains UPDATED_NAME
        defaultContactAddressesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where name does not contain DEFAULT_NAME
        defaultContactAddressesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactAddressesList where name does not contain UPDATED_NAME
        defaultContactAddressesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessName equals to DEFAULT_BUSSINESS_NAME
        defaultContactAddressesShouldBeFound("bussinessName.equals=" + DEFAULT_BUSSINESS_NAME);

        // Get all the contactAddressesList where bussinessName equals to UPDATED_BUSSINESS_NAME
        defaultContactAddressesShouldNotBeFound("bussinessName.equals=" + UPDATED_BUSSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessName in DEFAULT_BUSSINESS_NAME or UPDATED_BUSSINESS_NAME
        defaultContactAddressesShouldBeFound("bussinessName.in=" + DEFAULT_BUSSINESS_NAME + "," + UPDATED_BUSSINESS_NAME);

        // Get all the contactAddressesList where bussinessName equals to UPDATED_BUSSINESS_NAME
        defaultContactAddressesShouldNotBeFound("bussinessName.in=" + UPDATED_BUSSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessName is not null
        defaultContactAddressesShouldBeFound("bussinessName.specified=true");

        // Get all the contactAddressesList where bussinessName is null
        defaultContactAddressesShouldNotBeFound("bussinessName.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessNameContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessName contains DEFAULT_BUSSINESS_NAME
        defaultContactAddressesShouldBeFound("bussinessName.contains=" + DEFAULT_BUSSINESS_NAME);

        // Get all the contactAddressesList where bussinessName contains UPDATED_BUSSINESS_NAME
        defaultContactAddressesShouldNotBeFound("bussinessName.contains=" + UPDATED_BUSSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessName does not contain DEFAULT_BUSSINESS_NAME
        defaultContactAddressesShouldNotBeFound("bussinessName.doesNotContain=" + DEFAULT_BUSSINESS_NAME);

        // Get all the contactAddressesList where bussinessName does not contain UPDATED_BUSSINESS_NAME
        defaultContactAddressesShouldBeFound("bussinessName.doesNotContain=" + UPDATED_BUSSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessId equals to DEFAULT_BUSSINESS_ID
        defaultContactAddressesShouldBeFound("bussinessId.equals=" + DEFAULT_BUSSINESS_ID);

        // Get all the contactAddressesList where bussinessId equals to UPDATED_BUSSINESS_ID
        defaultContactAddressesShouldNotBeFound("bussinessId.equals=" + UPDATED_BUSSINESS_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessIdIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessId in DEFAULT_BUSSINESS_ID or UPDATED_BUSSINESS_ID
        defaultContactAddressesShouldBeFound("bussinessId.in=" + DEFAULT_BUSSINESS_ID + "," + UPDATED_BUSSINESS_ID);

        // Get all the contactAddressesList where bussinessId equals to UPDATED_BUSSINESS_ID
        defaultContactAddressesShouldNotBeFound("bussinessId.in=" + UPDATED_BUSSINESS_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessId is not null
        defaultContactAddressesShouldBeFound("bussinessId.specified=true");

        // Get all the contactAddressesList where bussinessId is null
        defaultContactAddressesShouldNotBeFound("bussinessId.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessIdContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessId contains DEFAULT_BUSSINESS_ID
        defaultContactAddressesShouldBeFound("bussinessId.contains=" + DEFAULT_BUSSINESS_ID);

        // Get all the contactAddressesList where bussinessId contains UPDATED_BUSSINESS_ID
        defaultContactAddressesShouldNotBeFound("bussinessId.contains=" + UPDATED_BUSSINESS_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByBussinessIdNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where bussinessId does not contain DEFAULT_BUSSINESS_ID
        defaultContactAddressesShouldNotBeFound("bussinessId.doesNotContain=" + DEFAULT_BUSSINESS_ID);

        // Get all the contactAddressesList where bussinessId does not contain UPDATED_BUSSINESS_ID
        defaultContactAddressesShouldBeFound("bussinessId.doesNotContain=" + UPDATED_BUSSINESS_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address1 equals to DEFAULT_ADDRESS_1
        defaultContactAddressesShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the contactAddressesList where address1 equals to UPDATED_ADDRESS_1
        defaultContactAddressesShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultContactAddressesShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the contactAddressesList where address1 equals to UPDATED_ADDRESS_1
        defaultContactAddressesShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address1 is not null
        defaultContactAddressesShouldBeFound("address1.specified=true");

        // Get all the contactAddressesList where address1 is null
        defaultContactAddressesShouldNotBeFound("address1.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address1 contains DEFAULT_ADDRESS_1
        defaultContactAddressesShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the contactAddressesList where address1 contains UPDATED_ADDRESS_1
        defaultContactAddressesShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address1 does not contain DEFAULT_ADDRESS_1
        defaultContactAddressesShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the contactAddressesList where address1 does not contain UPDATED_ADDRESS_1
        defaultContactAddressesShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address2 equals to DEFAULT_ADDRESS_2
        defaultContactAddressesShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the contactAddressesList where address2 equals to UPDATED_ADDRESS_2
        defaultContactAddressesShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultContactAddressesShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the contactAddressesList where address2 equals to UPDATED_ADDRESS_2
        defaultContactAddressesShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address2 is not null
        defaultContactAddressesShouldBeFound("address2.specified=true");

        // Get all the contactAddressesList where address2 is null
        defaultContactAddressesShouldNotBeFound("address2.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address2 contains DEFAULT_ADDRESS_2
        defaultContactAddressesShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the contactAddressesList where address2 contains UPDATED_ADDRESS_2
        defaultContactAddressesShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where address2 does not contain DEFAULT_ADDRESS_2
        defaultContactAddressesShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the contactAddressesList where address2 does not contain UPDATED_ADDRESS_2
        defaultContactAddressesShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where city equals to DEFAULT_CITY
        defaultContactAddressesShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the contactAddressesList where city equals to UPDATED_CITY
        defaultContactAddressesShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where city in DEFAULT_CITY or UPDATED_CITY
        defaultContactAddressesShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the contactAddressesList where city equals to UPDATED_CITY
        defaultContactAddressesShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where city is not null
        defaultContactAddressesShouldBeFound("city.specified=true");

        // Get all the contactAddressesList where city is null
        defaultContactAddressesShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByCityContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where city contains DEFAULT_CITY
        defaultContactAddressesShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the contactAddressesList where city contains UPDATED_CITY
        defaultContactAddressesShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where city does not contain DEFAULT_CITY
        defaultContactAddressesShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the contactAddressesList where city does not contain UPDATED_CITY
        defaultContactAddressesShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllContactAddressesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultContactAddressesShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the contactAddressesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultContactAddressesShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultContactAddressesShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the contactAddressesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultContactAddressesShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where postalCode is not null
        defaultContactAddressesShouldBeFound("postalCode.specified=true");

        // Get all the contactAddressesList where postalCode is null
        defaultContactAddressesShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where postalCode contains DEFAULT_POSTAL_CODE
        defaultContactAddressesShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the contactAddressesList where postalCode contains UPDATED_POSTAL_CODE
        defaultContactAddressesShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultContactAddressesShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the contactAddressesList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultContactAddressesShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where province equals to DEFAULT_PROVINCE
        defaultContactAddressesShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

        // Get all the contactAddressesList where province equals to UPDATED_PROVINCE
        defaultContactAddressesShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
        defaultContactAddressesShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

        // Get all the contactAddressesList where province equals to UPDATED_PROVINCE
        defaultContactAddressesShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where province is not null
        defaultContactAddressesShouldBeFound("province.specified=true");

        // Get all the contactAddressesList where province is null
        defaultContactAddressesShouldNotBeFound("province.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByProvinceContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where province contains DEFAULT_PROVINCE
        defaultContactAddressesShouldBeFound("province.contains=" + DEFAULT_PROVINCE);

        // Get all the contactAddressesList where province contains UPDATED_PROVINCE
        defaultContactAddressesShouldNotBeFound("province.contains=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where province does not contain DEFAULT_PROVINCE
        defaultContactAddressesShouldNotBeFound("province.doesNotContain=" + DEFAULT_PROVINCE);

        // Get all the contactAddressesList where province does not contain UPDATED_PROVINCE
        defaultContactAddressesShouldBeFound("province.doesNotContain=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where detail equals to DEFAULT_DETAIL
        defaultContactAddressesShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the contactAddressesList where detail equals to UPDATED_DETAIL
        defaultContactAddressesShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactAddressesByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultContactAddressesShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the contactAddressesList where detail equals to UPDATED_DETAIL
        defaultContactAddressesShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactAddressesByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where detail is not null
        defaultContactAddressesShouldBeFound("detail.specified=true");

        // Get all the contactAddressesList where detail is null
        defaultContactAddressesShouldNotBeFound("detail.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByDetailContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where detail contains DEFAULT_DETAIL
        defaultContactAddressesShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the contactAddressesList where detail contains UPDATED_DETAIL
        defaultContactAddressesShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactAddressesByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where detail does not contain DEFAULT_DETAIL
        defaultContactAddressesShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the contactAddressesList where detail does not contain UPDATED_DETAIL
        defaultContactAddressesShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllContactAddressesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where active equals to DEFAULT_ACTIVE
        defaultContactAddressesShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the contactAddressesList where active equals to UPDATED_ACTIVE
        defaultContactAddressesShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultContactAddressesShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the contactAddressesList where active equals to UPDATED_ACTIVE
        defaultContactAddressesShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where active is not null
        defaultContactAddressesShouldBeFound("active.specified=true");

        // Get all the contactAddressesList where active is null
        defaultContactAddressesShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate equals to DEFAULT_CONTRACT_START_DATE
        defaultContactAddressesShouldBeFound("contractStartDate.equals=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the contactAddressesList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultContactAddressesShouldNotBeFound("contractStartDate.equals=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate in DEFAULT_CONTRACT_START_DATE or UPDATED_CONTRACT_START_DATE
        defaultContactAddressesShouldBeFound("contractStartDate.in=" + DEFAULT_CONTRACT_START_DATE + "," + UPDATED_CONTRACT_START_DATE);

        // Get all the contactAddressesList where contractStartDate equals to UPDATED_CONTRACT_START_DATE
        defaultContactAddressesShouldNotBeFound("contractStartDate.in=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate is not null
        defaultContactAddressesShouldBeFound("contractStartDate.specified=true");

        // Get all the contactAddressesList where contractStartDate is null
        defaultContactAddressesShouldNotBeFound("contractStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate is greater than or equal to DEFAULT_CONTRACT_START_DATE
        defaultContactAddressesShouldBeFound("contractStartDate.greaterThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the contactAddressesList where contractStartDate is greater than or equal to UPDATED_CONTRACT_START_DATE
        defaultContactAddressesShouldNotBeFound("contractStartDate.greaterThanOrEqual=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate is less than or equal to DEFAULT_CONTRACT_START_DATE
        defaultContactAddressesShouldBeFound("contractStartDate.lessThanOrEqual=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the contactAddressesList where contractStartDate is less than or equal to SMALLER_CONTRACT_START_DATE
        defaultContactAddressesShouldNotBeFound("contractStartDate.lessThanOrEqual=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate is less than DEFAULT_CONTRACT_START_DATE
        defaultContactAddressesShouldNotBeFound("contractStartDate.lessThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the contactAddressesList where contractStartDate is less than UPDATED_CONTRACT_START_DATE
        defaultContactAddressesShouldBeFound("contractStartDate.lessThan=" + UPDATED_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByContractStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where contractStartDate is greater than DEFAULT_CONTRACT_START_DATE
        defaultContactAddressesShouldNotBeFound("contractStartDate.greaterThan=" + DEFAULT_CONTRACT_START_DATE);

        // Get all the contactAddressesList where contractStartDate is greater than SMALLER_CONTRACT_START_DATE
        defaultContactAddressesShouldBeFound("contractStartDate.greaterThan=" + SMALLER_CONTRACT_START_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAgrrementIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where agrrementId equals to DEFAULT_AGRREMENT_ID
        defaultContactAddressesShouldBeFound("agrrementId.equals=" + DEFAULT_AGRREMENT_ID);

        // Get all the contactAddressesList where agrrementId equals to UPDATED_AGRREMENT_ID
        defaultContactAddressesShouldNotBeFound("agrrementId.equals=" + UPDATED_AGRREMENT_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAgrrementIdIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where agrrementId in DEFAULT_AGRREMENT_ID or UPDATED_AGRREMENT_ID
        defaultContactAddressesShouldBeFound("agrrementId.in=" + DEFAULT_AGRREMENT_ID + "," + UPDATED_AGRREMENT_ID);

        // Get all the contactAddressesList where agrrementId equals to UPDATED_AGRREMENT_ID
        defaultContactAddressesShouldNotBeFound("agrrementId.in=" + UPDATED_AGRREMENT_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAgrrementIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where agrrementId is not null
        defaultContactAddressesShouldBeFound("agrrementId.specified=true");

        // Get all the contactAddressesList where agrrementId is null
        defaultContactAddressesShouldNotBeFound("agrrementId.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByAgrrementIdContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where agrrementId contains DEFAULT_AGRREMENT_ID
        defaultContactAddressesShouldBeFound("agrrementId.contains=" + DEFAULT_AGRREMENT_ID);

        // Get all the contactAddressesList where agrrementId contains UPDATED_AGRREMENT_ID
        defaultContactAddressesShouldNotBeFound("agrrementId.contains=" + UPDATED_AGRREMENT_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByAgrrementIdNotContainsSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where agrrementId does not contain DEFAULT_AGRREMENT_ID
        defaultContactAddressesShouldNotBeFound("agrrementId.doesNotContain=" + DEFAULT_AGRREMENT_ID);

        // Get all the contactAddressesList where agrrementId does not contain UPDATED_AGRREMENT_ID
        defaultContactAddressesShouldBeFound("agrrementId.doesNotContain=" + UPDATED_AGRREMENT_ID);
    }

    @Test
    @Transactional
    void getAllContactAddressesByEmploymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where employmentType equals to DEFAULT_EMPLOYMENT_TYPE
        defaultContactAddressesShouldBeFound("employmentType.equals=" + DEFAULT_EMPLOYMENT_TYPE);

        // Get all the contactAddressesList where employmentType equals to UPDATED_EMPLOYMENT_TYPE
        defaultContactAddressesShouldNotBeFound("employmentType.equals=" + UPDATED_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByEmploymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where employmentType in DEFAULT_EMPLOYMENT_TYPE or UPDATED_EMPLOYMENT_TYPE
        defaultContactAddressesShouldBeFound("employmentType.in=" + DEFAULT_EMPLOYMENT_TYPE + "," + UPDATED_EMPLOYMENT_TYPE);

        // Get all the contactAddressesList where employmentType equals to UPDATED_EMPLOYMENT_TYPE
        defaultContactAddressesShouldNotBeFound("employmentType.in=" + UPDATED_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByEmploymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where employmentType is not null
        defaultContactAddressesShouldBeFound("employmentType.specified=true");

        // Get all the contactAddressesList where employmentType is null
        defaultContactAddressesShouldNotBeFound("employmentType.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate equals to DEFAULT_HOURLY_RATE
        defaultContactAddressesShouldBeFound("hourlyRate.equals=" + DEFAULT_HOURLY_RATE);

        // Get all the contactAddressesList where hourlyRate equals to UPDATED_HOURLY_RATE
        defaultContactAddressesShouldNotBeFound("hourlyRate.equals=" + UPDATED_HOURLY_RATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate in DEFAULT_HOURLY_RATE or UPDATED_HOURLY_RATE
        defaultContactAddressesShouldBeFound("hourlyRate.in=" + DEFAULT_HOURLY_RATE + "," + UPDATED_HOURLY_RATE);

        // Get all the contactAddressesList where hourlyRate equals to UPDATED_HOURLY_RATE
        defaultContactAddressesShouldNotBeFound("hourlyRate.in=" + UPDATED_HOURLY_RATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate is not null
        defaultContactAddressesShouldBeFound("hourlyRate.specified=true");

        // Get all the contactAddressesList where hourlyRate is null
        defaultContactAddressesShouldNotBeFound("hourlyRate.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate is greater than or equal to DEFAULT_HOURLY_RATE
        defaultContactAddressesShouldBeFound("hourlyRate.greaterThanOrEqual=" + DEFAULT_HOURLY_RATE);

        // Get all the contactAddressesList where hourlyRate is greater than or equal to UPDATED_HOURLY_RATE
        defaultContactAddressesShouldNotBeFound("hourlyRate.greaterThanOrEqual=" + UPDATED_HOURLY_RATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate is less than or equal to DEFAULT_HOURLY_RATE
        defaultContactAddressesShouldBeFound("hourlyRate.lessThanOrEqual=" + DEFAULT_HOURLY_RATE);

        // Get all the contactAddressesList where hourlyRate is less than or equal to SMALLER_HOURLY_RATE
        defaultContactAddressesShouldNotBeFound("hourlyRate.lessThanOrEqual=" + SMALLER_HOURLY_RATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsLessThanSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate is less than DEFAULT_HOURLY_RATE
        defaultContactAddressesShouldNotBeFound("hourlyRate.lessThan=" + DEFAULT_HOURLY_RATE);

        // Get all the contactAddressesList where hourlyRate is less than UPDATED_HOURLY_RATE
        defaultContactAddressesShouldBeFound("hourlyRate.lessThan=" + UPDATED_HOURLY_RATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByHourlyRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where hourlyRate is greater than DEFAULT_HOURLY_RATE
        defaultContactAddressesShouldNotBeFound("hourlyRate.greaterThan=" + DEFAULT_HOURLY_RATE);

        // Get all the contactAddressesList where hourlyRate is greater than SMALLER_HOURLY_RATE
        defaultContactAddressesShouldBeFound("hourlyRate.greaterThan=" + SMALLER_HOURLY_RATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate equals to DEFAULT_CREATED_DATE
        defaultContactAddressesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the contactAddressesList where createdDate equals to UPDATED_CREATED_DATE
        defaultContactAddressesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultContactAddressesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the contactAddressesList where createdDate equals to UPDATED_CREATED_DATE
        defaultContactAddressesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate is not null
        defaultContactAddressesShouldBeFound("createdDate.specified=true");

        // Get all the contactAddressesList where createdDate is null
        defaultContactAddressesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultContactAddressesShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the contactAddressesList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultContactAddressesShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultContactAddressesShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the contactAddressesList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultContactAddressesShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate is less than DEFAULT_CREATED_DATE
        defaultContactAddressesShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the contactAddressesList where createdDate is less than UPDATED_CREATED_DATE
        defaultContactAddressesShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        // Get all the contactAddressesList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultContactAddressesShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the contactAddressesList where createdDate is greater than SMALLER_CREATED_DATE
        defaultContactAddressesShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllContactAddressesByContactsIsEqualToSomething() throws Exception {
        Contacts contacts;
        if (TestUtil.findAll(em, Contacts.class).isEmpty()) {
            contactAddressesRepository.saveAndFlush(contactAddresses);
            contacts = ContactsResourceIT.createEntity(em);
        } else {
            contacts = TestUtil.findAll(em, Contacts.class).get(0);
        }
        em.persist(contacts);
        em.flush();
        contactAddresses.addContacts(contacts);
        contactAddressesRepository.saveAndFlush(contactAddresses);
        Long contactsId = contacts.getId();

        // Get all the contactAddressesList where contacts equals to contactsId
        defaultContactAddressesShouldBeFound("contactsId.equals=" + contactsId);

        // Get all the contactAddressesList where contacts equals to (contactsId + 1)
        defaultContactAddressesShouldNotBeFound("contactsId.equals=" + (contactsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactAddressesShouldBeFound(String filter) throws Exception {
        restContactAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bussinessName").value(hasItem(DEFAULT_BUSSINESS_NAME)))
            .andExpect(jsonPath("$.[*].bussinessId").value(hasItem(DEFAULT_BUSSINESS_ID)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].contractStartDate").value(hasItem(DEFAULT_CONTRACT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].agrrementId").value(hasItem(DEFAULT_AGRREMENT_ID)))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restContactAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactAddressesShouldNotBeFound(String filter) throws Exception {
        restContactAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContactAddresses() throws Exception {
        // Get the contactAddresses
        restContactAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContactAddresses() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();

        // Update the contactAddresses
        ContactAddresses updatedContactAddresses = contactAddressesRepository.findById(contactAddresses.getId()).get();
        // Disconnect from session so that the updates on updatedContactAddresses are not directly saved in db
        em.detach(updatedContactAddresses);
        updatedContactAddresses
            .name(UPDATED_NAME)
            .bussinessName(UPDATED_BUSSINESS_NAME)
            .bussinessId(UPDATED_BUSSINESS_ID)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .province(UPDATED_PROVINCE)
            .detail(UPDATED_DETAIL)
            .active(UPDATED_ACTIVE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .agrrementId(UPDATED_AGRREMENT_ID)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .createdDate(UPDATED_CREATED_DATE);
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(updatedContactAddresses);

        restContactAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactAddressesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
        ContactAddresses testContactAddresses = contactAddressesList.get(contactAddressesList.size() - 1);
        assertThat(testContactAddresses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContactAddresses.getBussinessName()).isEqualTo(UPDATED_BUSSINESS_NAME);
        assertThat(testContactAddresses.getBussinessId()).isEqualTo(UPDATED_BUSSINESS_ID);
        assertThat(testContactAddresses.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testContactAddresses.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testContactAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContactAddresses.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testContactAddresses.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testContactAddresses.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testContactAddresses.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testContactAddresses.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testContactAddresses.getAgrrementId()).isEqualTo(UPDATED_AGRREMENT_ID);
        assertThat(testContactAddresses.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
        assertThat(testContactAddresses.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testContactAddresses.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingContactAddresses() throws Exception {
        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();
        contactAddresses.setId(count.incrementAndGet());

        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactAddressesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactAddresses() throws Exception {
        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();
        contactAddresses.setId(count.incrementAndGet());

        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactAddresses() throws Exception {
        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();
        contactAddresses.setId(count.incrementAndGet());

        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactAddressesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactAddressesWithPatch() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();

        // Update the contactAddresses using partial update
        ContactAddresses partialUpdatedContactAddresses = new ContactAddresses();
        partialUpdatedContactAddresses.setId(contactAddresses.getId());

        partialUpdatedContactAddresses
            .name(UPDATED_NAME)
            .bussinessId(UPDATED_BUSSINESS_ID)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .detail(UPDATED_DETAIL)
            .active(UPDATED_ACTIVE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .agrrementId(UPDATED_AGRREMENT_ID);

        restContactAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactAddresses))
            )
            .andExpect(status().isOk());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
        ContactAddresses testContactAddresses = contactAddressesList.get(contactAddressesList.size() - 1);
        assertThat(testContactAddresses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContactAddresses.getBussinessName()).isEqualTo(DEFAULT_BUSSINESS_NAME);
        assertThat(testContactAddresses.getBussinessId()).isEqualTo(UPDATED_BUSSINESS_ID);
        assertThat(testContactAddresses.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testContactAddresses.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testContactAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContactAddresses.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testContactAddresses.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testContactAddresses.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testContactAddresses.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testContactAddresses.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testContactAddresses.getAgrrementId()).isEqualTo(UPDATED_AGRREMENT_ID);
        assertThat(testContactAddresses.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
        assertThat(testContactAddresses.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testContactAddresses.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateContactAddressesWithPatch() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();

        // Update the contactAddresses using partial update
        ContactAddresses partialUpdatedContactAddresses = new ContactAddresses();
        partialUpdatedContactAddresses.setId(contactAddresses.getId());

        partialUpdatedContactAddresses
            .name(UPDATED_NAME)
            .bussinessName(UPDATED_BUSSINESS_NAME)
            .bussinessId(UPDATED_BUSSINESS_ID)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .province(UPDATED_PROVINCE)
            .detail(UPDATED_DETAIL)
            .active(UPDATED_ACTIVE)
            .contractStartDate(UPDATED_CONTRACT_START_DATE)
            .agrrementId(UPDATED_AGRREMENT_ID)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .createdDate(UPDATED_CREATED_DATE);

        restContactAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactAddresses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactAddresses))
            )
            .andExpect(status().isOk());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
        ContactAddresses testContactAddresses = contactAddressesList.get(contactAddressesList.size() - 1);
        assertThat(testContactAddresses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContactAddresses.getBussinessName()).isEqualTo(UPDATED_BUSSINESS_NAME);
        assertThat(testContactAddresses.getBussinessId()).isEqualTo(UPDATED_BUSSINESS_ID);
        assertThat(testContactAddresses.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testContactAddresses.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testContactAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testContactAddresses.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testContactAddresses.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testContactAddresses.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testContactAddresses.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testContactAddresses.getContractStartDate()).isEqualTo(UPDATED_CONTRACT_START_DATE);
        assertThat(testContactAddresses.getAgrrementId()).isEqualTo(UPDATED_AGRREMENT_ID);
        assertThat(testContactAddresses.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
        assertThat(testContactAddresses.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testContactAddresses.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingContactAddresses() throws Exception {
        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();
        contactAddresses.setId(count.incrementAndGet());

        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactAddressesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactAddresses() throws Exception {
        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();
        contactAddresses.setId(count.incrementAndGet());

        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactAddresses() throws Exception {
        int databaseSizeBeforeUpdate = contactAddressesRepository.findAll().size();
        contactAddresses.setId(count.incrementAndGet());

        // Create the ContactAddresses
        ContactAddressesDTO contactAddressesDTO = contactAddressesMapper.toDto(contactAddresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactAddressesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactAddresses in the database
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactAddresses() throws Exception {
        // Initialize the database
        contactAddressesRepository.saveAndFlush(contactAddresses);

        int databaseSizeBeforeDelete = contactAddressesRepository.findAll().size();

        // Delete the contactAddresses
        restContactAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactAddresses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactAddresses> contactAddressesList = contactAddressesRepository.findAll();
        assertThat(contactAddressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
