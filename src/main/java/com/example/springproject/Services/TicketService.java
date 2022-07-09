package com.example.springproject.Services;

import com.example.springproject.Entity.Ticket;
import com.example.springproject.Entity.Train;
import com.example.springproject.Entity.User;
import com.example.springproject.Repository.TicketRepository;
import com.example.springproject.Repository.TrainRepository;
import com.example.springproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class TicketService {
    public TicketService(UserRepository userRepository, TicketRepository ticketRepository, TrainRepository trainRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.trainRepository = trainRepository;
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TrainRepository trainRepository;
    @Autowired
    EmailService emailService;

    public List<Ticket> getPaginatedTicketList(String email, int page){
        User user = userRepository.findByEmail(email).get(0);
        List<Ticket> ticketList = ticketRepository.getTicketByOwnerAndEnableOrderByIdDesc
                (user.getId(),true, PageRequest.of(--page,5));
        return ticketList;
    }
    public void addInfoAboutTicketsToModel(Model model,List<Ticket> tickets){
        List<Long> trainId = new ArrayList<>();
        List<String> fStation= new ArrayList<>();
        List<Date> ordered = new ArrayList<>();
        List<Date> fDate = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        List<String> lStation = new ArrayList<>();
        List<Date> lDate = new ArrayList<>();
        for(Ticket t : tickets){
            ordered.add(t.getDate());
            Train train = trainRepository.findTrainById(t.getTrain()).get(0);
            trainId.add(train.getId());
            String firstStation = (t.getFirstStation()!=null)?t.getFirstStation():train.getStations().get(0);
            fStation.add(firstStation);
            fDate.add(train.getAgenda().get(train.getStations().indexOf(firstStation)));
            costs.add(t.getCost());
            String lastStation = (t.getLastStation()!=null)?t.getLastStation():train.getStations().get(train.getStations().size()-1);
            lStation.add(lastStation);
            lDate.add(train.getAgenda().get(train.getStations().indexOf(lastStation)));
        }
        model.addAttribute("trainId",trainId);
        model.addAttribute("fStation",fStation);
        model.addAttribute("fDate",fDate);
        model.addAttribute("lStation",lStation);
        model.addAttribute("lDate",lDate);
        model.addAttribute("costs",costs);
        model.addAttribute("ordered",ordered);
    }
    public Ticket createTicketFromForm(Long train, String station1,String station2){
        Ticket ticket = new Ticket.TicketBuilder().train(train).firstStation(station1).lastStation(station2).build();
        ticket.setDate(Date.from(Instant.now()));
        ticket.setCost(trainRepository.findTrainById(train).get(0).getCost());
        return ticket;
    }
    public void BuyTicket(Ticket ticket, User user){
        ticket.setOwner(user.getId());
        ticket.setEnable(true);
        Train train = trainRepository.findTrainById(ticket.getTrain()).get(0);
        if(user.getMoney()<ticket.getCost())
            throw new IllegalArgumentException("Noenoughfunds");
        if(train.getBooked()>=train.getSeats())
            throw new IllegalArgumentException("NoFreeSeats");
        saveChangesAboutBuying(train,user,ticket);
    }
    @Transactional
    public void saveChangesAboutBuying(Train train, User user,Ticket ticket){
        train = trainRepository.findTrainById(train.getId()).get(0);
        user = userRepository.getUserById(user.getId()).get();
        train.setBooked(train.getBooked()+1);
        user.setMoney(user.getMoney()-ticket.getCost());
        ticketRepository.save(ticket);
        trainRepository.save(train);
        userRepository.save(user);

    }
    public void sendEmailAboutBuying(Long trainId,String userEmail, HttpServletRequest request ){
        Train train = trainRepository.findTrainById(trainId).get(0);
        User user = userRepository.findByEmail(userEmail).get(0);
        emailService.sendMessageAboutTicketBuying(train,user,request);
    }
}
