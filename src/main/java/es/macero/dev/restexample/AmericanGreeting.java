package es.macero.dev.restexample;

class AmericanGreeting {

    private String message;

    // Required for JSON deserialization
    AmericanGreeting() {
    }

    public AmericanGreeting(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
