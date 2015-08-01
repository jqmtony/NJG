/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTCellEditorListener;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SetCostAccountLevelUI extends AbstractSetCostAccountLevelUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SetCostAccountLevelUI.class);

	private ProjectInvestPlanEditUI parentUI = null;

	private CurProjectInfo projectInfo = null;

	private List alreadySelectedAccount;

	private List alreadySetValueAccount;

	private List accountList;

	private Map setValueMap;
	private boolean isSelected = true;
	private Color lock = new Color(254, 254, 211);
	
	private Map parentMap;

	/**
	 * output class constructor
	 */
	public SetCostAccountLevelUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		initTableStyle();
		String curProjectId = null;

		Map uiContext = getUIContext();
		if (uiContext.get("curProject") != null) {
			projectInfo = (CurProjectInfo) uiContext.get("curProject");
			curProjectId = projectInfo.getId().toString();
		}
		if (uiContext.get("parentUI") != null) {
			parentUI = (ProjectInvestPlanEditUI) uiContext.get("parentUI");
		}

		if (uiContext.get("alreadySelectAccount") != null) {
			alreadySelectedAccount = (List) uiContext
					.get("alreadySelectAccount");
		}
		if (uiContext.get("setValueMap") != null) {
			setValueMap = (Map) uiContext.get("setValueMap");
		}
		if (uiContext.get("alreadySetValueAccount") != null) {
			alreadySetValueAccount = (List) uiContext
					.get("alreadySetValueAccount");
		}
		fillCostAccountToTable(curProjectId);
		if (alreadySelectedAccount != null && alreadySelectedAccount.size() > 0) {
			refreshTableByIsSelected(alreadySelectedAccount);
		}
		/********** 创建级次按钮 ************/
		buiderLevelButtom();
		this.isSplit.setEnabled(true);
		/********** 创建级次按钮 ************/
	}

	/**
	 * 获取工程项目对应的成本科目
	 * 
	 * @param curProjectId
	 * @return
	 */
	protected CostAccountCollection getAllCostAccountByCurProjectId(
			String curProjectId) {
		CostAccountCollection cols = null;
		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("parent.*"));
		viewInfo.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProject", curProjectId));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		SorterItemCollection sort = new SorterItemCollection();
		sort.add(new SorterItemInfo("longnumber"));
		if (isSelected) {
			filter.getFilterItems().add(
					new FilterItemInfo("isSplit", new Integer(1), CompareType.EQUALS));
			this.isSplit.setSelected(true);
		} else {
			filter.remove("isSplit");
			this.isSplit.setSelected(false);
		}
		viewInfo.setSorter(sort);
		viewInfo.setFilter(filter);
		try {
			cols = CostAccountFactory.getRemoteInstance()
					.getCostAccountCollection(viewInfo);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cols;
	}

	protected void initTableStyle() {
		kDTable1.checkParsed();
		KDCheckBox chkBox = new KDCheckBox();
		final KDTDefaultCellEditor editor = new KDTDefaultCellEditor(chkBox);
		editor.addCellEditorListener(new KDTCellEditorListener() {

			public void editingCanceled() {
				// TODO Auto-generated method stub

			}

			public void editingChanged() {
				// TODO Auto-generated method stub
				KDCheckBox chk = (KDCheckBox) editor.getComponent();
				boolean isSelected = chk.isSelected();
				int rowIndex = kDTable1.getSelectManager().getActiveRowIndex();
				CostAccountInfo info = (CostAccountInfo) kDTable1.getCell(
						rowIndex, "id").getUserObject();
				if (isSelected) {

					Set costSet = new HashSet();
					// 选取所有一级和二级科目
					getAllParentAccount(info, costSet);
					if (costSet.size() > 0) {
						CostAccountInfo otherInfo = null;
						CostAccountInfo cellInfo = null;
						// 把所有的下级科目都选上
						for (Iterator it = costSet.iterator(); it.hasNext();) {
							otherInfo = (CostAccountInfo) it.next();
							for (int i = 0; i < kDTable1.getRowCount(); i++) {
								cellInfo = (CostAccountInfo) kDTable1.getCell(
										i, "id").getUserObject();
								if (cellInfo.getId().toString().equals(
										otherInfo.getId().toString())) {
									kDTable1.getCell(i, "id").setValue(
											Boolean.TRUE);
								}
							}
						}
					}

				} else {
					if (setValueMap != null) {
						if (setValueMap.containsKey(info.getId().toString())) {
							int n = MsgBox.showConfirm2New(null, "当前科目:"
									+ info.getName()
									+ " 已经存在数据，重新设置将清除原有数据，是否进行 ");
							if (JOptionPane.NO_OPTION == n) {
								// kDTable1.getCell(rowIndex,
								// "id").setValue(Boolean.TRUE);
								chk.setSelected(true);
								abort();
							} else {
								// kDTable1.getCell(rowIndex,
								// "id").setValue(Boolean.FALSE);
								chk.setSelected(false);
							}
						}
					}

					// 取出所有下级科目
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("longnumber", info
									.getLongNumber()
									+ "!%", CompareType.LIKE));
					filter.getFilterItems().add(
							new FilterItemInfo("curProject", projectInfo
									.getId().toString()));
					view.setFilter(filter);
					List childrenList = null;
					try {
						CostAccountCollection cols = CostAccountFactory
								.getRemoteInstance().getCostAccountCollection(
										view);
						if (cols.size() > 0) {
							childrenList = new ArrayList();
							for (Iterator it = cols.iterator(); it.hasNext();) {
								childrenList.add(((CostAccountInfo) it.next())
										.getId().toString());
							}
						}
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CostAccountInfo otherInfo = null;

					// 如果取消了上级科目，则取消所有下级科目
					for (int i = 0; i < kDTable1.getRowCount(); i++) {
						otherInfo = (CostAccountInfo) kDTable1.getCell(i, "id")
								.getUserObject();
						// if(alreadySetValueAccount != null){
						// if(alreadySetValueAccount.contains(otherInfo)){
						// int n = MsgBox.showConfirm2New(null,
						// "当前科目:"+otherInfo
						// .getName()+" 已经存在数据，重新设置将清除原有数据，是否进行 ");
						// if(JOptionPane.NO_OPTION == n){
						// kDTable1.getCell(i, "id").setValue(Boolean.TRUE);
						// abort();
						// }
						// }
						// }
						if (childrenList != null
								&& childrenList.contains(otherInfo.getId()
										.toString())) {

							if (setValueMap != null) {
								if (setValueMap.containsKey(otherInfo.getId()
										.toString())) {
									int n = MsgBox
											.showConfirm2New(
													null,
													"当前科目:"
															+ otherInfo
																	.getName()
															+ " 已经存在数据，重新设置将清除原有数据，是否进行 ");
									if (JOptionPane.NO_OPTION == n) {
										// kDTable1.getCell(rowIndex,
										// "id").setValue(Boolean.TRUE);
										chk.setSelected(true);
									} else {
										// kDTable1.getCell(i,
										// "id").setValue(Boolean.FALSE);
										chk.setSelected(false);
									}
								} else {
									kDTable1.getCell(i, "id").setValue(
											Boolean.FALSE);
								}
							} else {
								kDTable1.getCell(i, "id").setValue(
										Boolean.FALSE);
							}

						}

					}
				}

			}

			public void editingStopped() {
				// TODO Auto-generated method stub
			}
		});
		this.kDTable1.getColumn("id").setEditor(editor);
		this.kDTable1.getColumn("number").setWidth(150);
		this.kDTable1.getColumn("name").setWidth(205);
		this.kDTable1.getColumn("number").getStyleAttributes().setLocked(true);
		this.kDTable1.getColumn("name").getStyleAttributes().setLocked(true);
	}

	/***
	 * 获取该科目的所有上级科目
	 */
	protected void getAllParentAccount(CostAccountInfo costAccount, Set set) {
		CostAccountInfo info = costAccount;
		if (info.getParent() != null) {
			set.add(info.getParent());
			info = info.getParent();
			getAllParentAccount(info, set);
		}
	}

	/**
	 * 填充成本科目到KDTable中去
	 */
	protected void fillCostAccountToTable(String curProjectId) {
		kDTable1.refresh();
		CostAccountCollection cols = getAllCostAccountByCurProjectId(curProjectId);
		kDTable1.checkParsed();
		IRow row = null;
		CostAccountInfo account = null;
		
		parentMap = new HashMap();
		String pId = "";
		String Id = "";
		CostAccountInfo costAccountInfo = null;
		for (int i = 0; i < cols.size(); i++) {
			costAccountInfo = cols.get(i);
			if (costAccountInfo.getParent() != null) {
				Id = costAccountInfo.getId().toString();
				pId = costAccountInfo.getParent().getId().toString();

				if (!Id.equals(pId)) {
					parentMap.put(pId, costAccountInfo.getParent().getName());
				}
			}
		}
		
		
		
		if (cols.size() > 0) {

			for (Iterator it = cols.iterator(); it.hasNext();) {
				row = this.kDTable1.addRow();
				account = (CostAccountInfo) it.next();
				String id = account.getId().toString();
				// 当前科目已经被选中
				if (alreadySelectedAccount != null
						&& alreadySelectedAccount.size() > 0) {
					if (!alreadySelectedAccount.contains(account)) {
						row.getCell("id").setValue(Boolean.FALSE);
					} else {
						row.getCell("id").setValue(Boolean.TRUE);
						
						if (account.getLevel() <= 2) {
							row.getCell("id").getStyleAttributes().setLocked(
									true);
							row.getStyleAttributes().setBackground(lock);
						}
					}
				} else {
					if (account.getLevel() <= 2) {
						row.getCell("id").setValue(Boolean.TRUE);
						row.getCell("id").getStyleAttributes().setLocked(true);
						row.getStyleAttributes().setBackground(lock);
					} else {
						row.getCell("id").setValue(Boolean.FALSE);
					}
					if (!account.isIsLeaf()) {
						row.getStyleAttributes().setBackground(lock);
					}

				}
				row.getCell("id").setUserObject(account);
				row.getCell("number").setValue(
						account.getLongNumber().replace('!', '.'));
				row.getCell("name").setValue(account.getName());
				// 用于点击级次便于判断
				row.setUserObject(account);
			}
		}
		IRow curRow = null;
		String curId = null;
		CostAccountInfo acct = null;
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			curRow = kDTable1.getRow(i);
			acct = (CostAccountInfo) curRow.getUserObject();
			curId = acct.getId().toString();
			if (!parentMap.containsKey(curId)) {
				curRow.getStyleAttributes().setBackground(Color.WHITE);
			}
		}

	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionOk_actionPerformed
	 */
	public void actionOk_actionPerformed(ActionEvent e) throws Exception {
		super.actionOk_actionPerformed(e);
		accountList = new ArrayList();
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			boolean isChecked = ((Boolean) kDTable1.getCell(i, "id").getValue())
					.booleanValue();
			if (isChecked) {
				CostAccountInfo account = (CostAccountInfo) kDTable1.getCell(i,
						"id").getUserObject();
				// accountMap.put(account.getLongNumber(),account);
				accountList.add(account);
				// if(account.getParent() != null &&
				// !accountList.contains(account.getParent()) ){
				// accountList.add(account.getParent());
				// }
			}

		}
		// refreshTableByIsSelected(accountList);
		// uiContext.put("account",accountMap);
		parentUI.fillCostAccount(accountList);
		this.destroyWindow();
	}

	/**
	 * output actionCancelSelect_actionPerformed
	 */
	public void actionCancelSelect_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelSelect_actionPerformed(e);
		this.disposeUIWindow();
	}

	public void refreshTableByIsSelected(List selectedAccount) {
		KDTable table = this.kDTable1;
		if (table.getRowCount() > 0) {
			for (int i = 0; i < table.getRowCount(); i++) {
				boolean isSelected = selectedAccount.contains(table.getCell(i,
						"id").getUserObject());
				boolean cellValue = Boolean.valueOf(
						table.getCell(i, "id").getValue().toString())
						.booleanValue();
				if (isSelected != cellValue && isSelected) {
					table.getCell(i, "id")
							.setValue(Boolean.valueOf(isSelected));
				}
			}
		}
	}
	
	/**
	 * 只查看可拆分科目事件
	 */
	public void actionIsSplit_actionPerformed(ActionEvent e) throws Exception {
		isSelected = this.isSplit.isSelected();
		Map uiContext = getUIContext();
		String curProjectId = null;
		if (uiContext.get("curProject") != null) {
			projectInfo = (CurProjectInfo) uiContext.get("curProject");
			curProjectId = projectInfo.getId().toString();
		}
		fillCostAccountToTable(curProjectId);
	}
	
	  /**
	 * 获得当前成本科目的最大级次
	 * 
	 * @return 最大级次
	 */
	private int getMaxLevel() {
		// 申明级次
		int level = 0;
		// 查询数据库sql
		String sql = "select max(FLevel) from T_FDC_CostAccount where FCurProject = ?";
		// 获得数据库操作对象
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		builder.addParam(projectInfo.getId().toString());
		try {
			// 获得返回的最大级次
			IRowSet set = builder.executeQuery();
			while (set.next()) {
				level = set.getInt(1);
			}
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
		return level;
	}

	/**
	 * 动态创建级次按钮
	 */
	private void buiderLevelButtom() {
		// 获取最大级次
		int level = this.getMaxLevel();
		// 申明级次按钮的X坐标值
		int x = 0;
		// 便利生成级次按钮
		for (int i = 0; i < level; i++) {
			// 创建一个级次按钮
			KDWorkButton levelBtn = new KDWorkButton();
			// 设置按钮的级次
			levelBtn.setText(Integer.toString(i + 1));
			// 设置按钮字体
			levelBtn.setFont(new Font("Dialog", 0, 9));
			// 设置按钮大小
			levelBtn.setSize(14, 16);
			// 设置每个按钮的位置
			levelBtn.setLocation(x, 8);
			// 为每个按钮添加点击事件
			levelBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						// 调用级次按钮事件
						actionClickLevel(e);
					} catch (Exception e1) {
						handUIException(e1);
					}
				}

			});
			// 将按钮添加到页面中
			this.pagePanel.add(levelBtn);
			// 每次循环改变按钮的X坐标值
			x = x + 14;
		}
	}

	/**
	 * 每个级次按钮的点击事件
	 * 
	 * @param e
	 *            点击的按钮事件
	 * @throws Exception
	 */
	public void actionClickLevel(ActionEvent e) throws Exception {
		// 从事件中获取当前点击的按钮
		KDWorkButton btn = (KDWorkButton) e.getSource();
		// 得到点击的按钮级次值
		int sth = Integer.parseInt(btn.getText());
		// 根据获得的按钮级次值进行不同的过滤
		changeTreeView(sth);
	}

	/**
	 * 级次点击按钮
	 * 
	 * @param level
	 *            当前点击的级次
	 */
	private void changeTreeView(int level) {
		IRow row;
		int rowLevel;
		level = level + 1;
		CostAccountInfo info;
		// 遍历整个表格
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			row = kDTable1.getRow(i);
			info = (CostAccountInfo) row.getUserObject();
			rowLevel = info.getLevel();
			if (level < rowLevel) {
				row.getStyleAttributes().setHided(true);
			}
			if(level > rowLevel){
				row.getStyleAttributes().setHided(false);
			}
			if (level == rowLevel) {
				row.getStyleAttributes().setHided(true);
			}
		}
	}

}