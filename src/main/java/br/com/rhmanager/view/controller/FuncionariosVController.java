/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rhmanager.view.controller;

import br.com.rhmanager.TableView.FuncionarioTableView;
import br.com.rhmanager.bean.Funcionario;
import br.com.rhmanager.controller.CargoController;
import br.com.rhmanager.controller.FuncionarioController;
import br.com.rhmanager.util.Icons;
import br.com.rhmanager.vo.FuncionarioVOTable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lucas
 */
public class FuncionariosVController implements Initializable {

    /**
     * Initializes the controller class.
     */
    FuncionarioController funcionarioController = new FuncionarioController();
    FuncionarioTableView funcionarioTableView;
    CargoController cargoController;

    ObservableList<Funcionario> funcionarios;
    ObservableList<String> cargos;
    List<Funcionario> funcionario = new ArrayList<>();
    Thread carregarTb;

    @FXML
    private TableView<FuncionarioVOTable> tvFuncionarios;

    @FXML
    private TableColumn<FuncionarioVOTable, String> tcNome = new TableColumn<>();

    @FXML
    private TableColumn<FuncionarioVOTable, String> tcCPF = new TableColumn<>();

    @FXML
    private TableColumn<FuncionarioVOTable, String> tcCargo = new TableColumn<>();

    @FXML
    private TableColumn<FuncionarioVOTable, String> tcStatus = new TableColumn<>();

    @FXML
    private MenuItem btNovoFuncionario;

    @FXML
    private MenuItem btEditFuncionario;

    @FXML
    private MenuItem btExcluirFuncionario;

    @FXML
    private MenuItem btInfoFuncionario;

    @FXML
    private Menu mOptions;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCPF;

    @FXML
    private ComboBox<String> cbCargos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        mOptions.setGraphic(Icons.getIcon(Icons.ICON_MENU, 33));
        btNovoFuncionario.setGraphic(Icons.getIcon(Icons.ICON_ADD, 33));
        btInfoFuncionario.setGraphic(Icons.getIcon(Icons.ICON_INFO, 33));
        btExcluirFuncionario.setGraphic(Icons.getIcon(Icons.ICON_DELETE, 33));
        btEditFuncionario.setGraphic(Icons.getIcon(Icons.ICON_EDIT, 33));
        cargoController = new CargoController();

        funcionarioTableView = new FuncionarioTableView(tvFuncionarios, tcNome, tcCPF, tcCargo, tcStatus);

        cargos = FXCollections.observableArrayList();

        cargoController.preencherComboBoxDB(cargos, cbCargos);

        tfNome.setOnKeyPressed((KeyEvent kv)
                -> {
            final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
            if (ENTER.match(kv)) {
                //TODO HERE
                buscarFuncionario();

            }
        });

        tfCPF.setOnKeyPressed((KeyEvent kv)
                -> {
            final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
            if (ENTER.match(kv)) {
                //TODO HERE
                buscarFuncionario();

            }
        });

    }

    @FXML
    private void editarFuncionario() {
        funcionarioController.editarFuncionario(tvFuncionarios, new Stage());
    }

    @FXML
    private void novoFuncionario() {
        FormFuncionariosVController ffvc = new FormFuncionariosVController();
        Stage stage = new Stage();
        FXMLLoader fxml = new FXMLLoader();
        Parent root;
        try {
            root = fxml.load(getClass().getResource("/fxml/FormFuncionarios.fxml").openStream());
            ffvc = fxml.getController();
            //ffvc.initialize("LUCAS");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/formfuncionarios.css");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Formulário de Funcionários");
            stage.setScene(scene);
            fxml.setController(ffvc);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FuncionariosVController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void buscarFuncionario() {
        funcionario = funcionarioController.buscarFuncionarios(tfNome, tfCPF, cbCargos, funcionario);
        carregarTable();
    }

    Runnable carregarTabela = new Runnable() {
        @Override
        public void run() {
            funcionarioTableView.addAllDados(funcionario);
            funcionarioTableView.start();
        }
    };

    public void carregarTable() {
        carregarTb = new Thread(carregarTabela);
        carregarTb.start();
    }
}
