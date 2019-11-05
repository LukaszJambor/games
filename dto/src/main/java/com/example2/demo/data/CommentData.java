package com.example2.demo.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentData {

    private Long id;
    private String uuid;
    private String comment;
    private Long gameKey;
}
