package rangeslider;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class DemoApp extends JPanel {
	private JLabel label_x_range_text = new JLabel();
    private JLabel label_x_range_value = new JLabel();
    private JLabel label_y_range_text = new JLabel();
    private JLabel label_y_range_value = new JLabel();
    private RsCore rangeSlider_x = new RsCore(0, 10);
    private RsCore rangeSlider_y = new RsCore(0, 10);
    
    /**
     * Constructeur par défaut
     * 
     */
    public DemoApp(){
    	setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        setLayout(new GridBagLayout());
        
        label_x_range_text.setText("Minimum:");
        label_y_range_text.setText("Maximum:");
        label_x_range_value.setHorizontalAlignment(JLabel.LEFT);
        label_y_range_value.setHorizontalAlignment(JLabel.LEFT);

        rangeSlider_x.setValue(3);
        rangeSlider_x.setMaxValue(7);
        
        System.out.println("value max : " + rangeSlider_x.getMaxValue());
        System.out.println("value : " + rangeSlider_x.getValue());
        
        add(label_x_range_text);
        add(rangeSlider_x);
        add(label_y_range_text);
        add(rangeSlider_y);
    }
    
	/**
	 * Methode de rendu visuel
	 */
	public void render(){
		JFrame frame = new JFrame();
        frame.setTitle("Application");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	/**
	 * Executable
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DemoApp().render();
            }
        });
    }

}
