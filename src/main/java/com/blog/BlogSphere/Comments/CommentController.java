package com.blog.BlogSphere.Comments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/postId/{postId}/save")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "postId") long postId,
                                                 @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.createComment(postId, comment), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public CommentResponse getAllComments(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        CommentResponse allComments = commentService.getAllComments(pageNo, pageSize, sortBy, sortDir);
        return allComments;
    }

    @GetMapping("/postId/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable long postId){
        List<Comment> commentsByPostId = commentService.getCommentsByPostId(postId);
        return commentsByPostId;
    }

    // @GetMapping("/postId/{postId}/id/{id}")  if we want this
    @GetMapping("id/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable long id){ // here we have to @PathVariable long PostId, @PathVariable long id
        Comment commentById = commentService.getCommentById(id);
        return new ResponseEntity<>(commentById, HttpStatus.OK);
    }

    @PutMapping("/postId/{postId}/CommentId/{id}")
    public ResponseEntity<Comment> updateCommet(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id,
            @RequestBody Comment comment
            ){
        Comment updatedComment = commentService.updateComment(postId, id, comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/postId/{postId}/CommentId/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id
            ){
        commentService.deleteCommnt(postId, id);
        return new ResponseEntity<>("Comment has been delete.", HttpStatus.OK);
    }
}
