package com.arcade.dto;

public abstract class BaseDto {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "id=" + id +
                '}';
    }

}
