package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentRecordOrderBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.RecordOrderResponse;
import com.birdwind.inspire.medical.diary.presenter.RecorderOrderPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.RecordOrderAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecorderOrderView;

import java.util.List;

public class RecordOrderFragment extends AbstractFragment<RecorderOrderPresenter, FragmentRecordOrderBinding>
    implements RecorderOrderView {

    private long uid;

    private RecordOrderAdapter recordOrderAdapter;

    @Override
    public RecorderOrderPresenter createPresenter() {
        return new RecorderOrderPresenter(this);
    }

    @Override
    public FragmentRecordOrderBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentRecordOrderBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        recordOrderAdapter.addChildClickViewIds(R.id.ib_play_record_order_fragment,
            R.id.ib_stop_play_record_order_fragment);
        recordOrderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ib_play_record_order_fragment) {
                RecordOrderResponse.Response recordOrderItem = (RecordOrderResponse.Response) adapter.getItem(position);
                recordOrderAdapter.startPlay(true, position, recordOrderItem);
            } else if (view.getId() == R.id.ib_stop_play_record_order_fragment) {
                recordOrderAdapter.startPlay(false, position, null);
            }
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
        recordOrderAdapter = new RecordOrderAdapter(R.layout.item_record_order);
        recordOrderAdapter.setAnimationEnable(true);
        recordOrderAdapter.setRecyclerView(binding.rvRecordRecordOrderFragment);
    }

    @Override
    public void initView() {
        binding.rvRecordRecordOrderFragment
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvRecordRecordOrderFragment.setHasFixedSize(true);
        binding.rvRecordRecordOrderFragment.setAdapter(recordOrderAdapter);
    }

    @Override
    public void doSomething() {
        presenter.getRecordOrder(uid);
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
    public void onGetPatientVoiceList(boolean isSuccess, List<RecordOrderResponse.Response> response) {
        recordOrderAdapter.setNewInstance(response);
    }

    @Override
    public void onDestroy() {
        recordOrderAdapter.releaseMediaPlayer();
        super.onDestroy();
    }
}
