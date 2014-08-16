package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.adapter.UserAdapter;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.controler.GlobalControler;
import ua.startandroid.myapplication.protobuff.ProtoType;
import ua.startandroid.myapplication.adapter.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13.06.14
 * Time: 9:32
 * To change this template use File | Settings | File Templates.
 */
public class OnlinePlayerActivity extends Activity implements View.OnClickListener {
    private Button statistics;
    private String[] onlineUsers;
    private Intent intent;
    private Handler handler;
    private StartClient startClient;
    private User myUser;
    private int size;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_oponent_online);

        statistics = (Button) findViewById(R.id.onlineOponentStatistics);
        statistics.setOnClickListener(this);

        myUser = GlobalControler.getMyUser();
        size = GlobalControler.allOnlinePlayers().size();

        final List<User> listUsers = GlobalControler.allOnlinePlayers();

        for (int j = 0; j < listUsers.size(); j++) {

            if (myUser.getId() == listUsers.get(j).getId()) {
                listUsers.remove(j);
            }
        }
        size = listUsers.size();

        onlineUsers = new String[size];
        for (int i = 0; i < size; i++) {
            onlineUsers[i] = listUsers.get(i).getId() + " " + listUsers.get(i).getNameUser();

        }

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                ProtoType type = ProtoType.fromInt(msg.what);

                switch (type) {

                    case CWONTPLAY:
                        String oponent = null;
                        MyPointGame.CWontPlay cWontPlay = (MyPointGame.CWontPlay) msg.obj;

                        int idOponent = cWontPlay.getOponentId();
                        myUser.setIdOponent(idOponent);

                        List<User> users = GlobalControler.allOnlinePlayers();
                        for (int i = 0; i < users.size(); i++) {
                            if (myUser.getIdOponent() == users.get(i).getId()) {
                                oponent = users.get(i).getNameUser();
                            }
                        }
                        if (oponent == null) {
                            MyPointGame.SWontGame sWontGame = MyPointGame.SWontGame.newBuilder().setLogin(myUser.getNameUser()).build();
                            startClient.sendPacket(sWontGame);
                        } else {
                            createDialogWindow();
                        }
                        break;
                    case CSTARTGAME:
                        MyPointGame.CStartGame cStartGame = (MyPointGame.CStartGame) msg.obj;
                        int idOponentGame = cStartGame.getOpponentId();
                        myUser.setIdOponent(idOponentGame);
                        startActivity(intent);
                        break;
                    case CUPDATEONLINEPLAYER:
                        MyPointGame.CUpdateOnlinePlayer cUpdateOnlinePlayer = (MyPointGame.CUpdateOnlinePlayer) msg.obj;
                        int sizeListPlayers = cUpdateOnlinePlayer.getPlayerList().size();
                        List<MyPointGame.Player> playersOponent = cUpdateOnlinePlayer.getPlayerList();
                        for (int j = 0; j < sizeListPlayers; j++) {
                            UserAdapter userAdapter = new UserAdapter(playersOponent.get(j));
                            if (userAdapter.getId() == myUser.getIdOponent()) {
                                if (myUser.getIdOponent() < myUser.getId()) {
                                    GlobalControler.setIsFirstOrSecondMove(true);
                                }
                                    String oponentName = userAdapter.getNameUser();
                                    intent.putExtra("myOponentOnline", oponentName);
                                    intent.putExtra("myLoginOnline", myUser.getNameUser());
                                    createDialogWindow();
                            }
                        }
                }
            }
        };
        //Intent namePlayer = getIntent();
        // String name = namePlayer.getStringExtra("myLogin");
        GlobalControler globalControler = GlobalControler.getGlobalControler();
        startClient = globalControler.getStartClient();
        if (startClient == null) {
        }
        startClient.setHandler(handler);
     //Fragments
        intent = new Intent(this,FieldToMyPointGameActivity.class);
        ListView listOponent = (ListView) findViewById(R.id.onlinePlayersListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, onlineUsers);
        // присваиваем адаптер списку
        listOponent.setAdapter(adapter);
        listOponent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                long a = id;
                TextView oponentName = (TextView) itemClicked;
                String oponentLogin = oponentName.getText().toString();
                String myLogin = myUser.getNameUser();
                intent.putExtra("myOponentOnline", oponentLogin);
                intent.putExtra("myLoginOnline", myLogin);
                myUser.setIdOponent(listUsers.get(position).getId());
                MyPointGame.SWontPlay sWontPlay = MyPointGame.SWontPlay.newBuilder()
                        .setLoseId(myUser.getIdOponent())
                        .setMyId(GlobalControler.getMyUser().getId()).build();
                startClient.sendPacket(sWontPlay);

            }
        });

    }

    private void createDialogWindow() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Заголовок");
        alert.setMessage("Do you wont play?");

        alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                MyPointGame.SStartGame sStartGame = MyPointGame.SStartGame.newBuilder()
                        .setMyId(myUser.getId()).setOpponentId(myUser.getIdOponent()).build();
                startClient.sendPacket(sStartGame);
            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Если отменили.
            }
        });
        alert.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onlineOponentStatistics:
                Intent statisticsIntent =  new Intent(this, StatisticsActivity.class);
                startActivity(statisticsIntent);
                break;

        }
    }
}