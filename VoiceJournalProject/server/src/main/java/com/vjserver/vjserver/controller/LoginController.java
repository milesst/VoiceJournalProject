package com.vjserver.vjserver.controller;
import com.vjserver.vjserver.login.LoginRequest;
import com.vjserver.vjserver.login.LoginResponse;
import com.vjserver.vjserver.repository.UserRepository;
import com.vjserver.vjserver.resource.UserResource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserRepository userRepository;

    LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody LoginRequest req) {
		
        UserResource user = new UserResource(userRepository.selectByUsername(req.getUsername()));
        System.out.println(user.getUserPassword());
        System.out.println(req.getPassword());
        String token = "";
        if (user.getUserPassword().equals(req.getPassword())) {
		    token = getJWTToken(req.getUsername());
            LoginResponse userresp = new LoginResponse();
            userresp.setId(user.getId());
            userresp.setFirstName(user.getFirstName());
            userresp.setLastName(user.getLastName());
            userresp.setPatronymic(user.getPatronymic());
            userresp.setToken(token);		
            return userresp;
        }
        else return new LoginResponse();
	}

	private String getJWTToken(String username) {
		String token = Math.round((Math.random()*10000)) + username;

		return token;
	}
}
