package com.example.springproject.service;

import com.example.springproject.Entity.Ticket;
import com.example.springproject.Entity.Train;
import com.example.springproject.Entity.User;
import com.example.springproject.Repository.TicketRepository;
import com.example.springproject.Repository.TrainRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.Services.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceTest {
    static private Long ID =1l;
    TicketRepository ticketRepository;
    TrainRepository trainRepository;
    UserRepository userRepository;
    TicketService ticketService;
    List<Train> trainList;
    List<User> users;
    public TicketServiceTest(){
        ticketRepository = mock(TicketRepository.class);
        trainRepository = mock(TrainRepository.class);
        userRepository = mock(UserRepository.class);
        ticketService = new TicketService(userRepository,ticketRepository,trainRepository);
        users = new ArrayList<>();
        trainList= new ArrayList<>();
        for(int i =0;i<3;i++){
            User user = new User();
            user.setId(Long.valueOf(i+1));
            user.setMoney(100*i);
            user.setEmail("email@gmail.com");
            users.add(user);

        }
        Train train = new Train();
        train.setId(ID);
        train.setEnable(true);
        train.setSeats(1);
        train.setBooked(0);
        train.setCost(100);
        trainList.add(train);

    }
    @Test
    void BuyTicketTest(){
        for(long i=1;i<4;i++){
            when(userRepository.getUserById(i)).thenReturn(Optional.of(users.get((int) (i-1))));
        }
        when(trainRepository.findTrainById(ID)).thenReturn(trainList);
        Ticket ticket = new Ticket();
        ticket.setEnable(true);
        ticket.setCost(trainList.get(0).getCost());
        ticket.setTrain(trainList.get(0).getId());
        Assertions.assertEquals(100,users.get(1).getMoney());
        Assertions.assertThrows(IllegalArgumentException.class ,
                ()->ticketService.BuyTicket(ticket,users.get(0)),"Noenoughfunds");
        ticketService.BuyTicket(ticket,users.get(1));
        Assertions.assertEquals(0,users.get(1).getMoney());
        Assertions.assertThrows(IllegalArgumentException.class ,
                ()->ticketService.BuyTicket(ticket,users.get(2)),"NoFreeSeats");
    }
    @Test
    void createTicketFromForm(){
        String station1 = "st1";
        String station2 = "st2";
        when(trainRepository.findTrainById(ID)).thenReturn(trainList);
        Ticket ticket = new Ticket();
        ticket.setCost(100);
        ticket.setTrain(ID);
        ticket.setFirstStation(station1);
        ticket.setLastStation(station2);
        ticket.setDate(Date.from(Instant.now()));
        Assertions.assertEquals(ticket,ticketService.createTicketFromForm(ID,station1,station2));
    }
}
