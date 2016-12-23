package ru.happy_giraffe.androidbehaviors.core;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.happy_giraffe.androidbehaviors.interfaces.OnBehaviorAttachListener;
import ru.happy_giraffe.androidbehaviors.interfaces.OnBehaviorDetachListener;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */

public final class Container<T extends Behavior> {
    private List<T> components;
    private Context context;
    private Map<String, T> cachedMap;
    private OnBehaviorAttachListener<T> onBehaviorAttachListener;
    private OnBehaviorDetachListener<T> onBehaviorDetachListener;

    public Container(Context context) {
        components = new ArrayList<>();
        cachedMap = new HashMap<>();
        this.context = context;
    }

    public List<T> getComponents() {
        return components;
    }

    @Nullable
    public T getComponent(Class<? extends Behavior> target) {
        for (T component : components) {
            if (component.getClass().equals(target)) {
                return component;
            }
        }

        return null;
    }

    public List<T> getComponents(Class<? extends Behavior> target) {
        List<T> result = new ArrayList<>();

        for (T component : components) {
            if (component.getClass().equals(target)) {
                result.add(component);
            }
        }

        return result;
    }

    @Nullable
    public T getComponent(String name) {
        for (T component : components) {
            if (component.getName().equals(name)) {
                return component;
            }
        }

        return null;
    }

    public void fillBehaviors(List<T> components) {
        for (T component : components) {
            attach(component);
        }
    }

    public void attach(T component) {
        if (cachedMap.containsKey(component.getName())) {
            return; //throws there
        }

        components.add(component);
        cachedMap.put(component.getName(), component);

        callAttachListener(component);
    }

    public void detach(T component) {
        components.remove(component);
        cachedMap.remove(component.getName());

        if (onBehaviorDetachListener != null) {
            onBehaviorDetachListener.detach(component);
        }
    }

    public void attachBefore(T from, T component) {
        int index = components.indexOf(from);

        components.add(index, component);
        cachedMap.put(component.getName(), component);

        callAttachListener(component);
    }

    public void attachAfter(T from, T component) {
        int index = components.indexOf(from);

        components.add(index + 1, component);
        cachedMap.put(component.getName(), component);

        callAttachListener(component);
    }

    public void attachFirst(T component) {
        components.add(0, component);
        cachedMap.put(component.getName(), component);

        callAttachListener(component);
    }

    public void destroy(){
        this.components = null;
        this.cachedMap = null;
        this.context = null;
    }

    public Context getContext() {
        return context;
    }

    public void setOnBehaviorAttachListener(OnBehaviorAttachListener<T> onBehaviorAttachListener) {
        this.onBehaviorAttachListener = onBehaviorAttachListener;
    }

    public void setOnBehaviorDetachListener(OnBehaviorDetachListener<T> onBehaviorDetachListener) {
        this.onBehaviorDetachListener = onBehaviorDetachListener;
    }

    private void callAttachListener(T behavior) {
        if (onBehaviorAttachListener != null) {
            onBehaviorAttachListener.attach(behavior);
        }
    }
}
