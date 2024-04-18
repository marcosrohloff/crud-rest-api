package br.com.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class EmployeeHateoas extends RepresentationModel<EmployeeHateoas> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String role;

    private String address;

    //empty constructor for jpa
    public EmployeeHateoas() {
    }

    public EmployeeHateoas(String name, String role, String address) {
        this.name = name;
        this.role = role;
        this.address = address;
    }

    //set the getters and setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "EmployeeHateoas{" + "id=" + id + ", name=" + name + ", role=" + role + ", address=" + address + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role, this.address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeHateoas)) {
            return false;
        }

        EmployeeHateoas employee = (EmployeeHateoas) o;
        return Objects.equals(this.id, employee.id)
                && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role)
                && Objects.equals(this.address, employee.address);
    }

}
