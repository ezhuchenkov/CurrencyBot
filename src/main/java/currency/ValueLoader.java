package currency;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

public class ValueLoader {

    private final URL url;
    private final String out;
    private final ObjectMapper mapper;
    private final Money m;

    public ValueLoader() {
        try {
            this.url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            this.out = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        this.mapper = new ObjectMapper();
        try {
            this.m = mapper.readValue(out, Money.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public String getValue(String s) {
        double value = m.map.get(s).value;
        return String.valueOf(value);
    }

    public String getNames() {
        Set<String> key = m.map.keySet();
        return key.toString();
    }

    public boolean hasMatch(String s) {
        return m.map.containsKey(s);
    }

}
