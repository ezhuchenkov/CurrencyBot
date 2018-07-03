package currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Valute {

    @JsonProperty("Value")
    public final double value;

    @JsonCreator
    public Valute(@JsonProperty("Value") double value) {
        this.value = value;
    }
}
