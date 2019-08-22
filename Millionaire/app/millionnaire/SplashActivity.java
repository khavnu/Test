package who.is.millionnaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView mImgPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImgPlay = findViewById(R.id.img_play);
        mImgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGamePlayActivity();
            }
        });
    }

    private void openGamePlayActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
