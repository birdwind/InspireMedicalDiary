package com.birdwind.inspire.medical.diary.enums;

import androidx.annotation.StringRes;

import com.birdwind.inspire.medical.diary.R;

public enum SettingFunctionEnums {
    PERSONAL_INFO(0, R.string.setting_basic_info), LOGOUT(1, R.string.common_logout), FEEDBACK(3, R.string.setting_feedback);

    private final int value;

    private final int nameId;

    SettingFunctionEnums(int value, @StringRes int nameId) {
        this.value = value;
        this.nameId = nameId;
    }

    public static SettingFunctionEnums parseEnumsByType(int value) {
        for (SettingFunctionEnums settingFunctionEnums : SettingFunctionEnums.values()) {
            if (settingFunctionEnums.value == value) {
                return settingFunctionEnums;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public int getNameId() {
        return nameId;
    }
}
