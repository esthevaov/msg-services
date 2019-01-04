package com.vervloet.msgservices.repository;

import com.vervloet.msgservices.domain.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByUserId(Long userId);

    List<Post> findAllByOrderByVotesAsc();

    List<Post> findAllByOrderByVotesDesc();

    List<Post> findAllByOrderByCommentsDesc();

    List<Post> findAllByOrderByCreatedDesc();
}
