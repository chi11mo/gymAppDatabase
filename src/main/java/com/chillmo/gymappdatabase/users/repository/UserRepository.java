package com.chillmo.gymappdatabase.users.repository;

import com.chillmo.gymappdatabase.users.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, Long> {

    void deleteById(Long id);

    User findUserById(Long id);

    Optional<User> findUserByUsername(String username);


    // List<User> findByParticipatingEventsContaining(Event event);


    /**
     * Finds a user by the given mail adress.
     *
     * @param eMail the mail of the searched {@link User}
     * @return an Optional with the requested {@link User} if found
     */


}
