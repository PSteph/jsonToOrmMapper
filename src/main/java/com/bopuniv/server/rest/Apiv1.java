package com.bopuniv.server.rest;

import com.bopuniv.server.dto.UserDto;
import com.bopuniv.server.entities.User;
import com.bopuniv.server.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class Apiv1 {

    @Autowired
    IUserService userService;

}
