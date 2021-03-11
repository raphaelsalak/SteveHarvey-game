import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255319694373975038L;
	
	public Window(int width, int height, String title, Game game)
	{
		//Frame of our window
		 JFrame frame = new JFrame(title);
		 frame.setPreferredSize(new Dimension(width, height));
		 frame.setMaximumSize(new Dimension(width, height));
		 frame.setMinimumSize(new Dimension(width, height));
		//Uses the escape button to close the game 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Can we resize a window? NO. it complicates things.
		 frame.setResizable(false);
		//our window starts in the center, instead of the top left 
		 frame.setLocationRelativeTo(null);
		//adds game class to the frame 
		 frame.add(game);
		//make sure you can see it
		 frame.setVisible(true);
		//run the game 
		 game.start();
		 
	}

}
