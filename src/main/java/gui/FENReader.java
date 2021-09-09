//package gui;
//
//import gui.Pieces.*;
//
//public class FENReader {
//    public FENReader() {
//    }
//    public ChessBoard readInFEN(String FEN){
//        //TODO IMPLEMENT THE REST OF THE FEN READER, IT ONLY IMPLEMENTS THE POSITIONS ITSELF RIGHT NOW AND NOT THE CASTLING ETC.
//        ChessBoard board = new ChessBoard();
//        int toBeSkipped = 0;
//        int pos = 0;
//        int index = 0;
//        loop:
//        for(; index<FEN.length(); index++){
//            switch (FEN.charAt(index)){
//                case '/':
//                    toBeSkipped++;
//                    break;
//                case 'k':
//                    board.getBoard()[pos].setPiece(new King("black", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'q':
//                    board.getBoard()[pos].setPiece(new Queen("black", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'r':
//                    board.getBoard()[pos].setPiece(new Rook("black", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'b':
//                    board.getBoard()[pos].setPiece(new Bishop("black", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'n':
//                    board.getBoard()[pos].setPiece(new Knight("black", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'p':
//                    board.getBoard()[pos].setPiece(new Pawn("black", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'K':
//                    board.getBoard()[pos].setPiece(new King("white", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'Q':
//                    board.getBoard()[pos].setPiece(new Queen("white", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'R':
//                    board.getBoard()[pos].setPiece(new Rook("white", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'B':
//                    board.getBoard()[pos].setPiece(new Bishop("white", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'N':
//                    board.getBoard()[pos].setPiece(new Knight("white", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case 'P':
//                    board.getBoard()[pos].setPiece(new Pawn("white", calcXCoordinate(pos), calcYCoordinate(pos)));
//                    pos++;
//                    break;
//                case ' ':
//                    index++;
//                    break loop;
//                default:
//                    int step = Character.getNumericValue(FEN.charAt(index));
//                    pos = pos + step;
//                    break;
//            }
//        }
//
//        return board;
//    }
//
//    public int calcXCoordinate(int index){
//        int x = index%8;
//        return x;
//    }
//
//    public int calcYCoordinate(int index){
//        int y = (index-calcXCoordinate(index))/8;
//        return y;
//    }
//}
