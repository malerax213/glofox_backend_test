package com.tui.proof.repository;

import com.tui.proof.model.Client;
import com.tui.proof.model.PilotesOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepository
    extends CrudRepository<PilotesOrder, Integer> {

    List<PilotesOrder> findAllByClient(Client client);
}
