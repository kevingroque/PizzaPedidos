package app.roque.com.pizzapedidos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class InicioActivity extends AppCompatActivity {

    private VideoView videoview;
    private Button btnOrdenar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        videoview = (VideoView)findViewById(R.id.videoview);
        btnOrdenar = (Button)findViewById(R.id.btnOrdenar);

        videoview.setMediaController(new MediaController(this));
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_pizza));
        videoview.start();

        btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
    }
}
