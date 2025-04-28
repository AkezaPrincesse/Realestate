package com.innova.realestate.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Embedded
    private Address address;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private User owner;

    @OneToMany(mappedBy = "property")
    private List<Lease> leases;

    public Property() {
    }


    public Property(Long id, String title, Address address, Double price, User owner) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.owner = owner;
    }

    public Property(Long id, String title, Address address, Double price, User owner, List<Lease> leases) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.owner = owner;
        this.leases = leases;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Lease> getLeases() {
        return leases;
    }

    public void setLeases(List<Lease> leases) {
        this.leases = leases;
    }
}
