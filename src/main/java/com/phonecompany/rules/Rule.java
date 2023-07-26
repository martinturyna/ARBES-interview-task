package com.phonecompany.rules;

import com.phonecompany.pojo.PhoneCall;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public interface Rule {

    Integer CALCULATION_LIMIT_MINUTE = 4;
    BigDecimal OUT_OF_INTERVAL_RATE = new BigDecimal("0.5");
    BigDecimal INSIDE_OF_INTERVAL_RATE = new BigDecimal("1.0");
    Integer INTERVAL_START_HOUR = 8;
    Integer INTERVAL_END_HOUR = 16;
    BigDecimal FIFTH_AND_FURTHER_MINUTE_RATE = new BigDecimal("0.2");

    List<PhoneCall> applyRule(List<PhoneCall> phoneCalls);
    BigDecimal getPrice();

    default long roundUp(Duration duration) {

        var durationInMinutes = duration.toMinutes();



        if (duration.toSeconds() % 60 > 0) {
            durationInMinutes++;
        }

        return (durationInMinutes >= 0) ? durationInMinutes : 0;
    }

}
