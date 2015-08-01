/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.IProgramming;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.invite.InviteFormEnum;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.Uuid;

/**
 * output class name
 */
public class ProgrammingEditUI extends AbstractProgrammingEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingEditUI.class);
    private CreateProTableRow create = new CreateProTableRow(dataBinder);//分录操作对象
	private DataChangeListener dataChangeListener = null;
	private EntryTreeSumField sumClass = new EntryTreeSumField();
    private final String LONGNUMBER = "longNumber";//长编码
    private final String HEADNUMBER = "headNumber";//长级长编码
    private final String BALANCE = "balance";
	private final String AMOUNT = "amount";
	private final String CONTROLAMOUNT = "controlAmount";
    protected KDWorkButton btnAddnewLine;
    protected KDWorkButton btnInsertLines;
    protected KDWorkButton btnRemoveLines;
    protected KDWorkButton btnDetails;
    
	public ProgrammingEditUI() throws Exception {
		super();
	}
    public void onLoad() throws Exception {
    	txtAllAimCost.setEnabled(false);
		txtBuildArea.setEnabled(false);
		txtSaleArea.setEnabled(false);
    	super.onLoad();
		initTable();
		setAttachmentRenderer();
    	setSmallButton();
    	setSmallBtnEnable();
    	setAimCostFilter();
    	initData();
    	setMouseClick();
    	txtVersion.setPrecision(1);

		if (this.getUIContext().get("modify") != null) {
			// 修订情况下给合约新增ID
			ProgrammingContractInfo programmingContractInfo = new ProgrammingContractInfo();
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				kdtEntries.getCell(i, "id").setValue(BOSUuid.create(programmingContractInfo.getBOSType()));
				ProgrammingContractInfo proConInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
				kdtEntries.getCell(i, "isCiting").setValue(Boolean.valueOf(proConInfo.isIsCiting()));
			}
		}
	}

    protected void initListener() {
    	super.initListener();
    	dataChangeListener = new DataChangeListener() {
    		public void dataChanged(DataChangeEvent e) {
    			dataChangeMethod(e);
    		}
    	};
    	prmtAimCost.addDataChangeListener(dataChangeListener);
    }
    
    private AimCostInfo  oriAimCost = null;
    /**
         * 目标成本更改事件
         * @param e
         */
        private void dataChangeMethod(DataChangeEvent e) {
    		if(oriAimCost==null){
    			oriAimCost =  (AimCostInfo) e.getOldValue();
    		}
    	
    		AimCostInfo oldInfo = (AimCostInfo) e.getOldValue();
    		AimCostInfo newInfo = (AimCostInfo) e.getNewValue();
    		if (oldInfo != null) {
    			if (newInfo == null) { // 清目标成本
    				if (kdtEntries.getRowCount() > 0) {
    					int i = FDCMsgBox.showConfirm2("是否清空目标成本,清除后将会把之前所规划的金额置为零！");
    					if (FDCMsgBox.OK == i) {
    						clearAimCost();
    						txtAimCoustVersion.setText(null);
    					}
    					if (FDCMsgBox.CANCEL == i) {
    						prmtAimCost.removeDataChangeListener(dataChangeListener);
    						prmtAimCost.setValue(oldInfo);
    						prmtAimCost.addDataChangeListener(dataChangeListener);
    					}
    				}
    			} else {
    				if (kdtEntries.getRowCount() > 0) {
    					if (oldInfo.getId().toString().equals(newInfo.getId().toString())) {
    						return;
    					} else {// 改目标成本
    						//by tim_gao 
    						if(isBillModify()&&newInfo!=null){
    							//得到新目标成本的成本分录,与对应经济条款
    					
    							Set costEntIds = new HashSet();
    							for(Iterator idIt = newInfo.getCostEntry().iterator();idIt.hasNext();){
    								costEntIds.add(((CostEntryInfo)idIt.next()).getId().toString());
    							}
    							EntityViewInfo view = new EntityViewInfo();
    							FilterInfo filter = new FilterInfo();
    							filter.getFilterItems().add(new FilterItemInfo("head.id",newInfo.getId().toString()));
    							SelectorItemCollection sels = new SelectorItemCollection();
    							sels.add("costAccount.id");
    							sels.add("costAccount.srcCostAccountId");
    							sels.add("costAmount");
    							view.setFilter(filter);
    							view.setSelector(sels);
    							CostEntryCollection costEntry  = null;
    							try {
    								costEntry  =CostEntryFactory.getRemoteInstance().getCostEntryCollection(view);
    							} catch (BOSException e1) {
    								// TODO Auto-generated catch block
    								FDCMsgBox.showWarning("加载分录信息失败!请自行新增添加 !");
    								SysUtil.abort();

    							}
//    							ProgrammingContractCollection cols = editData.getEntries();
    							ProgrammingContractCollection tempCols = new ProgrammingContractCollection();
    							for(Iterator it =  editData.getEntries().iterator();it.hasNext();){
    								ProgrammingContractInfo info = (ProgrammingContractInfo) it.next();
    								info.setId(null);
    								info.setProgramming(null);
    								tempCols.add(info);
    							}
    							 editData.getEntries().clear();
    							 editData.getEntries().addCollection(tempCols);
    							this.loadFields();
    							int sum = this.getDetailTable().getRowCount();
    							for(int i = 0 ; i <sum ; i++){
    								IRow row = this.getDetailTable().getRow(i);
    								row.getStyleAttributes().setBackground(Color.PINK);
    							}
    							prmtAimCost.removeDataChangeListener(dataChangeListener);
    							prmtAimCost.setValue(newInfo);
    							prmtAimCost.addDataChangeListener(dataChangeListener);
    							
    							//by tim_gao 修订操作时,去除IDs
    							for(int i = 0 ; i<kdtEntries.getRowCount() ; i++){
    								ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
    								
    								if(isBillModify()&&oriAimCost!=null&&
    										!(oriAimCost.getId().equals(((AimCostInfo)this.prmtAimCost.getValue()).getId()))){
    								//分录的	
    									ProgrammingContractEconomyCollection ecomyCols = rowObject.getEconomyEntries();
    									if(ecomyCols!=null&&ecomyCols.size()>0){
    										ecomyCols = (ProgrammingContractEconomyCollection)reLeaseID(ecomyCols,"contract");
    										
    										rowObject.getEconomyEntries().clear();
    										
    										rowObject.getEconomyEntries().addCollection(ecomyCols);
    									}
    									ProgrammingContracCostCollection costCols = rowObject.getCostEntries();
    									if(costCols!=null&&costCols.size()>0){
    									
    										costCols = (ProgrammingContracCostCollection)reLeaseIDEXChangeAmount(costCols,"contract",costEntry);
    										rowObject.getCostEntries().clear();
    										rowObject.getCostEntries().addCollection(costCols);
    										
    									}
    									
    								}
    							}
    						}
    						
    						
    					}
    				}
    			}
    		} else {
    			if (kdtEntries.getRowCount() > 0) {
    				int i = FDCMsgBox.showConfirm2("是否更改目标成本，更改后将会更改之前所规划的金额！");
    				if (FDCMsgBox.OK == i) {
    					changeAimCost(newInfo);
    				}
    				if (FDCMsgBox.CANCEL == i) {
    					prmtAimCost.removeDataChangeListener(dataChangeListener);
    					prmtAimCost.setValue(oldInfo);
    					prmtAimCost.addDataChangeListener(dataChangeListener);
    				}
    			}
    		}
    		
    		if(e.getNewValue() != null){
    			txtAimCoustVersion.setText(((AimCostInfo)e.getNewValue()).getVersionNumber());
    			String id = ((AimCostInfo) e.getNewValue()).getId().toString();
    			getCostAmount(id);
    		}else{
    			txtAimCoustVersion.setText(null);
    			txtAllAimCost.setValue(null);
    		}
    	}

	/**
	 * 获取目标成本的总金额
	 * 
	 * @param id
	 */
	private void getCostAmount(String id) {
		StringBuffer sql = new StringBuffer("select sum(fcostamount) as amount from T_AIM_CostEntry where fheadid ='" + id + "'");
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sql.toString());
		IRowSet rs;
		try {
			rs = sqlBuilder.executeQuery();

			if (rs.next()) {
				txtAllAimCost.setValue(rs.getBigDecimal("amount"));
			}
		} catch (BOSException e1) {
			logger.error(e1);
			handUIExceptionAndAbort(e1);
		} catch (SQLException e2) {
			logger.error(e2);
			handUIExceptionAndAbort(e2);
		}
	}

	/**
	 * 需要更改的值如下：
	 * 
	 * 界面所本的目标成本版本号清空
	 * 
	 * 成本构成 :目标成本，已分配，待分配，本合约分配 清0
	 * 
	 * 规划合约金额清0,控制金额清0,规划余额清0，控制余额清0
	 * 
	 * 经济条款：付款金额清0
	 */
	private void clearAimCost() {
		txtAimCoustVersion.setText(null);
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			ProgrammingContractEconomyCollection economyEntries = programmingContractInfo.getEconomyEntries();
			// 成本构成
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				// 目标成本，已分配，待分配，本合约分配 清0
				pccInfo.setGoalCost(FDCHelper.ZERO);
				pccInfo.setAssigned(FDCHelper.ZERO);
				pccInfo.setAssigning(FDCHelper.ZERO);
				pccInfo.setContractAssign(FDCHelper.ZERO);
			}
			// 规划合约金额清0,控制金额清0,规划余额清0，控制余额清0
			programmingContractInfo.setAmount(FDCHelper.ZERO);
			programmingContractInfo.setControlAmount(FDCHelper.ZERO);
			programmingContractInfo.setBalance(FDCHelper.ZERO);
			programmingContractInfo.setControlBalance(FDCHelper.ZERO);
			// 经济条款
			for (int k = 0; k < economyEntries.size(); k++) {
				ProgrammingContractEconomyInfo pceInfo = economyEntries.get(k);
				// 付款金额清0
				pceInfo.setAmount(FDCHelper.ZERO);
			}
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(i), programmingContractInfo);
			kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
		}
	}

	/**
	 * 改变目标成本或是加目标成本
	 * 
	 * 方案：
	 * 
	 * 1.更新各成本构成中"目标成本"、"待分配"的值
	 * 
	 * 新"目标成本"： 重新通过新关联的"目标成本"和各成本构成中"工程项目"、"成本科目"作为条件过滤出新的
	 * 
	 * 新"待分配":新"目标成本"-旧"已分配"
	 * 
	 * 2.更新后，把成本构中带有负"待分配"值所对应的合约在框架界面分录中用红色标志出来（整行用红色字体）
	 * 
	 * 3.分录中有红色标志的行时保存，提交按钮提示“不可保存…………”,即不可保存不可提交
	 * 
	 * 需要更改的值如下：
	 * 
	 * 界面所本的目标成本版本号
	 * 
	 * 成本构成 :目标成本，待分配例
	 * 
	 * 二期增加内容（2011.04.13）：
	 * 
	 * 若 原"本合约分配" = 原"目标成本" 则动态更新 "本合约分配" = 新"目标成本"，
	 * 
	 * @modified By Owen_wen
	 *           调用ProgrammingImportUI中的方法，使用与先导入目标成本再导入模板的效果一样，复用那边的代码
	 * 
	 */
    private void changeAimCost(AimCostInfo aimCost) {
		kdtEntries.getEditManager().editingStopped();

		try {
			ProgrammingImportUI importUI = new ProgrammingImportUI(this.editData, aimCost);
			importUI.computeAim();
			editData.setAimCost(aimCost);
			clearAndDisplay(editData.getEntries());
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}
    }
	/**
	 * 存在合同引用关系，把目标成本f7控件置灰
	 * 
	 * @return
	 */
	private boolean isCiting() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object value = kdtEntries.getCell(i, "isCiting").getValue();
			if (value instanceof Boolean) {
				Boolean b = (Boolean) value;
				if (b.booleanValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 计算建筑单方、可售单方
	 */
	private void calcSquare(int index) {
		BigDecimal totalBuildArea = txtBuildArea.getBigDecimalValue();
		BigDecimal sellArea = txtSaleArea.getBigDecimalValue();

		if (index == 0) {
			int size = this.kdtEntries.getRowCount();
			for (int i = 0; i < size; i++) {
				BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(i, "amount").getValue());
				if (amount.compareTo(FDCHelper.ZERO) != 0) {
					if (totalBuildArea != null && totalBuildArea.compareTo(FDCHelper.ZERO) != 0) {
						kdtEntries.getCell(i, "buildPerSquare").setValue(amount.divide(totalBuildArea, 4, BigDecimal.ROUND_HALF_UP));
					}
					if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
						kdtEntries.getCell(i, "soldPerSquare").setValue(amount.divide(sellArea, 4, BigDecimal.ROUND_HALF_UP));
					}
				} else {
					kdtEntries.getCell(i, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(i, "soldPerSquare").setValue(FDCHelper.ZERO);
				}
			}
		} else {
			BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(index, "amount").getValue());
			if (amount.compareTo(FDCHelper.ZERO) != 0) {
				if (totalBuildArea != null && totalBuildArea.compareTo(FDCHelper.ZERO) != 0) {
					kdtEntries.getCell(index, "buildPerSquare").setValue(amount.divide(totalBuildArea, 4, BigDecimal.ROUND_HALF_UP));
				}
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					kdtEntries.getCell(index, "soldPerSquare").setValue(amount.divide(sellArea, 4, BigDecimal.ROUND_HALF_UP));
				}
			} else {
				kdtEntries.getCell(index, "buildPerSquare").setValue(FDCHelper.ZERO);
				kdtEntries.getCell(index, "soldPerSquare").setValue(FDCHelper.ZERO);
			}
		}
	}
    
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			changeActoinState(false);
			actionImport.setEnabled(true);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			changeActoinState(false);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		}
	}
	
	/**
	 * 设置附件列显示格式
	 */
	private void setAttachmentRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					return "查看";
				}
				return null;
			}
		});
		this.kdtEntries.getColumn("attachment").setRenderer(objectValueRender);
	}
	//前一版本框架合约ID
	private List oldProgId = new ArrayList();
	private void initData() throws Exception {
		if (isBillModify()) {
			for(Iterator it = editData.getEntries().iterator() ; it.hasNext();){
				oldProgId.add(((ProgrammingContractInfo)it.next()).getId());
			}
			actionCopy_actionPerformed(null);
			int cou = 0;
			for(Iterator it = editData.getEntries().iterator(); it.hasNext();){
				((ProgrammingContractInfo)it.next()).setSrcId(oldProgId.get(cou).toString());
				cou++;
			}
			for (int i = 0, count = kdtEntries.getRowCount(); i < count; i++) {
				kdtEntries.getCell(i, "isCiting").setValue(Boolean.FALSE);
			}
			createTree();
			// setNameDisplay();
		}
		this.btnExport.setEnabled(true);
		Object node = getUIContext().get("treeSelectedObj");
    	if(node != null)
    		curProject = (CurProjectInfo)node;
		
		if(editData.getProject() == null){
			if(curProject != null){
				editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
			}
		}

		StringBuffer sql = new StringBuffer("select FID from T_AIM_MeasureCost where fprojectid ");
		sql.append(" = '" + editData.getProject().getId().toString() + "' and fstate = '4AUDITTED' ");
		sql.append(" order BY FMAINVERNO desc , FSUBVERNO desc");
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sql.toString());
		IRowSet rs = sqlBuilder.executeQuery();
		if (rs.next()) {
			String measureCostID = rs.getString("FID");
			if (measureCostID != null && measureCostID.length() > 1) {
				StringBuffer sql_2 = new StringBuffer("select a.FTotalBuildArea ,  isnull(b.FSellArea ,0) as FSellArea from T_AIM_PlanIndex as a ");
				sql_2.append(" left outer join T_AIM_PlanIndexEntry as b on a.FID = b.FParentID ");
				sql_2.append(" where a.FHEADID = '" + measureCostID + "'");
				sqlBuilder = new FDCSQLBuilder(sql_2.toString());
				IRowSet rs_2 = sqlBuilder.executeQuery();
				BigDecimal totalBuildArea = FDCHelper.ZERO;
				BigDecimal sellArea = FDCHelper.ZERO;
				while (rs_2.next()) {
					totalBuildArea = rs_2.getBigDecimal("FTotalBuildArea");
					sellArea = sellArea.add(rs_2.getBigDecimal("FSellArea"));
				}
				txtBuildArea.setValue(totalBuildArea);
				txtSaleArea.setValue(sellArea);
			}
		}
		
		// modified by zhaoqin for R131029-0240 on 2013/11/25
		// 走工作流时，取不到工程项目
		if(null == curProject && editData.getProject() != null){
			curProject = editData.getProject();
		}
    }
    
	//修订时置空字段值
    protected void setFieldsNull(AbstractObjectValue newData) {
    	ProgrammingInfo info = (ProgrammingInfo) newData;
    	String number = getDateString();
    	info.setNumber(number);
    	txtNumber.setText(number);
    	inputVersion(info);
    	info.setState(FDCBillStateEnum.SAVED);
    }
	
    /**
     * 设置分录失去焦点及分录按钮是否可用
     */
	private void setKDTableLostFocus(){
		kdtEntries.getEditManager().stopEditing();
		kdtEntries.getSelectManager().remove();
		kdtEntries.getSelectManager().setActiveRowIndex(-1);
		btnInsertLines.setEnabled(false);
		btnRemoveLines.setEnabled(false);
		btnDetails.setEnabled(false);
	}

	/**
	 * 目标成本过滤条件
	 */
	private void setAimCostFilter() {
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		// 先添加项目过滤，后期改为自动带出不可修改
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", editData.getProject().getId().toString()));
		entityView.setFilter(filter);
		prmtAimCost.setEntityViewInfo(entityView);
		if (prmtAimCost.getValue() != null) {
			txtAimCoustVersion.setText(((AimCostInfo) prmtAimCost.getValue()).getVersionNumber());
		}		
	}
    
	/**
	 * 加载时对表格进行设置
	 */
	private void initTable() {
		kdtEntries.getColumn("id").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("level").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("longName").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isCiting").getStyleAttributes().setLocked(true);
		// 规划余额、控制余额数字格式化两位小数
		cellToFormattedText(kdtEntries, "balance");
		cellToFormattedText(kdtEntries, "controlBalance");
		cellToFormattedText(kdtEntries , AMOUNT);
		cellToFormattedText(kdtEntries , CONTROLAMOUNT);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(1024);
		kdtEntries.getColumn("remark").setEditor(cellEditor);
//		kdtEntries.getColumn("headNumber").getStyleAttributes().setHided(false);
		
		kdtEntries.getColumn(AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn(CONTROLAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("balance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("controlBalance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		kdtEntries.getColumn("buildPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("soldPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		KDTDefaultCellEditor inviteEditor = new KDTDefaultCellEditor(new KDComboBox());
		KDComboBox kdCombox = (KDComboBox) inviteEditor.getComponent();
		kdCombox.addItem(InviteFormEnum.PUBLICINVITE);
		kdCombox.addItem(InviteFormEnum.INVITATORYINVITE);
		kdCombox.addItem(InviteFormEnum.OTHER);
		kdCombox.addItem(InviteFormEnum.TENDERDISCUSSION);
		kdCombox.setSelectedIndex(-1);
		kdtEntries.getColumn("inviteWay").setEditor(inviteEditor);

		createCostCentertF7();
	}

	private void cellToFormattedText(KDTable table, String column) {
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor.getComponent();
		kdftf.setDataType(1);
		kdftf.setPrecision(2);
		kdftf.setSupportedEmpty(true);
		table.getColumn(column).setEditor(cellEditor);
	}

	public void loadFields() {
		super.loadFields();
		//加载数据时按长编码排序
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		//单元格编码模式
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		kdtEntries.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		if (!OprtState.ADDNEW.equals(getOprtState())) {
			createTree();
			setNameDisplay();
			handleOldData();
			AimCostInfo aimCost = editData.getAimCost();
			if (aimCost != null && aimCost.getId() != null && aimCost.getId().toString().length() > 1)
				getCostAmount(editData.getAimCost().getId().toString());	
		}
		if (!OprtState.VIEW.equals(getOprtState())) {
			setCellEditorForTable();
		}
		setTableFontColor();
		cellAttachment();
		
		setTableRenderer();
		sumClass.caclTotalAmount(kdtEntries);
		setMyFontColor();
	}
	
	/**
	 * 设置金额字段显示格式
	 */
	private void setTableRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					if("0.00".equals(o.toString())){
						return "0";
					}
				}else{
					return "0";
				}
				return o.toString();
			}
		});
		kdtEntries.getColumn("controlAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("amount").setRenderer(objectValueRender);
		kdtEntries.getColumn("balance").setRenderer(objectValueRender);
		kdtEntries.getColumn("controlBalance").setRenderer(objectValueRender);
		kdtEntries.getColumn("signUpAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("changeAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("settleAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("buildPerSquare").setRenderer(objectValueRender);
		kdtEntries.getColumn("soldPerSquare").setRenderer(objectValueRender);	
	}
	
	private void cellAttachment() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object idObj = kdtEntries.getCell(i, "id").getValue();
			if (idObj != null) {
				String id = idObj.toString();
				StringBuffer allAttachmentName = getAllAttachmentName(id);
				if (!FDCHelper.isEmpty(allAttachmentName)) {
					kdtEntries.getCell(i, "attachment").setValue("存在附件");
				}
			}
		}
	}

	/**
	 * 获取合约框架所有附件名称字符串，名称与乐称以","相隔
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" SELECT DISTINCT boAt.FBoID, at.FID, at.FSimpleName, at.FName_l2, at.FCreateTime FROM T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		logger.info("sql:" + fdcBuilder.getSql().toString());
		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (FDCHelper.isEmpty(rs.getString("FSimpleName"))) {
					sb.append(rs.getString("FName_l2") + ";");
				} else {
					if (rs.isLast()) {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
					} else {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}
		return sb;
	}
	
	public void onShow() throws Exception {
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
		
		super.onShow();
		//禁用表头排序
		kdtEntries.addKDTMouseListener(new KDTSortManager(kdtEntries));
		kdtEntries.getSortMange().setSortAuto(false);
		
		setAuditBtnEnable();
		FDCTableHelper.generateFootRow(kdtEntries).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}
	
	/**
	 * 设置审批按钮显示
	 */
	private void setAuditBtnEnable(){
		if(editData.getState() == null){
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}else if(FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(false);
			actionSave.setEnabled(false);
		}else if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
		}
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		if (this.oprtState.equals(STATUS_EDIT)) {
			// boolean lastVersion = ((IProgramming) getBizInterface()).isLastVersion(
			// new ObjectUuidPK(editData.getId()));
			boolean isAudit = FDCBillStateEnum.AUDITTED.equals(editData.getState())
					|| FDCBillStateEnum.AUDITTING.equals(editData.getState());
			if (!isBillModify()) {
				actionAudit.setEnabled(!isAudit);
				actionUnAudit.setEnabled(isAudit);
				actionImport.setEnabled(!isAudit);
			}
		}
	}
	
	private void changeActoinState(boolean flag) {
		actionAudit.setEnabled(flag);
		actionUnAudit.setEnabled(flag);
		actionSubmit.setEnabled(flag);
		actionImport.setEnabled(flag);
	}
	
    /**
     * 在面签中添加新增、插入、删除、详细信息按钮
     */
    private void setSmallButton(){
    	btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
    	menuItemImport.setIcon(EASResource.getIcon("imgTbtn_input"));
    	btnExport.setIcon(EASResource.getIcon("imgTbtn_output"));
    	menuItemExport.setEnabled(true);
    	menuItemExport.setIcon(EASResource.getIcon("imgTbtn_output"));
    	btnRefresh.setEnabled(true);
    	menuItemRefresh.setEnabled(true);
    	btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
    	menuItemRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
    	btnAddnewLine = new KDWorkButton();
    	btnInsertLines = new KDWorkButton();
    	btnRemoveLines = new KDWorkButton();
    	btnDetails = new KDWorkButton();
    	
    	btnDetails.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    actionDetails_actionPerformed(e);
                }
                catch (Exception e1){
                	logger.error("detials" , e1);
                	handUIExceptionAndAbort(e1);
                }
            }});
    	
    	btnAddnewLine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                	actionAddnewLine_actionPerformed(e);
                }
                catch (Exception e1){
                	handUIExceptionAndAbort(e1);
                }
            }});
    	
    	btnInsertLines.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLines_actionPerformed(arg0);
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
				
			}
    		
    	});
    	
    	btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLines_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});
    	
    	setButtonStyle(btnAddnewLine,"新增行","imgTbtn_addline");
    	setButtonStyle(btnRemoveLines,"删除行","imgTbtn_deleteline");
    	setButtonStyle(btnInsertLines,"插入行","imgTbtn_insert");
    	setButtonStyle(btnDetails,"详细信息","imgTbtn_particular");
    }
    
    private void setButtionEnable(boolean isEnable){
    	btnAddnewLine.setEnabled(isEnable);
		btnInsertLines.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
    }
    
    //设置按钮显示效果
    private void setButtonStyle(KDWorkButton button , String text , String icon){
    	button.setText(text);
    	button.setToolTipText(text);
    	button.setVisible(true);
    	button.setIcon(EASResource.getIcon(icon));
    	conProgramming.addButton(button);
    }
    
    //设置新增、删除、插入分录按钮是否可用
    private void setSmallBtnEnable(){
    	if(OprtState.VIEW.equals(getOprtState())){
    		setButtionEnable(false);
    		if(kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0){
    			btnDetails.setEnabled(false);
    		}else{
    			btnDetails.setEnabled(true);
    		}
    	}else{
    		btnAddnewLine.setEnabled(true);
    		if(kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0){
    			btnInsertLines.setEnabled(false);
    			btnRemoveLines.setEnabled(false);
    			btnDetails.setEnabled(false);
    		}else{
    			btnInsertLines.setEnabled(true);
    			btnRemoveLines.setEnabled(true);
    			btnDetails.setEnabled(true);
    		}
    	}
    }
    
    /**
     * 点击新增行按钮
     * @param e
     * @throws Exception
     */
    public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntries.getRowCount();
    	int row = -1;
    	if(rowIndex < 0){
    		//下标为0则正常新增
    		create.addLine(kdtEntries, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
    		Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntries.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntries.getCell(rowIndex, "longName").getValue();
    		
    		ProgrammingContractInfo headObject = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
    		//新增时判断数据是否合法
    		if(o == null || o.toString().trim().length() == 0){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
    			return;
    		}else if((o.toString().trim()+".").length() >= 80){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码过长\n请修改后再新增子级框架合约！");
    			return;
    		}else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约名称不能为空！");
    			return;
    		}else if(head == null){
    			int level = new Integer(headlevel.toString()).intValue();
    			if(level == 1){
    				row = getInsertRowIndex(o , rowIndex , rowCount);
    				create.insertLine(kdtEntries , row , new Integer(headlevel.toString()).intValue() + 1 , headObject);
    				kdtEntries.getCell(row, HEADNUMBER).setValue(o);
    				if(name != null)
    					kdtEntries.getCell(row, "longName").setValue(name.toString().trim()+".");
    			}
    		}else{
    			String ln = o.toString();
    			
    			if(ln.length() == (head.toString().length() + 1)){
    				MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
        			return;
    			}
    			row = getInsertRowIndex(o , rowIndex , rowCount);
    			
    			create.insertLine(kdtEntries , row , new Integer(headlevel.toString()).intValue() + 1 , headObject);
    			kdtEntries.getCell(row, HEADNUMBER).setValue(o);
    			if(longName != null)
    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
    		}
    	}
    	//设置编码列的编辑格式、限制上级编码不可修改
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntries.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
		
		formatName(row);
		
    	createTree();

    	setSmallBtnEnable();
		createCostCentertF7();
	}

	private void formatName(int rowIndex) {
		KDTextField txtName = new KDTextField();
		txtName.setMaxLength(80);
		txtName.setDocument(new LimitedLengthDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("^\\.+$")) {
					return;
				}
				super.insertString(offs, str, a);
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);
			}
		});
		KDTDefaultCellEditor cellEditorName = new KDTDefaultCellEditor(txtName);
		kdtEntries.getCell(rowIndex, "name").setEditor(cellEditorName);
    }
    
    /**
     * 点击插入行按钮
     * @param e
     * @throws Exception
     */
    public void actionInsertLines_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	Object name = kdtEntries.getCell(rowIndex, "name").getValue();
    	if(o == null || o.toString().trim().length() == 0){
			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约编码不能为空！");
			return;
		}else if(name == null || StringUtils.isEmpty(name.toString())){
			MsgBox.showInfo("分录第 "+(rowIndex+1)+" 行，框架合约名称不能为空！");
			return;
		}
    	Object headNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
		Object level = kdtEntries.getCell(rowIndex, "level").getValue();
		ProgrammingContractInfo headObject = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
    	create.insertLine(kdtEntries , rowIndex , new Integer(level.toString()).intValue() , headObject.getParent());
		kdtEntries.getCell(rowIndex, HEADNUMBER).setValue(headNumber);
		if(headObject.getParent() != null && headObject.getParent().getDisplayName() != null){
			kdtEntries.getCell(rowIndex, "longName").setValue(headObject.getParent().getDisplayName().toString().trim()+".");
		}
		KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntries.getCell(rowIndex, LONGNUMBER).setEditor(cellEditorNumber);
		formatName(rowIndex);
		createTree();
    }

    /**
     * 点击删除行按钮
     * @param e
     * @throws Exception
     */
    public void actionRemoveLines_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = this.kdtEntries.getSelectManager().getActiveRowIndex();
    	Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	Object h = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	
    	boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue();
    	if(isCiting){
    		MsgBox.showInfo("存在被引用的框架合约“"+longNumber.toString()+"”,无法删除！");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	if(longNumber != null){
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString() , rowIndex , list);
    	}
    	
    	int oldLevel = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		boolean isHasSomeLevel = false;// 默认没有同级节点
		int someIndex = -1;// 有同级节点时对应下标
		int SomeLevel = 0;// 有同级节点时对应级数
		if (h != null) {
			for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
				if (i == rowIndex) {
					continue;
				}
				Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
				if (l_2 != null) {
					if (h.toString().equals(l_2.toString())) {
						isHasSomeLevel = true;
						someIndex = i;
						SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
						break;
					}
				}
			}
		}
    	
		if(list.size() > 1){
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"您当前删除的父节点“"+longNumber.toString()+"”下还有其他的框架合约，确定要一起删除吗？")){
				create.removeLine(kdtEntries, list);
				if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "balance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlBalance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "buildPerSquare").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "soldPerSquare").setValue(FDCHelper.ZERO);
					}
				} else if (isHasSomeLevel && oldLevel == 2) {
					if (h != null) {
						for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
							Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
							if (l_2 != null) {
								if (h.toString().equals(l_2.toString())) {
									someIndex = i;
									SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
									break;
								}
							}
						}
					}
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
		}else{
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"是否确认删除数据？")){
		    	create.removeLine(kdtEntries,rowIndex);
		    	if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "balance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlBalance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "buildPerSquare").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "soldPerSquare").setValue(FDCHelper.ZERO);
					}
				} else if (isHasSomeLevel && oldLevel == 2) {
					if (h != null) {
						for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
							Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
							if (l_2 != null) {
								if (h.toString().equals(l_2.toString())) {
									someIndex = i;
									SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
									break;
								}
							}
						}
					}
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
    	}
		
		if (h != null) {
			for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
				Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
				if (l_2 != null) {
					if (h.toString().equals(l_2.toString())) {
						someIndex = i;
						SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
						break;
					}
				}
			}
		}
		
		//删除时需要重新对金额进行汇总
		int loop = getLoop(rowIndex, h);
		if (loop > 0) {
			int level = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			ProgrammingContractInfo proConInfo = (ProgrammingContractInfo) kdtEntries.getRow(loop).getUserObject();
			ProgrammingContracCostCollection costEntries = proConInfo.getCostEntries();
			if (costEntries.size() == 0) {
				if (!isHasSomeLevel) {
					kdtEntries.getCell(loop, "amount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlAmount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "balance").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlBalance").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "soldPerSquare").setValue(FDCHelper.ZERO);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("balance"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlBalance"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
				} else {
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("balance"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("controlBalance"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
			}
		}
		sumClass.caclTotalAmount(kdtEntries);
		sumClass.appendProFootRow(null,null);
		setSmallBtnEnable();
		dataBinder.storeFields();
		createTree();
    }
	private int getLoop(int rowIndex, Object h) {
		int loop = 0;
		boolean isHasSame = false;
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			if (i == rowIndex)
				break;
			Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
			if (l_2 != null) {
				if (h.toString().equals(l_2.toString())) {
					loop = i;
					isHasSame = true;
					break;
				}
			}
		}
		
		if(!isHasSame){
			if(rowIndex == kdtEntries.getRowCount()){
				loop = rowIndex - 1 ;
			}else{
				loop = rowIndex - 1;
			}
		}
		return loop;
	}
    

	/**
     * @author tim_gao
     * @param cols
     * @param s
     * @return
     */
	private AbstractObjectCollection reLeaseID(AbstractObjectCollection cols,String s){
		if(cols==null||!(cols.size()>0)){
			return null;
		}
		AbstractObjectCollection temp = (AbstractObjectCollection) cols.clone();
	
		for(Iterator it = cols.iterator();it.hasNext();){
			CoreBaseInfo obj = (CoreBaseInfo) it.next();
			obj.setId(null);
			obj.setString(s, null);
			temp.addObject(obj);
		}
		cols.clear();
		return temp;
	}
	
	private AbstractObjectCollection reLeaseIDEXChangeAmount(AbstractObjectCollection cols,String s,AbstractObjectCollection tempAcc){
		if(cols==null||!(cols.size()>0)){
			return null;
		}
		AbstractObjectCollection temp = (AbstractObjectCollection) cols.clone();
		temp.clear();
		
		boolean ismatched = false;
		BigDecimal amount = FDCHelper.ZERO;
		for(Iterator it = cols.iterator();it.hasNext();){
			
			ismatched = false;
			amount = FDCHelper.ZERO;
			ProgrammingContracCostInfo costInfo = (ProgrammingContracCostInfo) it.next();

			//循环累加成本科目的目标成本
			for (Iterator itAcc = tempAcc.iterator(); itAcc.hasNext();) {

				CostEntryInfo costent = (CostEntryInfo) itAcc.next();
				if (costent != null
						&& costent.getCostAccount().getSrcCostAccountId() != null
						&& costent.getCostAccount().getSrcCostAccountId().toString().equals(
								costInfo.getCostAccount().getSrcCostAccountId().toString())) {

					amount = amount.add(costent.getCostAmount() == null ? FDCHelper.ZERO : costent.getCostAmount());
					ismatched = true;
				}
				
			}
			//目标成本
			costInfo.setGoalCost(amount);
			//待分配为目标成本-已分配
			costInfo.setAssigning(FDCHelper.subtract(amount, costInfo.getAssigned()));

			if (!ismatched) {
				//新版本不存在的
				costInfo.setGoalCost(FDCHelper.ZERO);
				costInfo.setAssigned(FDCHelper.ZERO);
				costInfo.setAssigning(FDCHelper.ZERO);
			}
			costInfo.setId(null);
			costInfo.setString(s, null);
			temp.addObject(costInfo);
		}
		cols.clear();
		return temp;
	}

	
	/**
	 * 点击详细信息，弹出分录单据
	 * 
	 * @param e
	 * @throws Exception
	 */
    public void actionDetails_actionPerformed(ActionEvent e) throws Exception{

		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		this.kdtEntries.getEditManager().editingStopped();
		Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (rowIndex == -1) {
			FDCMsgBox.showInfo("请选择行");
			return;
		}
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("treeSelectedObj");
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		uiContext.put("programmingContract", rowObject);// 规划合约
		uiContext.put("pcCollection", pcCollection);// 规划合约集合
		uiContext.put("project", project);// 工程项目
		uiContext.put("aimCostInfo", aimCostInfo);// 目标成本

		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null,
				oprtState);
		uiWindow.show();
		// 绑定数据到分录上
		dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
		// 更新长名称
		setEntriesNameCol(rowIndex, level);
		// 更新长编码
		Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldLongNumber != null && newLongNumber != null) {
			if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
				setEntriesNumberCol(rowIndex, level);
			}
		}
		calcSquare(rowIndex);
		// 更新规划金额,规划余额,控制金额，控制余额的汇总
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("buildPerSquare"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("soldPerSquare"), level);
		// 重新判断行是否带有错误数据，错的则字体用红颜色，对的则字体用黑颜色
		setMyFontColor();
	}

	/**
	 * 重新判断行是否带有错误数据，错的则字体用红颜色，对的则字体用黑颜色
	 * @param rowIndex
	 */
	private void setMyFontColor() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			boolean flag = false;// false默认表示没有错误数据
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection pccCollection = programmingContractInfo.getCostEntries();
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);
				BigDecimal assigning = pccInfo.getAssigning();// 待分配
				BigDecimal contractAssign = pccInfo.getContractAssign();// 本合约分配
				// 若"本合约分配">"待分配"，表示有错误数据
				if (contractAssign.compareTo(assigning) > 0) {
					flag = true;
				}
			}
			if (flag) {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.RED);
			} else {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
				Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
				if (blanceValue != null) {
					BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
					if (blance.compareTo(FDCHelper.ZERO) > 0) {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.green);
					} else {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
					}
				}
			}

			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			if (blanceValue != null) {
				BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
				if (blance.compareTo(FDCHelper.ZERO) == 0) {
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.black);
				}
			}
		}
	}
    
	/**
	 * 获取所选行的所有子级节点、并判断是否有被引用的框架合约
	 * @param longNumber
	 * @param rowIndex
	 * @param list
	 */
    private void getSublevel(String longNumber , int rowIndex , ArrayList list){
    	int rowCount = kdtEntries.getRowCount();
    	for(int i = rowIndex+1 ; i < rowCount ; i++){
			Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
	    	boolean isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue();
	    	if(isCiting){
	    		MsgBox.showInfo("存在被引用的框架合约“"+l.toString()+"”,无法删除！");
	    		SysUtil.abort();
	    	}
			if(headNumber != null && headNumber.toString() != null){
				if(headNumber.toString().startsWith(longNumber)){
					list.add(new Integer(i));
				}else{
					break;
				}
			}
    	}
    }
    
    /**
     * 加载时生成树形
     */
    private void createTree(){
		int maxLevel = 0;
		int[] levelArray = new int[kdtEntries.getRowCount()];
		
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtEntries.getTreeColumn().setDepth(maxLevel);
		
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.repaint();
    }
    
    /**
     * 获取要新增行的位置（下标）
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    protected int getInsertRowIndex(Object o , int rowIndex , int rowCount){
    	int row = 0;
    	String longNumber = o.toString();
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
    	
		for(int i = rowIndex+1 ; i < rowCount ; i++){
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 == level || level_2 < level) {
				return i;
			}
			Object n = kdtEntries.getCell(i, LONGNUMBER).getValue();
			if(n != null && n.toString() != null){
				if(!n.toString().startsWith(longNumber)){
					row = i;
					break;
				}
			}else{
				return i+1;
			}
			
			if(rowIndex + 2 == rowCount){
				return rowCount;
			}
		}
		return row == 0 ? rowCount : row;
    }
    
    protected void kdtEntries_editStarting(KDTEditEvent e) throws Exception {
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		Object longNumber = kdtEntries.getCell(e.getRowIndex(), LONGNUMBER).getValue();
			if(longNumber != null && longNumber.toString().trim().length() > 80){
				MsgBox.showInfo("分录第 "+(e.getRowIndex()+1)+" 行，框架合约编码超长\n请修改上级编码后再进行编辑！");
				kdtEntries.getEditManager().cancelEditing();
				e.setCancel(true);
			}
    	}
    }
    
    protected void kdtEntries_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	setSmallBtnEnable();
    }
    
    protected void kdtEntries_editStarted(KDTEditEvent e) throws Exception {
    	//如果编辑的是编码列则需求获取编码列的编辑器、控制上级编码不可以被删除、修改
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    		ICellEditor editor = kdtEntries.getCell(rowIndex, LONGNUMBER).getEditor();
    		if(editor != null){
    			if(editor instanceof KDTDefaultCellEditor){
		    		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    	    		KDTextField txtLongNumber = (KDTextField) de.getComponent();
    	    		LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
    	    		txtLongNumber.setMaxLength(80);
    	    		String txt = "";
    	    		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	    		Object subNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	    		if(longNumber == null || longNumber.toString().trim().length() == 0){
    	    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    	    				txt = subNumber.toString().trim()+".";
    	    				kdtEntries.getCell(rowIndex, LONGNUMBER).setValue(txt);
    	    			}
    	    		}
    	    		else{
    	    			txt = longNumber.toString().trim();
    	    		}
    	    		if(level > 1){
    	    			doc.setLimitedText(subNumber.toString().trim()+".");
    	    			doc.setIsOnload(true);
    	    			doc.setIsAutoUpdate(true);
    	    			txtLongNumber.setText(txt);
    	    			doc.setIsOnload(false);
    	    			doc.setIsAutoUpdate(false);
    	    		}else{
    					doc.setIsAutoUpdate(true);
    					doc.setIsOnload(true);
    					txtLongNumber.setText(txt);
    					doc.setIsAutoUpdate(false);
    					doc.setIsOnload(false);
    	    		}
    			}
    		}
    	}
    }
    
    protected void kdtEntries_editStopped(KDTEditEvent e) throws Exception {
    	//设置编码
    	Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue == null && newValue == null){
			return;
		}
		this.dataBinder.storeFields();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		int colIndex = e.getColIndex();
		
    	if(colIndex == kdtEntries.getColumnIndex(LONGNUMBER)){
    		if(oldValue != null && newValue != null){
    			if(oldValue.equals(newValue)){
    				return;
    			}
    		}
    		setEntriesNumberCol(rowIndex, level);
    	}
    	
    	if(colIndex == kdtEntries.getColumnIndex("name")){
    		setEntriesNameCol(rowIndex, level);
    	}
    	
		// 判断规划金额是否大于已发生金额或控制金额
    	if (colIndex == kdtEntries.getColumnIndex("amount")) {
			Object newAmountObj = e.getValue();// 新规划金额
			Object oldAmountObj = e.getOldValue();// 原规划金额
			Object oldControlAmountObj = kdtEntries.getCell(rowIndex, "controlAmount").getValue();
			Object oldBalanceObj = kdtEntries.getCell(rowIndex, "balance").getValue();
			BigDecimal oldAmount = FDCHelper.toBigDecimal(oldAmountObj);
			BigDecimal newAmount = FDCHelper.toBigDecimal(newAmountObj);
			BigDecimal oldBalance = FDCHelper.toBigDecimal(oldBalanceObj);
			BigDecimal oldControlAmount = FDCHelper.toBigDecimal(oldControlAmountObj);
			
			// 已发生金额
			BigDecimal occurredAmount = oldAmount.subtract(oldBalance);
			if (newAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("第 ").append(rowIndex + 1).append("行 \n");
				msg.append("规划金额 不能小于 控制金额");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			}
			Boolean isCiting = (Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue();
			if (Boolean.FALSE == isCiting) {
				return;
			}
			
			if (newAmount.compareTo(occurredAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("第 ").append(rowIndex + 1).append("行 \n");
				msg.append("规划金额 不能小于 发生金额");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			}
			
			calcSquare(e.getRowIndex());
			setMyFontColor();
    	}
		// 判断规划金额是否大于已发生金额或控制金额2
    	if (colIndex == kdtEntries.getColumnIndex("controlAmount")) {
			Object newControlAmountObj = e.getValue();// 新控制金额
			Object oldControlAmountObj = e.getOldValue();// 原控制金额
			Object oldAmountObj = kdtEntries.getCell(rowIndex, "amount").getValue();
			BigDecimal oldControlAmount= oldControlAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldControlAmountObj);
			BigDecimal newControlAmount = newControlAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(newControlAmountObj);
			BigDecimal oldAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldAmountObj);
			if (oldAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("第 ").append(rowIndex + 1).append("行 \n");
				msg.append("规划金额 不能小于 控制金额");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "controlAmount").setValue(oldControlAmount);
				return;
			}
			Boolean isCiting = (Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue();
			if (Boolean.FALSE == isCiting) {
				return;
			}
			if (oldAmount.compareTo(newControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("第 ").append(rowIndex + 1).append("行 \n");
				msg.append("规划金额 不能小于 控制金额");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "controlAmount").setValue(oldControlAmount);
				return;
			} 
			
			setMyFontColor();
    	}
    	if (colIndex == kdtEntries.getColumnIndex("amount") || 
    			colIndex == kdtEntries.getColumnIndex("controlAmount")){
    		if(oldValue != null && newValue != null){
				if ((new BigDecimal(oldValue.toString())).compareTo(new BigDecimal(newValue.toString())) == 0) {
    				return;
    			}
    		}
			// 更新规划余额和控制余额
			if (colIndex == kdtEntries.getColumnIndex("amount")) {
				if (oldValue != null && newValue != null) {
					Object balanceObj = kdtEntries.getCell(rowIndex, "balance").getValue();
					if (balanceObj != null) {
						BigDecimal balance = FDCHelper.toBigDecimal(balanceObj);
						kdtEntries.getCell(rowIndex, "balance").setValue(
								balance.add((FDCHelper.toBigDecimal(newValue).subtract(FDCHelper.toBigDecimal(oldValue)))));
					}
				}
				if (level != 1) {
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("balance"), level);
				}
			}

			if (colIndex == kdtEntries.getColumnIndex("controlAmount")) {
				if (oldValue != null && newValue != null) {
					Object controlBalanceObj = kdtEntries.getCell(rowIndex, "controlBalance").getValue();
					if (controlBalanceObj != null) {
						BigDecimal controlBalance = FDCHelper.toBigDecimal(controlBalanceObj);
						kdtEntries.getCell(rowIndex, "controlBalance").setValue(
								controlBalance.add((FDCHelper.toBigDecimal(newValue).subtract(FDCHelper.toBigDecimal(oldValue)))));
					}
				}
				if (level != 1) {
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("controlBalance"), level);
				}
			}
			caclTotalAmount(e.getRowIndex(), e.getColIndex(), level);
    	}
		// 设置规划余额颜色
		if (colIndex == kdtEntries.getColumnIndex(BALANCE)) {
			Object blanceValue = kdtEntries.getCell(rowIndex, BALANCE).getValue();
			if (oldValue != null && newValue != null) {
				BigDecimal newV = new BigDecimal(newValue.toString());;
				BigDecimal oldV = new BigDecimal(oldValue.toString());
				if (newV.compareTo(oldV) == 0) {
					return;
				}
			}
			if (blanceValue != null) {
				BigDecimal blance = new BigDecimal(blanceValue.toString());
				if (blance.compareTo(FDCHelper.ZERO) > 0) {
					kdtEntries.getCell(rowIndex, BALANCE).getStyleAttributes().setFontColor(Color.green);
				} else {
					kdtEntries.getCell(rowIndex, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
		}
    }

	/**
	 * 金额类字段在修改时自动向上汇总
	 * 
	 * @param index
	 * @param colIndex
	 * @param level
	 */
    private void caclTotalAmount(int index , int colIndex , int level) {
		if(level == 1){
			return;
		}
		int loop = index;
		int loopLevel = level;
		while(loop >= 0){
			int parentIndex = 0;
			BigDecimal dbSum = FDCHelper.ZERO;
			int curLevel = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			if(curLevel > loopLevel){
				loop--;
				continue;
			}
			String parentNumber = "";
			if(curLevel == 1){
				parentNumber = kdtEntries.getCell(loop, LONGNUMBER).getValue().toString();
			}else{
				parentNumber = kdtEntries.getCell(loop, HEADNUMBER).getValue().toString();
			}
			dbSum = FDCHelper.ZERO;
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
				Object h = kdtEntries.getCell(i, HEADNUMBER).getValue();
				int cacl_Level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				String number = "";
				//判断要汇总的下标值
				if (l != null) {
					if (parentNumber.equals(l.toString())) {
						parentIndex = i;
					}
					number = l.toString();
				}else if(h != null){
					if (parentNumber.equals(h.toString())) {
						for (int j = 0; j < kdtEntries.getRowCount(); j++) {
							if(j == i)
								continue;
							int j_Level = new Integer(kdtEntries.getCell(j, "level").getValue().toString()).intValue();
							if(cacl_Level == j_Level){
								Object l_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
								if(l_2 != null){
									if(h.toString().equals(l_2.toString())){
										parentIndex = j;
									}
								}
							}
						}
					}
					number = h.toString();
				}
				//计算汇总值
				if (loopLevel == cacl_Level && number.startsWith(parentNumber)) {
					ICell cell = kdtEntries.getRow(i).getCell(colIndex);
					String cellValue = kdtEntries.getCellDisplayText(cell);
					if (cellValue != null)
						cellValue = cellValue.toString().replaceAll(",", "");

					if (!StringUtility.isNumber(cellValue)) {
						Object cellObj = cell.getValue();
						if (cellObj != null)
							cellValue = cellObj.toString();
						if (!StringUtility.isNumber(cellValue))
							continue;
					}
					BigDecimal bigdem = new BigDecimal(String.valueOf(cellValue).trim());
					dbSum = dbSum.add(bigdem);
				}
			}
			String strSum = null;
			if(!(parentIndex == kdtEntries.getRowCount() - 1)){
				strSum = dbSum.toString();
				kdtEntries.getCell(parentIndex, colIndex).setValue(strSum);
				calcSquare(parentIndex);
			}
			loop--;
			loopLevel--;
		}
	}
    
	private String attachMentTempID = null;
    protected void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		if (e.getClickCount() < 2 || e.getType() == KDTStyleConstants.HEAD_ROW || e.getColIndex() == 0) {
			return;
		}
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("treeSelectedObj");
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		uiContext.put("programmingContract", rowObject);// 规划合约
		uiContext.put("pcCollection", pcCollection);// 规划合约集合
		uiContext.put("project", project);// 工程项目
		uiContext.put("aimCostInfo", aimCostInfo);// 目标成本

		// 双击编辑附件
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntries.getColumnIndex("attachment")) {
			boolean isEdit = false;// 默认为查看状态
			// if (oprtState.equals(OprtState.ADDNEW) || oprtState.equals(OprtState.EDIT)) {
			// isEdit = true;
			// }
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			AttachmentUIContextInfo info = getAttacheInfo();
			if (info == null) {
				info = new AttachmentUIContextInfo();
			}
			if (FDCHelper.isEmpty(info.getBoID())) {
				String boID = rowObject.getId().toString();
				if (boID == null) {
					if (!isEdit) {
						if (attachMentTempID == null) {
							boID = acm.getAttID().toString();
							attachMentTempID = boID;
						} else {
							boID = attachMentTempID;
						}
					} else {
						return;
					}
				}
				info.setBoID(boID);
				acm.showAttachmentListUIByBoID(boID, this, isEdit);
				Object idObj = kdtEntries.getCell(rowIndex, "id").getValue();
				StringBuffer allAttachmentName = getAllAttachmentName(idObj.toString());
				if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
					kdtEntries.getCell(rowIndex, "attachment").setValue("存在附件");
				} else {
					kdtEntries.getCell(rowIndex, "attachment").setValue(null);
				}
			}
			SysUtil.abort();
		}


		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null,
					oprtState);
			uiWindow.show();
			// 绑定数据到分录上
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
			// 更新长名称
			setEntriesNameCol(rowIndex, level);
			// 更新长编码
			Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldLongNumber != null && newLongNumber != null) {
				if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
					setEntriesNumberCol(rowIndex, level);
				}
			}
			// 更新规划金额,规划余额,控制金额，控制余额的汇总
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), level);
			calcSquare(rowIndex);
			// 重新判断行颜色
			setMyFontColor();
		}
    }

	/**
	 * 获取有框架所有合约
	 * 
	 * @return
	 */
	private ProgrammingContractCollection getPCCollection() {
		ProgrammingContractCollection pcCollection = new ProgrammingContractCollection();
		ProgrammingContractInfo pcInfo = null;
		int columnCount = kdtEntries.getRowCount();
		for (int i = 0; i < columnCount; i++) {
			pcInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			setContractToEditData(i, pcInfo);
			pcCollection.add(pcInfo);
		}
		return pcCollection;
	}
	
	/**
	 * 编码列更改更新数据、更新下级节点编码
	 * @param rowIndex
	 * @param level
	 */
    private void setEntriesNumberCol(int rowIndex, int level) {
		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if(longNumber != null && longNumber.toString().trim().length() > 0){
			String lnumber = longNumber.toString();
			if(level == 1){
				kdtEntries.getCell(rowIndex, "number").setValue(lnumber);
			}else{
				String number = lnumber.substring(lnumber.lastIndexOf(".")+1 , lnumber.length());
				kdtEntries.getCell(rowIndex, "number").setValue(number);
			}
			for(int i = rowIndex+1 ; i < kdtEntries.getRowCount() ; i++){
				Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
				Object longNumber_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				
				String[] editString = lnumber.split("\\.");
				if(longNumber_2 != null && longNumber_2.toString().trim().length() > 0){
					String hNumber_2 = headNumber.toString();
					String lNumber_2 = longNumber_2.toString();
					String[] newL = lNumber_2.split("\\.");
					String[] newH = hNumber_2.split("\\.");
					for(int j = 0 ; j < editString.length ; j++){
						newL[j] = editString[j];newH[j] = editString[j];
					}
					StringBuffer str = new StringBuffer();
					for(int j = 0 ; j < newL.length ; j++){
						str.append(newL[j]).append(".");
					}
					if(newL.length < level_2)
						str.append(".");
					StringBuffer str2 = new StringBuffer();
					for(int j = 0 ; j < newH.length ; j++){
						str2.append(newH[j]).append(".");
					}
					setkdtEntriesNumber(i , str.substring(0, str.length() - 1) , str2.substring(0, str2.length() - 1));
				}
			}
		}
	}

    /**
     * 更改名称时调用、更改子节点长名称
     * @param rowIndex
     * @param level
     */
	private void setEntriesNameCol(int rowIndex, int level) {
		Object name =  kdtEntries.getCell(rowIndex , "name").getValue();
		if(name != null && name.toString().trim().length() > 0){
			String nameStr = name.toString().trim();
			String blank = setNameIndent(level);
			kdtEntries.getCell(rowIndex ,"name").setValue(blank+nameStr);
			if(level == 1){
				kdtEntries.getCell(rowIndex ,"longName").setValue(nameStr);
			}else{
				Object lo = kdtEntries.getCell(rowIndex ,"longName").getValue();
				String displayName = lo == null ? "" : lo.toString();
				String ln = displayName.substring(0 , displayName.lastIndexOf("."))+".";
				kdtEntries.getCell(rowIndex ,"longName").setValue(ln + nameStr);
			}
			
			Object lo = kdtEntries.getCell(rowIndex ,"longName").getValue();
			String displayName = lo == null ? "" : lo.toString();
			if(level == 1){
				displayName = displayName+".";
			}
			String [] l = displayName.split("\\.");
			for(int i = rowIndex+1 ; i < kdtEntries.getRowCount() ; i++){
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				Object l2 = kdtEntries.getCell(i ,"longName").getValue();
				String l3[] = l2.toString().split("\\.");
				for(int j = 0 ; j < l.length ; j++){
					if(l3[j] != null && l3[j].length() > 0){
						l3[j] = l[j];
					}
				}
				StringBuffer str = new StringBuffer();
				for(int j = 0 ; j < l3.length ; j++){
					str.append(l3[j]).append(".");
				}
				Object n2 = kdtEntries.getCell(i ,"name").getValue();
				if(n2 == null){
					str.append(".");
				}
				kdtEntries.getCell(i ,"longName").setValue(str.substring(0, str.length() - 1));
				displayName = null;
			}
		}
	}
	
	/**
	 * 修改下级编码、并重新加载编码器
	 * @param i
	 * @param lnumber
	 * @param hNumber
	 */
	private void setkdtEntriesNumber(int i, String lnumber, String hNumber) {
		kdtEntries.getCell(i, HEADNUMBER).setValue(hNumber);
		kdtEntries.getCell(i, LONGNUMBER).setValue(lnumber);	
		
		ICellEditor editor = kdtEntries.getCell(i, LONGNUMBER).getEditor();
		if(editor != null){
			if(editor instanceof KDTDefaultCellEditor){
	    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    			KDTextField txtLongNumber = (KDTextField) de.getComponent();
    			LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
    			doc.setIsAutoUpdate(true);
    			doc.setIsOnload(true);
	    		txtLongNumber.setText(lnumber);
	    		doc.setIsAutoUpdate(false);
	    		doc.setIsOnload(false);
			}
		}
	}
	
	/**
	 * 为分录设置编辑器
	 */
	private void setCellEditorForTable(){
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			KDTextField txtLongNumber = new KDTextField();
			String txt = "";
			Object longNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
			Object subNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if(longNumber == null || longNumber.toString().trim().length() == 0){
    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    				txt = subNumber.toString().trim()+".";
    			}
			}
			else{
				txt = longNumber.toString().trim();
			}
			if(level > 1){
				LimitedTextDocument document = new LimitedTextDocument(subNumber.toString().trim()+"." , true);
				txtLongNumber.setMaxLength(80);
				txtLongNumber.setDocument(document);
				txtLongNumber.setText(txt);
				document.setIsOnload(false);
			}else{
				LimitedTextDocument document = new LimitedTextDocument("");
				txtLongNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtLongNumber.setText(txt);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
			
			KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
			kdtEntries.getCell(i , LONGNUMBER).setEditor(cellEditorNumber);
			String name = (String) kdtEntries.getCell(i, "name").getValue();
			formatName(i);
			kdtEntries.getCell(i, "name").setValue(name);
		}
    }
	
	private void setTableFontColor(){
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			if(blanceValue != null){
				BigDecimal blance = (BigDecimal)blanceValue;
				if(blance.compareTo(FDCHelper.ZERO) > 0){
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.green);
				}else{
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
		}
	}

	/**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * 设置合约单据头信息
	 * 
	 * @param rowIndex
	 * @param rowObject
	 */
	private void setContractToEditData(int rowIndex, ProgrammingContractInfo rowObject) {
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		rowObject.setLevel(level);
		if (level > 1) {
			Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (FDCHelper.isEmpty(longNumber)) {
				String subNumber = (String) kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
				if (!FDCHelper.isEmpty(subNumber)) {
					rowObject.setLongNumber(subNumber.toString().trim() + ".");// 长编码
				}
			} else {
				rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// 长编码
			}
		} else {
			rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// 长编码
		}
		rowObject.setDisplayName((String) kdtEntries.getCell(rowIndex, "longName").getValue());// 长名称
		rowObject.setNumber((String) kdtEntries.getCell(rowIndex, "number").getValue());// 编码
		rowObject.setName((String) kdtEntries.getCell(rowIndex, "name").getValue());// 名称

		rowObject.setWorkContent((String) kdtEntries.getCell(rowIndex, "workContent").getValue());// 工作内容
		rowObject.setSupMaterial((String) kdtEntries.getCell(rowIndex, "supMaterial").getValue());// 甲供及甲指材设
		rowObject.setInviteWay((InviteFormEnum) kdtEntries.getCell(rowIndex, "inviteWay").getValue());// 招标方式
		if (!FDCHelper.isEmpty(kdtEntries.getCell(rowIndex, "inviteOrg").getValue())) {
			Object value = kdtEntries.getCell(rowIndex, "inviteOrg").getValue();
			rowObject.setInviteOrg((CostCenterOrgUnitInfo) value);// 招标组织
		}
		
		Object amount = kdtEntries.getCell(rowIndex, AMOUNT).getValue();
		if (!FDCHelper.isEmpty(amount)) {
			rowObject.setAmount(new BigDecimal(amount.toString()));// 规划金额
		}
		Object controlAmount = kdtEntries.getCell(rowIndex, CONTROLAMOUNT).getValue();
		if (!FDCHelper.isEmpty(controlAmount)) {
			rowObject.setControlAmount(new BigDecimal(controlAmount.toString())); // 控制金额
		}
		Object signUpAmount = kdtEntries.getCell(rowIndex, "signUpAmount").getValue();
		if (!FDCHelper.isEmpty(signUpAmount)) {
			rowObject.setSignUpAmount(new BigDecimal(signUpAmount.toString())); // 签约金额
		}
		Object changeAmount = kdtEntries.getCell(rowIndex, "changeAmount").getValue();
		if (!FDCHelper.isEmpty(changeAmount)) {
			rowObject.setChangeAmount(new BigDecimal(changeAmount.toString())); // 变更金额
		}
		Object settleAmount = kdtEntries.getCell(rowIndex, "settleAmount").getValue();
		if (!FDCHelper.isEmpty(settleAmount)) {
			rowObject.setSettleAmount(new BigDecimal(settleAmount.toString())); // 结算金额
		}
		Object balance = kdtEntries.getCell(rowIndex, "balance").getValue();
		if (!FDCHelper.isEmpty(balance)) {
			rowObject.setBalance(new BigDecimal(balance.toString())); // 规划余额
		}
		Object controlBalance = kdtEntries.getCell(rowIndex, "controlBalance").getValue();
		if (!FDCHelper.isEmpty(controlBalance)) {
			rowObject.setControlBalance(new BigDecimal(controlBalance.toString())); // 控制余额
		}
		Object citeVersionObj = kdtEntries.getCell(rowIndex, "citeVersion").getValue();
		if (!FDCHelper.isEmpty(citeVersionObj)) {
			rowObject.setCiteVersion(new Integer(kdtEntries.getCell(rowIndex, "citeVersion").getValue().toString()).intValue());// 引用版本
		}

		rowObject.setDescription((String) kdtEntries.getCell(rowIndex, "remark").getValue());// 备注
	}

	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}
    
	public void inputVersion(ProgrammingInfo info) {
		if (info == null) {
			return;
		}
		if (info.getVersion() == null) {
			info.setVersion(new BigDecimal("1.0"));
		} else {
			BigDecimal version = info.getVersion();
			editData.setVersion(version.add(new BigDecimal("1.0")));
		}
	}

	/**
	 * 
	 * 描述：根据参数KEY获取参数值
	 * @return
	 * @Author：jian_cao
	 * @CreateTime：2012-8-3
	 */
	private String getFDCParamValueByKey(String key) {

		return ContextHelperFactory.getRemoteInstance().getStringParam(key,
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()));
	}

	/**
	 * 保存前把检查存在"本合约分配"大于 "待分配"情况
	 * R120720-0316 add by jian_cao
	 * 添加了根据系统参数值做判断的提示代码
	 */
	private void verifyRedData(String fdc229ParamValue) {

		if (com.kingdee.util.StringUtils.isEmpty(fdc229ParamValue) || "0".equals(fdc229ParamValue))
			return;
		
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				BigDecimal contractAssign = pccInfo.getContractAssign();
				BigDecimal assigning = pccInfo.getAssigning();
				if (contractAssign.compareTo(assigning) > 0) {
					Color fontColor = kdtEntries.getRow(i).getStyleAttributes().getFontColor();
					Color red = new Color(255, 0, 0);
					if (fontColor.equals(red)) {
						
						if ("2".equals(fdc229ParamValue)) {
							FDCMsgBox.showWarning(this, "对不起，成本构成中存在\"本合约分配\"大于 \"待分配\"，不能保存或提交！");
							SysUtil.abort();
						} else if ("1".equals(fdc229ParamValue)) {
							//如果用户点了否 就抛出中断不继续执行
							if (FDCMsgBox.OK != FDCMsgBox.showConfirm2("成本构成中存在\"本合约分配\"大于 \"待分配\"，是否要继续提交?")) {
								SysUtil.abort();
							}
						}
						
					}
				}
			}
		}
	}
	

	/**
	 * 判断已规划金额是否大于总目标成本金额
	 * 
	 * @return 大于返回true
	 */
	private boolean verifyAmountVSAimCost() {
		kdtEntries.getEditManager().editingStopped();
		Object aimCostAccountTotalObj = txtAllAimCost.getNumberValue();
		BigDecimal aimCostAccountTotal = FDCHelper.ZERO;// 总目标成本
		BigDecimal amountTotal = FDCHelper.ZERO;// 总规划金额
		if (!FDCHelper.isEmpty(aimCostAccountTotalObj)) {
			aimCostAccountTotal = FDCHelper.toBigDecimal(aimCostAccountTotalObj);
		}
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level == 1) {
				Object amountSingleObj = kdtEntries.getCell(i, "amount").getValue();
				BigDecimal amountSingle = FDCHelper.ZERO;
				if (amountSingleObj != null) {
					amountSingle = FDCHelper.toBigDecimal(amountSingleObj);
				}
				amountTotal = amountTotal.add(amountSingle);
			}
		}
		// 已规划金额大于总目标成本金额 return true
		if (amountTotal.compareTo(aimCostAccountTotal) > 0) {
			return true;
		}
		return false;
	}

	private void verifyControlParam(String fdc229ParamValue) {
		if (prmtAimCost.getData() != null) {
			
			if (!com.kingdee.util.StringUtils.isEmpty(fdc229ParamValue)) {
				int i = Integer.parseInt(fdc229ParamValue);
				switch (i) {
				case 0:
					// 不控制
					break;
				case 1:
					// 提示控制
					if (verifyAmountVSAimCost()) {
						int isSubmit = FDCMsgBox.showConfirm2("总规划金额大于总目标成本金额，是否要提交？");
						if (FDCMsgBox.OK == isSubmit) {
							break;
						} else {
							SysUtil.abort();
						}
					}
				case 2:
					// 严格控制
					if (verifyAmountVSAimCost()) {
						FDCMsgBox.showWarning(this, "总规划金额大于总目标成本金额，不允许提交");
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * 合约规划保存
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		veryfyForSave();
		super.actionSave_actionPerformed(e);
		sumClass.appendProFootRow(null, null);
	}

	/**
	 * 保存校验
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void veryfyForSave() throws EASBizException, BOSException {
		String name_txt = txtName.getText();
		String errrMsg = "合约框架版本名称";
		if (name_txt == null || name_txt.trim().equals("")) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + "不能为空！"));
		}
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "工程项目不能为空"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "版本号不能为空"));
		}
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name_txt, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingFactory.getRemoteInstance().exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + name_txt + "已经存在，不能重复！"));
		}
		// 保存时去掉分录名称的前后空格
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}

			row.getCell("sortNumber").setValue(new Integer(i));
		}

		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
	}
	
    /**
     * output actionSubmit_actionPerformed
     */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		String errorMsg = "如下成本科目的待规划金额不为0：";
		for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
			Object assigning = kdtCostAccount.getCell(j,"assigning").getValue();
			Object proName = kdtCostAccount.getCell(j,"proName").getValue();
			if(!"0".equals(assigning.toString())&&proName!=null)
			{
				int  i=j+1;
				Object costName = kdtCostAccount.getCell(j,"costName").getValue();
				errorMsg=errorMsg+"\n行"+i+":  "+costName;
			}
		}
		if(!"如下成本科目的待规划金额不为0：".equals(errorMsg)){
			MsgBox.showDetailAndOK(this, "目标成本还有待规划金额，请确认是否提交", errorMsg, 3);		
		}
		//读取FDC229_ISSTRICTCONTROL参数的值  add by jian_cao
		String fdc229ParamValue = this.getFDCParamValueByKey(FDCConstants.FDCSCH_PARAM_ISSTRICTCONTROL);
		verifyControlParam(fdc229ParamValue);
		verifyRedData(fdc229ParamValue);
		verifyDataBySave();
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "工程项目不能为空"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "版本号不能为空"));
		}
		//保存时去掉分录名称的前后空格
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}
			
			row.getCell("sortNumber").setValue(new Integer(i));
		}

		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
		
		String curVersion = txtVersion.getText();
		String versionGroup = txtVersionGroup.getText();
		super.actionSubmit_actionPerformed(e);
		if (isBillModify()) {
			((IProgramming) getBizInterface()).billModify(versionGroup, curVersion); // 维护最新版本字段
			ProgrammingListUI parentUI = (ProgrammingListUI) getUIContext().get("parent");
			parentUI.refreshList();
			this.getUIContext().remove(ProgrammingListUI.IS_MODIFY);
		}
		
		setAuditBtnEnable();
		sumClass.appendProFootRow(null, null);
	}

	// 是否修订
	private boolean isBillModify() {
		Boolean isSet = (Boolean) getUIContext().get(ProgrammingListUI.IS_MODIFY);
		return isSet != null && isSet.booleanValue();
	}
	
	// 控制金额不能大于规划金额
	// private void checkAmount(int rowIndex) throws EASBizException {
	// if (rowIndex < 0) {
	// return;
	// }
	//		
	// IRow row = kdtEntries.getRow(rowIndex);
	// Object cellValue = row.getCell("amount").getValue();
	// BigDecimal amount = checkNumber(cellValue, rowIndex, "amount", 0); // 规划金额
	//		
	//
	// cellValue = row.getCell("controlAmount").getValue();
	// BigDecimal ctrlAmount = checkNumber(cellValue, rowIndex, "controlAmount", 0); // 控制金额
	//		
	// if (ctrlAmount.compareTo(amount) > 0) {
	// StringBuffer msg = new StringBuffer();
	// msg.append("第 ").append(rowIndex + 1).append("行 控制金额不能大于规划金额");
	// throw new EASBizException(new NumericExceptionSubItem("1", msg.toString()));
	// }
	// }
    
	/**
	 * 提交前校验数据，名称、分录编码及名称
	 * @throws Exception
	 */
    public void verifyDataBySave() throws Exception {
    	int rowCount = kdtEntries.getRowCount();
    	String name_txt = txtName.getText();
    	String errrMsg = "合约框架版本名称";
		if (name_txt == null || name_txt.trim().equals("")) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg+"不能为空！"));
		}
		
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name_txt, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingFactory.getRemoteInstance().exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg+name_txt+"已经存在，不能重复！"));
		}
		
    	for(int i = 0 ; i < rowCount ; i++){
			// checkAmount(i);
    		
    		Object number = kdtEntries.getCell(i, "longNumber").getValue();
    		Object head = kdtEntries.getCell(i, HEADNUMBER).getValue();
    		if(number == null || number.toString().trim() == null){
	    		throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		Object level = kdtEntries.getCell(i, "level").getValue();
    		int level_int = new Integer(level.toString()).intValue();
			if(level_int != 1){
	    		String ln = number.toString();
	    		if(ln.length() == (head.toString().length() + 1)){
	    			throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
				}
			}
			
			String longNumber = number.toString().trim();
			if(longNumber.length() > 80){
				throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，编码超长，请重新输入！"));
			}
    		
    		Object proName = kdtEntries.getCell(i, "name").getValue();
    		if(proName == null || proName.toString().trim() == null){
    			throw new ProgrammingException(ProgrammingException.NAME_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		if(proName != null && proName.toString().trim().length() > 80){
    			throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，框架合约名称超长！"));
        	}
    		
    		Object longName = kdtEntries.getCell(i, "longName").getValue();
    		if(longName != null && !StringUtils.isEmpty(longName.toString())){
    			if(longName.toString().length() > 255){
    				throw new EASBizException(new NumericExceptionSubItem("1", "分录第"+(i+1)+"行，框架合约长名称超长\n请修改框架合约名称数据！"));
    			}
    		}
    		
    		String lnumber = number.toString();
    		String name = proName.toString().trim();
    		
    		for(int j = 0 ; j < rowCount ; j++){
    			if(j == i)
    				continue;
    			
    			Object number_2 = kdtEntries.getCell(j, "longNumber").getValue();
        		Object proName_2 = kdtEntries.getCell(j, "name").getValue();
        		
        		if(number_2 != null && number_2.toString().trim().length() > 0){
        			if(lnumber.equals(number_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "编码"});
        			}
        		}
        		
        		if(proName_2 != null && proName_2.toString().trim().length() > 0){
        			if(name.equals(proName_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "名称"});
        			}
        		}
    		}
    	}
    }
    
    /**
     * 获取当前时间字符串，用于设置编码
     */
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
    
    /**
     * 设置名称列显示缩进效果、在前面加空格
     */
    private void setNameDisplay(){
		int rowCount = kdtEntries.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString());
			}
		}
	}
    
    //覆盖掉父类的方法
    protected void setAuditButtonStatus(String oprtType) {
    }
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkHighVersion();
        super.actionEdit_actionPerformed(e);
        setSmallBtnEnable();
        setCellEditorForTable();
