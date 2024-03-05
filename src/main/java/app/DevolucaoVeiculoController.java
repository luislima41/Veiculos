package app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import modelo.Motorista;
import modelo.Veiculo;
import modelo.VeiculoUsado;
import util.Dao;

public class DevolucaoVeiculoController {

  @FXML
  private ComboBox<VeiculoUsado> retiradas;

  private ObservableList<VeiculoUsado> getRetiradasOb;

  private List<VeiculoUsado> getRetiradas;

  @FXML
  private DatePicker dateDevolution;

  private Dao<VeiculoUsado> daoVeiculoUsado;

@FXML
private void initialize() {
    daoVeiculoUsado = new Dao(VeiculoUsado.class);

    // Filtra a lista para incluir apenas veículos que ainda não foram devolvidos
    getRetiradas = daoVeiculoUsado.listarTodos().stream()
                    .filter(retirada -> retirada.getDevolucao() == null)
                    .collect(java.util.stream.Collectors.toList());

    getRetiradasOb = FXCollections.observableArrayList(getRetiradas);

    retiradas.setItems(getRetiradasOb);
}



@FXML
private void retiradaDevolucao() {
    VeiculoUsado retirada = retiradas.getSelectionModel().getSelectedItem();
    LocalDate data = dateDevolution.getValue();

    try {
        retirada.setDevolucao(data);
        daoVeiculoUsado.alterar(retirada);

        // Remove o veículo devolvido da lista de retiradas
        getRetiradasOb.remove(retirada);

        // Atualiza a ComboBox com a lista atualizada
        retiradas.setItems(getRetiradasOb);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Veículo devolvido com sucesso!");
        alert.show();
    } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Não foi possível devolver o veículo");
        alert.show();
    }
}


    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menu");
    }

}
