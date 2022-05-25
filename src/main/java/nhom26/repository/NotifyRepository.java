package nhom26.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nhom26.model.Notify;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long>{

}
