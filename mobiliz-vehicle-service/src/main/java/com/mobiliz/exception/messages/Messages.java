package com.mobiliz.exception.messages;

public class Messages {


    public static class CompanyGroup {
        public static final String NOT_EXISTS = "CompanyGroup cannot find with given id: ";
        public static final String NOT_EXISTS_BY_GIVEN_COMPANY_ID = "Company group cannot find with given company id: ";
        public static final String COMPANY_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED = "Company group id didn't matched given admin id: ";
        public static final String COMPANY_GROUP_ID_AND_DISTRICT_GROUP_ID_NOT_MATCHED = "Company group id didn't matched given company district group id: ";
    }

    public static class CompanyDistrictGroup {
        public static final String NOT_EXISTS = "CompanyDistrictGroup cannot find with given id: ";
        public static final String NOT_EXISTS_BY_GIVEN_COMPANY_ID = "CompanyDistrictGroup cannot find with given company id: ";
        public static final String COMPANY_DISTRICT_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED = "Company district group id didn't matched given admin id: ";
        public static final String COMPANY_DISTRICT_GROUP_ID_AND_FLEET_GROUP_ID_NOT_MATCHED = "Company district group id didn't matched given company fleet group id: ";
    }

    public static class Company {
        public static final String NOT_EXISTS = "Company cannot find with given id: ";
        public static final String ADMIN_IN_USE = "Admin already in company";
        public static final String ADMIN_NOT_EXIST = "Admin can not found with given id: ";
        public static final String ADMIN_NOT_MATCHED = "Company admin didn't matched given id: ";
        public static final String NAME_IN_USE = "Name already in use try another request name: ";
    }

    public static class CompanyFleetGroup {
        public static final String NOT_EXISTS = "CompanyFleetGroup cannot find with given id: ";
        public static final String NOT_EXISTS_BY_GIVEN_COMPANY_ID = "CompanyFleetGroup cannot find with given company id: ";
        public static final String ADMIN_NOT_MATCHED = "Company admin didn't matched given id:";
        public static final String NAME_IN_USE = "Name already in use try another request name: ";
        public static final String COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED = "Company fleet group id didn't matched given admin id: ";

    }

    public static class Vehicle {
        public static final String NOT_EXISTS = "Vehicle cannot find with given id: ";
        public static final String NOT_EXISTS_BY_GIVEN_COMPANY_ID = "Vehicle cannot find with given company id: ";
    }

    public static class Permission {

        public static final String NO_PERMISSION = "Only Admin can create";
    }

}
