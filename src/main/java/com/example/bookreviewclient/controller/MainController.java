package com.example.bookreviewclient.controller;


import com.example.bookreviewclient.model.Category;
import com.example.bookreviewclient.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/")
    public String main(Model model) throws IOException {
        List<Category> categories = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Category.class)
                .toStream()
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);

        return "main";
    }

    //메인 게시글
    @ResponseBody
    @GetMapping("/main/posts")
    public ResponseEntity<Map<String, Object>> posts() {
        Map<String, Object> map = new HashMap<>();
        List<Post> posts = webClient.get()
                .uri("/main/posts")
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());
        map.put("posts", posts);

        return ResponseEntity.ok(map);
    }
    //최신순
    @ResponseBody
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> latest() {
        Map<String, Object> map = new HashMap<>();

        List<Post> posts = webClient.get()
                .uri("/latest")
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());
        map.put("posts", posts);

        return ResponseEntity.ok(map);
    }
    //조회순
    @ResponseBody
    @GetMapping("/popular")
    public ResponseEntity<Map<String, Object>> popular() {
        Map<String, Object> map = new HashMap<>();

        List<Post> posts = webClient.get()
                .uri("/popular")
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());
        map.put("posts", posts);

        return ResponseEntity.ok(map);
    }
}
