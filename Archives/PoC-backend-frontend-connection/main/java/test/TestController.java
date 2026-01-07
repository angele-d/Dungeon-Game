package test;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;


import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Controller
public class TestController {

    @MessageMapping("/test")
    @SendTo("/topic/messages")
    public Map<String, String> hello(Map<String, String> payload) {
        String message = payload.get("message");
        return Map.of("message", "Hello World!");
    }
}