package com.phonecompany.rules;

import com.phonecompany.pojo.PhoneCall;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class StandardChargeRateUpToFiveMinutesRule implements Rule {

    private BigDecimal price;

    /**
     * This method iterates over first given minutes of every record and apply the rate for minutes in spite of they are
     * in given interval or not.
     *
     * @param phoneCalls
     * @return phoneCalls List
     *
     */
    @Override
    public List<PhoneCall> applyRule(List<PhoneCall> phoneCalls) {

        price = new BigDecimal(0);

        for (PhoneCall pc : phoneCalls) {
            LocalDateTime currDateTime = pc.startDateTime();

            BigDecimal appliedRate = new BigDecimal(0);

            var minutesCount = 0;

            while(currDateTime.isBefore(pc.endDateTime()) && currDateTime.isBefore(pc.startDateTime().plusMinutes(4))) {

                appliedRate = (currDateTime.getHour() < INTERVAL_START_HOUR ||
                        currDateTime.getHour() >= INTERVAL_END_HOUR) ?
                        appliedRate.add(OUT_OF_INTERVAL_RATE) :
                        appliedRate.add(INSIDE_OF_INTERVAL_RATE);

                currDateTime = currDateTime.plusMinutes(1);
                minutesCount++;
            }

            price = price.add(appliedRate);

            log.info("Call to phone number {} has {} minutes before limit. Applied rate is {}",
                    pc.phoneNumber(), minutesCount, appliedRate);

        }

        return phoneCalls;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
