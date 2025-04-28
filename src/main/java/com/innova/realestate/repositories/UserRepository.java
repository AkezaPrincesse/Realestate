package com.innova.realestate.repositories;

import com.innova.realestate.models.Property;
import com.innova.realestate.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Basic CRUD Operations
    Optional<User> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();

    // Find by Name & Email
    Optional<User> findByName(String name);
     Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String keyword);
    Optional<User> findByIdAndEmail(Long id, String email);

    // Sorting Queries
    List<User> findAllByOrderByNameAsc();
    List<User> findAllByOrderByEmailAsc();

    // Pagination
    Page<User> findAll(Pageable pageable);
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // JPQL Queries
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> searchByName(@Param("name") String name);

    // Custom query to find users by property title
    @Query("SELECT u FROM User u JOIN u.properties p WHERE p.title = :title")
    List<User> findUsersByPropertyTitle(@Param("title") String title);

    List<User> name(String name);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
