package nhom26.repository;

import nhom26.model.Post;
import nhom26.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByUsername(@Param("username") String username);
    
    @Transactional
    @Modifying
    @Query(value="delete from user where user_id = :id", nativeQuery  = true)
    void deleteByUserId(Long id);
    
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
}
