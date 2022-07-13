package com.example.springproject.controllers;

import com.example.springproject.entity.User;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.services.UserService;
import com.example.springproject.services.UtilityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class RegisterControllerTest {
        RegisterController controller;
        @Mock
        UserRepository userRepository;
        UserService userService;
        @Mock
        UtilityService utilityService;
        public RegisterControllerTest(){
            MockitoAnnotations.initMocks(this);
            userService = new UserService(userRepository);
            controller = new RegisterController(utilityService,userService);
        }

        @Test
        void registerPageTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/registerPage"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("Register.html"));
        }
        @Test
        void registerUser()throws  Exception{
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
            User user = new User();
            user.setPassword("password");
            user.setEmail("wrongemail");
            user.setName("name");
            user.setMoney(0);
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            when(userRepository.findByEmail("wrongemail")).thenReturn(List.of(user));
            mockMvc.perform(MockMvcRequestBuilders.post("/registration").with(request ->
            { request.setParameter("email", "wrongemail");
                request.setParameter("name", "name");
                request.setParameter("password", "password");
                return request;}))
                    .andExpect(MockMvcResultMatchers.redirectedUrl("/registerPage"))
                    .andExpect(MockMvcResultMatchers.request().sessionAttribute("error","EmailInDB"));



//            mockMvc.perform(MockMvcRequestBuilders.post("/registration").with(request ->
//                    { request.setParameter("email", "1");
//                        request.setParameter("name", "name");
//                        request.setParameter("password", "1");
//                        return request;}))
//                    .andExpect(MockMvcResultMatchers.redirectedUrl("/registerPage"))
//                    .andExpect(MockMvcResultMatchers.request().sessionAttribute("error","WrongFormat"));


            mockMvc.perform(MockMvcRequestBuilders.post("/registration").with(request ->
                    { request.setParameter("email", "email@gmail.com");
                        request.setParameter("name", "name");
                        request.setParameter("password", "12345678");
                        return request;}))
//                    .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
                    .andExpect(MockMvcResultMatchers.view().name("login.html"));
        }
}
