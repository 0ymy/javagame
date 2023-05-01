package com.bitset.jgametest;

import com.bitset.javagame.Color;
import com.bitset.javagame.event.Event;
import com.bitset.javagame.event.EventType;
import com.bitset.javagame.Surface;
import com.bitset.javagame.WindowSurface;

public class Game {
    private WindowSurface window;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        window = new WindowSurface("Hello world.", 640, 480);
        window.setSize(640, 640);
        run();
    }

    private void run() {
        double delta = 1000000000d / 60d;
        long lastTime = System.nanoTime();
        double count = 0d;

        boolean running = true;

        var testSurf = Surface.fromFile("com/bitset/jgametest/tree.png");

        float angle = 0f;

        while (running) {
            long time = System.nanoTime();
            count += (time - lastTime) / delta;
            lastTime = time;

            if (count >= 1) {
                count--;

                window.getMousePosition();

                for (var event : window.getEvents()) {
                    if (event.type == EventType.QUIT) {
                        running = false;
                    } else if (event.type == EventType.KEY_DOWN) {
                        if (event.key == Event.KEY_W) {
                            System.out.println("hello");
                        }
                    }
                }

                angle += 1f;
                Surface test = testSurf.rotate(angle);

                window.fill(new Color(255, 0, 0, 255));
                window.surface(test, window.width / 2 - test.width / 2, window.height / 2 - test.height / 2, test.width, test.height);
                window.oval(new Color(0, 255, 0, 255), window.width / 2, window.height / 2, 128, 128, 0);
                window.flip();
            }
        }

        window.exit();
    }

}
