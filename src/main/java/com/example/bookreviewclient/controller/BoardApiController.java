package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.dto.PostDto;
import com.example.bookreviewclient.model.Member;
import com.example.bookreviewclient.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardApiController {
    @Autowired
    private WebClient webClient;

    //글 작성
    @PostMapping("/board/write")
    public int write(@RequestBody PostDto dto, Authentication auth) {
        Member member = (Member) auth.getPrincipal();
        System.out.println(member.getMemberEmail());

        dto.setMember(member);

        int postId = webClient.post()
                .uri("/board/write")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return postId;
    }

    //글 수정
    @PostMapping("/board/update/{postId}")
    public int updatePost(@PathVariable("postId") Long id, @RequestBody PostDto dto){

        int postId = webClient.post()
                .uri("/board/update/{postId}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return Math.toIntExact(postId);
    }
    //파일 삭제
    @GetMapping("/delete/{fileId}")
    public int deleteFile(@PathVariable("fileId") Long id){

        int fileId = webClient.get()
                .uri("/delete/{fileId}", id)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return fileId;
    }

    //글 삭제
    @PostMapping("/board/delete/post/{postId}")
    public int deletePost(@PathVariable("postId") Long id){

        int postId = webClient.post()
                .uri("/board/delete/post/{postId}", id)
                .retrieve()
                .bodyToMono(Integer.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return postId;
    }

    //내가 작성한 게시글 가져오기
    @GetMapping ("/board/mypost/list")
    public ResponseEntity<Map<String, Object>> getMyPost(Authentication auth) {

        Member member = (Member)auth.getPrincipal();

        Map<String, Object> map = new HashMap<>();

        List<Post> posts = webClient.get()
                .uri("/board/mypost/list/{id}", member.getMemberId())
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());

        map.put("posts", posts);
        return ResponseEntity.ok(map);
    }

    //글 검색
    @GetMapping("/board/search")
    public  ResponseEntity<Map<String, Object>> search(
            @RequestParam(value="searchType") String type,@RequestParam(value="search") String word){

        Map<String, Object> map = new HashMap<>();

        List<Post> posts = webClient.get()
                .uri("/board/search/{type}/{word}", type, word)
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());

        map.put("posts", posts);
        return ResponseEntity.ok(map);
    }

    //차트데이터 보내기
    @GetMapping("/chart/data")
    public  ResponseEntity<Map<String, Object>> chart(){
        Map<String, Object> map = webClient.get()
                .uri("/chart/data")
                .retrieve()
                .bodyToMono(Map.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return ResponseEntity.ok(map);
    }

}
