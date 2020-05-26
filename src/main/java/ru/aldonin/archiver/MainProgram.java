package ru.aldonin.archiver;


public class MainProgram {
    public static void main(String[] args) {

        UserInputRequest.SayHello();

        String request = "";

        while (!request.equalsIgnoreCase("exit")) {
            request = UserInputRequest.requestUserIntention();
            if (request.equalsIgnoreCase("exit"))
                break;
            if (RequestProcessor.checkIfRequestIsValid(request)) {

                if (RequestProcessor.checkForMatchingSave(request)) {
                    Zipper.createZip(request);
                }
                else if (RequestProcessor.checkForMatchingRetrieve(request)) {
                    Zipper.extractFilesToDirectory(request);
                }
                else if (RequestProcessor.checkForMatchingAddPassword(request)) {
                    Zipper.repackAddPassword(request);
                }
                else if (RequestProcessor.checkForMatchingGetSum(request)) {
                    System.out.println(CheckSumUtils.getChecksum(request));
                }
            }
        }
        UserInputRequest.SayBye();
    }
}
