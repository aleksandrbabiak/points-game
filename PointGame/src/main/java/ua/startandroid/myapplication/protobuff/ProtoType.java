package ua.startandroid.myapplication.protobuff;

import com.my_point_game.MyPointGame;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 12.06.14
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public enum ProtoType {
    UNKNOWN((byte) 0x00),
    SWONTGAME((byte) 0x01, MyPointGame.SWontGame.class),
    SWONTPLAY((byte) 0x03, MyPointGame.SWontPlay.class ),
    SSTARTGAME((byte) 0x04, MyPointGame.SStartGame.class),
    SONEMOVE((byte) 0x05, MyPointGame.SOneMove.class),
    SWINGAME((byte) 0x06, MyPointGame.SWinGame.class),
    SNEWGAME((byte) 0x07, MyPointGame.SNewGame.class),
    SFINISHGAME((byte) 0x08, MyPointGame.SFinishGame.class),
    SSENDMESSAGE((byte) 0x09, MyPointGame.SSendMessage.class),

    CUNKNOWN((byte) 0x00, 0),
    CWONTGAME((byte) 0x01, 1),
    CUPDATEONLINEPLAYER((byte) 0x02, 2),
    CWONTPLAY((byte) 0x03, 3),
    CSTARTGAME((byte) 0x04, 4),
    CONEMOVE((byte) 0x05, 5),
    CWINGAME((byte) 0x06, 6),
    CNEWGAME((byte) 0x07, 7),
    CFINISHGAME((byte) 0x08, 8),
    CSENDMESSAGE((byte) 0x09, 9);


    private final static HashMap<Class, ProtoType> classMap;
    private final static HashMap<Byte, ProtoType> byteMap;
    private final static HashMap<Integer, ProtoType> intMap;
    private  final byte  b;
    public int index;
    private Class protoClass;

    static {
        classMap = new HashMap<Class, ProtoType>(64);
        byteMap = new HashMap<Byte, ProtoType>(64);
        intMap = new HashMap<Integer, ProtoType>(64);
        for (ProtoType type : values()) {
            classMap.put(type.protoClass, type);
            if (type.protoClass == null) {
                byteMap.put(type.b, type);
                intMap.put(type.index, type);
            }

        }
    }
    private ProtoType(byte b, int index) {
        this.b = b;
        this.index = index;
    }
    private ProtoType(byte b) {
        this.b = b;
    }
    private ProtoType(byte b, Class protoClass) {
        this.b = b;
        this.protoClass = protoClass;
    }

    public static ProtoType fromByte(byte b) {
        ProtoType type = byteMap.get(b);
        if (type != null)
            return type;
        else
            return UNKNOWN;
    }

    public static ProtoType fromClass(Class c) {
        for ( ProtoType type : values()) {
            if (type.protoClass != null && type.protoClass.equals(c)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static int getInt(byte b) {
        ProtoType type = byteMap.get(b);
        if (type != null)
            return type.index;
        else
            return UNKNOWN.index;
    }

    public static ProtoType fromInt(int index) {
        ProtoType type = intMap.get(index);
        if (type != null)
            return type;
        else
            return UNKNOWN;
    }

    public byte getByteValue() {
        return b;
    }

}
