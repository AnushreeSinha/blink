import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MyApplet extends JApplet  {
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 4316810066205324810L;

	public void init() {
	JButton browse = new JButton("Browse");
	browse.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new java.io.File("/"));
	chooser.setDialogTitle("Browse the folder to process");
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	chooser.setAcceptAllFileFilterUsed(false);
	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	System.out.println("getCurrentDirectory(): "+ chooser.getCurrentDirectory());
	System.out.println("getSelectedFile() : "+ chooser.getSelectedFile());
	}
	else {
	 System.out.println("No Selection ");
 }
	}
	});


	add(browse);


	}
	}


		


