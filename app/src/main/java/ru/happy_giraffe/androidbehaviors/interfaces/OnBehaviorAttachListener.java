package ru.happy_giraffe.androidbehaviors.interfaces;

import ru.happy_giraffe.androidbehaviors.core.Behavior;

/**
 * Created by JimmDiGriz on 23.12.2016.
 */

public interface OnBehaviorAttachListener<T> {
    void attach(T behavior);
}
