package com.phonecompany.rules;

import com.phonecompany.pojo.PhoneCall;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Slf4j
public class FifthAndFurtherMinuteRateRule implements Rule {

    private BigDecimal price;

    /**
     * This method takes every record and apply low rate for fifth and further minute if duration of call
     * is longer then 4 minutes.
     *
     * @param phoneCalls
     * @return
     */
    @Override
    public List<PhoneCall> applyRule(List<PhoneCall> phoneCalls) {

        price = new BigDecimal(0);

        for (PhoneCall pc : phoneCalls) {

            Duration diff = Duration.between(pc.startDateTime().plusMinutes(CALCULATION_LIMIT_MINUTE), pc.endDateTime());
            var minutesOverLimit = roundUp(diff);

            BigDecimal appliedRate = BigDecimal.ZERO;

            if (diff.toSeconds() > 0) {
                appliedRate = FIFTH_AND_FURTHER_MINUTE_RATE.multiply(new BigDecimal(minutesOverLimit));
                price = price.add(appliedRate);
            }

            log.info("Call to phone number {} has {} minutes over limit. Applied rate is {}",
                    pc.phoneNumber(), minutesOverLimit, appliedRate);

        }

        return phoneCalls;

    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

}
