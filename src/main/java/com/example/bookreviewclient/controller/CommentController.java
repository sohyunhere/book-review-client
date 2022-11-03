package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.dto.CommentsDto;
import com.example.bookreviewclient.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private WebClient webClient;

    //댓글 작성
    @PostMapping("/comment/write")
    public int write(@RequestBody CommentsDto dto, Authentication auth) {
        Member member = (Member) auth.getPrincipal();
        dto.setMember(member);

        int commentId = webClient.post()
                .uri("/comment/write")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return commentId;
    }

    //댓글 삭제
    @GetMapping("/board/delete/comment/{commentId}")
    public int deleteComment(@PathVariable("commentId") Long id) {
        int commentId = webClient.get()
                .uri("/board/delete/comment/{commentId}", id)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);
        return commentId;
    }

    //댓글 수정
    @PostMapping("/board/update/comment/{commentId}")
    public int updateComment(@PathVariable("commentId") Long id,
                             @RequestParam(value = "content") String content) {
        int commentId = webClient.post()
                .uri("/board/update/comment/{commentId}", id)
                .bodyValue(content)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return Math.toIntExact(commentId);
    }
}
