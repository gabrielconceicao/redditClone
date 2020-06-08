package br.com.clone.reddit.controller;

import br.com.clone.reddit.dto.PostRequest;
import br.com.clone.reddit.dto.PostResponse;
import br.com.clone.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPost(id));
    }

    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long subredditId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostsBySubredditId(subredditId));
    }

    @GetMapping("user/{username}")
    public ResponseEntity<List<PostResponse>> getPostByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostsByUsername(username));
    }



}
