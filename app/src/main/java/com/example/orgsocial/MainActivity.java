package com.example.orgsocial;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.orgsocial.R;
import com.example.orgsocial.adapters.SectionPagerAdapter;
import com.example.orgsocial.authentication.LoginActivity;
import com.example.orgsocial.databinding.ActivityMainBinding;
import com.example.orgsocial.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

   private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setWithViewPager(binding.viewpager);
         String groups = getIntent().getStringExtra("groups");
        if(groups != null){
            binding.viewpager.setCurrentItem(1);
        }
        binding.homeTablayout.setupWithViewPager(binding.viewpager);
        setSupportActionBar(binding.toolbar);
        binding.chatsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ContactsActivity.class));
            }
        });
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageSelected(int position) {
                if(position == 0 ){
                    binding.chatsFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(MainActivity.this,ContactsActivity.class));
                        }
                    });
                }else if(position == 1 ){
                    binding.chatsFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(MainActivity.this,ActivityGroupList.class));
                        }
                    });
                }else{
                    binding.chatsFab.setOnClickListener(null);
                    binding.chatsFab.setVisibility(View.GONE);
                }
                 changeFabIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24);
        binding.toolbar.setOverflowIcon(drawable);

    }
    public void setWithViewPager(ViewPager viewPager){
     SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
     adapter.addFragment(new ChatsFragment(),"Chats");
     adapter.addFragment(new Groups(),"Groups");
     adapter.addFragment(new CallsFragment(),"Calls");
     adapter.addFragment(new Events(),"Events");
     viewPager.setAdapter(adapter);
     viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Handle your menu item clicks here
        if(id == R.id.menu_search){
            Toast.makeText(getApplication(),"Search icon clicked",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.new_event){
            Toast.makeText(getApplication(),"New Event Selected",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.new_group){
            Toast.makeText(getApplication(),"New Group Selected",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.settings){
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeFabIcon(final int position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch(position){
                    case 0:binding.chatsFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_24));
                    break;
                    case 1:binding.chatsFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_24)); break;
                    case 2:binding.chatsFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_add_ic_call_24));break;
                    case 3:binding.chatsFab.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_24)); break;
                }
            }
        },100);

    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof Groups){
            ((Groups) fragment).FAB = binding.chatsFab;
        }else if(fragment instanceof ChatsFragment){
            ((ChatsFragment) fragment).FAB = binding.chatsFab;
        }


    }

    public static class Welcome extends Fragment {

        SharedPreferences prefs = null;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            prefs = getActivity().getSharedPreferences("com.example.orgsocial",MODE_PRIVATE);
            View view = inflater.inflate(R.layout.fragment_welcome, container, false);
            Button accept = view.findViewById(R.id.accept_privacy_policy_btn);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prefs.edit().putBoolean("firstrun", false).commit();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            });
            return view;
        }

    }



}
