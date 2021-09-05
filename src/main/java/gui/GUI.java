package gui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.text.StringCharacterIterator;

public class GUI extends Application {
    private final int size = 8;
    private GridPane root = new GridPane();
    private int x;
    private int y;
    private boolean white_move;
    private boolean white_king_castling;
    private boolean white_queen_castling;
    private boolean black_king_castling;
    private boolean black_queen_castling;

    private boolean selected = false;
    private Node selectedNode;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        createBoard("1rb5/p1ppQ3/n3kp2/1p1R1N2/6p1/2N5/PPP2P1P/1K5R w - - 0 1");
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println(selected);
            if (selected) {
                selected = false;
                Node source = (Node) e.getSource();
                int colIndex = (int) (e.getSceneX() / 100);
                int rowIndex = (int) (e.getSceneY() / 100);
                if(containsPiece(rowIndex,colIndex)){
                    Node temp = getNodeByRowColumnIndex(rowIndex,colIndex);
                    root.getChildren().remove(temp);
                }
                root.add(selectedNode, colIndex, rowIndex);
            } else {
                Node source = (Node) e.getSource();
                int colIndex = (int) (e.getSceneX() / 100);
                int rowIndex = (int) (e.getSceneY() / 100);
                Node result = getNodeByRowColumnIndex(rowIndex, colIndex);
                root.getChildren().remove(result);
                System.out.println("Mouse clicked cell: x= " + colIndex + " + y= " + rowIndex);
                selected = true;
                selectedNode = result;
            }
        });
        Scene scene = new Scene(root, 800, 800);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void createBoard(final String FEN) {
        root.getChildren().clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle square = new Rectangle();
                Color color;
                if ((i + j) % 2 == 0) {
                    color = Color.rgb(74, 75, 62);
                } else {
                    color = Color.rgb(48, 114, 68);
                }
                square.setFill(color);
                root.add(square, j, i);
                square.widthProperty().bind(root.widthProperty().divide(size));
                square.heightProperty().bind(root.heightProperty().divide(size));
            }
        }
        Image blackBishop = new Image("gui/black_bishop.png");
        Image blackKing = new Image("gui/black_king.png");
        Image blackKnight = new Image("gui/black_knight.png");
        Image blackPawn = new Image("gui/black_pawn.png");
        Image blackQueen = new Image("gui/black_queen.png");
        Image blackRook = new Image("gui/black_rook.png");

        Image whiteBishop = new Image("gui/white_bishop.png");
        Image whiteKing = new Image("gui/white_king.png");
        Image whiteKnight = new Image("gui/white_knight.png");
        Image whitePawn = new Image("gui/white_pawn.png");
        Image whiteQueen = new Image("gui/white_queen.png");
        Image whiteRook = new Image("gui/white_rook.png");

        x = 0;
        y = 0;
        StringCharacterIterator iterator = new StringCharacterIterator(FEN);
        while (iterator.current() != ' ') {
            ImageView child;
            switch (iterator.current()) {
                case 'k':
                    child = new ImageView(blackKing);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'b':
                    child = new ImageView(blackBishop);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'n':
                    child = new ImageView(blackKnight);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'p':
                    child = new ImageView(blackPawn);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'q':
                    child = new ImageView(blackQueen);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'r':
                    child = new ImageView(blackRook);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'K':
                    child = new ImageView(whiteKing);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'B':
                    child = new ImageView(whiteBishop);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'N':
                    child = new ImageView(whiteKnight);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'P':
                    child = new ImageView(whitePawn);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'Q':
                    child = new ImageView(whiteQueen);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case 'R':
                    child = new ImageView(whiteRook);
                    child.setFitWidth(100);
                    child.setFitHeight(100);
                    root.add(child, x, y);
                    increment();
                    break;
                case '/':
                    x = 0;
                    y += 1;
                    break;
                default:
                    int temp = Character.getNumericValue(iterator.current());
                    x = x + temp;
                    break;
            }
            iterator.next();
        }

    }

    public void increment() {
        x += 1;
    }

    public Node getNodeByRowColumnIndex(int row, int column) {
        Node result = null;

        for (Node node : root.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                if (node instanceof ImageView) {
                    result = node;
                    break;
                }
            }
        }
        return result;
    }

    public boolean containsPiece(int row, int column) {
        for(Node node : root.getChildren()){
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                if (node instanceof ImageView) {
                    return true;
                }
            }
        }
        return false;
    }
}
