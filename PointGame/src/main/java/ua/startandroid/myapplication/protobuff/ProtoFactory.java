package ua.startandroid.myapplication.protobuff;

import android.util.Log;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.my_point_game.MyPointGame;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 12.06.14
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class ProtoFactory {
    public static AbstractMessageLite createProtoObject(byte data[], ProtoType type) throws InvalidProtocolBufferException {

        switch (type) {

            case CWONTGAME:
                MyPointGame.CWontGame cWontGame = MyPointGame.CWontGame
                        .parseFrom(data);
                return  cWontGame;

            case CSTARTGAME:
                MyPointGame.CStartGame cStartGame = MyPointGame.CStartGame
                        .parseFrom(data);
                return cStartGame;
            case CFINISHGAME:
                MyPointGame.CFinishGame cFinishGame = MyPointGame.CFinishGame
                        .parseFrom(data);
                return cFinishGame;
            case CNEWGAME:
                MyPointGame.CNewGame cNewGame = MyPointGame.CNewGame
                        .parseFrom(data);
                return cNewGame;
            case CONEMOVE:
                MyPointGame.COneMove cOneMove = MyPointGame.COneMove
                        .parseFrom(data);
                return cOneMove;
            case CSENDMESSAGE:
                MyPointGame.CSendMessage cSendMessage = MyPointGame.CSendMessage
                        .parseFrom(data);
                return cSendMessage;
            case CUPDATEONLINEPLAYER:
                MyPointGame.CUpdateOnlinePlayer cUpdateOnlinePlayer = MyPointGame.CUpdateOnlinePlayer
                        .parseFrom(data);
                return cUpdateOnlinePlayer;
            case CWINGAME:
                MyPointGame.CWinGame cWinGame = MyPointGame.CWinGame
                        .parseFrom(data);
                return cWinGame;
            case CWONTPLAY: MyPointGame.CWontPlay cWontPlay = MyPointGame.CWontPlay
                    .parseFrom(data);
                return cWontPlay;
            default:
                return null;

        }

    }
}