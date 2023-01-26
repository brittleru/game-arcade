package com.arcade.dto;

public abstract class BaseDto {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "id=" + id +
                '}';
    }

}
