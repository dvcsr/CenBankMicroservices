package com.cenbank.compliance.constant;

import java.time.Duration;

public class ComplianceConstants {

    private ComplianceConstants() {
    }

    //response dto constant
    public static final String  STATUS_201 = "201";
    public static final String  MESSAGE_201 = "Report created successfully";
    public static final String  STATUS_200 = "200";
    public static final String  MESSAGE_200 = "Request processed successfully";
    public static final String  STATUS_417 = "417";
    public static final String  MESSAGE_417_UPDATE= "Update failed. Please try again or contact Dev team";
    public static final String  MESSAGE_417_DELETE= "Delete failed. Please try again or contact Dev team";
    public static final String  STATUS_500 = "500";
    public static final String  MESSAGE_500 = "An error occurred. Please try again or contact Dev team";

    //kyc summary related constant
    public static final String KYC_STATUS_CLEAN = "CLEAN";
    public static final String KYC_STATUS_AT_RISK = "AT_RISK";
    public static final String KYC_STATUS_EXPIRED = "EXPIRED";

    //due diligence related constant
    public static final String DD_PROGRESS_SUSPICIOUS = "SUSPICIOUS";
    public static final String DD_PROGRESS_NO_ISSUE = "NO ISSUE";
    public static final String DD_PROGRESS_INVESTIGATING = "INVESTIGATING";
    public static final String DD_PROGRESS_NOT_STARTED = "NOT STARTED";


    public static final Duration KYC_VALIDITY_DURATION = Duration.ofSeconds(20);
}