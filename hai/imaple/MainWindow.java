/*
 * Haiiiiiiii faggot
 */

package imaple;

//~--- non-JDK imports --------------------------------------------------------

import imaple.canvas.AbstractCanvas;

//~--- JDK imports ------------------------------------------------------------

import imaple.canvas.LoginScreen;
import imaple.util.TimerManager;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class MainWindow extends JFrame {
    public static final Image cursor = ImageFactory.loadImageCP("cimage/MapleCursor.png");
    //private static Image testLoadFromIDA = ImageFactory.loadImage(DataFileType.MAP, "Effect.img/event.coconut.victory.2.png");
    private boolean renderToScreen = false;
    private final Object innerCanvas = new Object();
    Dimension dim = getToolkit().getScreenSize();
    private AbstractCanvas canvas;
    private BufferStrategy dblBuffer;
    private JCanvas drawCanvas;
    private JLogicWorker logics;
    private int mouseX, mouseY;
    private JKeyHandler keyHandler;
    private JMouseHandler mouseHandler;
    private JRenderer renderer;
    private JWinHandler winHandler;

    public MainWindow() {
        super("iMaple - The 2nd Generation of the MapleStory Custom Client");
    }

    public void initialize(boolean fullscreen) {
        if (fullscreen) {
            setUndecorated(true);    //NO FUCKING DECORATIONS BITCH!!
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setFocusCycleRoot(false);
        pack();
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setMaximumSize(new Dimension(800, 600));
        setResizable(false);
        Cursor cursorCover = getToolkit().createCustomCursor(getToolkit().createImage("cover.jpg"), new Point(0, 0), "cover");
        setCursor(cursorCover);
        this.drawCanvas = new JCanvas();
        this.mouseHandler = new JMouseHandler();
        this.keyHandler = new JKeyHandler();
        this.winHandler = new JWinHandler();

        JPanel mainPanel = (JPanel) this.getContentPane();

        mainPanel.setSize(800, 600);
        mainPanel.setLayout(null);
        mainPanel.add(drawCanvas);

        this.drawCanvas.createBufferStrategy(2);
        dblBuffer = this.drawCanvas.getBufferStrategy();
        renderToScreen = true;
        this.drawCanvas.addMouseListener(mouseHandler);
        this.drawCanvas.addMouseMotionListener(mouseHandler);
        this.drawCanvas.addMouseWheelListener(mouseHandler);
        this.addKeyListener(keyHandler);
        this.addWindowListener(winHandler);
        renderer = new JRenderer();
        logics = new JLogicWorker();
        renderer.start();
        logics.start();
        setLocationRelativeTo(null);
    }

    public void addCanvas(AbstractCanvas canvas) {
        synchronized (innerCanvas) {
            this.canvas = canvas;
        }
    }

    public void removeCanvas() {
        synchronized (innerCanvas) {
            this.canvas = null;
        }
    }

    private void renderToScreen(final Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Dialog", Font.PLAIN, 10));
        g.fillRect(0, 0, 800, 600);

        if (canvas != null) {
            canvas.doRender(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("Loading iMaple's Login Screen...", 482, 34);
        TimerManager.getInstance().register(new Runnable() {
            public void run() {
                LoginScreen login = new LoginScreen();
                login.doRender(g);
            }
        }, 1);
        drawCursor(g);
    }

    public void drawCursor(Graphics2D g) {
        g.drawImage(cursor, mouseX, mouseY, null);
        g.drawImage(cursor, (dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2, null); // I just did this for a test..
    }

    public void onMousePressed(MouseEvent evt) {
        if (canvas != null) {
            canvas.onMousePressed(evt);
        }
    }

    public void onMouseReleased(MouseEvent evt) {
        if (canvas != null) {
            canvas.onMouseReleased(evt);
        }
    }

    public void onMouseMoved(MouseEvent evt) {
        if (canvas != null) {
            canvas.onMouseMoved(evt);
        }
    }

    public void onMouseDragged(MouseEvent evt) {
        if (canvas != null) {
            canvas.onMouseDragged(evt);
        }
    }

    public void onKeyPressed(KeyEvent evt) {
        if (canvas != null) {
            canvas.onKeyPressed(evt);
        }
    }

    public void onKeyReleased(KeyEvent evt) {
        if (canvas != null) {
            canvas.onKeyReleased(evt);
        }
    }

    public class JCanvas extends Canvas {
        public JCanvas() {
            setSize(new Dimension(800, 600));
        }
    }

    public class JKeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            onKeyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            onKeyReleased(e);
        }
    }

    public class JLogicWorker extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(999);
                } catch (Exception e) {}
            }
        }
    }

    public class JMouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            onMousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            onMouseReleased(e);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            onMouseDragged(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            onMouseMoved(e);
        }
    }

    public class JRenderer extends Thread {
        @Override
        public void run() {
            while (true) {
                if (renderToScreen) {
                    Graphics2D g = (Graphics2D) dblBuffer.getDrawGraphics();

                    renderToScreen(g);
                    dblBuffer.show();
                    g.dispose();
                }

                int delay = 20;

                try {
                    if (canvas != null) {
                        delay = canvas.getRenderDelay();
                    }

                    Thread.sleep(delay);
                } catch (InterruptedException e) {}
            }
        }
    }

    public class JWinHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
