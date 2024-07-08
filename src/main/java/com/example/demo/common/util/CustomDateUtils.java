package com.example.demo.common.util;

import com.example.demo.common.exception.CommonException;
import com.example.demo.common.model.Status;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class CustomDateUtils {

    public static Date parseDate(String dateInString, String... parsePatterns) throws CommonException {
        try {
            return DateUtils.parseDate(dateInString, parsePatterns);
        } catch (ParseException e) {
            throw new CommonException(Status.INTERNAL_SERVER_ERROR, String.format("CustomDateUtils - ParseDate Failed Date:%s", dateInString), e);
        }
    }

}
