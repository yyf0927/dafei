package com.mjs.xiaomi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjs.xiaomi.R;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * Created by dafei on 2017/9/7.
 */
public class LibraryStatisticsFragment extends Fragment {
    QMUITopBar topbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_library_statistics,null);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topbar= (QMUITopBar) view.findViewById(R.id.topbar);
        topbar.setTitle(getString(R.string.title_library_statistics));
        topbar.setBackgroundDividerEnabled(false);
    }
}
