package com.polarbears.capstone.hmscontact.repository;

import com.polarbears.capstone.hmscontact.domain.Contacts;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ContactsRepositoryWithBagRelationshipsImpl implements ContactsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Contacts> fetchBagRelationships(Optional<Contacts> contacts) {
        return contacts.map(this::fetchContactAddresses);
    }

    @Override
    public Page<Contacts> fetchBagRelationships(Page<Contacts> contacts) {
        return new PageImpl<>(fetchBagRelationships(contacts.getContent()), contacts.getPageable(), contacts.getTotalElements());
    }

    @Override
    public List<Contacts> fetchBagRelationships(List<Contacts> contacts) {
        return Optional.of(contacts).map(this::fetchContactAddresses).orElse(Collections.emptyList());
    }

    Contacts fetchContactAddresses(Contacts result) {
        return entityManager
            .createQuery(
                "select contacts from Contacts contacts left join fetch contacts.contactAddresses where contacts is :contacts",
                Contacts.class
            )
            .setParameter("contacts", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Contacts> fetchContactAddresses(List<Contacts> contacts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, contacts.size()).forEach(index -> order.put(contacts.get(index).getId(), index));
        List<Contacts> result = entityManager
            .createQuery(
                "select distinct contacts from Contacts contacts left join fetch contacts.contactAddresses where contacts in :contacts",
                Contacts.class
            )
            .setParameter("contacts", contacts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
