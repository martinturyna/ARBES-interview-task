package com.phonecompany.pojo;

import java.time.LocalDateTime;
import java.util.Objects;

public record PhoneCall(String phoneNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {

    public PhoneCall {
        Objects.requireNonNull(phoneNumber);
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);
    }

}
