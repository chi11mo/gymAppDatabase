package com.chillmo.gymappdatabase.users.repository;

import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface serves as the repository for Token entities, providing an abstraction layer to interact
 * with the database. It extends CrudRepository, enabling CRUD operations on Token entities.
 */
@Repository // Indicates that this interface is a Spring-managed repository, which interacts with the database.
public interface TokenRepository extends CrudRepository<Token, String> {

    /**
     * Finds a Token entity by its tokenContent.
     *
     * @param tokenContent The content of the token to be searched for.
     * @return Token entity if found, otherwise null.
     */
    Token findTokenByTokenContent(String tokenContent);

    /**
     * Finds a Token entity associated with a specific User entity.
     *
     * @param user The User entity associated with the token to be searched for.
     * @return Token entity if found, otherwise null.
     */
    Token findTokenByUser(User user);
}
