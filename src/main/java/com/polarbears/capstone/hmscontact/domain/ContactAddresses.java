package com.polarbears.capstone.hmscontact.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmscontact.domain.enumeration.EMPLOYMENTTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContactAddresses.
 */
@Entity
@Table(name = "contact_addresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bussiness_name")
    private String bussinessName;

    @Column(name = "bussiness_id")
    private String bussinessId;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "province")
    private String province;

    @Column(name = "detail")
    private String detail;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @Column(name = "agrrement_id")
    private String agrrementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EMPLOYMENTTYPES employmentType;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany(mappedBy = "contactAddresses")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contactAddresses" }, allowSetters = true)
    private Set<Contacts> contacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactAddresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ContactAddresses name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBussinessName() {
        return this.bussinessName;
    }

    public ContactAddresses bussinessName(String bussinessName) {
        this.setBussinessName(bussinessName);
        return this;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getBussinessId() {
        return this.bussinessId;
    }

    public ContactAddresses bussinessId(String bussinessId) {
        this.setBussinessId(bussinessId);
        return this;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getAddress1() {
        return this.address1;
    }

    public ContactAddresses address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public ContactAddresses address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public ContactAddresses city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public ContactAddresses postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return this.province;
    }

    public ContactAddresses province(String province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDetail() {
        return this.detail;
    }

    public ContactAddresses detail(String detail) {
        this.setDetail(detail);
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getActive() {
        return this.active;
    }

    public ContactAddresses active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getContractStartDate() {
        return this.contractStartDate;
    }

    public ContactAddresses contractStartDate(LocalDate contractStartDate) {
        this.setContractStartDate(contractStartDate);
        return this;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getAgrrementId() {
        return this.agrrementId;
    }

    public ContactAddresses agrrementId(String agrrementId) {
        this.setAgrrementId(agrrementId);
        return this;
    }

    public void setAgrrementId(String agrrementId) {
        this.agrrementId = agrrementId;
    }

    public EMPLOYMENTTYPES getEmploymentType() {
        return this.employmentType;
    }

    public ContactAddresses employmentType(EMPLOYMENTTYPES employmentType) {
        this.setEmploymentType(employmentType);
        return this;
    }

    public void setEmploymentType(EMPLOYMENTTYPES employmentType) {
        this.employmentType = employmentType;
    }

    public Double getHourlyRate() {
        return this.hourlyRate;
    }

    public ContactAddresses hourlyRate(Double hourlyRate) {
        this.setHourlyRate(hourlyRate);
        return this;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public ContactAddresses createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Contacts> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contacts> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.removeContactAddresses(this));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.addContactAddresses(this));
        }
        this.contacts = contacts;
    }

    public ContactAddresses contacts(Set<Contacts> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public ContactAddresses addContacts(Contacts contacts) {
        this.contacts.add(contacts);
        contacts.getContactAddresses().add(this);
        return this;
    }

    public ContactAddresses removeContacts(Contacts contacts) {
        this.contacts.remove(contacts);
        contacts.getContactAddresses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactAddresses)) {
            return false;
        }
        return id != null && id.equals(((ContactAddresses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactAddresses{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bussinessName='" + getBussinessName() + "'" +
            ", bussinessId='" + getBussinessId() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", province='" + getProvince() + "'" +
            ", detail='" + getDetail() + "'" +
            ", active='" + getActive() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", agrrementId='" + getAgrrementId() + "'" +
            ", employmentType='" + getEmploymentType() + "'" +
            ", hourlyRate=" + getHourlyRate() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
