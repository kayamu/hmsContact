package com.polarbears.capstone.hmscontact.domain.enumeration;

/**
 * The EMPLOYMENTTYPES enumeration.
 */
public enum EMPLOYMENTTYPES {
    CONTRACTED("contracted"),
    FULLTIME("fulltime"),
    PARTTIME("parttime");

    private final String value;

    EMPLOYMENTTYPES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
