package com.example.springproject.controllers;

import com.example.springproject.Controllers.MainController;
import com.example.springproject.Services.UtilityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class MainControllerTest {
    @Mock
    UtilityService utilityService;
    MainController controller;

    public MainControllerTest(){
        MockitoAnnotations.initMocks(this);
        controller = new MainController(utilityService);
    }

    @Test
    void redirectOnHomeTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"));
    }
    @Test
    void LoginPageTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("login.html"));
    }
    @Test
    void HomePageTest() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockMvcResultMatchers.request().sessionAttribute("locale","locale_en");
        mockMvc
                .perform(MockMvcRequestBuilders.get("/home").with(request ->
                {request.setRemoteUser("nazikforall@gmail.com"); return request;}))
                .andExpect(MockMvcResultMatchers.view().name("home.html"));
    }
    @Test
    void changeLocaleToUaTest() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/locale_ua")
                .header("referer","localhost:8080/home"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andExpect(
        MockMvcResultMatchers.request().sessionAttribute("locale",new Locale("locale_ua")));


    }
    @Test
    void changeLocaleToEn() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/locale_en")
                        .header("referer","localhost:8080/home"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                        .andExpect(
        MockMvcResultMatchers.request().sessionAttribute("locale",new Locale("locale_en")));
    }

}
