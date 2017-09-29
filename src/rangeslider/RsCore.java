package rangeslider;

import javax.swing.JSlider;

import rangeslider.RsUI;

@SuppressWarnings("serial")
public class RsCore extends JSlider {
	
	private int min_value;
	private int max_value;

	/**
	 * Constructeur par défaut, nécessite une spécification de valeur minimale et maximale du slider
	 * 
	 * @param min_value
	 * @param max_value
	 */
	public RsCore(int min_value, int max_value){
		this.setMaximum(min_value);
		this.setMaximum(max_value);
		this.setOrientation(HORIZONTAL);
	}
	
	@Override
    public void updateUI() {
        setUI(new RsUI(this));
        updateLabelUIs();
    }
	
	public void setValue(int value){
		if ( value < this.getValue() + this.getExtent()){
			int new_ext = this.getExtent()+this.getValue()-value;
			getModel().setRangeProperties(value, new_ext, getMinimum(), 
		            getMaximum(), getValueIsAdjusting());
		}
		
	}
	
	public void setMaxValue(int value){
		 if (!(this.getValue() < value)){
			 System.out.println("salut charles.");
			 setExtent(value-getValue());
		 }
	}
	
	public int getMaxValue() {
        return this.getValue() + this.getExtent();
    }
	
	
}
