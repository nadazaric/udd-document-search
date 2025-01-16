package com.udd.back.feature_auth.dto;

public class UserDetailsDTO {

    private String name;
    private String username;

    public UserDetailsDTO() {}

    public UserDetailsDTO(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
