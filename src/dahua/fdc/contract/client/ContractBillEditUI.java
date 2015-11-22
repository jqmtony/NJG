/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.NumberFormatterEx;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.sql.parser.Lexer;
import com.kingdee.bos.sql.parser.SqlStmtParser;
import com.kingdee.bos.sql.parser.TokenList;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Arrays;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.AttachmentUtils;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptProdUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptProjUI;
import com.kingdee.eas.fdc.basedata.client.CostSplitApptUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.util.FDCKDBizPromptBoxHelper;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBailEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractModelFactory;
import com.kingdee.eas.fdc.contract.ContractModelInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractF7UI;
import com.kingdee.eas.fdc.costindexdb.client.BuildPriceIndexEditUI;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo;
import com.kingdee.eas.fdc.finance.PayPlanNewCollection;
import com.kingdee.eas.fdc.finance.PayPlanNewFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillEditUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadSplitListUI;
import com.kingdee.eas.fdc.invite.AcceptanceLetterCollection;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.InviteAllInformationUI;
import com.kingdee.eas.fdc.invite.news.InviteProcessEnum;
import com.kingdee.eas.fdc.invite.news.StrategyPactInfo;
import com.kingdee.eas.fdc.invite.news.TenderDiscusstionFactory;
import com.kingdee.eas.fdc.invite.news.TenderDiscusstionInfo;
import com.kingdee.eas.fdc.invite.news.client.InviteWFBillViewUI;
import com.kingdee.eas.fdc.invite.news.client.StrategyPactEditUI;
import com.kingdee.eas.fdc.invite.news.client.TenderDiscusstionEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.fm.common.client.FMIsqlUI;
import com.kingdee.eas.fm.common.client.FMIsqlUIHandler;
import com.kingdee.eas.fm.common.client.SQLStmtInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.ma.bg.client.BgBalanceViewUI;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**  
 * 
 * 描述:合同单据编辑界面 
 * @author liupd  date:2006-10-10 <p>
 * @version EAS5.1.3
 */
public class ContractBillEditUI extends AbstractContractBillEditUI implements IWorkflowUISupport {
	private KDComboBox isLonelyCalCombo = null;
	
	private static final String DETAIL_DEF_ID = "detailDef.id";

	private static final Logger logger = CoreUIObject
			.getLogger(ContractBillEditUI.class);

	private static final String BINDING_PROPERTY = "codeType.number";
	
	//责任人是否可以选择其他部门的人员
	private boolean canSelectOtherOrgPerson = false;

	//项目合同签约总金额超过项目
	private String controlCost;

	//控制方式
	private String controlType;

	//合同审批前进行拆分
	private boolean splitBeforeAudit;
	
	//是否显示“合同费用项目”
	private boolean isShowCharge = false;
	//房地产业务系统正文管理是否启用审批笔迹留痕功能
	boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
	
	//补充合同可以选择提交状态的主合同
	private boolean isSupply = false;
	
	private final ArrayList orgIDList = new ArrayList();
	
	//add by david_yang PT043562 2011.03.29 此单据是否有附件
	private boolean isHasAttchment = false;
	JButton btnAdd1;
	JButton btnRemove1;
	JButton btnAdd2;
	JButton btnRemove2;
	/*
	 * 表列名
	 */
	private static final String DETAIL_COL = ContractClientUtils.CON_DETAIL_DETAIL_COL;

	private static final String CONTENT_COL = ContractClientUtils.CON_DETIAL_CONTENT_COL;

	private static final String ROWKEY_COL = ContractClientUtils.CON_DETIAL_ROWKEY_COL;

	private static final String DESC_COL = ContractClientUtils.CON_DETIAL_DESC_COL;

	private static final String DATATYPE_COL = ContractClientUtils.CON_DETIAL_DATATYPE_COL;

	/*
	 * 行标识
	 */
	private static final String NA_ROW = ContractClientUtils.MAIN_CONTRACT_NAME_ROW;

	private static final String NU_ROW = ContractClientUtils.MAIN_CONTRACT_NUMBER_ROW;

	private static final String AM_ROW = ContractClientUtils.AMOUNT_WITHOUT_COST_ROW;

	private static final String LO_ROW = ContractClientUtils.IS_LONELY_CAL_ROW;


	//明细分录内容列
	private static int detailContentNum = 2;
	private static final String INVITEPROJECT_NAME = "招标";
	private static final String TENDERDISCUSSTION_NAME = "议标";
	private static final String STRATEGYPACT_NAME = "战略";
	/**
	 * 得到补充合同分录固定位置
	 * @return
	 */
	protected int getSupplyContractCellNumber(){
		return  this.getDetailTable().getRowCount()-1;
	}
	
	
	
	/*
	 * 主合同id 
	 */
	private String mainContractId = null;

	private BigDecimal detailAmount = FDCHelper.ZERO;
	private ContractCostSplitEditUI conSplitUI = null;
	private boolean isWholeAgeProject = false;
	
	public ContractBillEditUI() throws Exception {
		super();
		jbInit();
	}
	
	/**
	 * 合同提交审批时是否必须与计划任务进行关联 2010-08-09
	 * 
	 * @return
	 */
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return isRealtedTask;
	}
	private void jbInit() {
		titleMain = this.getUITitle();
	}

	//变化事件
	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} else if ("grtAmount".equals(listener.getShortCut())) {
			setGrtAmt("txtGrtAmount", e);
		} else if ("grtRate".equals(listener.getShortCut())) {
			setGrtAmt("txtGrtRate", e);
		} else if ("amount".equals(listener.getShortCut())){
			setGrtAmt("txtGrtRate", e);
		}
	}

	//业务日期变化事件
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	ControlDateChangeListener amountChangeListener = new ControlDateChangeListener(
		"amount");
	
	ControlDateChangeListener grtAmountChangeListener = new ControlDateChangeListener(
			"grtAmount");

	ControlDateChangeListener grtRateChangeListener = new ControlDateChangeListener(
			"grtRate");

	protected void attachListeners() {
		pkbookedDate.addDataChangeListener(bookedDateChangeListener);
		txtamount.addDataChangeListener(amountChangeListener);
		txtGrtAmount.addDataChangeListener(grtAmountChangeListener);
		txtGrtRate.addDataChangeListener(grtRateChangeListener);
		
		addDataChangeListener(comboCurrency);
		addDataChangeListener(contractPropert);
		addDataChangeListener(contractSource);
		//addDataChangeListener(comboCurrency);

		addDataChangeListener(prmtcontractType);
		addDataChangeListener(txtamount);
		addDataChangeListener(txtExRate);
		addDataChangeListener(txtStampTaxRate);
		addDataChangeListener(prmtRespDept);
	}

	protected void detachListeners() {
		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
		txtamount.removeDataChangeListener(amountChangeListener);
		txtGrtAmount.removeDataChangeListener(grtAmountChangeListener);
		txtGrtRate.removeDataChangeListener(grtRateChangeListener);
		
		removeDataChangeListener(comboCurrency);
		removeDataChangeListener(contractPropert);
		removeDataChangeListener(contractSource);

		removeDataChangeListener(prmtcontractType);
		removeDataChangeListener(txtamount);
		removeDataChangeListener(txtGrtAmount);
		removeDataChangeListener(txtExRate);
		removeDataChangeListener(txtGrtRate);
		removeDataChangeListener(txtStampTaxRate);
		removeDataChangeListener(prmtRespDept);
	}

	/** 是否使用不计成本的金额, 是否单据计算 = 是, 此变量值为 false, 是否单据计算 = 否, 此变量值 = true, 
	 * 如果详细信息没有是否单独计算, 则此变量值为 false
	 */
	protected boolean isUseAmtWithoutCost = false;

	public void loadFields() {
		detachListeners();

		super.loadFields();

		/**** modify by lihaiou**********/
		 initPromptBoxFormat();
		/******** modify end *************/
		if (editData.getContractSourceId() != null) {
			ContractSourceInfo conSourceInfo = editData.getContractSourceId();
			//如果不是新增
			if (!getOprtState().equals(OprtState.ADDNEW)) {
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("inviteProject.*");
					sic.add("strategyPact.*");
					sic.add("tenderDiscusstion.*");
					ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()),
							sic);
					if (conSourceInfo.getName().indexOf(STRATEGYPACT_NAME) != -1) {
						contInviteProject.setVisible(false);
						tenderDiscusstion.setVisible(false);
						contStrategyPact.setVisible(true);
						conEntrustReason.setVisible(false);
						prmtStrategyPact.setValue(info.getStrategyPact());
					} else if (INVITEPROJECT_NAME.equals(conSourceInfo.getName())) {
						contStrategyPact.setVisible(false);
						tenderDiscusstion.setVisible(false);
						contInviteProject.setVisible(true);
						conEntrustReason.setVisible(false);
						prmtInviteProject.setValue(info.getInviteProject());
					} else if (TENDERDISCUSSTION_NAME.equals(conSourceInfo.getName())) {
						contStrategyPact.setVisible(false);
						contInviteProject.setVisible(false);
						tenderDiscusstion.setVisible(true);
						conEntrustReason.setVisible(false);
						
						//prmtTenderDiscusstion.setEditFormat("$number$");
						prmtTenderDiscusstion.setValue(info.getTenderDiscusstion());
					} else {
						contStrategyPact.setVisible(false);
						contInviteProject.setVisible(false);
						tenderDiscusstion.setVisible(false);
						conEntrustReason.setVisible(true);
					}
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			} else {
				contStrategyPact.setVisible(false);
				contInviteProject.setVisible(false);
				tenderDiscusstion.setVisible(false);
				conEntrustReason.setVisible(true);
			}
		}
		tblBail.removeRows();
		//很无语的东西，已经都绑定好了的东西，在保存的时候已经存进了数据库，加载数据的时候也取到了数据，可就是不会填充
		//到表格中来，郁闷死了！ 只能自己维护使其填值了，竟然不加载已经绑定好的单据的分录的分录到表格中去  by Cassiel_peng  2008-8-27
		this.tblEconItem.getColumn("payCondition").getStyleAttributes().setWrapText(true);
		this.tblEconItem.getColumn("desc").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("bailCondition").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("desc").getStyleAttributes().setWrapText(true);
		if(this.editData.getBail()!=null&&this.editData.getBail().getEntry()!=null){
			for (int i = 0; i < this.editData.getBail().getEntry().size(); i++) {
				ContractBailEntryInfo entry = this.editData.getBail().getEntry().get(i);
				IRow row=tblBail.addRow();
				row.setUserObject(entry);
				row.getCell("bailAmount").setValue(FDCHelper.toBigDecimal(entry.getAmount(),2));
				row.getCell("bailDate").setValue(entry.getBailDate());
				row.getCell("bailRate").setValue(entry.getProp());
				row.getCell("bailCondition").setValue(entry.getBailConditon());
				row.getCell("desc").setValue(entry.getDesc());
			}
		}
		
		isUseAmtWithoutCost = editData.isIsAmtWithoutCost();
		loadDetailEntries();
          
		this.txtOverAmt.setValue(new Double(editData.getOverRate()));
		
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}

		//币别选择
		GlUtils.setSelectedItem(comboCurrency, editData.getCurrency());

		txtExRate.setValue(editData.getExRate());
		txtLocalAmount.setValue(editData.getAmount());
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(
					baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			}
		}

		if (editData != null && editData.getCurProject() != null) {

			//			String projId = editData.getCurProject().getId().toString();
			//			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);

			//解决bug BT315446 注释代码 txtProj.setText(curProject.getDisplayName());
			//txtProj.setText(curProject.getDisplayName());
			txtProj.setText(editData.getCurProject().getDisplayName());

			FullOrgUnitInfo costOrg = this.orgUnitInfo;
			//FDCHelper.getCostCenter(editData.getCurProject(),null).castToFullOrgUnitInfo(); 
			//FDCClientUtils.getCostOrgByProj(projId);

			txtOrg.setText(costOrg.getDisplayName());
			editData.setOrgUnit(costOrg);
			editData.setCU(curProject.getCU());
		}

		attachListeners();
		//2009-1-20 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
		
		this.comboCoopLevel.setSelectedItem(editData.getCoopLevel());
		this.contConSettleAmount.setEnabled(false);
		this.txtConSettleAmout.setEnabled(false);
		setCapticalAmount();
		if (isMutilProject() && this.editData.getId()!=null) {
			try {
				setContSettleAmount();
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}else {
			this.contConSettleAmount.setVisible(false);
		}
		detailTableAutoFitRowHeight(tblEconItem);
		detailTableAutoFitRowHeight(tblBail);
		setButtonStatus();
		setDisplay();
	}
	
	protected void initPromptBoxFormat(){
		btnViewInvite.setVisible(false);
		
		
		
		FDCKDBizPromptBoxHelper.initBox(prmtTenderDiscusstion,TenderDiscusstionEditUI.class.getName());
		FDCKDBizPromptBoxHelper.initBox(prmtInviteProject,InviteWFBillViewUI.class.getName());
		FDCKDBizPromptBoxHelper.initBox(prmtStrategyPact,StrategyPactEditUI.class.getName());
		
		//议标
		prmtTenderDiscusstion.setDisplayFormat("$name$");
		prmtTenderDiscusstion.setEditFormat("$number$");
		prmtTenderDiscusstion.setCommitFormat("$number$");
		
		prmtInviteProject.setDisplayFormat("$name$");
		prmtInviteProject.setEditFormat("$number$");
		prmtInviteProject.setCommitFormat("$number$");
		//
		prmtStrategyPact.setDisplayFormat("$name$");
		prmtStrategyPact.setEditFormat("$number$");
		prmtStrategyPact.setCommitFormat("$number$");
		
		prmtStrategyPact.addDataChangeListener(new DataChangeListener(){

			/**
			 * 数据发生变化时的处理方法。
			 */
			public void dataChanged(DataChangeEvent eventObj){
				strategyPactChanged();
			}
		});
	}
	
	/**
	 * 根据选择的战略协议类型更新合同采购类型
	 * @author RD_haiou_li
	 * @date:2014-05-04
	 */
	private void strategyPactChanged(){
		StrategyPactInfo strategyPactInfo = (StrategyPactInfo)prmtStrategyPact.getValue();
		InviteProcessEnum inviteProcessEnum = strategyPactInfo.getInviteProcess();
		InviteTypeInfo inviteTypeinfo = null;
		if(inviteProcessEnum == InviteProcessEnum.INVITEPROJECT){
			//inviteTypeinfo = strategyPactInfo.getInvProject().getInviteType();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("inviteType.id"));
	    	sic.add(new SelectorItemInfo("inviteType.number"));
	    	sic.add(new SelectorItemInfo("inviteType.name"));
	    	sic.add(new SelectorItemInfo("inviteType.contractType.id"));
	    	sic.add(new SelectorItemInfo("inviteType.contractType.number"));
	    	sic.add(new SelectorItemInfo("inviteType.contractType.name"));
	    	sic.add(new SelectorItemInfo("inviteType.contractType.displayName"));
	    	String id = strategyPactInfo.getInvProject().getId().toString();
	    	try {
	    		InviteProjectInfo tenderdiscusstionInfo = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(new ObjectUuidPK(id), sic);
				inviteTypeinfo = tenderdiscusstionInfo.getInviteType();
	    	} catch (EASBizException e) {
				logger.error(e.getMessage());
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		} else if(inviteProcessEnum == InviteProcessEnum.TENDERDISCUSSTION){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("invType.id"));
	    	sic.add(new SelectorItemInfo("invType.number"));
	    	sic.add(new SelectorItemInfo("invType.name"));
	    	sic.add(new SelectorItemInfo("invType.contractType.id"));
	    	sic.add(new SelectorItemInfo("invType.contractType.number"));
	    	sic.add(new SelectorItemInfo("invType.contractType.name"));
	    	sic.add(new SelectorItemInfo("invType.contractType.displayName"));
	    	String id = strategyPactInfo.getTenderDiscussion().getId().toString();
	    	try {
				TenderDiscusstionInfo tenderdiscusstionInfo = TenderDiscusstionFactory.getRemoteInstance().getTenderDiscusstionInfo(new ObjectUuidPK(id), sic);
				inviteTypeinfo = tenderdiscusstionInfo.getInvType();
	    	} catch (EASBizException e) {
				logger.error(e.getMessage());
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
	    	
		}
		if(inviteTypeinfo !=null && inviteTypeinfo.getContractType() != null){
			prmtcontractType.setValue(inviteTypeinfo.getContractType());
		}
	}
	private boolean isMutilProject(){
		boolean  flag = false;
		try {
			flag = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MULTIPROJECT);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return flag;
	}
	/**
	 * 设置合同结算金额
	 * @throws Exception 
	 * @throws BOSException 
	 */
	private void setContSettleAmount() throws Exception  {
		this.txtConSettleAmout.setValue(getConSettleAmount(this.editData.isIsCoseSplit(),this.editData.getId().toString(),this.editData.getCurProject()));
	}

	/**
	 * 设置原币金额大写和本币金额大写
	 * 
	 * @author owen_wen 2011-06-09
	 */
	private void setCapticalAmount() {
		BigDecimal amount = FDCHelper.toBigDecimal(txtamount.getBigDecimalValue());
		this.txtOrgAmtBig.setText(FDCClientHelper.getChineseFormat(amount, false));
		this.txtOrgAmtBig.setHorizontalAlignment(JTextField.RIGHT);

		BigDecimal localAmt = FDCHelper.toBigDecimal(txtLocalAmount.getBigDecimalValue());
		this.txtAmtBig.setText(FDCClientHelper.getChineseFormat(localAmt.compareTo(FDCNumberHelper.ZERO) == 0 ? FDCNumberHelper.ZERO
				: localAmt, false));
		this.txtAmtBig.setHorizontalAlignment(JTextField.RIGHT);
	}
	
	/*
	 * 查看合同是否关联框架合约
	 * 
	 * @seecom.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#
	 * actionViewProgramCon_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewProgramCon_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getProgrammingContract() == null) {
			MsgBox.showInfo(this, "该合同没有关联框架合约!");
			this.abort();
		}

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ContractBillInfo contractBillInfo = (ContractBillInfo) editData;
		ProgrammingContractInfo proContInfo = getProContInfo(editData.getProgrammingContract().getId().toString());
		contractBillInfo.setProgrammingContract(proContInfo);
		uiContext.put("programmingContract", proContInfo);
		uiContext.put("programmingContractTemp", "programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	/**
	 * 获取所关联的规划合约
	 * 
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id), selItemCol);
		return pcInfo;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		//保存履约保证金及返还部分
		ContractBailInfo contractBailInfo=this.editData.getBail();
		if(contractBailInfo==null){
			contractBailInfo=new ContractBailInfo();
			contractBailInfo.setId(BOSUuid.create(contractBailInfo.getBOSType()));
			this.editData.setBail(contractBailInfo);
		}
		contractBailInfo.setAmount(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2));
		contractBailInfo.setProp(FDCHelper.toBigDecimal(txtBailRate.getBigDecimalValue(),2));
		ContractBailEntryCollection coll=contractBailInfo.getEntry();
		coll.clear();
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			IRow row=tblBail.getRow(i);
			ContractBailEntryInfo bailEntryInfo=(ContractBailEntryInfo)row.getUserObject();
			bailEntryInfo.setParent(contractBailInfo);
			//此处最好还是处理一下有分录行但其实用户并没有真正输入值的情况
			if(row.getCell("bailAmount").getValue()!=null){
				bailEntryInfo.setAmount(FDCHelper.toBigDecimal(row.getCell("bailAmount").getValue(),2));
			}
			if(row.getCell("bailRate").getValue()!=null){
				bailEntryInfo.setProp(FDCHelper.toBigDecimal(row.getCell("bailRate").getValue(),2));
			}
			if(row.getCell("bailDate").getValue()!=null){
				bailEntryInfo.setBailDate(row.getCell("bailDate").getValue()==null?DateTimeUtils.truncateDate(new Date()):(Date)row.getCell("bailDate").getValue());
			}
			bailEntryInfo.setBailConditon((String)row.getCell("bailCondition").getValue());
			bailEntryInfo.setDesc((String)row.getCell("desc").getValue());
			coll.add(bailEntryInfo);
		}

		
		editData.setSignDate(DateTimeUtils.truncateDate(editData.getSignDate()));
		editData.setIsAmtWithoutCost(isUseAmtWithoutCost);
		editData.setOverRate(Double.valueOf(this.txtOverAmt.getText()).doubleValue());
		
		super.storeFields();
		
		//add by zkyan 此时editData.getProgrammingContract()为空，应放在storeFields之后
		// 维护源ID用于动态规划
		if (this.editData.getProgrammingContract() != null) {
			this.editData.setSrcProID(this.editData.getProgrammingContract().getId().toString());
		}
		
		
		storeDetailEntries();
		
		// 去掉合同编号的前后空格
		editData.setNumber(this.txtNumber.getText().trim());
		// 去掉合同名称的前后空格
		editData.setName(this.txtcontractName.getText().trim());

	}
	protected void prmtMainContract_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo view = null;		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSubContract",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("contractSourceId.id",ContractSourceInfo.COOP_VALUE));
		if (prmtpartB.getData() != null) 
			//加上过滤条件：战略主合同与子合同的乙方相同，added by Owen_wen 2010-8-18
			filter.getFilterItems().add(new FilterItemInfo("partB.id", ((SupplierInfo)prmtpartB.getData()).getId().toString())); 		
		if(prmtMainContract.getQueryAgent().getEntityViewInfo() != null){
			view = prmtMainContract.getQueryAgent().getEntityViewInfo();
			view.setFilter(filter);
		}else{
			view = new EntityViewInfo();
			view.setFilter(filter);
		}
		prmtMainContract.setEntityViewInfo(view);
		prmtMainContract.getQueryAgent().resetRuntimeEntityView();
		
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		ContractBillEditDataProvider dataPvd = new ContractBillEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		ContractBillEditDataProvider dataPvd = new ContractBillEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint");
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillFactory
				.getRemoteInstance();
	}

	/**
	 * 
	 * 描述：合同详细信息Table
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	private KDTable getDetailInfoTable() {
		return tblDetail;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		if(table==this.tblEconItem){
			return new ContractPayItemInfo();
		}else if(table==this.tblBail){
			ContractBailEntryInfo contractBailEntryInfo = new ContractBailEntryInfo();
			contractBailEntryInfo.setId(BOSUuid.create(contractBailEntryInfo.getBOSType()));
			return contractBailEntryInfo;
		}else{
			return new ContractBillEntryInfo();
		}
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();

		//objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setSignDate(serverDate);
//		objectValue.setCeremonyb(FDCHelper.ZERO);
		this.ceremonyb.setText("0.00");
//		this.txtamount.setEditable(false);
//		objectValue.setProgrammingContract(item)

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);

		// 默认直接合同
		objectValue.setContractPropert(ContractPropertyEnum.DIRECT);
		//默认乙方true
		prmtpartB.setRequired(true);
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		if (projId == null) {
			projId = editData.getCurProject().getId();
		}

		CurProjectInfo curProjectInfo = this.curProject;
		objectValue.setCurProject(curProjectInfo);
		if (curProjectInfo.getLandDeveloper() != null)
			objectValue.setLandDeveloper(curProjectInfo.getLandDeveloper());

		try {
			BigDecimal buildArea = FDCHelper.getApportionValue(curProjectInfo.getId().toString(),ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
			objectValue.setContractPrice(FDCHelper.divide(objectValue.getAmount(), buildArea,2,4));
			
//			默认委托
//			objectValue.setContractSourceId(ContractSourceFactory
//					.getRemoteInstance().getContractSourceInfo(
//							new ObjectUuidPK(ContractSourceInfo.TRUST_VALUE)));
			//以前是默认委托，现在改成成自动选择取列表中的第一项或者为空白
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			evi.setFilter(filter);
			SorterItemCollection sic = new SorterItemCollection();
			SorterItemInfo sorts = new SorterItemInfo();
			sorts.setPropertyName("number");
			sorts.setSortType(SortType.ASCEND);
			sic.add(sorts);	
			evi.setSorter(sic);
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			
			objectValue.setContractSourceId((ContractSourceInfo)(ContractSourceFactory.getRemoteInstance().getCollection(evi).get(0)));
			
			if (typeId != null) {
				ContractTypeInfo contractTypeInfo = null;

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("number");
				selector.add("name");
				selector.add("longNumber");
				selector.add("isLeaf");
				selector.add("dutyOrgUnit.id");
				selector.add("dutyOrgUnit.number");
				selector.add("dutyOrgUnit.name");
				selector.add("payScale");
				selector.add("stampTaxRate");
				selector.add("isCost");
				selector.add("isRefProgram");
				selector.add("isWorkLoadConfirm");
				contractTypeInfo = ContractTypeFactory
						.getRemoteInstance()
						.getContractTypeInfo(new ObjectUuidPK(typeId), selector);

				objectValue.setContractType(contractTypeInfo);
				objectValue.setRespDept(contractTypeInfo.getDutyOrgUnit());
				objectValue.setIsCoseSplit(contractTypeInfo.isIsCost());
				objectValue.setPayScale(contractTypeInfo.getPayScale());

				objectValue.setStampTaxRate(contractTypeInfo.getStampTaxRate());
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		// 签约日期
		// objectValue.setSignDate(new Date());

		//造价性质
		objectValue.setCostProperty(CostPropertyEnum.BASE_CONFIRM);

		/*
		 * 变更提示比例％数值设置缺省值5.00，手工修改（可以在【预警】设置初始值）。结算提示比例％数值设置缺省值100.00，手工修改（可以在【预警】设置初始值）。付款提示比例％数值设置缺省值85.00，手工修改（可以在【预警】设置初始值）。
		 */
		if(StringUtils.isEmpty(txtpayPercForWarn.getText())){
			objectValue.setPayPercForWarn(new BigDecimal("85"));
		}
		//modify by lihaiou.2014-07.07，如果DEP配置有值，则不修改
		if(StringUtils.isEmpty(txtchgPercForWarn.getText())){
			objectValue.setChgPercForWarn(new BigDecimal("5"));
		}

		//未结算
		objectValue.setHasSettled(false);

		objectValue.setGrtRate(FDCHelper.ZERO);

		objectValue.setCurrency(baseCurrency);
		objectValue.setExRate(FDCHelper.ONE);
		objectValue.setAmount(FDCHelper.ZERO);
		objectValue.setOriginalAmount(FDCHelper.ZERO);
		objectValue.setPayScale(FDCHelper.ZERO);
		objectValue.setGrtAmount(FDCHelper.ZERO);

		objectValue.setCU(curProjectInfo.getCU());
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);

		//期间
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		mainContractId = null;
		detailAmount = FDCHelper.ZERO;
		//还需要保存 履约保证金及返还部分
		ContractBailInfo contractBail=new ContractBailInfo();
		contractBail.setId(BOSUuid.create(contractBail.getBOSType()));
		objectValue.setBail(contractBail);
		objectValue.setIsFiveClass(false);
		return objectValue;
	}

	private void setInviteCtrlVisibleByContractSource(
			ContractSourceInfo contractSource) {
		if (contractSource == null || contractSource.getId() == null)
			return;
		String sourceId = contractSource.getId().toString();
		if (ContractSourceInfo.TRUST_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
		} else if (ContractSourceInfo.INVITE_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(true);
			// 二标价
			contsecondPrice.setVisible(false);
			// 底价
			contbasePrice.setVisible(false);

			//备注
			contRemark.setVisible(false);

			//			战略合作级别
			contCoopLevel.setVisible(false);
			// 计价方式
			contPriceType.setVisible(false);
			
			chkIsSubMainContract.setVisible(false);
			conMainContract.setVisible(false);
			conEffectiveEndDate.setVisible(false);
			conEffectiveStartDate.setVisible(false);
			conInformation.setVisible(false);
			kDScrollPane1.setVisible(false);

		} else if (ContractSourceInfo.DISCUSS_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			// 中标价
			contwinPrice.setVisible(true);
			// 中标单位
			contwinUnit.setVisible(true);
			// 二标价
			contsecondPrice.setVisible(true);
			// 底价
			contbasePrice.setVisible(true);

			//备注
			contRemark.setVisible(true);

		} else if (ContractSourceInfo.COOP_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			//战略合作级别
			contCoopLevel.setVisible(true);
//			// 计价方式
//			contPriceType.setVisible(true);
			chkIsSubMainContract.setVisible(true);
			conMainContract.setVisible(true);
			conEffectiveEndDate.setVisible(true);
			conEffectiveStartDate.setVisible(true);
			conInformation.setVisible(true);
			kDScrollPane1.setVisible(true);
			this.prmtMainContract.setEnabled(false);
		} else {
			setInviteCtrlVisible(false);
		}

	}
	/**
	 * 非独立计算的补充合同的合约规划不能修改
	 */
	private void setCntractPropertEnable() {
		if (editData != null && ContractPropertyEnum.SUPPLY.equals(editData.getContractPropert()) && editData.isIsAmtWithoutCost()) {
			prmtFwContract.setEnabled(false);
		}
	}

	private void setInviteCtrlVisible(boolean visible) {
		Component[] components = pnlInviteInfo.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(visible);
		}
	}

	/**
	 * 
	 * 描述：合同性质变化，合同详细信息的字段变化
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#contractPropert_itemStateChanged(java.awt.event.ItemEvent)
	 */
	ContractPropertyEnum contractPro = null;

	protected void contractPropert_itemStateChanged(ItemEvent e) throws Exception {
		
		if (e.getStateChange() == ItemEvent.SELECTED) {
			ContractPropertyEnum contractProp = (ContractPropertyEnum) e.getItem();
			contractPro = contractProp;
			contractPropertChanged(contractProp);

		}

		super.contractPropert_itemStateChanged(e);

	}

	private void contractPropertChanged(ContractPropertyEnum contractProp) {
		// // 是否参与编码规则
		// boolean flag = isInCode("contractPropert");
		// if (!flag) {
		// return;
		// }

		if (isFirstOnload()) {
			return;
		}
		if (contractProp != null) {
			// 甲方是否必录（只有第三方合同时才必录）
			prmtpartC.setRequired(contractProp == ContractPropertyEnum.THREE_PARTY);
			fillDetailByPropert(contractProp);

			try {
				setCodePropertyChanged(true);
				// 设置编码规则编码
				setNumberByCodingRule();
			} finally {
				setCodePropertyChanged(false);
			}
		}
	}

	

	class MyDataChangeListener implements DataChangeListener {
		// 设置主合同编码规则变化对单据编码设置 add by deeplove 2011-12-23
		public void dataChanged(DataChangeEvent eventObj) {
			String name = null;
			ContractBillInfo info = null;
			if (eventObj.getNewValue() != null) {
				info = (ContractBillInfo) eventObj.getNewValue();
				name = info.getName();
				mainContractId = info.getId().toString();
				if (isUseAmtWithoutCost && detailAmount.compareTo(FDCHelper.ZERO) > 0) {
					try {
						MsgContractHasSplit();
					} catch (EASBizException e) {
						handUIExceptionAndAbort(e);
					} catch (BOSException e) {
						handUIExceptionAndAbort(e);
					}
				}
				comboCurrency.setEnabled(false);
				for (int i = 0; i < comboCurrency.getItemCount(); i++) {
					Object o = comboCurrency.getItemAt(i);
					if (o instanceof CurrencyInfo) {
						CurrencyInfo c = (CurrencyInfo) o;
						if (c.getId().equals(info.getCurrency().getId())) {
							comboCurrency.setSelectedIndex(i);
						}
					}
				}
				comboCurrency.setEditable(false);
				txtExRate.setValue(info.getExRate());
				txtExRate.setEditable(false);
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("programmingContract.*");
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(info.getId().toString()), sic);
				} catch (Exception e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				}
				// 如果非单独计算补充合同
				if (isLonelyCalCombo.getSelectedItem().equals(BooleanEnum.FALSE) && info.getProgrammingContract() != null) {
					prmtFwContractTemp.setValue(info.getProgrammingContract());
//					prmtFwContract.setText(info.getProgrammingContract().getName());
					prmtFwContract.setValue(info.getProgrammingContract());
					prmtFwContract.setEnabled(false);
					txtControlAmount.setValue(info.getProgrammingContract().getAmount());
					editData.setProgrammingContract(info.getProgrammingContract());					
				}
			} else {
				name = null;
				// 清除主合同也清除关联的框架合约
				prmtFwContractTemp.setValue(null);
				prmtFwContract.setText(null);
				prmtFwContract.setEnabled(true);
				editData.setProgrammingContract(null);
			}
			//			comboCurrency.setSelectedItem(info.getCurrency());
			//设置补充合同币别和汇率和主合同一致

			getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),
					CONTENT_COL).setValue(name);
			try {		
				if (info != null) {
					contractMainChanged(info.getNumber());
				}
			} catch (CodingRuleException e) {
				handUIExceptionAndAbort(e);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}

	}

	/**
	 * 
	 * 描述：根据行标识获取行Index
	 * 
	 * @param rowKey
	 * @return
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	private int getRowIndexByRowKey(String rowKey) {
		int rowIndex = 0;

		int rowCount = getDetailInfoTable().getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = (String) getDetailInfoTable().getCell(i, ROWKEY_COL)
					.getValue();
			if (key != null && key.equals(rowKey)) {
				rowIndex = i;
				break;
			}
		}

		return rowIndex;
	}
	/**
	 * getRowIndexByRowKey方法中判断金额行时，如果没有金额行，会默认返回第一行
	 * 造成保存三方合同和补充合同时中断，故增加此方法判断合同详细信息中是否存在金额行
	 */
	private boolean isHasAMRow() {
		boolean isHas = false;
		int rowCount = getDetailInfoTable().getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = (String) getDetailInfoTable().getCell(i, ROWKEY_COL).getValue();
			if (key != null && key.equals(AM_ROW)) {
				isHas = true;
				break;
			}
		}

		return isHas;
	}
	// 是否刚刚显示四条附加记录
	boolean lastDispAddRows = false;

	/**
	 * 
	 * 描述：补充合同则合同详细信息要多显示4个字段
	 * 
	 * @param contractProp
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	private void fillDetailByPropert(ContractPropertyEnum contractProp) {

		if (contractProp == ContractPropertyEnum.SUPPLY) {

			if (lastDispAddRows) {
				ICell cell = getDetailInfoTable().getRow(
						getRowIndexByRowKey(NU_ROW)).getCell(CONTENT_COL);
				if (contractProp == ContractPropertyEnum.SUPPLY) {
					cell.getStyleAttributes().setBackground(
							FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					if (cell.getEditor() != null
							&& cell.getEditor().getComponent() instanceof KDBizPromptBox) {
						KDBizPromptBox box = (KDBizPromptBox) cell.getEditor()
								.getComponent();
						box.setRequired(true);
					}
				} else {
					cell.getStyleAttributes().setBackground(Color.WHITE);
					if (cell.getEditor() != null
							&& cell.getEditor().getComponent() instanceof KDBizPromptBox) {
						KDBizPromptBox box = (KDBizPromptBox) cell.getEditor()
								.getComponent();
						box.setRequired(false);
					}
				}
				return;
			}

			/*
			 * 内容
			 */
			String isLonelyCal = ContractClientUtils.getRes("isLonelyCal");
			String amountWithOutCost = ContractClientUtils
					.getRes("amountWithOutCost");
			String mainContractNumber = ContractClientUtils
					.getRes("mainContractNumber");
			String mainContractName = ContractClientUtils
					.getRes("mainContractName");

			/*
			 * 是否单独计算
			 */
			IRow isLonelyCalRow = getDetailInfoTable().addRow();
			isLonelyCalRow.getCell(ROWKEY_COL).setValue(LO_ROW);
			isLonelyCalRow.getCell(DETAIL_COL).setValue(isLonelyCal);
			KDComboBox isLonelyCalCombo = getBooleanCombo();
			KDTDefaultCellEditor isLonelyCalComboEditor = new KDTDefaultCellEditor(
					isLonelyCalCombo);
			isLonelyCalRow.getCell(CONTENT_COL).setEditor(
					isLonelyCalComboEditor);
			isLonelyCalRow.getCell(CONTENT_COL).setValue(BooleanEnum.TRUE); // 默认是
			isLonelyCalCombo.addItemListener(new IsLonelyChangeListener());
			isLonelyCalRow.getCell(DATATYPE_COL).setValue(DataTypeEnum.BOOL);

			/*
			 * 补充协议金额
			 */
			IRow amountWithOutCostRow = getDetailInfoTable().addRow();
			amountWithOutCostRow.getCell(ROWKEY_COL).setValue(AM_ROW);
			amountWithOutCostRow.getCell(DETAIL_COL)
					.setValue(amountWithOutCost);

			amountWithOutCostRow.getCell(CONTENT_COL).setEditor(
					FDCClientHelper.getNumberCellEditor());
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
			// 如果为是，则“补充协议金额”不允许录入
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			amountWithOutCostRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.NUMBER);

			/*
			 * 对应主合同编码
			 */
			IRow mainContractNumberRow = getDetailInfoTable().addRow();
			mainContractNumberRow.getCell(ROWKEY_COL).setValue(NU_ROW);
			mainContractNumberRow.getCell(DETAIL_COL).setValue(mainContractNumber);
			KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
			KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(kDBizPromptBoxContract);
			ICell cell = mainContractNumberRow.getCell(CONTENT_COL);
			cell.setEditor(prmtContractEditor);
			ObjectValueRender objectValueRender = getF7Render();
			cell.setRenderer(objectValueRender);

			MyDataChangeListener lis = new MyDataChangeListener();
			kDBizPromptBoxContract.addDataChangeListener(lis);

			mainContractNumberRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				cell.getStyleAttributes().setBackground(
						FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				kDBizPromptBoxContract.setRequired(true);
			} else {
				cell.getStyleAttributes().setBackground(Color.WHITE);
				kDBizPromptBoxContract.setRequired(false);
			}

			/*
			 * 对应主合同名称
			 */
			IRow mainContractNameRow = getDetailInfoTable().addRow();
			mainContractNameRow.getCell(ROWKEY_COL).setValue(NA_ROW);
			mainContractNameRow.getCell(DETAIL_COL).setValue(mainContractName);
			mainContractNameRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);

			mainContractNameRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
			
			mainContractNameRow.getCell(CONTENT_COL).getStyleAttributes().setUnderline(true);
			mainContractNameRow.getCell(CONTENT_COL).getStyleAttributes().setFontColor(Color.BLUE);

			lastDispAddRows = true;
		} else {
			if (lastDispAddRows) {
				isLonelyChanged(BooleanEnum.TRUE);
				getDetailInfoTable().removeRow(getRowIndexByRowKey(LO_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(AM_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NU_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NA_ROW));
			}

			lastDispAddRows = false;
		}

		// 默认值
		isUseAmtWithoutCost = false;
		this.ceremonyb.setNegatived(false);
		// 不能负数则置0
		if (FDCHelper.compareTo(ceremonyb.getBigDecimalValue(), FDCHelper.ZERO) < 0) {
			this.ceremonyb.setValue(FDCHelper.ZERO);
			this.ceremonybb.setValue(FDCHelper.ZERO);
			this.txtamount.setValue(FDCHelper.ZERO);
			this.txtLocalAmount.setValue(FDCHelper.ZERO);
		}
		btnProgram.setEnabled(true);
	}

	private void addKDTableLisener() {
		tblDetail.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				comboCurrency.setEnabled(true);
				txtExRate.setEditable(true);
				KDTSelectManager sm = tblDetail.getSelectManager();
				if (sm.getActiveRowIndex() == getRowIndexByRowKey(LO_ROW)
						&& sm.getActiveColumnIndex() == 2) {
					if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
						e.setCancel(true);
					}
				}
			}
		});
	}

	private ObjectValueRender getF7Render() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		BizDataFormat format = new BizDataFormat("$number$");
		objectValueRender.setFormat(format);
		return objectValueRender;
	}

	private KDBizPromptBox getContractF7Editor() {
		KDBizPromptBox kDBizPromptBoxContract = new KDBizPromptBox();
		kDBizPromptBoxContract
				.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
		kDBizPromptBoxContract.setDisplayFormat("$number$");
		kDBizPromptBoxContract.setEditFormat("$number$");
		kDBizPromptBoxContract.setCommitFormat("$number$");
		kDBizPromptBoxContract.setEditable(true);
		FilterInfo filter = new FilterInfo();
		
		FilterItemCollection filterItems = filter.getFilterItems();
		//		filterItems.add(new FilterItemInfo("contractPropert", ContractPropertyEnum.DIRECT_VALUE));
		//		2009-01-13 yx 支持补充合同的主合同为三方合同(财富联合 R081201-122)
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,
				CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled",
				Boolean.TRUE));
		//1.4.3.2.2 只能是已经审批的合同
