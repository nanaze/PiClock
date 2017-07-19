package com.github.nanaze.piclock;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.LocalTime;

public class ClockCanvas extends Canvas {
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
      (int) (dim.width - rect.getWidth()) / 2,
      (int) (dim.height - rect.getHeight()) / 2 + (int) Math.abs(rect.getY()));
  }
}
