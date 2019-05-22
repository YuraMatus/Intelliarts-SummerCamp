package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static HashMap<String, Integer> COMMANDS_ARGS_COUNT = new HashMap<>();

    static {
        COMMANDS_ARGS_COUNT.put("purchase", 5);
        COMMANDS_ARGS_COUNT.put("all", 1);
        COMMANDS_ARGS_COUNT.put("clear", 2);
        COMMANDS_ARGS_COUNT.put("report", 3);
    }

    private static String readCommandsLineFromConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static boolean isInputValid(List<String> args) {
        String command = args.get(0);
        if (!COMMANDS_ARGS_COUNT.containsKey(command)) {
            System.err.println(String.format("No existing command %s", command));
            return false;
        }

        int argsCount = COMMANDS_ARGS_COUNT.get(command);

        if (args.size() != argsCount) {
            System.err.println(String.format("Command %s has invalid count of arguments %d, should be %d",
                    command, args.size(), argsCount
            ));
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Manager manager = new Manager();

        while (true) {
            String commandsStr;
            try {
                commandsStr = readCommandsLineFromConsole().trim();
            } catch (IOException e) {
                continue;
            }

            ArrayList<String> cmdArgs = new ArrayList<>(Arrays.asList(commandsStr.split(" ", 5)));

            if (!isInputValid(cmdArgs)) {
                continue;
            }

            String command = cmdArgs.get(0);

            switch (command) {
                case "purchase":
                    manager.addPurchase(cmdArgs);
                    manager.showAllPurchases();
                    break;
                case "all":
                    manager.showAllPurchases();
                    break;
                case "clear":
                    manager.deletePurchase(cmdArgs);
                    break;
                case "report":
                    manager.calculateReport(cmdArgs);
                    break;
            }
        }
    }
}
