package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.MainActivity;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.controler.GlobalControler;
import ua.startandroid.myapplication.enum_type.MoveType;
import ua.startandroid.myapplication.protobuff.ProtoType;

/**
 * Created by Alex on 22.04.14.
 */
public class FieldToGameActivity extends Activity implements View.OnClickListener {

    private StartClient startClient;
    private BoxAdapter boxAdapter;
    private Button chatButton;
    private Handler handler;
    private int flag = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_game);
        createLinerLayout();
        boxAdapter = new BoxAdapter(this);
        GridView gridView = (GridView) findViewById(R.id.fieldGame_gridView);
        gridView.setAdapter(boxAdapter);
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {

                ProtoType type = ProtoType.fromInt(msg.what);
                switch (type) {

                    case CSENDMESSAGE:
                        MyPointGame.CSendMessage cSendMessage = (MyPointGame.CSendMessage) msg.obj;
                        String str = cSendMessage.getSendMessageLoseLogin();
                        break;
                    case CONEMOVE:
                        MyPointGame.COneMove cOneMove = (MyPointGame.COneMove) msg.obj;
                        int i = cOneMove.getI();
                        int j = cOneMove.getJ();
                        MyPointGame.MoveType moveType = cOneMove.getMoveType();

                        if (moveType == MyPointGame.MoveType.BLUE_POINT) {
                            boxAdapter.drowableBlueOrRedPointOnline(i, j, MoveType.BLUE_POINT);
                            boxAdapter.myFildViewsActive();
                            GlobalControler.setIsFirstOrSecondMove(false);

                        } else {
                            boxAdapter.drowableBlueOrRedPointOnline(i, j, MoveType.RED_POINT);
                            boxAdapter.myFildViewsActive();
                            GlobalControler.setIsFirstOrSecondMove(true);
                        }
                        break;

                }
            }
        };
        startClient = GlobalControler.getGlobalControler().getStartClient();
        if (startClient != null) {
            startClient.setHandler(handler);
        }
    }

    private void createLinerLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fieldToGameLinerLayoutTextView);
        TextView firstNameTextView = new TextView(this);
        TextView secondNameTextView = new TextView(this);

        Intent onlineGame = getIntent();
        if (onlineGame.getStringExtra("myLoginOnline") != null) {
            firstNameTextView.setText(onlineGame.getStringExtra("myLoginOnline"));
            secondNameTextView.setText(onlineGame.getStringExtra("myOponentOnline"));
        } else {
            Intent twoPlayers = getIntent();
            String firstName = twoPlayers.getStringExtra("firstName");
            String secondName = twoPlayers.getStringExtra("secondName");
            flag = twoPlayers.getFlags();
            firstNameTextView.setText(firstName);
            secondNameTextView.setText(secondName);
        }
        linearLayout.addView(firstNameTextView);
        if (flag == 0) {
            GlobalControler.setIsOnlineGaame(true);
            chatButton = new Button(this);
            chatButton.setText("Chat");
            chatButton.findViewById(1);
            chatButton.setId(1);
            linearLayout.addView(chatButton);
            chatButton.setOnClickListener(this);

        }
        linearLayout.addView(secondNameTextView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 1:
                Intent intent = new Intent(this, Chat.class);
                startActivity(intent);
                break;
        }
    }
}
