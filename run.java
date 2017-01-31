import hFrame;

public class run()
{
	public static void main(String[] args)
	{
		frame = new hFrame("Hangman");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Insets insets = frame.getInsets();
		frame.setSize(450 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
}