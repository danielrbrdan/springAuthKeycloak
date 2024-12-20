package com.project.base.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.base.auth.dto.UserDTO;

@RequestMapping("/token")
@RestController
public class TokenController {
    private static final String REALM = "main";
    private static final String KEYCLOACK_URL_TOKEN = "http://localhost:8080/realms/"+REALM+"/protocol/openid-connect/token";

    @PostMapping()
    public ResponseEntity<String> token(@RequestBody UserDTO user) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.clientId);
        formData.add("username", user.username);
        formData.add("password", user.password);
        formData.add("grant_type", user.grantType);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(formData,
                headers);

        var result = rt.postForEntity(KEYCLOACK_URL_TOKEN, entity,
                String.class);

        return result;
    }

}