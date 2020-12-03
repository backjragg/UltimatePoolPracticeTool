package application;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class CueBall {
	private Circle ball;
	private Line directionLine;
	private int pocketNumber;
	private boolean play = false;

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
			}
		});

		ball.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				directionLine.setOpacity(0);
				shootCue(directionLine.getEndX(), directionLine.getEndY());
			}
		});
	}

	private void shootCue(double xPos, double yPos) {
		pocketNumber = 0;
		PathTransition path = new PathTransition();
		path.setDuration(Duration.millis(1000));
		
		if(hitPocket(xPos, yPos))
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
				}
			});
		}
		else
		{
			path.setNode(ball);
			path.setPath(directionLine);
			path.play();
			ball.setCenterX(xPos);
			ball.setCenterY(yPos);
		}
		
	}
	
	private boolean hitPocket(double xPos, double yPos)
	{
		double a = ball.getCenterY() - yPos;
		double b = xPos - ball.getCenterX();
		double c = (ball.getCenterX() - xPos) * ball.getCenterY() + (yPos - ball.getCenterY()) * ball.getCenterX();
		
		double x = 265;
		double y = 164;
		double d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(ball.getCenterX(), ball.getCenterY(), xPos, yPos, x, y))
		{
			System.out.println(d);
			pocketNumber = 1;
			return true;
		}
		
		x = 265;
		y = 483.68;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(ball.getCenterX(), ball.getCenterY(), xPos, yPos, x, y))
		{
			pocketNumber = 2;
			return true;
		}
		
		x = 888;
		y = 164;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(ball.getCenterX(), ball.getCenterY(), xPos, yPos, x, y))
		{
			pocketNumber = 3;
			return true;
		}
		
		x = 888;
		y = 483.68;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(ball.getCenterX(), ball.getCenterY(), xPos, yPos, x, y))
		{
			pocketNumber = 4;
			return true;
		}
		
		x = 576.5;
		y = 160;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(ball.getCenterX(), ball.getCenterY(), xPos, yPos, x, y))
		{
			pocketNumber = 5;
			return true;
		}
		
		x = 576.5;
		y = 487.68;
		d = Math.abs((a*x + b*y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
		if(d <= 18 && isBetween(ball.getCenterX(), ball.getCenterY(), xPos, yPos, x, y))
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
	
	public Circle getBall() {
		return ball;
	}

	public Line getDirectionLine() {
		return directionLine;
	}
}