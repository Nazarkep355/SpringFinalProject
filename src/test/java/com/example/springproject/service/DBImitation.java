package com.example.springproject.service;

import com.example.springproject.entity.User;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBImitation{
    List<User> users;
    public DBImitation(){
        users = new ArrayList<>();
    }
    List<User> getUsersByEmail(String email){
        return users.stream().filter((a->{return a.getEmail().equals(email);}))
                .collect(Collectors.toList());

    }
    Answer<?> add(User user){
       users.add(user);
       return new Answer<Object>() {
           @Override
           public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
               return null;
           }
       } ;
    }

}
