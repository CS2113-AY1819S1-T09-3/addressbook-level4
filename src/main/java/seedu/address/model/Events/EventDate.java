package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Date {
    public final String ThisDate;
    public static final String DATE_VALIDATION_REGEX =  "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])$";
    public static final String MESSAGE_DATE =
            "Date should only contain alphanumeric characters and three slashes, and it should not be blank";
    public Date (String date){
        requireNonNull(date);
        checkArgument(CheckValid(date), MESSAGE_DATE);
        ThisDate= date;
    }
    public static boolean CheckValid(String date){
        return date.matches(DATE_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisDate;
    }

}
