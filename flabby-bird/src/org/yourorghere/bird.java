package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import static org.yourorghere.FlabbyBird.g;
import static org.yourorghere.FlabbyBird.rot;

public class bird {

    float x;
    float y;
    float size;
    float delta = 0;
    boolean die = false;
    boolean finish = false;

    public bird(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
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

    public void rotate(GL gl) {//rotate function of the bird
        gl.glRotatef(rot, 0f, 0f, 1f);
        if (rot > -70) {//rotate for falling
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
        if (this.delta <= -FlabbyBird.h + 0.45f | this.delta >= FlabbyBird.h - 0.45f) {
            //die if the bird coloied with the top or the bottom of the screan
            this.die = true;
        }

        if (this.die) {// if the bird die fall faster
            if (this.delta <= -1) {//dont continue falling to infite
                this.delta = -1;
                this.finish = true;
            }
            gl.glTranslatef(0f, delta, 0f);
            this.delta = this.delta - 0.02f;
        } else {// keep falling 
            this.delta = this.delta - 0.002f;
            gl.glTranslatef(0f, delta, 0f);
        }

        rotate(gl);//rotate the bird

        init(gl);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0.15);
        gl.glVertex2d(x + size+(size/4f), y + size);
        gl.glTexCoord2d(0, 0.15);
        gl.glVertex2d(x - size-(size/4f), y + size);
        gl.glTexCoord2d(0, 0.85);
        gl.glVertex2d(x - size-(size/4f), y - size);
        gl.glTexCoord2d(1, 0.85);
        gl.glVertex2d(x + size+(size/4f), y - size);
        gl.glEnd();
        gl.glFlush();
    }

    public void setDie(boolean die) {
        this.die = die;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

}
