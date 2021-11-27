package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * FlabbyBird.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class FlabbyBird implements GLEventListener, KeyListener {

    static float delta = 0;
    static float x = 0;
    static float rot = 0;
    static float g = 2f;
    static int height = 720;
    static int width = 1280;
    static float h = width / height;

    static bird bird = new bird(0, 0, 0.03f);
    static pipe pipe = new pipe(-0.04f, -0.1f, 0.3f);
    static background bg = new background();
    //static input listener=new input();

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new FlabbyBird());
        canvas.addKeyListener(new FlabbyBird());
        frame.add(canvas);
        frame.setSize(width, height);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();

    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        //System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) height / (float) width;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1f, 1f, -1f * h, 1f * h, -1f, 1f);//perform orthographic projuction
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        //define GL drawable
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // draw the background
        gl.glLoadIdentity();
        gl.glPushMatrix();
        bg.draw(gl);
        gl.glPopMatrix();
        
        //draw the pipes
        gl.glLoadIdentity();
        gl.glPushMatrix();
        pipe.draw(gl);
        gl.glPopMatrix();
        
        // draw the bird
        gl.glLoadIdentity();
        gl.glPushMatrix();
        bird.setDelta(delta);
        bird.draw(gl);
        delta = bird.getDelta();
        gl.glPopMatrix();
        
        //coloied function
        Colosion(bird, pipe);

    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {// space key pressed function 
        if (ke.getKeyCode() == KeyEvent.VK_SPACE && bird.die == false) {// if pressed jump and rotate
            delta = delta + 0.06f;
            rot = g + 45;
            g = 2;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    public void Colosion(bird bird, pipe pipe) {// colosion function
        
        //define the top ,bottom,right,left of pipe and bird 
        float bTop = bird.y + bird.delta +bird.size;
        float bBottom = bird.y +bird.delta -bird.size;
        float bRight = bird.x + bird.size;
        float pTop = pipe.y;
        float pBottom = pipe.y + pipe.gap;
        float pLeft = pipe.x;
        float pRight=pipe.x+0.1f;
        //if statement check if its colied or not
        if (bTop > pBottom && bRight > pLeft && bRight<pRight) {
            bird.die = true;
        } else if (bBottom<pTop && bRight>pLeft && bRight<pRight) {
            bird.die=true;
        }
    }
}
