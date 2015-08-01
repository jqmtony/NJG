/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.ProductDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProductDynamicCostUI extends AbstractProductDynamicCostUI {

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	private DyCostSplitDataGetter dyGetter;

	//新增的
	private DynamicCostCollection addDynamicCostColl = new DynamicCostCollection();
	//所有的
	private DynamicCostCollection allDynamic = new DynamicCostCollection();
	
	private HashMap acctMap = new HashMap();

	private DyProductTypeGetter dyProductTypeGetter;

	private AimCostSplitDataGetter aimGetter;

	private HappenDataGetter happenGetter;

	private Map aimApportionMap;
	
	private Map dyApportionMap;
	
	private RetValue retValueNotLeaf;

	private Map rowMap = new HashMap();
	
	/** 是否级次 */
	private boolean isShowSplitAcct = true;
	
	// 是否包含禁用科目
	protected boolean isAll = false;
	
	// 数据对象变化时，刷新界面状态
	protected void setActionState() {
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	private void initAcct(FilterInfo acctFilter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		menuItemSubmit.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		this.isSplitBtn.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.isSplitBtn.setEnabled(true);
	}

	private void initControl() {
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
		this.btnSplit.setVisible(true);
		this.btnSplit.setEnabled(true);
	}

	private void setRowColor(IRow row, Color color) {
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		String[] productTypeIds = this.dyProductTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			amountColumns.add(productTypeId + "DynamicSell");
			amountColumns.add(productTypeId + "Dynamic");
			amountColumns.add(productTypeId + "HasHappen");
			amountColumns.add(productTypeId + "NoHappen");
		}
		for (int k = 0; k < amountColumns.size(); k++) {
			String colName = (String) amountColumns.get(k);
			row.getCell(colName).getStyleAttributes().setFontColor(color);
		}
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		int colCount = tblMain.getColumnCount();
		int loopCount = (colCount - 1) / 6;
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		//		 amountColumns.add("amount");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node.isLeaf()) {
			String[] productTypeIds = this.dyProductTypeGetter.getProductTypeIds();
			for (int i = 0; i < productTypeIds.length; i++) {
				String productTypeId = productTypeIds[i];
				// amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				// amountColumns.add(productTypeId + "DynamicSell");
				// amountColumns.add(productTypeId + "Dynamic");
			}
		} else {
			ProductTypeCollection productTypeIds = (ProductTypeCollection) this.retValueNotLeaf.get("ProductTypeCollection");
			for (int i = 0; i < productTypeIds.size(); i++) {
				String productTypeId = productTypeIds.get(i).getId().toString();
				// amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				// amountColumns.add(productTypeId + "DynamicSell");
				// amountColumns.add(productTypeId + "Dynamic");
			}
		}
			
		
		
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
		    BigDecimal oldamount = null;
			if (row.getCell("acctNumber").getValue().toString().equals("5001.402.303")) {
				System.out.println();
			}
			if (row.getUserObject() == null) {
				oldamount = (BigDecimal) row.getCell("amount").getValue();
				
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					} else if (row.getCell("amount").getUserObject() != null) {
						rowList.add(rowAfter);
					} else if (rowAfter.getCell("amount").getUserObject() != null) {
						rowList.add(rowAfter);
					} 
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					// BigDecimal values =
					// FDCHelper.toBigDecimal(row.getCell(colName
					// ).getValue());// 非明细值
																								// ，
																								// 翻倍
					BigDecimal amount = FDCHelper.ZERO;// 设置默认值0，值由明细行汇总
					if (colName.equals("amount")) {
						row.getCell(colName).setValue(amount);
					}
					boolean isRed = false;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
//					if (hasData) {
//						amount = FDCHelper.add(row.getCell(colName).getValue(), amount);
//						row.getCell(colName).setValue(amount);
//					} else {
//						amount = FDCHelper.add(row.getCell(colName).getValue(), amount);
						if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
							row.getCell(colName).setValue(amount);
						}
