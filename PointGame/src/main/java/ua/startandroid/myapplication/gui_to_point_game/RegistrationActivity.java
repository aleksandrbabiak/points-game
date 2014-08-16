package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.adapter.User;
import ua.startandroid.myapplication.adapter.UserAdapter;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.controler.GlobalControler;
import ua.startandroid.myapplication.data_base_to_point_game.AbstractDataBase;
import ua.startandroid.myapplication.data_base_to_point_game.Player;
import ua.startandroid.myapplication.data_base_to_point_game.PlayerDAOImpl;
import ua.startandroid.myapplication.protobuff.ProtoType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alex on 04.05.14.
 */
public class RegistrationActivity extends Activity implements View.OnClickListener {

    private AlertDialog.Builder alert;
    private TextView createNewLogin;
    private Intent intent;
    private String loginPlayer;
    private PlayerDAOImpl dbHelper;
    private boolean isCorrectLogin;
    private String[] playersNames;
    private ListView lvMain;
    private String myName;
    private User myUser;
    private Handler clientHandler;
    private StartClient startClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        createNewLogin = (TextView) findViewById(R.id.registration_createNewNick);
        createNewLogin.setOnClickListener(this);

        // DB object

        dbHelper = new PlayerDAOImpl(this);
        // Create List View
        lvMain = (ListView) findViewById(R.id.registration_listView);
        intent = new Intent(this, OnlinePlayerActivity.class);

        myUser = new User();

        clientHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                ProtoType protoType = ProtoType.fromInt(msg.what);
                switch (protoType) {

                    case CWONTGAME:
                        MyPointGame.CWontGame cWontGame = (MyPointGame.CWontGame) msg.obj;
                        myUser.setId(cWontGame.getMyId());
                        break;

                    case CUPDATEONLINEPLAYER:
                        MyPointGame.CUpdateOnlinePlayer cUpdateOnlinePlayer = (MyPointGame.CUpdateOnlinePlayer) msg.obj;
                        int sizeListPlayers = cUpdateOnlinePlayer.getPlayerList().size();
                        for (int i = 0; i < sizeListPlayers; i++) {
                            UserAdapter userAdapter = new UserAdapter(cUpdateOnlinePlayer.getPlayer(i));
                            User userOponent = new User();
                            userOponent.setId(userAdapter.getIdOponent());
                            userOponent.setName(userAdapter.getNameUser());
                            GlobalControler.addAllPlayersOnline(userOponent);
                            startActivity(intent);
                        }

                        break;
                }
            }
        };
        startClient = new StartClient(clientHandler);
        GlobalControler globalControler = GlobalControler.getGlobalControler();
        globalControler.setStartClient(startClient);
        showAndCheckedLoginPlayer();
    }

    private void showAndCheckedLoginPlayer() {
        // create SQLite
        playersNames = dbHelper.returnPlayersNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playersNames);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;

                myName = textView.getText().toString(); // получаем текст нажатого элемента
                myUser.setName(myName);
                startClient.setUser(myUser);
                GlobalControler.setMyUser(myUser);
                if (!startClient.isAlive()) {
                    startClient.start();
                }
            }
        });
    }

    private boolean checkedLoginPlayer(String login) {
        boolean isLoginCorrect = true;
        for (int i = 0; i < playersNames.length; i++) {
            if (playersNames[i].equals(login)) {
                isLoginCorrect = false;
                i = playersNames.length;
            } else {
                isLoginCorrect = true;
            }
        }
        return isLoginCorrect;
    }

    private void createDialogWindow() {
        alert = new AlertDialog.Builder(this);
        alert.setTitle("Заголовок");
        //alert.setMessage("Сообщение");
        // Добавим поле ввода
        final EditText input = new EditText(this);
        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 15 || charSequence.length() < 0) {
                    Toast.makeText(RegistrationActivity.this, "Huyna", Toast.LENGTH_LONG).show();
                    isCorrectLogin = false;
                } else {
                    isCorrectLogin = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    isCorrectLogin = false;
                }
            }
        });
        alert.setView(input);

        alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                loginPlayer = input.getText().toString();
                int i = 0;
                if (isCorrectLogin) {
                    boolean isCorectName = checkedLoginPlayer(loginPlayer);
                    if (isCorectName) {
                        Player player = new Player();
                        player.setLogin(loginPlayer);
                        dbHelper.insertPlayer(player);
                        //   intent.putExtra("myLogin", loginPlayer);
                        GlobalControler.getMyUser().setName(loginPlayer);
                        startClient.start();
                    }
                }
            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_createNewNick:
                createDialogWindow();
                break;
        }
    }
}
