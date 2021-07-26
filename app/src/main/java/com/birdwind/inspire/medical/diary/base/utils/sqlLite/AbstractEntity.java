package com.birdwind.inspire.medical.diary.base.utils.sqlLite;

public abstract class AbstractEntity<T> {

    public abstract int getId();

    public abstract void parse(T object);

    public abstract T parse();
}
