package ui;

import backend.Backend;
import core.Peer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileShareApp extends Application {

    private final ListView<Peer> peerList = new ListView<>();
    private final Label peerLabel = new Label();
    private final Label connectionStatusLabel = new Label();
    private Backend backend;
    private final Button discoverButton = new Button("🔄");
    private final Button sendButton = new Button("Send File");

    public FileShareApp() {
        discoverButton.setTooltip(new Tooltip("Discover Peers"));
        peerList.setTooltip(new Tooltip("Select a Peer to share files with it"));
        sendButton.disableProperty().bind(peerList.getSelectionModel().selectedItemProperty().isNull());

    }

    @Override
    public void start(Stage primaryStage) {
        connectBackend();
        if (backendReady()) connectionStatusLabel.setText("Visible in network as " + backend.localPeer.name());
        else connectionStatusLabel.setText("Disconnected from network");

        if (backend != null) {
            backend.onFileReceived((file, sender) -> {
                Platform.runLater(() -> {
                    String message = "Received file " + file.getName() + " by " + sender;
                    Toast.show(primaryStage, message, 2000);
                });
            });
        }

        discoverButton.setOnAction(e -> discover());
        sendButton.setOnAction(e -> selectAndSendFile(primaryStage));

        // Layout
        StackPane root = new StackPane();
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.getChildren().add(discoverButton);

        VBox mainLayout = new VBox(10, connectionStatusLabel, topBar, peerList, peerLabel, sendButton);
        mainLayout.setPadding(new Insets(10));

        primaryStage.setTitle("LAN File Sender");
        root.getChildren().add(mainLayout);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        discover();
    }

    private void selectAndSendFile(Stage primaryStage) {
        Peer selected = peerList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null && file.exists()) {
            backend.sendFile(selected.ip(), selected.fileTransferPort(), file);
        }
        Toast.show(primaryStage, "File sent to " + selected.name(), 2000);
    }

    private void discover() {
        peerList.getItems().clear();
        List<Peer> peers = backendReady() ? backend.discoverPeers() : new ArrayList<>();
        if (peers.isEmpty()) {
            peerLabel.setText("No peers found.");
        } else {
            peerLabel.setText(peers.size() + " peer(s) found.");
            peerList.getItems().addAll(peers);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean backendReady() {
        return backend != null;
    }

    private void connectBackend() {
        if (backend != null) return;
        try {
            backend = new Backend();
            backend.start();
        } catch (IOException e) {
            backend = null;
        }
    }
}
