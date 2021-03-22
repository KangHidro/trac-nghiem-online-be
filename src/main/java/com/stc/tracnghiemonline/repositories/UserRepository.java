package com.stc.tracnghiemonline.repositories;

import com.stc.tracnghiemonline.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:21 PM
 * Filename  : UserRepository
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
