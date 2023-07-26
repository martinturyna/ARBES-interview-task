package com.phonecompany.billing;

import com.opencsv.exceptions.CsvValidationException;
import com.phonecompany.exception.ParseLineException;
import com.phonecompany.parser.PhoneLogParser;
import com.phonecompany.service.RulesApplicator;

import java.io.IOException;
import java.math.BigDecimal;


public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

    @Override
    public BigDecimal calculate(String phoneLog) {

        var phoneLogParser = new PhoneLogParser();
        var rulesApplicator = new RulesApplicator();

        try {
            return rulesApplicator.calculate(phoneLogParser.parse(phoneLog));
        } catch (CsvValidationException | IOException | ParseLineException e) {
            throw new RuntimeException(e);
        }

    }
}
