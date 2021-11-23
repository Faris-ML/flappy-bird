package org.yourorghere;


import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import static org.yourorghere.FlabbyBird.g;
import static org.yourorghere.FlabbyBird.rot;

/**
 *
 * @author TFgam
 */
public class bird {
    float x;
    float y;
    float size;
    float delta=0;

    public bird(float x,float y,float size) {
        this.x=x;
        this.y=y;
        this.size=size;        
    }

    public void init(GL gl) {
        Texture tex;
        //activate texture mapping for 2D 
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            //load texture 
            tex = TextureIO.newTexture(new File("bird.png"), true);
            tex.bind();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void rotate(GL gl){
        gl.glRotatef(rot, 0f, 0f, 1f);
        if (rot > -70) {
            rot = rot - g;
            g = g + 0.004f;
            if (g > 5) {
                g = 5;
            }
        }
    
    }

    public float getRot() {
        return rot;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    
    public float getG() {
        return g;
    }

   
    
    public void draw(GL gl) {
        if (this.delta < -10f*FlabbyBird.h| this.delta>10f*FlabbyBird.h) {
            //die

        } else {
            // going down
            this.delta = this.delta - 0.002f;
            gl.glTranslatef(0f, delta, 0f);
        }
        rotate(gl);
        init(gl);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0.15);
        gl.glVertex2d(x+size, y+size);
        gl.glTexCoord2d(0, 0.15);
        gl.glVertex2d(x-size, y+size);
        gl.glTexCoord2d(0, 0.85);
        gl.glVertex2d(x-size, y-size);
        gl.glTexCoord2d(1, 0.85);
        gl.glVertex2d(x+size, y-size);
        gl.glEnd();
        gl.glFlush();
    }

}
