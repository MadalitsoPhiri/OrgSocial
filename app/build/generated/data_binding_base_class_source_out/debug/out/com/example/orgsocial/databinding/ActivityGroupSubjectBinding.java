// Generated by data binding compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orgsocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityGroupSubjectBinding extends ViewDataBinding {
  @NonNull
  public final FloatingActionButton confirmGroup;

  @NonNull
  public final CircularImageView groupIcon;

  @NonNull
  public final EditText groupSubject;

  @NonNull
  public final TextView participantCount;

  @NonNull
  public final TextView participants;

  @NonNull
  public final RecyclerView participantsDrawerRecycler;

  @NonNull
  public final Toolbar toolbar;

  protected ActivityGroupSubjectBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FloatingActionButton confirmGroup, CircularImageView groupIcon, EditText groupSubject,
      TextView participantCount, TextView participants, RecyclerView participantsDrawerRecycler,
      Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.confirmGroup = confirmGroup;
    this.groupIcon = groupIcon;
    this.groupSubject = groupSubject;
    this.participantCount = participantCount;
    this.participants = participants;
    this.participantsDrawerRecycler = participantsDrawerRecycler;
    this.toolbar = toolbar;
  }

  @NonNull
  public static ActivityGroupSubjectBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_group_subject, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityGroupSubjectBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityGroupSubjectBinding>inflateInternal(inflater, R.layout.activity_group_subject, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityGroupSubjectBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_group_subject, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityGroupSubjectBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityGroupSubjectBinding>inflateInternal(inflater, R.layout.activity_group_subject, null, false, component);
  }

  public static ActivityGroupSubjectBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityGroupSubjectBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityGroupSubjectBinding)bind(component, view, R.layout.activity_group_subject);
  }
}
