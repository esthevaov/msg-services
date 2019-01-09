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
import java.util.Optional;
import java.util.function.Function;
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

    public ResponseEntity<?> create(Post post) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("user", "email", customUserDetails.getUsername()));

        post.setUser(user);

        post.setVotes(0L);

        Post savedPost = postRepository.save(post);

        return new ResponseEntity<>(PostMapper.mapDomainToVo(savedPost), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAll() {

        List<Post> allPosts = postRepository.findAll();

        List<PostVo> allPostsVo = allPosts.stream().map(n -> PostMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allPostsVo, HttpStatus.OK);

    }

    public ResponseEntity<?> getAllByVoteNumberAsc() {

        List<Post> allPosts = postRepository.findAllByOrderByVotesAsc();

        List<PostVo> allPostsVo = allPosts.stream().map(n -> PostMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allPostsVo, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllByVoteNumberDesc() {

        List<Post> allPosts = postRepository.findAllByOrderByVotesDesc();

        List<PostVo> allPostsVo = allPosts.stream().map(n -> PostMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allPostsVo, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllByCommentNumber() {

        List<Post> allPosts = postRepository.findAllByOrderByCommentsDesc();

        List<PostVo> allPostsVo = allPosts.stream().map(n -> PostMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allPostsVo, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllByDateRecent() {

        List<Post> allPosts = postRepository.findAllByOrderByCreatedDesc();

        List<PostVo> allPostsVo = allPosts.stream().map(n -> PostMapper.mapDomainToVo(n))
            .collect(Collectors.toList());

        return new ResponseEntity<>(allPostsVo, HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        return new ResponseEntity<>(PostMapper.mapDomainToVo(post), HttpStatus.OK);
    }

    public ResponseEntity<?> upvote(Long postId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("user", "email", customUserDetails.getUsername()));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        List<Vote> votedList = post.getVotedList();
        Map<Long, Vote> votedMap = votedList.stream().collect(Collectors.toMap(Vote::getUserId, Function.identity()));

        if (!votedMap.containsKey(user.getId())){
            votedList.add(new Vote(post.getId(), user.getId(), 1));
            post.setVotes(post.getVotes()+1L);
        } else {
            if (votedMap.get(user.getId()).getTypeVote() == 1){
                return new ResponseEntity<>("Already Upvoted", HttpStatus.CONFLICT);
            } else {
                votedMap.get(user.getId()).setTypeVote(1);
                post.setVotes(post.getVotes()+2L);
            }
        }
        Post savedPost = postRepository.save(post);

        return new ResponseEntity<>("Upvoted Successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> downvote(Long postId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails customUserDetails = ((CustomUserDetails)principal);

        User user = userRepository.findByEmail(customUserDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("user", "email", customUserDetails.getUsername()));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        List<Vote> votedList = post.getVotedList();
        Map<Long, Vote> votedMap = votedList.stream().collect(Collectors.toMap(Vote::getUserId, Function.identity()));

        if (!votedMap.containsKey(user.getId())){
            votedList.add(new Vote(post.getId(), user.getId(), -1));
            post.setVotes(post.getVotes()-1L);
        } else {
            if (votedMap.get(user.getId()).getTypeVote() == -1){
                return new ResponseEntity<>("Already Downvoted", HttpStatus.CONFLICT);
            } else {
                votedMap.get(user.getId()).setTypeVote(-1);
                post.setVotes(post.getVotes()-2L);
            }
        }
        Post savedPost = postRepository.save(post);

        return new ResponseEntity<>("Downvoted Successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long postId) {

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
