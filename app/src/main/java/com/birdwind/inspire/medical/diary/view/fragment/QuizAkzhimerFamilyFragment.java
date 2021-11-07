package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizAkzhimerBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuizAkzhimerFamilyFragment extends AbstractFragment<QuizContentPresenter, FragmentQuizAkzhimerBinding>
    implements QuizContentView, ToolbarCallback, View.OnClickListener {

    @Override
    public QuizContentPresenter createPresenter() {
        return new QuizContentPresenter(this);
    }

    @Override
    public FragmentQuizAkzhimerBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentQuizAkzhimerBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {}

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {}

    @Override
    public String setRightButtonText() {
        return getString(R.string.quiz_submit);
    }

    @Override
    public ToolbarCallback setToolbarCallback() {
        return this;
    }

    @Override
    public void clickTopBarRightTextButton(View view) {
        List<Integer> answers = new ArrayList<>();
        boolean isCanSubmit = true;
        int option1_Id = binding.comQuizQuizAkzhimerFragment.rgOption1QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option2_Id = binding.comQuizQuizAkzhimerFragment.rgOption2QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option3_Id = binding.comQuizQuizAkzhimerFragment.rgOption3QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option4_Id = binding.comQuizQuizAkzhimerFragment.rgOption4QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option5_Id = binding.comQuizQuizAkzhimerFragment.rgOption5QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option6_Id = binding.comQuizQuizAkzhimerFragment.rgOption6QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option7_Id = binding.comQuizQuizAkzhimerFragment.rgOption7QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option8_Id = binding.comQuizQuizAkzhimerFragment.rgOption8QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option9_Id = binding.comQuizQuizAkzhimerFragment.rgOption9QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option10_Id = binding.comQuizQuizAkzhimerFragment.rgOption10QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option11_Id = binding.comQuizQuizAkzhimerFragment.rgOption11QuizAkzhimerComponent.getCheckedRadioButtonId();
        int option12_Id = binding.comQuizQuizAkzhimerFragment.rgOption12QuizAkzhimerComponent.getCheckedRadioButtonId();

        int answer_1 = 0;
        int answer_2 = 0;
        int answer_3 = 0;
        int answer_4 = 0;
        int answer_5 = 0;
        int answer_6 = 0;
        int answer_7 = 0;
        int answer_8 = 0;
        int answer_9 = 0;
        int answer_10 = 0;
        int answer_11 = 0;
        int answer_12 = 0;

        if (option1_Id == binding.comQuizQuizAkzhimerFragment.rbOption1NoQuizAkzhimerComponent.getId()) {
            answer_1 = 0;
        } else if (option1_Id == binding.comQuizQuizAkzhimerFragment.rbOption1YesQuizAkzhimerComponent.getId()) {
            answer_1 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option2_Id == binding.comQuizQuizAkzhimerFragment.rbOption2NoQuizAkzhimerComponent.getId()) {
            answer_2 = 0;
        } else if (option2_Id == binding.comQuizQuizAkzhimerFragment.rbOption2YesQuizAkzhimerComponent.getId()) {
            answer_2 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option3_Id == binding.comQuizQuizAkzhimerFragment.rbOption3NoQuizAkzhimerComponent.getId()) {
            answer_3 = 0;
        } else if (option3_Id == binding.comQuizQuizAkzhimerFragment.rbOption3YesQuizAkzhimerComponent.getId()) {
            answer_3 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option4_Id == binding.comQuizQuizAkzhimerFragment.rbOption4NoQuizAkzhimerComponent.getId()) {
            answer_4 = 0;
        } else if (option4_Id == binding.comQuizQuizAkzhimerFragment.rbOption4YesQuizAkzhimerComponent.getId()) {
            answer_4 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.comQuizQuizAkzhimerFragment.rbOption5NoQuizAkzhimerComponent.getId()) {
            answer_5 = 0;
        } else if (option5_Id == binding.comQuizQuizAkzhimerFragment.rbOption5YesQuizAkzhimerComponent.getId()) {
            answer_5 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option6_Id == binding.comQuizQuizAkzhimerFragment.rbOption6NoQuizAkzhimerComponent.getId()) {
            answer_6 = 0;
        } else if (option6_Id == binding.comQuizQuizAkzhimerFragment.rbOption6YesQuizAkzhimerComponent.getId()) {
            answer_6 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option7_Id == binding.comQuizQuizAkzhimerFragment.rbOption7NoQuizAkzhimerComponent.getId()) {
            answer_7 = 0;
        } else if (option7_Id == binding.comQuizQuizAkzhimerFragment.rbOption7YesQuizAkzhimerComponent.getId()) {
            answer_7 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option8_Id == binding.comQuizQuizAkzhimerFragment.rbOption8NoQuizAkzhimerComponent.getId()) {
            answer_8 = 0;
        } else if (option8_Id == binding.comQuizQuizAkzhimerFragment.rbOption8YesQuizAkzhimerComponent.getId()) {
            answer_8 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option9_Id == binding.comQuizQuizAkzhimerFragment.rbOption9NoQuizAkzhimerComponent.getId()) {
            answer_9 = 0;
        } else if (option9_Id == binding.comQuizQuizAkzhimerFragment.rbOption9YesQuizAkzhimerComponent.getId()) {
            answer_9 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option10_Id == binding.comQuizQuizAkzhimerFragment.rbOption10NoQuizAkzhimerComponent.getId()) {
            answer_10 = 0;
        } else if (option10_Id == binding.comQuizQuizAkzhimerFragment.rbOption10YesQuizAkzhimerComponent.getId()) {
            answer_10 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option11_Id == binding.comQuizQuizAkzhimerFragment.rbOption11NoQuizAkzhimerComponent.getId()) {
            answer_11 = 0;
        } else if (option11_Id == binding.comQuizQuizAkzhimerFragment.rbOption11YesQuizAkzhimerComponent.getId()) {
            answer_11 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option12_Id == binding.comQuizQuizAkzhimerFragment.rbOption12NoQuizAkzhimerComponent.getId()) {
            answer_12 = 0;
        } else if (option12_Id == binding.comQuizQuizAkzhimerFragment.rbOption12YesQuizAkzhimerComponent.getId()) {
            answer_12 = 1;
        } else {
            isCanSubmit = false;
        }
        answers.add(answer_1);
        answers.add(answer_2);
        answers.add(answer_3);
        answers.add(answer_4);
        answers.add(answer_5);
        answers.add(answer_6);
        answers.add(answer_7);
        answers.add(answer_8);
        answers.add(answer_9);
        answers.add(answer_10);
        answers.add(answer_11);
        answers.add(answer_12);
        if (isCanSubmit) {
            presenter.submit(answers, true);
        } else {
            showToast("尚有題目還沒填寫");
        }
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void submitSuccess(boolean isSuccess, String msg) {
        if (isSuccess) {
            if(App.userModel.isProxy()){
                ((FamilyMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_3);
            }else{
                ((FamilyMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_2);
            }
        } else {
            showDialog(getString(R.string.common_dialog_title), msg, null);
        }
    }
}
