package com.murilonerdx.model.player;

import com.guidedhacking.GHMemory;
import com.guidedhacking.GHPointer;
import com.murilonerdx.Rotation;
import com.murilonerdx.model.Enemy;
import com.murilonerdx.model.Helper;

public class Player {

    private final int moduleBase = Helper.moduleBase;
    private GHPointer healthPtr  = new GHPointer(moduleBase+0x17B264,0xEC); // moduleBase+0x17B264 = MainPlayer + EC = Vida

    private GHPointer teamPtr = new GHPointer(moduleBase+0x17B264,0x220);
    private GHPointer posXPtr = new GHPointer(moduleBase+0x17B264,0x2C);
    private GHPointer posYPtr = new GHPointer(moduleBase+0x17B264,0xC);
    private GHPointer posZPtr = new GHPointer(moduleBase+0x17B264,0x28);

    private GHPointer rotXPtr = new GHPointer(moduleBase+0x17B264, 0x34);
    private GHPointer rotYPtr = new GHPointer(moduleBase+0x17B264, 0x38);

    private GHPointer bulletAmmoPtr = new GHPointer(moduleBase+0x17B264,0x140);


    public Position getPosition() {
        float x = GHMemory.readFloat(GHMemory.getObjectAddress(posXPtr));
        float y = GHMemory.readFloat(GHMemory.getObjectAddress(posYPtr));
        float z = GHMemory.readFloat(GHMemory.getObjectAddress(posZPtr));

        return new Position(x,y,z);
    }

    public float getDistance(Position x){
        Position here = this.getPosition();
        return (float) Math.sqrt(Math.pow((here.x-x.x),2) + Math.pow((here.y-x.y),2) + Math.pow((here.z-x.z),2));
    }

    public void setPosition(Position pos) {
        setPosition(pos.x, pos.y, pos.z);
    }

    public void setPosition(float x, float y, float z) {
        GHMemory.writeFloat(x,GHMemory.getObjectAddress(posXPtr));
        GHMemory.writeFloat(y,GHMemory.getObjectAddress(posYPtr));
        GHMemory.writeFloat(z,GHMemory.getObjectAddress(posZPtr));
    }

    public Rotation getRotation() {
        float x = GHMemory.readFloat(GHMemory.getObjectAddress(rotXPtr));
        float y = GHMemory.readFloat(GHMemory.getObjectAddress(rotYPtr));

        return new Rotation(x,y);
    }

    public void setRotation(Rotation rot) {
        setRotation(rot.x, rot.y);
    }

    public void setRotation(float x, float y) {
        GHMemory.writeFloat(x,GHMemory.getObjectAddress(rotXPtr));
        GHMemory.writeFloat(y,GHMemory.getObjectAddress(rotYPtr));
    }

    public int getHealth() {
        return GHMemory.readInt(GHMemory.getObjectAddress(healthPtr));
    }

    public void setHealth(int health) {
        GHMemory.writeInt(health,GHMemory.getObjectAddress(healthPtr));
    }

    public int getGrenadeAmmo() {
        return GHMemory.readInt(GHMemory.getObjectAddress(bulletAmmoPtr));
    }

    public void setGrenadeAmmo(int ammo) {
        GHMemory.writeInt(ammo,GHMemory.getObjectAddress(bulletAmmoPtr));
    }

    public String getTeam() {
        return GHMemory.readString(GHMemory.getObjectAddress(teamPtr),5);
    }

    public boolean isEnemy(Enemy player) {
        return !(getTeam().contains(player.getTeam()));
    }
}
