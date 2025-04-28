package com.innova.realestate.repositories;

import com.innova.realestate.models.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findByNameContainingIgnoreCase(String name);

    Tenant findByEmail(String email);

    @Query("SELECT COUNT(t) FROM Tenant t")
    Long getTotalTenants();

    @Query("SELECT SUBSTRING(t.name, 1, 1) AS initial, COUNT(t) FROM Tenant t GROUP BY SUBSTRING(t.name, 1, 1)")
    List<Object[]> countTenantsGroupedByInitial();
}
