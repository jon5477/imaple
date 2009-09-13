/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import imaple.canvas.AbstractCanvas;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
    private static final Image cursor = ImageFactory.loadImageCP("cimage/MapleCursor.png");
    private JCanvas drawCanvas;
    private BufferStrategy dblBuffer;
    private boolean renderToScreen = false;
    private JMouseHandler myMouseBitch;
    private JKeyHandler myKeyBitch;
    private JWinHandler winHandler;
    private JRenderer renderer;
    private JLogicWorker logics;
    private AbstractCanvas canvas;
    private final Object yourMomPussy = new Object();
    private int mouseX, mouseY;

    public MainWindow() {
	super("iMaple - The 2nd Generation of the MapleStory Custom Client");
    }

    public void initiallize(boolean fullscreen) {
	if (fullscreen) {
            setUndecorated(true); //NO FUCKING DECORATIONS BITCH!!
	}
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setFocusCycleRoot(false);
        pack();
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setMaximumSize(new Dimension(800, 600));
        setResizable(false);
        Cursor customCursor = getToolkit().createCustomCursor(getToolkit().createImage("twomengayorgy.jpg"),
                new Point(0, 0), "twomengayorgy");
        setCursor(customCursor);
	this.drawCanvas = new JCanvas();
	this.myMouseBitch = new JMouseHandler();
	this.myKeyBitch = new JKeyHandler();
	this.winHandler = new JWinHandler();
	JPanel theFuckingPanel = (JPanel) this.getContentPane();
        theFuckingPanel.setSize(800, 600);
        theFuckingPanel.setLayout(null);
	theFuckingPanel.add(drawCanvas);
	this.drawCanvas.createBufferStrategy(2);
	dblBuffer = this.drawCanvas.getBufferStrategy();
	renderToScreen = true;
	this.drawCanvas.addMouseListener(myMouseBitch);
	this.drawCanvas.addMouseMotionListener(myMouseBitch);
	this.drawCanvas.addMouseWheelListener(myMouseBitch);
	this.addKeyListener(myKeyBitch);
	this.addWindowListener(winHandler);
	renderer = new JRenderer();
	logics = new JLogicWorker();
	renderer.start();
	logics.start();
	setLocationRelativeTo(null);
    }

    public void addCanvas(AbstractCanvas canvas) {
	synchronized (yourMomPussy) { //Careful, we must synchronize this, who wants your mom's pussy used at the same time by 2 different things??!
            this.canvas = canvas;
	}
    }

    public void removeCanvas() {
	synchronized (yourMomPussy) { //Careful, we must synchronize this, who wants your mom's pussy used at the same time by 2 different things??!
            this.canvas = null;
	}
    }

    private void renderToScreen(Graphics2D g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, 800, 600);
        if (canvas != null) {
            canvas.doRender(g);
	}
	g.drawImage(cursor, mouseX, mouseY, null);
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

    public class JWinHandler extends WindowAdapter {
	@Override
	public void windowClosing(WindowEvent e) {
            System.exit(0);
	}
    }

    public class JRenderer extends Thread {
	@Override public void run() {
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

    public class JLogicWorker extends Thread {
        @Override public void run() {
            while (true) {
                try {
                    Thread.sleep(999);
                } catch (Exception e) {}
            }
        }
    }
}
