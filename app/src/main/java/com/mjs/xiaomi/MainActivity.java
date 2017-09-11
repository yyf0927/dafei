package com.mjs.xiaomi;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button loading,success,fail,img,text,custom,invite,list,tab,tab_scroll,tab_bottom;
    private QMUIListPopup mListPopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

    }

    private void initView() {
        loading= (Button) findViewById(R.id.loading);
        success= (Button) findViewById(R.id.success);
        fail= (Button) findViewById(R.id.fail);
        img= (Button) findViewById(R.id.img);
        text= (Button) findViewById(R.id.text);
        custom= (Button) findViewById(R.id.custom);
        invite= (Button) findViewById(R.id.invite);
        list= (Button) findViewById(R.id.list);
        tab= (Button) findViewById(R.id.tab);
        tab_scroll= (Button) findViewById(R.id.tab_scroll);
        tab_bottom= (Button) findViewById(R.id.tab_bottom);
    }
    private void initListener(){
        loading.setOnClickListener(this);
        success.setOnClickListener(this);
        fail.setOnClickListener(this);
        img.setOnClickListener(this);
        text.setOnClickListener(this);
        custom.setOnClickListener(this);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetGrid();
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListPopupIfNeed();
                mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
                mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_BOTTOM);
                mListPopup.show(v);
                list.setText("隐藏列表浮层");

            }
        });
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabActivity.class));
            }
        });
        tab_scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabScrollActivity.class));
            }
        });
        tab_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BottomTabActivity.class));
            }
        });

    }

    private void initListPopupIfNeed() {
        if (mListPopup == null) {

            String[] listItems = new String[]{
                    "Item 1",
                    "Item 2",
                    "Item 3",
                    "Item 4",
                    "Item 5",
            };
            List<String> data = new ArrayList<>();

            for (String listItem : listItems) {
                data.add(listItem);
            }

            ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.simple_list_item, data);

            mListPopup = new QMUIListPopup(MainActivity.this, QMUIPopup.DIRECTION_NONE, adapter);
            mListPopup.create(QMUIDisplayHelper.dp2px(MainActivity.this, 140), QMUIDisplayHelper.dp2px(MainActivity.this, 200), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, "Item " + (i + 1), Toast.LENGTH_SHORT).show();
                    mListPopup.dismiss();
                }
            });
            mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    list.setText("显示列表浮层");
                }
            });
        }
    }


    QMUITipDialog tipDialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在加载")
                        .create();
                break;
            case R.id.success:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord("发送成功")
                        .create();
                break;
            case R.id.fail:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                        .setTipWord("发送失败")
                        .create();
                break;
            case R.id.img:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .create();
                break;
            case R.id.text:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setTipWord("请勿重复操作")
                        .create();
                break;
            case R.id.custom:
                tipDialog = new QMUITipDialog.CustomBuilder(this)
                        .setContent(R.layout.tipdialog_custom)
                        .create();
                break;
        }
        tipDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },1500);
    }

    private void showSimpleBottomSheetGrid() {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        final int TAG_SHARE_CHAT = 3;
        final int TAG_SHARE_LOCAL = 4;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.mipmap.icon_more_operation_share_friend, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_more_operation_share_moment, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_more_operation_share_weibo, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_WECHAT_FRIEND:
                                Toast.makeText(MainActivity.this, "分享到微信", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                Toast.makeText(MainActivity.this, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WEIBO:
                                Toast.makeText(MainActivity.this, "分享到微博", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).build().show();


    }
}
