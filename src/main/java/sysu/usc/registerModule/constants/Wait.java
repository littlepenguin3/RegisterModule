package sysu.usc.registerModule.constants;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Component
@NoArgsConstructor
@Data
public class Wait {
    public long amount;
    public TemporalUnit unit;

    public void setUnit(String unit) {
        switch (unit){
            case "m":
                this.unit = ChronoUnit.MINUTES;
                break;
            case "s":
                this.unit = ChronoUnit.SECONDS;
                break;
            default:
                this.unit = ChronoUnit.MILLIS;
        }
    }
}
