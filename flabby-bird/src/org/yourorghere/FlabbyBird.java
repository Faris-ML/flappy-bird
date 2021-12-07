package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class FlabbyBird implements GLEventListener, KeyListener {

    static float delta = 0;
    static float x = 0;
    static float rot = 0;
    static float g = 2f;
    static int height = 720;
    static int width = 1280;
    static float h = width / height;
    static float a = (2f + 0.15f) / 4f;
    static bird bird = new bird(0, 0, 0.03f);
    static pipe pipe1 = new pipe(1f + (0f * a), 0.3f);
    static pipe pipe2 = new pipe(1f + (1f * a), 0.3f);
    static pipe pipe3 = new pipe(1f + (2f * a), 0.3f);
    static pipe pipe4 = new pipe(1f + (3f * a), 0.3f);
    static pipe pipe[] = {pipe1, pipe2, pipe3, pipe4};
    static background bg = new background();
    static int view;
    static TextRenderer t;
    static boolean test = false;
    static float faster=0.003f;

    //static input listener=new input();
    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new FlabbyBird());
        canvas.addKeyListener(new FlabbyBird());
        frame.add(canvas);
        frame.setSize(1280, 720);
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

        //remove black background
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

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

        if (bird.finish) {
            view = 2;
        }

        if (view == 0) {
            startScreen(drawable);
        }
        if (view == 1) {
            
            t.setColor(Color.WHITE);
            // draw the background
            gl.glLoadIdentity();
            gl.glPushMatrix();
            bg.draw(gl);
            gl.glPopMatrix();

            //draw the pipes
            gl.glLoadIdentity();
            gl.glPushMatrix();
            for (int i = 0; i < pipe.length; i++) {
                pipe[i].draw(gl);
                Colosion(bird, pipe[i]);//coloied function
            }

            gl.glPopMatrix();

            // draw the bird
            gl.glLoadIdentity();
            gl.glPushMatrix();
            bird.setDelta(delta);
            bird.draw(gl);
            delta = bird.getDelta();
            gl.glPopMatrix();
        }
        if (view == 2) {
            endScreen(drawable);
        }

    }

    public void startScreen(GLAutoDrawable drawable) {
        String start = "Welcome to Flabby Bird";
        text(drawable, start, 330, 400, 50);
        String enter = "press Enter to start...";
        text(drawable, enter, 520, 320, 20);
        String controls = "Controls:";
        text(drawable, controls, 16, 70, 15);
        String controls1 = "Spacebar to jump-up";
        text(drawable, controls1, 16, 55, 15);
    }

    public void endScreen(GLAutoDrawable drawable) {
        String start = "Game Over";
        text(drawable,start, 350, 500, 50);
        int count = pipe1.counter+pipe2.counter+pipe3.counter+pipe4.counter;
        String Congratulations = "Thank you for playing, Your score: "+count;
        text(drawable,Congratulations, 180, 400, 50);
        String enter = "press P to play Again...";
        text(drawable,enter, 390, 270, 20);
        String close = "press Esc to close...";
        text(drawable,close, 390, 230, 20);
    }

    public void text(GLAutoDrawable drawable, String s, int x, int y, int size) {
        t = new TextRenderer(new java.awt.Font("Verdana", java.awt.Font.BOLD, size));
        t.setColor(Color.BLACK);
        t.beginRendering(drawable.getWidth(), drawable.getHeight());
        t.draw(s, x, y);
        t.endRendering();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_SPACE && bird.die == false) {// if pressed jump and rotate
            delta = delta + 0.06f;
            rot = g + 45;
            g = 2;
        } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {// if pressed jump and rotate
            view = 1;
        } else if (ke.getKeyCode() == KeyEvent.VK_P && bird.finish == true) {// if pressed jump and rotate
            view = 1;
            bird.setDie(false);;
            delta=0;
            bird.setFinish(false);
            for (int i = 0; i < pipe.length; i++) {
               pipe[i].setX(1+(float)i*a);
               pipe[i].counter=0;
            }

        } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE && bird.finish == true) {// if pressed jump and rotate
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    public void Colosion(bird bird, pipe pipe) {// colosion function

        //define the top ,bottom,right,left of pipe and bird 
        float bTop = bird.y + bird.delta + bird.size;
        float bBottom = bird.y + bird.delta - bird.size;
        float bRight = bird.x + bird.size;
        float pTop = pipe.y;
        float pBottom = pipe.y + pipe.gap;
        float pLeft = pipe.x;
        float pRight = pipe.x + 0.15f;
        //if statement check if its colied or not
        if (bTop > pBottom && bRight > pLeft && bRight < pRight) {
            bird.die = true;
        } else if (bBottom < pTop && bRight > pLeft && bRight < pRight) {
            bird.die = true;
        }
    }
}