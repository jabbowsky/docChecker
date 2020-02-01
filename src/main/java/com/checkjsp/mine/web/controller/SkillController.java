package com.checkjsp.mine.web.controller;

import com.checkjsp.mine.web.model.SkillWord;
import com.checkjsp.mine.web.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("name")
public class SkillController {
    @Autowired
    SkillService service;

    @RequestMapping(value="/all-skills", method = RequestMethod.GET)
    public String showAllSkills(ModelMap model){
        List<SkillWord> skillWords = service.retrieveAllSkills();
        model.put("skillWords", skillWords );
        return "skills";
    }

    @RequestMapping(value="/refresh-skills", method = RequestMethod.GET)
    public String refreshAllSkills(ModelMap model){
        try {
            service.refreshAllSkilles();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<SkillWord> skillWords = service.retrieveAllSkills();
        model.put("skillWords", skillWords );
        return "redirect:/all-skills";
    }

    @RequestMapping(value = "/save-skills", method = RequestMethod.GET)
    public String writeToDatabase() {
        System.out.println("writeToDatabase");
        try {
            service.wirteToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/all-skills";
    }

    @RequestMapping(value = "/delete-skill-perm", method = RequestMethod.GET)
    public String deleteDocument(@RequestParam int id) {
        String user = getLoggedInUserName();
        try {
            service.deleteSkillPerm(user,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/all-skills";
    }

    private String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }
}
