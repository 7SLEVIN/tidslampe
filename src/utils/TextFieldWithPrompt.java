package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.FocusManager;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextFieldWithPrompt extends JTextField {
	
	private String placeholder;
	private Point offset;
	
	public TextFieldWithPrompt(int columns, String placeholder) {
		super(columns);
		this.placeholder = placeholder;
		this.offset = new Point(7,20);
	}
	
	@Override
	protected void paintComponent(java.awt.Graphics g) {
	    super.paintComponent(g);
	
	    if(getText().isEmpty() && ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)){
	        Graphics2D g2 = (Graphics2D)g.create();
	        g2.setBackground(Color.gray);
	        g2.setColor(Color.gray);
//	        g2.setFont(getFont().deriveFont(Font.ITALIC));
	        g2.drawString(this.placeholder, this.offset.x, this.offset.y); //figure out x, y from font's FontMetrics and size of component.
	        g2.dispose();
	    }
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}


	
}