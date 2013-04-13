package DatabaseInterfaces;

import org.javalite.activejdbc.*;

public class Champs extends Model{
    private String name;
    
    public static LazyList<Champs> getChamps(String name){
        return Champs.where("Name = '"+name+"'");
    }
    
    public static LazyList<Champs> getAllChamps(){
        return Champs.where("id > 0");
    }
    
     public void setName(String name){
        this.name = name;
        this.set("Name",this.name);
    }
    
    public String getName(){
        return (String)this.get("name");
    }
}
