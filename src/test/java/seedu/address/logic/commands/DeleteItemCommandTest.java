package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalItems.getTypicalItemList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.item.DeleteItemCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Item;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteItemCommand}.
 */
public class DeleteItemCommandTest {

    private Model model = new ModelManager(getTypicalItemList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteItemCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(outOfBoundIndex);

        assertCommandFailure(deleteItemCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteItemCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitAddressBook();
        showNoItem(expectedModel);

        assertCommandSuccess(deleteItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getItemList().size());

        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(outOfBoundIndex);

        assertCommandFailure(deleteItemCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitAddressBook();

        // delete -> first item deleted
        deleteItemCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered item list to show all items
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteItemCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Item} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted item in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the item object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemDeleted() throws Exception {
        DeleteItemCommand deleteItemCommand = new DeleteItemCommand(INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showItemAtIndex(model, INDEX_SECOND_ITEM);
        Item itemToDelete = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second item in unfiltered item list / first item in filtered item list
        deleteItemCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered item list to show all items
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(itemToDelete, model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased()));
        // redo -> deletes same second item in unfiltered item list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteItemCommand deleteFirstCommand = new DeleteItemCommand(INDEX_FIRST_ITEM);
        DeleteItemCommand deleteSecondCommand = new DeleteItemCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteItemCommand deleteFirstCommandCopy = new DeleteItemCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoItem(Model model) {
        model.updateFilteredItemList(p -> false);

        assertTrue(model.getFilteredItemList().isEmpty());
    }
}
