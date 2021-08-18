package com.ats.blooddonor.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.blooddonor.Model.LogoutModel;
import com.ats.blooddonor.Model.SharedPreManager;
import com.ats.blooddonor.Model.UserResponse;
import com.ats.blooddonor.R;
import com.ats.blooddonor.activities.SplashScreen;
import com.ats.blooddonor.auth.AuthActivity;
import com.ats.blooddonor.auth.fragments.LoginFragment;
import com.ats.blooddonor.auth.fragments.Profilefragment;
import com.ats.blooddonor.auth.fragments.SelectLoginSignup;
import com.ats.blooddonor.auth.fragments.SignupFragment;
import com.ats.blooddonor.network.APIService;
import com.ats.blooddonor.network.RetroInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.menu)
    ImageView nav_menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottomHome)
    LinearLayout bottomhome;
    @BindView(R.id.bottomSearch)
    LinearLayout bottomSearch;
    @BindView(R.id.bottomHistory)
    LinearLayout bottomHistory;
    @BindView(R.id.bottomProfile)
    LinearLayout bottomProfile;
    @BindView(R.id.bottomBar_home)
    ImageView bottomBar_home;
    @BindView(R.id.iv_bottomHistory)
    ImageView iv_bottomHistory;
    @BindView(R.id.iv_bottomProfile)
    ImageView iv_bottomProfile;
    @BindView(R.id.iv_bottomSearch)
    ImageView iv_bottomSearch;
    @BindView(R.id.img_bell)
    ImageView img_bell;
    @BindView(R.id.bottomBar)
    LinearLayout bottomBar;
    @BindView(R.id.bottomIcon)
    LinearLayout bottomIcon;

    TextView userPhone;
    TextView profile_name;
    RelativeLayout rel_layoutTop;
    CircleImageView pro_image;
    APIService apiService;
    String token;
    SharedPreManager sharedPreManager;
    LinearLayout[] levelsArray;
    private long pressedTime;
    static String mainTag="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.textView);
        apiService = RetroInstance.getRetroClient().create(APIService.class);
        navigationView.setNavigationItemSelectedListener(this);

     LinearLayout[] levelsArray = {bottomhome, bottomSearch, bottomHistory, bottomProfile};

        for(int i = 0; i < 4; i++) {
            levelsArray[i].setOnClickListener(this);
        }

        loadFragmentFirst(new HomeFragment(), "homeFragment");
        bottomBar_home.setColorFilter(getResources().getColor(R.color.red));
        iv_bottomProfile.setColorFilter(getResources().getColor(R.color.gray));
        iv_bottomSearch.setColorFilter(getResources().getColor(R.color.gray));
        iv_bottomHistory.setColorFilter(getResources().getColor(R.color.gray));
        token = new SharedPreManager(getApplicationContext()).getAcces_token();
        pro_image = (CircleImageView) header.findViewById(R.id.profile_image);
        userPhone = (TextView) header.findViewById(R.id.userphone);
        profile_name = (TextView) header.findViewById(R.id.UserName);
        rel_layoutTop = findViewById(R.id.rel_layoutTop);
        getUserInfo();
    }


//    @Override
//    public void onBackPressed() {

//        if (mainTag.equals("welcomeFragment")) {
//            loadFragmentFirst(new SignupFragment(), "signupFragment");
//        } else if (mainTag.equals("registerFragment")){
//            loadFragmentFirst(new SignupFragment(), "signupFragment");
//        }

//        loadFragmentFirst(new HomeFragment(), "Home Fragment");
//        if (pressedTime + 1500 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finishAffinity();
//            finish();
//        } else{
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        pressedTime = System.currentTimeMillis();
//    }

    public void hideToolbar() {
        rel_layoutTop.setVisibility(View.GONE);
    }
    public void showToolbar() {
        rel_layoutTop.setVisibility(View.VISIBLE);
    }


    public boolean loadFragmentFirst(Fragment fragment, String tag) {
        mainTag = tag;
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_container, fragment, tag)
                    .commit();
            return true;
        }
        return false;
    }
    public boolean loadFragment(Fragment fragment, String tag) {
        mainTag = tag;
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_container, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
            return true;
        }
        return false;
    }


    @OnClick(R.id.menu)
    void onNavMenuClick() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_donors:
                rel_layoutTop.setVisibility(View.GONE);
                loadFragment(new FindDonor(), "Find Donor");
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

            case R.id.nav_signout:
                drawerLayout.closeDrawer(Gravity.LEFT);
                new SharedPreManager(HomeActivity.this).logout();
                getSigout();
                break;

            case R.id.nav_blood:
                drawerLayout.closeDrawer(Gravity.LEFT);
                rel_layoutTop.setVisibility(View.GONE);
                loadFragment(new RequestBloodFragment(), "RequestBlood");

                break;

            case R.id.nav_contactus:
                rel_layoutTop.setVisibility(View.GONE);
                loadFragment(new ContactUsFragment(), "Contact us fragment");
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;

        }
        return loadFragment(fragment);
    }

    private void getSigout() {
        apiService.logout("Bearer " + token).enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {

//                Toast.makeText(HomeActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_LONG).show();
                Intent i = new Intent(HomeActivity.this, AuthActivity.class);
                //Intent is used to switch from one activity to another.
                startActivity(i);
                //invoke the SecondActivity.
                finish();
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {

            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    void getUserInfo() {


        apiService.user("Bearer " + token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        String getGender = response.body().getGender();
                        String name = response.body().getFullName();
                        String phone = response.body().getPhone();
                        profile_name.setText(name);
                        userPhone.setText(phone);
                        String default_gender = "female";
                        if (default_gender.equals(getGender)) {
                            Picasso.get().load(R.drawable.female).into(pro_image);
                        } else {
                            Picasso.get().load(R.drawable.male).into(pro_image);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottomHome:
                loadFragmentFirst(new HomeFragment(), "HomeFragment");
                showToolbar();
                bottomBar_home.setColorFilter(getResources().getColor(R.color.red));
                iv_bottomProfile.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomSearch.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomHistory.setColorFilter(getResources().getColor(R.color.gray));

                break;

            case R.id.bottomProfile:
                loadFragment(new Profilefragment(), "Profilefragment");
                hideToolbar();
                bottomBar_home.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomProfile.setColorFilter(getResources().getColor(R.color.red));
                iv_bottomSearch.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomHistory.setColorFilter(getResources().getColor(R.color.gray));

                break;
            case R.id.bottomSearch:
                loadFragment(new FindDonor(), "FindDonor");
                hideToolbar();
                bottomBar_home.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomProfile.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomSearch.setColorFilter(getResources().getColor(R.color.red));
                iv_bottomHistory.setColorFilter(getResources().getColor(R.color.gray));
                break;
            case R.id.bottomHistory:
                hideToolbar();
                loadFragment(new HistoryFragment(), "HistoryFragment");
                bottomBar_home.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomProfile.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomSearch.setColorFilter(getResources().getColor(R.color.gray));
                iv_bottomHistory.setColorFilter(getResources().getColor(R.color.red));
                break;


        }
    }

    public void hideShowBottomBar(boolean value)
    {
        if (value)
        {
            bottomBar.setVisibility(View.VISIBLE);
            bottomIcon.setVisibility(View.VISIBLE);
        }else {
            bottomBar.setVisibility(View.GONE);
            bottomIcon.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.img_bell)
    void getRequest(){
        loadFragment(new DonationRequestFragment(), "donationRequestFragment");
        hideToolbar();
        hideShowBottomBar(false);
    }
}
