/*
 * 创建日期 2006-3-30
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

public class AmusementUI extends CoreUI {
	private static final Logger logger = CoreUIObject.getLogger(AmusementUI.class);
	public static void createNewUI(CoreUIObject setUI) {
		try {
			IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			UIContext uiContext = new UIContext(setUI);
			IUIWindow wnd = fy
					.create("com.kingdee.eas.fdc.aimcost.client.AmusementUI",
							uiContext);
			wnd.show();
		} catch (UIException e) {
			SysUtil.abort(e);
		}
	}

	public static void createInThisUI(CoreUIObject setUI) {
		AmusementUI amuse = null;
		try {
			amuse = new AmusementUI();
		} catch (Exception e) {
			// @AbortException
			logger.info(e.getMessage(), e);
			return;
		}
		setUI.setLayout(null);
		Random random = new Random();
		BoundBall ball = amuse.new BoundBall(random.nextInt(setUI.getWidth()),
				random.nextInt(setUI.getHeight()), 5, random.nextInt(15),
				random.nextInt(360), setUI.getWidth(), setUI.getHeight() - 20);
		ball.addAreasByComponents(setUI.getComponents());
		setUI.add(ball);
	}

	public static void addHideMenuItem(final CoreUIObject setUI, KDMenu menu) {
		menu.add(new AbstractHidedMenuItem("ctrl F6") {
			public void action_actionPerformed() {
				createInThisUI(setUI);
			}
		});
		menu.add(new AbstractHidedMenuItem("ctrl shift F6") {
			public void action_actionPerformed() {
				createNewUI(setUI);
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public AmusementUI() throws Exception {
		super();

	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.setLayout(null);
		this.menuFile.add(new AbstractHidedMenuItem("F6") {
			public void action_actionPerformed() {
				testBallInThisUI();
			}
		});
	}

	public boolean destroyWindow() {
		for (int i = 0; i < this.getComponentCount(); i++) {
			if (this.getComponent(i) instanceof MoveBall) {
				MoveBall ball = (MoveBall) this.getComponent(i);
				ball.destroy();
			}
		}
		;
		return super.destroyWindow();
	}

	public void onShow() throws Exception {
		super.onShow();
	}

	public void testBallInThisUI() {
		WeightBall ball = new WeightBall(100, 100, 5);
		ball.setSpeed(10);
		ball.setDirection(90);
//		ball.setAddSpeed(0);
		ball.setWindowWidth(this.getWidth());
		ball.setWindowHeight(this.getHeight());
		this.add(ball);
	}

	public interface IArea {
		public boolean pointInArea(Point point);

		public boolean xInArea(int x);

		public boolean yInArea(int y);
	}

	public class RectArea implements IArea {
		public Point leftTop;

		public int width;

		public int height;

		public RectArea(Point leftTopPoint, int width, int height) {
			this.leftTop = leftTopPoint;
			this.width = width;
			this.height = height;
		}

		public boolean pointInArea(Point point) {
			if (point.x > leftTop.x && point.x <= leftTop.x + width
					&& point.y > leftTop.y && point.y < leftTop.y + height) {
				return true;
			}
			return false;
		}

		public boolean xInArea(int x) {
			if (x > leftTop.x && x <= leftTop.x + width) {
				return true;
			}
			return false;
		}

		public boolean yInArea(int y) {
			if (y > leftTop.y && y < leftTop.y + height) {
				return true;
			}
			return false;
		}

	}

	public class Ball extends JComponent {
		public Color color;

		public Color textColor;

		public Point center;

		public int acr;

		public String text;

		public Random random = new Random();

		protected void mouseReleased(MouseEvent e) {

		}

		protected void mousePressed(MouseEvent e) {

		}

		protected void mouseExited(MouseEvent e) {

		}

		protected void mouseEntered(MouseEvent e) {
		}

		protected void mouseClicked(MouseEvent e) {
		}

		protected void mouseMoved(MouseEvent e) {

		}

		protected void mouseDragged(MouseEvent e) {

		}

		public Ball(Point center, int acr) {
			this(center.x, center.y, acr);
		}

		public Ball(int x, int y, int acr) {
			this(x, y, acr, null);

		}

		public Ball(int x, int y, int acr, Color color) {
			if (color != null) {
				this.color = color;
			} else {
				this.color = new Color(random.nextInt(255),
						random.nextInt(255), random.nextInt(255));
			}
			this.resize(x, y, acr);
			this.addMouseListener(new MouseListener() {

				public void mouseReleased(MouseEvent e) {
					// mouseReleased(e);
				}

				public void mousePressed(MouseEvent e) {
					// mousePressed(e);
				}

				public void mouseExited(MouseEvent e) {
					// mouseExited(e);
				}

				public void mouseEntered(MouseEvent e) {
					// mouseEntered(e);
				}

				public void mouseClicked(MouseEvent e) {
					// mouseClicked(e);
				}

			});
			this.addMouseMotionListener(new MouseMotionListener() {

				public void mouseMoved(MouseEvent e) {

				}

				public void mouseDragged(MouseEvent e) {

				}

			});
		}

		protected void destroy() {
		}

		public void paint(Graphics g) {
			if (this.getParent().getComponentCount() == 0) {
				this.destroy();
			}
			super.paint(g);
			this.setOpaque(true);
			g.setColor(color);
			g.fillArc(1, 1, this.getWidth() - 2, this.getHeight() - 2, 0, 360);
			g.setColor(textColor);
			if (text != null) {
				int len = text.length();
				g.drawString(text, center.x - 5 * len, center.y - 3);
			}
		}

		public void resize(int x, int y, int acr) {
			this.center = new Point(x, y);
			this.acr = acr;
			this.setBounds(x - acr, y - acr, acr * 2, acr * 2);
		}

		public void resize(Point center, int acr) {
			this.center = center;
			this.acr = acr;
			this.setBounds(center.x - acr, center.y - acr, acr * 2, acr * 2);
		}

		public void setPos(int x, int y) {
			this.center = new Point(x, y);
			this.setBounds(x - acr, y - acr, acr * 2, acr * 2);
		}

		public void setPos(Point point) {
			this.center = point;
			this.setBounds(center.x - acr, center.y - acr, acr * 2, acr * 2);
		}

		public int getAcr() {
			return acr;
		}

		public void setAcr(int acr) {
			this.acr = acr;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Color getTextColor() {
			return textColor;
		}

		public void setTextColor(Color textColor) {
			this.textColor = textColor;
		}
	}

	public class MoveBall extends Ball {
		private java.util.Timer timer = new java.util.Timer(true);

		private TimerTask task = null;

		public float speed;

		protected void finalize() throws Throwable {
			super.finalize();
			destroy();
		}

		protected void destroy() {
			this.task.cancel();
			this.timer.cancel();
		}

		/**
		 * 
		 */
		private void setTimer() {
			task = new TimerTask() {
				public void run() {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							vefityThread();
							setPos(getNextPos());
						}
					});
				}
			};
			timer.schedule(task, 0, 50);
		}

		public MoveBall(Point center, int acr) {
			this(center, acr, 0);
		}

		public MoveBall(int x, int y, int acr) {
			this(x, y, acr, 0);
		}

		public MoveBall(Point center, int acr, int speed) {
			this(center.x, center.y, acr, speed);
		}

		public MoveBall(int x, int y, int acr, int speed) {
			super(x, y, acr);
			this.speed = speed;
			if (speed != -1) {
				setTimer();
			}
		}

		public void setSpeed(float speed) {
			this.speed = speed;
		}

		protected Point getNextPos() {
			return center;
		}

		private void vefityThread() {
			// if(isValid&&this.getParent()!=null&&!this.getParent().isValid()){
			// this.destroy();
			// }
			// if (!isValid) {
			// if (this.getParent() != null && this.getParent().isValid()) {
			// this.isValid = true;
			// }
			// }
			if (this.timer == null) {
				this.task.cancel();
			}
		}
	}

	public class LineMoveBall extends MoveBall {
		protected int direction;

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		public LineMoveBall(Point center, int acr) {
			super(center.x, center.y, acr);
		}

		public LineMoveBall(int x, int y, int acr) {
			super(x, y, acr);
			this.direction = 45;
		}

		public LineMoveBall(int x, int y, int acr, int speed) {
			this(x, y, acr, speed, 45);
		}

		public LineMoveBall(int x, int y, int acr, int speed, int direction) {
			super(x, y, acr, speed);
			this.direction = direction;
		}

		protected Point getNextPos() {
			int nextX = center.x
					+ new Double((speed + 1)
							* Math.sin(direction * 2 * Math.PI / 360))
							.intValue();
			int nextY = center.y
					+ new Double((speed + 1)
							* Math.cos(direction * 2 * Math.PI / 360))
							.intValue();
			return new Point(nextX, nextY);
		}

		public void adjustDirection() {
			while (this.direction < 0) {
				this.direction = this.direction + 360;
			}
			while (this.direction > 360) {
				this.direction = this.direction - 360;
			}
		}
	}

	public class WindowMoveBall extends LineMoveBall {
		private int windowWidth;

		private int windowHeight;

		public WindowMoveBall(int x, int y, int acr) {
			this(x, y, acr, 2000, 2000);
		}

		public WindowMoveBall(int x, int y, int acr, int windowWidth,
				int windowHeight) {
			super(x, y, acr);
			this.windowWidth = windowWidth;
			this.windowHeight = windowHeight;
		}

		public WindowMoveBall(int x, int y, int acr, int speed,
				int initDirection, int windowWidth, int windowHeight) {
			this(x, y, acr, windowWidth, windowHeight);
			this.speed = speed;
			this.direction = initDirection;
		}

		protected Point getNextPos() {
			Point nextPos = super.getNextPos();
			int nextX = nextPos.x;
			int nextY = nextPos.y;
			if (nextX - this.acr < 0 || nextX + this.acr > windowWidth) {
				reboundByY();
				return center;
			}
			if (nextY - this.acr < 0 || nextY + this.acr > windowHeight) {
				reboundByX();
				return center;
			}
			return nextPos;
		}

		public void reboundByY() {
			this.direction = 360 - this.direction;
			this.adjustDirection();
		}

		public void reboundByX() {
			this.direction = 180 - this.direction;
			this.adjustDirection();
		}

		public int getWindowHeight() {
			return windowHeight;
		}

		public void setWindowHeight(int windowHeight) {
			this.windowHeight = windowHeight;
		}

		public int getWindowWidth() {
			return windowWidth;
		}

		public void setWindowWidth(int windowWidth) {
			this.windowWidth = windowWidth;
		}

	}

	public class BoundBall extends WindowMoveBall {
		List areaList = new ArrayList();

		public BoundBall(int x, int y, int acr, int speed, int initDirection,
				int windowWidth, int windowHeight) {
			super(x, y, acr, speed, initDirection, windowWidth, windowHeight);
		}

		public BoundBall(int x, int y, int acr) {
			super(x, y, acr);
		}

		protected Point getNextPos() {
			Point nextPoint = super.getNextPos();
			for (int i = 0; i < areaList.size(); i++) {
				IArea area = (IArea) areaList.get(i);
				if (area.pointInArea(this.center)) {
					return nextPoint;
				}
				if (area.pointInArea(nextPoint)) {
					if (area.xInArea(center.x)) {
						this.reboundByX();
						return new Point(center.x + (nextPoint.x - center.x)
								/ 2, center.y);
					}
					if (area.yInArea(center.y)) {
						this.reboundByY();
						return new Point(center.x, center.y
								+ (nextPoint.y - center.y) / 2);
					}
					this.direction = this.direction - 180;
					this.adjustDirection();

				}
			}
			return nextPoint;
		}

		public void addAreasByComponents(Component[] components) {
			for (int i = 0; i < components.length; i++) {
				Component component = components[i];
				IArea area = new RectArea(component.getLocation(), component
						.getWidth(), component.getHeight());
				this.areaList.add(area);
			}
		}

		public void addArea(IArea area) {
			this.areaList.add(area);
		}
	}

	public class WeightBall extends WindowMoveBall {
		float addSpeed = (float) 0.1;

		int addSpeedDirection = 0;

		protected Point getNextPos() {
			this.speed += new Double(addSpeed
					* 30
					* Math.cos((addSpeedDirection - direction) * 2 * Math.PI
							/ 360)).intValue();

//			this.direction += Math.sin((direction - addSpeedDirection - 180) * 2
//							* Math.PI / 360)*addSpeed;
			if(direction-addSpeedDirection>180){
				direction+=10*addSpeed;
			} else if(direction-addSpeedDirection<180){
				direction-=1*addSpeed;
			}
			this.adjustDirection();
			Point point = super.getNextPos();
			return point;
		}

		public WeightBall(int x, int y, int acr) {
			super(x, y, acr);
		}

		public float getAddSpeed() {
			return addSpeed;
		}

		public void setAddSpeed(float addSpeed) {
			this.addSpeed = addSpeed;
		}

		public int getAddSpeedDirection() {
			return addSpeedDirection;
		}

		public void setAddSpeedDirection(int addSpeedDirection) {
			this.addSpeedDirection = addSpeedDirection;
		}

	}

	public class ClickBall extends WindowMoveBall {
		public ClickBall(int x, int y, int acr) {
			super(x, y, acr);
			this.setTextColor(Color.BLACK);
			this.setText("0");
			Color newColor = new Color(color.getRed(), color.getGreen(), color
					.getGreen(), 100);
			this.setColor(newColor);
		}

		protected void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					setColor(getNextColor());
					int i = Integer.parseInt(text);
					i++;
					setText(i + "");
				}
			});

		}

		private Color getNextColor() {
			int alpha = this.getColor().getAlpha() - 10;
			if (alpha < 0) {
				alpha = 0;
			}
			Color newColor = new Color(color.getRed(), color.getGreen(), color
					.getGreen(), alpha);
			return newColor;
		}
	}
}
