/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.pkg120223;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Xandão
 */
public class Boss implements GLEventListener{
    GLU glu = new GLU();
    GLUT glut = new GLUT();
    private double posBossX = 0.0;
    private double posBossY = 3.8;
    private double rot = 0.0;
    private int incBossX = 1;
    private int incBossY = 1;
    float luzDifusa[]   ={1f,0f,0f,1.0f};
    float matDifusa1[]  ={0f,0f,1f,0.0f};
    float matDifusa2[]  ={1.0f,1f,0f,0.0f};

    @Override
    public void init(GLAutoDrawable glAuto) {
        Animator a = new Animator(glAuto);
        a.start();
    }

    @Override
    public void dispose(GLAutoDrawable glAuto) {
        
    }

    @Override
    public void display(GLAutoDrawable glAuto) {
        GL2 gl = glAuto.getGL().getGL2();
        
        gl.glLoadIdentity();
        gl.glTranslated(posBossX, posBossY, -7);
        gl.glRotated(rot, 0, 1, 0);
        
        gl.glPushMatrix(); 
          gl.glScaled(0.5, 0.5, 0.5);
          gl.glColor3f(1, 0, 1.25f);
            glut.glutSolidTorus(1f, 1f, 10, 10);
        gl.glPopMatrix();
        rot+=0.5;
    }

    @Override
    public void reshape(GLAutoDrawable glAuto, int i, int i1, int i2, int i3) {
    }

    public double getPosBossX() {
        return posBossX;
    }

    public void setPosBossX(double posBossX) {
        this.posBossX = posBossX;
    }

    public double getPosBossY() {
        return posBossY;
    }

    public void setPosBossY(double posBossY) {
        this.posBossY = posBossY;
    }  

    public int getIncBossX() {
        return incBossX;
    }

    public void setIncBossX(int incBossX) {
        this.incBossX = incBossX;
    }

    public int getIncBossY() {
        return incBossY;
    }

    public void setIncBossY(int incBossY) {
        this.incBossY = incBossY;
    }
    
    
}
