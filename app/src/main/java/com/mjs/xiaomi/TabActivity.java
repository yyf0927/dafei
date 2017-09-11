package com.mjs.xiaomi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dafei on 2017/9/6.
 */
public class TabActivity extends Activity {
    private QMUITopBar topbar;
    private QMUITabSegment tabSegment;
    private ViewPager contentViewPager;
    private Map<ContentPage, View> mPageMap = new HashMap<>();
    private ContentPage mDestPage = ContentPage.Item1;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return ContentPage.SIZE;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            ContentPage page = ContentPage.getPage(position);
            View view = getPageView(page);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_tab);
        initView();
        initTopBar();
        initTabAndPager();
    }

    private void initTopBar() {
        topbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        topbar.setTitle("TAB标题");
        topbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBottomSheetList();
                    }
                });
    }

    private void initView(){
        topbar=(QMUITopBar)findViewById(R.id.topbar);
        tabSegment=(QMUITabSegment)findViewById(R.id.tabSegment);
        contentViewPager=(ViewPager)findViewById(R.id.contentViewPager);
    }

    private void showBottomSheetList() {
        new QMUIBottomSheet.BottomListSheetBuilder(this)
                .addItem(getResources().getString(R.string.tabSegment_mode_general))
                .addItem(getResources().getString(R.string.tabSegment_mode_indicator_with_content))
                .addItem(getResources().getString(R.string.tabSegment_mode_left_icon_and_auto_tint))
                .addItem(getResources().getString(R.string.tabSegment_mode_sign_count))
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        switch (position) {
                            case 0:
                                tabSegment.reset();
                                tabSegment.setHasIndicator(false);
                                tabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_1_title)));
                                tabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_2_title)));
                                break;
                            case 1:
                                tabSegment.reset();
                                tabSegment.setHasIndicator(true);
                                tabSegment.setIndicatorPosition(false);
                                tabSegment.setIndicatorWidthAdjustContent(false);
                                tabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_1_title)));
                                tabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_2_title)));
                                break;
                            case 2:
                                tabSegment.reset();
                                tabSegment.setHasIndicator(false);
                                QMUITabSegment.Tab component = new QMUITabSegment.Tab(
                                        ContextCompat.getDrawable(TabActivity.this, R.mipmap.icon_tabbar_component),
                                        null,
                                        "Components", true
                                );
                                QMUITabSegment.Tab util = new QMUITabSegment.Tab(
                                        ContextCompat.getDrawable(TabActivity.this, R.mipmap.icon_tabbar_util),
                                        null,
                                        "Helper", true
                                );
                                tabSegment.addTab(component);
                                tabSegment.addTab(util);
                                break;
                            case 3:
                                QMUITabSegment.Tab tab = tabSegment.getTab(0);
                                tab.setSignCountMargin(0, -QMUIDisplayHelper.dp2px(TabActivity.this, 4));
                                tab.showSignCountView(TabActivity.this, 99);
                                break;
                        }
                        tabSegment.notifyDataChanged();
                    }
                })
                .build()
                .show();

    }

    private void initTabAndPager() {
        contentViewPager.setAdapter(mPagerAdapter);
        contentViewPager.setCurrentItem(mDestPage.getPosition(), false);
        tabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_1_title)));
        tabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_2_title)));
        tabSegment.setupWithViewPager(contentViewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                tabSegment.hideSignCountView(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
                tabSegment.hideSignCountView(index);
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    private View getPageView(ContentPage page) {
        View view = mPageMap.get(page);
        if (view == null) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(ContextCompat.getColor(this, R.color.app_color_description));

            if (page == ContentPage.Item1) {
                textView.setText(R.string.tabSegment_item_1_content);
            } else if (page == ContentPage.Item2) {
                textView.setText(R.string.tabSegment_item_2_content);
            }

            view = textView;
            mPageMap.put(page, view);
        }
        return view;
    }

    public enum ContentPage {
        Item1(0),
        Item2(1);
        public static final int SIZE = 2;
        private final int position;

        ContentPage(int pos) {
            position = pos;
        }

        public static ContentPage getPage(int position) {
            switch (position) {
                case 0:
                    return Item1;
                case 1:
                    return Item2;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }
}