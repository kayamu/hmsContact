package com.polarbears.capstone.hmscontact.service.dto;

import com.polarbears.capstone.hmscontact.domain.enumeration.CONTACTTYPE;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.polarbears.capstone.hmscontact.domain.Contacts} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactsDTO implements Serializable {

    private Long id;

    private String userID;

    private CONTACTTYPE type;

    private String name;

    private String hstNumber;

    private String detail;

    private String email;

    private String phone;

    private Boolean gender;

    private LocalDate birthdate;

    private LocalDate createdDate;

    private Set<ContactAddressesDTO> contactAddresses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public CONTACTTYPE getType() {
        return type;
    }

    public void setType(CONTACTTYPE type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHstNumber() {
        return hstNumber;
    }

    public void setHstNumber(String hstNumber) {
        this.hstNumber = hstNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<ContactAddressesDTO> getContactAddresses() {
        return contactAddresses;
    }

    public void setContactAddresses(Set<ContactAddressesDTO> contactAddresses) {
        this.contactAddresses = contactAddresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactsDTO)) {
            return false;
        }

        ContactsDTO contactsDTO = (ContactsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactsDTO{" +
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
            ", contactAddresses=" + getContactAddresses() +
            "}";
    }
}
