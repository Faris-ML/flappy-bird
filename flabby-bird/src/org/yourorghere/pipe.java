package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import java.util.Random;
import static org.yourorghere.FlabbyBird.faster;

public class pipe {

    float x;
    float y;
    float gap;
    Random random = new Random();
    int counter=0;

    public pipe(float x, float gap) {
        this.x = x;
        this.y = rand(random, gap);
        this.gap = gap;
    }

    public static float rand(Random random, float gap) {
        float min = -0.3f;
        float max = 0.3f - gap;
        float fx = random.nextFloat();
        float resault = (max - min) * fx + min;
        return resault;
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

    public static void init(GL gl) {
        Texture tex;
        //activate texture mapping for 2D 
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            //load texture 
            tex = TextureIO.newTexture(new File("pipe.png"), true);
            tex.bind();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void draw(GL gl) {
        if (this.x <= -1.15) {//if the pipe reach the most left go back to right
            this.x = 1;
            this.y = rand(random, this.gap);
        }
        if (!FlabbyBird.bird.die) { //keep moving from left to right
            this.x = this.x - faster;
            faster= faster + 0.0000003f;
        }
        if(this.x <= -0.15+faster && !FlabbyBird.bird.die && this.x >= -0.15){
            counter++;
        }
        //draw the bottom pipe
        init(gl);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x, y);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x + 0.15, y);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x + 0.15, -1f * FlabbyBird.h);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x, -1f * FlabbyBird.h);
        gl.glEnd();
        gl.glFlush();

        //draw the top pipe
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(x, y + gap);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(x + 0.15, y + gap);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(x + 0.15, 1f * FlabbyBird.h);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(x, 1f * FlabbyBird.h);
        gl.glEnd();
        gl.glFlush();

    }
}