package dungeon.engine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import dungeon.engine.AI.BFS;
import dungeon.engine.AI.DFS;
import dungeon.engine.tiles.Treasure;

public class StrategyTest {
    
    private Grid grid;
    
    @BeforeEach
    void setUp() {
        grid = new Grid();
    }
    
    @Test
    void testBFSFindsDirectPath() {
        grid.setTile(new Treasure(new Coords(0, 3)));
        BFS bfs = new BFS(grid);
        
        Coords next = bfs.search(new Coords(0, 0));
        assertEquals(new Coords(0, 1), next);
        
        next = bfs.search(new Coords(0, 1));
        assertEquals(new Coords(0, 2), next);
        
        next = bfs.search(new Coords(0, 2));
        assertEquals(new Coords(0, 3), next);
    }
    
    @Test
    void testDFSFindsDirectPath() {
        grid.setTile(new Treasure(new Coords(0, 3)));
        DFS dfs = new DFS(grid);
        
        Coords next = dfs.search(new Coords(0, 0));
        assertEquals(new Coords(0, 1), next);
        
        next = dfs.search(new Coords(0, 1));
        assertEquals(new Coords(0, 2), next);
        
        next = dfs.search(new Coords(0, 2));
        assertEquals(new Coords(0, 3), next);
    }
    
    @Test
    void testBFSReturnsNullWhenNoTreasure() {
        BFS bfs = new BFS(grid);
        
        Coords next = bfs.search(new Coords(0, 0));
        assertNull(next);
    }
    
    @Test
    void testDFSReturnsNullWhenNoTreasure() {
        DFS dfs = new DFS(grid);
        
        Coords next = dfs.search(new Coords(0, 0));
        assertNull(next);
    }
    
    @Test
    void testBFSFromTreasureLocation() {
        Coords treasureCoords = new Coords(3, 3);
        grid.setTile(new Treasure(treasureCoords));
        BFS bfs = new BFS(grid);
        
        Coords next = bfs.search(treasureCoords);
        assertEquals(treasureCoords, next);
    }
    
    @Test
    void testDFSFromTreasureLocation() {
        Coords treasureCoords = new Coords(3, 3);
        grid.setTile(new Treasure(treasureCoords));
        DFS dfs = new DFS(grid);
        
        Coords next = dfs.search(treasureCoords);
        assertEquals(treasureCoords, next);
    }
    
    @Test
    void testBFSConsistency() {
        // Test that BFS returns same result for same input
        grid.setTile(new Treasure(new Coords(4, 4)));
        BFS bfs = new BFS(grid);
        
        Coords start = new Coords(2, 2);
        Coords result1 = bfs.search(start);
        Coords result2 = bfs.search(start);
        
        assertEquals(result1, result2, "BFS should be deterministic");
    }
    
    @Test
    void testDFSConsistency() {
        grid.setTile(new Treasure(new Coords(4, 4)));
        DFS dfs = new DFS(grid);
        
        Coords start = new Coords(2, 2);
        Coords result1 = dfs.search(start);
        Coords result2 = dfs.search(start);
        
        assertEquals(result1, result2, "DFS should be deterministic");
    }
}
