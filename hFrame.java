package Hangman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class hFrame extends JFrame
{
	private ButtonHandler buttonHandler = new ButtonHandler();
	private boolean running = false;
	private boolean win;
	private static int wordIdx = 0;
	private int triesLeft;
	private String buttonValue;
	private String word;
	String[] charIdx;
	boolean[] flag = new boolean[16];
	private String[] wordList = {   "sisyphus",
									"prometheus",
									"space ghost",
									"L'Etranger",
									"cosmo kramer",
									"speedforce",
									"saint west",
									"maison margiela",
									"demeulemeester",
									"deluxe hamburger"};
	private JButton start;
	private JButton quit;
	private JButton[] keyboard;
	private JLabel welcome;
	private JLabel xs;
	private JLabel tries;

	private static hFrame frame;
	private static Container pane;

	public hFrame(String name)
	{
		super(name);
		Dimension dim;
		pane = getContentPane();

		setLayout(null);

		Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
		Font xsFont = new Font(Font.SERIF, Font.BOLD, 25);
		Font titleFont = new Font(Font.SERIF, Font.BOLD, 35);
		Insets insets = pane.getInsets();

		welcome = new JLabel("Welcome to Hangman!", SwingConstants.CENTER);
		welcome.setFont(titleFont);
		welcome.setVerticalAlignment(SwingConstants.CENTER);
		welcome.setBounds(insets.left, 30 + insets.top, 450, 45);
		add(welcome);

		xs = new JLabel("X X X X X X X X X X X X X X X X", SwingConstants.CENTER);
		xs.setFont(xsFont);
		xs.setVerticalAlignment(SwingConstants.CENTER);
		xs.setBounds(insets.left, 105 + insets.top, 450, 30);
		add(xs);

		tries = new JLabel("Tries Left: 10");
		tries.setVisible(false);
		tries.setBounds(185 + insets.left, 362 + insets.top, 200, 30);
		add(tries);

		start = new JButton("Start Game");
		dim = start.getPreferredSize();
		start.setBounds(10 + insets.left, 365 + insets.top, dim.width, dim.height);
		start.addActionListener(buttonHandler);
		start.setToolTipText("Starts The Game Ya Dingus");
		add(start);

		quit = new JButton("Stop Game");
		dim = quit.getPreferredSize();
		quit.setBounds(10 + insets.left, 365 + insets.top, dim.width, dim.height);
		quit.addActionListener(buttonHandler);
		add(quit);

		int down = 200;
		int[] keyHPos = {0, 50, 100, 150, 200, 250, 300, 350, 400, 0, 50, 100, 150, 200, 250, 300, 350, 400, 25, 75,
				125, 175, 225, 275, 325, 375};
		keyboard = new JButton[26];

		for (int i = 0; i < keyboard.length; i++)
		{
			if (i == 9 | i == 18)
				down += 40;

			keyboard[i] = new JButton(Character.toString((char) (i + 65)));
			keyboard[i].addActionListener(buttonHandler);

			add(keyboard[i]);
			keyboard[i].setFont(buttonFont);
			dim = keyboard[i].getPreferredSize();

			keyboard[i].setBounds(3 + keyHPos[i], down + insets.top, 43, 30);
		}
	}

	private String resetStr(String[] word, boolean[] flag)
	{
		int length = word.length;
		String newWord = "";
		String[] temp = new String[length];

		if (running == false)
		{
			for (int i = 0; i < 16; i++)
			{
				newWord = "X X X X X X X X X X X X X X X X";
			}
		}
		else if (running == true)
		{
			for (int i = 0; i < length; i++)
			{
				if (flag[i] == true)
				{
					temp[i] = word[i];
				}
				if (flag[i] == false)
				{
					temp[i] = "-";
				}
				newWord += (temp[i] + " ");
			}
		}
		return newWord;
	}

	public class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton Button = (JButton) e.getSource();
			String cmd = e.getActionCommand();
			xs.setForeground(Color.BLACK);

			if (cmd.equals("Stop Game"))
			{
				running = false;
				frame.repaint();
				start.setVisible(true);
				quit.setVisible(false);
				tries.setVisible(false);
				for (JButton key : keyboard)
				{
					key.setVisible(true);
				}
				tries.setText("Tries Left: 10");
				xs.setText("X X X X X X X X X X X X X X X X");
			}
			if (cmd.equals("Start Game"))
			{
				running = true;
				frame.repaint();
				start.setVisible(false);
				quit.setVisible(true);
				tries.setVisible(true);
				startGame();

				System.out.println(word);
			}
			if(cmd.equals("Start Game") != true && cmd.equals("Stop Game") != true)
			{
				if (running == true && triesLeft != 0  && win == false)
				{
					Button.setVisible(false);
					buttonValue = cmd;
					int correct = 0;
					int blanks = 0;

					for (int i = 0; i < charIdx.length; i++)
					{
						if (charIdx[i].equalsIgnoreCase(cmd))
						{
							correct++;
							flag[i] = true;
						}
					}
					if (correct == 0)
					{
						triesLeft--;
					}

					tries.setText("Tries Left: " + triesLeft);

					if(resetStr(charIdx, flag).contains("-") == false)
					{
						win = true;
					}
					xs.setText(resetStr(charIdx, flag));
				}

				if (running == true && triesLeft == 0 && win == false)
				{
					xs.setForeground(Color.RED);
					tries.setForeground(Color.RED);
				}
				if (running == true && triesLeft != 0 && win == true)
				{
					xs.setForeground(Color.GREEN);
					tries.setForeground(Color.GREEN);
				}
			}
		}
	}

	public void startGame()
	{
		triesLeft = 10;
		win = false;

		if(wordIdx == 10)
		{
			wordIdx = 0;
		}

		word = wordList[wordIdx].toUpperCase();
		charIdx = word.split("(?!^)");

		for (int i = 0; i < charIdx.length; i++)
		{
			if (charIdx[i].equalsIgnoreCase(" ") || charIdx[i].equalsIgnoreCase("'"))
			{
				flag[i] = true;
			}
			else
			{
				flag[i] = false;
			}
		}

		xs.setText(resetStr(charIdx, flag));
		wordIdx++;
	}

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
