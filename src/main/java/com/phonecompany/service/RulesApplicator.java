package com.phonecompany.service;

import com.phonecompany.pojo.PhoneCall;
import com.phonecompany.rules.FifthAndFurtherMinuteRateRule;
import com.phonecompany.rules.MostCalledNumberRule;
import com.phonecompany.rules.Rule;
import com.phonecompany.rules.StandardChargeRateUpToFiveMinutesRule;

import java.math.BigDecimal;
import java.util.List;

public class RulesApplicator {

    public BigDecimal calculate(List<PhoneCall> phoneCalls) {

        var price = new BigDecimal(0);

        Rule mostCalledNumberRule = new MostCalledNumberRule();
        Rule standardChargeRateUpToFiveMinutesRule = new StandardChargeRateUpToFiveMinutesRule();
        Rule longerThanFiveMinutesCallRule = new FifthAndFurtherMinuteRateRule();

        List<PhoneCall> reducedList = mostCalledNumberRule.applyRule(phoneCalls);
        standardChargeRateUpToFiveMinutesRule.applyRule(reducedList);
        price = price.add(standardChargeRateUpToFiveMinutesRule.getPrice());
        longerThanFiveMinutesCallRule.applyRule(reducedList);
        price = price.add(longerThanFiveMinutesCallRule.getPrice());

        return price;
    }


}
