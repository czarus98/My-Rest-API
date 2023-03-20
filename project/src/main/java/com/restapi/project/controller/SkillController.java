package com.restapi.project.controller;

import com.restapi.project.dto.SkillDto;
import com.restapi.project.model.Skill;
import com.restapi.project.service.JobPositionService;
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
    public ResponseEntity<String> createPosition(@RequestBody SkillDto skillDto) {
        return null; // TO DO
    }

    @RequestMapping(value = "/skills/{ID}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> removePosition(@PathVariable("ID") Long id) {
        return null; // TO DO
    }

    @RequestMapping(value = "/skills/{ID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> updatePosition(@PathVariable("ID") Long id, @RequestBody SkillDto skillDto) {
        return null; // TO DO
    }
}
