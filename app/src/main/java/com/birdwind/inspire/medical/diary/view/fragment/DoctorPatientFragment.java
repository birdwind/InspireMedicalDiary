package com.birdwind.inspire.medical.diary.view.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.birdwind.inspire.medical.diary.model.PainterDiseaseModel;
import com.birdwind.inspire.medical.diary.model.SurveyWebSocketModel;
import com.birdwind.inspire.medical.diary.model.response.PatientResponse;
import com.birdwind.inspire.medical.diary.presenter.DoctorPatientPresenter;
import com.birdwind.inspire.medical.diary.receiver.ChatBroadcastReceiver;
import com.birdwind.inspire.medical.diary.receiver.PainterSurveyBroadcastReceiver;
import com.birdwind.inspire.medical.diary.view.adapter.PatientAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.DoctorPatientPresent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

public class DoctorPatientFragment extends AbstractFragment<DoctorPatientPresenter, FragmentDoctorPatientBinding>
    implements DoctorPatientPresent, OnItemClickListener {

    private PatientAdapter patientAdapter;

    private ChatBroadcastReceiver chatBroadcastReceiver;

    private PainterSurveyBroadcastReceiver painterSurveyBroadcastReceiver;

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
        if (friendResponse.size() > 0) {
            binding.rvPatientDoctorPatientMain.setVisibility(View.VISIBLE);
            binding.tvNoneDataDoctorPatientMain.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        PatientResponse.Response friend = (PatientResponse.Response) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putLong("UID", friend.getPID());
        bundle.putString("name", friend.getName());
        bundle.putString("avatar", friend.getPhotoUrl());
        bundle.putInt("disease", friend.getDisease());

        PatientDashboardFragment patientDashboardFragment = new PatientDashboardFragment();
        patientDashboardFragment.setArguments(bundle);

        pushFragment(patientDashboardFragment);
    }

    @Override
    public boolean isShowTopBar() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        chatBroadcastReceiver = new ChatBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                long userId = 0;
                if (bundle != null) {
                    userId = bundle.getLong("userId", 0);
                    updateNewChatIcon(userId);
                }
            }
        };
        chatBroadcastReceiver.register(context);

        painterSurveyBroadcastReceiver = new PainterSurveyBroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    SurveyWebSocketModel surveyWebSocketModel =
                            (SurveyWebSocketModel) bundle.getSerializable("surveyWebSocketModel");
                    updateNewSurveyIcon(surveyWebSocketModel.getPID());
                }
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        chatBroadcastReceiver.unregister(context);
        painterSurveyBroadcastReceiver.unregister(context);
    }

    private void updateNewChatIcon(Long userId) {
        for (int i = 0; i < patientAdapter.getData().size(); i++) {
            PatientResponse.Response patient = patientAdapter.getItem(i);
            if (patient.getPID() == userId) {
                patient.setHasUnreadMessage(true);
                patientAdapter.setData(i, patient);
                break;
            }
        }
    }

    private void updateNewSurveyIcon(Long userId) {
        for (int i = 0; i < patientAdapter.getData().size(); i++) {
            PatientResponse.Response patient = patientAdapter.getItem(i);
            if (patient.getPID() == userId) {
                patient.setHasUnreadReport(true);
                patientAdapter.setData(i, patient);
                break;
            }
        }
    }
}
