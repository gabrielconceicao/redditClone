package br.com.clone.reddit.service;

import br.com.clone.reddit.dto.CommentsDto;
import br.com.clone.reddit.exceptions.PostNotFoundException;
import br.com.clone.reddit.exceptions.UsernameNotFoundException;
import br.com.clone.reddit.mapper.CommentsMapper;
import br.com.clone.reddit.model.NotificationEmail;
import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.model.User;
import br.com.clone.reddit.repository.CommentRepository;
import br.com.clone.reddit.repository.PostRepository;
import br.com.clone.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
public class CommentsService {

    private final CommentsMapper commentsMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void createComments(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found - id:" + commentsDto.getPostId()));

        commentRepository.save(commentsMapper.map(commentsDto, post, authService.getCurrentUser()));

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post.");
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getCommentsByPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found - id:" + id));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentsMapper::mapToDto)
                .collect(toList());
    }

    public List<CommentsDto> getCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

        return commentRepository.findByUser(user)
                .stream()
                .map(commentsMapper::mapToDto)
                .collect(toList());
    }
}
