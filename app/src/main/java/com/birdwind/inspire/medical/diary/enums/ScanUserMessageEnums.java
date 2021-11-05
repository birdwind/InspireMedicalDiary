package com.birdwind.inspire.medical.diary.enums;

/**
 * 0開頭為病患會看到的錯誤 1開頭為家屬會看到的錯誤 2開頭為醫生會看到的錯誤 3開頭判斷身分 4開頭為詢問
 */
public enum ScanUserMessageEnums {
    IS_DOCTOR("此人身分為醫生", "301"), IS_FAMILY("此人身分為家屬", "302"), IS_PAINTER("此人身分為病人", "303"), DOCTOR_ALREADY_ADD_PAINTER(
        "已在病人名單裡", "201"), HAVE_ANOTHER_DOCTOR("此病人已經有其他醫生負責", "201"), HAVE_DOCTOR("已經有負責的醫生",
            "001"), ADD_TO_PAINTER("是否要加為自己的病人", "400"), ADD_TO_FAMILY("是否要加為自己的家屬",
                "401"), ADD_TO_DOCTOR("是否要加為自己的醫生", "402"), ADD_TO_PROXY_PAINTER("是否要加為自己的病人(家屬代理)", "403");

    private String key;

    private String value;

    ScanUserMessageEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ScanUserMessageEnums parseEnumsByKey(String key) {
        for (ScanUserMessageEnums scanUserMessageEnums : ScanUserMessageEnums.values()) {
            if (scanUserMessageEnums.key.equals(key)) {
                return scanUserMessageEnums;
            }
        }
        return null;
    }

    public static ScanUserMessageEnums parseEnumsByValue(String value) {
        for (ScanUserMessageEnums scanUserMessageEnums : ScanUserMessageEnums.values()) {
            if (scanUserMessageEnums.value.equals(value)) {
                return scanUserMessageEnums;
            }
        }
        return null;
    }
}
