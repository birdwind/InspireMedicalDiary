package com.diary.init.base.utils.sqlLite;

public abstract class AbstractEntity<T> {

    public abstract int getId();

    public abstract void parse(T object);

    public abstract T parse();
}
