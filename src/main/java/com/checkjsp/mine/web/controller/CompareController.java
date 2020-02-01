package com.checkjsp.mine.web.controller;

import com.checkjsp.mine.web.model.Document;
import com.checkjsp.mine.web.model.Skill;
import com.checkjsp.mine.web.model.SkillWord;
import com.checkjsp.mine.web.service.DocService;
import com.checkjsp.mine.web.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("name")
public class CompareController {

    @Autowired
    SkillService skillService;

    @Autowired
    DocService docService;

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public String showDocs(ModelMap model) {
        String user = getLoggedInUserName();
        List<Skill> skills = skillService.retrieveSkills(user);
        model.put("skills", skills);
        model.put("docs", docService.coloredDocs(user, skills));
        model.addAttribute("skill", new Skill(0, user, "add new skill"));
        model.addAttribute("document", new Document(0, user, "add header", "add doc text", new ArrayList<SkillWord>()));
        return "compare";
    }

    private String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    @RequestMapping(value = "/delete-skill", method = RequestMethod.GET)
    public String deleteSkill(@RequestParam int id) {
        String user = getLoggedInUserName();
        skillService.deleteSkill(user, id);
        return "redirect:/compare";
    }

    @RequestMapping(value = "/clear-skills", method = RequestMethod.GET)
    public String clearSkill() {
        String user = getLoggedInUserName();
        skillService.clearSkills(user);
        return "redirect:/compare";
    }

    @RequestMapping(value = "/delete-document", method = RequestMethod.GET)
    public String deleteDocument(@RequestParam int id) {
        String user = getLoggedInUserName();
        docService.deleteDocument(user, id);
        return "redirect:/compare";
    }

    @RequestMapping(value = "/find-skills-in-document", method = RequestMethod.GET)
    public String findSkillsInDocument(@RequestParam int id) {
        docService.parseDocumentSkills(id, skillService.retrieveAllSkills());
        System.err.println("parseDocumentSkills  ");
        return "redirect:/compare";
    }


    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public String checkForms(@ModelAttribute(value = "skill") @Valid Skill skill, BindingResult skillResult,
                             @ModelAttribute(value = "document") @Valid Document document, BindingResult docResult
    ) {
        String user = getLoggedInUserName();
        if (skillResult.hasErrors() && docResult.hasErrors()) {
            for (ObjectError err : skillResult.getAllErrors()) {
                System.err.println("skillResult error " + err.toString());
            }
            for (ObjectError err : docResult.getAllErrors()) {
                System.err.println("docResult error " + err.toString());
            }
            return "error";
        }
        if (document != null && document.getText() != null && !document.getText().equals("add doc text")) {
            docService.addDocument(user, document.getHeader(), document.getText());
        }

        if (skill != null && skill.getName() != null && !skill.getName().equals("add new skill")) {
            if (skill.getName().contains(",")) {
                String[] skills = skill.getName().split(",");
                for (String skillName : skills) {
                    skillService.addSkill(user, skillName.trim());
                }
            } else {
                skillService.addSkill(user, skill.getName());
            }
        }

        return "redirect:/compare";
    }
}