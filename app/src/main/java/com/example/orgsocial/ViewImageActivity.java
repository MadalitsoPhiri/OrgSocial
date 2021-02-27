package com.example.orgsocial;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.orgsocial.databinding.ActivityViewImageBinding;
import com.example.orgsocial.models.Common;

public class ViewImageActivity extends AppCompatActivity {
ActivityViewImageBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = DataBindingUtil.setContentView(this,R.layout.activity_view_image);
     String photoUrl = getIntent().getStringExtra("photoUrl");
     String photoType = getIntent().getStringExtra("PhotoType");
     if(photoUrl.equals("") && photoType.equals("chat")){
         binding.myZoomageView.setImageDrawable(getDrawable(R.drawable.ic_group_13));
     }else if(photoUrl.equals("") && photoType.equals("group")){
         binding.myZoomageView.setImageDrawable(getDrawable(R.drawable.ic_group_14));
     }else{
         binding.myZoomageView.setImageBitmap(Common.IMAGE_BITMAP);
     }

       binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
     //Click listeners for the screen widgets
     binding.viewImageShare.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(getApplicationContext(),"Share clicked",Toast.LENGTH_SHORT).show();
         }
     });
     binding.profileViewImageEdit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(getApplicationContext(),"Edit the Profile Image",Toast.LENGTH_SHORT).show();
         }
     });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }
}
/*if(photoUrl.equals("")){
         binding.myZoomageView.setImageDrawable(getDrawable(R.drawable.ic_group_13));
     }else{
         binding.myZoomageView.setImageBitmap(Common.IMAGE_BITMAP);
     }
*/