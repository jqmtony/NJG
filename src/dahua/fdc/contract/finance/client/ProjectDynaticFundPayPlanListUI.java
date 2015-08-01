package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanInfo;
import com.kingdee.eas.fdc.finance.SignStateEnum;
import com.kingdee.eas.fdc.finance.client.util.CellAmountUnitRender;
import com.kingdee.eas.fdc.finance.client.util.ChartUtil;
import com.kingdee.eas.fm.common.AmountUnitEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 项目动态资金支付计划
 */
public class ProjectDynaticFundPayPlanListUI extends AbstractProjectDynaticFundPayPlanListUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectDynaticFundPayPlanListUI.class);
	private CellAmountUnitRender moneyRender = new CellAmountUnitRender(AmountUnitEnum.yuan, 2);
	private KDTDefaultCellEditor moneyEditor;

	public ProjectDynaticFundPayPlanListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionView.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionUnit.setEnabled(true);
		this.actionSyn.setEnabled(true);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		actionSyn.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_refresh"));
		actionMonthPayDistributePic.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_autoarrange"));
		actionPayProgressPic.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_autoarrange"));
		initUnit();
	}
	public void onShow() throws Exception {
		super.onShow();
		this.actionWorkFlowG.setVisible(false);
	}

	private void initUnit() {
		btnUnit.setIcon(EASResource.getIcon("imgTbtn_unit"));
		List unitList = (List) AmountUnitEnum.getEnumList();
		final KDMenu menu = new KDMenu(btnUnit.getText());
		menu.setIcon(EASResource.getIcon("imgTbtn_unit"));
		for (int i = 0; i < unitList.size(); i++) {
			final AmountUnitEnum unitEnum = (AmountUnitEnum) unitList.get(i);
			KDMenuItem menuItem = new KDMenuItem(unitEnum.getAlias());
			menuItem.setUserObject(unitEnum);
			menuItem.setAction(new ItemAction() {
				public void actionPerformed(ActionEvent e) {
					KDMenuItem source = (KDMenuItem) e.getSource();
					for (int i = 0; i < btnUnit.getAssistButtonCount(); i++) {
						KDMenuItem assitButton = (KDMenuItem) btnUnit.getAssitButton(i);
						KDMenuItem menuItem = (KDMenuItem) menu.getMenuComponent(i);
						if (assitButton.getText().equals(source.getText())) {
							assitButton.setIcon(EASResource.getIcon("imgTbtn_affirm"));
							menuItem.setIcon(assitButton.getIcon());
						} else {
							assitButton.setIcon(null);
							menuItem.setIcon(null);
						}
					}
					updateMoneyUnit(unitEnum);
				}
			});
			menuItem.setText(unitEnum.getAlias());
			menuItem.setToolTipText(unitEnum.getAlias());
			btnUnit.addAssistMenuItem(menuItem);
			KDMenuItem menuItem2 = new KDMenuItem(unitEnum.getAlias());
			menuItem2.setAction(menuItem.getAction());
			menuItem2.setText(unitEnum.getAlias());
			menuItem2.setToolTipText(unitEnum.getAlias());
			menu.add(menuItem2);
		}
		menuBiz.add(menu);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		//		super.tblMain_tableClicked(e);
	}

	protected void initMoneyCell(ICell cell) {
		if (cell != null) {
			cell.setEditor(getMoneyEditor());
			cell.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			cell.setRenderer(moneyRender);
			cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			cell.getStyleAttributes().setLocked(true);
		}
	}

	protected void initMoneyColumn(String colName) {
		tblMain.getColumn(colName).setEditor(getMoneyEditor());
		tblMain.getColumn(colName).getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
		tblMain.getColumn(colName).setRenderer(moneyRender);
		tblMain.getColumn(colName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(colName).getStyleAttributes().setLocked(true);
	}

	protected KDTDefaultCellEditor getMoneyEditor() {
		if (moneyEditor == null) {
			KDFormattedTextField formatField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
			formatField.setPrecision(2);
			formatField.setHorizontalAlignment(SwingConstants.RIGHT);
			moneyEditor = new KDTDefaultCellEditor(formatField);
		}
		return moneyEditor;

	}

	protected void updateMoneyUnit(AmountUnitEnum unitEnum) {
		moneyRender.setUnit(unitEnum);

		tblMain.updateUI();
		tblMain.setEditable(false);
	}

	protected void tblMain_doRequestRowSet(final RequestRowSetEvent e) {
	}

	
	/**
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#treeSelectChange()
	 */
	protected void treeSelectChange() throws Exception {
		//1、获取选中节点及其子节点的所有项目id
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		this.actionMonthPayDistributePic.setEnabled(false);
		this.actionPayProgressPic.setEnabled(false);
		if (projectNode == null) {
			return;
		}
		if (projectNode.isLeaf() && projectNode.getUserObject() instanceof CurProjectInfo) {
			actionMonthPayDistributePic.setEnabled(true);
			actionPayProgressPic.setEnabled(true);
		}
		if (projectNode.getUserObject() instanceof OrgStructureInfo) {
			isCompany = true;
		} else {
			isCompany = false;
		}
		fillTable(projectNode);
	}

	protected void fillTable(DefaultKingdeeTreeNode projectNode) throws BOSException {
		tblMain.removeRows();
		removeAllDynationColumn();
		Set set = new HashSet();
		getSelectProjectIds(projectNode, set);
		if (set.size() < 1) {
			set.add("kingdee");
		}
		//2、查询出所有需要查询的项目的数据，并按照项目的编码排序
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", set, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("curProject.parent.name"));
		sic.add(new SelectorItemInfo("entry.programming.*"));
		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("entry.detailEntry.*"));
		

		view.setSelector(sic);

		ProjectDynaticFundPayPlanCollection col = ProjectDynaticFundPayPlanFactory.getRemoteInstance()
				.getProjectDynaticFundPayPlanCollection(view);
		
		if (isCompany) {
			tblMain.getColumn("programmingName").getStyleAttributes().setHided(true);
		} else {
			tblMain.getColumn("programmingName").getStyleAttributes().setHided(false);
		}
		
		//3、循环显示出数据。
		Map sumMap = new HashMap();//合计行<列id,值>
		if (col != null && col.size() > 0) {
			
			addTableColumn(col);
			
			System.out.println("共有" + col.size() + "条记录");
			for (int i = 0; i < col.size(); i++) {
				System.out.println("开始显示第" + i + "条记录");
				ProjectDynaticFundPayPlanInfo eInfo = col.get(i);
				ProjectDynaticFundPayPlanEntryCollection entrys = eInfo.getEntry();
				//项目合计
				Map projectSumMap = new HashMap();
				Set ps = new HashSet();
				for (int j = 0; j < entrys.size(); j++) {
					System.out.println("开始显示第" + i + "条记录的第" + j + "条分录：");
					IRow row = tblMain.addRow();
					if (isCompany) {
						row.getStyleAttributes().setHided(true);
					}
					//id
					row.getCell("id").setValue(eInfo.getId().toString());
					//项目id
					row.getCell("curProject.id").setValue(eInfo.getCurProject().getId().toString());
					//项目编码
					row.getCell("curProject.number").setValue(eInfo.getCurProject().getNumber());
					//项目名称
					row.getCell("curProject.name").setValue(eInfo.getCurProject().getName());
					//合约规划id
					row.getCell("programming.id").setValue(entrys.get(j).getProgramming().getId().toString());
					//合约规划名称
					int level = entrys.get(j).getProgramming().getLevel();
					
					String proName = entrys.get(j).getProgramming().getName();
					for(int m=1;m<level;m++){
						proName="      "+proName;
					}
					row.getCell("programmingName").setValue(proName);
					//规划金额（元）
					row.getCell("programmingAmount").setValue(entrys.get(j).getProgrammingAmount());
					initMoneyCell(row.getCell("programmingAmount"));
					if (!ps.contains(entrys.get(j).getProgramming().getId()) && entrys.get(j).getSignState()!=null) {
						if (projectSumMap.get("programmingAmount") == null) {
							projectSumMap.put("programmingAmount", entrys.get(j).getProgrammingAmount());
						} else {
							BigDecimal pa = (BigDecimal) projectSumMap.get("programmingAmount");
							if (pa == null) {
								pa = BigDecimal.ZERO;
							}
							if (entrys.get(j).getProgrammingAmount() != null) {
								pa = pa.add(entrys.get(j).getProgrammingAmount());
							}
							projectSumMap.put("programmingAmount", pa);
						}
						if (sumMap.get("programmingAmount") == null) {
							sumMap.put("programmingAmount", entrys.get(j).getProgrammingAmount());
						} else {
							BigDecimal pa = (BigDecimal) sumMap.get("programmingAmount");
							if (pa == null) {
								pa = BigDecimal.ZERO;
							}
							if (entrys.get(j).getProgrammingAmount() != null) {
								pa = pa.add(entrys.get(j).getProgrammingAmount());
							}
							sumMap.put("programmingAmount", pa);
						}
						ps.add(entrys.get(j).getProgramming().getId());
					}
					//签约情况
					row.getCell("signState").setValue(entrys.get(j).getSignState());
					//总金额
					row.getCell("totalAmount").setValue(entrys.get(j).getTotalAmount());
					
					if(FDCHelper.compareTo(FDCHelper.ZERO, entrys.get(j).getTotalAmount())>=0 && entrys.get(j).getSignState()!=null){
						row.getStyleAttributes().setHided(true);
					}
					initMoneyCell(row.getCell("totalAmount"));
					BigDecimal pa = (BigDecimal) projectSumMap.get("totalAmount");
					if (pa == null) {
						pa = BigDecimal.ZERO;
					}
					if (entrys.get(j).getTotalAmount() != null) {
						pa = pa.add(entrys.get(j).getTotalAmount());
					}
					projectSumMap.put("totalAmount", pa);

					BigDecimal pa2 = (BigDecimal) sumMap.get("totalAmount");
					if (pa2 == null) {
						pa2 = BigDecimal.ZERO;
					}
					if (entrys.get(j).getTotalAmount() != null) {
						pa2 = pa2.add(entrys.get(j).getTotalAmount());
					}
					sumMap.put("totalAmount", pa2);

					//添加支付信息
					if (entrys.get(j).getDetailEntry() != null && entrys.get(j).getDetailEntry().size() > 0) {
						ProjectDynaticFundPayPlanDetailEntryCollection detailEntry = entrys.get(j).getDetailEntry();
						//循环添加支付明细
						label: for (int k = 0; k < detailEntry.size(); k++) {
							ProjectDynaticFundPayPlanDetailEntryInfo edInfo = detailEntry.get(k);
							int payMonth = edInfo.getPayMonth();
							String payMonthCol = String.valueOf(payMonth);
							int year = payMonth / 100;
							int month = payMonth % 100;
							//循环列，判断支付明细对应的月份是否存在。
							for (int l = 0; l < tblMain.getColumnCount(); l++) {
								String key = tblMain.getColumnKey(l);
								if (key.length() != 6) {
									continue;
								}
								String sYear = key.substring(0, 4);
								String sMonth = key.substring(4);
								int nYear = Integer.parseInt(sYear);
								int nMonth = Integer.parseInt(sMonth);
								//存在的日期
								if (nYear == year && nMonth == month) {
									BigDecimal value = (BigDecimal) projectSumMap.get(key);
									if (value == null) {
										value = BigDecimal.ZERO;
									}
									if (edInfo.getPayAmount() != null) {
										value = value.add(edInfo.getPayAmount());
									}
									projectSumMap.put(key, value);
									BigDecimal value2 = (BigDecimal) sumMap.get(key);
									if (value2 == null) {
										value2 = BigDecimal.ZERO;
									}
									if (edInfo.getPayAmount() != null) {
										value2 = value2.add(edInfo.getPayAmount());
									}
									sumMap.put(key, value2);
									row.getCell(l).setValue(edInfo.getPayAmount());
									initMoneyCell(row.getCell(l));
									continue label;
								}
								if (nYear > year || (nYear == year && nMonth > month)) {
									//新增一列
									IColumn column = tblMain.addColumn(l);
									column.setKey(String.valueOf(payMonth));
									tblMain.getHeadRow(0).getCell(l).setValue(year + "年" + month + "月");
									row.getCell(l).setValue(edInfo.getPayAmount());
									initMoneyCell(row.getCell(l));
									continue label;
								}
							}
							//
							if (tblMain.getColumn(payMonthCol) == null) {
								IColumn column = tblMain.addColumn();
								column.setKey(String.valueOf(payMonth));
								tblMain.getHeadRow(0).getCell(column.getColumnIndex()).setValue(year + "年" + month + "月");
							}
							row.getCell(payMonthCol).setValue(edInfo.getPayAmount());
							initMoneyCell(row.getCell(payMonthCol));
						}
					}
				}
				if (projectSumMap.size() > 0) {
					//添加合计
					IRow sumRow = tblMain.addRow();

					sumRow.getCell("curProject.id").setValue(eInfo.getCurProject().getId().toString());
					//项目编码
					sumRow.getCell("curProject.number").setValue(eInfo.getCurProject().getNumber());
					//项目名称
					sumRow.getCell("curProject.name").setValue(eInfo.getCurProject().getName());
					sumRow.getCell("programming.id").setValue("heji");
					sumRow.getCell("programmingName").setValue("合计");
					//添加累计支付
					IRow addUpRow = tblMain.addRow();
					if (isCompany) {
						addUpRow.getStyleAttributes().setHided(true);
					}

					addUpRow.getCell("curProject.id").setValue(eInfo.getCurProject().getId().toString());
					//项目编码
					addUpRow.getCell("curProject.number").setValue(eInfo.getCurProject().getNumber());
					//项目名称
					addUpRow.getCell("curProject.name").setValue(eInfo.getCurProject().getName());
					addUpRow.getCell("programming.id").setValue("leijizhifu");
					addUpRow.getCell("programmingName").setValue("累计支付");
					setRowSum(sumRow, addUpRow, projectSumMap);
					sumRow.getStyleAttributes().setBackground(Color.YELLOW);
					addUpRow.getStyleAttributes().setBackground(Color.YELLOW);
				}
				meregeTable(tblMain, 0, tblMain.getRowCount() - 1, new String[] { "curProject.id" }, new String[] { "curProject.name" });
				meregeTable(tblMain, 0, tblMain.getRowCount() - 1, new String[] { "programming.id" }, new String[] { "programmingName",
						"programmingAmount" });
			}
			System.out.println("显示总计行");
			if (sumMap.size() > 0) {
				//添加总计
				IRow sumRow = tblMain.addRow();
				sumRow.getCell("programmingName").setValue("总计");
				sumRow.getStyleAttributes().setLocked(true);
				//添加累计支付。
				IRow addUpRow = tblMain.addRow();
				if (isCompany) {
					addUpRow.getStyleAttributes().setHided(true);
				}
				addUpRow.getCell("programmingName").setValue("累计支付");
				setRowSum(sumRow, addUpRow, sumMap);

				sumRow.getStyleAttributes().setBackground(Color.ORANGE);
				addUpRow.getStyleAttributes().setBackground(Color.ORANGE);
				addUpRow.getStyleAttributes().setLocked(true);
			}
			System.out.println("显示总计行显示结束。");
		}
	}

	private void addTableColumn(ProjectDynaticFundPayPlanCollection col) {
		Set column =new HashSet();
		for(int i=0;i<col.size();i++){
			ProjectDynaticFundPayPlanInfo projectDynaticFundPayPlanInfo = col.get(i);
			ProjectDynaticFundPayPlanEntryCollection entry = projectDynaticFundPayPlanInfo.getEntry();
			if(entry.size()>0){
				for(int j=0;j<entry.size();j++){
					ProjectDynaticFundPayPlanDetailEntryCollection detailEntry = entry.get(j).getDetailEntry();
					if(detailEntry.size()>0){
						for(int k=0;k<detailEntry.size();k++){
							column.add(detailEntry.get(k).getPayMonth());
						}
					}
				}
			}
		}
		List columnList = new ArrayList(column);
		// 排序
		Collections.sort(columnList, new Comparator() {

			public int compare(Object o1, Object o2) {
				Integer d1 = (Integer) o1;
				Integer d2 = (Integer) o2;
				return d1.intValue() - d2.intValue();
			}
		});
		for(int k=0;k<columnList.size();k++){
			int payMonth = ((Integer) columnList.get(k)).intValue();
			int year = payMonth / 100;
			int month = payMonth % 100;
			IColumn addColumn = tblMain.addColumn();
			addColumn.setKey(String.valueOf(payMonth));
			tblMain.getHeadRow(0).getCell(addColumn.getColumnIndex()).setValue(year + "年" + month + "月");
		}
		
	}

	public void meregeTable(KDTable table, int beginRow, int endRow, String[] references, String[] col) {
		if (beginRow < 0) {
			beginRow = 0;
		}
		if (endRow < 0) {
			endRow = table.getRowCount() - 1;
		}
		if (endRow < 1 || endRow - beginRow < 1)
			return;
		KDTMergeManager kmm = table.getMergeManager();
		kmm.setDataMode(KDTMergeManager.DATA_UNIFY);
		//所有需要融合字段的列数
		int[] indexs = new int[col.length];
		for (int j = 0, m = col.length; j < m; j++) {
			indexs[j] = table.getColumnIndex(col[j]);
		}
		int i = beginRow, temp = -1;
		Object[] begin = new Object[references.length];
		Object[] cur = new Object[references.length];
		for (; i <= endRow; i++) {
			if (i == beginRow) {
				for (int j = 0; j < references.length; j++) {
					begin[j] = table.getCell(i, references[j]).getValue();
					cur[j] = table.getCell(i, references[j]).getValue();
				}
				//获取上一行的数据，判断是否参考字段是否和首行字段相同
				if (beginRow > 0) {
					Object[] pre = new Object[references.length];
					boolean bool = true;
					for (int j = 0; j < references.length; j++) {
						pre[j] = table.getCell(beginRow - 1, references[j]).getValue();
						if (begin[j] != null && begin[j].equals(pre[j])) {
							continue;
						} else {
							bool = false;
							break;
						}
					}
					//判断是否都相同，如果存在不同，则直接跳过
					if (bool) {
						//如果相同，则获取其首行
						KDTMergeBlock mb = table.getCell(beginRow - 1, col[0]).getMergeBlock();
						temp = mb.getTop();
					}
				}
				if (temp == -1) {
					temp = i;
				}
				continue;
			}
			boolean bool = true;
			for (int j = 0; j < references.length; j++) {
				cur[j] = table.getCell(i, references[j]).getValue();
				if ((cur[j] == null && begin[j] == null) || (cur[j] != null && cur[j].equals(begin[j]))) {
					continue;
				} else {
					bool = false;
				}
			}
			if (bool) {
				continue;
			}
			for (int j = 0, m = col.length; j < m; j++) {
				kmm.mergeBlock(temp, indexs[j], i - 1, indexs[j], KDTMergeManager.SPECIFY_MERGE);
			}
			for (int j = 0; j < references.length; j++) {
				begin[j] = table.getCell(i, references[j]).getValue();
			}
			temp = i;
		}
		for (int j = 0, m = col.length; j < m; j++) {
			kmm.mergeBlock(temp, indexs[j], i - 1, indexs[j], KDTMergeManager.SPECIFY_MERGE);
		}
    }
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
	}

	public void setRowSum(IRow row, IRow addUpRow, Map projectSumMap) {
		//设置合计行的值
		row.getStyleAttributes().setLocked(true);
		addUpRow.getStyleAttributes().setLocked(true);
		if (projectSumMap != null && projectSumMap.keySet() != null && projectSumMap.size() > 0) {
			Set set = projectSumMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Object key = it.next();
				row.getCell(key.toString()).setValue(projectSumMap.get(key));
				initMoneyCell(row.getCell(key.toString()));
			}
		}
		//设置累计合计行的值。
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			if (tblMain.getColumn(i).getKey().length() == 6) {
				if (tblMain.getColumn(i - 1) != null && tblMain.getColumn(i - 1).getKey().length() == 6) {

					Object obj = addUpRow.getCell(i - 1).getValue();
					Object obj2 = row.getCell(i).getValue();
					addUpRow.getCell(i).setValue(FDCHelper.add(obj, obj2));
				} else {
					addUpRow.getCell(i).setValue(row.getCell(i).getValue());
				}
				initMoneyCell(addUpRow.getCell(i));
			}
		}
	}

	public void removeAllDynationColumn() {
		for (int i = tblMain.getColumnCount() - 1; i >= 0; i--) {
			if (tblMain.getColumn(i).getKey().length() == 6) {
				tblMain.removeColumn(i);
			}
		}
	}

	/**
	 * 同步
	 */
	public void actionSyn_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
		if (projectNode == null || projectNode.getUserObject() == null) {
			throw new EASBizException(new NumericExceptionSubItem("021", "请先选择一个节点。"));
		}
		Set set = new HashSet();
		getSelectProjectIds(projectNode, set);
		ProjectDynaticFundPayPlanFactory.getRemoteInstance().synData(set);
		MsgBox.showInfo("同步成功！");
		fillTable(projectNode);
	}

	/**
	 * 单位
	 */
	public void actionUnit_actionPerformed(ActionEvent e) throws Exception {
	}

	/**
	 * 月度支付分布图
	 */
	public void actionMonthPayDistributePic_actionPerformed(ActionEvent e) throws Exception {
		//		获取总计金额.
		if (tblMain.getRowCount() < 2) {
			throw new EASBizException(new NumericExceptionSubItem("021", "没有数据。"));
		}
		IRow row = this.tblMain.getRow(tblMain.getRowCount() - 2);
		List seriesKeys = new ArrayList();
		seriesKeys.add(((DefaultKingdeeTreeNode) getProjSelectedTreeNode().getParent()).getUserObject() + " " + getSelectProject());
		//打开月度支付分布图
		List listValues = new ArrayList();
		List listKeys = new ArrayList();
		List listColNames = new ArrayList();
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			if (tblMain.getColumnKey(i).length() == 6) {
				if (row.getCell(i).getValue() != null) {
					listKeys.add(tblMain.getColumnKey(i));
					listColNames.add(tblMain.getHeadRow(0).getCell(i).getValue());
					listValues.add(new BigDecimal(row.getCell(i).getValue().toString()));
				}
			}
		}
		if (listKeys.size() < 1) {
			throw new EASBizException(new NumericExceptionSubItem("021", "没有数据"));
		}
		String key = (String) listKeys.get(0);
		int iKey = Integer.parseInt(key);
		int year = Integer.parseInt(key) / 100;
		int month = Integer.parseInt(key) % 100;
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, year);
		ca.set(Calendar.MONTH, month - 1);
		//补充缺少的月份。
		for (int i = 1; i < listKeys.size(); i++) {
			String key2 = (String) listKeys.get(i);
			System.out.println("time is:" + key2);
			int year2 = Integer.parseInt(key2) / 100;
			int month2 = Integer.parseInt(key2) % 100;
			ca.add(Calendar.MONTH, 1);
			if (ca.get(Calendar.YEAR) == year2 && ca.get(Calendar.MONTH) + 1 == month2) {
			} else {
				//				ca.add(Calendar.MONTH, 1);
				while (true) {
					int month3 = ca.get(Calendar.MONTH) + 1;
					listKeys.add(i, String.valueOf(ca.get(Calendar.YEAR)) + (month3 < 10 ? "0" + month3 : String.valueOf(month3)));
					listColNames.add(i, String.valueOf(ca.get(Calendar.YEAR)) + "年" + String.valueOf(ca.get(Calendar.MONTH) + 1) + "月");
					listValues.add(i, BigDecimal.ZERO);
					ca.add(Calendar.MONTH, 1);
					i++;
					if (ca.get(Calendar.YEAR) == year2 && ca.get(Calendar.MONTH) + 1 == month2) {
						break;
					}
				}
			}
		}
		double[][] values = new double[1][listValues.size()];//{{1,1.1,2.5,3}};
		for (int i = 0; i < values[0].length; i++) {
			values[0][i] = Double.parseDouble(listValues.get(i).toString());
		}

		//		List keys=new ArrayList();
		//		keys.add(((DefaultKingdeeTreeNode)getProjSelectedTreeNode().getParent()).getUserObject()+" "+getSelectProject());
		//		List groupKeys=new ArrayList();
		//		groupKeys.add("111");
		//		groupKeys.add("122");
		//		Map map=new UIContext(this);
		//		//设置选中项目
		//		map.put("project", this.getSelectProject());
		//		map.put("title", ((DefaultKingdeeTreeNode)getProjSelectedTreeNode().getParent()).getUserObject()+" "+getSelectProject()+"月度支付分布图");
		//		map.put("keys", keys.toArray(new String[]{}));
		//		map.put("groupKeys", groupKeys.toArray(new String[]{}));
		//		map.put("values", values);
		//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(ProjectDynaticFundPayPlanMonthPayMapUI.class.getName(), map, null,OprtState.VIEW);
		//		uiWindow.show();
		//		List seriesKeys=new ArrayList();
		//		seriesKeys.add(((DefaultKingdeeTreeNode)getProjSelectedTreeNode().getParent()).getUserObject()+" "+getSelectProject());
		//		double[][] values=new double[][]{{1,1.1,2.5,3}};
		//		List groupKeys=new ArrayList();
		//		groupKeys.add("2012年1月");
		//		groupKeys.add("2012年2月");
		//		groupKeys.add("2012年3月");
		//		groupKeys.add("2012年4月");
		String title = ((DefaultKingdeeTreeNode) getProjSelectedTreeNode().getParent()).getUserObject() + " " + getSelectProject()
				+ "月度支付分布图";
		ChartUtil.openMapUI(new UIContext(this), title, "月度支付分布图", (String[]) seriesKeys.toArray(new String[] {}), (String[]) listColNames
				.toArray(new String[] {}), values, 3);
	}

	/**
	 * 支付进度图
	 */
	public void actionPayProgressPic_actionPerformed(ActionEvent e) throws Exception {
		//获取总计金额.
		if (tblMain.getRowCount() < 2) {
			throw new EASBizException(new NumericExceptionSubItem("021", "没有数据。"));
		}
		List seriesKeys = new ArrayList();
		seriesKeys.add(((DefaultKingdeeTreeNode) getProjSelectedTreeNode().getParent()).getUserObject() + " " + getSelectProject());
		IRow row = this.tblMain.getRow(tblMain.getRowCount() - 1);
		//打开月度支付分布图
		List listValues = new ArrayList();
		List listKeys = new ArrayList();
		List listColNames = new ArrayList();
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			if (tblMain.getColumnKey(i).length() == 6) {
				if (row.getCell(i).getValue() != null) {
					listKeys.add(tblMain.getColumnKey(i));
					listColNames.add(tblMain.getHeadRow(0).getCell(i).getValue());
					listValues.add(new BigDecimal(row.getCell(i).getValue().toString()));
				}
			}
		}
		if (listKeys.size() < 1) {
			throw new EASBizException(new NumericExceptionSubItem("021", "没有数据"));
		}
		String key = (String) listKeys.get(0);
		int iKey = Integer.parseInt(key);
		int year = Integer.parseInt(key) / 100;
		int month = Integer.parseInt(key) % 100;
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, year);
		ca.set(Calendar.MONTH, month - 1);
		//补充缺少的月份。
		for (int i = 1; i < listKeys.size(); i++) {
			String key2 = (String) listKeys.get(i);
			int year2 = Integer.parseInt(key2) / 100;
			int month2 = Integer.parseInt(key2) % 100;
			ca.add(Calendar.MONTH, 1);
			if (ca.get(Calendar.YEAR) == year2 && ca.get(Calendar.MONTH) + 1 == month2) {
			} else {
				//				ca.add(Calendar.MONTH, 1);
				//				int addCount=0;
				while (true) {
					int month3 = ca.get(Calendar.MONTH) + 1;
					listKeys.add(i, String.valueOf(ca.get(Calendar.YEAR)) + (month3 < 10 ? "0" + month3 : String.valueOf(month3)));
					listColNames.add(i, String.valueOf(ca.get(Calendar.YEAR)) + "年" + String.valueOf(ca.get(Calendar.MONTH) + 1) + "月");
					listValues.add(i, listValues.get(i - 1));
					ca.add(Calendar.MONTH, 1);
					i++;
					if (ca.get(Calendar.YEAR) == year2 && ca.get(Calendar.MONTH) + 1 == month2) {
						break;
					}
				}
			}
		}
		double[][] values = new double[1][listValues.size()];//{{1,1.1,2.5,3}};
		for (int i = 0; i < values[0].length; i++) {
			values[0][i] = Double.parseDouble(listValues.get(i).toString());
		}
		//		List groupKeys=new ArrayList();
		//		groupKeys.add("2012年1月");
		//		groupKeys.add("2012年2月");
		//		groupKeys.add("2012年3月");
		//		groupKeys.add("2012年4月");
		String title = ((DefaultKingdeeTreeNode) getProjSelectedTreeNode().getParent()).getUserObject() + " " + getSelectProject()
				+ "月度支付分布图";
		ChartUtil.openMapUI(new UIContext(this), title, "支付进度图", (String[]) seriesKeys.toArray(new String[] {}), (String[]) listColNames
				.toArray(new String[] {}), values, 14);
		//		Map map=new UIContext(this);
		//		//设置选中项目
		//		map.put("project", this.getSelectProject());
		//		map.put("title", ((DefaultKingdeeTreeNode)getProjSelectedTreeNode().getParent()).getUserObject()+" "+getSelectProject()+"月度支付分布图");
		//		map.put("keys", keys.toArray(new String[]{}));
		//		map.put("groupKeys", groupKeys.toArray(new String[]{}));
		//		map.put("values", values);
		//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(ProjectDynaticFundPayPlanPayScheduleMapUI.class.getName(), map, null,OprtState.VIEW);
		//		uiWindow.show();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	}
	
	private static boolean isCompany = false;

	public void getSelectProjectIds(DefaultKingdeeTreeNode projectNode, Set set) {
		if (projectNode == null) {
			return;
		}
		if (projectNode.getUserObject() == null) {
			return;
		}
		if (projectNode.getUserObject() instanceof CurProjectInfo) {
			set.add(((CurProjectInfo) projectNode.getUserObject()).getId().toString());
		}
		
		for (int i = 0; i < projectNode.getChildCount(); i++) {
			getSelectProjectIds((DefaultKingdeeTreeNode) projectNode.getChildAt(i), set);
		}
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectDynaticFundPayPlanFactory.getRemoteInstance();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#audit(java.util.List)
	 */
	protected void audit(List ids) throws Exception {

	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#getRemoteInterface()
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		return null;
	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.ProjectTreeListBaseUI#unAudit(java.util.List)
	 */
	protected void unAudit(List ids) throws Exception {

	}
}