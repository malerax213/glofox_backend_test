package com.tui.proof.repository;

import com.tui.proof.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository
    extends CrudRepository<Client, Integer> {

    Client findByTelephone(String telephone);
}
