/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.pkg120223;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author aacds
 */
public class Enemy implements GLEventListener{
    
    GLU glu = new GLU();
    GLUT glut = new GLUT();
    private double posEnemyX = 0.0;
    private double posEnemyY = 0.0;
    private double rot = 0.0;
    float luzDifusa[]   ={1f,0f,0f,1.0f};
    float matDifusa1[]  ={0f,0f,1f,0.0f};
    float matDifusa2[]  ={1.0f,1f,0f,0.0f};
    
    @Override
    public void init(GLAutoDrawable glAuto) {
        Animator a = new Animator(glAuto);
        a.start();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        
    }

    @Override
    public void display(GLAutoDrawable glAuto) {
        GL2 gl = glAuto.getGL().getGL2();
        
        gl.glLoadIdentity();
        gl.glTranslated(posEnemyX, 3.5, -7);
        gl.glRotated(rot, 0, 1, 0);
        
        gl.glPushMatrix(); 
          gl.glScaled(0.25, 0.25, 0.25);
          gl.glColor3f(1, 0, 0);
            glut.glutSolidCube(1);
        gl.glPopMatrix();
        rot+=0.5;
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getPosEnemyX() {
        return posEnemyX;
    }

    public void setPosEnemyX(double posEnemyX) {
        this.posEnemyX = posEnemyX;
    }

    public double getPosEnemyY() {
        return posEnemyY;
    }

    public void setPosEnemyY(double posEnemyY) {
        this.posEnemyY = posEnemyY;
    }
}
