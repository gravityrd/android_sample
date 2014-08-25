package com.gravityrd.jofogas.model;

public class Category {
    public final Integer imageResourceId;
    public final String name;
    public final String pos;

    Category(String name, Integer imageResourceId, String pos) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.pos = pos;
    }
}
