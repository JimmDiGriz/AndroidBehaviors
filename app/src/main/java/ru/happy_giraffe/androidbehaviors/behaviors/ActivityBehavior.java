package ru.happy_giraffe.androidbehaviors.behaviors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ru.happy_giraffe.androidbehaviors.annotations.BClick;
import ru.happy_giraffe.androidbehaviors.annotations.BViewById;
import ru.happy_giraffe.androidbehaviors.core.Behavior;
import ru.happy_giraffe.androidbehaviors.core.Container;
import ru.happy_giraffe.androidbehaviors.exceptions.FieldMustBeInstanceOfView;
import ru.happy_giraffe.androidbehaviors.exceptions.InvalidOnClickSignature;
import ru.happy_giraffe.androidbehaviors.exceptions.TargetViewNotFound;
import ru.happy_giraffe.androidbehaviors.utils.ExtrasLoader;
import ru.happy_giraffe.androidbehaviors.utils.ResourcesLoader;

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
    @CallSuper
    public void onStart() {
        Class<? extends ActivityBehavior> object = getClass();

        getAnnotatedViews(object);
        attachClickListeners(object);
        loadResources(object);
        getAnnotatedExtras(object);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

    @SuppressWarnings("unchecked")
    private void getAnnotatedViews(Class<? extends ActivityBehavior> object) {
        for (Field field: object.getFields()) {
            try {
                if (!field.isAnnotationPresent(BViewById.class)) {
                    continue;
                }

                if (field.get(this) != null) {
                    continue;
                }

                BViewById annotation = field.getAnnotation(BViewById.class);

                int id = annotation.value();

                Class type = field.getType();

                if (!View.class.isAssignableFrom(type)) {
                    throw new FieldMustBeInstanceOfView();
                }

                field.set(this, getView(id, type));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void attachClickListeners(Class<? extends ActivityBehavior> object) {
        for (final Method method: object.getMethods()) {
            try {
                if (!method.isAnnotationPresent(BClick.class)) {
                    continue;
                }

                BClick annotation = method.getAnnotation(BClick.class);

                int[] ids = annotation.value();

                for (int id : ids) {
                    View v = getView(id);

                    if (v == null) {
                        throw new TargetViewNotFound();
                    }

                    final Class[] paramTypes = method.getParameterTypes();
                    final boolean isValidParams = paramTypes.length == 1 && View.class.isAssignableFrom(paramTypes[0]);

                    if (paramTypes.length > 1) {
                        throw new InvalidOnClickSignature();
                    }

                    if (paramTypes.length == 1 && !View.class.isAssignableFrom(paramTypes[0])) {
                        throw new InvalidOnClickSignature();
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (!isValidParams) {
                                    method.invoke(ActivityBehavior.this);
                                    return;
                                }

                                method.invoke(ActivityBehavior.this, view);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void getAnnotatedExtras(Class<? extends ActivityBehavior> object) {
        ExtrasLoader.loadExtras(this, getActivity());
    }

    private void loadResources(Class<? extends ActivityBehavior> object) {
        ResourcesLoader.loadResources(object, getActivity(), this);
    }
}
