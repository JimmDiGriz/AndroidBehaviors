package ru.happy_giraffe.androidbehaviors.sample.behaviors;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EBean;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.containers.BehavioralActivity;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public class TestBehavior extends ActivityBehavior {
    private TextView messageTxt;
    private int counter = 0;

    public TestBehavior(@NonNull Container owner, String name) {
        super(owner, name);
    }

    @Override
    public void onStart() {
        super.onStart();

        messageTxt = getView(R.id.message_txt, TextView.class);

        messageTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageTxt.setText(String.valueOf(counter++));
            }
        });
    }
}
