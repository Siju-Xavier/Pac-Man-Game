import java.awt.*;
import java.util.HashSet;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class PacMan extends JPanel implements ActionListener, KeyListener {


    class Block{
        int x,y;
        int width;
        int height;
        Image image;

        int startX,startY;
        int velocityX = 0,velocityY = 0;
        char direction= 'U';

        Block(int x, int y, int width, int height, Image image){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.image = image;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction){
            char oldDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += velocityX;
            this.y -= velocityY;
            for (Block wall : walls){
                if (collision(this, wall)){
                    this.x -= this.velocityX;
                    this.y += this.velocityY;
                    this.direction = oldDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity(){
            if(direction == 'U'){
                this.velocityX = 0;
                this.velocityY = tileSize/4;
            }
            else if(direction == 'D'){
                this.velocityX = 0;
                this.velocityY = -tileSize/4;
            }
            else if(direction == 'L'){
                this.velocityX = -tileSize/4;
                this.velocityY = 0;
            }
            else if(direction == 'R'){
                this.velocityX = +tileSize/4;
                this.velocityY = 0;
            }
        }

        void reset(){
            this.x = startX;
            this.y = startY;

        }
    }


    private int rowCount = 21;
    private int colCount = 19;
    private int tileSize = 32;
    private int boardWidth = colCount * tileSize;
    private int boardHeight = rowCount * tileSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image redGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    private String[] map = {
            //X = wall, O = skip, P = pac man, ' ' = food
            //Ghosts: b = blue, o = orange, p = pink, r = re
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"

};

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    Timer gameLoop;
    char[] directions = {'U', 'D', 'L', 'R'};
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;
    


    PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        //LOAD IMAGES
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();

        loadMap();
        for (Block ghost : ghosts){
            char newDirection = directions[random.nextInt(directions.length)];
            ghost.updateDirection(newDirection);
        }
        gameLoop = new Timer(50, this);
        gameLoop.start();
    }

    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();


        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                String row = map[r];
                char mapChar = row.charAt(c);

                int x = c * tileSize;
                int y = r * tileSize;

                if (mapChar == 'X') {//block wall
                    Block wall = new Block(x, y, tileSize, tileSize, wallImage);
                    walls.add(wall);
                } else if (mapChar == 'b') {
                    Block ghost = new Block(x, y, tileSize, tileSize, blueGhostImage);
                    ghosts.add(ghost);
                } else if (mapChar == 'r') {
                    Block ghost = new Block(x, y, tileSize, tileSize, redGhostImage);
                    ghosts.add(ghost);
                } else if (mapChar == 'o') {
                    Block ghost = new Block(x, y, tileSize, tileSize, orangeGhostImage);
                    ghosts.add(ghost);
                } else if (mapChar == 'p') {
                    Block ghost = new Block(x, y, tileSize, tileSize, pinkGhostImage);
                    ghosts.add(ghost);
                } else if (mapChar == 'P') {
                    pacman = new Block(x, y, tileSize, tileSize, pacmanRightImage);
                } else if (mapChar == ' ') {
                    Block food = new Block(x + 14, y + 14, 4, 4, null);
                    foods.add(food);
                }

            }
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for(Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);

        }
        for(Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
        g.setColor(Color.WHITE);
        for (Block food : foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
        //score
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        if (gameOver) {
            g.drawString("Game Over" + String.valueOf(score), tileSize / 2, tileSize / 2);
        }
        else{
            g.drawString("Score: " + String.valueOf(score)+ "  " + "Lives: " + String.valueOf(lives), tileSize / 2, tileSize / 2);
        }
    }

    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y -= pacman.velocityY;

        //check wall collisions
        for (Block wall : walls){
            if (collision(pacman, wall)){
                pacman.x -= pacman.velocityX;
                pacman.y += pacman.velocityY;
                break;
            }
        }

        //check ghost collisions
        for (Block ghost : ghosts){
            if (collision(ghost, pacman)){
                lives -= 1;
                if (lives == 0){
                    gameOver = true;
                    return;
                }
                resetPositions();

            }
            if (ghost.y == tileSize*9 && ghost.direction != 'U' && ghost.direction != 'D'){
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y -= ghost.velocityY;
            for (Block wall : walls){
            if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) {
                ghost.x -= ghost.velocityX;
                ghost.y += ghost.velocityY;
                char newDirection = directions[random.nextInt(directions.length)];
                ghost.updateDirection(newDirection);
            }
            }
        }
        //
        Block foodEaten = null;
        for (Block food : foods){
            if (collision(pacman, food)){
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);

        if (foods.isEmpty()){
            loadMap();
            resetPositions();
        }
    }

    public boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y ;
    }

    public void resetPositions(){
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Block ghost : ghosts){
            ghost.reset();
            char newDirection = directions[random.nextInt(directions.length)];
            ghost.updateDirection(newDirection);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if (gameOver) {
            gameLoop.stop();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (gameOver) {
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }
       // System.out.println("Key released" + e.getKeyCode());
        if (e.getKeyCode()== KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        }
        else if (e.getKeyCode()== KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        }
        else if (e.getKeyCode()== KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        }
        else if (e.getKeyCode()== KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }

        if (pacman.direction == 'U'){
            pacman.image = pacmanUpImage;
        }
        else if (pacman.direction == 'D'){
            pacman.image = pacmanDownImage;
        }
        else if (pacman.direction == 'L'){
            pacman.image = pacmanLeftImage;
        }
        else if (pacman.direction == 'R'){
            pacman.image = pacmanRightImage;
        }
    }

    }


