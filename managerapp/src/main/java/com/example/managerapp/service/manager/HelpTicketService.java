package com.example.managerapp.service.manager;

import com.example.managerapp.model.AssignMessage;
import com.example.managerapp.model.HelpTicket;
import com.example.managerapp.model.MessageSentEvent;
import com.example.managerapp.repo.HelpTicketRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/
@Service
@AllArgsConstructor
//@NoArgsConstructor
public class HelpTicketService {

    private final HelpTicketRepository helpTicketRepository;

    public Optional<HelpTicket> getTicketByUser(String username){
        return helpTicketRepository.findHelpTicketByUser(username);
    }

    public Optional<HelpTicket> getTicketByTicketId(String id){
        return helpTicketRepository.findHelpTicketById(id);
    }

    public void assignManager(AssignMessage message){
        HelpTicket ticket = helpTicketRepository.findHelpTicketById(message.getTicketId()).get();
        ticket.setManager(message.getAssignTo());
        helpTicketRepository.save(ticket);

    }

    public HelpTicket createTicket(String user) {
        HelpTicket ticket = new HelpTicket();
        ticket.setUser(user);
        ticket.setSolved(false);
        return helpTicketRepository.save(ticket);
    }

    public List<HelpTicket> getAllTickets(){
        return helpTicketRepository.findAll();
    }

    public void addMessage(String ticketId, MessageSentEvent message) {
        HelpTicket ticket = helpTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.getChatList().add(message);
        helpTicketRepository.save(ticket);
    }

    public List<MessageSentEvent> getMessages(String ticketId) {
        HelpTicket ticket = helpTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticket.getChatList();
    }
}
