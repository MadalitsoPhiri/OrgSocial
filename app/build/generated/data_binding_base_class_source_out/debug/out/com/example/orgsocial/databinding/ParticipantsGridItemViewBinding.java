// Generated by view binder compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.orgsocial.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ParticipantsGridItemViewBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CircularImageView profileImage;

  @NonNull
  public final ProgressBar profileImgProgress;

  @NonNull
  public final TextView username;

  private ParticipantsGridItemViewBinding(@NonNull LinearLayout rootView,
      @NonNull CircularImageView profileImage, @NonNull ProgressBar profileImgProgress,
      @NonNull TextView username) {
    this.rootView = rootView;
    this.profileImage = profileImage;
    this.profileImgProgress = profileImgProgress;
    this.username = username;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ParticipantsGridItemViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ParticipantsGridItemViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.participants_grid_item_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ParticipantsGridItemViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.profileImage;
      CircularImageView profileImage = rootView.findViewById(id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.profileImgProgress;
      ProgressBar profileImgProgress = rootView.findViewById(id);
      if (profileImgProgress == null) {
        break missingId;
      }

      id = R.id.username;
      TextView username = rootView.findViewById(id);
      if (username == null) {
        break missingId;
      }

      return new ParticipantsGridItemViewBinding((LinearLayout) rootView, profileImage,
          profileImgProgress, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
