package view.state;

import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
abstract public class AbstractMenuViewState extends AbstractViewState {
    private JPanel panel;

    public AbstractMenuViewState() {
        super();
        
        // Config panel
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        // Background
        //this.bg = this.spriteHandler.get(BG).getImage();
        
        // Blank panel
        this.panel = new JPanel();
        //this.panel.setMaximumSize(new Dimension(0, (500/5)-this.logo.getHeight()));
        this.panel.setOpaque(false);
        
        // Add to panel
        this.add(this.panel);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        //g.drawImage(bg, 0, 0, null);
    }

}
