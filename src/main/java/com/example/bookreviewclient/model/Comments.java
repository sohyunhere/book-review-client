package com.example.bookreviewclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Comments implements Serializable{
   @Id
   @Column(name = "COMMENT_ID")
   @SequenceGenerator(name = "COMMENT_ID_GENERATOR", sequenceName = "COMMENT_SEQ", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_ID_GENERATOR")
   private Long commentId;

   private String content;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "WRITTENDATE")
   private Date writtenDate;

   @ManyToOne
   @JoinColumn(name = "POST_ID")
   private Post post;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Comments() {

    }
}
