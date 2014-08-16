package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.startandroid.myapplication.R;

import ua.startandroid.myapplication.data_base_to_point_game.Player;
import ua.startandroid.myapplication.data_base_to_point_game.PlayerDAOImpl;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 27.05.14
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsActivity extends Activity {

    private String[] playersLogins;
    private Integer[] playersPoints;
    private Integer[] playersWin;
    private Integer[] playersLose;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_players);
             showInfoToPlayer();
            ListView listView1 = (ListView) findViewById(R.id.statisticsPlayerListViewLogin);
            ListView listView2  = (ListView) findViewById(R.id.statisticsPlayerListViewPoints);
            ListView listView3 = (ListView) findViewById(R.id.statisticsPlayerListWin);
            ListView listView4 = (ListView) findViewById(R.id.statisticsPlayerListViewLose);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, playersLogins);
        // присваиваем адаптер списку
        listView1.setAdapter(adapter1);

        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, playersPoints);
        // присваиваем адаптер списку
        listView2.setAdapter(adapter2);

        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, playersWin);
        // присваиваем адаптер списку
        listView3.setAdapter(adapter3);

        ArrayAdapter<Integer> adapter4 = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, playersLose);
        // присваиваем адаптер списку
        listView4.setAdapter(adapter4);

    }
    private String[] showLoginToPlayer(){

        return null;
    }

    private String[] showInfoToPlayer() {
        PlayerDAOImpl playerDAO = new PlayerDAOImpl(this);
        ArrayList<Player> listPlayers = playerDAO.queryPlayer();
        int sizeArray = listPlayers.size();

        int j = 0;

        if(sizeArray > 12){
            sizeArray = 12;
        }
        playersLogins = new String[sizeArray];
        playersPoints = new Integer[sizeArray];
        playersWin = new Integer[sizeArray];
        playersLose = new Integer[sizeArray];

        for (int i = 0; i < sizeArray; i++) {
            playersLogins[i] = listPlayers.get(i).getLogin();
            playersPoints[i] = listPlayers.get(i).getPoints();
            playersWin[i] = listPlayers.get(i).getWin_game();
            playersLose[i] = listPlayers.get(i).getLose_game();
            j++;
        }
              for (int k = 0; k < playersLogins.length; k++){
              }
        return playersLogins;
    }
}



