/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testguiarduino;



import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Usuario
 */
public class TestGUIArduino extends Application {
    
    private String message = "";

    
    @Override
    public void start(Stage primaryStage) {

        
        StackPane root = new StackPane();
        root.setPadding(new Insets(10));
        VBox panel = new VBox(10);
        
        Label title = new Label("Registro");
        title.setFont(new Font(16));
        
        TableView<Registro> table = new TableView<>();
        ObservableList<Registro> list = FXCollections.observableArrayList(Data.getInstance().getRegs());

        table.setItems(list);
        
        TableColumn c1 = new TableColumn("ID");
        c1.setMinWidth(150);
        c1.setCellValueFactory(new PropertyValueFactory<Registro,String>("id"));
        
        TableColumn c2 = new TableColumn("Fecha");
        c2.setMinWidth(150);
        c2.setCellValueFactory(new PropertyValueFactory<Registro,String>("date"));

        
        table.getColumns().addAll(c1,c2);
        panel.getChildren().addAll(title,table);
        

        
        
                

        root.getChildren().add(panel);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Sistema de registro");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getWindow().setOnCloseRequest(event ->  {
                Data.GuardarDatos();
                System.exit(1);});
        
        PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
        
        SerialPortEventListener listener = new SerialPortEventListener() {

            @Override
            public void serialEvent(SerialPortEvent spe) {
                try {
                    if (arduino.isMessageAvailable()){
                        
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
                        String date = sdf.format(cal.getTime());
 
                        message = arduino.printMessage();             
                        String id = message;
                        Registro reg = new Registro(id,date);
                        Data.getInstance().registrar(id,reg);
                        table.getItems().add(reg);
                    }
                } catch (SerialPortException ex) {
                    Logger.getLogger(TestGUIArduino.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArduinoException ex) {
                    Logger.getLogger(TestGUIArduino.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        try {
            arduino.arduinoRX("/dev/ttyUSB0",9600,listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(TestGUIArduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(TestGUIArduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Data.CargarDatos();
        launch(args);
    }
    

    
}
