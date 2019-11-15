import java.util.Arrays;
import java.util.List;

public class WhitespaceAndPunctuations {

    private static final List<Character> Punctuations = Arrays.asList('(');
    private static final List<Character> FullStopPunctuations = Arrays.asList('.', ':', ',', '!', ')', '?');

    public static String normalizeString(String input) {
        StringBuilder builder = new StringBuilder();
        String normalizedInput = normalizeInput(input);

        boolean doubleQuoteEven = false;
        char prevChar, nextChar;
        int inputLength = normalizedInput.length();
        for (int index = 0; index < inputLength; index++) {
            char currentChar = normalizedInput.charAt(index);

            // If currentChar is a full stop punctuation, remove preceding whitespace and add following whitespace
            if (FullStopPunctuations.contains(currentChar)) {

                // Remove preceding whitespace
                if (index > 0) {
                    prevChar = normalizedInput.charAt(index - 1);
                    if (Character.isWhitespace(prevChar)) {
                        builder.deleteCharAt(builder.length() - 1);
                    }
                }

                // Append character
                builder.append(currentChar);

                // Add following whitespace if needed
                if (index < inputLength - 1) {
                    nextChar = normalizedInput.charAt(index + 1);
                    if (!FullStopPunctuations.contains(nextChar)) {
                        builder.append(" ");
                    }
                }
            }

            // Else if currentChar is a normal punctuation, add preceding whitespace
            else if (Punctuations.contains(currentChar)) {

                // Add preceding whitespace if needed
                if (index > 0) {
                    prevChar = normalizedInput.charAt(index - 1);
                    if (!Character.isWhitespace(prevChar)) {
                        builder.append(" ");
                    }
                }

                // Append character
                builder.append(currentChar);
            }

            // Whitespace character, don't append it if prev is a punctuation character
            else if (Character.isWhitespace(currentChar)) {
                if (index > 0) {
                    prevChar = normalizedInput.charAt(index - 1);
                    if (!Punctuations.contains(prevChar) && !FullStopPunctuations.contains(prevChar)) {
                        builder.append(currentChar);
                    }
                }
            }

            // If currentChar is a double quote
            else if (currentChar == '"') {

                // If it is the first quote (opening quote), add preceding whitespace and remove following whitespace
                if (!doubleQuoteEven) {

                    // Add preceding whitespace if needed
                    if (index > 0) {
                        prevChar = normalizedInput.charAt(index - 1);
                        if (!Character.isWhitespace(prevChar)) {
                            builder.append(" ");
                        }
                    }

                    // Append currentChar
                    builder.append(currentChar);

                    // Remove following whitespace
                    if (index < inputLength - 1) {
                        nextChar = normalizedInput.charAt(index + 1);
                        if (Character.isWhitespace(nextChar)) {
                            index += 1;
                        }
                    }

                    doubleQuoteEven = true;
                }

                // If it is the second quote (closing quote), remove preceding whitespace and add following whitespace
                else {

                    // Remove preceding whitespace if needed
                    if (index > 0) {
                        prevChar = normalizedInput.charAt(index - 1);
                        if (Character.isWhitespace(prevChar)) {
                            builder.deleteCharAt(builder.length() - 1);
                        }
                    }

                    // Append currentChar
                    builder.append(currentChar);

                    // Add following character
                    if (index < inputLength - 1) {
                        nextChar = normalizedInput.charAt(index + 1);
                        if (!Character.isWhitespace(nextChar) && !Punctuations.contains(nextChar) && !FullStopPunctuations.contains(nextChar)) {
                            builder.append(" ");
                        }
                    }

                    doubleQuoteEven = false;
                }
            }

            // All others, just append it
            else {
                builder.append(currentChar);
            }
        }

        return normalizeInput(builder.toString());
    }

    private static String normalizeInput(String input) {
        String newInput = input.trim();
        return newInput.replaceAll("\\s+", " ");
    }
}
