package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.Grid;
import dungeon.engine.Tile;
import java.util.Scanner;
import java.util.List;


public class TerminalLauncher {
    public static final int SIZE_GRID = 15;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grid grid = new Grid();
        List<String> legend = List.of("S", "T", ".", "#", "@", "E", "W", "M");
        List<String> name_cases = List.of("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E");
        System.out.println("============== Welcome in the Dungeon ! ==============");
        System.out.println("\n");
        System.out.println("Heroes' strategy :");
        System.out.println("   [1] DFS");
        System.out.println("   [2] BFS");
        System.out.println("   [3] A*");
        System.out.println("\n");
        
        int strategy = 0;
        while (strategy == 0) {
            System.out.print("Give the indicated AI strategy : ");
            int strategy_AI = scanner.nextInt();
            if (strategy_AI > 0 && strategy_AI < 4){
                strategy = 1;
            }
        }

        System.out.println("\n");
        System.out.print("Thank you !");
        System.out.println("\n");

        int end_action = 0;
        int action_player = 3;

        make_action(grid,SIZE_GRID);

        while (end_action == 0){

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
                    while (choice == 0) {
                        System.out.print("What do you want to place ? ");
                        String action_object = scanner.next();
                        if (legend.contains(action_object)){
                            choice = 1;
                        }
                    }
                    while (choice_coord == 0) {
                        System.out.print("Where do you want to place ? ");
                        String pos_object_x = scanner.next();
                        String pos_object_y = scanner.next();
                        if (name_cases.contains(pos_object_x) && name_cases.contains(pos_object_y)){
                            choice_coord = 1;
                        }
                    }
                    // TODO : Add element in grid with pos_object and action_object
                    print_grid(grid, SIZE_GRID);
                    break;
                case 2:
                    int choice_delete = 0;
                    while (choice_delete == 0) {
                        System.out.print("Where do you want to delete ? ");
                        String pos_object_delete_x = scanner.next();
                        String pos_object_delete_y = scanner.next();
                        if (name_cases.contains(pos_object_delete_x) && name_cases.contains(pos_object_delete_y)){
                            choice_delete = 1;
                        }
                    }
                    // TODO : Delete element in grid with pos_object_delete
                    print_grid(grid, SIZE_GRID);
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
                execute_game(grid);
                break;
            case 6:
                // TODO : Print Score
                System.out.println("This is the end !");
                break;
            default:
                break;
        }        
    }
    public static void make_action (Grid grid, int size){
        System.out.println("Legend : S = Starting Point, T = Treasure, . = Empty tile, # = Stone Wall, @ = Wood Wall, E = Hero, W = Trap Wall, M = Mine");
        System.out.println("\n");
        print_grid(grid, size);
        System.out.println("\n");
        System.out.println("Actions :");
        System.out.println("   [1] Place an element");
        System.out.println("   [2] Delete an element");
        System.out.println("   [3] Save");
        System.out.println("   [4] Load a game");
        System.out.println("   [5] Start the game");
        System.out.println("   [6] Leave");
        System.out.println("\n");
        System.out.println("\n");
    }

    public static void print_grid(Grid grid, int size) {
        System.out.println("   0 1 2 3 4 5 6 7 8 9 A B C D E");
        String[] cases = new String[] {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E"};
        for (int i = 0; i < size; i++) {
            System.out.println(cases[i] + " " + print_grid_line(grid, i, size));
        }      
    }

    public static String print_grid_line(Grid grid, int line, int size){
        String line_completed = new String();
        for (int decr = 0; decr < size ; decr++ ){
            Tile tile = grid.getTile(new Coords(line,decr));
            if (tile == null){
                line_completed += " V";
            } else if (tile instanceof dungeon.engine.tiles.Empty) {
                line_completed += " .";
            } else if (tile instanceof dungeon.engine.tiles.Treasure) {
                line_completed += " T";
            } else {
                line_completed += "  ";
            }
        }
        return line_completed;
    }

    public static void execute_game(Grid grid) {
        // TODO : The game starts
        int end = 0;
        int round = 1;
        while (end == 0){
            System.out.println("===================== Round " + round + " ! =====================");
            end = 1;
            print_grid(grid, SIZE_GRID);
        }
    }
}
