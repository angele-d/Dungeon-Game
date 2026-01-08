package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.SaveManager;
import dungeon.engine.GameEngine;
import dungeon.engine.Game;
import java.util.Scanner;

import java.io.IOException;
import java.util.List;

public class EditGame{
    public static CoordsWrapper editGame(Game game, Scanner scanner, int startingPointPassage, int treasurePointPassage, List<String> name_cases, List<String> legend, int size_grid,String legendString, int action_player,int S_x,int S_y){
        int end_action = 0;
        
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
                    GameEngine.getInstance().placeTile(game.getId(), coordTile , TerminalLauncher.getTypeObject(action_object));
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
                        }
                    }
                    
                    Coords coordTileDelete = new Coords(Integer.parseInt(pos_object_delete_x),Integer.parseInt(pos_object_delete_y));
                    GameEngine.getInstance().placeTile(game.getId(), coordTileDelete , "empty");
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
                    int game_id = 0;
                    int choice_load = 0;
                    while (choice_load == 0) {
                        System.out.print("What is your game id ? ");
                        String input = scanner.next();
                        try {
                            game_id = Integer.parseInt(input);
                            choice_load = 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Veuillez entrer un nombre entre 1 et 6 !");
                            continue;
                        }
                    }
                    game = GameEngine.getInstance().getGame(game_id);
                    System.out.println("Your game is ready ! ");
                    break;
                case 5:
                    if (startingPointPassage > 0 && treasurePointPassage > 0){
                        if (game.isSimulationReady()){
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
        return new CoordsWrapper(S_x, S_y);
    }
}