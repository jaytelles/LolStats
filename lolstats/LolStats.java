package lolstats;

import Statistics.SpecificStats;
import Statistics.GeneralStats;
import DatabaseInterfaces.Champs;
import DatabaseInterfaces.Players;
import DatabaseInterfaces.Gameinfo;
import DatabaseInterfaces.Gamemapinfos;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.InitException;


/**improvements:
                 * 1. nicknames for summoner names for faster name entry
                 * 2. remote usage on client/server model - works for local networks. Gotta figure out how to get outside usage
                 * 3. user logins
                 * 4. if the person's role is adc, then add a field in gameinfos that contains the person that supported, and prompt for entry
                 * 5. if the person's role is support, then add a field in gameinfos that contains the person that was the adc, and prompt for entry
                 * 6. total - wins to compute stats?
                 * 7. centering cells
                 * 
                 * 
                 * 
                 */

public class LolStats {
    private static LazyList<Champs> champs;
    private static LazyList<Players> players;

    /**
     * IMPROVEMENTS
     * 2. edit a game
     *
     * 
     * v 0.5
     * 15. add an output for description
     * 
     * 17. make the champs output alphabetical - DONE   
     * 
     * 18. finish the outputs that come from gamemapinfo
     * 
     * 19. in the number of teammates section, omit the "0" row if playername does not match username - DONE
     * 
     * 20. prompt to continue entering teammate stats after each teammate. that way you can have 3 teammates, but only enter stats for 2 - DONE
     * 
     * 21. rename a player function - DONE
     * 
     * 22. make sure that the same champ cannot be entered twice for the user's team, as well as no champ twice on the enemy team - DONE
     * 
     * 23. refactor the roles, champs, and enemy champs list to a single object, then make a list of those objects
     * 
     * 24. stats sections now show alphabetized lists of champions/matchups - DONE.
     * 
     * 
     * V 0.6.0
     * 25. instead of typing out names for stats, have the user select from a list of all players they've played with
     * 
     * 26. add the "help" option so that users can see the options again - DONE
     * 
     * 27. when changing a username, and the entered username already exists, make sure to return the user to the change prompt, rather than the main menu. 
     *      Return the user to the main menu after three failed attempts!
     * 
     * 28. When entering a new player, the user has three tries to get each field correct before returning them to the main menu
     * 
     * 29. When entering a new champ, the user has three tries to get each field correct before returning them to the main menu
     * 
     */
    
