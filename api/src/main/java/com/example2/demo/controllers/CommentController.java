package com.example2.demo.controllers;

import com.example2.demo.converters.ShortCommentToCommentDataMapper;
import com.example2.demo.dao.projections.ShortComment;
import com.example2.demo.data.CommentData;
import com.example2.demo.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/restapi/v1")
public class CommentController {

    private CommentService commentService;
    private ShortCommentToCommentDataMapper shortCommentToCommentDataMapper;

    public CommentController(CommentService commentService, ShortCommentToCommentDataMapper shortCommentToCommentDataMapper) {
        this.commentService = commentService;
        this.shortCommentToCommentDataMapper = shortCommentToCommentDataMapper;
    }

    @GetMapping(path = "games/{gameId}/shortComments")
    public ResponseEntity<List<CommentData>> getShortComments(@PathVariable("gameId") Long id) {
        List<ShortComment> commentEntityList = commentService.getShortComments(id);
        List<CommentData> commentDataList = commentEntityList.stream()
                .map(comment -> shortCommentToCommentDataMapper.toDto(comment))
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDataList);
    }
}
