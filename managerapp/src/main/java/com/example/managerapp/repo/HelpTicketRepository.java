package com.example.managerapp.repo;
/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.model.HelpTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HelpTicketRepository extends MongoRepository<HelpTicket, String> {
    Optional<HelpTicket> findHelpTicketById(String id);
    public Optional<HelpTicket> findHelpTicketByUser(String username);
    // Custom query methods if needed
}
