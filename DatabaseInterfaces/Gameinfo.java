package DatabaseInterfaces;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

public class Gameinfo extends Model {
    private int gameNumber;
    private String playerName;
    private String champPlayed;
    private String role;//Top, Mid, ADC, Supp, Jungle
    private String laneOpponent;
    private String laneOutcome;
    private int kills;
    private int deaths;
    private int assists;
    private int numTeammates;
    private String gotFirstBlood;
    private String gameOutcome;
    private String description;
    private String submitterName;
    
    
    public void setGameNumber(int gameNumber){
        this.gameNumber = gameNumber;
        this.set("GameNumber",this.gameNumber);
    }
    
    public void setPlayerName(String playerName){
        this.playerName = playerName;
        this.set("PlayerName",this.playerName);
    }    
        
    public void setChampPlayed(String champPlayed){
        this.champPlayed = champPlayed;
        this.set("ChampPlayed",this.champPlayed);
    }
    
    public void setRole(String role){
        this.role = role;
        this.set("Role",this.role);
    }
    
    public void setLaneOpponent(String laneOpponent){
        this.laneOpponent = laneOpponent;
        this.set("LaneOpponent",this.laneOpponent);
    }
    
    public void setLaneOutcome(String laneOutcome){
        this.laneOutcome = laneOutcome;
        this.set("LaneOutcome",this.laneOutcome);
    }
    
    public void setKills(int kills){
        this.kills = kills;
        this.set("Kills",this.kills);
    }
    
    public void setDeaths(int deaths){
        this.deaths = deaths;
        this.set("Deaths",this.deaths);
    }
    
    public void setAssists(int assists){
        this.assists = assists;
        this.set("Assists",this.assists);
    }
    
    public void setNumTeammates(int numTeammates){
        this.numTeammates = numTeammates;
        this.set("NumTeammates",this.numTeammates);
    }
    
    public void setGotFirstBlood(String gotFirstBlood){
        this.gotFirstBlood = gotFirstBlood;
        this.set("GotFirstBlood",gotFirstBlood);
    }
    
    public void setGameOutcome(String gameOutcome){
        this.gameOutcome = gameOutcome;
        this.set("GameOutcome",this.gameOutcome);
    }
    
    public void setDescription(String description){
        this.description = description;
        this.set("Description",this.description);
    }
    
    public void setSubmitterName(String submitterName){
        this.submitterName = submitterName;
        this.set("SubmitterName",this.submitterName);
    }

    public static LazyList<Gameinfo> getAllGameinfos(){
        return Gameinfo.where("id>=0");
    }
    
    public int getGameNumber(){
        return (int)this.get("GameNumber");
    }
    
    public String getPlayerName(){
        return (String)this.get("PlayerName");
    }
    
    public String getChampPlayed(){
        return (String)this.get("ChampPlayed");
    }
    
    public String getRole(){
        return (String)this.get("Role");
    }
    
    public String getLaneOpponent(){
        return (String)this.get("LaneOpponent");
    }
    
    public String getLaneOutcome(){
        return (String)this.get("LaneOutcome");
    }
    
    public int getKills(){
        return (int)this.get("Kills");
    }
    
    public int getDeaths(){
        return (int)this.get("Deaths");
    }
    
    public int getAssists(){
        return (int)this.get("Assists");
    }
    
    public int getNumTeammates(){    
        return (int)this.get("NumTeammates");
    }
    
    public String getGotFirstBlood(){
        return (String)this.get("GotFirstBlood");
    }
    
    public String getGameOutcome(){
        return (String)this.get("gameOutcome");
    }
    
    public String getDescription(){
        return (String)this.get("Description");
    }
    
    public String getSubmitterName(){
        return (String)this.get("SubmitterName");
    }
}