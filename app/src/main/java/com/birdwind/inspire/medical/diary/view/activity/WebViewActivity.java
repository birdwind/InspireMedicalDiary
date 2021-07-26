package com.birdwind.inspire.medical.diary.view.activity;

import java.util.HashMap;
import java.util.Map;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityWebviewBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.customer.ProgressWebView;
import com.leaf.library.StatusBarUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebViewActivity extends AbstractActivity<AbstractPresenter, ActivityWebviewBinding>
    implements ProgressWebView.ProgressWebViewListener {

    private String link;

    private boolean isPost;

    private boolean isMoney;

    private String title;

    private String postData;

    private int pageStatus;

    private boolean isSuccess;

    private String dialogContent;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityWebviewBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivityWebviewBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.progressWebView.addProgressWebViewListener(this);
        binding.viewWebViewHeader.llCloseTopBarComp.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void initView() {
        StatusBarUtil.setLightMode(this);
        binding.progressWebView.setPageStatus(pageStatus);
        binding.viewWebViewHeader.llRightButtonTopBarComp.setVisibility(View.GONE);
        binding.viewWebViewHeader.btRightButtonTopBarComp.setVisibility(View.GONE);

        Map<String, String> header = new HashMap<>();
        if (App.userModel != null) {
            header.put("Token", App.userModel.getToken());
        }
        if (isPost) {
            binding.progressWebView.postUrl(link, postData.getBytes());
        } else {
            binding.progressWebView.loadUrl(link, header);
        }
        binding.viewWebViewHeader.llCloseTopBarComp.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            link = bundle.getString("link");
            isPost = bundle.getBoolean("isPost", false);
            postData = bundle.getString("postData", "");
            isMoney = bundle.getBoolean("isMoney", false);
            // title = bundle.getString("title", getString(R.string.kmt));
            pageStatus = bundle.getInt("pageStatus", 0);
            dialogContent = bundle.getString("dialogContent", "");
        }
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void getHtml(String msg) {}

    @Override
    public void getIsWebViewSuccess(boolean isSuccess) {
        // btBottomBack.setVisibility(View.VISIBLE);
        this.isSuccess = isSuccess;
    }

    @Override
    public void hideCloseButton() {
        // tvMoneyCancel.setVisibility(View.INVISIBLE);
    }
}
