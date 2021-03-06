package ru.happy_giraffe.androidbehaviors.sample.behaviors;

import android.support.annotation.NonNull;
import android.widget.TextView;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.annotations.BClick;
import ru.happy_giraffe.androidbehaviors.annotations.BViewById;
import ru.happy_giraffe.androidbehaviors.annotations.resources.BStringRes;
import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public class TestBehavior extends ActivityBehavior {
    @BViewById(R.id.message_txt)
    protected TextView messageTxt;

    @BStringRes(R.string.test_string)
    protected String testString;

    private int counter = 0;

    public TestBehavior(@NonNull Container owner, String name) {
        super(owner, name);
    }

    @Override
    public void onStart() {
        super.onStart();

        messageTxt.setText(testString);
    }

    @BClick(R.id.message_txt)
    protected void onMessageTxtClick() {
        messageTxt.setText(String.valueOf(counter++));
    }
}
