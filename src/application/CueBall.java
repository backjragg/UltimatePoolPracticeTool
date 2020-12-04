package application;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import java.util.List;
import java.util.ArrayList;
import javafx.geometry.Point2D;

/**
 * This class creates and controls the cue ball based off of mouse click/drag.
 * The ball should "roll" around the table based off the direction shot, bounce off
 * walls, and "sink" into pockets.
 *
 * @author Jack Bragg, Kyle Fletcher, Jacob Raulerson
 *
 */
public class CueBall {
	private Circle ball; // the cue ball
	private Line directionLine; // the line drawn when player clicks the ball and drags the mouse
	private List<Line> lines = new ArrayList<Line>();
	private int pocketNumber;
	private boolean play = false;

	/**
	 * creates the cue ball and assigns the behavior based off mouse clicking and dragging
	 * @param xPos x position to place the ball
	 * @param yPos y position to place the ball
	 * @param color is the color to set the ball to
	 */
	CueBall(double xPos, double yPos, Color color) {
		ball = new Circle(xPos, yPos, 12, color);

		directionLine = new Line();

		ball.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				directionLine.setOpacity(1);
				directionLine.setStartX(ball.getCenterX());
				directionLine.setStartY(ball.getCenterY());
				directionLine.setEndX(ball.getCenterX());
				directionLine.setEndY(ball.getCenterY());
			}
		});

		ball.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				directionLine.setEndX(directionLine.getStartX() - (event.getX() - directionLine.getStartX()));
				directionLine.setEndY(directionLine.getStartY() - (event.getY() - directionLine.getStartY()));
				int edge = hitEdge(directionLine);
				if (edge >= 0)
				{
					getIntersection(directionLine, edge);
				}

			}
		});

		ball.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				directionLine.setOpacity(0);
				lines.clear();
				lines.add(directionLine);
				shootCue(directionLine.getEndX(), directionLine.getEndY());
			}
		});
	}

	/**
	 * Shoots the cue ball based off of the directionLine created by the player.
	 * @param xPos x position the ball should land
	 * @param yPos y position the ball should land
	 */
	private void shootCue(double xPos, double yPos) {
		pocketNumber = 0;
		PathTransition path = new PathTransition();
		path.setDuration(Duration.millis(1000));

		if(hitPocket(ball.getCenterX(), ball.getCenterY(), xPos, yPos))
		{
			play = true;
			if(pocketNumber == 1)
			{
				xPos = 265;
				yPos = 164;
			}
			else if(pocketNumber == 2)
			{
				xPos = 265;
				yPos = 483.68;
			}
			else if(pocketNumber == 3)
			{
				xPos = 888;
				yPos = 164;
			}
			else if(pocketNumber == 4)
			{
				xPos = 888;
				yPos = 483.68;
			}
			else if(pocketNumber == 5)
			{
				xPos = 576.5;
				yPos = 160;
			}
			else if(pocketNumber == 6)
			{
				xPos = 576.5;
				yPos = 487.68;
			}

			path.setNode(ball);
			directionLine.setEndX(xPos);
			directionLine.setEndY(yPos);
			path.setPath(directionLine);
			path.play();
			path.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event)
				{
					if(play)
					{
						ball.setCenterX(419.75);
						ball.setCenterY(321.84);
						directionLine.setStartX(419.75);
						directionLine.setEndX(420.75);
						directionLine.setEndY(321.84);
						ball.setCenterX(420.75);
						path.play();
						play = false;
					}
					// update the score
					GameScreen.setScore(GameScreen.getScore() + 1);
				}
			});

		}
		else
		{
			int hitVal = hitEdge(directionLine);
			path.setNode(ball);



			if (hitVal < 0)
			{
				path.setPath(directionLine);
				ball.setCenterX(xPos);
				ball.setCenterY(yPos);
			}

			else
			{
				path.setCycleCount(1);
				reflectLine(0, hitVal);
				Polyline poly = new Polyline();
				poly.getPoints().add(ball.getCenterX());
				poly.getPoints().add(ball.getCenterY());

				for (int i = 0; i < lines.size(); i++)
				{
					poly.getPoints().add(lines.get(i).getEndX());
					poly.getPoints().add(lines.get(i).getEndY());
				}
				path.setPath(poly);

				ball.setCenterX(lines.get(lines.size() - 1).getEndX());
				ball.setCenterY(lines.get(lines.size() - 1).getEndY());
				System.out.println("Size: " + lines.size());
				System.out.println("X: " + ball.getCenterX());
				System.out.println("Y: " + ball.getCenterY());



			}


			path.play();

		}

	}

	private void reflectLine(int index, int hitVal)
	{
		if(hitVal >= 0)
		{
			Point2D intercept = getIntersection(lines.get(index), hitVal);
			Line newLine = new Line();
			newLine.setStartX(intercept.getX());
			newLine.setStartY(intercept.getY());

	        //reflect newLine.setEndX and Y across point based on hitVal

			/*
		 	0 = left border
			1 = right border
			2 = top border
			3 = bottom border
			*/

			if (hitVal ==  0)
			{
				newLine.setEndX(276 + Math.abs(276 - lines.get(index).getStartX()));
				newLine.setEndY(intercept.getY() + 2 * (intercept.getY() - lines.get(index).getStartY()));

			}

			if (hitVal ==  1)
			{
				newLine.setEndX(876 - Math.abs(876 - lines.get(index).getStartX()));
				newLine.setEndY(intercept.getY() + 2 * (intercept.getY() - lines.get(index).getStartY()));
			}

			if (hitVal ==  2)
			{
				newLine.setEndY(176 + Math.abs(176 - lines.get(index).getStartY()));
				newLine.setEndX(intercept.getX() + 2 * (intercept.getX() - lines.get(index).getStartX()));
			}

			if (hitVal ==  3)
			{
				newLine.setEndY(471 - Math.abs(471 - lines.get(index).getStartY()));
				newLine.setEndX(intercept.getX() + 2 * (intercept.getX() - lines.get(index).getStartX()));
			}

			Line current = lines.get(index);

			current.setEndX(intercept.getX());
			current.setEndY(intercept.getY());

			lines.set(index, current);
			lines.add(newLine);


			if (!hitPocket(newLine.getStartX(), newLine.getStartY(), newLine.getEndX(), newLine.getEndY()))
			{
				hitVal = hitEdge(lines.get(lines.size() - 1));
				System.out.println("hitVal: " + hitVal);
				reflectLine(lines.size() - 1, hitVal);
			}
			else
			{
				Line holeShot = new Line();
				holeShot.setStartX(newLine.getEndX());
				holeShot.setStartY(newLine.getEndY());

				double xPos = 0;
				double yPos = 0;

				if(pocketNumber == 1)
				{
					xPos = 265;
					yPos = 164;
				}
				else if(pocketNumber == 2)
				{
					xPos = 265;
					yPos = 483.68;
				}
				else if(pocketNumber == 3)
				{
					xPos = 888;
					yPos = 164;
				}
				else if(pocketNumber == 4)
				{
					xPos = 888;
					yPos = 483.68;
				}
				else if(pocketNumber == 5)
				{
					xPos = 576.5;
					yPos = 160;
				}
				else if(pocketNumber == 6)
				{
					xPos = 576.5;
					yPos = 487.68;
				}

				holeShot.setEndX(xPos);
				holeShot.setEndY(yPos);

			}


		}

	}

	private Point2D getIntersection(Line line, int hitVal)
	{
		double m = (line.getEndY() - line.getStartY()) / (line.getEndX() - line.getStartX());
		double x = 0;
		double y = 0;



		if (hitVal ==  0)
		{
			//intersecting left border, getting intersection for x = 264
			//y = m(x - xo) + yo
			x = 276;
			y = m * (x - line.getStartX()) + line.getStartY();


		}

		if (hitVal ==  1)
		{
			//intersecting right border, getting intersection for x = 888
			//y = m(x - xo) + yo
			x = 876;
			y = m * (x - line.getStartX()) + line.getStartY();

		}

		if (hitVal ==  2)
		{
			//intersecting top border, getting intersection for y = 164
			//x = (y - yo)/m + xo
			y = 176;
			x = ((y - line.getStartY())/m) + line.getStartX();
		}

		if (hitVal ==  3)
		{
			//intersecting bottom border, getting intersection for y = 483
			//x = (y - yo)/m + xo
			y = 471;
			x = ((y - line.getStartY())/m) + line.getStartX();
		}

		Point2D intersection = new Point2D(x, y);
		return intersection;
	}

	private int hitEdge(Line line)
	{

		double leftBorder = 276; //xPos
		double rightBorder = 876; //xPos
		double topBorder = 176; //yPos
		double bottomBorder = 471; //yPos

		/*
		 	0 = left border
			1 = right border
			2 = top border
			3 = bottom border
		   -1 = no hit
		*/

		double endX = line.getEndX();
		double endY = line.getEndY();

		if (endX >= rightBorder)
		{
			return 1;
		}

		if( endX <= leftBorder)
		{
			return 0;
		}

		if (endY >= bottomBorder)
		{
			return 3;
		}
		if (endY <= topBorder)
		{
			return 2;
		}

		return -1;

	}

	private boolean hitPocket(double xStart, double yStart, double xEnd, double yEnd)
	{
		double a = yStart - yEnd;
		double b = xEnd - xStart;
		double c = (xStart - xEnd) * yStart + (yEnd - yStart) * xStart;

		double x = 265;
		double y = 164;
		double d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(xStart, yStart, xEnd, yEnd, x, y))
		{
			pocketNumber = 1;
			return true;
		}

		x = 265;
		y = 483.68;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(xStart, yStart, xEnd, yEnd, x, y))
		{
			pocketNumber = 2;
			return true;
		}

		x = 888;
		y = 164;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(xStart, yStart, xEnd, yEnd, x, y))
		{
			pocketNumber = 3;
			return true;
		}

		x = 888;
		y = 483.68;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(xStart, yStart, xEnd, yEnd, x, y))
		{
			pocketNumber = 4;
			return true;
		}

		x = 576.5;
		y = 160;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(xStart, yStart, xEnd, yEnd, x, y))
		{
			pocketNumber = 5;
			return true;
		}

		x = 576.5;
		y = 487.68;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(xStart, yStart, xEnd, yEnd, x, y))
		{
			pocketNumber = 6;
			return true;
		}

		return false;
	}

	private boolean isBetween(double x1, double y1, double x2, double y2, double x3, double y3)
	{
		boolean xBetween = false;
		boolean yBetween = false;

		double distance = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
		if (distance <= 18) return true;

		if (x1 >= x2)
		{
			if(x1 >= x3 && x3 >= x2) xBetween = true;
		}
		else
		{
			if(x2 >= x3 && x3 >= x1) xBetween = true;
		}

		if (y1 >= y2)
		{
			if(y1 >= y3 && y3 >= y2) yBetween = true;
		}
		else
		{
			if(y2 >= y3 && y3 >= y1) yBetween = true;
		}

		if(xBetween && yBetween) return true;
		else return false;
	}

	/**
	 * @return the cue ball object
	 */
	public Circle getBall() {
		return ball;
	}

	/**
	 * @return the direction line shown when player is lining up a shot
	 */
	public Line getDirectionLine() {
		return directionLine;
	}
}