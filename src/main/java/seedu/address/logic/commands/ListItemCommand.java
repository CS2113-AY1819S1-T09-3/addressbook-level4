package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListItemCommand extends Command {

    public static final String COMMAND_WORD = "listItems";
    public static final String COMMAND_ALIAS = "lI";

    public static final String MESSAGE_SUCCESS = "Listed all items";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}