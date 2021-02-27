// Generated by view binder compiler. Do not edit!
package com.example.orgsocial.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.orgsocial.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class BottomSheetEditPhoneBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnSave;

  @NonNull
  public final EditText editPhone;

  private BottomSheetEditPhoneBinding(@NonNull LinearLayout rootView, @NonNull Button btnCancel,
      @NonNull Button btnSave, @NonNull EditText editPhone) {
    this.rootView = rootView;
    this.btnCancel = btnCancel;
    this.btnSave = btnSave;
    this.editPhone = editPhone;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BottomSheetEditPhoneBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BottomSheetEditPhoneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.bottom_sheet_edit_phone, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BottomSheetEditPhoneBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_cancel;
      Button btnCancel = rootView.findViewById(id);
      if (btnCancel == null) {
        break missingId;
      }

      id = R.id.btn_save;
      Button btnSave = rootView.findViewById(id);
      if (btnSave == null) {
        break missingId;
      }

      id = R.id.edit_phone;
      EditText editPhone = rootView.findViewById(id);
      if (editPhone == null) {
        break missingId;
      }

      return new BottomSheetEditPhoneBinding((LinearLayout) rootView, btnCancel, btnSave,
          editPhone);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
