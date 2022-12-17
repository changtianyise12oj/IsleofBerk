package com.GACMD.isleofberk.util;

public class Util {

    public static double ticksToDays(double ticks) {
        double seconds = ticks/20;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours/24;
        return days;
    }

    /**
     * Convert ticks to ticks
     * @param seconds
     * @return
     */
    public static int secondsToTicks(int seconds) {
        return seconds * 20;
    }

    /**
     * Each minecraft day is 20 minutes
     * @param minutes
     * @return
     */
    public static int minutesToSeconds(int minutes) {
        return minutes * secondsToTicks(60);
    }

    /**
     * Each minecraft day is 20 minutes
     * @param mcDays
     * @return
     */
    public static int mcDaysToMinutes(int mcDays) {
        return mcDays * minutesToSeconds(20);
    }

    public static int hoursToMinutes(int hours) {
        return hours * minutesToSeconds(60);
    }

    public static int daysFromHours(int days) {
        return days * hoursToMinutes(24);
    }

    public static float angleDifference(float angle1, float angle2){
        float phi = Math.abs(angle1 - angle2) % 360;
        float dif = phi > 180 ? 360 - phi : phi;
        int sign = angle1 - angle2 >= 0 && angle1 - angle2 <= 180 || angle1 - angle2 <= -180 && angle1 - angle2 >= -360 ? 1 : -1;
        dif *= sign;
        return dif;
    }

    private static final float DEGREES_TO_RADIANS = 0.017453292519943295F;

    public static float toRadians(float angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }
}
