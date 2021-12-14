package com.murilonerdx;

import com.guidedhacking.GHArchitecture;
import com.guidedhacking.GHInput;
import com.guidedhacking.GHMemory;
import com.guidedhacking.GHTools;
import com.murilonerdx.model.Helper;
import com.murilonerdx.model.player.mode.BulletMode;
import com.murilonerdx.model.player.mode.EspMode;
import com.sun.glass.events.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
        offset life = ac_client.exe+17B0B8
        ac_client.exe = 00400000
        cheat engine acess memory view, double click in ac_client.exe -> tools dissect PE headers [portable executable(PE)]
        MainPlayerBase: ac_client.exe+17B264

        PointerEntityList: ac_client.exe + 187C10
        MainPlayerEnemy: "ac_client.exe"+0018EFDC (EC + 4)
        Position: "ac_client.exe"+0018EFDC (0x4 - 8)
        QtdPlayers = "ac_client.exe" 0x187C18;

        ANGLEX: 34 #Vai de 0 até 360
        ANGLEY: 38 #Vai de 90 a -90
        X: 8 #Movar para os lados
        Y: C
        Z: 4 #Profundidade, pra frente e pra trás mudam


 */

public class AssaltCube extends Application {
    public static Pane pane;
    private static boolean redraw = true;
    private final int moduleBase = Helper.moduleBase;

    private static boolean esp = false;
    private static boolean aimbot = false;

    private static Label header;
    private static Label line1;
    private static Label line2;
    private static Label line3;
    private static Label line4;
    private static Label exit;
    private static Label mode;

    private static boolean attached = false;
    static Stage stage;

    public static void main(String[] args) {
        try {
            if (GHMemory.openProcess("AssaultCube")) {
                attached = true;
                GHMemory.setArchitecture(GHArchitecture.Win32);
                launch(args);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (attached) {
            this.stage = primaryStage;
            pane = new Pane();
            Scene scene = new Scene(pane, GHTools.getGameWidth(), GHTools.getGameHeight());
            System.out.println((GHTools.getGameWidth() - 6) + "x" + (GHTools.getGameHeight() - 31));
            primaryStage.setTitle("MENU_OVERLAY");

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            pane.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
            primaryStage.setAlwaysOnTop(true);


            header = new Label("<<Guia de como usar o trainee>>");
            line1 = new Label("Acesse github murilonerdx:");
            line2 = new Label("ATIVAR HACK [L]");
            line3 = new Label("DESATIVAR HACK [K]");
            line4 = new Label("ESP [P]");
            exit = new Label("SAIR [X]");
            mode = new Label("MODE: NORMAL");

            pane.getChildren().add(header);
            pane.getChildren().add(line1);
            pane.getChildren().add(line2);
            pane.getChildren().add(line3);
            pane.getChildren().add(line4);
            pane.getChildren().add(exit);
            pane.getChildren().add(mode);

            header.setLayoutX(150);
            line1.setLayoutX(150);
            line2.setLayoutX(150);
            line3.setLayoutX(150);
            line4.setLayoutX(150);
            exit.setLayoutX(150);
            mode.setLayoutX(150);

            header.setLayoutY(430);
            line1.setLayoutY(460);
            line2.setLayoutY(490);
            line3.setLayoutY(520);
            line4.setLayoutY(550);
            exit.setLayoutY(580);
            mode.setLayoutY(610);

            header.setTextFill(Color.ALICEBLUE);
            line1.setTextFill(Color.ORANGE);
            line2.setTextFill(Color.ORANGE);
            line3.setTextFill(Color.ORANGE);
            line4.setTextFill(Color.ORANGE);
            exit.setTextFill(Color.RED);
            mode.setTextFill(Color.GREEN);

            primaryStage.setX(GHTools.getGameXPos());
            primaryStage.setY(GHTools.getGameYPos());
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();

            AnimationTimer updater = new AnimationTimer() {
                @Override
                public void handle(long arg0) {
                    if (attached) {
                        stage.setX(GHTools.getGameXPos());
                        stage.setY(GHTools.getGameYPos());

                        if (redraw) {
                            header.setText("Criado por Murilo");
                            line1.setText("github: murilonerdx");
                            line2.setText("[L] - BULLET & LIFE +999");
                            line3.setText("[K] - DESATIVAR HACK ");
                            line4.setText("[P] - ESP ");
                            redraw = false;
                        }

                        if (GHInput.getKeyDown(KeyEvent.VK_L)) {
                            header.setText("Criado por Murilo");
                            line1.setText("github: murilonerdx");
                            line2.setText("[L] - BULLET & LIFE +999");
                            line3.setText("[K] - DESATIVAR HACK ");
                            line4.setText("[P] - ESP ");
                            mode.setText("MODE: ATIVO");

                            BulletMode.activeBulletInfinite();
                        }

                        if (GHInput.getKeyDown(KeyEvent.VK_K)) {
                            header.setText("Criado por Murilo");
                            line1.setText("github: murilonerdx");
                            line2.setText("[L] - BULLET & LIFE +999");
                            line3.setText("[K] - DESATIVAR HACK ");
                            line4.setText("[P] - ESP ");
                            mode.setText("MODE: DESATIVADO");

                            BulletMode.deActivateBulletInfinite();
                        }

                        if (GHInput.getKeyDown(KeyEvent.VK_P)) {
                            header.setText("Criado por Murilo");
                            line1.setText("github: murilonerdx");
                            line2.setText("[L] - BULLET & LIFE +999");
                            line3.setText("[K] - DESATIVAR HACK ");
                            line4.setText("[P] - ESP ");

                            EspMode.startEsp();
                        }

                        if (GHInput.getKeyDown(KeyEvent.VK_X)) {
                            attached = false;
                        }
                    } else {
                        Platform.exit();
                    }
                }
            };
            updater.start();
        }

    }

    @Override
    public void stop() {
        //clean up:
        GHMemory.close();
        stage.close();
        System.exit(0);
    }
}
