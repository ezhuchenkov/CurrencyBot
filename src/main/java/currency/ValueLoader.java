package currency;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class ValueLoader {

    private static final String URL_PATH = "https://www.cbr-xml-daily.ru/daily_json.js";

    private final URL url;
    private final ObjectMapper mapper;

    public ValueLoader() {
        try {
            url = new URL(URL_PATH);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Wrong url: " + URL_PATH, e);
        }
        mapper = new ObjectMapper();
    }

    private Money getMoney() {
        try {
            String out = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
            return mapper.readValue(out, Money.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String getValue(String s) {
        double value = getMoney().map.get(s).value;
        return String.valueOf(value);
    }

    public String getNames() {
        return getMoney().map.keySet().stream()
                .sorted()
                .collect(joining(", "));
    }

    public boolean hasMatch(String s) {
        return getMoney().map.containsKey(s);
    }

}
