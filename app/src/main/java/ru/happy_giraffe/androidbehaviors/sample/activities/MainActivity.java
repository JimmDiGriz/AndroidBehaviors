package ru.happy_giraffe.androidbehaviors.sample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.EActivity;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.annotations.ABehavior;
import ru.happy_giraffe.androidbehaviors.containers.BehavioralActivity;
import ru.happy_giraffe.androidbehaviors.sample.behaviors.TestBehavior;

@EActivity(R.layout.activity_main)
public class MainActivity extends BehavioralActivity {
    @ABehavior(name = "test")
    protected TestBehavior testBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    @ABehavior(name = "test2")
//    protected TestBehavior testBehavior2;
//
//    @ABehavior(name = "test3")
//    protected TestBehavior testBehavior3;
//
//    @ABehavior(name = "test4")
//    protected TestBehavior testBehavior4;
}
