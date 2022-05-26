package nhom26.repository;

import nhom26.model.Role;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(@Param("role") String role);
    
    @Transactional
    @Modifying
    @Query(value="delete from user_role where user_id = :id", nativeQuery  = true)
    void deleteRoleUserByUserId(Long id);
}
