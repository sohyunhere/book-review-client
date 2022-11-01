package com.example.bookreviewclient.dto;

import com.example.bookreviewclient.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PostDd {
    private Long postId;
    private Member member;
    private String postTitle;
    private Date readBookDate;
    private String bookTitle;

    private String content;
    private Date writtenDate;
    private Long viewCount;

    private String author;
    private String publisher;
    private Location location;
    private AttachedFile attachedFile = null;
    private Category category;
    private List<Comments> comments = null;
    public PostDd(){}
    public PostDd(Post post) {
        postId = post.getPostId();
        category = post.getCategory();
        member = post.getMember();
        location = post.getLocation();
        postTitle = post.getPostTitle();
        readBookDate = post.getReadBookDate();
        content = post.getContent();
        bookTitle = post.getBookTitle();
        writtenDate = post.getWrittenDate();
        viewCount = post.getViewCount();
        author = post.getAuthor();
        publisher = post.getPublisher();

        if(post.getComments() != null) {
            List<Comments> commentsList = new ArrayList<>();
            List<Comments> comments = post.getComments();
            for (Comments comment : comments) {
                commentsList.add(comment);
            }
        }

        if(post.getAttachedFile() != null) {
            attachedFile = post.getAttachedFile();
        }
    }
}
