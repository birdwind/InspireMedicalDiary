package com.birdwind.inspire.medical.diary.enums;

public enum IdentityEnums {
    PAINTER(0), FAMILY(1), DOCTOR(2), SYSTEM(4);

    private int type;

    IdentityEnums(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static IdentityEnums parseEnumsByType(int type) {
        for (IdentityEnums identityEnums : IdentityEnums.values()) {
            if (identityEnums.type == type) {
                return identityEnums;
            }
        }
        return null;
    }
}
