package com.innova.realestate.repositories;

import com.innova.realestate.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT COUNT(p) FROM Property p")
    Long getTotalProperties();

    @Query("SELECT p.title, COUNT(l) FROM Lease l JOIN l.property p GROUP BY p.title")
    List<Object[]> countLeasesPerProperty();

    @Query("SELECT p.title, SUM(pay.amount) FROM Payment pay JOIN pay.lease l JOIN l.property p GROUP BY p.title")
    List<Object[]> totalEarningsPerProperty();
}
