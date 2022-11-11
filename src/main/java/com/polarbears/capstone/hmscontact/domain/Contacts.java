package com.polarbears.capstone.hmscontact.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmscontact.domain.enumeration.CONTACTTYPE;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contacts.
 */
@Entity
@Table(name = "contacts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userID;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CONTACTTYPE type;

    @Column(name = "name")
    private String name;

    @Column(name = "hst_number")
    private String hstNumber;

    @Column(name = "detail")
    private String detail;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany
    @JoinTable(
        name = "rel_contacts__contact_addresses",
        joinColumns = @JoinColumn(name = "contacts_id"),
        inverseJoinColumns = @JoinColumn(name = "contact_addresses_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contacts" }, allowSetters = true)
    private Set<ContactAddresses> contactAddresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contacts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return this.userID;
    }

    public Contacts userID(String userID) {
        this.setUserID(userID);
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public CONTACTTYPE getType() {
        return this.type;
    }

    public Contacts type(CONTACTTYPE type) {
        this.setType(type);
        return this;
    }

    public void setType(CONTACTTYPE type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Contacts name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHstNumber() {
        return this.hstNumber;
    }

    public Contacts hstNumber(String hstNumber) {
        this.setHstNumber(hstNumber);
        return this;
    }

    public void setHstNumber(String hstNumber) {
        this.hstNumber = hstNumber;
    }

    public String getDetail() {
        return this.detail;
    }

    public Contacts detail(String detail) {
        this.setDetail(detail);
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return this.email;
    }

    public Contacts email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Contacts phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getGender() {
        return this.gender;
    }

    public Contacts gender(Boolean gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public Contacts birthdate(LocalDate birthdate) {
        this.setBirthdate(birthdate);
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Contacts createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<ContactAddresses> getContactAddresses() {
        return this.contactAddresses;
    }

    public void setContactAddresses(Set<ContactAddresses> contactAddresses) {
        this.contactAddresses = contactAddresses;
    }

    public Contacts contactAddresses(Set<ContactAddresses> contactAddresses) {
        this.setContactAddresses(contactAddresses);
        return this;
    }

    public Contacts addContactAddresses(ContactAddresses contactAddresses) {
        this.contactAddresses.add(contactAddresses);
        contactAddresses.getContacts().add(this);
        return this;
    }

    public Contacts removeContactAddresses(ContactAddresses contactAddresses) {
        this.contactAddresses.remove(contactAddresses);
        contactAddresses.getContacts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contacts)) {
            return false;
        }
        return id != null && id.equals(((Contacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contacts{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", hstNumber='" + getHstNumber() + "'" +
            ", detail='" + getDetail() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
