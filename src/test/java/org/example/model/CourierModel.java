package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourierModel {

    private String login;
    private String password;
    private String firstName;
    private String id;

    public CourierModel(){
    }

    public CourierModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierModel(String id) {
        this.id = id;
    }
}