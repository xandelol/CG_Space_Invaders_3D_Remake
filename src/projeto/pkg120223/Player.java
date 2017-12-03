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
public class Player implements GLEventListener{
    
    GLU glu = new GLU();
    GLUT glut = new GLUT();
    private double posPlayer = 0.0;
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
       
        //Player
        gl.glLoadIdentity();
        gl.glTranslated(posPlayer, -3.5, -7);
        gl.glRotated(0, 0, 1, 0);
        
        gl.glPushMatrix();
          gl.glScaled(1, 0.25, 0.25);
            gl.glColor3f(0, 0, 1);
            glut.glutSolidCube(1);
        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        
    }

    public double getPosPlayer() {
        return posPlayer;
    }

    public void setPosPlayer(double posPlayer) {
        this.posPlayer = posPlayer;
    }
}
