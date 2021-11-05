package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentRecordOrderBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.RecordOrderAdapter;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecordOrderFragment extends AbstractFragment<AbstractPresenter, FragmentRecordOrderBinding> {
    private RecordOrderAdapter recordOrderAdapter;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentRecordOrderBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentRecordOrderBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recordOrderAdapter = new RecordOrderAdapter(R.layout.item_record_order);
        recordOrderAdapter.setAnimationEnable(true);
        recordOrderAdapter.setRecyclerView(binding.rvRecordRecordOrderFragment);
        List<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tempList.add(0);
        }
        recordOrderAdapter.setNewInstance(tempList);
    }

    @Override
    public void initView() {
        binding.rvRecordRecordOrderFragment
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvRecordRecordOrderFragment.setHasFixedSize(true);
        binding.rvRecordRecordOrderFragment.setAdapter(recordOrderAdapter);
    }

    @Override
    public void doSomething() {

    }

    @Override
    public boolean isShowTopBar() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            return true;
        } else {
            return false;
        }
    }
}
