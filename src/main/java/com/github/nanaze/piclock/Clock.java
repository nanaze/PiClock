package com.github.nanaze.piclock;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.Frame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.Dimension;

import java.lang.Math;
import java.lang.Thread;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;


class ClockKeyListener implements KeyListener {
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      System.exit(0);
    }
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }
}

class Clock {

  public static void main(String[] args) throws InterruptedException {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    Frame frame = new Frame();
    Canvas canvas = new ClockCanvas();
    frame.add(canvas);

    KeyListener kl = new ClockKeyListener();
    frame.addKeyListener(kl);

    gd.setFullScreenWindow(frame);

    TimerTask task = new TimerTask() {
      public void run() {
        canvas.repaint();
      }
    };

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(task, 0, 1000);
  }
}
