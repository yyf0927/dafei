package com.mjs.xiaomi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mjs.xiaomi.R;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * Created by dafei on 2017/9/7.
 */
public class MyBookFragment extends Fragment {
    QMUITopBar topbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_book, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topbar = (QMUITopBar) view.findViewById(R.id.topbar);
        topbar.setTitle(getString(R.string.title_my_library));
        //根据有没有藏书来判断是否显示图标
//        topbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(),"111",Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}
