package com.desired.offermachi.customer.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.constant.hand;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.fragment.CustomerSupportFragment;
import com.desired.offermachi.customer.view.fragment.DealsoftheDayFragment;
import com.desired.offermachi.customer.view.fragment.ExclusiveFragment;
import com.desired.offermachi.customer.view.fragment.FavouritesFragment;
import com.desired.offermachi.customer.view.fragment.FeedsFragment;
import com.desired.offermachi.customer.view.fragment.FollowFragment;
import com.desired.offermachi.customer.view.fragment.HelpFragment;
import com.desired.offermachi.customer.view.fragment.HomeFragment;
import com.desired.offermachi.customer.view.fragment.InviteFriendFragment;
import com.desired.offermachi.customer.view.fragment.MycouponsFragment;
import com.desired.offermachi.customer.view.fragment.ProfileFragment;
import com.desired.offermachi.customer.view.fragment.SmartShoppingFragment;
import com.desired.offermachi.customer.view.fragment.StoreFragment;
import com.desired.offermachi.customer.view.fragment.TermConditionFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager FM;
    FragmentTransaction FT;
    LinearLayout smartshopping,dealsoftheday,coupons,favourites,ifollow;
    ImageView smartshoppingimg,dealsofthedayimg,couponsimg,favouritesimg,ifollowimg;
    TextView smartshoppingtext,dealsofthedaytext,couponstext,favouritestext,ifollowtext;
    Toolbar toolbar;
    DrawerLayout drawer;
    ImageView ivTitleLogo;
    TextView tvMainTitle;
    ImageView info;
    private View navHeader;
    NavigationView navigationView;
    TextView name,email;
    ImageView imageView;
    String ImageHolder,username,useremail;
   User user;
   ImageView btnnotification;
   int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        toolbar = findViewById(R.id.toolbar);
        ivTitleLogo = toolbar.findViewById(R.id.logo);
        tvMainTitle = toolbar.findViewById(R.id.tv_title);

        setToolTittle("", 1);
        user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
       String pos= UserSharedPrefManager.GetClickNoti(this);
        String diffNavi= UserSharedPrefManager.GetClickNotiPos(this);
        if (pos.equals("1")) {


            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();

            switch (diffNavi)
            {
                case "0":
                    FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
                break;

                case "1":
//                    GoDealOfTheDay(1);
                    FT.replace(R.id.framelayout_id, new DealsoftheDayFragment()).commit();
                break;
                case "2":
                    FT.replace(R.id.framelayout_id, new ExclusiveFragment()).commit();
                break;
                default:
                    FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
                    break;
            }
//            FT.replace(R.id.framelayout_id, new DealsoftheDayFragment()).commit();


            UserSharedPrefManager.SaveClickNoti(this,"0","");
        }
