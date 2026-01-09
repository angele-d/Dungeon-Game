package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.SaveManager;
import dungeon.engine.GameEngine;
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
        gameGenerator(0, 0);
    }

    public static void gameGenerator(int edit, int game_id_load) {
        int DISPLAY_SIZE = 10;
        Scanner scanner = new Scanner(System.in);
        Game game;
        if (edit == 0) {
            game = GameEngine.getInstance().newGame();
        } else {
            game = GameEngine.getInstance().getGame(game_id_load);
            System.out.println("Your game is ready ! ");
        }

        System.out.println("============== Welcome in the Dungeon ! ==============");
        System.out.println("\n");
        System.out.println("Heroes' strategy :");
        System.out.println("   [1] BFS");
        System.out.println("   [2] A*");
        System.out.println("\n");

        int strategy_AI = 0;
        int strategy = 0;
        while (strategy == 0) {
            System.out.print("Give the indicated AI strategy : ");
            String strat = scanner.next();
            try {
                strategy_AI = Integer.parseInt(strat);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 and 2 !");
                System.out.println("\n");
                continue;
            }
            if (strategy_AI > 0 && strategy_AI < 3) {
                strategy = 1;
            } else {
                System.out.println("Please enter a number between 1 and 2 !");
                System.out.println("\n");
            }
        }

        System.out.println("\n");
        System.out.print("Thank you !");
        System.out.println("\n");

        String legendString = legendString();

        int size_grid = game.getGrid().getSize();
        List<String> legend = List.of("#", "@", "W", "M", "P");
        List<String> name_cases = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

        PrintGrid.make_action(game, size_grid, legendString);

        int action_player = 3;

        int end_action = 0;

        while (end_action == 0) {

            game = GameEngine.getInstance().getGame(game.getId());
            int action = 0;
            while (action == 0) {
                System.out.print("Do an action : ");
                String input = scanner.next();
                try {
                    action_player = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number between 1 and 6 !");
                    System.out.println("\n");
                    continue;
                }
                if (action_player > 0 && action_player < 7) {
                    action = 1;
                } else {
                    System.out.println("Please enter a number between 1 and 6 !");
                    System.out.println("\n");
                }
            }

            System.out.println("\n");
            System.out.print("Thank you !");
            System.out.println("\n");

            switch (action_player) {
                case 1:
                    int choice = 0;

                    String action_object = "";

                    while (choice == 0) {
                        System.out.print("What do you want to place ? ");
                        action_object = scanner.next();
                        System.out.println("\n");
                        if (legend.contains(action_object)) {
                            choice = 1;
                        }
                    }
                    int canAdd = 0;
                    while (canAdd == 0) {
                        int choice_coord = 0;
                        String pos_object_x = "";
                        String pos_object_y = "";
                        while (choice_coord == 0) {
                            System.out.print("Where do you want to place ? ");
                            
                            pos_object_x = scanner.next();
                            pos_object_y = scanner.next();
                            System.out.println("\n");
                            if (name_cases.contains(pos_object_x) && name_cases.contains(pos_object_y)) {
                                choice_coord = 1;
                            } else {
                                System.out.print("Please choose a available tile...");
                                System.out.println("\n");
                            }

                        }

                        Coords coordTile = new Coords(Integer.parseInt(pos_object_x), Integer.parseInt(pos_object_y));
                        GameEngine.getInstance().placeTile(game.getId(), coordTile,
                                TerminalLauncher.getTypeObject(action_object));
                        game = GameEngine.getInstance().getGame(game.getId());
                        boolean canPlace = game.placementOnGrid(game.getGrid().getTile(coordTile));
                        if (canPlace) {
                            canAdd = 1;
                        } else {
                            System.out.print("You cannot place on the treasure or the starting point...");
                            System.out.println("\n");
                        }
                    }
                    PrintGrid.make_action(game, size_grid, legendString);
                    System.out.println("\n");
                    System.out.println("Coin : " + game.getMoney());
                    break;
                case 2:
                    int canDelete = 0;
                    while (canDelete == 0) {
                        int choice_delete = 0;
                        String pos_object_delete_x = "";
                        String pos_object_delete_y = "";
                        while (choice_delete == 0) {
                            System.out.print("Where do you want to delete ? ");
                            pos_object_delete_x = scanner.next();
                            pos_object_delete_y = scanner.next();
                            if (name_cases.contains(pos_object_delete_x) && name_cases.contains(pos_object_delete_y)) {
                                choice_delete = 1;
                            } else {
                                System.out.print("Please choose a case...");
                                System.out.println("\n");
                            }
                        }

                        Coords coordTileDelete = new Coords(Integer.parseInt(pos_object_delete_x),
                                Integer.parseInt(pos_object_delete_y));
                        GameEngine.getInstance().placeTile(game.getId(), coordTileDelete, "empty");
                        game = GameEngine.getInstance().getGame(game.getId());
                        boolean canPlace_delete = game.placementOnGrid(game.getGrid().getTile(coordTileDelete));
                        if (canPlace_delete) {
                            canDelete = 1;
                        } else {
                            System.out.print("You cannot delete the treasure or the starting point...");
                            System.out.println("\n");
                        }

                    }
                    PrintGrid.make_action(game, size_grid, legendString);
                    System.out.println("\n");
                    System.out.println("Coin : " + game.getMoney());
                    break;
                case 3:
                    try {
                        SaveManager.save(game);
                        GameEngine.getInstance().updateLeaderboard();
                    } catch (IOException e) {
                        System.err.println("Error during backup : " + e.getMessage());
                        System.out.println("\n");
                    }
                    System.out.println("I save your game !");
                    System.out.println("\n");
                    break;
                case 4:
                    int optionLead = LeaderBoard.showLeaderBoard(scanner, DISPLAY_SIZE);
                    if (optionLead != -1) {
                        game = GameEngine.getInstance().getGame(optionLead);
                        System.out.println("Your game is ready ! ");
                        System.out.println("\n");
                        PrintGrid.make_action(game, size_grid, legendString);
                    }
                    break;
                case 5:
                    if (game.isSimulationReady()) {
                        end_action = 1;

                    } else {
                        System.out.println("At least one accessible path is needed...");
                        System.out.println("\n");
                    }
                    break;
                case 6:
                    System.out.print("Would you save your game ? (yes/no) ");
                    String save_game = scanner.next();
                    if (save_game.equals("yes")) {
                        try {
                            SaveManager.save(game);
                            GameEngine.getInstance().updateLeaderboard();
                            System.out.println("I save your game !");
                            System.out.println("\n");
                            System.out.println("\n");
                            end_action = 1;
                        } catch (IOException e) {
                            System.err.println("Error during backup : " + e.getMessage());
                            System.out.println("\n");
                            System.out.println("\n");
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
                ExecuteGame.execute_game(game, size_grid, legendString,
                        strategy_AI, scanner);

                int lead_option = 0;
                while (lead_option == 0) {
                    int action = 0;
                    int action_player_game = 1;
                    while (action == 0) {
                        PrintGrid.manage_end_game();
                        String input = scanner.next();
                        try {
                            action_player_game = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a number between 1 and 5 !");
                            System.out.println("\n");
                            continue;
                        }
                        if (action_player_game > 0 && action_player_game < 6) {
                            action = 1;
                        }
                    }

                    switch (action_player_game) {
                        case 1:
                            gameGenerator(0, 0);
                            lead_option = 1;
                            break;
                        case 2:
                            gameGenerator(1, game.getId());
                            lead_option = 1;
                            break;
                        case 3:
                            try {
                                SaveManager.save(game);
                                GameEngine.getInstance().updateLeaderboard();
                                System.out.println("I save your game !");
                            } catch (IOException e) {
                                System.err.println("Error during backup : " + e.getMessage());
                            }
                            System.out.println("\n");
                            break;
                        case 4:
                            int optionLead = LeaderBoard.showLeaderBoard(scanner, DISPLAY_SIZE);
                            if (optionLead != -1) {
                                game = GameEngine.getInstance().getGame(optionLead);
                                gameGenerator(1, game.getId());
                                lead_option = 1;
                            }
                            break;
                        case 5:
                            lead_option = 1;
                            break;
                        default:
                            break;
                    }
                }
                break;

            case 6:
                System.out.println("This is the end !");
                System.out.println("\n");
                System.out.println("\n");
                break;
            default:
                break;

        }

        scanner.close();
    }

    public static String getTypeObject(String action_object) {
        switch (action_object) {
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

    public static String legendString() {
        String legend = "Legend : S = Starting Point, T = Treasure, E = Hero, . = Empty tile, # = Stone Wall - "
                + (new StoneWall(null)).getPlacementCost() + ", @ = Wood Wall - "
                + (new WoodWall(null)).getPlacementCost() + ", W = Wall Trap - "
                + (new WallTrap(null)).getPlacementCost() + ", M = Mine - " + (new Mine(null)).getPlacementCost()
                + ", P = Poison Trap - " + (new PoisonTrap(null)).getPlacementCost();
        return legend;
    }
}
