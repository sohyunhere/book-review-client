package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.dto.PostDd;
import com.example.bookreviewclient.model.Category;
import com.example.bookreviewclient.model.Comments;
import com.example.bookreviewclient.model.Member;
import com.example.bookreviewclient.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardWebController {
    @Autowired
    private WebClient webClient;

    //글쓰기 페이지로 이동
    @GetMapping("/board/write")
    public String goWrite(Model model) {
        List<Category> categories = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Category.class)
                .toStream()
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);

        return "board/wr_post";
    }

    //글 보기 + 조회수 1씩 올리기
    @GetMapping("/board/{postId}")
    public String postView(@PathVariable("postId") Long id, Model model){

        PostDd post = webClient.get()
                .uri("/board/{postId}", id)
                .retrieve()
                .bodyToMono(PostDd.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        model.addAttribute("post", post);
        return "board/v_post";
    }

    //내가 작성한 글게시글 화면이동
    @GetMapping("/board/mypost")
    public String goMyPost(){
        return "member/myPost";
    }

    //수정 게시글로 이동
    @GetMapping("/board/update/{postId}")
    public String updatePost(@PathVariable("postId") Long id, Model model) {
        PostDd post = webClient.get()
                .uri("/board/update/{postId}", id)
                .retrieve()
                .bodyToMono(PostDd.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);
        model.addAttribute("post", post);
        return "board/u_post";
    }


    //내가 작성한 댓글
    @GetMapping("/board/myComment")
    public String getMyComment(Authentication auth, Model model) {
        Member member = (Member) auth.getPrincipal();

        List<Comments> comments = webClient.get()
                .uri("/board/myComment/{email}", member.getMemberEmail())
                .retrieve()
                .bodyToFlux(Comments.class)
                .toStream()
                .collect(Collectors.toList());

        model.addAttribute("comments", comments);
        return "member/myComment";
    }

    //나의 업로드 위치
    @GetMapping("/board/myUploadLocation")
    public String getMyUploadLocation(Authentication auth, Model model){
        Member member = (Member) auth.getPrincipal();

        List<Post> posts = webClient.get()
                .uri("/board/myUploadLocation/{id}", member.getMemberId())
                .retrieve()
                .bodyToFlux(Post.class)
                .toStream()
                .collect(Collectors.toList());

        model.addAttribute("posts", posts);
        return "member/uploadLocation";
    }

    //게시판 통계
    @GetMapping("/board/chart")
    public String drawChart(){
        return "member/boardChart";
    }
}
