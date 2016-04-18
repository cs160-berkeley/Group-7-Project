package cs160.autismbuddie;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
        if (b.getInt("activity") == 0) {
            act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FacesActivity.class);
                    intent.putExtra(getActivity().getPackageName() + ".picker", 1);
                    startActivity(intent);
                }
            });
        } else if (b.getInt("activity") == 1) {
            act.setImageResource(R.drawable.triviahome);
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
