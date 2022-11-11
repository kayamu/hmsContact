package com.polarbears.capstone.hmscontact.service;

import com.polarbears.capstone.hmscontact.domain.*; // for static metamodels
import com.polarbears.capstone.hmscontact.domain.Contacts;
import com.polarbears.capstone.hmscontact.repository.ContactsRepository;
import com.polarbears.capstone.hmscontact.service.criteria.ContactsCriteria;
import com.polarbears.capstone.hmscontact.service.dto.ContactsDTO;
import com.polarbears.capstone.hmscontact.service.mapper.ContactsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Contacts} entities in the database.
 * The main input is a {@link ContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactsDTO} or a {@link Page} of {@link ContactsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactsQueryService extends QueryService<Contacts> {

    private final Logger log = LoggerFactory.getLogger(ContactsQueryService.class);

    private final ContactsRepository contactsRepository;

    private final ContactsMapper contactsMapper;

    public ContactsQueryService(ContactsRepository contactsRepository, ContactsMapper contactsMapper) {
        this.contactsRepository = contactsRepository;
        this.contactsMapper = contactsMapper;
    }

    /**
     * Return a {@link List} of {@link ContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactsDTO> findByCriteria(ContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsMapper.toDto(contactsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactsDTO> findByCriteria(ContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.findAll(specification, page).map(contactsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contacts> createSpecification(ContactsCriteria criteria) {
        Specification<Contacts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contacts_.id));
            }
            if (criteria.getUserID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserID(), Contacts_.userID));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Contacts_.type));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Contacts_.name));
            }
            if (criteria.getHstNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHstNumber(), Contacts_.hstNumber));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), Contacts_.detail));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contacts_.email));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Contacts_.phone));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Contacts_.gender));
            }
            if (criteria.getBirthdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthdate(), Contacts_.birthdate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Contacts_.createdDate));
            }
            if (criteria.getContactAddressesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getContactAddressesId(),
                            root -> root.join(Contacts_.contactAddresses, JoinType.LEFT).get(ContactAddresses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
