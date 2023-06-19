package br.com.senac.exercicioaluno.controller;

import br.com.senac.exercicioaluno.model.Aluno;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;

@Component
@FxmlView("/main-aluno.fxml")
public class AlunoController {
    @FXML
    private TextField nome;

    @FXML
    private TextField disciplina;

    @FXML
    private TextField nota1;

    @FXML
    private TextField nota2;

    @FXML
    private TextField nota3;

    @FXML
    private TableView<Aluno> tabelaAlunos;

    @FXML
    private TableColumn<Aluno, String> colunaNome;

    @FXML
    private TableColumn<Aluno, String> colunaDisciplina;

    @FXML
    private TableColumn<Aluno, String> colunaNota1;

    @FXML
    private TableColumn<Aluno, String> colunaNota2;

    @FXML
    private TableColumn<Aluno, String> colunaNota3;

    @FXML
    public void initialize() {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
        colunaNota1.setCellValueFactory(new PropertyValueFactory<>("nota1"));
        colunaNota2.setCellValueFactory(new PropertyValueFactory<>("nota2"));
        colunaNota3.setCellValueFactory(new PropertyValueFactory<>("nota3"));

        tabelaAlunos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/cadastro-aluno.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        Stage stage = new Stage();
                        stage.setTitle("Alterar");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void executarSalvar(){

        Alert alertInclusao = new Alert(Alert.AlertType.CONFIRMATION);
        alertInclusao.setTitle("Confirmação de Inclusão");
        alertInclusao.setHeaderText("Confirma inclusão de registro?");

        Optional<ButtonType> retornoAlerta = alertInclusao.showAndWait();

        if(retornoAlerta.get() == ButtonType.OK) {

            Aluno aluno = new Aluno();
            aluno.setNome(nome.getText());
            aluno.setDisciplina(disciplina.getText());

            try {
                aluno.setNota1(Double.parseDouble(nota1.getText()));
                aluno.setNota2(Double.parseDouble(nota2.getText()));
                aluno.setNota3(Double.parseDouble(nota3.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Teste FInally");
            }

            if (!aluno.getNome().equals("") && !aluno.getDisciplina().equals("")) {
                tabelaAlunos.getItems().add(aluno);
                this.limparCampos();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de Inclusão");
                alert.setHeaderText("Nome ou disciplina não informada!");

                alert.show();
            }
        }
    }

    public void executarExcluir() {

    }

    public void limparCampos(){
        nome.setText("");
        disciplina.setText("");
        nota1.setText("");
        nota2.setText("");
        nota3.setText("");
    }
}
