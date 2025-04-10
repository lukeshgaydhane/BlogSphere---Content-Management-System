package com.blog.BlogSphere.posts;

public interface PostService {

    Post createPost(Post post);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    Post getPostById(Long id);

    Post updatePost(Post post, Long id);

    void deletePost(long id);
}
