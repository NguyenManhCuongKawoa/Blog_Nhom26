package nhom26.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notify {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "post_id")
	private Long postId;
	
	@Column(name = "sender_id")
	private Long senderId;
	
	@Column(name = "sender_name")
	private String senderName;
	

	@Column(name = "receiver_id")
	private Long receiver;
	
	@Column(name = "short_content")
	private String shortContent;
	
	@Column(name = "created_at")
	private LocalDate createdAt;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPostId() {
		return postId;
	}


	public void setPostId(Long postId) {
		this.postId = postId;
	}


	public Long getSenderId() {
		return senderId;
	}


	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public Long getReceiver() {
		return receiver;
	}


	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}


	public String getShortContent() {
		return shortContent;
	}


	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}


	public LocalDate getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
