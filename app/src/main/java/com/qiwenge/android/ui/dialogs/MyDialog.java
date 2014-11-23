package com.qiwenge.android.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiwenge.android.R;

import java.util.ArrayList;
import java.util.List;


/**
 * FDialogUtils
 * <p/>
 * Created by Eric on 2014年9月25日
 *
 * @since 2.0
 */
public class MyDialog {

    private String mTitle;

    private Dialog mDialog;

    private Activity mContext;

    private LinearLayout layoutContainer;

    private TextView tvTitle;

    @SuppressWarnings("unused")
    private MyDialog() {
    }

    public MyDialog(Activity act) {
        this.mContext = act;
        init();
    }

    public MyDialog(Activity act, String title) {
        this.mTitle = title;
        this.mContext = act;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.Dialog_Fullscreen);
        mDialog.getWindow().setWindowAnimations(R.style.MyDialogStyle);
        View container = getContainerView(mContext);
        tvTitle = (TextView) container.findViewById(R.id.tv_dialog_title);
        if (mTitle != null) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        layoutContainer = (LinearLayout) container.findViewById(R.id.layout_dialog_content);
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(container);
        mDialog.setCancelable(true);
    }

    public void setItems(String[] items, OnItemClickListener listener) {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < items.length; i++) {
            data.add(items[i]);
        }
        DialogSimpleAdapter adpater = new DialogSimpleAdapter(mContext, data);
        setItems(adpater, listener);
    }

    public void setItems(BaseAdapter adapter, final OnItemClickListener listener) {
        View view = getView(mContext, R.layout.dialog_l_list);
        ListView listView = (ListView) view.findViewById(R.id.listview_dialog);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) listener.onItemClick(parent, view, position, id);
                dismiss();
            }
        });
        addView(view);
    }

    public void setPositiveButton(String btnText, final OnPositiveClickListener listener) {
        View view = getView(mContext, R.layout.dialog_l_simple);
        TextView btnCancel = (TextView) view.findViewById(R.id.tv_btn_cancel);
        TextView btnSure = (TextView) view.findViewById(R.id.tv_btn_sure);
        if (btnText != null) btnSure.setText(btnText);
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) listener.onClick();
            }
        });
        addView(view);
    }

    public void addView(View view) {
        layoutContainer.addView(view);
    }

    public void removeAllViews() {
        layoutContainer.removeAllViews();
    }


    public void show() {
        if (mDialog != null) mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null) mDialog.dismiss();
    }

    public void setCancelable(boolean b) {
        mDialog.setCancelable(b);
    }

    private View getContainerView(Activity context) {
        return LayoutInflater.from(context).inflate(R.layout.dialog_l_container, null);
    }

    public View getView(Activity context, int layoutResId) {
        return LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public interface OnPositiveClickListener {

        public void onClick();
    }
}