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

    @Operation(summary = "전체 사용자 조회", description = "모든 사용자를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    })
    @GetMapping("")
    public ApiResult<List<UserResponse>> users() {
        List<UserResponse> users = userService.findUsers();
        return ApiUtils.success(users);
    }

    @Operation(summary = "요청자 타입의 사용자 조회", description = "모든 요청자를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    })
    @GetMapping("/requester")
    public ApiResult<List<UserResponse>> requester() {
        List<UserResponse> users = userService.findRequesters();
        return ApiUtils.success(users);
    }

    @Operation(summary = "봉사자 타입의 사용자 조회", description = "모든 봉사자를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    })
    @GetMapping("/volunteer")
    public ApiResult<List<UserResponse>> volunteer() {
        List<UserResponse> users = userService.findVolunteers();
        return  ApiUtils.success(users);
    }

    @Operation(summary = "단일 사용자 조회", description = "한 사용자의 정보를 조회한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping("/{userId}")
    public ApiResult<UserResponse> user(@PathVariable("userId") Long userId) {
        UserResponse user = userService.findOne(userId);
        return ApiUtils.success(user);
    }

    @Operation(summary = "회원가입", description = "회원을 등록한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserRegisterResponse.class)))
    })
    @PostMapping("/register")
    public ApiResult<UserRegisterResponse> joinUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse result = userService.register(userRegisterRequest);
        return ApiUtils.success(result);
    }

    @Operation(summary = "로그인", description = "로그인을 하여 회원 인증을 한다", tags = { "User" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserLoginResultToken.class)))
    })
    @PostMapping("/login")
    public ApiResult<UserLoginResultToken> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResultToken result = userService.login(userLoginRequest);
        return ApiUtils.success(result);
    }
}
