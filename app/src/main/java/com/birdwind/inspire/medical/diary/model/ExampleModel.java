package com.birdwind.inspire.medical.diary.model;

import com.birdwind.inspire.medical.diary.base.model.BaseModel;

public class ExampleModel implements BaseModel {

    private String url;
    private Boolean isCurrent;

    public ExampleModel(String url, Boolean isCurrent) {
        this.url = url;
        this.isCurrent = isCurrent;
    }

    public ExampleModel(String url) {
        this(url, false);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
