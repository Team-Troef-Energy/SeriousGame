package nl.hu.serious_game.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Battery implements Cloneable {
    @Id
    @GeneratedValue
    @Setter // TODO: ids should not be settable. this is to set the id to zero when cloning.
    private Long id;

    private int amount;

    private transient float chargeSpeed;
    private transient float dischargeSpeed;
    private transient float currentCharge;
    private transient float maxCharge;

    // 13.5 kWh is the capacity of a single battery.
    // 5 kWh is the charge speed of a single battery.
    // 11.5 kWh is the discharge speed of a single battery.
    public Battery(int amount) {
        maxCharge = 13.5f * amount;
        chargeSpeed = 5 * amount;
        dischargeSpeed = 11.5f * amount;
        this.amount = amount;
    }

    // Takes the current that needs to be handled.
    // Returns the current that remains afterwards.
    // The maximum amount of current that can be demanded is the discharge speed.
    // The maximum amount of current that can be stored is the charge speed.
    public Current chargeOrDischarge(Current current) {
        if (current.getAmount() == 0) {
            return current;
        }

        // If the current is demanded, the battery will discharge
        if (current.getDirection() == Direction.DEMAND) {
            float available = Math.min(this.dischargeSpeed, this.currentCharge);
            float flow = Math.min(available, current.getAmount());
            this.currentCharge -= flow;
            return new Current(current.getAmount() - flow, Direction.DEMAND);
        }

        // If the current is produced, the battery will charge
        if (current.getDirection() == Direction.PRODUCTION) {
            float available = Math.min(this.chargeSpeed, this.maxCharge - this.currentCharge);
            float flow = Math.min(available, current.getAmount());
            this.currentCharge += flow;
            return new Current(current.getAmount() - flow, Direction.PRODUCTION);
        }
        throw new RuntimeException("Unreachable");
     }

     public Battery clone() {
         try {
             return (Battery) super.clone();
         } catch (CloneNotSupportedException e) {
             throw new RuntimeException("Cloning failed");
         }
     }
}
