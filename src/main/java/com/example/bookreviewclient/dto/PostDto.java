package com.example.bookreviewclient.dto;

import com.example.bookreviewclient.model.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
public class PostDto {
    private Member member;
    private Category category;
    private Long categoryId;
    private String postTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date readDate;
    private String bookTitle;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date writtenDate;
    private Long viewCount;

    private String author;
    private String publisher;

    private Location location;
    private Long locationId;
    private double lat;
    private double lng;
    public Post toEntity(){
        return Post.builder()
                .member(member)
                .postTitle(postTitle)
                .readBookDate(new java.sql.Date(readDate.getTime()))
                .bookTitle(bookTitle)
                .content(content)
                .viewCount(viewCount)
                .author(author)
                .publisher(publisher)
                .category(category)
                .writtenDate(writtenDate)
                .location(location)
                .build();
    }
    private Long postId;
    private Date readBookDate;
    private AttachedFile attachedFile = null;
    private List<Comments> comments = null;
    public PostDto(){}
    public PostDto(Post post) {
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
            List<Comments> commentsList = post.getComments();
            for (Comments comment : commentsList) {
                comments.add(comment);
            }
        }

        if(post.getAttachedFile() != null) {
            attachedFile = post.getAttachedFile();
        }
    }

}
