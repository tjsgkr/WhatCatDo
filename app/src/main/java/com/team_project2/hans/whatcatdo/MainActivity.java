package com.team_project2.hans.whatcatdo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";

    /*layout Component*/
    ImageView img_menu_home;
    ImageView img_menu_camera;
    ImageView img_menu_log;
    private CameraView cameraView;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        img_menu_camera = findViewById(R.id.img_menu_camera);
        img_menu_home = findViewById(R.id.img_menu_home);
        img_menu_log = findViewById(R.id.img_menu_log);

        Loading();
        checkPermission();

        viewPager = findViewById(R.id.pager_main);


        img_menu_home.setOnClickListener(movePageListener);
        img_menu_home.setTag(0);

        img_menu_camera.setOnClickListener(movePageListener);
        img_menu_camera.setTag(1);

        img_menu_log.setOnClickListener(movePageListener);
        img_menu_log.setTag(2);

        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            viewPager.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new PictureFragment();
                case 2:
                    return new LogMenuFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }


    private void Loading(){
        startActivity(new Intent(this, LoadingActivity.class));
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 마시멜로우 버전과 같거나 이상이라면
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);  //마지막 인자는 체크해야될 권한 갯수
            }
        }
    }
}
