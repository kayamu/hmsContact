package com.polarbears.capstone.hmscontact.service.dto;

import com.polarbears.capstone.hmscontact.domain.enumeration.EMPLOYMENTTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmscontact.domain.ContactAddresses} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactAddressesDTO implements Serializable {

    private Long id;

    private String name;

    private String bussinessName;

    private String bussinessId;

    private String address1;

    private String address2;

    private String city;

    private String postalCode;

    private String province;

    private String detail;

    private Boolean active;

    private LocalDate contractStartDate;

    private String agrrementId;

    private EMPLOYMENTTYPES employmentType;

    private Double hourlyRate;

    private LocalDate createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getAgrrementId() {
        return agrrementId;
    }

    public void setAgrrementId(String agrrementId) {
        this.agrrementId = agrrementId;
    }

    public EMPLOYMENTTYPES getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EMPLOYMENTTYPES employmentType) {
        this.employmentType = employmentType;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactAddressesDTO)) {
            return false;
        }

        ContactAddressesDTO contactAddressesDTO = (ContactAddressesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactAddressesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactAddressesDTO{" +
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
