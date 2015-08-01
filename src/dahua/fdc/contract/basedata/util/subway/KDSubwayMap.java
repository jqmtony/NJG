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
 * 地铁图控件 <br>
 * 本控件用于显示一串关键的节点，以及节点各自的状态，<br>
 * 就像地图站路线图，标志哪些已经过，正在到达哪站，哪些即将到达<br>
 * 
 * 
 * @author emanon
 * 
 */
public class KDSubwayMap extends JComponent {
	private static final long serialVersionUID = 8667100535673641865L;

	// 宽
	private int width = 1000;
	// 高
	private int height = 200;
	// 左右边距
	private int clip = 30;
	// 上边距
	private int top = 55;
	// 最佳宽度
	int preferredWidth = 800;

	// 滚动面板
	private JPanel pnlScroll;
	// 线面板
	private KDSubwayLinePanel pnlLine;
	// 节点面板
	private JPanel pnlItem;
	// 说明面板
	private JPanel pnlDesc;

	// 节点名称集合
	private String[] names;
	// 节点状态集合
	private KDSubwayItemState[] allStates;
	// 节点状态
	private int[] itemStates;
	// 节点属性集合
	private Object[] values;
	// 节点集合
	private KDSubwayItemButton[] items;

	// 节点个数
	private static int itemNums = 0;

	// 节点大小
	private static int itemSize = 10;

	// 连接线线长度(两节点中点间的距离)
	private static int lineLength = 60;
	// 连接线线宽度
	private static int lineHeight = 4;
	// 连接线线颜色
	private static Color lineColor = Color.BLACK;

	// 节点渲染器
	private KDSubwayItemRenderer renderer;
	// 是否显示节点名称
	private boolean isShowName = true;

	/**
	 * 构造方法<br>
	 * 
	 * @param names
	 *            名称集合
	 * @param allStates
	 *            所有可能的状态集合
	 * @param itemStates
	 *            各节点对应的状态
	 * @param values
	 *            各节点明细属性值
	 */
	public KDSubwayMap(String[] names, KDSubwayItemState[] allStates, int[] itemStates, Object[] values) {
		this(names, allStates, itemStates, values, lineLength, true);
	}

	/**
	 * 构造方法
	 * 
	 * @param names
	 *            名称集合
	 * @param allStates
	 *            所有可能的状态集合
	 * @param itemStates
	 *            各节点对应的状态
	 * @param values
	 *            各节点明细属性值
	 * @param lineLength
	 *            2个节点间连接线长度
	 * @param isShowName
	 *            是否显示节点名称(有时候名称过长，可不显示)
	 */
	public KDSubwayMap(String[] names, KDSubwayItemState[] allStates, int[] itemStates, Object[] values, int lineLength, boolean isShowName) {
		if (names.length != itemStates.length || (values != null && names.length != values.length) || names.length <= 0) {
			// 名称集合与状态集合、明细集合的长度不等，说明存在错误
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
	 * 构造方法<br>
	 * 
	 * @param names
	 *            名称集合
	 * @param allStates
	 *            所有可能的状态集合
	 * @param itemStates
	 *            各节点对应的状态
	 * @param values
	 *            各节点明细属性值
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
	 * 初始化整个控件
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
	 * 添加连接线面板
	 */
	protected void addLine() {
		pnlLine = new KDSubwayLinePanel(itemNums, lineLength, lineHeight, lineColor);
		pnlLine.setBounds(clip, top, pnlLine.getWidth(), pnlLine.getHeight());
		pnlScroll.add(pnlLine);
	}

	/**
	 * 添加节点面板
	 * <p>
	 * 设置为透明，且在最上层，即可覆盖连接线
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
		// 设置为最上层，这样就能覆盖住线条了，该死的线条
		pnlScroll.add(pnlItem, 0);
	}

	/**
	 * 添加说明面板
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
