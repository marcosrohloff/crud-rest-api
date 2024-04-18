package br.com.restapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.restapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String userName);

    List<User> findAllByOrderByRegDateTimeDesc();
}
