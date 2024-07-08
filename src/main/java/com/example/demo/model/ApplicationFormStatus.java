package com.example.demo.model;

import lombok.Getter;

@Getter
public enum ApplicationFormStatus {
    PENDING("pending"),
    CANCEL("cancel"),
    APPROVED("approved"),
    REJECTED("rejected");
    ;

    private final String type;

    ApplicationFormStatus(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
