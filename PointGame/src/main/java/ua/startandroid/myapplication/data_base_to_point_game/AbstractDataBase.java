package ua.startandroid.myapplication.data_base_to_point_game;

import java.util.ArrayList;

/**
 * Created by Alex on 01.05.14.
 */
public interface AbstractDataBase {
    public int deletePlayer(Player player);

    public long insertPlayer(Player player);

    public int updatePlayer(Player player);

    public ArrayList<Player> queryPlayer();

    public String[] returnPlayersNames();
}