//					}
					// values = FDCHelper.add(values, amount);
					// if
					// (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)
					// != 0) {
					// row.getCell(colName).setValue(values);
					// }
				}
			}
			 for (int topCount = 0; topCount < loopCount; topCount++) {
				BigDecimal dy = (BigDecimal) row.getCell(4 + (topCount * 6) + 6).getValue();
				BigDecimal hasHappend = (BigDecimal) row.getCell(4 + (topCount * 6) + 3).getValue();
				BigDecimal notHappend = FDCHelper.subtract(dy, hasHappend);
				row.getCell(8 + (topCount * 6)).setValue(notHappend);
			}

			//			 row.getCell("amount").setValue(FDCHelper.add(oldamount, row.getCell("amount").getValue()));
		}
		
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIExceptionAndAbort(e);
			}
		}
		
		
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setAimUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(node.isLeaf())
		{
			String[] productTypeIds = this.dyProductTypeGetter.getProductTypeIds();
			for (int i = 0; i < productTypeIds.length; i++) {
				String productTypeId = productTypeIds[i];
				amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				amountColumns.add(productTypeId + "Dynamic");
				amountColumns.add(productTypeId + "DynamicSell");
			}
		}
		else{
			ProductTypeCollection productTypeIds = (ProductTypeCollection)this.retValueNotLeaf.get("ProductTypeCollection");
			for (int i = 0; i < productTypeIds.size(); i++) {
				String productTypeId = productTypeIds.get(i).getId().toString();
				amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				amountColumns.add(productTypeId + "Dynamic");
				amountColumns.add(productTypeId + "DynamicSell");
			}
		}
			
		
		
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				if (row.getCell("amount").getUserObject() != null)
					rowList.add(row);
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					} else if (rowAfter.getCell("amount").getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					// BigDecimal amount =
					// FDCHelper.toBigDecimal(row.getCell(colName
					// ).getValue());//非明细值，翻倍
					BigDecimal amount = FDCHelper.ZERO;// 设置默认值0，值由明细行汇总
					boolean isRed = false;
					boolean hasData = false;
					if (colName.endsWith("AimSell")) {
					} else if (colName.endsWith("DynamicSell")) {
					} else {
						for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) rowList.get(rowIndex);
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null && rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
								isRed = true;
							}
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(((Integer) value).toString()));
								}
								hasData = true;
							}
						}
					}

					if (row.getCell("amount").getUserObject() != null) {
						if (colName.equals("amount")) {
							//amount=FDCHelper.add(row.getCell("amount").getValue
							// (), amount);
						}
						if (colName.endsWith("Dynamic")) {
							// amount =
							// FDCHelper.add(row.getCell(colName.substring
							// (0,colName.length()-7)+"Aim").getValue(),
							// amount);
						}
					}

					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.BLACK);
					}
					if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) != 0) {

						row.getCell(colName).setValue(amount);
					}
				}
			}
			for (int k = 0; k < amountColumns.size(); k++) {
				String colName = (String) amountColumns.get(k);
				BigDecimal amount = FDCHelper.ZERO;
				if (colName.endsWith("Dynamic")) {
					row.getCell(colName.substring(0, colName.length() - 7) + "NoHappen").setValue(
							FDCHelper.subtract(row.getCell(colName.substring(0, colName.length() - 7) + "Dynamic").getValue(), row.getCell(colName.substring(0, colName.length() - 7) + "HasHappen")
									.getValue()));
				}
				if (colName.endsWith("AimSell")) {
					String productId = colName.substring(0, colName.length() - 7);
					String key = getSelectObjId() + " ";
					key += productId + " ";
					key += ApportionTypeInfo.sellAreaType;
					BigDecimal aimCost = (BigDecimal) row.getCell(productId + "Aim").getValue();
					BigDecimal aimSellArea = FDCHelper.toBigDecimal(this.aimApportionMap.get(key));
					if (!FDCHelper.isZero(aimCost) && aimSellArea.compareTo(FDCHelper.ZERO) != 0) {
						amount = aimCost.divide(aimSellArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell(colName).setValue(amount);
					}
				} else if (colName.endsWith("DynamicSell")) {
					String productId = colName.substring(0, colName.length() - 11);
					String key = getSelectObjId() + " ";
					key += productId + " ";
					key += ApportionTypeInfo.sellAreaType;

					BigDecimal dynCost = (BigDecimal) row.getCell(productId + "Dynamic").getValue();
					BigDecimal dySellArea = FDCHelper.toBigDecimal(this.dyApportionMap.get(key));
					if (!FDCHelper.isZero(dynCost) && dySellArea.compareTo(FDCHelper.ZERO) != 0) {
						amount = dynCost.divide(dySellArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell(colName).setValue(amount);
					}

				}

			}
		}
		if (amountColumns.size() > 0) {
			String[] columns = new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try {
				AimCostClientHelper.setTotalCostRow(table, columns);
			} catch (Exception e) {
				handUIException(e);
			}
		}
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setNotLeafUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		// amountColumns.add("amount");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.isLeaf()) {
			String[] productTypeIds = this.dyProductTypeGetter.getProductTypeIds();
			for (int i = 0; i < productTypeIds.length; i++) {
				String productTypeId = productTypeIds[i];
				// amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				// amountColumns.add(productTypeId + "DynamicSell");
				// amountColumns.add(productTypeId + "Dynamic");
			}
		} else {
			ProductTypeCollection productTypeIds = (ProductTypeCollection) this.retValueNotLeaf.get("ProductTypeCollection");
			for (int i = 0; i < productTypeIds.size(); i++) {
				String productTypeId = productTypeIds.get(i).getId().toString();
				// amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				// amountColumns.add(productTypeId + "DynamicSell");
				// amountColumns.add(productTypeId + "Dynamic");
			}
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				if (row.getCell("amount").getUserObject() != null) {
					rowList.add(row);
				}
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					// BigDecimal amount =
					// FDCHelper.toBigDecimal(row.getCell(colName
					// ).getValue());//非明细值，翻倍
					BigDecimal amount = FDCHelper.ZERO;//设置默认值0，值由明细行汇总
					boolean isRed = false;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					if(FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO)!=0){
						row.getCell(colName).setValue(amount);
					}
				}
			}

		}
		if (amountColumns.size() > 0) {
			String[] columns = new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try {
				AimCostClientHelper.setTotalCostRow(table, columns);
			} catch (Exception e) {
				handUIException(e);
			}
		}
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setNotLeafAimUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		RetValue productSellAreaValue = (RetValue) retValueNotLeaf.get("ProductSellAreaValue");
		if (node.isLeaf()) {
			String[] productTypeIds = this.dyProductTypeGetter.getProductTypeIds();
			for (int i = 0; i < productTypeIds.length; i++) {
				String productTypeId = productTypeIds[i];
				amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				amountColumns.add(productTypeId + "Dynamic");
				amountColumns.add(productTypeId + "DynamicSell");
			}
		} else {
			ProductTypeCollection productTypeIds = (ProductTypeCollection) this.retValueNotLeaf.get("ProductTypeCollection");
			for (int i = 0; i < productTypeIds.size(); i++) {
				String productTypeId = productTypeIds.get(i).getId().toString();
				amountColumns.add(productTypeId + "Aim");
				amountColumns.add(productTypeId + "AimSell");
				// amountColumns.add(productTypeId + "HasHappen");
				// amountColumns.add(productTypeId + "NoHappen");
				amountColumns.add(productTypeId + "Dynamic");
				amountColumns.add(productTypeId + "DynamicSell");
			}
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				if (row.getCell("amount").getUserObject() != null)
					rowList.add(row);
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					} else if (rowAfter.getCell("amount").getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					// BigDecimal amount =
					// FDCHelper.toBigDecimal(row.getCell(colName
					// ).getValue());//非明细值，翻倍
					BigDecimal amount = FDCHelper.ZERO;// 设置默认值0，值由明细行汇总
					boolean isRed = false;
					boolean hasData = false;
					if (colName.endsWith("AimSell")) {
					} else if (colName.endsWith("DynamicSell")) {
					} else {
						for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) rowList.get(rowIndex);
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null && rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
								isRed = true;
							}
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(((Integer) value).toString()));
								}
								hasData = true;
							}
						}
					}

					if (row.getCell("amount").getUserObject() != null) {
						if (colName.endsWith("Dynamic")) {
							amount = FDCHelper.add(row.getCell(colName.substring(0, colName.length() - 7) + "Aim").getValue(), amount);
						}
					}

					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(Color.BLACK);
					}
					if (FDCHelper.toBigDecimal(amount).compareTo(FDCHelper.ZERO) != 0) {
						row.getCell(colName).setValue(amount);
					}
				}
			}
			for (int k = 0; k < amountColumns.size(); k++) {
				String colName = (String) amountColumns.get(k);
				BigDecimal amount = FDCHelper.ZERO;
				if (colName.endsWith("Dynamic")) {
					row.getCell(colName.substring(0, colName.length() - 7) + "NoHappen").setValue(
							FDCHelper.subtract(row.getCell(colName.substring(0, colName.length() - 7) + "Dynamic").getValue(), row.getCell(colName.substring(0, colName.length() - 7) + "HasHappen")
									.getValue()));
				}
				if (colName.endsWith("AimSell")) {
					String productID = colName.substring(0, colName.length() - 7);
					String areaValueDynKey = productID + ProjectStageEnum.DYNCOST_VALUE;
					BigDecimal aimCost = (BigDecimal) row.getCell(productID + "Aim").getValue();
					BigDecimal aimSellArea = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(areaValueDynKey));
					if (!FDCHelper.isZero(aimCost) && aimSellArea.compareTo(FDCHelper.ZERO) != 0) {
						amount = aimCost.divide(aimSellArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell(colName).setValue(amount);
					}
				} else if (colName.endsWith("DynamicSell")) {
					String productID = colName.substring(0, colName.length() - 11);
					String areaValueDynKey = productID + ProjectStageEnum.DYNCOST_VALUE;
					BigDecimal dynCost = (BigDecimal) row.getCell(productID + "Aim").getValue();
					BigDecimal dySellArea = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(areaValueDynKey));
					if (!FDCHelper.isZero(dynCost) && dySellArea.compareTo(FDCHelper.ZERO) != 0) {
						amount = dynCost.divide(dySellArea, 2, BigDecimal.ROUND_HALF_UP);
						row.getCell(colName).setValue(amount);
					}
					
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
	}
	
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblMain.getRow(rowIndex);
		
		if(row==null){
			return;
		}
		
		if (tblMain.getColumnKey(columnIndex).equals("isChoose")) {
			List rowList = new ArrayList();
			Boolean isChoose = (Boolean) row.getCell("isChoose").getValue();
			if (row.getCell("isChoose").getUserObject() == null) {
				isChoose = Boolean.valueOf(!isChoose.booleanValue());
				row.getCell("isChoose").setValue(isChoose);
			}
			if (rowIndex >= tblMain.getRowCount() - 1) {
				return;
			}
			int level = row.getTreeLevel();
			for (int i = rowIndex + 1; i < tblMain.getRowCount(); i++) {
				if (tblMain.getRow(i).getTreeLevel() > level) {
					rowList.add(tblMain.getRow(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < rowList.size(); i++) {
				IRow childRow = (IRow) rowList.get(i);
				if (childRow.getCell("isChoose").getUserObject() == null) {
					childRow.getCell("isChoose").setValue(isChoose);
				}
			}
		}
	}

	public void fillTable() throws Exception {
//		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("start");
		KDTable table = this.tblMain;
		table.removeRows();
		rowMap.clear();
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!treeNode.isLeaf()) {
			return;
		}
		TimeTools.getInstance().msValuePrintln("start get data");
		String objectId = this.getSelectObjId();
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		// acctFilter.getFilterItems().add(
		// new FilterItemInfo("isEnabled", new Integer(1)));
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);
		this.initAcct(acctFilter);
		this.addDynamicCostColl.clear();
//		this.happenGetter = new HappenDataGetter(objectId, false, false);
//		this.aimGetter = new AimCostSplitDataGetter(objectId);
//		this.dyGetter = new DyCostSplitDataGetter(objectId, aimGetter,
//				happenGetter,this.dyProductTypeGetter);
		TimeTools.getInstance().msValuePrintln("end get data");
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel);
		TimeTools.getInstance().msValuePrintln("start fillNode");
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(i));
		}
		TimeTools.getInstance().msValuePrintln("end fillNode");
				this.setAimUnionData();
				 this.setUnionData();
		 if (this.isShowSplitAcct) {
			filterSplitData();
		}
		TimeTools.getInstance().msValuePrintln("end fillTable");
	}
	public void fillTableNotLeaf() throws Exception{
		if(retValueNotLeaf==null)
			return;
		CostAccountCollection costAccounts = (CostAccountCollection)retValueNotLeaf.get("CostAccountCollection");
		ProductTypeCollection prodcutMap = (ProductTypeCollection)retValueNotLeaf.get("ProductTypeCollection");
		RetValue productCostValues = (RetValue)retValueNotLeaf.get("productCostValues");
		RetValue productSellAreaValue = (RetValue)retValueNotLeaf.get("ProductSellAreaValue");
		RetValue costValues = (RetValue)retValueNotLeaf.get("costValues");
		
		String[] resName = new String[] { "AimSell", "Aim", "HasHappen",
				"NoHappen", "DynamicSell", "Dynamic" };
		this.tblMain.removeRows();
		rowMap.clear();
		tblMain.getTreeColumn().setDepth(retValueNotLeaf.getInt("maxLevel"));
		if(costAccounts!=null){
			for(Iterator it=costAccounts.iterator();it.hasNext();){
				CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
				if (!isAll) {
					if (!costAccountInfo.isIsEnabled()) {
						continue;
					}
				}
				IRow row = tblMain.addRow();
				row.setTreeLevel(costAccountInfo.getLevel() - 1);
				String longNumber = costAccountInfo.getLongNumber();
				row.getCell("acctNumber").setValue(longNumber.replace('!', '.'));
				row.getCell("acctName").setValue(costAccountInfo.getName());
				
				
				
				if(costAccountInfo.isIsLeaf()){
					row.setUserObject(costAccountInfo);
					RetValue costValue = (RetValue)costValues.get(longNumber);
					RetValue productCostValue = (RetValue)productCostValues.get(longNumber);
					if(costValue!=null&&costValue.getBigDecimal("dynCost")!=null&&costValue.getBigDecimal("dynCost").compareTo(FDCHelper.ZERO)!=0)
						row.getCell("amount").setValue(costValue.getBigDecimal("dynCost"));
					for (Iterator iter = prodcutMap.iterator(); productCostValue!=null&&iter.hasNext();) {
						ProductTypeInfo product = (ProductTypeInfo) iter.next();
						String productID = product.getId().toString();
						//AIM 目标
						String cellKey = productID+"Aim";
						String valueKey = productID+"aimCost";
						
//						String areaValueAimKey = productID+ProjectStageEnum.AIMCOST_VALUE;
						String areaValueDynKey = productID+ProjectStageEnum.DYNCOST_VALUE;
						BigDecimal aimCost = FDCHelper.toBigDecimal(productCostValue.getBigDecimal(valueKey));
						if(aimCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(aimCost);
						//目标可售单方
						BigDecimal aimSellArea = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(areaValueDynKey));
						if(aimCost.compareTo(FDCHelper.ZERO)!=0&&aimSellArea.compareTo(FDCHelper.ZERO)!=0){
							BigDecimal aimSellValue = aimCost.divide(aimSellArea,2,BigDecimal.ROUND_HALF_UP);
							cellKey = productID+"AimSell";
							row.getCell(cellKey).setValue(aimSellValue);
						}
						
						//已发生
						cellKey = productID+"HasHappen";
						valueKey = productID+"happenCost";
						BigDecimal happenCost = FDCHelper.toBigDecimal(productCostValue.getBigDecimal(valueKey));
						if(happenCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(happenCost);
						
						//动态
						cellKey = productID+"Dynamic";
						valueKey = productID+"dynCost";
						BigDecimal dynCost = FDCHelper.toBigDecimal(productCostValue.getBigDecimal(valueKey));
						if(dynCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(dynCost);
						BigDecimal dynSellArea = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(areaValueDynKey));
						if(dynCost.compareTo(FDCHelper.ZERO)!=0&&dynSellArea.compareTo(FDCHelper.ZERO)!=0){
							BigDecimal dynSellValue = dynCost.divide(dynSellArea,2,BigDecimal.ROUND_HALF_UP);
							cellKey = productID+"DynamicSell";
							row.getCell(cellKey).setValue(dynSellValue);
						}
						//未发生 = 动态 - 已发生
						cellKey = productID+"NoHappen";
						
						BigDecimal noHappenCost = dynCost.subtract(happenCost);
						if(noHappenCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(noHappenCost);
						
						
					}
				}else{
					rowMap.put(new Integer(row.getRowIndex()), costAccountInfo);
					RetValue costValue = (RetValue)costValues.get(longNumber);
					RetValue productCostValue = (RetValue)productCostValues.get(longNumber);
					if(costValue!=null&&costValue.getBigDecimal("dynCost")!=null&&costValue.getBigDecimal("dynCost").compareTo(FDCHelper.ZERO)!=0){
						row.getCell("amount").setValue(costValue.getBigDecimal("dynCost"));
						row.getCell("amount").setUserObject("hasData");
					}
					for (Iterator iter = prodcutMap.iterator(); productCostValue!=null&&iter.hasNext();) {
						ProductTypeInfo product = (ProductTypeInfo) iter.next();
						String productID = product.getId().toString();
						//AIM 目标
						String cellKey = productID+"Aim";
						String valueKey = productID+"aimCost";
						
//						String areaValueAimKey = productID+ProjectStageEnum.AIMCOST_VALUE;
						String areaValueDynKey = productID+ProjectStageEnum.DYNCOST_VALUE;
						BigDecimal aimCost = FDCHelper.toBigDecimal(productCostValue.getBigDecimal(valueKey));
						if(aimCost.compareTo(FDCHelper.ZERO)!=0){
							row.getCell(cellKey).setValue(aimCost);
							row.getCell("amount").setUserObject("hasData");
						}
						//目标可售单方
						BigDecimal aimSellArea = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(areaValueDynKey));
						if(aimCost.compareTo(FDCHelper.ZERO)!=0&&aimSellArea.compareTo(FDCHelper.ZERO)!=0){
							BigDecimal aimSellValue = aimCost.divide(aimSellArea,2,BigDecimal.ROUND_HALF_UP);
							cellKey = productID+"AimSell";
							row.getCell(cellKey).setValue(aimSellValue);
						}
						
						//已发生
						cellKey = productID+"HasHappen";
						valueKey = productID+"happenCost";
						BigDecimal happenCost = FDCHelper.toBigDecimal(productCostValue.getBigDecimal(valueKey));
						if(happenCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(happenCost);
						
						//动态
						cellKey = productID+"Dynamic";
						valueKey = productID+"dynCost";
						BigDecimal dynCost = FDCHelper.toBigDecimal(productCostValue.getBigDecimal(valueKey));
						if(dynCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(dynCost);
						BigDecimal dynSellArea = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(areaValueDynKey));
						if(dynCost.compareTo(FDCHelper.ZERO)!=0&&dynSellArea.compareTo(FDCHelper.ZERO)!=0){
							BigDecimal dynSellValue = dynCost.divide(dynSellArea,2,BigDecimal.ROUND_HALF_UP);
							cellKey = productID+"DynamicSell";
							row.getCell(cellKey).setValue(dynSellValue);
						}
						//未发生 = 动态 - 已发生
						cellKey = productID+"NoHappen";
						
						BigDecimal noHappenCost = dynCost.subtract(happenCost);
						if(noHappenCost.compareTo(FDCHelper.ZERO)!=0)
							row.getCell(cellKey).setValue(noHappenCost);
						
						
					}
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				}
			}
		}
		setNotLeafAimUnionData();
		setNotLeafUnionData();
	}

	private BigDecimal getSumAdjustCol(String acctId) throws BOSException,
			SQLException {
		TimeTools.getInstance().msValuePrintln("---start getSumAdjustCol----");
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql()
				+ " where ");
		String fullNumber = "";
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
					+ "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		String longNumber = acct.getLongNumber();
		innerSql.append(" (FLongNumber ='").append(longNumber).append("'")
				.append(" or FLongNumber like '").append(longNumber).append(
						"!%' ").append(" or FLongNumber like '%!").append(
						longNumber).append("!%') ");
		innerSql.append("and (FullNumber =	'").append(fullNumber).append("'")
				.append(" or FullNumber like '").append(fullNumber).append(
						"!%' ").append(" or FullNumber like '%!").append(
						fullNumber).append("!%') ");

		String sql = "select sum(FAdjustSumAmount) sumAmount from T_AIM_DynamicCost where FAccountId in ("
				+ innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		BigDecimal adjustAmount = null;
		while (rs.next()) {
			adjustAmount = rs.getBigDecimal("sumAmount");
		}
		if (adjustAmount != null && adjustAmount.compareTo(FDCHelper.ZERO) == 0) {
			return null;
		}
		TimeTools.getInstance().msValuePrintln("---end getSumAdjustCol----");
		return adjustAmount;
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			MsgBox.showError("成本科目的级别太多!");
			return;
		}
		if (!isAll) {
			if (!costAcct.isIsEnabled()) {
				return;
			}
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		row.getCell("isChoose").setValue(Boolean.FALSE);
		row.getCell("acctNumber").setUserObject(costAcct);
		Boolean b = Boolean.valueOf(costAcct.isIsSplit());
		row.getCell("isSplit").setValue(b);
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			BigDecimal adjustAmount = null;
			BigDecimal dynamicAmount = null;
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(costAcct
					.getId().toString());
			// 动态成本跟节点要求汇总
//			if(true) return;
			if (proNode.isLeaf()) {
				if (dynamic != null) {
					adjustAmount = dynamic.getAdjustSumAmount();
				}
			} else {
				adjustAmount = getSumAdjustCol(costAcct.getId().toString());
			}
			BigDecimal aimAmount = this.aimGetter.getAimCost(costAcct.getId()
					.toString());
			if (adjustAmount == null) {
				adjustAmount = FDCHelper.ZERO;
			}
			
			dynamicAmount = aimAmount.add(adjustAmount);
			if (dynamicAmount.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell("amount").setValue(dynamicAmount);
			}
			if (dynamic != null) {
				DynamicCostProductSplitEntryCollection cashe = this.dyGetter
						.getProductSplitEntry(dynamic.getId().toString());
				Map databaseData = new HashMap();
				if (cashe != null) {
					for (int j = 0; j < cashe.size(); j++) {
						DynamicCostProductSplitEntryInfo split = cashe.get(j);
						databaseData.put(split.getProduct().getId().toString(),
								split.getSplitAmount());
					}
				}
				row.getCell("acctNumber").setUserObject(databaseData);
			} else {
				row.getCell("acctNumber").setUserObject(new HashMap());
			}
			fillAimData(row);
			DynamicCostProductSplitEntryCollection splits = getRowSetting(row);
			calculateRowData(row, splits);
		} else {
			rowMap.put(new Integer(row.getRowIndex()), costAcct);
			BigDecimal adjustAmount = null;
			BigDecimal dynamicAmount = null;
			DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(costAcct
					.getId().toString());
			// 动态成本跟节点要求汇总
			// if(true) return;
			if (proNode.isLeaf()) {
				if (dynamic != null) {
					adjustAmount = dynamic.getAdjustSumAmount();
				}
			} else {
				adjustAmount = getSumAdjustCol(costAcct.getId().toString());
			}
			BigDecimal aimAmount = this.aimGetter.getAimCost(costAcct.getId()
					.toString());
			if (adjustAmount == null) {
				adjustAmount = FDCHelper.ZERO;
			}
			dynamicAmount = aimAmount.add(adjustAmount);
			//			if (dynamicAmount.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell("amount").setValue(dynamicAmount);
				
				/**
			 * 存在目标成本为空，但是调整有值的情况
			 */
			if (!FDCHelper.isZero(aimAmount) || !FDCHelper.isZero(getSumAdjustCol2(costAcct.getId().toString()))) {
				row.getCell("amount").setUserObject(costAcct.getId().toString());
			}
			if (dynamic != null) {
				DynamicCostProductSplitEntryCollection cashe = this.dyGetter
						.getProductSplitEntry(dynamic.getId().toString());
				Map databaseData = new HashMap();
				if (cashe != null) {
					for (int j = 0; j < cashe.size(); j++) {
						DynamicCostProductSplitEntryInfo split = cashe.get(j);
						databaseData.put(split.getProduct().getId().toString(),
								split.getSplitAmount());
					}
				}
				row.getCell("acctNumber").setUserObject(databaseData);
			} else {
				row.getCell("acctNumber").setUserObject(new HashMap());
			}
			fillAimData(row);
			DynamicCostProductSplitEntryCollection splits = getRowSetting(row);
			calculateRowData(row, splits);
			
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
			//		}
			}
	}
	
	/**
	 * 
	 * 描述：获取当前非明细科目调整金额
	 * @param acctId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @Author：keyan_zhao
	 * @CreateTime：2012-9-27
	 */
	private BigDecimal getSumAdjustCol2(String acctId) throws BOSException, SQLException {
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql() + " where ");
		String fullNumber = "";
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		if (acct == null)
			return FDCHelper.ZERO;
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber() + "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		String longNumber = acct.getLongNumber();
		innerSql.append(" FLongNumber ='").append(longNumber).append("'");
		innerSql.append("and FullNumber =	'").append(fullNumber).append("'");

		String sql = "select sum(FAdjustSumAmount) sumAmount from T_AIM_DynamicCost where FAccountId in (" + innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		BigDecimal adjustAmount = null;
		while (rs.next()) {
			adjustAmount = rs.getBigDecimal("sumAmount");
		}
		if (adjustAmount != null && adjustAmount.compareTo(FDCHelper.ZERO) == 0) {
			return null;
		}
		return adjustAmount;
	}
	
	private void fillAimData(IRow row) throws BOSException, SQLException {
		CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
		if(acct == null) {
			acct = (CostAccountInfo)rowMap.get(new Integer(row.getRowIndex()));
		}
		if (acct == null) {
			return;
		} 
		String acctId = acct.getId().toString();
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (this.dyProductTypeGetter.getProductTypeSize() > 0 && proNode.isLeaf()) {
			Map productAim = this.aimGetter.getProductMap(acctId);

			Set keySet = productAim.keySet();
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
				String productId = (String) iter.next();
				BigDecimal value = (BigDecimal) productAim.get(productId);
				if (row.getCell(productId + "Aim") != null) {
					row.getCell(productId + "Aim").setValue(value);
//					row.getCell("amount").setUserObject("hasData");
				}
			}
			this.setSellAmountbySplit(row, productAim, true);

		}
	}

	private DynamicCostProductSplitEntryCollection getRowSetting(IRow entryRow) throws BOSException {
		CostAccountInfo acct = (CostAccountInfo) entryRow.getUserObject();
		if(acct==null){
			acct = (CostAccountInfo)rowMap.get(new Integer(entryRow.getRowIndex()));
		}
		String acctId = acct.getId().toString();
		Map aimProductMap = this.aimGetter.getProductMap(acctId);
		DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(acct.getId()
				.toString());
		DynamicCostProductSplitEntryCollection cashe = new DynamicCostProductSplitEntryCollection();
		AdjustRecordEntryCollection adjustEntrys = new AdjustRecordEntryCollection();
		if (dynamic != null) {
			cashe = this.dyGetter.getProductSplitEntry(dynamic.getId()
					.toString());
			adjustEntrys = dynamic.getAdjustEntrys();
		}
		HappenDataInfo happen = this.happenGetter.getHappenInfo(acctId);
		Map hasHappenMap = new HashMap();
		if (happen != null && happen.getProductAmountMap() != null) {
			hasHappenMap = happen.getProductAmountMap();
		}
		Map adjustMap = getAdjustDirectMap(adjustEntrys);
		DynamicCostProductSplitEntryCollection splits = this.dyGetter
				.createSetting(cashe, aimProductMap, adjustMap, hasHappenMap);
		return splits;
	}

	private Map getAdjustDirectMap(AdjustRecordEntryCollection adjustCostEntrys) {
		// 调整的直接费用
		Map adjustMap = new HashMap();
		if (adjustCostEntrys != null) {
			for (int i = 0; i < adjustCostEntrys.size(); i++) {
				AdjustRecordEntryInfo info = adjustCostEntrys.get(i);
				BigDecimal costAmount = info.getCostAmount();
				ProductTypeInfo product = info.getProduct();
				if (costAmount != null && product != null) {
					if (adjustMap.containsKey(product.getId().toString())) {
						BigDecimal value = (BigDecimal) adjustMap.get(product
								.getId().toString());
						adjustMap.put(product.getId().toString(), value
								.add(costAmount));
					} else {
						adjustMap.put(product.getId().toString(), costAmount);
					}
				}
			}
		}
		return adjustMap;
	}

	private void calculateRowData(IRow entryRow,
			DynamicCostProductSplitEntryCollection splits) throws BOSException {
		clearRowData(entryRow);
		// 没有拆分方案
		if (splits.size() <= 0) {
			entryRow.getCell("acctName").setUserObject(new HashMap());
			entryRow.getCell("apportionType").setUserObject("null");
			this.setRowColor(entryRow, Color.BLUE);
			
//			return; 没有分摊方案也让分摊
		}
		CostAccountInfo acct = (CostAccountInfo) entryRow.getUserObject();
		if(acct==null){
			acct = (CostAccountInfo)rowMap.get(new Integer(entryRow.getRowIndex()));
		}
		Map dyCalculateData = this.dyGetter.getDyProductData(acct.getId()
				.toString(), splits);
		Set keySet = dyCalculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal finalAmount = (BigDecimal) dyCalculateData.get(prodId);
			if (entryRow.getCell(prodId + "Dynamic") != null) {
				entryRow.getCell(prodId + "Dynamic").setValue(finalAmount);
			}
		}
		// 设置可售面积列
		setSellAmountbySplit(entryRow, dyCalculateData, false);
		entryRow.getCell("acctName").setUserObject(dyCalculateData);

		Map happenCalculateData = this.dyGetter.getHasHappenProductData(acct
				.getId().toString(), splits);
		keySet = happenCalculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal finalAmount = (BigDecimal) happenCalculateData
					.get(prodId);
			entryRow.getCell(prodId + "HasHappen").setValue(finalAmount);
			BigDecimal dyAmount = (BigDecimal) dyCalculateData.get(prodId);
			entryRow.getCell(prodId + "NoHappen").setValue(FDCHelper.subtract(dyAmount, finalAmount));
		}
		
		ApportionTypeInfo apportionType = null;//firstSplit.getApportionType();
		if(splits!=null&&splits.size()>0){
			DynamicCostProductSplitEntryInfo firstSplit = splits.get(0);
			apportionType = firstSplit.getApportionType();
		}
		// 有分摊方案条目，但分摊类型为空，表示没有分摊过，只是有指定分摊
		if (apportionType == null) {
			entryRow.getCell("acctName").setUserObject(new HashMap());
			entryRow.getCell("apportionType").setUserObject("null");
			this.setRowColor(entryRow, Color.BLUE);
			return;
		}
		entryRow.getCell("apportionType").setValue(apportionType.getName());
		if (entryRow.getCell("apportionType").getUserObject() != null) {
			String apporId = (String) entryRow.getCell("apportionType")
					.getUserObject();
			if (!apportionType.getId().toString().equals(apporId)) {
				entryRow.getCell("apportionType").getStyleAttributes()
						.setFontColor(Color.RED);
			} else {
				entryRow.getCell("apportionType").getStyleAttributes()
						.setFontColor(Color.BLACK);
			}
		} else {
			entryRow.getCell("apportionType").setUserObject(
					apportionType.getId().toString());
		}
		if (isEaquelDatabase(entryRow)) {
			this.setRowColor(entryRow, Color.BLACK);
		} else {
			this.setRowColor(entryRow, Color.RED);
		}
	}

	private void clearRowData(IRow entryRow) {
		// 清空行上旧内容
		String[] productTypeIds = this.dyProductTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			entryRow.getCell(productTypeId + "DynamicSell").setValue(null);
			entryRow.getCell(productTypeId + "HasHappen").setValue(null);
			entryRow.getCell(productTypeId + "NoHappen").setValue(null);
			entryRow.getCell(productTypeId + "Dynamic").setValue(null);
		}
	}

	private void setSellAmountbySplit(IRow entryRow, Map calculateData,
			boolean isAimCost) {
		Set keySet = calculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			String key = getSelectObjId() + " ";
			key += productId + " ";
			key += ApportionTypeInfo.sellAreaType;
			BigDecimal area = null;
			String colName = "";
			if (isAimCost) {
				colName = "Aim";
				area = (BigDecimal) this.aimApportionMap.get(key);
			} else {
				colName = "Dynamic";
				area = (BigDecimal) this.dyApportionMap.get(key);
			}
			BigDecimal value = null;
			if (entryRow.getCell(productId + colName) != null) {
				value = (BigDecimal) entryRow.getCell(productId + colName)
						.getValue();
			}
			if (value != null && area != null
					&& area.compareTo(FDCHelper.ZERO) != 0) {
				entryRow.getCell(productId + colName + "Sell").setValue(
						value.divide(area, 2, BigDecimal.ROUND_HALF_UP));
			}

		}
	}

	private boolean isEaquelDatabase(IRow entryRow) {
		final Object obj = this.pnlMain.getUserObject();
		if(obj!=null&&obj.equals("submit")){
			return true;
		}
		Map databaseData = (Map) entryRow.getCell("acctNumber").getUserObject();
		Map splitData = (Map) entryRow.getCell("acctName").getUserObject();
		Map verifyData = new HashMap();
		if (splitData == null) {
			splitData = new HashMap();
		}
		if (databaseData == null) {
			databaseData = new HashMap();
		}
		Set splitSet = splitData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			verifyData.put(productId, splitData.get(productId));
		}

		Set dataSet = databaseData.keySet();
		for (Iterator iter = dataSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount1 = (BigDecimal) verifyData.get(productId);
			if (amount1 == null) {
				amount1 = FDCHelper.ZERO;
			}
			BigDecimal amount2 = (BigDecimal) databaseData.get(productId);
			if (amount2 == null) {
				amount2 = FDCHelper.ZERO;
			}
			verifyData.put(productId, FDCHelper.subtract(amount1, amount2));
		}
		splitSet = verifyData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount = (BigDecimal) verifyData.get(productId);
			if (amount.compareTo(FDCHelper.ZERO) != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node==null||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		selectedRowIndex = -1;
		TimeTools.getInstance().reset();
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		if(e!=null&&e.getOldLeadSelectionPath()!=null){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)e.getOldLeadSelectionPath().getLastPathComponent();
			if(node!=null&&node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo)
				this.verifySaved((CurProjectInfo)node.getUserObject());
		}
		
