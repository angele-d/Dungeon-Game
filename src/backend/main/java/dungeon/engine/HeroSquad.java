package dungeon.engine;

import java.util.ArrayList;

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

    public ArrayList<Hero> getHeroes(){
        return heroList;
    }

    public int getSquadSize(){
        return heroList.size();
    }

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
} 