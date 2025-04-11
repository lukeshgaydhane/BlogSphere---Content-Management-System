package com.blog.BlogSphere.Comments;

import com.blog.BlogSphere.posts.PostResponse;

import java.util.List;

public interface CommentService {

    Comment createComment(long postId, Comment comment);

    CommentResponse getAllComments(int pageNo, int pageSize, String sortBy, String sortDir);

    List<Comment> getCommentsByPostId(long postId);

    Comment getCommentById(long id);

    Comment updateComment(long postId, long id, Comment comment);

    void deleteCommnt(long postId, long id);
}