//		filterItems.add(new FilterItemInfo("state",
//				FDCBillStateEnum.AUDITTED_VALUE));
		Set stateSet = new HashSet();
		stateSet.add(FDCBillStateEnum.AUDITTED_VALUE);
		if(isSupply){
			stateSet.add(FDCBillStateEnum.AUDITTING_VALUE);
			stateSet.add(FDCBillStateEnum.SUBMITTED_VALUE);
		}
		filterItems.add(new FilterItemInfo("state", stateSet, CompareType.INCLUDE));
		//2008-09-17 周勇 已结算的合同不需要出现
		filterItems.add(new FilterItemInfo("hasSettled", Boolean.FALSE));

		String projId = null;
		if (editData.getCurProject() != null
				&& editData.getCurProject().getId() != null) {
			projId = editData.getCurProject().getId().toString();
		} else if (getUIContext().get("projectId") != null) {
			projId = ((BOSUuid) getUIContext().get("projectId")).toString();
		}
		if (projId != null) {
			filterItems.add(new FilterItemInfo("curProject.id", projId
					.toString()));
		}
		if (editData.getId() != null) {
			filterItems.add(new FilterItemInfo("id", editData.getId()
					.toString(), CompareType.NOTEQUALS));
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		kDBizPromptBoxContract.setEntityViewInfo(view);
		return kDBizPromptBoxContract;
	}

	private KDComboBox getBooleanCombo() {
		isLonelyCalCombo = new KDComboBox();
		isLonelyCalCombo.addItems(BooleanEnum.getEnumList().toArray());
		return isLonelyCalCombo;
	}

	class IsLonelyChangeListener implements ItemListener {
		/*
		 * ? 是否单独计算：布尔型，下拉列表，枚举值：是、否。必录项，缺省为是。不可编辑。
		 * 如果为是，则“不计成本的金额”不允许录入，在“合同金额”录入。 如果为否，则“不计成本的金额”需要录入，“合同金额”不允许录入。
		 * 如果在“合同金额”里录入了数据，“是否单独计算”又改为否，则系统自动将“合同金额”里的数据写到“不计成本的金额”里；反之也一样。
		 */
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				BooleanEnum b = (BooleanEnum) e.getItem();
				isLonelyChanged(b);
			}
		}
	}

	/**
	 * 是否单独计算发生改变
	 * @param b
	 */
	private void isLonelyChanged(BooleanEnum b) {
		ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW))
				.getCell(CONTENT_COL);
		BigDecimal cereBigdeDecimal=ceremonyb.getBigDecimalValue();
		if (b == BooleanEnum.TRUE) {
			// 将关联框架按钮恢复
			btnProgram.setEnabled(true);
			cell.getStyleAttributes().setLocked(true);
			txtamount.setEditable(false);
			ceremonyb.setEditable(true);
			txtGrtRate.setValue(FDCHelper.ZERO);
			txtGrtRate.setRequired(true);
			txtGrtRate.setEditable(true);
			if (cell.getValue() != null && cell.getValue() instanceof Number) {
				txtamount.setNumberValue((Number) cell.getValue());
				calLocalAmt();
				cell.setValue(null);
			}
			setAmountRequired(true);

			isUseAmtWithoutCost = false;
			// 清除主合同框架合约
			//prmtFwContractTemp.setValue(null);
			//prmtFwContract.setText(null);
			editData.setProgrammingContract(null);
		} else {
			// 将关联框架按钮恢复
			btnProgram.setEnabled(false);
			cell.getStyleAttributes().setLocked(true);
			txtamount.setEditable(false);
			ceremonyb.setEditable(false);
			//ceremonyb.setValue(FDCHelper.ZERO);
			//ceremonybb.setValue(FDCHelper.ZERO);
			//若为不独立结算的补充合同 则保修比例不允许录入
			if (contractPro == ContractPropertyEnum.SUPPLY) {
				txtGrtRate.setRequired(false);
				txtGrtRate.setValue(FDCHelper.ZERO);
				txtGrtRate.setEditable(false);
				txtGrtAmount.setRequired(false);
				txtGrtAmount.setEditable(false);
			}
			if (txtamount.getNumberValue() != null) {
				cell.setValue(txtamount.getNumberValue());
				if (!isUseAmtWithoutCost) {
					cell.setValue(null);
				} else {
					cell.setValue(txtamount.getNumberValue());
				}
				try {
					MsgContractHasSplit();
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			}
			// 建发新需求
			// txtamount.setValue(null);
			// txtLocalAmount.setValue(null);
			txtStampTaxAmt.setValue(null);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			calStampTaxAmt();
			// 带入主合同框架合约
			if (mainContractId != null && getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW), CONTENT_COL).getValue() != null) {
				ContractBillInfo info = null;
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("programmingContract.*");
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(mainContractId.toString()), sic);
				} catch (Exception e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				}
				// 如果非单独计算补充合同
				if (info.getProgrammingContract() != null) {
					prmtFwContractTemp.setValue(info.getProgrammingContract());
					//					prmtFwContract.setText(info.getProgrammingContract().getName());
					prmtFwContract.setValue(info.getProgrammingContract());
					editData.setProgrammingContract(info.getProgrammingContract());
				}
			}
			ICell cell2 = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW))
			.getCell(CONTENT_COL);
			cell2.setValue(cereBigdeDecimal);
			cell2.setUserObject(cereBigdeDecimal);
		
		}
		//by tim_gao 根据是否独立控制签约金额状态
		checkSignAmountByIsLongCal();
	}

	private void setAmountRequired(boolean required) {
		txtamount.setRequired(required);
		txtLocalAmount.setRequired(required);
	}

	/**
	 * 描述：合同形成方式变化，招标/议标信息控件变化
	 */
	protected void contractSource_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		setInviteCtrlVisibleByContractSource((ContractSourceInfo) newValue);
		Object obj = newValue;
		if (obj != null) {
			ContractSourceInfo conSourceInfo = (ContractSourceInfo) obj;
			if (conSourceInfo.getName().indexOf(STRATEGYPACT_NAME) != -1) {
				contInviteProject.setVisible(false);
				tenderDiscusstion.setVisible(false);
				contStrategyPact.setVisible(true);
				conEntrustReason.setVisible(false);
			} else if (INVITEPROJECT_NAME.equals(conSourceInfo.getName())) {
				contStrategyPact.setVisible(false);
				tenderDiscusstion.setVisible(false);
				contInviteProject.setVisible(true);
				conEntrustReason.setVisible(false);
			} else if (TENDERDISCUSSTION_NAME.equals(conSourceInfo.getName())) {
				contStrategyPact.setVisible(false);
				contInviteProject.setVisible(false);
				tenderDiscusstion.setVisible(true);
				conEntrustReason.setVisible(false);
			} else {
				contStrategyPact.setVisible(false);
				contInviteProject.setVisible(false);
				tenderDiscusstion.setVisible(false);
				conEntrustReason.setVisible(true);
			}
		} else {
			contStrategyPact.setVisible(false);
			contInviteProject.setVisible(false);
			tenderDiscusstion.setVisible(false);
			conEntrustReason.setVisible(false);
		}
		super.contractSource_dataChanged(e);
	}

	/**
	 * output contractSource_willShow method
	 */
	protected void contractSource_willShow(
			com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception {
	}

	// 子类可以重载
	protected void fetchInitParam() throws Exception {

		super.fetchInitParam();

		Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			IObjectPK comPK = new ObjectUuidPK(orgUnitInfo.getId()
						.toString());
			HashMap hmParamIn = new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_STARTMG, comPK);
	        hmParamIn.put(FDCConstants.FDC_PARAM_SELECTPERSON, null);
	        //项目合同签约总金额超过项目
	        hmParamIn.put(FDCConstants.FDC_PARAM_OUTCOST, comPK);
	      //自定义参数
	        hmParamIn.put(FDCConstants.FDC_PARAM_CONTROLTYPE, comPK);
	        
	        //合同审批前进行拆分
	        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITBFAUDIT, comPK);
	        //合同费用项目 2008-12-10
	        hmParamIn.put(FDCConstants.FDC_PARAM_CHARGETYPE,null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_SELECTSUPPLY, comPK);
	        
	        param = FDCUtils.getParamHashMapBatch(null, hmParamIn);
		}
		
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString())
					.booleanValue();
		}
		if (param.get(FDCConstants.FDC_PARAM_OUTCOST) != null) {
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if (param.get(FDCConstants.FDC_PARAM_CONTROLTYPE) != null) {
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)
					.toString();
		}

		//splitBeforeAudit
		if (param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT) != null) {
			splitBeforeAudit = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString())
					.booleanValue();
		}
		//是否显示“合同费用项目”
		if(param.get(FDCConstants.FDC_PARAM_CHARGETYPE) != null){
			isShowCharge = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CHARGETYPE).toString()).booleanValue();			
		}
		if(param.get(FDCConstants.FDC_PARAM_SELECTSUPPLY) != null){
			isSupply = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTSUPPLY).toString()).booleanValue();			
		}
	}
	protected void txtBailOriAmount_dataChanged(DataChangeEvent e)
			throws Exception {
		EventListener[] listeners = txtBailRate.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			txtBailRate.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		try{
			BigDecimal bailRate= FDCHelper.ZERO;
			BigDecimal bailOriAmount= FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2);
			BigDecimal contractAmount=FDCHelper.toBigDecimal(txtamount.getNumberValue(),2);
			if(contractAmount.compareTo(FDCHelper.ZERO)!=0){
				bailRate=bailOriAmount.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("履约保证金比例大于100%");
					txtBailOriAmount.setValue(null);
					SysUtil.abort();
				}
				this.txtBailRate.setValue(bailRate);
			}
		}finally{
			for(int i=0;i<listeners.length;i++){
				txtBailRate.addDataChangeListener((DataChangeListener)listeners[i]);
			}
		}
		//如果履约保证金发生改变的话，相应的分录也应该发生改变
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()!=null&&txtamount.getNumberValue()!=null){
				BigDecimal bailAmount=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(),FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2)),FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()==null&&txtamount.getNumberValue()!=null&&this.tblBail.getRow(i).getCell("bailAmount").getValue()!=null){
				//反算比例
				BigDecimal bailRate=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE), FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2));
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("返回比例大于100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
		
	}
	
	protected void txtBailRate_dataChanged(DataChangeEvent e) throws Exception {
		EventListener[] listeners = txtBailOriAmount.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			txtBailOriAmount.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		try{
			BigDecimal bailOriAmount= FDCHelper.ZERO;
			BigDecimal bailRate= FDCHelper.toBigDecimal(txtBailRate.getNumberValue(),2);
			BigDecimal contractAmount=FDCHelper.toBigDecimal(txtamount.getNumberValue(),2);
			bailOriAmount=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(bailRate,contractAmount),FDCHelper.ONE_HUNDRED));
			this.txtBailOriAmount.setValue(bailOriAmount);
		
		}finally{
			for(int i=0;i<listeners.length;i++){
				txtBailOriAmount.addDataChangeListener((DataChangeListener)listeners[i]);
			}
		}
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()!=null&&txtamount.getNumberValue()!=null){
				BigDecimal bailAmount=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(),FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2)),FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()==null&&txtamount.getNumberValue()!=null&&this.tblBail.getRow(i).getCell("bailAmount").getValue()!=null){
				//反算比例
				BigDecimal bailRate=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE), FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2));
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("返回比例大于100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
	}
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		this.contAttachmentNameList.setEnabled(true);
		//为comboAttachmentNameList再次设置组件访问权限，否则即使是在可视可编辑的情况下也不能选择下拉列表框中的项  by Cassiel_peng
		this.comboAttachmentNameList.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.comboAttachmentNameList.setEnabled(true);
		this.comboAttachmentNameList.setEditable(true);
		
		//设置容器不可收缩
		this.kDContainer1.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contPayItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contBailItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);

		
		// 工作流处理时修改字段
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editAuditProgColumn(); // 在改类中实现以下方法
			}
		});
		// 如果为补充合同则关联框架合约至灰
		if (mainContractId != null && BooleanEnum.FALSE.equals(getDetailInfoTable().getCell(getRowIndexByRowKey(LO_ROW), CONTENT_COL).getValue() )) {
			this.btnProgram.setEnabled(false);
		} else {
			this.btnProgram.setEnabled(true);
		}
		// 设置关联框架合约按钮为灰
		if (getOprtState().equals(OprtState.VIEW)) {
			Object isFromWorkflow = getUIContext().get("isFromWorkflow");
			if (isFromWorkflow != null && isFromWorkflow.toString().equals("true")) {
				this.btnProgram.setEnabled(true);
			} else {
				this.btnProgram.setEnabled(false);
			}
		}
		contOrgAmtBig.setLocation(5, 5);
		isWin();
		checkSignAmountByIsLongCal();
		kDDateCreateTime.setDatePattern("yyyy-MM-dd   HH:mm:ss");
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);

		this.initProgramControlMode();
		
		// modify by yxl 20151111 拆分页签表格初始化合约规划列，打开编辑界面时，默认加载拆分页签
		kDTabbedPane1.setSelectedComponent(kDPanel2);
		final KDBizPromptBox kdtEntrys_programming_PromptBox = new KDBizPromptBox();
		kdtEntrys_programming_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ProgrammingContractQuery");
//			 kdtEntrys_programming_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");
		kdtEntrys_programming_PromptBox.setDisplayFormat("$number$");
		kdtEntrys_programming_PromptBox.setEditFormat("$number$");
		kdtEntrys_programming_PromptBox.setCommitFormat("$number$");
//		kdtEntrys_programming_PromptBox.setSelector(new KDPromptSelector() {
//			public void show() {
//				try {
//					UIContext context = new UIContext(ContractBillEditUI.this);
//					Object object = getUIContext().get("projectId");
//					if (object == null && editData.getCurProject() != null) {
//						object = editData.getCurProject().getId();
//					}
//					context.put("projectId", object);
//					context.put("allowZero", Boolean.FALSE);
//					//新建界面生成 uiwindow(合约框架F7)对象
//					UIFactory.createUIFactory().create(ProgrammingContractF7UI.class.getName(), context).show();
//				} catch (Exception e) {
//					handUIExceptionAndAbort(e);
//				}
//			}
//			public boolean isCanceled() {
//				return false;
//			}
//			//得到返回的值
//			public Object getData() {
//				return getUIContext().get("selectedValue");
//			}
//		});
		KDTDefaultCellEditor kdtEntrys_programming_CellEditor = new KDTDefaultCellEditor(kdtEntrys_programming_PromptBox);
		kdtSplitEntry.getColumn("programming").setEditor(kdtEntrys_programming_CellEditor);
		ObjectValueRender tblEconItem_payType_OVR = new ObjectValueRender();
		tblEconItem_payType_OVR.setFormat(new BizDataFormat("$name$"));
		kdtSplitEntry.getColumn("programming").setRenderer(tblEconItem_payType_OVR);
		if(editData.getCurProject()!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isWholeAgeStage");
			CurProjectInfo curInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(editData.getCurProject().getId()),sic);
			isWholeAgeProject = curInfo.isIsWholeAgeStage();
			this.prmtFwContract.setEnabled(!curInfo.isIsWholeAgeStage());
			kdtSplitEntry.getColumn("programming").getStyleAttributes().setLocked(!curInfo.isIsWholeAgeStage());
			//
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filInfo = new FilterInfo();
			if(curInfo.isIsWholeAgeStage()){
				
			}else{
				filInfo.getFilterItems().add(new FilterItemInfo("programming.project.id",editData.getCurProject().getId()));
				filInfo.getFilterItems().add(new FilterItemInfo("programming.isLatest","1"));
			}
			view.setFilter(filInfo);
		    kdtEntrys_programming_PromptBox.setEntityViewInfo(view);
		}
		KDTSortManager sortManager = new KDTSortManager(kdtSplitEntry){
			public void sort(int colIndex) {
				super.sort(colIndex);
			}
		};   
		sortManager.setSortAuto(false);   
		sortManager.setClickCount(10);
		for(int i = 0; i<kdtSplitEntry.getColumnCount();i++){  
		    this.kdtSplitEntry.getColumn(i).setSortable(false);   
		}  
	}
	
	private void setProgrammingContractCellF7(int rowIndex, final Object project){
		KDBizPromptBox prmtPC = new KDBizPromptBox();
//		KDBizPromptBox prmtPC = (KDBizPromptBox)kdtSplitEntry.getCell(rowIndex,"programming").getEditor().getComponent();
//		final BOSUuid proId = BOSUuid.read(projectId);
		prmtPC.setDisplayFormat("$name$");
		prmtPC.setEditFormat("$name$");
		prmtPC.setCommitFormat("$name$");
		prmtPC.setSelector(new KDPromptSelector() {
			public void show() {
				try {
					UIContext context = new UIContext(ContractBillEditUI.this);
					Object object = project;
					if (object == null && editData.getCurProject() != null) {
						object = editData.getCurProject().getId();
					}
					context.put("projectId", object);
					context.put("allowZero", Boolean.FALSE);
					//新建界面生成 uiwindow(合约框架F7)对象
					UIFactory.createUIFactory().create(ProgrammingContractF7UI.class.getName(), context).show();
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
			public boolean isCanceled() {
				return false;
			}
			//得到返回的值
			public Object getData() {
				return getUIContext().get("selectedValue");
			}
		});
		kdtSplitEntry.getCell(rowIndex, "programming").setEditor(new KDTDefaultCellEditor(prmtPC));
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		kdtSplitEntry.getCell(rowIndex, "programming").setRenderer(kdtCostEntries_costAccount_OVR);
	}
	
	private void reloadPanel(){
		if(tabPanel.getSelectedIndex() == tabPanel.indexOfTab("合同拆分")){
			if("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState()) ){
				Object[] objs = new Object[3];
				objs[0] = txtNumber.getText();
				objs[1] = txtcontractName.getText();
				objs[2] = ceremonyb.getBigDecimalValue();
				conSplitUI.reloadCompoent(objs);
				if(prmtFwContract.getValue()!=null && prmtFwContract.getValue() instanceof ProgrammingContractInfo){
					ProgrammingContractInfo contractInfo = (ProgrammingContractInfo)prmtFwContract.getValue();
					conSplitUI.loadProgrammingContractInfo(contractInfo);
				}
			}
		}
	}
	
	private void addContSplitTab(String id, String uiClass, String title){
		try {
			KDScrollPane scrollPane=new KDScrollPane();
			tabPanel.add(scrollPane,title);
			UIContext uiContext = new UIContext(this);
			String state = null;
			if(id == null){
				state = "ADDNEW";
				uiContext.put("fromContractNew",editData);
			}else{
				uiContext.put(UIContext.ID, id);
				state = "EDIT";
			}
			CoreUIObject plUI = (CoreUIObject)UIFactoryHelper.initUIObject(uiClass, uiContext,null,state);
			scrollPane.setViewportView(plUI);
			scrollPane.setKeyBoardControl(true);
			conSplitUI = (ContractCostSplitEditUI)plUI;
		} catch (UIException e) {
			handUIException(e);
		}
	}
	
	private String getSplitId(String contractId) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		ContractCostSplitCollection contractCostSplitCollection = ContractCostSplitFactory
				.getRemoteInstance().getContractCostSplitCollection(view);
		if (contractCostSplitCollection.size() > 0) {
			ContractCostSplitInfo info = contractCostSplitCollection.get(0);
			return info.getId().toString();
		}
		return null;
	}

	private void initCtn() {
		if (btnAdd1 != null || btnRemove1 != null || btnAdd2 != null || btnRemove2 != null) {
			return;
		}
		btnAdd1 = contPayItem.add(new ItemAction() {

			public void actionPerformed(ActionEvent arg0) {
				addLine(tblEconItem);
				appendFootRow(tblEconItem);
				EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			}

		});
		btnRemove1 = contPayItem.add(new ItemAction() {

			public void actionPerformed(ActionEvent arg0) {
				tblEconItem.checkParsed();
				int index = -1;
				index = tblEconItem.getSelectManager().getActiveRowIndex();
				if (index != -1) {
					tblEconItem.removeRow(index);
				}
			}
		});

		this.contPayItem.setVisibleOfExpandButton(true);
		btnAdd1.setText("新增分录");
		btnRemove1.setText("删除分录");
		btnAdd1.setRequestFocusEnabled(false);
		btnRemove1.setRequestFocusEnabled(false);
		btnAdd1.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemove1.setIcon(EASResource.getIcon("imgTbtn_deleteline"));

		btnAdd2 = contBailItem.add(new ItemAction() {
			public void actionPerformed(ActionEvent arg0) {
				addLine(tblBail);
				appendFootRow(tblBail);
				EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
			}

		});
		btnRemove2 = contBailItem.add(new ItemAction() {
			public void actionPerformed(ActionEvent arg0) {
				tblBail.checkParsed();
				int index = -1;
				index = tblBail.getSelectManager().getActiveRowIndex();

				if (index != -1) {
					tblBail.removeRow(index);
					if (tblBail.getRowCount() <= 0) {
						txtBailOriAmount.setRequired(false);
						txtBailRate.setRequired(false);
					}
				}
			}
		});
		btnAdd2.setText("新增分录");
		btnRemove2.setText("删除分录");
		this.contBailItem.setVisibleOfExpandButton(true);
		btnAdd2.setRequestFocusEnabled(false);
		btnRemove2.setRequestFocusEnabled(false);
		btnAdd2.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemove2.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
	}
	
	//modify by yxl 20151022 点击成本指标库按钮跳转到成本指标库编辑界面
	protected void btnBuildPriceIndex_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId() == null){
			MsgBox.showInfo("请先保存合同单据！");
		}else{
			UIContext uiContext = new UIContext(this);
			uiContext.put("contractInfo", editData);
			uiContext.put("contractStationType", "sign");
			String state = OprtState.ADDNEW;
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select fid from CT_COS_BuildPriceIndex where CFContractId='"+editData.getId().toString()+"' and CFContractStation='10'");
			IRowSet rs = builder.executeQuery();
			if(rs.next() && rs.getString(1) != null){
				state = OprtState.VIEW;
				uiContext.put("ID", rs.getString(1));
			}
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					BuildPriceIndexEditUI.class.getName(), uiContext, null,state);
			uiWindow.show();
		}
		
	}
	
	public boolean isContractRaleToBuildPriceIndex(String contractId) throws Exception {
		boolean flag = false;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from CT_COS_BuildPriceIndex where CFContractId='"+contractId+"' and CFContractStation='10'");
		IRowSet rs = builder.executeQuery();
		if(rs.next() && rs.getString(1) != null){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 在编辑状态下动步取到最新版本对应的合约规划
	 * 
	 * @throws BOSException
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private void initProgrammingContract() throws BOSException, NumberFormatException, SQLException {
		String newVerPcID = editData.getSrcProID();
		ProgrammingContractInfo pcInfo = editData.getProgrammingContract();
		if (pcInfo != null) {
			BOSUuid id = pcInfo.getProgramming().getId();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FIsLatest from T_CON_Programming ");
			builder.appendSql("where FID = '" + id + "' ");
			IRowSet iRowSet = builder.executeQuery();
			if (iRowSet.next()) {
				boolean isLates = iRowSet.getBoolean("FIsLatest");
				if (isLates) {// 如果当前版本还是最新版
					// 不做处理
				} else {// 如果当前版本不是最新版了
					// 读出最新版相对应的框架合约
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("id", newVerPcID, CompareType.EQUALS));
					view.setFilter(filter);
					ProgrammingContractCollection pcCol = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view);
					if (pcCol.size() != 0) {
						ProgrammingContractInfo pcTemp = pcCol.get(0);
						editData.setProgrammingContract(pcTemp);
						prmtFwContractTemp.setData(pcTemp);
						
						/* modified by zhaoqin for R131230-0044 on 2013/12/31 start */
						//txtcontractName.setText(pcTemp.getName());
						//txtNumber.setText(pcTemp.getNumber());
						/* modified by zhaoqin for R131230-0044 on 2013/12/31 end */
						
						txtamount.setValue(pcTemp.getControlBalance());

						//						prmtFwContract.setText(pcTemp.getName());
						prmtFwContract.setValue(pcTemp);
					}
				}
			}
		}
	}
	
	/*
	 * 工作流处理时修改字段
	 */
	private void editAuditProgColumn() {
		// 判断是否在工作流审批中
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		// 调试
		logger.error(isFromWorkflow + "************************");
		logger.error(getOprtState().equals(STATUS_FINDVIEW) + "");
		logger.error(actionSave.isVisible() + "");
		logger.error((editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING) + "");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_FINDVIEW) && actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING)) {

			// 首先锁定界面上所有的空间
			this.lockUIForViewStatus();

			// 首先给控件个访问的权限，然后设置是否可编辑就可以了！不能使用unloack方法，这样会使所用的控件处于edit的状态
			this.prmtFwContractTemp.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtFwContractTemp.setEditable(true);
			this.actionSave.setEnabled(true);

		}
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}
	
	protected void tblEconItem_editStopped(KDTEditEvent e) throws Exception {
		BigDecimal contractAmount = FDCHelper.ZERO;
		if(isUseAmtWithoutCost){
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}
			if(FDCHelper.toBigDecimal(newValue).compareTo(FDCHelper.ZERO)==1){
				contractAmount =FDCHelper.toBigDecimal(newValue);
			}
			
		}else{
			contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue(),2);
		
		}
		
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn payAmountCol=this.tblEconItem.getColumn("payAmount");
		IColumn payRateCol=this.tblEconItem.getColumn("payRate");
		if(colIndex==payAmountCol.getColumnIndex()){
			BigDecimal cellPayRateValue=FDCHelper.ZERO;
			BigDecimal cellPayAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
			if(contractAmount.compareTo(FDCHelper.ZERO)!=0){//除数不能为0
				cellPayRateValue=cellPayAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellPayRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("付款比例大于100%");
					this.tblEconItem.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblEconItem.getCell(rowIndex, "payRate").setValue(cellPayRateValue);
			}
		}
		if(colIndex==payRateCol.getColumnIndex()){
			BigDecimal cellPayAmountValue=FDCHelper.ZERO;
			BigDecimal cellPayRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
			cellPayAmountValue=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(contractAmount,cellPayRateValue),FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_UP),2);
			this.tblEconItem.getCell(rowIndex, "payAmount").setValue(cellPayAmountValue);
		}
		/*
		if(colIndex==this.tblEconItem.getColumn("payType").getColumnIndex()){
			//添加付款类型F7
			KDBizPromptBox bizPayTypeBox = new KDBizPromptBox();
			bizPayTypeBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			bizPayTypeBox.setCommitFormat("$number$");
		    bizPayTypeBox.setDisplayFormat("$name$");
		    bizPayTypeBox.setEditFormat("$number$");
		    bizPayTypeBox.setRequired(true);
			KDTDefaultCellEditor payTypeEditor=new KDTDefaultCellEditor(bizPayTypeBox);
			this.tblEconItem.getColumn("payType").setEditor(payTypeEditor);
		}
		*/
		if(colIndex==this.tblEconItem.getColumn("date").getColumnIndex()){
			KDDatePicker payDataPicker = new KDDatePicker();
			payDataPicker.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
			ICellEditor payDateEditor = new KDTDefaultCellEditor(payDataPicker);
			this.tblEconItem.getColumn("date").setEditor(payDateEditor);
		}
		detailTableAutoFitRowHeight(tblEconItem);
	}
	/**
	 * 2009-11-23日修改：计算返还金额时应该用履约保证金额*返还比例 兰远军
	 */
	protected void tblBail_editStopped(KDTEditEvent e) throws Exception {
		
//		BigDecimal contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue(),2);
		BigDecimal bailOrgAmount=FDCHelper.toBigDecimal(this.txtBailOriAmount.getNumberValue(),2);
		
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn bailAmountCol=this.tblBail.getColumn("bailAmount");
		IColumn bailRateCol=this.tblBail.getColumn("bailRate");
		if(colIndex==bailAmountCol.getColumnIndex()){
			BigDecimal cellBailRateValue=FDCHelper.ZERO;
			BigDecimal cellBailAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
			if(bailOrgAmount.compareTo(FDCHelper.ZERO)!=0){
				cellBailRateValue=cellBailAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(bailOrgAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellBailRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("返还比例大于100%");
					this.tblBail.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getCell(rowIndex, "bailRate").setValue(cellBailRateValue);
			}
		}
		if(colIndex==bailRateCol.getColumnIndex()){
			BigDecimal cellBailAmountValue=FDCHelper.ZERO;
			BigDecimal cellBailRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
			cellBailAmountValue=FDCHelper.divide(FDCHelper.multiply(cellBailRateValue, bailOrgAmount), FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			this.tblBail.getCell(rowIndex, "bailAmount").setValue(cellBailAmountValue);
		}
		if(colIndex==this.tblBail.getColumn("bailDate").getColumnIndex()){
			KDDatePicker bailDatePicker1 = new KDDatePicker();
			bailDatePicker1.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
			ICellEditor bailDateEditor = new KDTDefaultCellEditor(bailDatePicker1);
			this.tblBail.getColumn("bailDate").setEditor(bailDateEditor);
		}
		detailTableAutoFitRowHeight(tblBail);
	}
	
	
	/**
	 * 设置"付款事项"、"履约保证金及返还部分"表格的相关样式设置   by Cassiel_peng
	 */
	private void initEcoEntryTableStyle() {
		((KDTTransferAction) tblEconItem.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		((KDTTransferAction) tblBail.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		tblEconItem.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("payRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("date").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		tblEconItem.getHeadRow(0).getCell("payCondition").setValue("支付节点");
		
		tblBail.getColumn("bailAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailDate").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		txtBailOriAmount.setRemoveingZeroInDispaly(false);
		this.txtBailOriAmount.setRequestFocusEnabled(true);
		this.txtBailOriAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailOriAmount.setPrecision(2);
		this.txtBailOriAmount.setNegatived(false);
		this.txtBailRate.setMaximumValue(FDCHelper.MAX_DECIMAL);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailOriAmount.setHorizontalAlignment(JTextField.RIGHT);
//		this.txtBailRate.setPercentDisplay(true);
		this.txtBailRate.setRequestFocusEnabled(true);
		this.txtBailRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailRate.setPrecision(2);
		this.txtBailRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailRate.setNegatived(false);
		this.txtBailRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBailRate.setRemoveingZeroInDispaly(false);
	}
	public void onLoad() throws Exception {
//		BOSUuid.create((new ParamInfo ()).getBOSType());
		//2008-12-30 暂时屏蔽新功能 复制分录
		this.actionCopyLine.setVisible(false);
		this.actionCopyLine.setEnabled(false);
		this.txtamount.setEditable(false);
		this.contConSettleAmount.setVisible(false);
		this.txtSplitedAmount.setEnabled(false);
		this.txtUnSplitAmount.setEnabled(false);
//		this.ceremonybb.setEditable(false);
		initListeners();
		// added by shangjing
		// 为合约框架名称F7添加自定义的F7界面 (框架合约F7 界面)
		prmtFwContract.setSelector(new KDPromptSelector() {
			IUIWindow win = null;

			public void show() {
				try {
					UIContext context = new UIContext(ContractBillEditUI.this);
					Object object = getUIContext().get("projectId");
					if (object == null) {
						if (editData.getCurProject() != null) {
							object = editData.getCurProject().getId();
						}
					}
					context.put("projectId", object);
					/*** modify by lihaiou. 2013.09.22. for bug R130916-0204***********/
					context.put("allowZero", Boolean.FALSE);
					/**** modify end*********************/
					//新建界面生成 uiwindow(合约框架F7)对象
					win = UIFactory.createUIFactory().create(ProgrammingContractF7UI.class.getName(), context);
					win.show();
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}

			public boolean isCanceled() {
				return false;
			}

			//得到返回的值
			public Object getData() {
				return getUIContext().get("selectedValue");
			}
		});
		
	    this.txtOverAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				// TODO Auto-generated method stub
				Object old  = eventObj.getOldValue();
				if(old == null){
					old = Double.valueOf("0.0");
				}
				Object newValue = eventObj.getNewValue();
				if(newValue != null ){
				if( Double.valueOf(newValue.toString()).doubleValue() < 0 || Double.valueOf(newValue.toString()).doubleValue() > 999 ){
				   txtOverAmt.setValue(old,false);
				}}
			}
		});			
	    
	    // 勾选是否战略子合同时，如果合同没有乙方给出提示
	    this.chkIsSubMainContract.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(chkIsSubMainContract.isSelected() && prmtpartB.getData() == null){
					FDCMsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "plsSelectPartBFirst"));
					chkIsSubMainContract.setSelected(false);
					prmtpartB.requestFocus(true);
				}
			}
	    });
	    
	    this.chkIsSubMainContract.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(chkIsSubMainContract.isSelected()){
					if (prmtpartB.getData() != null) {
						prmtMainContract.setEnabled(true);
						prmtMainContract.setRequired(true);
					}
				}else{
					prmtMainContract.setEnabled(false);
					prmtMainContract.setRequired(false);
					prmtMainContract.setDataNoNotify(null);
				}
			}});
	    
	    this.prmtMainContract.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue() != null){
					ContractBillInfo info = (ContractBillInfo) eventObj.getNewValue();
					if(info.getEffectiveEndDate() != null)
					kdpEffectiveEndDate.setValue(info.getEffectiveEndDate());
					if(info.getEffectiveStartDate() != null )
					kdpEffectStartDate.setValue(info.getEffectiveStartDate());
					if(info.getCoopLevel() != null){
						comboCoopLevel.setSelectedItem(info.getCoopLevel());
					}
					Date now = null;
					try {
						 now = FDCCommonServerHelper.getServerTime();
					} catch (BOSException e) {
						handUIExceptionAndAbort(e);
					} 
					if(now != null && info.getEffectiveEndDate() != null && info.getEffectiveStartDate() != null){
						if(now.after(info.getEffectiveEndDate()) || now.before(info.getEffectiveStartDate())){
							int n = MsgBox.showConfirm2New(null, "当前战略合同已过期，继续使用该合同吗？");
							if(JOptionPane.NO_OPTION == n){
								prmtMainContract.setDataNoNotify(null);
								abort();
							}
						}
					}
					
				}
			}});
		
		//只显示已启用的合同费用项目
		EntityViewInfo viewInfo = this.prmtCharge.getEntityViewInfo();
		if (viewInfo == null) {
			viewInfo = new EntityViewInfo();
		}
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		viewInfo.setFilter(filterInfo);
		this.prmtCharge.setEntityViewInfo(viewInfo);
		tblCost.getStyleAttributes().setLocked(true);
		tblCost.checkParsed();
		//金额靠右
		txtamount.setHorizontalAlignment(JTextField.RIGHT);
		//不允许为负
