package ru.happy_giraffe.androidbehaviors.behaviors;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ru.happy_giraffe.androidbehaviors.annotations.BClick;
import ru.happy_giraffe.androidbehaviors.annotations.BViewById;
import ru.happy_giraffe.androidbehaviors.core.Behavior;
import ru.happy_giraffe.androidbehaviors.core.Container;
import ru.happy_giraffe.androidbehaviors.utils.ResourcesLoader;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */
public abstract class FragmentBehavior extends Behavior {
    private Fragment fragment;
    private View fragmentView;

    public FragmentBehavior(@NonNull Container owner, String name, Fragment fragment) {
        super(owner, name);

        this.fragment = fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fragmentView = view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    public void onStart() {
        Class<? extends FragmentBehavior> object = getClass();

        getAnnotatedViews(object);
        attachClickListeners(object);
        loadResources(object);
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroyView() {

    }

    @CallSuper
    public void onDestroy() {
        if (this.owner != null) {
            this.owner.destroy();
        }

        if (this.fragment != null) {
            this.fragment = null;
        }

        if (this.fragmentView != null) {
            this.fragmentView = null;
        }
    }

    public void onDetach() {

    }

    protected View getView(int id) {
        return getActivity().findViewById(id);
    }

    protected <T extends View> T getView(int id, Class<T> cls) {
        return cls.cast(getView(id));
    }

    protected View getFragmentView(int id) {
        return fragmentView.findViewById(id);
    }

    protected <T extends View> T getFragmentView(int id, Class<T> cls) {
        return cls.cast(getFragmentView(id));
    }

    protected AppCompatActivity getActivity() {
        return (AppCompatActivity) owner.getContext();
    }

    protected <T extends AppCompatActivity> T getActivity(Class<T> cls) {
        return cls.cast(owner.getContext());
    }

    protected Fragment getFragment() {
        return this.fragment;
    }

    protected <T extends Fragment> T getFragment(Class<T> cls) {
        return cls.cast(getFragment());
    }

    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    @SuppressWarnings("unchecked")
    private void getAnnotatedViews(Class<? extends FragmentBehavior> object) {
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
                    continue;
                }

                field.set(this, getFragmentView(id, type));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void attachClickListeners(Class<? extends FragmentBehavior> object) {
        for (final Method method: object.getMethods()) {
            try {
                if (!method.isAnnotationPresent(BClick.class)) {
                    continue;
                }

                BClick annotation = method.getAnnotation(BClick.class);

                int[] ids = annotation.value();

                for (int id : ids) {
                    View v = getFragmentView(id);

                    if (v == null) {
                        continue;
                    }

                    final Class[] paramTypes = method.getParameterTypes();
                    final boolean isValidParams = paramTypes.length == 1 && View.class.isAssignableFrom(paramTypes[0]);

                    if (paramTypes.length > 1) {
                        break;
                    }

                    if (paramTypes.length == 1 && !View.class.isAssignableFrom(paramTypes[0])) {
                        break;
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (!isValidParams) {
                                    method.invoke(FragmentBehavior.this);
                                    return;
                                }

                                method.invoke(FragmentBehavior.this, view);
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

    private void loadResources(Class<? extends FragmentBehavior> object) {
        ResourcesLoader.loadResources(object, getActivity(), this);
    }
}
