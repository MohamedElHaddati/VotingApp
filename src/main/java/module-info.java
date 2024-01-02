module com.example.votingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            
    opens com.example.votingapp to javafx.fxml;
    opens com.example.votingapp.Controllers;
    exports com.example.votingapp;
    exports com.example.votingapp.Controllers;
}