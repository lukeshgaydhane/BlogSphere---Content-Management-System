package com.blog.BlogSphere.posts;

import com.blog.BlogSphere.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    }

    @Override
    public Post updatePost(Post post, Long id) {
        Post updatePost= postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        updatePost.setTitle(post.getTitle());
        updatePost.setDescription(post.getDescription());
        updatePost.setContent(post.getContent());

        Post updatedPost = postRepository.save(updatePost);
        return updatedPost;
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.deleteById(id);
    }
}
