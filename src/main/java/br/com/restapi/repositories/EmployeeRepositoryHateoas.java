package br.com.restapi.repositories;

import br.com.restapi.entity.EmployeeHateoas;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author marcos
 */
public interface EmployeeRepositoryHateoas extends JpaRepository<EmployeeHateoas, Long>{
    
}
