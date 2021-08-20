package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.animation.SlideHeightAnimation;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentChatBinding;
import com.birdwind.inspire.medical.diary.presenter.ChatPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class ChatFragment extends AbstractFragment<ChatPresenter, FragmentChatBinding> implements ChatView {

    protected SlideHeightAnimation expandSlideFriendGroupAnimation;

    protected SlideHeightAnimation shrinkSlideFriendGroupAnimation;

    private int uid;

    private boolean isHideFriendGroup;

    @Override
    public ChatPresenter createPresenter() {
        return new ChatPresenter(this);
    }

    @Override
    public FragmentChatBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentChatBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.comGroupFriend.llDownArrowGroupFriendComponent.setOnClickListener(v -> {
            hideFriendGroup(!isHideFriendGroup);
            isHideFriendGroup = !isHideFriendGroup;
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getInt("UID", 0);
        } else {
            uid = 0;
        }

        isHideFriendGroup = false;


        expandSlideFriendGroupAnimation =
                new SlideHeightAnimation(binding.comGroupFriend.rlMainGroupFriendComponent, Utils.dp2px(context, 0), Utils.dp2px(context, 120), 300);
        shrinkSlideFriendGroupAnimation =
                new SlideHeightAnimation(binding.comGroupFriend.rlMainGroupFriendComponent, Utils.dp2px(context, 120), Utils.dp2px(context, 0), 300);
        expandSlideFriendGroupAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideFriendGroupAnimation.setInterpolator(new AccelerateInterpolator());
    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {
        presenter.getChatMessage(uid);
    }

    private void hideFriendGroup(boolean isHide) {
        if (isHide) {
            slideHeightAnimation(binding.comGroupFriend.rlMainGroupFriendComponent, shrinkSlideFriendGroupAnimation);
        } else {
            slideHeightAnimation(binding.comGroupFriend.rlMainGroupFriendComponent, expandSlideFriendGroupAnimation);
        }
    }

    private void slideHeightAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }
}
