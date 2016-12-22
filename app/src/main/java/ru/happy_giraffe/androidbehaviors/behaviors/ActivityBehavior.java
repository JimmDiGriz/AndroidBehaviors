package ru.happy_giraffe.androidbehaviors.behaviors;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import ru.happy_giraffe.androidbehaviors.core.Behavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public abstract class ActivityBehavior extends Behavior {
    public ActivityBehavior(@NonNull Container owner, String name) {
        super(owner, name);
    }

    public void onCreate() {

    }

    public void onStart() {

    }

    public void onRestart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    @CallSuper
    public void onDestroy() {
        if (this.owner != null) {
            this.owner.destroy();
        }
    }
}
