package com.murilonerdx.model.player.mode;

import com.guidedhacking.GHMemory;
import com.guidedhacking.GHPointer;
import com.guidedhacking.GHTools;
import com.murilonerdx.AssaltCube;
import com.murilonerdx.model.Enemy;
import com.murilonerdx.model.Helper;
import com.murilonerdx.model.player.Player;
import com.murilonerdx.model.player.Position;
import com.murilonerdx.model.player.Position2D;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Esp extends AnimationTimer {
    private boolean stop = false;
    private static Pane EspOverlay;
    private boolean initialized = false;
    private Enemy[] players;
    private final int moduleBase = Helper.moduleBase;

    Rectangle[] EspBoxes;
    GHPointer listPointer;
    long playerList;
    Player myPlayer;
    Position enemyWorldPos;
    Position2D enemyScreenPos;

    public void stopEsp() {
        stop = true;
        for (Rectangle r : EspBoxes) {
            if (r != null)
                r.setVisible(false);
        }
    }

    @Override
    public void handle(long now) {
        if (!stop) {
            try {
                if (!initialized) {
                    EspOverlay = AssaltCube.pane;
                    //EspOverlay.getChildren().add(new Rectangle (25, 25, Color.RED));
                    players = new Enemy[GHMemory.readInt(moduleBase + 0x187C18) - 1];
                    EspBoxes = new Rectangle[players.length];

                    listPointer = new GHPointer(moduleBase + 0x187C10, 0x4);
                    playerList = GHMemory.getObjectAddress(listPointer);
                    myPlayer = new Player();
                    enemyScreenPos = new Position2D(0, 0);

                    for (int i = 0; i < players.length; i++) {
                        players[i] = new Enemy((int) playerList + (i * 4));
                        EspBoxes[i] = new Rectangle(50, 50, Color.TRANSPARENT);
                        EspBoxes[i].setStroke(Color.RED);
                        EspOverlay.getChildren().add(EspBoxes[i]);
                    }
                    initialized = false;
                }


                if (players.length == (GHMemory.readInt(moduleBase + 0x187C18) - 1) && !stop) {
                    for (int i = 0; i < players.length; i++) {
                        if (stop) {
                            break;
                        }

                        enemyWorldPos = players[i].getPosition();
                        enemyWorldPos.z -= 8;

                        if (Helper.WorldToScreen(enemyWorldPos, enemyScreenPos, GHTools.getGameWidth(), GHTools.getGameHeight()) && !stop) {
                            if (enemyScreenPos.x > 0 && enemyScreenPos.y > 0) {
                                EspBoxes[i].setVisible(true);
                                float dist = myPlayer.getDistance(enemyWorldPos);
                                EspBoxes[i].setHeight(8000 / dist);
                                EspBoxes[i].setWidth(5000 / dist);
                                EspBoxes[i].setX(enemyScreenPos.x - (EspBoxes[i].getWidth() / 2));
                                EspBoxes[i].setY(enemyScreenPos.y - (EspBoxes[i].getHeight() / 2));
                            }
                        } else {
                            EspBoxes[i].setVisible(false);
                        }

                    }
                }
            } catch (Exception e) {
                //exception handeling
            }
        }
    }
}