//		txtamount.setNegatived(false);
		txtLocalAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setPrecision(6);
		txtGrtRate.setEditable(true);
		txtExRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtExRate.setPrecision(6);
		EntityViewInfo invView = new EntityViewInfo();
		FilterInfo invFilter = new FilterInfo();
		invView.setFilter(invFilter);
		invFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		prmtinviteType.setEntityViewInfo(invView);

		//init currency
		removeDataChangeListener(comboCurrency);
		FDCClientHelper.initComboCurrency(comboCurrency, true);
		
		txtcontractName.setMaxLength(200);//此句代码必须在super之前,否则在加载保存好的数据的时候只会截取前80个字符显示 by Cassiel_peng
		initCtn();
		if( this.getOprtState()==OprtState.VIEW) {//added by ken_liu。abstract中设置了上限，在加载数据前先取消。。
			this.reSetMaxNum4AmountField();
		}
		super.onLoad();
		this.reSetMaxNum4AmountField();//added by ken_liu。在FDCBillEditUI又设置了上限。。因此再取消一次。。
		setMaxMinValueForCtrl();
		
		initEcoEntryTableStyle();
		sortTabbedPanel();

		//亿达需要，万科先注销
		//		fillCostPanel();

		setInviteCtrlVisible(false);
		EntityViewInfo viewContractSource = new EntityViewInfo();
		FilterInfo filterSource = new FilterInfo();
		filterSource.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		viewContractSource.setFilter(filterSource);
		this.contractSource.setEntityViewInfo(viewContractSource);
		
		updateButtonStatus();

		// 初始化合同类型F7
		prmtcontractType.setSelector(new ContractTypePromptSelector(this));

		//由于甲方基础资料级别为S4，构建filter时会自动加上CU隔离 为去除默认级别filter，修改掉
		//		EntityViewInfo view = new EntityViewInfo();
		//		FilterInfo filter = new FilterInfo();
		//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		//		HashSet set=new HashSet();
		//		set.add(OrgConstants.SYS_CU_ID);
		String cuId = editData.getCU().getId().toString();
		//		//
		//		set.add(cuId);
		//		filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
		//
		//		view.setFilter(filter);
		//		prmtlandDeveloper.setEntityViewInfo(view);
		//去掉默认级别filter
		/**
		 * 新需求。数据共享与隔离：
		 *	1)	上下级数据共享。即，下级可查看上级建的甲方，上级可查看到下级建的甲方。
		 *	2)	同级的数据隔离。（如下图所示：华南区不能查看到西部区的数据，深圳地产不能查看到广州地产的数据）。
		 * 增加了orgUnit字段，对其长编码及编码进行过滤。added by andy_liu 2012-4-10
		 */
		prmtlandDeveloper.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				/*	KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
				HashSet set = new HashSet();
				set.add(OrgConstants.SYS_CU_ID);
				String cuId = editData.getCU().getId().toString();
				set.add(cuId);
				filter.getFilterItems().add(
						new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);*/
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				FilterInfo filter = new FilterInfo();
				String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", longNumber + "%", CompareType.LIKE));
				Set numberSet = new HashSet(Arrays.asList(longNumber.split("!")));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.number", numberSet, CompareType.INCLUDE));
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				filter.setMaskString("(#0 or #1) and #2");
				EntityViewInfo ev = new EntityViewInfo();
				ev.setFilter(filter);
				f7.setEntityViewInfo(ev);
			}
		});
	
		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		
		FDCClientUtils.setPersonF7(prmtRespPerson, this,
				canSelectOtherOrgPerson ? null : cuId);

		actionAddLine.setEnabled(false);
		actionAddLine.setVisible(false);

		actionInsertLine.setEnabled(false);
		actionInsertLine.setVisible(false);

		actionRemoveLine.setEnabled(false);
		actionRemoveLine.setVisible(false);

		//		txtGrtAmount.setEditable(false);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);

		if (editData.getContractSource() != null) {
			setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		}

		if (STATUS_ADDNEW.equals(getOprtState())
				&& prmtcontractType.getData() != null) {
			ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType
					.getData();
			removeDetailTableRowsOfContractType();

			try {
				fillDetailByContractType(cType.getId().toString());
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		setContractType();
		//合同终止操作目前暂时因建发R120114-0032提单改成终止状态
		//原作废状态被放出，可以修改 added by andy_liu 2012-5-8
		if (editData != null
				&& (editData.getState() == FDCBillStateEnum.CANCEL || editData.isIsArchived())) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionSplit.setEnabled(false);
			btnSplit.setEnabled(false);
		}

		//合同审批前进行拆分
		if (splitBeforeAudit && editData.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (!FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			this.actionViewCost.setVisible(false);
		} else {
			this.actionViewCost.setVisible(true);
		}

		setPrecision();

		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, prmtpartB, cuId);
		FDCClientUtils.initSupplierF7(this, prmtpartC, cuId);
		FDCClientUtils.initSupplierF7(this, prmtwinUnit, cuId);
		//FDCClientUtils.initSupplierF7(this, prmtlowestPriceUnit, cuId);
		//FDCClientUtils.initSupplierF7(this, prmtlowerPriceUnit, cuId);
		//FDCClientUtils.initSupplierF7(this, prmtmiddlePriceUnit, cuId);
		//FDCClientUtils.initSupplierF7(this, prmthigherPriceUnit, cuId);
		//FDCClientUtils.initSupplierF7(this, prmthighestPriceUni, cuId);

		addKDTableLisener();
		handleOldData();

		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		//当通过付款申请单查看合同时，可以查看到合同的附件信息。
		if (STATUS_FINDVIEW.equals(this.oprtState)) {

			this.actionAttachment.setVisible(true);
			this.actionAttachment.setEnabled(true);
			//没有启用 审批前必须拆分完全 参数时，查看不显示拆分按钮
			String prjId = editData.getOrgUnit().getId().toString();
			boolean isSplitBeforeAudit = FDCUtils.getDefaultFDCParamByKey(null,
					prjId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
			if (!isSplitBeforeAudit) {
				this.actionSplit.setEnabled(false);
				this.actionSplit.setVisible(false);
			}
		}

		getDetailInfoTable().getStyleAttributes().setWrapText(true);
		//getDetailInfoTable().getStyleAttributes().set
		this.disableTableMenus(this.getDetailInfoTable());
		//修改合同时判断是否为非独立结算的补充合同  
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			for (int i = 0; i < getDetailInfoTable().getRowCount(); i++) {
				Object o = getDetailInfoTable().getRow(i).getCell(CONTENT_COL)
						.getValue();
				if (o instanceof BooleanEnum) {
					if ((BooleanEnum) o == BooleanEnum.FALSE) {
						this.txtGrtRate.setEditable(false);
					}
				}

			}
		}

		//业务日志判断为空时取期间中断
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		//当非查看状态时才获得焦点
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!getOprtState().equals(STATUS_VIEW)) {
					prmtcontractType.requestFocus(true);
				}
				//合同单据印花税率精度目前可以到0.01%，但是借款合同的印花税率为0.05‰，系统无法输入
				txtStampTaxRate.setPrecision(2);
			}
		});
		//根据参数是否显示合同费用项目
		if(!isShowCharge){
			this.conChargeType.setVisible(false);
			this.prmtCharge.setRequired(false);
			this.actionViewBgBalance.setVisible(false);
			this.actionViewBgBalance.setEnabled(false);
			this.menuItemViewBgBalance.setVisible(false);
			this.menuItemViewBgBalance.setEnabled(false);
		}else{
			this.conChargeType.setVisible(true);
			this.prmtCharge.setRequired(true);
			this.actionViewBgBalance.setVisible(true);
			this.actionViewBgBalance.setEnabled(true);
			this.menuItemViewBgBalance.setVisible(true);
			this.menuItemViewBgBalance.setEnabled(true);
		}
			//如果附件名列表中没有数据，那么查看附件按钮置灰
			boolean hasAttachment=getAttachmentNamesToShow();
			if(!hasAttachment){
				this.btnViewAttachment.setEnabled(false);
				this.btnViewAttachment.setVisible(true);
			}else{
				this.btnViewAttachment.setEnabled(true);	
				this.btnViewAttachment.setVisible(true);
			}
//		}
	
		this.comboAttachmentNameList.setEditable(true);
		
		if(getOprtState()==STATUS_EDIT){
			EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
			EcoItemHelper.setBailRowBackColorWhenInit(this.tblBail, txtBailOriAmount, txtBailRate);			
		}

		if(this.editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}
		// Dean_zhu 2011-1-5
		if (this.editData.getProgrammingContract() != null) {
			if (FDCBillStateEnum.AUDITTING.equals(this.editData.getState()) || FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
				this.btnProgram.setEnabled(false);
			} else {
				this.btnProgram.setEnabled(true);
			}

		}
		
		detailTableAutoFitRowHeight(getDetailInfoTable());
		detailTableAutoFitRowHeight(tblEconItem);
		detailTableAutoFitRowHeight(tblBail);
		fillModelByContractType();
		
		/**
		 * add by libing 关于需求（补充合同能查看主合同信息） 只要不是新增状态，补充合同的都应该 有链接
		 * 不能用固定的格式去取东西，因为表格的行数不是固定的，但是取表格最后一行的数据的第二个格里面的数据 就可以达成效果 嘿嘿～
		 * 
		 */
		if (!getOprtState().equals(OprtState.ADDNEW)) {
			
			//added by ken..非关联合约，名称为非必录。
			if (editData.getContractPropert() != null) {
				if( !editData.getContractType().isIsRefProgram() ) {
					prmtFwContract.setRequired(false);
				}
				
				if (editData.getContractPropert().equals(ContractPropertyEnum.SUPPLY)) {
					int supplyContactNum = getSupplyContractCellNumber();
				
					if (tblDetail.getCell(supplyContactNum, detailContentNum) != null) {
						Object value2 = tblDetail.getCell(supplyContactNum,detailContentNum).getValue();
						if (value2 != null) {
							ICell cell = tblDetail.getCell(supplyContactNum, detailContentNum);
							cell.getStyleAttributes().setUnderline(true);
							cell.getStyleAttributes().setFontColor(Color.BLUE);
						}
					}
				}
			}
		}

		
		//by tim_gao 根据是否独立控制签约金额状态
		checkSignAmountByIsLongCal();
		
		
		//合同录入》新增补充合同》查看主合同界面点击新增按钮，可以同时打开多个合同新增界面；
		//建议：只能打开一个新增界面
		Boolean isFormCon = (Boolean) this.getUIContext().get("isFromCon");
		Boolean isFormList = (Boolean) this.getUIContext().get("isFromList");
		if (isFormList != null && isFormList.booleanValue()) {
		} else {
			actionAddNew.setEnabled(false);
			actionCopy.setEnabled(false);
			actionRemove.setEnabled(false);
			actionEdit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			actionAudit.setEnabled(false);
			
			// modified by zhaoqin for R140116-0099 on 2014/01/16
			//actionViewContent.setVisible(false);
		}
		
		setCntractPropertEnable();
		setContractTypeRequired();
		
		/**start	ken_liu  合同编辑界面：查看正文-->查看范文；只负责正文；**/
		if(editData==null||editData.getId()==null||editData.getContractModel()==null){
			btnViewContrnt.setEnabled(false);
		}
		//合同范本列表可选。
		comboModel.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		comboModel.setEnabled(true);
		/**end**/
		//		contInviteProject.setVisible(false);
		//		contStrategyPact.setVisible(false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		String dateStr = DateTimeUtils.formatDate(new Date());
		Date time2 = DateTimeUtils.parseDate(dateStr + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("endDate", time2, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("startDate", time2, CompareType.LESS_EQUALS));
		String orgNum = SysContext.getSysContext().getCurrentFIUnit().getNumber();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", "%" + orgNum, CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", "01", CompareType.LIKE));
		filter.setMaskString("#0 and #1 and (#2 or #3)");
		view.setFilter(filter);
		prmtStrategyPact.setEntityViewInfo(view);
		kDTabbedPane1.remove(pnlInviteInfo);
		
		//移至onload,避免onshow中出错时导致死循环。ken_liu
		if (this.getOprtState().equals(OprtState.EDIT)) {
			initProgrammingContract();
		}
//		this.reSetMaxNum4AmountField();
		// init kdtSplitEntry modify by yxl 20151105
		kdtSplitEntry.getColumn("amount").setEditor(FDCSplitClientHelper.getTotalCellNumberEdit());
		kdtSplitEntry.getColumn("price").setEditor(getCellNumberEdit());
		kdtSplitEntry.getColumn("workLoad").setEditor(getCellNumberEdit());
		kdtSplitEntry.getColumn("splitScale").setEditor(getScaleCellNumberEdit());
		
		
		((KDTTransferAction) kdtSplitEntry.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		//焦点到了最后一行时，不自动新增行
		disableAutoAddLine(kdtSplitEntry);	
		
		initCtrlListener();
		
		//拆分组号		jelon 12/27/2006
		int idx=0;
		ContractBillSplitEntryInfo entry=null;
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			entry=(ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
		
		disableAutoAddLine(kdtSplitEntry);
		disableAutoAddLineDownArrow(kdtSplitEntry);
		disableEnterFocusTravel();
		Object[] listeners = kdtSplitEntry.getListenerList().getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == KDTSelectListener.class)
			{
				kdtSplitEntry.getSelectManager().removeKDTSelectListener(((KDTSelectListener) listeners[i + 1]));
			}
		}
		
	}
	
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED_MILLION);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public KDTDefaultCellEditor getScaleCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(10);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
    protected void initCtrlListener(){
		//处理键盘delete事件
    	kdtSplitEntry.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					for (int i = 0; i < kdtSplitEntry.getSelectManager().size(); i++)
					{
						KDTSelectBlock block = kdtSplitEntry.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int amount_index=kdtSplitEntry.getColumnIndex("amount");
								int directAmt_index=kdtSplitEntry.getColumnIndex("directAmt");
								int price_index=kdtSplitEntry.getColumnIndex("price");
								int workLoad_index=kdtSplitEntry.getColumnIndex("workLoad");
								//如果列不是上面的列或者单元格锁定了的话，则取消事件
								if((colIndex!=amount_index&&colIndex!=directAmt_index&&colIndex!=price_index&&colIndex!=workLoad_index)||(kdtSplitEntry.getCell(rowIndex, colIndex).getStyleAttributes().isLocked())) {
									e.setCancel(true);
									continue;
								}
								try
								{
									kdtSplitEntry.getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									kdtSplitEntry_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, 
											rowIndex, colIndex,false,1));
								} catch (Exception e1)
								{
									handUIExceptionAndAbort(e1);
								}
							}
//							e.setCancel(true);
						}
					}

				}
				else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
					kdtSplitEntry.putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
    	kdtSplitEntry.setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					kdtSplitEntry.putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable的KDTEditListener仅在编辑的时候触发，
		 * KDTPropertyChangeListener则是在删除，粘贴等导致单元格value发生变化的时候都会触发。
		 */
    	kdtSplitEntry.addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // 表体单元格值发生变化
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(kdtSplitEntry.getClientProperty("ACTION_PASTE")!=null){
			    		//触发editStop事件
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		KDTEditEvent event=new KDTEditEvent(kdtSplitEntry);
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = kdtSplitEntry.getCell(rowIndex,colIndex);
			    		if(cell==null){
			    			return;
			    		}
			    		event.setValue(cell.getValue());
			    		try {
			    			kdtSplitEntry_editStopped(event);			    			
			    		} catch (Exception e1) {
			    			handUIExceptionAndAbort(e1);
			    		}
			    	}
			    }
			}
		});
	}
	
	private void setSplitButton(){
		this.kDContainer2.removeAllButton();
		this.actionAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		this.actionProgrAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		this.actionSplitProj.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showparent"));
		this.actionSplitBotUp.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showsubflow"));
		this.actionSplitProd.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_citetree"));
		this.actionRemoveSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		this.actionAcctSelect.setEnabled(true);
		this.actionSplitProj.setEnabled(true);
		this.actionSplitProd.setEnabled(true);
		this.actionSplitBotUp.setEnabled(true);
		this.actionRemoveSplit.setEnabled(true);
		
		KDWorkButton btnAcctSelect = (KDWorkButton)this.kDContainer2.add(actionAcctSelect);
		btnAcctSelect.setText("成本科目");
		KDWorkButton btnProgrAcctSelect = (KDWorkButton)this.kDContainer2.add(actionProgrAcctSelect);
		btnProgrAcctSelect.setText("规划科目");
//		KDWorkButton btnSplitProj = (KDWorkButton)this.kDContainer2.add(actionSplitProj);
//		btnSplitProj.setText("自动拆分");
//		KDWorkButton btnSplitBotUp = (KDWorkButton)this.kDContainer2.add(actionSplitBotUp);
//		btnSplitBotUp.setText("末级拆分");
		KDWorkButton btnSplitProd = (KDWorkButton)this.kDContainer2.add(actionSplitProd);
		btnSplitProd.setText("产品拆分");
		KDWorkButton btnRemoveLine = (KDWorkButton)this.kDContainer2.add(actionRemoveSplit);
		btnRemoveLine.setText("删除分录");
		
		
		this.kDLabelContainer5.setBounds(150, 2, 200, 19);
		this.kDContainer2.add(this.kDLabelContainer5);
		this.kDLabelContainer6.setBounds(370, 2, 180, 19);
		this.kDContainer2.add(this.kDLabelContainer6);
	}
	//引入合约规划的成本构成信息 modify by yxl 20151120
	public void actionProgrAcctSelect_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()==null){
			MsgBox.showInfo("请先保存合同单据！");
			return;
		}
		if(isClearFlag){
    		return;
    	}
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)prmtFwContract.getValue();
		String checkIsExistProg = null;
		if(pcInfo != null)
			checkIsExistProg = pcInfo.getId().toString();
		checkProgSub(checkIsExistProg);
		BigDecimal allAssigned = FDCHelper.ZERO;
		if(checkIsExistProg != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("costEntries.*");
			sic.add("costEntries.costAccount.*");
			sic.add("costEntries.costAccount.curProject.*");
			pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(BOSUuid.read(checkIsExistProg)),sic);
			ProgrammingContracCostCollection costEntries = pcInfo.getCostEntries();
			if(costEntries.size() == 0){
				FDCMsgBox.showInfo("当前合同对应的框架合约的成本构成为空，原拆分数据不变");
				this.abort();
			}
			kdtSplitEntry.removeRows(false);
	    	IRow row = null;
	    	ProgrammingContracCostInfo acct = null;
	    	ContractBillSplitEntryInfo entry = null;
	    	int groupIndex = 0;
	    	List cachList = new ArrayList();
	    	for(Iterator iter = costEntries.iterator();iter.hasNext();){
	    		acct=(ProgrammingContracCostInfo)iter.next();
	    		if (cachList.contains(acct.getCostAccount())) {
					continue;
				} else {
					cachList.add(acct.getCostAccount());
				}
	    		//总的已分配金额
				allAssigned = allAssigned.add((acct.getContractAssign() == null ? FDCHelper.ZERO:acct.getContractAssign()));
	    	}
	    	int tempCount = 0;
	    	BigDecimal tempAll = FDCHelper.ZERO;
	    	BigDecimal splitAmount = FDCHelper.ZERO;
	    	cachList = new ArrayList();
	    	for(Iterator iter = costEntries.iterator();iter.hasNext();){
	    		acct=(ProgrammingContracCostInfo)iter.next();
	    		if (cachList.contains(acct.getCostAccount())) {
					continue;
				} else {
					cachList.add(acct.getCostAccount());
				}
	    		entry = new ContractBillSplitEntryInfo();
				entry.setCostAccount(acct.getCostAccount());  
				entry.setLevel(0);
				entry.setIsLeaf(true);
				entry.setProgrammings((ProgrammingContractInfo)prmtFwContract.getValue());
				tempCount++;
				//拆分组号	
				groupIndex++;
				entry.setIndex(groupIndex);
				row=addEntry(entry);
				row.getCell("costAccount.curProject.number").setValue(acct.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
	    		row.getCell("costAccount.number").setValue(acct.getCostAccount().getLongNumber().replace('!','.'));
	    		row.getCell("costAccount.curProject.name").setValue(acct.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
	    		row.getCell("costAccount.id").setValue(acct.getCostAccount().getId());
	    		row.getCell("costAccount.name").setValue(acct.getCostAccount().getDisplayName().replace('_','\\'));
	    		if(tempCount == costEntries.size()){
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("splitScale").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("splitScale").setValue(FDCHelper.subtract(new BigDecimal(100), tempAll));
		    		}
	    			//金额
	    			if(editData.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("amount").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("amount").setValue(FDCHelper.divide(editData.getAmount().multiply(FDCHelper.subtract(new BigDecimal(100), tempAll)), new BigDecimal(100), 4, 5));
		    		}
	    		}else{
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
	    				row.getCell("splitScale").setValue(FDCHelper.ZERO);
	    			}else{
	    				row.getCell("splitScale").setValue(FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
	    			}
	    			if(editData.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
	    				row.getCell("amount").setValue(FDCHelper.ZERO);
	    			}else{
	    				row.getCell("amount").setValue(editData.getAmount().multiply(FDCHelper.divide(acct.getContractAssign(), allAssigned,4,5)));
	    			}
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0){
	    				tempAll = FDCHelper.add(tempAll, FDCHelper.ZERO);
	    			}else{
	    				tempAll = FDCHelper.add(tempAll, FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
	    			}
	    		}
	    		splitAmount = FDCHelper.add(FDCHelper.toBigDecimal(row.getCell("amount").getValue()), splitAmount);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.name").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.name").getStyleAttributes().setLocked(false);
				row.getCell("splitScale").getStyleAttributes().setLocked(false);
				row.getCell("amount").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.curProject.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("standard").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				entry.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
				setDisplay(row.getRowIndex());
	    	}
	    	isClearFlag = true;
		}
	}
	
	private void checkProgSub(String checkIsExistProg) throws Exception {
    	if(checkIsExistProg == null){
    		FDCMsgBox.showWarning("该合同没有关联框架合约");
			this.abort();
    	}
//    	boolean costSplit = FDCUtils.isCostSplit(null, editData.getId().toString());
//    	if(!costSplit){
//    		FDCMsgBox.showWarning("当前合同是非才成本类合同，不能关联规划科目");
//    		this.abort();
//    	}
    	if(kdtSplitEntry.getRowCount() > 0){
			int ret = MsgBox.showConfirm2(this,"选择规划科目将清空原拆分数据，是否继续，是，清空并继续操作，否，放弃本次操作");
			if (ret==MsgBox.YES) {
			} else if(ret==MsgBox.CANCEL){
				this.abort();
			}
		}
    }

	/*
	 * R131220-0068 取消金额上限限制
	 *added by ken_liu 
	 */
	private void reSetMaxNum4AmountField() {
		reSetMaxNum4AmountField(ceremonyb, null);	//原币
		reSetMaxNum4AmountField(txtamount, null);
		
		reSetMaxNum4AmountField(ceremonybb, null);	//本币
		reSetMaxNum4AmountField(txtLocalAmount, null);
		
		reSetMaxNum4AmountField(txtStampTaxAmt, null);	//印花税金额
		reSetMaxNum4AmountField(txtGrtAmount, null);	//保修金额
	}
	
	private void reSetMaxNum4AmountField(KDFormattedTextField field, BigDecimal maxNum ) {
		field.setMaximumValue(maxNum);
		NumberFormatterEx formatter = (NumberFormatterEx) field.getFormatter();
		formatter.setMaximum(maxNum); 
	}
	
	private void initListeners() {
		//
		prmtStrategyPact.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (e.getNewValue() != null) {
					StrategyPactInfo pactInfo = (StrategyPactInfo) e.getNewValue();
					SupplierStockInfo suStockInfo = null;
					SupplierInfo supplierInfo = null;
					try {
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add(new SelectorItemInfo("*"));
						suStockInfo = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(
								new ObjectUuidPK(pactInfo.getSupplier().getId()), sic);
						supplierInfo = SupplierFactory.getRemoteInstance().getSupplierInfo(
								"select * where number ='" + suStockInfo.getNumber() + "'");
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					prmtpartB.setValue(supplierInfo);
				}
			}
		});
	}
	/**
	 * 查看招标或战略协议
	 * */
	public void actionViewInvite_actionPerformed(ActionEvent e) throws Exception {
		Object obj = editData.getContractSourceId();
		if (obj != null) {
			ContractSourceInfo conSourceInfo = (ContractSourceInfo) obj;
			if(ContractSourceInfo.DISCUSS_VALUE.equals(conSourceInfo.getId().toString())){
				prmtTenderDiscussion_mouseClicked(null);
			}else if (ContractSourceInfo.COOP_VALUE.equals(conSourceInfo.getId().toString())) {//查看战略协议
				prmtStrategyPact_mouseClicked(null);
			} else if (ContractSourceInfo.INVITE_VALUE.equals(conSourceInfo.getId().toString())) {//查看招标立项
				prmtInviteProject_mouseClicked(null);
			}
		}
	}
	/**
	 * 查看议标
	 * */
	protected void prmtTenderDiscussion_mouseClicked(MouseEvent e) throws Exception {
		if (prmtTenderDiscusstion.getValue() != null && prmtTenderDiscusstion.getValue() instanceof TenderDiscusstionInfo) {
			FDCBillInfo info = (FDCBillInfo) prmtTenderDiscusstion.getValue();
			UIContext uiContext = new UIContext(this);
			String id = info.getId().toString();
			uiContext.put("ID", id);
			IUIWindow uiWindow = null;
			// 创建UI对象并显示
			try {
				uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenderDiscusstionEditUI.class.getName(), uiContext, null,
						OprtState.VIEW);
			} catch (UIException e1) {
				//handUIException(e1);
				handUIExceptionAndAbort(e1);
			}
			uiWindow.show();
		}
	}
	/**
	 * 查看战略协议
	 * */
	protected void prmtStrategyPact_mouseClicked(MouseEvent e) throws Exception {
		if (prmtStrategyPact.getValue() != null && prmtStrategyPact.getValue() instanceof StrategyPactInfo) {
			StrategyPactInfo info = (StrategyPactInfo) prmtStrategyPact.getValue();
			UIContext uiContext = new UIContext(this);
			String id = info.getId().toString();
			uiContext.put("ID", id);
			IUIWindow uiWindow = null;
			// 创建UI对象并显示
			try {
				uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(StrategyPactEditUI.class.getName(), uiContext, null,
						OprtState.VIEW);
			} catch (UIException e1) {
				e1.printStackTrace();
			}
			uiWindow.show();
		}
	}
	/**
	 * 查看招标立项
	 * */
	protected void prmtInviteProject_mouseClicked(MouseEvent e) throws Exception {
		if (prmtInviteProject.getValue() != null && prmtInviteProject.getValue() instanceof InviteProjectInfo) {
			String inviteID = ((FDCBillInfo)prmtInviteProject.getValue()).getId().toString();
			StringBuffer sb = new StringBuffer();
			sb.append("select invprj.fid invprjID, qualify.fid qualifyID,  busTech.fid busTechID,  busbiz.fid busbizID, distri.fid distriID,")
			  .append(	" startbid.fid startbidID,invAppra.fid invAppraID from t_inv_inviteProject invprj");
			sb.append( " left join T_INV_SupplierQualify qualify on qualify.FInviteProjectID = invprj.FID ");
			sb.append(" left join T_INV_BiddingDocumentTech busTech on busTech.FInviteProjectID = invprj.FID ");
			sb.append(" left join T_INV_BiddingDocumentBusiness busbiz on busbiz.FInviteProjectID = invprj.FID ");
			sb.append(" left join T_INV_InviteDistributed distri on distri.FInvProID = invprj.FID \n");
			
			sb.append(" left join T_INV_StartBid startbid on startbid.FInviteProjectID = invprj.FID \n");
			sb.append(" left join T_INV_InviteAppraising invAppra on invAppra.FInvProID = invprj.FID \n");
			sb.append(" where invprj.fid='"+inviteID+"'");
			FDCSQLBuilder strSql = new FDCSQLBuilder();
			strSql.appendSql(sb.toString());

			IRowSet quitSet = null;
			try {
				quitSet = strSql.executeQuery(); //查询结果
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			}
			try {
				if (quitSet!=null && quitSet.next()) {
					
					String suppQualiID = quitSet.getString("qualifyID");
					String techID = quitSet.getString("busTechID");
					String bizID = quitSet.getString("busbizID");
					String distributeID = quitSet.getString("distriID");
					String startBidID = quitSet.getString("startbidID");
					String appraisingID = quitSet.getString("invAppraID");
					UIContext uiContextCC = new UIContext(this);
					uiContextCC.put("inviteID", inviteID);
					uiContextCC.put("suppQualifyID", suppQualiID);
					uiContextCC.put("techID", techID);
					uiContextCC.put("bizID", bizID);
					uiContextCC.put("distributeID", distributeID);
					uiContextCC.put("startBidID", startBidID);
					uiContextCC.put("appraisingID", appraisingID);
			
					IUIWindow guideF = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InviteWFBillViewUI.class.getName(), uiContextCC, null,
							OprtState.VIEW);
					guideF.show();
				}
			
			} catch(Exception e2){
				handUIExceptionAndAbort(e2);
			}
		}
//		if (prmtInviteProject.getValue() != null && prmtInviteProject.getValue() instanceof InviteProjectInfo) {
//			InviteProjectInfo info = (InviteProjectInfo) prmtInviteProject.getValue();
//			UIContext uiContext = new UIContext(this);
//			String id = info.getId().toString();
//			uiContext.put("ID", id);
//			IUIWindow uiWindow = null;
//			// 创建UI对象并显示
//			try {
//				uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteNewProjectEditUI.class.getName(), uiContext, null,
//						OprtState.VIEW);
//			} catch (UIException e1) {
//				e1.printStackTrace();
//			}
//			uiWindow.show();
//		}
	}
	 /* 描述：根据合同的类型是否关联合约规划来判断显示是否必录
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author：jian_cao
	 * @CreateTime：2013-2-4
	 */
	private void setContractTypeRequired() throws EASBizException, BOSException {

		ContractTypeInfo typeInfo = (ContractTypeInfo) prmtcontractType.getValue();

		if (typeInfo == null) {
			return;
		}
		typeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(typeInfo.getId()));
		prmtFwContract.setRequired(typeInfo.isIsRefProgram());
	}
	
	/**
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#prmtRespDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void prmtRespDept_dataChanged(DataChangeEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if (FDCUtils.isEqual(oldValue, newValue)) {
			return;
		}

		// 是否参与编码规则
		boolean flag = isInCode("respDept");
		if (!flag) {
			return;
		}

		AdminOrgUnitInfo unitInfo = (AdminOrgUnitInfo) this.prmtRespDept.getValue();
		this.editData.setRespDept(unitInfo);

		try {
			setCodePropertyChanged(true);
			// 设置编码规则编码
			setNumberByCodingRule();
		} finally {
			setCodePropertyChanged(false);
		}
	}
	
	protected void checkSignAmountByIsLongCal(){
		//by tim_gao 对是否独立计算对签约金额的影响
		this.ceremonyb.setPrecision(2);
		if(isUseAmtWithoutCost){
			this.ceremonyb.setEditable(true);
			this.ceremonybb.setEditable(false);
			this.ceremonyb.setRequired(true);
			this.ceremonybb.setRequired(false);
			this.ceremonyb.setNegatived(true);
		}else{
			this.ceremonyb.setEditable(true);
			this.ceremonybb.setEditable(false);
			
			this.ceremonyb.setRequired(true);
			this.ceremonybb.setRequired(true);
			this.ceremonyb.setNegatived(false);
			// 不能负数则置0
			if (FDCHelper.compareTo(ceremonyb.getBigDecimalValue(), FDCHelper.ZERO) < 0) {
				this.ceremonyb.setValue(FDCHelper.ZERO);
				this.ceremonybb.setValue(FDCHelper.ZERO);
				this.txtamount.setValue(FDCHelper.ZERO);
				this.txtLocalAmount.setValue(FDCHelper.ZERO);
			}
			
		}
	}
	
	private void OpenMainUi() {
		int mcount = tblDetail.getRowCount();
		Object value = tblDetail.getCell(mcount - 1, detailContentNum).getValue();
		if (value != null) {
			String mainContractNumber = editData.getMainContractNumber();
			if (editData.getMainContractNumber() != null) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select fid from t_con_contractbill where fnumber=?");
				builder.addParam(mainContractNumber);
				try {
					IRowSet executeQuery = builder.executeQuery();
					if (executeQuery.next()) {
						String stringID = executeQuery.getString("fid");
						UIContext uicontext = new UIContext(this);
						uicontext.put(UIContext.ID, stringID);
						//合同录入》新增补充合同》查看主合同界面点击新增按钮，可以同时打开多个合同新增界面；
						//建议：只能打开一个新增界面
						uicontext.put("isFromCon", Boolean.TRUE);
						IUIWindow uiwindow = null;
						uiwindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(ContractBillEditUI.class.getName(), uicontext, null, OprtState.VIEW);
						uiwindow.show();
					}
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				} catch (SQLException e) {
					handUIExceptionAndAbort(e);
				}
			} else {
				/**
				 * 查看界面进来的时候居然得不到值！！(用表格（9，2）的去取)
				 */
				// Object value2 = tblDetail.getCell(9, 2).getValue();
				int supplyF7Cell = mcount - 2;
				Object value2 = tblDetail.getCell(supplyF7Cell, detailContentNum).getValue();
				if (value2 != null && value2 instanceof ContractBillInfo) {
					ContractBillInfo info = (ContractBillInfo) value2;
					UIContext uicontext = new UIContext(this);
					if (info != null && info.getId() != null) {
						uicontext.put(UIContext.ID, info.getId().toString());
						
						//合同录入》新增补充合同》查看主合同界面点击新增按钮，可以同时打开多个合同新增界面；
						//建议：只能打开一个新增界面

						uicontext.put("isFromCon", Boolean.TRUE);
						
						IUIWindow uiwindow = null;
						try {
							uiwindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory")
									.create(ContractBillEditUI.class.getName(), uicontext, null, OprtState.VIEW);
							uiwindow.show();
						} catch (UIException e) {
							handUIExceptionAndAbort(e);
						}

					}
				}
			}
		}
	}
	
	//业务日期变化引起,期间的变化
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		//super.pkbookedDate_dataChanged(e);

		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, true);

		this.comboCurrency_itemStateChanged(null);
	}

	private void setMaxMinValueForCtrl() {
		FDCClientHelper.setValueScopeForPercentCtrl(txtchgPercForWarn);
		FDCClientHelper.setValueScopeForPercentCtrl(txtpayPercForWarn);

		FDCClientHelper.setValueScopeForPercentCtrl(txtPayScale);
		FDCClientHelper.setValueScopeForPercentCtrl(txtStampTaxRate);
		FDCClientHelper.setValueScopeForPercentCtrl(txtGrtRate);

		this.txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
		
		//改为在onload时调用reSetMaxNum4AmountField设置。。。ken_liu
//		this.txtamount.setMaximumValue(FDCHelper.MAX_VALUE);
//		this.txtLocalAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		
		this.txtLocalAmount.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE2);
		txtExRate.setMaximumValue(FDCHelper.ONE_THOUSAND);
		txtExRate.setMinimumValue(FDCHelper.ZERO);

		txtNumber.setMaxLength(80);
