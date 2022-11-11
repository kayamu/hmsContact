package com.polarbears.capstone.hmscontact.service.criteria;

import com.polarbears.capstone.hmscontact.domain.enumeration.EMPLOYMENTTYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmscontact.domain.ContactAddresses} entity. This class is used
 * in {@link com.polarbears.capstone.hmscontact.web.rest.ContactAddressesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contact-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactAddressesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EMPLOYMENTTYPES
     */
    public static class EMPLOYMENTTYPESFilter extends Filter<EMPLOYMENTTYPES> {

        public EMPLOYMENTTYPESFilter() {}

        public EMPLOYMENTTYPESFilter(EMPLOYMENTTYPESFilter filter) {
            super(filter);
        }

        @Override
        public EMPLOYMENTTYPESFilter copy() {
            return new EMPLOYMENTTYPESFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter bussinessName;

    private StringFilter bussinessId;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter city;

    private StringFilter postalCode;

    private StringFilter province;

    private StringFilter detail;

    private BooleanFilter active;

    private LocalDateFilter contractStartDate;

    private StringFilter agrrementId;

    private EMPLOYMENTTYPESFilter employmentType;

    private DoubleFilter hourlyRate;

    private LocalDateFilter createdDate;

    private LongFilter contactsId;

    private Boolean distinct;

    public ContactAddressesCriteria() {}

    public ContactAddressesCriteria(ContactAddressesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.bussinessName = other.bussinessName == null ? null : other.bussinessName.copy();
        this.bussinessId = other.bussinessId == null ? null : other.bussinessId.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.province = other.province == null ? null : other.province.copy();
        this.detail = other.detail == null ? null : other.detail.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.contractStartDate = other.contractStartDate == null ? null : other.contractStartDate.copy();
        this.agrrementId = other.agrrementId == null ? null : other.agrrementId.copy();
        this.employmentType = other.employmentType == null ? null : other.employmentType.copy();
        this.hourlyRate = other.hourlyRate == null ? null : other.hourlyRate.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.contactsId = other.contactsId == null ? null : other.contactsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContactAddressesCriteria copy() {
        return new ContactAddressesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getBussinessName() {
        return bussinessName;
    }

    public StringFilter bussinessName() {
        if (bussinessName == null) {
            bussinessName = new StringFilter();
        }
        return bussinessName;
    }

    public void setBussinessName(StringFilter bussinessName) {
        this.bussinessName = bussinessName;
    }

    public StringFilter getBussinessId() {
        return bussinessId;
    }

    public StringFilter bussinessId() {
        if (bussinessId == null) {
            bussinessId = new StringFilter();
        }
        return bussinessId;
    }

    public void setBussinessId(StringFilter bussinessId) {
        this.bussinessId = bussinessId;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public StringFilter address1() {
        if (address1 == null) {
            address1 = new StringFilter();
        }
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public StringFilter address2() {
        if (address2 == null) {
            address2 = new StringFilter();
        }
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public StringFilter postalCode() {
        if (postalCode == null) {
            postalCode = new StringFilter();
        }
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getProvince() {
        return province;
    }

    public StringFilter province() {
        if (province == null) {
            province = new StringFilter();
        }
        return province;
    }

    public void setProvince(StringFilter province) {
        this.province = province;
    }

    public StringFilter getDetail() {
        return detail;
    }

    public StringFilter detail() {
        if (detail == null) {
            detail = new StringFilter();
        }
        return detail;
    }

    public void setDetail(StringFilter detail) {
        this.detail = detail;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LocalDateFilter getContractStartDate() {
        return contractStartDate;
    }

    public LocalDateFilter contractStartDate() {
        if (contractStartDate == null) {
            contractStartDate = new LocalDateFilter();
        }
        return contractStartDate;
    }

    public void setContractStartDate(LocalDateFilter contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public StringFilter getAgrrementId() {
        return agrrementId;
    }

    public StringFilter agrrementId() {
        if (agrrementId == null) {
            agrrementId = new StringFilter();
        }
        return agrrementId;
    }

    public void setAgrrementId(StringFilter agrrementId) {
        this.agrrementId = agrrementId;
    }

    public EMPLOYMENTTYPESFilter getEmploymentType() {
        return employmentType;
    }

    public EMPLOYMENTTYPESFilter employmentType() {
        if (employmentType == null) {
            employmentType = new EMPLOYMENTTYPESFilter();
        }
        return employmentType;
    }

    public void setEmploymentType(EMPLOYMENTTYPESFilter employmentType) {
        this.employmentType = employmentType;
    }

    public DoubleFilter getHourlyRate() {
        return hourlyRate;
    }

    public DoubleFilter hourlyRate() {
        if (hourlyRate == null) {
            hourlyRate = new DoubleFilter();
        }
        return hourlyRate;
    }

    public void setHourlyRate(DoubleFilter hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getContactsId() {
        return contactsId;
    }

    public LongFilter contactsId() {
        if (contactsId == null) {
            contactsId = new LongFilter();
        }
        return contactsId;
    }

    public void setContactsId(LongFilter contactsId) {
        this.contactsId = contactsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContactAddressesCriteria that = (ContactAddressesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(bussinessName, that.bussinessName) &&
            Objects.equals(bussinessId, that.bussinessId) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(city, that.city) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(province, that.province) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(active, that.active) &&
            Objects.equals(contractStartDate, that.contractStartDate) &&
            Objects.equals(agrrementId, that.agrrementId) &&
            Objects.equals(employmentType, that.employmentType) &&
            Objects.equals(hourlyRate, that.hourlyRate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(contactsId, that.contactsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            bussinessName,
            bussinessId,
            address1,
            address2,
            city,
            postalCode,
            province,
            detail,
            active,
            contractStartDate,
            agrrementId,
            employmentType,
            hourlyRate,
            createdDate,
            contactsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactAddressesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (bussinessName != null ? "bussinessName=" + bussinessName + ", " : "") +
            (bussinessId != null ? "bussinessId=" + bussinessId + ", " : "") +
            (address1 != null ? "address1=" + address1 + ", " : "") +
            (address2 != null ? "address2=" + address2 + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
            (province != null ? "province=" + province + ", " : "") +
            (detail != null ? "detail=" + detail + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (contractStartDate != null ? "contractStartDate=" + contractStartDate + ", " : "") +
            (agrrementId != null ? "agrrementId=" + agrrementId + ", " : "") +
            (employmentType != null ? "employmentType=" + employmentType + ", " : "") +
            (hourlyRate != null ? "hourlyRate=" + hourlyRate + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (contactsId != null ? "contactsId=" + contactsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