//		if (isCiting()) {
//			prmtAimCost.setEnabled(false);
//		} else {
//			prmtAimCost.setEnabled(true);
//		}
        handleOldData();
    }
    
    private void checkHighVersion() throws Exception {
		BigDecimal version = editData.getVersion().add(FDCHelper.ONE);
		String versionGroup = editData.getVersionGroup();
		
		String oql = "where version = '".concat(version.toString()).concat("' and versionGroup = '")
			.concat(versionGroup).concat("'");
		if (getBizInterface().exists(oql)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "存在更高版本不能进行此操作"));
		}
	}

    /**
     * output actionAudit_actionPerformed
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
			FDCMsgBox.showInfo("保存状态单据不允许审批！");
			SysUtil.abort();
		}
		if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				ProgrammingFactory.getRemoteInstance().audit(editData.getId());
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
				actionEdit.setEnabled(false);
				editData.setState(FDCBillStateEnum.AUDITTED);
				MsgBox.showInfo("审批成功！");
			}
		}
		handleOldData();
	}

    /**
     * output actionUnAudit_actionPerformed
     */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				boolean isLastVersion = ProgrammingFactory.getRemoteInstance().isLastVersion(new ObjectUuidPK(editData.getId().toString()));
				if (!isLastVersion) {
					throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能反审批"));
				}
				checkHighVersion();
				
				ProgrammingFactory.getRemoteInstance().unAudit(editData.getId());
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
				if(this.getOprtState().equals(STATUS_VIEW))
					actionEdit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				MsgBox.showInfo("反审批成功！");
			}
		}
		handleOldData();
	}
    
    /**
     * 全局侦听，鼠标点击分录外时，分录需要失去焦点
     */
    private void setMouseClick() {
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	Component[] com = this.getComponents();
    	for(int i = 0 ; i < com.length ; i++){
    		if(!com[i].equals(kdtEntries)){
    			com[i].addMouseListener(new MouseListener(){
    				public void mouseClicked(MouseEvent arg0) {
    				}
    				public void mouseEntered(MouseEvent arg0) {
    				}
    				public void mouseExited(MouseEvent arg0) {
    				}
    				public void mousePressed(MouseEvent arg0) {
    				}
    				public void mouseReleased(MouseEvent arg0) {
    					if(!arg0.getComponent().equals(kdtEntries)){
    						setKDTableLostFocus();
    					}
    				}
    	    	});
    		}
    	}
    	
    	this.txtName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtProjectName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtVersion.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtAimCoustVersion.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
	}

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.contract.programming.client.ProgrammingEditUI.class.getName();
    }
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("soldArea"));
		sic.add(new SelectorItemInfo("aimCost.*"));
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("entries.longNumber"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("entries.parent.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.paymentType.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.controlAmount"));
		sic.add(new SelectorItemInfo("entries.balance"));
		sic.add(new SelectorItemInfo("entries.controlBalance"));
		sic.add(new SelectorItemInfo("entries.signUpAmount"));
		sic.add(new SelectorItemInfo("entries.changeAmount"));
		sic.add(new SelectorItemInfo("entries.settleAmount"));
		sic.add(new SelectorItemInfo("entries.citeVersion"));
		sic.add(new SelectorItemInfo("entries.isCiting"));
		sic.add(new SelectorItemInfo("entries.attachment"));
		sic.add(new SelectorItemInfo("entries.description"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.number"));
		sic.add(new SelectorItemInfo("entries.level"));
		sic.add(new SelectorItemInfo("entries.parent.longNumber"));
		sic.add(new SelectorItemInfo("entries.sortNumber"));
		sic.add(new SelectorItemInfo("entries.displayName"));
		sic.add(new SelectorItemInfo("entries.workcontent"));
		sic.add(new SelectorItemInfo("entries.supMaterial"));
		sic.add(new SelectorItemInfo("entries.inviteWay"));
		sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		sic.add(new SelectorItemInfo("entries.buildPerSquare"));
		sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("versionGroup"));
	    sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		return sic;
	}      

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.programming.ProgrammingInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
        objectValue.setVersion(new BigDecimal("1.0"));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (SysContext.getSysContext().getCurrentUser()));
        objectValue.setVersionGroup(Uuid.create().toString());
        objectValue.setProject((CurProjectInfo) this.getUIContext().get("treeSelectedObj"));

        return objectValue;
    }
    
    protected void setTableToSumField() {
		HashMap sumFields = getSumFields();
		if (sumFields == null) return;
		//取出所有的KDTable及合计列信息
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[])sumFields.get(table);
			super.setTableToSumField(table, sumColNames);			
		}
	}
    
    protected HashMap getSumFields() {
		HashMap sumFields = new HashMap();
		// 目标成本页签合计列
		sumFields.put(this.kdtCostAccount, new String[] { "aimCost", "assigned", "assigning"});
		return sumFields;
	}
    
    private void setProTableToSumField() {
		HashMap sumFields = getProSumFields();
		if (sumFields == null) return;
		//取出所有的KDTable及合计列信息
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[])sumFields.get(table);
			sumClass.setProTableToSumField(table, sumColNames);			
		}
	}
    
    private HashMap getProSumFields(){
    	HashMap sumFields = new HashMap();
    	sumFields.put(this.kdtEntries, new String[] {"amount", "controlAmount", "signUpAmount", "changeAmount", "settleAmount", "balance",
    			"controlBalance", "buildPerSquare", "soldPerSquare" });
    	return sumFields;
    }
    
    protected void handleOldData() {
		if(!(getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
    
    protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
    	if(kDTabbedPane1.getSelectedIndex() == 1){
    		loadCostAccountToCostEntry();
		}
    }
    
    /**
     * 切换到成本页签时加载数据
     */
    private void loadCostAccountToCostEntry(){
    	loadCostAccountTabData();
		setCostNumberFormat();
		createCostTree();
		
		List rows = this.kdtCostAccount.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtCostAccount.getColumnIndex("costNumber"), KDTSortManager.SORT_ASCEND));

		moneyCount();
	    kdtCostAccount.setRefresh(false);
    }
    /**
     * 成本页签 加载数据时金额汇总
     * 
     */
    private void moneyCount()
    {
		// start
		IRow row = null;
		Map map = new HashMap();
		BigDecimal aimCost = null;
		BigDecimal assigned = null;
		BigDecimal assigning = null;
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			row = kdtCostAccount.getRow(i);
			aimCost = (BigDecimal) row.getCell("aimCost").getValue();
			assigned = (BigDecimal) row.getCell("assigned").getValue();
			assigning = (BigDecimal) row.getCell("assigning").getValue();
			row.getCell("assigning").setValue(aimCost.subtract(assigned));
			CostAccountInfo costInfo = (CostAccountInfo) row.getUserObject();
			if (costInfo.isIsLeaf()) {
				String parentId = costInfo.getParent().getId().toString();
				if (map.containsKey(parentId)) {
					BigDecimal[] temp = (BigDecimal[]) map.get(parentId);
					temp[0] = temp[0].add(aimCost);
					temp[1] = temp[1].add(assigned);
					temp[2] = temp[2].add(assigning);
				} else {
					BigDecimal[] temp = new BigDecimal[3];
					temp[0] = aimCost;
					temp[1] = assigned;
					temp[2] = assigning;
					map.put(parentId, temp);
				}
			} else {
				BigDecimal[] temp = new BigDecimal[] { new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0") };
				map.put(costInfo.getId().toString(), temp);
			}
		}
		// end
    }
    
    /**
	 * 设置成本页签的成本科目长编码显示格式
	 */
	private void setCostNumberFormat(){
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return null;
			}
		});
		kdtCostAccount.getColumn("costNumber").setRenderer(render);
	}
	
	/**
	 * 设置成本页签的树形
	 */
	private void createCostTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtCostAccount.getRowCount()];
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			IRow row = kdtCostAccount.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtCostAccount.getTreeColumn().setDepth(maxLevel);
		kdtCostAccount.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
    
    /**
	 * 加载成本科目页签数据显示
	 */
	private void loadCostAccountTabData(){
		kdtCostAccount.removeRows();
		kdtCostAccount.getStyleAttributes().setLocked(true);
		int rowCount = kdtEntries.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			ProgrammingContractInfo rowObject = (ProgrammingContractInfo)kdtEntries.getRow(i).getUserObject();
			Object name = kdtEntries.getCell(i, "name").getValue();//框架合约模板名称
			String proName = "";
			String oldName = "";
			if(name != null && name.toString().trim().length() > 0){
				proName = name.toString().trim();
				oldName = name.toString().trim();
			}
			createCostEntriesRow(i, rowObject, proName, oldName);
		}
	}
	
	/**
	 * 创建成本页签分录行
	 * @param i
	 * @param rowObject
	 * @param proName
	 * @param oldName
	 */
	private void createCostEntriesRow(int i, ProgrammingContractInfo rowObject, String proName, String oldName) {
		if (rowObject != null) {
			ProgrammingContracCostCollection proCol = rowObject.getCostEntries();
			if (proCol.size() > 0) {
				addRowForCost(proName, oldName, proCol);
			}
		}
	}
	
	/**
	 * 添加行到成本分录
	 * @param proName
	 * @param oldName
	 * @param proCol
	 */
	private void addRowForCost(String proName, String oldName, ProgrammingContracCostCollection proCol) {
		for (int i = 0; i < proCol.size(); i++) {
			boolean isHas = false;
			ProgrammingContracCostInfo info = proCol.get(i);
			BigDecimal contractAssign = info.getContractAssign();// 本合约分配
			BigDecimal goalCost = info.getGoalCost();// 目标成本
			if (contractAssign != null && goalCost != null) {
				if(goalCost.compareTo(FDCHelper.ZERO) > 0){
					BigDecimal percent = contractAssign.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED);
					if (percent.intValue() <= 0) {
						proName = oldName;
					}else if(percent.intValue() < 100){
						proName = oldName + percent.intValue() + "%";
					}
				}
			}
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("curProject.name");
			sic.add("curProject.id");	// modified by zhaoqin for R131029-0240 on 2013/11/22
			CostAccountInfo costInfo = null;
			try {
				costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getCostAccount().getId()) , sic);
			} catch (EASBizException e) {
				logger.error(e);
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				logger.error(e);
				handUIExceptionAndAbort(e);
			}
			if(!curProject.getId().equals(costInfo.getCurProject().getId())){
				proName = costInfo.getCurProject().getName()+"."+proName;
			}
			
