package nl.hu.serious_game.domain;

import lombok.Getter;

public class Battery implements Cloneable {
    @Getter
    private float chargeSpeed;
    @Getter
    private float dischargeSpeed;
    @Getter
    private float currentCharge;
    @Getter
    private float maxCharge;
    @Getter
    private int amount;

    public Battery(int amount) {
        maxCharge = 13.5f * amount;
        chargeSpeed = 5 * amount;
        dischargeSpeed = 11.5f * amount;
        this.amount = amount;
    }

    // Takes the current that needs to be handled.
    // Returns the current that remains afterwards.
    public Current chargeOrDischarge(Current current) {
        if (current.amount() == 0) {
            return current;
        }
        if (current.direction() == Direction.DEMAND) {
            float available = Math.min(this.dischargeSpeed, this.currentCharge);
            float flow = Math.min(available, current.amount());
            this.currentCharge -= flow;
            return new Current(current.amount() - flow, Direction.DEMAND);
        }
        if (current.direction() == Direction.PRODUCTION) {
            float available = Math.min(this.chargeSpeed, this.maxCharge - this.currentCharge);
            float flow = Math.min(available, current.amount());
            this.currentCharge += flow;
            return new Current(current.amount() - flow, Direction.PRODUCTION);
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
