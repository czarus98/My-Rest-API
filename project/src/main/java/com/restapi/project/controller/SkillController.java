package com.restapi.project.controller;

import com.restapi.project.dto.SkillDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class SkillController {
    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllSkills() {
        return null; // TO DO
    }

    @RequestMapping(value = "/skills/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getSkill(@PathVariable("ID") Long id) {
        return null; // TO DO
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
