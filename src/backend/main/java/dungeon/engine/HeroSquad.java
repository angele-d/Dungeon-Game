package dungeon.engine;

import java.util.ArrayList;

import dungeon.engine.Strategies.Strategy;

public class HeroSquad{ 
    private ArrayList<Hero> heroList;

    public HeroSquad(){
        heroList = new ArrayList<Hero>();
    }

    public void addHero(Hero hero){
        heroList.add(hero);
    }

    public void removeHero(Hero hero){
        heroList.remove(hero);
    }

    /* --- Getters and Setters --- */

    public ArrayList<Hero> getHeroes(){
        return heroList;
    }

    public int getSquadSize(){
        return heroList.size();
    }

    public void setStrategy(Strategy strategy) {
        for(Hero hero : heroList){
            hero.setStrategy(strategy);
        }
    }

    /* --- Builder --- */

    public static class Builder{
        private ArrayList<Hero> heroList = new ArrayList<>();

        public Builder(){
            
        }

        public Builder addHero(Hero hero){
            this.heroList.add(hero);
            return this;
        }

        public HeroSquad build(){
            HeroSquad squad = new HeroSquad();
            for(Hero hero : this.heroList){
                squad.addHero(hero);
            }
            return squad;
        }
    }

    public ArrayList<ArrayList<String>> serialized() {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for(Hero hero : this.heroList){
            ArrayList<String> hero_serialized = new ArrayList<String>();
            hero_serialized.add(hero.toString());
            hero_serialized.add(Integer.toString(hero.getCoords().x()));
            hero_serialized.add(Integer.toString(hero.getCoords().y()));
            hero_serialized.add(Integer.toString(hero.getHealth()/hero.getMaxHealth()));
        }
        return result;
    }
} 