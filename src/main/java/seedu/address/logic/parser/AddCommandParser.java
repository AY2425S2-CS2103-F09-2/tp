package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_PARENTPHONE, PREFIX_EMAIL,
                        PREFIX_TAG);

        // Check for missing compulsory prefixes
        StringBuilder missingPrefixes = new StringBuilder();
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            missingPrefixes.append(PREFIX_NAME).append(" ");
        }
        if (argMultimap.getValue(PREFIX_PHONE).isEmpty()) {
            missingPrefixes.append(PREFIX_PHONE).append(" ");
        }
        if (argMultimap.getValue(PREFIX_PARENTPHONE).isEmpty()) {
            missingPrefixes.append(PREFIX_PARENTPHONE).append(" ");
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isEmpty()) {
            missingPrefixes.append(PREFIX_EMAIL).append(" ");
        }

        if (!missingPrefixes.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            getMissingPrefixesMessage(missingPrefixes.toString().trim(),
                                    AddCommand.MESSAGE_USAGE)));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_PARENTPHONE, PREFIX_EMAIL);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Phone parentPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PARENTPHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, parentPhone, email, tagList);
        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns a formatted error message indicating which prefixes are missing.
     */
    private static String getMissingPrefixesMessage(String missingPrefixes, String usageMessage) {
        if (missingPrefixes.isEmpty()) {
            return usageMessage;
        }
        return "Missing the following required fields: " + missingPrefixes + "\n" + usageMessage;
    }
}
