package com.restapi.project.controller;

import com.restapi.project.dto.CandidateDto;
import com.restapi.project.service.CandidateServiceImpl;
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
public class CandidateController {
    Logger logger = LoggerFactory.getLogger(CandidateController.class);
    @Autowired
    private CandidateServiceImpl candidateService;

    @RequestMapping(value = "/candidates", method = RequestMethod.GET) // TESTED
    @ResponseBody
    public ResponseEntity<List<CandidateDto>> getAllCandidates() {
        try {
            return new ResponseEntity<>(candidateService.getAllCandidates(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/candidates/{ID}", method = RequestMethod.GET) // TESTED
    @ResponseBody
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable("ID") Long id) {
        try {
            return new ResponseEntity<>(candidateService.getCandidate(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/candidates", method = RequestMethod.POST) // TESTED
    @ResponseBody
    public ResponseEntity<String> createCandidate(@RequestBody CandidateDto candidateDto) {
        try {
            candidateService.createCandidate(candidateDto);
            return new ResponseEntity<>("{ \"message\" : \"Candidate created succesfully\" } ", HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/candidates/{ID}", method = RequestMethod.PUT) // TESTED
    @ResponseBody
    public ResponseEntity<String> updateCandidate(@PathVariable("ID") Long id, @RequestBody CandidateDto newCandidate) {
        try {
            candidateService.putCandidate(newCandidate, id);
            return new ResponseEntity<>("{ \"message\" : \"Method \"PUT\" completed successfully\" } ", HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/candidates/{ID}", method = RequestMethod.DELETE) // TESTED
    @ResponseBody
    public ResponseEntity<String> deleteCandidate(@PathVariable("ID") Long id) {
        try {
            candidateService.removeCandidate(id);
            return new ResponseEntity<>("{ \"message\" : \"Candidate deleted succesfully\" } ", HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
