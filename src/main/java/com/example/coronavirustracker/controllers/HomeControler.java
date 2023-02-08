package com.example.coronavirustracker.controllers;

import com.example.coronavirustracker.models.PlacesStats;
import com.example.coronavirustracker.services.CoronavirusData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeControler {
    @Autowired
    CoronavirusData coronavirusData;

    @GetMapping("/")
    public String home(Model model){
		List<PlacesStats> stats = coronavirusData.getStats();
		int totalCases = stats.stream().mapToInt(stat -> stat.getLatestRecord()).sum();
        int incresedCases = stats.stream().mapToInt(stat -> stat.getIncresedCases()).sum();

        model.addAttribute("PlacesStats", stats);
		model.addAttribute("totalCases", totalCases);
        model.addAttribute("incCases", incresedCases);
        return "home";
    }

    @PostMapping("/")
    public String newhome(@RequestParam String place, Model model){
        List<PlacesStats> stats = coronavirusData.getStats();
        int totalCases = stats.stream().mapToInt(PlacesStats::getLatestRecord).sum();
        int incresedCases = stats.stream().mapToInt(PlacesStats::getIncresedCases).sum();
        List<PlacesStats> statsFiltered = stats.stream()
                .filter(stat -> stat.getCountry().equalsIgnoreCase(place)).collect(Collectors.toList());
        System.out.println(stats);
        System.out.println(statsFiltered);
        model.addAttribute("PlacesStats", statsFiltered);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("incCases", incresedCases);
        return "home";
    }
}