else{
        if (user.getSmartShopping().equals("1")) {
            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.framelayout_id, new SmartShoppingFragment()).commit();
        } else if (user.getSmartShopping().equals("0")) {
            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
        }
    }

        if(user != null){
            getNotiCount();
        }

        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);
        navigationView.getMenu().getItem(5).setVisible(false);

        name = (TextView) navHeader.findViewById(R.id.navname);
        email = (TextView) navHeader.findViewById(R.id.navemail);
        imageView = (ImageView) navHeader.findViewById(R.id.imageView);

        username = user.getUsername();
        useremail = user.getEmail();
        ImageHolder = user.getProfile();
        Log.e("main", "ImageHolder=="+ImageHolder );
        if (username.equals("null")){

        }else{
            name.setText(username);
        }
        if (useremail.equals("null")){

        }else{
            email.setText(useremail);
        }
        if (ImageHolder!= null)
        {
            try {
                Picasso.get().load(ImageHolder).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_avatar).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        smartshopping=(LinearLayout)findViewById(R.id.homelinear);
        dealsoftheday=(LinearLayout)findViewById(R.id.category_linear_id);
        coupons=(LinearLayout)findViewById(R.id.account_linear_id);
        favourites=(LinearLayout)findViewById(R.id.cart_linear_id);
        ifollow=(LinearLayout)findViewById(R.id.more_linear_id);

        smartshoppingimg=(ImageView)findViewById(R.id.home_img);
        dealsofthedayimg=(ImageView)findViewById(R.id.categoryimg_id);
        couponsimg=(ImageView)findViewById(R.id.account_img);
        favouritesimg=(ImageView)findViewById(R.id.cart_img);
        ifollowimg=(ImageView)findViewById(R.id.more_img);

        smartshoppingtext=(TextView) findViewById(R.id.hometext_id);
        dealsofthedaytext=(TextView)findViewById(R.id.categorytext_id);
        couponstext=(TextView)findViewById(R.id.accountext_id);
        favouritestext=(TextView)findViewById(R.id.carttext_id);
        ifollowtext=(TextView)findViewById(R.id.moretext_id);

        info=(ImageView)findViewById(R.id.info_id);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        btnnotification=findViewById(R.id.btnnotification);
        btnnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.floting_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DashBoardActivity.this, SearchActivity.class);
                startActivity(intent);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        smartshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user.getSmartShopping().equals("0")){
                    showdialog();
                }
