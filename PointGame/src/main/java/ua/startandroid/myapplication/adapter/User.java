package ua.startandroid.myapplication.adapter;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 07.07.14
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */
public class User implements TransformInUser {
   private String name;
   private int myId;
   private int idOponenta;


    @Override
    public int getIdOponent() {
        return idOponenta;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public void setIdOponent(int idOponenta){
        this.idOponenta = idOponenta;
    }

    @Override
    public int getId() {
        return myId;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public void setId(int myId){
        this.myId = myId;
    }

    @Override
    public String getNameUser() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public void setName(String name){
        this.name = name;
    }


}
