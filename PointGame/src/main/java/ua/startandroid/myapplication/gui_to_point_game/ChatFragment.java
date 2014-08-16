package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.protobuf.Message;
import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.adapter.User;
import ua.startandroid.myapplication.controler.GlobalControler;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.07.14
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class ChatFragment extends Fragment implements IChatFragment, View.OnClickListener {
    private EditText topEdit;
    private EditText lowerEdit;
    private Button sendBut;
    private ISentListener iSentListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, null);
        topEdit = (EditText) view.findViewById(R.id.topEditText);
        topEdit.setEnabled(false);
        lowerEdit = (EditText) view.findViewById(R.id.lowerEditText);
        lowerEdit.setOnClickListener(this);
        sendBut = (Button) view.findViewById(R.id.sendButton);
        sendBut.setOnClickListener(this);

        return view;
    }

    @Override
    public void setSentButtonClickListener(ISentListener iSentListener) {
        this.iSentListener = iSentListener;
    }

    @Override
    public void addNowMessage(String str) {
        topEdit.append(str + "\n");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendButton:
                Message sendMessage = (Message) lowerEdit.getText();
                lowerEdit.setText("");
                topEdit.append(sendMessage + "\n");
                iSentListener.sent(sendMessage);
                User myUser = GlobalControler.getMyUser();
                MyPointGame.SSendMessage sSendMessage = MyPointGame.SSendMessage.newBuilder()
                        .setMyId(myUser.getId())
                        .setOponentId(myUser.getIdOponent())
                        .setSendMessageLoseLogin(sendMessage.toString()).build();
                GlobalControler.getGlobalControler().getStartClient().sendPacket(sSendMessage);
                break;
        }
    }
}
