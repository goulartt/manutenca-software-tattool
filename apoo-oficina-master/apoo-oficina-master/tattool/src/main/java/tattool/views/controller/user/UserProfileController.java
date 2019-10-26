package tattool.views.controller.user;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tattool.rest.consume.UserRest;
import tattool.util.UserSession;

public class UserProfileController implements Initializable{

    @FXML
    private Label userName;

    @FXML
    private Label userLogin;

    @FXML
    private Label userFunction;
    
    private UserRest rest = new UserRest();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userName.setText(UserSession.userLogado.getNome());
		userLogin.setText(UserSession.userLogado.getUsuario());
		userFunction.setText(UserSession.userLogado.getRole() == 1 ? "Admin" : "Funcionário");
	}

}
