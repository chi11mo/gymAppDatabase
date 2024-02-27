package com.chillmo.gymappdatabase.users.repository;

import com.chillmo.gymappdatabase.users.domain.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interact with the Token-Objects stored in the DB.
 */
@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

    Token findTokenByTokenContent(String tokenContent);

}
