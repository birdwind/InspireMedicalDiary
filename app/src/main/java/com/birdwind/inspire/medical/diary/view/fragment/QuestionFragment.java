package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuestionBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.bumptech.glide.Glide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

/**
 * @Author BirdWind
 * @Description
 * @Date 2022-06-22 23:48
 */
public class QuestionFragment extends AbstractFragment<AbstractPresenter, FragmentQuestionBinding> {
    private int count;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentQuestionBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentQuestionBinding.inflate(inflater);
    }

    @Override
    public void addListener() {
        binding.btNextQuestionFragment.setOnClickListener(v -> {
            if (count == 3) {
                onBackPressedByActivity();
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("count", count + 1);
                QuestionFragment questionFragment = new QuestionFragment();
                questionFragment.setArguments(bundle);
                replaceFragment(questionFragment);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            count = bundle.getInt("count", 0);
        } else {
            count = 0;
        }
    }

    @Override
    public void initView() {
        switch (count) {
            case 0:
                initIntro();
                break;
            case 1:
                initAudio();
                Glide.with(context).load(R.mipmap.audio1)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_no_picture))
                    .into(binding.ivImageQuestionFragment);
                break;
            case 2:
                initAudio();
                Glide.with(context).load(R.mipmap.audio2)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_no_picture))
                    .into(binding.ivImageQuestionFragment);
                break;
            case 3:
                initDrawing();
                Glide.with(context).load(R.mipmap.drawing1)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_no_picture))
                    .into(binding.ivImageQuestionFragment);

        }
    }

    @Override
    public void doSomething() {

    }

    private void initIntro() {
        binding.tvIntroQuestionFragment.setVisibility(View.VISIBLE);
        binding.llToolQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setText("開始");
    }

    private void initAudio() {
        binding.tvTitleQuestionFragment.setVisibility(View.VISIBLE);
        binding.ivImageQuestionFragment.setVisibility(View.VISIBLE);
        binding.llToolQuestionFragment.setVisibility(View.VISIBLE);
        binding.tvCountdownQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setText("完成，下一步");
    }

    private void initDrawing() {
        binding.tvTitleQuestionFragment.setVisibility(View.VISIBLE);
        binding.ivImageQuestionFragment.setVisibility(View.VISIBLE);
        binding.llToolQuestionFragment.setVisibility(View.VISIBLE);
        binding.pvDrawingQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setText("完成，下一步");
        binding.tvTitleQuestionFragment.setText("請畫一個跟這張圖形狀一樣的圖片。");
    }

    private void initBoolean() {
        binding.tvTitleQuestionFragment.setVisibility(View.VISIBLE);
        binding.ivImageQuestionFragment.setVisibility(View.VISIBLE);
        binding.llToolQuestionFragment.setVisibility(View.VISIBLE);
        binding.pvDrawingQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setVisibility(View.VISIBLE);
        binding.btNextQuestionFragment.setText("完成，下一步");
    }
}
