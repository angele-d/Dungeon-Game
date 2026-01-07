package dungeon.engine;

import org.junit.jupiter.api.Test;

class GameEngineTest {
    @Test
    void GameEngineSerializationTest() {
        Game new_game = GameEngine.getInstance().newGame();
        System.out.println(new_game.getGrid().serialized().toString());
    }
}
