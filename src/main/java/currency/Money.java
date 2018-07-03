package currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Money {

    @JsonProperty("Valute")
   public final Map<String,Valute> map;

    @JsonCreator
    public Money( @JsonProperty("Valute") Map<String, Valute> map) {
        this.map = map;

    }

    @Override
    public String toString() {
        return super.toString();
    }
}




