package com.mjs.xiaomi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mjs.xiaomi.MainActivity;
import com.mjs.xiaomi.R;
import com.mjs.xiaomi.activity.CaptureActivity;
import com.mjs.xiaomi.util.CommonUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * Created by dafei on 2017/9/7.
 */
public class BookSearchFragment extends Fragment {
    QMUITopBar topbar;
    TextView tv_sao;

    //打开扫描界面请求码
    private int REQUEST_CODE = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_book_search,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topbar= (QMUITopBar) view.findViewById(R.id.topbar);
        tv_sao= (TextView) view.findViewById(R.id.tv_sao);
        topbar.setTitle(getString(R.string.title_book_search));
        initListener();
    }

    private void initListener(){
        tv_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开二维码扫描界面
                if(CommonUtil.isCameraCanUse()){
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }else{
                    Toast.makeText(getActivity(),"请打开此应用的摄像头权限！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
