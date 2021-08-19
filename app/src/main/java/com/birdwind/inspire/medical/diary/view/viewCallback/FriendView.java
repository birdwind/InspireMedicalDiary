package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.FriendResponse;

import java.util.List;

public interface FriendView extends BaseCustomView {
    void onGetFriends(List<FriendResponse.Response> friendResponse);
}
