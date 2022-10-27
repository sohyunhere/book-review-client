package com.example.bookreviewclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name="ATTACHEDFILE")
public class AttachedFile implements Serializable {
    @Id
    @Column(name = "FILE_ID")
    @SequenceGenerator(name = "FILE_ID_GENERATOR", sequenceName = "FILE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_ID_GENERATOR")
    private Long fileId;

    @Column(name = "NAME")
    private String name;
    @Column(name = "PATH")
    private String path;

    @OneToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    public AttachedFile() {

    }
}
