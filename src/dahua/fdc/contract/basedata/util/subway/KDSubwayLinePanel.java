package com.kingdee.eas.fdc.basedata.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 节点间连接线面板
 * <p>
 * 由于连接线必须被节点覆盖，此处单独将连接线画到一个面板<br>
 * 在构建地铁图的时候，最先画连接线，然后在其上画节点。<br>
 * 连接线也同节点一样，是一段一段画出，方便后期扩展，每个节点间的线不同
 * 
 * @author emanon
 * 
 */
public class KDSubwayLinePanel extends JPanel {
	private static final long serialVersionUID = 5344132260589737475L;

	// 节点个数
	private int itemNums = 0;
	// 连接线线长度(两节点中点间的距离)
	private int lineLength = 60;
	// 连接线线宽度
	private int lineHeight = 4;
	// 连接线线颜色
	private Color lineColor = Color.BLACK;

	public KDSubwayLinePanel(int itemNums, int lineLength, int lineHeight,
			Color lineColor) {
		this.itemNums = itemNums;
		this.lineLength = lineLength;
		this.lineHeight = lineHeight;
		this.lineColor = lineColor;
		// 设置透明
		this.setOpaque(false);
		initPanel();
	}

	/**
	 * 设置面板大小
	 */
	private void initPanel() {
		int width = (itemNums - 1) * lineLength;
		Dimension size = new Dimension(width, lineHeight);
		this.setSize(size);
		this.setPreferredSize(size);
	}

	public void paint(Graphics g) {
		super.paint(g);
		drawLines(g);
	}

	/**
	 * 注：<br>
	 * 这里的线条其实是一个个首尾相连的完全一样的长矩形<br>
	 * 之所以不一次性画完，是为了方便以后扩展，可能会根据每个节点画不同的线条<br>
	 * 比如填充不同颜色以表示状态等
	 */
	private void drawLines(Graphics g) {
		for (int i = 0; i < itemNums; i++) {
			drawLine(g, i);
		}
	}

	/**
	 * 画连接线<br>
	 * 第一个节点不画线<br>
	 * 
	 * @param g
	 * 
	 * @param index
	 */
	private void drawLine(Graphics g, int index) {
		if (index == 0) {
			return;
		}
		g.setColor(lineColor);
		g.fillRect((index - 1) * lineLength, 0, lineLength, lineHeight);
	}

}