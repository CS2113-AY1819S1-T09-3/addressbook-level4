package seedu.address.logic.parser.ledger;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ledger.AddLedgerCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

/**
 * Parses input command arguments and creates a new addLedgerCommand object
 */

public class AddLedgerCommandParser implements Parser<AddLedgerCommand> {

    private final Logger logger = LogsCenter.getLogger(AddLedgerCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddLedgerCommand
     * and returns an AddLedgerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddLedgerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) /*|| argMultimap.getPreamble().isEmpty()*/) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLedgerCommand.MESSAGE_USAGE));
        }


        DateLedger date = ParserUtil.parseDateLedger(argMultimap.getValue(PREFIX_DATE).get());
        //Account account = ParserUtil.parseBalance(Double.parseDouble(argMultimap.getValue(PREFIX_BALANCE).get()));

        Ledger ledger = new Ledger(date, new Account(0.0));
        return new AddLedgerCommand(ledger);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
