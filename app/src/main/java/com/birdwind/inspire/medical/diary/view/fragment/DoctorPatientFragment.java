package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentDoctorPatientBinding;
import com.birdwind.inspire.medical.diary.model.response.PatientResponse;
import com.birdwind.inspire.medical.diary.presenter.DoctorPatientPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.PatientAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.DoctorPatientPresent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

public class DoctorPatientFragment extends AbstractFragment<DoctorPatientPresenter, FragmentDoctorPatientBinding>
    implements DoctorPatientPresent, OnItemClickListener {

    private PatientAdapter patientAdapter;

    @Override
    public DoctorPatientPresenter createPresenter() {
        return new DoctorPatientPresenter(this);
    }

    @Override
    public FragmentDoctorPatientBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentDoctorPatientBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        patientAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        patientAdapter = new PatientAdapter(R.layout.item_patient);
        patientAdapter.setAnimationEnable(true);
    }

    @Override
    public void initView() {
        binding.rvPatientDoctorPatientMain.setHasFixedSize(true);
        binding.rvPatientDoctorPatientMain
            .setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        // binding.rvPatientDoctorMainActivity.setNestedScrollingEnabled(false);
        binding.rvPatientDoctorPatientMain.setAdapter(patientAdapter);
    }

    @Override
    public void doSomething() {
        presenter.getPatient();
    }

    @Override
    public void onGetFriends(List<PatientResponse.Response> friendResponse) {
        patientAdapter.setList(friendResponse);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        PatientResponse.Response friend = (PatientResponse.Response) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putLong("UID", friend.getPID());
        bundle.putString("name", friend.getName());
        bundle.putString("avatar", friend.getPhotoUrl());

        PatientFragment patientFragment = new PatientFragment();
        patientFragment.setArguments(bundle);

        pushFragment(patientFragment);
    }

    @Override
    public boolean isShowTopBar() {
        return false;
    }
}
