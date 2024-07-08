package com.example.demo.common.model;

import lombok.Getter;

@Getter
public enum ElementType {
    FORM("form"),
    SECTION("section"),
    TEXT_INPUT("text_input"),
    RADIO_BUTTON("radio_button"),
    MULTIPLE_CHOICE("multiple_choice"),
    ;

    private final String type;

    ElementType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
