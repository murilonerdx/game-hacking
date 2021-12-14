package com.murilonerdx.model;

import com.guidedhacking.GHMemory;
import com.guidedhacking.GHPointer;
import com.murilonerdx.model.player.Position;

public class Enemy {
    private GHPointer healthPtr;
    private GHPointer posYPtr;
    private GHPointer posXPtr;
    private GHPointer posZPtr;
    private GHPointer teamPtr;
    private String name;

    public Enemy(int enemyPointer) throws ClassNotFoundException {
        healthPtr  = new GHPointer(enemyPointer,0xEC);
        posXPtr = new GHPointer(enemyPointer,0x2C);
        posYPtr = new GHPointer(enemyPointer,0xC);
        posZPtr = new GHPointer(enemyPointer,0x28);
        teamPtr = new GHPointer(enemyPointer,0x220);


        GHPointer namePtr = new GHPointer(enemyPointer,0x205);
        name = GHMemory.readString(GHMemory.getObjectAddress(namePtr),4);
    }

    public Position getPosition() {
        float x = GHMemory.readFloat(GHMemory.getObjectAddress(posXPtr));
        float y = GHMemory.readFloat(GHMemory.getObjectAddress(posYPtr));
        float z = GHMemory.readFloat(GHMemory.getObjectAddress(posZPtr));

        return new Position(x,y,z);
    }

    public void setPosition(Position pos) {
        setPosition(pos.x, pos.y, pos.z);
    }

    public void setPosition(float x, float y, float z) {
        GHMemory.writeFloat(x,GHMemory.getObjectAddress(posXPtr));
        GHMemory.writeFloat(y,GHMemory.getObjectAddress(posYPtr));
        GHMemory.writeFloat(z,GHMemory.getObjectAddress(posZPtr));
    }

    public int getHealth() {
        return GHMemory.readInt(GHMemory.getObjectAddress(healthPtr));
    }
    public boolean isAlive() {
        return (getHealth()>0 && getHealth()<1000);
    }

    public String getTeam() {
        return GHMemory.readString(GHMemory.getObjectAddress(teamPtr),5);
    }
    public String getName() {
        return name;
    }
}
