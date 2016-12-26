package ru.happy_giraffe.androidbehaviors.sample.fragments;

import org.androidannotations.annotations.EFragment;

import ru.happy_giraffe.androidbehaviors.R;
import ru.happy_giraffe.androidbehaviors.annotations.ABehavior;
import ru.happy_giraffe.androidbehaviors.containers.BehavioralFragment;
import ru.happy_giraffe.androidbehaviors.sample.behaviors.TestFragmentBehavior;

/**
 * Created by JimmDiGriz on 26.12.2016.
 */
@EFragment(R.layout.fragment_test)
public class TestFragment extends BehavioralFragment {
    @ABehavior(name="test")
    protected TestFragmentBehavior testFragmentBehavior;
}
