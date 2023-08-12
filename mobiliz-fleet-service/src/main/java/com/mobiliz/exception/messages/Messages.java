package com.mobiliz.exception.messages;

public class Messages {


    public static class CompanyFleetGroup {
        public static final String NOT_EXISTS = "CompanyFleetGroup cannot find with given id: ";
        public static final String NOT_EXISTS_BY_GIVEN_ADMIN_ID = "CompanyFleetGroup cannot find with given admin id: ";
        public static final String NAME_IN_USE = "Name already in use try another request name: ";
        public static final String COMPANY_FLEET_GROUP_ID_AND_ADMIN_ID_NOT_MATCHED = "Company fleet group id didn't matched given admin id: ";
        public static final String ADMIN_NOT_FOUND = "Admin can not found with given id: ";
        public static final String USER_HAS_NO_PERMIT = "You don't have access";
    }

}
