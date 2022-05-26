package nhom26.repository;

import nhom26.model.Post;
import nhom26.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);
    Page<Post> findByUserAndAcceptedOrderByCreateDateDesc(User user, Integer accepted,Pageable pageable);

    Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);
    Page<Post> findAllByAcceptedOrderByCreateDateDesc(Integer accepted, Pageable pageable);


    Optional<Post> findById(Long id);
    
    List<Post> findByUser(User user);
    
    @Transactional
    @Modifying
    @Query(value="delete from post where user_id = :id", nativeQuery  = true)
    void deletePostByUserId(Long id);
}
