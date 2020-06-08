package br.com.clone.reddit.repository;

import br.com.clone.reddit.model.Comment;
import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
