package br.com.clone.reddit.repository;

import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.model.User;
import br.com.clone.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByPostAndUserOrderByVoteId(Post post, User currentUser);

}
