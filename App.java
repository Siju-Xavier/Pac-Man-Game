import javax.swing.JFrame;

public class App {

    public static void main(String[] args) throws Exception {
        int rowCount = 21;
        int colCount = 19;
        int tileSize = 32;
        int boardWidth = colCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Pac-man");
      //  frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacMan = new PacMan();
        frame.add(pacMan);
        frame.pack();
        pacMan.requestFocus();
        frame.setVisible(true);
    }
}
