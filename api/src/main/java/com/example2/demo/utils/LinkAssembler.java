package com.example2.demo.utils;

import com.example2.demo.controllers.GameApiController;
import com.example2.demo.controllers.UserApiController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class LinkAssembler {

    public static Link generateGameLink(Long id) {
        return WebMvcLinkBuilder
                .linkTo(GameApiController.class)
                .slash("games/" + id)
                .withSelfRel();
    }

    public static Link generateCommentLink(Long userId, Long commentId) {
        return WebMvcLinkBuilder
                .linkTo(UserApiController.class)
                .slash("users/" + userId + "/comments/" + commentId)
                .withSelfRel();
    }
}
