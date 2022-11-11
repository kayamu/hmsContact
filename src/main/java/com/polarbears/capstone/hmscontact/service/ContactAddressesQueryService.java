package com.polarbears.capstone.hmscontact.service;

import com.polarbears.capstone.hmscontact.domain.*; // for static metamodels
import com.polarbears.capstone.hmscontact.domain.ContactAddresses;
import com.polarbears.capstone.hmscontact.repository.ContactAddressesRepository;
import com.polarbears.capstone.hmscontact.service.criteria.ContactAddressesCriteria;
import com.polarbears.capstone.hmscontact.service.dto.ContactAddressesDTO;
import com.polarbears.capstone.hmscontact.service.mapper.ContactAddressesMapper;
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
 * Service for executing complex queries for {@link ContactAddresses} entities in the database.
 * The main input is a {@link ContactAddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactAddressesDTO} or a {@link Page} of {@link ContactAddressesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactAddressesQueryService extends QueryService<ContactAddresses> {

    private final Logger log = LoggerFactory.getLogger(ContactAddressesQueryService.class);

    private final ContactAddressesRepository contactAddressesRepository;

    private final ContactAddressesMapper contactAddressesMapper;

    public ContactAddressesQueryService(
        ContactAddressesRepository contactAddressesRepository,
        ContactAddressesMapper contactAddressesMapper
    ) {
        this.contactAddressesRepository = contactAddressesRepository;
        this.contactAddressesMapper = contactAddressesMapper;
    }

    /**
     * Return a {@link List} of {@link ContactAddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactAddressesDTO> findByCriteria(ContactAddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactAddresses> specification = createSpecification(criteria);
        return contactAddressesMapper.toDto(contactAddressesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactAddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactAddressesDTO> findByCriteria(ContactAddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactAddresses> specification = createSpecification(criteria);
        return contactAddressesRepository.findAll(specification, page).map(contactAddressesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactAddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContactAddresses> specification = createSpecification(criteria);
        return contactAddressesRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactAddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContactAddresses> createSpecification(ContactAddressesCriteria criteria) {
        Specification<ContactAddresses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContactAddresses_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContactAddresses_.name));
            }
            if (criteria.getBussinessName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBussinessName(), ContactAddresses_.bussinessName));
            }
            if (criteria.getBussinessId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBussinessId(), ContactAddresses_.bussinessId));
            }
            if (criteria.getAddress1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress1(), ContactAddresses_.address1));
            }
            if (criteria.getAddress2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress2(), ContactAddresses_.address2));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), ContactAddresses_.city));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), ContactAddresses_.postalCode));
            }
            if (criteria.getProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvince(), ContactAddresses_.province));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), ContactAddresses_.detail));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ContactAddresses_.active));
            }
            if (criteria.getContractStartDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getContractStartDate(), ContactAddresses_.contractStartDate));
            }
            if (criteria.getAgrrementId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgrrementId(), ContactAddresses_.agrrementId));
            }
            if (criteria.getEmploymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmploymentType(), ContactAddresses_.employmentType));
            }
            if (criteria.getHourlyRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHourlyRate(), ContactAddresses_.hourlyRate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ContactAddresses_.createdDate));
            }
            if (criteria.getContactsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getContactsId(),
                            root -> root.join(ContactAddresses_.contacts, JoinType.LEFT).get(Contacts_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
