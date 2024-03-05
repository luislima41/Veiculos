package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import modelo.Veiculo;
import util.Dao;

import java.io.IOException;
import java.util.List;

public class ListarVeiculo {
    @FXML
    private ListView<Veiculo> veiculos;

    private Dao<Veiculo> dao;

    @FXML
    private void initialize() {
        dao = new Dao(Veiculo.class);
        List<Veiculo> listaVeiculos = dao.listarTodos();
        veiculos.setItems(FXCollections.observableArrayList(listaVeiculos));
        veiculos.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Veiculo> call(ListView<Veiculo> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Veiculo veiculo, boolean empty) {
                        super.updateItem(veiculo, empty);
                        if (empty || veiculo == null) {
                            setText(null);
                        } else {
                            setText("Marca: " + veiculo.getMarca() +
                                    ", Modelo: " + veiculo.getModelo() +
                                    ", Placa: " + veiculo.getPlaca());
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