    /** BUGS
     //* 1. Fixed a bug where exit was not an option after doing general stats - done
     * 
     * 2. General stats "people played with" for a player that is not the user - done
     * 
     //* 3. enterTeammates description is not checked for length - done
     * 
     //* 4. entered teammate data for a test game, and the playername for all teammates was morello. the username was lolstats dafuq? - DONE. 
     *      accepted value not reset, and username was never actually set for the teammate gameinfos.
     * 
    // * 5. the prompt to continue entering teammate data is always displayed, even when the teammate entered is the lat teammate - DONE
    *       forgot to add check for that case
    * 
    //* 6. error when exit is chosen as the first option. program execution asks for a pw then goes to player creation - probably related to permissions - DONE
    *       added parens to the permissions check in option for adding a new player
    * 
    //* 7. fixed error where user not notified that hte program would not work without an active internet connection.
    * 
    //* 8. Adding a user does not input the user as all lowercase - Resolved. Added a toLowerCase to the string passed to the DB
    *   Additionally, this might affect the change username function. Check and see. - It does not.
    * 
    //* 9. I added a user, then could not add that person to the game i was entering stats for. 
    *   Close/Reopen connection to repopulate relations? - No, just update the list of players after each change.
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
                }
                openingMenu(player);

                
                Base.close();
            } catch (InitException e){
                System.out.println("Error 37. The System is down. The System is down. The System is down. Once you get this reference, text Jay and tell him to turn the internet on.");
                System.out.println("Or, alternatively, make sure that you have an active internet connection.");
            }
            Scanner input = new Scanner(System.in);
            System.out.print("Press Enter to finish");
            input.nextLine();
	}
        
        public static Players userLogin(){
            Scanner input = new Scanner(System.in);
            String username = "";
            boolean accepted = false;
            int k = 0;
            int tries = 0;

            while(!accepted&&tries<3){
                System.out.print("Enter your summoner name: ");
                username = input.nextLine();
                
                for(k=0; k<players.size()&&!accepted; k++){
                    if(players.get(k).getSummonerName().equalsIgnoreCase(username)){
                        accepted = true;
                    }
                }
                tries++;
            }
            if(tries<=3){
                return players.get(k-1);
            } else {
                System.out.println("Username not recognized. Talk to Jay for an account");
                System.exit(0);
                return null;
            }
        }
        
        public static void displayOptions(Players player){
            System.out.println("0. Change password");
            System.out.println("1. Enter Stats for a game");
            System.out.println("2. Specific Stats For Player");
            System.out.println("3. General Stats For Player");
                
            if(player.getModStatus().equalsIgnoreCase("yes")||player.getSuperStatus().equalsIgnoreCase("yes")){
                System.out.println("4. Add a new player");
            }
            if(player.getSuperStatus().equalsIgnoreCase("yes")){
                System.out.println("5. Enter new champ");
                System.out.println("6. Make a player a mod/super");
                System.out.println("7. Change a player's summoner name");
            }
            System.out.println("Quit|Exit to terminate this runtime instance");
        }
        
        public static void openingMenu(Players player){
                Scanner input = new Scanner(System.in);
                String line;
                boolean accepted = false;
                boolean passwordConfirmed = false;
                displayOptions(player);
                 
                while(!accepted){
                    System.out.print("\tEnter your choice: ");
                    line = input.nextLine();
                    accepted = false;
                                        
                    if(line.equals("0")){
                        if(passwordConfirmed||userLogin(player)){
                            changePassword(player);
                        } else {
                            System.out.println("Wrong password.");
                        }
                    } else if(line.equals("1")){
                        if(passwordConfirmed||userLogin(player)){
                            dataEntry(player.getSummonerName());
                            passwordConfirmed = true;
                        } else {
                            System.out.println("Wrong password.");
                        }
                    } else if (line.equals("2")){
                        startSpecificStats(player);
                    } else if(line.equals("3")){
                        startGeneralStats(player);
                    } else if(line.equals("4") && (player.getModStatus().equalsIgnoreCase("yes")||player.getSuperStatus().equalsIgnoreCase("yes"))){
                        if(passwordConfirmed||userLogin(player)){
                            createNewPlayer();
                            passwordConfirmed = true;
                        } else {
                            System.out.println("Wrong password.");
                        }
                        players = Players.getAllPlayers();
                    } else if (line.equals("5") &&  player.getSuperStatus().equalsIgnoreCase("yes")){
                        if(passwordConfirmed||userLogin(player)){
                            createNewChamp();
                            passwordConfirmed = true;
                        } else {
                            System.out.println("Wrong password.");
                        }
                        champs = Champs.getAllChamps();
                    } else if(line.equalsIgnoreCase("6") && player.getSuperStatus().equalsIgnoreCase("Yes")){
                        if(passwordConfirmed||userLogin(player)){
                            modifyPermissions();
                            passwordConfirmed = true;
                        } else {
                            System.out.println("Wrong password.");
                        }
                        players = Players.getAllPlayers();
                    } else if(line.equalsIgnoreCase("7")&&player.getSuperStatus().equalsIgnoreCase("Yes")){
                        if(passwordConfirmed||userLogin(player)){
                            changeSummonerName();
                            passwordConfirmed = true;
                        }
                        players = Players.getAllPlayers();
                    } else if(line.equalsIgnoreCase("help")){
                        displayOptions(player);
                    }else if(line.equalsIgnoreCase("quit")||line.equalsIgnoreCase("exit")){
                        accepted = true;
                    }
                }
        }
        
        public static void dataEntry(String username){ 
            ArrayList<String> roles = new ArrayList<>();
            ArrayList<String> alliedChampsList = new ArrayList<>();
            ArrayList<String> enemyChampsList = new ArrayList<>();
            Scanner input = new Scanner(System.in);
            Gamemapinfos mapinfo = new Gamemapinfos();
            Gameinfo gameinfo = new Gameinfo();
            String data;
            int intdata;
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
                    alliedChampsList.add(data);
                    accepted = true;
                    gameinfo.setChampPlayed(data);
                    data = "";
                } else {
                    System.out.println("That champion does not exist");
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
                    enemyChampsList.add(data);
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
                        intdata = Integer.valueOf(data);
                        gameinfo.setKills(intdata);
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
            
            if(teamGotFirstBlood&&gameinfo.getKills()>0){
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
            accepted = false;
            
            
            boolean confirmed = mapinfo.saveIt();
            while(!confirmed){
                confirmed = mapinfo.saveIt();
            }
            
            gameinfo.setSubmitterName(username);
            gameinfo.setPlayerName(username);
            gameinfo.setGameNumber((int)Long.valueOf(String.valueOf(mapinfo.getId())).longValue());
            
            confirmed = gameinfo.saveIt();
            while(!confirmed){
                confirmed = gameinfo.saveIt();
            }
            
            while(!accepted && gameinfo.getNumTeammates()>0){
                System.out.print("Do you want to add stats for your teammates? [yes/no]: ");
                data = input.nextLine();
                if(data.equalsIgnoreCase("Yes")){
                    accepted = true;
                    enterTeammates((int)Long.valueOf(String.valueOf(mapinfo.getId())).longValue(), gameinfo.getNumTeammates(), 
                    teamGotFirstBlood, personallyGotFirstBlood, gameinfo.getGameOutcome(), username, roles, alliedChampsList, enemyChampsList);
                    
                } else if(data.equalsIgnoreCase("No")){
                    accepted = true;
                }
            } 
        }
       
        private static void enterTeammates(int gameNumber, int numTeammates, boolean teamGotFirstBlood, boolean personallyGotFirstBlood, 
                                            String gameOutcome, String username, 
                                            ArrayList<String> roles, ArrayList<String> alliedChampsList, ArrayList<String> enemyChampsList){
            ArrayList<String> teammates = new ArrayList<>();
            teammates.add(username);
            Scanner input = new Scanner(System.in);
            Gameinfo gameinfo;
            String data;
            int kills = -1;
            int intdata;
            boolean accepted = false;
            boolean firstBloodTaken = personallyGotFirstBlood;
            boolean continueEntry = true;
           
            
            for (int k=0; k<numTeammates&&continueEntry; k++){
                gameinfo = new Gameinfo();
                gameinfo.setSubmitterName(username);
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
                        if(!alliedChampsList.contains(data)){
                            alliedChampsList.add(data);
                            accepted = true;
                            gameinfo.setChampPlayed(data);
                            data = "";
                        } else {
                            System.out.println("That champion has already been entered for your team.");
                        }
                    } else {
                        System.out.println("That champion does not exist");
                    }
                }  
                accepted = false;

                while(!accepted){
                    System.out.print("Enter Role: ");
                    data = input.nextLine();
                    data = fixInput(data); 
                    if(data.equals("Top")||data.equals("Jungler")||data.equals("Support")||data.equals("Adc")||data.equals("Mid")){
                        if(!roles.contains(data)){
                            accepted = true;
                            gameinfo.setRole(data);
                            roles.add(data);
                            data = "";
                        } else {
                            System.out.println("That role has already been assigned");
                        }
                    }
                }
                accepted = false;

                while(!accepted){
                    System.out.print("Enter Lane Phase Opponent: ");
                    data = input.nextLine();
                    data = fixInput(data);
                    if(containsChamp(champs,data)){
                        if(!enemyChampsList.contains(data)){
                            enemyChampsList.add(data);
                            accepted = true;
                            gameinfo.setLaneOpponent(data);
                            data = "";
                        } else {
                            System.out.println("That champion has already been entered for the opposing team.");
                        }
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
                accepted = false;
                
                boolean confirmed = gameinfo.saveIt();
                while(!confirmed){
                    confirmed = gameinfo.saveIt();
                }
                
                
                while(!accepted&&k<numTeammates-1){
                    System.out.print("Continue Entry?[yes/no]: ");
                    data = input.nextLine();
                    
                    if(data.equalsIgnoreCase("yes")){
                        accepted = true;
                    } else if (data.equalsIgnoreCase("no")){
                        accepted = true;
                        continueEntry = false;
                    }
                }
                accepted = false;
            }
        }
           
        private static void startSpecificStats(Players player){
            Players statsPlayer = new Players();
            Scanner input = new Scanner(System.in);
            String choice = "";
            boolean accepted = false;
            
            for(int k=0; k<players.size(); k++){
                System.out.println("\t" + k + ": " + players.get(k).getSummonerName());
                
            }
            while(!accepted){
                System.out.print("\tEnter the number of the user to do stats for: ");
                choice = input.nextLine();
                
                if(isNumerical(choice) && (Integer.valueOf(choice)<players.size() && Integer.valueOf(choice)>=0)){//using lazy evaluation to make sure that input is numerical before check the int value. like a baws.
                    accepted = true;
                } else {
                    System.out.println("Enter valid input");
                    
                }
            }
            
            statsPlayer = players.get(Integer.valueOf(choice));
            //the player you're doing stats for, then the current user
            SpecificStats statsPage = new SpecificStats(statsPlayer.getSummonerName(), player.getSummonerName());
            try{
                statsPage.doStatsForOnePlayer();
            } catch (IOException e){
                    System.out.println("The stats file may be currently open. Make sure that it is closed");
                    System.out.print("Press Enter to continue");
                    input.nextLine();
                    startSpecificStats(statsPlayer);
            }
        }
        
        private static void startGeneralStats(Players player){
            Players statsPlayer = new Players();
            Scanner input = new Scanner(System.in);
            String choice = "";
            boolean accepted = false;
            
            for(int k=0; k<players.size(); k++){
                System.out.println("\t" + k + ": " + players.get(k).getSummonerName());
                
            }
            while(!accepted){
                System.out.print("\tEnter the number of the user to do stats for: ");
                choice = input.nextLine();
                
                if(isNumerical(choice) && (Integer.valueOf(choice)<players.size() && Integer.valueOf(choice)>=0)){//using lazy evaluation to make sure that input is numerical before check the int value. like a baws.
                    accepted = true;
                } else {
                    System.out.println("Enter valid input");
                    
                }
            }
            
            statsPlayer = players.get(Integer.valueOf(choice));
            //the player you're doing stats for, then the current user
            GeneralStats statsPage = new GeneralStats(statsPlayer.getSummonerName(), player.getSummonerName());
            try{
                statsPage.doGeneralStats();
            } catch (IOException e){
                    System.out.println("The stats file may be currently open. Make sure that it is closed");
                    System.out.print("Press Enter to continue");
                    input.nextLine();
                    startSpecificStats(statsPlayer);
            }
        }
        
        private static void createNewPlayer(){
            boolean accepted = false;
            Scanner input = new Scanner(System.in);
            Players player = new Players();
            int tries = 0;
            
            while(!accepted&&tries<3){
                System.out.print("Enter First Name: ");
                String firstName1 = input.nextLine();
                System.out.print("Confirm First Name: ");
                String firstName2 = input.nextLine();
                           
                if(firstName1.equals(firstName2)){
                    player.setFirstName(firstName1);
                    accepted = true;
                    tries = 0;
                } else {
                    tries++;
                }
            }
            accepted = false;
            
            while(!accepted&&tries<3){
                System.out.print("Enter Last Name: ");
                String lastName1 = input.nextLine();
                System.out.print("Confirm Last Name: ");
                String lastName2 = input.nextLine();
                
                if(lastName1.equals(lastName2)){
                    player.setLastName(lastName1);
                    accepted = true;
                    tries = 0;
                } else {
                    tries++;
                }
            }
            accepted = false;
            
            while(!accepted&&tries<3){
                System.out.print("Enter Summoner Name: ");
                String username1 = input.nextLine();
                System.out.print("Confirm Summoner Name: ");
                String username2 = input.nextLine();
                
                if(username1.equals(username2)){
                    if(!containsName(players, username1)){                    
                        player.setSummonerName(username1.toLowerCase());
                        boolean confirmed = player.saveIt();
                        while(!confirmed){
                            accepted = true;
                            tries = 0;
                            confirmed = player.saveIt();
                        }
                    } else {
                        System.out.println("Summoner name already exists");
                        tries++;
                    }
                } else {
                    tries++;
                }
            }
            
            if(tries==3){
                System.out.println("You tried unsuccessfully to enter info 3 times. Returning you to the main menu");
            }
        }
        
        private static void createNewChamp(){
            boolean accepted = false;
            Scanner input = new Scanner(System.in);
            int tries = 0;
            while(!accepted&&tries<3){
                System.out.print("Enter new champ: ");
                String newname1 = input.nextLine();
                System.out.print("Confirm new champ: ");
                String newname2 = input.nextLine();
                if(newname1.equals(newname2)){
                    Champs champ = new Champs();
                    champ.setName(newname1);      
                    boolean contained = false;
                    for(int k=0; k<champs.size()&&!contained; k++){
                        if(champs.get(k).getName().equalsIgnoreCase(champ.getName())){
                            contained = true;
                        }   
                    }
                               
                    if(!contained){
                        accepted = true;
                        boolean confirmed = champ.saveIt();
                        while(!confirmed){
                            confirmed = champ.saveIt();
                        }
                    } else {
                        System.out.println("Champion already contained in table");
                        tries++;
                    }
                } else {
                    System.out.println("Names didn't match. Try again.");
                    tries++;
                }
            }
            if(tries>=3){
                System.out.println("Invalid inputs. Returning you to the main menu");
            }
        }
        
        private static void modifyPermissions(){
            Scanner input = new Scanner(System.in);
            boolean accepted = false;
            boolean confirm = false;
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
            //System.out.println(player.getSummonerName());
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
                confirm = player.saveIt();
                while(!confirm){
                    confirm = player.saveIt();
                }
            } else if(choice.equalsIgnoreCase("No")){
                player.setModStatus("No");
                confirm = player.saveIt();
                while(!confirm){
                    confirm = player.saveIt();
                }
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
                confirm = player.saveIt();
                while(!confirm){
                    confirm = player.saveIt();
                }
            } else if(choice.equalsIgnoreCase("No")){
                player.setSuperStatus("No");
                confirm = player.saveIt();
                while(!confirm){
                    confirm = player.saveIt();
                }
            }  
        }
        
        private static void changeSummonerName(){
            Players player = new Players();
            Scanner input = new Scanner(System.in);
            String choice = "";
            String oldname;
            String newname1 = "";
            String newname2 = "";
            boolean accepted = false;
            boolean confirmed = false;
            
            for(int k=0; k<players.size(); k++){
                System.out.println("\t" + k + ": " + players.get(k).getSummonerName());
                
            }
            while(!accepted){
                System.out.print("\tEnter the number of the user to change: ");
                choice = input.nextLine();
                
                if(isNumerical(choice) && (Integer.valueOf(choice)<players.size() && Integer.valueOf(choice)>=0)){//using lazy evaluation to make sure that input is numerical before check the int value. like a baws.
                    accepted = true;
                } else {
                    System.out.println("Enter valid input");
                    
                }
            }
            
            player = players.get(Integer.valueOf(choice));
            oldname = player.getSummonerName();
             
            accepted = false;
            int tries = 0;
            while(!accepted&&tries<3){
               System.out.print("Enter New Summoner Name: ");
               newname1 = input.nextLine();
               System.out.print("Confirm New Summoner Name: ");
               newname2 = input.nextLine();
               
               if(newname1.equals(newname2)&&!(newname1.equals("")||newname2.equals(" "))){
                   if(!containsName(players, newname1)){
                       newname1 = newname1.toLowerCase();
                       player.setSummonerName(newname1);
                       accepted = true;
                       do{
                           confirmed = player.saveIt();
                       } while(!confirmed);
                   } else {
                       System.out.println("Summoner name already exists");
                       tries++;
                   } 
               }
            }
             
            if(tries<3){
                LazyList<Gameinfo> games = Gameinfo.getAllGameinfos();
                boolean changed = false;
                for(int k=0; k<games.size(); k++){
                    confirmed = false;
                    if(games.get(k).getSubmitterName().equalsIgnoreCase(oldname)){
                        games.get(k).setSubmitterName(player.getSummonerName());
                        changed = true;
                    }

                    if(games.get(k).getPlayerName().equalsIgnoreCase(oldname)){
                        games.get(k).setPlayerName(player.getSummonerName());
                        changed = true;
                    }

                    if(changed){
                        do{
                            confirmed = games.get(k).saveIt();
                        } while(!confirmed);
                        changed = false;
                    }
                }
            } else {
                System.out.println("Invalid inputs. Returning you to the main menu");
            }
        }
        
        private static void changePassword(Players player){
            boolean accepted = false;
            Scanner input = new Scanner(System.in);
            String pw1 = "";
            String pw2;
            int tries = 0;
            while(!accepted&&tries<3){
                System.out.print("Enter new password: ");
                pw1 = input.nextLine();
                System.out.print("Confirm password: ");
                pw2 = input.nextLine();
                
                if(pw1.equals(pw2)){
                    accepted = true;
                    player.setPassword(pw1);
                    boolean confirmed = false;
                    do{
                        confirmed = player.saveIt();
                    }while(!confirmed);
                    System.out.println("Password successfully changed");
                } else {
                    System.out.println("Inputs do not match; Try again.");
                    tries++;
                }
            }
            if(tries==3){
                System.out.println("Invalid inputs. Returning you to the main menu");
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
        
        public static boolean userLogin(Players player){
            Scanner input = new Scanner(System.in);
            String choice;
            boolean accepted = false;
            
            for(int k=0; k<3&&!accepted; k++){
                System.out.print("Enter your password: ");
                choice = input.nextLine();
                
                if(choice.equals(player.getPassword())){
                    accepted = true;
                }
            }
            
            return accepted;
        }
}
