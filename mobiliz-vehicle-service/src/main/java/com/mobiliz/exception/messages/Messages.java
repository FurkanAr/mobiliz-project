package com.mobiliz.exception.messages;

public class Messages {


    public static class CompanyGroup {
        public static final String NOT_EXISTS = "CompanyGroup cannot find with given id: ";
    }

    public static class CompanyDistrictGroup {
        public static final String NOT_EXISTS = "CompanyDistrictGroup cannot find with given id: ";
    }

    public static class Company {
        public static final String NOT_EXISTS = "Company cannot find with given id: ";
        public static final String ADMIN_IN_USE = "Admin already in company";
        public static final String ADMIN_NOT_EXIST = "Admin can not found with given id" ;
    }

    public static class CompanyFleetGroup {
        public static final String NOT_EXISTS = "CompanyFleetGroup cannot find with given id: ";
    }

    public static class Vehicle {
        public static final String NOT_EXISTS = "Vehicle cannot find with given id: ";
    }

    public static class Permission {

        public static final String NO_PERMISSION = "Only Admin can create";
    }

}
