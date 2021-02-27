package com.example.orgsocial;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.orgsocial.databinding.ActivityAccountSettingsBindingImpl;
import com.example.orgsocial.databinding.ActivityAddParticipantsBindingImpl;
import com.example.orgsocial.databinding.ActivityChatProfileBindingImpl;
import com.example.orgsocial.databinding.ActivityChatsBindingImpl;
import com.example.orgsocial.databinding.ActivityContactsBindingImpl;
import com.example.orgsocial.databinding.ActivityEditGroupAdminsBindingImpl;
import com.example.orgsocial.databinding.ActivityEditGroupSubjectBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupChatBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupDescriptionBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupListBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupProfileBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupSettingsBindingImpl;
import com.example.orgsocial.databinding.ActivityGroupSubjectBindingImpl;
import com.example.orgsocial.databinding.ActivityMainBindingImpl;
import com.example.orgsocial.databinding.ActivityParticipantsGroupsBindingImpl;
import com.example.orgsocial.databinding.ActivityProfileBindingImpl;
import com.example.orgsocial.databinding.ActivitySettingsBindingImpl;
import com.example.orgsocial.databinding.ActivityViewImageBindingImpl;
import com.example.orgsocial.databinding.FragmentCallsBindingImpl;
import com.example.orgsocial.databinding.FragmentChatsBindingImpl;
import com.example.orgsocial.databinding.FragmentEventsBindingImpl;
import com.example.orgsocial.databinding.FragmentGroupsBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYACCOUNTSETTINGS = 1;

  private static final int LAYOUT_ACTIVITYADDPARTICIPANTS = 2;

  private static final int LAYOUT_ACTIVITYCHATPROFILE = 3;

  private static final int LAYOUT_ACTIVITYCHATS = 4;

  private static final int LAYOUT_ACTIVITYCONTACTS = 5;

  private static final int LAYOUT_ACTIVITYEDITGROUPADMINS = 6;

  private static final int LAYOUT_ACTIVITYEDITGROUPSUBJECT = 7;

  private static final int LAYOUT_ACTIVITYGROUPCHAT = 8;

  private static final int LAYOUT_ACTIVITYGROUPDESCRIPTION = 9;

  private static final int LAYOUT_ACTIVITYGROUPLIST = 10;

  private static final int LAYOUT_ACTIVITYGROUPPROFILE = 11;

  private static final int LAYOUT_ACTIVITYGROUPSETTINGS = 12;

  private static final int LAYOUT_ACTIVITYGROUPSUBJECT = 13;

  private static final int LAYOUT_ACTIVITYMAIN = 14;

  private static final int LAYOUT_ACTIVITYPARTICIPANTSGROUPS = 15;

  private static final int LAYOUT_ACTIVITYPROFILE = 16;

  private static final int LAYOUT_ACTIVITYSETTINGS = 17;

  private static final int LAYOUT_ACTIVITYVIEWIMAGE = 18;

  private static final int LAYOUT_FRAGMENTCALLS = 19;

  private static final int LAYOUT_FRAGMENTCHATS = 20;

  private static final int LAYOUT_FRAGMENTEVENTS = 21;

  private static final int LAYOUT_FRAGMENTGROUPS = 22;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(22);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_account_settings, LAYOUT_ACTIVITYACCOUNTSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_add_participants, LAYOUT_ACTIVITYADDPARTICIPANTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_chat_profile, LAYOUT_ACTIVITYCHATPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_chats, LAYOUT_ACTIVITYCHATS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_contacts, LAYOUT_ACTIVITYCONTACTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_edit_group_admins, LAYOUT_ACTIVITYEDITGROUPADMINS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_edit_group_subject, LAYOUT_ACTIVITYEDITGROUPSUBJECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_group_chat, LAYOUT_ACTIVITYGROUPCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_group_description, LAYOUT_ACTIVITYGROUPDESCRIPTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_group_list, LAYOUT_ACTIVITYGROUPLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_group_profile, LAYOUT_ACTIVITYGROUPPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_group_settings, LAYOUT_ACTIVITYGROUPSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_group_subject, LAYOUT_ACTIVITYGROUPSUBJECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_participants_groups, LAYOUT_ACTIVITYPARTICIPANTSGROUPS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_profile, LAYOUT_ACTIVITYPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_settings, LAYOUT_ACTIVITYSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.activity_view_image, LAYOUT_ACTIVITYVIEWIMAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.fragment_calls, LAYOUT_FRAGMENTCALLS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.fragment_chats, LAYOUT_FRAGMENTCHATS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.fragment_events, LAYOUT_FRAGMENTEVENTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.orgsocial.R.layout.fragment_groups, LAYOUT_FRAGMENTGROUPS);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYACCOUNTSETTINGS: {
          if ("layout/activity_account_settings_0".equals(tag)) {
            return new ActivityAccountSettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_account_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYADDPARTICIPANTS: {
          if ("layout/activity_add_participants_0".equals(tag)) {
            return new ActivityAddParticipantsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_add_participants is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCHATPROFILE: {
          if ("layout/activity_chat_profile_0".equals(tag)) {
            return new ActivityChatProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_chat_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCHATS: {
          if ("layout/activity_chats_0".equals(tag)) {
            return new ActivityChatsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_chats is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCONTACTS: {
          if ("layout/activity_contacts_0".equals(tag)) {
            return new ActivityContactsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_contacts is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYEDITGROUPADMINS: {
          if ("layout/activity_edit_group_admins_0".equals(tag)) {
            return new ActivityEditGroupAdminsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_edit_group_admins is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYEDITGROUPSUBJECT: {
          if ("layout/activity_edit_group_subject_0".equals(tag)) {
            return new ActivityEditGroupSubjectBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_edit_group_subject is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPCHAT: {
          if ("layout/activity_group_chat_0".equals(tag)) {
            return new ActivityGroupChatBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPDESCRIPTION: {
          if ("layout/activity_group_description_0".equals(tag)) {
            return new ActivityGroupDescriptionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_description is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPLIST: {
          if ("layout/activity_group_list_0".equals(tag)) {
            return new ActivityGroupListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPPROFILE: {
          if ("layout/activity_group_profile_0".equals(tag)) {
            return new ActivityGroupProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPSETTINGS: {
          if ("layout/activity_group_settings_0".equals(tag)) {
            return new ActivityGroupSettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYGROUPSUBJECT: {
          if ("layout/activity_group_subject_0".equals(tag)) {
            return new ActivityGroupSubjectBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_group_subject is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPARTICIPANTSGROUPS: {
          if ("layout/activity_participants_groups_0".equals(tag)) {
            return new ActivityParticipantsGroupsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_participants_groups is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPROFILE: {
          if ("layout/activity_profile_0".equals(tag)) {
            return new ActivityProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSETTINGS: {
          if ("layout/activity_settings_0".equals(tag)) {
            return new ActivitySettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYVIEWIMAGE: {
          if ("layout/activity_view_image_0".equals(tag)) {
            return new ActivityViewImageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_view_image is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCALLS: {
          if ("layout/fragment_calls_0".equals(tag)) {
            return new FragmentCallsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_calls is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCHATS: {
          if ("layout/fragment_chats_0".equals(tag)) {
            return new FragmentChatsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_chats is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTEVENTS: {
          if ("layout/fragment_events_0".equals(tag)) {
            return new FragmentEventsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_events is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTGROUPS: {
          if ("layout/fragment_groups_0".equals(tag)) {
            return new FragmentGroupsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_groups is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(22);

    static {
      sKeys.put("layout/activity_account_settings_0", com.example.orgsocial.R.layout.activity_account_settings);
      sKeys.put("layout/activity_add_participants_0", com.example.orgsocial.R.layout.activity_add_participants);
      sKeys.put("layout/activity_chat_profile_0", com.example.orgsocial.R.layout.activity_chat_profile);
      sKeys.put("layout/activity_chats_0", com.example.orgsocial.R.layout.activity_chats);
      sKeys.put("layout/activity_contacts_0", com.example.orgsocial.R.layout.activity_contacts);
      sKeys.put("layout/activity_edit_group_admins_0", com.example.orgsocial.R.layout.activity_edit_group_admins);
      sKeys.put("layout/activity_edit_group_subject_0", com.example.orgsocial.R.layout.activity_edit_group_subject);
      sKeys.put("layout/activity_group_chat_0", com.example.orgsocial.R.layout.activity_group_chat);
      sKeys.put("layout/activity_group_description_0", com.example.orgsocial.R.layout.activity_group_description);
      sKeys.put("layout/activity_group_list_0", com.example.orgsocial.R.layout.activity_group_list);
      sKeys.put("layout/activity_group_profile_0", com.example.orgsocial.R.layout.activity_group_profile);
      sKeys.put("layout/activity_group_settings_0", com.example.orgsocial.R.layout.activity_group_settings);
      sKeys.put("layout/activity_group_subject_0", com.example.orgsocial.R.layout.activity_group_subject);
      sKeys.put("layout/activity_main_0", com.example.orgsocial.R.layout.activity_main);
      sKeys.put("layout/activity_participants_groups_0", com.example.orgsocial.R.layout.activity_participants_groups);
      sKeys.put("layout/activity_profile_0", com.example.orgsocial.R.layout.activity_profile);
      sKeys.put("layout/activity_settings_0", com.example.orgsocial.R.layout.activity_settings);
      sKeys.put("layout/activity_view_image_0", com.example.orgsocial.R.layout.activity_view_image);
      sKeys.put("layout/fragment_calls_0", com.example.orgsocial.R.layout.fragment_calls);
      sKeys.put("layout/fragment_chats_0", com.example.orgsocial.R.layout.fragment_chats);
      sKeys.put("layout/fragment_events_0", com.example.orgsocial.R.layout.fragment_events);
      sKeys.put("layout/fragment_groups_0", com.example.orgsocial.R.layout.fragment_groups);
    }
  }
}
