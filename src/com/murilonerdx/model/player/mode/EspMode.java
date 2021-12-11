package com.murilonerdx.model.player.mode;

public class EspMode {
    private static boolean active = false;
    private static Esp esp;

    public static void startEsp() {
        if(!active) {
            active = true;
            esp = new Esp();
            esp.start();
        }
    }

    public static void stopEsp() {
        if(active) {
            active = false;
            esp.stopEsp();
        }

    }
}
