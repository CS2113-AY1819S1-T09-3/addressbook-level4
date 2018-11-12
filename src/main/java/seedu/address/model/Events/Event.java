package seedu.address.model.Events;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Presents the event.
 */
public class Event {
    private final EventName name;
    private final Venue venue;
    private final Description description;
    private final EventDate date;
    private final Set<Tag> tags = new HashSet<>();

    public Event(EventName name, Venue venue, Description description, EventDate date) {
        requireAllNonNull(name, venue, description, date);
        this.name = name;
        this.venue = venue;
        this.description = description;
        this.date = date;
        this.tags.addAll(tags);
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public EventName getEventName() { return name; }
    public Venue getVenue() { return venue; }
    public Description getDescription() { return description; }
    public EventDate getEventDate() { return date; }

    /**
     * @param otherEvent
     * @return to check whether the given event is the same as existing one.
     */
    public boolean isSameEvent(Event otherEvent) {
        if ( name.equals(otherEvent.name)&&date.equals(otherEvent.date)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, venue, description, date, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Venue: ")
                .append(getVenue())
                .append(", Description: ")
                .append(getDescription())
                .append(", Date: ")
                .append(getEventDate());
        return builder.toString();
    }
}

