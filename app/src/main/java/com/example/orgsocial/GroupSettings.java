package com.example.orgsocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.databinding.ActivityGroupSettingsBinding;
import com.example.orgsocial.models.GroupUser;
import com.example.orgsocial.models.User;

import java.io.Serializable;
import java.util.List;

public class GroupSettings extends AppCompatActivity{
    ActivityGroupSettingsBinding binding;
    List<GroupUser> list;
    String groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(this,R.layout.activity_group_settings);
      Intent intent = getIntent();
      setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       list =  (List<GroupUser>) intent.getSerializableExtra("final");
       list.remove(0);
       groupId = intent.getStringExtra("groupId");

      binding.editGroupAdmins.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(GroupSettings.this,EditGroupAdmins.class).putExtra("participants",(Serializable) list).putExtra("groupId",groupId));
              finish();

          }
      });
    }
}