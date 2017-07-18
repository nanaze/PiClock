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


class ClockCanvas extends Canvas {
    public ClockCanvas() {
	setBackground(Color.BLACK);
    }
    
    public void paint(Graphics g) {
	g.setColor(Color.WHITE);

	LocalTime time = LocalTime.now();
	String timeString = String.format("%d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
	
	drawString(timeString, g);
    }

    private void drawString(String timeString, Graphics g) {
	Dimension dim = getSize();

	int fontSize = 1;
	Font font = new Font(Font.MONOSPACED, Font.PLAIN, fontSize);	
	Font biggestFont = font;

	// Figure out the biggest font that fits.
	while (true) {
	    font = new Font(Font.MONOSPACED, Font.PLAIN, fontSize);
	    FontMetrics fm = g.getFontMetrics(font);
	    Rectangle2D rect = fm.getStringBounds(timeString, g);

	    if (rect.getWidth() <= dim.width &&
		rect.getHeight() <= dim.height) {
		biggestFont = font;
	    } else {
		break;
	    }

	    fontSize++;
	}

	// Draw the string.
	g.setFont(biggestFont);
	Rectangle2D rect = g.getFontMetrics().getStringBounds(timeString, g);
	g.drawString(timeString,
		     (int)(dim.width - rect.getWidth()) / 2,
		     (int)(dim.height - rect.getHeight()) / 2 + (int) Math.abs(rect.getY()));
    }
}

class ClockKeyListener implements KeyListener {
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	    System.exit(0);
	}
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
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

	TimerTask task = new TimerTask(){
		public void run() {
		    canvas.repaint();
		}
	    };

	Timer timer = new Timer();
	timer.scheduleAtFixedRate(task, 0, 1000);
    }
}
