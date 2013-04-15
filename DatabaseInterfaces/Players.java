package DatabaseInterfaces;

import org.javalite.activejdbc.*;

public class Players extends Model{
    private String firstName;
    private String lastName;
    private String summonerName;
    
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
        this.set("FirstName",this.firstName);
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
        this.set("LastName",this.lastName);
    }
    
    public void setSummonerName(String summonerName){
        this.summonerName = summonerName;
        this.set("SummonerName",this.summonerName);
    }
    
    public static LazyList<Players> getAllPlayers(){
        return Players.where("id > 0");
    }
    
    public String getFirstName(){
        return (String)this.get("FirstName");
    }
    
    public String getLastName(){
        return (String)this.get("LastName");
    }
    
    public String getSummonerName(){
        return (String)this.get("SummonerName");
    }
    
   
}
