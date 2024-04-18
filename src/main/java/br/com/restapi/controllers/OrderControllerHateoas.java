package br.com.restapi.controllers;

import br.com.restapi.entity.OrderHateoas;
import br.com.restapi.entity.Status;
import br.com.restapi.exceptions.OrderNotFoundExceptionHateoas;
import br.com.restapi.repositories.OrderRepositoryHateoas;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author marcos
 */
@RestController
public class OrderControllerHateoas {

    private final OrderRepositoryHateoas repositoryOrder;

    public OrderControllerHateoas(OrderRepositoryHateoas repositoryHateoas) {
        this.repositoryOrder = repositoryHateoas;
    }

    @GetMapping("/orders")
    ResponseEntity<List<OrderHateoas>> consultOrderAll() {
        long idOrder;
        Link linkUri;
        List<OrderHateoas> orderList = repositoryOrder.findAll();
        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (OrderHateoas orderHateoas : orderList) {
            idOrder = orderHateoas.getId();
            linkUri = linkTo(methodOn(OrderControllerHateoas.class).consultOneOrder(idOrder)).withSelfRel();
            orderHateoas.add(linkUri);
            linkUri = linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("Custumer order list");
            orderHateoas.add(linkUri);
        }
        return new ResponseEntity<List<OrderHateoas>>(orderList, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    ResponseEntity<OrderHateoas> consultOneOrder(@PathVariable Long id) {
        Optional<OrderHateoas> orderPointer = repositoryOrder.findById(id);
        if (orderPointer.isPresent()) {
            OrderHateoas order = orderPointer.get();
            order.add(linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("All orders"));
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/orders")
    OrderHateoas newOrder(@RequestBody OrderHateoas newOrder) {
        return repositoryOrder.save(newOrder);
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable long id) {
        repositoryOrder.deleteById(id);
    }

    @PutMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelOrderById(@PathVariable long id) {
        OrderHateoas cancelledOrder = repositoryOrder.
                findById(id).orElseThrow(() -> new OrderNotFoundExceptionHateoas(id));
        
        if (cancelledOrder.getStatus() == Status.IN_PROGRESS) {
            cancelledOrder.setStatus(Status.COMPLETED);
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOneOrder(id)).withSelfRel());
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("Complete order list"));
            repositoryOrder.save(cancelledOrder);

            return new ResponseEntity<>(cancelledOrder, HttpStatus.OK);

        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, 
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can't complete the task, the order has a "
                        + cancelledOrder.getStatus() + " status");
    }
    
    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<?> completeOrderById(@PathVariable long id){
        OrderHateoas cancelledOrder = repositoryOrder.findById(id).orElseThrow(
                () -> new OrderNotFoundExceptionHateoas(id));
        if (cancelledOrder.getStatus() == Status.IN_PROGRESS){
            cancelledOrder.setStatus(Status.COMPLETED);
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOneOrder(id)).withSelfRel());
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll())
                    .withRel("Complete order list"));
            repositoryOrder.save(cancelledOrder);
            return new ResponseEntity<>(cancelledOrder,HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body("You can't complete the task, the order has a " +
                        cancelledOrder.getStatus() + " status");
    }
}
