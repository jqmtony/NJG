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
		/********** �������ΰ�ť ************/
		buiderLevelButtom();
		this.isSplit.setEnabled(true);
		/********** �������ΰ�ť ************/
	}

	/**
	 * ��ȡ������Ŀ��Ӧ�ĳɱ���Ŀ
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
					// ѡȡ����һ���Ͷ�����Ŀ
					getAllParentAccount(info, costSet);
					if (costSet.size() > 0) {
						CostAccountInfo otherInfo = null;
						CostAccountInfo cellInfo = null;
						// �����е��¼���Ŀ��ѡ��
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
							int n = MsgBox.showConfirm2New(null, "��ǰ��Ŀ:"
									+ info.getName()
									+ " �Ѿ��������ݣ��������ý����ԭ�����ݣ��Ƿ���� ");
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

					// ȡ�������¼���Ŀ
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

					// ���ȡ�����ϼ���Ŀ����ȡ�������¼���Ŀ
					for (int i = 0; i < kDTable1.getRowCount(); i++) {
						otherInfo = (CostAccountInfo) kDTable1.getCell(i, "id")
								.getUserObject();
						// if(alreadySetValueAccount != null){
						// if(alreadySetValueAccount.contains(otherInfo)){
						// int n = MsgBox.showConfirm2New(null,
						// "��ǰ��Ŀ:"+otherInfo
						// .getName()+" �Ѿ��������ݣ��������ý����ԭ�����ݣ��Ƿ���� ");
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
													"��ǰ��Ŀ:"
															+ otherInfo
																	.getName()
															+ " �Ѿ��������ݣ��������ý����ԭ�����ݣ��Ƿ���� ");
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
	 * ��ȡ�ÿ�Ŀ�������ϼ���Ŀ
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
	 * ���ɱ���Ŀ��KDTable��ȥ
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
				// ��ǰ��Ŀ�Ѿ���ѡ��
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
				// ���ڵ�����α����ж�
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
	 * ֻ�鿴�ɲ�ֿ�Ŀ�¼�
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
	 * ��õ�ǰ�ɱ���Ŀ����󼶴�
	 * 
	 * @return ��󼶴�
	 */
	private int getMaxLevel() {
		// ��������
		int level = 0;
		// ��ѯ���ݿ�sql
		String sql = "select max(FLevel) from T_FDC_CostAccount where FCurProject = ?";
		// ������ݿ��������
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		builder.addParam(projectInfo.getId().toString());
		try {
			// ��÷��ص���󼶴�
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
	 * ��̬�������ΰ�ť
	 */
	private void buiderLevelButtom() {
		// ��ȡ��󼶴�
		int level = this.getMaxLevel();
		// �������ΰ�ť��X����ֵ
		int x = 0;
		// �������ɼ��ΰ�ť
		for (int i = 0; i < level; i++) {
			// ����һ�����ΰ�ť
			KDWorkButton levelBtn = new KDWorkButton();
			// ���ð�ť�ļ���
			levelBtn.setText(Integer.toString(i + 1));
			// ���ð�ť����
			levelBtn.setFont(new Font("Dialog", 0, 9));
			// ���ð�ť��С
			levelBtn.setSize(14, 16);
			// ����ÿ����ť��λ��
			levelBtn.setLocation(x, 8);
			// Ϊÿ����ť��ӵ���¼�
			levelBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						// ���ü��ΰ�ť�¼�
						actionClickLevel(e);
					} catch (Exception e1) {
						handUIException(e1);
					}
				}

			});
			// ����ť��ӵ�ҳ����
			this.pagePanel.add(levelBtn);
			// ÿ��ѭ���ı䰴ť��X����ֵ
			x = x + 14;
		}
	}

	/**
	 * ÿ�����ΰ�ť�ĵ���¼�
	 * 
	 * @param e
	 *            ����İ�ť�¼�
	 * @throws Exception
	 */
	public void actionClickLevel(ActionEvent e) throws Exception {
		// ���¼��л�ȡ��ǰ����İ�ť
		KDWorkButton btn = (KDWorkButton) e.getSource();
		// �õ�����İ�ť����ֵ
		int sth = Integer.parseInt(btn.getText());
		// ���ݻ�õİ�ť����ֵ���в�ͬ�Ĺ���
		changeTreeView(sth);
	}

	/**
	 * ���ε����ť
	 * 
	 * @param level
	 *            ��ǰ����ļ���
	 */
	private void changeTreeView(int level) {
		IRow row;
		int rowLevel;
		level = level + 1;
		CostAccountInfo info;
		// �����������
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