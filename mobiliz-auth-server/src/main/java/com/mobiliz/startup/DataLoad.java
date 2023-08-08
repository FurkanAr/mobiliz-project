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
                1L, "Mobiliz", "fundakar@gmail.com", "Test-password123",
                "STANDARD");

        UserRequest ali =  new UserRequest("ali-aktar", "Ali", "Aktar",
                1L, "Mobiliz", "aliaktar@gmail.com", "Test-password123",
                "ADMIN");
        UserRequest zeynep =  new UserRequest("zeynep-sever", "Zeynep", "Sever",
                2L, "Navigator", "zeynepsever@gmail.com", "Test-password123",
                "ADMIN");
        UserRequest can =  new UserRequest("can-tok", "Can", "Tok",
                3L, "Tracker", "cantok@gmail.com", "Test-password123",
                "ADMIN");

        UserRequest akif =  new UserRequest("akif-bıcak", "Akif", "Bıcak",
                1L, "Mobiliz", "akif-bıcak@gmail.com", "Test-password123",
                "STANDARD");

        UserRequest gizem =  new UserRequest("gizem-ak", "Gizem", "Ak",
                2L, "Navigator", "gizemak@gmail.com", "Test-password123",
                "STANDARD");

        UserRequest selim =  new UserRequest("selim-ak", "Selim", "Ak",
                3L, "Tracker", "selimak@gmail.com", "Test-password123",
                "STANDARD");

        List<UserRequest> userRequestList = List.of(funda,ali,zeynep,can,akif,gizem,selim);

        userRequestList.forEach(authenticationService::register);



    }
}
