package com.example.bookreviewclient;


import com.example.bookreviewclient.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

//    private final CategoryService categoryService;
//    private final BoardService boardService;

    @Autowired
    private WebClient webClient;

    @GetMapping("/")
    public String main(Model model) throws IOException {
//        List<Category> categories = (List<Category>) webClient.get()
//                .uri("/")
//                .retrieve()
//                .bodyToFlux(Category.class);
//
//        Flux.fromIterable(webClient.get()
//                .uri("/")
//                .retrieve()
//                .bodyToFlux(Category.class)


//        List<Category> categories = categoryService.findAll();

//        Mono<String> m = webClient.get()
//                .uri("http://localhost:5011/webclient/test-create")
//                .retrieve()
//                .bodyToMono(String.class);
//        m.map( arg-> arg.length()).subscribe( str->{  //map을 통해서 string값을 길이로 바꾸었다.
//            System.out.println(str);  //숫자 4가나온다.
//        });


//        model.addAttribute("categories", categories);

        return "main";
    }

    //메인 게시글
    @ResponseBody
    @GetMapping("/main/posts")
    public ResponseEntity<Map<String, Object>> posts() {
        Map<String, Object> map = new HashMap<>();
//        map.put("posts", boardService.findAllByLatest());

        return ResponseEntity.ok(map);
    }
    //최신순
    @ResponseBody
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> latest() {
        Map<String, Object> map = new HashMap<>();
//        map.put("posts", boardService.findAllByLatest());
//
        return ResponseEntity.ok(map);
    }
    //조회순
    @ResponseBody
    @GetMapping("/popular")
    public ResponseEntity<Map<String, Object>> popular() {
        Map<String, Object> map = new HashMap<>();
//        map.put("posts", boardService.findAllByView());
//
        return ResponseEntity.ok(map);
    }
}
