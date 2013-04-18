package lolstats;

import Statistics.SpecificStats;
import Statistics.GeneralStats;
import Statistics.NumberCruncher;
import Statistics.RecordCruncher;
import DatabaseInterfaces.Champs;
import DatabaseInterfaces.Players;
import DatabaseInterfaces.Gameinfo;
import DatabaseInterfaces.Gamemapinfos;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.InitException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

/**improvements:
                 * 1. nicknames for summoner names for faster name entry
                 * 2. remote usage on client/server model - works for local networks. Gotta figure out how to get outside usage
                 * 3. user logins
                 * 4. if the person's role is adc, then add a field in gameinfos that contains the person that supported, and prompt for entry
                 * 5. if the person's role is support, then add a field in gameinfos that contains the person that was the adc, and prompt for entry
                 * 6. total - wins to compute stats?
                 * 7. centering cells
                 */

public class LolStats {
    private static LazyList<Champs> champs;
    private static LazyList<Players> players;
    
    
    /**
     * IMPROVEMENTS
     * 2. edit a game
     * 
     * 7a. add a password to all accounts in the DB - DONE
     * 7b. data - account supplied proper password
     * 7c. stats - both types do not take a password
     * 7d. mod powers take a password
     * 
     * 11. add an option for superusers to change the permissions on an existing account
     * 
     * 14. add a change password method
     */
	public static void main(String[] args) throws IOException {
            final String delCMD = "netsh advfirewall firewall delete rule name=\"MYSQL\" protocol=tcp localport=3306";
            final String makeCMD = "netsh advfirewall firewall add rule name=\"MYSQL\" dir=in action=allow protocol=TCP localport=3306";
            Runtime.getRuntime().exec(delCMD);
            Runtime.getRuntime().exec(makeCMD);
            
            
            try{
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://lolstats.no-ip.org/test", "remoteuser", "remoteuserpassword");                
                players = Players.getAllPlayers();
                champs = Champs.getAllChamps();
                Players player = userLogin();
                if(player.getSummonerName().equalsIgnoreCase("blindzubat")){
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                    System.out.println("LOOKS LIKE OP DELIVERED");
                }
                openingMenu(player);

                /*String playerName3 = "lolshoppip";
                SpecificStats statsPage = new SpecificStats(playerName3);
                statsPage.doStatsForOnePlayer();

                GeneralStats gstatsPage = new GeneralStats(playerName3);
                statsPage.doGeneralStats();*/
                Scanner input = new Scanner(System.in);
                System.out.print("Press Enter to finish");
                input.nextLine();
                Base.close();
            } catch (InitException e){
                System.out.println("Error 37. The System is down. The System is down. The System is down. Once you get this reference, text Jay and tell him to turn the internet on.");
            }
            
	}
        
        public static Players userLogin(){
            Scanner input = new Scanner(System.in);
            String username = "";
            boolean accepted = false;
            int k = 0;

            while(!accepted){
                System.out.print("Enter your summoner name: ");
                username = input.nextLine();
                
                for(k=0; k<players.size(); k++){
                    if(players.get(k).getSummonerName().equals(username)){
                        break;
                    }
                }
                accepted = true;
            }
            return players.get(k);
        }
        
        public static void openingMenu(Players player){
                Scanner input = new Scanner(System.in);
                String line;
                boolean accepted = false;
                
                System.out.println("1. Enter Stats for a game");
                System.out.println("2. Specific Stats For Player");
                System.out.println("3. General Stats For Player");
                
                if(player.getModStatus().equalsIgnoreCase("yes")){
                    System.out.println("4. Add a new player");
                }
                if(player.getSuperStatus().equalsIgnoreCase("yes")){
                    System.out.println("5. Enter new champ");
                    System.out.println("6. Make a player a mod/super");
                }
                System.out.println("Quit|Exit to terminate this runtime instance");
                 
                while(!accepted){
                    System.out.print("\tEnter your choice: ");
                    line = input.nextLine();
                    accepted = false;
                                        
                    if(line.equals("1")){
                        dataEntry(player.getSummonerName());
                    } else if (line.equals("2")){
                        startSpecificStats(player);
                    } else if(line.equals("3")){
                        startGeneralStats(player);
                    } else if(line.equals("4") && player.getModStatus().equalsIgnoreCase("yes")){
                        createNewPlayer();
                    } else if (line.equals("5") &&  player.getSuperStatus().equalsIgnoreCase("yes")){
                        createNewChamp();
                    } else if(line.equalsIgnoreCase("6") && player.getSuperStatus().equalsIgnoreCase("Yes")){
                        System.out.println("here");
                        modifyPermissions();
                    } else if(line.equalsIgnoreCase("quit")||line.equalsIgnoreCase("exit")){
                        accepted = true;
                    }
                }
        }
        
