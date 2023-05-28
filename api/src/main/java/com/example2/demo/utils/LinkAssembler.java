package com.example2.demo.utils;

import com.example2.demo.controllers.GameApiController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

public class LinkAssembler {

    public static Link generateGameLink(Long id){
        return ControllerLinkBuilder
                .linkTo(GameApiController.class)
                .slash("games/" + id)
                .withSelfRel();
    }
}
