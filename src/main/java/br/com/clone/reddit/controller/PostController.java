package br.com.clone.reddit.controller;

import br.com.clone.reddit.dto.PostRequest;
import br.com.clone.reddit.dto.PostResponse;
import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getAll());
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostRequest postRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(postService.createPost(postRequest));

    }



}
