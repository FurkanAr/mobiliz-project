
# Mobiliz Project

It is a microservice prepared with java, spring boot where you can keep vehicle records and user vehicle authorizations.


## Microservice diagram


![mobiliz_microservice_diagram_created](https://github.com/FurkanAr/mobiliz-project/assets/63981707/4e64b8f2-3005-4347-a873-3260fbd1a8d5)

## Requirements

• Users must be able to register and login to the system.

• User authentication should be resolved at API gateway level and sent to microservices in "request header".

• Company Admin should only be able to make transactions for vehicles belonging to her/his company.

• Create,Update,Delete request can only be done by the CompanyAdmin user, while the Read request can be done by everyone.

• Vehicles should be categorized and grouped as region, fleet, group.

• Fleet admin should only view and act on their own fleet's vehicles

• All users of the company should not be authorized for all vehicles of the company.

• Users can be authorized directly to the vehicle or to the group. It can be authorized to more than one vehicle and group at the same time.

• Users should see the vehicles they are authorized in the form of trees and lists.

## System Acceptances

• Users can only be of one type: Standard, CompanyAdmin.

• For vehicles, there should be plate, chassis number, label, brand, model, model year information. Plate, brand, model and model year information are our mandatory fields. Other fields are optional fields.

• A group can contain vehicles and/or groups under it.

• If new vehicles are added to the group to which the user is authorized, the user will also be authorized to those vehicles.

## Used technologies:

• Java 11

• RESTful

• Spring Boot

• Spring Security

• Spring JPA

• Docker

• PostgreSQL

• Postman

## Authors

- [@Furkan Ar](https://www.github.com/FurkanAr)
