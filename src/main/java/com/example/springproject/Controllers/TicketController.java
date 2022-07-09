package com.example.springproject.Controllers;

import com.example.springproject.Entity.Ticket;
import com.example.springproject.Entity.User;
import com.example.springproject.Services.TicketService;
import com.example.springproject.Services.UserService;
import com.example.springproject.Services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UtilityService utilityService;
@GetMapping("/all")
    public String usersTickets(Model model, HttpServletRequest request, int page){
    utilityService.addUserInformation(model,request);
    utilityService.setStrings(model,request);
    List<Ticket> ticketList = ticketService.getPaginatedTicketList(request.getRemoteUser(),page);
    ticketService.addInfoAboutTicketsToModel(model,ticketList);
    model.addAttribute("currentPage",page);
    return "Tickets";
}
//@RequestMapping(value = "/change", method = { RequestMethod.POST, RequestMethod.GET })
//    public String change(HttpServletRequest request){
//   Train train = (Train) request.getSession().getAttribute("train");
//    Ticket ticket = (Ticket) request.getSession().getAttribute("ticket");
//    User user = (User) request.getSession().getAttribute("userToChange");
//    ticketService.saveChangesAboutBuying(train,user,ticket,request);
//    request.getSession().setAttribute("train",null);
//    request.getSession().setAttribute("ticket",null);
//    request.getSession().setAttribute("userToChange",null);
//    return "redirect:/tickets/all?page=1";
//}
@PostMapping("/create")
public String buyTicket(HttpServletRequest request, Long train, String station1, String station2){
        Ticket ticket = ticketService.createTicketFromForm(train,station1,station2);
        User user = userService.getUserByEmail(request.getRemoteUser());
       try{ ticketService.BuyTicket(ticket,user);
           ticketService.sendEmailAboutBuying(train,request.getRemoteUser(),request);
    }catch (IllegalArgumentException e)
       {request.getSession().setAttribute("error",e.getMessage());
       return "redirect:/trains/"+ train+((station1!=null)?"?station1="+station1+"&station2="+station2:"");
       }
        return "redirect:/tickets/all?page=1";
}

}
