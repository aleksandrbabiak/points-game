package ua.startandroid.myapplication.gui_to_point_game;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.my_point_game.MyPointGame;
import ua.startandroid.myapplication.R;
import ua.startandroid.myapplication.controler.GlobalControler;
import ua.startandroid.myapplication.enum_type.MoveType;

/**
 * Created by Alex on 22.04.14.
 */
public class BoxAdapter extends BaseAdapter {
    private Context context;
    private int x;
    private int y;
    private MyImageView myImageView;
    private View[] fields = new View[800];
    private View[][] fieldsGame = new View[25][32];
    private boolean isRedOrBlue = true;
    private int sizeFieldGame = fields.length;
    private boolean isfirstMove = true;

    public BoxAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return sizeFieldGame;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        myImageView = new MyImageView(context);
        myImageView = (MyImageView) convertView;

        if (myImageView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myImageView = (MyImageView) li.inflate(R.layout.text_view_to_grid_view, parent, false);

            if (fields[position] == null) {
                fields[position] = myImageView;
                x = 0;
                y = 0;
                double d = position / 25.0;
                int i = position / 25;
                if (d - i == 0) {
                    x = 24;
                    y = i;
                } else {
                    y = i;
                    x = position - (25 * i) - 1;
                }


                myImageView.setI(x);
                myImageView.setJ(y);
            }
        }

        MyImageView fieldView = (MyImageView) myImageView.findViewById(R.id.text);
        fieldView.setOnClickListener(new EventHandler());
        fieldsGame[x][y] = myImageView;
        return myImageView;
    }

    @Override
    public Object getItem(int position) {
        return fields[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean drowableRedPoint(MyImageView myImageView) {
        myImageView.setBackgroundResource(R.drawable.pointr);
        isRedOrBlue = true;
        myImageView.setEnabled(false);
        return isRedOrBlue;

    }

    private boolean drowableBluePoint(MyImageView myImageView) {
        myImageView.setBackgroundResource(R.drawable.pointb);
        isRedOrBlue = false;
        myImageView.setEnabled(false);

        return isRedOrBlue;

    }
  public void myFildViewsNotActive(){
          for(int i = 0; i < 25; i++){
              for(int j = 0; j < 32; j++){
                  fieldsGame[i][j].setEnabled(false);
              }
      }
  }
    public void myFildViewsActive(){
        for(int i = 0; i < 25; i++){
            for(int j = 0; j < 32; j++){
               myImageView = (MyImageView) fieldsGame[i][j];
                if(myImageView.getIsActiveView()){
                fieldsGame[i][j].setEnabled(true);
                }
                }
        }
    }
  public void drowableBlueOrRedPointOnline(int i, int j, MoveType moveType){
      if(moveType == MoveType.BLUE_POINT){
      fieldsGame[i][j].setBackgroundResource(R.drawable.pointb);
      myImageView = (MyImageView) fieldsGame[i][j];
      myImageView.setIsActiveView(false);
      }else{
          fieldsGame[i][j].setBackgroundResource(R.drawable.pointr);
      }
  }

    private class EventHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            myImageView = (MyImageView) view;


            if (GlobalControler.getIsOnlineGaame()) {

             if(GlobalControler.getIsFirstOrSecondMove()){
                  drowableBlueOrRedPointOnline(myImageView.getI(), myImageView.getJ(),MoveType.BLUE_POINT);
                  myFildViewsNotActive();

                 MyPointGame.SOneMove sOneMove = MyPointGame.SOneMove.newBuilder()
                         .setI(myImageView.getI())
                         .setJ(myImageView.getJ())
                         .setMoveType(MyPointGame.MoveType.BLUE_POINT).build();
                 GlobalControler.getGlobalControler().getStartClient().sendPacket(sOneMove);


             }else{
             drowableBlueOrRedPointOnline(myImageView.getI(), myImageView.getJ(), MoveType.RED_POINT);
                 myFildViewsNotActive();
                 MyPointGame.SOneMove sOneMove = MyPointGame.SOneMove.newBuilder()
                         .setI(myImageView.getI())
                         .setJ(myImageView.getJ())
                         .setMoveType(MyPointGame.MoveType.RED_POINT).build();
                 GlobalControler.getGlobalControler().getStartClient().sendPacket(sOneMove);

             }

            } else {
                if (isRedOrBlue) {
                    drowableBluePoint(myImageView);
                } else {
                    drowableRedPoint(myImageView);
                }
            }
        }
    }
}
