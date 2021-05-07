  package application;

  import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
  import javafx.scene.media.MediaPlayer;
  import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Game extends Application
{	
	private static Pane root;
	Stage currStage;
	
	private List<GameObject> bullets = new ArrayList<>();
	private List<GameObject> enemies = new ArrayList<>();
	
	double xVel = 0;
	double yVel = 0;
	

	int levelMax=6;
	int currentLevel=0;

	int factor = 2;	
	int enemiesKilled=0;
	
	Text test= new Text();
	Text test2= new Text();
	





	
	private GameObject player;
	
	
	////private GameObject LOL;
	
	
	private Parent createContent()
	{
		//creating the scene with root. add all things to screen with root
		root = new Pane();
		root.setPrefSize(1280,630);
		
		//this is the background
		Image imgPod = new Image(getClass().getResourceAsStream("Dumbbell-Nebula.jpg"));
		ImageView ivPod = new ImageView(imgPod);
		root.getChildren().add(ivPod);
		

		//accessing the classes		
		player = new Player();
		
		try {
	        Rectangle rect = new Rectangle(49,35);
	        Image image = new Image("application/HarveyFace.jpg");
	        ImagePattern image_pattern = new ImagePattern(image);
	        rect.setFill(image_pattern);
	        player.setNode(rect); 

	    }catch(Exception e) {
	        e.printStackTrace();
	        System.out.println("Invalid Path");
	    }
		
		//lol = new Harveybot();
		
		test.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));

		test.setStroke(Color.CYAN);  
		test.setX(40);
		test.setY(100);

		test2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));

		test2.setStroke(Color.CYAN);  
		test2.setX(1000);
		test2.setY(100);
		
		root.getChildren().add(test);
		root.getChildren().add(test2);

		

		//----------------------------------------------------------------------------------


	      
	      
	     //----------------------------------------------------------------------------------
		//player.setVelocity(new Point2D(2,0));
		
		addGameObject(player, 300, 300);
		//addGameObject(lol,300,300);
		
		AnimationTimer timer = new AnimationTimer()
				{
				@Override
					public void handle(long now)
					{
					if(currentLevel == levelMax) {
						stop();
						//4 has 3 levels. one less than the level max
						StackPane menu = new StackPane();
						Label label = new Label("CONGRATULATIONS! YOU WON!");
						label.setMaxWidth(Double.MAX_VALUE);
						AnchorPane.setLeftAnchor(label, 0.0);
						AnchorPane.setRightAnchor(label, 0.0);
						label.setAlignment(Pos.CENTER);
						label.setFont(new Font("Times New Roman", 60));
						menu.getChildren().add(label);
						currStage.setScene(new Scene(menu));
						currStage.show();
						//Platform.exit();
					}
					else {
						onupdate();
					}
					}
				};
				timer.start();


	
		
	return root;
	}
	//classes for game objects
	//***********************************************************************************************************************************************************************************************************//
	private static class Player extends GameObject 
	{
		Player()
		{
			super(new Rectangle(49,15,Color.CYAN));
			//Image image = new Image("HarveyFace.jpg");
		}
	}
	public void addBullet(GameObject bullet, double x, double y)
	{
		bullets.add(bullet);
		addGameObject(bullet,x,y);
		
	}
	public void addEnemy(GameObject enemy, double x, double y)
	{
		enemies.add(enemy);
		addGameObject(enemy,x,y);
		
	}
	private void addGameObject(GameObject object, double x, double y)
	{
		object.getView().setTranslateX(x);
		object.getView().setTranslateY(y);
		root.getChildren().add(object.getView());
	}

	private static class Enemy extends GameObject
	{
		Enemy()
		{
			super(new Circle(15,15,15,Color.RED));
		}
	}
	private static class Bullet extends GameObject
	{
		Bullet()
		{
			super(new Circle(5,5,5,Color.GREEN));
		}
	}
	public static class Harveybot extends GameObject
	{
		Harveybot()
		{
			super(new Image("HarveyFace.jpg"));			
		}
	}
	//*******************************************************************************************************************************************************************************************************//
	private void onupdate()
	{

	      
		for (GameObject bullet : bullets)
		{
			for(GameObject enemy : enemies)
			{
				if (bullet.isColliding(enemy))
				{
					bullet.setAlive(false);
					enemy.setAlive(false);
					if(enemy.isDead())
					enemiesKilled +=1;
					System.out.print("Killed: "+enemiesKilled);
					root.getChildren().removeAll(bullet.getView(), enemy.getView());
					test2.setText("Kills: "+enemiesKilled); 
					test.setText("Level: "+currentLevel); 
				}
			}
		}
		for (GameObject enemy : enemies)
		{
			boolean hit = false;
			int hits = 0;
			if(player.isColliding(enemy))
			{
				root.getChildren().removeAll(enemy.getView());
				hit = true;
				if(hit=true)
				System.out.print("HIT");
				//root.getChildren().removeAll(player.getView(), enemy.getView());
				System.out.print("GAME OVER");
				Platform.exit();
			}

		}

		bullets.removeIf(GameObject::isDead);
		enemies.removeIf(GameObject::isDead);
		bullets.forEach(GameObject::update);
		enemies.forEach(GameObject::update);
		player.update();
		//LOL.update();
		
		
		if(currentLevel <= levelMax)
		{
			if(enemies.isEmpty())
			{
				factor +=10;
				System.out.print("Level"+ currentLevel);
				for(int j=0;j<factor;j++)
				{
				addEnemy(new Enemy(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
				}
				currentLevel +=1;
				test2.setText("Kills: "+enemiesKilled); 
				test.setText("Level: "+currentLevel); 
			}
		}

	}


	/*
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		


		stage.setScene(new Scene(createContent()));
		currStage = stage;
		
		//Image imgPod2 = new Image(getClass().getResourceAsStream("HarveyFace.jpg"));
		//ImageView ivPod2 = new ImageView(imgPod2);

		//ivPod2.setX(hX);
		//ivPod2.setY(hY);
		
		//root.getChildren().add(ivPod2);

        //


      
		stage.getScene().setOnKeyPressed(e ->
		{
			if(e.getCode()== KeyCode.LEFT)
			{
			player.rotateLeft();

			}
			else if(e.getCode() == KeyCode.RIGHT)
			{
			player.rotateRight();

			}
			if (e.getCode() == KeyCode.SPACE)
			{
				Bullet bullet = new Bullet();
				bullet.setVelocity(player.getVelocity().normalize().multiply(25));
				addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
			}
			if(e.getCode() == KeyCode.D)
			{
				xVel += 5;
				player.setVelocity(new Point2D(xVel,yVel));
				//LOL.setVelocity(new Point2D(xVel,yVel));
				hX += 100;
		//		ivPod2.setX(hX);
		//		ivPod2.setY(hY);
			}
			if(e.getCode() == KeyCode.A)
			{
				xVel -= 5;
				player.setVelocity(new Point2D(xVel,yVel));
				//LOL.setVelocity(new Point2D(xVel,yVel));
				hX -= 100;
		//		ivPod2.setX(hX);
 		//		ivPod2.setY(hY);
			}
			if(e.getCode() == KeyCode.S)
			{
				yVel += 5;
				player.setVelocity(new Point2D(xVel,yVel));
				//LOL.setVelocity(new Point2D(xVel,yVel));
				hY +=100;
				//		ivPod2.setX(hX);
		 		//		ivPod2.setY(hY);
			}
			if(e.getCode() == KeyCode.W)
			{
				yVel -= 5;
				player.setVelocity(new Point2D(xVel,yVel));
				//LOL.setVelocity(new Point2D(xVel,yVel));
				hY -=100;
				//		ivPod2.setX(hX);
		 		//		ivPod2.setY(hY);
			}	
		});
		
 
		 
		
  

		stage.show();		
	}
	*/
	
	
	@Override
	public void start(Stage stage) 
	{

		try {
			currStage = stage;
			StackPane menu = new StackPane();
			menu.setPrefHeight(150);
			Button button1 = new Button("START GAME!");
			button1.setTranslateY(40);
			button1.autosize();
			Label label = new Label("STEVE HARVEY SPACE BATTLE");
			label.setMaxWidth(Double.MAX_VALUE);
			AnchorPane.setLeftAnchor(label, 0.0);
			AnchorPane.setRightAnchor(label, 0.0);
			label.setAlignment(Pos.TOP_CENTER);
			label.setFont(new Font("Times New Roman", 30));
		
			
			
			

			


			
			button1.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			        stage.setScene(new Scene(createContent()));
			        stage.getScene().setOnKeyPressed(b ->
					{
						if(b.getCode()== KeyCode.LEFT)
						{
						player.rotateLeft();

						}
						else if(b.getCode() == KeyCode.RIGHT)
						{
						player.rotateRight();

						}
						if (b.getCode() == KeyCode.SPACE)
						{
							Bullet bullet = new Bullet();
							bullet.setVelocity(player.getVelocity().normalize().multiply(25));
							addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
						}
						if(b.getCode() == KeyCode.D)
						{
							xVel += 1;
							player.setVelocity(new Point2D(xVel,yVel));  
						}
						if(b.getCode() == KeyCode.A)
						{
							xVel -= 1;
							player.setVelocity(new Point2D(xVel,yVel));
						}
						if(b.getCode() == KeyCode.S)
						{
							yVel += 1;
							player.setVelocity(new Point2D(xVel,yVel));
						}
						if(b.getCode() == KeyCode.W)
						{
							yVel -= 1;
							player.setVelocity(new Point2D(xVel,yVel));
						}
					
					});

			        stage.show();
			    }
			});
			//text.setText("The quick brown fox jumps over the lazy dog");

			menu.getChildren().add(label);
			menu.getChildren().add(button1);
			stage.setScene(new Scene(menu));
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
