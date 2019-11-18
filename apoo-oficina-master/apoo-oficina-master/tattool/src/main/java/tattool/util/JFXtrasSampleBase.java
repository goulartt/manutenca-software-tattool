package tattool.util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.util.Duration;
import jfxtras.animation.Timer;
import jfxtras.util.NodeUtil;

/**
 */
abstract public class JFXtrasSampleBase extends SampleBase {


	// if we ever find a better way of doing this...
	protected void showPopup(Node owner, String text) {
		Popup popup = new Popup();
		popup.setAutoFix(true);
		popup.setAutoHide(true);
		popup.setHideOnEscape(true);

		// popup contents
		Label label = new Label(text);
		label.setStyle("-fx-background-color: #FFFFFF;");
		popup.getContent().add(label);
		popup.show(owner, NodeUtil.screenX(owner), NodeUtil.screenY(owner));
		
		new Timer( () -> {
			popup.hide(); 
		}).withDelay(new Duration(1500)).start();  
	}

}
