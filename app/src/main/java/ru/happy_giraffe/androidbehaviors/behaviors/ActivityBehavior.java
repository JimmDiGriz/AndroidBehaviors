package ru.happy_giraffe.androidbehaviors.behaviors;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.happy_giraffe.androidbehaviors.core.Behavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public abstract class ActivityBehavior extends Behavior {
    public ActivityBehavior(@NonNull Container owner, String name) {
        super(owner, name);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    /**
     * if owner activity is @EActivity from android annotations, then in onStart() method we can start work with views.
     * if owner activity is not @EActivity, then we can start work with views in onCreate(),
     * but in this case we need to place set content view before call super, facing NullPointerException otherwise.
     * cause of it onStart() always is better place for starting work with behavior.
     * */
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

    protected View getView(int id) {
        return getActivity().findViewById(id);
    }

    protected <T extends View> T getView(int id, Class<T> cls) {
        return cls.cast(getView(id));
    }

    protected AppCompatActivity getActivity() {
        return (AppCompatActivity) owner.getContext();
    }

    protected <T extends AppCompatActivity> T getActivity(Class<T> cls) {
        return cls.cast(owner.getContext());
    }
}
