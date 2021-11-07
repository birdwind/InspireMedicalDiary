package com.birdwind.inspire.medical.diary.view.adapter;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.base.utils.DateTimeFormatUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.model.response.RecordOrderResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.Objects;

public class RecordOrderAdapter extends BaseQuickAdapter<RecordOrderResponse.Response, BaseViewHolder> {

    private MediaPlayer mediaPlayer;

    public RecordOrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, RecordOrderResponse.Response response) {
        ImageView ivQuestionImage = baseViewHolder.getView(R.id.iv_image_record_order_fragment);
        CustomPicasso.getImageLoader(getContext()).load(response.getPhotoUrl())
            .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.ic_default_image)))
            .into(ivQuestionImage);

        baseViewHolder.setText(R.id.tv_question_record_order_fragment, response.getContent());
        baseViewHolder.setText(R.id.tv_time_record_order_fragment, DateTimeFormatUtils.dateFormat(response.getTimeC()));
    }

    public void startPlay(boolean isStart, int position, RecordOrderResponse.Response recordOrderItem) {
        View playView = getViewByPosition(position, R.id.ib_play_record_order_fragment);
        View stopView = getViewByPosition(position, R.id.ib_stop_play_record_order_fragment);
        assert playView != null;
        assert stopView != null;

        if (isStart) {
            if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
                playAudio(recordOrderItem.getVoiceFile(), position);
                playView.setVisibility(View.GONE);
                stopView.setVisibility(View.VISIBLE);
            }
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            playView.setVisibility(View.VISIBLE);
            stopView.setVisibility(View.GONE);
        }
    }

    private void playAudio(String url, int position) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();

        // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setAudioAttributes(
            new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                startPlay(false, position, null);
            });
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

}
