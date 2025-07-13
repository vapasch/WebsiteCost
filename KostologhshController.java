import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class KostologhshController  {
    
    //fxml ids
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBox<String> typoscomboid;
    @FXML private ComboBox<String> designcomboid;
    @FXML private ComboBox<String> glwssescomboid;
    @FXML private Button ypologismostimhsid;
    @FXML private Label emfanishtimhsid;
    @FXML private CheckBox synthshshcheckid;
    @FXML private ListView<String> listaid;
    @FXML private Button editid;
    @FXML private Button delid;
    
    //pinakes gia tis epiloges
    String [] typosStoixeia = {"Προσωπική", "εταιρική", "e-shop"};
    String [] designStoixeia = {"Έτοιμο Τemplate", "custom"};
    String [] glwssesStoixeia = {"1 γλώσσα" ,"2 γλώσσες","3 γλώσσες"};
    String synthrhsh1Etos;
    String selected = "null";

    //times
    int[] typosTimh = {200, 400, 800};
    int[] designTimh = {100, 300};
    int[] glwssesTimh = {200, 400, 800};
    
    //metavlhtes
    int totalPoso = 0;
    String inputLista;


    
@FXML void initialize() {
    typoscomboid.getItems().addAll(typosStoixeia);
    designcomboid.getItems().addAll(designStoixeia);
    glwssescomboid.getItems().addAll(glwssesStoixeia);

    // Apenergopoioyme ta koympia kai meta vazoyme ena listener wste na energopoihthoyn otan kanoyme click se mia seira ths listas
    delid.setDisable(true);
    editid.setDisable(true);

    // listener 
    listaid.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
        boolean hasSelection = newVal != null;
        delid.setDisable(!hasSelection);
        editid.setDisable(!hasSelection);
    });
}


    
    
    
    //O ypologismos gia to kostologio
    @FXML void ypologismos_kostologioy(ActionEvent event) {
        totalPoso = 0;
        //xrhmata apo ton typo
        if (typoscomboid.getValue().equals("Προσωπική")) {
            totalPoso += typosTimh[0];
        }
        else if (typoscomboid.getValue().equals("εταιρική")){
            totalPoso += typosTimh[1];
        }
        else{
            totalPoso += typosTimh[2];
        }

    //xrhmata apo to design
    if(designcomboid.getValue().equals("Έτοιμο Τemplate")){
        totalPoso += designTimh[0];
    }
    else{
        totalPoso += designTimh[1];
    }

    //xrhmata apo tis glwsses
    if(glwssescomboid.getValue().equals("1 γλώσσα")){
        totalPoso += glwssesTimh[0];
    }
    else if (glwssescomboid.getValue().equals("2 γλώσσες")){
        totalPoso += glwssesTimh[1];
    }
    else{
        totalPoso += glwssesTimh[2];
    }


    //kostos synthrhsh enos etoys
    if (synthshshcheckid.isSelected()) { //an den exei kanei select apla den prostetoyme tipota
    totalPoso += 300; 
    synthrhsh1Etos = "Με συντήρηση";
    }
    else{
        synthrhsh1Etos = "Χωρίς συντήρηση";
    }


    emfanishtimhsid.setText(String.valueOf(totalPoso)); //allazoyme to onoma toy label apo 0 sto poso poy shmplhrwthke

    // Vazoyme sthn lista thn synallagh
    inputLista = typoscomboid.getValue() + " | " + designcomboid.getValue() + " | " + glwssescomboid.getValue() + " | " +synthrhsh1Etos + " | " + " Σύνολο: " + totalPoso + "€" ;
    listaid.getItems().add(inputLista);

     Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Προσθήκη");
    alert.setHeaderText(inputLista);
    alert.setContentText("Προστέθηκε η παραπάνω συναλλαγή");
    alert.showAndWait();

    selected = "null";//reset
}


    @FXML
    void delLista(ActionEvent event) {
        selected = listaid.getSelectionModel().getSelectedItem(); //otan kanoyme click panw sthn seira thn kanoyme select
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Επιβεβαίωση Διαγραφής");
        confirm.setHeaderText(selected);
        confirm.setContentText("Είσαι σίγουρος ότι θέλεις να διαγράψεις αυτή τη κοστολόγηση");
        Optional<ButtonType> result = confirm.showAndWait();
        delid.setDisable(false);
        
        if (result.get() == ButtonType.OK){
            listaid.getItems().remove(selected);
    
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Επιτυχία Διαγραφής");
            alert.setHeaderText(selected);
            alert.setContentText("Το παραπάνω κοστολόγιο διαγράφτηκε με επιτυχία");
            alert.showAndWait();
            delid.setDisable(true);

            selected = "null";//reset
        }      
    }
        
    

    @FXML
    void editLista(ActionEvent event) {
        selected = listaid.getSelectionModel().getSelectedItem(); //otan kanoyme click panw sthn seira thn kanoyme select
        
        //xwrizoyme se 5 part tthn seira me meso diaxwrishs to "|". Sthn synexeia afairoyme ta kena
        String[] part = selected.split("\\|");
        int partLength = part.length;
        for (int i = 0; i < partLength; i++){
            part[i] = part[i].trim();
        }
    
        // combobox gia typo
        ComboBox<String> typosCB = new ComboBox<>();
        typosCB.getItems().addAll(typosStoixeia);
        typosCB.setValue(part[0]);

        // combobox gia design
        ComboBox<String> designCB = new ComboBox<>();
        designCB.getItems().addAll(designStoixeia);
        designCB.setValue(part[1]);

        // combobox gia glwsses
        ComboBox<String> glwssesCB = new ComboBox<>();
        glwssesCB.getItems().addAll(glwssesStoixeia);
        glwssesCB.setValue(part[2]);

        // checkbox gia synthrhsh
        CheckBox synthCheck = new CheckBox("Με συντήρηση");
        synthCheck.setSelected(part[3].equals("Με συντήρηση"));

        // alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Επεξεργασία");
        alert.setHeaderText("Αλλαγή στοιχείων");

        // episrtefoyme ta pedia sta paratyra
        VBox box = new VBox(10, typosCB, designCB, glwssesCB, synthCheck);
        alert.getDialogPane().setContent(box);

        // periptwsh poy pathsei ok
        alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            // ypologizoyme thn nea timh
            int total = 0;

            if (typosCB.getValue().equals("Προσωπική")) total += typosTimh[0];
            else if (typosCB.getValue().equals("εταιρική")) total += typosTimh[1];
            else total += typosTimh[2];

            if (designCB.getValue().equals("Έτοιμο Τemplate")) total += designTimh[0];
            else total += designTimh[1];

            if (glwssesCB.getValue().equals("1 γλώσσα")) total += glwssesTimh[0];
            else if (glwssesCB.getValue().equals("2 γλώσσες")) total += glwssesTimh[1];
            else total += glwssesTimh[2];

            String synth = synthCheck.isSelected() ? "Με συντήρηση" : "Χωρίς συντήρηση";
            if (synthCheck.isSelected()) total += 300;

            // ftiaxnoyme thn nea grammh
            String newLine = typosCB.getValue() + " | " + designCB.getValue() + " | " + glwssesCB.getValue() + " | " + synth + " | Σύνολο: " + total + "€";

            // Enhmerwnoyme thn lista
            listaid.getItems().remove(selected);
            listaid.getItems().add(newLine);
            emfanishtimhsid.setText(String.valueOf(total));
        }
    });

    }

   




}


