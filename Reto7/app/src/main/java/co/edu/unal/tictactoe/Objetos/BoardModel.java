package co.edu.unal.tictactoe.Objetos;

public class BoardModel {
    int First;
    int Second;
    int Third;
    int Fourth;
    int Fifth;
    int Seventh;
    int Eighth;
    int Ninth;
    int Tenth;

    public BoardModel(){}

    public BoardModel(int first, int second, int third, int fourth, int fifth, int seventh, int eighth, int ninth, int tenth){
        this.First = first;
        this.Second = second;
        this.Third = third;
        this.Fourth = fourth;
        this.Fifth = second;
        this.Seventh = second;
        this.Eighth = second;
        this.Ninth = second;
        this.Tenth = second;
    }

    public int getFirst() {
        return First;
    }
    public void setFirst(int first) {
        First = first;
    }


    public int getSecond() {
        return Second;
    }

    public void setFifth(int fifth) {
        Fifth = fifth;
    }

    public void setSecond(int second) {
        Second = second;
    }

    public int getThird() {
        return Third;
    }

    public void setThird(int third) {
        Third = third;
    }

    public int getFourth() {
        return Fourth;
    }

    public void setFourth(int fourth) {
        Fourth = fourth;
    }

    public int getFifth() {
        return Fifth;
    }

    public int getSeventh() {
        return Seventh;
    }

    public void setSeventh(int seventh) {
        Seventh = seventh;
    }

    public int getEighth() {
        return Eighth;
    }

    public void setEighth(int eighth) {
        Eighth = eighth;
    }

    public int getNinth() {
        return Ninth;
    }

    public void setNinth(int ninth) {
        Ninth = ninth;
    }

    public int getTenth() {
        return Tenth;
    }

    public void setTenth(int tenth) {
        Tenth = tenth;
    }
}
