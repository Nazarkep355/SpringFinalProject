package com.example.springproject.service;

import com.example.springproject.Entity.User;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.Services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    UserService userService;
    DBImitation dbImitation;
    UserRepository repository;
    public UserServiceTest(){
        dbImitation = new DBImitation();
        for(long i=1;i<6;i++)
        {User user = new User();
        user.setId(i);
        user.setEmail("user"+i+"@gmail.com");
        dbImitation.add(user);}
        repository = mock(UserRepository.class);
        userService= new UserService(repository);
    }
    @Test
    void isUserWithEmailExists_ShouldReturnBoolean(){
        for(int i = 1;i<6;i++){
        when(repository.findByEmail("user"+i+"@gmail.com"))
                .thenReturn(dbImitation.getUsersByEmail("user"+i+"@gmail.com"));}
        Assertions.assertTrue(userService.isUserWithEmailExists("user1@gmail.com"));
        Assertions.assertTrue(userService.isUserWithEmailExists("user3@gmail.com"));
        Assertions.assertTrue(userService.isUserWithEmailExists("user5@gmail.com"));
        Assertions.assertFalse(userService.isUserWithEmailExists("user20@gmail.com"));
    }
//    @Test
//    void registerUserTest(){
//        List<User> newUsers = new ArrayList<>();
//        for(int i =0;i<3;i++){
//            User user = new User();
//            user.setEmail("user"+10+i+"@gmail.com");
//            user.setPassword("123"+i);
//            newUsers.add(user);
//            when(repository.save(user)).then(dbImitation.add(user));
//        }
//        int size= dbImitation.users.size();
//        userService.registerUser(newUsers.get(0));
//        Assertions.assertTrue(dbImitation.users.size()>size);
//
//    }
}
