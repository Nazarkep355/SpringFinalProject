package com.example.springproject.Services;

import com.example.springproject.Entity.Route;
import com.example.springproject.Entity.Ticket;
import com.example.springproject.Entity.Train;
import com.example.springproject.Entity.User;
import com.example.springproject.Repository.RouteRepository;
import com.example.springproject.Repository.TicketRepository;
import com.example.springproject.Repository.TrainRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.Utility.TrainUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainService {
    public TrainService(){}
    public TrainService(TrainRepository trainRepository,
                        RouteRepository routeRepository,
                        TicketRepository ticketRepository,
                        UserRepository userRepository
    ){
        this.trainRepository=trainRepository;
        this.routeRepository = routeRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }
    @Autowired
    TrainRepository trainRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    public boolean isExistTrainWithId(Long id){
        return trainRepository.findTrainById(id).size()>0;
    }
    public Train getTrainById(Long id){
        if(!isExistTrainWithId(id))
            throw new IllegalArgumentException("NoTrainWithId");
        return trainRepository.findTrainById(id).get(0);
    }
    public List<Train> getPaginatedTrains(int page){
        Pageable pageable = PageRequest.of(--page,5);
        return trainRepository.findAllByEnableOrderById(true,pageable);
    }
    public Train makeTrainFromDateAndRoute(String dateString,Long routeId,int seats){
        Date date = TrainUtility.getDateFromForm(dateString);
        Route route = routeRepository.getRouteById(routeId).get();
        return TrainUtility.planTrainOnDate(date,route,seats);
    }
    public void saveTrain(Train train){
        trainRepository.save(train);
    }
    public Train getLastTrain(){
        return trainRepository.findFirstByOrderByIdDesc().get(0);
    }
    public List<Train> getTrainsFilteredByStations(String station1, String station2){
        List<Train> trainList = trainRepository.findTrainsByEnableOrderById(true);
        trainList = trainList.stream().filter((a)->{
            return a.getStations().contains(station1)
                    &&a.getStations().contains(station2)
                    &&a.getStations().indexOf(station1)<a.getStations().indexOf(station2);})
                    .collect(Collectors.toList());
        return trainList;
    }

    public List<Train> getTrainsFilteredByStationsAndDate(String station1,String station2,String dateString){
        Date date1 =  TrainUtility.getDateFromForm(dateString);
        Date date2 =  TrainUtility.getDateFromForm(dateString);
        date1.setHours(date1.getHours()-24);
        date2.setHours(date2.getHours()+24);
        List<Train> trainList = getTrainsFilteredByStations(station1,station2);
        trainList = trainList.stream().filter((a)->{ return a.getAgenda().get(0).after(date1)
                &&a.getAgenda().get(a.getAgenda().size()-1).before(date2);})
                .collect(Collectors.toList());
        return trainList;
    }
    @Transactional
    public void cancelTrain(Long trainId, HttpServletRequest request){
        List<Ticket> ticketList = ticketRepository.getTicketsByTrainAndEnable(trainId,true);
        List<String> emails = new ArrayList<>();
        Train train = trainRepository.findTrainById(trainId).get(0);
        for(Ticket t: ticketList){
            User user = userRepository.getUserById(t.getOwner()).get();
            user.setMoney(user.getMoney()+t.getCost());
            t.setEnable(false);
            if(!emails.contains(user.getEmail()))
            emails.add(user.getEmail());
            ticketRepository.save(t);
            userRepository.save(user);
        }
        if(emails.size()>0){
            String[] recipients = new String[emails.size()];
            for(int i=0;i<recipients.length;i++)
                recipients[i]=emails.get(i);
            emailService.sendMessageAboutCanceling(recipients,train,request);
        }

        train.setEnable(false);
        trainRepository.save(train);
    }
    public List<Train> findTrainsByStation(String station, int page){
        page--;
        return trainRepository.findTrainsByStationsContainingOrderByIdDesc(station,PageRequest.of(page,5));
    }
}
