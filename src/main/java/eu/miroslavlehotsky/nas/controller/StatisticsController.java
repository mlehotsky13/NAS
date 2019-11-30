package eu.miroslavlehotsky.nas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @GetMapping
    public String getStatisticsPage(Model model) {
        return "statistics";
    }
}
