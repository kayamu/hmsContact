package com.polarbears.capstone.hmscontact.web.rest;

import com.polarbears.capstone.hmscontact.repository.ContactAddressesRepository;
import com.polarbears.capstone.hmscontact.service.ContactAddressesQueryService;
import com.polarbears.capstone.hmscontact.service.ContactAddressesService;
import com.polarbears.capstone.hmscontact.service.criteria.ContactAddressesCriteria;
import com.polarbears.capstone.hmscontact.service.dto.ContactAddressesDTO;
import com.polarbears.capstone.hmscontact.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.polarbears.capstone.hmscontact.domain.ContactAddresses}.
 */
@RestController
@RequestMapping("/api")
public class ContactAddressesResource {

    private final Logger log = LoggerFactory.getLogger(ContactAddressesResource.class);

    private static final String ENTITY_NAME = "hmscontactContactAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactAddressesService contactAddressesService;

    private final ContactAddressesRepository contactAddressesRepository;

    private final ContactAddressesQueryService contactAddressesQueryService;

    public ContactAddressesResource(
        ContactAddressesService contactAddressesService,
        ContactAddressesRepository contactAddressesRepository,
        ContactAddressesQueryService contactAddressesQueryService
    ) {
        this.contactAddressesService = contactAddressesService;
        this.contactAddressesRepository = contactAddressesRepository;
        this.contactAddressesQueryService = contactAddressesQueryService;
    }

    /**
     * {@code POST  /contact-addresses} : Create a new contactAddresses.
     *
     * @param contactAddressesDTO the contactAddressesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactAddressesDTO, or with status {@code 400 (Bad Request)} if the contactAddresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-addresses")
    public ResponseEntity<ContactAddressesDTO> createContactAddresses(@RequestBody ContactAddressesDTO contactAddressesDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContactAddresses : {}", contactAddressesDTO);
        if (contactAddressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactAddresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactAddressesDTO result = contactAddressesService.save(contactAddressesDTO);
        return ResponseEntity
            .created(new URI("/api/contact-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-addresses/:id} : Updates an existing contactAddresses.
     *
     * @param id the id of the contactAddressesDTO to save.
     * @param contactAddressesDTO the contactAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the contactAddressesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-addresses/{id}")
    public ResponseEntity<ContactAddressesDTO> updateContactAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContactAddressesDTO contactAddressesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContactAddresses : {}, {}", id, contactAddressesDTO);
        if (contactAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactAddressesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactAddressesDTO result = contactAddressesService.update(contactAddressesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactAddressesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-addresses/:id} : Partial updates given fields of an existing contactAddresses, field will ignore if it is null
     *
     * @param id the id of the contactAddressesDTO to save.
     * @param contactAddressesDTO the contactAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the contactAddressesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactAddressesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactAddressesDTO> partialUpdateContactAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContactAddressesDTO contactAddressesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactAddresses partially : {}, {}", id, contactAddressesDTO);
        if (contactAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactAddressesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactAddressesDTO> result = contactAddressesService.partialUpdate(contactAddressesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactAddressesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-addresses} : get all the contactAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactAddresses in body.
     */
    @GetMapping("/contact-addresses")
    public ResponseEntity<List<ContactAddressesDTO>> getAllContactAddresses(ContactAddressesCriteria criteria) {
        log.debug("REST request to get ContactAddresses by criteria: {}", criteria);
        List<ContactAddressesDTO> entityList = contactAddressesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /contact-addresses/count} : count all the contactAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contact-addresses/count")
    public ResponseEntity<Long> countContactAddresses(ContactAddressesCriteria criteria) {
        log.debug("REST request to count ContactAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(contactAddressesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contact-addresses/:id} : get the "id" contactAddresses.
     *
     * @param id the id of the contactAddressesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactAddressesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-addresses/{id}")
    public ResponseEntity<ContactAddressesDTO> getContactAddresses(@PathVariable Long id) {
        log.debug("REST request to get ContactAddresses : {}", id);
        Optional<ContactAddressesDTO> contactAddressesDTO = contactAddressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactAddressesDTO);
    }

    /**
     * {@code DELETE  /contact-addresses/:id} : delete the "id" contactAddresses.
     *
     * @param id the id of the contactAddressesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-addresses/{id}")
    public ResponseEntity<Void> deleteContactAddresses(@PathVariable Long id) {
        log.debug("REST request to delete ContactAddresses : {}", id);
        contactAddressesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
