package nutrisoy.ui;

/**
 * Contains the text and display status of a response produced for the graphical interface.
 */
public class GuiResponse {
    private final String message;
    private final boolean error;

    /**
     * Creates a graphical response.
     *
     * @param message response text to display
     * @param error whether the response describes an error
     */
    public GuiResponse(String message, boolean error) {
        assert message != null : "GUI response message must not be null";
        this.message = message;
        this.error = error;
    }

    /**
     * Returns the response text.
     *
     * @return response text
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns whether the response represents an error.
     *
     * @return true when the response is an error
     */
    public boolean isError() {
        return error;
    }
}
