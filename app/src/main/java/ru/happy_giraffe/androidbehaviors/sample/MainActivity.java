package ru.happy_giraffe.androidbehaviors.sample;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.annotations.ABehavior;
import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.containers.BehavioralActivity;
import ru.happy_giraffe.androidbehaviors.sample.behaviors.TestBehavior;

@EActivity(R.layout.activity_main)
public class MainActivity extends BehavioralActivity {
    @ABehavior(name = "test")
    protected TestBehavior testBehavior;

    @ABehavior(name = "test2")
    protected TestBehavior testBehavior2;

    @ABehavior(name = "test3")
    protected TestBehavior testBehavior3;

    @ABehavior(name = "test4")
    protected TestBehavior testBehavior4;
}
