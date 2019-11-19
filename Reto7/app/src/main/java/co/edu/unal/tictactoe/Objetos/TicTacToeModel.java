package co.edu.unal.tictactoe.Objetos;

public class TicTacToeModel {
    int player1;
    int player2;

    public TicTacToeModel(){}

    public TicTacToeModel(int player1, int player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer1(int player1) {
        player1 = player1;
    }

    public void setPlayer2(int player2) {
        player2 = player2;
    }
}
