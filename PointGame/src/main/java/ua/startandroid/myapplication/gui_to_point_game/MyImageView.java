package ua.startandroid.myapplication.gui_to_point_game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.07.14
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class MyImageView extends ImageView {

    private int i;
    private int j;
    private boolean activeView = true;


    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getI(){
        return i;
    }
    public void setI(int i){
        this.i = i;
    }

    public int getJ(){
        return j;
    }
    public void setJ(int j){
        this.j = j;
    }

    public boolean getIsActiveView() {
        return activeView;
    }

    public void setIsActiveView(boolean activeView) {
        this.activeView = activeView;
    }

}
