package br.com.clone.reddit.mapper;
import br.com.clone.reddit.dto.PostRequest;
import br.com.clone.reddit.dto.PostResponse;
import br.com.clone.reddit.model.Post;
import br.com.clone.reddit.model.Subreddit;
import br.com.clone.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);

    /*Integer commentCount(Post post) {
        return commmentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return post.getCreatedDate().toString();
    }*/
}
