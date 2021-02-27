// Generated by view binder compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.orgsocial.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentWelcomeBinding implements ViewBinding {
  @NonNull
  private final View rootView;

  @NonNull
  public final Button acceptPrivacyPolicyBtn;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final LinearLayout linearLayout7;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView8;

  private FragmentWelcomeBinding(@NonNull View rootView, @NonNull Button acceptPrivacyPolicyBtn,
      @NonNull ImageView imageView3, @NonNull LinearLayout linearLayout7,
      @NonNull TextView textView7, @NonNull TextView textView8) {
    this.rootView = rootView;
    this.acceptPrivacyPolicyBtn = acceptPrivacyPolicyBtn;
    this.imageView3 = imageView3;
    this.linearLayout7 = linearLayout7;
    this.textView7 = textView7;
    this.textView8 = textView8;
  }

  @Override
  @NonNull
  public View getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentWelcomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentWelcomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_welcome, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentWelcomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.accept_privacy_policy_btn;
      Button acceptPrivacyPolicyBtn = rootView.findViewById(id);
      if (acceptPrivacyPolicyBtn == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = rootView.findViewById(id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.linearLayout7;
      LinearLayout linearLayout7 = rootView.findViewById(id);
      if (linearLayout7 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = rootView.findViewById(id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = rootView.findViewById(id);
      if (textView8 == null) {
        break missingId;
      }

      return new FragmentWelcomeBinding(rootView, acceptPrivacyPolicyBtn, imageView3, linearLayout7,
          textView7, textView8);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
