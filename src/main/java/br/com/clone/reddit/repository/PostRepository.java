package br.com.clone.reddit.repository;

import br.com.clone.reddit.dto.PostResponse;
import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.model.Subreddit;
import br.com.clone.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    List<Post> findAllBySubreddit(Subreddit subreddit);
}
