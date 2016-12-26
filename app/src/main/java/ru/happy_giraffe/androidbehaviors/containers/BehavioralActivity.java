package ru.happy_giraffe.androidbehaviors.containers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.happy_giraffe.androidbehaviors.annotations.ABehavior;
import ru.happy_giraffe.androidbehaviors.behaviors.ActivityBehavior;
import ru.happy_giraffe.androidbehaviors.core.Container;
import ru.happy_giraffe.androidbehaviors.exceptions.DuplicateBehaviorsInContainer;
import ru.happy_giraffe.androidbehaviors.interfaces.OnBehaviorAttachListener;
import ru.happy_giraffe.androidbehaviors.interfaces.OnBehaviorDetachListener;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public abstract class BehavioralActivity extends AppCompatActivity {
    protected Container<ActivityBehavior> container;

    public BehavioralActivity() {
        super();

        container = new Container<>(this);
        container.fillBehaviors(getComponents());

        container.setOnBehaviorAttachListener(new OnBehaviorAttachListener<ActivityBehavior>() {
            @Override
            public void attach(ActivityBehavior behavior) {
                onBehaviorAttach(behavior);
            }
        });

        container.setOnBehaviorDetachListener(new OnBehaviorDetachListener<ActivityBehavior>() {
            @Override
            public void detach(ActivityBehavior behavior) {
                onBehaviorDetach(behavior);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<ActivityBehavior> getComponents() {
        Class<? extends BehavioralActivity> object = getClass();

        List<ActivityBehavior> temp = new ArrayList<>();

        for (Field field: object.getFields()) {
            try {
                if (!field.isAnnotationPresent(ABehavior.class)) {
                    continue;
                }

                if (field.get(this) != null) {
                    temp.add((ActivityBehavior) field.get(this));
                    continue;
                }

                ABehavior annotation = field.getAnnotation(ABehavior.class);

                String name = annotation.name();

                Class type = field.getType();

                Class[] constructorParamTypes = new Class[] {Container.class, String.class};

                Constructor constructor = type.getConstructor(constructorParamTypes);

                if (constructor == null) {
                    continue;
                }

                temp.add((ActivityBehavior) constructor.newInstance(container, name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return temp;
    }

    public void attach(ActivityBehavior component) {
        try {
            container.attach(component);
        } catch (DuplicateBehaviorsInContainer duplicateBehaviorsInContainer) {
            duplicateBehaviorsInContainer.printStackTrace();
        }
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
            behavior.onCreate(savedInstanceState);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (ActivityBehavior behavior : container.getComponents()) {
            behavior.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void onBehaviorAttach(ActivityBehavior behavior) {

    }

    protected void onBehaviorDetach(ActivityBehavior behavior) {

    }
}
