package com.restapi.project.controller;

import com.restapi.project.dto.JobPositionDto;
import com.restapi.project.service.JobPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class JobPositionController {

    Logger logger = LoggerFactory.getLogger(JobPositionController.class);

    @Autowired
    private JobPositionService jobPositionService;

    @RequestMapping(value = "/positions", method = RequestMethod.GET) // TESTED
    @ResponseBody
    public ResponseEntity<List<JobPositionDto>> getAllPositions() {
        try {
            return new ResponseEntity<>(jobPositionService.getAllJobs(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.GET) // TESTED
    @ResponseBody
    public ResponseEntity<JobPositionDto> getPosition(@PathVariable("ID") Long id) {
        try {
            return new ResponseEntity<>(jobPositionService.getPosition(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/positions", method = RequestMethod.POST) // TESTED
    @ResponseBody
    public ResponseEntity<String> postPosition(@RequestBody JobPositionDto jobPositionDto) {
        try {
            jobPositionService.createPosition(jobPositionDto);
            return new ResponseEntity<>("{ \"message\" : \"Job position created succesfully\" } ", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.PATCH) // TESTED
    @ResponseBody
    public ResponseEntity<String> patchPosition(@RequestBody JobPositionDto jobPositionDto, @PathVariable("ID") Long id) {
        try {
            jobPositionService.patchPosition(jobPositionDto, id);
            return new ResponseEntity<>("{ \"message\" : \"Job position patched succesfully\" } ", HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("{ \"message\" : \"Job position not found\" } ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.DELETE) // TESTED
    @ResponseBody
    public ResponseEntity<String> deletePosition(@PathVariable("ID") Long id) {
        try {
            jobPositionService.removePosition(id);
            return new ResponseEntity<>("{ \"message\" : \"Position deleted succesfully\" } ", HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/positions/{ID}", method = RequestMethod.PUT) // TESTED
    @ResponseBody
    public ResponseEntity<String> putPosition(@PathVariable("ID") Long id, @RequestBody JobPositionDto jobPositionDto) {
        try {
            jobPositionService.replacePosition(jobPositionDto, id);
            return new ResponseEntity<>("{ \"message\" : \"Method \"PUT\" for job position completed successfully\" } ", HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
