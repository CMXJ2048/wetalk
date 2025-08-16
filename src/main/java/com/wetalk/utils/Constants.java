package com.wetalk.utils;

/**
 * Application-wide constants used across controllers/services for messages and keys.
 * Centralizing strings avoids duplication and eases future i18n or changes.
 */
public class Constants {
    /** Generic not found message for user-related lookups. */
    public static final String USER_NOT_FOUND = "User not found";
    /** Message for invalid login credentials. */
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    /** Message shown when registration succeeds. */
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    /** Message shown when profile update succeeds. */
    public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully";
    /** Message shown when deletion succeeds. */
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    /** Fallback message for unexpected server-side errors. */
    public static final String SERVER_ERROR = "An unexpected error occurred. Please try again later.";
}