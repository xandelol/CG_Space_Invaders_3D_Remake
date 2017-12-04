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
public class Tiro implements GLEventListener{
    GLU glu = new GLU();
    GLUT glut = new GLUT();
    private double posTiroX = 0.0;
    private double posTiroY = -3.5;
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
    public void dispose(GLAutoDrawable glAuto) {
        
    }

    @Override
    public void display(GLAutoDrawable glAuto) {
        GL2 gl = glAuto.getGL().getGL2();
       
        //Player
        gl.glLoadIdentity();
        gl.glTranslated(posTiroX, posTiroY, -7);
        gl.glRotated(rot, 0, 1, 0);
        
        gl.glPushMatrix();
          gl.glScaled(1, 1, 1);
            gl.glColor3f(0, 1, 1);
            glut.glutWireSphere(0.10f,10,10);
        gl.glPopMatrix();
        rot+=0.5;
        posTiroY += 0.01;
    }

    @Override
    public void reshape(GLAutoDrawable glAuto, int i, int i1, int i2, int i3) {
        
    }

    public double getPosTiroX() {
        return posTiroX;
    }

    public void setPosTiroX(double posTiroX) {
        this.posTiroX = posTiroX;
    }

    public double getPosTiroY() {
        return posTiroY;
    }

    public void setPosTiroY(double posTiroY) {
        this.posTiroY = posTiroY;
    }
    
    
}
