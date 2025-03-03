// Generated by view binder compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.orgsocial.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentStatusBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView chatLastMessage;

  @NonNull
  public final TextView chatName;

  @NonNull
  public final CircularImageView chatProfile;

  private FragmentStatusBinding(@NonNull LinearLayout rootView, @NonNull TextView chatLastMessage,
      @NonNull TextView chatName, @NonNull CircularImageView chatProfile) {
    this.rootView = rootView;
    this.chatLastMessage = chatLastMessage;
    this.chatName = chatName;
    this.chatProfile = chatProfile;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentStatusBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentStatusBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_status, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentStatusBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.chat_last_message;
      TextView chatLastMessage = rootView.findViewById(id);
      if (chatLastMessage == null) {
        break missingId;
      }

      id = R.id.chat_name;
      TextView chatName = rootView.findViewById(id);
      if (chatName == null) {
        break missingId;
      }

      id = R.id.chat_profile;
      CircularImageView chatProfile = rootView.findViewById(id);
      if (chatProfile == null) {
        break missingId;
      }

      return new FragmentStatusBinding((LinearLayout) rootView, chatLastMessage, chatName,
          chatProfile);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
