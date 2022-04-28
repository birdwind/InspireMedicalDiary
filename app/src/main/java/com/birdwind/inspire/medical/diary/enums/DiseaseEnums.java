package com.birdwind.inspire.medical.diary.enums;

public enum DiseaseEnums {
    NOT_SET(0), HEADACHE(3), ALZHEIMER(1), PERKINS(2);

    private int type;

    DiseaseEnums(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
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
