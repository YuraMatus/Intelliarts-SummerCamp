package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixerAPI {
    private String TOKEN = "d13adfb0303a7872ed7d0311fde79ce0";
    private String FIXER_API_URL = "http://data.fixer.io/api/latest";

    public double convert(String from, String to, double amount) {
        try {
            URL url = new URL(String.format(
                    "%s?access_key=%s&symbols=%s,%s", FIXER_API_URL, TOKEN, from.toUpperCase(), to.toUpperCase()
            ));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            assert con.getResponseCode() == 200;

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            double fromValue = getValueOfBase(content.toString(), from);
            double toValue = getValueOfBase(content.toString(), to);

            return (toValue / fromValue) * amount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double getValueOfBase(String content, String base) {
        Pattern regex = Pattern.compile(String.format("(?<=\"%s\":)\\d+(.\\d+)?", base));
        Matcher matcher = regex.matcher(content);

        if (matcher.find()) {
            String group = matcher.group();
            return Double.parseDouble(group);
        }

        return 0;
    }
}
