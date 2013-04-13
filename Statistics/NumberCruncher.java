package Statistics;

import java.util.ArrayList;
import org.javalite.activejdbc.LazyList;
import DatabaseInterfaces.Gameinfo;

public class NumberCruncher {
    public static int getTotalKills(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            sum += games.get(k).getKills();
        }
        return sum;
    }
    
    public static int getTotalKills(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            sum += games.get(k).getKills();
        }
        return sum;
    }
    
    public static int getTotalDeaths(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            sum += games.get(k).getDeaths();
        }
        return sum;
    }
    
    public static int getTotalDeaths(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            sum += games.get(k).getDeaths();
        }
        return sum;
    }
    
    public static int getTotalAssists(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            sum += games.get(k).getAssists();
        }
        return sum;
    }
    
    public static int getTotalAssists(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            sum += games.get(k).getAssists();
        }
        return sum;
    }
    
    public static int getTotalLanesWon(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Won")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLanesWon(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Won")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLanesLost(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Lost")){
                sum++;
            }
        }
        return sum;
    }
    
     public static int getTotalLanesLost(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Lost")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLanesEven(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Even")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLanesEven(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Even")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLanesOther(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Other")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLanesOther(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOutcome().equals("Other")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalWins(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getGameOutcome().equals("Yes")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalWins(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getGameOutcome().equals("Yes")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLosses(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getGameOutcome().equals("No")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTotalLosses(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getGameOutcome().equals("No")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTimesGottenFirstBlood(LazyList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getGotFirstBlood().equals("Yes")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getTimesGottenFirstBlood(ArrayList<Gameinfo> games){
        int sum = 0;
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getGotFirstBlood().equals("Yes")){
                sum++;
            }
        }
        return sum;
    }
    
    public static int getStreak(ArrayList<Gameinfo> games){
        if(games.size()==0){
            return 0;
        }
        
        int sum;
        if(games.get(games.size()-1).getGameOutcome().equals("No")){
            sum = -1;
            for(int k=games.size()-2; k>=0&&games.get(k).getGameOutcome().equals("No"); k--){
                sum--;
            }
            sum--;
            return sum;
            
        } else {
            sum = 1;
            for(int k=games.size()-2; k>=0&&games.get(k).getGameOutcome().equals("Yes"); k--){
                sum++;
            }
            return sum;
        }
        
    }
}
