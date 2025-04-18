package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Phone parentPhone;
    private final Email email;

    private final Set<Tag> tags = new HashSet<>();
    private String note = "";

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Phone parentPhone, Email email, Set<Tag> tags) {
        requireAllNonNull(name, phone, parentPhone, email, tags);
        this.name = name;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.email = email;
        this.tags.addAll(tags);
    }

    /**
     * Person Constructor with Note.
     */
    public Person(Name name, Phone phone, Phone parentPhone, Email email, Set<Tag> tags, String note) {
        requireAllNonNull(name, phone, parentPhone, email, tags);
        this.name = name;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.email = email;
        this.tags.addAll(tags);
        this.note = note;
    }

    /**
     * Creates and returns a copy of the current Person.
     */
    public Person copy() {
        Set<Tag> copiedTags = new HashSet<>();
        for (Tag tag : this.tags) {
            copiedTags.add(new Tag(tag.tagName));
        }
        return new Person(new Name(this.name.fullName), new Phone(this.phone.value),
                new Phone(this.parentPhone.value), new Email(this.email.value), copiedTags, this.note);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Phone getParentPhone() {
        return parentPhone;
    }

    public Email getEmail() {
        return email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && parentPhone.equals(otherPerson.parentPhone)
                && email.equals(otherPerson.email)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, parentPhone, email, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("parentPhone", parentPhone)
                .add("email", email)
                .add("tags", tags)
                .toString();
    }
}
