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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private WebClient webClient;

    //카테고리별 페이지로 이동
    @GetMapping("/category/{categoryId}")
    public String postListByCategory(@PathVariable("categoryId") Long id, Model model) {

        List<Category> categories = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Category.class)
                .toStream()
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", id);

        return "board/c_postList";
    }

    //카테고리 게시글
    @ResponseBody
    @PostMapping("/category/post/{categoryId}")
    public ResponseEntity<Map<String, Object>> categoryPost(@PathVariable("categoryId") Long id) {
        Map<String, Object> map = new HashMap<>();

        List<Post> posts = webClient.post()
                .uri("/category/post/{categoryId}", id)
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());

        map.put("posts", posts);
        return ResponseEntity.ok(map);
    }
}
