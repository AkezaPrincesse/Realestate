package com.innova.realestate.repositories;

import com.innova.realestate.models.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

    List<Lease> findByTenant_Id(Long tenantId);

    List<Lease> findByProperty_Id(Long propertyId);

    List<Lease> findByStartDateBetween(LocalDate start, LocalDate end);

    List<Lease> findByEndDateBefore(LocalDate date); // Expiring leases

    @Query("SELECT COUNT(l) FROM Lease l WHERE l.startDate >= :start AND l.startDate <= :end")
    Long countNewLeasesInPeriod(LocalDate start, LocalDate end);
}
