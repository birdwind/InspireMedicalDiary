package com.birdwind.inspire.medical.diary.enums;

import androidx.annotation.StringRes;

import com.birdwind.inspire.medical.diary.R;

public enum DiseaseEnums {
    NOT_SET(0, R.string.painter_dialog_not_set), HEADACHE(3, R.string.painter_dialog_headache), ALZHEIMER(1,
        R.string.painter_dialog_alzheimer), PERKINS(2, R.string.painter_dialog_perkins);

    private int type;

    private @StringRes int diseaseNameId;

    DiseaseEnums(int type, @StringRes int diseaseNameId) {
        this.type = type;
        this.diseaseNameId = diseaseNameId;
    }

    public int getType() {
        return type;
    }

    public int getDiseaseNameId() {
        return diseaseNameId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static DiseaseEnums parseEnumsByType(int type) {
        for (DiseaseEnums diseaseEnums : DiseaseEnums.values()) {
            if (diseaseEnums.type == type) {
                return diseaseEnums;
            }
        }
        return null;
    }
}
