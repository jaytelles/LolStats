package DatabaseInterfaces;

import org.javalite.activejdbc.*;

public class Players extends Model{
    private String firstName;
    private String lastName;
    private String summonerName;
    private String modStatus;
    private String superStatus;
    
    
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
    
    public void setModStatus(String status){
        if (status.equalsIgnoreCase("Yes")||status.equalsIgnoreCase("No")){
            modStatus = status;
            this.set("ModStatus",this.modStatus);
        }
    }
    
    private void setSuperStatus(String status){
        if(status.equalsIgnoreCase("Yes")||status.equalsIgnoreCase("No")){
            superStatus = status;
            this.set("SuperStatus",this.superStatus);
        }
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
    
    public String getModStatus(){
        return (String) this.get("ModStatus");
    }
    
    public String getSuperStatus(){
        return (String)this.get("SuperStatus");
    }
}
