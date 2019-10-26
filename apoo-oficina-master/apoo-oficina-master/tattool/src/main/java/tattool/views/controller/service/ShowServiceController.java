package tattool.views.controller.service;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import tattool.domain.model.Art;
import tattool.domain.model.Session;
import tattool.domain.modelfx.ServiceFX;
import tattool.domain.modelfx.SessionFX;
import tattool.rest.consume.SessionRest;
import tattool.util.ConvertModelToFX;

public class ShowServiceController implements Initializable{
	
	@FXML
    private Label serviceName;

    @FXML
    private Label customerName;
    
    @FXML
    private Label serviceStatus;

    @FXML
    private Label numberSessions;

    @FXML
    private Label paidSessions;

    @FXML
    private JFXTreeTableView<SessionFX> sessionsTable;
    
    @FXML
    private JFXListView<ArtListItem> artsList;

    @FXML
    private OctIconView closeButton;

	private ServiceFX service = new ServiceFX();

	private List<Session> sessions = new ArrayList<>();
	
	private SessionRest rest = new SessionRest();

	private List<Art> artes = new ArrayList<>();
    
    public ShowServiceController(ServiceFX service2, List<Session> sessions, List<Art> artes) {
    	this.service = service2;
    	this.sessions  = sessions;
    	this.artes  = artes;
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTableColumns();
    	populateTable();
    	populateArtsList();
    	carregaCampo();
	}
	
	private void carregaCampo() {
		serviceName.setText(service.getName().get());
		customerName.setText(service.getCustomer().getName());
		serviceStatus.setText(service.getStatus().get());
		numberSessions.setText(service.getQuantSessions().toString());
		List<Session> pagos = sessions.stream().filter(s -> s.getStatus().equals("PAGO")).collect(Collectors.toList());
		Integer sessoesPagas = pagos.size();
		paidSessions.setText(sessoesPagas.toString());
		
	}
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<SessionFX,String> price    = new JFXTreeTableColumn<>("Preço");
    	JFXTreeTableColumn<SessionFX,String> date     = new JFXTreeTableColumn<>("Data");
    	JFXTreeTableColumn<SessionFX,String> duration = new JFXTreeTableColumn<>("Duração");
    	JFXTreeTableColumn<SessionFX,String> status   = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	price.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.3));
    	date.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.2));
    	duration.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.2));
    	status.prefWidthProperty().bind(sessionsTable.widthProperty().multiply(0.3));
    	
    	price.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().price;
			}
    	});
    	date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().date;
			}
    	});

    	duration.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().duration;
			}
    	});
    	status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionFX, String> param)
			{
				return param.getValue().getValue().status;
			}
    	});
    	
    	sessionsTable.getColumns().setAll(price, date, duration, status);
    }
    
    /*
     * 	##	POPULA A TABLE
     */
    
    void populateTable()
    {
    	ObservableList<SessionFX> sessions = FXCollections.observableArrayList();
    	
    	sessions = FXCollections.observableArrayList(ConvertModelToFX.convertListSessionToSessionFX(rest.findByService(service.getId())));
  
    	final TreeItem<SessionFX> root = new RecursiveTreeItem<SessionFX>(sessions, RecursiveTreeObject::getChildren);
    	
    	sessionsTable.setRoot(root);
    	sessionsTable.setShowRoot(false);
    }
    
    /*
     * 	##	POUPLA A LISTA DE ARTES
     */
    
    void populateArtsList() {
    	if (!artes.isEmpty()) {
    		for (Art art : artes) {
    			artsList.getItems().add(new ArtListItem(art));
    		}    		
    	} else {
			artsList.getItems().add(new ArtListItem());
    	}
    
    }
    
    /*
     * 	##	CLASSE DO ITEM DA LISTA DE ARTES
     */
    
    private class ArtListItem extends Label {
    	
    	ArtListItem(String text) {
    		setText(text);
    		MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.IMAGE);
    		icon.setGlyphSize(22);
    		setGraphic(icon);
    		setGraphicTextGap(10);
    		
    		getStyleClass().add("art-list-item");
    	}

		public ArtListItem(Art art) {
			setText(art.getDescription());
    		Image img = new Image(new ByteArrayInputStream(art.getImage()));
    		ImageView view2 = new ImageView(img);
    		view2.autosize();
    		view2.setFitHeight(250);
    		view2.setFitWidth(250);

    		setGraphic(view2);
    		setGraphicTextGap(10);
    		
    		getStyleClass().add("art-list-item");
		}

		public ArtListItem() {
    		setText("Nenhuma arte cadastrada!");
		}
    }
}
