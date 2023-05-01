package com.bitset.javagame;

import com.bitset.javagame.event.Event;
import com.bitset.javagame.event.EventType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

public class WindowSurface extends Surface implements WindowListener, KeyListener {
    private JFrame frame;

    private List<com.bitset.javagame.event.Event> events;

    public WindowSurface(String title, int width, int height) {
        super(width, height);

        frame = new JFrame();
        frame.getContentPane().setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setTitle(title);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addWindowListener(this);
        frame.addKeyListener(this);

        events = new ArrayList<>();
    }

    public Point getMousePosition() {
        var position = frame.getContentPane().getMousePosition();
        
        if (position == null) {
            position = new java.awt.Point(0, 0);
        }

        return new Point(position.x, position.y);
    }

    public void setSize(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        frame.getContentPane().setPreferredSize(new Dimension(width, height));
        frame.pack();

        this.width = width;
        this.height = height;
    }

    public void flip() {
        var graphics = (Graphics2D) frame.getContentPane().getGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);

        events.clear();
    }

    public void exit() {
        frame.dispose();
    }

    public List<com.bitset.javagame.event.Event> getEvents() {
        return events;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        events.add(new Event(EventType.QUIT));
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        events.add(new Event(EventType.KEY_DOWN, e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        events.add(new Event(EventType.KEY_UP, e.getKeyCode()));
    }
}
