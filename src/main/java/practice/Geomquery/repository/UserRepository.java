package practice.Geomquery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.Geomquery.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
