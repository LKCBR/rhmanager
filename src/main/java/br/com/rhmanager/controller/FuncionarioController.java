/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rhmanager.controller;

import br.com.rhmanager.bean.Funcionario;
import br.com.rhmanager.daoImpl.FuncionarioDAOImpl;
import br.com.rhmanager.util.AlertUtil;
import br.com.rhmanager.util.Icons;
import br.com.rhmanager.view.controller.FormFuncionariosVController;
import br.com.rhmanager.view.controller.FuncionariosVController;
import br.com.rhmanager.vo.FuncionarioVOTable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author lucas
 */
public class FuncionarioController {

    public List<Funcionario> listarFuncionarios() {
        FuncionarioDAOImpl funcionarioDaoImpl = new FuncionarioDAOImpl();

        return funcionarioDaoImpl.getAllFuncionarios();
    }

    public Funcionario getFuncionarioById(String idS) {
        Long id = Long.parseLong(idS);
        FuncionarioDAOImpl funcionarioDaoImpl = new FuncionarioDAOImpl();

        return funcionarioDaoImpl.getFuncionarioById(id);
    }

    public void editarFuncionario(TableView<FuncionarioVOTable> tvFuncionarios, Stage stage) {
        if (tvFuncionarios.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader();
            Funcionario funcionario = getFuncionarioById(tvFuncionarios.getSelectionModel().getSelectedItem().getId());
            Parent root;
            try {
                root = loader.load(FormFuncionariosVController.class.getResource("/fxml/FormFuncionarios.fxml").openStream());
                FormFuncionariosVController controller = loader.getController();
                controller.setFuncionario(funcionario);

                Scene scene = new Scene(root);

                stage.setTitle("Formulário do Funcionário " + funcionario.getNome());
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            AlertUtil.alertAtencao("Atenção", "Nenhum Funcionário Selecionado!", "Selecione o Funcionário que deseja editar na tabela.");
        }
    }

    public void validar(TextField tfNome) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(tfNome, Validator.createEmptyValidator("Campo Obrigatório"));
    }

}
