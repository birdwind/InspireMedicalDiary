package com.birdwind.inspire.medical.diary.utils;

import android.content.Context;

import androidx.fragment.app.Fragment;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.EasyImage;

public class EasyImageUtils {

    private EasyImage easyImage;

    public EasyImageUtils(Context context, String title) {
        easyImage = new EasyImage.Builder(context).setChooserTitle(title).setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .setFolderName("InspireDiary").setCopyImagesToPublicGalleryFolder(false).build();
    }

    public void showEasyImage(Fragment fragment) {
        easyImage.openChooser(fragment);
    }

    public EasyImage getEasyImage() {
        return easyImage;
    }
}
