package com.restapi.project.controller;

import com.restapi.project.dto.SkillDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.service.SkillService;
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
public class SkillController {

    Logger logger = LoggerFactory.getLogger(SkillController.class);
    @Autowired
    private SkillService skillService;

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<SkillDto>> getAllSkills() {
        try {
            return new ResponseEntity<>(skillService.getAllSkills(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/skills/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<SkillDto> getSkill(@PathVariable("ID") Long id) {
        try {
            return new ResponseEntity<>(skillService.getSkill(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/skills", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> postSkill(@RequestBody SkillDto skillDto) {
        try {
            skillService.createSkill(skillDto);
            return new ResponseEntity<>("{ \"message\" : \"Skill created succesfully\" } ", HttpStatus.OK);
        } catch (ResourceAlreadyExistsError alreadyExistsError) {
            alreadyExistsError.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/skills/{ID}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteSkill(@PathVariable("ID") Long id) {
        try {
            skillService.removeSkill(id);
            return new ResponseEntity<>("{ \"message\" : \"Skill deleted succesfully\" } ", HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<>("{ \"message\" : \"Skill not found\" } ", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/skills/{ID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> putSkill(@PathVariable("ID") Long id, @RequestBody SkillDto skillDto) {
        return null; // TO DO
    }
}
