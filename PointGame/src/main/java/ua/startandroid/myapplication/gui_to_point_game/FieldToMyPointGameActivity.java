package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.controler.GlobalControler;
import ua.startandroid.myapplication.enum_type.MoveType;
import ua.startandroid.myapplication.protobuff.ProtoType;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.07.14
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class FieldToMyPointGameActivity extends Activity implements View.OnClickListener {
    private IFieldGameFragment fieldGameFragment;
    private IChatFragment chatFragment;
    private Handler handler;
    private StartClient startClient;
    private BoxAdapter boxAdapter;
    private FragmentTransaction fragmentTransaction;
    private int flag;
    private Button chatButton;
    private ISentListener isentListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_point_game);
        Intent intent = getIntent();
        createLinerLayout();
        fieldGameFragment = new FieldGameFragment();
        chatFragment = new ChatFragment();
        isentListener = new ISentListener() {
            @Override
            public void sent(com.google.protobuf.Message message) {
                MyPointGame.SSendMessage sSendMessage = MyPointGame.SSendMessage.newBuilder()
                        .setMyId(GlobalControler.getMyUser().getId())
                        .setOponentId(GlobalControler.getMyUser().getIdOponent())
                        .setSendMessageLoseLogin(message.toString()).build();
                GlobalControler.getGlobalControler().getStartClient().sendPacket(sSendMessage);
            }
        };

        chatFragment.setSentButtonClickListener(isentListener);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragments, (Fragment) fieldGameFragment);
        fragmentTransaction.add(R.id.fragments, (Fragment) chatFragment);
        fragmentTransaction.hide((Fragment) chatFragment);
        fragmentTransaction.commit();

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {

                ProtoType type = ProtoType.fromInt(msg.what);
                switch (type) {

                    case CSENDMESSAGE:
                        MyPointGame.CSendMessage cSendMessage = (MyPointGame.CSendMessage) msg.obj;
                        String str = cSendMessage.getSendMessageLoseLogin();
                        chatFragment.addNowMessage(str);
                        break;
                    case CONEMOVE:
                        boxAdapter = fieldGameFragment.getBoxAdapter();
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
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linerLayoutView);
        TextView firstNameTextView = new TextView(this);
        TextView secondNameTextView = new TextView(this);

        if (getIntent().getStringExtra("myLoginOnline") != null) {
            firstNameTextView.setText(getIntent().getStringExtra("myLoginOnline"));
            secondNameTextView.setText(getIntent().getStringExtra("myOponentOnline"));
        } else {

            String firstName = getIntent().getStringExtra("firstName");
            String secondName = getIntent().getStringExtra("secondName");
            flag = getIntent().getFlags();
            firstNameTextView.setText(firstName);
            secondNameTextView.setText(secondName);
        }
        linearLayout.addView(firstNameTextView);
        if (flag == 0) {
            GlobalControler.setIsOnlineGaame(true);
            chatButton = new Button(this);
            chatButton.setText("Chat");
            chatButton.setId(1);
            linearLayout.addView(chatButton);
            chatButton.setOnClickListener(this);
            linearLayout.addView(secondNameTextView);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case 1:

                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.hide((Fragment) fieldGameFragment);
                fragmentTransaction1.show((Fragment) chatFragment);
                fragmentTransaction1.commit();
                break;


        }
    }
}