//		this.dyProductTypeGetter = new DyProductTypeGetter(selectObjId);
//		this.aimProductTypeGetter=new AimProductTypeGetter(selectObjId,ProjectStageEnum.AIMCOST);
//		this.aimApportionMap = ProjectHelper.getIndexValueByProjProd(null,
//				selectObjId,ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
//		this.dyApportionMap = ProjectHelper.getIndexValueByProjProd(null,
//				selectObjId, ProjectStageEnum.DYNCOST);
//		this.dyProductTypeGetter.setDyApportionMap(dyApportionMap);
////		this.aimProductTypeGetter.setAimApportionMap(aimApportionMap);
		
		fetchAndFill(selectObjId); 
		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
		tblMain.getViewManager().freeze(0, acctNameIndex);
		initUserConfig();
		
	}

	private void fetchAndFill(String selectObjId) throws EASBizException,
			BOSException, Exception {
		this.actionSubmit.setEnabled(false);
		this.actionApportion.setEnabled(false);
		this.actionRevert.setEnabled(false);
		
		LongTimeDialog dialog = UITools.getDialog(this);
		if(dialog==null)
			return;
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec() throws Exception {
            	String selectObjId = getSelectObjId(); 
            	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
        		.getLastSelectedPathComponent();
        		if (node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo) {
        			fetchData(selectObjId);
        			resetProductCol();
        			setApporAction();
        			fillTable();
        		} else {
        			Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(node);
        			boolean selectObjIsPrj = node.getUserObject() instanceof CurProjectInfo;
        			fetchDataNotLeaf(selectObjId,leafPrjIds,selectObjIsPrj);
        			resetProductColNotLeaf();
        			setApporAction();
        			fillTableNotLeaf();
        		}
                return null;
            }

            public void afterExec(Object result) throws Exception {
                
            }
        });
        dialog.show();
	}

	private void resetProductCol() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 5; i++) {
			tblMain.removeColumn(5);
		}
		Map prodcutMap = this.dyProductTypeGetter.getSortedProductMap();
		String[] resName = new String[] { "AimSell", "Aim", "HasHappen",
				"NoHappen", "DynamicSell", "Dynamic" };
		int i = 0;
		Set keySet = prodcutMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			for (int j = 0; j < 6; j++) {
				String key = product.getId().toString() + resName[j];
				IColumn col = tblMain.addColumn();
				col.setKey(key);
				col.getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
				tblMain.getHeadRow(1).getCell(key).setValue(
						AimCostHandler.getResource(resName[j]));
			}
			tblMain.getHeadMergeManager().mergeBlock(0, 5 + i * 6, 0,
					5 + 5 + i * 6);
			i++;

		}
		if (tblMain.getColumn("isSplit") == null) {
			IColumn c = tblMain.addColumn();
			c.setKey("isSplit");
			c.getStyleAttributes().setHided(true);
		}
	}
	private void resetProductColNotLeaf() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 5; i++) {
			tblMain.removeColumn(5);
		}
		if(retValueNotLeaf==null)
			return;
		ProductTypeCollection prodcutMap = (ProductTypeCollection)this.retValueNotLeaf.get("ProductTypeCollection");
		String[] resName = new String[] { "AimSell", "Aim", "HasHappen",
				"NoHappen", "DynamicSell", "Dynamic" };
		int i = 0;
		
		for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) iter.next();
			for (int j = 0; j < 6; j++) {
				String key = product.getId().toString() + resName[j];
				IColumn col = tblMain.addColumn();
				col.setKey(key);
				col.getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
				tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
				tblMain.getHeadRow(1).getCell(key).setValue(
						AimCostHandler.getResource(resName[j]));
			}
			tblMain.getHeadMergeManager().mergeBlock(0, 5 + i * 6, 0,
					5 + 5 + i * 6);
			i++;

		}
	}

	private void verifySaved(CurProjectInfo curProject) throws EASBizException, BOSException {
		if (!currentOrg.isIsBizUnit()) {
			return;
		}
		boolean isEdited = false;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				isEdited = !this.isEaquelDatabase(row);
				if (row.getCell("apportionType").getStyleAttributes()
						.getFontColor().equals(Color.RED)) {
					isEdited = true;
				}
				if (isEdited) {
					break;
				}
			}
		}
		if (isEdited) {
			if (MsgBox.showConfirm2(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
				submitData(curProject);
//				this.btnSubmit.doClick();
			}
		}
	}

	/**
	 * 设置表格属性
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
		table.setRefresh(false);
		table.getColumn("apportionType").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		table.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.addHeadRow(1, (IRow) table.getHeadRow(0).clone());
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		table.setColumnMoveable(true);
	}

	private void setApporAction() {
		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
			// this.actionSubmit.setEnabled(false);
			// this.actionApportion.setEnabled(false);
			// this.actionRevert.setEnabled(false);
			// this.tblMain.getColumn("isChoose").getStyleAttributes().setHided(
			// true);
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!node.isLeaf()) {
			this.actionSubmit.setEnabled(false);
			this.actionApportion.setEnabled(false);
			this.actionRevert.setEnabled(false);
		} else {
			this.actionSubmit.setEnabled(true);
			this.actionApportion.setEnabled(true);
			this.actionRevert.setEnabled(true);
		}
		tblMain.getColumn("apportionType").getStyleAttributes().setHided(!node.isLeaf());
		tblMain.getColumn("isChoose").getStyleAttributes().setHided(!node.isLeaf());
	}

	/**
	 * output class constructor
	 */
	public ProductDynamicCostUI() throws Exception {
		super();
	}

	int selectedRowIndex = -1;
	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		selectedRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo)
		submitData((CurProjectInfo)node.getUserObject());
		this.setMessageText(EASResource
				.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
//		this.pnlMain.setUserObject("submit");
		final String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fetchData(selectObjId);
		resetProductCol();
		
		fillTable();
//		setApporAction();
//		this.pnlMain.setUserObject(null);
		if(selectedRowIndex>=0 && tblMain.getRow(selectedRowIndex)!=null) {
			tblMain.getSelectManager().select(selectedRowIndex, 0);
			tblMain.getLayoutManager().scrollRowToShow(selectedRowIndex);
		}
	}

	protected String getKeyFieldName() {
		return "acctNumber";
	}
	
	private void submitData(CurProjectInfo curProject) throws BOSException, EASBizException {
		if(curProject==null||!curProject.isIsLeaf())
			return;
		Map dynSplitEntryMap=new HashMap();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				boolean isEdited = !this.isEaquelDatabase(row);
				if (row.getCell("apportionType").getStyleAttributes()
						.getFontColor().equals(Color.RED)) {
					isEdited = true;
				}
				//2008-12-2 因为是多版本保存 ，不管是否修改，保存改工程项目下所有科目的分摊方案
//				if (isEdited) {
					CostAccountInfo acct = (CostAccountInfo) row
							.getUserObject();
					DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(acct
							.getId().toString());
					if(dynamic==null) continue;
					final String dynamicCostId = dynamic.getId().toString();
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(
//							new FilterItemInfo("dynamicCostId", dynamic.getId()
//									.toString()));
//					DynamicCostProductSplitEntryFactory.getRemoteInstance()
//							.delete(filter);
					DynamicCostProductSplitEntryCollection rowSetting = this
							.getRowSetting(row);
					Map calData = (Map) row.getCell("acctName").getUserObject();
					DynamicCostProductSplitEntryCollection newColl=new DynamicCostProductSplitEntryCollection();
					for (int j = 0; j < rowSetting.size(); j++) {
						DynamicCostProductSplitEntryInfo info = rowSetting
								.get(j);
						info.setDynamicCostId(dynamicCostId);
						info.setSplitAmount((BigDecimal) calData.get(info
								.getProduct().getId().toString()));
//						info.setDescription("isChoose");
						if (info.getApportionType() != null) {
							newColl.add((DynamicCostProductSplitEntryInfo)info.clone());
						}
					}
					
					if(newColl.size()>0){
						dynSplitEntryMap.put(dynamicCostId, newColl);
					}
//				}
			}
		}
		if(addDynamicCostColl.size()==0&&dynSplitEntryMap.size()==0){
			return;
		}
		
		Map dynCostMap=new HashMap();
		if(addDynamicCostColl.size()>0){
			dynCostMap.put("addDynamicCostColl", addDynamicCostColl);
			addDynamicCostColl=new DynamicCostCollection();
		}
		//所有的
		if(allDynamic.size() > 0 ){
			dynCostMap.put("allDynamic",allDynamic);
		}
		dynCostMap.put("dynSplitEntryMap", dynSplitEntryMap);
		//增加工程项目信息
		String projectId = curProject.getId().toString();
		
		dynCostMap.put("projectId",projectId);
		FDCCostRptFacadeFactory.getRemoteInstance().submitDynProductCost(dynCostMap);
		CacheServiceFactory.getInstance().discardType(new DynamicCostProductSplitEntryInfo().getBOSType());
	}

	/**
	 * output actionApportion_actionPerformed
	 */
	public void actionApportion_actionPerformed(ActionEvent e) throws Exception {
		List rows = new ArrayList();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				if (row.getCell("isChoose").getValue() != null
						&& ((Boolean) row.getCell("isChoose").getValue())
								.booleanValue()) {
					rows.add(row);
				}
			}
		}
		if (rows.size() == 0) {
			this.setMessageText(AimCostHandler.getResource("NoDetailRow"));
			this.showMessage();
			return;
		}
		UIContext uiContext = new UIContext(this);
		if (rows.size() == 1) {
			IRow selectRow = (IRow) rows.get(0);
			// 准备参数
			DynamicCostProductSplitEntryCollection entrys = getRowSetting(selectRow);
			CostAccountInfo acct = (CostAccountInfo) selectRow.getUserObject();
			String acctId = acct.getId().toString();
			Boolean isFullApportion = Boolean.valueOf(this.aimGetter
					.isFullApportion(acctId));
			uiContext.put("isFullApportion", isFullApportion);
			uiContext.put("isMutiSelected", Boolean.FALSE);
			uiContext.put("splits", entrys);
		} else {
			uiContext.put("isFullApportion", Boolean.TRUE);
			uiContext.put("isMutiSelected", Boolean.TRUE);
			uiContext.put("splits",
					new DynamicCostProductSplitEntryCollection());
		}
		String selectObjId = this.getSelectObjId();
		BOSObjectType bosType = BOSUuid.read(selectObjId).getType();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			uiContext.put("projectId", selectObjId);
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(DynamicCostProductSplitSettingUI.class.getName(),
							uiContext, null, "ADDNEW");
			uiWin.show();
			Map context = ((DynamicCostProductSplitSettingUI) uiWin
					.getUIObject()).getUIContext();
			if (context.get("isOK") == null) {
				return;
			}
			for (int i = 0; i < rows.size(); i++) {
				IRow row = (IRow) rows.get(i);
				CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
				DynamicCostProductSplitEntryCollection col = null;
				if (context.get("splits") != null) {
					col = (DynamicCostProductSplitEntryCollection) ((DynamicCostProductSplitEntryCollection) context
							.get("splits")).clone();
				}
				//findbusgs by sxhong
				if(col==null){
					continue;
				}
				DynamicCostInfo dynamic = this.dyGetter.getDynamicInfo(acct
						.getId().toString());
				if (dynamic == null) {
					dynamic = new DynamicCostInfo();
					dynamic.setId(BOSUuid.create(dynamic.getBOSType()));
					dynamic.setAccount(acct);
					dynamic.setAdjustSumAmount(FDCHelper.ZERO);
					dynamic.setIntendingCostSumAmount(FDCHelper.ZERO);
					this.dyGetter.updateDynamic(acct.getId().toString(),
							dynamic);
					this.addDynamicCostColl.add(dynamic);
					//新增的
					allDynamic.add(dynamic);
				}else{
					//原有的
					this.allDynamic.add(dynamic);
				}

				String acctId = acct.getId().toString();
				DynamicCostProductSplitEntryInfo firstSplit = col.get(0);
				ApportionTypeInfo apportionType = firstSplit.getApportionType();
				if (apportionType.getId().toString().equals(
						ApportionTypeInfo.aimCostType)) {
					if (!this.aimGetter.isFullApportion(acctId)) {
						continue;
					}
				}
				Map aimProductMap = this.aimGetter.getProductMap(acctId);
				HappenDataInfo happen = this.happenGetter.getHappenInfo(acctId);
				Map hasHappenMap = new HashMap();
				if (happen != null && happen.getProductAmountMap() != null) {
					hasHappenMap = happen.getProductAmountMap();
				}
				Map adjustMap = getAdjustDirectMap(dynamic.getAdjustEntrys());
				col = this.dyGetter.createSetting(col, aimProductMap,
						adjustMap, hasHappenMap);
				this.dyGetter.updateProductMap(dynamic.getId().toString(), col);
				this.calculateRowData(row, col);
				this.setAimUnionData();
				this.setUnionData();
			}
		}
	}

	/**
	 * output actionRevert_actionPerformed
	 */
	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fetchData(selectObjId);
