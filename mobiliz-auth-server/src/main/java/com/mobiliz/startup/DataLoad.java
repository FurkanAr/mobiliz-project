package com.mobiliz.startup;


import com.mobiliz.request.UserRequest;
import com.mobiliz.service.AuthenticationService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DataLoad {

    private final AuthenticationService authenticationService;

    public DataLoad(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {


       UserRequest funda =  new UserRequest("funda-kar", "Funda", "Kar",
                1L, "Mobiliz", 1L, "Istanbul Filo" ,
               "fundakar@gmail.com", "Test-password123", "ADMIN");

        UserRequest ali =  new UserRequest("ali-aktar", "Ali", "Aktar",
                1L, "Mobiliz", 2L, "Ankara Filo" , "aliaktar@gmail.com", "Test-password123",
                "ADMIN");
        UserRequest zeynep =  new UserRequest("zeynep-sever", "Zeynep", "Sever",
                1L, "Mobiliz",3L, "Bursa Filo", "zeynepsever@gmail.com", "Test-password123",
                "ADMIN");
        UserRequest can =  new UserRequest("can-tok", "Can", "Tok",
                2L, "Navigator", 4L, "Istanbul Filo", "cantok@gmail.com", "Test-password123",
                "ADMIN");

        UserRequest akif =  new UserRequest("akif-bıcak", "Akif", "Bıcak",
                2L, "Navigator", 5L, "Ankara Filo", "akif-bıcak@gmail.com", "Test-password123",
                "ADMIN");

        UserRequest gizem =  new UserRequest("gizem-ak", "Gizem", "Ak",
                3L, "Tracker", 6L, "Istanbul Filo",  "gizemak@gmail.com", "Test-password123",
                "ADMIN");

        UserRequest selim =  new UserRequest("selim-ak", "Selim", "Ak",
                1L, "Mobiliz", null, null, "selimak@gmail.com", "Test-password123",
                "STANDARD");

        UserRequest seda =  new UserRequest("seda-ak", "Seda", "Ak",
                1L, "Mobiliz", null, null, "sedaak@gmail.com", "Test-password123",
                "STANDARD");

        UserRequest deniz =  new UserRequest("deniz-ak", "Deniz", "Ak",
                1L, "Mobiliz", null, null, "denizak@gmail.com", "Test-password123",
                "STANDARD");
        UserRequest ezgi =  new UserRequest("ezgi-ak", "Ezgi", "Ak",
                2L, "Navigator", null, null, "ezgiak@gmail.com", "Test-password123",
                "STANDARD");
        UserRequest cengiz =  new UserRequest("cengiz-ak", "Cengiz", "Ak",
                3L, "Tracker", null, null, "cengiz@gmail.com", "Test-password123",
                "STANDARD");

        List<UserRequest> userRequestList = List.of(funda,ali,zeynep,can,akif,gizem,selim,seda,deniz,ezgi,cengiz);

        userRequestList.forEach(authenticationService::register);



    }
}
