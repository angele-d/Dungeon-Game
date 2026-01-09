package dungeon.engine;

import java.util.ArrayList;

import dungeon.engine.Strategies.Strategy;

public class HeroSquad {
    private ArrayList<Hero> heroList;

/* --- Constructor --- */

    public HeroSquad() {
        heroList = new ArrayList<Hero>();
    }
    
/* --- Getters and Setters --- */

    /** 
     * Gets the list of heroes in the squad.
     * @return ArrayList<Hero>
     */
    public ArrayList<Hero> getHeroes() {
        return heroList;
    }

    /** 
     * Gets the size of the hero squad.
     * @return int
     */
    public int getSquadSize() {
        return heroList.size();
    }

    /** 
     * Sets the strategy for all heroes in the squad.
     * @param strategy
     */
    public void setStrategy(Strategy strategy) {
        for (Hero hero : heroList) {
            hero.setStrategy(strategy);
        }
    }

/* --- Builder --- */

    public static class Builder {
        private ArrayList<Hero> heroList = new ArrayList<>();

        public Builder() {

        }

        public Builder addHero(Hero hero) {
            this.heroList.add(hero);
            return this;
        }

        public HeroSquad build() {
            HeroSquad squad = new HeroSquad();
            for (Hero hero : this.heroList) {
                squad.addHero(hero);
            }
            return squad;
        }
    }

/* --- Functions --- */

    /** 
     * Adds a hero to the squad.
     * @param hero
     */
    public void addHero(Hero hero) {
        heroList.add(hero);
    }
    /** 
     * Removes a hero from the squad.
     * @param hero
     */
    public void removeHero(Hero hero) {
        heroList.remove(hero);
    }

    /** 
     * Serializes the hero squad for saving.
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> serialized() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (Hero hero : this.heroList) {
            ArrayList<String> hero_serialized = new ArrayList<String>();
            hero_serialized.add('"' + hero.toString() + '"');
            if (hero.getCoords() != null) {
                hero_serialized.add(Integer.toString(hero.getCoords().x()));
                hero_serialized.add(Integer.toString(hero.getCoords().y()));
            } else {
                hero_serialized.add(null);
                hero_serialized.add(null);
            }
            hero_serialized.add(Float.toString(((float) hero.getHealth()) / ((float) hero.getMaxHealth())));
            result.add(hero_serialized);
        }
        return result;
    }
}