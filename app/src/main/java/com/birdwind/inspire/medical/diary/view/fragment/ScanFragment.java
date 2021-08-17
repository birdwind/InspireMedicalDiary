package com.birdwind.inspire.medical.diary.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.ExampleLoginBinding;
import com.birdwind.inspire.medical.diary.databinding.FragmentScanBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;

public class ScanFragment extends AbstractFragment<AbstractPresenter, FragmentScanBinding> implements OnScannerCompletionListener {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentScanBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentScanBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {
    }

    @Override
    public void onResume() {
        binding.svCameraScanFragment.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        binding.svCameraScanFragment.onPause();
        super.onPause();
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (rawResult == null) {
            showToast("沒有QRCode");
            return;
        }
    }
}
