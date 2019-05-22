package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Manager {
    private HashMap<DateOfPurchase, ArrayList<PurchaseItem>> purchaseDiary = new HashMap<>();
    private FixerAPI fixerAPI = new FixerAPI();

    void calculateReport(List<String> args) {
        int year = Integer.parseInt(args.get(1));
        String base = args.get(2);

        double sum = purchaseDiary
                .keySet()
                .stream()
                .filter(d -> d.getYear() == year)
                .flatMap(d -> purchaseDiary.get(d).stream())
                .mapToDouble(item -> fixerAPI.convert(item.getCurrency(), base, item.getCost()))
                .sum();

        System.out.println(String.format("%.2f %s", sum, base));
    }

    void showAllPurchases() {
        purchaseDiary
                .keySet()
                .stream()
                .sorted()
                .forEach(this::showPurchasesForDate);
    }

    void addPurchase(List<String> args) {
        addNewPurchaseForDate(
                parseDate(args),
                new PurchaseItem(Double.parseDouble(args.get(2)), args.get(3), parseName(args.get(4)))
        );
    }

    void deletePurchase(List<String> args) {
        DateOfPurchase date = parseDate(args);

        List<DateOfPurchase> purchasesToDelete = purchaseDiary
                .keySet()
                .stream()
                .filter(d -> date.compareTo(d) > 0)
                .collect(Collectors.toList());

        purchasesToDelete
                .stream()
                .sorted()
                .peek(this::showPurchasesForDate)
                .forEach(purchaseDiary::remove);
    }

    // Util methods
    private void showPurchasesForDate(DateOfPurchase date) {
        System.out.println(date);
        purchaseDiary.get(date).forEach(System.out::println);
        System.out.println();
    }

    private void addNewPurchaseForDate(DateOfPurchase date, PurchaseItem item) {
        if (purchaseDiary.containsKey(date)) {
            purchaseDiary.get(date).add(item);
        } else {
            purchaseDiary.put(date, new ArrayList<>(Collections.singletonList(item)));
        }
    }

    private static String parseName(String name) {
        if (name.endsWith("\"")) {
            name = name.substring(0, name.length() - 2);
        }
        if (name.startsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    private static DateOfPurchase parseDate(List<String> array) {
        List<String> dateData = new ArrayList<>(Arrays.asList(array.get(1).split("-")));

        return new DateOfPurchase(
                Integer.parseInt(dateData.get(0)),
                Integer.parseInt(dateData.get(2)),
                Integer.parseInt(dateData.get(1))
        );

    }
}
