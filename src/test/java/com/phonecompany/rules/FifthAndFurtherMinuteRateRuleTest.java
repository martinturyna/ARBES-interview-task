package com.phonecompany.rules;

import com.phonecompany.pojo.PhoneCall;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FifthAndFurtherMinuteRateRuleTest {

    @InjectMocks
    FifthAndFurtherMinuteRateRule fifthAndFurtherMinuteRateRule;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    public void setUp() { fifthAndFurtherMinuteRateRule = new FifthAndFurtherMinuteRateRule(); }

    @Test
    public void applyRuleTest_rateGreaterThanZero() {

        var phoneCalls = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:15:00", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:30:00", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);

        fifthAndFurtherMinuteRateRule.applyRule(phoneCalls);

        assertEquals(new BigDecimal("7.4"), fifthAndFurtherMinuteRateRule.getPrice());

    }

    @Test
    public void applyRuleTest_rateEqualsZero() {

        var phoneCalls = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:02:59", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:03:59", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);

        fifthAndFurtherMinuteRateRule.applyRule(phoneCalls);

        assertEquals(new BigDecimal(0), fifthAndFurtherMinuteRateRule.getPrice());

    }

    @Test
    public void applyRuleTest_rateGreaterThanZeroOverMidnight() {

        var phoneCalls = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 23:59:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 00:05:22", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:03:21", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);

        fifthAndFurtherMinuteRateRule.applyRule(phoneCalls);

        assertEquals(new BigDecimal("0.6"), fifthAndFurtherMinuteRateRule.getPrice());

    }

}