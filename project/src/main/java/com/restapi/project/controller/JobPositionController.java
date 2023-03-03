package com.restapi.project.controller;

import com.restapi.project.dto.JobPositionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class JobPositionController {

    @RequestMapping(value = "/positions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllPositions() {
        return null; // TO DO
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getPosition(@PathVariable("ID") Long id) {
        return null; // TO DO
    }

    @RequestMapping(value = "/positions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createPosition(@RequestBody JobPositionDto jobPositionDto) {
        return null; // TO DO
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> removePosition(@PathVariable("ID") Long id) {
        return null; // TO DO
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> updatePosition(@PathVariable("ID") Long id, @RequestBody JobPositionDto jobPositionDto) {
        return null; // TO DO
    }
}
