package com.blog.BlogSphere.posts;

import com.blog.BlogSphere.Exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepository postRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    @Override
    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // Determine sorting direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Fetch paginated data from the repository
        Page<Post> posts = postRepository.findAll(pageable);

        // Get content for the page
        List<Post> listOfPosts = posts.getContent();

        // Populate PostResponse object
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listOfPosts);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
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
