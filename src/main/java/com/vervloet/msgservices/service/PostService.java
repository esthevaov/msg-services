package com.vervloet.msgservices.service;

import com.vervloet.msgservices.domain.model.CustomUserDetails;
import com.vervloet.msgservices.domain.model.Post;
import com.vervloet.msgservices.domain.exceptions.ResourceNotFoundException;
import com.vervloet.msgservices.domain.model.User;
import com.vervloet.msgservices.domain.model.Vote;
import com.vervloet.msgservices.domain.vo.PostVo;
import com.vervloet.msgservices.mapper.PostMapper;
import com.vervloet.msgservices.repository.PostRepository;
import com.vervloet.msgservices.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createPost(Post post) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername());

        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return new ResponseEntity<>(PostMapper.mapDomainToVo(savedPost), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllPosts() {

        List<Post> allPosts = postRepository.findAll();

        List<PostVo> allPostsVo = allPosts.stream().map(n -> PostMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allPostsVo, HttpStatus.OK);

    }

    public ResponseEntity<?> getPostById(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        return new ResponseEntity<>(PostMapper.mapDomainToVo(post), HttpStatus.OK);
    }

    public ResponseEntity<?> upvoteMessage(Long messageId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername());

        Post message = postRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));

        List<Vote> votedList = message.getVotedList();

        if (!votedList.(user.getId())){
            votedMap.put(user.getId(), true);
            message.setVotedMap(votedMap);
            message.setVotes(message.getVotes()+1L);
        } else {
            if (votedMap.get(user.getId())){
                return new ResponseEntity<>("Already Upvoted", HttpStatus.CONFLICT);
            } else {
                votedMap.replace(user.getId(),true);
                message.setVotedMap(votedMap);
                message.setVotes(message.getVotes()+2L);
            }
        }

        return new ResponseEntity<>("Upvoted Successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> downvoteMessage(Long messageId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername());

        Post message = postRepository.findById(messageId)
            .orElseThrow(() -> new ResourceNotFoundException("message", "id", messageId));

        Map<Long,Boolean> votedMap = message.getVotedMap();

        if (!votedMap.containsKey(user.getId())){
            votedMap.put(user.getId(), false);
            message.setVotedMap(votedMap);
            message.setVotes(message.getVotes()-1L);
        } else {
            if (!votedMap.get(user.getId())){
                return new ResponseEntity<>("Already Downvoted", HttpStatus.CONFLICT);
            } else {
                votedMap.replace(user.getId(),false);
                message.setVotedMap(votedMap);
                message.setVotes(message.getVotes()-2L);
            }
        }

        return new ResponseEntity<>("Downvoted Successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> deletePost(Long postId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if( post.getUser().getEmail().equals(customUserDetails.getUsername())){
            postRepository.delete(post);
            return new ResponseEntity<>("Post Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Action not authorized for this user", HttpStatus.UNAUTHORIZED);
        }


    }

}
