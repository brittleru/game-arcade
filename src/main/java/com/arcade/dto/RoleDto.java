package com.arcade.dto;

import javax.validation.constraints.NotEmpty;

public class RoleDto extends BaseDto {

    private String name;

    @NotEmpty(message = "Name can't be empty")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                '}';
    }

}
