package application;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.geometry.Point2D;

public class GameObject 
{
	private Node view;
	private Point2D velocity = new Point2D(0,0);
	private boolean alive = true;
	private Image image;
	private int x, y;
	private int xVel;
	private int yVel;
	
	
	public GameObject(Node view)
	{
		this.view = view;
		
	}
	public GameObject(boolean add) {
		// TODO Auto-generated constructor stub
	}
	public GameObject(Image image2) {
		// TODO Auto-generated constructor stub
	}
	public void update()
	{
		view.setTranslateX(view.getTranslateX()+velocity.getX());
		view.setTranslateY(view.getTranslateY()+velocity.getY());
	}
	public boolean isAlive()
	{
		return alive;
	}
	public boolean isDead()
	{
		return !alive;
	}
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	public double getRotate()
	{
		return view.getRotate();
	}
	
	public void rotateRight()
	{
		view.setRotate(view.getRotate()+15);
		setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	}
	public void rotateLeft()
	{
		view.setRotate(view.getRotate()-15);
		setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
	}
	public void setVelocity(Point2D velocity)
	{
		this.velocity = velocity;
	}
	public Point2D getVelocity()
	{
		return velocity;
	}
	public Node getView()
	{
		return view;
	}
	public boolean isColliding(GameObject other)
	{
		return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
	}
	public void playerMove(Stage stage)
	{
		stage.getScene().setOnKeyPressed(e ->
		{
			if(e.getCode()== KeyCode.LEFT)
			{
			rotateLeft();

			}
			else if(e.getCode() == KeyCode.RIGHT)
			{
			rotateRight();

			}

		if(e.getCode() == KeyCode.D)
		{
			xVel += 1;
			setVelocity(new Point2D(xVel,yVel));

		}
		if(e.getCode() == KeyCode.A)
		{
			xVel -= 1;
			setVelocity(new Point2D(xVel,yVel));

		}
		if(e.getCode() == KeyCode.S)
		{
			yVel += 1;
			setVelocity(new Point2D(xVel,yVel));
		}
		if(e.getCode() == KeyCode.W)
		{
			yVel -= 1;
			setVelocity(new Point2D(xVel,yVel));
		}	
		});
	}
	public Node getNode() 
	{
        return view;
    }
    public void setNode(Node a) 
    {
        view = a;
    }
}
	

