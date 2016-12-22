package ru.happy_giraffe.androidbehaviors.containers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public abstract class BehavioralActivity extends AppCompatActivity {
    protected Container<ActivityBehavior> container;

    public BehavioralActivity() {
        super();

        container = new Container<>(this);
        container.fillBehaviors(getComponents());
    }

    public abstract List<ActivityBehavior> getComponents();

    public void attach(ActivityBehavior component) {
        container.attach(component);
    }

    public void detach(ActivityBehavior component) {
        container.detach(component);
    }

    public void attachBefore(ActivityBehavior from, ActivityBehavior component) {
        container.attachBefore(from, component);
    }

    public void attachAfter(ActivityBehavior from, ActivityBehavior component) {
        container.attachAfter(from, component);
    }

    public void attachFirst(ActivityBehavior component) {
        container.attachFirst(component);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onCreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onDestroy();
        }

        super.onDestroy();
    }
}
