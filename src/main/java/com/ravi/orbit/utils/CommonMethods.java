package com.ravi.orbit.utils;

public class CommonMethods {

    public static boolean isEmpty(String userName) {
        return userName == null || userName.length() == 0;
    }

    public static boolean isEmpty(Double d) {
        boolean isEmpty = false;
        if (d == null || d <= 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static boolean isEmpty(Long l) {
        boolean isEmpty = false;
        if (l == null || l <= 0) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public static String getName(String firstName, String middleName, String lastName) {
        // Check if the input strings are null and assign them an empty string if they
        // are
        firstName = firstName != null ? firstName.trim() : "";
        middleName = middleName != null ? middleName.trim() : "";
        lastName = lastName != null ? lastName.trim() : "";

        // Use StringBuilder for efficiency in string concatenation
        StringBuilder fullName = new StringBuilder();

        // Append first name
        fullName.append(firstName);

        // Append middle name if it's not empty
        if (!middleName.isEmpty()) {
            if (fullName.length() > 0) {
                fullName.append(" "); // Add space before middle name if necessary
                fullName.append(middleName);
            }
        }

        // Append last name if it's not empty
        if (!lastName.isEmpty()) {
            if (fullName.length() > 0) {
                fullName.append(" "); // Add space before last name if necessary
                fullName.append(lastName);
            }
        }

        return fullName.toString();
    }

    /* generates address */
    public static String getAddress(String unitNo, String streetNo, String streetName, String suburbName) {
        if (!CommonMethods.isEmpty(unitNo)) {
            return unitNo + "/" + streetNo + " " + streetName + ", " + suburbName;
        } else {
            return streetNo + " " + streetName + ", " + suburbName;
        }
    }

}
