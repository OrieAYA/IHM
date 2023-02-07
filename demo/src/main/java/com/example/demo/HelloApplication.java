package com.example.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class HelloApplication extends Application {

    final String WP = "file:WPon.png";
    final String WT = "file:WTower.png";
    final String WK = "file:WKnight.png";
    final String WF = "file:WFou.png";
    final String WKi = "file:WKing.png";
    final String WQ = "file:WQueen.png";

    final String BP = "file:BPon.png";
    final String BT = "file:BTower.png";
    final String BK = "file:BKnight.png";
    final String BF = "file:BFou.png";
    final String BKi = "file:BKing.png";
    final String BQ = "file:BQueen.png";

    final String noPiece = "file:nopiece.png";

    boolean w = false;

    Stage stage;

    final Map<String, String> paths = new HashMap<>();

    List<String> wPieces = Arrays.asList("WP","WT","WK","WF","WKi","WQ");
    List<String> bPieces = Arrays.asList("BP","BT","BK","BF","BKi","BQ");

    String[][] mat = {
            {"BT","BK","BF","BKi","BQ","BF","BK","BT"},
            {"BP","BP","BP","BP","BP","BP","BP","BP"},
            {"","","","","","","",""},
            {"","","","","","","",""},
            {"","","","","","","",""},
            {"","","","","","","",""},
            {"WP","WP","WP","WP","WP","WP","WP","WP"},
            {"WT","WK","WF","WKi","WQ","WF","WK","WT"}
    };

    private GridPane setup(boolean white){

        GridPane root = new GridPane();
        StackPane node;

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){

                node = new StackPane();
                node.getChildren().add(new ImageView(new Image(paths.get(mat[i][j]))));
                node.setPadding(new Insets(20,20,20,20));

                if(white)node.addEventFilter(MouseEvent.MOUSE_PRESSED, showMoovesW(i, j, node, root));
                else node.addEventFilter(MouseEvent.MOUSE_PRESSED, showMoovesB(i, j, node, root));

                root.add(node, j, i);

            }
        }

        setOriginalColors(root);

        return root;
    }

    private EventHandler showMoovesB(int i, int j, StackPane node, GridPane root){

        int finalI = i;
        int finalJ = j;
        StackPane finalNode = node;

        EventHandler<MouseEvent> showMooves = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setOriginalColors(root);

                switch (mat[finalI][finalJ]){

                    case "BP":
                        //Bouger
                        if(mat[finalI+1][finalJ] == "") {
                            possibleMoove(findNode(root, finalI + 1, finalJ), finalNode, root);
                            if (finalI == 1 && mat[finalI+2][finalJ] == "") {
                                possibleMoove(findNode(root, finalI + 2, finalJ), finalNode, root);
                            }
                        }
                        //Manger
                        if(wPieces.contains(mat[finalI+1][finalJ+1])){
                            possibleMoove(findNode(root, finalI + 1, finalJ + 1), finalNode, root);
                        }
                        if(wPieces.contains(mat[finalI+1][finalJ-1])){
                            possibleMoove(findNode(root, finalI + 1, finalJ - 1), finalNode, root);
                        }
                        break;

                    case "BT":
                        //Droite
                        for(int m = finalJ + 1; m<8; m++){
                            if(bPieces.contains(mat[finalI][m]))break;
                            else if(wPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Gauche
                        for(int m = finalJ - 1; m>=0; m--){
                            if(bPieces.contains(mat[finalI][m]))break;
                            else if(wPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Bas
                        for(int m = finalI + 1; m<8; m++){
                            if(bPieces.contains(mat[m][finalJ]))break;
                            else if(wPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        //Haut
                        for(int m = finalI - 1; m>=0; m--){
                            if(bPieces.contains(mat[m][finalJ]))break;
                            else if(wPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        break;

                    case "BK":
                        //Bouger
                        if(!bPieces.contains(mat[finalI + 2][finalJ + 1]))possibleMoove(findNode(root, finalI + 2, finalJ + 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI + 2][finalJ - 1]))possibleMoove(findNode(root, finalI + 2, finalJ - 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 2][finalJ + 1]))possibleMoove(findNode(root, finalI - 2, finalJ + 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 2][finalJ - 1]))possibleMoove(findNode(root, finalI - 2, finalJ - 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI + 1][finalJ + 2]))possibleMoove(findNode(root, finalI + 1, finalJ + 2), finalNode, root);
                        if(!bPieces.contains(mat[finalI + 1][finalJ - 2]))possibleMoove(findNode(root, finalI + 1, finalJ - 2), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 1][finalJ + 2]))possibleMoove(findNode(root, finalI - 1, finalJ + 2), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 1][finalJ - 2]))possibleMoove(findNode(root, finalI - 1, finalJ - 2), finalNode, root);

                    case "BF":
                        int x = finalJ + 1;
                        int y = finalI - 1;
                        while(x < 8 && y >= 0){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y--;
                        }
                        x = finalJ - 1;
                        y = finalI - 1;
                        while(x >= 0 && y >= 0){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y--;
                        }
                        x = finalJ + 1;
                        y = finalI + 1;
                        while(x < 8 && y < 8){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y++;
                        }
                        x = finalJ - 1;
                        y = finalI + 1;
                        while(x >= 0 && y < 8){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y++;
                        }
                        break;

                    case "BKi":
                        //Bouger
                        if(!bPieces.contains(mat[finalI + 1][finalJ + 1]))possibleMoove(findNode(root, finalI + 1, finalJ + 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI][finalJ + 1]))possibleMoove(findNode(root, finalI, finalJ + 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI + 1][finalJ]))possibleMoove(findNode(root, finalI + 1, finalJ), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 1][finalJ + 1]))possibleMoove(findNode(root, finalI - 1, finalJ + 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 1][finalJ - 1]))possibleMoove(findNode(root, finalI - 1, finalJ - 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI][finalJ - 1]))possibleMoove(findNode(root, finalI, finalJ - 1), finalNode, root);
                        if(!bPieces.contains(mat[finalI - 1][finalJ]))possibleMoove(findNode(root, finalI - 1, finalJ), finalNode, root);
                        if(!bPieces.contains(mat[finalI + 1][finalJ - 1]))possibleMoove(findNode(root, finalI + 1, finalJ - 1), finalNode, root);
                        break;

                    case "BQ":
                        //Bouger
                        //Droite
                        for(int m = finalJ + 1; m<8; m++){
                            if(bPieces.contains(mat[finalI][m]))break;
                            else if(wPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Gauche
                        for(int m = finalJ - 1; m>=0; m--){
                            if(bPieces.contains(mat[finalI][m]))break;
                            else if(wPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Bas
                        for(int m = finalI + 1; m<8; m++){
                            if(bPieces.contains(mat[m][finalJ]))break;
                            else if(wPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        //Haut
                        for(int m = finalI - 1; m>=0; m--){
                            if(bPieces.contains(mat[m][finalJ]))break;
                            else if(wPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        x = finalJ + 1;
                        y = finalI - 1;
                        while(x < 8 && y >= 0){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y--;
                        }
                        x = finalJ - 1;
                        y = finalI - 1;
                        while(x >= 0 && y >= 0){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y--;
                        }
                        x = finalJ + 1;
                        y = finalI + 1;
                        while(x < 8 && y < 8){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y++;
                        }
                        x = finalJ - 1;
                        y = finalI + 1;
                        while(x >= 0 && y < 8){
                            if(bPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(wPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y++;
                        }
                        break;
                }

            }
        };

        return showMooves;
    }

    private EventHandler showMoovesW(int i, int j, StackPane node, GridPane root){

        int finalI = i;
        int finalJ = j;
        StackPane finalNode = node;

        EventHandler<MouseEvent> showMooves = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                setOriginalColors(root);

                switch (mat[finalI][finalJ]){
                    case "WP":
                        possibleMoove(findNode(root,finalI-1, finalJ), finalNode, root);
                        if(finalI==6){
                            possibleMoove(findNode(root,finalI-2, finalJ), finalNode, root);
                        }
                        break;
                    case "WT":
                        //Droite
                        for(int m = finalJ + 1; m<8; m++){
                            if(wPieces.contains(mat[finalI][m]))break;
                            else if(bPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Gauche
                        for(int m = finalJ - 1; m>=0; m--){
                            if(wPieces.contains(mat[finalI][m]))break;
                            else if(bPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Bas
                        for(int m = finalI + 1; m<8; m++){
                            if(wPieces.contains(mat[m][finalJ]))break;
                            else if(bPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        //Haut
                        for(int m = finalI - 1; m>=0; m--){
                            if(wPieces.contains(mat[m][finalJ]))break;
                            else if(bPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        break;

                    case "WK":
                        //Bouger
                        if(!wPieces.contains(mat[finalI - 2][finalJ + 1]))possibleMoove(findNode(root, finalI - 2, finalJ + 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI - 2][finalJ - 1]))possibleMoove(findNode(root, finalI - 2, finalJ - 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI - 1][finalJ + 2]))possibleMoove(findNode(root, finalI - 1, finalJ + 2), finalNode, root);
                        if(!wPieces.contains(mat[finalI - 1][finalJ - 2]))possibleMoove(findNode(root, finalI - 1, finalJ - 2), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 1][finalJ + 2]))possibleMoove(findNode(root, finalI + 1, finalJ + 2), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 1][finalJ - 2]))possibleMoove(findNode(root, finalI + 1, finalJ - 2), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 2][finalJ + 1]))possibleMoove(findNode(root, finalI + 2, finalJ + 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 2][finalJ - 1]))possibleMoove(findNode(root, finalI + 2, finalJ - 1), finalNode, root);

                    case "WF":
                        int x = finalJ + 1;
                        int y = finalI - 1;
                        while(x < 8 && y >= 0){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y--;
                        }
                        x = finalJ - 1;
                        y = finalI - 1;
                        while(x >= 0 && y >= 0){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y--;
                        }
                        x = finalJ + 1;
                        y = finalI + 1;
                        while(x < 8 && y < 8){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y++;
                        }
                        x = finalJ - 1;
                        y = finalI + 1;
                        while(x >= 0 && y < 8){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y++;
                        }
                        break;

                    case "WKi":
                        //Bouger
                        if(!wPieces.contains(mat[finalI - 1][finalJ - 1]))possibleMoove(findNode(root, finalI - 1, finalJ - 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI][finalJ - 1]))possibleMoove(findNode(root, finalI, finalJ - 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI - 1][finalJ]))possibleMoove(findNode(root, finalI - 1, finalJ), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 1][finalJ - 1]))possibleMoove(findNode(root, finalI + 1, finalJ - 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 1][finalJ + 1]))possibleMoove(findNode(root, finalI + 1, finalJ + 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI][finalJ + 1]))possibleMoove(findNode(root, finalI, finalJ + 1), finalNode, root);
                        if(!wPieces.contains(mat[finalI + 1][finalJ]))possibleMoove(findNode(root, finalI + 1, finalJ), finalNode, root);
                        if(!wPieces.contains(mat[finalI - 1][finalJ + 1]))possibleMoove(findNode(root, finalI - 1, finalJ + 1), finalNode, root);
                        break;

                    case "WQ":
                        //Bouger
                        //Droite
                        for(int m = finalJ + 1; m<8; m++){
                            if(wPieces.contains(mat[finalI][m]))break;
                            else if(bPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Gauche
                        for(int m = finalJ - 1; m>=0; m--){
                            if(wPieces.contains(mat[finalI][m]))break;
                            else if(bPieces.contains(mat[finalI][m])){
                                possibleMoove(findNode(root, finalI, m), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, finalI, m), finalNode, root);
                        }
                        //Bas
                        for(int m = finalI + 1; m<8; m++){
                            if(wPieces.contains(mat[m][finalJ]))break;
                            else if(bPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        //Haut
                        for(int m = finalI - 1; m>=0; m--){
                            if(wPieces.contains(mat[m][finalJ]))break;
                            else if(bPieces.contains(mat[m][finalJ])){
                                possibleMoove(findNode(root, m, finalJ), finalNode, root);
                                break;
                            }
                            possibleMoove(findNode(root, m, finalJ), finalNode, root);
                        }
                        x = finalJ + 1;
                        y = finalI - 1;
                        while(x < 8 && y >= 0){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y--;
                        }
                        x = finalJ - 1;
                        y = finalI - 1;
                        while(x >= 0 && y >= 0){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y--;
                        }
                        x = finalJ + 1;
                        y = finalI + 1;
                        while(x < 8 && y < 8){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x++;
                            y++;
                        }
                        x = finalJ - 1;
                        y = finalI + 1;
                        while(x >= 0 && y < 8){
                            if(wPieces.contains(mat[y][x]))break;
                            else{
                                possibleMoove(findNode(root, y, x), finalNode, root);
                                if(bPieces.contains(mat[y][x]))break;
                            }
                            x--;
                            y++;
                        }
                        break;
                }

            }
        };

        return showMooves;
    }

    private void possibleMoove(Node node, StackPane act, GridPane root){

        EventHandler<MouseEvent> moove = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                StackPane n = (StackPane) node;
                mat[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)];

                mat[GridPane.getRowIndex(act)][GridPane.getColumnIndex(act)] = "";

                game(stage);
            }
        };

        node.setStyle("-fx-background-color: YELLOW");
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, moove);

    }

    private void setOriginalColors(GridPane gridPane){
        for (Node node : gridPane.getChildren()) {
            if((GridPane.getColumnIndex(node)%2 == GridPane.getRowIndex(node)%2) || (GridPane.getColumnIndex(node)%2 == GridPane.getRowIndex(node)%2)){
                node.setStyle("-fx-background-color: CORNSILK");
            }
            else{
                node.setStyle("-fx-background-color: DARKGOLDENROD");
            }
        }
    }

    private Node findNode(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private boolean verifWin(){
        return false;
    }

    private void game(Stage stage){

        stage.setScene(new Scene(setup(w = !w),520,520));
        stage.show();

        if(verifWin()){

        }

    }

    @Override
    public void start(Stage st) throws IOException {

        stage = st;

        paths.put("WP", WP);
        paths.put("WT", WT);
        paths.put("WK", WK);
        paths.put("WF", WF);
        paths.put("WKi", WKi);
        paths.put("WQ", WQ);
        paths.put("WP", WP);
        paths.put("WT", WT);
        paths.put("WK", WK);
        paths.put("WF", WF);
        paths.put("WKi", WKi);
        paths.put("WQ", WQ);
        paths.put("BP", BP);
        paths.put("BT", BT);
        paths.put("BK", BK);
        paths.put("BF", BF);
        paths.put("BKi", BKi);
        paths.put("BQ", BQ);
        paths.put("", noPiece);

        stage.setTitle("Chess");
        stage.setScene(new Scene(setup(w = !w),520,520));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}