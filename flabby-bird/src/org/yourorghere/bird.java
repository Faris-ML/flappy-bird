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
    float rot=0;
    float g=0;

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
        gl.glRotatef(this.rot, 0f, 0f, 1f);
//        if (this.rot > -90) {
//            this.rot = this.rot - this.g;
//            this.g = this.g + 0.004f;
//            if (this.g > 5) {
//                this.g = 5;
//            }
//        }
    
    }

    public float getRot() {
        return rot;
    }

    public void setRot(float rot) {
        this.rot = rot;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }
    
    public void draw(GL gl) {
        if (this.y <= -10*9/16| this.y>=10*9/16) {
            //die

        } else {
            // going down
            this.y = this.y - 0.02f;
        }
        
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
