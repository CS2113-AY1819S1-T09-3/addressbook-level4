package seedu.address.logic.parser.event;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javax.swing.*;
import seedu.address.logic.commands.EventCommand.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Events.Description;
import seedu.address.model.Events.EventDate;
import seedu.address.model.Events.EventName;
import seedu.address.model.Events.Venue;
import seedu.address.model.Events.Event;

/**
 * Parses input command arguments and creates a new addEventCommand object
 */

public class AddEventCommandParser implements Parser<AddEventCommand> {
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_VENUE, PREFIX_DESCRIPTION, PREFIX_DATE);
        EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        EventDate date = ParserUtil.parserEventDate(argMultimap.getValue(PREFIX_DATE).get());


        Event event = new Event(name, venue, description,date);
        return new AddEventCommand(event);
    }

}
