package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.*;
import com.sunkyuj.douner.utils.ApiResult;
import com.sunkyuj.douner.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "전체 사용자 조회", description = "모든 사용자를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @GetMapping("")
    public ApiResult<List<User>> users() {
        List<User> users = userService.findUsers();
        return ApiUtils.success(users);
    }

    @Operation(summary = "요청자 타입의 사용자 조회", description = "모든 요청자를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @GetMapping("/requester")
    public ApiResult<List<User>> requester() {
        List<User> users = userService.findRequesters();
        return ApiUtils.success(users);
    }

    @Operation(summary = "봉사자 타입의 사용자 조회", description = "모든 봉사자를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @GetMapping("/volunteer")
    public ApiResult<List<User>> volunteer() {
        List<User> users = userService.findVolunteers();
        return  ApiUtils.success(users);
    }

    @Operation(summary = "단일 사용자 조회", description = "한 사용자의 정보를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @GetMapping("/{userId}")
    public ApiResult<User> user(@PathVariable("userId") Long userId) {
        User user = userService.findOne(userId);
        return ApiUtils.success(user);
    }

    @Operation(summary = "회원가입", description = "회원을 등록한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @PostMapping("/register")
    public ApiResult<UserRegisterResponse> joinUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse result = userService.register(userRegisterRequest);
        return ApiUtils.success(result);
    }

    @Operation(summary = "로그인", description = "로그인을 하여 회원 인증을 한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @PostMapping("/login")
    public ApiResult<UserLoginResultToken> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResultToken result = userService.login(userLoginRequest);
        return ApiUtils.success(result);
    }
}