//		resetProductCol();
//		setApporAction();
		this.fillTable();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		setButtonDefaultStyl(this.btnSubmit);
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
	}

	public void actionAll_actionPerformed(ActionEvent e) throws Exception {

		isAll = true;
		this.actionRefresh_actionPerformed(e);
	}
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo)
			verifySaved((CurProjectInfo)node.getUserObject());
		
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
/*		this.aimApportionMap = ProjectHelper.getIndexValueByProjProd(null,
				selectObjId, ProjectStageEnum.AIMCOST);
		this.dyApportionMap = ProjectHelper.getIndexValueByProjProd(null,
				selectObjId, ProjectStageEnum.DYNCOST);
		this.dyProductTypeGetter.setDyApportionMap(dyApportionMap);
//		this.aimProductTypeGetter.setAimApportionMap(aimApportionMap);
*/		
		TimeTools.getInstance().reset();
		fetchAndFill(selectObjId); 
		initUserConfig();
	}

	public boolean destroyWindow() {
	
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			if(node!=null && node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo)
				verifySaved((CurProjectInfo)node.getUserObject());
			
		} catch (Exception e) { 
			super.destroyWindow();
			handUIException(e);
		} 
		return super.destroyWindow();
		
	}
	
	private void fetchData(String prjId) throws EASBizException, BOSException{
        TimeTools.getInstance().msValuePrintln("start fetchData");
		final ProductDynamicCostMap productDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getProductDynamicCost(prjId, null);
		dyGetter=productDynamicCostMap.getDyCostSplitDataGetter();
		aimGetter=productDynamicCostMap.getAimCostSplitDataGetter();
		happenGetter=productDynamicCostMap.getHappenDataGetter();
		dyProductTypeGetter=productDynamicCostMap.getDyProductTypeGetter();
		aimApportionMap=productDynamicCostMap.getAimSellApportionMap();
		dyApportionMap=productDynamicCostMap.getDyApportionMap();
		TimeTools.getInstance().msValuePrintln("end fetchData");
		
	}
	private void fetchDataNotLeaf(String objId,Set leafPrjIds,boolean selectObjIsPrj) throws EASBizException, BOSException{
		TimeTools.getInstance().msValuePrintln("start fetchData");
		ParamValue paramValue = new ParamValue();
		paramValue.put("selectObjID", objId);
		paramValue.put("leafPrjIDs", leafPrjIds);
		paramValue.put("selectObjIsPrj", Boolean.valueOf(selectObjIsPrj));
		retValueNotLeaf = ProjectCostRptFacadeFactory.getRemoteInstance().getCollectionProductDynCost(paramValue);
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}
	/**
	 * @description 显示可拆分科目事件
	 * @author 陈伟
	 * @createDate 2011-8-3
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.aimcost.client.AbstractDynamicCostUI#actionSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		// 当前按钮显示内容
		String txt = this.btnSplit.getText();
		if (txt.equals(FDCAimcostClientHelper.getRes("showSplit"))) {
			this.btnSplit.setText(FDCAimcostClientHelper.getRes("showAll"));
			this.isSplitBtn.setText(FDCAimcostClientHelper.getRes("showAll"));
			isShowSplitAcct = true;
		} else {
			this.btnSplit.setText(FDCAimcostClientHelper.getRes("showSplit"));
			this.isSplitBtn.setText(FDCAimcostClientHelper.getRes("showSplit"));
			isShowSplitAcct = false;
			 
		}
		actionRefresh_actionPerformed(null);
	}

	/**
	 * @description 过滤可拆分科目
	 * @author 向晓帆
	 * @createDate 2011-9-22
	 * @version EAS7.0
	 * @see com.kingdee.eas.fdc.aimcost.client.AbstractDynamicCostUI#actionSplit_actionPerformed(java.awt.event.ActionEvent)
	 */
	private void filterSplitData() {
		int rowCount = tblMain.getRowCount();
		CostAccountInfo info = null;
		String tmpNumber = null;
		String curNumber = null;
		IRow row = null;
		Boolean isSplit = null;
		// 循环遍历删出不可拆分的科目
		for (int i = rowCount; i > 0; i--) {
			row = tblMain.getRow(i);
			if (row != null) {
				// 得到当前行成本科目对象
				isSplit = (Boolean) row.getCell("isSplit").getValue();
				if (!isSplit.booleanValue()) {
					tblMain.removeRow(i);
				}
			}
		}
	}
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}