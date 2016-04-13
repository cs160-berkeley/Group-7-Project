package cs160.autismbuddie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind card views
        CardView sendCard = (CardView)findViewById(R.id.sendCard);
        CardView reminderCard = (CardView)findViewById(R.id.reminderCard);
        CardView packagesCard = (CardView)findViewById(R.id.packagesCard);
        CardView modesCard = (CardView)findViewById(R.id.modesCard);

        // Set on click listeners
        if(reminderCard != null)
            reminderCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
                    startActivity(intent);
                }
            });
        if(packagesCard != null)
            packagesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GetPackagesActivity.class);
                    startActivity(intent);
                }
            });
        if(modesCard != null)
            modesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Mode switched!", Toast.LENGTH_SHORT).show();
                }
            });


    }
}
