package ru.happy_giraffe.androidbehaviors.containers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.happy_giraffe.androidbehaviors.annotations.ABehavior;
import ru.happy_giraffe.androidbehaviors.behaviors.FragmentBehavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

/**
 * Created by JimmDiGriz on 23.12.2016.
 */
public class BehavioralFragment extends Fragment {
    protected Container<FragmentBehavior> container;

    public BehavioralFragment() {
        super();

        container = new Container<>(getActivity());
        container.fillBehaviors(getComponents());
    }

    @SuppressWarnings("unchecked")
    public List<FragmentBehavior> getComponents() {
        Class<? extends BehavioralFragment> object = getClass();

        List<FragmentBehavior> temp = new ArrayList<>();

        for (Field field : object.getFields()) {
            try {
                if (!field.isAnnotationPresent(ABehavior.class)) {
                    continue;
                }

                if (field.get(this) != null) {
                    temp.add((FragmentBehavior) field.get(this));
                    continue;
                }

                ABehavior annotation = field.getAnnotation(ABehavior.class);
                String name = annotation.name();

                Class type = field.getType();

                Class[] constructorParamTypes = new Class[] {Container.class, String.class, Fragment.class};

                Constructor constructor = type.getConstructor(constructorParamTypes);

                if (constructor == null) {
                    continue;
                }

                temp.add((FragmentBehavior) constructor.newInstance(container, name, this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onAttach(context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onCreate(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);

        for (FragmentBehavior behavior : this.container.getComponents()) {
            behavior.onCreateView(inflater, container, savedInstanceState);
        }

        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onViewCreated(view, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        for (FragmentBehavior behavior : container.getComponents()) {
            behavior.onDetach();
        }
    }
}
