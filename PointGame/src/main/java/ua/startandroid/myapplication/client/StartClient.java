package ua.startandroid.myapplication.client;

import android.content.Context;
import android.util.Log;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.my_point_game.MyPointGame;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.os.Handler;
import ua.startandroid.myapplication.adapter.User;
import ua.startandroid.myapplication.protobuff.ProtoFactory;
import ua.startandroid.myapplication.protobuff.ProtoType;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the botserver.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and botserver by sending the first message to
 * the botserver.
 */
public class StartClient extends Thread {
    private Handler handler;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private BufferedInputStream in;
    private DataInputStream dataInputStream;
    private User myUser;
    private boolean isConnecting = true;

    public StartClient(Handler handler) {
        this.handler = handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setUser(User myUser) {
        this.myUser = myUser;
    }

    private void setConnecting() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("192.168.1.101");
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, 1910);
            socket = new Socket();
            socket.connect(socketAddress, 5000);
            in = new BufferedInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(in);
            MyPointGame.SWontGame sWontGame = MyPointGame.SWontGame.newBuilder().setLogin(myUser.getNameUser()).build();
            sendPacket(sWontGame);

            while (isConnecting) {
                if (socket == null) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                byte b = dataInputStream.readByte();
                int length = dataInputStream.readInt();
                byte[] data = new byte[length];
                dataInputStream.readFully(data);
                ProtoType protoType = ProtoType.fromByte(b);
                int length1 = data.length;
                android.os.Message message = handler.obtainMessage(ProtoType.getInt(b), ProtoFactory.createProtoObject(data, protoType));
                handler.sendMessage(message);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(final AbstractMessageLite abstractMessageLite) {
        if (dataOutputStream == null)
            return;
        try {
            byte[] data = abstractMessageLite.toByteArray();
            int length = data.length;
            dataOutputStream.writeByte(ProtoType.fromClass(abstractMessageLite.getClass()).getByteValue());
            dataOutputStream.writeInt(length);
            dataOutputStream.write(data);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        setConnecting();
    }

}
