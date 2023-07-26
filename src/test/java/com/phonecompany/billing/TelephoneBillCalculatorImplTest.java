package com.phonecompany.billing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class TelephoneBillCalculatorImplTest {

    @InjectMocks
    TelephoneBillCalculatorImpl telephoneBillCalculator;

    @BeforeEach
    public void setUp() { telephoneBillCalculator = new TelephoneBillCalculatorImpl(); }

    @Test
    public void calculateTest_validPhoneLog() {

        String phoneLoge =
        """
        420774555455,01-07-2023 10:00:00,01-07-2023 10:15:35
        420772554980,01-07-2023 11:45:23,01-07-2023 11:50:45
        420774555800,02-07-2023 12:30:11,02-07-2023 12:45:35
        420772554785,02-07-2023 13:00:12,02-07-2023 13:10:38
        420774555475,03-07-2023 14:25:05,03-07-2023 14:45:18
        420772554676,03-07-2023 15:00:02,03-07-2023 15:15:07
        420774555123,04-07-2023 16:30:11,04-07-2023 16:45:33
        420772554954,04-07-2023 17:10:03,04-07-2023 17:25:06
        420774555422,05-07-2023 18:20:22,05-07-2023 18:30:54
        420772554675,05-07-2023 19:00:15,05-07-2023 19:15:30
        420774555111,06-07-2023 20:30:10,06-07-2023 20:40:40
        420772554777,06-07-2023 21:00:14,06-07-2023 21:15:38
        420774555999,07-07-2023 22:00:16,07-07-2023 22:15:42
        420772554888,07-07-2023 23:00:20,07-07-2023 23:15:44
        420774555222,08-07-2023 09:00:25,08-07-2023 09:15:50
        420772554333,08-07-2023 10:00:30,08-07-2023 10:15:55
        420774555333,09-07-2023 11:00:35,09-07-2023 11:15:58
        420774555455,09-07-2023 12:00:40,09-07-2023 12:15:02""";

        assertEquals(new BigDecimal("82.4"), telephoneBillCalculator.calculate(phoneLoge));

    }


}