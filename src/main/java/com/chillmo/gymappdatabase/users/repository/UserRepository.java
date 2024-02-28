package com.chillmo.gymappdatabase.users.repository;

import com.chillmo.gymappdatabase.users.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface for CRUD operations on a repository for the {@link User} type.
 * This interface extends {@link CrudRepository}, providing generic CRUD operations for User entities.
 * Spring Data JPA automatically implements this repository interface in a bean that has CRUD functionality.
 * <p>
 * Annotated with {@link Repository} to indicate that this interface is a Spring managed repository.
 * {@link Transactional( true)} indicates that all methods in this repository should be run
 * within a read-only transaction, improving performance for read operations.
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Deletes a {@link User} entity by its id.
     *
     * @param id the id of the entity to delete.
     */
    void deleteById(Long id);

    /**
     * Retrieves a {@link User} by its id.
     *
     * @param id the id of the entity to retrieve.
     * @return the {@link User} with the given id.
     */
    User findUserById(Long id);

    /**
     * Retrieves a {@link User} by its username.
     *
     * @param username the username of the entity to retrieve.
     * @return an {@link Optional} describing the {@link User} found, or an empty {@link Optional} if no entity found.
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Retrieves a {@link User} by its email.
     *
     * @param email the email of the entity to retrieve.
     * @return the {@link User} with the given email.
     */
    User findUserByEmail(String email);

}