//			//added by ken...
//			kdtCostAccount.checkParsed();
			String name = null;
			for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
				name = null;
				IRow row_k = kdtCostAccount.getRow(j);
				String number = row_k.getCell("costNumber").getValue().toString();
				Object name_1 = row_k.getCell("proName").getValue();
				if(name_1 != null)
					name = name_1.toString();
				if (number.equals(costInfo.getLongNumber())) {
					isHas = true;
					if(name == null){
						row_k.getCell("proName").setValue(proName);
					}else{
						row_k.getCell("proName").setValue(name + "," + proName);
					}
					Object ass = row_k.getCell("assigned").getValue();
					BigDecimal assigned = FDCHelper.ZERO;
					if(ass != null && !StringUtils.isEmpty(ass.toString())){
						assigned = new BigDecimal(ass.toString());
						assigned = assigned.add(contractAssign);
						row_k.getCell("assigned").setValue(assigned);
					}
					Object aim = row_k.getCell("aimCost").getValue();
					BigDecimal aimCost = FDCHelper.ZERO;
					if(aim != null && !StringUtils.isEmpty(aim.toString())){
						aimCost = new BigDecimal(aim.toString());
					}
					row_k.getCell("assigning").setValue(aimCost.subtract(assigned));
				}
			}
			if (!isHas) {
				List list = new ArrayList();
				getParentCostAccountInfo(costInfo, list);
				addCostAccountParent(list, goalCost, contractAssign);
				IRow row = kdtCostAccount.addRow();
				row.setUserObject(costInfo);
				row.getCell("costNumber").setValue(costInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costInfo.getLevel())+costInfo.getName());
				row.getCell("aimCost").setValue(goalCost);
				row.getCell("assigned").setValue(contractAssign);
				row.getCell("assigning").setValue(goalCost.subtract(contractAssign));
				row.getCell("level").setValue(costInfo.getLevel() + "");
				row.getCell("proName").setValue(proName);
			}else{
				List list = new ArrayList();
				getParentCostAccountInfo(costInfo, list);

				for(int k = 0 ; k < list.size() ; k++){
					CostAccountInfo costAccountInfo = (CostAccountInfo)list.get(k);
					for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
						IRow row = kdtCostAccount.getRow(j);
						String number = row.getCell("costNumber").getValue().toString();
						if (number.equals(costAccountInfo.getLongNumber())) {
							BigDecimal aimCost = (BigDecimal)row.getCell("aimCost").getValue();
							BigDecimal assigned = (BigDecimal)row.getCell("assigned").getValue();
							BigDecimal assigning = (BigDecimal)row.getCell("assigning").getValue();

							//							row.getCell("aimCost").setValue(aimCost != null ? aimCost.add(goalCost) : goalCost);
							row.getCell("assigned").setValue(assigned != null ? assigned.add(contractAssign) : contractAssign);
							if (aimCost != null) {
								aimCost = FDCHelper.ZERO;
							}
							if (assigned == null) {
								assigned = FDCHelper.ZERO;
							}
							row.getCell("assigning").setValue(
									assigning != null ? assigning.add(aimCost.subtract(assigned)) : aimCost.subtract(assigned));
							isHas = true;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 递归获取成本科目的所有上级成本科目
	 * @param info
	 * @param list
	 * @return
	 */
	private CostAccountInfo getParentCostAccountInfo(CostAccountInfo info , List list){
		if(info.getParent() != null){
			CostAccountInfo costInfo = null;;
			try {
				costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getParent().getId()));
			} catch (EASBizException e) {
				logger.error(e);
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				logger.error(e);
				handUIExceptionAndAbort(e);
			}
			list.add(costInfo);
			return getParentCostAccountInfo(costInfo , list);
		}
		return null;
	}
	
	/**
	 * 如果存在上级的成本科目则先新增上级成本科目
	 * @param list
	 */
	private void addCostAccountParent(List list,BigDecimal goalCost,BigDecimal contractAssign) {
		for(int i = 0 ; i < list.size() ; i++){
			CostAccountInfo costAccountInfo = (CostAccountInfo)list.get(i);
			boolean isHas = false;
			for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
				IRow row = kdtCostAccount.getRow(j);
				String number = row.getCell("costNumber").getValue().toString();
				if (number.equals(costAccountInfo.getLongNumber())) {
					BigDecimal aimCost = (BigDecimal)row.getCell("aimCost").getValue();
					BigDecimal assigned = (BigDecimal)row.getCell("assigned").getValue();
					BigDecimal assigning = (BigDecimal)row.getCell("assigning").getValue();
					
					row.getCell("aimCost").setValue(
							aimCost != null ? aimCost.add(goalCost) : goalCost);
					row.getCell("assigned").setValue(
							assigned != null ? assigned.add(contractAssign)
									: contractAssign);
					row.getCell("assigning").setValue(
							assigning != null ? assigning.add(goalCost
									.subtract(contractAssign)) : goalCost
									.subtract(contractAssign));
					isHas = true;
				}
			}
			if(!isHas){
				IRow row = kdtCostAccount.addRow();
				row.setUserObject(costAccountInfo);
				row.getCell("costNumber").setValue(costAccountInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costAccountInfo.getLevel())+costAccountInfo.getName());
				row.getCell("level").setValue(costAccountInfo.getLevel() + "");
				
				row.getCell("aimCost").setValue(goalCost);
				row.getCell("assigned").setValue(contractAssign);
				row.getCell("assigning").setValue(goalCost.subtract(contractAssign));
			}
		}
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId() != null){
			if(ProgrammingFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))){
				editData = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(editData.getId().toString()) , getSelectors());
				if(editData == null )return;
				setDataObject(editData);
				loadFields();
			}
		}
    	this.dataBinder.storeFields();
		setKDTableLostFocus();
    	createTree();
    	loadCostAccountToCostEntry();
    	// 解决刷新数据合计不对问题
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
	}
	
	/**
	 * 从模板导入功能
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			if (entry.isIsCiting()) {
				throw new EASBizException(new NumericExceptionSubItem("1", 
				"合约框架中存在被引用的框架合约\n不允许此操作"));
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("editData", editData);
		uiContext.put("project", curProject);
		uiContext.put("aimCost", prmtAimCost.getData());
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingImportUI.class.getName(), uiContext, null,	OprtState.VIEW , WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		ui.show();
		
		clearAndDisplay(entries);
	}

	/**
	 * 
	 * 描述：清除控件中原来的内容，并重新loadField显示
	 * 
	 * @param entries
	 *            创建人：owen_wen 创建时间：2011-10-14
	 */
	private void clearAndDisplay(ProgrammingContractCollection entries) {
		// 从末级逐级向上汇总
		// 规划金额 = 下级规划金额汇总
		Set leafSet = findLeafSet();
		cleanNotLeafAmout(leafSet);
		totalAmout(leafSet);
		// 导入后规划余额 = 规划金额
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			entry.setBalance(entry.getAmount());
		}
		
		setDataObject(editData);
		loadFields();
		if (this.oprtState.equals(STATUS_ADDNEW)) {
			setNameDisplay();
		}
	}
	
	private Set findLeafSet() {
		Set leafNumberSet = new HashSet();
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo info = entries.get(i);
			String longNumber = info.getLongNumber();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				ProgrammingContractInfo infoJ = entries.get(j);
				String longNumberJ = infoJ.getLongNumber();
				if (longNumberJ.startsWith(longNumber)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf && longNumber.indexOf('.') != -1) {
				leafNumberSet.add(longNumber);
			}
		}
		return leafNumberSet;
	}
	
	/**
	 * 一个根节点下的所有节点
	 * @param numberList
	 * @return
	 */
	private Set findLeafList(List numberList) {
		Set leafNumberList = new HashSet();
		for (int i = 0, size = numberList.size(); i <size; i++) {
			String number = numberList.get(i).toString();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				String numberJ = numberList.get(j).toString();
				if (numberJ.startsWith(number)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf) {
				leafNumberList.add(number);
			}
		}
		return leafNumberList;
	}
	
	/**
	 * 从叶节点递归向上逐级汇总
	 * @param leafSet
	 */
	private void totalAmout(Set leafSet) {
		if (leafSet.isEmpty()) {
			return;
		}
		
		Set leafParentSet = new HashSet(); // 当前叶节点的父节点
		for (Iterator it = leafSet.iterator(); it.hasNext();) {
			BigDecimal leafAmount = FDCHelper.ZERO; // 当前叶节点金额
			String leafLongNumber = it.next().toString();
			ProgrammingContractCollection entries = editData.getEntries();
			for (int j = 0, sizeJ = entries.size(); j < sizeJ; j++) {
				ProgrammingContractInfo entry = entries.get(j);
				if (leafLongNumber.equals(entry.getLongNumber())) {
					leafAmount = entry.getAmount();
					break;
				}
			}
			ProgrammingContractInfo parentEntry = getParentEntry(leafLongNumber);
			if (parentEntry != null) {
				parentEntry.setAmount(leafAmount.add(parentEntry.getAmount()));
				leafParentSet.add(parentEntry.getLongNumber());
			}
		}
		
		List nodeList = new ArrayList();
		nodeList.addAll(leafParentSet);
		totalAmout(findLeafList(nodeList));
	}
	
	/**
	 * 所有非叶节点数据清零，直接从叶节点汇总。
	 * @param leafSet
	 */
	private void cleanNotLeafAmout(Set leafSet) {
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			if (!leafSet.contains(longNumber)) {
				// 不包含子节点的根节点不删除
				boolean hasChild = false;
				for (int j = 0; j < size; j++) {
					if (i == j) {
						continue;
					}
					if (entries.get(j).getLongNumber().startsWith(longNumber)) {
						hasChild = true;
						break;
					}
				}
				if (hasChild) {
					entry.setAmount(FDCHelper.ZERO);
				}
			}
		}
	}
	
	private ProgrammingContractInfo getParentEntry(String subLongNumber) {
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			if (entry.getLongNumber().equals(subLongNumber)) {
				return entry.getParent();
			}
		}
		return null;
	}
	
	/**
	 * 另存为模板功能
	 */
	public void actionExportPro_actionPerformed(ActionEvent e) throws Exception {
		verifyDataBySave();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			row.getCell("sortNumber").setValue(new Integer(i));
		}
		this.dataBinder.storeFields();
		verifyRedData(this.getFDCParamValueByKey(FDCConstants.FDCSCH_PARAM_ISSTRICTCONTROL));
		UIContext uiContext = new UIContext(this);
		uiContext.put("Programming", editData);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingExportUI.class.getName(), uiContext, null,	OprtState.EDIT);
		ui.show();
	}

	private void createCostCentertF7() {
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setVisible(true);
		f7.setEnabled(true);
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		EntityViewInfo view = f7.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		if (view.getFilter() == null) {
			view.setFilter(new FilterInfo());
		}
		Set idSet = null;
		try {
			idSet = FDCUtils.getAuthorizedOrgs(null);
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("COSTCENTERORGUNIT.id", idSet, CompareType.INCLUDE));
		f7.setEntityViewInfo(view);

		KDTDefaultCellEditor kdtEntriese_ORG_CellEditor = new KDTDefaultCellEditor(f7);
		kdtEntries.getColumn("inviteOrg").setEditor(kdtEntriese_ORG_CellEditor);
		ObjectValueRender kdtEntries_ORG_OVR = new ObjectValueRender();
		kdtEntries_ORG_OVR.setFormat(new BizDataFormat("$name$"));
		kdtEntries.getColumn("inviteOrg").setRenderer(kdtEntries_ORG_OVR);
	}	
}