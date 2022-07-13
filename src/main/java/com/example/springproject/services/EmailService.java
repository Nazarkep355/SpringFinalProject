package com.example.springproject.services;

import com.example.springproject.entity.Train;
import com.example.springproject.entity.User;
import com.example.springproject.utility.TrainUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;


@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    UtilityService utilityService;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("TrainEmailer@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    public void sendSimpleMessage(
            String[] to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("TrainEmailer@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    public void sendMessageAboutCanceling(String[] recipients, Train train, HttpServletRequest request) {
        ResourceBundle bundle = utilityService.getLocaleBundle(request);
        StringBuilder message = new StringBuilder();
        message.append(bundle.getString("SorryTrain"));
        message.append(TrainUtility.fromTo(train)).append(TrainUtility.dateToString(train.getAgenda().get(0))).append(bundle.getString("CostWasReturned"));
        sendSimpleMessage(recipients, bundle.getString("TrainCanceled"), message.toString());
    }

    public void sendMessageAboutTicketBuying(Train train, User user, HttpServletRequest request) {
        ResourceBundle bundle = utilityService.getLocaleBundle(request);
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(bundle.getString("Hello"));
        messageBuilder.append(user.getName()).append(",");
        messageBuilder.append(bundle.getString("youJustBooked"));
        messageBuilder.append(TrainUtility.fromTo(train)).append(". ");
        messageBuilder.append(bundle.getString("HeIsComingOn"));
        messageBuilder.append(bundle.getString("YouWatchSchedule"));
        sendSimpleMessage(user.getEmail(), bundle.getString("TicketBought"), messageBuilder.toString());
    }

}
