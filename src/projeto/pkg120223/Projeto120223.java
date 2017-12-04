package projeto.pkg120223;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
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
    Player player = new Player();
    Enemy enemy = new Enemy();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private boolean esquerdo = false;
    private boolean direito = false;
    private boolean reiniciar = false;
    private boolean tiro;
    private double posPlayer = 0.0;
    private double posEnemyY = 0.0;
    private int lvl = 1;
    float luzDifusa[]   ={1f,0f,0f,1.0f};
    float matDifusa1[]  ={0f,0f,1f,0.0f};
    float matDifusa2[]  ={1.0f,1f,0f,0.0f};
    
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
        createEnemies(2, glAuto);
        Animator a = new Animator(glAuto);
        a.start();
    }
    double rot;
    @Override
    public void display(GLAutoDrawable glAuto) {
        GL2 gl = glAuto.getGL().getGL2();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT |
                   GL.GL_DEPTH_BUFFER_BIT);
        
        //Player
        player.setPosPlayer(posPlayer);
        player.display(glAuto);
        if(esquerdo&&(posPlayer>-3.5))posPlayer-=0.03;
        if(direito&&(posPlayer<3.5))posPlayer+=0.03;
        
        //Inimigos
        for (Enemy e : enemies) {
            e.setPosEnemyY(e.getPosEnemyY()+posEnemyY);
            e.display(glAuto);
        }
        posEnemyY-=0.000005;
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
        

        if(evt.getKeyCode() == KeyEvent.VK_R)
            reiniciar = true;
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

        if(evt.getKeyCode() == KeyEvent.VK_R)
            reiniciar = false;
    }
    
    private void createEnemies(int level, GLAutoDrawable glAuto){
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
}
