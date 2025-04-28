package com.innova.realestate.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lease_id")
    private Lease lease;

    private Double amount;

    private LocalDate paymentDate;

    public Payment() {}

    public Payment(Long id, Lease lease, Double amount, LocalDate paymentDate) {
        this.id = id;
        this.lease = lease;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Long getId() {
        return id;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
