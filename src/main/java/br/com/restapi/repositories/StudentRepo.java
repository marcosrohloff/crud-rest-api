package br.com.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restapi.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

}
