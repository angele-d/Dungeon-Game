package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.HeroSquad;
import dungeon.engine.Hero;
import dungeon.engine.GameEngine;
import dungeon.engine.Game;
import dungeon.engine.Grid;
import dungeon.engine.Tile;
import java.util.Scanner;
import java.util.List;


public class TerminalLauncher {
    public static final int SIZE_GRID = 15;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = GameEngine.getInstance().newGame();
        int ID = game.getId();
        List<String> legend = List.of("#", "@", "W", "M");
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
            strategy_AI = scanner.nextInt();
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

        make_action(game,SIZE_GRID);

        while (end_action == 0){

            game = GameEngine.getInstance().getGame(game.getId());
            int action = 0;
            while (action == 0) {
                System.out.print("Do an action : ");
                action_player = scanner.nextInt();
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
                            choice = 1;
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
                    // TODO: Money
                    GameEngine.getInstance().placeTile(ID, new Coords(Integer.parseInt(pos_object_x),Integer.parseInt(pos_object_y)) , getTypeObject(action_object));
                    game = GameEngine.getInstance().getGame(game.getId());
                    print_grid(game, SIZE_GRID);
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
                    // TODO: Money
                    GameEngine.getInstance().placeTile(ID, new Coords(Integer.parseInt(pos_object_delete_x),Integer.parseInt(pos_object_delete_y)) , "empty");
                    game = GameEngine.getInstance().getGame(game.getId());
                    print_grid(game, SIZE_GRID);
                    break;
                case 3:
                    // TODO : Save the game
                    System.out.println("I save your game !");
                    break;
                case 4:
                    // TODO : Check if the game exists and load the game
                    System.out.println("Your game is ready ! ");
                    break;
                case 5:
                    end_action = 1;
                    break; 
                case 6:
                    end_action = 1;
                    break;       
                default:
                    break;
            } 
        }

        switch (action_player) {
            case 5:
                System.out.println("================= Heroes are here ! =================");
                execute_game(game);
                break;
            case 6:
                // TODO : Print Score
                System.out.println("This is the end !");
                break;
            default:
                break;
        }
        
        scanner.close();
    }
    public static void make_action (Game game, int size){
        print_grid(game, size);
        System.out.println("\n");
        System.out.println("Actions :");
        System.out.println("   [1] Place an element");
        System.out.println("   [2] Delete an element");
        System.out.println("   [3] Save");
        System.out.println("   [4] Load a game");
        System.out.println("   [5] Start the game");
        System.out.println("   [6] Leave");
        System.out.println("\n");
        System.out.println("Coin :" + game.getMoney());
        System.out.println("\n");
    }

    public static void print_grid(Game game, int size) {
        System.out.println("Legend : S = Starting Point, T = Treasure, . = Empty tile, # = Stone Wall, @ = Wood Wall, E = Hero, W = Wall Trap, M = Mine");
        System.out.println("\n");
        System.out.println("   0 1 2 3 4 5 6 7 8 9 A B C D E");
        String[] cases = new String[] {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E"};
        for (int i = 0; i < size; i++) {
            System.out.println(cases[i] + " " + print_grid_line(game, i, size));
        }
    }

    public static String print_grid_line(Game game, int line, int size){
        String line_completed = new String();
        for (int decr = 0; decr < size ; decr++ ){
            Tile tile = game.getGrid().getTile(new Coords(line,decr));
            if(heroIsHere(game,line, decr)){
                line_completed += " E";
            } else {
                if (tile instanceof dungeon.engine.tiles.Empty) {
                    line_completed += " .";
                } else if (tile instanceof dungeon.engine.tiles.Treasure) {
                    line_completed += " T";
                } else if (tile instanceof dungeon.engine.tiles.StartingPoint) {
                    line_completed += " S";
                } else if (tile instanceof dungeon.engine.tiles.traps.Mine) {
                    line_completed += " M";
                } else if (tile instanceof dungeon.engine.tiles.traps.WallTrap) {
                    line_completed += " T";
                } else if (tile instanceof dungeon.engine.tiles.wall.StoneWall) {
                    line_completed += " #";
                } else if (tile instanceof dungeon.engine.tiles.wall.WoodWall) {
                    line_completed += " @";
                } else {
                    line_completed += "  ";
                }
            }
        }
        return line_completed;
    }

    public static void execute_game(Game game) {
        int end = 0;
        int round = 1;
        while (end == 0){
            System.out.println("===================== Round " + round + " ! =====================");
            if(end == 0 && round == 3){ // TODO: Finish Game : Print score, print finish
                end = 1;
            } else {
                GameEngine.getInstance().nextTurn(game.getId());
                print_grid(game, SIZE_GRID);
                sleepHalfSecond();
            }
            round ++;
        }
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

    public static boolean heroIsHere(Game game, int line, int row){
        HeroSquad heroS = game.getHeroSquad();
        for (Hero hero : heroS.getHeroes()){
            if(hero.getCoords().equals(new Coords(line,row))){
                return true;
            }
        }
        return false;
    }

    public static void sleepHalfSecond() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
