package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.SaveManager;
import dungeon.engine.GameEngine;
import dungeon.engine.Observers.ScoreManager;
import dungeon.engine.tiles.traps.Mine;
import dungeon.engine.tiles.traps.PoisonTrap;
import dungeon.engine.tiles.traps.WallTrap;
import dungeon.engine.tiles.wall.StoneWall;
import dungeon.engine.tiles.wall.WoodWall;
import dungeon.engine.Game;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;


public class TerminalLauncher {
    public static void main(String[] args) {
        gameGenerator(0,0);
    }

    public static void gameGenerator(int edit, int game_id_load){
        Scanner scanner = new Scanner(System.in);
        Game game;
        if (edit == 0){
            game = GameEngine.getInstance().newGame();
            System.out.println("============== Welcome in the Dungeon ! ==============");
            System.out.println("\n");
            System.out.println("Heroes' strategy :");
            System.out.println("   [1] BFS");
            System.out.println("   [2] A*");
            System.out.println("\n");
            
            int strategy = 0;
            int strategy_AI = 0;
            while (strategy == 0) {
                System.out.print("Give the indicated AI strategy : ");
                String strat = scanner.next();
                try {
                    strategy_AI = Integer.parseInt(strat);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre entre 1 et 2 !");
                    continue;
                }
                if (strategy_AI > 0 && strategy_AI < 3){
                    strategy = 1;
                } else {
                    System.out.println("Veuillez entrer un nombre entre 1 et 2 !");
                }
            }


            System.out.println("\n");
            System.out.print("Thank you !");
            System.out.println("\n");
        } else {
            game = GameEngine.getInstance().getGame(game_id_load);
            System.out.println("Your game is ready ! ");
        }
        
        int startingPointPassage = 0;
        int treasurePointPassage = 0;
        ScoreManager scoreManager = new ScoreManager();
        String legendString = legendString();
        scoreManager.setScore(0);

        int size_grid = game.getGrid().getSize();
        List<String> legend = List.of("S","T","#", "@", "W", "M","P");
        List<String> name_cases = List.of("0","1","2","3","4","5","6","7","8","9");
        System.out.println("============== Welcome in the Dungeon ! ==============");
        System.out.println("\n");
        System.out.println("Heroes' strategy :");
        System.out.println("   [1] BFS");
        System.out.println("   [2] A*");
        System.out.println("\n");
        
        int strategy = 0;
        int strategy_AI = 0;
        while (strategy == 0) {
            System.out.print("Give the indicated AI strategy : ");
            String strat = scanner.next();
            try {
                strategy_AI = Integer.parseInt(strat);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entre 1 et 2 !");
                continue;
            }
            if (strategy_AI > 0 && strategy_AI < 3){
                strategy = 1;
            } else {
                System.out.println("Veuillez entrer un nombre entre 1 et 2 !");
            }
        }


        System.out.println("\n");
        System.out.print("Thank you !");
        System.out.println("\n");

        

        PrintGrid.make_action(game,size_grid, legendString);

        int action_player = 3;
        int S_x = -1;
        int S_y = -1;


        CoordsWrapper startingPoint = EditGame.editGame(game, scanner, startingPointPassage, treasurePointPassage, name_cases, legend, size_grid, legendString, action_player,S_x,S_y);

        S_x = startingPoint.x;
        S_x = startingPoint.y;
        switch (action_player) {
            case 5:
                System.out.println("================= Heroes are here ! =================");
                ExecuteGame.execute_game(game,size_grid, new Coords(S_x,S_y), scoreManager, legendString, strategy_AI);
                
                int action = 0;
                int action_player_game = 1;
                while (action == 0) {
                    PrintGrid.manage_end_game();
                    String input = scanner.next();
                    try {
                        action_player_game = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Veuillez entrer un nombre entre 1 et 5 !");
                        continue;
                    }
                    if (action_player_game > 0 && action_player_game < 6){
                        action = 1;
                    }
                }
                switch (action_player_game) {
                    case 1:
                        gameGenerator(0,0);
                        break;
                    case 2:
                        gameGenerator(1,game.getId());
                        break;
                    case 3:
                        try {
                            SaveManager.save(game);
                        } catch (IOException e) {
                            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
                        }
                        System.out.println("I save your game !");
                        break;
                    case 4: // TODO: Leaderboard
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }
                break;
            case 6:
                System.out.println("This is the end !");
                break;
            default:
                break;
        }
        
        scanner.close();
    }
    

    public static String getTypeObject(String action_object){
        switch (action_object) {
            case "S":
                return "startingpoint";
            case "T":
                return "treasure";
            case "#":
                return "stonewall";
            case "@":
                return "woodwall";
            case "W":
                return "walltrap";
            case "M":
                return "mine";
            case "P":
                return "poisontrap";
            default:
                return "empty";
        }
    }
    
    public static String legendString(){
        String legend = "Legend : S = Starting Point, T = Treasure, E = Hero, . = Empty tile, # = Stone Wall - " + (new StoneWall(null)).getPlacementCost() + ", @ = Wood Wall - " + (new WoodWall(null)).getPlacementCost() + ", W = Wall Trap - " + (new WallTrap(null)).getPlacementCost() + ", M = Mine - " + (new Mine(null)).getPlacementCost() + ", P = Poison Trap - " + (new PoisonTrap(null)).getPlacementCost();
        return legend;
    }
}
