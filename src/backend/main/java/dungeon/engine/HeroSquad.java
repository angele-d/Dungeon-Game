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
} 