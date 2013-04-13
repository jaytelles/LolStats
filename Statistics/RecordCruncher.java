package Statistics;

import java.util.ArrayList;
import org.javalite.activejdbc.LazyList;
import DatabaseInterfaces.Gameinfo;

/**
 * 
 * @author NRDT
 * 
 * This class contains many segments of repeated code. The point of this is to 
 * put a layer on top of SQL statements that a programmer may or may not get 
 * right, and allows for programmatic manipulation of the database without any 
 * knowledge of the syntax of SQL
 */
public class RecordCruncher{
    public static ArrayList<Gameinfo> filterPlayers(LazyList<Gameinfo> games, String summonerName){
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getPlayerName().equals(summonerName)){
                filteredList.add(games.get(k));
            }
        }
        return filteredList;
    }
    
    public static ArrayList<Gameinfo> filterPlayers(ArrayList<Gameinfo> games, String summonerName){
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getPlayerName().equals(summonerName)){
                filteredList.add(games.get(k));
            }
        }
        return filteredList;
    }
    
    public static ArrayList<Gameinfo> filterChampions(ArrayList<Gameinfo> games, String champion){      
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getChampPlayed().equals(champion)){
                filteredList.add(games.get(k));
            }
        }
        return filteredList;
    }
    
    public static ArrayList<Gameinfo> filterMatchups(ArrayList<Gameinfo> games, String matchup){
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getLaneOpponent().equals(matchup)){
                filteredList.add(games.get(k));
            }
        }
        return filteredList;
    }
    
    public static ArrayList<Gameinfo> filterRole(ArrayList<Gameinfo> games, String role){
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getRole().equalsIgnoreCase(role)){
                filteredList.add(games.get(k));
            }
        }
        
        return filteredList;
    }
    
    public static ArrayList<Gameinfo> filterNumTeammates(ArrayList<Gameinfo> games, int numTeammates){
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(games.get(k).getNumTeammates()==numTeammates){
                filteredList.add(games.get(k));
            }
        }
        
        return filteredList;
    }
    
    public static ArrayList<Gameinfo> removeSupports(ArrayList<Gameinfo> games){
        ArrayList<Gameinfo> filteredList = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
           if(!games.get(k).getRole().equals("Support")){
               filteredList.add(games.get(k));
           }
        }
        
        return filteredList;
    }
    
    //The decision was made to not use the SQL distinct() function to reduce the amount of SQL syntax in this code.
    public static ArrayList<String> findAllChampions(ArrayList<Gameinfo> games){
        ArrayList<String> names = new ArrayList<>();
        for(int k=0; k<games.size(); k++){
            if(!names.contains(games.get(k).getChampPlayed())){
                names.add(games.get(k).getChampPlayed());
            }
        }
        return names;
    }
    
    public static ArrayList<String> findAllMatchups(ArrayList<Gameinfo> games){
         ArrayList<String> names = new ArrayList<>();
         for(int k=0; k<games.size(); k++){
             if(!names.contains(games.get(k).getLaneOpponent())){
                 names.add(games.get(k).getLaneOpponent());
             }
         }
         return names;
    }
    
    public static ArrayList<String> findAllTeammates(LazyList<Gameinfo> games){
        ArrayList<String> filteredList = new ArrayList<String>();
        for(int k=0; k<games.size(); k++){
            if(!filteredList.contains(games.get(k).getPlayerName())){
                filteredList.add(games.get(k).getPlayerName());
            }
        }
        return filteredList;
    }   
}