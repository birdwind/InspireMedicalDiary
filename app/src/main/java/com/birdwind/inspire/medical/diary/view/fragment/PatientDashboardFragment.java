package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.animation.SlideHeightAnimation;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentPatientBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.presenter.PatientDashboardPresent;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatMemberService;
import com.birdwind.inspire.medical.diary.view.adapter.GroupMemberAdapter;
import com.birdwind.inspire.medical.diary.view.adapter.ViewPage2Adapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.PatientDashboardView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientDashboardFragment extends AbstractFragment<PatientDashboardPresent, FragmentPatientBinding>
    implements PatientDashboardView, OnItemClickListener {

    private long uid;

    private String patientName;

    private String patientAvatar;

    private DiseaseEnums diseaseEnums;

    private boolean isHideFriendGroup;

    private ViewPage2Adapter viewPage2Adapter;

    private Map<String, Fragment> fragmentMap;

    private GroupMemberAdapter groupMemberAdapter;

    private ChatMemberService chatMemberService;

    protected SlideHeightAnimation expandSlideMemberGroupAnimation;

    protected SlideHeightAnimation shrinkSlideMemberGroupAnimation;

    protected RotateAnimation expandRotateArrowAnimation;

    protected RotateAnimation shrinkRotateArrowAnimation;

    @Override
    public PatientDashboardPresent createPresenter() {
        return new PatientDashboardPresent(this);
    }

    @Override
    public FragmentPatientBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentPatientBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        groupMemberAdapter.setOnItemClickListener(this);
        binding.comGroupMemberPatientFragment.llDownArrowChatGroupComponent.setOnClickListener(v -> {
            hideFriendGroup(!isHideFriendGroup);
            isHideFriendGroup = !isHideFriendGroup;
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getLong("UID", App.userModel.getUid());
            patientName = bundle.getString("name", "");
            patientAvatar = bundle.getString("avatar", "");
            diseaseEnums = DiseaseEnums.parseEnumsByType(bundle.getInt("disease", 0));
        } else {
            uid = App.userModel.getUid();
            patientName = "";
            patientAvatar = "";
            diseaseEnums = App.userModel.getDiseaseEnums();
        }

        isHideFriendGroup = false;

        Bundle patientBundle = new Bundle();
        patientBundle.putLong("UID", uid);
        ChatFragment chatFragment = new ChatFragment();
        ChartPatientFragment chartPatientFragment = new ChartPatientFragment();
        ChartFamilyFragment chartFamilyFragment = new ChartFamilyFragment();
        RecordOrderFragment recordOrderFragment = new RecordOrderFragment();
        chatFragment.setArguments(patientBundle);
        chartPatientFragment.setArguments(patientBundle);
        chartFamilyFragment.setArguments(patientBundle);
        recordOrderFragment.setArguments(patientBundle);

        fragmentMap = new HashMap<>();
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
            if (App.userModel.isProxy()) {
                if (diseaseEnums == DiseaseEnums.ALZHEIMER) {
                    fragmentMap.put(getString(R.string.doctor_main_tab_chart_patient), recordOrderFragment);
                } else {
                    fragmentMap.put(getString(R.string.doctor_main_tab_chart_patient), chartPatientFragment);
                }
                fragmentMap.put(getString(R.string.doctor_main_tab_chart_family), chartFamilyFragment);
            } else {
                fragmentMap.put(getString(R.string.doctor_main_tab_chart_patient), chartFamilyFragment);
            }
        } else {
            if (diseaseEnums == DiseaseEnums.ALZHEIMER) {
                fragmentMap.put(getString(R.string.doctor_main_tab_chart), recordOrderFragment);
            } else {
                fragmentMap.put(getString(R.string.doctor_main_tab_chart), chartPatientFragment);
            }
        }
        fragmentMap.put(getString(R.string.doctor_main_tab_message), chatFragment);

        viewPage2Adapter = new ViewPage2Adapter(this, fragmentMap);
        chatMemberService = new ChatMemberService(context);

        groupMemberAdapter = new GroupMemberAdapter(R.layout.item_chat_member);
        groupMemberAdapter.setAnimationEnable(true);
        groupMemberAdapter.setRecyclerView(binding.comGroupMemberPatientFragment.rvMemberChatGroupComponent);

        expandSlideMemberGroupAnimation =
            new SlideHeightAnimation(binding.comGroupMemberPatientFragment.rlChatGroupComponent,
                Utils.dp2px(context, 45), Utils.dp2px(context, 148), 300);
        shrinkSlideMemberGroupAnimation =
            new SlideHeightAnimation(binding.comGroupMemberPatientFragment.rlChatGroupComponent,
                Utils.dp2px(context, 148), Utils.dp2px(context, 45), 300);
        expandSlideMemberGroupAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideMemberGroupAnimation.setInterpolator(new AccelerateInterpolator());

        expandRotateArrowAnimation =
            new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrinkRotateArrowAnimation =
            new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        initRotateAnimation(expandRotateArrowAnimation);
        initRotateAnimation(shrinkRotateArrowAnimation);
    }

    @Override
    public void initView() {
        binding.vpPatientFragment.setAdapter(viewPage2Adapter);
        new TabLayoutMediator(binding.tlPatientFragment, binding.vpPatientFragment, ((tab, position) -> {
            tab.setText(fragmentMap.keySet().toArray()[position].toString());
        })).attach();

        if (App.userModel.getIdentityEnums() != IdentityEnums.DOCTOR) {
            binding.comGroupMemberPatientFragment.rlChatGroupComponent.setVisibility(View.GONE);
        }

        binding.comGroupMemberPatientFragment.rvMemberChatGroupComponent
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.comGroupMemberPatientFragment.rvMemberChatGroupComponent.setHasFixedSize(true);
        binding.comGroupMemberPatientFragment.rvMemberChatGroupComponent.setAdapter(groupMemberAdapter);
        binding.comGroupMemberPatientFragment.tvTargetAvatarChatGroupComponent.setText(patientName);
        Glide.with(context).load(patientAvatar).placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar))
            .into(binding.comGroupMemberPatientFragment.civTargetAvatarChatGroupComponent);

    }

    @Override
    public void doSomething() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            presenter.getChatMember(uid);
        }
    }

    @Override
    public void onGetChatMember(boolean isSuccess) {
        if (isSuccess) {
            groupMemberAdapter.setList(chatMemberService.getChatMemberByPID(uid));
        }
    }

    private void initRotateAnimation(RotateAnimation rotateAnimation) {
        rotateAnimation.setDuration(300); // duration in ms
        rotateAnimation.setRepeatCount(0); // -1 = infinite repeated
        rotateAnimation.setRepeatMode(Animation.REVERSE); // reverses each repeat
        rotateAnimation.setFillAfter(true); // keep rotation after animation
        rotateAnimation.setInterpolator(new AccelerateInterpolator());
    }

    private void hideFriendGroup(boolean isHide) {
        if (isHide) {
            startAnimation(binding.comGroupMemberPatientFragment.rlMainChatGroupComponent,
                shrinkSlideMemberGroupAnimation);
            startAnimation(binding.comGroupMemberPatientFragment.ivDownArrowChatGroupComponent,
                shrinkRotateArrowAnimation);
        } else {
            startAnimation(binding.comGroupMemberPatientFragment.rlMainChatGroupComponent,
                expandSlideMemberGroupAnimation);
            startAnimation(binding.comGroupMemberPatientFragment.ivDownArrowChatGroupComponent,
                expandRotateArrowAnimation);
        }
    }

    private void startAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ChatMemberResponse.Response chatMemberResponse = (ChatMemberResponse.Response) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putLong("UID", chatMemberResponse.getUID());
        // bundle.putString("name", chatMemberResponse.getUserName());
        // bundle.putString("avatar", chatMemberResponse.getPhotoUrl());
        ChartPatientFragment chartFragment = new ChartPatientFragment();
        chartFragment.setArguments(bundle);
        pushFragment(chartFragment);
    }

    @Override
    public boolean isShowTopBar() {
        return App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR;
    }
}
