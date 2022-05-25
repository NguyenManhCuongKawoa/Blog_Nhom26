package nhom26.controller;

import nhom26.model.Comment;
import nhom26.model.Post;
import nhom26.model.User;
import nhom26.service.CommentService;
import nhom26.service.NotifyService;
import nhom26.service.PostService;
import nhom26.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final NotifyService notifyService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService, NotifyService notifyService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.notifyService = notifyService;
    }

    @PostMapping("/createComment")
    public String createNewPost(@Valid Comment comment,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/commentForm";

        } else {
        	comment = commentService.save(comment);
            notifyService.sendNotify(comment);
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

    @GetMapping("/commentPost/{id}")
    public String commentPostWithId(@PathVariable Long id,
                                    Principal principal,
                                    Model model) {

        Optional<Post> post = postService.findForId(id);

        if (post.isPresent()) {
            Optional<User> user = userService.findByUsername(principal.getName());

            if (user.isPresent()) {
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setPost(post.get());

                model.addAttribute("comment", comment);

                return "/commentForm";

            } else {
                return "/error";
            }

        } else {
            return "/error";
        }
    }

}
