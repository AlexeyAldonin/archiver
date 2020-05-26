package ru.aldonin.archiver;

import java.util.Scanner;

public class UserInputRequest {

    public static void SayHello () {
        System.out.println("Добро пожаловать в уникальный многофункциональный архиватор!");
    }

    public static void SayBye () {
        System.out.println("Благодарим за использование нашего невероятного многофункционального архиватора!");
    }

    public static String requestUserIntention () {
        System.out.println("Введите запрос Ваш запрос." +
                "\nДля получения справки введите Help" +
                "\nДля выхода введите EXIT");
        return new Scanner(System.in).nextLine();
    }

    public static String askForPassword () {
        System.out.println("Введите пароль. Чтобы пропустить ввод пароля нажмие ENTER");
        return new Scanner(System.in).nextLine();
    }

    public static void showHelp() {
        System.out.println("Справка:\n" +
                "Список доступных запросов:\n" +
                "save – сохранение файлов в архиве\n" +
                "retrieve – восстановление файлов из архива\n" +
                "csum – вычисление контрольной суммы файла\n" +
                "repas – установка пароля для архива\n" +
                "\n" +
                "Список дополнительных атрибутов:\n" +
                "-f - атрибут указания исходного источника (файла или целой папки)\n" +
                "-t - атрибут указания целевой директории (при отсутствии отрибута по умолчанию - исходная диектория)\n" +
                "-p - атрибут указания пароля \n" +
                "-s - атрибут указания подсчета контрольной суммы\n" +
                "Для получения справки введите HELP\n" +
                "Для выхода введите EXIT\n");
    }


    public static String requestForPath (String endLine) {
        System.out.println("Укажите расположение " + endLine +
                "\nДля выхода введите EXIT"
                + "\nДля справки введит HELP");
        String out = new Scanner(System.in).nextLine();
        if (out.equalsIgnoreCase("help")) {
            showHelp();
            out = requestForPath(endLine);
        }
        return out;
    }

}
