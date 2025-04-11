package com.blog.BlogSphere.Comments;

import com.blog.BlogSphere.Exception.BlogAPIException;
import com.blog.BlogSphere.Exception.ResourceNotFoundException;
import com.blog.BlogSphere.posts.PostRepository;
import com.blog.BlogSphere.posts.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public PostRepository postRepository;

    @Override
    public Comment createComment(long postId, Comment comment) {

                // retrieve post entity by id
                Post post = postRepository.findById(postId).orElseThrow(
                        () -> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment entity
        comment.setPost(post);

        // comment entity to DB
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    @Override
    public CommentResponse getAllComments(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> comments = commentRepository.findAll(pageable);
        List<Comment> commentList = comments.getContent();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(commentList);
        commentResponse.setPageNo(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLast(comments.isLast());


        return commentResponse;
    }

    @Override
    public List<Comment> getCommentsByPostId(long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        List<Comment> commentList =commentRepository.getCommentsByPostId(postId);
        return commentList.stream().collect(Collectors.toList());
    }

    @Override
    public Comment getCommentById(long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );
        return comment;
    }

    @Override
    public Comment updateComment(long postId, long id, Comment comment) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment commentThrow = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );

        if (!commentThrow.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }


        commentThrow.setTitle(comment.getTitle());
        commentThrow.setBody(comment.getBody());

        Comment UpdatedComment = commentRepository.save(commentThrow);

        return UpdatedComment;
    }

    @Override
    public void deleteCommnt(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment commentThrow = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );

        if(!commentThrow.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(commentThrow);
    }

}
