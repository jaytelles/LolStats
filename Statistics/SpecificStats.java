package Statistics;

import DatabaseInterfaces.Gameinfo;
import Statistics.NumberCruncher;
import Statistics.RecordCruncher;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SpecificStats extends Stats{    
    public SpecificStats(String playerName, String username){
        rowPosition = 0;
        this.playerName = playerName;
        this.username = username;
        games = RecordCruncher.filterUsers(Gameinfo.getAllGameinfos(), username);
        this.games = RecordCruncher.filterPlayers(games,playerName);
    }
        
    public void doStatsForOnePlayer() throws IOException{
            //Takes as input an arraylist of gameinfos for just one player. **have to filter the list before this call
            //this is meant to be a very detailed view
            Workbook wb = new HSSFWorkbook();
            Sheet sh = wb.createSheet("Sheet1");
            FileOutputStream fos = new FileOutputStream(username + "'s stats for " + playerName+" specific.xls");            

            rowPosition = super.writeStatsTotalRowColumnHeader(sh,rowPosition, 0);
            
            rowPosition = super.doStatsTotalRow(sh,games,rowPosition,0);
            
            rowPosition++;
            rowPosition = super.writeStatsRowColumnHeader(sh,rowPosition,0,"Role",false);
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Support"), rowPosition, 0, games.size(),"Support");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "ADC"), rowPosition, 0, games.size(),"ADC");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Mid"), rowPosition, 0, games.size(),"Mid");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Jungler"), rowPosition, 0, games.size(),"Jungler");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Top"), rowPosition, 0, games.size(),"Top");
            
            rowPosition++;
            rowPosition = super.writeStatsRowColumnHeader(sh,rowPosition,0,"Teammates",false);
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 0),rowPosition, 0, games.size(), "0");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 1),rowPosition, 0, games.size(), "1");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 2),rowPosition, 0, games.size(), "2");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 3),rowPosition, 0, games.size(), "3");
            rowPosition = super.doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 4),rowPosition, 0, games.size(), "4");        
            
            doSpecificChampStats(sh, games, rowPosition);
            
            //1st blood stats
            //draft/blind stats
            //place spawned stats
            //rowPosition+=2;
            
            wb.write(fos);   
            fos.close();     
        }
    
    private boolean doSpecificChampStats(Sheet sh, ArrayList<Gameinfo> games, int rowPosition) throws IOException{           
            ArrayList<String> champNames = RecordCruncher.findAllChampions(games);
            ArrayList<Gameinfo> champGames;
            ArrayList<Gameinfo> roleGames;
            ArrayList<String> matchupNames;
            ArrayList<Gameinfo> matchupGames;
            
            for(int k=0; k<champNames.size(); k++){
                writeStatsRowColumnHeader(sh,rowPosition,0,"Champion",false); rowPosition++;
                champGames = RecordCruncher.filterChampions(games, champNames.get(k));
                doStatsRow(sh, champGames, rowPosition, 0, champGames.size(), champNames.get(k)); rowPosition++;
                writeStatsRowColumnHeader(sh,rowPosition,1,"Matchup",true);rowPosition++;
                
                roleGames = RecordCruncher.filterRole(champGames, "Top");
                matchupNames = RecordCruncher.findAllMatchups(roleGames);
                for(int m=0; m<matchupNames.size(); m++){
                    matchupGames = RecordCruncher.filterMatchups(roleGames,matchupNames.get(m));
                    doMatchupStatsRow(sh,matchupGames,rowPosition, 1, matchupGames.size(), matchupNames.get(m), "Top"); rowPosition++;
                }
                
                roleGames = RecordCruncher.filterRole(champGames, "Jungler");
                matchupNames = RecordCruncher.findAllMatchups(roleGames);
                for(int m=0; m<matchupNames.size(); m++){
                    matchupGames = RecordCruncher.filterMatchups(roleGames,matchupNames.get(m));
                    doMatchupStatsRow(sh,matchupGames,rowPosition, 1, matchupGames.size(), matchupNames.get(m), "Jungler"); rowPosition++;
                }
                
                roleGames = RecordCruncher.filterRole(champGames, "Mid");
                matchupNames = RecordCruncher.findAllMatchups(roleGames);
                for(int m=0; m<matchupNames.size(); m++){
                    matchupGames = RecordCruncher.filterMatchups(roleGames,matchupNames.get(m));
                    doMatchupStatsRow(sh,matchupGames,rowPosition, 1, matchupGames.size(), matchupNames.get(m), "Mid"); rowPosition++;
                }
                
                roleGames = RecordCruncher.filterRole(champGames, "ADC");
                matchupNames = RecordCruncher.findAllMatchups(roleGames);
                for(int m=0; m<matchupNames.size(); m++){
                    matchupGames = RecordCruncher.filterMatchups(roleGames,matchupNames.get(m));
                    doMatchupStatsRow(sh,matchupGames,rowPosition, 1, matchupGames.size(), matchupNames.get(m), "Adc"); rowPosition++;
                }
                
                roleGames = RecordCruncher.filterRole(champGames, "Support");
                matchupNames = RecordCruncher.findAllMatchups(roleGames);
                for(int m=0; m<matchupNames.size(); m++){
                    matchupGames = RecordCruncher.filterMatchups(roleGames,matchupNames.get(m));
                    doMatchupStatsRow(sh,matchupGames,rowPosition, 1, matchupGames.size(), matchupNames.get(m), "Support"); rowPosition++;
                }
                
                
                rowPosition+=2;
            }
            
            return true;
        }
            
    private boolean doMatchupStatsRow(Sheet sh, ArrayList<Gameinfo> games, int rowPosition, int cellPosition, int totalSize, String rowHeader, String role) throws IOException{
            Row row = sh.createRow(rowPosition);
            double total, wins, losses, streak, kills, deaths, assists, lanesWon, timesFB;
           
            if(games.isEmpty()){
                total = 0;
            } else {
                 total = games.size();
            }
            
            Cell cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(role);
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(rowHeader);
            cell=row.createCell(cellPosition); cellPosition++; wins = (double)NumberCruncher.getTotalWins(games); cell.setCellValue(wins);
            cell=row.createCell(cellPosition); cellPosition++; losses = (double)NumberCruncher.getTotalLosses(games); cell.setCellValue(losses);
            cell=row.createCell(cellPosition); cellPosition++; streak = (double)NumberCruncher.getStreak(games); cell.setCellValue(streak);
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(total);
            if(total!=0){
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(streak/total);
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(wins/total);
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(total/totalSize);
            } else {
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
            }
            
            cell=row.createCell(cellPosition);  cellPosition++; kills = (double)NumberCruncher.getTotalKills(games); cell.setCellValue(kills); 
            cell=row.createCell(cellPosition); cellPosition++; deaths = (double)NumberCruncher.getTotalDeaths(games); cell.setCellValue(deaths);
            cell=row.createCell(cellPosition); cellPosition++; assists = (double)NumberCruncher.getTotalAssists(games); cell.setCellValue(assists);
            if(deaths!=0){
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(kills/deaths);
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((kills+assists)/deaths);
            } else {
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
            }
            
            cell=row.createCell(cellPosition); cellPosition++; lanesWon=(double)NumberCruncher.getTotalLanesWon(games); cell.setCellValue(lanesWon);
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((double)NumberCruncher.getTotalLanesLost(games));
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((double)NumberCruncher.getTotalLanesEven(games));
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((double)NumberCruncher.getTotalLanesOther(games));
            if(total!=0){
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(lanesWon/total);
            } else {
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
            }
            
            cell=row.createCell(cellPosition); cellPosition++; timesFB=(double)NumberCruncher.getTimesGottenFirstBlood(games); cell.setCellValue(timesFB);
            if(total!=0){
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(timesFB/total);
            } else {
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
            }
            return true;
        }   
}
