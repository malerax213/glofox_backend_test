package com.tui.proof.repository;

import com.tui.proof.model.Address;
import com.tui.proof.model.PilotesOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository
    extends CrudRepository<Address, Integer> {

    Address findByStreet(String street);

}
