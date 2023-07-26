package com.phonecompany.rules;

import com.phonecompany.pojo.PhoneCall;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class MostCalledNumberRuleTest {

    @InjectMocks
    MostCalledNumberRule mostCalledNumberRule;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    public void setUp() { mostCalledNumberRule = new MostCalledNumberRule(); }

    @Test
    public void applyRuleTest_OneMostCalledNumber() {

        var phoneCalls = new ArrayList<PhoneCall>();
        var phoneCallsExpected = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("411111111111",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:15:00", dateTimeFormatter));

        var pc2 = new PhoneCall("411111111111",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:30:00", dateTimeFormatter));

        var pc3 = new PhoneCall("400000000000",
                LocalDateTime.parse("11-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 16:30:00", dateTimeFormatter));

        var pc4 = new PhoneCall("422222222222",
                LocalDateTime.parse("11-07-2023 17:00:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 17:30:00", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);
        phoneCalls.add(pc3);
        phoneCalls.add(pc4);

        phoneCallsExpected.add(pc3);
        phoneCallsExpected.add(pc4);

        assertEquals(phoneCallsExpected, mostCalledNumberRule.applyRule(phoneCalls));

    }

    @Test
    public void applyRuleTest_TwoMostCalledNumbers_ChooseArithmeticalHighest() {

        var phoneCalls = new ArrayList<PhoneCall>();
        var phoneCallsExpected = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("411111111111",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:15:00", dateTimeFormatter));

        var pc2 = new PhoneCall("411111111111",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:30:00", dateTimeFormatter));

        var pc3 = new PhoneCall("400000000000",
                LocalDateTime.parse("11-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 16:30:00", dateTimeFormatter));

        var pc4 = new PhoneCall("422222222222",
                LocalDateTime.parse("11-07-2023 17:00:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 17:30:00", dateTimeFormatter));

        var pc5 = new PhoneCall("422222222222",
                LocalDateTime.parse("12-07-2023 17:00:00", dateTimeFormatter),
                LocalDateTime.parse("12-07-2023 17:30:00", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);
        phoneCalls.add(pc3);
        phoneCalls.add(pc4);
        phoneCalls.add(pc5);

        phoneCallsExpected.add(pc1);
        phoneCallsExpected.add(pc2);
        phoneCallsExpected.add(pc3);

        assertEquals(phoneCallsExpected, mostCalledNumberRule.applyRule(phoneCalls));
    }

    @Test
    public void applyRuleTest_OneCalledNumberOnly() {

        var phoneCalls = new ArrayList<PhoneCall>();
        var phoneCallsExpected = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("411111111111",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:15:00", dateTimeFormatter));

        var pc2 = new PhoneCall("411111111111",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:30:00", dateTimeFormatter));

        var pc3 = new PhoneCall("411111111111",
                LocalDateTime.parse("11-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 16:30:00", dateTimeFormatter));


        phoneCalls.add(pc1);
        phoneCalls.add(pc2);
        phoneCalls.add(pc3);

        assertEquals(phoneCallsExpected, mostCalledNumberRule.applyRule(phoneCalls));
    }



}