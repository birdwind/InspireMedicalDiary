package com.birdwind.inspire.medical.diary.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentChatBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.ChatPresenter;
import com.birdwind.inspire.medical.diary.receiver.ChatBroadcastReceiver;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatService;
import com.birdwind.inspire.medical.diary.view.adapter.ChatAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;

public class ChatFragment extends AbstractFragment<ChatPresenter, FragmentChatBinding> implements ChatView {

    private long uid;

    private ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;

    private ChatBroadcastReceiver chatBroadcastReceiver;

    private ChatAdapter chatAdapter;

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
            sendMessage(false);
        });

        binding.etMessageChatFragment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage(false);
            }
            return false;
        });

        binding.ibSendVisitChatFragment.setOnClickListener(v -> {
            sendMessage(true);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getLong("UID", App.userModel.getUid());
        } else {
            uid = App.userModel.getUid();
        }

        chatAdapter = new ChatAdapter();
        chatAdapter.setAnimationEnable(true);
        chatAdapter.setRecyclerView(binding.rvMessageChatFragment);
        chatService = new ChatService(context);

        onScrollChangedListener = () -> {
            if (binding.rvMessageChatFragment.getScrollY() == 0) {
                LogUtils.d("滑到最上面拉!別滑了");
            }
        };
    }

    @Override
    public void initView() {
        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                break;
            case FAMILY:
            case PAINTER:
                binding.ibSendVisitChatFragment.setVisibility(View.GONE);
                break;
        }
        binding.llMessageChatFragment.setBackgroundColor(App.userModel.getIdentityMainColor());
        binding.rvMessageChatFragment
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvMessageChatFragment.setHasFixedSize(true);
        binding.rvMessageChatFragment.setAdapter(chatAdapter);
    }

    @Override
    public void doSomething() {

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
    public void onSendMessage(boolean isSuccess) {

    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onPause() {
        super.onPause();
        chatBroadcastReceiver.unregister(context);
    }

    @Override
    public boolean isShowTopBar() {
        return App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR;
    }

    private void scrollToChatLatest() {
        binding.rvMessageChatFragment.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    private void sendMessage(boolean isSchedule) {
        String message = binding.etMessageChatFragment.getText().toString();

        if (isSchedule) {
            presenter.sendScheduleMessage(uid, message);
        } else if (!TextUtils.isEmpty(message)) {
            presenter.sendChatMessage(uid, message);
        }
        binding.etMessageChatFragment.setText("");
    }

    @Override
    public boolean onBackPressed() {
        return App.userModel.getIdentityEnums() != IdentityEnums.DOCTOR;
    }
}
