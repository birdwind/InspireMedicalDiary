package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentFriendBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.presenter.FriendPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.PatientAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.FriendView;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends AbstractFragment<FriendPresenter, FragmentFriendBinding> implements FriendView {

    private PatientAdapter patientAdapter;

    @Override
    public FriendPresenter createPresenter() {
        return new FriendPresenter(this);
    }

    @Override
    public FragmentFriendBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentFriendBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {
        binding.rvPatientFriendFragment.setHasFixedSize(true);
        binding.rvPatientFriendFragment
            .setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        // binding.rvPatientDoctorMainActivity.setNestedScrollingEnabled(false);
        binding.rvPatientFriendFragment.setAdapter(patientAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        patientAdapter = new PatientAdapter(R.layout.item_patient);
        patientAdapter.setAnimationEnable(true);
        List<String> patientList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            patientList.add("患者" + (i + 1));
        }
        patientAdapter.setList(patientList);
    }

    @Override
    public void doSomething() {
        presenter.getPatient();
    }
}
