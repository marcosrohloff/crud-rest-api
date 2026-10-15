package br.com.restapi.repositories;

import br.com.restapi.entity.OrderHateoas;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author marcos
 */
public interface OrderRepositoryHateoas extends JpaRepository<OrderHateoas, Long>{
    
}