//		txtcontractName.setMaxLength(80);

		KDTextField tblDetail_desc_TextField = new KDTextField();
		tblDetail_desc_TextField.setName("tblDetail_desc_TextField");
		tblDetail_desc_TextField.setMaxLength(80);
		KDTDefaultCellEditor tblDetail_desc_CellEditor = new KDTDefaultCellEditor(
				tblDetail_desc_TextField);
		tblDetail.getColumn("desc").setEditor(tblDetail_desc_CellEditor);
		tblEconItem.getColumn("desc").setEditor(tblDetail_desc_CellEditor);
		tblBail.getColumn("desc").setEditor(tblDetail_desc_CellEditor);
		
		this.txtcontractName.setMaxLength(200);
		EcoItemHelper.setEntryTableCtrl(this.tblEconItem, this.tblBail);
	}

	/**
	 * 
	 * 描述：保证Tab的顺序
	 * 
	 * @author:liupd 创建时间：2006-7-19
	 *               <p>
	 */
	private void sortTabbedPanel() {

		kDTabbedPane1.removeAll();

		kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
		kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
		kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
		//亿达需要，万科先注销
		//		kDTabbedPane1.add(pnlCost, "成本信息");		
	}

	/**
	 * output prmtcontractType_willShow method
	 */
	protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception {
		if (STATUS_EDIT.equals(getOprtState())) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("changeContractType"));
		}
	}
	
	protected void prmtModel_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo view = null;		
		FilterInfo filter = new FilterInfo();
		view = new EntityViewInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",null));
		if(prmtcontractType.getValue()==null){
			prmtModel.setEntityViewInfo(view);
			return;
		}
		view.setFilter(new FilterInfo());
		view.getSelector().add("id");
		view.getSelector().add("attachID");
		view.getSelector().add("name");
		view.getSelector().add("description");
		view.getSelector().add("type");
		view.getSelector().add("size");
		view.getSelector().add("sharedDesc");
		view.getSelector().add("createTime");
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",editData.getContractType().getId().toString()));
		prmtModel.setEntityViewInfo(view);
		prmtModel.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void prmtModel_dataChanged(DataChangeEvent e) throws Exception {
		updateModel(false);
		fillAttachmnetList();
	}
	
	//常旭说支持维护，所以另存一份
	private void updateModel(boolean oprt) throws Exception {
		if(prmtModel.getValue()==null){
			editData.setModel(null);
			return;
		}
		AttachmentInfo info = (AttachmentInfo)prmtModel.getValue();
		FilterInfo filter = new FilterInfo();
		if(oprt){
			filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(filter);
			editData.setModel(null);
			prmtModel.setValue(null);
			return;
		}
		if(editData.getModel()!=null&&editData.getModel().getId().toString().equals(info.getId().toString())){
			return;
		}
		if(editData.getModel()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getModel().getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(filter);
			if(oprt){
				return;
			}
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("boAttchAsso.*"));
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		evi.setSelector(sic);
		AttachmentInfo attachment = AttachmentFactory.getRemoteInstance().getAttachmentCollection(evi).get(0);
		attachment.setId(BOSUuid.create(attachment.getBOSType()));
		attachment.getBoAttchAsso().get(0).setId(BOSUuid.create("172F3A47"));
		attachment.getBoAttchAsso().get(0).setBoID(editData.getId().toString());
		AttachmentFactory.getRemoteInstance().addnew(attachment);
		editData.setModel(attachment);
		prmtModel.setValue(attachment);
	}
	
	protected void prmtcontractType_dataChanged(DataChangeEvent e) throws Exception {
		Object newValue = e.getNewValue();

		try {
			setCodePropertyChanged(true);
			contractTypeChanged(newValue);
		} finally {
			setCodePropertyChanged(false);
		}

		super.prmtcontractType_dataChanged(e);

		detailTableAutoFitRowHeight(getDetailInfoTable());
		updateModel(true);
		fillAttachmnetList();

		fillModelByContractType();
	}

	private boolean isExistCodingRule() {
		Context ctx = null;
		String currentOrgId = this.orgUnitInfo.getId().toString();
		String bindingProperty = getBindingProperty();

		boolean isExist = false;
		try {
			isExist = FdcCodingRuleUtil.isExist(ctx, editData, currentOrgId, bindingProperty);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		return isExist;
	}

	/**
	 * / 设置主合同编码规则变化对单据编码设置 add by deeplove 2011-12-23 deeplove / <br>
	 * modify by libing
	 */
	private void contractMainChanged(Object newValue) throws Exception, BOSException, EASBizException, CodingRuleException {
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			String mainContractNumber = newValue.toString();
			editData.setMainContractNumber(mainContractNumber);
			this.editData.setContractPropert((ContractPropertyEnum) contractPropert.getSelectedItem());

			try {
				setCodePropertyChanged(true);
				setNumberByCodingRule();
			} finally {
				setCodePropertyChanged(false);
			}
		} else {
			return;
		}
	}

	/**
	 * 合同类型发生改变时，要做的工作
	 * 
	 * @param newValue
	 */
	private void contractTypeChanged(Object newValue) throws Exception,
			BOSException, EASBizException, CodingRuleException {
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			ContractTypeInfo info = (ContractTypeInfo) newValue;
			//if (info != null&& 
			//update by renliang 2010-6-4
			 if((this.editData.getContractType() == null || !this.editData
							.getContractType().getId().equals(info.getId()))) {
				this.editData.setContractType(info);
				this.setNumberByCodingRule();
			}
			removeDetailTableRowsOfContractType();
			fillDetailByContractType(info.getId().toString());
			chkCostSplit.setSelected(info.isIsCost());
			fillInfoByContractType(info);
		} else {
			if (newValue != null) {
				// 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
				if (STATUS_EDIT.equals(getOprtState())) {
					if ((this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))) {
						// prmtcontractType.setEnabled(false);
						actionSplit.setEnabled(true);
					} else {
						ContractTypeInfo info = (ContractTypeInfo) newValue;
						if (!info.isIsLeaf()) {
							MsgBox.showWarning(this, FDCClientUtils
									.getRes("selectLeaf"));
							prmtcontractType.setData(null);
							prmtcontractType.requestFocus();
							SysUtil.abort();
						}
						removeDetailTableRowsOfContractType();
						fillDetailByContractType(info.getId().toString());
						chkCostSplit.setSelected(info.isIsCost());
						fillInfoByContractType(info);
						if (this.editData.getContractType() != null
								///&& info != null update by renliang
								&& !this.editData.getContractType().getId()
										.equals(info.getId())) {
							this.editData.setContractType(info);
							
							this.setNumberByCodingRule();
						}
					}
				}
			}
		}
		setCheckBoxValue();
		
		ContractTypeInfo conType = (ContractTypeInfo) newValue;
		if (conType == null) {
			return;
		}
		if (!conType.isIsRefProgram()) {
			prmtFwContract.setRequired(false);
		} else {
			prmtFwContract.setRequired(true);
		}
	}

	/**
	 * 根据合同类型填充带过来的数据
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void fillInfoByContractType(ContractTypeInfo info)
			throws BOSException, EASBizException {
		//R100429-042合同类型改变时，责任部门不根据合同类型的改变而改变，即合同类型改不清空责任部门 by cassiel_peng 2010-05-21
		/*
		 * R100429-042之前的改动有问题，进入新增界面，选择合同类型，无法带出责任部门.
		 * 应该是，责任部门有值之后，责任部门不根据合同类型的改变而改变 by zhiyuan_tang
		 */
		if (info.getDutyOrgUnit() != null) {
			BOSUuid id = info.getDutyOrgUnit().getId();
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("isLeaf");
			CoreBaseInfo value = AdminOrgUnitFactory.getRemoteInstance()
			.getValue(new ObjectUuidPK(id), selectors);
			prmtRespDept.setValue(value);
		}

		txtPayScale.setNumberValue(info.getPayScale());

		txtStampTaxRate.setNumberValue(info.getStampTaxRate());
	}

	private void removeDetailTableRowsOfContractType() {

		for (int i = 0; i < getDetailInfoTable().getRowCount(); i++) {
			String rowKey = (String) getDetailInfoTable()
					.getCell(i, ROWKEY_COL).getValue();
			if (rowKey == null || rowKey.length() == 0) {
				getDetailInfoTable().removeRow(i);
				i--;
			}
		}
	}

	/**
	 * 根据合同类型填充合同详细信息页签的内容
	 * @param id
	 * @throws Exception
	 */
	private void fillDetailByContractType(String id) throws Exception {

		/**
		 * 其他自定义的合同类型的时候，系统没有自动显示备注
		 */
		if (!ContractClientUtils.isContractTypePre(id)) {
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(
					ContractClientUtils.getRes("remark"));
			row.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		ContractDetailDefCollection contractDetailDefCollection = ContractDetailDefFactory
				.getRemoteInstance().getContractDetailDefCollection(view);

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);

		for (Iterator iter = contractDetailDefCollection.iterator(); iter.hasNext();) {
			ContractDetailDefInfo element = (ContractDetailDefInfo) iter.next();
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(element.getName());
			KDTDefaultCellEditor editor = getEditorByDataType(element.getDataTypeEnum());
			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			
			
			if (element.getDataTypeEnum() == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat(
						"%r{yyyy-M-d}t");
			} else if (element.getDataTypeEnum() == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes()
						.setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			row.getCell(DATATYPE_COL).setValue(element.getDataTypeEnum());
			row.getCell(DETAIL_DEF_ID).setValue(element.getId().toString());

			row.getCell(DESC_COL).setEditor(editorString);
			
			// 如果合同详细定义为必录项，则背景色要显示为必录色  added by owen_wen 2010-09-10
			if (element.isIsMustInput()){
				this.setRequiredBGColor(row);
			}
		}

	}

	/**
	 * description		根据传入的数据类型 返回对应的编辑器
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	public KDTDefaultCellEditor getEditorByDataType(DataTypeEnum dataType) {
		if (dataType == DataTypeEnum.DATE) {
			//KDDatePicker datePicker = new KDDatePicker();
			return FDCClientHelper.getDateCellEditor();
		} else if (dataType == DataTypeEnum.BOOL) {
			KDComboBox booleanCombo = getBooleanCombo();
			return new KDTDefaultCellEditor(booleanCombo);
		} else if (dataType == DataTypeEnum.NUMBER) {
			return FDCClientHelper.getNumberCellEditor();
		} else if (dataType == DataTypeEnum.STRING) {

			KDTextArea indexValue_TextField = new KDTextArea();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setMaxLength(1000);
			indexValue_TextField.setColumns(10);
			indexValue_TextField.setWrapStyleWord(true);
			indexValue_TextField.setLineWrap(true);
			indexValue_TextField.setAutoscrolls(true);

			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			return indexValue_CellEditor;
		}
		return null;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(oprtType)) {
			prmtcontractType.setEnabled(true);
			actionSplit.setEnabled(false);
		} else {
			if (this.editData != null) {
				setContractType();
			} else {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			}
		}
		if (STATUS_VIEW.equals(oprtType) || "FINDVIEW".equals(oprtType)) {
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			// btnAddLine.setEnabled(false);
			// btnInsertLine.setEnabled(false);
			// btnRemoveLine.setEnabled(false);
			tblDetail.getStyleAttributes().setLocked(true);
			//			menuBiz.setVisible(false);
		}
		btnAddLine.setVisible(false);
		btnInsertLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnAddLine.setEnabled(false);
		btnInsertLine.setEnabled(false);
		btnRemoveLine.setEnabled(false);
		actionViewContent.setEnabled(true);
		actionViewContent.setVisible(true);	// modified by zhaoqin for R140116-0099 on 2014/01/16

		if (STATUS_FINDVIEW.equals(oprtType)
				&& getUIContext().get("source") == null) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		} else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		}

		//合同审批前进行拆分
		if (splitBeforeAudit && editData != null && editData.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (editData != null
				&& !FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (oprtType.equals(STATUS_ADDNEW)) {
			actionContractPlan.setEnabled(false);
		} else {
			actionContractPlan.setEnabled(true);
		}

		//		txtGrtAmount.setEditable(false);
		setButtonStatus();
	}

	/*
	 * 20070315 jack 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
	 */
	private void setContractType() {
		if (STATUS_EDIT.equals(getOprtState())) {
			if (this.editData.getState() != null
					&& (this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))) {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			} else {
				prmtcontractType.setEnabled(true);
				actionSplit.setEnabled(false);
			}
		}
	}

	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	/**
	 * 显示单据行
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		if(table==this.tblEconItem){
			if(obj!=null){
				dataBinder.loadLineFields(tblEconItem, row, obj);
			}
		}
		if(table==this.tblBail){
			if(obj!=null){
				dataBinder.loadLineFields(tblBail, row, obj);
			}
		}
		if(table==this.kdtSplitEntry){
			if(obj!=null){
				dataBinder.loadLineFields(kdtSplitEntry, row, obj);
			}
		}
	}

	private void storeDetailEntries() {
		ContractBillEntryInfo entryInfo = null;
		int count = getDetailInfoTable().getRowCount();
		ContractBillInfo billInfo = new ContractBillInfo();
		billInfo.setId(editData.getId());

		for (int i = 0; i < editData.getEntrys().size(); i++) {
			editData.getEntrys().removeObject(i);
			i--;
		}
		/*
		 * 如果有主合同编码,且不是直接合同,设置单据头上的主合同编码
		 */
		editData.setMainContractNumber(null);
		for (int i = 0; i < count; i++) {
			entryInfo = new ContractBillEntryInfo();

			entryInfo.setParent(billInfo);
			String detail = (String) getDetailInfoTable()
					.getCell(i, DETAIL_COL).getValue();
			DataTypeEnum dataType = (DataTypeEnum) getDetailInfoTable()
					.getCell(i, DATATYPE_COL).getValue();
			Object contentValue = getDetailInfoTable().getCell(i, CONTENT_COL)
					.getValue();
			String content = null;

			if (contentValue != null) {
				if (contentValue instanceof CoreBaseInfo) {
					content = ((CoreBaseInfo) contentValue).getId().toString();

				} else if (contentValue instanceof Date) {
					Date date = (Date) contentValue;
					content = DateFormat.getDateInstance().format(date);

				} else {
					content = contentValue.toString();
				}
			}

			String rowKey = (String) getDetailInfoTable().getCell(i, ROWKEY_COL).getValue();
			String desc = (String) getDetailInfoTable().getCell(i, DESC_COL).getValue();
			String detailDefId = (String) getDetailInfoTable().getCell(i, DETAIL_DEF_ID).getValue();
			

			entryInfo.setDetail(detail);

			entryInfo.setContent(content);
			entryInfo.setRowKey(rowKey);
			entryInfo.setDesc(desc);
			if (dataType != null) {
				entryInfo.setDataType(dataType);
			}
			entryInfo.setDetailDefID(detailDefId);
			editData.getEntrys().add(entryInfo);
//			
//			//added by ken..同步分录与单据头‘是否单独计算’显示状态。
//			if (rowKey != null && rowKey.equals(LO_ROW)){
//				BooleanEnum b = (BooleanEnum)contentValue;
//				if (b == BooleanEnum.TRUE) {
//					editData.setIsAmtWithoutCost(true);
//				}else {
//					editData.setIsAmtWithoutCost(false);
//				}
//			}

			/*
			 * 如果有主合同编码,且不是直接合同,设置单据头上的主合同编码
			 */
			if (rowKey != null && rowKey.equals(NU_ROW)
					&& editData.getContractPropert() != ContractPropertyEnum.DIRECT) {
				if (contentValue != null && contentValue instanceof CoreBillBaseInfo) {
					String number = ((CoreBillBaseInfo) contentValue).getNumber();
					editData.setMainContractNumber(number);
				}
			}

			if (isUseAmtWithoutCost && rowKey != null && rowKey.equals(AM_ROW)) {
				//R110506-0418：非单独结算的补充合同不计成本金额取数口径不一致  By zhiyuan_tang
//				editData.setAmount(FDCHelper.toBigDecimal(content));
//				editData.setOriginalAmount(FDCHelper.divide(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
				
				editData.setOriginalAmount(FDCHelper.toBigDecimal(content));
				editData.setAmount(FDCHelper.multiply(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
			}

		}
	}
	private void detailTableAutoFitRowHeight(KDTable table) {
		//ADD by zhiyuan_tang 2010-08-06  合同详细自适应行高
		for (int i = 0; i < table.getRowCount(); i++) {
			KDTableHelper.autoFitRowHeight(table, i);
		}
	}
	
	/**
	 * 设置表格单元为背景为必录色
	 * @author owen_wen 2010-09-10
	 */
	private void setRequiredBGColor(IRow row){
		row.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	}

	/**
	 * 载入合同详细的分录
	 */
	protected void loadDetailEntries() {
		if (STATUS_ADDNEW.equals(getOprtState()))
			return;
		getDetailInfoTable().removeRows(false);

		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);

		// 优化，改为批量取出，不要在下面的for循环中RPC
		Map cddiCollsMap = getContractDetailDefCollectionBatch(coll);
		
		ContractDetailDefCollection detailColls = getConDetailsDef();		
		for (int i = 0; i < coll.size(); i++) {
			ContractBillEntryInfo entryInfo = coll.get(i);
			IRow row = getDetailInfoTable().addRow();
			row.getCell(DETAIL_COL).setValue(entryInfo.getDetail());

			DataTypeEnum dataType = entryInfo.getDataType();
			if (dataType == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
			} else if (dataType == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
				row.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			//详细信息定义ID可能为空， 此处需添加判断。 modify by yzq at 2010-09-20
			if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
				try {
					ContractDetailDefInfo cddi = (ContractDetailDefInfo) cddiCollsMap.get(entryInfo.getDetailDefID());
					if (cddi != null && cddi.isIsMustInput()) {
						setRequiredBGColor(row);
					}
				} catch (Exception e1) {
					logger.warn(e1.getCause(), e1);
					// 居然有奥园客户关联的详细信息定义ID在数据库中找不到，所以暂时注释下面一行 Added by Owen_wen 2012-11-26 
					// handUIExceptionAndAbort(e1); 
				} 
			}

			KDTDefaultCellEditor editor = getEditorByDataType(dataType);

			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			if (entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)) {
				KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
				KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(kDBizPromptBoxContract);
				row.getCell(CONTENT_COL).setEditor(prmtContractEditor);
				ObjectValueRender objectValueRender = getF7Render();
				row.getCell(CONTENT_COL).setRenderer(objectValueRender);
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(LO_ROW)) {
				KDComboBox box = (KDComboBox) row.getCell(CONTENT_COL)
						.getEditor().getComponent();
				IsLonelyChangeListener isLonelyChangeListener = new IsLonelyChangeListener();
				box.addItemListener(isLonelyChangeListener);

				lastDispAddRows = true;
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NU_ROW)) {
				Component component = row.getCell(CONTENT_COL).getEditor()
						.getComponent();
				if (component instanceof KDBizPromptBox) {
					KDBizPromptBox box = (KDBizPromptBox) row.getCell(
							CONTENT_COL).getEditor().getComponent();
					box.addDataChangeListener(new MyDataChangeListener());
				}
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NA_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);

			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NU_ROW)
					&& entryInfo.getContent() != null
					&& entryInfo.getContent().trim().length() > 0) {
				String id = entryInfo.getContent();
				try {
					ContractBillInfo contractBillInfo = ContractBillFactory
							.getRemoteInstance().getContractBillInfo(
									new ObjectUuidPK(id));
					row.getCell(CONTENT_COL).setValue(contractBillInfo);
					mainContractId = id;
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}

			} else if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(AM_ROW)) {
				if (entryInfo.getContent() != null
						&& entryInfo.getContent().trim().length() > 0) {
					BigDecimal decm = new BigDecimal(entryInfo.getContent());
					row.getCell(CONTENT_COL).setValue(decm);
				}

				if (isUseAmtWithoutCost) {
					if (STATUS_FINDVIEW.equals(this.getOprtState())
							|| STATUS_VIEW.equals(this.getOprtState())) {
						row.getCell(CONTENT_COL).getStyleAttributes()
								.setLocked(true);
					} else
						row.getCell(CONTENT_COL).getStyleAttributes()
								.setLocked(false);
					txtamount.setEditable(true);
					setAmountRequired(false);
				} else {
					row.getCell(CONTENT_COL).getStyleAttributes().setLocked(
							true);
					txtamount.setEditable(true);
					setAmountRequired(true);
				}
			} else if (dataType == DataTypeEnum.BOOL) {
				BooleanEnum be = BooleanEnum.FALSE;
				if (entryInfo.getContent() != null
						&& entryInfo.getContent().equals(
								BooleanEnum.TRUE.getAlias())) {
					be = BooleanEnum.TRUE;

				}
				row.getCell(CONTENT_COL).setValue(be);
			} else if (dataType == DataTypeEnum.DATE
					&& !FDCHelper.isEmpty(entryInfo.getContent())) {
				DateFormat df = DateFormat.getDateInstance();

				Date date = null;
				try {
					date = df.parse(entryInfo.getContent());
				} catch (ParseException e) {
					handUIExceptionAndAbort(e);
				}
				row.getCell(CONTENT_COL).setValue(date);
			} else {
				row.getCell(CONTENT_COL).setValue(entryInfo.getContent());
			}

			row.getCell(ROWKEY_COL).setValue(entryInfo.getRowKey());
			row.getCell(DESC_COL).setValue(entryInfo.getDesc());
			row.getCell(DATATYPE_COL).setValue(dataType);
			row.getCell(DETAIL_DEF_ID).setValue(entryInfo.getDetailDefID());
			//			lastDispAddRows=false;
			row.getCell(DESC_COL).setEditor(editorString);

			if (dataType == DataTypeEnum.STRING) {
				int height = row.getHeight();
				int lentgh1 = entryInfo.getContent() != null ? entryInfo.getContent().length() : 0;
				int lentgh2 = entryInfo.getDesc() != null ? entryInfo.getDesc().length() : 0;
				int heightsize = lentgh1 / 75 > lentgh2 / 125 ? lentgh1 / 75 : lentgh2 / 125;

				row.setHeight(height * (1 + heightsize));
			}
			if(STATUS_EDIT.equals(getOprtState())){
				//在保存时候已经有的合同详细信息从 detailColls 中移除，detailColls 中剩余的就是需要重新追加到表格尾部的记录了。
				if(detailColls!=null&&detailColls.size()>0){
					for(int j = 0;j<detailColls.size();j++){
						ContractDetailDefInfo detail = (ContractDetailDefInfo)detailColls.get(j);
						String detailId = detail.getId().toString();
						if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
							if(entryInfo.getDetailDefID().equals(detailId)){
								detailColls.remove(detail);
							}
						}
					}
				}
			}
		}
		//追加到表格尾部
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter.next();
					fillConDetails(editorString, detail);
				}
			}

			if (!lastDispAddRows) {
				fillDetailByPropert(editData.getContractPropert());
			}
			if (isUseAmtWithoutCost && mainContractId != null) {
				this.comboCurrency.setEnabled(false);
			}

			detailTableAutoFitRowHeight(getDetailInfoTable());
		}
	}

	/**
	 * 优化，批量取数，减少RPC次数。
	 * 
	 * 根据合同单据分录关联的合同详细定义信息，获取合同详细定义信息Map集
	 * @param coll
	 * @return cddiCollsMap 合同详细定义信息Map集, key 是ID，value 是ContractDetailDefInfo
	 * @Author：owen_wen
	 * @CreateTime：2013-4-10
	 */
	private Map getContractDetailDefCollectionBatch(ContractBillEntryCollection coll) {
		Set idSet = new HashSet();
		for (int i = 0; i < coll.size(); i++) {
			idSet.add(coll.get(i).getDetailDefID());
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		Map cddiCollsMap = new HashMap();
		try {
			ContractDetailDefCollection cddiColls = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefCollection(view);
			for (int i = 0; i < cddiColls.size(); i++) {
				cddiCollsMap.put(cddiColls.get(i).getId().toString(), cddiColls.get(i));
			}
		} catch (BOSException e) {
			logger.info(e.getMessage(), e);
		}

		return cddiCollsMap;
	}
	
	/**
	 * description		填充合同详细信息
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-17<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private void fillConDetails(KDTDefaultCellEditor editorString, ContractDetailDefInfo detail) {
		IRow appendRow = this.tblDetail.addRow();
		appendRow.getCell(DETAIL_COL).setValue(detail.getName());
		KDTDefaultCellEditor _editor = getEditorByDataType(detail
				.getDataTypeEnum());
		if (_editor != null) {
			appendRow.getCell(CONTENT_COL).setEditor(_editor);
		}
		if (detail.getDataTypeEnum() == DataTypeEnum.DATE) {
			appendRow.getCell(CONTENT_COL).setValue(
					FDCHelper.getCurrentDate());
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setNumberFormat("%r{yyyy-M-d}t");
		} else if (detail.getDataTypeEnum() == DataTypeEnum.NUMBER) {
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		appendRow.getCell(DATATYPE_COL).setValue(
				detail.getDataTypeEnum());
		appendRow.getCell(DETAIL_DEF_ID).setValue(
				detail.getId().toString());

		appendRow.getCell(DESC_COL).setEditor(editorString);

		// 如果合同详细定义为必录项，则背景色要显示为必录色 added by owen_wen 2010-09-10
		if (detail.isIsMustInput()) {
			this.setRequiredBGColor(appendRow);
		}
	}
	private ContractDetailDefCollection getConDetailsDef() {
		String conTypeId = this.editData.getContractType().getId().toString();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("dataTypeEnum"));
		selector.add(new SelectorItemInfo("isMustInput"));
		selector.add(new SelectorItemInfo("name"));
		selector.add(new SelectorItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id",conTypeId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(selector);
		ContractDetailDefCollection detailColls = null;
		try {
			detailColls = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefCollection(view);
		} catch (BOSException e2) {
			handUIExceptionAndAbort(e2);
		}
		return detailColls;
	}

	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ContractBillInfo info = (ContractBillInfo) newData;
//		info.setId(BOSUuid.create(info.getBOSType()));
		info.setCurProject(editData.getCurProject());
		info.setContractType(editData.getContractType());
		info.setIsArchived(false);
		if (mainContractId != null && BooleanEnum.FALSE.equals(getDetailInfoTable().getCell(getRowIndexByRowKey(LO_ROW), CONTENT_COL).getValue() )) {
			this.btnProgram.setEnabled(false);
		} else {
			this.btnProgram.setEnabled(true);
		}
	}

	/*
	 * 增加编辑控件的解锁。
	 */
	protected void unLockUI() {
		super.unLockUI();
		getDetailInfoTable().getStyleAttributes().setLocked(false);
		chkCostSplit.setEnabled(true);
		chkIsPartAMaterialCon.setEnabled(true);
		/*
		 * 原币金额是否可写由是否使用不计成本的金额来控制
		 */
		int rowIndex = getRowIndexByRowKey(AM_ROW);
		IRow row = getDetailInfoTable().getRow(rowIndex);
		if (row != null) {
			ICell cell = row.getCell(CONTENT_COL);
			if (isUseAmtWithoutCost) {
				if (rowIndex > 0)
					cell.getStyleAttributes().setLocked(false);
				txtamount.setEditable(false);
			} else {
				if (rowIndex > 0)
					cell.getStyleAttributes().setLocked(true);
				txtamount.setEditable(true);
			}
		}

		int conNameRowIdx = getRowIndexByRowKey(NA_ROW);

		if (conNameRowIdx > 0) {
			getDetailInfoTable().getRow(conNameRowIdx).getCell(CONTENT_COL)
					.getStyleAttributes().setLocked(true);
		}
	}

	protected void lockUIForViewStatus() {
		super.lockUIForViewStatus();
		chkCostSplit.setEnabled(false);
		chkIsPartAMaterialCon.setEnabled(false);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		//记录当前查看时的工程项目
		this.curProject = editData.getCurProject();
		super.actionAddNew_actionPerformed(e);
		prmtcontractType.setEnabled(true);
		kDDateCreateTime.setEnabled(false);
		comboCurrency.setEnabled(true);
		prmtModel.setEnabled(false);
		getDetailInfoTable().removeRows();
		comboModel.removeAllItems();
		ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType.getData();
		if (cType != null) {				
			fillDetailByContractType(cType.getId().toString());
		}

		lastDispAddRows = false;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		/**
		 * 如果不重新加载一下onLoad()方法，那么检测editData的时候，editData中的数据还是查看时候的数据，
		 * 关闭窗口的时候通过isModify方法会调用storeFields方法重新给editData赋值，
		 * 这样一个查看时候的editData对象的复制对象（oldValue）和一个编辑时候的editData对象比较的时候数据就不匹配，
		 * 故提示数据已经修改
		 */

		//查看界面点修改按钮 1.先把当前的查看状态置为编辑状态
		this.setOprtState(STATUS_EDIT);
		//查看界面点修改按钮 2.重新加载一下数据到editData中
		this.onLoad();
		//如果是已审批状态就不能修改和提交    add by Jian_cao BT721345
		if (FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
		}
		Object obj = txtExRate.getValue();
		initProgrammingContract();
		//该合同已经进行了拆分，不能进行修改
		if (editData.getSplitState() != null
				&& !CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);
		//		没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
		if (STATUS_EDIT.equals(getOprtState())) {
			if ((this.editData.getState()
					.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))) {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			} else {
				prmtcontractType.setEnabled(true);
				//				actionSplit.setEnabled(true);
			}
		}
		comboCurrency_itemStateChanged(null);

		if (obj != null) {
			txtExRate.setValue(obj);
		}
		if (isUseAmtWithoutCost && mainContractId != null) {
			this.comboCurrency.setEnabled(false);
		}
		
		if(editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}
		
		this.btnProgram.setEnabled(true);
		if(editData.getCurProject()!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isWholeAgeStage");
			CurProjectInfo curInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(editData.getCurProject().getId()),sic);
			this.prmtFwContract.setEnabled(!curInfo.isIsWholeAgeStage());
		}
		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();
		
		ContractDetailDefCollection detailColls = getConDetailsDef();
		ContractBillEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillEntryInfo) coll.get(i);
			// 在保存时候已经有的合同详细信息从 detailColls 中移除，detailColls
			// 中剩余的就是需要重新追加到表格尾部的记录了。
			if (detailColls != null && detailColls.size() > 0) {
				for (int j = 0; j < detailColls.size(); j++) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) detailColls
							.get(j);
					String detailId = detail.getId().toString();
					if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
						if (entryInfo.getDetailDefID().equals(detailId)) {
							detailColls.remove(detail);
						}
					}
				}
			}
		}
	//追加到表格尾部
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter
							.next();
					fillConDetails(editorString, detail);
				}
			}
		}
		
		setCntractPropertEnable();
		setButtonStatus();
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		if (getSelectBOID() == null)
			return;
		//合同拆分		jelon 12/30/2006
		String contrBillID = getSelectBOID();
		AbstractSplitInvokeStrategy invokeStra = new ContractSplitInvokeStrategy(contrBillID, this);
		invokeStra.invokeSplit();
	}

	public void actionDelSplit_actionPerformed(ActionEvent e) throws Exception {
		checkConInWF();
		if (getSelectBOID() == null)
			return;

		ContractBillInfo costBillInfo = null;
		//合同拆分		jelon 12/30/2006
		String contrBillID = getSelectBOID();
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("splitState");
		costBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contrBillID)), selectors);

		//该合同已经进行了拆分，不能进行修改
		if (costBillInfo.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(costBillInfo
						.getSplitState())) {
			MsgBox.showWarning(this, "该合同还没有拆分");
			SysUtil.abort();
		}

		if (confirmRemove()) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contrBillID));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("id");

			ContractCostSplitCollection col = ContractCostSplitFactory
					.getRemoteInstance().getContractCostSplitCollection(view);
			IObjectPK[] pks = new IObjectPK[col.size()];
			for (int i = 0; i < col.size(); i++) {
				pks[i] = new ObjectUuidPK(col.get(i).getId().toString());
			}

			ContractCostSplitFactory.getRemoteInstance().delete(pks);

			ConNoCostSplitCollection nocol = ConNoCostSplitFactory
					.getRemoteInstance().getConNoCostSplitCollection(view);
			IObjectPK[] nopks = new IObjectPK[nocol.size()];
			for (int i = 0; i < nocol.size(); i++) {
				nopks[i] = new ObjectUuidPK(nocol.get(i).getId().toString());
			}

			ConNoCostSplitFactory.getRemoteInstance().delete(nopks);
		}
	}

	private void checkConInWF() {
		if (editData == null || editData.getSplitState() == null)
			return;
		//是否必须审核前拆分
		try {
			if (splitBeforeAudit
					&& FDCUtils.isRunningWorkflow(editData.getId().toString())
					&& CostSplitStateEnum.ALLSPLIT.equals(editData
							.getSplitState())) {
				MsgBox.showWarning(this, "合同在工作流，不能删除合同拆分！");
				SysUtil.abort();
			}
		} catch (BOSException e) {

			handUIExceptionAndAbort(e);
		}
	}

	public static void showSplitUI(CoreUIObject parentUI, String billId,
			String billPropName, String bosType, boolean isCostSplit)
			throws BOSException, UIException {
		String splitBillID = null;

		FDCBillInfo billInfo = null;
		CoreBaseCollection coll = null;

		String editName = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(billPropName, billId));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		if (isCostSplit) {
			editName = com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
			coll = ContractCostSplitFactory.getRemoteInstance().getCollection(view);
		} else {
			editName = com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class.getName();
			coll = ConNoCostSplitFactory.getRemoteInstance().getCollection(view);
		}

		boolean isSplited = false;
		boolean isAudited = false;

		if (coll.size() > 0) {
			billInfo = (FDCBillInfo) coll.get(0);
			splitBillID = billInfo.getId().toString();
			isSplited = true;

			if (billInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
				isAudited = true;
			}
		}

		UIContext uiContext = new UIContext(parentUI);
		String oprtState;

		if (isSplited) {
			uiContext.put(UIContext.ID, splitBillID);

			if (isAudited) {
				oprtState = OprtState.VIEW;
			} else {
				oprtState = OprtState.EDIT;
			}
		} else {
			uiContext.put("costBillID", billId);
			oprtState = OprtState.ADDNEW;
		}

		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(editName, uiContext, null, oprtState);

		uiWin.show();
	}

	//设置精度
	protected void setPrecision() {
		CurrencyInfo currency = editData.getCurrency();
		Date bookedDate = (Date) editData.getBookedDate();

		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,
					currency.getId(), company, bookedDate);
		} catch (Exception e) {
			txtExRate.setPrecision(2);
			handUIExceptionAndAbort(e);
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtExRate.setPrecision(exPrecision);
		BigDecimal exRate = editData.getExRate();
		txtExRate.setValue(exRate);
		txtamount.setPrecision(curPrecision);
		txtamount.setValue(editData.getOriginalAmount());
	}

	protected void comboCurrency_itemStateChanged(ItemEvent e) throws Exception {

		super.comboCurrency_itemStateChanged(e);
		if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState()))
			return;
		if (e == null || e.getStateChange() == ItemEvent.SELECTED) {
			CurrencyInfo currency = (CurrencyInfo) this.comboCurrency
					.getSelectedItem();
			//CurrencyInfo baseCurrency = FDCClientHelper.getBaseCurrency(SysContext.getSysContext().getCurrentFIUnit());
			Date bookedDate = (Date) pkbookedDate.getValue();

			ExchangeRateInfo exchangeRate = FDCClientHelper
					.getLocalExRateBySrcCurcy(this, currency.getId(), company,
							bookedDate);

			int curPrecision = FDCClientHelper.getPrecOfCurrency(currency
					.getId());
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;

			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}

			txtExRate.setPrecision(exPrecision);
			txtExRate.setValue(exRate);
			txtamount.setPrecision(curPrecision);
			ceremonyb.setPrecision(curPrecision);

			if (baseCurrency != null
					&& baseCurrency.getId().equals(currency.getId())) {
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			} else {
				txtExRate.setEditable(true);
				txtExRate.setRequired(true);
				txtExRate.setEnabled(true);
			}
			calLocalAmt();
		}
	}

	protected void txtExRate_dataChanged(DataChangeEvent e) throws Exception {
		super.txtExRate_dataChanged(e);
		calLocalAmt();
	}
	/**
	 * 计算付款事项的原币金额或者比例   by Cassiel_peng 2009-8-26
	 */
	private void calPayItemAmt(){
		EcoItemHelper.calPayItemAmt(this.tblEconItem, this.tblBail, txtamount);
	}
	/**
	 * 计算履约保证金及返还部分的原币金额或者比例   by Cassiel_peng 2009-8-26
	 * 当合同金额发生改变时，应该以履约保证金额*返还比率 兰远军
	 */
	private void calBailAmt(){
	   EcoItemHelper.calBailAmt(tblBail, txtamount, txtBailOriAmount, txtBailRate);
	}
	/**
	 * 计算本币金额
	 *
	 */
	private void calLocalAmt() {
		if (ceremonyb.getBigDecimalValue() != null
				&& txtExRate.getBigDecimalValue() != null) {
//			BigDecimal lAmount = FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(),2).multiply(
//					FDCHelper.toBigDecimal(txtExRate.getBigDecimalValue(),2));
			/*
			 * 注：1.金额保存时尽量保留多位小数，以便后续计算准确
			 *     2.小数保存后取出进行运算或比较时可随意截取小数位
			 *     3.小数位数据相互计算时先计算再截取
			 */    
			
			BigDecimal lAmount = ceremonyb.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue());
			txtLocalAmount.setNumberValue(lAmount);
			ceremonybb.setNumberValue(lAmount);
			txtamount.setNumberValue(ceremonyb.getBigDecimalValue(), false);
		} else {
			txtLocalAmount.setNumberValue(null);
		}
		
		//需要计算本位币的地方，都需要重新计算印花税金额
		calStampTaxAmt();
		
		calGrtAmt();
		
		try {
			BigDecimal buildArea = FDCHelper.getApportionValue(editData.getCurProject().getId().toString(),ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
			this.txtcontractPrice.setValue(FDCHelper.divide(this.txtamount.getBigDecimalValue(), buildArea,2,4));
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//重要的事情要做两遍
        for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			IRow row = kdtSplitEntry.getRow(i);
			if(!row.getCell("splitScale").getStyleAttributes().getBackground().equals(Color.white))
				continue;
			try {
				runCalAmount(row, i);
				runCalAmount(row, i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 计算印花税金额
	 *
	 */
	private void calStampTaxAmt() {
		
		//使用不计成本的金额
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}

			//计算印花税
			BigDecimal originalAmount = (BigDecimal) newValue;
			//乘除法空by tim_gao 
			BigDecimal stampTaxAmount = FDCHelper.multiply(originalAmount, FDCHelper.multiply(txtExRate.getBigDecimalValue(),txtStampTaxRate
					.getBigDecimalValue()));
			
			 stampTaxAmount =FDCHelper.divide(stampTaxAmount, FDCConstants.B100,BigDecimal.ROUND_HALF_UP,2);
			txtStampTaxAmt.setNumberValue(stampTaxAmount);

			return;
		}

		//不使用不计成本的金额
		if (txtamount.getBigDecimalValue() != null&& txtStampTaxRate.getBigDecimalValue() != null) {
			BigDecimal stampTaxAmount = FDCHelper.multiply(txtamount.getBigDecimalValue(),txtExRate.getBigDecimalValue())
					.multiply(txtStampTaxRate.getBigDecimalValue());
			stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtStampTaxAmt.setNumberValue(stampTaxAmount);
		} else {
			txtStampTaxAmt.setNumberValue(null);
		}
	}

	/**
	 * 计算保修金额
	 * 
	 */
	private void calGrtAmt() {

		//使用不计成本的金额,计算保修金额
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}

			//计算保修金额
			BigDecimal originalAmount = (BigDecimal) newValue!=null?(BigDecimal) newValue:FDCHelper.ZERO;
			if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
				originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
			}
			BigDecimal grtAmount = originalAmount.multiply(txtGrtRate
					.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);

			return;
		}

		//不使用不计成本的金额
		if (txtamount.getBigDecimalValue() != null
				&& txtGrtRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
			BigDecimal grtAmount = txtamount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue()).multiply(
					txtGrtRate.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);
		} else {
			txtGrtAmount.setNumberValue(null);
		}
	}

	/**
	 * 描述： 合同金额<br>
	 * 
	 * <pre>
	 *       	 isUseAmtWithoutCost true 
	 *       				取不计成本的金额 
	 *            isUseAmtWithoutCost false
	 *                    不使用不计成本的金额
	 *   @return BigDecimal
	 * 
	 */
	private BigDecimal getAmount() {

		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}
			return FDCHelper.toBigDecimal(newValue);
		} else {
			return FDCHelper.toBigDecimal(txtamount.getBigDecimalValue());
		}
	}

	/**
	 * 描述：R090112-050保修金额是根据保修金比例自动计算的，不能直接录入。
	 * <p>
	 * 业务中的保修金额有的时候不根据比例制定，可以直接录入保修金额，再倒算保修金比例
	 * 
	 * @author pengwei_hou Date:2009-04-10
	 * @param flag
	 * @param e
	 */
	private void setGrtAmt(String flag, DataChangeEvent e) {
		BigDecimal amount = getAmount();
		if (amount != null && txtExRate.getBigDecimalValue() != null) {
			amount = amount.multiply(txtExRate.getBigDecimalValue());
		}
		if(FDCHelper.toBigDecimal(txtGrtAmount.getBigDecimalValue()).compareTo(amount) > 0
				&&!(amount.compareTo(FDCHelper.ZERO)==-1)){//当小于零的时候不判断
			String msg = "保修金大于 ";
			if (isUseAmtWithoutCost) {
				msg = msg + "合同本币";
			} else {
				msg  = msg + "不计成本的金额本币";
			}
			MsgBox.showWarning(this, msg);
			txtGrtAmount.setValue(FDCHelper.ZERO);
			SysUtil.abort();
		}
		if (flag.equals("txtGrtAmount")) {

			DataChangeListener[] listeners = (DataChangeListener[]) txtGrtRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtGrtRate.removeDataChangeListener(listeners[i]);
			}
			BigDecimal grtAmount = FDCHelper.toBigDecimal(txtGrtAmount.getBigDecimalValue());
			BigDecimal grtRate = FDCHelper.ZERO;
			if (FDCHelper.ZERO.compareTo(amount) != 0) {
				grtRate = grtAmount.multiply(FDCHelper.ONE_HUNDRED).divide(amount, 6, BigDecimal.ROUND_HALF_UP);
				txtGrtRate.setValue(grtRate);
			}
			for (int i = 0; i < listeners.length; i++) {
				txtGrtRate.addDataChangeListener(listeners[i]);
			}
		} else {
			DataChangeListener[] listeners = (DataChangeListener[]) txtGrtAmount
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtGrtAmount.removeDataChangeListener(listeners[i]);
			}

			BigDecimal grtRate = FDCHelper.toBigDecimal(txtGrtRate.getBigDecimalValue());
			BigDecimal grtAmount = grtRate.multiply(amount).divide(FDCHelper.ONE_HUNDRED, 2,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setValue(grtAmount);

			for (int i = 0; i < listeners.length; i++) {
				txtGrtAmount.addDataChangeListener(listeners[i]);
			}
		}
	}

	protected void txtStampTaxRate_dataChanged(DataChangeEvent e) throws Exception {
		calStampTaxAmt();
	}

	protected void txtamount_dataChanged(DataChangeEvent e) throws Exception {
		calLocalAmt();
		calStampTaxAmt();
		calPayItemAmt();//by Cassiel_peng
		calBailAmt();
	
		if (contractPropert.getSelectedItem() != null && (ContractPropertyEnum) contractPropert.getSelectedItem() == ContractPropertyEnum.SUPPLY) {
			
			if (getDetailInfoTable() != null && getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)) != null && getDetailInfoTable() != null
					&& getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL) != null
					&& getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL).getValue() != null) {
				BooleanEnum b = (BooleanEnum) getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL).getValue();
				isLonelyChanged(b);
			}
		}
	}
	//签约金额原币
	protected void ceremonyb_dataChanged(DataChangeEvent e){
		if(!isFirstOnload()){
			calLocalAmt();
			calStampTaxAmt();
			calPayItemAmt();//by Cassiel_peng
			calBailAmt();
			setCapticalAmount();
			
			//如果是补充合同
			if (contractPropert.getSelectedItem() != null
					&& (ContractPropertyEnum) contractPropert.getSelectedItem() == ContractPropertyEnum.SUPPLY) {
				//如果存在 ‘补充协议金额’行
				if (getDetailInfoTable() != null && getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)) != null) {
					ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
					if (cell != null) {
						cell.setValue(this.ceremonyb.getNumberValue());
					}
				}
			}
		}
	}

	protected void txtGrtRate_dataChanged(DataChangeEvent e) throws Exception {
		calLocalAmt();
		//    	calGrtAmt();
	}

	protected void verifyInputForSave() throws Exception {

		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		
		this.verifyInputForContractDetail();
		
		BigDecimal buildArea = FDCHelper.getApportionValue(editData.getCurProject().getId().toString(),ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
		this.txtcontractPrice.setValue(FDCHelper.divide(this.txtamount.getBigDecimalValue(), buildArea,2,4));
		
		super.verifyInputForSave();

		FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);
	
		checkProjStatus();
		
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("合同摘要信息大于系统约定长度(255)！");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("录入战略子合同时，战略主合同编码不能为空！");
				abort();
			}
		}
		
		if (this.ceremonyb.getText() == null || this.ceremonyb.getText().equals("")) {
			com.kingdee.eas.rptclient.newrpt.util.MsgBox.showInfo("签约金额原币不能为空");
			SysUtil.abort();
		}
		
	}

	public void actionViewCost_actionPerformed(ActionEvent e) throws Exception {
		String selectBOID = getSelectBOID();
		if (selectBOID == null) {
			MsgBox.showWarning(this, "合同还未保存");
			SysUtil.abort();
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, selectBOID);
		uiContext.put(FDCConstants.FDC_INIT_PROJECT, curProject);

		ContractCostInfoUI.showEditUI(this, uiContext, getOprtState());
	}

	/**
	 * @param e
	 * @throws Exception
	 *             modify by libing
	 */
	protected void tblDetail_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblDetail_tableClicked(e);
		// 首先是补充合同
		if (editData.getContractPropert() != null) {
			if (!editData.getContractPropert().equals(ContractPropertyEnum.SUPPLY)) {
				return;
			}
		}
		// 然后判断不为空
		int rowIndex = tblDetail.getSelectManager().getActiveRowIndex();
		// 最后一行
		int mcount = tblDetail.getRowCount();
		int colIndex = tblDetail.getSelectManager().getActiveColumnIndex();
		if (e.getButton() == 1 && e.getClickCount() == 2 && rowIndex == (mcount - 1) && colIndex == 2) {
			OpenMainUi();
		}
		// 查看的时候貌似 双击是单击
		// if (getOprtState().equals(OprtState.VIEW)) {
		// if (e.getButton() == 1 && e.getClickCount() == 1 && rowIndex == 10 &&
		// colIndex == 2) {
		// OpenMainUi();
		// }
		// }
	}
	
	protected void tblDetail_editStopping(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent evt)
			throws Exception {
		KDTable table = (KDTable) evt.getSource();
		IRow entryRow = table.getRow(evt.getRowIndex());
		//ICell cell = entryRow.getCell(evt.getColIndex());

		Object newValue = evt.getValue();
		IColumn col = table.getColumn(evt.getColIndex());
		String colKey = col.getKey();

		//内容发生修改
		if (colKey.equals("content") && (newValue instanceof BigDecimal)
				&& txtStampTaxRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {

			if (isUseAmtWithoutCost && AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
				//
				if (mainContractId != null) {
					MsgContractHasSplit();
				}
				detailAmount = (BigDecimal) newValue;
				// 计算印花税
				BigDecimal originalAmount = (BigDecimal) newValue;
				if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
					originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
				}
				BigDecimal stampTaxAmount = originalAmount.multiply(txtStampTaxRate.getBigDecimalValue());
				stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtStampTaxAmt.setNumberValue(stampTaxAmount);

				// 计算保修金额
				BigDecimal grtAmount = originalAmount.multiply(txtGrtRate.getBigDecimalValue());
				grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtGrtAmount.setNumberValue(grtAmount);
			}
		}
		// 补充合同的主合同编码支持编码规则 by hpw 2012.02.10
		if (colKey != null
				&& colKey.equals("content")
				&& contractPropert.getUserObject() != ContractPropertyEnum.DIRECT) {
			if (newValue != null && newValue instanceof CoreBillBaseInfo) {
				ContractBillInfo conInfo = (ContractBillInfo) newValue;
				editData.setMainContractNumber(conInfo.getNumber());
				
				boolean isLonelyCal = false;
				for (int i = 0; i < tblDetail.getRowCount(); i++) {
					if (LO_ROW.equals(tblDetail.getCell(i, ROWKEY_COL).getValue())) {
						if (BooleanEnum.TRUE.equals(tblDetail.getCell(i, CONTENT_COL).getValue())) {
							isLonelyCal = true;
							break;
						}
					}
				}
				if (prmtFwContract.getValue() == null && isLonelyCal) {
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("programmingContract.name");
					selector.add("programmingContract.number");
					selector.add("programmingContract.id");
					conInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(conInfo.getId()), selector);
					if (conInfo.getProgrammingContract() != null) {
						prmtFwContract.setValue(conInfo.getProgrammingContract());
					}
						
				}
			}
		}
	}

	public void MsgContractHasSplit() throws EASBizException, BOSException {
		if (this.checkContractHasSplit(mainContractId))
			MsgBox.showInfo(this,
					"该非单独计算的补充合同金额审批后会追加到对应主合同签约金额中，如主合同拆分已被引用请先删除或者作废该合同拆分。");
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 描述：删除前的检查，检查项如下：<p>
	 * <li> 合同是否已拆分，如果已拆分，不能删除；
	 * <li> 如果单据在工作流中不能删除；
	 * <li> “作废”状态合同不能删除；
	 * <li> “被打回”状态的合同不能删除；
	 * @Author：owen_wen
	 * @CreateTime：2012-3-21
	 */
	protected void checkBeforeRemove() throws Exception {
		// 已经做合同拆分的合同不能删除
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill", editData.getId()));
		view.setFilter(filter);
		view.getSelector().add("id");
		CoreBillBaseCollection coll = ContractCostSplitFactory.getRemoteInstance().getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		if (iter.hasNext()) {
			logger.info("ContractBill can't be removed, because the ContractBill has been splited.");
			MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
		
		// 如果单据在工作流中不能删除；
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		//R120305-1237建发暂时需求：作废、终止、打回状态合同不能删除-andy_liu 2012-6-15 
		if (FDCBillStateEnum.INVALID == editData.getState() || FDCBillStateEnum.BACK == editData.getState()
				|| FDCBillStateEnum.CANCEL == editData.getState()) {
			logger.info("ContractBill can't be removed, because the ContractBill has been canceled or sent back.");
			MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
	}

	boolean isCopy = false;
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		isCopy = true;
		//获取当前单据的责任部门
		Object obj = editData.get("respDept");
		if (editData == null) {
			return;
		}
		AdminOrgUnitInfo adminOrgInfo = null; 
		if(obj !=null){
			adminOrgInfo = (AdminOrgUnitInfo)obj;
		}
		
		super.actionCopy_actionPerformed(e);
		chkCostSplit.setEnabled(true);
		editData.setSplitState(null);
		this.pkbookedDate.setValue(this.bookedDate);
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		//this.cbPeriod.setValue(this.curPeriod);
		
		//获取当前用户的权限组织
		Set orgsSet = FDCUtils.getAuthorizedOrgs(null);
		//如果当前用户没有责任部门组织的权限，应该显示为空。
		if(adminOrgInfo!=null){
			if(orgsSet==null || (orgsSet !=null && !orgsSet.contains(adminOrgInfo.getId().toString()))){
				this.prmtRespDept.setValue(null);
				editData.setRespDept(null);
			}
		}
		// 如果是已禁用合同，合同类型为空
		if (editData != null) {
			if (editData.getContractType().isIsEnabled() == false) {
				editData.setContractType(null);
				prmtcontractType.setValue(null);
			}
		}
		this.btnProgram.setEnabled(true);
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();

		//		 如果是虚体成本中心，则不能拆分
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
		if(STATUS_ADDNEW.equals(getOprtState())){
			prmtModel.setEnabled(false);
		}else{
			prmtModel.setEnabled(true);
		}
		
		//如果是新增单据，"查看正文"按钮也置灰(因为如果是"新增单据"附件列表一定为空，所以不需要再判断"查看附件"按钮)
		if(STATUS_ADDNEW.equals(getOprtState())){
			this.btnViewContrnt.setEnabled(false);
			this.comboModel.setEnabled(false);
			this.btnViewContrnt.setVisible(true);
		}else if(STATUS_EDIT.equals(getOprtState())){
			this.btnViewContrnt.setEnabled(true);
			this.comboModel.setEnabled(true);
		}
		
	}
	
	// 此方法在状态改变时调用by hpw
	protected void initDataStatus() {
		super.initDataStatus();
		if (!STATUS_EDIT.equals(getOprtState()) && !STATUS_ADDNEW.equals(getOprtState())) {
			btnAdd1.setEnabled(false);
			btnRemove1.setEnabled(false);
			btnAdd2.setEnabled(false);
			btnRemove2.setEnabled(false);
		} else {
			btnAdd1.setEnabled(true);
			btnRemove1.setEnabled(true);
			btnAdd2.setEnabled(true);
			btnRemove2.setEnabled(true);
		}

	}
	
	private KDTable table=tblEconItem;
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....在工作流中是不能新增或者是删除分录的，故。。。
		}
		else{
			//			if (this.tblEconItem.isFocusOwner()) {
				table=this.tblEconItem;
			//			} 
			//			if (this.tblBail.isFocusOwner()) {
			//				table=this.tblBail;
			//			}
			addLine(getDetailTable());
			appendFootRow(getDetailTable());
			EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		}
	}

	/**
	 * 
	 */
	public void actionAddTblBailLine_actionPerformed(ActionEvent e) throws Exception {
		if (STATUS_VIEW.equals(getOprtState())) {
			//.....do nothing.....
		} else if ((getOprtState() == STATUS_VIEW || getOprtState() == STATUS_FINDVIEW) && this.editData.getId() != null
				&& FDCUtils.isRunningWorkflow(this.editData.getId().toString())) {
			//.....do nothing.....在工作流中是不能新增或者是删除分录的，故。。。
		} else {
			//			if (this.tblEconItem.isFocusOwner()) {
			//				table=this.tblEconItem;
			//			} 
			//			if (this.tblBail.isFocusOwner()) {
				table=this.tblBail;
			//			}
			addLine(getDetailTable());
			appendFootRow(getDetailTable());
			EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		}
	}
	
	public void actionRemoveLine_actionPerformed(ActionEvent e)throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....因为在工作流审批时需要将"查看正文"的所有按钮功能都放开(在有权限的前提下)故将界面操作状态设置为了STATUS.EDIT..但在工作路中是不能新增或者是删除分录的，故。。。
		}else{
			//			if (this.tblEconItem.isFocusOwner()) {
				this.tblEconItem.checkParsed();
				int index = -1;
				index = this.tblEconItem.getSelectManager().getActiveRowIndex();
				if (index != -1) {
					tblEconItem.removeRow(index);
				} else {
			
				}
			//			} else if (this.tblBail.isFocusOwner()) {
			//				this.tblBail.checkParsed();
			//				int index = -1;
			//				index = this.tblBail.getSelectManager().getActiveRowIndex();
			//
			//				if (index != -1) {
			//					tblBail.removeRow(index);
			//					if(this.tblBail.getRowCount()<=0){
			//						this.txtBailOriAmount.setRequired(false);
			//						this.txtBailRate.setRequired(false);
			//					}
			//				}
			//			}
		}
	}

	/**
	 * 
	 */
	public void actionRemoveTblBailLine_actionPerformed(ActionEvent e) throws Exception {
		if (STATUS_VIEW.equals(getOprtState())) {
			//.....do nothing.....
		} else if ((getOprtState() == STATUS_VIEW || getOprtState() == STATUS_FINDVIEW) && this.editData.getId() != null
				&& FDCUtils.isRunningWorkflow(this.editData.getId().toString())) {
			//.....do nothing.....因为在工作流审批时需要将"查看正文"的所有按钮功能都放开(在有权限的前提下)故将界面操作状态设置为了STATUS.EDIT..但在工作路中是不能新增或者是删除分录的，故。。。
		} else {
			this.tblBail.checkParsed();
			int index = -1;
			index = this.tblBail.getSelectManager().getActiveRowIndex();

			if (index != -1) {
				tblBail.removeRow(index);
				if (this.tblBail.getRowCount() <= 0) {
					this.txtBailOriAmount.setRequired(false);
					this.txtBailRate.setRequired(false);
				}
			}
		}
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		this.btnContractPlan.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		btnDelSplit.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		menuItemViewContent.setText(menuItemViewContent.getText() + "(V)");
		menuItemViewContent.setMnemonic('V');
		menuItemSplit.setText(menuItemSplit.getText() + "(S)");
		menuItemSplit.setMnemonic('S');
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		btnViewCost.setIcon(EASResource.getIcon("imgTbtn_sequencecheck"));
		initEcoEntryTableStyle();
		btnViewInvite.setEnabled(true);
		
		setSplitButton();
	}
	
	protected void setButtonStatus() {
		boolean flse = (getOprtState().equals("ADDNEW")||getOprtState().equals("EDIT"))?true:false;
		this.actionAcctSelect.setEnabled(flse);
		this.actionSplitProj.setEnabled(flse);
		this.actionSplitProd.setEnabled(flse);
		this.actionSplitBotUp.setEnabled(flse);
		this.actionRemoveSplit.setEnabled(flse);
	}

	protected void checkRef(String id) throws Exception {
		super.checkRef(id);

		ContractClientUtils.checkContractBillRef(this, id);

		if (!splitBeforeAudit) {
			boolean hasSettleSplit = checkContractHasSplit(id);
			if (hasSettleSplit) {
				MsgBox.showWarning("合同已经拆分,不能反审批!");
				SysUtil.abort();
				return;
			}
		}
	}

	private boolean checkContractHasSplit(String id) throws EASBizException,
			BOSException {
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("contractBill.id", id));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = false;
		if (ContractCostSplitFactory.getRemoteInstance().exists(filterSett)
				|| ConNoCostSplitFactory.getRemoteInstance().exists(filterSett)) {
			hasSettleSplit = true;
		}
		return hasSettleSplit;
	}

	/**
	 * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {

		if (STATUS_ADDNEW.equals(this.oprtState)||STATUS_EDIT.equals(this.oprtState)) {
			setNumberByCodingRule();
		}
	}
	
	/**
	 * 描述：根据编码规则设置编码
	 * @Author：jian_cao
	 * @CreateTime：2013-1-17
	 */
	private void setNumberByCodingRule() {
		//合同类型
		ContractTypeInfo contractType = (ContractTypeInfo) this.prmtcontractType.getValue();
		// 合同性质
		String contractPropert = ((ContractPropertyEnum) this.contractPropert.getSelectedItem()).getValue();
		try {

			//获取合同编码规则的编码集合
			ContractCodingTypeCollection codingTypeList = ContractBillFactory.getRemoteInstance().getContractCodingType(contractType,
					contractPropert, ContractThirdTypeEnum.NEW_VALUE);

			int size = codingTypeList.size();
			String bindingProperty = getBindingProperty();
			String orgId = null;
			if (size > 0) {
				//只有当组织列表为空集合的时候才去找
				if (orgIDList.size() == 0) {
					ContractUtil.findParentOrgUnitIdToList(null, this.orgUnitInfo.getId().toString(), orgIDList);
				}
				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
				for (int i = 0; i < size; i++) {
					ContractCodingTypeInfo codingType = codingTypeList.get(i);
					// 设置合同编码类型
					this.editData.setCodeType(codingType);
					// 循环组织列表树查询是否有编码规则有就使用
					for (int j = 0; j < orgIDList.size(); j++) {
						orgId = orgIDList.get(j).toString();
						if (setNumber(iCodingRuleManager, orgId, bindingProperty)) {
							return;
						}
					}
				}
			}

			this.txtNumber.setEnabled(true);
			this.txtNumber.setEditable(true);
		} catch (Exception err) {
			setNumberTextEnabled();
			handUIExceptionAndAbort(err);
		}
	}

	
	/**
	 * 转换编码number，将"!"变为"."
	 * 
	 * @param orgNumber
	 * @author owen_wen 2010-11-23
	 */
	private String convertNumber(String orgNumber){
		return orgNumber.replaceAll("!", ".");
	}

	
	/**
	 * 判断编码规则状态,包括启用、新增设置、断码等
	 * 
	 * @describes
	 * @author 李兵
	 * @createDate 2011-12-28
	 */
	protected boolean setNumber(ICodingRuleManager iCodingRuleManager,
			String orgId, String bindingProperty) throws CodingRuleException,
			EASBizException, BOSException {

		if (editData == null || orgId == null || bindingProperty == null) {
			return false;
		}
		
		this.editData.setContractPropert((ContractPropertyEnum) contractPropert.getSelectedItem());
		this.editData.setContractType((ContractTypeInfo) this.prmtcontractType.getValue());
		
		//如果存在合同编码规则
		if (iCodingRuleManager.isExist(this.editData, orgId, bindingProperty)) {
			CodingRuleInfo codingRuleInfo = iCodingRuleManager.getCodingRule(editData, orgId, bindingProperty);
			this.txtNumber.setEnabled(false);
			this.txtNumber.setEditable(false);
			// 编码规则中启用了"新增显示"
			if (codingRuleInfo.isIsAddView()) {

				String costCenterId = null;
				if (editData instanceof FDCBillInfo
						&& ((FDCBillInfo) editData).getOrgUnit() != null) {
					costCenterId = ((FDCBillInfo) editData).getOrgUnit().getId().toString();
				} else {
					costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
				}
				try {
					// 新增时，或绑定属性发生改变时才需要去取number
					if (STATUS_ADDNEW.equals(this.oprtState) || isCodePropertyChanged()) { 
						String number = prepareNumberForAddnew(iCodingRuleManager, (FDCBillInfo) editData, orgId, costCenterId,
								bindingProperty);
						// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
						prepareNumber(editData, convertNumber(number));
						
						// 获取编码规则是否支持修改
						boolean flag = FDCClientUtils.isAllowModifyNumber(this.editData, orgId, bindingProperty);
						this.txtNumber.setEnabled(flag);
						this.txtNumber.setEditable(flag);

						// 获取编码规则定义中，参与编码的属性Map
						fetchValueAttributeMap(orgId, bindingProperty);

						return true;
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			} 

			if (STATUS_ADDNEW.equals(this.oprtState) && iCodingRuleManager.isUseIntermitNumber(editData, orgId, bindingProperty)) {
				this.txtNumber.setText("");
			}
			return true;
		}
		
		return false;
	}

	// 编码规则中启用了“新增显示”,必须检验是否已经重复
	protected String prepareNumberForAddnew(ICodingRuleManager iCodingRuleManager, FDCBillInfo info,
			String orgId, String costerId, String bindingProperty) throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// 如果编码重复重新取编码
			if (bindingProperty != null) {
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			} else {
				number = iCodingRuleManager.getNumber(editData, orgId);
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (((IFDCBill) getBizInterface()).exists(filter) && i < 1000);

		return number;
	}

	/**
	 * 描述：设置编码控件是否可编辑
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2013-11-28
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#setNumberTextEnabled()
	 */
	protected void setNumberTextEnabled() {
		super.setNumberTextEnabled();
	}

	boolean amtWarned = false;

	protected void txtamount_focusGained(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtamount_focusGained(e);

		if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
			ContractClientUtils.checkSplitedForAmtChange(this, getSelectBOID());
			amtWarned = true;
		}
	}
	
	protected void afterSubmitAddNew() {
		// TODO Auto-generated method stub
		super.afterSubmitAddNew();
		if(prmtcontractType.getValue()!=null){
			try {
				contractTypeChanged(prmtcontractType.getValue());
				
			} catch (CodingRuleException e) {
				handUIExceptionAndAbort(e);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
			detailTableAutoFitRowHeight(getDetailInfoTable());
			detailTableAutoFitRowHeight(tblEconItem);
			detailTableAutoFitRowHeight(tblBail);
		}else{
		}
		comboModel.removeAllItems();
		this.editData.setContractModel(null);
	}
	
	/**
	 * 拆分后，不允许修改“是否成本拆分”字段
	 */
	protected void chkCostSplit_mouseClicked(MouseEvent e) throws Exception {
		super.chkCostSplit_mouseClicked(e);

		if (STATUS_EDIT.equals(getOprtState())) {

			if (editData.getSplitState() != null && editData.getSplitState() != CostSplitStateEnum.NOSPLIT) {

				boolean b = chkCostSplit.isSelected();

				chkCostSplit.getModel().setSelected(!b);

				MsgBox.showWarning(this, ContractClientUtils.getRes("splitedNotChangeIsCSplit"));

				SysUtil.abort();
			}
		}
		setCheckBoxValue();
	}

	/**
	 * 描述：若是甲供材合同，则是否进入动态成本项为否，且不可修改. <br>
	 * 
	 * 2009-9-3 进入动态成本，和是否甲供材，没有关系了！by yong_zhou
	 * @param MouseEvent
	 *            e
	 */
	protected void chkIsPartAMaterialCon_mouseClicked(MouseEvent e)
			throws Exception {
		setCheckBoxValue();
	}
	private void setCheckBoxValue(){
		/*
		 * 2009-9-3 进入动态成本，和是否甲供材，没有关系了！by yong_zhou
		 */
//		if (chkIsPartAMaterialCon.isSelected()) {
//			chkCostSplit.setSelected(false);
//			chkCostSplit.setEnabled(false);
//			chkIsPartAMaterialCon.setEnabled(true);
//		}else{
//			chkCostSplit.setEnabled(true);
//		}
//		if (chkCostSplit.isSelected()) {
//			chkIsPartAMaterialCon.setSelected(false);
//			chkIsPartAMaterialCon.setEnabled(false);
//			chkCostSplit.setEnabled(true);
//		} else{
//			chkIsPartAMaterialCon.setEnabled(true);
//		}
	}
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		
		/* modified by zhaoqin for R140211-0204 on 2014/02/17 start */
		Object source = e.getSource();
		if (source == btnNext || source == btnPre || source == btnFirst || source == btnLast || source == menuItemNext || source == menuItemPre || source == menuItemLast || source == menuItemFirst
				|| source == btnRemove || source == menuItemRemove) {
			try {
				this.reLoadData();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		/* modified by zhaoqin for R140211-0204 on 2014/02/17 end */
	}
	
	/**
	 * 当点击 前一、后一等按钮时，重新加载相关数据 - R140211-0204
	 * 
	 * @author zhaoqin
	 * @date 2014/02/17
	 */
	private void reLoadData() throws Exception {
		if (editData.getContractSource() != null) {
			setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		}

		setContractType();
		//合同终止操作目前暂时因建发R120114-0032提单改成终止状态
		//原作废状态被放出，可以修改 added by andy_liu 2012-5-8
		if (editData != null && (editData.getState() == FDCBillStateEnum.CANCEL || editData.isIsArchived())) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionSplit.setEnabled(false);
			btnSplit.setEnabled(false);
		}

		//合同审批前进行拆分
		if (splitBeforeAudit && editData.getState() != null && !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (!FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			this.actionViewCost.setVisible(false);
		} else {
			this.actionViewCost.setVisible(true);
		}

		setPrecision();

		//如果附件名列表中没有数据，那么查看附件按钮置灰
		boolean hasAttachment=getAttachmentNamesToShow();
		if(!hasAttachment){
			this.btnViewAttachment.setEnabled(false);
			this.btnViewAttachment.setVisible(true);
		}else{
			this.btnViewAttachment.setEnabled(true);	
			this.btnViewAttachment.setVisible(true);
		}
	
		this.comboAttachmentNameList.setEditable(true);
	
		if(this.editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}

		if (this.editData.getProgrammingContract() != null) {
			if (FDCBillStateEnum.AUDITTING.equals(this.editData.getState()) || FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
				this.btnProgram.setEnabled(false);
			} else {
				this.btnProgram.setEnabled(true);
			}
		}
	
		fillModelByContractType();
		
		/**
		 * add by libing 关于需求（补充合同能查看主合同信息） 只要不是新增状态，补充合同的都应该 有链接
		 * 不能用固定的格式去取东西，因为表格的行数不是固定的，但是取表格最后一行的数据的第二个格里面的数据 就可以达成效果 嘿嘿～
		 * 
		 */
		if (!getOprtState().equals(OprtState.ADDNEW)) {
			if (editData.getContractPropert() != null) {
				if( !editData.getContractType().isIsRefProgram() ) {
					prmtFwContract.setRequired(false);
				}
				if (editData.getContractPropert().equals(ContractPropertyEnum.SUPPLY)) {
					int supplyContactNum = getSupplyContractCellNumber();
					if (tblDetail.getCell(supplyContactNum, detailContentNum) != null) {
						Object value2 = tblDetail.getCell(supplyContactNum,detailContentNum).getValue();
						if (value2 != null) {
							ICell cell = tblDetail.getCell(supplyContactNum, detailContentNum);
							cell.getStyleAttributes().setUnderline(true);
							cell.getStyleAttributes().setFontColor(Color.BLUE);
						}
					}
				}
			}
		}

		setCntractPropertEnable();
		setContractTypeRequired();
		
		if(editData==null||editData.getId()==null||editData.getContractModel()==null){
			btnViewContrnt.setEnabled(false);
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {

		//如果启用财务一体化,必须录入期间
		if (this.isIncorporation && cbPeriod.getValue() == null) {
			MsgBox.showConfirm2(this, "启用成本月结,必须录入期间");
			SysUtil.abort();
		}

		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);

		super.verifyInput(e);
		//		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
		//		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);

		/*
		 * 补充合同必须录入主合同编码
		 */
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ContractBillEntryCollection entrys = editData.getEntrys();
			boolean hasMainContNum = false;
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillEntryInfo element = (ContractBillEntryInfo) iter
						.next();
				String rowKey = element.getRowKey();
				if (rowKey != null && rowKey.equals(NU_ROW)
						&& element.getContent() != null
						&& element.getContent().length() > 0) {
					hasMainContNum = true;
					break;
				}
			}

			if (!hasMainContNum) {
				MsgBox.showWarning(this, "补充合同必须录入主合同编码(在合同详细信息中录入)");
				SysUtil.abort();
			}
		}
		// 进入工作流不提示 by tim_gao
		if (!chkCostSplit.isSelected()) {
			boolean isWarn  = true;
			if (null != this.editData) {
				if (null != this.editData.getId()) {
					String id = this.editData.getId().toString();
					ProcessInstInfo instInfo = null;
					ProcessInstInfo[] procInsts = null;
					try {
						IEnactmentService service2 = EnactmentServiceFactory
								.createRemoteEnactService();
						procInsts = service2
								.getProcessInstanceByHoldedObjectId(id);
					} catch (BOSException e1) {
						handUIExceptionAndAbort(e1);
					}

					for (int i = 0, n = procInsts.length; i < n; i++) {
						if ("open.running".equals(procInsts[i].getState())
								|| "open.not_running.suspended"
										.equals(procInsts[i].getState())) {
							instInfo = procInsts[i];
						}
					}
					
					if (instInfo != null
							&& !getOprtState().equals(STATUS_ADDNEW)) {
						isWarn = false;
					}
				}
			}
			if(isWarn){
				MsgBox.showInfo(this, ContractClientUtils.getRes("NotEntryCost"));	
			}
		}
		checkStampMatch();
		checkProjStatus();
		// modify by yxl 20151113
//		for(int i=0;i<tblEconItem.getRowCount();i++){
//			if(FDCHelper.isEmpty(tblEconItem.getCell(i,"date").getValue())) {
//				FDCMsgBox.showInfo("付款事项第"+(i+1)+"行的日期不能为空！");
//				SysUtil.abort();
//			}
//		}
	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	/**
	 * 提交时校验:履约保证金金额必须等于履约保证金及返还列表分录中返回金额之和  by Cassiel_peng  2008-8-26
	 */
	private void checkBailOriAmount() {
		BigDecimal itemAmtSum=FDCHelper.ZERO;
		for(int i=0;i<tblEconItem.getRowCount();i++){
			itemAmtSum=FDCHelper.add(itemAmtSum, tblEconItem.getRow(i).getCell("payAmount").getValue());
		}
		// by tm_gao 非单独计算的补充合同与不计成本金额计算
		if(ContractPropertyEnum.SUPPLY.equals((ContractPropertyEnum)this.contractPropert.getSelectedItem())
				&& null!=getDetailInfoTable().getCell(getRowIndexByRowKey(LO_ROW), CONTENT_COL)
				&&BooleanEnum.FALSE.equals(getDetailInfoTable().getCell(getRowIndexByRowKey(LO_ROW), CONTENT_COL).getValue() )){
			BigDecimal temp = (BigDecimal) getDetailInfoTable().getCell(getRowIndexByRowKey(AM_ROW), CONTENT_COL).getValue();
			if(null==temp){
				temp = FDCHelper.ZERO;
			}
			if(itemAmtSum.compareTo(FDCHelper.toBigDecimal(temp ,2))==1
					&&!(temp.compareTo(FDCHelper.ZERO)==-1)){//只有大于零的情况，小于零不判断
				MsgBox.showWarning("合同经济条款付款金额之和不能大于不计成本的金额！");
				SysUtil.abort();
			}
		}else{
			if(itemAmtSum.compareTo(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(),2))==1
					&&!(txtamount.getBigDecimalValue().compareTo(FDCHelper.ZERO)==-1)){//只有大于零的情况，小于零不判断
				MsgBox.showWarning("合同经济条款付款金额之和不能大于合同原币金额！");
				SysUtil.abort();
			}
		}
		
		BigDecimal bailAmountSum=FDCHelper.ZERO;
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailAmount")!=null){
				bailAmountSum=FDCHelper.add(bailAmountSum, this.tblBail.getRow(i).getCell("bailAmount").getValue());
			}
		}
		if(bailAmountSum.compareTo(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2))!=0){
			MsgBox.showWarning("履约保证金金额必须等于履约保证金及返还列表分录中返回金额之和");
			SysUtil.abort();
		}
	}
	
	/**
	 * 校验 “合同详细信息” 中的必录项：如果必录项为空，给出提示
	 * @author owen_wen 2010-09-10
	 */
	private void verifyInputForContractDetail(){
		ContractBillEntryCollection coll = editData.getEntrys();
		
		for (int i = 0, size = coll.size(); i < size ; i++){
			ContractBillEntryInfo entryInfo = coll.get(i);
			
			ContractDetailDefInfo cddi = null;
			
			// 有可能是补充合同加上的分录，这里不作判断
			if (coll.get(i).getDetailDefID() == null)
				continue;
			
			try {
				cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
			} catch (EASBizException e1) {
				handUIExceptionAndAbort(e1);
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			} catch (UuidException e1) {
				handUIExceptionAndAbort(e1);
			}
			
			if (cddi!=null && cddi.isIsMustInput()){
				//by tim_gao R111017-0261 只校验内容为空
				
//				if (entryInfo.getContent() == null || entryInfo.getDesc() == null){
//					String info = ContractClientUtils.getRes("conDtlCantEmpty");
//					String[] args = new String[] {entryInfo.getDetail(), entryInfo.getContent()==null? "内容":"描述"};
//					FDCMsgBox.showInfo(this,FDCClientHelper.formatMessage(info, args));
//					SysUtil.abort();
//					return;
//				}
				if (entryInfo.getContent() == null ){
					String info = ContractClientUtils.getRes("conDtlCantEmpty");
					String[] args = new String[] {entryInfo.getDetail(),  "内容"};
					FDCMsgBox.showInfo(this,FDCClientHelper.formatMessage(info, args));
					SysUtil.abort();
					return;
				}
			}
		}
	}
	
	private String getProSrcId(BOSUuid conId) throws BOSException, SQLException {
		String proSrcId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FSrcProID from t_con_contractBill where ");
		builder.appendParam("fid", conId.toString());
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			proSrcId = rowSet.getString("FSrcProID");
		}
		return proSrcId;
	}
	
	/**
	 * 描述：0:严格控制， 1:提示控制；2:不提示
	 */
	private String getParam4ProgramAmountControlMode(IObjectPK comPK) throws BOSException, EASBizException {
		return ParamControlFactory.getRemoteInstance().getParamValue(comPK, FDCConstants.FDC_PARAM_CONTRACT_PROGRAM_AMOUNT);
	}
	
	protected void verifyInputForSubmint() throws Exception {
		this.verifyInputForContractDetail();
		checkAmout();
		//预算控制
		checkMbgCtrlBalance();
		
		//增加校验
		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);
		//add by shangjing 判断框架合约是否必填
		
		ContractTypeInfo typeInfo = (ContractTypeInfo) prmtcontractType.getValue();
		//重现查询一下合同类型对象，是否关联合约规划字段如果新建合同时就选中了合同类型是没有把这个字段带过来的。R130105-0082 add by jian_cao
		typeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(typeInfo.getId()));
		boolean isRef = typeInfo.isIsRefProgram();

