package br.com.restapi.controllers;

import br.com.restapi.entity.EmployeeHateoas;
import br.com.restapi.repositories.EmployeeRepositoryHateoas;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author marcos
 */
@RestController
public class EmployeeControllerHateoas {

    private final EmployeeRepositoryHateoas repository;

    public EmployeeControllerHateoas(EmployeeRepositoryHateoas repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    ResponseEntity<List<EmployeeHateoas>> listOfEmployeeAll() {
        List<EmployeeHateoas> employeeHateoasList = repository.findAll();
        long id;
        Link linkUri;
        // inserindo retorno com reponse http
        if (employeeHateoasList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (EmployeeHateoas employeeHateoas : employeeHateoasList) {
            id = employeeHateoas.getId();
            linkUri = linkTo(methodOn(EmployeeControllerHateoas.class).consultOneEmployee(id)).withSelfRel();
            employeeHateoas.add(linkUri);
        }
        return new ResponseEntity<List<EmployeeHateoas>>(employeeHateoasList, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    ResponseEntity<EmployeeHateoas> consultOneEmployee(@PathVariable Long id) {
        // Para enviar como resposta HTTP devemos setar o retorno para ResponseEntity<>
        Link linkUri;
        Optional<EmployeeHateoas> employeesHateoasOptional = repository.findById(id);
        if (employeesHateoasOptional.isPresent()) {
            EmployeeHateoas employeesHateoas = employeesHateoasOptional.get();
            employeesHateoas.add(linkTo(methodOn(EmployeeControllerHateoas.class).listOfEmployeeAll()).withRel("Employees List"));
            employeesHateoas.add(linkTo(methodOn(EmployeeControllerHateoas.class).consultOneEmployee(id)).withSelfRel());
            return new ResponseEntity<>(employeesHateoas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/employees/{id}")
    EmployeeHateoas replaceEmployee(@RequestBody EmployeeHateoas newEmployee, long id) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setAddress(newEmployee.getAddress());
            employee.setRole(newEmployee.getRole());
            return repository.save(newEmployee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    //adicionando um employee
    @PostMapping("/employees")
    EmployeeHateoas newEmployee(@RequestBody EmployeeHateoas newEmployee) {
        return repository.save(newEmployee);
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable long id) {
        repository.deleteById(id);
    }
}
