package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.*;
import com.sunkyuj.douner.utils.ApiResult;
import com.sunkyuj.douner.utils.ApiUtils;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 나중에 register로 대체
//    @PostMapping()
//    public Long addUser(@RequestBody UserDto userDto){
//        return userService.join(userDto);
//    }


    @GetMapping("")
    public ApiResult<List<User>> users() {
        List<User> users = userService.findUsers();
        return ApiUtils.success(users);
    }

    @GetMapping("/requester")
    public ApiResult<List<User>> requester() {
        List<User> users = userService.findRequesters();
        return ApiUtils.success(users);
    }

    @GetMapping("/volunteer")
    public ApiResult<List<User>> volunteer() {
        List<User> users = userService.findVolunteers();
        return  ApiUtils.success(users);
    }

    @GetMapping("/{userId}")
    public ApiResult<User> user(@PathVariable("userId") Long userId) {
        User user = userService.findOne(userId);
        return ApiUtils.success(user);
    }

    @PostMapping("/register")
    public ApiResult<UserRegisterResponse> joinUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse result = userService.register(userRegisterRequest);
        return ApiUtils.success(result);
    }

    @PostMapping("/login")
    public ApiResult<UserLoginResultToken> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResultToken result = userService.login(userLoginRequest);
        return ApiUtils.success(result);
    }
}
