package dungeon.ui.web;

import dungeon.engine.Coords;
import dungeon.engine.GameEngine;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;


import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Controller
public class GameController {

    @MessageMapping("/test")
    @SendTo("/topic/messages")
    public Map<String, String> hello(Map<String, String> payload) {
        String message = payload.get("message");
        return Map.of("message", "Hello World!");
    }

    @MessageMapping("/place_tile")
    @SendTo("/topic/tile_placed")
    public Map<String, String> place_tile(Map<String, String> payload) {
        String id = payload.get("id");
        String type = payload.get("tile_type");
        String col = payload.get("col");
        String row = payload.get("row");
        Map<String, String> result = GameEngine.getInstance().placeTile(Integer.parseInt(id), new Coords(Integer.parseInt(col), Integer.parseInt(row)), type);
        return result;
    }

    @MessageMapping("/launch_game")
    @SendTo("/topic/game_launched")
    public Map<String, String> launch_game(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().launchGame(Integer.parseInt(id));
        return result;
    }

    @MessageMapping("/next_step")
    @SendTo("/topic/step_result")
    public Map<String, String> next_step(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().nextTurn(Integer.parseInt(id));
        return result;
    }

    @MessageMapping("/change_ai")
    @SendTo("/topic/ai_changed")
    public Map<String, String> change_ai(Map<String, String> payload) {
        String id = payload.get("id");
        String type = payload.get("ai");
        Map<String, String> result = GameEngine.getInstance().changeAI(Integer.parseInt(id), type);
        return result;
    }
}
