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

import ru.happy_giraffe.androidbehaviors.core.Behavior;
import ru.happy_giraffe.androidbehaviors.core.Container;

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

    public void onAttach(Context context) {

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
}
