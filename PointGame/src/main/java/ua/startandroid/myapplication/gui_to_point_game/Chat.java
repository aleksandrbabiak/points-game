package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.adapter.User;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.controler.GlobalControler;
import ua.startandroid.myapplication.protobuff.ProtoType;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 14.07.14
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class Chat extends Activity implements View.OnClickListener {

    private EditText topEdit;
    private EditText lowerEdit;
    private Button sendButton;
    private StartClient startClient;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_to_game);
        topEdit = (EditText) findViewById(R.id.topEditText);
        lowerEdit = (EditText) findViewById(R.id.lowerEditText);
        topEdit.setEnabled(false);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        startClient = GlobalControler.getGlobalControler().getStartClient();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendButton:
                String sendMessage = lowerEdit.getText().toString();
                lowerEdit.setText("");
                topEdit.append(sendMessage + "\n");

                User myUser = GlobalControler.getMyUser();
                MyPointGame.SSendMessage sSendMessage = MyPointGame.SSendMessage.newBuilder()
                        .setMyId(myUser.getId())
                        .setOponentId(myUser.getIdOponent())
                        .setSendMessageLoseLogin(sendMessage).build();
                startClient.sendPacket(sSendMessage);

        }
    }
}