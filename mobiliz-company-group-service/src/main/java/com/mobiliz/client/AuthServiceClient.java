package com.mobiliz.client;

import com.mobiliz.client.request.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "Authentication", url = "http://localhost:9091/api")
public interface AuthServiceClient {

    @PostMapping(value = "/auth/token")
    public String token(@RequestBody TokenRequest tokenRequest);

}
