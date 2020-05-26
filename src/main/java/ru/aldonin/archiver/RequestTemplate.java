package ru.aldonin.archiver;

public enum RequestTemplate {

    // main ones
    SAVE ("save"),
    RETRIEVE("retrieve"),
    GET_SUM ("csum"),
    ADD_PASSWORD ("repas"),
    HELP ("help"),

    //options
    SOURCE ("-f"),
    TARGET ("-t"),
    PASSWORD ("-p"),
    CHECKSUM ("-s")
    ;

    private String request;

    RequestTemplate(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }
}
