package com.phonecompany.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.phonecompany.exception.ParseLineException;
import com.phonecompany.exception.PhoneNumberFormatException;
import com.phonecompany.pojo.PhoneCall;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PhoneLogParser {

    /*  Simple regex pattern matching any phone number containing
        12 digits. Pattern can be changed and improved to match
        Any format of international numbers.*/
    private static final Pattern universalPattern = Pattern.compile("^\\d{12}$");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public List<PhoneCall> parse(String phoneLog) throws CsvValidationException, IOException, ParseLineException {

        var phoneCalls = new ArrayList<PhoneCall>();

        try {

            var csvReader = new CSVReader(new StringReader(phoneLog));
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                phoneCalls.add(parseLine(nextRecord));
            }

        }
        catch (Exception e) {
            log.error("\n{}", phoneLog, e);
            throw e;
        }

        return phoneCalls;
    }

    private PhoneCall parseLine(String[] line) throws ParseLineException {

        if (line.length != 3) {
            throw new ParseLineException("PhoneLog parsing failed - Wrong file structure");
        }

        return new PhoneCall(
                parseFromStringToPhoneNumber(line[0]),
                parseFromStringToLocalDateTime(line[1]),
                parseFromStringToLocalDateTime(line[2]));
    }

    private String parseFromStringToPhoneNumber(String s) {

        Matcher matcher = universalPattern.matcher(s);

        if (!matcher.matches()) {
            throw new PhoneNumberFormatException("Phone number parsing failed - is not valid phone number");
        }

        return s;
    }

    private LocalDateTime parseFromStringToLocalDateTime(String s) {
        try {
            return LocalDateTime.parse(s, dateTimeFormatter);
        }
        catch(DateTimeParseException e) {
            log.error("{}", s, e);
            throw e;
        }
    }

}
