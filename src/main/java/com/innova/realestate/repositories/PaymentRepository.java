package com.innova.realestate.repositories;

import com.innova.realestate.models.Payment;
import com.innova.realestate.models.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 🔹 Find all payments by a specific lease
    List<Payment> findByLease(Lease lease);

    // 🔹 Find payments made between two dates
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // 🔹 Total payments for a given lease
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.lease = :lease")
    Double getTotalPaidByLease(Lease lease);

    // 🔹 Grouped total payments by month
    @Query("SELECT FUNCTION('MONTH', p.paymentDate) AS month, SUM(p.amount) FROM Payment p GROUP BY FUNCTION('MONTH', p.paymentDate)")
    List<Object[]> getMonthlyPaymentSums();

    // 🔹 Get total payment amount
    @Query("SELECT SUM(p.amount) FROM Payment p")
    Double getTotalPayments();
}
