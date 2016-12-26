package ru.happy_giraffe.androidbehaviors.sample.behaviors;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.behaviors.FragmentBehavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 26.12.2016.
 */
public class TestFragmentBehavior extends FragmentBehavior {
    private TextView messageTxt;
    private int counter = 0;

    public TestFragmentBehavior(@NonNull Container owner, String name, Fragment fragment) {
        super(owner, name, fragment);
    }

    @Override
    public void onStart() {
        super.onStart();

        messageTxt = getFragmentView(R.id.message_txt, TextView.class);

        messageTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageTxt.setText(String.valueOf(counter++));
            }
        });
    }
}
