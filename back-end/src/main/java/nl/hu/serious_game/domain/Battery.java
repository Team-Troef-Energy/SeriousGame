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

    // 13.5 kWh is the capacity of a single battery.
    // 5 kWh is the charge speed of a single battery.
    // 11.5 kWh is the discharge speed of a single battery.
    public Battery(int amount) {
        maxCharge = 13.5f * amount;
        chargeSpeed = 5 * amount;
        dischargeSpeed = 11.5f * amount;
        this.amount = amount;
    }

    // Takes the electricity that needs to be handled.
    // Returns the electricity that remains afterwards.
    // The maximum amount of electricity that can be demanded is the discharge speed.
    // The maximum amount of electricity that can be produced is the charge speed.
    public Electricity use(Electricity electricity) {
        if (electricity.amount() == 0) {
            return electricity;
        }
        if (electricity.direction() == Direction.DEMAND) {
            float available = Math.min(this.dischargeSpeed, this.currentCharge);
            float flow = Math.min(available, electricity.amount());
            this.currentCharge -= flow;
            return new Electricity(electricity.amount() - flow, Direction.DEMAND);
        }
        if (electricity.direction() == Direction.PRODUCTION) {
            float available = Math.min(this.chargeSpeed, this.maxCharge - this.currentCharge);
            float flow = Math.min(available, electricity.amount());
            this.currentCharge += flow;
            return new Electricity(electricity.amount() - flow, Direction.PRODUCTION);
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
