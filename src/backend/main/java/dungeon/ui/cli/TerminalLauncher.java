package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.SaveManager;
import dungeon.engine.GameEngine;
import dungeon.engine.Observers.ScoreManager;
import dungeon.engine.tiles.traps.Mine;
import dungeon.engine.tiles.traps.WallTrap;
import dungeon.engine.tiles.wall.StoneWall;
import dungeon.engine.tiles.wall.WoodWall;
import dungeon.engine.Game;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;


public class TerminalLauncher {
    public static void main(String[] args) {
        gameGenerator();
    }
    
    public static void gameGenerator(){
        Scanner scanner = new Scanner(System.in);
        Game game = GameEngine.getInstance().newGame();
        int ID = game.getId();
        int startingPointPassage = 0;
        int treasurePointPassage = 0;
        ScoreManager scoreManager = new ScoreManager();
        String legendString = legendString();
        scoreManager.setScore(0);

        int size_grid = game.getGrid().getSize();
        List<String> legend = List.of("S","T","#", "@", "W", "M");
        List<String> name_cases = List.of("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E");
        System.out.println("============== Welcome in the Dungeon ! ==============");
        System.out.println("\n");
        System.out.println("Heroes' strategy :");
        System.out.println("   [1] DFS");
        System.out.println("   [2] BFS");
        System.out.println("   [3] A*");
        System.out.println("\n");
        
        int strategy = 0;
        int strategy_AI = 0;
        while (strategy == 0) {
            System.out.print("Give the indicated AI strategy : ");
            String strat = scanner.next();
            try {
                strategy_AI = Integer.parseInt(strat);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre entre 1 et 3 !");
                continue;
            }
            if (strategy_AI > 0 && strategy_AI < 4){
                strategy = 1;
            }
        }

        GameEngine.getInstance().changeAI(ID, getTypeAI(strategy_AI));

        System.out.println("\n");
        System.out.print("Thank you !");
        System.out.println("\n");

        int end_action = 0;
        int action_player = 3;

        PrintGrid.make_action(game,size_grid, legendString);

        int S_x = -1;
        int S_y = -1;
        int T_x = -1;
        int T_y = -1;

        while (end_action == 0){

            game = GameEngine.getInstance().getGame(game.getId());
            int action = 0;
            while (action == 0) {
                System.out.print("Do an action : ");
                String input = scanner.next();
                try {
                    action_player = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre entre 1 et 6 !");
                    continue;
                }
                if (action_player > 0 && action_player < 7){
                    action = 1;
                }
            }

            System.out.println("\n");
            System.out.print("Thank you !");
            System.out.println("\n");

            switch (action_player) {
                case 1:
                    int choice = 0;
                    int choice_coord = 0;
                    String action_object = "";
    
                    while (choice == 0) {
                        System.out.print("What do you want to place ? ");
                        action_object = scanner.next();
                        if (legend.contains(action_object)){
                            if (!(startingPointPassage == 1 && action_object.equals("S")) && !(treasurePointPassage == 1 && action_object.equals("T"))){
                                if(action_object.equals("S")){
                                    startingPointPassage = 1;
                                }
                                if(action_object.equals("T")){
                                    treasurePointPassage = 1;
                                }
                                choice = 1;
                            } else {
                                System.out.print("You cannot place two treasures or two starting points...");
                            }
                            
                        }
                    }
                    String pos_object_x = "";
                    String pos_object_y = "";
                    while (choice_coord == 0) {
                        System.out.print("Where do you want to place ? ");
                        pos_object_x = scanner.next();
                        pos_object_y = scanner.next();
                        if (name_cases.contains(pos_object_x) && name_cases.contains(pos_object_y)){
                            choice_coord = 1;
                            pos_object_x = convertButton(pos_object_x);
                            pos_object_y = convertButton(pos_object_y);
                        }
                        
                    }
                    if(action_object.equals("S")){
                        S_x = Integer.parseInt(pos_object_x);
                        S_y = Integer.parseInt(pos_object_y);
                    }
                    if(action_object.equals("T")){
                        T_x = Integer.parseInt(pos_object_x);
                        T_y = Integer.parseInt(pos_object_y);
                    }
                    if((new Coords(S_x,S_y)).equals(new Coords(Integer.parseInt(pos_object_x),Integer.parseInt(pos_object_y))) && !(action_object.equals("S"))){
                        startingPointPassage = 0;
                    }
                    if((new Coords(T_x,T_y)).equals(new Coords(Integer.parseInt(pos_object_x),Integer.parseInt(pos_object_y))) && !(action_object.equals("T"))){
                        treasurePointPassage = 0;
                    }
                    
                    
                    Coords coordTile = new Coords(Integer.parseInt(pos_object_x),Integer.parseInt(pos_object_y));
                    GameEngine.getInstance().placeTile(ID, coordTile , getTypeObject(action_object));
                    game = GameEngine.getInstance().getGame(game.getId());
                    game.placementOnGrid(game.getGrid().getTile(coordTile));
                    
                    PrintGrid.make_action(game, size_grid, legendString);
                    System.out.println("\n");
                    System.out.println("Coin : " + game.getMoney());
                    break;
                case 2:
                    int choice_delete = 0;
                    String pos_object_delete_x = "";
                    String pos_object_delete_y = "";
                    while (choice_delete == 0) {
                        System.out.print("Where do you want to delete ? ");
                        pos_object_delete_x = scanner.next();
                        pos_object_delete_y = scanner.next();
                        if (name_cases.contains(pos_object_delete_x) && name_cases.contains(pos_object_delete_y)){
                            choice_delete = 1;
                            pos_object_delete_x = convertButton(pos_object_delete_x);
                            pos_object_delete_y = convertButton(pos_object_delete_y);
                        }
                    }
                    
                    Coords coordTileDelete = new Coords(Integer.parseInt(pos_object_delete_x),Integer.parseInt(pos_object_delete_y));
                    GameEngine.getInstance().placeTile(ID, coordTileDelete , "empty");
                    game = GameEngine.getInstance().getGame(game.getId());
                    game.placementOnGrid(game.getGrid().getTile(coordTileDelete));
                    
                    PrintGrid.make_action(game, size_grid, legendString);
                    System.out.println("\n");
                    System.out.println("Coin : " + game.getMoney());
                    break;
                case 3:
                    try {
                        SaveManager.save(game);
                    } catch (IOException e) {
                        System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
                    }
                    System.out.println("I save your game !");
                    break;
                case 4:
                    // TODO : Check if the game exists and load the game
                    // load(game, )
                    System.out.println("Your game is ready ! ");
                    break;
                case 5:
                    if (startingPointPassage > 0 && treasurePointPassage > 0){
                        if (game.isTerminated()){
                            end_action = 1;
                        } else {
                            System.out.println("At least one accessible path is needed...");
                        }
                        
                    } else {
                        System.out.print("You must have a starting point and a treasure...");
                    }
                    
                    break; 
                case 6:
                    System.out.print("Would you save your game ? (yes/no) ");
                    String save_game = scanner.next();
                    if (save_game.equals("yes")) {
                        try {
                            SaveManager.save(game);
                            System.out.println("I save your game !");
                            end_action = 1; 
                        } catch (IOException e) {
                            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
                        }
                    } else {
                        end_action = 1; 
                    }
                    break;       
                default:
                    break;
            } 
        }

        switch (action_player) {
            case 5:
                System.out.println("================= Heroes are here ! =================");
                ExecuteGame.execute_game(game,size_grid, new Coords(S_x,S_y), scoreManager, legendString);
                // TODO: a la mort, afficher si nouvelle partie, si enregistrer, leaderboard, edition de sa partie
                break;
            case 6:
                System.out.println("This is the end !");
                break;
            default:
                break;
        }
        
        scanner.close();
    }
    


    public static String convertButton(String thing){
        switch (thing) {
            case "A":
                return "10";
            case "B":
                return "11";
            case "C":
                return "12";
            case "D":
                return "13";
            case "E":
                return "14";
        
            default:
                return thing;
        }
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
            default:
                return "empty";
        }
    }

    public static String getTypeAI(int strategy_AI){
        switch (strategy_AI) {
            case 1:
                return "DFS";
            case 2:
                return "BFS";
            case 3:
                return "Astar";
            default:
                return "BFS";
        }
    }
    
    public static String legendString(){
        String legend = "Legend : S = Starting Point, T = Treasure, E = Hero, . = Empty tile, # = Stone Wall - " + (new StoneWall(null)).getPlacementCost() + ", @ = Wood Wall - " + (new WoodWall(null)).getPlacementCost() + ", W = Wall Trap - " + (new WallTrap(null)).getPlacementCost() + ", M = Mine - " + (new Mine(null)).getPlacementCost();
        return legend;
    }
    
    
    
    // TODO: leaderboard (affiche page par page les games)
}
