package com.birdwind.inspire.medical.diary.view.popupWindow;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.databinding.PopupMyDeviceBinding;
import com.birdwind.inspire.medical.diary.view.customer.smartPopupWindow.SmartPopupWindow;
import com.birdwind.inspire.medical.diary.view.popupWindow.callback.MyDeviceCallback;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MyDevicePopupWindow extends SmartPopupWindow implements View.OnClickListener {

    private View view;

    private PopupMyDeviceBinding binding;

    private MyDeviceCallback myDeviceCallback;

//    private DeviceResponse.Response device;

    private SmartPopupWindow smartPopupWindow;

    public MyDevicePopupWindow(Context context, MyDeviceCallback myDeviceCallback) {
        super(context);
        setFocusable(true);
        view = LayoutInflater.from(context).inflate(R.layout.popup_my_device, null, false);
        binding = PopupMyDeviceBinding.bind(view);
        binding.btLogoutMyDevicePopup.setOnClickListener(this);
        binding.btRemoveMyDevicePopup.setOnClickListener(this);
        this.myDeviceCallback = myDeviceCallback;
        initPopupWindow(context);
    }

    private void initPopupWindow(Context context) {
        if (smartPopupWindow == null) {
            smartPopupWindow = Builder.build((Activity) context, view).setOutsideTouchDismiss(true).createPopupWindow();
            smartPopupWindow.setWindowLayoutMode(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

//    public void show(View anchor, DeviceResponse.Response device) {
//        if (!smartPopupWindow.isShowing()) {
//            this.device = device;
//            if (device.get瀏覽器().equals("APP")) {
//                binding.btLogoutMyDevicePopup.setVisibility(View.GONE);
//            } else {
//                binding.btLogoutMyDevicePopup.setVisibility(View.VISIBLE);
//            }
//            smartPopupWindow.showAtAnchorView(anchor, VerticalPosition.BELOW, HorizontalPosition.ALIGN_RIGHT);
//        }
//    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == binding.btRemoveMyDevicePopup.getId()) {
//            smartPopupWindow.dismiss();
//            myDeviceCallback.onClickRemove(device);
//        } else if (v.getId() == binding.btLogoutMyDevicePopup.getId()) {
//            smartPopupWindow.dismiss();
//            myDeviceCallback.onClickLogout(device);
//        }
    }
}
