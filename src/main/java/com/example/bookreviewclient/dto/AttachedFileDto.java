package com.example.bookreviewclient.dto;

import com.example.bookreviewclient.model.AttachedFile;
import com.example.bookreviewclient.model.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachedFileDto {

    private String name;

    private String path;

    private Post post;
    private long postId;
    public AttachedFile toEntity(){
        return AttachedFile.builder()
                .post(post)
                .path(path)
                .name(name)
                .build();
    }

}
