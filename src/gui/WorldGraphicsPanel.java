package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

import util.Tuple;
import backend.TurtleModel;
import backend.WorldsCollection;

public class WorldGraphicsPanel extends Component {

	public static final Dimension SCREEN_DIMENSION = new Dimension(533, 533);
	Graphics2D graphicsEngine;
	protected JTextArea historyTextArea;
	protected JTextArea inputTextArea, consoleOutputTextArea;

	/**
	 * TODO: Should read image path and path color from file
	 */

	public WorldGraphicsPanel() {
		super();
	}

	public void paint(Graphics g) {
		graphicsEngine = (Graphics2D) g;
		graphicsEngine.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		drawBorder();
		drawTrails();
		drawTurtle();
	}

	void drawTurtle() {
		TurtleModel turtle = WorldsCollection.getInstance().getCurrentWorld().getTurtle();
		BufferedImage turtleImage = turtle.getImage();
		if (turtle.isShowing()) {
			Tuple center = getCenter();
			double rotationAngle = Math.toRadians(turtle.getHeading());
			AffineTransform tx = AffineTransform.getRotateInstance(
					rotationAngle, turtleImage.getWidth(this) / 2,
					turtleImage.getHeight(this) / 2);
			AffineTransformOp op = new AffineTransformOp(tx,
					AffineTransformOp.TYPE_BILINEAR);
			graphicsEngine.drawImage(op.filter(turtleImage, null),
					(int) (center.x + turtle.getPosition().x - turtleImage
							.getWidth(this) / 2),
							(int) (center.y - turtle.getPosition().y - turtleImage
									.getHeight(this) / 2), null);
		}
	}

	public Dimension getPreferredSize() {
		return SCREEN_DIMENSION;
	}

	void drawBorder() {
		graphicsEngine.draw3DRect(0, 0, SCREEN_DIMENSION.width - 1,
				SCREEN_DIMENSION.height - 1, true);
	}

	void drawTrails() {
		graphicsEngine.setColor(WorldsCollection.getInstance().getCurrentWorld().getPenColor());
		Tuple center = getCenter();
		for (ArrayList<Tuple> path : WorldsCollection.getInstance().getCurrentWorld().getPaths()) {
			for (int i = 0; i < (path.size() - 1); i++) {
				graphicsEngine.draw(new Line2D.Double(path.get(i).x + center.x,
						-path.get(i).y + center.y,
						path.get(i + 1).x + center.x, -path.get(i + 1).y
						+ center.y));
			}
		}
	}

	Tuple getCenter() {
		double centerX = SCREEN_DIMENSION.width / 2;
		double centerY = SCREEN_DIMENSION.height / 2;
		return new Tuple(centerX, centerY);
	}
}