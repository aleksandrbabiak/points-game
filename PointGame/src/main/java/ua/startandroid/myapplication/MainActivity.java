package ua.startandroid.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.gui_to_point_game.SelectPlayGameActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button start;
    private Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start = (Button) findViewById(R.id.mainActivity_start);
        start.setOnClickListener(this);
        exit = (Button) findViewById(R.id.mainActivity_exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mainActivity_start:
                Intent intent = new Intent(this, SelectPlayGameActivity.class);
               startActivity(intent);
                break;

            case R.id.mainActivity_exit:
                finish();
                break;
        }
    }
}
