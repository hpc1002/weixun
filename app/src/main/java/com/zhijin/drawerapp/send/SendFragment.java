package com.zhijin.drawerapp.send;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zhijin.drawerapp.R;
import com.zhijin.drawerapp.base.BaseFragment;
import com.zhijin.drawerapp.utils.ToastManager;

import butterknife.BindView;

/**
 * Created by hpc on 2017/3/16.
 */

public class SendFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.idea_mesasge_edit)
    EditText ideaMesasgeEdit;
    @BindView(R.id.idea_number)
    EditText ideaNumber;
    @BindView(R.id.idea_sendtext)
    TextView ideaSendtext;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_send, container, false);
    }

    @Override
    protected void initListener() {
        ideaSendtext.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.idea_sendtext) {
            String content = ideaMesasgeEdit.getText().toString().trim();
            String number = ideaNumber.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastManager.show("请填写你遇到的问题");
                return;
            }
            if (TextUtils.isEmpty(number)) {
                ToastManager.show("选填不能为空");
                return;
            }
            ToastManager.show("信息反馈成功");
            ideaMesasgeEdit.setText("");
            ideaNumber.setText("");
        }
    }
}
