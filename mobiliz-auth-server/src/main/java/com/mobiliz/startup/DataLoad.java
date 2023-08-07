package com.mobiliz.startup;


import com.mobiliz.request.UserRequest;
import com.mobiliz.service.AuthenticationService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class DataLoad {

    private final AuthenticationService authenticationService;

    public DataLoad(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        authenticationService.register(new UserRequest("tester", "test-user", "test-surname",
                1L, "test-company", "tester@gmail.com", "Test-password123",
                "STANDARD"));


    }
}
