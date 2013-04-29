package lolstats;

public class MenuObject{//menuinfo?
    private String summonerName;
    private String role;
    private String champPlayed;
    private String enemyChamp;
    
    
    public MenuObject(){
        summonerName = role = champPlayed = enemyChamp = "";
    }
    
    public MenuObject(String summonerName, String role, String champPlayed, String enemyChamp){
        this.summonerName = summonerName;
        this.role = role;
        this.champPlayed = champPlayed;
        this.enemyChamp = enemyChamp;
    }
    
    public String getSummonerName(){
        return summonerName;
    }
    
    public String getRole(){
        return role;
    }
    
    public String getChampPlayed(){
        return champPlayed;
    }
    
    public String getEnemyChamp(){
        return enemyChamp;
    }
    
    
    public void setSummonerName(String summonerName){
        this.summonerName = summonerName;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public void setChampPlayed(String champPlayed){
        this.champPlayed = champPlayed;
    }
    
    public void setEnemyChamp(String enemyChamp){
        this.enemyChamp = enemyChamp;
    }
}