package com.birdwind.inspire.medical.diary.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.animation.SlideHeightAnimation;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentChatBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.ChatPresenter;
import com.birdwind.inspire.medical.diary.receiver.ChatBroadcastReceiver;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatMemberService;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatService;
import com.birdwind.inspire.medical.diary.view.adapter.ChatAdapter;
import com.birdwind.inspire.medical.diary.view.adapter.GroupMemberAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;
import com.bumptech.glide.Glide;

public class ChatFragment extends AbstractFragment<ChatPresenter, FragmentChatBinding> implements ChatView {

    private ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;

    private ChatBroadcastReceiver chatBroadcastReceiver;

    protected SlideHeightAnimation expandSlideFriendGroupAnimation;

    protected SlideHeightAnimation shrinkSlideFriendGroupAnimation;

    protected RotateAnimation expandRotateArrowAnimation;

    protected RotateAnimation shrinkRotateArrowAnimation;

    private long uid;

    private String friendName;

    private String friendAvatar;

    private boolean isHideFriendGroup;

    private GroupMemberAdapter groupMemberAdapter;

    private ChatAdapter chatAdapter;

    private ChatMemberService chatMemberService;

    private ChatService chatService;

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
        binding.comGroupFriend.llDownArrowChatGroupComponent.setOnClickListener(v -> {
            hideFriendGroup(!isHideFriendGroup);
            isHideFriendGroup = !isHideFriendGroup;
        });

        binding.rvMessageChatFragment.setOnTouchListener(new View.OnTouchListener() {
            private ViewTreeObserver observer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (observer == null) {
                    observer = binding.rvMessageChatFragment.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                } else if (!observer.isAlive()) {
                    observer.removeOnScrollChangedListener(onScrollChangedListener);
                    observer = binding.rvMessageChatFragment.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                }
                return false;
            }
        });

        binding.ibSendChatFragment.setOnClickListener(v -> {
            sendMessage();
        });

        binding.etMessageChatFragment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage();
            }
            return false;
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getLong("UID", App.userModel.getUid());
            friendName = bundle.getString("name", "");
            friendAvatar = bundle.getString("avatar", "");
        } else {
            uid = App.userModel.getUid();
            friendName = "";
            friendAvatar = "";
        }

        isHideFriendGroup = false;

        expandSlideFriendGroupAnimation = new SlideHeightAnimation(binding.comGroupFriend.rlChatGroupComponent,
            Utils.dp2px(context, 45), Utils.dp2px(context, 148), 300);
        shrinkSlideFriendGroupAnimation = new SlideHeightAnimation(binding.comGroupFriend.rlChatGroupComponent,
            Utils.dp2px(context, 148), Utils.dp2px(context, 45), 300);
        expandSlideFriendGroupAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideFriendGroupAnimation.setInterpolator(new AccelerateInterpolator());

        expandRotateArrowAnimation =
            new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrinkRotateArrowAnimation =
            new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        initRotateAnimation(expandRotateArrowAnimation);
        initRotateAnimation(shrinkRotateArrowAnimation);

        groupMemberAdapter = new GroupMemberAdapter(R.layout.item_chat_member);
        groupMemberAdapter.setAnimationEnable(true);
        groupMemberAdapter.setRecyclerView(binding.comGroupFriend.rvMemberChatGroupComponent);

        chatAdapter = new ChatAdapter();
        chatAdapter.setAnimationEnable(true);
        chatAdapter.setRecyclerView(binding.rvMessageChatFragment);

        chatMemberService = new ChatMemberService(context);
        chatService = new ChatService(context);

        onScrollChangedListener = () -> {
            if (binding.rvMessageChatFragment.getScrollY() == 0) {
                LogUtils.d("滑到最上面拉!別滑了");
            }
        };
    }

    @Override
    public void initView() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            binding.comGroupFriend.rvMemberChatGroupComponent
                .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            binding.comGroupFriend.rvMemberChatGroupComponent.setHasFixedSize(true);
            binding.comGroupFriend.rvMemberChatGroupComponent.setAdapter(groupMemberAdapter);
            binding.comGroupFriend.tvTargetAvatarChatGroupComponent.setText(friendName);
            Glide.with(context).load(friendAvatar).placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar))
                .into(binding.comGroupFriend.civTargetAvatarChatGroupComponent);
        } else {
            binding.comGroupFriend.rlChatGroupComponent.setVisibility(View.GONE);
        }
        binding.llMessageChatFragment.setBackgroundColor(App.userModel.getIdentityMainColor());
        binding.rvMessageChatFragment
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvMessageChatFragment.setHasFixedSize(true);
        binding.rvMessageChatFragment.setAdapter(chatAdapter);
    }

    @Override
    public void doSomething() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            presenter.getChatMember(uid);
        }

        presenter.getChatMessage(uid);

        chatBroadcastReceiver = new ChatBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                long chatID = 0;
                if (bundle != null) {
                    chatID = bundle.getLong("chatID", 0);
                }
                chatAdapter.addData(chatService.getChatByID(chatID));
                scrollToChatLatest();
            }
        };
        chatBroadcastReceiver.register(context);
    }

    @Override
    public void onGetChatMessage(boolean isSuccess) {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
            chatAdapter.setList(chatService.findAll());
        } else {
            chatAdapter.setList(chatService.getChatListByPID(uid));
        }
        scrollToChatLatest();
    }

    @Override
    public void onGetChatMember(boolean isSuccess) {
        if (isSuccess) {
            groupMemberAdapter.setList(chatMemberService.getChatMemberByPID(uid));
        }
    }

    @Override
    public void onSendMessage(boolean isSuccess) {

    }

    @Override
    public void onPause() {
        super.onPause();
        chatBroadcastReceiver.unregister(context);
    }

    private void hideFriendGroup(boolean isHide) {
        if (isHide) {
            startAnimation(binding.comGroupFriend.rlMainChatGroupComponent, shrinkSlideFriendGroupAnimation);
            startAnimation(binding.comGroupFriend.ivDownArrowChatGroupComponent, shrinkRotateArrowAnimation);
        } else {
            startAnimation(binding.comGroupFriend.rlMainChatGroupComponent, expandSlideFriendGroupAnimation);
            startAnimation(binding.comGroupFriend.ivDownArrowChatGroupComponent, expandRotateArrowAnimation);
        }
    }

    private void startAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }

    private void initRotateAnimation(RotateAnimation rotateAnimation) {
        rotateAnimation.setDuration(300); // duration in ms
        rotateAnimation.setRepeatCount(0); // -1 = infinite repeated
        rotateAnimation.setRepeatMode(Animation.REVERSE); // reverses each repeat
        rotateAnimation.setFillAfter(true); // keep rotation after animation
        rotateAnimation.setInterpolator(new AccelerateInterpolator());
    }

    private void sendMessage() {
        String message = binding.etMessageChatFragment.getText().toString();
        if (!message.isEmpty()) {
            binding.etMessageChatFragment.setText("");
            presenter.sendChatMessage(uid, message);
        }
    }

    private void scrollToChatLatest() {
        binding.rvMessageChatFragment.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

}
