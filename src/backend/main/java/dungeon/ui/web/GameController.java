package dungeon.ui.web;

import dungeon.engine.Coords;
import dungeon.engine.Game;
import dungeon.engine.GameEngine;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/test")
    @SendTo("/topic/messages")
    public Map<String, String> hello(Map<String, String> payload) {
        payload.get("message");
        return Map.of("message", "Hello World!");
    }

    @MessageMapping("/new_game")
    public void newGame(Map<String, String> payload) {
        String tmpId = payload.get("id");
        Game new_game = GameEngine.getInstance().newGame();
        Map<String, String> result = GameEngine.getInstance().getGameStats(new_game.getId());
        result.put("id", String.valueOf(new_game.getId()));

        String destination = "/topic/get-id/" + tmpId;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/change_ai")
    public void changeAi(Map<String, String> payload) {
        String id = payload.get("id");
        String type = payload.get("ai");
        Map<String, String> result = GameEngine.getInstance().changeAI(Integer.parseInt(id), type);

        String destination = "/topic/ai_changed/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/place_tile")
    public void placeTile(Map<String, String> payload) {
        String id = payload.get("id");
        String type = payload.get("tile_type");
        String col = payload.get("col");
        String row = payload.get("row");
        Map<String, String> result = GameEngine.getInstance().placeTile(Integer.parseInt(id), new Coords(Integer.parseInt(col), Integer.parseInt(row)), type);

        String destination = "/topic/tile_placed/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/is_simulation_ready")
    public void isSimulationReady(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().isSimulationReady(Integer.parseInt(id));

        String destination = "/topic/simulation_ready/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/launch_game")
    public void launchGame(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().startSimulation(Integer.parseInt(id));

        String destination = "/topic/game_launched/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/get_game_stats")
    public void getGameStats(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().getGameStats(Integer.parseInt(id));
        String destination = "/topic/send_game_stats/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/next_step")
    public void nextStep(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().nextTurn(Integer.parseInt(id));

        String destination = "/topic/step_result/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/is_game_terminated")
    public void isGameTerminated(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = Map.of("result", String.valueOf(GameEngine.getInstance().isGameTerminated(Integer.parseInt(id))));

        String destination = "/topic/game_launched/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/end_game")
    public void endGame(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().getGameStats(Integer.parseInt(id));
        GameEngine.getInstance().endGame(Integer.parseInt(id));

        String destination = "/topic/game_ended/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }

    @MessageMapping("/get_leaderboard")
    public void getLeaderBoard(Map<String, String> payload) {
        String id = payload.get("id");
        Map<String, String> result = GameEngine.getInstance().getLeaderBoardString();

        String destination = "/topic/leaderboard/" + id;
        messagingTemplate.convertAndSend(destination, result);
    }
}
