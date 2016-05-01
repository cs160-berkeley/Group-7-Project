package cs160.autismbuddie;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by robinhu on 4/17/16.
 */
public class ActivityFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);
        Bundle b = getArguments();

        ImageView act = (ImageView) v.findViewById(R.id.img);
        String home_screen = b.getString("home_screen1", "");
        if (home_screen.length() > 0) {
            Bitmap btmp = MainActivity.getBitmapFromString(home_screen);
            act.setImageBitmap(btmp);
        }
        if (b.getInt("activity") == 0) {
            if (MainActivity.PACK) {
                act.setImageResource(R.drawable.faces_home2);
            }
            act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FacesActivity.class);
                    intent.putExtra(getActivity().getPackageName() + ".picker", 1);
                    startActivity(intent);
                }
            });
        } else if (b.getInt("activity") == 1) {
            if (MainActivity.PACK) {
                act.setImageResource(R.drawable.trivia_home2);
            }
            act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TriviaActivity.class);
                    intent.putExtra(getActivity().getPackageName() + ".picker", 1);
                    startActivity(intent);
                }
            });
        }
        return v;
    }
}
