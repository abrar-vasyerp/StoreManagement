package org.abrar.storemanagement.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class LogController {

    @GetMapping("/logs")
    public String viewLogs(Model model) {

        List<String> logs = readLastLogs(); // mock for now
        model.addAttribute("logs", logs);

        return "admin/logs"; // maps to logs.jsp
    }

    private List<String> readLastLogs() {
        return List.of(
                "2026-02-04 INFO  Application started",
                "2026-02-04 WARN  Disk usage at 80%",
                "2026-02-04 ERROR Database connection timeout"
        );
    }
}
