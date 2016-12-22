package ru.happy_giraffe.androidbehaviors.core;

import android.support.annotation.NonNull;

/**
 * Created by JimmDiGriz on 22.12.2016.
 */

public abstract class Behavior {
    protected Container owner;
    protected String name;

    public Behavior(@NonNull Container owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public Behavior getComponent() {
        return null;
    }

    public Container getOwner() {
        return owner;
    }

    public String getName() {
        return this.name;
    }
}
