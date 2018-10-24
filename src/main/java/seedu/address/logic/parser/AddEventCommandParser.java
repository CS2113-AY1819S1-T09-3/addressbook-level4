package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import javax.swing.*;
import seedu.address.logic.commands.EventCommand.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Events.Description;
import seedu.address.model.Events.EventDate;
import seedu.address.model.Events.EventName;
import seedu.address.model.Events.Venue;
import seedu.address.model.Events.Event;

/**
 * Parses input command arguments and creates a new addLedgerCommand object
 */

public class AddEventCommandParser implements Parser<AddEventCommand> {
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_VENUE, PREFIX_DATE, PREFIX_DESCRIPTION);
        EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        EventDate date = ParserUtil.parserEventDate(argMultimap.getValue(PREFIX_DATE).get());
        Description description = ParserUtil.parseDecription(argMultimap.getValue(PREFIX_DESCRIPTION).get());

        Event event = new Event(name, venue, description,date);
        return new AddEventCommand(event);
    }

}