//
//
////                Toast.makeText(DashBoardActivity.this, "smart shoping", Toast.LENGTH_SHORT).show();
//                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.whiteonlineshop));
//                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
//                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
//                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
//                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));
//
//
//                smartshoppingtext.setTextColor(getResources().getColor(R.color.white));
//                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
//                couponstext.setTextColor(getResources().getColor(R.color.yellow));
//                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
//                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));
//
//                if (user.getSmartShopping().equals("1")){
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.framelayout_id, new SmartShoppingFragment())
//                            .addToBackStack(null)
//                            .commit();
//                }
//                else
//                {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.framelayout_id, new HomeFragment())
//                            .addToBackStack(null)
//                            .commit();
//                }
                }
        });

        dealsoftheday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DashBoardActivity.this, "Deal of the day", Toast.LENGTH_SHORT).show();
                GoDealOfTheDay(1);

          /*      if (user.getSmartShopping().equals("0")){

                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.whitedeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));
                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.white));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));

    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.framelayout_id, new DealsoftheDayFragment())
            .addToBackStack(null)
            .commit();
            }*/
                 }
        });


        coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DashBoardActivity.this, "Coupon", Toast.LENGTH_SHORT).show();
                if (user.getSmartShopping().equals("0")){
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.whitecoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));
                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.white));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new MycouponsFragment())
                            .addToBackStack(null)
                            .commit();
                }

            }
        });


        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DashBoardActivity.this, "Favourite", Toast.LENGTH_SHORT).show();
                if (user.getSmartShopping().equals("0")){
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.whitefavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));

                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.white));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new FavouritesFragment())
                            .addToBackStack(null)
                            .commit();
                }
                }
        });
        ifollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DashBoardActivity.this, "ifollow", Toast.LENGTH_SHORT).show();
                if (user.getSmartShopping().equals("0")){
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.whitefollow));
                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.white));

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new FollowFragment())
                            .addToBackStack(null)
                            .commit();
                }
                }
        });


        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawers();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void GoDealOfTheDay(int i) {
        if(user==null)
        { user = UserSharedPrefManager.getInstance(DashBoardActivity.this).getCustomer();
        }

        if (user.getSmartShopping().equals("0")){

            smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
            dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.whitedeals));
            couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
            favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
            ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));
            smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
            dealsofthedaytext.setTextColor(getResources().getColor(R.color.white));
            couponstext.setTextColor(getResources().getColor(R.color.yellow));
            favouritestext.setTextColor(getResources().getColor(R.color.yellow));
            ifollowtext.setTextColor(getResources().getColor(R.color.yellow));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout_id, new DealsoftheDayFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void getNotiCount() {














    }

    private int dpToPx(Context applicationContext, int i) {
        return 0;
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();

           // FragmentManager fm = getSupportFragmentManager();
            if (FM.getBackStackEntryCount() > 0) {
                FM.popBackStack();
                if(FM.getBackStackEntryCount() ==1)
                {
                    setToolTittle("",1);
                }
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                     UserSharedPrefManager.SaveStoreFilter(DashBoardActivity.this,"");//only for not show privious selected category
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                                startActivity(intent);
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;
       // Class fragmentClass;
        switch(menuItem.getItemId()) {

            case R.id.nav_home:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new HomeFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }
                //toolbar.setTitle("");
                break;
                case R.id.nav_account:
                    if (count==0){
                        navigationView.getMenu().getItem(2).setVisible(true);
                        navigationView.getMenu().getItem(3).setVisible(true);
                        navigationView.getMenu().getItem(4).setVisible(true);
                        navigationView.getMenu().getItem(5).setVisible(true);
                        count=1;
                    }else{
                        navigationView.getMenu().getItem(2).setVisible(false);
                        navigationView.getMenu().getItem(3).setVisible(false);
                        navigationView.getMenu().getItem(4).setVisible(false);
                        navigationView.getMenu().getItem(5).setVisible(false);

                        count=0;
                    }
//                    if (user.getSmartShopping().equals("0")){
//                        UserSharedPrefManager.SaveStoreFilter(this,"");
//                        fragment = new ProfileFragment();
//                    }
//                    else{
//                        fragment = new SmartShoppingFragment();
//                    }


                   // toolbar.setTitle("My Profile");
                break;

            case R.id.nav_profile:

                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                      fragment = new ProfileFragment();
                     drawer.closeDrawer(GravityCompat.START);
                  }
                    break;

            case R.id.nav_feeds:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new FeedsFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_smartshopping:

                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    showdialog();
                    drawer.closeDrawer(GravityCompat.START);
                }
              /*
                if (user.getSmartShopping().equals("0")){
                    fragment = new HomeFragment();
                }
                else{
                    fragment = new SmartShoppingFragment();
                }*/

                break;
            case  R.id.nav_store:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new StoreFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_dealsday:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new DealsoftheDayFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case  R.id.nav_exclusive:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new ExclusiveFragment();
                    drawer.closeDrawer(GravityCompat.START);

                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_mycoupons:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new MycouponsFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_follow:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new FollowFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_fav:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new FavouritesFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_rate:
                if (user.getSmartShopping().equals("0")){
                    fragment = new HomeFragment();
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_invite:
                if (user.getSmartShopping().equals("0")){
                    fragment = new InviteFriendFragment();  drawer.closeDrawer(GravityCompat.START);

                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;

            case R.id.nav_help:
                if (user.getSmartShopping().equals("0")){
                    fragment = new HelpFragment();
                    drawer.closeDrawer(GravityCompat.START);

                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;

            case R.id.nav_customer:
                if (user.getSmartShopping().equals("0")){
                    fragment = new CustomerSupportFragment();
                    drawer.closeDrawer(GravityCompat.START);

                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;

            case R.id.nav_notification:
//                fragment = new HomeFragment();
                if (user.getSmartShopping().equals("0")) {
                    startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_terms:
                if (user.getSmartShopping().equals("0")) {
                    fragment = new TermConditionFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_logout:
                if (user.getSmartShopping().equals("0")) {
                    fragment = new HomeFragment();
                    UserSharedPrefManager.getInstance(getApplicationContext()).logout();
                }else{
                    fragment = new SmartShoppingFragment();
                    UserSharedPrefManager.getInstance(getApplicationContext()).logout();
                }
//             finish();
                break;
                default:
                    fragment = new HomeFragment();

        }

       /* try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       if(fragment==null)//only for not refresh
       {
           DrawerLayout drawer = findViewById(R.id.drawer_layout);
//           drawer.closeDrawer(GravityCompat.START);
       }
       else {
           // Insert the fragment by replacing any existing fragment
           FragmentManager fragmentManager = getSupportFragmentManager();
           fragmentManager.beginTransaction().replace(R.id.framelayout_id, fragment).commit();
           // Highlight the selected item has been done by NavigationView
           menuItem.setChecked(true);
           // Set action bar title
           setTitle(menuItem.getTitle());
           // Close the navigation drawer
           DrawerLayout drawer = findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);
       }
        return true;
    }
    public  void setToolTittle(String tittle,int diff)
    {
        switch (diff)
        {
            case 1:
                ivTitleLogo.setVisibility(View.VISIBLE);
                tvMainTitle.setVisibility(View.GONE);

                break;
            case 2:
                ivTitleLogo.setVisibility(View.GONE);
                tvMainTitle.setVisibility(View.VISIBLE);
                break;
            case 3:
//                toolbar.
                ivTitleLogo.setVisibility(View.GONE);
                tvMainTitle.setVisibility(View.VISIBLE);
                break;

        }
        tvMainTitle.setText(tittle);
    }

    private void showdialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.switch_layout);
        dialog.setTitle("Smart shopping Dialog");
        Switch Switch =(Switch) dialog.findViewById(R.id.smartswitch);
        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {




                // Toast.makeText(DashBoardActivity.this, "smart shoping", Toast.LENGTH_SHORT).show();
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.whiteonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));

                smartshoppingtext.setTextColor(getResources().getColor(R.color.white));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));


                if (user.getSmartShopping().equals("1")) {

                    info.setVisibility(View.INVISIBLE);
                    btnnotification.setVisibility(View.INVISIBLE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new SmartShoppingFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    info.setVisibility(View.VISIBLE);
                    btnnotification.setVisibility(View.VISIBLE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }

                String Nameholder,EmailHolder,PhoneHolder,AddressHolder,GenderHolder,ImageHolder,Dobholder,SmartShoppingHolder,SoundHolder,idholder;
                User user = UserSharedPrefManager.getInstance(DashBoardActivity.this).getCustomer();
                idholder= user.getId();

                Nameholder= user.getUsername();
                EmailHolder=user.getEmail();
                PhoneHolder= user.getMobile();
                AddressHolder= user.getAddress();
                GenderHolder= user.getGender();
                Dobholder=user.getDob();
                ImageHolder=user.getProfile();
                SmartShoppingHolder=user.getSmartShopping();
                SoundHolder=user.getNotificationsound();


                if (isChecked){
                    User user1 = new User(
                            idholder,
                            Nameholder,
                            EmailHolder,
                            PhoneHolder,
                            AddressHolder,
                            GenderHolder,
                            Dobholder,
                            ImageHolder,
                            "1",
                            SoundHolder,
                            "1"//shyam 11/9/19
                    );
                    UserSharedPrefManager.getInstance(DashBoardActivity.this).userLogin(user1);

                    startActivity(new Intent(DashBoardActivity.this,DashBoardActivity.class));
                    finish();

                }
                dialog.dismiss();

            }

        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user.getSmartShopping().equals("1")) {
            info.setVisibility(View.INVISIBLE);
            btnnotification.setVisibility(View.INVISIBLE);

            smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.whiteonlineshop));
            dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
            couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
            favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
            ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));

            smartshoppingtext.setTextColor(getResources().getColor(R.color.white));
            dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
            couponstext.setTextColor(getResources().getColor(R.color.yellow));
            favouritestext.setTextColor(getResources().getColor(R.color.yellow));
            ifollowtext.setTextColor(getResources().getColor(R.color.yellow));
        }
        else
        {
            info.setVisibility(View.GONE);
            btnnotification.setVisibility(View.VISIBLE);
        }
    }
}
