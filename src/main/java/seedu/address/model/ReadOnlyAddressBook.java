package seedu.address.model;

import javafx.collections.ObservableList;

import javafx.collections.ObservableSet;
import seedu.address.model.item.Item;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.Events.Event;
import seedu.address.model.member.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Event> getEventList();

    ObservableList<Ledger> getLedgerList();
    ObservableList<Item> getItemList();

    ObservableSet<Ledger> getLedgerSet();

}
