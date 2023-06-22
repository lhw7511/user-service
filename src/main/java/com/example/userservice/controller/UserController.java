package com.example.userservice.controller;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;

    private final UserService userService;
    private final Greeting greeting;


    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in User Service on PORT %s",env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user,UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


    @GetMapping("users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userByAll = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userByAll.forEach(u-> {
            result.add(new ModelMapper().map(u,ResponseUser.class));
        });
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String userId){
        ModelMapper mapper = new ModelMapper();
        UserDto user = userService.getUserByUserId(userId);
        ResponseUser responseUser = mapper.map(user, ResponseUser.class);


        return ResponseEntity.ok(responseUser);
    }



}
