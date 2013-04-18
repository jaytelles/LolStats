package DatabaseInterfaces;

import org.javalite.activejdbc.Model;

public class Gamemapinfos extends Model{
    private String placeSpawned;
    private String whoGotFirstBlood;//Them OR Us
    private String pickType;
    
    public void setPlaceSpawned(String placeSpawned){
        this.placeSpawned = placeSpawned;
        this.set("PlaceSpawned",this.placeSpawned);
    }
    
    public void setWhoGotFirstBlood(String whoGotFirstBlood){
        this.whoGotFirstBlood = whoGotFirstBlood;
        this.set("WhoGotFirstBlood",this.whoGotFirstBlood);
    }
    
    public void setPickType(String pickType){
        this.pickType = pickType;
        this.set("PickType",this.pickType);
    }
    
    public int getGameNumber(){
        return (int)this.get("GameNumber");
    }
    
    public String getPlaceSpawned(){
        return (String)this.get("PlaceSpawned");
    }
    
    public String getWhoGotFirstBlood(){
        return (String)this.get("FirstBlood");
    }
    
    public String getPickType(){
        return (String)this.get("PickType");
    }
}