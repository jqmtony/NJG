package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public final class PayReqTableHelper {
	private static final Logger logger = CoreUIObject.getLogger(PayReqUtils.class);

	public static final Color noEditColor = new Color(232, 232, 227);

	private PayRequestBillInfo editData = null;

	private PayRequestBillEditUI editUI = null;

	private HashMap bindCellMap = null;
	
	private KDTable table=null;
	
	/**
	 * 构造函数
	 * @param editUI
	 */
	public PayReqTableHelper(PayRequestBillEditUI editUI) {
		this.editUI = editUI;
		this.editData = editUI.editData;
		this.bindCellMap = editUI.bindCellMap;
	}

	/**
	 * 得到用于显示分录的table
	 * @param  deductTypeCollection 扣款类型集合
	 * @author sxhong 下午01:57:45
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException
	 */
	KDTable createPayRequetBillTable(DeductTypeCollection deductTypeCollection) throws EASBizException, BOSException {
		/*
		 * 将表格的创建放在onLoad的后面使得editData及getUIContext()在创建表格时可用,以控制扣款项的创建
		 */
		// 隐藏以前的kdtEntrys用自己的kdtable替代
		Rectangle kdtRectangle = editUI.kdtEntrys.getBounds();
		editUI.kdtEntrys.setEnabled(false);
		editUI.kdtEntrys.setVisible(false);
		
		// 6列1头部0行的表格
		table = new KDTable(12, 1, 0);
		
		table.setBounds(kdtRectangle);
		editUI.add(table, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
				| KDLayout.Constraints.ANCHOR_RIGHT_SCALE, kdtRectangle));
		
		// 隐藏行索引
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.setRefresh(false);
		table.getScriptManager().setAutoRun(false);
		StyleAttributes sa = table.getStyleAttributes();
		// 是否可编辑
		sa.setLocked(true);
		sa.setNumberFormat("###,##0.00");
		// 融合头部
		IRow headRow = table.getHeadRow(0);
		headRow.getCell(0).setValue(getRes("prjTable")); 
		KDTMergeManager mm = table.getHeadMergeManager();
		mm.mergeBlock(0, 0, 0, 11, KDTMergeManager.SPECIFY_MERGE);
//		for (char i = 'A'; i <= 'L'; i++) { 定位测试用的
//			headRow.getCell(i - 'A').setValue(String.valueOf(i));
//		}
		
		initFixTable(table);
		// 在第10行插入应扣款项分录
		initDynamicTable(table,deductTypeCollection);

		//设置计算公式（在这里设置公式后，后面会出现公式被覆盖）
//		calcTable();

		// 设置对齐方式
		sa = table.getColumn(1).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
		initLayout();
		
		//设置表格编辑颜色
		setTableCellColorAndEdit(table);
		
		table.setRefresh(true);
		// 自动调整大小
		table.setAutoResize(true);
		table.getScriptManager().setAutoRun(true);
		// 设置索引列固定宽度=2
		table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_FIXED);
		// 工程量确认流程与付款流程是否分离
		boolean isSeparate = !editUI.isSimpleInvoice && editUI.isSeparate && (FDCUtils.isContractBill(null, editData.getContractId()));
		table.getRow(6).getStyleAttributes().setHided(!isSeparate);
		// 隐藏增加工程款行
		table.getRow(7).getStyleAttributes().setHided(true);
		
		//设置表格拷贝的模式为值拷贝
		((KDTTransferAction)table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		return table;
	}

	/**
	 * description		设置表格对齐方式
	 * @author			蒲磊<p>	
	 * @createDate		2011-9-5<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void initLayout(){
		//项目名称,合同名称向左对齐
		table.getCell(0, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		table.getCell(2, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		table.getCell(0, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(14, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(3, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		table.getCell(0, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		table.getCell(0, 9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);


		table.getCell(2, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(3, 2).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getCell(3, 4).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getCell(2, 4).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 4).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		table.getCell(3, 8).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getCell(3, 10).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		table.getCell(0, 10).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 10).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 10).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		int rowCount = table.getRowCount();
		for (int i = 2; i < table.getColumnCount(); i++) {
			table.getCell(4, i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			for (int j = 5; j < rowCount - 4; j++) {
				table.getCell(j, i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}

		table.getCell(rowCount - 1, 4).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(rowCount - 2, 4).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(rowCount - 2, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(rowCount - 4, 10).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(rowCount - 4, 11).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	/**
	 * 奥园要求在付款申请单序时簿和付款申请单查询界面添加一列 "是否存在附件" 
	 * 由于可能多处需使用该功能,故抽取为方法  by Cassiel_peng
	 */
	public static Map handleAttachment(Set boIDS) throws BOSException{
		Map attachMap=new HashMap();
		if(boIDS==null || boIDS.size()==0) {
			return attachMap;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select fboid from t_bas_boattchasso where ");
		builder.appendParam("fboid", boIDS.toArray());
		IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			while (rowSet.next()) {
				//如果该合同有业务相关附件就进行标记，否则不标记
				attachMap.put(rowSet.getString("fboid"),Boolean.TRUE);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new BOSException(e1);
		}
		return attachMap;
	}
	//处理最后审批人和最后审批时间 by cassiel_peng 2010-05-26
	public static Map handleAuditPersonTime(Set payReqIds) throws EASBizException, BOSException {
		Map auditMap = new HashMap();
			auditMap= FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(payReqIds);
		return auditMap;
	}
	
	/**
	 * description		初始化表格的固定部分,初始化的时候加入绑定信息到bindCellmap
	 * @author			蒲磊<p>	
	 * @createDate		2011-9-2<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void initFixTable(KDTable table) {
		// 添加16行,在第10行插入应扣款项分录
		table.addRows(16);
		IRow row;
		ICell cell;
		// 第一列
		//合同名称0, 0 
		PayReqUtils.bindCell(table, 0, 0, getRes("contractBill.contractName"), PayRequestBillContants.CONTRACTNAME, bindCellMap);
		table.getCell(0, 1).getStyleAttributes().setNumberFormat("@");
		//合同付款次数
		PayReqUtils.bindCell(table, 3, 0, getRes("payTimes"), PayRequestBillContants.PAYTIMES, bindCellMap);
		//进度款项
		table.getCell(5, 0).setValue(getRes("scheduleAmt"));
		// 应扣款项下面的5行 deductAmtItem
//		table.getCell(9, 0).setValue(getRes("deductAmtItem"));
		//应扣甲供材扣款
		table.getCell(10, 0).setValue(getRes("payPartAMatlAmt"));
		//实付款
		table.getCell(11, 0).setValue(getRes("paid"));
		//余款
		table.getCell(12, 0).setValue(getRes("residue"));
		//本期计划付款
		PayReqUtils.bindCell(table, 13, 0, getRes("curPlannedPayment"), PayRequestBillContants.CURPLANNEDPAYMENT, bindCellMap);
		
		//本次申请
		PayReqUtils.bindCell(table, 14, 0, getRes("curReqPercent"), PayRequestBillContants.CURREQPERCENT, bindCellMap);
		//应付申请
		table.getCell(15, 0).setValue("应付申请%");
		
		// 第二列
		// 进度款项
		//合同内工程款
		table.getCell(5, 1).setValue(getRes("projectPriceInContract"));
		//预付款
		table.getCell(6, 1).setValue("预付款");
		//合同增加款项
		table.getCell(7, 1).setValue(getRes("addProjectAmt"));
		//奖励
		table.getCell(8, 1).setValue("奖励");// 增加奖励单在6行，其后面的加一
		//违约金
		table.getCell(9, 1).setValue("违约金");// 用违约金替代小计

		// 第三列
		//截至上期累计实付
		table.getCell(3, 2).setValue(getRes("lstAllPaid"));
		table.getCell(4, 2).setValue("原币");

		// 第四列
		PayReqUtils.bindCell(table, 1, 3, "变更指令金额本币", PayRequestBillContants.CHANGEAMT, bindCellMap);
		PayReqUtils.bindCell(table, 2, 3, "结算金额本币", PayRequestBillContants.SETTLEAMT, bindCellMap);
		table.getCell(4, 3).setValue("本币");
		//本期欠付款
		PayReqUtils.bindCell(table, 13, 3, getRes("curBackPay"), PayRequestBillContants.CURBACKPAY, bindCellMap);
		
		//累计申请％
		//table.getCell(14, 3).setValue(getRes("allReqPercent"));
		PayReqUtils.bindCell(table, 14, 3, getRes("allReqPercent"), PayRequestBillContants.ALLREQPERCENT, bindCellMap);
		
		//累计应付申请％
		 table.getCell(15, 3).setValue("累计应付申请%");
		//		PayReqUtils.bindCell(table, 15, 3, "累计应付申请%", PayRequestBillContants.ALLREQPERCENT, bindCellMap);

		// 第五列
		//截至上期累计申请
		table.getCell(3, 4).setValue(getRes("lstAllReq"));
		table.getCell(4, 4).setValue("原币");
		
		//第六列
		//截至上期累计申请（本币）
		table.getCell(4, 5).setValue("本币");
		
		//本次申请
		table.getCell(3, 6).setValue(getRes("curOccur"));
		//本期申请(原币)
		table.getCell(4, 6).setValue("原币");
		//形象进度
		PayReqUtils.bindCell(table, 14, 6, getRes("imageSchedule"), PayRequestBillContants.IMAGESCHEDULE, bindCellMap);
	
		// 第八列
		//本期申请(本币)
		table.getCell(4, 7).setValue("本币");

		//第九列
		//截至本期累计申请
		table.getCell(3, 8).setValue(getRes("curAllReq"));
		//原币
		table.getCell(4, 8).setValue("原币");
		
		// 第十列
		//合同造价本币
		PayReqUtils.bindCell(table, 0, 9, "合同造价本币", PayRequestBillContants.CONTRACTPRICE, bindCellMap);
		//最新造价本币
		PayReqUtils.bindCell(table, 1, 9, "最新造价本币", PayRequestBillContants.LATESTPRICE, bindCellMap);
		//本申请单已付本币
		PayReqUtils.bindCell(table, 2, 9, "本申请单已付本币", PayRequestBillContants.PAYEDAMT, bindCellMap);	

		table.getCell(4, 9).setValue("本币");
		
		//第十一列
		//截至本期累计实付
		table.getCell(3, 10).setValue(getRes("curAllPaid"));
		table.getCell(4, 10).setValue("原币");
		
		//第十二列
		//截至本期累计实付(本币)
		table.getCell(4, 11).setValue("本币");

		/*
		 * 特殊数据绑定进度款，甲供材,实付款
		 */
		// 进度款项,
		//合同内工程款
		row = table.getRow(5);
		cell = row.getCell(3);// 上期累计实付本币
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTPRJALLPAIDAMT, bindCellMap);
		
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		cell = row.getCell(4);////合同内工程款上期累计申请原币
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTPRJALLREQORGAMT, bindCellMap);
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */
		
		cell = row.getCell(5);// 上期累计申请本币
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTPRJALLREQAMT, bindCellMap);
		cell = row.getCell(6);// 本次申请（原币）
		cell.getStyleAttributes().setLocked(false);
		PayReqUtils.bindCell(cell, PayRequestBillContants.PROJECTPRICEINCONTRACTORI, bindCellMap, true);
		cell = row.getCell(7);// 本次申请(本币)
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, PayRequestBillContants.PROJECTPRICEINCONTRACT, bindCellMap, true);
		cell = row.getCell(9);// 截至本期累计申请（本币）
		PayReqUtils.bindCell(cell, PayRequestBillContants.PRJALLREQAMT, bindCellMap);
		
		//预付款
		row = table.getRow(6);
		cell = row.getCell(3);// 上期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADVANCEALLPAID, bindCellMap);
		cell = row.getCell(5);// 上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADVANCEALLREQ, bindCellMap);
		cell = row.getCell(6);// 发生额
		cell.getStyleAttributes().setLocked(false);
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADVANCE, bindCellMap, true);
		cell = row.getCell(7);// 发生额(本币)
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, PayRequestBillContants.LOCALADVANCE, bindCellMap, true);
		cell = row.getCell(9);// 累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADVANCEALLREQ, bindCellMap);
		cell = row.getCell(11);// 累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADVANCEALLPAID, bindCellMap);
		
		//增加工程款（隐藏）
		row = table.getRow(7);
		cell = row.getCell(3);// 合同内增加工程款累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADDPRJALLPAIDAMT, bindCellMap);
		cell = row.getCell(5);// 上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADDPRJALLREQAMT, bindCellMap);
		cell = row.getCell(6);// 发生额
		cell.getStyleAttributes().setLocked(false);
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADDPROJECTAMTORI, bindCellMap, true);
		cell = row.getCell(7);// 发生额(本币)
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADDPROJECTAMT, bindCellMap, true);
		cell = row.getCell(9);// 累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADDPRJALLREQAMT, bindCellMap);

		// 奖励单
		row = table.getRow(8);
		cell = row.getCell(3);// 奖励单累计实付
		PayReqUtils.bindCell(cell, "lstGuerdonPaidAmt", bindCellMap);
		cell = row.getCell(5);// 奖励单上期累计申请
		PayReqUtils.bindCell(cell, "lstGuerdonReqAmt", bindCellMap);
		cell = row.getCell(6);// 奖励单发生额
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "guerdonOriginalAmt", bindCellMap, true);
		cell = row.getCell(7);// 奖励单发生额（本币）
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "guerdonAmt", bindCellMap, true);
		cell = row.getCell(9);// 奖励单累计申请
		PayReqUtils.bindCell(cell, "allGuerdonAmt", bindCellMap);
		
		//违约金
		row = table.getRow(9);
		cell = row.getCell(3);// 违约金累计实付
		PayReqUtils.bindCell(cell, "lstCompensationPaidAmt", bindCellMap);
		cell = row.getCell(5);// 违约金上期累计申请
		PayReqUtils.bindCell(cell, "lstCompensationReqAmt", bindCellMap);
		cell = row.getCell(6);// 违约金发生额
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "compensationOriginalAmt", bindCellMap, true);
		cell = row.getCell(7);// 违约金发生额本币
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "compensationAmt", bindCellMap, true);
		cell = row.getCell(9);// 违约金累计申请
		PayReqUtils.bindCell(cell, "allCompensationAmt", bindCellMap);
				
		// 甲供材
		row = table.getRow(10);
		cell = row.getCell(3);// 截至上期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTAMATLALLPAIDAMT, bindCellMap);
		cell = row.getCell(5);// 截至上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTAMATLALLREQAMT, bindCellMap);
		cell = row.getCell(6);// 发生额原币
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLAMTORI, bindCellMap, true);
		cell = row.getCell(7);// 发生额本币
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLAMT, bindCellMap, true);
		cell = row.getCell(9);//本期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLALLREQAMT, bindCellMap);
		cell = row.getCell(11);//本期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLALLPAIDAMT, bindCellMap);
		
		// 实付款
		row = table.getRow(11);
		cell = row.getCell(6); //本次申请原币
		PayReqUtils.bindCell(cell, PayRequestBillContants.CURPAID, bindCellMap, true);
		cell = row.getCell(7); //本次申请本币
		PayReqUtils.bindCell(cell, PayRequestBillContants.CURPAIDLOCAL, bindCellMap, true);

		addOrgPriceForEntryTable(table);
		// paytimes
		StyleAttributes sa = ((ICell) bindCellMap.get(PayRequestBillContants.PAYTIMES)).getStyleAttributes();
		// 格式化付款次数
		sa.setNumberFormat("###,##0");
		// 形象进度
		sa = ((ICell) bindCellMap.get(PayRequestBillContants.IMAGESCHEDULE)).getStyleAttributes();
		KDFormattedTextField txt = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		txt.setPrecision(2);
		txt.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txt.setMinimumValue(FDCHelper._ONE_HUNDRED);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
		((ICell) bindCellMap.get(PayRequestBillContants.IMAGESCHEDULE)).setEditor(editor);
		sa.setLocked(false);

		/*
		 * 融合表格
		 */
		KDTMergeManager mm = table.getMergeManager();

		// 第一行
		mm.mergeBlock(0, 1, 0, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(0, 7, 0, 8, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(0, 10, 0, 11, KDTMergeManager.SPECIFY_MERGE);
		//第二行
		mm.mergeBlock(1, 1, 1, 2, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 4, 1, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 7, 1, 8, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 10, 1, 11, KDTMergeManager.SPECIFY_MERGE);

		//第三行
		mm.mergeBlock(2, 1, 2, 2, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 4, 2, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 7, 2, 8, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 10, 2, 11, KDTMergeManager.SPECIFY_MERGE);
		
		//第四行
		mm.mergeBlock(3, 0, 4, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(3, 1, 4, 1, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(3, 2, 3, 3, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(3, 4, 3, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(3, 6, 3, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(3, 8, 3, 9, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(3, 10, 3, 11, KDTMergeManager.SPECIFY_MERGE);
		
		// 进度款项
		mm.mergeBlock(5, 0, 9, 0, KDTMergeManager.SPECIFY_MERGE);
		// 应扣甲供材款
		mm.mergeBlock(10, 0, 10, 1, KDTMergeManager.SPECIFY_MERGE);
		// 实付款
		mm.mergeBlock(11, 0, 11, 1, KDTMergeManager.SPECIFY_MERGE);
		// 余款
		mm.mergeBlock(12, 0, 12, 9, KDTMergeManager.SPECIFY_MERGE);
		// 最後三行
		mm.mergeBlock(13, 1, 13, 2, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(13, 4, 13, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(13, 7, 13, 11, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(14, 1, 14, 2, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(14, 4, 14, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(14, 7, 14, 11, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(15, 1, 15, 2, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(15, 4, 15, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(15, 7, 15, 11, KDTMergeManager.SPECIFY_MERGE);
		mm = null;

		//added by ken...本次申请-预付款 不可编辑
		table.getCell(6,6).getStyleAttributes().setLocked(true);
	}

	/**
	 * description		根据最新需求，添加金额原币
	 * @author			蒲磊<p>	
	 * @createDate		2011-9-6<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void addOrgPriceForEntryTable(KDTable table){ 
		//第七列
		//合同造价原币
		PayReqUtils.bindCell(table, 0, 6,"合同造价原币" , PayRequestBillContants.CONTRACTORGPRICE, bindCellMap);
		PayReqUtils.bindCell(table, 1, 6,"最新造价原币" , PayRequestBillContants.LATESTORGPRICE, bindCellMap);
		PayReqUtils.bindCell(table, 2, 6,"本申请单已付原币" , PayRequestBillContants.PAYEDORGAMT, bindCellMap);
		
		//变更指令原币
		PayReqUtils.bindCell(table, 1, 0,"变更指令金额原币" , PayRequestBillContants.CHANGEORGAMT, bindCellMap);
		//结算金额原币
		PayReqUtils.bindCell(table, 2, 0,"结算金额原币" , PayRequestBillContants.SETTLEORGAMT, bindCellMap);

	}
	/**
	 * 初始化分录的动态部分,即应扣款项, 1.审核前扣款类型来源于基础资料,扣款类型,
	 * 其来自扣款单与申请单关联表(DeductBillOfPayReq)的过虑值
	 * 2.审核后扣款类型与金额都来自扣款单与申请单关联表(DeductBillOfPayReq)
	 * 
	 * @author sxhong Date 2006-9-29
	 * @param table
	 * @return
	 */
	private int initDynamicTable(KDTable table,DeductTypeCollection deductTypeCollection) {
		// 在第10行插入应扣款项分录,基础资料时用循环实现
		int base = 10;// 插入的基点
		int rows = 0;
		KDTMergeManager mm = table.getMergeManager();
		IRow row;
		String contractId = editData.getContractId();
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
			return base;
		}
		
		if (FDCBillStateEnum.AUDITTED.equals(editData.getState()) || FDCBillStateEnum.AUDITTING.equals(editData.getState())) {
			// 从DeductOfPayReqBill内取出数据
			DeductOfPayReqBillInfo info = null;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection items = filter.getFilterItems();
			items.add(new FilterItemInfo("payRequestBill.id", editData.getId().toString()));
			view.setFilter(filter);
			final SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
			sorterItemInfo.setSortType(SortType.ASCEND);
			view.getSorter().add(sorterItemInfo);
			view.getSelector().add("deductType.number"); 
			view.getSelector().add("deductType.name");
			view.getSelector().add("*");
			try {

				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				rows = c.size();
				//在 【所有启用的扣款类型】中移除【已经在付款申请单中存在的扣款项】
				for (int i = 0; i < rows; i++) {
					deductTypeCollection.remove(c.get(i).getDeductType());
				}
				//循环剩下的扣款类型并构建空的付款申请单对应的扣款项
				for (int j = 0; j < deductTypeCollection.size(); j++) {
					info = new DeductOfPayReqBillInfo();
					info.setDeductType(deductTypeCollection.get(j));
					c.add(info);
				}
				rows = c.size();
				for (int i = 0; i < rows; i++) {
					info = c.get(i);
					deductTypeCollection.remove(info.getDeductType());
					this.createDeductdRow(table, base + i, info);
				}
				
			} catch (BOSException e) {
				handUIException(e);
				SysUtil.abort();
			}

		} else {
			HashMap map = getDeductData(editData,deductTypeCollection);
			if (map.size() > 0) {
				DeductTypeInfo info = null; //扣款类型
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				view.setFilter(filter);
				final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("number");
				view.getSelector().add("name");
				try {

					DeductTypeCollection c = deductTypeCollection;
					rows = c.size();
					Object[] arrays;
					for (int i = 0; i < c.size(); i++) {
						info = c.get(i);
						arrays = (Object[]) map.get(info.getId().toString());
						for (int j = 0; j < 5; j++) {
							if (arrays[j] instanceof BigDecimal && ((BigDecimal) arrays[j]).compareTo(FDCHelper.ZERO) == 0) {
								arrays[j] = null;
							}
						}
						row = table.addRow(base + i);
						row.getCell(1).setValue(info.getName());
						row.getCell(1).getStyleAttributes().setNumberFormat("@");
						row.getCell(2).setValue(arrays[4]);
						row.getCell(3).setValue(arrays[0]);
						row.getCell(4).setValue(arrays[5]);
						row.getCell(5).setValue(arrays[1]);
						row.getCell(6).setValue(arrays[3]);
						row.getCell(7).setValue(arrays[2]);
						
						row.getCell(8).setExpressions("=E" + (base + i + 1) + "+G" + (base + i + 1)); //本期累计申请原币 = 上期累计 + 本次申请
						row.getCell(9).setExpressions("=F" + (base + i + 1) + "+H" + (base + i + 1));
						
						// by tim_gao 没有实付为0
						row.getCell(10).setExpressions("=IF(SUM(K6)>0,(C" + (base + i + 1) + "+G" + (base + i + 1)+"),0"+")");
//						row.getCell(10).setExpressions("=IF(SUM(I6)<>0,1,0)");
//						row.getCell(10).setExpressions("=SUM(I6)");
//						row.getCell(10).setExpressions("=IF(SUM(E10)<>0,0,0)");
						
						row.getCell(11).setExpressions("=IF(SUM(L6)>0,(D" + (base + i + 1) + "+H" + (base + i + 1)+"),0"+")");
//					row.getCell(11).setExpressions("=C" + (base + i + 1) + "+G" + (base + i + 1)); //原币
//					row.getCell(11).setExpressions("=IF(SUM(G" + (base + i + 1)+")=0,0,D" + (base + i + 1) + "+H" + (base + i + 1)+")");
//						
//						row.getCell(10).setExpressions("=C" + (base + i + 1) + "+G" + (base + i + 1)); //原币
//						row.getCell(11).setExpressions("=D" + (base + i + 1) + "+H" + (base + i + 1));
					}
				} catch (Exception e) {
					handUIException(e);
					SysUtil.abort();
				}
			}
		}

		// 最后一行小计
		int lastRowIdx = base + rows;
		row = table.addRow(lastRowIdx);
		row.getCell(1).setValue(getRes("subtotal"));
		if(rows != 0){
			/*
			 * 小计计算
			 */
			StringBuffer exp;
			for (char c = 'C'; c <= 'L'; c++) {
				exp = new StringBuffer("=sum(");
				exp.append(c).append(base + 1).append(':');
				exp.append(c).append(lastRowIdx);
				exp.append(')');
				row.getCell(c - 'A').setExpressions(exp.toString());
			}
			table.getCell(base, 0).setValue(getRes("deductAmtItem"));
			mm.mergeBlock(base, 0, base + rows, 0, KDTMergeManager.SPECIFY_MERGE);
		}

		return lastRowIdx;
	}

	/**
	 * 描述：创建扣款行
	 * @param table
	 * @param index
	 * @param info
	 * @param i
	 * @Author：jian_cao
	 * @CreateTime：2012-12-18
	 */
	private void createDeductdRow(KDTable table, int index, DeductOfPayReqBillInfo info) {
		IRow row;
		// 把显示零的地方变成空
		if (info.getAllPaidAmt() != null && info.getAllPaidAmt().compareTo(FDCHelper.ZERO) == 0) {
			info.setAllPaidAmt(null);
		}
		if (info.getAllReqAmt() != null && info.getAllReqAmt().compareTo(FDCHelper.ZERO) == 0) {
			info.setAllReqAmt(null);
		}
		if (info.getAmount() != null && info.getAmount().compareTo(FDCHelper.ZERO) == 0) {
			info.setAmount(null);
		}
		if (info.getOriginalAmount() != null && info.getOriginalAmount().compareTo(FDCHelper.ZERO) == 0) {
			info.setOriginalAmount(null);
		}
		if (info.getAllReqOriAmt() != null && info.getAllReqOriAmt().compareTo(FDCHelper.ZERO) == 0) {
			info.setAllReqOriAmt(null);
		}
		if (info.getAllPaidOriAmt() != null && info.getAllPaidOriAmt().compareTo(FDCHelper.ZERO) == 0) {
			info.setAllPaidOriAmt(null);
		}
		row = table.addRow(index);
		row.getCell(1).setValue(info.getDeductType().getName());
		row.getCell(1).getStyleAttributes().setNumberFormat("@");
		row.getCell(3).setValue(info.getAllPaidAmt());
		row.getCell(2).setValue(info.getAllPaidOriAmt());
		row.getCell(4).setValue(info.getAllReqOriAmt());
		row.getCell(5).setValue(info.getAllReqAmt());
		row.getCell(6).setValue(info.getOriginalAmount());
		row.getCell(7).setValue(info.getAmount());
		row.getCell(8).setExpressions("=E" + (index + 1) + "+G" + (index + 1)); //本期累计申请原币 = 上期累计 + 本次申请
		row.getCell(9).setExpressions("=F" + (index + 1) + "+H" + (index + 1));
		//modified by ken_liu...BT717916: 截止本期累计实付不加上本次申请付款.
		row.getCell(10).setExpressions("=IF(SUM(K6)>0,C" + (index + 1) + ",0" + ")");
		row.getCell(11).setExpressions("=IF(SUM(L6)>0,D" + (index + 1) + ",0" + ")");
	}

	/**
	 * 分录的计算逻辑
	 * 
	 * @author sxhong Date 2007-4-5
	 * @param table
	 * @param lastRowIdx
	 */
	void calcTable() {

		/*
		 * 设置统计公式--进度款项,用sum内置函数来进行计算,用法类似于Excel 如sum(D3，D7);
		 */
		IRow row = null;
		// 合同内工程数
		row = table.getRow(5);
		row.getCell(8).setExpressions("=sum(E6,G6)"); //截至本期累计申请(原币)
		//row.getCell(10).setExpressions("=sum(C6,G6)"); //截至本期累计实付（原币）
		
		/* modified by zhaoqin for R140319-0098 on 2014/03/21 start */
		//row.getCell(10).setExpressions("=sum(C6,H3)"); //截至本期累计实付（原币）
		row.getCell(9).setExpressions("=sum(F6,H6)"); //截至本期累计申请(本币)
		//row.getCell(11).setExpressions("=sum(D6,H6)"); //截至本期累计实付（本币）
		//row.getCell(11).setExpressions("=sum(D6,K3)"); //截至本期累计实付（本币）
		/* modified by zhaoqin for R140319-0098 on 2014/03/21 end */
		
		// 预付款
		row = table.getRow(6);
		row.getCell(8).setExpressions("=sum(E7,G7)"); //截至本期累计申请
//		row.getCell(10).setExpressions("=sum(C7,G7)"); //截至本期累计实付
		row.getCell(10).setExpressions("=sum(C7)"); //截至本期累计实付
		row.getCell(9).setExpressions("=sum(F7,H7)"); //截至本期累计申请(本币)
//		row.getCell(11).setExpressions("=sum(D7,H7)"); //截至本期累计实付（本币）
		row.getCell(11).setExpressions("=sum(D7)"); //截至本期累计实付（本币）

		// 增加工程款
		row = table.getRow(7);
		row.getCell(8).setExpressions("=sum(E8,G8)"); //截至本期累计申请
//		row.getCell(10).setExpressions("=sum(C8,G8)"); //截至本期累计实付
		row.getCell(10).setExpressions("=sum(C8)"); //截至本期累计实付
		row.getCell(9).setExpressions("=sum(F8,H8)"); //截至本期累计申请(本币)
//		row.getCell(11).setExpressions("=sum(D8,H8)"); //截至本期累计实付（本币）
		row.getCell(11).setExpressions("=sum(D8)"); //截至本期累计实付（本币）

		// 奖励
		row = table.getRow(8);
		row.getCell(8).setExpressions("=sum(E9,G9)"); //截至本期累计申请
//		row.getCell(10).setExpressions("=sum(C9,G9)"); //截至本期累计实付
		row.getCell(10).setExpressions("=sum(C9)"); //截至本期累计实付
		row.getCell(9).setExpressions("=sum(F9,H9)"); //截至本期累计申请(本币)
//		row.getCell(11).setExpressions("=sum(D9,H9)"); //截至本期累计实付（本币）
		row.getCell(11).setExpressions("=sum(D9)"); //截至本期累计实付（本币）

		// 违约金
		row = table.getRow(9);
		row.getCell(8).setExpressions("=sum(E10,G10)"); //截至本期累计申请
//		row.getCell(10).setExpressions("=sum(C10,G10)"); //截至本期累计实付
		row.getCell(10).setExpressions("=sum(C10)"); //截至本期累计实付
		row.getCell(9).setExpressions("=sum(F10,H10)"); //截至本期累计申请(本币)
//		row.getCell(11).setExpressions("=sum(D10,H10)"); //截至本期累计实付（本币）
		row.getCell(11).setExpressions("=sum(D10)"); //截至本期累计实付（本币）


		int lastRowIdx = table.getRowCount() - 1; 
		/*
		 * 甲供材款累计
		 */
		row = table.getRow(lastRowIdx - 5);
		// 应扣甲供材款”行、“本次申请原币”列应自动从款项调整中甲供扣款的“本次扣款金额”合计而来，而且不可编辑 by cassiel
		row.getCell(6).getStyleAttributes().setLocked(true);
		row.getCell(8).setExpressions("=E" + (lastRowIdx - 4) + "+G" + (lastRowIdx - 4));
		row.getCell(9).setExpressions("=F" + (lastRowIdx - 4) + "+H" + (lastRowIdx - 4));
//		row.getCell(10).setExpressions("=(C" + (lastRowIdx - 4) + "+G" + (lastRowIdx - 4) + ")");
		row.getCell(10).setExpressions("=(C" + (lastRowIdx - 4)+")");
//		row.getCell(11).setExpressions("=(D" + (lastRowIdx - 4) + "+H" + (lastRowIdx - 4) + ")");
		row.getCell(11).setExpressions("=(D" + (lastRowIdx - 4)+")");

		/*实付款
		 * 因为要初化完后才能定位应扣款项小计的位置,故放到这里来计算. 实付款 实付款＝进度款小计－应扣款项小计-应扣甲供材
		 * 调整为:实付款＝合同内工程款+预付款+奖励-违约金-应扣款项小计-应扣甲供材 by sxhong 2007/09/28
		 */
		int paidRowIdx = lastRowIdx - 4;
		row = table.getRow(paidRowIdx);
		ICell cell = null;
		StringBuffer exp;
		for (char c = 'C'; c <= 'L'; c++) {
			cell = row.getCell(c - 'A');
			exp = new StringBuffer("=");
			exp.append(c).append(6).append("+");			//合同内工程款
			exp.append(c).append(7).append("+");            //预付款
			exp.append(c).append(9).append("-");			//奖励
			exp.append(c).append(10).append("-");			//违约金
			exp.append(c).append(paidRowIdx).append("-");//甲供材
			exp.append(c).append(paidRowIdx - 1);				//应扣款小计
			cell.setExpressions(exp.toString());
		}
		
		/*
		 * 余款 余款＝最新造价－进度款小计
		 * 调整为 余款＝最新造价－合同内工程款 by sxhong 2007-9-28
		 * 调整为 余款＝最新造价－合同内工程款-预付款 by hpw 2009-7-22
		 * 调整为 余款＝最新造价－实付款小计  by hpw 2009-7-24
		 *  余额=最新造价-进度款项截止本期累计实付 by cassiel_peng 2010-05-28
		 *  
		 *  by tim_gao 2011年12月2日 调整为最新造价－合同内工程款
		 */
//		table.getCell(paidRowIdx + 1, 6).setExpressions("=F2-G8");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-(H5+H6)");
//		table.getCell(paidRowIdx + 1, 6).setExpressions("=G2-(H5+H6)");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-H"+(lastRowIdx-3));
		table.getCell(paidRowIdx + 1, 11).setExpressions("=K2-L6"); //本币
		table.getCell(paidRowIdx + 1, 10).setExpressions("=H2-K6"); //原币

		//本次申请%
		row = table.getRow(lastRowIdx-1);
		// 最新造价可能为零,不能直接使用计算公式的方法
		if (editData.getLatestPrice() != null && editData.getLatestPrice().compareTo(FDCHelper.ZERO) > 0) {
			//本次申请%=本期发生实付款/最新造价
			exp = new StringBuffer("=(");
			exp.append("H").append(6).append("/");
			exp.append("K2)*100");
			row.getCell(1).setExpressions(exp.toString());
			/*
			 * 累计申请%=本期累计申请/最新造价
			 */
			exp = new StringBuffer("=(");
			exp.append("J").append(6).append("/");
			exp.append("K2)*100");
			row.getCell(4).setExpressions(exp.toString());

			// 应付申请，应付累计申请
			row = table.getRow(lastRowIdx);
			exp = new StringBuffer("=(");
			exp.append("H").append(lastRowIdx-3).append("/");
			exp.append("K2)*100");
			row.getCell(1).setExpressions(exp.toString());
			
			exp = new StringBuffer("=(");
			exp.append("J").append(lastRowIdx-3).append("/");
			exp.append("K2)*100");
			row.getCell(4).setExpressions(exp.toString());
		}

		/**
		 * 形象进度
		 */
		
//		/**
//		 * 所有的本期累计实付等于上期累计实付 ？？？第一次加载的时候 貌似有点问题
//		 */
//		if (null == editData.getState()) {
//			for (int i = 5; i < lastRowIdx - 4; i++) {
//				table.getCell(i, 11).setExpressions("=D" + (i + 1));
//			}
//		}
	}

	/**
	 * 设置表格单元格的可编辑状态及颜色
	 * 
	 * @author sxhong Date 2006-9-28
	 */
	void setTableCellColorAndEdit(KDTable table) {

		// 设置计划付款行可以录入

		IRow row;
		ICell cell;
		int lastRowIdx = table.getRowCount() - 1;
		table.setRefresh(false);
		table.setEditable(true);
		table.setEnabled(true);
		StyleAttributes sa;
		sa = table.getStyleAttributes();
//		sa.setBackground(Color.WHITE);
		sa.setBackground(noEditColor);
		/*
		 * 可编辑单元格
		 */

		// 本期发生
		table.getCell(5, 6).getStyleAttributes().setLocked(false);
		table.getCell(5, 6).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
//		table.getCell(6, 6).getStyleAttributes().setLocked(false);  modified by ken_liu...不可编辑
//		// 甲供材
//		table.getCell(lastRowIdx - 4, 6).getStyleAttributes().setLocked(true);
//
//		row = table.getRow(lastRowIdx - 1);
//		row.getCell(1).getStyleAttributes().setLocked(false);
//		row.getCell(4).getStyleAttributes().setLocked(false);
//		row.getCell(6).getStyleAttributes().setLocked(false);
//		table.getCell(lastRowIdx, 6).getStyleAttributes().setLocked(false);
//
//		/*
//		 * 颜色
//		 */
//		for (int i = 5; i < table.getRowCount() - 2; i++) {
//			row = table.getRow(i);
//			for (int j = 2; j < table.getColumnCount(); j++) {
//				cell = row.getCell(j);
//				sa = cell.getStyleAttributes();
//				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
//				if (sa.isLocked()) {
//					sa.setBackground(noEditColor);
//				}
//			}
//		}
//		table.getCell(0, 6).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(1, 6).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(2, 6).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(2, 1).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(2, 3).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(lastRowIdx, 1).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(lastRowIdx, 3).getStyleAttributes().setBackground(noEditColor);
//
//		// 付款次数
//		table.getCell(3, 1).getStyleAttributes().setBackground(noEditColor);
//		table.setRefresh(true);
//		editUI.btnInputCollect.setEnabled(true);
//		// table.repaint();
//		// table.refresh();
//		
//		//去掉付款计划的格:
//		for (int i = 5; i < table.getColumnCount(); i++) {
//			StyleAttributes styleAttributes = table.getCell(lastRowIdx, i).getStyleAttributes();
//			styleAttributes.setLocked(true);
//			styleAttributes.setBackground(noEditColor);
//			if(i==6){
//				continue;
//			}
//			styleAttributes = table.getCell(lastRowIdx - 1, i).getStyleAttributes();
//			styleAttributes.setLocked(true);
//			styleAttributes.setBackground(noEditColor);
//		}
//		table.getCell(lastRowIdx-1, 1).getStyleAttributes().setLocked(true);
//		table.getCell(lastRowIdx-1, 1).getStyleAttributes().setBackground(noEditColor);
//		table.getCell(lastRowIdx-1, 3).getStyleAttributes().setLocked(true);
//		table.getCell(lastRowIdx-1, 3).getStyleAttributes().setBackground(noEditColor);
	}

	/**
	 * 重载扣款单
	 * @param editData
	 * @param table
	 * @throws Exception
	 */
	void reloadDeductTable(PayRequestBillInfo editData,KDTable table,DeductTypeCollection c) throws Exception {
		int base = 10;
		IRow row;
		HashMap map = getDeductData(editData,c);
		if (map.size() > 0) {
			DeductTypeInfo info = null;

			try{
				Object[] arrays;
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					arrays = (Object[]) map.get(info.getId().toString());
					for (int j = 0; j < 4; j++) {
						if (arrays[j] instanceof BigDecimal && ((BigDecimal) arrays[j]).compareTo(FDCHelper.ZERO) == 0) {
							arrays[j] = null;
						}
					}
					row = table.getRow(base + i);
					if(row == null) return;
					row.getCell(1).setValue(info.getName());
					row.getCell(1).getStyleAttributes().setNumberFormat("@");
					row.getCell(2).setValue(arrays[4]);
					row.getCell(3).setValue(arrays[0]);
					row.getCell(4).setValue(arrays[5]);
					row.getCell(5).setValue(arrays[1]);
					row.getCell(6).setValue(arrays[3]);
					row.getCell(7).setValue(arrays[2]);
				}
			} catch (Exception e) {
				handUIException(e);
				SysUtil.abort();
			}
		}
	}

	/**
	 * 为没有审核过的单据得到应扣款项的一个Map，其中： Key为DeductTypeInfo的ID Value 为Object[3]保存
	 * 0实付额,1累计额,2发生额,3发生额原币，4累计实付原币，5累计申请原币
	 * 
	 * @author sxhong Date 2006-10-26
	 * @return
	 */
	private HashMap getDeductData(PayRequestBillInfo editData,DeductTypeCollection deductTypeCollection) {
		String contractId = editData.getContractId();
		if (contractId == null) return null;

		HashMap map = new HashMap();
		EntityViewInfo view;
		FilterInfo filter;
		FilterItemCollection items;

		/*
		 * 
		 */
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");

		try {
			DeductTypeInfo info = null;
			DeductTypeCollection c = deductTypeCollection;
			for (int i = 0; i < c.size(); i++) {
				// new Object[6]保存 0实付额本币,1累计额本币,2发生额,3发生额原币，4累计实付原币，5累计申请原币
				info = c.get(i);
				Object[] o = new Object[6];
				map.put(info.getId().toString(), o);
			}
		} catch (Exception e) {
			handUIException(e);
			SysUtil.abort();
		}

		if (map.size() <= 0) {
			return map;
		}

		if (editUI.getOprtState().equals(OprtState.ADDNEW)) {
			/*
			 * 发生额,从DeductBillEntry过滤,过滤出已用扣款单放入notIncludeSet内
			 */
			Set notIncludeSet = new HashSet();
			view = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
			FilterItemCollection items2 = filter2.getFilterItems();
			items2.add(new FilterItemInfo("parent.PayRequestBill.contractId", contractId, CompareType.EQUALS));
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("deductBillEntry.id");
			view.setFilter(filter2);

			try {// 要过滤的集合
				DeductOfPayReqBillEntryCollection c;
				c = DeductOfPayReqBillEntryFactory.getRemoteInstance().getDeductOfPayReqBillEntryCollection(view);
				DeductOfPayReqBillEntryInfo info;
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					notIncludeSet.add(info.getDeductBillEntry().getId().toString());
				}
			} catch (BOSException e1) {
				handUIException(e1);
				SysUtil.abort();
			}

			view = new EntityViewInfo();
			filter = new FilterInfo();
			//items = filter.getFilterItems();
			items = filter.getFilterItems();
			items.add(new FilterItemInfo("contractId", contractId, CompareType.EQUALS));
			items.add(new FilterItemInfo("hasApplied", Boolean.FALSE, CompareType.EQUALS));
			items.add(new FilterItemInfo("Parent.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			if (notIncludeSet.size() > 0) {
				items.add(new FilterItemInfo("id", notIncludeSet, CompareType.NOTINCLUDE));
			}
			view.setFilter(filter);
			try {
				DeductBillEntryInfo info;
				DeductBillEntryCollection c = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view);
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					Object o = map.get(info.getDeductType().getId().toString());
					if (o != null) {
						Object[] arrays = (Object[]) o;
						BigDecimal v;
						// 发生额
						if (arrays[2] == null) {
							arrays[2] = info.getDeductAmt();
						} else {
							v = (BigDecimal) arrays[2];
							arrays[2] = v.add(info.getDeductAmt());
						}
						
						
						// 发生额原币
						if (arrays[3] == null) {
							arrays[3] = info.getDeductOriginalAmt();
						} else {
							v = (BigDecimal) arrays[3];
							arrays[3] = v.add(info.getDeductOriginalAmt());
						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
				SysUtil.abort();
			}
			/*
			 * 累计额:实付,申请
			 */
			view = new EntityViewInfo();
			filter = new FilterInfo();
			items = filter.getFilterItems();
			items = filter.getFilterItems();
			items.add(new FilterItemInfo("payRequestBill.contractId", contractId, CompareType.EQUALS));
			view.setFilter(filter);
			try {
				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				DeductOfPayReqBillInfo info;
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					Object o = map.get(info.getDeductType().getId().toString());
					if (o != null) {
						Object[] arrays = (Object[]) o;
						BigDecimal v;
						BigDecimal amount = info.getAmount();
						/*
						 * 申请,是以前的所有发生额之和
						 */
						if (arrays[1] == null) {
							arrays[1] = amount;
						} else {
							v = (BigDecimal) arrays[1];
							arrays[1] = FDCNumberHelper.add(v, amount);
						}
						//by tim_gao 申请的原币
						BigDecimal ov ;
						BigDecimal oriAmount = info.getOriginalAmount();
						if (arrays[5] == null) {
							arrays[5] = oriAmount;
						} else {
							ov = (BigDecimal) arrays[5];
							arrays[5] = FDCNumberHelper.add(ov, oriAmount);
						}
						/*
						 * 实付,是以前的所有已实付的发生额之和
						 */
						if (!info.isHasPaid()) {
							continue;
						}

						if (arrays[0] == null) {
							arrays[0] = amount;
						} else {
							v = (BigDecimal) arrays[0];
							arrays[0] = FDCNumberHelper.add(v, amount);
						}
						
						//by tim_gao 申请的原币
						
						if (arrays[4] == null) {
							arrays[4] = oriAmount;
						} else {
							ov = (BigDecimal) arrays[4];
							arrays[4] = FDCNumberHelper.add(ov, oriAmount);
						}

					}
				}

			} catch (BOSException e) {
				handUIException(e);
				SysUtil.abort();
			}

		} else {// 新增结束
			// 取数据之前重新算一次
			try {
				DeductOfPayReqBillFactory.getRemoteInstance().reCalcAmount(editData.getId().toString());
			} catch (Exception e1) {
				handUIException(e1);
				SysUtil.abort();
			}
			/*
			 * 保存后数据直接从DeductOfPayReqBill内取 累计额:实付,申请,发生额,发生额原币
			 */
			view = new EntityViewInfo();
			filter = new FilterInfo();
			items = filter.getFilterItems();
			items = filter.getFilterItems();
			// items.add(new
			// FilterItemInfo("payRequestBill.contractId",contractId,CompareType.EQUALS));
			items.add(new FilterItemInfo("payRequestBill.id", editData.getId().toString(), CompareType.EQUALS));
			view.setFilter(filter);
			try {
				DeductOfPayReqBillInfo info;
				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					Object o = map.get(info.getDeductType().getId().toString());
					if (o != null) {
						Object[] arrays = (Object[]) o;
						
						/*
						 * 累计申请原币
						 */
						arrays[5] = info.getAllReqOriAmt();
						/*
						 * 累计实付原币
						 */
						arrays[4] = info.getAllPaidOriAmt();
						/*
						 * 发生额原币
						 */
						arrays[3] = info.getOriginalAmount();
						/*
						 * 发生额
						 */
						arrays[2] = info.getAmount();

						/*
						 * 申请
						 */
						arrays[1] = info.getAllReqAmt();

						/*
						 * 实付
						 */
						arrays[0] = info.getAllPaidAmt();

					}
				}
			} catch (BOSException e) {
				handUIException(e);
				SysUtil.abort();
			}

		}
		return map;
	}

	
	
	/**
	 * 在新增,保存时动态更新值 最新造价,结算金额,变更金额等
	 * @author sxhong  		Date 2006-9-22
	 */
	void updateDynamicValue (PayRequestBillInfo editData,ContractBillInfo contractBill,
			ContractChangeBillCollection collection, BillBaseCollection billBaseCollection) throws Exception{
		boolean isUpdateCell = bindCellMap.get(PayRequestBillContants.CHANGEAMT) != null;
		//ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(editData.getContractId())));
		BigDecimal amount = FDCHelper.ZERO; //本币
		BigDecimal orgAmount = FDCHelper.ZERO; //原币

		ContractChangeBillInfo billInfo;
		//变更金额累计=未结算变更+已结算变更
		for (Iterator iter = collection.iterator(); iter.hasNext();)
		{
			billInfo = (ContractChangeBillInfo) iter.next();
			if(billInfo.isHasSettled()){
				amount = amount.add(FDCHelper.toBigDecimal(billInfo.getBalanceAmount()));
				orgAmount = orgAmount.add(FDCHelper.toBigDecimal(billInfo.getOriBalanceAmount()));
			}else{
				amount = amount.add(FDCHelper.toBigDecimal(billInfo.getAmount()));
				orgAmount = orgAmount.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
			}
		}
		
		editData.setChangeAmt(amount);
		if (isUpdateCell) {
			//设置变更签证金额本币
			if(amount!= null
					&& amount.compareTo(FDCHelper.ZERO) != 0){
				((ICell)bindCellMap.get(PayRequestBillContants.CHANGEAMT)).setValue(amount);
			}else{
				((ICell)bindCellMap.get(PayRequestBillContants.CHANGEAMT)).setValue(null);
			}	
			//设置变更签证金额原币
			if(orgAmount!= null
					&& orgAmount.compareTo(FDCHelper.ZERO) != 0){
				((ICell)bindCellMap.get(PayRequestBillContants.CHANGEORGAMT)).setValue(orgAmount);
			}else{
				((ICell)bindCellMap.get(PayRequestBillContants.CHANGEORGAMT)).setValue(null);
			}			
		}
		
		editData.setSettleAmt(contractBill.getSettleAmt());
		if (isUpdateCell) {
			// 结算金额
			if(contractBill.getSettleAmt()!= null
//					&& contractBill.getSettleAmt().compareTo(FDCHelper.ZERO) != 0
					){
				((ICell)bindCellMap.get(PayRequestBillContants.SETTLEAMT)).setValue(contractBill.getSettleAmt());
			}else{
				((ICell)bindCellMap.get(PayRequestBillContants.SETTLEAMT)).setValue(null);
			}
			
		}
		
		amount =FDCHelper.ZERO;
		
		// 根据结算单的状态来设置最新造价的值,已结算就为结算额
		if (!contractBill.isHasSettled())
		{
			/*
			 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
			 * by sxhong 2007/09/28
			 */
			//奖励
/*			直接用SQL
 			BigDecimal guerdonAmt = FDCHelper.ZERO;
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBill.getId()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isGuerdoned", Boolean.TRUE));
			view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("amount");
			GuerdonBillCollection coll = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
			GuerdonBillInfo info;
			for (Iterator iter = coll.iterator(); iter.hasNext();)
			{
				info = (GuerdonBillInfo) iter.next();
				guerdonAmt = guerdonAmt.add(FDCHelper.toBigDecimal(info.getAmount()));
			}
			
			//扣款,该合同下被付款申请单关联的扣款之和
			BigDecimal deductAmt = FDCHelper.ZERO;
			view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getSelector().add("amount");
			view.getFilter().appendFilterItem("payRequestBill.contractId", contractBill.getId());
			DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			for(int i=0;i<c.size();i++){
				deductAmt=deductAmt.add(FDCHelper.toBigDecimal(c.get(i).getAmount()));
			}
			//索赔
			BigDecimal compenseAmt = FDCHelper.ZERO;
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBill.getId()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isCompensated", Boolean.TRUE));
			view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("amount");
			CompensationBillCollection compenseColl = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
			CompensationBillInfo compenseInfo;
			for (Iterator iter = compenseColl.iterator(); iter.hasNext();)
			{
				compenseInfo = (CompensationBillInfo) iter.next();
				compenseAmt = compenseAmt.add(FDCHelper.toBigDecimal(compenseInfo.getAmount()));
			}*/
			//奖励
			BigDecimal guerdonAmt=FDCHelper.ZERO;
			BigDecimal guerdonOriginalAmt=FDCHelper.ZERO;
			BigDecimal compenseAmt=FDCHelper.ZERO;
			BigDecimal compenseOriginalAmt=FDCHelper.ZERO;
			BigDecimal deductAmt=FDCHelper.ZERO;
			BigDecimal deductOriginalAmt=FDCHelper.ZERO;
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1");
			builder.addParam(contractBill.getId().toString());
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				guerdonAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				guerdonOriginalAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
			}
			
			//违约
			builder.clear();
			builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1");
			builder.addParam(contractBill.getId().toString());
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				compenseAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				compenseOriginalAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
			}
			
			//扣款
			builder.clear();
			builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_DeductOfPayReqBill " +
					"where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)");
			builder.addParam(contractBill.getId().toString());
			rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				deductAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				deductOriginalAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
			}
			
			/*
			 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
			 * by sxhong 2007/09/28
			 * 
			 * 经罗忠慧确认,现系统最新造价=合同金额+变更金额
			 * by hpw 2009/05/11
			 * 
			 * 更改成用统一接口取合同最新造价
			 * 
			 */
			amount = (BigDecimal) FDCUtils.getLastAmt_Batch(null, new String[]{contractBill.getId().toString()}).get(contractBill.getId().toString());
//			amount = contractBill.getAmount().add(FDCHelper.toBigDecimal(editData.getChangeAmt()));
//			amount = amount.add(guerdonAmt).subtract(compenseAmt).subtract(deductAmt);
			
		}else{
			amount=contractBill.getSettleAmt();
		}
		
		editData.setLatestPrice(amount);
		if (isUpdateCell) {
			if(amount!= null
//					&& amount.compareTo(FDCHelper.ZERO) != 0
					){
				((ICell)bindCellMap.get(PayRequestBillContants.LATESTPRICE)).setValue(amount);
			}else{
				//editData.setLatestPrice(null);
				((ICell)bindCellMap.get(PayRequestBillContants.LATESTPRICE)).setValue(null);
			}			
		}
		
		if(isUpdateCell){
			StringBuffer exp;
			int lastRowIdx=editUI.getDetailTable().getRowCount()-1;
			IRow row=editUI.getDetailTable().getRow(lastRowIdx);
			//最新造价可能为零,不能直接使用计算公式的方法
			if(FDCHelper.isPositiveBigDecimal(editData.getLatestPrice())){
				//本次申请%=本期发生实付款/最新造价
				exp = new StringBuffer("=(");
				exp.append("H").append(6).append("/");
				exp.append("K2)*100");
				row.getCell(1).setExpressions(exp.toString());
				/*
				 * 累计申请%=本期累计申请/最新造价
				 */
				exp = new StringBuffer("=(");
				exp.append("J").append(6).append("/");
				exp.append("K2)*100");
				row.getCell(4).setExpressions(exp.toString());

				// 应付申请，应付累计申请
				row = table.getRow(lastRowIdx);
				exp = new StringBuffer("=(");
				exp.append("H").append(lastRowIdx-3).append("/");
				exp.append("K2)*100");
				row.getCell(1).setExpressions(exp.toString());
				exp = new StringBuffer("=(");
				exp.append("J").append(lastRowIdx-3).append("/");
				exp.append("K2)*100");
				row.getCell(4).setExpressions(exp.toString());
				
				
			}else{
				row.getCell(1).setValue(null);
				row.getCell(4).setValue(null);
			}
		}
		updateLstReqAmt(editData, isUpdateCell);
		
	
		
        //设置付款次数为合同的付款次数 从付款单中过滤        
        final int size = billBaseCollection.size();
        amount=FDCHelper.ZERO;
        if(editData != null && editData.getId() != null){
        	for(Iterator iter=billBaseCollection.iterator();iter.hasNext();){
        		PaymentBillInfo tmp = (PaymentBillInfo)iter.next();
        		if(tmp.getFdcPayReqID().equals(editData.getId().toString())){
        			amount = amount.add(FDCHelper.toBigDecimal(tmp.getAmount()));
        		}
        	}     	
        }
    	editData.setPayedAmt(amount);   //上期累计实付
        
		editData.setPayTimes(size);
        if(isUpdateCell){
        	((ICell)bindCellMap.get(PayRequestBillContants.PAYTIMES)).setValue(String.valueOf(size));
        	((ICell)bindCellMap.get(PayRequestBillContants.PAYEDAMT)).setValue(amount);
        }
	}

	/**
	 * description		更新合同内工程款、新增工程款、甲供材累计上期申请金额（非新增状态）
	 * @author			蒲磊<p>	
	 * @createDate		2011-9-7<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	void updateLstReqAmt(PayRequestBillInfo editData, boolean isUpdateCell) throws BOSException {
		BigDecimal lstReqAmt = FDCHelper.ZERO; //上期累计申请
		
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
		BigDecimal lstPrjAllReqOrgAmt = FDCHelper.ZERO; //截至上次累计申请原币
		
		BigDecimal lstaddProjectAmt = FDCHelper.ZERO; //新增工程款
		BigDecimal lstpayPartAMatAmt = FDCHelper.ZERO; //上期累计申请甲供材款
		FilterInfo filter;
		EntityViewInfo view;
		
		view = new EntityViewInfo();
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("projectPriceInContractOri");
		view.getSelector().add("addProjectAmt");
		view.getSelector().add("payPartAMatlAmt");
		view.getSelector().add("hasClosed");
		view.getSelector().add("entrys.projectPriceInContract");
		view.getSelector().add("entrys.addProjectAmt");
		view.getSelector().add("entrys.payPartAMatlAmt");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("createTime",editData.getCreateTime(),CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId",editData.getContractId()));
		
		view.setFilter(filter);
		PayRequestBillCollection cols = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		//截至上期间累计申请 ，关闭则取分录累计，未关闭则取实际申请取申请请单金额 by hpw 2010.9.22
		for(Iterator it = cols.iterator();it.hasNext();){
			PayRequestBillInfo info = (PayRequestBillInfo) it.next();
			if(info.isHasClosed()){
				for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
					PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo)iter.next();
					lstReqAmt = FDCHelper.add(lstReqAmt,entry.getProjectPriceInContract());
					lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,entry.getAddProjectAmt());
					lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,entry.getPayPartAMatlAmt());
					
					/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
					lstPrjAllReqOrgAmt = FDCHelper.add(lstPrjAllReqOrgAmt,entry.getAddProjectAmt());
				}
			}else{
				lstReqAmt = FDCHelper.add(lstReqAmt,info.getProjectPriceInContract());
				lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,info.getAddProjectAmt());
				lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,info.getPayPartAMatlAmt());
				
				/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
				lstPrjAllReqOrgAmt = FDCHelper.add(lstPrjAllReqOrgAmt,info.getProjectPriceInContractOri());
			}
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		editData.setLstPrjAllReqAmt(lstReqAmt);
		editData.setLstAddPrjAllReqAmt(lstaddProjectAmt);
	    editData.setLstAMatlAllReqAmt(lstpayPartAMatAmt);
		
		if(isUpdateCell){
			((ICell)bindCellMap.get(PayRequestBillContants.LSTPRJALLREQAMT)).setValue(lstReqAmt);
			
			/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
			((ICell)bindCellMap.get(PayRequestBillContants.LSTPRJALLREQORGAMT)).setValue(lstPrjAllReqOrgAmt);
			
			((ICell)bindCellMap.get(PayRequestBillContants.LSTADDPRJALLREQAMT)).setValue(lstaddProjectAmt);
//			((ICell)bindCellMap.get(PayRequestBillContants.LSTAMATLALLREQAMT)).setValue(null); 不知道为什么要直接设为NULL
			((ICell)bindCellMap.get(PayRequestBillContants.LSTAMATLALLREQAMT)).setValue(lstpayPartAMatAmt);
		}
		
		if (editData.getState() == FDCBillStateEnum.SAVED || editData.getState() == FDCBillStateEnum.SUBMITTED
				|| editData.getState() == FDCBillStateEnum.AUDITTING) {
			if(PayReqUtils.isContractBill(editData.getContractId())){
				builder.clear();
				builder=new FDCSQLBuilder();
				builder.appendSql("select fprjPriceInConPaid as amount from T_CON_ContractBill where fid=?");
				builder.addParam(editData.getContractId());
				IRowSet rowSet = builder.executeQuery();
				if(rowSet!=null&&rowSet.size()==1){
					try {
						rowSet.next();
						setCellValue(PayRequestBillContants.LSTPRJALLPAIDAMT, rowSet.getBigDecimal("amount"));
						editData.setLstPrjAllPaidAmt(rowSet.getBigDecimal("amount"));
					} catch (SQLException e) {
						e.printStackTrace();
						throw new BOSException(e);
					}
					
				}
			}
		}
	}
	
	void updateLstAdvanceAmt(PayRequestBillInfo editData, boolean isUpdate) throws BOSException, EASBizException {
		BigDecimal lstAdvanceAllPaid = FDCHelper.ZERO;
		BigDecimal lstAdvanceAllReq = FDCHelper.ZERO;
		BigDecimal advance = FDCHelper.ZERO;
		BigDecimal locAdvance = FDCHelper.ZERO;
		BigDecimal advanceAllReq = FDCHelper.ZERO;
		if(!isUpdate){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
			filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(),CompareType.LESS));
//			view.getSelector().add("prjPayEntry.*");
			view.getSelector().add("prjPayEntry.id");
			view.getSelector().add("prjPayEntry.locAdvance");
			view.getSelector().add("entrys.paymentBill.billStatus");
			view.getSelector().add("entrys.paymentBill.prjPayEntry.advance");
			view.getSelector().add("entrys.paymentBill.prjPayEntry.locAdvance");
			PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
				.getPayRequestBillCollection(view);
			for(int i=0;i<c.size();i++){
				PayRequestBillInfo info = c.get(i);
				if(info.getId().equals(editData.getId())){
					continue;
				}
				PayRequestBillEntryCollection entry = info.getEntrys();
				if(entry!=null&&entry.size()>0){
					for(int j=0;j<entry.size();j++){
						PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
						if(BillStatusEnum.PAYED.equals(payment.getBillStatus())){
							if(payment.getPrjPayEntry()!=null){
								lstAdvanceAllPaid = FDCHelper.add(lstAdvanceAllPaid,payment.getPrjPayEntry().getLocAdvance());
							}
						}
					}
				}
				if(info.getPrjPayEntry()!=null){
					lstAdvanceAllReq = FDCHelper.add(lstAdvanceAllReq, info.getPrjPayEntry().getLocAdvance());
				}
			}
			if(FDCHelper.ZERO.compareTo(lstAdvanceAllPaid)==0){
				lstAdvanceAllPaid = null;
			}
			if (FDCHelper.ZERO.compareTo(lstAdvanceAllReq) == 0) { 
				lstAdvanceAllReq = null;
			}
			((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLPAID)).setValue(lstAdvanceAllPaid);
			((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLREQ)).setValue(lstAdvanceAllReq);
			if(editData.getPrjPayEntry()!=null){
				//本期的申请及累计申请取本期的数据 by hpw 2009-07-28
				((ICell)bindCellMap.get(PayRequestBillContants.ADVANCE)).setValue(editData.getPrjPayEntry().getAdvance());
				((ICell)bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).setValue(editData.getPrjPayEntry().getLocAdvance());
				((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLREQ)).setValue(editData.getPrjPayEntry().getAdvanceAllReq());
			}
//			((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLREQ)).setValue(lstAdvanceAllReq);
			((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLPAID)).setValue(lstAdvanceAllPaid);
		} else{
			lstAdvanceAllPaid = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLPAID)).getValue());
			lstAdvanceAllReq = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLREQ)).getValue());
			advance = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.ADVANCE)).getValue());
			locAdvance = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).getValue());
			advanceAllReq =FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLREQ)).getValue());
			PayReqPrjPayEntryInfo prjPayEntry = editData.getPrjPayEntry();
			if (prjPayEntry == null) {
				prjPayEntry = new PayReqPrjPayEntryInfo();
			}
			prjPayEntry.setLstAdvanceAllPaid(lstAdvanceAllPaid);
			prjPayEntry.setLstAdvanceAllReq(lstAdvanceAllReq);
			prjPayEntry.setAdvance(advance);
			prjPayEntry.setLocAdvance(locAdvance);
			prjPayEntry.setAdvanceAllReq(advanceAllReq);
			prjPayEntry.setAdvanceAllPaid(lstAdvanceAllPaid);
			editData.setPrjPayEntry(prjPayEntry);
		}
		
		
		
	}
	
	/**
	 * 合同下已审批状态的付款申请单的预付款-本次申请原币+本张单据的预付款-本次申请原币必须大于0
	 * @param editData
	 * @throws BOSException
	 */
	void checkAdvance(PayRequestBillInfo editData,Map bindCellMap) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if(editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		}
		view.getSelector().add("prjPayEntry.advance");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
			.getPayRequestBillCollection(view);
		BigDecimal advance = FDCHelper.ZERO;
		if(c!=null){
			for(int i=0;i<c.size();i++){
				PayRequestBillInfo info = c.get(i);
				if(info.getPrjPayEntry()!=null){
					advance = FDCHelper.add(advance, info.getPrjPayEntry().getAdvance());
				}
			}
		}
		Object cellValue = ((ICell)bindCellMap.get(PayRequestBillContants.ADVANCE)).getValue();
		advance = FDCHelper.add(advance, cellValue);
		if (FDCHelper.ZERO.compareTo(advance) == 1) {
			MsgBox.showError("合同下已审批状态的付款申请单的 预付款本次申请原币 + 本张单据的预付款本次申请原币 必须大于0");
			SysUtil.abort();
		}
		
	}

	public void getAdvanceValueFromCell(PayRequestBillInfo editData,
			HashMap bindCellMap) {
//		if
//		PayReqPrjPayEntryInfo info = 
	}
	
	/**
	 * description		填充付款申请单对应的奖励项
	 * @author			蒲磊<p>	
	 * @createDate		2011-9-7<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	void reloadGuerdonValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{

    	if(!PayReqUtils.isContractBill(editData.getContractId())){
    		return;
    	}
    	//进行刷新操作从数据库内取
    	BigDecimal originalamount = FDCHelper.ZERO;
    	amount=FDCHelper.ZERO;
    	BigDecimal lstPaidAmt=FDCHelper.ZERO;
    	BigDecimal lstReqAmt=FDCHelper.ZERO;
    	EntityViewInfo view=new EntityViewInfo();
    	view.getSelector().add("amount");
    	view.getSelector().add("originalAmount");
    	view.getSelector().add("hasPaid");
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    	view.setFilter(filter);
    	// 再加一个时间过滤
    	Timestamp createTime = editData.getCreateTime();
    	filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    	GuerdonOfPayReqBillCollection c = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
    	for(int i = 0; i<c.size(); i++){
    		GuerdonOfPayReqBillInfo info = c.get(i);
    		if(info.getAmount() != null){
    			if(info.getPayRequestBill().getId().equals(editData.getId())){
    				amount = amount.add(FDCHelper.toBigDecimal(info.getAmount()));
    				originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
    			}else{
    				if(info.isHasPaid()){
    					lstPaidAmt = info.getAmount().add(lstPaidAmt);
    				}
    				
    				lstReqAmt = info.getAmount().add(lstReqAmt);
    			}
    		}
    	}
    	
    	if(amount.compareTo(FDCHelper.ZERO)==0){
    		amount=null;
    	}
    	if(originalamount.compareTo(FDCHelper.ZERO)==0){
    		originalamount=null;
    	}
    	if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    		lstPaidAmt=null;
    	}
    	if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    		lstReqAmt=null;
    	} 
    	((ICell)bindCellMap.get("guerdonAmt")).setValue(amount);
    	((ICell)bindCellMap.get("guerdonOriginalAmt")).setValue(originalamount);
    	((ICell)bindCellMap.get("lstGuerdonPaidAmt")).setValue(lstPaidAmt);
    	((ICell)bindCellMap.get("lstGuerdonReqAmt")).setValue(lstReqAmt);
    }
	
	/**
	 * 
	 * @author sxhong  		Date 2007-3-29
	 * @param objectValue
	 * @param contractBillId
	 * @throws BOSException
	 */
	void updateGuerdonValue(PayRequestBillInfo objectValue, String contractBillId,
			GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection,GuerdonBillCollection guerdonBillCollection) throws BOSException {
		FilterInfo filter;
		EntityViewInfo view;
		//奖励单
		BigDecimal lstPaidAmt=FDCHelper.ZERO;
		BigDecimal lstReqAmt=FDCHelper.ZERO;
		
		for(int i=0;i<guerdonOfPayReqBillCollection.size();i++){
			GuerdonOfPayReqBillInfo info = guerdonOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				
				lstReqAmt=info.getAmount().add(lstReqAmt);
			}
		}
		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
			lstPaidAmt=null;
		}
		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
			lstReqAmt=null;
		}
		objectValue.put("lstGuerdonPaidAmt", lstPaidAmt);
		objectValue.put("lstGuerdonReqAmt", lstReqAmt);
		
		//带出默认的本次发生额:所有未申请的奖励单
		BigDecimal sum=FDCHelper.ZERO;
		BigDecimal sumOriginal=FDCHelper.ZERO;
		for (int i = 0; i < guerdonBillCollection.size(); i++) {
			GuerdonBillInfo item = guerdonBillCollection.get(i);
			if(item.getAmount()!=null){
				sum=sum.add(item.getAmount());
			}
			if(item.getOriginalAmount()!=null){
				sumOriginal=sumOriginal.add(item.getOriginalAmount());
			}
		}
		if(sum.compareTo(FDCHelper.ZERO)==0){
			sum=null;
		}
		if(sumOriginal.compareTo(FDCHelper.ZERO)==0){
			sumOriginal=null;
		}
		objectValue.put("guerdonAmt", sum);
		objectValue.put("guerdonOriginalAmt", sumOriginal);
		
		if(bindCellMap.get("guerdonAmt")!=null&&bindCellMap.get("guerdonOriginalAmt")!=null){
			((ICell)bindCellMap.get("guerdonAmt")).setValue(sum);
			((ICell)bindCellMap.get("guerdonOriginalAmt")).setValue(sumOriginal);
			((ICell)bindCellMap.get("lstGuerdonPaidAmt")).setValue(lstPaidAmt);
			((ICell)bindCellMap.get("lstGuerdonReqAmt")).setValue(lstReqAmt);
		}
	}
	
	/**
	 * description		填充付款申请单对应的违约金
	 * @author			蒲磊<p>	
	 * @createDate		2011-9-7<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	void reloadCompensationValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{

    	if(!PayReqUtils.isContractBill(editData.getContractId())){
    		return;
    	}
    	BigDecimal originalamount = FDCHelper.ZERO;
    	amount=FDCHelper.ZERO;
    	BigDecimal lstPaidAmt=FDCHelper.ZERO;
    	BigDecimal lstReqAmt=FDCHelper.ZERO;
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    	view.setFilter(filter);
    	//再加一个时间过滤
    	Timestamp createTime = editData.getCreateTime();
    	filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    	
    	CompensationOfPayReqBillCollection c = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
    	for(int i=0;i<c.size();i++){
    		CompensationOfPayReqBillInfo info = c.get(i);
    		if(info.getAmount()!=null){
    			if(info.getPayRequestBill().getId().equals(editData.getId())){
    				amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
    				originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
    			}else{
    				if(info.isHasPaid()){
    					lstPaidAmt=info.getAmount().add(lstPaidAmt);
    				}
    				
    				lstReqAmt=info.getAmount().add(lstReqAmt);
    			}
    		}
    	}
    	if(amount.compareTo(FDCHelper.ZERO)==0){
    		amount=null;
    	}
    	if(originalamount.compareTo(FDCHelper.ZERO)==0){
    		originalamount=null;
    	}
    	if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    		lstPaidAmt=null;
    	}
    	if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    		lstReqAmt=null;
    	} 
    	((ICell)bindCellMap.get("compensationAmt")).setValue(amount);
    	((ICell)bindCellMap.get("compensationOriginalAmt")).setValue(originalamount);
    	((ICell)bindCellMap.get("lstCompensationPaidAmt")).setValue(lstPaidAmt);
    	((ICell)bindCellMap.get("lstCompensationReqAmt")).setValue(lstReqAmt);
    }
	
	/**
	 * 
	 * @author sxhong  		Date 2007-3-29
	 * @param objectValue
	 * @param contractBillId
	 * @throws BOSException
	 */
	void updateCompensationValue(PayRequestBillInfo objectValue, String contractBillId,
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection) throws BOSException {
		FilterInfo filter;
		EntityViewInfo view;
		//奖励单
		BigDecimal lstPaidAmt=FDCHelper.ZERO;
		BigDecimal lstReqAmt=FDCHelper.ZERO;
		for(int i=0;i<compensationOfPayReqBillCollection.size();i++){
			CompensationOfPayReqBillInfo info = compensationOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				
				lstReqAmt=info.getAmount().add(lstReqAmt);
			}
		}
		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
			lstPaidAmt=null;
		}
		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
			lstReqAmt=null;
		}
		objectValue.put("lstCompensationPaidAmt", lstPaidAmt);
		objectValue.put("lstCompensationReqAmt", lstReqAmt);
		
		//违约不需要带出
		//带出默认的本次发生额:所有未申请的奖励单
		BigDecimal sum=FDCHelper.ZERO;
		BigDecimal sumOriginal=FDCHelper.ZERO;
		//if(this.ge){//新增才需要带出
		
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isCompensated",String.valueOf(1),CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("amount");
			view.getSelector().add("originalamount");
			view.getSelector().add("isCompensated");
			CompensationBillCollection compensationBillCollection = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
			for (int i = 0; i < compensationBillCollection.size(); i++) {
				CompensationBillInfo item = compensationBillCollection.get(i);
				if(item.getAmount()!=null){
					sum=sum.add(item.getAmount());
				}
				if(item.getOriginalAmount()!=null){
					sumOriginal= FDCNumberHelper.add(sumOriginal,item.getOriginalAmount());
				}
			}
		//}
		if(sum.compareTo(FDCHelper.ZERO)==0){
			sum=null;
		}
		if(sumOriginal.compareTo(FDCHelper.ZERO)==0){
			sumOriginal=null;
		}
		objectValue.put("compensationAmt", sum);
		objectValue.put("compensationOriginalAmt", sumOriginal);
		
		if(bindCellMap.get("compensationAmt")!=null&&bindCellMap.get("compensationOriginalAmt")!=null){
			((ICell)bindCellMap.get("compensationAmt")).setValue(sum);
			((ICell)bindCellMap.get("compensationOriginalAmt")).setValue(sumOriginal);
			((ICell)bindCellMap.get("lstCompensationPaidAmt")).setValue(lstPaidAmt);
			((ICell)bindCellMap.get("lstCompensationReqAmt")).setValue(lstReqAmt);
		}
	}
	
	/*
	 * 得到资源文件
	 */
	private String getRes(String resName) {
		return PayReqUtils.getRes(resName);
	}

	private void handUIException(Exception e) {
		editUI.handUIException(e);
	}
	
	public ICell getCell(String key){
		Object object = bindCellMap.get(key);
		if(object instanceof ICell){
			return (ICell)object;
		}
		
		return null;
	}
	
	public Object getCellValue(String key){
		ICell cell=getCell(key);
		if(cell!=null){
			return cell.getValue();
		}
		return null;
	}
	
	public void setCellValue(String key,Object value){
		ICell cell=getCell(key);
		if(cell!=null){
			cell.setValue(value);
		}
	}
	
	public void debugCellExp(){

	}
	
	void setLstPrict(){
		if (editUI.getOprtState().equals(OprtState.ADDNEW)) {
			//新增的时候将未被关联的奖励，扣款，违约考虑进来
			BigDecimal latestPrice=FDCHelper.toBigDecimal(getCellValue("latestPrice"));
			BigDecimal gueronAmt=FDCHelper.toBigDecimal(getCellValue("guerdonAmt"));//奖励单
			BigDecimal compensationAmt=FDCHelper.toBigDecimal(getCellValue("compensationAmt"));//违约金
			ICell cell=table.getCell(table.getRowCount()-6, 5);
			BigDecimal psubTotal=FDCHelper.toBigDecimal(cell.getValue());//扣款单小计
			latestPrice=latestPrice.add(gueronAmt).subtract(compensationAmt).subtract(psubTotal);
			setCellValue("latestPrice", latestPrice);
		}
	}
	
	/**
	 * 重载甲供材扣款单
	 * @param editData
	 * @param amount
	 * @throws Exception
	 */
	void reloadPartAValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{
		
//		if(!PayReqUtils.isContractBill(editData.getContractId())){
//    		return;
//    	}
		BigDecimal originalAmount = FDCHelper.ZERO;
		if(amount == null){
			amount = FDCHelper.ZERO;
		}
		if(amount.compareTo(FDCHelper.ZERO)==1){
			amount = FDCHelper.ZERO;
			((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
		}
    	//进行刷新操作从数据库内取
    	BigDecimal lstPaidAmt = FDCHelper.ZERO;
    	BigDecimal lstReqAmt = FDCHelper.ZERO;
    	BigDecimal allReqAmt = FDCHelper.ZERO;
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    	view.setFilter(filter);
    	//再加一个时间过滤
    	Timestamp createTime = editData.getCreateTime();
    	filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    	
    	PartAOfPayReqBillCollection c = PartAOfPayReqBillFactory.getRemoteInstance().getPartAOfPayReqBillCollection(view);
    	for(int i=0;i<c.size();i++){
   			PartAOfPayReqBillInfo info = c.get(i);
   			if(info.getAmount()!=null){
   				
   				if(info.getPayRequestBill().getId().equals(editData.getId())){
   					amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
   					BigDecimal tem = FDCHelper.toBigDecimal(info.getOriginalAmount()).compareTo(FDCHelper.ZERO)>0?
   							FDCHelper.toBigDecimal(info.getOriginalAmount()):FDCHelper.toBigDecimal(info.getAmount());
   					originalAmount = originalAmount.add(tem);
   				}else{
	   				if(info.isHasPaid()){
	   					lstPaidAmt=info.getAmount().add(lstPaidAmt);
	   				}
	   				lstReqAmt=info.getAmount().add(lstReqAmt);
    			}
    		}
    	}
    	allReqAmt = lstReqAmt.add(amount);
    	if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    		lstPaidAmt = null;
    	}
    	if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    		lstReqAmt = null;
    	}
    	if(originalAmount.compareTo(FDCHelper.ZERO)==0){
    		originalAmount = null;
    	}
    	if(allReqAmt.compareTo(FDCHelper.ZERO)==0){
    		allReqAmt = null;
    	}
    	((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    	((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    	((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    	((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalAmount);
    	((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    	((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
    	
	}
	
	/**
	 * 甲供材应扣款数据,新增时调用更新
	 * @param objectValue
	 * @param contractBillId
	 * @param compensationOfPayReqBillCollection
	 * @throws BOSException
	 */
	void updatePartAValue(PayRequestBillInfo objectValue, String contractBillId,
			PartAOfPayReqBillCollection partAOfPayReqBillCollection) throws BOSException {
		
		//甲供扣款单
		if(partAOfPayReqBillCollection==null){
			return;
		}
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal originalamount = FDCHelper.ZERO;
		BigDecimal lstPaidAmt = FDCHelper.ZERO;
		BigDecimal lstReqAmt = FDCHelper.ZERO;
		BigDecimal allReqAmt = FDCHelper.ZERO;
		for(int i=0;i<partAOfPayReqBillCollection.size();i++){
			PartAOfPayReqBillInfo info = partAOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				lstReqAmt=info.getAmount().add(lstReqAmt);
				allReqAmt = lstReqAmt.add(amount);
			}
		}
		if(bindCellMap.get("payPartAMatlAmt")!=null){
			((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    		((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
		}
	}
	/**
	 * 重载甲供材确认单
	 * @param editData
	 * @param amount
	 * @throws Exception
	 */
	void reloadPartAConfmValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{
//		if(!PayReqUtils.isContractBill(editData.getContractId())){
//    		return;
//    	}
		if(amount == null){
			amount = FDCHelper.ZERO;
		}
		if(amount.compareTo(FDCHelper.ZERO) == 1){
			amount = FDCHelper.ZERO;
			((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount); //本次申请本币
		}
//    	if(amount!=null){
//    		((ICell) bindCellMap.get("payPartAMatlAmt")).setValue(amount);
//    	}else{
    		//进行刷新操作从数据库内取
    		amount=FDCHelper.ZERO;
    		BigDecimal originalamount = FDCHelper.ZERO; //原币
    		BigDecimal lstPaidAmt = FDCHelper.ZERO; //截至上期累计实付
    		BigDecimal lstReqAmt = FDCHelper.ZERO; //截至上期累计申请
    		BigDecimal allReqAmt = FDCHelper.ZERO; //本期累计申请
    		EntityViewInfo view=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    		view.setFilter(filter);
    		//再加一个时间过滤
    		Timestamp createTime = editData.getCreateTime();
    		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    		
    		PartAConfmOfPayReqBillCollection c = PartAConfmOfPayReqBillFactory.getRemoteInstance().getPartAConfmOfPayReqBillCollection(view);
    		for(int i=0;i<c.size();i++){
    			PartAConfmOfPayReqBillInfo info = c.get(i);
    			if(info.getAmount()!=null){
    				if(info.getPayRequestBill().getId().equals(editData.getId())){
    					amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
    					//2009-2-8 甲供扣款原币 应累加
    					originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
//    					originalamount = FDCHelper.toBigDecimal(info.getOriginalAmount());
    				}else{
	    				if(info.isHasPaid()){
	    					lstPaidAmt=info.getAmount().add(lstPaidAmt);
	    				}
	    				lstReqAmt=info.getAmount().add(lstReqAmt);
    				}
    			}
    		} 
    		allReqAmt = amount.add(lstReqAmt);
    		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    			lstPaidAmt = FDCHelper.ZERO;
    		}
    		if(originalamount.compareTo(FDCHelper.ZERO)==0){
    			originalamount = FDCHelper.ZERO;
    		}
    		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    			lstReqAmt = FDCHelper.ZERO;
    		}
    		
    		((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    		((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
	}
	
	/**
	 * 更新 甲供材确认单对应的数据
	 * @param objectValue
	 * @param contractBillId
	 * @param compensationOfPayReqBillCollection
	 * @throws BOSException
	 */
	void updatePartAConfmValue(PayRequestBillInfo objectValue, String contractBillId,
			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection) throws BOSException {

		if(partAConfmOfPayReqBillCollection==null){
			return;
		}
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal originalamount=FDCHelper.ZERO;
		BigDecimal lstPaidAmt=FDCHelper.ZERO;
		BigDecimal lstReqAmt=FDCHelper.ZERO;
		for(int i=0;i<partAConfmOfPayReqBillCollection.size();i++){
			PartAConfmOfPayReqBillInfo info = partAConfmOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				lstReqAmt=info.getAmount().add(lstReqAmt);
			}
		}
		
		if(bindCellMap.get("payPartAMatlAmt")!=null){
			((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    		((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
		}
	}
	
	protected void setBeforeAction() {

		table.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < table.getSelectManager().size(); i++) {
						KDTSelectBlock block = table.getSelectManager()
								.get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block
								.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block
									.getEndCol(); colIndex++) {
								//非合同内工程款Cell,或非编辑状态时撤销事件
								if((colIndex!=editUI.columnIndex||rowIndex!=editUI.rowIndex) ||table.getCell(rowIndex, colIndex).getStyleAttributes().isLocked()){
									e.setCancel(true);
									continue;
								}
								KDTEditEvent event = new KDTEditEvent(
										table, null, null, rowIndex,
										colIndex, true, 1);
								try {
									editUI.kdtEntrys_editStopped(event);
								} catch (Exception e1) {
									handUIException(e1);
									SysUtil.abort();
								}
							}
						}
					}
				}else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
					table.putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
		table.setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					table.putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable的KDTEditListener仅在编辑的时候触发，
		 * KDTPropertyChangeListener则是在删除，粘贴等导致单元格value发生变化的时候都会触发。
		 */
		table.addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // 表体单元格值发生变化
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(table.getClientProperty("ACTION_PASTE")!=null){
			    		// 触发editStop事件
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		if(rowIndex != editUI.rowIndex || colIndex != editUI.columnIndex){ //本期申请原币
			    			// 这里对整个表体cell做了监听，根据需要过滤
			    			return;
			    		}
			    		KDTEditEvent event=new KDTEditEvent(table);
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = table.getCell(rowIndex,colIndex);
			    		if(cell == null){
			    			return;
			    		}
			    		
			    		event.setValue(cell.getValue());
			    		try {
			    			editUI.kdtEntrys_editStopped(event);
			    			
			    		} catch (Exception e1) {
			    			handUIException(e1);
			    			SysUtil.abort();
			    		}
			    	}
			    }
			}
		});
	}
}
