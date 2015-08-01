package com.kingdee.eas.fdc.basedata.util.subway;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.kingdee.bos.ctrl.swing.KDScrollPane;

/**
 * ����ͼ�ؼ� <br>
 * ���ؼ�������ʾһ���ؼ��Ľڵ㣬�Լ��ڵ���Ե�״̬��<br>
 * �����ͼվ·��ͼ����־��Щ�Ѿ��������ڵ�����վ����Щ��������<br>
 * 
 * 
 * @author emanon
 * 
 */
public class KDSubwayMap extends JComponent {
	private static final long serialVersionUID = 8667100535673641865L;

	// ��
	private int width = 1000;
	// ��
	private int height = 200;
	// ���ұ߾�
	private int clip = 30;
	// �ϱ߾�
	private int top = 55;
	// ��ѿ��
	int preferredWidth = 800;

	// �������
	private JPanel pnlScroll;
	// �����
	private KDSubwayLinePanel pnlLine;
	// �ڵ����
	private JPanel pnlItem;
	// ˵�����
	private JPanel pnlDesc;

	// �ڵ����Ƽ���
	private String[] names;
	// �ڵ�״̬����
	private KDSubwayItemState[] allStates;
	// �ڵ�״̬
	private int[] itemStates;
	// �ڵ����Լ���
	private Object[] values;
	// �ڵ㼯��
	private KDSubwayItemButton[] items;

	// �ڵ����
	private static int itemNums = 0;

	// �ڵ��С
	private static int itemSize = 10;

	// �������߳���(���ڵ��е��ľ���)
	private static int lineLength = 60;
	// �������߿��
	private static int lineHeight = 4;
	// ����������ɫ
	private static Color lineColor = Color.BLACK;

	// �ڵ���Ⱦ��
	private KDSubwayItemRenderer renderer;
	// �Ƿ���ʾ�ڵ�����
	private boolean isShowName = true;

	/**
	 * ���췽��<br>
	 * 
	 * @param names
	 *            ���Ƽ���
	 * @param allStates
	 *            ���п��ܵ�״̬����
	 * @param itemStates
	 *            ���ڵ��Ӧ��״̬
	 * @param values
	 *            ���ڵ���ϸ����ֵ
	 */
	public KDSubwayMap(String[] names, KDSubwayItemState[] allStates, int[] itemStates, Object[] values) {
		this(names, allStates, itemStates, values, lineLength, true);
	}

	/**
	 * ���췽��
	 * 
	 * @param names
	 *            ���Ƽ���
	 * @param allStates
	 *            ���п��ܵ�״̬����
	 * @param itemStates
	 *            ���ڵ��Ӧ��״̬
	 * @param values
	 *            ���ڵ���ϸ����ֵ
	 * @param lineLength
	 *            2���ڵ�������߳���
	 * @param isShowName
	 *            �Ƿ���ʾ�ڵ�����(��ʱ�����ƹ������ɲ���ʾ)
	 */
	public KDSubwayMap(String[] names, KDSubwayItemState[] allStates, int[] itemStates, Object[] values, int lineLength, boolean isShowName) {
		if (names.length != itemStates.length || (values != null && names.length != values.length) || names.length <= 0) {
			// ���Ƽ�����״̬���ϡ���ϸ���ϵĳ��Ȳ��ȣ�˵�����ڴ���
			return;
		}
		this.names = names;
		this.allStates = allStates;
		this.itemStates = itemStates;
		this.isShowName = isShowName;
		if (values != null) {
			this.values = values;
		} else {
			this.values = names;
		}
		itemNums = names.length;
		this.lineLength = lineLength;
		initCompt();
	}

	/**
	 * ���췽��<br>
	 * 
	 * @param names
	 *            ���Ƽ���
	 * @param allStates
	 *            ���п��ܵ�״̬����
	 * @param itemStates
	 *            ���ڵ��Ӧ��״̬
	 * @param values
	 *            ���ڵ���ϸ����ֵ
	 */
	public KDSubwayMap(KDSubwayItemState[] allStates) {
		this.allStates = allStates;
		addDesc();
	}

	public void setRenderer (KDSubwayItemRenderer renderer) {
		this.renderer = renderer;
		for (int i = 0; i < items.length; i++) {
			items[i].setRenderer(renderer);
		}
	}

	public KDSubwayItemRenderer getRenderer() {
		return renderer;
	}

	/**
	 * ��ʼ�������ؼ�
	 */
	protected void initCompt() {
		setLayout(null);
		this.clip = lineLength / 2;
		pnlScroll = new JPanel();
		pnlScroll.setLayout(null);
		preferredWidth = (clip * 2) + ((itemNums - 1) * lineLength);
		pnlScroll.setPreferredSize(new Dimension(preferredWidth, 100));
		addLine();
		addItem();
		KDScrollPane sp = new KDScrollPane(pnlScroll);
		sp.setBounds(0, 0, width, 120);
		add(sp);
		addDesc();
	}

	/**
	 * ������������
	 */
	protected void addLine() {
		pnlLine = new KDSubwayLinePanel(itemNums, lineLength, lineHeight, lineColor);
		pnlLine.setBounds(clip, top, pnlLine.getWidth(), pnlLine.getHeight());
		pnlScroll.add(pnlLine);
	}

	/**
	 * ��ӽڵ����
	 * <p>
	 * ����Ϊ͸�����������ϲ㣬���ɸ���������
	 */
	protected void addItem() {
		pnlItem = new JPanel();
		pnlItem.setLayout(null);
		// pnlItem.setBackground(Color.PINK);
		pnlItem.setOpaque(false);
		pnlItem.setBounds(0, 0, preferredWidth, 90);

		items = new KDSubwayItemButton[itemNums];
		for (int i = 0; i < itemNums; i++) {
			Color circleColor = allStates[itemStates[i]].getStatusColor();
			KDSubwayItemButton item = new KDSubwayItemButton();
			item.setName(names[i]);
			item.setOpaque(true);
			item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
			item.setBackground(circleColor);
			item.setDetail(values[i]);
			item.setBounds(clip + i * lineLength - itemSize, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
			items[i] = item;
			pnlItem.add(item);
			if (isShowName) {
				JLabel name = new JLabel(names[i], SwingConstants.CENTER);
				name.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
				name.setToolTipText(names[i]);
				// name.setText(names[i]);
				name.setBounds(clip + i * lineLength - lineLength / 2 + 5, top + lineHeight / 2 - itemSize - 18, lineLength - 10, 16);
				pnlItem.add(name);
			}
		}
		// ����Ϊ���ϲ㣬�������ܸ���ס�����ˣ�����������
		pnlScroll.add(pnlItem, 0);
	}

	/**
	 * ���˵�����
	 */
	protected void addDesc() {
		pnlDesc = new JPanel();
		pnlDesc.setBounds(0, 120, width, height - 120);
		pnlDesc.setOpaque(false);
		// pnlDesc.setBackground(Color.PINK);
		pnlDesc.setBorder(BorderFactory.createEmptyBorder(10, 4, 4, 10));
		for (int i = 0; i < allStates.length; i++) {
			Color circleColor = allStates[i].getStatusColor();
			JButton btn = new JButton();
			btn.setOpaque(true);
			btn.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
			btn.setBackground(circleColor);
			btn.setEnabled(false);
			pnlDesc.add(btn);
			JLabel label = new JLabel(allStates[i].getStatusName());
			pnlDesc.add(label);
		}
		add(pnlDesc);
	}

}
