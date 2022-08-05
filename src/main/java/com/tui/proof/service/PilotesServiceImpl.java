package com.tui.proof.service;

import com.tui.proof.model.Address;
import com.tui.proof.model.Client;
import com.tui.proof.model.PilotesOrder;
import com.tui.proof.repository.AddressRepository;
import com.tui.proof.repository.ClientRepository;
import com.tui.proof.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;

@Service
@Log4j2
public class PilotesServiceImpl implements PilotesService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ClientRepository clientRepository;
    private int[] allowedPilotes = {5, 10, 15};
    private double pilotesPrice = 1.33;
    private double allowedUpdateTimeInMinutes = 5;

    @Override
    public PilotesOrder createPilotesOrder(PilotesOrder pilotesOrder) throws Exception{
        if(orderIsValidCheck(pilotesOrder)) {
            // If the client already exists
            Client c = clientRepository.findByTelephone(pilotesOrder.getClient().getTelephone());
            if(c != null) {
                pilotesOrder.setClient(c);
            }

            // If the address already exists
            Address a = addressRepository.findByStreet(pilotesOrder.getAddress().getStreet());
            if(a != null) {
                pilotesOrder.setAddress(a);
            }
            pilotesOrder.setOrderTotal(pilotesOrder.getPilotes() * pilotesPrice);
            pilotesOrder.setCreationTime(LocalDateTime.now());
            return orderRepository.save(pilotesOrder);
        } else {
            throw new Exception("There was a problem creating the PilotesOrder");
        }
    }

    @Override
    public PilotesOrder updatePilotesOrder(PilotesOrder pilotesOrder, Integer orderNumber) throws Exception{
        PilotesOrder pOrder;
        if (orderRepository.findById(orderNumber).isPresent()) {
            pOrder = orderRepository.findById(orderNumber).get();
        }else{
            throw new Exception("The given pilotesOrder number is not present in the DB");
        }

        Client c = clientRepository.findById(pOrder.getClient().getId()).get();
        Address a = addressRepository.findById(pOrder.getAddress().getId()).get();

        if(Duration.between(pOrder.getCreationTime(), LocalDateTime.now()).toMinutes() < allowedUpdateTimeInMinutes) {
            if (orderIsValidCheck(pilotesOrder)) {
                    pilotesOrder.setNumber(orderNumber);
                    pilotesOrder.setOrderTotal(pilotesOrder.getPilotes() * pilotesPrice);
                    pilotesOrder.setCreationTime(LocalDateTime.now());
                    pilotesOrder.getClient().setId(c.getId());
                    pilotesOrder.getAddress().setId(a.getId());
                    return orderRepository.save(pilotesOrder);
            } else {
                throw new Exception("There was a problem creating the PilotesOrder");
            }
        } else {
            throw new Exception("The update is only allowed the first 5 min after the creation of an pilotesOrder");
        }
    }

    @Override
    public List<PilotesOrder> searchOrdersByCustomerData(Integer clientId) throws Exception{
        List<PilotesOrder> orders;
        if (clientRepository.findById(clientId).isPresent()) {
            Client c = clientRepository.findById(clientId).get();

            orders = orderRepository.findAllByClient(c);
        }else{
            throw new Exception("The given client id is not present in the DB");
        }
        return orders;
    }

    private boolean orderIsValidCheck(PilotesOrder pilotesOrder) {
        if (!ArrayUtils.contains(allowedPilotes, pilotesOrder.getPilotes())) {
            log.error("The amount of pilotes is not correct. It can only have 5, 10 or 15 pilotes");
            return false;
        }
        //Address foundAddress = addressRepository.findByAddressId(pilotesOrder.getAddressId());
        //Client foundClient = clientRepository.findByClientId(pilotesOrder.getClientId());

        if (!addressIsValidCheck(pilotesOrder.getAddress()) || !clientIsValidCheck(pilotesOrder.getClient())) {
            log.error("The format of the address of the client or the client info is not correct");
            return false;
        }
        return true;
    }

    private boolean addressIsValidCheck(Address address){
        // Check if the City, the Country, the Street or the PostCode are null
        if("".equalsIgnoreCase(address.getCity()) || "".equalsIgnoreCase(address.getCountry()) ||
                "".equalsIgnoreCase(address.getStreet()) || "".equalsIgnoreCase(address.getPostcode())){

            return false;
        }
        // Check if the PostCode is integer
        try {
            Integer d = Integer.parseInt(address.getPostcode());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean clientIsValidCheck(Client client){
        // Check if the First Name, the Last Name or the telephone are null
        if("".equalsIgnoreCase(client.getFirstName()) || "".equalsIgnoreCase(client.getLastName()) ||
                "".equalsIgnoreCase(client.getTelephone())){
            return false;
        }

        // Check if the telephone is integer
        try {
            Integer d = Integer.parseInt(client.getTelephone());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
