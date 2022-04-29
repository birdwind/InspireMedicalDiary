package com.birdwind.inspire.medical.diary.view.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentSurveyListBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.SurveyQuestionnaireListResponse;
import com.birdwind.inspire.medical.diary.presenter.SurveyListPresenter;
import com.birdwind.inspire.medical.diary.view.activity.SurveyActivity;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionnaireAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.SurveyListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tbruyelle.rxpermissions3.Permission;

import java.util.List;

public class SurveyListFragment
        extends AbstractFragment<SurveyListPresenter, FragmentSurveyListBinding>
        implements SurveyListView, AbstractActivity.PermissionRequestListener {

    private int uid;

    private String patientName;

    private String familyName;

    private QuestionnaireAdapter questionnaireAdapter;

    private IdentityEnums identityEnums;

    private SurveyQuestionnaireListResponse.Response surveyQuestionnaireListResponse;

    @Override
    public SurveyListPresenter createPresenter() {
        return new SurveyListPresenter(this);
    }

    @Override
    public FragmentSurveyListBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
            boolean attachToParent) {
        return FragmentSurveyListBinding.inflate(inflater);
    }

    @Override
    public void addListener() {
        questionnaireAdapter.setOnItemClickListener((adapter, view, position) -> {
            surveyQuestionnaireListResponse =
                    (SurveyQuestionnaireListResponse.Response) adapter.getItem(position);
            if (hasPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)) {
                pushFragment();
            } else {
                getPermission(this, Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.RECORD_AUDIO);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = (int) bundle.getLong("UID", App.userModel.getUid());
            patientName = bundle.getString("patientName", "");
            familyName = bundle.getString("familyName", "");
            identityEnums = IdentityEnums.parseEnumsByType(
                    bundle.getInt("identity", App.userModel.getIdentityEnums().getType()));
        } else {
            uid = App.userModel.getUid();
            patientName = "";
            familyName = "";
            identityEnums = App.userModel.getIdentityEnums();
        }

        questionnaireAdapter = new QuestionnaireAdapter(R.layout.item_survey_questionnaire);
        questionnaireAdapter.setAnimationEnable(true);
        questionnaireAdapter.setRecyclerView(binding.rvSurveyListFragment);
    }

    @Override
    public void initView() {
        binding.rvSurveyListFragment.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvSurveyListFragment.setHasFixedSize(true);
        binding.rvSurveyListFragment.setAdapter(questionnaireAdapter);
    }

    @Override
    public void doSomething() {}

    @Override
    public void onResume() {
        super.onResume();
        init(patientName, familyName);

    }

    @Override
    public boolean isShowTopBar() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void getSurveyQuestionnaire(
            List<SurveyQuestionnaireListResponse.Response> surveyQuestionnaireListResponse) {
        questionnaireAdapter.setList(surveyQuestionnaireListResponse);
        if (surveyQuestionnaireListResponse.size() > 0) {
            binding.rvSurveyListFragment.setVisibility(View.VISIBLE);
            binding.tvNoneDataSurveyList.setVisibility(View.GONE);
        } else {
            binding.rvSurveyListFragment.setVisibility(View.GONE);
            binding.tvNoneDataSurveyList.setVisibility(View.VISIBLE);
        }
    }

    public void init(String patientName, String familyName) {
        presenter.getSurveyQuestionnaire(uid, identityEnums.getType());
        String topBarHeader = patientName;
        if (!TextUtils.isEmpty(familyName)) {
            topBarHeader += "-" + familyName;
        }
        fragmentNavigationListener.updateTitle(topBarHeader);
    }

    public void updateSurveyList(long uid, String patientName, boolean isLoad) {
        updateSurveyList(uid, patientName, "", IdentityEnums.PAINTER, isLoad);
    }

    public void updateSurveyList(long uid, String patientName, String familyName,
            IdentityEnums identityEnums, boolean isLoad) {
        this.uid = (int) uid;
        this.identityEnums = identityEnums;
        if (isLoad) {
            init(patientName, familyName);
        }
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.granted) {
            pushFragment();
        } else if (permission.shouldShowRequestPermissionRationale) {
            showToast(getString(R.string.scan_no_permission));
        } else {
            showToast(getString(R.string.error_common_permission_never_show));
        }
    }

    private void pushFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("questionnaireID", surveyQuestionnaireListResponse.getQuestionnaireID());
        bundle.putInt("identity", identityEnums.getType());
        startActivity(SurveyActivity.class, bundle);
    }
}
