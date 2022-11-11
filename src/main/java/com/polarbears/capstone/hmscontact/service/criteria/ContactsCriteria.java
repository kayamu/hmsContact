package com.polarbears.capstone.hmscontact.service.criteria;

import com.polarbears.capstone.hmscontact.domain.enumeration.CONTACTTYPE;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmscontact.domain.Contacts} entity. This class is used
 * in {@link com.polarbears.capstone.hmscontact.web.rest.ContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CONTACTTYPE
     */
    public static class CONTACTTYPEFilter extends Filter<CONTACTTYPE> {

        public CONTACTTYPEFilter() {}

        public CONTACTTYPEFilter(CONTACTTYPEFilter filter) {
            super(filter);
        }

        @Override
        public CONTACTTYPEFilter copy() {
            return new CONTACTTYPEFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userID;

    private CONTACTTYPEFilter type;

    private StringFilter name;

    private StringFilter hstNumber;

    private StringFilter detail;

    private StringFilter email;

    private StringFilter phone;

    private BooleanFilter gender;

    private LocalDateFilter birthdate;

    private LocalDateFilter createdDate;

    private LongFilter contactAddressesId;

    private Boolean distinct;

    public ContactsCriteria() {}

    public ContactsCriteria(ContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userID = other.userID == null ? null : other.userID.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.hstNumber = other.hstNumber == null ? null : other.hstNumber.copy();
        this.detail = other.detail == null ? null : other.detail.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.birthdate = other.birthdate == null ? null : other.birthdate.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.contactAddressesId = other.contactAddressesId == null ? null : other.contactAddressesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContactsCriteria copy() {
        return new ContactsCriteria(this);
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

    public StringFilter getUserID() {
        return userID;
    }

    public StringFilter userID() {
        if (userID == null) {
            userID = new StringFilter();
        }
        return userID;
    }

    public void setUserID(StringFilter userID) {
        this.userID = userID;
    }

    public CONTACTTYPEFilter getType() {
        return type;
    }

    public CONTACTTYPEFilter type() {
        if (type == null) {
            type = new CONTACTTYPEFilter();
        }
        return type;
    }

    public void setType(CONTACTTYPEFilter type) {
        this.type = type;
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

    public StringFilter getHstNumber() {
        return hstNumber;
    }

    public StringFilter hstNumber() {
        if (hstNumber == null) {
            hstNumber = new StringFilter();
        }
        return hstNumber;
    }

    public void setHstNumber(StringFilter hstNumber) {
        this.hstNumber = hstNumber;
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

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public BooleanFilter getGender() {
        return gender;
    }

    public BooleanFilter gender() {
        if (gender == null) {
            gender = new BooleanFilter();
        }
        return gender;
    }

    public void setGender(BooleanFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getBirthdate() {
        return birthdate;
    }

    public LocalDateFilter birthdate() {
        if (birthdate == null) {
            birthdate = new LocalDateFilter();
        }
        return birthdate;
    }

    public void setBirthdate(LocalDateFilter birthdate) {
        this.birthdate = birthdate;
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

    public LongFilter getContactAddressesId() {
        return contactAddressesId;
    }

    public LongFilter contactAddressesId() {
        if (contactAddressesId == null) {
            contactAddressesId = new LongFilter();
        }
        return contactAddressesId;
    }

    public void setContactAddressesId(LongFilter contactAddressesId) {
        this.contactAddressesId = contactAddressesId;
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
        final ContactsCriteria that = (ContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userID, that.userID) &&
            Objects.equals(type, that.type) &&
            Objects.equals(name, that.name) &&
            Objects.equals(hstNumber, that.hstNumber) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(contactAddressesId, that.contactAddressesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userID,
            type,
            name,
            hstNumber,
            detail,
            email,
            phone,
            gender,
            birthdate,
            createdDate,
            contactAddressesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userID != null ? "userID=" + userID + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (hstNumber != null ? "hstNumber=" + hstNumber + ", " : "") +
            (detail != null ? "detail=" + detail + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (contactAddressesId != null ? "contactAddressesId=" + contactAddressesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
