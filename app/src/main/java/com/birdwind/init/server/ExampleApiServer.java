package com.birdwind.init.server;

import com.birdwind.init.base.enums.BaseEnums;

import java.io.Serializable;

public enum ExampleApiServer implements BaseEnums {
    ICON_BADGE("iconbadges");

    ExampleApiServer(String url) {
        this.url = "api/index/" + url;
    }

    private String url;

    @Override
    public Serializable valueOf() {
        return url;
    }

    @Override
    public String valueOfName() {
        return url;
    }
}
