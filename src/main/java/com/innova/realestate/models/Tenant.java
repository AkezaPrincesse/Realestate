package com.innova.realestate.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "tenant")
    private List<Lease> leases;

    public Tenant() {}

    public Tenant(Long id, String name, String email, String phone, List<Lease> leases) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.leases = leases;
    }

    public Tenant(String name, String email, String phone, List<Lease> leases) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.leases = leases;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Lease> getLeases() {
        return leases;
    }

    public void setLeases(List<Lease> leases) {
        this.leases = leases;
    }
}
