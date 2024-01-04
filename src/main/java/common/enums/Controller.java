package common.enums;

public enum Controller {
    AI("ai"),
    CUSTOMER("streamsheets"),
    ;

    private final String controller;

    Controller(String controller) {
        this.controller = controller;
    }

    public String getController() {
        return controller;
    }
}
