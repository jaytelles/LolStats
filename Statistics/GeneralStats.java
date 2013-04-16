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

public class GeneralStats extends Stats{
    public GeneralStats(String playerName, String username) {
        rowPosition = 0;
        this.playerName = playerName;
        this.username = username;
        
        games = RecordCruncher.filterUsers(Gameinfo.getAllGameinfos(), username);
        games = RecordCruncher.filterPlayers(games,playerName);
    }
    
    public boolean doGeneralStats() throws IOException{
         Workbook wb = new HSSFWorkbook();
         Sheet sh = wb.createSheet("Sheet1");
         FileOutputStream fos = new FileOutputStream(username+"'s stats for "+playerName+" general.xls");   
         ArrayList<String> matchupColumns = new ArrayList<>();
         matchupColumns.add("Role");

         rowPosition = super.writeStatsTotalRowColumnHeader(sh,rowPosition, 0);
            
         rowPosition = doStatsTotalRow(sh,games,rowPosition,0);
         
         rowPosition++; 
         rowPosition = writeStatsRowColumnHeader(sh,rowPosition,0,matchupColumns);
         rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Support"), rowPosition, 0, games.size(),"Support");
         rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "ADC"), rowPosition, 0, games.size(),"ADC");
         rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Mid"), rowPosition, 0, games.size(),"Mid");
         rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Jungler"), rowPosition, 0, games.size(),"Jungler");
         rowPosition = super.doStatsRow(sh,RecordCruncher.filterRole(games, "Top"), rowPosition, 0, games.size(),"Top");
           
         rowPosition++;
         rowPosition = super.writeStatsRowColumnHeader(sh,rowPosition,0,matchupColumns);
         rowPosition = doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 0),rowPosition, 0, games.size(), "0");
         rowPosition = doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 1),rowPosition, 0, games.size(), "1");
         rowPosition = doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 2),rowPosition, 0, games.size(), "2");
         rowPosition = doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 3),rowPosition, 0, games.size(), "3");
         rowPosition = doStatsRow(sh,RecordCruncher.filterNumTeammates(games, 4),rowPosition, 0, games.size(), "4");
         rowPosition++;
         
         
         ArrayList<String> champNames = RecordCruncher.findAllChampions(games);
         ArrayList<Gameinfo> champGames;
         matchupColumns.clear();
         matchupColumns.add("Champion");
         rowPosition = super.writeStatsRowColumnHeader(sh,rowPosition,0,matchupColumns);
         for(int k=0; k<champNames.size(); k++){
             champGames = RecordCruncher.filterChampions(games, champNames.get(k));
             rowPosition = doStatsRow(sh, champGames, rowPosition, 0, champGames.size(), champNames.get(k));
         }
         rowPosition++;
         
         ArrayList<Gameinfo> teammateGames;
         ArrayList<String> teammateNames = RecordCruncher.findAllTeammates(Gameinfo.getAllGameinfos());
         matchupColumns.clear();
         matchupColumns.add("Role");
         rowPosition = super.writeStatsRowColumnHeader(sh,rowPosition,0,matchupColumns);
         for(int k=0; k<teammateNames.size(); k++){
             teammateGames = RecordCruncher.filterPlayers(Gameinfo.getAllGameinfos(), teammateNames.get(k));
             rowPosition = doStatsRow(sh,teammateGames,rowPosition,0,teammateGames.size(), teammateNames.get(k));
         }
         
         wb.write(fos);   
         fos.close();
         return true;
    }
}
