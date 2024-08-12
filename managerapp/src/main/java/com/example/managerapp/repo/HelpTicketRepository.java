package com.example.managerapp.repo;
/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.model.HelpTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HelpTicketRepository extends MongoRepository<HelpTicket, String> {
    Optional<HelpTicket> findHelpTicketById(String id);
    Optional<HelpTicket> findHelpTicketByUser(String username);
    List<HelpTicket> findAllHelpTicketByUser(String username);
    // Custom query methods if needed
}
