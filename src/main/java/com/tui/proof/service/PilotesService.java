package com.tui.proof.service;

import com.tui.proof.model.PilotesOrder;

import java.util.List;

public interface PilotesService {
    PilotesOrder createPilotesOrder(PilotesOrder pilotesOrder) throws Exception;

    List<PilotesOrder> searchOrdersByCustomerData(Integer clientId) throws Exception;

    PilotesOrder updatePilotesOrder(PilotesOrder pilotesOrder,
                                    Integer orderNumber) throws Exception;
}
