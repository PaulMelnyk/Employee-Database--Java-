/**
 * @author PaulMelnyk
 * @serial StudentID 14008771
 * @version Programming Assignment
 */
public class GuiMain {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				MainForm gui = new MainForm(); //creates a form to load GUI
				gui.setVisible(true); //allows GUI to display
			}
		});
		
	}
}
