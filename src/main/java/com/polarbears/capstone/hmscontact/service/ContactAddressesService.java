package com.polarbears.capstone.hmscontact.service;

import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import com.polarbears.capstone.hmscontact.repository.ContactAddressesRepository;
import com.polarbears.capstone.hmscontact.service.dto.ContactAddressesDTO;
import com.polarbears.capstone.hmscontact.service.mapper.ContactAddressesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactAddresses}.
 */
@Service
@Transactional
public class ContactAddressesService {

    private final Logger log = LoggerFactory.getLogger(ContactAddressesService.class);

    private final ContactAddressesRepository contactAddressesRepository;

    private final ContactAddressesMapper contactAddressesMapper;

    public ContactAddressesService(ContactAddressesRepository contactAddressesRepository, ContactAddressesMapper contactAddressesMapper) {
        this.contactAddressesRepository = contactAddressesRepository;
        this.contactAddressesMapper = contactAddressesMapper;
    }

    /**
     * Save a contactAddresses.
     *
     * @param contactAddressesDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactAddressesDTO save(ContactAddressesDTO contactAddressesDTO) {
        log.debug("Request to save ContactAddresses : {}", contactAddressesDTO);
        ContactAddresses contactAddresses = contactAddressesMapper.toEntity(contactAddressesDTO);
        contactAddresses = contactAddressesRepository.save(contactAddresses);
        return contactAddressesMapper.toDto(contactAddresses);
    }

    /**
     * Update a contactAddresses.
     *
     * @param contactAddressesDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactAddressesDTO update(ContactAddressesDTO contactAddressesDTO) {
        log.debug("Request to update ContactAddresses : {}", contactAddressesDTO);
        ContactAddresses contactAddresses = contactAddressesMapper.toEntity(contactAddressesDTO);
        contactAddresses = contactAddressesRepository.save(contactAddresses);
        return contactAddressesMapper.toDto(contactAddresses);
    }

    /**
     * Partially update a contactAddresses.
     *
     * @param contactAddressesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContactAddressesDTO> partialUpdate(ContactAddressesDTO contactAddressesDTO) {
        log.debug("Request to partially update ContactAddresses : {}", contactAddressesDTO);

        return contactAddressesRepository
            .findById(contactAddressesDTO.getId())
            .map(existingContactAddresses -> {
                contactAddressesMapper.partialUpdate(existingContactAddresses, contactAddressesDTO);

                return existingContactAddresses;
            })
            .map(contactAddressesRepository::save)
            .map(contactAddressesMapper::toDto);
    }

    /**
     * Get all the contactAddresses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContactAddressesDTO> findAll() {
        log.debug("Request to get all ContactAddresses");
        return contactAddressesRepository
            .findAll()
            .stream()
            .map(contactAddressesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one contactAddresses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactAddressesDTO> findOne(Long id) {
        log.debug("Request to get ContactAddresses : {}", id);
        return contactAddressesRepository.findById(id).map(contactAddressesMapper::toDto);
    }

    /**
     * Delete the contactAddresses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactAddresses : {}", id);
        contactAddressesRepository.deleteById(id);
    }
}
