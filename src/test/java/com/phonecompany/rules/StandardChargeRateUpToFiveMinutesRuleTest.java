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
public class StandardChargeRateUpToFiveMinutesRuleTest {

    @InjectMocks
    StandardChargeRateUpToFiveMinutesRule standardChargeRateUpToFiveMinutesRule;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    public void setUp() { standardChargeRateUpToFiveMinutesRule = new StandardChargeRateUpToFiveMinutesRule(); }


    @Test
    public void applyRuleTest_TwoPhoneCallsWithFullRateOneInIntervalOneOutOfInterval() {

        var phoneCalls = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:06:59", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("10-07-2023 16:05:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:15:23", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);

        standardChargeRateUpToFiveMinutesRule.applyRule(phoneCalls);

        assertEquals(new BigDecimal("6.0"), standardChargeRateUpToFiveMinutesRule.getPrice());

    }

    @Test
    public void applyRuleTest_TwoPhoneCallsOneOverIntervalBoundaryOneOutOfInterval() {

        var phoneCalls = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 15:58:30", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:10:30", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("10-07-2023 17:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 17:15:59", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);

        standardChargeRateUpToFiveMinutesRule.applyRule(phoneCalls);

        assertEquals(new BigDecimal("5.0"), standardChargeRateUpToFiveMinutesRule.getPrice());

    }





}