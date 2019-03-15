package fr.miage.matthieu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableHystrixDashboard
@Controller
public class MonitorServiceApplication {

    @RequestMapping("/")
    public String home() {
        return "forward:/hystrix/index.html";
    }

    public static void main(String[] args) {
        SpringApplication.run(MonitorServiceApplication.class, args);
    }
}
