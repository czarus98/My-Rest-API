package com.restapi.project.controller;

import com.restapi.project.dto.CandidateDto;
import com.restapi.project.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class CandidateController {

    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllCandidates(@RequestBody LoginDto loginDto) {
        return null; // TO DO
    }

    @RequestMapping(value = "/candidates/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getCandidate(@PathVariable("ID") Long id) {
        return null; // TO DO
    }

    @RequestMapping(value = "/candidate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getCandidate(@RequestBody CandidateDto candidateDto) {
        return null; // TO DO
    }
}
