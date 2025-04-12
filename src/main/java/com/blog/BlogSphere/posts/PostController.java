package com.blog.BlogSphere.posts;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    public PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post){
        Post savedPost = postService.createPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){
        PostResponse allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return allPosts;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(name = "id")  Long id){
        Post postById = postService.getPostById(id);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post, @PathVariable(name = "id")  Long id){
        Post updatedPost = postService.updatePost(post, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id")  long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post has been successfully deleted.", HttpStatus.OK);
    }

}
