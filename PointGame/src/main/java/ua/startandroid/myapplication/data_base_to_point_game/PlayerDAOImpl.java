package ua.startandroid.myapplication.data_base_to_point_game;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Alex on 30.04.14.
 */
public class PlayerDAOImpl implements AbstractDataBase {

    private MySqliteSourse connectionToDataBase;
    private SQLiteDatabase database;
    private Cursor c;

    public PlayerDAOImpl(Context context) {
        connectionToDataBase = new MySqliteSourse(context);
    }

    private void open() {
        database = connectionToDataBase.getWritableDatabase();
    }

    private void close() {

        database.close();

    }

    @Override
    public int deletePlayer(Player player) {

        int idUser = player.getId();
        open();
        int clearCount = database.delete(MySqliteSourse.TABLE_NAME, MySqliteSourse.COLUMN_ID + "=" + idUser, null);
        close();
        return clearCount;
    }

    @Override
    public long insertPlayer(Player player) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteSourse.COLUMN_LOGIN, player.getLogin());
        contentValues.put(MySqliteSourse.COLUMN_POINTS, player.getPoints());
        contentValues.put(MySqliteSourse.COLUMN_WIN_GAME, player.getWin_game());
        contentValues.put(MySqliteSourse.COLUMN_LOSE_GAME, player.getLose_game());

        open();
        long idPlayer = database.insert(MySqliteSourse.TABLE_NAME, null, contentValues);
        close();
        return idPlayer;
    }

    @Override
    public int updatePlayer(Player player) {
        int idUser = player.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteSourse.COLUMN_LOGIN, player.getLogin());
        contentValues.put(MySqliteSourse.COLUMN_POINTS, player.getPoints());
        contentValues.put(MySqliteSourse.COLUMN_WIN_GAME, player.getWin_game());
        contentValues.put(MySqliteSourse.COLUMN_LOSE_GAME, player.getLose_game());
        open();
        database.update(MySqliteSourse.TABLE_NAME, contentValues, MySqliteSourse.COLUMN_ID + "=" + idUser, null);
        close();
        return idUser;
    }

    @Override
    public ArrayList<Player> queryPlayer() {

        open();

        String query = "select * from " + MySqliteSourse.TABLE_NAME + ";";
        c = database.rawQuery(query, null);
        Player player;
        ArrayList<Player> listPlayers = new ArrayList<Player>();

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex(MySqliteSourse.COLUMN_ID);
            int loginColIndex = c.getColumnIndex(MySqliteSourse.COLUMN_LOGIN);
            int pointColIndex = c.getColumnIndex(MySqliteSourse.COLUMN_POINTS);
            int winGameColIndex = c.getColumnIndex(MySqliteSourse.COLUMN_WIN_GAME);
            int loseGameColIndex = c.getColumnIndex(MySqliteSourse.COLUMN_LOSE_GAME);

            do {

                player = new Player();
                player.setId(c.getInt(idColIndex));
                player.setLogin(c.getString(loginColIndex));
                player.setPoints(c.getInt(pointColIndex));
                player.setWin_game(c.getInt(winGameColIndex));
                player.setLose_game(c.getInt(loseGameColIndex));
                listPlayers.add(player);

            } while (c.moveToNext());
            c.close();
            close();
        }else{
             c.close();
            close();
        }

        close();
        return listPlayers;
    }

    @Override
    public String[] returnPlayersNames() {

        open();
        String namesToPlayer = "select " + MySqliteSourse.COLUMN_LOGIN + " from " + MySqliteSourse.TABLE_NAME + " ;";
        c = database.rawQuery(namesToPlayer, null);
        int numbersRowsLogin = c.getCount();
        String[] listPlayersName = new String[numbersRowsLogin];
        int numbersLoginPlayer = 0;
        if (c.moveToFirst()) {
            int loginColIndex = c.getColumnIndex(MySqliteSourse.COLUMN_LOGIN);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                listPlayersName[numbersLoginPlayer] = c.getString(loginColIndex);
                numbersLoginPlayer++;

            } while (c.moveToNext());
            c.close();
            close();
        } else {
             c.close();
            close();
        }
        return listPlayersName;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
