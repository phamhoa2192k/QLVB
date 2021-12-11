package edu.hust.document.service;

import edu.hust.document.entity.UserEntity;
import edu.hust.document.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public UserEntity updateUserEntity(UserEntity userEntity) {
        UserEntity userEntity1 = null;
        try {
            userEntity1 = userRepository.save(userEntity);
        }catch (Exception e) {
            //Log

        }

        return userEntity1;
    }
}
