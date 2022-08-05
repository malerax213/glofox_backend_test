package com.tui.proof.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.model.PilotesOrder;
import com.tui.proof.service.PilotesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
public class PilotesController {

    @Autowired 
    private PilotesService pilotesService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/orders")
    public ResponseEntity<String> saveOrder(@RequestBody PilotesOrder pilotesOrder) {
        try {
            pilotesService.createPilotesOrder(pilotesOrder);
            log.info("The order has been successfully created");
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Throwable e) {
            log.error(e);
            return new ResponseEntity<>("{\"success\":false, \"responseMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<String> updateOrder(@RequestBody PilotesOrder pilotesOrder, @PathVariable("id") Integer orderId) {
        try {
            pilotesService.updatePilotesOrder(pilotesOrder, orderId);
            log.info("The update to the order has been done successfully");
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Throwable e) {
            log.error(e);
            return new ResponseEntity<>("{\"success\":false, \"responseMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<String> fetchOrderList(@PathVariable("id") Integer clientId) {
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(pilotesService.searchOrdersByCustomerData(clientId)), HttpStatus.OK);
        } catch (Throwable e) {
            log.error(e);
            return new ResponseEntity<>("{\"success\":false, \"responseMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
