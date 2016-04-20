package cs160.autismbuddie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        if(getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton imageButton = (ImageButton)findViewById(R.id.reminderImageButton);
        if(imageButton != null)
        {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(Utils.TAG, "reminder sent, sending brush your teeth");
                    MainActivity.ptwUtil.sendMessage(PhoneToWatchUtil.PATH_SEND_REMINDER, "Brush your teeth");
                }
            });
        }

        /*
        RecyclerView reminderRecycler = (RecyclerView)findViewById(R.id.reminderRecycler);
        reminderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("Brush your teeth"));
        reminders.add(new Reminder("Take binder to school"));
        reminders.add(new Reminder("Finish your readings"));
        reminders.add(new Reminder("Return library book"));

        Reminder finished = new Reminder("Get permission slip signed");
        finished.checked = true;
        reminders.add(finished);
        finished.text = "Practice piano for an hour";
        reminders.add(finished);

        reminderRecycler.setAdapter(new RemindersAdapter(reminders));
        */

    }
}
