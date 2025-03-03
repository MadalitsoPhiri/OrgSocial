// Generated by view binder compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.orgsocial.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class BottomSheetPickerBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView bottomSheetTitle;

  @NonNull
  public final LinearLayout cameraButton;

  @NonNull
  public final LinearLayout galleryButton;

  @NonNull
  public final ImageButton groupIcon;

  private BottomSheetPickerBinding(@NonNull LinearLayout rootView,
      @NonNull TextView bottomSheetTitle, @NonNull LinearLayout cameraButton,
      @NonNull LinearLayout galleryButton, @NonNull ImageButton groupIcon) {
    this.rootView = rootView;
    this.bottomSheetTitle = bottomSheetTitle;
    this.cameraButton = cameraButton;
    this.galleryButton = galleryButton;
    this.groupIcon = groupIcon;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetPickerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetPickerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_picker, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetPickerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomSheetTitle;
      TextView bottomSheetTitle = rootView.findViewById(id);
      if (bottomSheetTitle == null) {
        break missingId;
      }

      id = R.id.camera_button;
      LinearLayout cameraButton = rootView.findViewById(id);
      if (cameraButton == null) {
        break missingId;
      }

      id = R.id.gallery_button;
      LinearLayout galleryButton = rootView.findViewById(id);
      if (galleryButton == null) {
        break missingId;
      }

      id = R.id.groupIcon;
      ImageButton groupIcon = rootView.findViewById(id);
      if (groupIcon == null) {
        break missingId;
      }

      return new BottomSheetPickerBinding((LinearLayout) rootView, bottomSheetTitle, cameraButton,
          galleryButton, groupIcon);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
