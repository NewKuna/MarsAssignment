package com.example.demo.common.model;

import lombok.Getter;

@Getter
public enum FormType {
    SELF("Self"),
    EXPERT("Expert"),
    ;

    private final String type;

    FormType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
