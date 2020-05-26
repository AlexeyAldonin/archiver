package ru.aldonin.archiver;


public class RequestProcessor {

    public static boolean checkIfRequestIsValid(String userInput) {
        if (userInput.equalsIgnoreCase(RequestTemplate.HELP.getRequest())) {
            UserInputRequest.showHelp();
            return false;
        }
        String[] requestAttributes = userInput.split(" ");
        if (!requestAttributes[0].equalsIgnoreCase(RequestTemplate.SAVE.getRequest()) &&
                !requestAttributes[0].equalsIgnoreCase(RequestTemplate.ADD_PASSWORD.getRequest()) &&
                !requestAttributes[0].equalsIgnoreCase(RequestTemplate.GET_SUM.getRequest()) &&
                !requestAttributes[0].equalsIgnoreCase(RequestTemplate.HELP.getRequest()) &&
                !requestAttributes[0].equalsIgnoreCase(RequestTemplate.RETRIEVE.getRequest()))
            return false;
        for (int i = 1; i < requestAttributes.length - 1; i++) {
            if (!requestAttributes[i].equalsIgnoreCase(RequestTemplate.CHECKSUM.getRequest()) &&
                    !requestAttributes[i].equalsIgnoreCase(RequestTemplate.PASSWORD.getRequest()) &&
                    !requestAttributes[i].equalsIgnoreCase(RequestTemplate.SOURCE.getRequest()) &&
                    !requestAttributes[i].equalsIgnoreCase(RequestTemplate.TARGET.getRequest()))
                return false;
        }
        if (userInput.startsWith(RequestTemplate.SAVE.getRequest())) {
            return userInput.contains(RequestTemplate.SOURCE.getRequest())
                    && userInput.contains(RequestTemplate.TARGET.getRequest());
        }
        return checkForMatchingSave(userInput) || checkForMatchingRetrieve(userInput)
                || checkForMatchingGetSum(userInput) || checkForMatchingAddPassword(userInput);
    }




    public static boolean checkForMatchingSave (String userInput) {
        return userInput.startsWith(RequestTemplate.SAVE.getRequest())
                && userInput.contains(RequestTemplate.SOURCE.getRequest());
    }

    public static boolean checkForMatchingRetrieve (String userInput) {
        return userInput.startsWith(RequestTemplate.RETRIEVE.getRequest())
                && userInput.contains(RequestTemplate.SOURCE.getRequest());
    }

    public static boolean checkForMatchingGetSum (String userInput) {
        return userInput.startsWith(RequestTemplate.GET_SUM.getRequest())
                && userInput.contains(RequestTemplate.SOURCE.getRequest());
    }

    public static boolean checkForMatchingAddPassword (String userInput) {
        return userInput.startsWith(RequestTemplate.ADD_PASSWORD.getRequest())
                && userInput.contains(RequestTemplate.SOURCE.getRequest())
                && userInput.contains(RequestTemplate.PASSWORD.getRequest());
    }
}

