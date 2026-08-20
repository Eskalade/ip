public enum Command {
    TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, BYE, UNKNOWN;

    public static Command fromString(String str) {
        if (str == null) {
            return UNKNOWN;
        }
        try {
            return Command.valueOf(str.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}