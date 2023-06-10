package com.sunkyuj.douner.chat;

import com.sunkyuj.douner.chat.model.*;
import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.utils.ApiResult;
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
    @ResponseBody
    @GetMapping("/{chatRoomId}")
    public ApiResult<List<ChatContentResponse>> getAllChatContent(@PathVariable("chatRoomId") Long chatRoomId) throws Exception {
        Long userId = jwtService.getUserId();
        List<ChatContentResponse> chatContents = chatProvider.getAllChatContent(chatRoomId);
        return ApiUtils.success(chatContents);
    }

    // 채팅방 생성
    // 채팅방은 봉사자가 생성하게 됨 (봉사자 -> 사회적약자)
    @ResponseBody
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
    @ResponseBody
    @PostMapping("/{chatRoomId}/content")
    public ApiResult<ChatContentResponse> postChatContent(@PathVariable("chatRoomId") Long chatRoomId, @RequestBody ChatContentRequest chatContentRequest) {
        //토큰 유효기간 파악

        ChatContentResponse chatContentResponse = chatService.postChatContent(chatRoomId,chatContentRequest);
        return ApiUtils.success(chatContentResponse);

    }

}
