package com.github.nanaze.piclock;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClockCanvas extends Canvas {

  private static final Pattern TIME_PATTERN =  Pattern.compile("[\\d:]+");

  public ClockCanvas() {
    setBackground(Color.BLACK);
  }


  public void paint(Graphics g) {
    g.setColor(Color.WHITE);

    LocalTime time = LocalTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
    String timeString = time.format(formatter);

    drawString(extractTime(timeString), g);
  }

  private static String extractTime(String formattedTime) {
    Matcher matcher = TIME_PATTERN.matcher(formattedTime);
    boolean patternFound = matcher.find();

    if (patternFound) {
      return matcher.group();
    }

    // If pattern was not found, just fall back.
    return formattedTime;
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
