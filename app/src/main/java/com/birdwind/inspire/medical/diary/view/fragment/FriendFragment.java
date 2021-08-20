package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.base.view.AbstractMainActivity;
import com.birdwind.inspire.medical.diary.databinding.FragmentFriendBinding;
import com.birdwind.inspire.medical.diary.model.response.FriendResponse;
import com.birdwind.inspire.medical.diary.presenter.FriendPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.FriendAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.FriendView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FriendFragment extends AbstractFragment<FriendPresenter, FragmentFriendBinding>
        implements FriendView, OnItemClickListener {

    private FriendAdapter friendAdapter;

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
        friendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initView() {
        binding.rvPatientFriendFragment.setHasFixedSize(true);
        binding.rvPatientFriendFragment
                .setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false));
        // binding.rvPatientDoctorMainActivity.setNestedScrollingEnabled(false);
        binding.rvPatientFriendFragment.setAdapter(friendAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        friendAdapter = new FriendAdapter(R.layout.item_patient);
        friendAdapter.setAnimationEnable(true);
    }

    @Override
    public void doSomething() {
        presenter.getPatient();
    }

    @Override
    public void onGetFriends(List<FriendResponse.Response> friendResponse) {
        friendAdapter.setList(friendResponse);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        FriendResponse.Response friend = (FriendResponse.Response) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt("UID", friend.getPid());

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);

        ((AbstractMainActivity) context).pushFragment(chatFragment, true);
        ((AbstractMainActivity) context).hideTopBar(false);
    }
}
