package br.com.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "CUSTUMER_ORDER")
public class OrderHateoas extends RepresentationModel<OrderHateoas> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Status status;

    private String description;

    public OrderHateoas() {
    }

    public OrderHateoas(Status status, String description) {
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderHateoas order = (OrderHateoas) o;
        return Objects.equals(id, order.id) && status == order.status 
                && Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, description);
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", status=" + status
                + ", description='" + description + '\''
                + '}';
    }

}
