package com.blog.BlogSphere.posts;

import java.util.List;

public interface PostService {

    Post createPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post updatePost(Post post, Long id);

    void deletePost(long id);
}
