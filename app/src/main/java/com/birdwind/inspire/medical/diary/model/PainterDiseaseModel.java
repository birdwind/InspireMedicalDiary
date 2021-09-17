package com.birdwind.inspire.medical.diary.model;

import java.io.Serializable;

public class PainterDiseaseModel implements Serializable {
    private int Doctor;

    private int Disease;

    public int getDisease() {
        return Disease;
    }

    public void setDisease(int disease) {
        Disease = disease;
    }

    public int getDoctor() {
        return Doctor;
    }

    public void setDoctor(int doctor) {
        Doctor = doctor;
    }
}
