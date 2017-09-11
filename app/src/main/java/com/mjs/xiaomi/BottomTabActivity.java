package com.mjs.xiaomi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mjs.xiaomi.R;
import com.mjs.xiaomi.fragment.BookSearchFragment;
import com.mjs.xiaomi.fragment.LibraryStatisticsFragment;
import com.mjs.xiaomi.fragment.MyBookFragment;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

/**
 * Created by dafei on 2017/9/7.
 */
public class BottomTabActivity extends AppCompatActivity {
    ViewPager mViewPager;
    QMUITabSegment mTabSegment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bottom_tab);
        initView();
        initTabs();
        initPagers();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabSegment = (QMUITabSegment) findViewById(R.id.tabs);
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        QMUITabSegment.Tab component = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_component),
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_component_selected),
                getString(R.string.tab_my_library), false
        );
        QMUITabSegment.Tab util = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_util),
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_util_selected),
                getString(R.string.tab_book_search), false
        );
        QMUITabSegment.Tab lab = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_lab),
                ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_lab_selected),
                getString(R.string.tab_library_statistics), false
        );
        mTabSegment.addTab(component)
                .addTab(util)
                .addTab(lab);
    }

    private void initPagers() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new MyBookFragment();
                } else if (position == 1) {
                    return new BookSearchFragment();
                } else if (position == 2) {
                    return new LibraryStatisticsFragment();
                }

                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "mybook";
                } else if (position == 1) {
                    return "mybook";
                } else if (position == 2) {
                    return "mybook";
                }

                return super.getPageTitle(position);
            }
        });
        mTabSegment.setupWithViewPager(mViewPager, false);
    }
}
