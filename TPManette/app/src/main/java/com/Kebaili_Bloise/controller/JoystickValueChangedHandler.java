package com.Kebaili_Bloise.controller;


public class JoystickValueChangedHandler implements JoystickView.ValueChangedHandler {
    @Override
    public void onValueChanged(int Vg, int Vd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!GameMessageManager.isConnected()) {
                    System.err.println("Connexion non établie !");
                    return;
                } else {
                    GameMessageManager.sendMessage("MotL=" + ((Vg/200.0)+0.5) + "#MotR=" + ((Vd/200.0)+0.5));
                }
            }
        }).start(); // Start the thread
    }
}
