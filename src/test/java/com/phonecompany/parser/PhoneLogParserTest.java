package com.phonecompany.parser;

import com.phonecompany.exception.ParseLineException;
import com.phonecompany.exception.PhoneNumberFormatException;
import com.phonecompany.pojo.PhoneCall;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PhoneLogParserTest {

    @InjectMocks
    PhoneLogParser phoneLogParser;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    public void setUp() {
        phoneLogParser = new PhoneLogParser();
    }

    @Test
    public void testParse_ValidPhoneLog_ParsedCorrectly() throws Exception {
        String phoneLog = "421234567890,10-07-2023 15:00:00,10-07-2023 15:15:00\n421234567891,10-07-2023 16:00:00,10-07-2023 16:30:00";

        var phoneCallsActual = phoneLogParser.parse(phoneLog);
        var phoneCallsExpected = new ArrayList<>();

        var pc1 = new PhoneCall("421234567890",
                LocalDateTime.parse("10-07-2023 15:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 15:15:00", dateTimeFormatter));

        var pc2 = new PhoneCall("421234567891",
                LocalDateTime.parse("10-07-2023 16:00:00", dateTimeFormatter),
                LocalDateTime.parse("10-07-2023 16:30:00", dateTimeFormatter));

        phoneCallsExpected.add(pc1);
        phoneCallsExpected.add(pc2);

        assertEquals(phoneCallsExpected, phoneCallsActual);
    }

    @Test
    public void testParse_IncorrectDateTimeFormat_DateTimeParseException() {
        var phoneLog = "421234567890,15:00:00 10-07-2023,15:15:00 10-07-2023";
        assertThrows(DateTimeParseException.class, () -> phoneLogParser.parse(phoneLog));
    }

    @Test
    public void testParse_WrongFileStructure_ParseLineException() {
        var phoneLog = "421234567890,15:00:00 10-07-2023";
        assertThrows(ParseLineException.class, () -> phoneLogParser.parse(phoneLog));
    }

    @Test
    public void testParse_WrongPhoneNumberFormat_PhoneNumberFormatException() {
        var phoneLog = "42011122233,15:00:00 10-07-2023,15:15:00 10-07-2023";
        assertThrows(PhoneNumberFormatException.class, () -> phoneLogParser.parse(phoneLog));
    }


}
