package dungeon.ui.cli;

import dungeon.engine.Coords;
import dungeon.engine.HeroSquad;
import dungeon.engine.Hero;
import dungeon.engine.Game;
import dungeon.engine.Tile;

public class PrintGrid {

    /** 
     * Displays the action menu for the game.
     * @param game
     * @param size
     * @param legendString
     */
    public static void make_action(Game game, int size, String legendString) {
        print_grid(game, size, legendString, 1);
        System.out.println("\n");
        System.out.println("Actions :");
        System.out.println("   [1] Place an element");
        System.out.println("   [2] Delete an element");
        System.out.println("   [3] Save");
        System.out.println("   [4] Load a game");
        System.out.println("   [5] Start the game");
        System.out.println("   [6] Leave");
        System.out.println("\n");
    }

    /** 
     * Prints the game grid to the console.
     * @param game
     * @param size
     * @param legendString
     * @param edit
     */
    public static void print_grid(Game game, int size, String legendString, int edit) {
        System.out.println(legendString);
        System.out.println("\n");
        System.out.println("Your game ID : " + game.getId());
        System.out.println("\n");
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        String[] cases = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        for (int i = 0; i < size; i++) {
            System.out.println(cases[i] + " " + print_grid_line(game, i, size, edit));
        }
        System.out.println("\n");
    }

    /** 
     * Prints a single line of the game grid.
     * @param game
     * @param line
     * @param size
     * @param edit
     * @return String
     */
    public static String print_grid_line(Game game, int line, int size, int edit) {
        String line_completed = new String();
        for (int decr = 0; decr < size; decr++) {
            Tile tile = game.getGrid().getTile(new Coords(line, decr));
            if (edit == 0 && heroIsHere(game, line, decr)) {
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
                } else if (tile instanceof dungeon.engine.tiles.traps.PoisonTrap) {
                    line_completed += " P";
                } else {
                    line_completed += "  ";
                }
            }
        }
        return line_completed;
    }

    /** 
     * Checks if a hero is present at the specified coordinates.
     * @param game
     * @param line
     * @param row
     * @return boolean
     */
    public static boolean heroIsHere(Game game, int line, int row) {
        HeroSquad heroS = game.getHeroSquad();
        for (Hero hero : heroS.getHeroes()) {
            if (hero.getCoords().equals(new Coords(line, row))) {
                return true;
            }
        }
        return false;
    }

    /** 
     * Displays the end game management menu.
     */
    public static void manage_end_game() {
        System.out.println("\n");
        System.out.println("What would you want to do ? ");
        System.out.println("   [1] New game");
        System.out.println("   [2] Edit your game");
        System.out.println("   [3] Save your game");
        System.out.println("   [4] Leaderboard");
        System.out.println("   [5] Leave");
        System.out.println("\n");
    }
}
