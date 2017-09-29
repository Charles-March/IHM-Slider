package rangeslider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

public class RsUI extends BasicSliderUI {
	
	protected Rectangle max_thumb = null;
	//private boolean max_thumb_selected;
	//private boolean min_thumb_selected;
	private boolean drag_max;
	private boolean drag_min;

	/**
	 * Constructeur par défaut
	 * 
	 * @param slider
	 */
	public RsUI(JSlider slider) {
		super(slider);
	}

	@Override
	public void installUI(JComponent c)   {
        max_thumb = new Rectangle();
        super.installUI(c);
    }
	
	@Override
    protected void calculateThumbLocation() {
        super.calculateThumbLocation();
        if (slider.getSnapToTicks()) {
            int maxValue = slider.getValue() + slider.getExtent();
            int snappedValue = maxValue; 
            int tickSpacing = 0;

            if (tickSpacing != 0) {
                if ((maxValue - slider.getMinimum()) % tickSpacing != 0) {
                    float temp = (float)(maxValue - slider.getMinimum()) / (float)tickSpacing;
                    int whichTick = Math.round(temp);
                    snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
                }

                if (snappedValue != maxValue) { 
                	System.out.println("new val" + slider.getExtent());
                    slider.setExtent(snappedValue - slider.getValue());
                }
            }
        }
        int upperPosition = xPositionForValue(slider.getValue() + slider.getExtent());

		System.out.println("nouvelle valeur max : " + (slider.getValue() + slider.getExtent()));
        this.max_thumb.x = upperPosition - (this.max_thumb.width / 2);
        this.max_thumb.y = trackRect.y;
    }
	
	@Override
	public void calculateThumbSize() {
		this.max_thumb.setSize(this.thumbRect.width,
				this.thumbRect.height);
		super.calculateThumbSize();
	}
	
	@Override
	public void paint(Graphics graph, JComponent comp){
        super.paint(graph, comp);
		Rectangle knobBounds = this.max_thumb;
        int w = knobBounds.width;
        int h = knobBounds.height;   
        Graphics2D g2d = (Graphics2D) graph.create();
        Shape thumbShape = new Rectangle(this.getThumbSize().width,
        		this.getThumbSize().height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(knobBounds.x, knobBounds.y);
        g2d.draw(thumbShape);
        g2d.dispose();
	}
	
	public void setMaxThumbPosition(int x, int y){
		Rectangle newRec = new Rectangle();
		newRec.setBounds(this.max_thumb);
		max_thumb.setLocation(x, y);
		this.slider.repaint();
	}
	
	protected TrackListener createTrackListener(JSlider slider){
		return new RangeListener();
	}
	
    @Override
    protected ChangeListener createChangeListener(JSlider slider) {
        return new ChangeListenerRange();
    }
	
	public class RangeListener extends TrackListener {
		
		public void mousePressed(MouseEvent e){
			int mouse_x = e.getX();
			int mouse_y = e.getY();
			if (max_thumb.contains(mouse_x, mouse_y)){
				int diff = mouse_x - max_thumb.x;
				drag_max = true;
			}
			if (thumbRect.contains(mouse_x, mouse_y)){
				int diff = mouse_x - thumbRect.x;
				drag_min = true;
			}
		}
		
		public void mouseReleased(MouseEvent e){
			drag_max = false;
			drag_min = false;
		}
		
		
		public void mouseDragged(MouseEvent e) {
			int mouse_x = e.getX();
			int track_max = trackRect.x + trackRect.width- 1/2*max_thumb.width;
			int track_min = trackRect.x - 1*max_thumb.width;
			
			if (drag_max){
				int diff = mouse_x - max_thumb.x;
				if (mouse_x < track_max && mouse_x > track_min && mouse_x > thumbRect.x){
					slider.setValueIsAdjusting(true);
					setMaxThumbPosition(max_thumb.x+diff, max_thumb.y);
					slider.setExtent(xPositionForValue(slider.getValue())-slider.getValue());
					System.out.println("coucou : " + xPositionForValue( slider.getValue() +
                            slider.getExtent()));
					((RsCore)slider).setMaxValue(xPositionForValue( slider.getValue() +
                            slider.getExtent()));
					System.out.println("nouvelle valeur max : " + (((RsCore)slider).getMaxValue()));
				}
			}
			if (drag_min){
				System.out.println("valeur max du slider : " + slider.getMaximum());
				int diff = mouse_x - thumbRect.x;
				if (mouse_x < track_max && mouse_x > track_min && mouse_x < max_thumb.x){
					slider.setValueIsAdjusting(true);
					setThumbLocation(thumbRect.x+diff, thumbRect.y);
					System.out.println("nouvelle valeur min : " + (slider.getValue() ));
				}
			}
		}
	}
	
	public class ChangeListenerRange implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			calculateThumbLocation();
			slider.repaint();
			
		}
		
	}
}
