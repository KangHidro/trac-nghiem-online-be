package com.stc.tracnghiemonline.repositories;

import com.stc.tracnghiemonline.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:21 PM
 * Filename  : UserRepository
 */
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{$or: [{'email' : { $regex: ?0, $options: 'i' } }, {'name' : { $regex: ?0, $options: 'i' } }] }"
            , sort = "{'enable' : -1, 'email' : 1}")
    Page<User> getUserPaging(String search, Pageable pageable);

    @Query(value = "{'email' : ?0}")
    Optional<User> getUser(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndEnableTrue(String id);
}
