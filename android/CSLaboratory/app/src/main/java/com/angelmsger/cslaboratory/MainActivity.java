package com.angelmsger.cslaboratory;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelmsger.cslaboratory.course.course.CourseCourseItem;
import com.angelmsger.cslaboratory.course.subclass.CourseSubclassItem;
import com.angelmsger.cslaboratory.course.CourseFragment;
import com.angelmsger.cslaboratory.daily.article.DailyArticleItem;
import com.angelmsger.cslaboratory.daily.DailyFragment;
import com.angelmsger.cslaboratory.forum.ForumFragment;
import com.angelmsger.cslaboratory.forum.topic.ForumTopicItem;
import com.angelmsger.cslaboratory.login.LoginActivity;
import com.angelmsger.cslaboratory.login.User;
import com.angelmsger.cslaboratory.quality.ad.QualityADItem;
import com.angelmsger.cslaboratory.quality.article.QualityArticleItem;
import com.angelmsger.cslaboratory.quality.category.QualityCategoryItem;
import com.angelmsger.cslaboratory.quality.QualityFragment;
import com.angelmsger.cslaboratory.shared.ScrollingActivity;
import com.angelmsger.cslaboratory.topology.TopologyFragment;
import com.angelmsger.cslaboratory.topology.field.TopologyFieldItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener,
        DailyFragment.OnListFragmentInteractionListener,
        ForumFragment.OnListFragmentInteractionListener,
        QualityFragment.OnListFragmentInteractionListener,
        CourseFragment.OnListFragmentInteractionListener,
        TopologyFragment.OnListFragmentInteractionListener {
    private FloatingActionButton mFloatingActionButton;
    private ImageView mDrawerImage;
    private CircleImageView mDrawerAvatar;
    private TextView mDrawerNickname;
    private Button mDrawerSigninButton;

    // {@link ViewPager} 将持有页内容。
    private List<Fragment> mFragments;

    private List<NavigationTabBar.Model> mModels;
    private List<ColorStateList> mColorStates;

    private CSLaboratory mApplication;

    public static final int PERMISSIONS_REQUEST_INTERNET = 0;
    public static final int LOGIN_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        requestPermission();
        mApplication = (CSLaboratory) getApplication();
    }

    private void initUI() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "暂未添加的功能", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFragments = new ArrayList<>();
        mFragments.add(DailyFragment.newInstance(1));
        mFragments.add(ForumFragment.newInstance(1));
        mFragments.add(QualityFragment.newInstance(3));
        mFragments.add(CourseFragment.newInstance(2));
        mFragments.add(TopologyFragment.newInstance(2));

        // 创建适配器来为 activity 中的每一页返回一个 fragment。
        SectionsPagerAdapter mSectionsPagerAdapter
                = new SectionsPagerAdapter(getSupportFragmentManager());

        // 设置 ViewPager 的适配器。
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        NavigationTabBar mNavigationTabBar = (NavigationTabBar) findViewById(R.id.tabs);
        mModels = new ArrayList<>();
        mModels.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.mipmap.ic_visibility_black_48dp),
                        ContextCompat.getColor(this, R.color.colorDaily))
                        .title(getString(R.string.title_daily))
                        .build()
        );
        mModels.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.mipmap.ic_supervisor_account_black_48dp),
                        ContextCompat.getColor(this, R.color.colorForum))
                        .title(getString(R.string.title_forum))
                        .build()
        );
        mModels.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.mipmap.ic_pets_black_48dp),
                        ContextCompat.getColor(this, R.color.colorQuality))
                        .title(getString(R.string.title_quality))
                        .build()
        );
        mModels.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.mipmap.ic_settings_ethernet_black_48dp),
                        ContextCompat.getColor(this, R.color.colorCourse))
                        .title(getString(R.string.title_course))
                        .build()
        );

        mModels.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.mipmap.ic_extension_black_48dp),
                        ContextCompat.getColor(this, R.color.colorTopology))
                        .title(getString(R.string.title_topology))
                        .build()
        );

        mNavigationTabBar.setModels(mModels);
        mNavigationTabBar.setViewPager(mViewPager, 2);
        mNavigationTabBar.setOnPageChangeListener(this);

        mColorStates = new ArrayList<>();
        mColorStates.add(ContextCompat.getColorStateList(this, R.color.colorDaily));
        mColorStates.add(ContextCompat.getColorStateList(this, R.color.colorForum));
        mColorStates.add(ContextCompat.getColorStateList(this, R.color.colorQuality));
        mColorStates.add(ContextCompat.getColorStateList(this, R.color.colorCourse));
        mColorStates.add(ContextCompat.getColorStateList(this, R.color.colorTopology));

        // 为导航菜单头部用户头像增加点击事件监听器
        View view = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        mDrawerImage = (ImageView) view.findViewById(R.id.image);
        mDrawerAvatar = (CircleImageView) view.findViewById(R.id.avatar);
        mDrawerNickname = (TextView) view.findViewById(R.id.nickname);
        mDrawerSigninButton = (Button) view.findViewById(R.id.signin);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(MainActivity.this, LoginActivity.class), LOGIN_REQUEST);
            }
        });
        mDrawerSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerSigninButton.setText(getString(R.string.nav_header_signin_button_signed));
            }
        });
    }

    private void refreshUserinfo() {
        User user = (User) mApplication.mGlobal.get("User");
        if (user != null) {
            User.Base base = user.getBase();
            Glide.with(this).load(base.getImageURI()).into(mDrawerImage);
            Glide.with(this).load(base.getAvatarURI()).into(mDrawerAvatar);
            mDrawerNickname.setText(base.getNickname());
            mDrawerNickname.setText(base.getNickname());
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{ Manifest.permission.INTERNET },
                PERMISSIONS_REQUEST_INTERNET);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单，这将在 action bar 添加选项如果它被显示。
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 在此处理 action bar 选项点击事件。action bar 将会自动处理 Home/Up 键的点击事件
        // 如果需要自定义可以修改 AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 在此处理导航菜单选项点击事件。
        int id = item.getItemId();

        if (id == R.id.nav_message) {
            // 临时用来测试注册功能

        } else if (id == R.id.nav_route) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_draft) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST && resultCode == LoginActivity.LOGIN_SUCCESSFULLY) {
            // 登录成功，用户信息可读
            refreshUserinfo();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mModels.get(position).hideBadge();
        mFloatingActionButton.setBackgroundTintList(mColorStates.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCourseListScroll() {
        mModels.get(3).hideBadge();
    }

    @Override
    public void onCourseListUpdate(final int updateCount) {
        NavigationTabBar.Model model = mModels.get(3);
        model.setBadgeTitle(Integer.toString(updateCount));
        model.showBadge();
    }

    @Override
    public void onCourseCategoryItemClick(CourseSubclassItem courseSubclassItem) {

    }

    @Override
    public void onCourseItemClick(CourseCourseItem courseCourseItem) {

    }

    @Override
    public void onDailyListScroll() {
        mModels.get(0).hideBadge();
    }

    @Override
    public void onDailyListUpdate(int updateCount) {
        NavigationTabBar.Model model = mModels.get(0);
        model.setBadgeTitle(Integer.toString(updateCount));
        model.showBadge();
    }

    @Override
    public void onDailyItemClick(DailyArticleItem dailyArticleItem) {
        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
        intent.putExtra(ScrollingActivity.IMAGE_URI, dailyArticleItem.getImageURI());
        intent.putExtra(ScrollingActivity.ARTICLE_TITLE, dailyArticleItem.getTitle());
        intent.putExtra(ScrollingActivity.ARTICLE_CONTENT, dailyArticleItem.getContent());
        startActivity(intent);
    }

    @Override
    public void onForumListScroll() {
        mModels.get(1).hideBadge();
    }

    @Override
    public void onForumListUpdate(int updateCount) {
        NavigationTabBar.Model model = mModels.get(1);
        model.setBadgeTitle(Integer.toString(updateCount));
        model.showBadge();
    }

    @Override
    public void onForumItemClick(ForumTopicItem forumTopicItem) {

    }

    @Override
    public void onQualityListScroll() {
        mModels.get(2).hideBadge();
    }

    @Override
    public void onQualityListUpdate(int updateCount) {
        NavigationTabBar.Model model = mModels.get(2);
        model.setBadgeTitle(Integer.toString(updateCount));
        model.showBadge();
    }

    @Override
    public void onADItemClick(QualityADItem qualityAdItem) {

    }

    @Override
    public void onCategoryItemClick(QualityCategoryItem qualityCategoryItem) {

    }

    @Override
    public void onQualityItemClick(QualityArticleItem qualityArticleItem) {

    }

    @Override
    public void onTopologyListScroll() {
        mModels.get(4).hideBadge();
    }

    @Override
    public void onTopologyListUpdate(int updateCount) {
        NavigationTabBar.Model model = mModels.get(4);
        model.setBadgeTitle(Integer.toString(updateCount));
        model.showBadge();
    }

    @Override
    public void onTopologyItemClick(TopologyFieldItem topologyFieldItem) {

    }

    /**
     * {@link android.support.v4.view.PagerAdapter} 将为每一页提供 fragments。
     * 使用一个{@link FragmentPagerAdapter} 子类，将在内存中保持每一个载入的 fragment。
     * 如果这使得内存占用过高，则它可以被替换为
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 返回 position 位置对应的 Fragment
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            // 返回 Tab 页数量
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).toString();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 此处重写此方法阻止销毁 Fragment 以防止界面卡顿
            // super.destroyItem(container, position, object);
        }
    }
}
