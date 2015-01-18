package com.rc.leatherback.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 3357474440893113325L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
