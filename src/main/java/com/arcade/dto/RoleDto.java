package com.arcade.dto;

public class RoleDto extends BaseDto {

    private String name;

    public RoleDto(String name) {
        this.name = name;
    }

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