        public static void dataEntry(String username){ 
            ArrayList<String> roles = new ArrayList<>();
            Scanner input = new Scanner(System.in);
            Gamemapinfos mapinfo = new Gamemapinfos();
            Gameinfo gameinfo = new Gameinfo();
            String data;
            int intdata;
            int kills = -1;
            boolean accepted = false;
            boolean teamGotFirstBlood = false;
            boolean personallyGotFirstBlood = false;
            
            while(!accepted){
               System.out.print("Enter Place Spawned[Top/Bottom]: ");
               data = input.nextLine();
               data = fixInput(data);
               
                if(data.equals("Top")||data.equals("Bottom")){
                    accepted = true;
                    mapinfo.setPlaceSpawned(data);
                    data = "";
                }
           }
           accepted = false;
              
           while(!accepted){
               System.out.print("Enter who got first blood[Them/Us]: ");
               data = input.nextLine();
               data = fixInput(data);
               
               if(data.equals("Them")||data.equals("Us")){
                   accepted = true;
                   mapinfo.setWhoGotFirstBlood(data);
                   if(data.equals("Us")){
                       teamGotFirstBlood = true;
                   }
                   data = ""; 
               }
           }
           accepted = false;
           
           while (!accepted){
               System.out.print("Enter the pick type[Blind/Draft]: ");
               data = input.nextLine();
               data = fixInput(data);
               
               if(data.equals("Blind")||data.equals("Draft")){
                   accepted = true;
                   mapinfo.setPickType(data);
                   data = "";
               }
           }
           accepted = false;     

            while(!accepted){
                System.out.print("Enter your champion: ");
                data = input.nextLine();
                data = fixInput(data);
                if(containsChamp(champs,data)){
                    accepted = true;
                    gameinfo.setChampPlayed(data);
                    data = "";
                }
            }  
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter Role: ");
                data = input.nextLine();
                data = fixInput(data);
                if(data.equals("Top")||data.equals("Jungler")||data.equals("Support")||data.equals("Adc")||data.equals("Mid")){
                    accepted = true;
                    gameinfo.setRole(data);
                    roles.add(data);
                    data = "";
                }
            }
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter Lane Phase Opponent: ");
                data = input.nextLine();
                data = fixInput(data);
                if(containsChamp(champs,data)){
                    accepted = true;
                    gameinfo.setLaneOpponent(data);
                    data = "";
                }
            }
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter Lane Outcome[Won/Lost/Even/Other]: ");
                data = input.nextLine();
                data = fixInput(data);
                if(data.equals("Won")||data.equals("Lost")||data.equals("Even")||data.equals("Other")){
                    accepted = true;
                    gameinfo.setLaneOutcome(data);
                    data = "";
                }
            }
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter Kills: ");
		data = input.nextLine();
                if(!(data.equals(null)||(data.length()==0))){
                    if(isNumerical(data)){
                        kills = Integer.valueOf(data);
                        gameinfo.setKills(kills);
                        data = "";
                        accepted = true;
                    }
                }
            }
            accepted = false;
	    
	    while(!accepted){
                System.out.print("Enter Deaths: ");
		data = input.nextLine();
                if(!(data.equals(null)||(data.length()==0))){
                    if(isNumerical(data)){
                        intdata = Integer.valueOf(data);
                        gameinfo.setDeaths(intdata);
                        intdata = -1;
                        data = "";
                        accepted = true;
                    }
                }
            }
            accepted = false;

	    while(!accepted){
                System.out.print("Enter Assists: ");
		data = input.nextLine();
                if(!(data.equals(null)||(data.length()==0))){
                    if(isNumerical(data)){
                        intdata = Integer.valueOf(data);
                        gameinfo.setAssists(intdata);
                        intdata = -1;
                        data = "";
                        accepted = true;
                    }
                }
            }
            accepted = false;

	    while(!accepted){
                System.out.print("Enter Number of Teammates[0-4]: ");
		data = input.nextLine();
                if(!(data.equals(null)||(data.length()==0))){
                    if(isNumerical(data)){
                        intdata = Integer.valueOf(data);
                        if(intdata>=0&&intdata<=4){
                            gameinfo.setNumTeammates(intdata);
                            data = "";
                            accepted = true;
                        }
                    }
                }
            }
            accepted = false;
            
            if(teamGotFirstBlood&&kills>0){
                while(!accepted){
                    System.out.print("Got First Blood[Yes/No]: ");
                    data = input.nextLine();
                    data = fixInput(data);
                    if(data.equals("Yes")||data.equals("No")){
                            accepted = true;
                            gameinfo.setGotFirstBlood(data);
                            if(data.equals("Yes")){
                                personallyGotFirstBlood = true;
                            }
                            data = "";
                    }
                }
                accepted = false;
            } else {
                gameinfo.setGotFirstBlood("No");
            }
	
	    while(!accepted){
	    	System.out.print("Won Game[Yes/No]: ");
		data = input.nextLine();
		data = fixInput(data);
		if(data.equals("Yes")||data.equals("No")){
			accepted = true;
			gameinfo.setGameOutcome(data);
		}
	    }
	    accepted = false;
            
            while(!accepted){
                System.out.print("Enter Description: ");
                data = input.nextLine();
                
                if(data.length()<256){
                    accepted = true;
                    gameinfo.setDescription(data);
                } else {
                    System.out.println("Description  is longer than 256 characters");
                }
            }
            
            mapinfo.saveIt();
            gameinfo.setSubmitterName(username);
            gameinfo.setPlayerName(username);
            gameinfo.setGameNumber((int)Long.valueOf(String.valueOf(mapinfo.getId())).longValue());
	    gameinfo.saveIt();
            enterTeammates((int)Long.valueOf(String.valueOf(mapinfo.getId())).longValue(), gameinfo.getNumTeammates(), teamGotFirstBlood, personallyGotFirstBlood, gameinfo.getGameOutcome(), gameinfo.getRole(), roles);
        }
       
        private static void enterTeammates(int gameNumber, int numTeammates, boolean teamGotFirstBlood, boolean personallyGotFirstBlood, 
                                            String gameOutcome, String username, ArrayList<String> roles){
            ArrayList<String> teammates = new ArrayList<>();
            teammates.add(username);
            Scanner input = new Scanner(System.in);
            Gameinfo gameinfo;
            String data;
            int kills = -1;
            int intdata;
            boolean accepted = false;
            boolean firstBloodTaken = personallyGotFirstBlood;
            
            System.out.println("Game Number: "+gameNumber);
            for (int k=0; k<numTeammates; k++){
                gameinfo = new Gameinfo();
                gameinfo.setNumTeammates(numTeammates);
                gameinfo.setGameNumber(gameNumber);
                gameinfo.setGameOutcome(gameOutcome);
                
                
                while(!accepted){
                    System.out.print("Enter your teammate's Summoner Name: ");
                    data = input.nextLine();
                    data = data.toLowerCase().trim();
                    if(containsName(players, data)){
                        if(contains(teammates,data)==false){
                           accepted = true;
                           gameinfo.setPlayerName(data);
                           teammates.add(data);
                           data = "";
                        } else { 
                            System.out.println("You already entered stats for that teammate. Try again");
                        }
                    }   
                }
                accepted = false;

                while(!accepted){
                    System.out.print("Enter your teammate's champion: ");
                    data = input.nextLine();
                    data = fixInput(data);
                    if(containsChamp(champs,data)){
                        accepted = true;
                        gameinfo.setChampPlayed(data);
                        data = "";
                    }
                }  
                accepted = false;

                while(!accepted){
                    System.out.print("Enter Role: ");
                    data = input.nextLine();
                    data = fixInput(data);
                    if(data.equals("Top")||data.equals("Jungler")||data.equals("Support")||data.equals("Adc")||data.equals("Mid")){
                        if(contains(roles,data)==false){
                            accepted = true;
                            gameinfo.setRole(data);
                            roles.add(data);
                            data = "";
                        }
                    }
                }
                accepted = false;

                while(!accepted){
                    System.out.print("Enter Lane Phase Opponent: ");
                    data = input.nextLine();
                    data = fixInput(data);
                    if(containsChamp(champs,data)){
                        accepted = true;
                        gameinfo.setLaneOpponent(data);
                        data = "";
                    }
                }
                accepted = false;

                while(!accepted){
                    System.out.print("Enter Lane Phase Outcome[Won/Lost/Even/Other]: ");
                    data = input.nextLine();
                    data = fixInput(data);
                    if(data.equals("Won")||data.equals("Lost")||data.equals("Even")||data.equals("Other")){
                        accepted = true;
                        gameinfo.setLaneOutcome(data);
                        data = "";
                    }
                }
                accepted = false;
                
               while(!accepted){
                System.out.print("Enter Kills: ");
		data = input.nextLine();
                if(!(data.equals(null)||(data.length()==0))){
                    if(isNumerical(data)){
                        kills = Integer.valueOf(data);
                        gameinfo.setKills(kills);
                        data = "";
                        accepted = true;
                    }
                }
            }
            accepted = false;

                while(!accepted){
                    System.out.print("Enter Deaths: ");
                    data = input.nextLine();
                    if(!(data.equals(null)||(data.length()==0))){
                        if(isNumerical(data)){
                            intdata = Integer.valueOf(data);
                            gameinfo.setDeaths(intdata);
                            intdata = -1;
                            data = "";
                            accepted = true;
                        }
                    }
                }
                accepted = false;

                while(!accepted){
                    System.out.print("Enter Assists: ");
                    data = input.nextLine();
                    if(!(data.equals(null)||(data.length()==0))){
                        if(isNumerical(data)){
                            intdata = Integer.valueOf(data);
                            gameinfo.setAssists(intdata);
                            intdata = -1;
                            data = "";
                            accepted = true;
                        }
                    }
                }
                accepted = false;
                
                
                if(!(k<(numTeammates-1))&&numTeammates==4&&teamGotFirstBlood&&kills>0&&!firstBloodTaken){
                    //IN THE GUI, MAKE SURE THAT IF YOU ENTER THIS CASE, THAT THIS TEAMMATE HAS KILLS>0
                    gameinfo.setGotFirstBlood("Yes");
                } else if(teamGotFirstBlood&&kills>0&&!firstBloodTaken){
                        while(!accepted){
                            System.out.print("Got First Blood[Yes/No]: ");
                            data = input.nextLine();
                            data = fixInput(data);
                            if(data.equals("Yes")||data.equals("No")){
                                    accepted = true;
                                    gameinfo.setGotFirstBlood(data);
                                    if(data.equals("Yes")){
                                        firstBloodTaken = true;
                                    }
                                    data = "";
                            }
                        }
                    accepted = false;
                } else {
                    gameinfo.setGotFirstBlood("No");
                }
                
                System.out.print("Enter Description: ");
                data = input.nextLine();
                gameinfo.setSubmitterName(username);
                gameinfo.setDescription(data);
                
                gameinfo.saveIt();
            }
        }
           
        private static void startSpecificStats(Players player){
            Scanner input = new Scanner(System.in);
            String playerName;
            boolean innerAccepted = false;
            while(!innerAccepted){
                System.out.print("Enter PlayerName for detailed stats view: ");
                playerName = input.nextLine();
                if(containsName(players, playerName)){
                    SpecificStats statsPage = new SpecificStats(playerName, player.getSummonerName());
                    try{
                        statsPage.doStatsForOnePlayer();
                    } catch (IOException e){
                        System.out.println("The stats file may be currently open. Make sure that it is closed");
                        System.out.print("Press Enter to continue");
                        input.nextLine();
                        startSpecificStats(player);
                    }
                    innerAccepted = true;
                }
            }
        }
        
        private static void startGeneralStats(Players player){
            boolean innerAccepted = false;
            String playerName;
            Scanner input = new Scanner(System.in);
            while(!innerAccepted){
                
                System.out.print("Enter PlayerName for general stats view: ");
                playerName = input.nextLine();
                if(containsName(players, playerName)){
                    
                    GeneralStats genStatsPage = new GeneralStats(playerName, player.getSummonerName());
                    try{
                        genStatsPage.doGeneralStats();
                    } catch (IOException e){
                        
                        System.out.println("The stats file may be currently open. Make sure that it is closed");
                        System.out.print("Press Enter to continue");
                        input.nextLine();
                        startGeneralStats(player);
                    }
                }
                innerAccepted = true;
            }
        }
        
        private static void createNewPlayer(){
            boolean accepted = false;
            Scanner input = new Scanner(System.in);
            Players player = new Players();
            
            while(!accepted){
                System.out.print("Enter First Name: ");
                String firstName1 = input.nextLine();
                System.out.print("Confirm First Name: ");
                String firstName2 = input.nextLine();
                           
                if(firstName1.equals(firstName2)){
                    player.setFirstName(firstName1);
                    accepted = true;
                }
            }
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter Last Name: ");
                String lastName1 = input.nextLine();
                System.out.print("Confirm Last Name: ");
                String lastName2 = input.nextLine();
                
                if(lastName1.equals(lastName2)){
                    player.setLastName(lastName1);
                    accepted = true;
                }
            }
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter Summoner Name: ");
                String username1 = input.nextLine();
                System.out.print("Confirm Summoner Name: ");
                String username2 = input.nextLine();
                
                if(username1.equals(username2)){
                    if(!containsName(players, username1)){                    
                        player.setSummonerName(username1);
                        player.saveIt();
                    } else {
                        System.out.println("Summoner name already exists");
                    }
                    accepted = true;
                }
            }
        }
        
        private static void createNewChamp(){
            boolean innerAccepted = false;
            Scanner input = new Scanner(System.in);
            while(!innerAccepted){
                System.out.print("Enter new champ: ");
                String newname1 = input.nextLine();
                System.out.print("Confirm new champ: ");
                String newname2 = input.nextLine();
                if(newname1.equals(newname2)){
                    innerAccepted = true;
                    Champs champ = new Champs();
                    champ.setName(newname1);
                                
                    boolean contained = false;
                    for(int k=0; k<champs.size()&&!contained; k++){
                        if(champs.get(k).getName().equalsIgnoreCase(champ.getName())){
                            contained = true;
                        }   
                    }
                               
                    if(!contained){
                        champ.saveIt();
                    } else {
                        System.out.println("Champion already contained in table");
                    }
                } else {
                    System.out.println("Names didn't match. Try again.");
                }
            }
        }
        
        private static void modifyPermissions(){
            Scanner input = new Scanner(System.in);
            boolean accepted = false;
            String choice = "";
            Players player = new Players();
            for(int k=0; k<players.size(); k++){
                System.out.println("\t" + k + ": " + players.get(k).getSummonerName());
            }
            
            while(!accepted){
                System.out.print("\tEnter a choice of user to change: ");
                choice = input.nextLine();
                
                if(isNumerical(choice) && (Integer.valueOf(choice)<players.size() && Integer.valueOf(choice)>=0)){//using lazy evaluation to make sure that input is numerical before check the int value. like a baws.
                    accepted = true;
                } else {
                    System.out.println("Enter valid input");
                    
                }
            }
            player = players.get(Integer.valueOf(choice));
            System.out.println(player.getSummonerName());
            accepted = false;
            
            while(!accepted){
                System.out.print("Enter choice for modship: ");
                choice = input.nextLine();
                if(choice.equalsIgnoreCase("Yes")||choice.equalsIgnoreCase("No")){
                    accepted = true;
                }
            }
            accepted = false;
            if(choice.equalsIgnoreCase("Yes")){
                player.setModStatus("Yes");
                player.saveIt();
            } else if(choice.equalsIgnoreCase("No")){
                player.setModStatus("No");
                player.saveIt();
            }
            
            
            while(!accepted){
                System.out.print("Enter choice for supership: ");
                choice = input.nextLine();
                if(choice.equalsIgnoreCase("Yes")||choice.equalsIgnoreCase("No")){
                    accepted = true;
                }
            }
            if(choice.equalsIgnoreCase("Yes")){
                player.setSuperStatus("Yes");
                player.saveIt();
            } else if(choice.equalsIgnoreCase("No")){
                player.setModStatus("No");
                player.saveIt();
            }  
        }
        
        public static String fixInput(String inputString){//not satisfied
            if(inputString==null || inputString.length()==0){
                return inputString;
            } 
            inputString = inputString.toLowerCase().trim();//this makes the whole body of the method necessary for runtime data entry validation
            String replacedString = (inputString.substring(0,1)).toUpperCase().concat(inputString.substring(1));;
                        
            for(int k=0; k<replacedString.length(); k++){           
                if(replacedString.charAt(k)==' '){
                    replacedString = replacedString.substring(0, k+1);
                    replacedString = replacedString.concat(String.valueOf(inputString.charAt(k+1)).toUpperCase());
                    replacedString = replacedString.concat(inputString.substring(k+2));
                }
            }
            return replacedString;
        }
        
        public static boolean isNumerical(String input){
            boolean isNumber = true;
            for(int k=0; k<input.length()&&isNumber; k++){
                if(!(String.valueOf(input.charAt(k))).matches("[0-9]")){
                    isNumber = false;
                }
            }
            return isNumber;
        }
        
        public static boolean containsChamp(LazyList<Champs> list, String name){
            for(int k=0; k<list.size(); k++){
                if(list.get(k).getName().equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
        
        public static boolean containsName(LazyList<Players> list, String name){
            name = name.toLowerCase().trim();
            for(int k=0; k<list.size(); k++){
                if (list.get(k).getSummonerName().equalsIgnoreCase(name)){
                    return true;
                }
            }
            return false;
        }
   
        public static boolean contains(ArrayList<String> list, String value){
            for(int k=0; k<list.size(); k++){
                if(list.get(k).equals(value)){
                    return true;
                }
            }
            return false;
        }   
}
