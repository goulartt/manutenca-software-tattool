package tattool.views.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.domain.model.Session;
import tattool.domain.modelfx.AgendamentoFX;
import tattool.rest.consume.SessionRest;
import tattool.util.ConvertModelToFX;
import tattool.util.JFXtrasSampleBase;
import tattool.views.controller.customer.CreateEditCustomerController;
import tattool.views.controller.service.CreateEditServiceController;

public class HomeController extends JFXtrasSampleBase implements Initializable {

	@FXML
	Label lblData = new Label();

	@FXML
	private JFXTreeTableView<AgendamentoFX> agendamentoTable;

	private SessionRest rest = new SessionRest();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblData.setText("Hoje é dia " + pegaData() + ".");
		createTableColumns();
		populateTable();
	}

	@FXML
	void createCustomer(ActionEvent event) {
		try {
			FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/create-edit.fxml"));
			BorderPane main = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
			viewLoader.setController(new CreateEditCustomerController(new Customer()));
			viewLoader.setRoot(main);
			main.getChildren().clear();
			viewLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void createService(ActionEvent event) {
		try {
			FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/create-edit.fxml"));
			BorderPane main = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
			viewLoader.setController(new CreateEditServiceController(new Service()));
			viewLoader.setRoot(main);
			main.getChildren().clear();
			viewLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void createNote(ActionEvent event) {

	}

	@FXML
	void updateNote(ActionEvent event) {

	}

	@FXML
	void deleteNote(ActionEvent event) {

	}

	private String pegaData() {
		java.util.Date d = new Date();
		return java.text.DateFormat.getDateInstance(DateFormat.LONG).format(d);
	}

	void createTableColumns() {
		
		JFXTreeTableColumn<AgendamentoFX, String> hour = new JFXTreeTableColumn<>("Data");
		JFXTreeTableColumn<AgendamentoFX, String> duration = new JFXTreeTableColumn<>("Duração");
		JFXTreeTableColumn<AgendamentoFX, String> price = new JFXTreeTableColumn<>("Preço");
		JFXTreeTableColumn<AgendamentoFX, String> client = new JFXTreeTableColumn<>("Cliente");

		// Colunas com largura responsiva

		hour.prefWidthProperty().bind(agendamentoTable.widthProperty().multiply(0.2));
		duration.prefWidthProperty().bind(agendamentoTable.widthProperty().multiply(0.4));
		price.prefWidthProperty().bind(agendamentoTable.widthProperty().multiply(0.2));
		client.prefWidthProperty().bind(agendamentoTable.widthProperty().multiply(0.2));

		hour.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<AgendamentoFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AgendamentoFX, String> param) {
						return param.getValue().getValue().hora;
					}
				});
		duration.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<AgendamentoFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AgendamentoFX, String> param) {
						return param.getValue().getValue().duracao;
					}
				});
		price.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<AgendamentoFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AgendamentoFX, String> param) {
						return param.getValue().getValue().preco;
					}
				});
		client.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<AgendamentoFX, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AgendamentoFX, String> param) {
						return param.getValue().getValue().cliente;
					}
				});

		agendamentoTable.getColumns().setAll(hour, duration, price, client);

	}

	/*
	 * ## POPULA A TABLE
	 */

	void populateTable() {
		ObservableList<AgendamentoFX> cashierSessions = FXCollections.observableArrayList();
		cashierSessions = FXCollections
				.observableArrayList(ConvertModelToFX.convertSessionToAgendamentoFX(rest.findAll()));

		final TreeItem<AgendamentoFX> root = new RecursiveTreeItem<AgendamentoFX>(cashierSessions,
				RecursiveTreeObject::getChildren);

		agendamentoTable.setRoot(root);
		agendamentoTable.setShowRoot(false);
	}

}
