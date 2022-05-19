package com.birdwind.inspire.medical.diary.view.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentBasicInfoBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.presenter.BasicInfoPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.BasicInfoView;

public class BasicInfoFragment extends AbstractFragment<BasicInfoPresenter, FragmentBasicInfoBinding>
    implements BasicInfoView {

    private boolean isFirst;

    private IdentityEnums identityEnums;

    private InformationResponse.Response originalInformation;

    private InformationResponse.Response information;

    @Override
    public BasicInfoPresenter createPresenter() {
        return new BasicInfoPresenter(this);
    }

    @Override
    public FragmentBasicInfoBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentBasicInfoBinding.inflate(inflater);
    }

    @Override
    public void addListener() {
        binding.etNameBasicInfoFragment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateConfirmButtonStatus(checkChanged());
            }
        });
        binding.etIdBasicInfoFragment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateConfirmButtonStatus(checkChanged());
            }
        });
        binding.rbMaleBasicInfoFragment.setOnCheckedChangeListener((compoundButton, isCheck) -> {
            binding.rbFemaleBasicInfoFragment.setChecked(!isCheck);
            updateConfirmButtonStatus(checkChanged());
        });

        binding.rbFemaleBasicInfoFragment.setOnCheckedChangeListener((compoundButton, isCheck) -> {
            binding.rbMaleBasicInfoFragment.setChecked(!isCheck);
            updateConfirmButtonStatus(checkChanged());
        });

        binding.btConfirmBasiInfoFragment.setOnClickListener(v -> {
            if (checkChanged()) {
                presenter.settBasicInfo(information);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            identityEnums = (IdentityEnums) bundle.getSerializable("identity");
        }
        if (identityEnums == null) {
            identityEnums = App.userModel.getIdentityEnums();
        }
        originalInformation = new InformationResponse.Response();
        information = new InformationResponse.Response();
        isFirst = true;
    }

    @Override
    public void initView() {
        binding.btConfirmBasiInfoFragment.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));
    }

    @Override
    public void doSomething() {
        presenter.getBasicInfo(App.userModel.getUid(), identityEnums);
    }

    @Override
    public void onGetInformation(boolean isSuccess, InformationResponse.Response response) {
        originalInformation = response;
        information = GsonUtils.parseJsonToBean(GsonUtils.toJson(response), InformationResponse.Response.class);
        binding.etNameBasicInfoFragment.setText(response.getName());
        binding.etIdBasicInfoFragment.setText(response.getIDNumber());
        if (originalInformation.getSex().equals(1)) {
            binding.rbFemaleBasicInfoFragment.setChecked(false);
            binding.rbMaleBasicInfoFragment.setChecked(true);
        } else if (originalInformation.getSex().equals(2)) {
            binding.rbFemaleBasicInfoFragment.setChecked(true);
            binding.rbMaleBasicInfoFragment.setChecked(false);
        } else {
            binding.rbMaleBasicInfoFragment.setChecked(false);
            binding.rbFemaleBasicInfoFragment.setChecked(false);
        }
        isFirst = false;
    }

    @Override
    public void onSetInformation(boolean isSuccess) {
        App.userModel.setName(information.getName());
        App.updateUserModel();
        onBackPressedByActivity();
    }

    private boolean checkChanged() {
        if (!isFirst) {
            information.setName(binding.etNameBasicInfoFragment.getText().toString());
            information.setIDNumber(binding.etIdBasicInfoFragment.getText().toString());
            information.setSex(binding.rbMaleBasicInfoFragment.isChecked() ? 1
                : binding.rbFemaleBasicInfoFragment.isChecked() ? 2 : 0);

            if (!information.getName().equals(originalInformation.getName())) {
                return true;
            }

            if (!information.getIDNumber().equals(originalInformation.getIDNumber())) {
                return true;
            }

            if (!information.getSex().equals(originalInformation.getSex())) {
                return true;
            }
        }

        return false;
    }

    private void updateConfirmButtonStatus(boolean isClickable) {
        if (isClickable) {
            binding.btConfirmBasiInfoFragment.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));
            binding.btConfirmBasiInfoFragment.setTextColor(getResources().getColor(R.color.colorWhite_FFFFFF));
        } else {
            binding.btConfirmBasiInfoFragment
                .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray_A6A6A6)));
            binding.btConfirmBasiInfoFragment.setTextColor(getResources().getColor(R.color.colorWhite_FFFFFF));
        }
    }
}
