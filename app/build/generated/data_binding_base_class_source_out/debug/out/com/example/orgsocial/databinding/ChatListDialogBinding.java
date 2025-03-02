// Generated by view binder compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public final class ChatListDialogBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView Username;

  @NonNull
  public final ImageButton chat;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final ImageButton profileInfo;

  private ChatListDialogBinding(@NonNull LinearLayout rootView, @NonNull TextView Username,
      @NonNull ImageButton chat, @NonNull ImageView profileImage,
      @NonNull ImageButton profileInfo) {
    this.rootView = rootView;
    this.Username = Username;
    this.chat = chat;
    this.profileImage = profileImage;
    this.profileInfo = profileInfo;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ChatListDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChatListDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.chat_list_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChatListDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Username;
      TextView Username = rootView.findViewById(id);
      if (Username == null) {
        break missingId;
      }

      id = R.id.chat;
      ImageButton chat = rootView.findViewById(id);
      if (chat == null) {
        break missingId;
      }

      id = R.id.profileImage;
      ImageView profileImage = rootView.findViewById(id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.profileInfo;
      ImageButton profileInfo = rootView.findViewById(id);
      if (profileInfo == null) {
        break missingId;
      }

      return new ChatListDialogBinding((LinearLayout) rootView, Username, chat, profileImage,
          profileInfo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
