package app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import app.App;
import util.ExclusaoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import modelo.Motorista;
import modelo.Veiculo;
import modelo.VeiculoUsado;
import util.Dao;

public class ReritadaVeiculoController {

  @FXML
  private ComboBox<Motorista> motoristas;

  @FXML
  private ComboBox<Veiculo> veiculos;

  @FXML
  private DatePicker dateRetirada;

  private ObservableList<Motorista> getMotoristasOB;
  private ObservableList<Veiculo> getVeiculoOB;

  private List<Motorista> getMotoristas;
  private List<Veiculo> getVeiculo;

  private Dao<Motorista> daoMotorista;
  private Dao<Veiculo> daoVeiculo;
  private Dao<VeiculoUsado> daoVeiculoUsado;

  @FXML
  private void initialize() {
    daoMotorista = new Dao(Motorista.class);
    daoVeiculo = new Dao(Veiculo.class);
    daoVeiculoUsado = new Dao(VeiculoUsado.class);

    getMotoristas = daoMotorista.listarTodos();
    getVeiculo = daoVeiculo.listarTodos();

    getMotoristasOB = FXCollections.observableArrayList(getMotoristas);
    getVeiculoOB = FXCollections.observableArrayList(getVeiculo);

    motoristas.setItems(getMotoristasOB);
    veiculos.setItems(getVeiculoOB);
  }

@FXML
private void retirarVeiculo() {
    Motorista tempMotorista = motoristas.getSelectionModel().getSelectedItem();
    Veiculo tempVeiculo = veiculos.getSelectionModel().getSelectedItem();
    LocalDate date = dateRetirada.getValue();

    try {
        // Verifica se o veículo está disponível para retirada
        boolean veiculoDisponivel = verificarDisponibilidade(tempVeiculo);

        if (veiculoDisponivel) {
            VeiculoUsado veiculoUsado = new VeiculoUsado();
            veiculoUsado.setMotorista(tempMotorista);
            veiculoUsado.setVeiculo(tempVeiculo);
            veiculoUsado.setRetirada(date);
            veiculoUsado.setDevolucao(null);
            daoVeiculoUsado.inserir(veiculoUsado);

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Veículo retirado com sucesso!");
            alert.show();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Este veículo não está disponível para retirada.");
            alert.show();
        }
    } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Não foi possível retirar o veículo");
        alert.show();
    }
}

// Método para verificar se o veículo está disponível para retirada
private boolean verificarDisponibilidade(Veiculo veiculo) {
    for (VeiculoUsado retirada : daoVeiculoUsado.listarTodos()) {
        if (retirada.getVeiculo().equals(veiculo) && retirada.getDevolucao() == null) {
            return false; // Veículo já está em uso
        }
    }
    return true; // Veículo está disponível para retirada
}



  @FXML
  private void voltarAoMenu() throws IOException {
    App.setRoot("menu");
  }
}
