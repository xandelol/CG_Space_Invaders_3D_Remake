package projeto.pkg120223;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EventListener;
import javafx.animation.Animation;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

public class Projeto120223 implements GLEventListener, KeyListener{
    
    GLU glu = new GLU();
    GLUT glut = new GLUT();
    GL2 gl;
    TextRenderer textRenderer;
    Player player = new Player();
    Enemy enemy = new Enemy();
    GameView gameView;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<Tiro> tiros = new ArrayList<Tiro>();
    ArrayList<Boss> bosses = new ArrayList<Boss>();
    private boolean esquerdo = false;
    private boolean direito = false;
    private boolean tiro;
    private boolean bossLvl = false;
    private long ultimoTiro;
    private double posPlayer = 0.0;
    private double posEnemyY = 0.0;
    private double posBossY = 3.8;
    private int lvl = 1;
    private int points = 0;
    private int vidas = 3;
    private int bossHit = 0;
    private boolean lvlUp = true;
    //Informacoes sobre a luz
    float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f};
    float luzDifusa[] = {1.0f, 1.0f, 1.0f, 1.0f};	   // "cor"
    float luzEspecular[] = {1.0f, 1.0f, 1.0f, 1.0f};// "brilho"
    float posicaoLuz[] = {0.0f, 50.0f, 50.0f, 1.0f};
    // Informacoes sobre o material
    float especularidade[] = {1.0f, 0.0f, 0.0f, 1.0f};
    int especMaterial = 60;
    double eqn[] = {-0.15, 0.15, 0, 0};
    
    public static void main(String[] args) {
        new Projeto120223();
    }
    
    public Projeto120223(){
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);
        
        JFrame frame = new JFrame("Projeto-120223");
        frame.setSize(1500,1000);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });
        
        frame.addKeyListener(this);
    }

    @Override
    public void init(GLAutoDrawable glAuto) {
        gl = glAuto.getGL().getGL2();
        gl.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        ultimoTiro = System.currentTimeMillis();
        createEnemies(lvl);
        Animator a = new Animator(glAuto);
        a.start();
        // Define a refletancia do material 
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, especularidade, 0);

        // Define a concentração do brilho
        gl.glMateriali(GL.GL_FRONT_AND_BACK, GL2.GL_SHININESS, especMaterial);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, especularidade, 0);

        // Ativa o uso da luz ambiente 
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, luzAmbiente, 0);

        // Define os parâmetros da luz de número 0
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, posicaoLuz, 0);

        // Habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        //Habilita o uso de iluminação
        gl.glEnable(GL2.GL_LIGHTING);
        // Habilita a luz de n�mero 0
        gl.glEnable(GL2.GL_LIGHT1);
        // Habilita o depth-buffering
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        gameView = GameView.Start;
        textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 50));
    }
    double rot;
    @Override
    public void display(GLAutoDrawable glAuto) {
        GL2 gl = glAuto.getGL().getGL2();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT |
                   GL.GL_DEPTH_BUFFER_BIT);
        switch(gameView){
            case Start:
                desenhaTexto("Space Invaders 3d Remake", 20, 380, 0.6f, Color.BLACK);
                desenhaTexto("Aperte P para iniciar", 160, 280, 0.4f, Color.DARK_GRAY);
                desenhaTexto("COMANDOS:", 50, 180, 0.5f, Color.RED);
                desenhaTexto("Use as setas para controlar a posição", 60, 155, 0.4f, Color.RED);
                desenhaTexto("Aperte ESPAÇO para atirar", 120, 125, 0.4f, Color.RED);
                break;
            case Gameplay:
                desenhaTexto(points + " pontos", 10, 10, 0.4f, Color.DARK_GRAY);
                desenhaTexto(vidas + " vidas", 410, 10, 0.4f, Color.DARK_GRAY);
                Game(glAuto);
                break;
            case GameOver:
                desenhaTexto("PONTUAÇÃO:", 60, 350, 0.4f, Color.BLACK);
                desenhaTexto(points + " pontos", 60, 300, 0.8f, Color.DARK_GRAY);
                desenhaTexto("FIM DE JOGO", 60, 200, 1, Color.RED);
                desenhaTexto("Aperte P para continuar", 120, 170, 0.4f, Color.DARK_GRAY);
                break;
        }           
    }

    @Override
    public void dispose(GLAutoDrawable glAuto) {
        
    }
    
    @Override
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1, 1, 30);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -5);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tiro = true;
        }

        
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            esquerdo = true;
        }

        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            direito = true;
        }
        

        if(evt.getKeyCode() == KeyEvent.VK_P){
            switch(gameView){
                case Start:
                    gameView = GameView.Gameplay;
                    break;
                case GameOver:
                    gameView = GameView.Start;
                    reset();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tiro = false;
        }

        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            esquerdo = false;
        }

        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            direito = false;
        }
    }
    
    private void Game(GLAutoDrawable glAuto){
        //Player
        player.setPosPlayer(posPlayer);
        player.display(glAuto);
        if(esquerdo&&(posPlayer>-3.5))posPlayer-=0.03;
        if(direito&&(posPlayer<3.5))posPlayer+=0.03;
        //
        //Inimigos
        for (Enemy e : enemies) {
            e.setPosEnemyY(e.getPosEnemyY()+posEnemyY);
            e.display(glAuto);
            if(e.getPosEnemyY()<=-3.35) gameView = GameView.GameOver;
        }
        posEnemyY-=0.000005;
        //
        //Tiro
        if(tiro){
            long tempoAtual = System.currentTimeMillis();
            if(tempoAtual > ultimoTiro+300){
                ultimoTiro = tempoAtual;
                createTiro(posPlayer);
            }
        }
        
        for(Tiro t : tiros){
            t.display(glAuto);
            if(t.getPosTiroY()>3.9) tiros.remove(t);
        }
        //
        
        verificaColisaoTiro();
        verificaColisaoPlayer();
        
        if(lvlUp){
            if(lvl<=3){
                createEnemies(lvl);
            }else if(lvl == 4){
                Boss boss = new Boss();
                bosses.add(boss);
                bossLvl=true;
            }
            lvlUp=false; 
        }
        
        if(enemies.size()==0){
            lvl++;
            lvlUp=true;
            posEnemyY=0.0;
        }
        
        if(bossLvl){
            for(Boss b : bosses){
                b.setPosBossY(posBossY);
                b.display(glAuto);
            }
            posBossY-=0.001;
            verificaColisaoTiroBoss();
            verificaColisaoPlayerBoss();
        }
    }
    
    private void desenhaTexto(String text, int x, int y, float fontscale, Color fontcolor)
    {
            textRenderer.beginRendering(500, 500);
            textRenderer.setColor(fontcolor);
            textRenderer.setSmoothing(true);

            textRenderer.draw3D(text, (float)x, (float)y, (float)0, (float)fontscale);
            textRenderer.endRendering(); 
            textRenderer.flush();
    }
    
    private void reset(){
        player = new Player();
        enemy = new Enemy();        
        enemies = new ArrayList<Enemy>();
        tiros = new ArrayList<Tiro>();
        bosses = new ArrayList<Boss>();
        esquerdo = false;
        direito = false;
        tiro = false;
        bossLvl = false;
        ultimoTiro = System.currentTimeMillis();
        posPlayer = 0.0;
        posEnemyY = 0.0;
        posBossY = 3.8;
        lvl = 1;
        vidas = 3;
        points = 0;
        bossHit = 0;
        lvlUp = true;
    }
    
    private void createEnemies(int level){
        double incX = 0.95;
        double incY = 0.95;
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < 8; j++) {
                Enemy e = new Enemy();
                e.setPosEnemyY(3.5-(incY*i));
                e.setPosEnemyX(-3.4+(incX*j));
                enemies.add(e);
            }
        }
    }
    
    private void createTiro(double posX){
        Tiro t = new Tiro();
        t.setPosTiroY(-3.5);
        t.setPosTiroX(posX);
        tiros.add(t);
    }
    
    private void verificaColisaoTiro(){
        for (Tiro t : tiros){
            for(Enemy e : enemies){
                if((t.getPosTiroX()>=(e.getPosEnemyX()-0.25) && t.getPosTiroX()<=(e.getPosEnemyX()+0.25))&&
                        (t.getPosTiroY()>=(e.getPosEnemyY()-0.25) && t.getPosTiroY()<=(e.getPosEnemyY()+0.25))){
                    tiros.remove(t);
                    enemies.remove(e);
                    points++;
                }
            }
        }
    }
    private void verificaColisaoTiroBoss(){
        for (Tiro t : tiros){
            for(Boss e : bosses){
                if((t.getPosTiroX()>=(e.getPosBossX()-0.5) && t.getPosTiroX()<=(e.getPosBossX()+0.5))&&
                        (t.getPosTiroY()>=(e.getPosBossY()-0.5) && t.getPosTiroY()<=(e.getPosBossY()+0.5))){
                    tiros.remove(t);
                    bossHit++;
                    if(bossHit>9){
                        bosses.remove(e);
                        points += 5+(e.getPosBossY());
                        gameView = GameView.GameOver;
                    }
                }
            }
        }
    }
    
    private void verificaColisaoPlayer(){
        for(Enemy e : enemies){
            if((e.getPosEnemyX()>=(player.getPosPlayer()-0.5) && e.getPosEnemyX()<=(player.getPosPlayer()+0.5))&&(e.getPosEnemyY()<=-3.25)){
                enemies.remove(e);
                vidas--;
                if (vidas==0) {
                    gameView = GameView.GameOver;
                }
            }
        }
    }
    
    private void verificaColisaoPlayerBoss(){
        for(Boss e : bosses){
            if((e.getPosBossX()>=(player.getPosPlayer()-0.5) && e.getPosBossX()<=(player.getPosPlayer()+0.5))&&(e.getPosBossY()<=-3.25)){
                enemies.remove(e);
                vidas-=3;
                if (vidas==0) {
                    gameView = GameView.GameOver;
                }
            }
        }
    }
}
