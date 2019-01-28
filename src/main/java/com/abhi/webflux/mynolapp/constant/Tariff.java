package com.abhi.webflux.mynolapp.constant;

/**
 * Created by Abhinav on 12/27/2018.
 */
public final class Tariff{
    public static final double maxTariff = 7.5;
    public static final double minTariff = 3.0;
    public static final double boatTariff = 5.0;
    public static final double zeroTariff = 0.0;

    public enum Tariff_Zone{
        SINGLE_ZONE(1,3.0),
        TWO_ZONE(2,5.0),
        THREE_ZONE(3,7.50),
        FOUR_ZONE(4,7.50),
        FIVE_ZONES(5,7.50),
        SIX_ZONES(6,7.50);
        private int zones;
        private double charge;

        Tariff_Zone(final int zones) {
            this.zones = zones;
        }

        Tariff_Zone(final int zones, double charge) {
            this(zones);
            this.charge = charge;
        }

        public int getZones() {
            return zones;
        }

        public double getCharge() {
            return charge;
        }
    };
}
