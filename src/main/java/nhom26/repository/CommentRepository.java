package nhom26.repository;

import nhom26.model.Comment;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Transactional
    @Modifying
    @Query(value="delete from comment where post_id = :id", nativeQuery  = true)
    void deleteCommentByPostId(Long id);
	
	@Transactional
    @Modifying
    @Query(value="delete from comment where user_id = :id", nativeQuery  = true)
    void deleteCommentByUserId(Long id);
}
