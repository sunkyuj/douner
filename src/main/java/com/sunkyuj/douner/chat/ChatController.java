package com.sunkyuj.douner.chat;

import com.sunkyuj.douner.chat.model.*;
import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.utils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.sunkyuj.douner.utils.ApiUtils;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatProvider chatProvider;
    private final JwtService jwtService;

//
//    @ResponseBody
//    @GetMapping("")
//    public ApiResult<List<GetChatRoom>> allChatSelect(){
//        //토큰 유효기간 파악
//
//
//    }
//
//
//    @ResponseBody
//    @GetMapping("{chatRoomId}")
//    public ApiResult<List<GetChatContent>> getAllChatContent(@PathVariable("chatRoomId") Long chatRoomId){
//
//    }

    // 한 유저의 채팅방 목록 받기
    @Operation(summary = "채팅방 목록 조회", description = "사용자의 채팅방 목록을 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @GetMapping("")
    public ApiResult<List<ChatRoomResponse>> findAllChatRoom() throws Exception {

        //토큰 유효기간 파악
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtService.getExp())){
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Long userId = jwtService.getUserId();
        List<ChatRoomResponse> chatRooms = chatProvider.findAllChatRoom(userId);
        return ApiUtils.success(chatRooms);
    }

    // 한 채팅방에서의 모든 채팅 받기
    @Operation(summary = "채팅 컨텐츠 조회", description = "채팅방의 컨텐츠를 모두 조회한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @GetMapping("/{chatRoomId}")
    public ApiResult<List<ChatContentResponse>> getAllChatContent(@PathVariable("chatRoomId") Long chatRoomId) throws Exception {
        Long userId = jwtService.getUserId();
        List<ChatContentResponse> chatContents = chatProvider.getAllChatContent(chatRoomId);
        return ApiUtils.success(chatContents);
    }

    // 채팅방 생성
    // 채팅방은 봉사자가 생성하게 됨 (봉사자 -> 사회적약자)
    @Operation(summary = "채팅방 생성", description = "봉사자가 해당 게시글에 대해 채팅을 요청하고, 채팅방을 생성한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @PostMapping("/{postId}")
    public ApiResult<ChatRoomResponse> createChatRoom(@PathVariable("postId") Long postId) throws Exception {
        //토큰 유효기간 파악
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtService.getExp())){
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Long volunteerId = jwtService.getUserId();
        ChatRoomResponse chatRoomResponse = chatService.createChatRoom(postId,volunteerId);
        return ApiUtils.success(chatRoomResponse);
    }



    // 채팅컨텐츠(메세지) 생성
    @Operation(summary = "채팅컨텐츠 생성", description = "해당 채팅방에 채팅컨텐츠(메세지)를 생성한다", tags = { "Chat" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    })
    @PostMapping("/{chatRoomId}/content")
    public ApiResult<ChatContentResponse> postChatContent(@PathVariable("chatRoomId") Long chatRoomId, @RequestBody ChatContentRequest chatContentRequest) {
        //토큰 유효기간 파악

        ChatContentResponse chatContentResponse = chatService.postChatContent(chatRoomId,chatContentRequest);
        return ApiUtils.success(chatContentResponse);

    }

}
