package com.murilonerdx.model.player.mode;

import com.guidedhacking.GHMemory;
import com.murilonerdx.model.Helper;
import com.murilonerdx.model.player.Player;

public class Bullet {
    private static final int moduleBase = Helper.moduleBase;
    private static final Player player = new Player();
    private static int bkpHealth;
    private static int bkpGrenadeAmmo;

    private static boolean active = false;

    public static void activeBulletInfinite(){
        if(!active) {
            active = true;

            //back up values:
            System.out.println(GHMemory.readInt(moduleBase+0x17B264+0xEC));
            bkpHealth = player.getHealth();
            bkpGrenadeAmmo = player.getGrenadeAmmo();


            //set new values:
            player.setHealth(999);
            player.setGrenadeAmmo(1997);
        }
    }

    public static void deActivateBulletInfinite(){
        if(active) {
            active = false;
            //reset old values:
            player.setHealth(bkpHealth);
            player.setGrenadeAmmo(bkpGrenadeAmmo);
        }
    }
}
