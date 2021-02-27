// Generated by data binding compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.orgsocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityProfileBinding extends ViewDataBinding {
  @NonNull
  public final TextView detailBio;

  @NonNull
  public final ImageButton detailBioEdit;

  @NonNull
  public final TextView detailEmail;

  @NonNull
  public final TextView detailName;

  @NonNull
  public final ImageButton detailNameEdit;

  @NonNull
  public final TextView detailPhone;

  @NonNull
  public final ImageButton detailPhoneEdit;

  @NonNull
  public final FloatingActionButton fabCamera;

  @NonNull
  public final CircularImageView profileImg;

  @NonNull
  public final ProgressBar profileImgProgress;

  @NonNull
  public final Toolbar toolbar;

  protected ActivityProfileBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView detailBio, ImageButton detailBioEdit, TextView detailEmail, TextView detailName,
      ImageButton detailNameEdit, TextView detailPhone, ImageButton detailPhoneEdit,
      FloatingActionButton fabCamera, CircularImageView profileImg, ProgressBar profileImgProgress,
      Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.detailBio = detailBio;
    this.detailBioEdit = detailBioEdit;
    this.detailEmail = detailEmail;
    this.detailName = detailName;
    this.detailNameEdit = detailNameEdit;
    this.detailPhone = detailPhone;
    this.detailPhoneEdit = detailPhoneEdit;
    this.fabCamera = fabCamera;
    this.profileImg = profileImg;
    this.profileImgProgress = profileImgProgress;
    this.toolbar = toolbar;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_profile, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityProfileBinding>inflateInternal(inflater, R.layout.activity_profile, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_profile, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityProfileBinding>inflateInternal(inflater, R.layout.activity_profile, null, false, component);
  }

  public static ActivityProfileBinding bind(@NonNull View view) {
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
  public static ActivityProfileBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityProfileBinding)bind(component, view, R.layout.activity_profile);
  }
}
