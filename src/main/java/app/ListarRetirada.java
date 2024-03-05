package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import modelo.VeiculoUsado;
import util.Dao;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListarRetirada {
    @FXML
    private ListView<VeiculoUsado> retiradas;

    private Dao<VeiculoUsado> dao;

    @FXML
    private void initialize() {
        dao = new Dao<>(VeiculoUsado.class);
        List<VeiculoUsado> listaRetirada = dao.listarTodos();
        retiradas.setItems(FXCollections.observableArrayList(listaRetirada));
        retiradas.setCellFactory(new Callback<>() {
            @Override
            public ListCell<VeiculoUsado> call(ListView<VeiculoUsado> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(VeiculoUsado retirada, boolean empty) {
                        super.updateItem(retirada, empty);
                        if (empty || retirada == null) {
                            setText(null);
                        } else {
                            String status = retirada.getDevolucao() != null ? "Devolvido" : "Em uso";
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String dataRetirada = retirada.getRetirada().format(formatter);
                            String dataDevolucao = retirada.getDevolucao() != null ? retirada.getDevolucao().format(formatter) : "N/A";
                            setText("Motorista: " + retirada.getMotorista().getNome() +
                                    ", Veículo: " + retirada.getVeiculo().getModelo() +
                                    ", Data de Retirada: " + dataRetirada +
                                    ", Data de Devolução: " + dataDevolucao +
                                    ", Status: " + status);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void voltarAoMenu() throws IOException {
        App.setRoot("menu");
    }
}