//		if (prmtFwContract.isRequired() && isRef && conContrarctRule.isVisible()) {
//			FDCClientVerifyHelper.verifyEmpty(this, prmtFwContract);
//		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
		
		//R20141226-0137   合同单据责任人字段是否可以设置不比录并隐藏
//		FDCClientVerifyHelper.verifyEmpty(this, prmtRespDept);
//		FDCClientVerifyHelper.verifyEmpty(this, prmtRespPerson);
		
		super.verifyInputForSubmint();
		if(tblBail.getRowCount()>0){
			FDCClientVerifyHelper.verifyEmpty(this, txtBailOriAmount);
			FDCClientVerifyHelper.verifyEmpty(this, txtBailRate);
		}
		EcoItemHelper.checkVeforeSubmit(tblEconItem, tblBail);
		checkBailOriAmount();
		
		checkBeforeSubmit();
		//合同提交审批前关校验是否关联进度任务
		if(isRelatedTask()){
			ContractClientUtils.checkConRelatedTaskSubmit(this.editData);
		}
		
		
		if(editData.getCurProject()!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isWholeAgeStage");
			CurProjectInfo curInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(editData.getCurProject().getId()),sic);
			//校验合约规划
			if(!curInfo.isIsWholeAgeStage()&&!chkisContractor.isSelected())
				checkConProgram();
		}
		//modify by yxl 20151027  提交时检验面平米指标，五大类合同
		if(FDCHelper.isEmpty(txtMIndexType.getText())){
			FDCMsgBox.showWarning("综合单价不能为空！");
			txtMIndexType.grabFocus();
			abort();
		}
		//拆分信息，如果是全期项目则检验合约规划不能为空；不是的话校验是否全部产品拆分。拆分比例不能超过100%
		BigDecimal splitRate = FDCHelper.ZERO;
		for(int i = 0; i < kdtSplitEntry.getRowCount3(); i++) {
			if(isWholeAgeProject){
				if(kdtSplitEntry.getCell(i,"programming").getValue() == null){
					FDCMsgBox.showWarning("拆分信息第"+(i+1)+"行的合约规划不能为空！");
					abort();
				}
			}else if(FDCHelper.isEmpty(kdtSplitEntry.getCell(i,"standard").getValue()) && FDCHelper.isEmpty(kdtSplitEntry.getCell(i,"product").getValue())){
				FDCMsgBox.showWarning("拆分信息第"+(i+1)+"行的科目须进行产品拆分！");
				abort();
			}
			if(kdtSplitEntry.getCell(i,"splitScale").getValue() != null)
				splitRate = splitRate.add((BigDecimal)kdtSplitEntry.getCell(i,"splitScale").getValue());
		}
		if(splitRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
			MsgBox.showWarning("拆分比例不能大于100%");
			abort();
		}
		if(!chkFiveClass.isSelected() && FDCMsgBox.showConfirm2("该合同不是五大类合同，继续提交吗？") != 0){
			abort();
		}
		if(editData.getId()==null||!isContractRaleToBuildPriceIndex(editData.getId().toString())){
			if(FDCMsgBox.showConfirm2("没有录入成本指标库数据，是否继续提交？") != 0)
				abort();
		}
	}

	/**
	 * by sl 大华需求，如果是总包合同，则不需要关联合约规划
	 */
	protected void chkisContractor_actionPerformed(ActionEvent e)throws Exception {
		super.chkisContractor_actionPerformed(e);
		checkContractor();
	}
	
	private void checkContractor(){
		if(this.chkisContractor.isSelected()){
			this.prmtFwContract.setEnabled(false);
			this.prmtFwContract.setValue(null);
		}else{
			this.prmtFwContract.setEnabled(true);
		}
	}
	/**
	 * 根据参数FDC228_ISSTRICTCONTROL和合约框架控制合同签定，控制逻辑如下：<p>
	 * <li>当参数为“严格控制”时，合同提交时必须关联合约规划，合同金额大于控制金额时，合同不允许提交；
	 * <li>当参数为“提示控制”时，合同提交时必须关联合约规划，合同金额大于合约控制金额时，合同允许提交；
	 * <li>当参数为“不控制”时，则不做任何判断。 <p>
	 * 	
	 * R111128-0450 建发又加了新的需求如下：<br>
	 * <li>关联合约规划勾选：参数为“严格控制”时，合同必须关联合约规划。合同提交时校验，合同金额大于控制余额时，提示：“合同签约金额超过关联的合约控制余额”，合同不允许提交
	* <li>关联合约规划勾选：参数为“提示控制”时，合同必须关联合约规划。合同提交时校验，合同金额大于控制余额时，提示：“合同签约金额超过关联的合约控制余额”，合同允许提交；
	* <li>关联合约规划勾选：参数为“不控制”时，不强制关联合约规划。如果关联了框架合约，且签约金额大于“控制余额”，提示：“合同签约金额超过关联的合约控制余额”；如果没有关联，则不做任何判断。
	* <li>关联合约规划未勾选：参数为“严格控制”时，不强制关联合约规划。如果关联了框架合约，且签约金额大于“控制余额”，提示：“合同签约金额超过关联的合约控制余额”；如果没有关联，则不做任何判断。
	* <li>关联合约规划未勾选：参数为“提示控制”时，不强制关联合约规划。如果关联了框架合约，且签约金额大于“控制余额”，提示：“合同签约金额超过关联的合约控制余额”；如果没有关联，则不做任何判断。
	* <li>关联合约规划未勾选：参数为“不控制”时，不强制关联合约规划。如果关联了框架合约，且签约金额大于“控制余额”，提示：“合同签约金额超过关联的合约控制余额”；如果没有关联，则不做任何判断。
	* @Author：owen_wen 从verifyInputForSubmint()中抽取的方法
	 * @CreateTime：2012-1-31
	 */
	private void checkConProgram() throws BOSException, EASBizException,
			SQLException {
		ContractTypeInfo typeInfo = (ContractTypeInfo)prmtcontractType.getValue();
		boolean isRef = typeInfo.isIsRefProgram(); // 合同类型是否勾选“关联合约规划”
	
		//======================合约框架控制合同签定逻辑控制
		ProgrammingContractInfo pc = (ProgrammingContractInfo)prmtFwContract.getValue();
		if(pc != null && this.editData.getId() !=null){
			String proSrcId = getProSrcId(this.editData.getId());
			if(proSrcId != null){
				SelectorItemCollection sick = new SelectorItemCollection();
				sick.add("*");
				pc = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(proSrcId), sick);
			}
		}
		
		boolean isValid = false; //这个值有什么用？ 合约框架是否有效吗? 
		if(pc != null){
			FDCSQLBuilder builderSql = new FDCSQLBuilder();
			builderSql.appendSql(" select prog.fid  from t_con_programmingContract prog ");
			builderSql.appendSql(" left join t_con_programming programming on programming.fid = prog.FProgrammingID ");
			builderSql.appendSql(" where  programming.fstate = '4AUDITTED' and ");
			builderSql.appendParam("prog.fid", pc.getId().toString());
			IRowSet rowSet = builderSql.executeQuery();
			if (rowSet.next()) {
				isValid = true;
			}
		}
		String programControlMode = getParamValue();
		if (!com.kingdee.util.StringUtils.isEmpty(programControlMode)) {
			int i = Integer.parseInt(programControlMode);
			switch (i) {
			case 0: // 严格控制时
				if (isValid) {
					if (this.editData.getAmount().compareTo(FDCHelper.toBigDecimal(pc.getControlBalance())) > 0) {
						if (isRef) {
							FDCMsgBox.showWarning(this, "合同签约金额超过关联的合约控制余额，不允许提交");
							SysUtil.abort();
						} else {
							if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否提交") == FDCMsgBox.CANCEL) {
								SysUtil.abort();
							}
						}
					}
				} else if (isRef) {
					FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交");
					SysUtil.abort();
				}
				break;
			case 1: // 提示控制时
				if (isValid) {
					if (this.editData.getAmount().compareTo(FDCHelper.toBigDecimal(pc.getControlBalance())) > 0) {
						if (isRef) {
							if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否提交") == FDCMsgBox.CANCEL) {
								SysUtil.abort();
							}
						}
					}
				} else if (isRef) {
					FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交");
					SysUtil.abort();
				}
				break;
			case 2: // 不控制时
				if (isValid) {
					if (this.editData.getAmount().compareTo(FDCHelper.toBigDecimal(pc.getControlBalance())) > 0) {
						if (isRef) {
							if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否提交") == FDCMsgBox.CANCEL) {
								SysUtil.abort();
							}
						}
					}
				}
				break;
			}
		}
	}
	
	/**
	 * 启用参数"是否必须与计划任务进行关联"后，合同删除和反审批时，校验该合同下是否存在“合同与任务关联单”
	 * 如果有，则不允许删除和反审批 by cassiel 2010-08-09
	 */
	private void checkConRelatedTaskDelUnAudit() throws BOSException, SQLException{
		ContractBillInfo contract = this.editData;
		if(contract.getId()==null){
			return;
		}
		String contractBillId = this.editData.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID=? group by fid ");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count > 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("合同已被任务关联，不能执行此操作。");
			SysUtil.abort();
		}
		
	}
	
	private void checkBeforeSubmit() {
		if(editData.getEffectiveEndDate()!= null && editData.getEffectiveStartDate() != null){
			if(editData.getEffectiveEndDate().before(editData.getEffectiveStartDate())){
				FDCMsgBox.showError("合同有效截止日期小于开始日期！");
				abort();
			}
		}
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("合同摘要信息大于系统约定长度(255)！");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("录入战略子合同时，战略主合同编码不能为空！");
				abort();
			}
		}
	}

	private void checkProjStatus() {
//		如果项目状态已关闭，则不能选择是否成本拆分 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = null;
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id), selectors);
				if (curProjectInfo.getProjectStatus() != null) {
					String closedState = ProjectStatusInfo.closeID;
					String transferState = ProjectStatusInfo.transferID;
					String projStateId = curProjectInfo.getProjectStatus().getId().toString();
					if (projStateId != null && (projStateId.equals(closedState) || projStateId.equals(transferState))) {
						if (chkCostSplit.isSelected()) {
							MsgBox.showWarning(this, "该合同所在的工程项目已经处于关闭或中转状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交");
							SysUtil.abort();
						}
					}
				}
			
			}catch (Exception ex) {
				handUIExceptionAndAbort(ex);
			}
			
		}
	}

	private void checkStampMatch() {
		/**
		 * 印花税金额不等于合同金额*印花税率，提示
		 */
		BigDecimal stampTaxAmt = editData.getStampTaxAmt() == null ? FDCHelper.ZERO : editData
				.getStampTaxAmt();
		BigDecimal amount = editData.getAmount() == null ? FDCHelper.ZERO : editData.getAmount();
		BigDecimal stampTaxRate = editData.getStampTaxRate() == null ? FDCHelper.ZERO : editData
				.getStampTaxRate();
		stampTaxAmt = stampTaxAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
		if (editData.getStampTaxAmt() != null
				&& editData.getAmount() != null
				&& stampTaxAmt.compareTo(amount.multiply(stampTaxRate).divide(FDCConstants.B100, 2,
						BigDecimal.ROUND_HALF_UP)) != 0) {
			int result = MsgBox.showConfirm2(this, ContractClientUtils.getRes("stampNotMatchAmt"));
			if (result == MsgBox.NO || result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * 检查金额
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void checkAmout() throws EASBizException, BOSException {
		String projectId = editData.getCurProject().getId().toString();

		if (editData.isIsCoseSplit()
				&& (FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType) || FDCContractParamUI.RD_CONTROL
						.equalsIgnoreCase(controlType))) {
			if (editData.getAmount() == null) {
				editData.setAmount(FDCConstants.ZERO);
			}
			BigDecimal amiCost = null;
			//获取成本控制金额
			String msg = null;
			if (!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)) {
				amiCost = FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(projectId);
				msg = "目标成本";
			} else {
				amiCost = FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(projectId);
				msg = "动态成本";
			}
			if (amiCost == null)
				amiCost = FDCConstants.ZERO;

			//获取项目合同签约总金额，审核状态的数据
			String id = editData.getId() != null ? editData.getId().toString() : null;
			BigDecimal signAmount = ContractFacadeFactory.getRemoteInstance().getProjectAmount(
					projectId, id);
			if (signAmount == null)
				signAmount = FDCConstants.ZERO;

			if (amiCost.compareTo(signAmount.add(editData.getAmount())) < 0) {
				//提示  严格控制
				FDCMsgBox.showWarning(this, "项目合同签约总金额已经超过了" + msg);
				if (!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)) {
					SysUtil.abort();
				}
			}

		}

	}

	public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception {
		String selectBOID = getSelectBOID();
		if (selectBOID == null) {
			MsgBox.showWarning(this, "合同还未保存，请先保存合同，再维护合同付款计划");
			SysUtil.abort();
		}
		ContractPayPlanEditUI.showEditUI(this, selectBOID, getOprtState());
	}
	
	/**
	 * 检查合同是否关联框架合约
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre() throws Exception {
		String billId = null;
		if(editData.getId() != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("programmingContract");
			ContractBillInfo conInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()),sic);
			if(conInfo.getProgrammingContract() != null){
				billId = conInfo.getProgrammingContract().getId().toString();
			}
		}
		return billId;
	}

	/**
	 * 更新老框架合约是否被引用
	 * 
	 * @throws Exception
	 */
	private void updateOldProg() throws Exception {
		String checkReaPre = checkReaPre();
		while (checkReaPre != null) {
			int count = 0;// 关联合约数
			int linkInviteProject = isCitingByInviteProject(checkReaPre);// 本合约关联招标立项数
			int linkContractBill = isCitingByContractBill(checkReaPre);// 本合约关联合同数
			int linkContractWithoutText = isCitingByContractWithoutText(checkReaPre);// 本合约关联无文本合同招立项数
			count = linkInviteProject + linkContractBill + linkContractWithoutText;
			boolean isCiting = preVersionProg(checkReaPre);
			if (count <= 1 && !isCiting) {
				updateProgrammingContract(checkReaPre, 0);
			}
			checkReaPre = isUpdateNextProgState(checkReaPre);
		}
		updateNewProg();
	}

	private String isUpdateNextProgState(String progId) throws Exception {
		String flag = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select fid from t_con_programmingContract where ");
		builder.appendParam("fSrcId", progId);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			flag = rowSet.getString("fid").toString();
		}
		return flag;
	}

	/**
	 * 更新新框架合约是否被引用
	 * 
	 * @throws Exception
	 */
	private void updateNewProg() throws Exception {
		if (editData.getProgrammingContract() != null) {
			String proId = editData.getProgrammingContract().getId().toString();
			while (proId != null) {
				updateProgrammingContract(proId, 1);
				proId = isUpdateNextProgState(proId);
			}
		}
	}

	// 重写EditUI的获取编码
	// 通过编码规则获取编码。子类可用。
	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				// 当前用户所属组织不存在时，缺省实现是用集团的
				orgId = OrgConstants.DEF_CU_ID;
			}
			if (iCodingRuleManager.isExist(caller, orgId)) {
				String number = "";
				if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) { // 此处的orgId与步骤1
					// ）
					// 的orgId时一致的
					// ，
					// 判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(caller, orgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(caller, orgId, null, null);
						// pb.setSelector(intermilNOF7);
						//要判断是否存在断号,是则弹出,否则不弹///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager.isDHExist(caller, orgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						} else {
							// 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?
							// 是因为使用用户选择功能也是get!
							number = iCodingRuleManager.getNumber(caller, orgId);
						}
					} else {
						// 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
						number = iCodingRuleManager.readNumber(caller, orgId);
					}
				} else {
					// 没有启用断号支持功能，此时获取了编码规则产生的编码
					String costCenterId = null;

					// costCenterId =
					// SysContext.getSysContext().getCurrentOrgUnit
					// ().getId().toString();售楼组织成本中心为空
					costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();

					// number =
					// prepareNumberForAddnew(iCodingRuleManager,(AgencyInfo
					// )editData, orgId,costCenterId, null);
					// iCodingRuleManager.getNumber(caller, orgId);
				}

				// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
				prepareNumber(caller, number);
				if (iCodingRuleManager.isModifiable(caller, orgId)) {
					// 如果启用了用户可修改
					setNumberTextEnabled();
				}
				return;
			}

			/*
			 * if (orgId != null && orgId.trim().length() > 0 &&
			 * iCodingRuleManager.isExist(caller, orgId)) { String number = "";
			 * 
			 * if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
			 * //此处的orgId // 与步骤1）的orgId时一致的，判断用户是否启用断号支持功能 {
			 * //启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时 number =
			 * iCodingRuleManager.readNumber(caller, orgId); } else {
			 * //没有启用断号支持功能，此时获取了编码规则产生的编码 number =
			 * iCodingRuleManager.getNumber(caller, orgId); }
			 * 
			 * //把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。 prepareNumber(caller,
			 * number);
			 * 
			 * return; } else { //当前用户所属组织不存在时，缺省实现是用集团的 String companyId =
			 * getNextCompanyId();
			 * 
			 * if (companyId != null && companyId.trim().length() > 0 &&
			 * iCodingRuleManager.isExist(caller, companyId)) { String number =
			 * "";
			 * 
			 * if (iCodingRuleManager.isUseIntermitNumber(caller, companyId))
			 * //此处的orgId // 与步骤1）的orgId时一致的，判断用户是否启用断号支持功能 {
			 * //启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时 number =
			 * iCodingRuleManager.readNumber(caller, companyId); } else {
			 * //没有启用断号支持功能，此时获取了编码规则产生的编码 number =
			 * iCodingRuleManager.getNumber(caller, companyId); }
			 * 
			 * //把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。 prepareNumber(caller,
			 * number);
			 * 
			 * return; } }
			 */
		} catch (Exception err) {
			// 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
			setNumberTextEnabled();
			
			// 获取编码规则出错，现可以手工输入编码！
			//			handleCodingRuleError(err);
			handUIExceptionAndAbort(err);

		}

		// 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
		setNumberTextEnabled();
	}
	public String getNumberFromCodeRule(IObjectValue billInfo) throws CodingRuleException, EASBizException, BOSException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		String sysNumber = null;
		String companyID = iCodingRuleManager.getCurrentAppOUID(billInfo);

		if (iCodingRuleManager.isExist(billInfo, companyID)) {
			// 没有启用断号支持功能，此时获取了编码规则产生的编码
			sysNumber = iCodingRuleManager.getNumber(billInfo, companyID);
		}

		return sysNumber;
	}
	  protected boolean isRuleAutoNumber() throws EASBizException, BOSException {
		 if (SysContext.getSysContext().getCurrentSaleUnit() != null) {
			BOSUuid orgUnitId = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo().getId();
			ICodingRuleManager ruleManager = null;
			try {
				ruleManager = CodingRuleManagerFactory.getRemoteInstance();
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
			try {
				if (ruleManager.isExist(editData, orgUnitId.toString()))
					return true;
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		}
		return false;
	}
	  
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// 保存前反写所关联的框架合约“是否引用”字段
//		updateOldProg();
		//add by zkyan 此时editData.getProgrammingContract()为空，应放在storeFields之后
		// 维护源ID用于动态规划
		//		if (this.editData.getProgrammingContract() != null) {
		//			this.editData.setSrcProID(this.editData.getProgrammingContract().getId().toString());
		//		}
		if (contractPropert.getSelectedItem() == ContractPropertyEnum.THREE_PARTY
				|| contractPropert.getSelectedItem() == ContractPropertyEnum.SUPPLY) {
			//增加方法判断是否存在金额行，避免不存在时返回默认第一行造成中断 add by zkyan
			if (getDetailInfoTable().getRowCount() > 0 && isHasAMRow()
					&& null != getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL).getValue()) {
				ceremonyb.setValue(getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL).getValue());
			}
		}
		ContractCodingTypeInfo codingTypeInfo = this.editData.getCodeType();
		
		super.actionSave_actionPerformed(e);
		this.editData.setCodeType(codingTypeInfo);
		EcoItemHelper.setPayItemRowBackColor(this.tblEconItem);
		EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		// 保存后反写写所关联的框架合约状态
		updateNewProg();
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			// btnSubmit.setEnabled(false);
			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
		}
		prmtModel.setEnabled(true);
		btnViewContrnt.setEnabled(true);
		comboModel.setEnabled(true);
