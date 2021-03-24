package net.purocodigo.backendcursojava.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.purocodigo.backendcursojava.UserRepository;
import net.purocodigo.backendcursojava.entities.UserEntity;
import net.purocodigo.backendcursojava.shared.dto.UserDto;

@Service
public class UserService implements UserServiceInterface {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new RuntimeException("El correo electronico ya existe");
        
        
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("testpassword");

        UUID  userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }
    
}
