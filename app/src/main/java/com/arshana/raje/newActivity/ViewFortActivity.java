package com.arshana.raje.newActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arshana.raje.Constant.API;
import com.arshana.raje.Constant.Constant;
import com.arshana.raje.Fragment.HistoryFragment;
import com.arshana.raje.Fragment.InfoFragment;
import com.arshana.raje.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewFortActivity extends AppCompatActivity {
    FloatingActionButton fab_Share;
    TextView txt_fort_name;
    ImageView img_back, img_fort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fort);

        txt_fort_name = findViewById(R.id.txt_fort_name);
        txt_fort_name.setText(getIntent().getStringExtra("title"));
        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        img_back = findViewById(R.id.img_back);
        img_fort = findViewById(R.id.img_fort);
        Picasso.with(this).load(API.FORT_PATH + FortActivity.statusList.get(Constant.fort_position).getImage()).into(img_fort);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        adapter.addFragment(new InfoFragment(), "माहिती");
        adapter.addFragment(new HistoryFragment(), "इतिहास");
//        adapter.addFragment(new HowToReachFragment(), "कसे पोहचावे");
        viewPager.setAdapter(adapter);
        fab_Share = findViewById(R.id.fab_Share);
        fab_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = getIntent().getStringExtra("title") + "\n\n" + FortActivity.statusList.get(Constant.fort_position).getInfo();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Constant.PLAYSTORE_URL + content);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}