//		conSplitUI.actionSave_actionPerformed(e);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// 保存前反写所关联的框架合约“是否引用”字段
//		updateOldProg();
		
			//如果是不控制的时候把合约规划控件的是否必录设置成false
		if ("2".equals(this.getParamValue().trim())) {
			this.prmtFwContract.setRequired(false);
		}
		
		//add by zkyan 此时editData.getProgrammingContract()为空，应放在storeFields之后 ,不清楚isCopy有什么意义
		// 维护源ID用于动态规划
		//		if (this.editData.getProgrammingContract() != null) {
		//			if (!isCopy) {
		//				this.editData.setSrcProID(this.editData.getProgrammingContract().getId().toString());
		//			}
		//		} else {
		//			this.editData.setSrcProID(null);
		//		}
		//by tim_gao 提交时没有加上
		if (contractPropert.getSelectedItem() == ContractPropertyEnum.THREE_PARTY
				|| contractPropert.getSelectedItem() == ContractPropertyEnum.SUPPLY) {
			//增加方法判断是否存在金额行，避免不存在时返回默认第一行造成中断 add by zkyan
			if (getDetailInfoTable().getRowCount() > 0 && isHasAMRow()
					&& null != getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL).getValue()) {
				ceremonyb.setValue(getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL).getValue());
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		// 保存后反写写所关联的框架合约状态
		updateNewProg();
		if (this.getOprtState().equals("ADDNEW")) {
			this.actionSave.setEnabled(true);
			//R101231-259  合同不能连续录入，新增提交后，选择合同类型不能带出详细信息。 by zhiyuan_tang 2010-01-18
//			this.tblDetail.removeAll();
			removeDetailTableRowsOfContractType();
			ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType.getData();
			if (cType != null) {				
				fillDetailByContractType(cType.getId().toString());
			}
			fillDetailByPropert(ContractPropertyEnum.DIRECT);
			fillModelByContractType();
		}
		handleOldData();
		EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
		EcoItemHelper.setBailRowBackColorWhenInit(tblBail, txtBailOriAmount, txtBailRate);
		if (STATUS_ADDNEW.equals(getOprtState())) {
			prmtModel.setEnabled(false);
		} else {
			prmtModel.setEnabled(true);
		}
		btnViewContrnt.setEnabled(true);
		comboModel.setEnabled(true);
		
		setNumberByCodingRule();
