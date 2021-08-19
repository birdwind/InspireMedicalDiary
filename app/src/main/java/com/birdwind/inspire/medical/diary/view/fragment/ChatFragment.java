package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentChatBinding;
import com.birdwind.inspire.medical.diary.presenter.ChatPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;

public class ChatFragment extends AbstractFragment<ChatPresenter, FragmentChatBinding> implements ChatView {

    private int uid;

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

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            uid = bundle.getInt("UID", 0);
        }else{
            uid = 0;
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {
        presenter.getChatMessage(uid);
    }
}
