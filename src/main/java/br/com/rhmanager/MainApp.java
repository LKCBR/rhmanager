package br.com.rhmanager;

import br.com.rhmanager.view.HomeView;
import br.com.rhmanager.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        new LoginView().start(null);
    }
    
    public static void main(String[] args) throws Exception {
        //HibernateUtil.getSession();
        launch(args);
        
    }
    
}