//		conSplitUI.actionSave_actionPerformed(e);
		setButtonStatus();
	}

	// 提交时，控制预算余额
	private void checkMbgCtrlBalance() throws BOSException, EASBizException {
		//根据参数是否显示合同费用项目
		if (!isShowCharge || editData.getConChargeType() == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer("");
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl
				.getBudget(FDCConstants.ContractBill, null, editData);

		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null && balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(bgCtrlResultInfo.getItemName() + "(" + bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(FDCConstants.VoucherResource, "BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString() + "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource, "isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	protected void fillCostPanel() throws Exception {
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
			public void run() {
				try {
					fillCostPanelByAcct();
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
		});

	}

	ContractCostHelper helper = null;

	//惰性加载costPanel
	protected void fillCostPanelByAcct() throws Exception {

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			return;
		}
		//aimCost hasHappen intendingHappen dynamicCost
		FDCHelper.formatTableNumber(tblCost, "aimCost");
		FDCHelper.formatTableNumber(tblCost, "hasHappen");
		FDCHelper.formatTableNumber(tblCost, "intendingHappen");
		FDCHelper.formatTableNumber(tblCost, "dynamicCost");
		FDCHelper.formatTableNumber(tblCost, "chayi");

		String selectObjId = this.curProject.getId().toString();
		DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory
				.getRemoteInstance().getDynamicCost(selectObjId, null, true);

		if (dynamicCostMap == null) {
			return;
		}

		Map acctMap = new HashMap();
		//获取当前合同的拆分科目			
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid", new Integer(0)));
		view.setFilter(filter);
		ContractCostSplitEntryCollection col = ContractCostSplitEntryFactory.getRemoteInstance()
				.getContractCostSplitEntryCollection(view);
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			ContractCostSplitEntryInfo element = (ContractCostSplitEntryInfo) iter.next();

			String costAcctId = element.getCostAccount().getId().toString();
			if (acctMap.containsKey(costAcctId)) {
				continue;
			}
			acctMap.put(costAcctId, element);
		}

		helper = new ContractCostHelper(tblCost, editData.getId().toString());
		helper.fillCostTable();
	}

	protected void tblCost_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (helper == null)
			return;
		helper.tblCost_tableClicked(this, e);
	}

	/*
	 *  在FDCBillEdit内将其改成抽象方法,以强制要求子类去实现
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return table;
	}

	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */

	public boolean isPrepareInit() {
		return true;
	}

	public boolean isPrepareActionSubmit() {
		//FDCClientVerifyHelper.verifyRequire(this);
		return false;
	}

	public boolean isPrepareActionSave() {
		//FDCClientVerifyHelper.verifyRequire(this);
		return false;
	}

	public RequestContext prepareActionSubmit(IItemAction itemAction)
			throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);

		return request;
	}

	/**
	 * EditUI提供的初始化handler参数方法
	 */
	protected void PrepareHandlerParam(RequestContext request) {
		super.PrepareHandlerParam(request);

	}
	//查看预算余额
	public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		//业务日期默认未设置，如果预算控制时以业务日期控制，会导致查看预算时查不到，手动设置一下
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		ContractBillInfo info = editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.ContractBill, null, info);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		info=null;
	}
	
	/**
	 * 得到调用FilterGetter类"下载附件"和"打开附件"的实例  by Cassiel_peng
	 */
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	/**
	 * 根据业务单据的ID得到该单据所有的关联附件并且将附件名称显示在下拉列表框中  
	 * 因为UI编辑区的"查看附件"和"查看正文"都只是在工作流中用来查看,因此禁止"维护"，故不需要处理权限。  by Cassiel_peng
	 */
	private boolean getAttachmentNamesToShow() throws Exception{
		        String boID = getSelectBOID();
		        boolean hasAttachment=false;
		        if(boID == null)
		        {
		            return hasAttachment;
		        } 
	        	SelectorItemCollection itemColl=new SelectorItemCollection();
	        	itemColl.add(new SelectorItemInfo("id"));
	        	itemColl.add(new SelectorItemInfo("attachment.name"));
	        	itemColl.add(new SelectorItemInfo("attachment.id"));
	        	EntityViewInfo view=new EntityViewInfo();
	        	view.getSelector().addObjectCollection(itemColl);
	        	//设置排序，按照时间进行排序, modify by lihaiou,2014-07-07
	        	SorterItemInfo sortItemInfo = new SorterItemInfo("attachment.createTime");
	        	sortItemInfo.setSortType(SortType.DESCEND);

	        	view.getSorter().add(sortItemInfo);
	        	//view.getSorter().add(new SorterItemInfo("attachment.name"));
	        	FilterInfo filter=new FilterInfo();
	        	filter.getFilterItems().add(new FilterItemInfo("boID",getSelectBOID()));
	        	
	        	view.setFilter(filter);
	        	
	        	BoAttchAssoCollection boAttchColl=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
	        	
	        	/* modified by zhaoqin for R140211-0204 on 2014/02/17 start */
	        	comboAttachmentNameList.removeAllItems();
	        	if(boAttchColl!=null&&boAttchColl.size()>0){
	        		//comboAttachmentNameList.removeAllItems();
	        		/* modified by zhaoqin for R140211-0204 on 2014/02/17 end */
	        		
	        		hasAttachment=true;
	        		for(int i=0;i<boAttchColl.size();i++){
	        			AttachmentInfo attachment=(AttachmentInfo)boAttchColl.get(i).getAttachment();
	        			this.comboAttachmentNameList.addItem(attachment);
	        			this.comboAttachmentNameList.setUserObject(attachment);
	        		}
	        	}
	        	return hasAttachment;
	        }
	
	/**
	 * 点击"查看附件"按钮   by Cassiel_peng
	 */
	protected void btnViewAttachment_actionPerformed(ActionEvent e) throws Exception {
		//得到下拉列表框中选中的附件的ID
		getFileGetter();
		Object selectObj=this.comboAttachmentNameList.getSelectedItem();
		if(selectObj!=null){
			String attachId=((AttachmentInfo)selectObj).getId().toString();
//			fileGetter.downloadAttachment(attachId);不需要下载附件
			fileGetter.viewAttachment(attachId);
		}
	};
	/**
	 * 点击"查看正文"按钮    by Cassiel_peng
	 */
	protected void btnViewContrnt_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null&&editData.getId()!=null&&editData.getContractModel()!=null){
			ContractModelUI modelUI = new ContractModelUI(this,editData.getId().toString(),editData.getContractModel());
			modelUI.editModel();
		}/*else{//现改为只负责查看范本。。。不兼顾正文。。ken_liu
		if(this.editData != null && this.editData.getId() != null&&this.editData instanceof FDCBillInfo){
			ForWriteMarkHelper.isUseWriteMarkForEditUI(editData, this.oprtState, this);
		}
	}*/
	}
	
	public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {		
		if(this.editData != null && this.editData.getId() != null&&this.editData instanceof FDCBillInfo){
			ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData, this.oprtState, this);
		}
	}
	
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		// 关联框架合约
		showProgramContract();
		// 关联框架合约的相关信息自动带入到合同中
		autoProgToCon();
	}

	/**
	 * 关联框架合约的相关信息自动带入到合同中
	 * @Modified Owen_wen 2011-10-26 只有当合同名称、合同编码、合同金额为空时，才会用框架的值填充。
	 */
	private void autoProgToCon() {
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		if (pcInfo != null) {
			if (StringUtils.isEmpty(txtcontractName.getText())) {
				this.txtcontractName.setText(pcInfo.getName());
			}
			if (StringUtils.isEmpty(txtNumber.getText())) {
				this.txtNumber.setText(pcInfo.getNumber());
			}
			if (txtamount.getNumberValue() == null || FDCNumberHelper.compareTo(FDCNumberConstants.ZERO, txtamount.getNumberValue()) == 0) {
				this.txtamount.setValue(pcInfo.getControlBalance());
			}
		}
	}

	/**
	 * 关联框架合约
	 * 
	 * @throws Exception
	 */
	private void showProgramContract() throws Exception {
		ProgrammingContractInfo pc = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		String temp = this.prmtFwContract.getText();
		if (pc != null && !FDCHelper.isEmpty(temp) &&  this.editData.getId() != null) {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select con.fid from t_con_contractbill con ");
			builder.appendSql(" inner join T_INV_AcceptanceLetter accep on accep.fid = con.fsourcebillid");
			builder.appendSql(" inner join t_inv_inviteProject invite on invite.fid = accep.FInviteProjectID where ");
			builder.appendSql("  con.fprogrammingcontract = invite.fprogrammingcontractid and ");
			builder.appendParam("con.fid", this.editData.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.next()) {
				FDCMsgBox.showInfo("框架合约由招标通知书带入，不可修改");
				this.abort();
			}
		}

		String pro_id1 = editData.getProgrammingContract() != null ? editData.getProgrammingContract().getId().toString() : "";
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ContractBillInfo contractBillInfo = editData;
		uiContext.put("contractBillInfo", contractBillInfo);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,
				OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			this.prmtFwContract.setValue(contractBillInfo.getProgrammingContract());
		} else {
			this.prmtFwContract.setValue(null);
		}
		String pro_id2 = contractBillInfo.getProgrammingContract() != null ? contractBillInfo.getProgrammingContract().getId().toString() : "";
		if (!pro_id1.equals(pro_id2)) {
			isCopy = false;
		}
	}
	
	/**
	 * 实现 IWorkflowUIEnhancement 接口必须实现的方法getWorkflowUIEnhancement by Cassiel_peng 2009-9-21
	 */
	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement(){
			public List getApporveToolButtons(CoreUIObject uiObject) {
				
				List toolButtionsList=new ArrayList();
				btnViewContent.setVisible(true);
				toolButtionsList.add(btnViewContent);
				btnViewCost.setVisible(true);
				toolButtionsList.add(btnViewCost);
				return toolButtionsList;
			}
		};
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData==null || editData.getId()==null){
			return;
		}
		checkMainSupplyCon();
	    if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionAudit_actionPerformed(e);
	}
	private void checkMainSupplyCon() throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select bill.fcontractpropert from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on  bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=entry.fcontent and main.fstate <> '4AUDITTED' ");
	    builder.appendSql("where ");
	    builder.appendParam("bill.fid", editData.getId().toString());
	    builder.appendSql(" and bill.fcontractpropert='SUPPLY' ");
	    builder.appendSql(" and entry.fdetail='对应主合同编码' ");
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()==1){
	    	rs.next();
	    	String prop = rs.getString("fcontractpropert");
	    	if("SUPPLY".equals(prop)){
	    		FDCMsgBox.showWarning(this,"您所选择的数据中存在主合同未审批的补充合同，不能进行此操作!");
		    	this.abort();
	    	}
	    }
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData==null || editData.getId()==null){
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select * from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on  main.fid=entry.fcontent ");
	    builder.appendSql("where");
	    builder.appendParam("main.fid", editData.getId().toString());
	    if(isSupply){
	    	builder.appendSql(" and bill.fstate='4AUDITTED'");
	    }
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this, "您所选择的数据存在被补充合同引用的合同，不能进行此操作!");
			this.abort();
		}
	    
	  //R110603-0148:如果存在变更签证申请，则不允许反审批
	    if (ContractUtil.hasChangeAuditBill(null, editData.getId())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasChangeAuditBill"));
			this.abort();
    	}
    	
    	//R110603-0148:如果存在变更签证确认，则不允许反审批
    	if (ContractUtil.hasContractChangeBill(null, editData.getId())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasContractChangeBill"));
			this.abort();
    	}
    	
	    if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionUnAudit_actionPerformed(e);
	}
	

	
	/**
	 * 处理R110125-045：合同管理_合同订立_合同录入_新增合同时印花税率无法带入的情况
	 * 重写了抽象类的此方法，只在印花税率等没有被赋值的时候，才给默认值 by zhiyuan_tang
	 */
	protected void applyDefaultValue(IObjectValue vo) {
		if (vo.get("originalAmount") == null) {
			vo.put("originalAmount",new java.math.BigDecimal(0));
		}
		if (vo.get("stampTaxRate") == null) {
			vo.put("stampTaxRate",new java.math.BigDecimal(0));
		}
		if (vo.get("stampTaxAmt") == null) {
			vo.put("stampTaxAmt",new java.math.BigDecimal(0));
		}
	}
	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}
	  
	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	/**
	 * 找出招标立项所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByInviteProject(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_INV_InviteProject ");
		buildSQL.appendSql("where FProgrammingContractId = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 找出合同所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByContractBill(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_CON_ContractBill ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 找出无文本合同所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByContractWithoutText(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private boolean preVersionProg(String progId) throws BOSException, SQLException {
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '" + progId + "')");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if (tempIsCiting > 0) {
			isCityingProg = true;
		}
		return isCityingProg;
	}
	
	//add by david_yang PT043562 2011.03.29
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetList();
	}
	
	public void fillAttachmnetList() {
		this.comboAttachmentNameList.removeAllItems();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}

		try {
			this.comboAttachmentNameList.addItems(AttachmentUtils.getAttachmentListByBillID(boId).toArray());
			this.isHasAttchment = this.comboAttachmentNameList.getItemCount() > 0;
			this.btnViewAttachment.setEnabled(isHasAttchment);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	
	private void fillModelByContractType() throws Exception{
		this.comboModel.removeAllItems();
		if(STATUS_ADDNEW.equals(getOprtState()))
			this.editData.setContractModel(null);
		String modelId = getContractModelId();
		if(modelId==null){
			return;
		}
		//当选择不同类型时删除正文
		if(editData!=null&&editData.getId()!=null&&modelId.equals(editData.getContractModel())){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("version",FDCHelper.ONE));
			filter.getFilterItems().add(new FilterItemInfo("parent",editData.getId().toString()));
			ContractContentFactory.getRemoteInstance().delete(filter);
			editData.setContractModel(null);
		}
//		comboModel.addItem("无范本");
		EntityViewInfo viewInfo = new EntityViewInfo();
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("fileName");
		viewInfo.getSelector().add("version");
		viewInfo.getSelector().add("fileType");
		viewInfo.getSelector().add("createTime");
		viewInfo.getSelector().add("creator.*");
		viewInfo.getSelector().add("contentFile");
		SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
		sorterItemInfo.setSortType(SortType.ASCEND);
		viewInfo.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo("version");
		sorterItemInfo.setSortType(SortType.DESCEND);
		viewInfo.getSorter().add(sorterItemInfo);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("parent", modelId);
		viewInfo.setFilter(filterInfo);
		ContractContentCollection contentCollection = ContractContentFactory
				.getRemoteInstance().getContractContentCollection(viewInfo);
		int idx = 0;
		ContractContentInfo temp = null;
		ContractContentCollection colls = new ContractContentCollection();
		if (contentCollection != null && contentCollection.size() > 0) {
			for (Iterator it = contentCollection.iterator(); it.hasNext();) {
				ContractContentInfo content = (ContractContentInfo) it.next();
				if(temp!=null){
					if(content.getFileType().equals(temp.getFileType())){
						if(FDCHelper.compareTo(content.getVersion(), temp.getVersion())==1){
							colls.remove(temp);
							
						}else{
							continue;
						}
					}
				}
				temp = content;
				colls.add(content);
			}
			EventListener[] listeners = comboModel.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)comboModel).removeItemListener((ItemListener)listeners[i]);
    		}
			for (Iterator it = colls.iterator(); it.hasNext();) {
				ContractContentInfo content = (ContractContentInfo) it.next();
				this.comboModel.addItem(content);
				comboModel.setUserObject(content);
				if(content.getId().toString().equals(editData.getContractModel())){
					comboModel.setSelectedIndex(idx);
				}else if(idx==0&&editData.getContractModel()==null){
					comboModel.setSelectedIndex(0);
					editData.setContractModel(content.getId().toString());
				}
				idx++;
			}
				
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)comboModel).addItemListener((ItemListener)listeners[i]);
    		}
		} 
		
	}
	
	private String getContractModelId() throws Exception{
		if(prmtcontractType.getData()==null){
			return null;
		}
		String typeId = ((ContractTypeInfo)prmtcontractType.getData()).getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));	
		filter.getFilterItems().add(new FilterItemInfo("contractType.id",typeId));
		ContractModelInfo info = (ContractModelInfo) ContractModelFactory.getRemoteInstance().getCollection(view).get(0);
		if(info==null){
			return null;
		}
		return info.getId().toString();
	}
	
	protected void comboModel_itemStateChanged(ItemEvent e) throws Exception {
		if(editData==null||editData.getId()==null||!(comboModel.getSelectedItem() instanceof ContractContentInfo)){
			return;
		}
		ContractContentInfo model = (ContractContentInfo)comboModel.getSelectedItem();
		if(model!=null){
			if(model.getId().toString().equals(editData.getContractModel())){
				return;
			}
			editData.setContractModel(model.getId().toString());
		}else{
			editData.setContractModel(null);
		}
	}
	
	private BigDecimal getConSettleAmount(boolean isCostSplit, String id, CurProjectInfo curProjectInfo) throws Exception {
		
		Set prjIds = (Set)getUIContext().get("prjIds");
		if(prjIds==null){
			prjIds = new HashSet();
			Object prjId = getUIContext().get("projectId");
			if(prjId==null){
				prjId=curProjectInfo.getId().toString();
			}
			prjIds.add(prjId.toString());
		}
		
		StringBuffer sb = new StringBuffer();
		int num = prjIds.size();
		int idx = 0;
		Iterator ite = prjIds.iterator();
		
		while(ite.hasNext()){
			sb.append("'"+ite.next().toString()+"'");
			if(idx<num-1){
				sb.append(",");
			}
			++idx;
		}
		
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if(isCostSplit){
			builder.appendSql("select sum(entry.famount) amount from T_CON_ContractCostSplitentry entry ");
			builder.appendSql("left join T_CON_ContractCostSplit split on  entry.fparentid = split.fid inner join T_FDC_CostAccount acc ");
			builder.appendSql(" on entry.fcostaccountid = acc.fid where ");
			builder.appendSql("acc.fcurproject in ("+ sb.toString());
			builder.appendSql(" ) and split.fcontractbillid = ? and entry.fisleaf=1 and split.fisinvalid=0  ");
			builder.addParam(id);
		}else{
			builder.appendSql("select sum(entry.famount) amount from T_CON_ConNoCostSplitentry entry ");
			builder.appendSql("left join T_CON_ConNoCostSplit split on entry.fparentid = split.fid ");
			builder.appendSql("where ");
			builder.appendSql("entry.fcurprojectid in ("+ sb.toString());
			builder.appendSql(") and split.fcontractbillid = ? and entry.fisleaf=1 and split.fisinvalid=0 ");
			builder.addParam(id);
		}
		IRowSet rowSet = null;
		try {
			rowSet = builder.executeQuery();
			while(rowSet.next()){
				return rowSet.getBigDecimal("amount");
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return FDCHelper.ZERO;
	}

	
	/**
	 * 
	 * @description 是否中标合同
	 * @author duhongming
	 * @createDate 2011-11-23
	 * @version EAS7.0
	 * @see
	 */
	private void isWin() {
		btnInviteExecudeInfo.setVisible(false);
		btnInviteExecudeInfo.setEnabled(false);
		if (editData != null && editData.getId() != null) {
			final String acceptanceLetterID = editData.getSourceBillId();
			if (!FDCHelper.isEmpty(acceptanceLetterID)
					&& BOSUuid.read(acceptanceLetterID).getType().equals(
							new BOSObjectType("1DD52081"))) {
				btnInviteExecudeInfo.setVisible(true);
				btnInviteExecudeInfo.setEnabled(true);
				pnlInviteInfo.add(btnInviteExecudeInfo);
				btnInviteExecudeInfo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							openInviteAllInfoPage(acceptanceLetterID);
						} catch (BOSException e1) {
							handUIExceptionAndAbort(e1);
						}
					}
				});
			}
		}
	}

	/**
	 * @description 业务日期数据改变后对合同编号处理
	 * @author 黄才进
	 * @createDate 2011-12-22
	 * @param newValue
	 * @version EAS7.0
	 * @see
	 */
	private void bookedDateChanged(Object newValue) {
		// 是否参与编码规则
		boolean flag = isInCode("bookedDate");
		if (!flag) {
			return;
		}
		
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			Date info = (Date) newValue;

			if (this.editData.getBookedDate() == null || !this.editData.getBookedDate().equals(info)) {
				this.editData.setBookedDate(info);

				try {
					setCodePropertyChanged(true);
					// 设置编码规则编码
					setNumberByCodingRule();
				} finally {
					setCodePropertyChanged(false);
				}
			}

		} else {
			if (newValue != null) {
				// 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的业务日期可以修改，合同编码也可以修改
				if (STATUS_EDIT.equals(getOprtState())) {
					if ((this.editData.getState().equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))) {

						actionSplit.setEnabled(true);
					} else {
						Date info = (Date) newValue;
						if (this.editData.getBookedDate() == null || !this.editData.getBookedDate().equals(info)) {
							this.editData.setBookedDate(info);

							try {
								setCodePropertyChanged(true);
								// 设置编码规则编码
								setNumberByCodingRule();
							} finally {
								setCodePropertyChanged(false);
							}
						}
					}
				}
			}
		}

	}

	/**
	 * @description 打开招标执行信息页面
	 * @author duhongming
	 * @createDate 2011-11-23
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	private void openInviteAllInfoPage(String acceptanceLetterID) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("inviteProject.id");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", acceptanceLetterID);
		view.setFilter(filter);
		AcceptanceLetterCollection col = AcceptanceLetterFactory.getRemoteInstance()
				.getAcceptanceLetterCollection(view);
		String INVITE_PROJECT = "";
		if (col != null && col.size() > 0) {
			INVITE_PROJECT = col.get(0).getInviteProject().getId().toString();
		}
		try {
			UIContext uiContext = new UIContext(this);
			 uiContext.put(UIContext.ID, null);
			uiContext.put("INVITE_PROJECT", INVITE_PROJECT);
			uiContext.put("LIST_UI", "com.kingdee.eas.fdc.invite.client.ContractBillEditUI");
			uiContext.put("INVITEPROJECT_NAME", null);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					InviteAllInformationUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException e1) {
			e1.printStackTrace();
			 FDCMsgBox.showWarning(this, "打开页面失败！");
			abort();
		}
	}
	

	/**
	 * added by shangjing
	 * 当关联了合约框架 合同的合同经济条款被带入
	 */
	protected void prmtFwContract_dataChanged(DataChangeEvent e) throws Exception {

		//得到F7选中的对象
		Object contractObj = prmtFwContract.getValue();
		//控制金额
		BigDecimal controlAmount = new BigDecimal(0);

		if (contractObj != null) {
			//转化为合约规划info
			if (contractObj instanceof String) {
				String string = getUIContext().get("projectId").toString();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programming.project.id", string));
				filter.getFilterItems().add(new FilterItemInfo("name", contractObj.toString()));
				boolean isExists = ProgrammingContractFactory.getRemoteInstance().exists(filter);
				if (isExists) {
					EntityViewInfo view = new EntityViewInfo();
					view.setFilter(filter);
					ProgrammingContractCollection collection = ProgrammingContractFactory.getRemoteInstance()
							.getProgrammingContractCollection(view);
					contractObj = collection.get(0);
				} else {
					prmtFwContract.setValue(e.getOldValue());
					return;
				}
			}
			
			
			ProgrammingContractInfo contractInfo = (ProgrammingContractInfo) contractObj;
//			if(conSplitUI != null)
//				conSplitUI.setProgrammingContractInfo(contractInfo);
			controlAmount = FDCHelper.toBigDecimal(contractInfo.getControlBalance(), 2);
			txtControlAmount.setNumberValue(controlAmount);
			//			this.txtControlAmount.setText(controlAmount
			//					.compareTo(new BigDecimal(0)) == 1 ? controlAmount
			//					.toString() : new BigDecimal(0).toString());
			//得到合约规划的经济条款 
//			ProgrammingContractEconomyCollection pcEconomyCollection = contractInfo.getEconomyEntries();
//			ProgrammingContractEconomyInfo economyInfo = null;
			//得到合约规划的付款规划   modify by yxl 20151022
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programming.id", contractInfo.getId().toString()));
			evi.setFilter(filter);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("BySchedule.*");
			sic.add("BySchedule.paymentType.number");
			sic.add("BySchedule.paymentType.name");
			sic.add("BySchedule.paymentType.id");
			evi.setSelector(sic);
			PayPlanNewCollection tColl = PayPlanNewFactory.getRemoteInstance().getPayPlanNewCollection(evi);
			if(tColl.size() > 0) {
				PayPlanNewByScheduleCollection ppbcoll = tColl.get(0).getBySchedule();
				PayPlanNewByScheduleInfo tsInfo = null;
				BigDecimal contractAmount = ceremonyb.getBigDecimalValue()==null?BigDecimal.ZERO:ceremonyb.getBigDecimalValue();
				Calendar calendar = Calendar.getInstance();
				List<Integer> months = new ArrayList<Integer>();
				Date beginDate = null;
				Date endDate = null;
				BigDecimal scale = null;
				BigDecimal amount = null;
				IRow row = null;
				tblEconItem.removeRows();
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				for(int i = 0; i < ppbcoll.size(); i++) {
					tsInfo = ppbcoll.get(i);
					beginDate = tsInfo.getBeginDate();
					endDate = tsInfo.getEndDate();
					scale = tsInfo.getPayScale();
					amount = contractAmount.multiply(scale).divide(FDCHelper.ONE_HUNDRED);
					if(beginDate!=null && endDate!=null){
						months.clear();
						while(beginDate.compareTo(endDate) <= 0) {
							calendar.setTime(beginDate);
							months.add(new Integer(calendar.get(Calendar.YEAR)*100+calendar.get(Calendar.MONTH)+1));
							calendar.add(Calendar.MONTH, 1);
							beginDate = calendar.getTime();
						}
						amount = amount.divide(new BigDecimal(months.size()),2,RoundingMode.HALF_UP);
						scale = scale.divide(new BigDecimal(months.size()),2,RoundingMode.HALF_UP);
						for (int j = 0; j < months.size(); j++) {
							row = tblEconItem.addRow();
							row.getCell("payType").setValue(tsInfo.getPaymentType());
							row.getCell("payCondition").setValue(tsInfo.getScheduleName());
							row.getCell("payRate").setValue(scale);
							row.getCell("payAmount").setValue(amount);
							row.getCell("date").setValue(dateFormat.parse(months.get(j)+"18"));
						}
					}else if(beginDate!=null && endDate==null){
						calendar.setTime(beginDate);
						row = tblEconItem.addRow();
						row.getCell("payType").setValue(tsInfo.getPaymentType());
						row.getCell("payCondition").setValue(tsInfo.getScheduleName());
						row.getCell("payRate").setValue(scale);
						row.getCell("payAmount").setValue(amount);
						row.getCell("date").setValue(dateFormat.parse(new Integer(calendar.get(Calendar.YEAR)*100+calendar.get(Calendar.MONTH)+1)+"18"));
					}else if(beginDate==null && endDate!=null){
						calendar.setTime(endDate);
						row = tblEconItem.addRow();
						row.getCell("payType").setValue(tsInfo.getPaymentType());
						row.getCell("payCondition").setValue(tsInfo.getScheduleName());
						row.getCell("payRate").setValue(scale);
						row.getCell("payAmount").setValue(amount);
						row.getCell("date").setValue(dateFormat.parse(new Integer(calendar.get(Calendar.YEAR)*100+calendar.get(Calendar.MONTH)+1)+"18"));
					}
				}
			}
			
//			PaymentTypeInfo typeInfo = null;
			//为合同中合同经济条款添加 合约规划的经济条款
			
//			if (pcEconomyCollection.size() > 0) {
//				for (int i = 0; i < pcEconomyCollection.size(); i++) {
//					economyInfo = pcEconomyCollection.get(i);
//					economyInfo = ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyInfo(
//							new ObjectUuidPK(economyInfo.getId()));
//					IRow row = tblEconItem.addRow();
//					row.getCell("date").setValue(economyInfo.getPaymentDate());
//					if (economyInfo.getPaymentType() != null) {
//						typeInfo = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(
//								new ObjectUuidPK(economyInfo.getPaymentType().getId()));
//					}
//					row.getCell("payType").setValue(typeInfo);
//					row.getCell("payCondition").setValue(economyInfo.getCondition());
//					row.getCell("payRate").setValue(economyInfo.getScale());
//					row.getCell("payAmount").setValue(economyInfo.getAmount());
//				}
//			}
		} else {
			tblEconItem.removeRows();
			txtControlAmount.setNumberValue(FDCHelper.toBigDecimal(controlAmount, 2));
			//			this.txtControlAmount.setText(controlAmount + "");
		}
	}

	// 合同签约金额超过与之关联的合约规划金额时是否严格控制
	public static final String FDC_PARAM_CONTRACT_PROGRAM_AMOUNT = "FDC228_ISSTRICTCONTROL";

	/**
	 * added by shangjing
	 * 获取参数设置值:合同签约金额超过与之关联的合同规划金额时是否严格控制
	 */
	private String getParamValue() {
		String programControlMode = "";
		try {
			// modified by zhaoqin for R130924-0167 on 2013/9/29 start
			String orgunitid = null;
			// 工程项目对应的成本中心id
			if(null != editData.getCurProject().getCostCenter())
				orgunitid = editData.getCurProject().getCostCenter().getId().toString();
			if(StringUtils.isEmpty(orgunitid) && null != this.curProject.getCostCenter())
				orgunitid = this.curProject.getCostCenter().getId().toString();
			if(!StringUtils.isEmpty(orgunitid))
				programControlMode = FDCUtils.getFDCParamByKey(null, orgunitid, FDCConstants.FDC_PARAM_CONTRACT_PROGRAM_AMOUNT);
			/*
			//这个方法里面的参数暂时有问题 可以看下 
			//programControlMode = getParam4ProgramAmountControlMode();
			// 0:严格控制 ;1:提示控制；2:不提示
			//直接查表来得到此参数的值 
			FDCSQLBuilder builder = new FDCSQLBuilder();
			//builder.appendSql("select fvalue_l2 from t_bas_paramitem where fkeyid in(select fid from t_bas_param where  fnumber  like '"
				//	+ FDC_PARAM_CONTRACT_PROGRAM_AMOUNT + "') and forgunitid like '" + comPK + "%'");

			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				programControlMode = rowSet.getString("fvalue_l2");
			}
			*/
			// modified by zhaoqin for R130924-0167 on 2013/9/29 end
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return programControlMode;
	}

	/**
	 * added by shangjing
	 * 初始化界面  
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();
		
	}

	/**
	 * 描述：根据参数初始化合约框架控件
	 * @Author：jian_cao
	 * @CreateTime：2012-10-22
	 */
	private void initProgramControlMode() {
		// added by shangjing 
		//“框架合约名称”、“控制金额”是否显示受参数“合同签约金额超过与之关联的合约规划金额时是否严格控制”影响
		String programControlMode = getParamValue();
		if (programControlMode != null) {
			// 若控制模式为 严格控制或 提示控制 显示“框架合约名称”、“控制金额”；且“框架合约名称”为必填项
			if ("0".equals(programControlMode.trim()) || "1".equals(programControlMode.trim())) {
				conContrarctRule.setVisible(true);
				conControlAmount.setVisible(true);
				ContractTypeInfo conType = editData.getContractType();
				if (conType != null && !conType.isIsRefProgram()) {
					prmtFwContract.setRequired(false);
				} else {
					prmtFwContract.setRequired(true);
				}
				if (kDPanel1.getY() == 119) {
					this.setBounds(new Rectangle(0, 0, 1013, 730));
					kDPanel1.setBounds(new Rectangle(3, 151, 998, 301));
					mainPanel.add(kDPanel1, new KDLayout.Constraints(3, 151, 1007, 301, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
					kDTabbedPane1.setBounds(new Rectangle(8, 459, 994, 234));
					mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(8, 459, 994, 234, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
					//					kDPanel1.setLocation(kDPanel1.getX(), kDPanel1.getY() + 32);
					//					kDTabbedPane1.setLocation(kDTabbedPane1.getX(), kDTabbedPane1
					//							.getY() + 32);

				}
			} else if ("2".equals(programControlMode.trim())) {
				conContrarctRule.setVisible(false);
				conControlAmount.setVisible(false);
				prmtFwContract.setRequired(false);
				if (kDPanel1.getY() == 151) {
					this.setBounds(new Rectangle(0, 0, 1013, 698));
					kDPanel1.setBounds(new Rectangle(3, 119, 998, 301));
					mainPanel.add(kDPanel1, new KDLayout.Constraints(3, 119, 1007, 301, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
					kDTabbedPane1.setBounds(new Rectangle(8, 427, 994, 234));
					mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(8, 427, 994, 234, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
					//					kDPanel1.setLocation(kDPanel1.getX(), kDPanel1.getY() - 32);
					//					kDTabbedPane1.setLocation(kDTabbedPane1.getX(), kDTabbedPane1
					//							.getY() - 32);
				}
			}
		}
   }

	// 取编码，编码规则必须设置绑定属性
	protected String getBindingProperty() {
		return BINDING_PROPERTY;
	}
	
	
	
    private int groupIndex=0;
	private IUIWindow acctUI=null;
    private String contractBillId=null;
	private Map parentMap = new HashMap();
    private List oldCostAccountLongNumber = new ArrayList();
	private HashMap entrysMap = new HashMap(); 
	private ContractBillSplitEntryCollection entrys = null;
    protected FDCCostSplitForContractSL fdcCostSplit=new FDCCostSplitForContractSL(null);
    private boolean isClearFlag = false;
	
	public void actionAcctSelect_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()==null){
			MsgBox.showInfo("请先保存合同单据！");
			return;
		}
		CostAccountCollection accts=null;
		if(acctUI==null){
			Map map = getUIContext();
			//从UIContext中获得当前ID
//			String costBillId = editData.getId().toString();
			CurProjectInfo curProject = editData.getCurProject();
			/* modified by zhaoqin for R130927-0088 on 2013/12/23 end */			
			
			//获得本合同拆分所在工程信息，放入UIContext，传递至选择科目
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",curProject);
			if (contractBillId != null) {
				uiContext.put("contractBillId", contractBillId);
			}
			/************* 作废合同重新拆分 *************/
//			uiContext.put("txtCostBillNumber", txtCostBillNumber.getText());
			/************* 作废合同重新拆分 *************/
			// 复杂模式：工程量与付款拆分不显示可拆分选项
//			if (isFinacial()) {
//				uiContext.put("isFinacial", Boolean.TRUE);
//			}
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)map.get("node");
			//如果为变更新增，则可以直接从CURRENT.VO中获得CurProjectInfo
			if(editData.getId()==null){
				if(map.get("CURRENT.VO") instanceof ConChangeSplitInfo){
					ConChangeSplitInfo info = (ConChangeSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof ContractCostSplitInfo){
					ContractCostSplitInfo info = (ContractCostSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof PaymentSplitInfo){
					PaymentSplitInfo info = (PaymentSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				
			}
//			//从uiContext中获得在FDCSplitListUI中保存的节点信息
//			if(node!=null && (node.getUserObject() instanceof OrgStructureInfo)){
//				OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
//			if(node!=null && node.getUserObject() instanceof CurProjectInfo){
//				CurProjectInfo info = (CurProjectInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
			
			
//			uiContext.put("curProject",editData.getCurProject());
//			uiContext.put("isMeasureSplit", isMeasureContract()?Boolean.TRUE:null);
			acctUI=UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(),	uiContext, null , null);       
		}else{
			((CostSplitAcctUI) acctUI.getUIObject()).actionNoneSelect_actionPerformed(null);
		}
		acctUI.show();
		IUIWindow uiWin=acctUI;
		
		if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {	
			accts=((CostSplitAcctUI) uiWin.getUIObject()).getData();
			parentMap = ((CostSplitAcctUI) uiWin.getUIObject()).getParentIdMap();
		}else{
			return;
		}
		

		CostAccountInfo acct=null;
		
		ContractBillSplitEntryInfo entry=null;
		IRow row=null;
		boolean isExist=false;
		// 在财务一体化复杂模式下做此操作 删除非明细科目
//		removeParentCostAccount(accts);
		
		for(Iterator iter=accts.iterator(); iter.hasNext();){
			acct = (CostAccountInfo)iter.next();
			
			//判断科目是否存在
			isExist=false;
			for(int i=0; i<kdtSplitEntry.getRowCount(); i++){			
				entry=(ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
								
				//允许选择在其他拆分方案中已存在的科目		jelon 12/6/06
				//if(entry.getCostAccount().getId().equals(acct.getId())){
				if(entry.getLevel()==0 && entry.getCostAccount().getId().equals(acct.getId())){
					isExist=true;
					break;
				}
			}
			if(!isExist){
				
				//entry=new FDCSplitBillEntryInfo();
				entry=new ContractBillSplitEntryInfo();
				entry.setCostAccount(acct);  
				entry.setLevel(0);
				entry.setIsLeaf(true);		//Jelon 	Dec 11, 2006
				entry.setAmount(FDCHelper.ZERO);
				entry.setProgrammings((ProgrammingContractInfo)prmtFwContract.getValue());
				//拆分组号	jelon 12/27/2006
				groupIndex++;
				entry.setIndex(groupIndex);
				
				row=addEntry(entry);
				setDisplay(row.getRowIndex());

			}				
		}
		setMenuSplitState();
		// 将直接金额的背景色设置成白色
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName())
				|| className.equals(PaymentSplitListUI.class.getName())) {
			for (int k = kdtSplitEntry.getRowCount() - 1; k > 0; k--) {
				if (kdtSplitEntry.getRow(k) != null && kdtSplitEntry.getRow(k).getCell("directAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
				if (kdtSplitEntry.getRow(k) != null
						&& kdtSplitEntry.getRow(k).getCell("directPayedAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directPayedAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
			}
		} else if (className.equals(WorkLoadConfirmBillListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			for (int k = kdtSplitEntry.getRowCount() - 1; k > 0; k--) {
				if (kdtSplitEntry.getRow(k) != null && kdtSplitEntry.getRow(k).getCell("directAmt") != null)
				kdtSplitEntry.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(new Color(0xffffff));
			}
		}
		setOneEntryAmt(txtamount.getBigDecimalValue());
	}
	
	 /**
     * 描述：针对一个科目的情况增加自动填入变更金额的功能
     * 后续可能会抽象到基类中支持所有拆分
     * 
     * @param shouldSplitAmt:应拆金额
     */
	private void setOneEntryAmt(BigDecimal shouldSplitAmt) throws Exception{
//		if(kdtSplitEntry.getRowCount()==1){
//			KDTEditEvent event = new KDTEditEvent(
//					kdtSplitEntry, null, null, 0,
//					kdtSplitEntry.getColumnIndex("amount"), true, 1);
//			final IRow row = kdtSplitEntry.getRow(0);
//			row.getCell("amount").setValue(shouldSplitAmt);
//			event.setValue(shouldSplitAmt);
//			kdtSplitEntry_editStopped(event);
//		}
	}
	
	public void setMenuSplitState() {
		// 新的成本科目编码集合
		List newCostAccountLongNumber = new ArrayList();
		// 判断是否工程量拆分和付款拆分打开
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillListUI.class.getName()) || className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			// 遍历新形成表格存入新的成本科目编码
			String longNumber = null;
			PaymentSplitEntryInfo info = null;
			for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
				info = (PaymentSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
				// 判断当前是否成本科目
				if (info.getCostAccount() instanceof CostAccountInfo) {
					newCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
				}
			}
			// 判断旧成本科目编码和新成本科目编码是否一致
			if (!oldCostAccountLongNumber.containsAll(newCostAccountLongNumber)) {
				this.getUIContext().put("isCanEnable", Boolean.FALSE);
				PaymentSplitEntryInfo tmpInfo = null;
				// 遍历新的表格用于判断是否全都是最明细科目
				for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
					tmpInfo = (PaymentSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
					// 判断当前行是否成本科目
					if (tmpInfo.getCostAccount() instanceof CostAccountInfo) {
						// 判断是否最明细成本科目，如果不是就设置按钮可编辑状态到工程量拆分和付款拆分
						if (!tmpInfo.getCostAccount().isIsLeaf()) {
							this.getUIContext().put("isCanEnable", Boolean.FALSE);
							return;
						} 
					}
				}
			}
		}
	}
	
    private void setDisplay(int rowIndex){
    	initDirectMap.clear();
    	setOneTreeDisplay(rowIndex);
    	initDirectAssign();
    	
    	setDisplay();
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	
    	sic.add(new SelectorItemInfo("isCoseSplit"));
		sic.add(new SelectorItemInfo("isPartAMaterialCon"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("codingNumber"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("chgPercForWarn"));
		sic.add(new SelectorItemInfo("payPercForWarn"));
		sic.add(new SelectorItemInfo("signDate"));
		sic.add(new SelectorItemInfo("landDeveloper.*"));
		sic.add(new SelectorItemInfo("contractType.*"));
		sic.add(new SelectorItemInfo("costProperty"));
		sic.add(new SelectorItemInfo("contractPropert"));
		sic.add(new SelectorItemInfo("contractSourceId.*"));
		sic.add(new SelectorItemInfo("partB.id"));
		sic.add(new SelectorItemInfo("isContractor"));
		sic.add(new SelectorItemInfo("contractPrice"));
		sic.add(new SelectorItemInfo("partB.number"));
		sic.add(new SelectorItemInfo("partB.name"));
		sic.add(new SelectorItemInfo("partC.id"));
		sic.add(new SelectorItemInfo("partC.number"));
		sic.add(new SelectorItemInfo("partC.name"));

		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("isSubContract"));
		
		sic.add(new SelectorItemInfo("lowestPriceUnit.id"));
		sic.add(new SelectorItemInfo("lowestPriceUnit.number"));
		sic.add(new SelectorItemInfo("lowestPriceUnit.name"));
		
		sic.add(new SelectorItemInfo("lowerPriceUnit.id"));
		sic.add(new SelectorItemInfo("lowerPriceUnit.number"));
		sic.add(new SelectorItemInfo("lowerPriceUnit.name"));
		
		sic.add(new SelectorItemInfo("middlePriceUnit.id"));
		sic.add(new SelectorItemInfo("middlePriceUnit.number"));
		sic.add(new SelectorItemInfo("middlePriceUnit.name"));
		
		sic.add(new SelectorItemInfo("higherPriceUnit.id"));
		sic.add(new SelectorItemInfo("higherPriceUnit.number"));
		sic.add(new SelectorItemInfo("higherPriceUnit.name"));
		
		sic.add(new SelectorItemInfo("highestPriceUni.id"));
		sic.add(new SelectorItemInfo("highestPriceUni.number"));
		sic.add(new SelectorItemInfo("highestPriceUni.name"));
		
		sic.add(new SelectorItemInfo("remark"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("coopLevel"));
		sic.add(new SelectorItemInfo("priceType"));
		sic.add(new SelectorItemInfo("mainContract.*"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("information"));
		sic.add(new SelectorItemInfo("lowestPrice"));
		sic.add(new SelectorItemInfo("lowerPrice"));
		sic.add(new SelectorItemInfo("higherPrice"));
		sic.add(new SelectorItemInfo("middlePrice"));
		sic.add(new SelectorItemInfo("highestPrice"));
		sic.add(new SelectorItemInfo("basePrice"));
		sic.add(new SelectorItemInfo("secondPrice"));
		sic.add(new SelectorItemInfo("inviteType.*"));
		sic.add(new SelectorItemInfo("winPrice"));
		sic.add(new SelectorItemInfo("winUnit.id"));
		sic.add(new SelectorItemInfo("winUnit.number"));
		sic.add(new SelectorItemInfo("winUnit.name"));
		sic.add(new SelectorItemInfo("fileNo"));
		sic.add(new SelectorItemInfo("quantity"));
		sic.add(new SelectorItemInfo("exRate"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("grtAmount"));
		sic.add(new SelectorItemInfo("currency"));
		sic.add(new SelectorItemInfo("respDept.*"));
		sic.add(new SelectorItemInfo("payScale"));
		sic.add(new SelectorItemInfo("stampTaxRate"));
		sic.add(new SelectorItemInfo("stampTaxAmt"));
		sic.add(new SelectorItemInfo("respPerson.*"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("grtRate"));
		sic.add(new SelectorItemInfo("bookedDate"));
		sic.add(new SelectorItemInfo("period.*"));
		sic.add(new SelectorItemInfo("conChargeType.*"));
		sic.add(new SelectorItemInfo("overRate"));
		sic.add(new SelectorItemInfo("ceremonyb"));
		sic.add(new SelectorItemInfo("ceremonybb"));
		sic.add(new SelectorItemInfo("programmingContract.name"));
		sic.add(new SelectorItemInfo("programmingContract.controlAmount"));
		sic.add(new SelectorItemInfo("payItems.payItemDate"));
		sic.add(new SelectorItemInfo("payItems.payCondition"));
		sic.add(new SelectorItemInfo("payItems.prop"));
		sic.add(new SelectorItemInfo("payItems.amount"));
		sic.add(new SelectorItemInfo("payItems.desc"));
		sic.add(new SelectorItemInfo("payItems.paymentType.*"));
		sic.add(new SelectorItemInfo("bail.entry.bailDate"));
		sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
		sic.add(new SelectorItemInfo("bail.entry.prop"));
		sic.add(new SelectorItemInfo("bail.entry.amount"));
		sic.add(new SelectorItemInfo("bail.entry.desc"));
		sic.add(new SelectorItemInfo("bail.entry.*"));
		sic.add(new SelectorItemInfo("bail.amount"));
		sic.add(new SelectorItemInfo("bail.prop"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.costCenter"));		// modified by zhaoqin for 建发 on 2013/10/10
		sic.add(new SelectorItemInfo("curProject.isWholeAgeStage"));		// modified by zhaoqin for 建发 on 2013/10/10
		
		sic.add(new SelectorItemInfo("curProject.parent.id"));
		sic.add(new SelectorItemInfo("curProject.parent.number"));
		sic.add(new SelectorItemInfo("curProject.parent.name"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.precision"));

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractType.isLeaf"));
		sic.add(new SelectorItemInfo("contractType.level"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longnumber"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("contractType.isRefProgram"));
		sic.add(new SelectorItemInfo("contractType.isWorkLoadConfirm"));
		
		sic.add(new SelectorItemInfo("codeType.id"));
		sic.add(new SelectorItemInfo("codeType.name"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.thirdType"));
		sic.add(new SelectorItemInfo("codeType.secondType"));
		
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractPlan.*"));

		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));

		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		
		sic.add(new SelectorItemInfo("payItems.*"));
		sic.add(new SelectorItemInfo("bail.*"));
		sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
		sic.add(new SelectorItemInfo("bail.entry.bailAmount"));
		sic.add(new SelectorItemInfo("bail.entry.prop"));
		sic.add(new SelectorItemInfo("bail.entry.bailDate"));
		sic.add(new SelectorItemInfo("bail.entry.desc"));
		
		sic.add(new SelectorItemInfo("auditor.id"));
		
		sic.add(new SelectorItemInfo("sourceBillId"));
		
		sic.add(new SelectorItemInfo("mainContract.*"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("information"));
		
		sic.add(new SelectorItemInfo("overRate"));
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("bookDate"));
		
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("programmingContract.programming.*"));
		sic.add(new SelectorItemInfo("programmingContract.programming.project.id"));
		
		sic.add(new SelectorItemInfo("srcProID"));
		sic.add(new SelectorItemInfo("contractModel"));
		sic.add(new SelectorItemInfo("entrustReason"));
		sic.add(new SelectorItemInfo("mIndexType"));
		sic.add(new SelectorItemInfo("isFiveClass"));
    	
		sic.add("splitEntry.costAccount.curProject.isLeaf");
		sic.add("splitEntry.costAccount.curProject.longNumber");
		sic.add("splitEntry.costAccount.curProject.number");
		sic.add("splitEntry.costAccount.curProject.name");
		sic.add("splitEntry.costAccount.curProject.displayName");
		sic.add("splitEntry.costAccount.name");
		sic.add("splitEntry.costAccount.longNumber");
		sic.add("splitEntry.costAccount.displayName");
		sic.add(new SelectorItemInfo("splitEntry.id"));
    	sic.add(new SelectorItemInfo("splitEntry.amount"));
    	sic.add(new SelectorItemInfo("splitEntry.product.id"));
		sic.add(new SelectorItemInfo("splitEntry.product.name"));
    	sic.add(new SelectorItemInfo("splitEntry.product.number"));
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.curProject.*"));
    	sic.add(new SelectorItemInfo("splitEntry.costAccount.*"));
    	sic.add(new SelectorItemInfo("splitEntry.level"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionType.name"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValue"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmount"));
    	sic.add(new SelectorItemInfo("splitEntry.apportionValueTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.directAmountTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.otherRatioTotal"));
    	sic.add(new SelectorItemInfo("splitEntry.splitType"));
    	sic.add(new SelectorItemInfo("splitEntry.workLoad"));
    	sic.add(new SelectorItemInfo("splitEntry.price"));
    	sic.add(new SelectorItemInfo("splitEntry.splitScale"));
    	sic.add(new SelectorItemInfo("splitEntry.programmings.id"));
		sic.add(new SelectorItemInfo("splitEntry.programmings.name"));
    	sic.add(new SelectorItemInfo("splitEntry.programmings.number"));
    	return sic;
    }
    
	/**
	 * 描述：设置分摊标准（全部）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    protected void setDisplay(){
    	ContractBillSplitEntryInfo entry=null;
    	IRow row=null;
    	initDirectMap.clear();
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			row=kdtSplitEntry.getRow(i);
			entry = (ContractBillSplitEntryInfo)row.getUserObject();
			if(entry.getLevel()==0){
				setOneTreeDisplay(i);
				//引入合同拆分比例时计算表头已拆分
				calcAmount(i);
			}
		}
		initDirectAssign();
    }
    
    private void setOneTreeDisplay(int rowIndex){
    	ContractBillSplitEntryInfo topEntry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();    	
    	int topLevel=topEntry.getLevel();		
        CostAccountInfo topAcct=topEntry.getCostAccount();

        ContractBillSplitEntryInfo entry=null;
        IRow row=null;
        ICell cell=null;
        String display=null;
        int level=0;
        
        CostAccountInfo acct=null;
        CurProjectInfo proj=null;
        
        

    	NodeClickListener nodeClickListener = new NodeClickListener(){
    		public void doClick(CellTreeNode source, ICell cell, int type)	{
    			//项目展开跟产品展开应该进行区分 by sxhong 2009/02/05
    			if(source!=null&&!source.isCollapse()&&source.isHasChildren()){
    				//将其下级所有的+变成-
    				int level=source.getTreeLevel();
    				for(int i=cell.getRowIndex()+1;i<kdtSplitEntry.getRowCount();i++){
    					if(cell.getColumnIndex()==kdtSplitEntry.getColumnIndex("costAccount.curProject.name")){
    						ICell cell2 = kdtSplitEntry.getCell(i, "costAccount.curProject.name");
    						if(cell2.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell2.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    						ICell cell3 = kdtSplitEntry.getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							node.setCollapse(false);
    						}
    					}else if(cell.getColumnIndex()==kdtSplitEntry.getColumnIndex("costAccount.name")){
    						ICell cell3 = kdtSplitEntry.getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    					}
    				}
    			}
     		}
    	};
        
        for(int i=rowIndex; i<kdtSplitEntry.getRowCount(); i++){
        	row=kdtSplitEntry.getRow(i);   
			entry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			
			level=entry.getLevel();
			
			acct=entry.getCostAccount();
			if(acct==null){
				proj=null;
			}else{
				proj=acct.getCurProject();
			}
				
			if(level>=topLevel){
				if(level==topLevel && i!=rowIndex){
					//下一个分配树
					break;	
				}
				
				//编码、名称
				if(entry.getCostAccount().getCurProject()!=null){
					//编码
					row.getCell("costAccount.curProject.number").setValue(
							entry.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
					row.getCell("costAccount.number").setValue(
							entry.getCostAccount().getLongNumber().replace('!','.'));

					//名称
					if(level==0){
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getDisplayName().replace('_','\\'));
						
					}else if(entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT && entry.isIsLeaf()){
						//产品拆分明细
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						row.getCell("costAccount.name").setValue("");
						
					}else if(entry.isIsAddlAccount()){
						//附加科目，直接分配
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						//row.getCell("costAccount.number").setValue(entry.getCostAccount().getLongNumber());
						row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());
						
					}else{
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getName());
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getName());	
					}		
					
					
					//测试树形
					if(level>=topLevel){
						CellTreeNode node = new CellTreeNode();
						node.addClickListener(nodeClickListener);			
						cell=row.getCell("costAccount.curProject.name");			
						// 节点的值
						node.setValue(cell.getValue());
						// 是否有子节点
						//if(entry.getCostAccount().getLongNumber().equals(topAcct.getLongNumber()) && !entry.getCostAccount().getCurProject().isIsLeaf()){
						/*if(entry.getCostAccount().getLongNumber().replace('!','.').equals(topAcct.getLongNumber().replace('!','.')) 
								&& !entry.getCostAccount().getCurProject().isIsLeaf()
								&& !isProdSplitLeaf(entry)){*/
						/*
						 * 屏蔽设置是否有孩子节点 by 29 // if (!entry.isIsLeaf() // &&
						 * !entry.getCostAccount().getCurProject().isIsLeaf() //
						 * && //
						 * entry.getCostAccount().getLongNumber().replace('!',
						 * // '.').equals( //
						 * topAcct.getLongNumber().replace('!', '.'))) { //
						 * node.setHasChildren(true); // } else { // //
						 * node.setHasChildren(false); // }
						 */
						
						//node.setHasChildren(!entry.isIsAddlAccount());
						// 节点的树级别
						node.setTreeLevel(entry.getLevel());
						cell.getStyleAttributes().setLocked(false);
						cell.setValue(node);
						
						if(level!=topLevel){
							node = new CellTreeNode();
							node.addClickListener(nodeClickListener);			
							cell=row.getCell("costAccount.name");			
							// 节点的值
							node.setValue(cell.getValue());
							/*
							 * 屏蔽设置是否有孩子节点 by 29// 是否有子节点 //
							 * node.setHasChildren(!entry.getCostAccount(). //
							 * isIsLeaf() // || (!entry.isIsLeaf() && //
							 * entry.getCostAccount().isIsLeaf() && //
							 * entry.getSplitType()!=null && //
							 * entry.getSplitType() //
							 * .equals(CostSplitTypeEnum.PRODSPLIT)) );
							 */
							// 节点的树级别
							//node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel()+1);	
							}else{
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							}
											
							cell.getStyleAttributes().setLocked(false);
							cell.setValue(node);							
						}
						//end
					}
				}
								
								
				//颜色
				if(level==0){
					row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
					row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));					
					row.getCell("splitScale").getStyleAttributes().setBackground(new Color(0xFFFFFF));
				}else{
					if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT) && entry.getProduct()==null){
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
					}else{
						row.getStyleAttributes().setBackground(new Color(0xE8E8E3));
					}					
					row.getCell("amount").getStyleAttributes().setLocked(true);
					//非科目行不能编辑 by hpw 2010-06-25
					row.getCell("splitScale").getStyleAttributes().setLocked(true);
					
					//附加科目处理（允许录入金额）
					/*
					if(entry.isIsAddlAccount() && entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
						if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)){						
						}else{
							row.getCell("amount").getStyleAttributes().setLocked(false);
							row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));									
						}					
					}*/
					if(entry.isIsAddlAccount() 
							&& proj!=null && proj.isIsLeaf()
							&& acct!=null && acct.isIsLeaf()
							&& !isProdSplitLeaf(entry)){
						row.getCell("amount").getStyleAttributes().setLocked(false);
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));								
					}
				}					
				if(isMeasureContract()){
//					row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
//					row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					setMeasureCtrl(row);
				}
				//直接归属
				initDirectAssign(row);		
				
			}else{
				break;
			}
			
        }
        for(int i=rowIndex;i<kdtSplitEntry.getRowCount();i++){
        	row=kdtSplitEntry.getRow(i);
			entry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()==0&&i!=rowIndex){
				break;
			}
			IRow rowNext = kdtSplitEntry.getRow(i + 1);
			// 取下级有非空情况需要判断
			if (rowNext == null) {
				continue;
			}
			if (!entry.isIsLeaf() && rowNext.getStyleAttributes().isHided()) {
				Object obj = row.getCell("costAccount.name").getValue();
				CellTreeNode node=null;
				if(obj instanceof CellTreeNode){
					node=(CellTreeNode)obj;
					node.setCollapse(true);
				}
				
			}
        }
        //归属标准
        setStandard(rowIndex);
    }
    
	public void actionSplitBotUp_actionPerformed(ActionEvent arg0) throws Exception {
		splitCost(CostSplitTypeEnum.BOTUPSPLIT);
	}
	
	public void actionRemoveSplit_actionPerformed(ActionEvent e)throws Exception {
		super.actionRemoveSplit_actionPerformed(e);

    	if(!actionRemoveSplit.isEnabled()||!actionRemoveSplit.isVisible()) return;
        //if ((kdtSplitEntry.getSelectManager().size() == 0) || isTableColumnSelected(kdtSplitEntry))
    	if ((kdtSplitEntry.getSelectManager().size() == 0))
        {
            FDCMsgBox.showInfo(this, EASResource
                    .getString(FrameWorkClientUtils.strResource
                            + "Msg_NoneEntry"));

            //FDCMsgBox.showInfo(this,"没有选中分录，无法删除！");
            return;
        }

        //[begin]进行删除分录的提示处理。
        if(confirmRemove())
        {
            int top = kdtSplitEntry.getSelectManager().get().getBeginRow();
            int bottom = kdtSplitEntry.getSelectManager().get().getEndRow();
            
            int idx=0;
            int idx1,idx2;
            
            boolean isTrue=false;
            ContractBillSplitEntryInfo entry=null;
            
            for(int i =bottom ;i>=top ;i--)
            {
            	idx=i;
            	
            	idx1=idx;
            	idx2=idx;
            	
            	//查找最后一行
            	isTrue=false;
            	for(int j=i+1; j<kdtSplitEntry.getRowCount(); j++){
            		entry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			idx2=j-1;
            			isTrue=true;
            			break;
            		}
            	}
            	if(!isTrue){
            		idx2=kdtSplitEntry.getRowCount()-1;
            	}
            	if(idx2<idx){
            		idx2=idx;
            	} 
            	
            	//从最后一行向前删除，直至Level=0
            	for(int j=idx2; j>=0; j--){
            		idx1=j;
            		
            		entry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			removeEntry(j);
            			break;
            		}else{
            			removeEntry(j);
            		}
            	}
            	
            	//i=idx1-1;
            	i=idx1;
            }            
        }

        
        if(kdtSplitEntry.getRowCount()>0){
        	calcAmount(0);
        }else{
        	txtSplitedAmount.setValue(FDCHelper.ZERO);    
        }        	
        

		//拆分组号		jelon 12/28/2006
		int idx=0;
		ContractBillSplitEntryInfo entry=null;
		for(int i=0; i<kdtSplitEntry.getRowCount(); i++){	
			entry=(ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
    
	}
    
    protected void calcAmount(int rowIndex){
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		BigDecimal amount = FDCHelper.ZERO;
		
		ContractBillSplitEntryInfo entry = null;
		
		//计算拆分总金额
		for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			/*if (kdtSplitEntry.getRow(i).getCell(COLAMOUNT).getValue()!=null){
				amount = amount.add(new BigDecimal(kdtSplitEntry.getRow(i).getCell(COLAMOUNT).getValue().toString()));
			}*/
			entry = (ContractBillSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
			
			if (entry.getLevel() == 0) {
				amount = entry.getAmount();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}
		amountTotal = amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (txtSplitedAmount.getBigDecimalValue() != null
				&& amountTotal.compareTo(FDCHelper.toBigDecimal(txtSplitedAmount.getBigDecimalValue())
						.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) {
			try {
				txtSplitedAmount_dataChanged(null);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			txtSplitedAmount.setValue(amountTotal);
		}    	
    } 
    
	protected void txtSplitedAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(e==null) return;
		super.txtSplitedAmount_dataChanged(e);

		BigDecimal amount = FDCHelper.toBigDecimal(txtamount.getValue());

		BigDecimal amtSplited = FDCHelper.toBigDecimal(e.getNewValue());

		txtUnSplitAmount.setValue(FDCHelper.subtract(amount, amtSplited));
	}
    
    public void actionSplitProd_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSplitProd_actionPerformed(e);
    	splitCost(CostSplitTypeEnum.PRODSPLIT);
    }
    
	public void actionSplitProj_actionPerformed(ActionEvent arg0) throws Exception {
		splitCost(CostSplitTypeEnum.PROJSPLIT);
	}
	
	private void splitCost(CostSplitTypeEnum costSplitType) throws Exception {

		//----------------------------------------------------------------------------------------
		//选择行

        if ((kdtSplitEntry.getSelectManager().size() == 0)
                || isTableColumnSelected(kdtSplitEntry))
        {
            FDCMsgBox.showInfo(this, "没有选中分录，无法设置拆分方案！");
            return;
        }
		
		
		int topIdx=-1;		
		int[] selectRows = KDTableUtil.getSelectedRows(kdtSplitEntry);        
        if(selectRows.length >0){
        	topIdx = selectRows[0];
        }
        if(!(topIdx>=0)){
        	return;
        }        	        
        

		//----------------------------------------------------------------------------------------
        //拆分对象
        IRow topRow=kdtSplitEntry.getRow(topIdx);         
		//FDCSplitBillEntryInfo selectEntry=editData.getEntrys().get(selectIdx);
        ContractBillSplitEntryInfo topEntry=(ContractBillSplitEntryInfo)topRow.getUserObject();
        
        

		int topLevel=topEntry.getLevel();			
		BOSUuid topId=topEntry.getId();		
		CostAccountInfo topAcct=topEntry.getCostAccount();			
		if(topAcct==null){
			return;
		}
		String topAcctNo=topEntry.getCostAccount().getLongNumber();
		        
        //拆分类型
		CostSplitTypeEnum splitType=topEntry.getSplitType();
		
		
		boolean isTrue=true;	
		
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){			//产品拆分	
			if(!isProdSplitEnabled(topEntry)){
				//FDCMsgBox.showInfo(this,"当前分录无法设置产品分摊方案！");			
				FDCMsgBox.showWarning(this,"所选分录不符合产品拆分条件,请选择明细分录进行操作");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)){	//自动拆分	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT)){					
					isTrue=false;					
				}
			}
			
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue=false;
			}
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"当前分录无法设置自动拆分方案！");		
				FDCMsgBox.showWarning(this, "所选分录不符合自动拆分条件,请选择一级非明细分录进行操作");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	//末级拆分	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				//if(topEntry.getSplitType().equals(CostSplitType.PROJSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
					//isTrue=false;
										
					//将当前的自动拆分转换成末级拆分	jelon 12/6/06
					if(topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) && topEntry.getLevel()==0){						
						if (!FDCMsgBox.isYes(FDCMsgBox
				                .showConfirm2(this,FDCSplitClientHelper.getRes("sure")))){
							return;
						}							
					}else{
						isTrue=false;
					}
				}
			}
			
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(topAcct.isIsLeaf() && topAcct.getCurProject().isIsLeaf()){
				isTrue=false;
			}
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue = false;
			}
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"当前分录无法设置末级拆分方案！");		
				FDCMsgBox.showWarning(this, "所选分录不符合末级拆分条件,请选择一级非明细分录进行操作");
				return;
			}
		}
				
		//topEntry.setSplitType(costSplitType);
        
        int level=0;

		
		//----------------------------------------------------------------------------------------
		//准备参数
        ContractBillSplitEntryCollection entrys=new ContractBillSplitEntryCollection();
		entrys.add(topEntry);
				
		ContractBillSplitEntryInfo entry=null;
		for(int i=topIdx+1; i<kdtSplitEntry.getRowCount(); i++){
			entry=(ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();		
			
			if(entry.getLevel()>topLevel){
				entrys.add(entry);
			}else{
				break;
			}
		}
				

		//----------------------------------------------------------------------------------------
		//拆分设置UI
		
		FDCSplitBillEntryCollection arfterOldEntrys = new FDCSplitBillEntryCollection();
		for (int i = 0; i < entrys.size(); i++) {
			FDCSplitBillEntryInfo oldNewEntryInfo = new FDCSplitBillEntryInfo();
			oldNewEntryInfo.putAll(entrys.get(i));
			arfterOldEntrys.add(oldNewEntryInfo);
		}
		
		UIContext uiContext = new UIContext(this); 
		//uiContext.put("costSplit", editData.getEntrys());		
		uiContext.put("costSplit", arfterOldEntrys);			
		uiContext.put("splitType", costSplitType);		
		uiContext.put("entryClass", getSplitBillEntryClassName());		
		uiContext.put("parentMap", parentMap);
		String apptUiName;
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			apptUiName=CostSplitApptProdUI.class.getName();
		}else{
			apptUiName=CostSplitApptProjUI.class.getName();
		}
		
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				apptUiName,	uiContext, null ,STATUS_ADDNEW );       
		uiWin.show();	
			
		if (!((CostSplitApptUI) uiWin.getUIObject()).isOk()) {
			return;
		}

		//返回值
		entrys=new ContractBillSplitEntryCollection();
		FDCSplitBillEntryCollection oldEntrys =(FDCSplitBillEntryCollection) ((CostSplitApptUI) uiWin.getUIObject()).getData() ;
		for (int i = 0; i < oldEntrys.size(); i++) {
			ContractBillSplitEntryInfo newInfo = new ContractBillSplitEntryInfo();
			newInfo.putAll(oldEntrys.get(i));
			entrys.add(newInfo);
		}
