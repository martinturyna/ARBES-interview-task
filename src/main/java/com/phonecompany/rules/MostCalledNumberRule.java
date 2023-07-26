package com.phonecompany.rules;

import com.phonecompany.pojo.PhoneCall;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class MostCalledNumberRule implements Rule {

    private BigDecimal price;


    /**
     * We reduce the list of phone calls -> we do not charge the most used phone number
     * */

    /**
     * First, we find frequency of appearance of every number in phoneCalls list. After that we create a copy
     * of this list containing every record except records to the most frequently called one.
     *
     * @param phoneCalls
     * @return Reduced copy of phone calls.
     *
     */
    @Override
    public List<PhoneCall> applyRule(List<PhoneCall> phoneCalls) {

        var newPhoneCalls = new ArrayList<PhoneCall>();
        var phoneCallsHistogram = new HashMap<String, Integer>();

        for (PhoneCall pc : phoneCalls) {
            phoneCallsHistogram.merge(pc.phoneNumber(), 1, Integer::sum);
        }

        var mostUsedPhoneNumber = getMostUsedNumber(phoneCallsHistogram);

        phoneCalls.stream()
                .filter(item -> !Objects.equals(item.phoneNumber(), mostUsedPhoneNumber))
                .forEach(newPhoneCalls::add);

        return newPhoneCalls;
    }

    private String getMostUsedNumber(Map<String, Integer> hm) {

        var maxVal = Integer.MIN_VALUE;
        var maxValPhoneNumber = "";

        for (Map.Entry<String, Integer> entry : hm.entrySet()) {

            if (entry.getValue() > maxVal) {

                maxValPhoneNumber = entry.getKey();
                maxVal = entry.getValue();

            } else if (entry.getValue().equals(maxVal)) {

                var res = maxValPhoneNumber.compareTo(entry.getKey());
                maxValPhoneNumber = (res > 0) ? maxValPhoneNumber : entry.getKey();

            }
        }

        log.info("The most frequent number is {} used {} times", maxValPhoneNumber, maxVal);

        return maxValPhoneNumber;

    }

    public BigDecimal getPrice() {
        return price;
    }
}
