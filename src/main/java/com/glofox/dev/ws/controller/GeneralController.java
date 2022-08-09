package com.glofox.dev.ws.controller;

import com.glofox.dev.model.Book;
import com.glofox.dev.model.Class;
import com.glofox.dev.service.GeneralService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
public class GeneralController {

    @Autowired 
    private GeneralService generalService;

    @PostMapping("/classes")
    public ResponseEntity<String> saveOrder(@RequestBody Class aClass) {
        try {
            generalService.createClass(aClass);
            log.info("The class has been successfully created");
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Throwable e) {
            log.error(e);
            return new ResponseEntity<>("{\"success\":false, \"responseMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<String> updateOrder(@RequestBody Book book) {
        try {
            generalService.createBooking(book);
            log.info("The booking has been created successfully");
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Throwable e) {
            log.error(e);
            return new ResponseEntity<>("{\"success\":false, \"responseMessage\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