//		entrys =oldEntrys.c;

		//		for (int i = 0; i < entrys.size(); i++) {
		//			if (entrys.get(i).getLevel() > 1) {
		//				entrys.get(i).setLevel(1);
		//			}
		//		}

		//----------------------------------------------------------------------------------------
		//删除原来的拆分
		int index=0;
		for(int i=topIdx+1; i<kdtSplitEntry.getRowCount(); i++){
			entry=(ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()>topLevel){
				index=i;
			}else{
				break;
			}			
		}
		for(int i=index; i>topIdx ; i--){
			entry=(ContractBillSplitEntryInfo)kdtSplitEntry.getRow(i).getUserObject();
			if(entry.getLevel()==topLevel){
				break;
			}
			else
			{
				removeEntry(i);
			}
		}
				
				
		
		//----------------------------------------------------------------------------------------
		
		//成本科目
		CostAccountInfo acct=null;
		acct=entrys.get(0).getCostAccount();
					
		//拆分类型
		splitType=costSplitType;	//CostSplitType.BOTUPSPLIT;
				
		//分摊类型
		ApportionTypeInfo apportionType;
		apportionType = entrys.get(0).getApportionType();  
		
		//附加科目
		boolean isAddlAcct=entrys.get(0).isIsAddlAccount();
		
		topEntry.setSplitType(splitType);
		topEntry.setApportionType(apportionType);	
		topEntry.setIsLeaf(false);			
		topEntry.setProduct(null);
		
		topRow.getCell("standard").setValue(splitType.toString());
		topRow.getCell("product").setValue("");
		topRow.getCell("product").getStyleAttributes().setLocked(true);
		
		//调试　begin
		if(apportionType!=null){
			topRow.getCell("apportionType.name").setValue(apportionType.getName());
		}
		topRow.getCell("splitType").setValue(splitType);
		//调试　end
		
		IRow row;				
		
		//产品拆分：删除全部拆分项
		if(entrys.size()==1){	
			topEntry.setIsLeaf(true);
			
			if(topEntry.getLevel()==0){
				topEntry.setSplitType(CostSplitTypeEnum.MANUALSPLIT);
				topEntry.setApportionType(null);
				
				topRow.getCell("splitType").setValue(CostSplitTypeEnum.MANUALSPLIT);
				//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
				

				//topRow.getCell("standard").setValue("");
				setDisplay(topIdx);
				
			}else{
				for(int i=topIdx-1; i>=0; i--){
					row=kdtSplitEntry.getRow(i);
					entry=(ContractBillSplitEntryInfo) row.getUserObject();
					
					if(entry.getLevel()==topEntry.getLevel()-1){
						topEntry.setSplitType(entry.getSplitType());
						topEntry.setApportionType(null);

						topRow.getCell("splitType").setValue(entry.getSplitType());
						//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
						
						setDisplay(i);
						
						break;
					}
				}
				
			}
			return;
		}
		//插入新的拆分行
		int idxCurr=topIdx;
		
		for(int i=1; i<entrys.size(); i++){
			entry=entrys.get(i);				

			//拆分组号	jelon 12/27/2006
			entry.setIndex(topEntry.getIndex());
						
			//entry.setSplitType(splitType);
			if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
				//项目拆分中包含的产品拆分
			}else{
				entry.setSplitType(splitType);
			}
			
			
			if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
				//项目拆分中包含的产品拆分
				entry.setIsAddlAccount(isAddlAcct);
			}
									
			idxCurr++;
			row=insertEntry(idxCurr,entry);			
			
			row.getCell("costAccount.curProject.name").setValue(entry.getCostAccount().getCurProject().getName());
			row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());	
		}
		
		//----------------------------------------------------------------------------------------
		
		//计算汇总数	
		//calcApportionData(topIdx,costSplitType);	//使用新接口　jelon 12/26/2006
		totApptValue(topIdx);

		//分摊成本
		//calcApportionAmount(topIdx,costSplitType);	//使用新接口　jelon 12/26/2006
		apptAmount(topIdx);
			

		//设置显示
		index=topIdx;
		
		//产品拆分，从拆分树的根节点开始设置
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			row=kdtSplitEntry.getRow(topIdx);
			entry=(ContractBillSplitEntryInfo)row.getUserObject();
			if(entry.getLevel()!=0){
				for(int i=topIdx-1; i>=0; i--){
					row=kdtSplitEntry.getRow(i);
					entry=(ContractBillSplitEntryInfo)row.getUserObject();
					if(entry.getLevel()==0){
						index=i;
						break;
					}
				}
			}
		}
		setDisplay(index);		
	}
	
    protected IRow insertEntry(int rowIndex, IObjectValue detailData)
    {
        IRow row = null;
        
        row = kdtSplitEntry.addRow(rowIndex);

        loadLineFields(kdtSplitEntry, row, detailData);
        
        return row;
    }
	
    private void totApptValue(int rowIndex){
    	ContractBillSplitEntryInfo entry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();

    	//修改调用接口	jelon 12/26/2006
		/*CostSplitType splitType=entry.getSplitType();
		calcApportionData(rowIndex,splitType);*/
		

		//fdcCostSplit.totApptValue((IObjectCollection)editData.get("entrys"),entry);
		fdcCostSplit.totApptValue(getEntrys(),entry);
						
		int level=entry.getLevel();
		IRow row=null;
		BigDecimal amount=null;
		
		for(int i=rowIndex; i<kdtSplitEntry.getRowCount(); i++){				
			row=kdtSplitEntry.getRow(i);
			entry=(ContractBillSplitEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level
					|| (entry.getLevel()==level && i==rowIndex)){
				amount=entry.getApportionValueTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("apportionValueTotal").setValue(amount);   	 

				amount=entry.getDirectAmountTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("directAmountTotal").setValue(amount); 

				amount=entry.getOtherRatioTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("otherRatioTotal").setValue(amount);
				
			}else{
				break;
			}
		}
    }
	
    protected void removeEntry(int idxRow)
    {    	
        IObjectValue detailData = (IObjectValue) kdtSplitEntry.getRow(idxRow).getUserObject();
        kdtSplitEntry.removeRow(idxRow);
        
        IObjectCollection collection = (IObjectCollection) kdtSplitEntry.getUserObject();
        if (collection == null)
        {
        	return;
        }
        else
        {
            if( detailData != null ) {
                collection.removeObject(detailData);
            }
        }
    }
    
    protected ContractBillSplitEntryCollection getEntrys(){
		entrysMap.clear();
		entrys = new ContractBillSplitEntryCollection();
		for (int i = 0; i < kdtSplitEntry.getRowCount(); i++) {
			ContractBillSplitEntryInfo info = (ContractBillSplitEntryInfo) kdtSplitEntry.getRow(i).getUserObject();
			// entrysMap.put(String.valueOf(info.getSeq()), info);
			String key = getEntrysMapKey(info);
			entrysMap.put(key, info);
			entrys.add(info);
		}
    	
    	return entrys;
	}
    
	protected String getEntrysMapKey(ContractBillSplitEntryInfo entryInfo) {
		String key = entryInfo.getSeq() + "";

		return key;
	}
	
	
    protected void apptAmount(int rowIndex){
    	ContractBillSplitEntryInfo topEntry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(rowIndex).getUserObject();
		fdcCostSplit.apptAmount(getEntrys(),topEntry);
		
		int level=topEntry.getLevel();
		IRow row=null;
		boolean isMeasureContract=isMeasureContract();
		
		Object value = kdtSplitEntry.getCell(rowIndex, "amount").getValue();
		//已分摊总金额
		BigDecimal totalAmt = FDCHelper.ZERO;
		for(int i=rowIndex+1; i<kdtSplitEntry.getRowCount(); i++){				
			row=kdtSplitEntry.getRow(i);
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level){
				BigDecimal amount=entry.getAmount();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("amount").setValue(amount);
				if (entry.getLevel() == level + 1) {
					totalAmt = FDCHelper.add(totalAmt, amount);
				}
				if (i == kdtSplitEntry.getRowCount() - 1 && FDCHelper.compareTo(value, totalAmt) != 0) {
					row.getCell("amount").setValue(
							FDCHelper.add(row.getCell("amount").getValue(), FDCHelper.subtract(value, totalAmt)));
				}
				
				if(isMeasureContract&&isProdSplitLeaf(entry)){
					entry.setPrice(topEntry.getPrice());
					row.getCell("price").setValue(topEntry.getPrice());	
					row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				}
			}else{
				break;
			}
		}
    }
	
	protected String getSplitBillEntryClassName(){
		return ConChangeSplitEntryInfo.class.getName();
	}
    
	protected boolean isMeasureContract(){
		return false;
	}
    
	protected void setMeasureCtrl(final IRow row) {
		Color cantEditColor=new Color(0xF5F5E6);
		row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		BigDecimal amount=FDCHelper.toBigDecimal(row.getCell("amount").getValue());
		BigDecimal price=FDCHelper.toBigDecimal(row.getCell("price").getValue());
		BigDecimal workLoad=FDCHelper.toBigDecimal(row.getCell("workLoad").getValue());
		if(price.signum()!=0||workLoad.signum()!=0){
			row.getCell("amount").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("amount").getStyleAttributes().setLocked(true);
		}else if (amount.signum()!=0){
			row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("price").getStyleAttributes().setLocked(true);
			row.getCell("workLoad").getStyleAttributes().setLocked(true);
		}else{
			row.getCell("amount").getStyleAttributes().setLocked(false);
			row.getCell("price").getStyleAttributes().setLocked(false);
			row.getCell("workLoad").getStyleAttributes().setLocked(false);
			row.getCell("amount").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("price").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("workLoad").getStyleAttributes().setBackground(Color.WHITE);
		}
		if(row.getUserObject() instanceof ContractBillSplitEntryInfo){
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)row.getUserObject();
			if(isProdSplitLeaf(entry)){
				row.getCell("price").setValue(entry.getPrice());	
				row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("price").getStyleAttributes().setLocked(true);
				row.getCell("workLoad").getStyleAttributes().setLocked(true);
			}
		}
	}
	
    protected boolean isProdSplitLeaf(ContractBillSplitEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
	
   private void setStandard(int index){
	   ContractBillSplitEntryInfo curEntry = (ContractBillSplitEntryInfo)kdtSplitEntry.getRow(index).getUserObject();    
    	
    	int level=curEntry.getLevel();	
    	
    	//1. 拆分根据节点，使用拆分类型作为归属标准
		if(level==0){
			//Jelon Dec 13, 2006			
			/*if(curEntry.getSplitType()!=null && curEntry.getSplitType()!=CostSplitType.MANUALSPLIT){
				kdtSplitEntry.getRow(index).getCell("standard").setValue(curEntry.getSplitType());			
			}*/
			if(curEntry.getSplitType()==null || curEntry.getSplitType()==CostSplitTypeEnum.MANUALSPLIT){
				kdtSplitEntry.getRow(index).getCell("standard").setValue("");	
			}else{
				kdtSplitEntry.getRow(index).getCell("standard").setValue(curEntry.getSplitType().toString());			
			}
		}
    	
		//2. 其他拆分结点，使用父级的分摊类型作为归属标准
    	String apptType=null;
    	if(curEntry.getApportionType()!=null){
    		apptType=curEntry.getApportionType().getName();
    	}
    	ContractBillSplitEntryInfo entry=null;
		IRow row=null;
		
		for(int i=index+1; i<kdtSplitEntry.getRowCount(); i++){
			row=kdtSplitEntry.getRow(i);
			entry = (ContractBillSplitEntryInfo)row.getUserObject();
			
						
			if(entry.getLevel()==level+1){	
				if(entry.isIsAddlAccount()){
					if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						row.getCell("standard").setValue(apptType);
					}else{
						row.getCell("standard").setValue("直接分配");
					}
				}else{
					row.getCell("standard").setValue(apptType);
				}
				
				if(!entry.isIsLeaf()){
					setStandard(i);
				}
			}
			else if(entry.getLevel()<=level){
				break;
			}		
			
		}	   					
	}
    
    private Map initDirectMap=new HashMap();
    private void  initDirectAssign(){
    	if(initDirectMap==null||initDirectMap.size()==0){
    		return;
    	}
    	
    	Map projProdMap=new HashMap();
		//产品类型		
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",new HashSet(initDirectMap.values()),CompareType.INCLUDE));
    	//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("IsAccObj", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("productType.*"); 		//使用“*”，用于列表中的数据和分录中的数据匹配
    	view.getSorter().add(new SorterItemInfo("productType.number"));
		try {   	    	    
			CurProjProductEntriesCollection c=CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);
			for(int i=0;i<c.size();i++){
				String prjId=c.get(i).getCurProject().getId().toString();
				CurProjProductEntriesCollection temp=(CurProjProductEntriesCollection)projProdMap.get(prjId);
				if(temp==null){
					temp=new CurProjProductEntriesCollection();
				}
				temp.add(c.get(i));
				projProdMap.put(prjId, temp);
			}
		}catch(BOSException e){
			handUIExceptionAndAbort(e);
		}
    	for(Iterator iter=initDirectMap.keySet().iterator();iter.hasNext();	){
    		Integer idx=(Integer)iter.next();
    		String prjId=(String)initDirectMap.get(idx);
    		if(idx==null){
    			continue;
    		}
    		IRow row=kdtSplitEntry.getRow(idx.intValue());
    		CurProjProductEntriesCollection coll=(CurProjProductEntriesCollection)projProdMap.get(prjId);
			if(coll==null){
				coll=new CurProjProductEntriesCollection();
			}
			ProductTypeCollection collProd=new ProductTypeCollection();
			//空行
			ProductTypeInfo prod=new ProductTypeInfo();
			prod.setName(null);
			//prod.setName("否");
	        collProd.insertObject(-1,prod);		
	        
	        //当前项目全部产品
			for (Iterator iter2 = coll.iterator(); iter2.hasNext();)
			{
				prod = ((CurProjProductEntriesInfo)iter2.next()).getProductType();	        
				if(prod!=null){
					collProd.add(prod);
				}
	        }

			KDComboBox cbo = new KDComboBox();    	    	    	
	        cbo.addItems(collProd.toArray());			
	        row.getCell("product").setEditor(new KDTDefaultCellEditor(cbo));  
    	}
    }

    public void initDirectAssign(IRow row){
    	ContractBillSplitEntryInfo entry;
		entry = (ContractBillSplitEntryInfo)row.getUserObject();
		
		
		boolean isTrue=false;
		isTrue=isProdSplitEnabled(entry);
    	
		if(!isTrue || !entry.isIsLeaf()){
			row.getCell("product").getStyleAttributes().setLocked(true);
			return;
		}else{
			row.getCell("product").getStyleAttributes().setBackground(new Color(0xFFFFFF));
			initDirectMap.put(new Integer(row.getRowIndex()), entry.getCostAccount().getCurProject().getId().toString());
		}
    }
    
	private boolean isProdSplitEnabled(ContractBillSplitEntryInfo entry){		
		boolean isTrue=false;
		if (entry != null && entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && entry.getCostAccount().getCurProject().isIsLeaf()) {
			
			if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				isTrue=true;
			}else if(entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) || entry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
				isTrue=true;
	    	}else if(entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    		if(!entry.isIsLeaf()){
	    			isTrue=true;
	    		}
	    	}			
		}
		
		return isTrue;
	}
	
    protected IRow addEntry(IObjectValue detailData)
    {
        IRow row = kdtSplitEntry.addRow();
        ((ContractBillSplitEntryInfo)detailData).setSeq(row.getRowIndex()+1);
        loadLineFields(kdtSplitEntry, row, detailData);
        afterAddLine(kdtSplitEntry, detailData);
        if(isWholeAgeProject)
        	setProgrammingContractCellF7(row.getRowIndex(),kdtSplitEntry.getCell(row.getRowIndex(),"costAccount.curProject.id").getValue());
        return row;
    }

    protected void kdtSplitEntry_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtSplitEntry_editStopped(e);
		final IRow row = kdtSplitEntry.getRow(e.getRowIndex());
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("amount")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount=FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				ContractBillSplitEntryInfo entry;
				entry = (ContractBillSplitEntryInfo)row.getUserObject();
				String key = getEntrysMapKey(entry);
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...见变量说明
				if (entrysMap.get(key) != null) {//modified by ken_liu...见变量说明
					// modified by zhaoqin on 2013/11/09 start, 录入金额时，NullPointException
					// entry = (FDCSplitBillEntryInfo) entrysMap.get(String.valueOf(entry.getSeq()));
					entry = (ContractBillSplitEntryInfo) entrysMap.get(key);
					// modified by zhaoqin on 2013/11/09 end
				}
				//amount=new BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal=row.getCell("amount").getValue();
				if(cellVal!=null){
					amount=new BigDecimal(cellVal.toString());
				}
				entry.setAmount(amount);
				//拆分比例
				if(entry.getLevel()==0){
					if(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						splitScale=FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), txtamount.getBigDecimalValue(),10,BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
					row.getCell("splitScale").setValue(splitScale);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				//分摊
				/*CostSplitType splitType=entry.getSplitType();
				calcApportionAmount(e.getRowIndex(),splitType);*/				
				apptAmount(e.getRowIndex());	
				
				
				//汇总
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}else if(entry.isIsLeaf() && isAddlAcctLeaf(entry)){
				}
			}
		}
		
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("splitScale")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				ContractBillSplitEntryInfo entry;
				entry = (ContractBillSplitEntryInfo)row.getUserObject();
				
				// modified by zhaoqin on 2013/11/09, 应调用统一的方法
				// String key = entry.getCostAccount().getId().toString() + String.valueOf(entry.getSeq());
				String key = getEntrysMapKey(entry);
								
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...见变量说明
				if (entrysMap.get(key) != null) {//modified by ken_liu...见变量说明
					entry = (ContractBillSplitEntryInfo) entrysMap.get(key);
				}
				Object cellVal=row.getCell("splitScale").getValue();
				if(cellVal!=null){
					splitScale=FDCHelper.toBigDecimal(cellVal);
				}
				if(entry.getLevel()==0){
					entry.setSplitScale(splitScale);
					amount = FDCHelper.divide(FDCHelper.multiply(txtamount.getBigDecimalValue(), splitScale),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
					row.getCell("amount").setValue(amount);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				
				apptAmount(e.getRowIndex());				
				//汇总
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}
			}
		}
		
		//附加科目汇总
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("amount")){
			BigDecimal value=UIRuleUtil.getBigDecimal(e.getValue());
			BigDecimal oldValue=e.getOldValue()==null?FDCHelper.ZERO:UIRuleUtil.getBigDecimal(e.getOldValue());
			BigDecimal changeAmt=value.subtract(oldValue);
			if (changeAmt.compareTo(FDCHelper.ZERO)!=0){
				ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)row.getUserObject();
				if(entry.isIsLeaf()&&entry.isIsAddlAccount()){
					totAddlAcct(entry.getCostAccount().getCurProject(), entry.getCostAccount(), changeAmt, e.getRowIndex());
					entry.setApportionValue(value);
					row.getCell("apportionValue").setValue(value);
				}
			}
		}
		//附加产品
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("product")){
			ContractBillSplitEntryInfo entry = (ContractBillSplitEntryInfo)row.getUserObject();
			Object product = row.getCell("product").getValue();
			//if(product!=null&&product instanceof ProductTypeInfo && ((ProductTypeInfo)product).getId()!=null ){
			if(product!=null&& product.toString()!=null ){
				entry.setProduct((ProductTypeInfo)product);
			}else{
				entry.setProduct(null);
			}
		}
		//加条件，否则末级拆分点击成本科目时归属金额可以录入，导致金额错误
		if(editData.getBoolean("isMeasureSplit")){
			handleMeasureCalc(e, row);
		}
	}
    
    protected boolean isAddlAcctLeaf(ContractBillSplitEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsAddlAccount() 
    			&& entry.getCostAccount()!=null && entry.getCostAccount().isIsLeaf()
    			&& entry.getCostAccount().getCurProject()!=null && entry.getCostAccount().getCurProject().isIsLeaf()
    			&& !isProdSplitLeaf(entry)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    
    protected void totAddlAcct(CurProjectInfo prj,CostAccountInfo acct,BigDecimal amount,int end) {
		IRow row=null;
    	CurProjectInfo curPrj=null;
		CostAccountInfo curAcct=null;
		BigDecimal sum=null; 
    	for (int i = end-1; i >=0 ; i--) {
			row = kdtSplitEntry.getRow(i);
			ContractBillSplitEntryInfo entry=(ContractBillSplitEntryInfo)(row.getUserObject());
			if(entry.getLevel()==0){
				break;
			}
			if(!entry.isIsAddlAccount()){
				continue;
			}
			
			curAcct=entry.getCostAccount();
			curPrj=entry.getCostAccount().getCurProject();
			//设置上级工程项目的相同科目,注:用长编码来判断
			if(prj.getParent()!=null&&prj.getParent().getId().equals(curPrj.getId())
					&&acct.getLongNumber().equals(curAcct.getLongNumber())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
			}
//			设置相同工程项目的上级科目,并递归处理
			if(prj.getId().equals(curPrj.getId())
					&&acct.getParent()!=null
					&&acct.getParent().getId().equals(curAcct.getId())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
				
				totAddlAcct(curPrj,curAcct,amount,i);
			}
		}

	}
    
	protected void handleMeasureCalc(KDTEditEvent e, final IRow row) {
		//量价汇总
		if (e.getColIndex()==kdtSplitEntry.getColumnIndex("workLoad")
			||e.getColIndex()==kdtSplitEntry.getColumnIndex("price")){
			FDCSplitBillEntryInfo entry= (FDCSplitBillEntryInfo)row.getUserObject();
			BigDecimal oldAmt=entry.getAmount();
			BigDecimal amount = FDCHelper.multiply(row.getCell("workLoad").getValue(), row.getCell("price").getValue());
			row.getCell("amount").setValue(amount);
			entry.setWorkLoad((BigDecimal)row.getCell("workLoad").getValue());
			entry.setPrice((BigDecimal)row.getCell("price").getValue());
			entry.setAmount(amount);
			try{
				kdtSplitEntry_editStopped(new KDTEditEvent(e.getSource(), oldAmt, amount, 
					row.getRowIndex(), row.getCell("amount").getColumnIndex(),false,1));
			}catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
				handUIExceptionAndAbort(e1);
			}
			calcAmount(0);
		}

		setMeasureCtrl(row);
	}
	
	private void loadTable(KDTable table,String sql){
		try {
			table.checkParsed();
			table.removeColumns();
			table.removeRows();
			IFMIsqlFacade isql = FMIsqlFacadeFactory.getRemoteInstance();
			IRowSet executeQuery = isql.executeQuery(sql, null);
			String nsql = sql;
			if(sql.startsWith("/*dialect*/"))
				nsql = sql.substring("/*dialect*/".length());
			SQLStmtInfo sqlStmtInfo;
			try
			{
				Lexer lexer = new Lexer(nsql);
				TokenList _tokList = new TokenList(lexer);
				com.kingdee.bos.sql.dom.stmt.SqlStmt sqlStmt = (new SqlStmtParser(_tokList)).stmt();
				sqlStmtInfo = FMIsqlUIHandler.getSQLStmtInfo(sqlStmt);
			}
			catch(ParserException pe)
			{
				sqlStmtInfo = new SQLStmtInfo();
				sqlStmtInfo.canAudoUpdate = false;
				sqlStmtInfo.isSelect = false;
			}
			FMIsqlUI.fillData(table,executeQuery ,sqlStmtInfo);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void runCalAmount(IRow row,int i) throws Exception{
		KDTEditEvent event = new KDTEditEvent(kdtSplitEntry, null, null, i,kdtSplitEntry.getColumnIndex("splitScale"), true, 1);
    	event.setValue(row.getCell("splitScale").getValue());
    	event.setOldValue("1.2222");
    	kdtSplitEntry_editStopped(event);
	}
}
