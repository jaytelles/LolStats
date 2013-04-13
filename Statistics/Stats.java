/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import DatabaseInterfaces.Gameinfo;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class Stats {
    protected int rowPosition;
    protected String playerName;
    protected ArrayList<Gameinfo> games;
    
    
    protected int writeStatsTotalRowColumnHeader(Sheet sh, int rowPosition, int cellPosition) throws IOException{
            Row row = sh.createRow(rowPosition);
            Cell cell = row.createCell(cellPosition);
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Won");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Lost");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Streak");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Ratio");
            cellPosition++;
            
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Kills");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Deaths");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Assists");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total K/D");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total K/D/A");
            cellPosition++;
            
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("No Supps");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Kills");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Deaths");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total Assists");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total K/D");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total K/D/A");
            
            //wb.write(fos);
            return ++rowPosition;
        }
    
    protected int doStatsTotalRow(Sheet sh, ArrayList<Gameinfo> games, int rowPosition, int cellPosition) throws IOException{
            Row row = sh.createRow(rowPosition);
            double wins, losses, kills, deaths, assists;

            //Section 1. Wins/Losses/Streak/Total/Ratio
            Cell cell = row.createCell(cellPosition); cellPosition++; wins = (double)NumberCruncher.getTotalWins(games); cell.setCellValue(wins);
            cell=row.createCell(cellPosition); cellPosition++; losses = (double)NumberCruncher.getTotalLosses(games); cell.setCellValue(losses);
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(wins+losses);
            cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((double)NumberCruncher.getStreak(games));
            cell=row.createCell(cellPosition); cellPosition+=2; cell.setCellValue(wins/games.size());
            
            //Section 2. Kills/Deaths/Assists/KD/KDA
            cell=row.createCell(cellPosition);  cellPosition++; kills = (double)NumberCruncher.getTotalKills(games); cell.setCellValue(kills); 
            cell=row.createCell(cellPosition); cellPosition++; deaths = (double)NumberCruncher.getTotalDeaths(games); cell.setCellValue(deaths);
            cell=row.createCell(cellPosition); cellPosition++; assists = (double)NumberCruncher.getTotalAssists(games); cell.setCellValue(assists);
            if(!(deaths==0)){
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(kills/deaths);
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((kills+assists)/deaths);
            } else {
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
            }
            
            //Spacing
            cellPosition+=2;
            
            //Section 3. No Supps Kills/Deaths/Assists/KD/KDA
            //games = RecordCruncher.removeSupports(games); 
            cell=row.createCell(cellPosition); cellPosition++; kills = (double)NumberCruncher.getTotalKills(RecordCruncher.removeSupports(games)); cell.setCellValue(kills);
            cell=row.createCell(cellPosition); cellPosition++; deaths = (double)NumberCruncher.getTotalDeaths(RecordCruncher.removeSupports(games)); cell.setCellValue(deaths);
            cell=row.createCell(cellPosition); cellPosition++; assists = (double)NumberCruncher.getTotalAssists(RecordCruncher.removeSupports(games)); cell.setCellValue(assists);
            if(!(deaths==0)){
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(kills/deaths);
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue((kills+assists)/deaths);
            } else {
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
                cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue("0");
            }
            
           
            return ++rowPosition;
        }
    
    protected int writeStatsRowColumnHeader(Sheet sh, int rowPosition, int cellPosition, String rowHeader, boolean isMatchup) throws IOException{
            Row row = sh.createRow(rowPosition);
            Cell cell;
            
            if(isMatchup){
                cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Role");
            }
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue(rowHeader);
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Won");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Lost");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Streak");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Total");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Streak Ratio");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Win Ratio");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Ratio to Total");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Kills");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Deaths");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Assists");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("K/D");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("K/D/A");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Lane Won");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Lane Lost");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Lane Even");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Lane Other");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Win Rate");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("Times FB");
            cell = row.createCell(cellPosition); cellPosition++; cell.setCellValue("FB Ratio");
            
            return ++rowPosition;
        }
    
    protected int doStatsRow(Sheet sh, ArrayList<Gameinfo> games, int rowPosition, int cellPosition, int totalSize, String rowHeader) throws IOException{
            Row row = sh.createRow(rowPosition);
            double total, wins, losses, streak, kills, deaths, assists, lanesWon, timesFB;
           
            if(games.isEmpty()){
                total = 0;
            } else {
                 total = games.size();
            }
            
            Cell  cell=row.createCell(cellPosition); cellPosition++; cell.setCellValue(rowHeader);
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
            return ++rowPosition;
        }
}
