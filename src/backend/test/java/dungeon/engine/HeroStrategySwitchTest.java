package dungeon.engine;

import dungeon.engine.Strategies.Strategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests sans framework de mocking pour vérifier le changement dynamique de stratégie d'un héros.
 * On utilise des "stubs" de Strategy pour contrôler le déplacement retourné.
 */
class HeroStrategySwitchTest {

    // --- Stubs de Strategy ---
    static class StubStrategyUp extends Strategy {
        @Override
        public Coords move(Game game, Hero hero) {
            Coords c = hero.getCoords();
            // Convention: "HAUT" => y - 1
            return new Coords(c.x(), c.y() - 1);
        }
    }

    static class StubStrategyDown extends Strategy {
        @Override
        public Coords move(Game game, Hero hero) {
            Coords c = hero.getCoords();
            // Convention: "BAS" => y + 1
            return new Coords(c.x(), c.y() + 1);
        }
    }

    @Test
    @DisplayName("1) Initialisation: le héros n'a pas de stratégie par défaut")
    void shouldHaveNoStrategyByDefaultOnInitialization() {
        // Given
        Dwarf hero = new Dwarf();

        // When
        // aucun traitement

        // Then
        assertNull(hero.strategy, "Le héros ne doit pas avoir de stratégie par défaut tant qu'elle n'est pas définie");
    }

    @Test
    @DisplayName("2) Changement de stratégie: HAUT puis BAS")
    void shouldSwitchStrategyFromUpToDown() {
        // Given
        Game game = new Game();
        Dwarf hero = new Dwarf();
        hero.setCoords(new Coords(3, 3));
        // On ajoute le héros à la squad du jeu pour satisfaire basicMove (tick poison)
        game.getHeroSquad().addHero(hero);

        Strategy up = new StubStrategyUp();
        Strategy down = new StubStrategyDown();

        // When - assigner la stratégie HAUT
        // Utilisons l'API existante de la squad pour propager la stratégie au héros
        game.getHeroSquad().setStrategy(up);

        // Then - le mouvement doit renvoyer une coordonnée "vers le haut"
        Coords resUp = hero.move(game);
        assertEquals(new Coords(3, 2), resUp, "Le déplacement avec la stratégie 'Up' doit renvoyer (3,2)");

        // When - changer la stratégie pour BAS
        game.getHeroSquad().setStrategy(down);

        // Then - le mouvement doit renvoyer une coordonnée "vers le bas"
        Coords resDown = hero.move(game);
        assertEquals(new Coords(3, 4), resDown, "Le déplacement avec la stratégie 'Down' doit renvoyer (3,4)");

        // Intégrité: ni la position interne, ni la vie ne doivent être modifiées par le simple choix de stratégie
        // (Le déplacement effectif est appliqué par Game.doMovement dans la boucle de tour, pas par Hero.move)
        assertEquals(new Coords(3, 3), hero.getCoords(), "Le héros ne doit pas changer de position tant que Game.doMovement n'est pas appelé");
        assertEquals(hero.getMaxHealth(), hero.getHealth(), "Changer de stratégie ne doit pas impacter les PV");
    }
}
