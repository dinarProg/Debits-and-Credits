package View;

import Model.Credit;
import Model.DAL;
import Model.Debitor;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;


public class MainController extends Application {

    @FXML
    TableView<Debitor> tableDebitors = new TableView<>();
    TableColumn<Debitor, String> name = new TableColumn<>("Имя");
    TableColumn<Debitor, String> adress = new TableColumn<>("Адресс");
    TableColumn<Debitor, String> phone = new TableColumn<>("Телефон");
    TableColumn<Debitor, Date> data = new TableColumn<>("Дата регистрации");

    @FXML
    TextField text_name = new TextField();
    @FXML
    TextField text_adress = new TextField();
    @FXML
    TextField text_phone = new TextField();
    @FXML
    TextField text_date = new TextField();

    @FXML
    TableView<Credit> tableCredits = new TableView<>();
    @FXML
    TableView<Credit> tablePayments = new TableView<>();

    private Stage primaryStage;
    DAL dal = new DAL();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Банк менеджер");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("Main.fxml"));

        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void initialize(){
        try {
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            adress.setCellValueFactory(new PropertyValueFactory<>("adress"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            data.setCellValueFactory(new PropertyValueFactory<>("data"));

            tableDebitors.setItems(dal.GetAllDebitors());
            tableDebitors.getColumns().addAll(name, adress, phone, data);

            tableDebitors.getSelectionModel().selectedItemProperty().addListener(
                   (observable, oldValue, newValue) -> showCreditTableForDebitor(newValue));

            TableColumn<Credit, Double> amount = new TableColumn<>("Сумма кредита");
            TableColumn<Credit, Double> balance = new TableColumn<>("Остаток");
            TableColumn<Credit, Date> openData = new TableColumn<>("Дата открытия кредита");

            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            openData.setCellValueFactory(new PropertyValueFactory<>("openData"));

            ObservableList<Credit> credits= dal.GetAllCredits();


            tableCredits.setItems(dal.GetAllCredits());
            tableCredits.getColumns().addAll(amount, balance, openData);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Возникла ошибка при получении таблицы клиентов");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }

    }


    public void showCreditTableForDebitor(Debitor debitor) {
        try {
            if (tableDebitors.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Выберите кредит");
                alert.setHeaderText(null);
                alert.setContentText("Выберите кредит для просмотра платежей");

                alert.showAndWait();
                return;
            }
            showDebitorDetails(debitor);
            if (tableCredits.getItems().size() > 0) tableCredits.getItems().clear();
            if (tableCredits.getColumns().size() > 0) tableCredits.getColumns().clear();

            TableColumn<Credit, Double> amount = new TableColumn<>("Сумма кредита");
            TableColumn<Credit, Double> balance = new TableColumn<>("Остаток");
            TableColumn<Credit, Date> openData = new TableColumn<>("Дата открытия кредита");

            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            openData.setCellValueFactory(new PropertyValueFactory<>("openData"));

            ObservableList<Credit> credits= dal.GetAllCreditsForDebitor(debitor.getId());

            tableCredits.setItems(credits);
            tableCredits.getColumns().addAll(amount, balance, openData);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Возникла ошибка при получении таблицы кредитов");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    private void showDebitorDetails(Debitor debitor) {
        text_name.setText(debitor.getName());
        text_adress.setText(debitor.getAdress());
        text_phone.setText(debitor.getPhone());
        text_date.setText(debitor.getData().toString());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
