package com.example2.demo.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentData {

    private Long id;
    private String uuid;
    private String comment;
    private Long gameKey;
    private String login;
}
