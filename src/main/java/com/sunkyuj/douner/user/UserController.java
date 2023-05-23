package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 나중에 register로 대체
    @PostMapping()
    public Long addUser(User user){
        return userService.join(user);
    }

    @Operation(summary = "전체 회원 조회 요청", description = "전체 회원 정보가 반환됩니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("")
    public List<User> users() {
        return userService.findUsers();
    }

    @GetMapping("/requester")
    public List<User> requester() {
        return userService.findRequesters();
    }

    @GetMapping("/volunteer")
    public List<User> volunteer() {
        return userService.findVolunteers();
    }

    @GetMapping("/{userId}")
    public User user(@PathVariable Long userId) {
        return userService.findOne(userId);
    }
//
//    @PostMapping("/register")
//    public ResponseEntity<UserRegisterResponse> joinUser(@Valid @RequestBody UserRegisterParam userRegisterParam) {
//        User res = userService.register(userRegisterParam);
//        UserRegisterResponse result = new UserRegisterResponse(res.getUserId(), "success");
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<UserLoginResultToken> loginUser(@Valid @RequestBody UserLoginReqParam userLoginReqParam) {
//        UserLoginResultToken result = userService.login(userLoginReqParam);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
