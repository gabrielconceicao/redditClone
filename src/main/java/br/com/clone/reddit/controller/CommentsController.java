package br.com.clone.reddit.controller;

import br.com.clone.reddit.dto.CommentsDto;
import br.com.clone.reddit.service.CommentsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Api(value = "Comments")
@RequestMapping("api/comments/")
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CommentsDto commentsDto) {
        commentsService.createComments(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentsService.getCommentsByPost(id));
    }

    @GetMapping("user/{username}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(commentsService.getCommentsByUsername(username));
    }
}
