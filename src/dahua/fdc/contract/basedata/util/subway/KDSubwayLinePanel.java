package com.kingdee.eas.fdc.basedata.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * �ڵ�����������
 * <p>
 * ���������߱��뱻�ڵ㸲�ǣ��˴������������߻���һ�����<br>
 * �ڹ�������ͼ��ʱ�����Ȼ������ߣ�Ȼ�������ϻ��ڵ㡣<br>
 * ������Ҳͬ�ڵ�һ������һ��һ�λ��������������չ��ÿ���ڵ����߲�ͬ
 * 
 * @author emanon
 * 
 */
public class KDSubwayLinePanel extends JPanel {
	private static final long serialVersionUID = 5344132260589737475L;

	// �ڵ����
	private int itemNums = 0;
	// �������߳���(���ڵ��е��ľ���)
	private int lineLength = 60;
	// �������߿��
	private int lineHeight = 4;
	// ����������ɫ
	private Color lineColor = Color.BLACK;

	public KDSubwayLinePanel(int itemNums, int lineLength, int lineHeight,
			Color lineColor) {
		this.itemNums = itemNums;
		this.lineLength = lineLength;
		this.lineHeight = lineHeight;
		this.lineColor = lineColor;
		// ����͸��
		this.setOpaque(false);
		initPanel();
	}

	/**
	 * ��������С
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
	 * ע��<br>
	 * �����������ʵ��һ������β��������ȫһ���ĳ�����<br>
	 * ֮���Բ�һ���Ի��꣬��Ϊ�˷����Ժ���չ�����ܻ����ÿ���ڵ㻭��ͬ������<br>
	 * ������䲻ͬ��ɫ�Ա�ʾ״̬��
	 */
	private void drawLines(Graphics g) {
		for (int i = 0; i < itemNums; i++) {
			drawLine(g, i);
		}
	}

	/**
	 * ��������<br>
	 * ��һ���ڵ㲻����<br>
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