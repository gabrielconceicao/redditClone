package br.com.clone.reddit.service;

import br.com.clone.reddit.dto.PostRequest;
import br.com.clone.reddit.dto.PostResponse;
import br.com.clone.reddit.exceptions.PostNotFoundException;
import br.com.clone.reddit.exceptions.SubredditNotFoundException;
import br.com.clone.reddit.exceptions.UsernameNotFoundException;
import br.com.clone.reddit.mapper.PostMapper;
import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.model.Subreddit;
import br.com.clone.reddit.model.User;
import br.com.clone.reddit.repository.PostRepository;
import br.com.clone.reddit.repository.SubredditRepository;
import br.com.clone.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public List<PostResponse> getAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public PostResponse createPost(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Not found Subreddit name" + postRequest.getSubredditName()));

        User currentUser = authService.getCurrentUser();
        Post post = postMapper.map(postRequest, subreddit, currentUser);

        postRepository.save(post);
        return postMapper.mapToDto(post);
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found" + id));

        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public List<PostResponse> getPostsBySubredditId(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found" + subredditId ));

        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
