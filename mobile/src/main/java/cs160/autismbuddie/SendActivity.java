package cs160.autismbuddie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ImageButton imageButton = (ImageButton)findViewById(R.id.sendImageButton);
        if(imageButton != null)
        {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(Utils.TAG, "Send clicked, sending trivia");
                    MainActivity.ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_TRIVIA, "pokemon");
                }
            });
        }
    }
}
