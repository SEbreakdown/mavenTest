package de.se.tinf11b3.breakdown;

import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * Server side component for the VDrawCanvas widget.
 */
@com.vaadin.ui.ClientWidget(de.se.tinf11b3.breakdown.client.ui.VCanvas.class)
public class Canvas extends AbstractComponent {

	private String message = "Click here.";
	private int clicks = 0;
	private String debug_text = "DEBUG:";

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		// Paint any component specific content by setting attributes
		// These attributes can be read in updateFromUIDL in the widget.
		target.addAttribute("clicks", clicks);
		target.addAttribute("message", message);
		
		// We could also set variables in which values can be returned
		// but declaring variables here is not required
		target.addVariable(this,"debug", debug_text);
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

		// Variables set by the widget are returned in the "variables" map.
//
		if(variables.containsKey("debug")) {
			System.out.println(variables.get("debug"));
		}
//		
		
		if(variables.containsKey("click")) {

			// When the user has clicked the component we increase the 
			// click count, update the message and request a repaint so 
			// the changes are sent back to the client.
			clicks++;
			message += "<br/>" + variables.get("click");
			requestRepaint();
		}
	}

}
