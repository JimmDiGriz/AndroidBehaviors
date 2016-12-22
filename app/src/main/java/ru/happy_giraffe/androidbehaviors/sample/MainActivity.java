package ru.happy_giraffe.androidbehaviors.sample;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.containers.BehavioralActivity;
import ru.happy_giraffe.androidbehaviors.sample.behaviors.TestBehavior;

@EActivity(R.layout.activity_main)
public class MainActivity extends BehavioralActivity {
    @Override
    public List<ActivityBehavior> getComponents() {
        List<ActivityBehavior> temp = new ArrayList<>();

        temp.add(new TestBehavior(container, "test"));

        return temp;
    }
}
