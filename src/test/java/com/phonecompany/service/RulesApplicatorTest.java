package com.phonecompany.service;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class RulesApplicatorTest {

    @InjectMocks
    RulesApplicator rulesApplicator;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    public void setUp() { rulesApplicator = new RulesApplicator(); }

    @Test
    public void calculateTest_phoneCallsList10items() {

        var phoneCalls = new ArrayList<PhoneCall>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:06:59", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("11-07-2023 16:05:00", dateTimeFormatter),
                LocalDateTime.parse("11-07-2023 16:15:23", dateTimeFormatter));

        var pc3 = new PhoneCall("421234567892",
                LocalDateTime.parse("12-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("12-07-2023 15:06:59", dateTimeFormatter));

        var pc4 = new PhoneCall("421234567893",
                LocalDateTime.parse("13-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("13-07-2023 15:06:59", dateTimeFormatter));

        phoneCalls.add(pc1);
        phoneCalls.add(pc2);
        phoneCalls.add(pc3);
        phoneCalls.add(pc4);


        assertEquals(new BigDecimal("12.6"), rulesApplicator.calculate(phoneCalls));

    }




}