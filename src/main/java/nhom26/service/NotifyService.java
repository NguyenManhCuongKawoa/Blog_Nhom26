package nhom26.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import nhom26.model.Comment;
import nhom26.model.Notify;
import nhom26.model.Post;
import nhom26.model.User;
import nhom26.repository.NotifyRepository;

@Service
public class NotifyService {
	
	@Autowired private NotifyRepository notifyRepository;
	
	@SendTo("/notify/public")
	public Notify sendNotify(Comment comment) {
		Post post = comment.getPost();
		User user = comment.getUser();
		
		Notify notify = new  Notify();
		notify.setPostId(post.getId());
		notify.setSenderId(user.getId());
		notify.setSenderName(user.getName());
		notify.setReceiver(post.getUser().getId());
		
		String shortContent = comment.getBody().length() > 55 ? comment.getBody().substring(0, 55) : comment.getBody();
		notify.setShortContent(shortContent);
		
		notify.setCreatedAt(LocalDate.now());
		
		notifyRepository.save(notify);
		
		return notify;
	}

}
