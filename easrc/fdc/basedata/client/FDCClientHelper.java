/*
 * @(#)FDCClientHelper.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JToolBar.Separator;
import javax.swing.plaf.basic.BasicPopupMenuUI;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.BasicFormattedTextField;
import com.kingdee.bos.ctrl.swing.IKDComponent;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDPasswordField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationEntryCollection;
import com.kingdee.bos.metadata.bot.BOTRelationEntryInfo;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.DataType;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.SelectorInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIDialog;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessDefInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.define.ProcessDef;
import com.kingdee.bos.workflow.monitor.client.BasicShowWfDefinePanel;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.multiapprove.client.DesignateNextActivityPerformerUI;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.PermissionException;
import com.kingdee.eas.base.permission.service.UsbKeyPermissionService;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.base.uiframe.client.UINewTab;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.assistant.IAccountBank;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.IExchangeRate;
import com.kingdee.eas.basedata.assistant.PeriodCollection;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.INewOrgViewFacade;
import com.kingdee.eas.basedata.org.NewOrgUnitHelper;
import com.kingdee.eas.basedata.org.NewOrgViewFacadeFactory;
import com.kingdee.eas.basedata.org.NewOrgViewHelper;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgTreeF7PromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.MeasureIndexCollection;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fi.cas.IJournal;
import com.kingdee.eas.fi.cas.JournalFactory;
import com.kingdee.eas.fi.cas.JournalInfo;
import com.kingdee.eas.fi.cas.JournalTypeEnum;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.IVoucherEntry;
import com.kingdee.eas.fi.gl.VoucherCollection;
import com.kingdee.eas.fi.gl.VoucherEntryCollection;
import com.kingdee.eas.fi.gl.VoucherEntryFactory;
import com.kingdee.eas.fi.gl.VoucherEntryInfo;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.EJBAccessFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.fm.common.FMLoginModel;
import com.kingdee.eas.fm.common.IContextHelper;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.fm.common.client.BizTypeChooseUI;
import com.kingdee.eas.fm.common.client.ExcelFileFilter;
import com.kingdee.eas.fm.common.client.FMClientVerifyHelper;
import com.kingdee.eas.fm.nt.client.IBotEditUI;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.FrameWorkUtils;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.client.CoreBillEditUI;
import com.kingdee.eas.framework.client.CoreBillListUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.ma.bg.BgCycleTypeEnum;
import com.kingdee.eas.ma.bg.BgEntryCollection;
import com.kingdee.eas.ma.bg.BgEntryInfo;
import com.kingdee.eas.ma.bg.BgItemInfo;
import com.kingdee.eas.ma.bg.BgPeriodNode;
import com.kingdee.eas.ma.bg.BgTypeInfo;
import com.kingdee.eas.port.pm.utils.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:房地产客户端辅助类
 * 
 * @author liupd date:2006-10-19
 *         <p>
 * @version EAS5.2
 */
public class FDCClientHelper {
	
	//图标常量
	/** 图标 － 审批 */
	public static final Icon ICON_AUDIT = EASResource.getIcon("imgTbtn_audit");

	/** 图标 － 反审批 */
	public static final Icon ICON_UNAUDIT = EASResource.getIcon("imgTbtn_unaudit");

	/** 图标 － 计算器 */
	public static final Icon ICON_CALCULATOR = EASResource.getIcon("imgTbtn_counter");

	/** 图标 － 保存 */
	public static final Icon ICON_SAVE = EASResource.getIcon("imgTbtn_save");

	/** 图标 － 提交 */
	public static final Icon ICON_SUBMIT = EASResource.getIcon("imgTbtn_submit");

	/** 图标 － 第一 */
	public static final Icon ICON_FIRST = EASResource.getIcon("imgTbtn_first");

	/** 图标 － 上一 */
	public static final Icon ICON_PREVIOUS = EASResource.getIcon("imgTbtn_previous");

	/** 图标 － 下一 */
	public static final Icon ICON_NEXT = EASResource.getIcon("imgTbtn_next");

	/** 图标 － 最后 */
	public static final Icon ICON_LAST = EASResource.getIcon("imgTbtn_last");

	/** 图标 － 刷新 */
	public static final Icon ICON_REFRESH = EASResource.getIcon("imgTbtn_refresh");

	/** 图标 － 自动计算 */
	public static final Icon ICON_AUTOCOUNT = EASResource.getIcon("imgTbtn_autocount");

	/** 图标 － 拆分 */
	public static final Icon ICON_SPLIT = EASResource.getIcon("imgTbtn_split");

	/** 图标 － 移动 */
	public static final Icon ICON_MOVE = EASResource.getIcon("imgTbtn_move");
	
	private static final int NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL = KDFormattedTextField.BIGDECIMAL_TYPE;

	public static final int NUMBERTEXTFIELD_ALIGNMENT = JTextField.RIGHT;

	public final static Color KDTABLE_TOTAL_BG_COLOR = new Color(0xF6F6BF);
	
	public final static Color KDTABLE_COMMON_BG_COLOR = new Color(0xFCFBDF);

	public final static Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(0xF5F5E6);
	
	public final static Color KDTABLE_DISABLE_BG_COLOR =	new Color(0xE8E8E3);

	// 格式化精度设置
	public final static String KDTABLE_NUMBER_FTM = "%-.2n";

	// public final static String kdTablePercentFtm = "%{0.00%}f";
	public final static String KDTABLE_PERCENT_FTM = "%r{0.00}p";

	public final static String KDTABLE_DATE_FMT = "yyyy-MM-dd";

	public final static String ACTUAL_DIGIT_FMT = "#,##0.###########";

	// 缓存的币别信息
	public final static HashMap mapPrecOfCurrency = new HashMap(32);
	
	//缓存的汇率精度信息
	protected static Map mapPrecOfExrate = new HashMap(32);

	// 小计行颜色
	public static final int SUBTOTAL = 0xF5F5E6;

	// 总计行颜色
	public static final int TOTAL = 0xF6F6B6;

	// 差异记录颜色
	public static final int PASTRECORD = 0xFFEA67;

	/** 公共资源路径 */
	public static final String RES = "com.kingdee.eas.fm.common.client.FMCommonClientResource";

	/** 默认汇率币别 */
	public static final int DEFAULT_EXCHANGERATE_PREC = 3;

	public static String getNumberFtm(BOSUuid currencyId) {
		return "%r-[ ]0." + getPrecOfCurrency(currencyId) + "n";
	}

	public static String getNumberFtm(int precision) {
		return "%r-[ ]0." + precision + "n";
	}

	public static String getNumberFtm() {
		return "%r-[ ]{###,###,###.##}15.2n";
	}

	public static int getLocalCurPre() {
		int pre = 0;
		for (Iterator iter = mapPrecOfCurrency.values().iterator(); iter
				.hasNext();) {
			Integer currencyPre = (Integer) iter.next();
			if (currencyPre.intValue() > pre) {
				pre = currencyPre.intValue();
			}
		}
		return pre;
	}
	
	public static int getExRatePrecOfCurrency(String currencyId) {
		if (mapPrecOfExrate.size() == 0) {
			//setPrecOfCurrenty(mapPrecOfCurrency);
			CompanyOrgUnitInfo com = SysContext.getSysContext().getCurrentFIUnit();
			String exTable = com.getBaseExchangeTable().getId().toString();
			String localCurId = com.getBaseCurrency().getId().toString();
			try {
				mapPrecOfExrate = (Map)ActionCache.get("FDCBillListUIHandler.mapPrecOfExrate");
				if(mapPrecOfExrate==null){
//					mapPrecOfExrate = ContractFacadeFactory.getRemoteInstance().getExRatePre(exTable,localCurId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!mapPrecOfExrate.containsKey(currencyId)) {
			return 2;
		}
		
		return ((Integer) mapPrecOfExrate.get(currencyId)).intValue();
	}

	public static int getPrecOfCurrency(BOSUuid currencyId) {
		if (mapPrecOfCurrency.size() == 0) {
			setPrecOfCurrenty(mapPrecOfCurrency);
		}

		if (!mapPrecOfCurrency.containsKey(currencyId)) {
			CurrencyInfo info = getCurrencyInfo(currencyId);
			if (info != null) {
				mapPrecOfCurrency.put(info.getId(), new Integer(info
						.getPrecision()));
				return info.getPrecision();
			}
			return 10;
		}

		return ((Integer) mapPrecOfCurrency.get(currencyId)).intValue();
	}

	/**
	 * 根据币别ID获取币别对象 <p>
	 * 将private开放为public，供外部使用。 Modified by Owen_wen 2010-09-15
	 * @param id 币别ID
	 * @return
	 */
	public static CurrencyInfo getCurrencyInfo(BOSUuid id) {
		IObjectPK pk = new ObjectUuidPK(id);

		ICurrency iCurrency;
		CurrencyInfo info = null;
		try {
			iCurrency = CurrencyFactory.getRemoteInstance();
			info = iCurrency.getCurrencyInfo(pk);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		return info;
	}

	private static void setPrecOfCurrenty(HashMap map) {
		String str = "select fid,fprecision from t_bd_currency";
		ISQLExecutor sqlExe = SQLExecutorFactory.getRemoteInstance(str);
		IRowSet rs = null;

		try {
			rs = sqlExe.executeSQL();

			while (rs.next()) {
				map.put(BOSUuid.read(rs.getString(1)),
						new Integer(rs.getInt(2)));
			}
		} catch (BOSException bose) {
			return;
		} catch (SQLException sqle) {
			return;
		}
	}
	

	/**
	 * 获取小数格式
	 * 
	 * @param scale
	 *            小数位数
	 * @param needkilobit
	 *            是否显示千分位
	 * @return "%r{#,##0.00}f"
	 */
	public static String getNumberFormat(int scale, boolean needkilobit) {
		StringBuffer sb = new StringBuffer("%r{");
		if (needkilobit) {
			sb.append("#,##");
		}
		sb.append("0");
		if (scale > 0) {
			sb.append(".");
			for (int i = 0; i < scale; i++) {
				sb.append("0");
			}
		}
		sb.append("}f");
		return sb.toString();
	}

	/**
	 * 初始化组织选择框
	 * 
	 * @param bgTypeInfo
	 */
	public static void initOrgF7(BgTypeInfo bgTypeInfo,
			KDBizPromptBox bizPromptOrgUnit) {
		initOrgF7(bgTypeInfo, bizPromptOrgUnit, false);
	}

	/**
	 * 初始化组织选择框
	 * 
	 * @param bgTypeInfo
	 */
	public static void initOrgF7(BgTypeInfo bgTypeInfo,
			KDBizPromptBox bizPromptOrgUnit, boolean isMultiChoose) {
		// 清空预算组织F7
		bizPromptOrgUnit.setData(null);
		bizPromptOrgUnit.setEnabled(true);
		bizPromptOrgUnit.setEnabledMultiSelection(isMultiChoose);

		// TODO 未完成
		OrgType[] orgType = { OrgType.Company };
		OrgTreeF7PromptBox box = new OrgTreeF7PromptBox(null, orgType);

		bizPromptOrgUnit.setEditFormat("$name$");
		bizPromptOrgUnit.setDisplayFormat("$name$");
		bizPromptOrgUnit.setSelector(box);
		box.show();
	}

	/**
	 * 生成期间树 根据预算实例的分录集合
	 */
	public static DefaultKingdeeTreeNode getPeriodTreeRoot(
			BgEntryCollection bgEntries) {
		// TODO 确认已经排序良好
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();

		Iterator iter = bgEntries.iterator();

		while (iter.hasNext()) {
			BgEntryInfo bgEntryInfo = (BgEntryInfo) iter.next();
		}

		return root;
	}

	// /**
	// * 根据起点期间和终点期间初始化期间树的根
	// * @param startBgPeriod
	// * @param endBgPeriod
	// * @return
	// * @deprecated
	// */
	// public static DefaultKingdeeTreeNode getPeriodTreeRoot(PeriodInfo
	// periodInfoFrom,
	// PeriodInfo periodInfoTo) {
	// DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();
	// DefaultKingdeeTreeNode yearRoot = null;
	// DefaultKingdeeTreeNode seasonRoot = null;
	//
	// // 产生预算期间节点数组
	// Vector vector = new BgPeriodRange(periodInfoFrom,
	// periodInfoTo).getPeriodNodesInRange();
	//
	// for (int i = 0; i < vector.size(); i++) {
	// BgPeriodNode tmpBgPeriodNode = (BgPeriodNode) vector.get(i);
	// DefaultKingdeeTreeNode tmpTreeNode = new
	// DefaultKingdeeTreeNode(tmpBgPeriodNode);
	//
	// if (tmpBgPeriodNode.getBgCycle().equals(BgCycleTypeEnum.Year)) {
	// root.add(tmpTreeNode);
	// yearRoot = tmpTreeNode;
	// } else if (tmpBgPeriodNode.getBgCycle().equals(BgCycleTypeEnum.Season)) {
	// yearRoot.add(tmpTreeNode);
	// seasonRoot = tmpTreeNode;
	// } else if (tmpBgPeriodNode.getBgCycle().equals(BgCycleTypeEnum.Period)) {
	// seasonRoot.add(tmpTreeNode);
	// }
	// }
	//
	// return root;
	// }

	/**
	 * @param vector
	 * @return
	 */
	public static DefaultKingdeeTreeNode getPeriodTreeRoot(Vector vector) {
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();
		DefaultKingdeeTreeNode yearRoot = null;
		DefaultKingdeeTreeNode seasonRoot = null;

		for (int i = 0; i < vector.size(); i++) {
			BgPeriodNode tmpBgPeriodNode = (BgPeriodNode) vector.get(i);
			DefaultKingdeeTreeNode tmpTreeNode = new DefaultKingdeeTreeNode(
					tmpBgPeriodNode);

			if (tmpBgPeriodNode.getBgCycle().equals(BgCycleTypeEnum.Year)) {
				root.add(tmpTreeNode);
				yearRoot = tmpTreeNode;
			} else if (tmpBgPeriodNode.getBgCycle().equals(
					BgCycleTypeEnum.Season)) {
				yearRoot.add(tmpTreeNode);
				seasonRoot = tmpTreeNode;
			} else if (tmpBgPeriodNode.getBgCycle().equals(
					BgCycleTypeEnum.Period)) {
				seasonRoot.add(tmpTreeNode);
			}
		}

		return root;
	}

	/**
	 * 得到当前选中的树节点的期间节点
	 * 
	 * @return
	 */
	public static BgPeriodNode getCurrentBgPeriodNode(KDTree treeBgPeriod) {
		// 得到用户当前选中的节点,并得到其对应的预算期间
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeBgPeriod
				.getLastSelectedPathComponent();

		if (node == null) {
			return null;
		}

		if (node.getUserObject() == null) {
			return null;
		}

		return (BgPeriodNode) node.getUserObject();
	}

	/**
	 * 得到当前的树的最末期间节点
	 * 
	 * @return
	 */
	public static BgPeriodNode getEndBgPeriodNode(KDTree treeBgPeriod) {
		// 得到用户当前选中的节点,并得到其对应的预算期间
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeBgPeriod
				.getPathForRow(treeBgPeriod.getRowCount() - 1)
				.getLastPathComponent();

		if (node == null) {
			return null;
		}

		if (node.getUserObject() == null) {
			return null;
		}

		return (BgPeriodNode) node.getUserObject();
	}

	/**
	 * 根据预算模板生成样表
	 * 
	 */

	// public static void initBgFormTable(BgTemplateInfo bgTpInfo, KDTable
	// kdTable)
	// {
	// initBgFormTable_distribute(bgTpInfo,kdTable);
	// // 表头融合模式
	// kdTable.getHeadMergeManager().mergeBlock(0, 0, kdTable.getHeadRowCount()
	// - 1,
	// kdTable.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
	// }
	// /**
	// * 根据预算模板生成样表(分解用)
	// *
	// */
	// public static void initBgFormTable_distribute(BgTemplateInfo bgTpInfo,
	// KDTable kdTable) {
	// // 清楚表的设置
	// clearTable(kdTable);
	// System.out.println("id:" + bgTpInfo.getId());
	// // 新增两行表头
	// IRow mainHeadRow = kdTable.addHeadRow();
	// IRow multiCurrencyHeadRow = null;
	//
	// // 初始化预算项目类和预算项目行
	// initBgItemColumnAndRow(bgTpInfo, kdTable);
	//
	// // 设置预算要素列
	// int columnCursor = kdTable.getColumnCount();
	// //Iterator bgElementIter = bgTpInfo.getBgTpColumns().iterator();
	// BgTemplateColumnCollection tpCols = bgTpInfo.getBgTpColumns();
	//		
	// // 初始化币别信息
	// boolean isMultiCurrency = bgTpInfo.getRefCurrencies().size() > 1;
	// CurrencyInfo soloCurrency = null;
	//
	// if (!isMultiCurrency) {
	// if (bgTpInfo.getRefCurrencies().size() > 0) {
	// //有可能没有币种
	// soloCurrency = bgTpInfo.getRefCurrencies().get(0).getCurrency();
	// }
	// }
	//
	// //while (bgElementIter.hasNext()) {
	// for ( int i = 0, size = tpCols.size(); i < size; i++)
	// {
	// //BgTemplateColumnInfo bgTpColInfo = (BgTemplateColumnInfo)
	// bgElementIter.next();
	// BgTemplateColumnInfo bgTpColInfo = tpCols.get(i);
	// BgElementInfo bgElementInfo = bgTpColInfo.getBgElement();
	//
	// // 启用多币别栏
	// if (bgElementInfo.getDataType().equals(BgDataTypeEnum.Amount) &&
	// isMultiCurrency) {
	// // 如果还没有新增多币别栏,新增一个
	// if (multiCurrencyHeadRow == null) {
	// multiCurrencyHeadRow = kdTable.addHeadRow();
	// }
	//
	// Iterator currencyIter = bgTpInfo.getRefCurrencies().iterator();
	//
	// while (currencyIter.hasNext()) {
	// ReferencedCurrencyInfo refCyInfo = (ReferencedCurrencyInfo)
	// currencyIter.next();
	// CurrencyInfo cyInfo = refCyInfo.getCurrency();
	//
	// IColumn ic = kdTable.addColumn(columnCursor);
	//
	// // 对这个币别设定相应的精度
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm(cyInfo.getId()));
	//
	// //设置对齐模式
	// ic.getStyleAttributes().getAlignment().setHorzionAlign(Alignment.RIGHT);
	//
	// ic.setMergeable(false);
	// ic.setKey(FMHelper.getBgElementKey(bgElementInfo, cyInfo));
	// mainHeadRow.getCell(columnCursor).setValue(bgElementInfo.getName());
	// multiCurrencyHeadRow.getCell(columnCursor).setValue(cyInfo.getName());
	// columnCursor++;
	// }
	// }
	// // 不启用多币别栏
	// else {
	// IColumn ic = kdTable.addColumn(columnCursor);
	//
	// // 对这个币别设定相应的精度
	// if (soloCurrency != null) {
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm(soloCurrency.getId()));
	// }else{
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm());
	// }
	//
	// // 设置对齐模式
	// ic.getStyleAttributes().getAlignment().setHorzionAlign(Alignment.RIGHT);
	// ic.setMergeable(false);
	// ic.setKey(bgElementInfo.getNumber());
	// mainHeadRow.getCell(columnCursor).setValue(bgElementInfo.getName());
	// columnCursor++;
	// }
	// }
	//
	// // 如果出现了多币别栏,处理一下其他表头的融合
	// if (multiCurrencyHeadRow != null) {
	// for (int i = 0; i < kdTable.getColumnCount(); i++) {
	// // 应该不可能是空
	// if (multiCurrencyHeadRow.getCell(i) == null) {
	// continue;
	// }
	//
	// if (multiCurrencyHeadRow.getCell(i).getValue() == null) {
	// multiCurrencyHeadRow.getCell(i).setValue(mainHeadRow.getCell(i).getValue());
	// }
	// }
	// }
	// }
	/**
	 * @param kdTable
	 */
	public static void clearTable(KDTable kdTable) {
		kdTable.removeHeadRows();
		kdTable.removeRows();
		kdTable.removeColumns();
	}

	// /**
	// *
	// * @param bgTpInfo
	// * @param kdTable
	// */
	// public static void initBgItemColumnAndRow(BgTemplateInfo bgTpInfo,
	// KDTable
	// kdTable) {
	// // 获取用户可视的维度集合
	// ReferencedDimensionCollection visibleRefDims = bgTpInfo.getVisibleDims();
	// Iterator visibleRefDimsIter = visibleRefDims.iterator();
	//
	// int columnCursor = 0;
	//
	// while (visibleRefDimsIter.hasNext()) {
	// //获得此列对应的维度
	// ReferencedDimensionInfo refDimInfo = (ReferencedDimensionInfo)
	// visibleRefDimsIter.next();
	// String refName = refDimInfo.getBgDimension().getName();
	//
	// // 新增一列
	// IColumn ic = null;
	//
	// if (kdTable.getColumnIndex(refName) == -1) { //不存在将增加的列
	// ic = kdTable.addColumn(columnCursor);
	// ic.setKey(refName);
	// } else { //该列存在
	// ic = kdTable.getColumn(refName);
	// }
	//
	// // 设置表头名称
	// kdTable.getHeadRow(0).getCell(columnCursor).setValue(refDimInfo.getBgDimension()
	// .getAlias(SysContext.getSysContext()
	// .getLocale()));
	//
	// // 预算项目的格子不可以编辑
	// ic.getStyleAttributes().getProtection().setLocked(true);
	//
	// // 列游标加一
	// columnCursor++;
	// }
	//
	// // 根据维度信息判定有几列是实现预算项目用的
	// int itemColumnsNum = visibleRefDims.size();
	//
	// // 填充数据
	// Iterator iter = bgTpInfo.getBgTpRows().iterator();
	//
	// while (iter.hasNext()) {
	// // 从迭代器中获取info
	// BgTemplateRowInfo bgTpRowInfo = (BgTemplateRowInfo) iter.next();
	//
	// // 增加一行
	// IRow ir = kdTable.addRow();
	//
	// // userObject是一个字符串
	// ir.setUserObject(FMHelper.getBgItemsKey(
	// new Object[] {
	// bgTpRowInfo.getBgItem1(), bgTpRowInfo.getBgItem2(),
	// bgTpRowInfo.getBgItem3(),
	// bgTpRowInfo.getBgItem4(), bgTpRowInfo.getBgItem5(),
	// bgTpRowInfo.getBgItem6()
	// }));
	//
	// // 获取表头行的单元，并设置单元的值
	// ICell cell;
	//
	// // 设置预算项目列信息
	// for (int i = 0; i < itemColumnsNum; i++) {
	// cell = ir.getCell(i);
	//
	// // 获取bgItem的key，使用getValue的方式去寻值
	// String valueKey = "bgItem" + String.valueOf(i + 1);
	// BgItemInfo bgItemInfo = (BgItemInfo) bgTpRowInfo.get(valueKey);
	//
	// if (bgItemInfo == null) {
	// cell.setValue(null);
	// } else {
	// cell.setValue(bgItemInfo);
	// }
	// }
	// }
	//
	// // 对第一列做一项工作,就是树型化
	// if (itemColumnsNum == 1) {
	// treeFormTable(kdTable);
	// }
	//
	// // 需要融合
	// kdTable.getMergeManager().mergeBlock(0, 0, kdTable.getRowCount() - 1,
	// itemColumnsNum - 1,
	// KDTMergeManager.FREE_ROW_MERGE);
	// }

	/**
	 * 设置树
	 */
	public static void treeFormTable(KDTable kdTable) {
		kdTable.getTreeColumn().setOrientation(KDTStyleConstants.UP);

		int maxDepth = 0;

		for (int i = 0; i < kdTable.getRowCount(); i++) {
			BgItemInfo bgItemInfo = (BgItemInfo) kdTable.getRow(i).getCell(0)
					.getValue();

			if (bgItemInfo == null) {
				return;
			}

			if (bgItemInfo.getLongNumber() != null) {
				int depth = StringUtils.occurencesOf(
						bgItemInfo.getItemNumber(), '.');
				kdTable.getRow(i).setTreeLevel(depth);

				if (maxDepth < depth) {
					maxDepth = depth;
				}
			}
		}

		kdTable.getTreeColumn().setDepth(maxDepth);
	}

	// /*
	// * 得到默认的组织单位
	// */
	// public static CompanyOrgUnitInfo getCompanyOrgUnitInfo() {
	// CompanyOrgUnitInfo companyOrgUnitInfo = null;
	//
	// try {
	// Object comobj =
	// SysContext.getSysContext().getProperty(SysContextConstant.DEF_COMPANY);
	// ObjectUuidPK compk = new ObjectUuidPK(BOSUuid.read(
	// comobj.toString()));
	// ICompanyOrgUnit iCompanyOrgUint =
	// CompanyOrgUnitFactory.getRemoteInstance();
	// SelectorItemCollection sic = new SelectorItemCollection();
	//
	// companyOrgUnitInfo = iCompanyOrgUint.getCompanyOrgUnitInfo(compk,
	// sic);
	// } catch (EASBizException e) {
	// e.printStackTrace();
	// } catch (BOSException e) {
	// e.printStackTrace();
	// }
	//
	// return companyOrgUnitInfo;
	// }

	/**
	 * 设置选择
	 * 
	 * @param cbo
	 * @param anObject
	 */
	static public void setSelectObject(KDComboBox cbo, Object anObject) {
		if (CoreBaseInfo.class.isInstance(anObject)) {
			CoreBaseInfo newObject = (CoreBaseInfo) anObject;

			for (int i = 0; i < cbo.getItemCount(); i++) {
				Object obj = cbo.getItemAt(i);
				// yangzk 2006-3-9增加类型判断
				if (obj instanceof CoreBaseInfo) {
					CoreBaseInfo info = (CoreBaseInfo) cbo.getItemAt(i);
					if (obj == null) {
						continue;
					}
					if (newObject.getId().equals(info.getId())) {
						cbo.setSelectedIndex(i);
						break;
					}
				}
			}
		} else {
			cbo.setSelectedItem(anObject);
		}
	}

	//
	// /**
	// * 根据预算报表模板初始化预算表格
	// *
	// * @param bgRptTpInfo
	// * @param kdtBgFormData
	// */
	// public static void initBgRptFormTable(BgRptTemplateInfo bgRptTpInfo,
	// KDTable kdTable) {
	// // 清除原有的设置
	// clearTable(kdTable);
	//
	// // 新增两行表头
	// IRow mainHeadRow = kdTable.addHeadRow();
	//
	// // 初始化预算项目类和预算项目行
	// initBgRptItemColumnAndRow(bgRptTpInfo, kdTable);
	//
	// // 设置预算要素列
	// int columnCursor = kdTable.getColumnCount();
	// Iterator bgElementIter = bgRptTpInfo.getBgTemplate().getBgType()
	// .getElements().iterator();
	//
	// CurrencyInfo soloCurrency = null;
	//
	// if (bgRptTpInfo.getBgTemplate().getRefCurrencies().size() == 1) {
	// soloCurrency = bgRptTpInfo.getBgTemplate().getRefCurrencies().get(0)
	// .getCurrency();
	// }
	//
	// while (bgElementIter.hasNext()) {
	// BgElementInfo bgElementInfo = (BgElementInfo) bgElementIter.next();
	//
	// // 报表都是单一币别
	// IColumn ic = kdTable.addColumn(columnCursor);
	//
	// if (soloCurrency != null) {
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm(
	// soloCurrency.getId()));
	// }
	//
	// ic.getStyleAttributes().getAlignment().setHorzionAlign(Alignment.RIGHT);
	// ic.setKey(bgElementInfo.getNumber());
	// mainHeadRow.getCell(columnCursor).setValue(bgElementInfo.getName());
	// columnCursor++;
	// }
	// }

	// /**
	// * 初始化预算报表的报表项目
	// *
	// * @param bgRptTpInfo
	// * @param kdTable
	// */
	// private static void initBgRptItemColumnAndRow(
	// BgRptTemplateInfo bgRptTpInfo, KDTable kdTable) {
	// if ((bgRptTpInfo == null) || (bgRptTpInfo.getBgRptTpRows() == null)) {
	// return;
	// }
	//
	// // 新增一列
	// int columnCursor = 0;
	// IColumn ic = kdTable.addColumn(columnCursor);
	// ic.setKey("");
	//
	// // TODO 设置表头名称
	// kdTable.getHeadRow(0).getCell(columnCursor).setValue("报表项目");
	//
	// // 填充数据
	// Iterator iter = bgRptTpInfo.getBgRptTpRows().iterator();
	//
	// while (iter.hasNext()) {
	// // 从迭代器中获取info
	// BgRptTemplateRowInfo bgRptTpRowInfo = (BgRptTemplateRowInfo) iter.next();
	//
	// // 增加一行
	// IRow ir = kdTable.addRow();
	//
	// // 将预算报表行对象放入
	// ir.setUserObject(bgRptTpRowInfo);
	//
	// // 获取表头行的单元，并设置单元的值
	// ICell cell = ir.getCell(0);
	//
	// if (bgRptTpRowInfo.getPrefix() == null) {
	// cell.setValue(bgRptTpRowInfo.getName());
	// } else {
	// cell.setValue(bgRptTpRowInfo.getPrefix() +
	// bgRptTpRowInfo.getName());
	// }
	//
	// cell.setUserObject(bgRptTpRowInfo);
	// }
	//
	// // 这些预算项目,都是不可以编辑的
	// kdTable.getColumn(0).getStyleAttributes().getProtection().setLocked(true);
	// }

	/**
	 * 获取表格的多行选择时候,所有行的数组
	 * 
	 * @param table
	 * @return
	 */
	public static Set getSelectedRows(KDTable table) {
		ArrayList arrayList = table.getSelectManager().getBlocks();

		TreeSet set = new TreeSet();

		for (int i = 0, size = arrayList.size(); i < size; i++) {
			// 获取一个选择块
			KDTBlock block = (KDTBlock) arrayList.get(i);

			int top = block.getTop();
			int bottom = block.getBottom();

			for (int j = top; j <= bottom; j++) {
				Integer theInteger = new Integer(j);
				set.add(theInteger);
			}
		}

		return set;
	}

	/**
	 * 获取选择的行,如果是多选,则返回第一行
	 * 
	 * @param table
	 * @return
	 */
	public static int getSelectedRow(KDTable table) {

		KDTSelectBlock selectBlock = table.getSelectManager().get();
		return selectBlock.getTop();
	}

	/**
	 * 校验输入的数字是否过大
	 */
	public static boolean verifyTooBigger(String value, double target) {
		boolean result = false;

		try {
			double big = Double.parseDouble(value);

			if (big >= target) {
				result = true;
			}
		} catch (NumberFormatException e) {
			result = true;
		}

		return result;
	}

	/**
	 * 校验输入的是否为数字
	 */
	public static boolean verifyIsNumber(String value) {
		boolean result = true;

		try {
			new BigDecimal(value);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;
	}

	/**
	 * 校验输入的是否为整数
	 */
	public static boolean verifyIsInteger(String value) {
		boolean result = true;

		try {
			int big = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;
	}

	/**
	 * 校验输入的是否为正数（不包括零）
	 */
	public static boolean verifyIsPlus(String value)
			throws NumberFormatException {
		boolean result = true;
		float big = Float.parseFloat(value);

		if (big <= 0) {
			result = false;
		}

		return result;
	}

	// /**
	// * 校验输入的是否为正数（包括零）
	// */
	// public static boolean verifyIsPlus1(String value) throws
	// NumberFormatException {
	// boolean result = true;
	// float big = Float.parseFloat(value);
	//
	// if (big < 0) {
	// result = false;
	// }
	//
	// return result;
	// }

	/**
	 * 获取DecimalFormat
	 */
	public static DecimalFormat getDecimalFormat() {
		DecimalFormat format = new DecimalFormat("#.##");
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);

		return format;
	}

	/**
	 * 获取DecimalFormat
	 */
	public static DecimalFormat getDecimalFormat(int length) {
		DecimalFormat format = new DecimalFormat("#.##");

		if (length == 3) {
			format = new DecimalFormat("#.###");
		} else if (length == 4) {
			format = new DecimalFormat("#.####");
		}

		format.setMaximumFractionDigits(length);
		format.setMinimumFractionDigits(length);

		return format;
	}

	// /**
	// * 校验用户的组织选择是否正确,即是否是下级组织
	// */
	// public static void checkSubOrgs(Component com, String[] lNumber) {
	// int size = lNumber.length;
	//
	// //分配的组织个数的层数
	// int[] qty = new int[size - 1];
	//
	// //默认组织最多选择六级
	// int[] dd = new int[6];
	//
	// Vector[] vec = new Vector[6];
	//
	// for (int i = 0; i < 6; i++) {
	// vec[i] = new Vector(4);
	// }
	//
	// //被分配的组织longNumber和它的级数
	// String superNumber = lNumber[size - 1];
	// int superQty = superNumber.split("!").length;
	//
	// for (int i = 0; i < qty.length; i++) {
	// String number = lNumber[i];
	// qty[i] = number.split("!").length;
	//
	// if (qty[i] <= superQty) {
	// //选择组织中有平级或上级
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	//
	// if (number.indexOf(superNumber) == -1) {
	// //选择的组织中有不属于该组织的下级组织
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg1");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	// }
	//
	// for (int i = 0; i < dd.length; i++) {
	// dd[i] = 0;
	// }
	//
	// for (int i = 0; i < qty.length; i++) {
	// if ((qty[i] == 1)) {
	// dd[0] = 1;
	// }
	//
	// if ((qty[i] == 2)) {
	// dd[1] = 1;
	// }
	//
	// if ((qty[i] == 3)) {
	// dd[2] = 1;
	// }
	//
	// if ((qty[i] == 4)) {
	// dd[3] = 1;
	// }
	//
	// if ((qty[i] == 5)) {
	// dd[4] = 1;
	// }
	//
	// if ((qty[i] == 6)) {
	// dd[5] = 1;
	// }
	// }
	//
	// int begin = -1;
	// int end = 0;
	//
	// for (int i = 0; i < dd.length; i++) {
	// if ((dd[i] == 1) && (begin == -1)) {
	// begin = i;
	// }
	//
	// if (dd[i] == 1) {
	// end = i;
	// }
	// }
	//
	// if (begin == -1) {
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	//
	// for (int i = begin; i <= end; i++) {
	// if (dd[i] == 0) {
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg2");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	// }
	//
	// for (int i = 0; i < qty.length; i++) {
	// if (qty[i] == 1) {
	// vec[0].addElement(lNumber[i]);
	// } else if (qty[i] == 2) {
	// vec[1].addElement(lNumber[i]);
	// } else if (qty[i] == 3) {
	// vec[2].addElement(lNumber[i]);
	// } else if (qty[i] == 4) {
	// vec[3].addElement(lNumber[i]);
	// } else if (qty[i] == 5) {
	// vec[4].addElement(lNumber[i]);
	// } else if (qty[i] == 6) {
	// vec[5].addElement(lNumber[i]);
	// }
	// }
	//
	// if ((begin == end) && (superQty != begin)) {
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg2");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	//
	// for (int i = end; i > begin; i--) {
	// Object[] obj1 = vec[i].toArray();
	// Object[] obj2 = vec[i - 1].toArray();
	//
	// for (int m = 0; m < obj1.length; m++) {
	// boolean isOk = false;
	// String number1 = (String) obj1[m];
	//
	// for (int n = 0; n < obj2.length; n++) {
	// String number2 = (String) obj2[n];
	//
	// if (number1.indexOf(number2) != -1) {
	// isOk = true;
	//
	// break;
	// }
	// }
	//
	// if (!isOk) {
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg2");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	// }
	// }
	// }
	//
	// /**
	// * 根据下级组织集合，选定的组织集合判断选定的组织是否为下级组织
	// *
	// * @param subOrgC
	// * @param selectOrgC
	// */
	// public static boolean checkSubOrgUnit(OrgUnitCollection subOrgC,
	// OrgUnitCollection selectOrgC) {
	// boolean isYes = false;
	// int size = subOrgC.size();
	// int selectSize = selectOrgC.size();
	// OrgUnitInfo orgUnitSelect;
	// OrgUnitInfo orgUnitSub;
	// for (int i = 0; i < selectSize; i++) {
	// orgUnitSelect = selectOrgC.get(i);
	// boolean isExist = false;
	// for (int j = 0; j < size; j++) {
	// orgUnitSub = subOrgC.get(j);
	// if (orgUnitSelect.getId().equals(orgUnitSub.getId())) {
	// //如果有就跳出判断下一个
	// isExist = true;
	// break;
	// }
	// }
	// if (!isExist) {
	// //找了一遍都没有说明不是下级组织,找到一个不是下级组织的即可跳出
	// isYes = true;
	// break;
	// }
	// }
	// return isYes;
	// }
	//

	/**
	 * 设置Text空间的编码规则<br>
	 * 设置编码规则的地点必须在 initDataStatus()设置,
	 * 
	 * @author fengrenfei 修改此方法， 必须告诉我！
	 * 
	 * @param info
	 *            业务对象
	 * @param txtField
	 *            文本框
	 * @param companyId
	 *            公司Id
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 * @throws BOSException
	 */
	public static void initNumber(CoreBaseInfo info, KDTextField txtField,
			String companyId) {
		if (!FMHelper.isEmpty(info.getString("number"))) {
			return;
		}
		boolean rst = hasNumber(info, companyId);

		if (rst) {
			// 设置了编码规则不允许编辑
			txtField.setEditable(false);
			txtField.setEnabled(false);
			String number = getDispNumber(info, companyId);
			if (!FMHelper.isEmpty(number)) {
				info.setString("number", number);
				txtField.setText(number);
			}
		} else {
			// 如果编码规则或去不成功可以编辑
			txtField.setEnabled(true);
			txtField.setEditable(true);
		}
	}

	/**
	 * 获取新增显示时的编码(不能是断号支持)
	 * 
	 * @param info
	 * @param companyId
	 * @return
	 */
	private static String getDispNumber(CoreBaseInfo info, String companyId) {
		String number = null;

		ICodingRuleManager codingRuleManager;
		try {
			codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (codingRuleManager.isAddView(info, companyId)
					&& !codingRuleManager.isUseIntermitNumber(info, companyId)) {// 新增显示且不是断号支持
				number = codingRuleManager.getNumber(info, companyId);
			}
		} catch (BOSException e) {
		} catch (CodingRuleException e) {
		} catch (EASBizException e) {
		}

		return number;
	}

	/**
	 * 设置列空间的编码规则
	 * 
	 * @param info
	 *            实体对象
	 * @param col
	 *            列
	 * @param companyId
	 *            对应的业务公司
	 */
	public static void initNumber(CoreBaseInfo info, IColumn col,
			String companyId) {
		boolean rst = hasNumber(info, companyId);

		if (!rst) {
			// 如果编码规则获取不成功可以编辑
			col.getStyleAttributes().setLocked(false);

		} else {
			// 设置了编码规则不允许编辑
			col.getStyleAttributes().setLocked(true);
			col.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		}
	}

	/**
	 * 根据编码规则获取编码，无异常提示的。
	 * 
	 * @param info
	 * @param companyId
	 * @return
	 */
	public static boolean hasNumber(CoreBaseInfo info, String companyId) {
		if ((info == null) || (companyId == null) || companyId.equals("")) {
			return false;
		}

		boolean result = false;

		try {
			ICodingRuleManager codingRuleManager = CodingRuleManagerFactory
					.getRemoteInstance();
			result = codingRuleManager.isExist(info, companyId);

		} catch (Exception e) {
			;
		}

		return result;
	}

	/**
	 * 根据编码规则获取编码，无异常提示的。
	 * 
	 * @param info
	 * @param companyId
	 * @return
	 */
	private static String getNumber(CoreBaseInfo info, String companyId) {
		if ((info == null) || (companyId == null) || companyId.equals("")) {
			return null;
		}

		String result = null;
		try {
			ICodingRuleManager codingRuleManager = CodingRuleManagerFactory
					.getRemoteInstance();
			result = codingRuleManager.getNumber(info, companyId);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		if (result != null && result.equals("")) {
			result = null;
		}
		return result;
	}

	/*
	 * 初始化数据计划生命周期
	 */
	public static void initSpinAfterNow(KDSpinner spnBeginYear,
			KDSpinner spnBeginMonth, KDSpinner spnEndYear, KDSpinner spnEndMonth) {
		// 初始化spinner
		Calendar instance = Calendar.getInstance();
		int thisYear = instance.get(Calendar.YEAR);
		SpinnerNumberModel yModel = new SpinnerNumberModel(thisYear, thisYear,
				FMConstants.MAX_YEAR, 1);
		spnBeginYear.setModel(yModel);

		SpinnerNumberModel yModel1 = new SpinnerNumberModel(thisYear, thisYear,
				FMConstants.MAX_YEAR, 1);
		spnEndYear.setModel(yModel1);
		int thisMonth = instance.get(Calendar.MONTH) + 1;

		SpinnerNumberModel mModel = new SpinnerNumberModel(thisMonth, 1, 12, 1);
		spnBeginMonth.setModel(mModel);

		SpinnerNumberModel mModel1 = new SpinnerNumberModel(thisMonth, 1, 12, 1);
		spnEndMonth.setModel(mModel1);
	}

	/**
	 * 
	 * @param spnBeginYear
	 * @param spnBeginMonth
	 * @param spnEndYear
	 * @param spnEndMonth
	 */
	public static void initSpinFromTO(KDSpinner spnBeginYear,
			KDSpinner spnBeginMonth, KDSpinner spnEndYear, KDSpinner spnEndMonth) {
		// 初始化spinner
		Calendar instance = Calendar.getInstance();
		int thisYear = instance.get(Calendar.YEAR);
		SpinnerNumberModel yModel = new SpinnerNumberModel(thisYear, 2000,
				FMConstants.MAX_YEAR, 1);
		spnBeginYear.setModel(yModel);

		SpinnerNumberModel yModel1 = new SpinnerNumberModel(thisYear, 2000,
				FMConstants.MAX_YEAR, 1);
		spnEndYear.setModel(yModel1);

		int thisMonth = instance.get(Calendar.MONTH) + 1;

		SpinnerNumberModel mModel = new SpinnerNumberModel(thisMonth, 1, 12, 1);
		spnBeginMonth.setModel(mModel);

		SpinnerNumberModel mModel1 = new SpinnerNumberModel(thisMonth, 1, 12, 1);
		spnEndMonth.setModel(mModel1);
	}

	
	/**
	 * 
	 * 描述：初始化表格
	 * @author:liupd
	 * 创建时间：2006-8-3 <p>
	 */
	public static void initTable(KDTable tblMain) {		
		if(tblMain!=null){
			tblMain.setColumnMoveable(true);
			tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(tblMain.getColumn("createTime")!=null){
				FDCHelper.formatTableDate(tblMain, "createTime");
			}
			if(tblMain.getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(tblMain, "auditTime");
			}
			if(tblMain.getColumn("createDate")!=null){
				FDCHelper.formatTableDate(tblMain, "createDate");
			}
			if(tblMain.getColumn("createTime")!=null){
				FDCHelper.formatTableDate(tblMain, "createTime");
			}
			if(tblMain.getColumn("signDate")!=null){
				FDCHelper.formatTableDate(tblMain, "signDate");
			}
			if(tblMain.getColumn("entrys.deductDate")!=null){
				FDCHelper.formatTableDate(tblMain, "entrys.deductDate");
			}
			if(tblMain.getColumn("amount")!=null){
				FDCHelper.formatTableNumber(tblMain, "amount");
			}
			if(tblMain.getColumn("exRate")!=null){
				FDCHelper.formatTableNumber(tblMain, "exRate");
			}
			if(tblMain.getColumn("originalAmount")!=null){
				FDCHelper.formatTableNumber(tblMain, "originalAmount");
			}
			
			//entrys.deductAmt
			if(tblMain.getColumn("entrys.deductAmt")!=null){
				FDCHelper.formatTableNumber(tblMain, "entrys.deductAmt");
			}
			
			if(tblMain.getColumn("costNouse")!=null){
				FDCHelper.formatTableNumber(tblMain, "costNouse");
			}
			
			//deductAmount
			if(tblMain.getColumn("deductAmount")!=null){
				FDCHelper.formatTableNumber(tblMain, "deductAmount");
			}
			
			if(tblMain.getColumn("totalOriginalAmount")!=null){
				FDCHelper.formatTableNumber(tblMain, "totalOriginalAmount");
			}
			
			if(tblMain.getColumn("totalSettlePrice")!=null){
				FDCHelper.formatTableNumber(tblMain, "totalSettlePrice");
			}
			
			//付款计划
			if(tblMain.getColumn("signAmount")!=null){
			FDCHelper.formatTableNumber(tblMain, "signAmount");
			}
			if(tblMain.getColumn("lastPrice")!=null){
				FDCHelper.formatTableNumber(tblMain, "lastPrice");
			}
			if(tblMain.getColumn("payOriAmount")!=null){
				FDCHelper.formatTableNumber(tblMain, "payOriAmount");
			}
			if(tblMain.getColumn("payAmount")!=null){
				FDCHelper.formatTableNumber(tblMain, "payAmount");
			}
			if(tblMain.getColumn("notPayAmount")!=null){
				FDCHelper.formatTableNumber(tblMain, "notPayAmount");
			}
			if(tblMain.getColumn("payedAmt")!=null){
				FDCHelper.formatTableNumber(tblMain, "payedAmt");
			}
			if(tblMain.getColumn("payProportion")!=null){
				FDCHelper.formatTableNumber(tblMain, "payProportion");
			}
		}


	}
	
	
//	class FDCBillDataFillListener  implements KDTDataFillListener{
//
//		KDTable tblMain;
//		CoreUI ui;
//		public FDCBillDataFillListener(KDTable tblMain,CoreUI ui){
//			this.tblMain = tblMain;
//			this.ui = ui;
//		}
//		
//		public void afterDataFill(KDTDataRequestEvent e) {
//			initTable(tblMain);
//			tblMainAfterDataFill(e,tblMain);
//		}
//		
//	}
	
	public static void initTableListener(KDTable tblMain,CoreUI ui) {	
		
		FDCBillDataFillListener listener = new FDCBillDataFillListener(tblMain,ui);
		//Table填充数据之后，设置原币的精度
		tblMain.getDataRequestManager().addDataFillListener(listener);	
	}
	
	//设置原币金额的精度
	protected static void tblMainAfterDataFill(KDTDataRequestEvent e,KDTable tblMain,CoreUI ui) {
		int first = e.getFirstRow();
		int count = e.getLastRow() - e.getFirstRow() + 1;
		
		int indexOfCurPre = tblMain.getColumnIndex("currency.precision");
		int indexOfCurId = tblMain.getColumnIndex("currency.id");
		if(indexOfCurPre<0){
			return ;
		}
		//增加一个i<tblMain.getRowCount()的判断 确保tblMain.getRow(i)不会为null
		for (int i = first; i < first + count && i< tblMain.getRowCount(); i++) {	
			Object  obj  = tblMain.getRow(i).getCell(indexOfCurPre).getValue();
			if(!(obj instanceof Integer)){
				continue ;
			}
			Integer curPre = (Integer) tblMain.getRow(i).getCell(indexOfCurPre).getValue();
			
			int pre  = curPre.intValue();
			//存在可能为空的情况
			if(tblMain.getRow(i)!=null && tblMain.getRow(i).getCell("originalAmount")!=null){
				tblMain.getRow(i).getCell("originalAmount")
						.getStyleAttributes().setNumberFormat(
								FDCClientHelper.getNumberFormat(pre,true));
				
				//
				ICell exRateCell = tblMain.getRow(i).getCell("exRate");
				if(exRateCell!=null && indexOfCurId>=0){
					String curId = (String)tblMain.getRow(i).getCell(indexOfCurId).getValue();
					int expre = getExRatePrecOfCurrency(curId);
					exRateCell.getStyleAttributes().setNumberFormat(
							FDCClientHelper.getNumberFormat(expre,true));
				}
			}
		}
	}
	
	
	/**
	 * @param treeType
	 * @deprecated not need
	 */
	public static void clearTree(KDTree tree) {
		if ((tree == null) || (tree.getModel() == null)) {
			return;
		}

		tree.removeAllChildrenFromParent((MutableTreeNode) tree.getModel()
				.getRoot());
	}

	/**
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void initCurrency(KDComboBox cbo) throws BOSException,
			EASBizException {
		ICurrency iCurrency = CurrencyFactory.getRemoteInstance();
		CurrencyCollection currencyCollection = iCurrency
				.getCurrencyCollection(true);
		cbo.removeAllItems();
		cbo.addItems(currencyCollection.toArray());
	}

	/**
	 * 获取所有被选中的行
	 */
	public static List getAllSelectedRow(KDTable tblMain) {
		List selectBlocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock selectBlock = null;
		List rowList = new ArrayList();

		for (int i = 0; i < selectBlocks.size(); i++) {
			selectBlock = (KDTSelectBlock) selectBlocks.get(i);

			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow row = tblMain.getRow(j);
				rowList.add(row);
			}
		}

		return rowList;
	}

	/**
	 * @param k
	 */
	public static void suiteWidth(KDTable tbl, int k) {
		int width = 0;

		for (int i = 0; i < tbl.getColumnCount(); i++) {
			if (i != k) {
				width += tbl.getColumn(i).getWidth();
			}
		}

		tbl.getColumn(k).setWidth(tbl.getWidth() - width);
	}

	public static void suiteWidth(KDTable tbl, int tblWidth, int k) {
		int width = 0;

		for (int i = 0; i < tbl.getColumnCount(); i++) {
			if (i != k) {
				width += tbl.getColumn(i).getWidth();
			}
		}

		tbl.getColumn(k).setWidth(tblWidth - width);
	}

	/**
	 * 设置树的默认选中节点
	 */
	public static DefaultKingdeeTreeNode goLeaf(KDTree tree) {
		TreeModel model = tree.getModel();

		if (model == null) {
			return null;
		}

		Object objNode = model.getRoot();

		while (tree.getModel().getChildCount(objNode) > 0) {
			objNode = tree.getModel().getChild(objNode, 0);
		}
		tree.setSelectionNode((DefaultKingdeeTreeNode) objNode);
		return (DefaultKingdeeTreeNode) objNode;
	}

	/**
	 * 设置默认节点为当前公司
	 */
	public static void goCurrentOrgNode(KDTree tree) {
		TreeModel model = tree.getModel();
		if (model == null) {
			return;
		}
		IContextHelper ctxHelper = ContextHelperFactory.getRemoteInstance();
		BOSUuid currOrgId = ctxHelper.getCurrentCompany().getId();
		goOrgViewNode(tree, currOrgId);
	}

	/**
	 * @param tree
	 * @param currOrgId
	 */
	public static void goOrgViewNode(KDTree tree, BOSUuid currOrgId) {
		for (int i = 0, height = tree.getRowCount(); i < height; i++) {
			TreePath tp = tree.getPathForRow(i);
			if (tp == null) {
				continue;
			}
			DefaultKingdeeTreeNode tn = (DefaultKingdeeTreeNode) tp
					.getLastPathComponent();
			OrgStructureInfo orgViewInfo = (OrgStructureInfo) tn
					.getUserObject();
			if (orgViewInfo.getId().equals(currOrgId)) {
				tree.setSelectionNode(tn);
				return;
			}
		}
	}

	/**
	 * 获取币别编辑器
	 */
	public static KDTDefaultCellEditor getCurrencyEditor(int prec) {
		KDFormattedTextField currencyTxtFiled = new KDFormattedTextField();
		currencyTxtFiled.setDataType(NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL);
		currencyTxtFiled.setPrecision(prec);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(
				currencyTxtFiled);
		return cellEditor;
	}

	public static KDTDefaultCellEditor getNumberEditor() {
		KDNumberTextField numberTextField = new KDNumberTextField();
		numberTextField.setDataType(KDNumberTextField.BIGDECIMAL_TYPE);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(
				numberTextField);

		return cellEditor;
	}

	/**
	 * 公司树初始化
	 * 
	 * @param tree
	 *            树控件
	 * @param targetUI
	 *            界面对象
	 * @param action
	 *            权限action
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static DefaultTreeModel getCompanyTreeModel()
			throws EASBizException, BOSException {
		// OrgViewType orgViewType,KDTree tree,IMetaDataPK uiPK,IMetaDataPK
		// actionPK) throws EASBizException, BOSException
		// OrgViewUtils.buildOrgViewTree(OrgViewType.COMPANY, tree, targetUI,
		// action);

		INewOrgViewFacade iNewOrgView = null;
		iNewOrgView = NewOrgViewFacadeFactory.getRemoteInstance();
		IRowSet rows = iNewOrgView.buildRowSetByCU(OrgConstants.DEF_CU_ID,
				OrgConstants.FI_VIEW_ID, true);
		DefaultKingdeeTreeNode root = NewOrgViewHelper.buildTreeByRowSet(rows);
		return new DefaultTreeModel(root);

	}

	/**
	 * @param companyId
	 * @return
	 * @throws BOSException
	 */
	public static CompanyOrgUnitCollection getCompanyCollection(
			String[] companyId) throws BOSException {
		// // Object []companyInfo = null;
		// if(FMHelper.isEmpty(companyId)){
		// return new CompanyOrgUnitCollection();
		// }
		// ICompanyOrgUnit iCompany = CompanyOrgUnitFactory
		// .getRemoteInstance();
		// // TODO 考虑只获取公司的name
		// String osql = "select * where id in('" + companyId[0] +"'";
		// for(int i=1; i<companyId.length; i++){
		// osql+=",'" + companyId[i]+"'";
		// }
		// osql+=")";
		// return iCompany.getCompanyOrgUnitCollection(osql);
		return getCompanyOrgUnitCollection(companyId);
	}
	
	public static void initComboMeasureIndex(KDComboBox comboMeasureIndex,boolean isEdit) throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(isEdit){
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		}
		view.getSorter().add(new SorterItemInfo("number"));
		MeasureIndexCollection measureIndexCollection = MeasureIndexFactory.getRemoteInstance().getMeasureIndexCollection(view);
		if(measureIndexCollection==null||measureIndexCollection.size()==0){
			FDCMsgBox.showWarning("没有启用成本测算分摊指标设置数据!");
			SysUtil.abort();
		}
		comboMeasureIndex.addItems(measureIndexCollection.toArray());

	}
	
	/**
	 * 初始化测算阶段
	 * 
	 * @param comBoMeasureStage
	 *            控件
	 */
	public static void initComboMeasureStage(KDComboBox comboMeasureStage,boolean isEdit) throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);//一失足成千古恨
		if(isEdit){
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		}
		view.getSorter().add(new SorterItemInfo("number"));
		MeasureStageCollection measureStageCollection = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
		if(measureStageCollection==null||measureStageCollection.size()==0){
			FDCMsgBox.showWarning("没有启用测算阶段!");
			SysUtil.abort();
		}
		comboMeasureStage.addItems(measureStageCollection.toArray());

	}
	
	/**
	 * 初始化币别列表
	 * 
	 * @param comboCurrency
	 *            币别控件
	 * @param isSelBaseCurr
	 *            是否选中当前公司本位币
	 */
	public static void initComboCurrency(KDComboBox comboCurrency,
			boolean isSelBaseCurr) throws EASBizException, BOSException {
		CurrencyCollection currencyCollection = (CurrencyCollection)ActionCache.get("FDCBillEditUIHandler.currencyCollection");
		// 初始化币别列表
		if(currencyCollection==null){
			ICurrency iCurrency = CurrencyFactory.getRemoteInstance();
			currencyCollection = iCurrency.getCurrencyCollection(true);
		}
		comboCurrency.addItems(currencyCollection.toArray());

	}

	/**
	 * 初始化币别列表
	 */
	public static void initComboCurrency(KDComboBox comboCurrency)
			throws EASBizException, BOSException {
		initComboCurrency(comboCurrency, false);
	}

	/**
	 * 对单元格进行币别格式化
	 */
	public static void currencyFormat(BOSUuid currencyId, ICell cell) {
		if (currencyId == null || cell == null) {
			return;
		}
		int prec = getPrecOfCurrency(currencyId);
		KDTDefaultCellEditor editor = getCurrencyEditor(prec);
		String numberFmt = getNumberFtm(currencyId);
		cell.getStyleAttributes().setNumberFormat(numberFmt);
		cell.setEditor(editor);
	}

	/**
	 * 对列进行币别格式化
	 */
	public static void currencyFormat(BOSUuid currencyId, IColumn col) {
		if (currencyId == null || col == null) {
			return;
		}
		int prec = getPrecOfCurrency(currencyId);
		KDTDefaultCellEditor editor = getCurrencyEditor(prec);
		String numberFmt = getNumberFtm(currencyId);
		col.getStyleAttributes().setNumberFormat(numberFmt);
		col.setEditor(editor);
	}

	/**
	 * 获取公司本位币
	 */
	public static CurrencyInfo getBaseCurrency(CompanyOrgUnitInfo company)
			throws EASBizException, BOSException {
		CurrencyInfo currency = ContextHelperFactory.getRemoteInstance()
				.getCompanyBaseCurrency(company);
		return currency;
	}

	/**
	 * 校验是否选中行
	 */
	public static void checkSelected(Component ui, KDTable table) {
		if (table.getRowCount() == 0
				|| table.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showWarning(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 提示保存成功
	 */
	public static void showSubmitSuccess(CoreUI ui, CoreBaseInfo info) {
		String classAlias = FrameWorkUtils.getClassAlias((CoreBaseInfo) info);
		ui.setMessageText(classAlias
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		ui.showMessage();
	}

	/**
	 * 提示保存成功
	 */
	public static void showSubmitMsg(CoreUI ui, String s) {
		ui.setMessageText(s);
		ui.showMessage();
	}

	/**
	 * 获取总集团
	 */
	public static OrgUnitInfo getGroupOrg() {
		OrgUnitInfo groupOrg = null;
		try {

			groupOrg = NewOrgUnitHelper.getRootCU();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return groupOrg;
	}

	/**
	 * 根据银行账户获取公司
	 */
	public static CompanyOrgUnitInfo getCompanyByBankAcct(String acctBankId)
			throws EASBizException, BOSException {
		SelectorItemCollection src = new SelectorItemCollection();
		src.add(new SelectorItemInfo("*"));
		src.add(new SelectorItemInfo("company.*"));
		IAccountBank iAccountBank = AccountBankFactory.getRemoteInstance();
		AccountBankInfo accountBank = iAccountBank.getAccountBankInfo(
				new ObjectUuidPK(acctBankId), src);
		CompanyOrgUnitInfo comapny = accountBank.getCompany();
		return comapny;
	}

	/**
	 * 根据过滤条件，主要是公司的ID数组，获得公司的Collection
	 */
	public static CompanyOrgUnitCollection getCompanyOrgUnitCollection(
			String[] companyIds) throws BOSException {
		// String[] companyId = param.getCompanyIdAry();
		if (FMHelper.isEmpty(companyIds)) {
			return new CompanyOrgUnitCollection();
		}
		Set set = FMHelper.asSet(companyIds);
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.getSelector().add("*");
		// ev.getSelector().add("number");
		ev.getSelector().add("accountTable.id");
		ev.getSelector().add("accountTable.number");
		filter.getFilterItems().add(
				new FilterItemInfo("id", set, CompareType.INCLUDE));
		ev.setFilter(filter);
		CompanyOrgUnitCollection coll = null;

		ICompanyOrgUnit company = CompanyOrgUnitFactory.getRemoteInstance();
		coll = company.getCompanyOrgUnitCollection(ev);

		return coll;

	}

	/**
	 * 根据具体的Query，过滤条件，获得数据集
	 * 
	 * @param sql
	 * @return
	 * @throws BOSException
	 */
	public static IRowSet getRowSet(IMetaDataPK mainQueryPK, EntityViewInfo evi) {
		IQueryExecutor exec = QueryExecutorFactory
				.getRemoteInstance(mainQueryPK);
		exec.setObjectView(evi);
		IRowSet rowSet = null;
		try {
			rowSet = exec.executeQuery();
			/*
			 * String s = exec.getSQL(); s ="select sum(ass) from (" + s+") a ";
			 * ISQLExecutor exc = SQLExecutorFactory.getRemoteInstance(s); rs =
			 * exc.executeSQL();
			 */
		} catch (BOSException e) {
			SysUtil.abort(e);
		}
		return rowSet;
	}

	// /**
	// * 验证结算中心状态
	// */
	// public static void verifyFSStatus() throws BOSException {
	// FSSwitchStatusEnum status = FSClientHelper.getSettCenterStatus();
	// if (status.equals(FSSwitchStatusEnum.CLOSED)) {
	// MsgBox.showWarning(EASResource.getString(OpenUI.resourcePath,
	// "switchIsClose"));
	// SysUtil.abort();
	// }
	// }

	/**
	 * 根据币别格式设置Table中 某列的数据格式
	 * 
	 * @param tblMain
	 * @param columnKey
	 * @param currencyID
	 */
	public static void setNumberFormat(KDTable tblMain, String columnKey,
			String currencyID) {
		// 设置结算额精度
		String numberFormat = null;
		if (currencyID == null) {
			numberFormat = "%r-[ ]{###,###,##0.00}15.2n";
		} else {
			numberFormat = getNumberFtm(BOSUuid.read(currencyID));
		}
		StyleAttributes styleAttributes = tblMain.getColumn(columnKey)
				.getStyleAttributes();
		styleAttributes.setNumberFormat(numberFormat);
		styleAttributes.setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	/**
	 * 
	 * 描述：按实际位数格式化, 格式化之后，单元格的值为String类型，有分组
	 * 
	 * @param cell
	 *            table cell
	 * @author:liupd 创建时间：2005-11-8
	 *               <p>
	 */
	public static void setActualDigitNumberFormat(ICell cell) {
		if (cell == null) {
			throw new IllegalArgumentException("cell is null");
		}
		if (cell.getValue() == null) {
			throw new IllegalArgumentException("cell's value is null");
		}
		DecimalFormat df = new DecimalFormat(ACTUAL_DIGIT_FMT);
		cell.setValue(df.format(cell.getValue()));
	}

	/**
	 * 
	 * 描述：按实际位数格式化表格
	 * 
	 * @param table
	 * @param columKey
	 * @author:liupd 创建时间：2005-11-8
	 *               <p>
	 */
	public static void setActualDigitNumberFormat(KDTable table,
			String columnKey) {
		StyleAttributes styleAttributes = table.getColumn(columnKey)
				.getStyleAttributes();
		styleAttributes.setNumberFormat("%r-[ ]{###,###,###.##########}n");
		styleAttributes.setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	public static void setActualDigitNumberFormat(KDTable table,
			String columnKey, int scale) {
		StyleAttributes styleAttributes = table.getColumn(columnKey)
				.getStyleAttributes();
		String strScale = "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < scale; i++) {
//			strScale = strScale + "#";
			buf.append("#");
		}
		strScale = buf.toString();
		styleAttributes
				.setNumberFormat("%r-[ ]{###,###,###." + strScale + "}n");
		styleAttributes.setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	/**
	 * 清空F7控件
	 */
	public static void clearF7(KDBizPromptBox f7Item) {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", "null"));
		ev.setFilter(filter);
		f7Item.setEntityViewInfo(ev);
	}

	/**
	 * 根据币别设置某单元格的数据格式
	 * 
	 * @param row
	 * @param columnKey
	 * @param currencyID
	 */
	public static void setNumberFormat(IRow row, String columnKey,
			String currencyID) {
		// 设置结算额精度
		String numberFormat = getNumberFtm(BOSUuid.read(currencyID));
		StyleAttributes styleAttributes = row.getCell(columnKey)
				.getStyleAttributes();
		styleAttributes.setNumberFormat(numberFormat);
		styleAttributes.setHorizontalAlign(HorizontalAlignment.RIGHT);

	}

	/**
	 * 
	 * 描述：导出Table的数据到Excel
	 * 
	 * @param ui
	 *            当前UI
	 * @param table
	 *            要导出数据的KDTable
	 * @throws Exception
	 *             liupd 创建时间：2005-5-17
	 */
	public static void exportToExcel(Component ui, KDTable table)
			throws Exception {
		String filePath = null;

		// 弹出文件选择框
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setDialogTitle(EASResource.getString(RES, "exportToExcel"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		fileChooser.setFileFilter(new ExcelFileFilter());

		String fileName = null;
		if (fileChooser.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
			fileName = fileChooser.getSelectedFile().getName();

			if (!filePath.toLowerCase().endsWith(ExcelFileFilter.POSTFIX)) {
				filePath = filePath + ExcelFileFilter.POSTFIX;
			}
		} else {
			// 用户取消了，返回
			return;
		}

		if (filePath == null) {
			return;
		}
		if (fileName.lastIndexOf(".") != -1)
			fileName = fileName.substring(0, fileName.lastIndexOf("."));

		KDSBook kdsBook = new KDSBook(fileName);

		String sheetName = fileName;

		KDSSheet kdsSheet = table.getIOManager().saveToKDSSheet(kdsBook, true,
				false, sheetName);

		kdsBook.addSheet(null, kdsSheet);

		ExportManager export = new ExportManager();

		try {
			export.exportToExcel(kdsBook, filePath);
		} catch (RuntimeException e1) {
			MsgBox.showWarning(ui, EASResource.getString(RES, "fileIsOpen"));
		}
	}

	/**
	 * 导出 if flag==true 导出隐藏列，否则不导出隐藏列
	 * 
	 * @param ui
	 * @param table
	 * @param flag
	 * @throws Exception
	 */

	public static void exportExcel(Component ui, KDTable table, boolean flag)
			throws Exception {
		// table.getStyleAttributes().setBackground(Color.WHITE);
		String filePath = null;

		// 弹出文件选择框
		KDFileChooser fileChooser = new KDFileChooser();
		// fileChooser.setDialogTitle(EASResource.getString(FPFormClientHelper.resourcePath,
		// "ExportFP"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		fileChooser.setFileFilter(new ExcelFileFilter());

		String fileName = null;
		// File[] files = fileChooser.getSelectedFiles();
		File fileDir = null;
		String totalFileName = null;

		if (fileChooser.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
			fileDir = new File(filePath
					.substring(0, filePath.lastIndexOf("\\")));
			fileName = fileChooser.getSelectedFile().getName();
			// totalFileName = fileName;
			totalFileName = fileName;
			if (!fileName.toLowerCase().endsWith(ExcelFileFilter.POSTFIX)) {
				totalFileName = fileName + ExcelFileFilter.POSTFIX;
			}

			if (!filePath.toLowerCase().endsWith(ExcelFileFilter.POSTFIX)) {
				filePath = filePath + ExcelFileFilter.POSTFIX;
			}
		} else {
			// 用户取消了，返回
			return;
		}

		if (filePath == null) {

			return;
		}
		if (fileName.lastIndexOf(".") != -1)
			fileName = fileName.substring(0, fileName.lastIndexOf("."));

		File[] files = null;
		Vector data = new Vector();
		int status = -1;
		if (fileDir != null && fileDir.isDirectory()) {
			files = fileDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				// 如果是文件，则加到结果集中去
				if (files[i].isFile()) {
					// data.add(files[i]);
					if (files[i].getName().equals(totalFileName)) {
						// MsgBox.showWarning(ui, EASResource.getString(RES,
						// "FileExisted"));
						status = MsgBox.showConfirm2(ui, EASResource.getString(
								RES, "FileExisted"));
						// 取消
						if (status == 2)
							return;
					}
				}
			}
		}

		// if( data.contains(filePath) ){
		// MsgBox.showWarning(ui, EASResource.getString(RES, "FileRecovered"));
		// }

		KDSBook kdsBook = new KDSBook(fileName);

		String sheetName = fileName;
		// TODO file name
		KDSSheet kdsSheet;
		if (flag) {
			kdsSheet = table.getIOManager().saveToKDSSheet(kdsBook, true,
					false, sheetName);
		} else {
			kdsSheet = table.getIOManager().saveToKDSSheet(kdsBook, true,
					false, false, sheetName);
		}

		kdsBook.addSheet(null, kdsSheet);

		ExportManager export = new ExportManager();

		try {
			export.exportToExcel(kdsBook, filePath);
		} catch (RuntimeException e1) {
			MsgBox.showWarning(ui, EASResource.getString(RES, "fileIsOpen"));
		}
	}

	/**
	 * 导出
	 * 
	 * @param ui
	 * @param table
	 * @throws Exception
	 */
	public static void exportExcel(Component ui, KDTable table)
			throws Exception {
		exportExcel(ui, table, false);
	}

	public static Frame getFrameAncestor(Component c) {
		for (Component p = c; p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	public static IMetaDataPK getActionPK(ItemAction action) {
		if (action == null) {
			return null;
		}
		String actoinName = action.getClass().getName();
		if (actoinName.indexOf("$") >= 0) {
			actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
		}

		return new MetaDataPK(actoinName);
	}

	// /**
	// * 根据过滤条件，获得记息对象的Collection
	// */
	// public static IntObjectCollection getIntObjectCollection(
	// String[] IntObjectIds) throws BOSException {
	// if (FMHelper.isEmpty(IntObjectIds)) {
	// return new IntObjectCollection();
	// }
	// Set set = FMHelper.asSet(IntObjectIds);
	// EntityViewInfo ev = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// ev.getSelector().add("name");
	// ev.getSelector().add("number");
	// filter.getFilterItems().add(
	// new FilterItemInfo("id", set, CompareType.INCLUDE));
	// ev.setFilter(filter);
	// IntObjectCollection coll = null;
	//
	// IIntObject intObject = IntObjectFactory.getRemoteInstance();
	// coll = intObject.getIntObjectCollection(ev);
	//
	// return coll;
	//
	// }

	/**
	 * WorkButton执行某项操作时候，提示执行成功信息
	 * 
	 * @param ui
	 * @param component
	 */
	public static void showSuccessInfo(Component ui, Component component) {
		if (!(component instanceof KDWorkButton)) {
			return;
		}
		String text = ((KDWorkButton) component).getText();
		MsgBox.showInfo(ui, text + EASResource.getString(RES, "SUCCESS"));
	}

	public static void showCanNotInfo(Component ui, ItemAction action) {
		if (action == null) {
			return;
		}
		MsgBox.showError(ui, "不能" + action.getExtendProperty("Name"));
		SysUtil.abort();
	}

	public static void showSuccessInfo(CoreUI ui, ItemAction action) {
		if (action == null) {
			return;
		}
		ui.setMessageText(action.getExtendProperty("Name")
				+ EASResource.getString(RES, "SUCCESS"));
		ui.showMessage();

	}

	public static void showSuccessMessage(CoreUI ui, Component component) {
		if (!(component instanceof KDWorkButton)) {
			return;
		}
		String text = ((KDWorkButton) component).getText();
		if (FMHelper.isEmpty(text)) {
			text = ((KDWorkButton) component).getToolTipText();
		}
		ui.setMessageText(text + EASResource.getString(RES, "SUCCESS"));
		ui.showMessage();
	}

	public static void printTDMetaDataField(String pkName) throws BOSException {
		MetaDataLoaderFactory.setClientMetaDataPath("W:\\apusic\\metas");
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		IMetaDataPK pk = new MetaDataPK(pkName);
		QueryInfo q = loader.getQuery(pk);
		SelectorInfo s = q.getSelector();
		/**
		 * <Field><Name>company </Name> <Alias>记账公司 </Alias> <Description/>
		 * <Type>STRING </Type> </Field>
		 */
		for (int i = 0; i < s.size(); i++) {
			System.out.println(" <Field" + " name=\"" + s.get(i).getName()
					+ "\" alias=\"" + s.get(i).getDisplayName()
					+ "\" description=\"" + s.get(i).getDisplayName()
					+ "\" type=\""
					+ getTDDataType(s.get(i).getReturnType().toString())
					+ "\" expr=\"" + s.get(i).getName() + "\" />");
		}
	}

	public static String getTDDataType(String bosType) {
		if (bosType == null) {
			return "string";
		}
		if (bosType.equals("String")) {
			return "string";
		}
		if (bosType.equals("Int")) {
			return "integer";
		}
		if (bosType.equals("Number")) {
			return "decimal";
		}
		if (bosType.equals("String")) {
			return "string";
		}
		if (bosType.equals("Date")) {
			return "datetime";
		}
		return bosType.toLowerCase();

	}

	public static void printImport(String metaPK) throws BOSException {
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		IMetaDataPK pk = new MetaDataPK(metaPK);

		EntityObjectInfo entity = loader.getEntity(pk);
		DataTableInfo table = entity.getTable();
		PropertyCollection properties = entity.getPropertiesRuntime();
		String sp = "\t";
		for (int i = 0; i < properties.size(); i++) {
			PropertyInfo p = properties.get(i);

			DataType dataType = DataType.getEnum(p.getString("datatype"));
			if (p.getMappingField() == null) {
				continue;
			}
			ColumnInfo col = table.getColumnByName(p.getMappingField()
					.getName());
			if (col == null) {
				continue;
			}

			System.out.println(p.getName() + sp + getDataTypeAilas(dataType)
					+ sp + col.getLength() + sp + p.getAlias());
		}

	}

	private static String getDataTypeAilas(DataType dataType) {
		if (dataType == null) {
			return "字符串";
		}
		if (dataType.equals(DataType.STRING)) {
			return "字符串";
		}

		if (dataType.equals(DataType.DATE)) {
			return "日期";
		}
		if (dataType.equals(DataType.TIME)) {
			return "日期";
		}
		if (dataType.equals(DataType.TIMESTAMP)) {
			return "日期";
		}
		if (dataType.equals(DataType.DECIMAL)) {
			return "数字型";
		}
		if (dataType.equals(DataType.BOOLEAN)) {
			return "布尔";
		}

		return dataType.toString();

	}

	/**
	 * 描述：根据操作状态更新UI Title 注意：使用此方法，要保证UI元数据Title的定义，不能包含“编辑‘，“查看”等， 比如资金计划模板的UI
	 * Title 应该定义为：“资金计划模板”，而不是“资金计划模板 - 编辑” FMClientHelper.updateUITitle(this,
	 * resHelper.getString("this.title"));
	 * 
	 * @param UI对象
	 * @author:liupd 创建时间：2005-8-15
	 *               <p>
	 */
	public static void updateUITitle(CoreUIObject ui, String title) {
		String resPath = "com.kingdee.eas.fm.common.FMResource";
		String strTitle = title;
		if (ui.getOprtState().equals(OprtState.VIEW)) {
			ui.setUITitle(strTitle + " - "
					+ EASResource.getString(resPath, "view"));
		} else if (ui.getOprtState().equals(OprtState.EDIT)) {
			ui.setUITitle(strTitle + " - "
					+ EASResource.getString(resPath, "edit"));
		} else if (ui.getOprtState().equals(OprtState.ADDNEW)) {
			ui.setUITitle(strTitle + " - "
					+ EASResource.getString(resPath, "new"));
		}
	}

	/**
	 * 根据单据id获取生成的凭证集合
	 * 
	 * @param billId
	 * @return
	 * @throws BOSException
	 */
	public static VoucherCollection getVoucherCollection(String billId)
			throws BOSException {
		// 通过botp的转换信息下查到结算单转换到凭证的集合
		String[] srcEntriesID = new String[] { billId };
		IBTPManager iBTPManager = BTPManagerFactory.getRemoteInstance();
		// 王伟东 修改 2005-06-20 开始
		// 修改BUG(BT023830)，套打出错的问题
		// Map map = iBTPManager.getDestEntries("", "", srcEntriesID);

		Map map = iBTPManager.getDestEntries(BOSUuid.read(billId).getType()
				.toString(), new VoucherInfo().getBOSType().toString(),
				srcEntriesID);

		VoucherCollection colVoucher = null;
		// 王伟东 修改 2005-06-20 结束
		if (!map.isEmpty() && map.size() > 0) {
			BOTRelationEntryCollection entryColl = (BOTRelationEntryCollection) map
					.get(billId);
			BOTRelationEntryInfo entryInfo;
			Set voucherIds = new HashSet();

			for (int i = 0; i < entryColl.size(); i++) {
				entryInfo = entryColl.get(i);
				String voucherId = entryInfo.getDestObjectID();
				voucherIds.add(voucherId);

			}
			IVoucher iVoucher = VoucherFactory.getRemoteInstance();
			SelectorItemCollection cel = new SelectorItemCollection();
			cel.add("*");
			cel.add("creator.*");
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().addObjectCollection(cel);
			FilterInfo filter = new FilterInfo();

			FilterItemInfo filterItem = new FilterItemInfo("id", voucherIds,
					CompareType.INCLUDE);
			filter.getFilterItems().add(filterItem);
			view.setFilter(filter);
			colVoucher = iVoucher.getVoucherCollection(view);
		}
		if (colVoucher == null) {
			colVoucher = new VoucherCollection();
		}
		return colVoucher;
	}

	/**
	 * 通过单据id找到单据的number
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static String getBillNumber(String id) throws Exception {
		// IBillBase iBillBase = BillBaseFactory.getRemoteInstance();
		// IObjectPK pk = new ObjectUuidPK(id);
		// BillBaseInfo info = iBillBase.getBillBaseInfo(pk);
		// return info.getNumber();
		if (id == null || id.trim().length() == 0) {
			return null;
		}
		IObjectPK pk = new ObjectUuidPK(id);
		BOSUuid bosId = BOSUuid.read(id);

		IObjectValue info = DynamicObjectFactory.getRemoteInstance().getValue(
				bosId.getType(), pk);
		// BillBaseInfo info = iBillBase.getBillBaseInfo(pk);
		return info.getString("number");
	}

	/**
	 * 
	 * 描述：remove virtual companys
	 * 
	 * @param fullOrgUnits
	 *            FullOrgUnitInfo Array
	 * @return FullOrgUnitInfo Array that have removed virtual companys
	 * @throws Exception
	 * @author:liupd 创建时间：2005-8-25
	 *               <p>
	 */
	public static Object[] removeVirtualCompany(Object[] fullOrgUnits)
			throws Exception {
		List list = new ArrayList();
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add(new SelectorItemInfo("id"));
		selectors.add(new SelectorItemInfo("number"));
		selectors.add(new SelectorItemInfo("name"));
		selectors.add(new SelectorItemInfo("isOnlyUnion"));
		FullOrgUnitInfo orgUnit = null;
		CompanyOrgUnitInfo company = null;
		for (int i = 0; i < fullOrgUnits.length; i++) {
			orgUnit = ((FullOrgUnitInfo) fullOrgUnits[i]);
			company = CompanyOrgUnitFactory.getRemoteInstance()
					.getCompanyOrgUnitInfo(new ObjectUuidPK(orgUnit.getId()),
							selectors);
			if (!company.isIsOnlyUnion()) {
				list.add(orgUnit);
			}
		}

		return list.toArray();
	}

	/**
	 * 查看将要提交的流程
	 * 
	 * @author fengrenfei
	 * @param ui
	 * @param bosType
	 * @throws Exception
	 */
	public static void viewSubmitProccess(CoreUIObject ui, BOSObjectType bosType)
			throws Exception {
		if (FMHelper.isEmpty(bosType)) {
			return;
		}
//		IWfDefineService service = (IWfDefineService) BOSObjectFactory
//				.createRemoteBOSObject(CoreBillEditUI.WfDefineServiceName,
//						IWfDefineService.class);
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();

		String procDefID = service.findSubmitProcDef(bosType, SysContext
				.getSysContext().getCurrentUserInfo().getId().toString());
		if (procDefID != null) {
			// 显示UI
			ProcessDefInfo processDefInfo = service
					.getProcessDefInfo(procDefID);
			ProcessDef processDef = EnactmentServiceFactory
					.createRemoteEnactService().getProcessDefByDefineHashValue(
							processDefInfo.getMd5HashValue());
			// String kpdl = processDefInfo.getKpdl();
			Locale currentLocale = SysContext.getSysContext().getLocale();
			// ProcessDef processDef = KpdlParser.parseKpdl(kpdl,
			// currentLocale);

			String procDefDiagramTitle = processDef.getName(currentLocale);

			UIContext uiContext = new UIContext(ui);
			uiContext.put("define", processDef);
			uiContext.put("title", procDefDiagramTitle);

			String className = BasicShowWfDefinePanel.class.getName();
			IUIFactory uiFactory = UIFactory.createUIFactory(getEditUIMode(ui));
			IUIWindow uiWindow = uiFactory.create(className, uiContext);

			uiWindow.show();
		} else {
			MsgBox.showInfo(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotDef"));
			// MessageBox("没有对应流程");
		}

	}

	/**
	 * submitWF 描述：
	 * 
	 * @param ui
	 * @param bosType
	 * @param opertion
	 * @throws Exception
	 * @author:liupd 创建时间：2005-9-6
	 *               <p>
	 */
	public static void viewProccessDef(CoreUIObject ui, String funcName,
			String packageName, String operationName) throws Exception {

//		IWfDefineService service = (IWfDefineService) BOSObjectFactory
//				.createRemoteBOSObject(CoreBillEditUI.WfDefineServiceName,
//						IWfDefineService.class);
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();

		String procDefID = service.findProcDef(SysContext.getSysContext()
				.getCurrentUserInfo().getId().toString(), funcName,
				 operationName);

		if (procDefID != null) {
			// 显示UI
			ProcessDefInfo processDefInfo = service
					.getProcessDefInfo(procDefID);
			// String kpdl = processDefInfo.getKpdl();
			ProcessDef processDef = EnactmentServiceFactory
					.createRemoteEnactService().getProcessDefByDefineHashValue(
							processDefInfo.getMd5HashValue());
			Locale currentLocale = SysContext.getSysContext().getLocale();
			// ProcessDef processDef = KpdlParser.parseKpdl(kpdl,
			// currentLocale);

			String procDefDiagramTitle = processDef.getName(currentLocale);

			UIContext uiContext = new UIContext(ui);
			uiContext.put("define", processDef);
			uiContext.put("title", procDefDiagramTitle);

			String className = BasicShowWfDefinePanel.class.getName();
			IUIFactory uiFactory = UIFactory.createUIFactory(getEditUIMode(ui));
			IUIWindow uiWindow = uiFactory.create(className, uiContext);

			uiWindow.show();
		} else {
			MsgBox.showInfo(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotDef"));
			// MessageBox("没有对应流程");
		}

	}

	/**
	 * 查看处理的流程
	 * 
	 * @author fengrenfei
	 * @param ui
	 * @param id
	 * @throws BOSException
	 */
	public static void viewDoProccess(CoreUIObject ui, String id)
			throws BOSException {
		// String selectId = null;
		if (id == null) {

		}
		IEnactmentService service2 = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = service2
				.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if ("open.running".equals(procInsts[i].getState())) {
				instInfo = procInsts[i];
			}
		}
		if (instInfo == null) {

			MsgBox.showInfo(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotInstance"));
			// MessageBox("没有对应流程实例");
		} else {
			// 显示UI
			showWorkflowDiagram(ui, instInfo);
		}

	}
	
	public static void viewWorkFlowForEditUI(CoreUIObject ui, String id) throws BOSException {
		if (id == null) {
			return;
		}
        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo processInstInfo = null;
        ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(id);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if (procInsts[i].getState().startsWith("open")) {
            	processInstInfo = procInsts[i];
            }
        }
        if (processInstInfo == null) {
        	//如果没有运行时流程，判断是否有可以展现的流程图并展现
        	procInsts = service.getAllProcessInstancesByBizobjId(id);
        	if(procInsts== null || procInsts.length <=0)
        		FDCMsgBox.showInfo(ui, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_WFHasNotInstance"));
        	else if(procInsts.length ==1){
        		showWorkflowDiagram(ui, procInsts[0]);
        	}else{
        		UIContext uiContext = new UIContext(ui);
				uiContext.put("procInsts", procInsts);
				String className = ProcessRunningListUI.class.getName();
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className, uiContext);
				uiWindow.show();
        	}
		} else {
			showWorkflowDiagram(ui, processInstInfo);
        }
	}

	/**
	 * @param ui
	 * @param instInfo
	 * @throws UIException
	 */
	private static void showWorkflowDiagram(CoreUIObject ui, ProcessInstInfo processInstInfo) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIMode(ui)).create(className, uiContext);
		uiWindow.show();
	}

	/**
	 * 描述：查看多级审批结果
	 * 
	 * @param ui
	 * @param id
	 * @throws BOSException
	 * @author:wangweidong 创建时间：2006-3-30
	 *                     <p>
	 */
	public static void viewAuditResult(CoreUIObject ui, String id)
			throws BOSException {

		if (id == null || id.trim().length() < 1) {
			return;
		}

		MultiApproveUtil.showApproveHis(BOSUuid.read(id),
				UIModelDialogFactory.class.getName(), ui);
	}

	/**
	 * 描述：查看工作流的下一步处理人
	 * 
	 * @param ui
	 * @param id
	 * @throws BOSException
	 * @author:wangweidong 创建时间：2006-3-30
	 *                     <p>
	 */
	public static void viewNextPerson(CoreUIObject ui, String id)
			throws BOSException {
		if (id == null) {
			return;
		}
		DesignateNextActivityPerformerUI
				.designateNextActivityPerformerByBillId(ui, id);
	}

	/**
	 * 获取编辑（包括新增）界面的打开方式（页签、新窗口、模态窗口）
	 */
	static public String getEditUIMode(CoreUIObject ui) {
		Object win = ui.getUIWindow();
		if (win instanceof UINewFrame) {
			return UIFactoryName.NEWWIN;
		} else if (win instanceof UIDialog) {
			return UIFactoryName.MODEL;
		}

		return UIFactoryName.NEWTAB;
	}

	/**
	 * 
	 * 描述：给Table Cell赋BigDecimal值，用于把计算器返回值赋给Cell，增加了最大值的校验
	 * 
	 * @param ui
	 *            当前UI
	 * @param cell
	 *            Table Cell
	 * @param number
	 *            BigDecimal Value
	 * @author:liupd 创建时间：2005-9-1
	 *               <p>
	 */
	public static void setNumberValueForCell(Component ui, ICell cell,
			BigDecimal number) {
		if (cell != null && !cell.getStyleAttributes().isLocked()
				&& number != null) {
			if (!isExceedMaxValue(ui, number)) {
				cell.setValue(number);
			}
		}

	}

	/**
	 * 描述：检查数字是否超过最大值
	 * 
	 * @param ui
	 *            当前UI
	 * @param number
	 *            要检验的数字
	 * @return true - exceed, false - not exceed
	 * @author:liupd 创建时间：2005-9-1
	 *               <p>
	 */
	public static boolean isExceedMaxValue(Component ui, BigDecimal number) {
		if (number.compareTo(FMConstants.MAX_VALUE) > 0) {
			MsgBox.showWarning(ui, formatMessage(RES, "numberValueTooLarge",
					null));
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述：格式化资源，如果参数args为null或长度为0，则取资源并返回，否则取资源，并按照args进行格式化后返回。
	 * 
	 * @param resPath
	 *            资源路径
	 * @param resName
	 *            资源项名称
	 * @param args
	 *            参数
	 * @return 格式化后的资源
	 * @author:liupd 创建时间：2005-9-1
	 *               <p>
	 */
	public static String formatMessage(String resPath, String resName,
			Object[] args) {
		if (resPath == null || resName == null) {
			throw new IllegalArgumentException("resPath or resName is nul!");
		}
		String str = EASResource.getString(resPath, resName);
		if (args == null || args.length == 0) {
			return str;
		}
		return MessageFormat.format(str, args);
	}

	/**
	 * 
	 * 描述：格式化资源，如果参数args为null或长度为0，则取资源并返回，否则取资源，并按照args进行格式化后返回。
	 * 
	 * @param resPath
	 *            资源路径
	 * @param resName
	 *            资源项名称
	 * @param args
	 *            参数
	 * @return 格式化后的资源
	 * @author:liupd 创建时间：2005-9-1
	 *               <p>
	 */
	public static String formatMessage(String str,Object[] args) {
		if (str==null||args == null || args.length == 0) {
			return str;
		}
		return MessageFormat.format(str, args);
	}
	/**
	 * 
	 * 描述：清除ToolBar上多余的分割线
	 * 
	 * @param toolBar
	 * @author:liupd 创建时间：2005-9-5
	 *               <p>
	 */
	public static void clearSeparatorOnToolBar(KDToolBar toolBar) {
		int size = toolBar.getComponentCount();
		Component c = null;
		boolean isSeparator = false;
		int lastVisibleBtnPosition = 0;
		for (int i = 0; i < size; i++) {
			c = toolBar.getComponentAtIndex(i);
			if (c.isVisible()) {
				if (c instanceof Separator) {
					if (isSeparator) {
						c.setVisible(false);
					}
					isSeparator = true;
				} else {
					isSeparator = false;
					lastVisibleBtnPosition = i;
				}
			}
		}

		for (int i = lastVisibleBtnPosition; i < size; i++) {
			c = toolBar.getComponentAtIndex(i);
			if (c instanceof Separator) {
				c.setVisible(false);
			}
		}

	}

	/**
	 * 描述：
	 * 
	 * @param menu
	 * @author:wangweidong 创建时间：2006-9-8
	 *                     <p>
	 */
	public static void clearSeparatorOnMenu(KDMenu menu) {

		int size = menu.getMenuComponentCount();

		Component c = null;
		boolean isSeparator = false;
		int lastVisibleBtnPosition = 0;
		for (int i = 0; i < size; i++) {
			c = menu.getMenuComponent(i);
			if (c.isVisible()) {
				if (c instanceof JSeparator) {
					if (isSeparator) {
						c.setVisible(false);
					}
					isSeparator = true;
				} else {
					isSeparator = false;
					lastVisibleBtnPosition = i;
				}
			}
		}

		for (int i = lastVisibleBtnPosition; i < size; i++) {
			c = menu.getMenuComponent(i);
			if (c instanceof JSeparator) {
				c.setVisible(false);
			}
		}
	}

	/**
	 * 
	 * 描述：禁用Table的某些个性化设置
	 * 
	 * @param tHelper
	 * @author:liupd 创建时间：2005-9-8
	 *               <p>
	 */
	public static void disableSomeTableUserPreferenceConf(
			TablePreferencesHelper tHelper) {
		tHelper.setCanSetBGColor(false);
		tHelper.setCanSetForeColor(false);
	}

	public static String getAccountTabled(String strCompanyId)
			throws BOSException, EASBizException {
		if (FMHelper.isEmpty(strCompanyId)) {
			return null;
		}
		ICompanyOrgUnit company = CompanyOrgUnitFactory.getRemoteInstance();
		CompanyOrgUnitInfo comInfo = company
				.getCompanyOrgUnitInfo(new ObjectUuidPK(strCompanyId));
		String strAccountTableId = comInfo.getAccountTable().getId().toString();
		return strAccountTableId;
	}

	/**
	 * get the company's gl current peirod
	 * 
	 * @param companyInfo
	 * @return
	 * @throws NumberFormatException
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static PeriodInfo getGLCurrentPeriod(Component comp,
			CompanyOrgUnitInfo companyInfo) throws EASBizException,
			BOSException {
		PeriodInfo currentPeriod = SystemStatusCtrolUtils.getCurrentPeriod(
				null, SystemEnum.GENERALLEDGER, companyInfo);
		if (currentPeriod == null) {
			MsgBox.showWarning(comp, EASResource.getString(
					"com.kingdee.eas.fm.common.FMResource",
					"GL_CurrentPeriod_NULL"));
			SysUtil.abort();
		}
		return currentPeriod;
	}

	/**
	 * 
	 * 描述：单张单据联查
	 * 
	 * @param owner
	 * @param billId
	 * @param company
	 * @throws Exception
	 * @author jxd 创建时间：2005-11-8
	 */
	public static void viewDetailBill(CoreUIObject owner, String billId,
			CompanyOrgUnitInfo company) throws Exception {
		String[] idList = new String[1];
		idList[0] = billId;
		viewDetailBill(owner, idList, company);
	}

	/**
	 * 由于日记帐的一个实体对应两个界面，特殊处理
	 * 
	 * @param owner
	 * @param billId
	 * @param company
	 * @throws Exception
	 */
	public static void viewJournal(CoreUIObject owner, String billId,
			CompanyOrgUnitInfo company) throws Exception {
		if (FMHelper.isEmpty(billId)) {
			throw new Exception("idList is null or empty!");
		}

		// IMetaDataLoader loader = MetaDataLoaderFactory
		// .getRemoteMetaDataLoader();

		// BOSObjectType bosType = BOSUuid.read(billId).getType();

		// isBillExists(billId, bosType);

		// EntityObjectInfo entity = loader.getEntity(bosType);
		String popUi = null;

		UIContext uiContext = new UIContext(owner);
		uiContext.put("company", company);
		IUIWindow uiWindow = null;

		// 如果选中行只关联一个利息单，则直接显示利息单查看界面
		uiContext.put(UIContext.ID, billId);
		// popUi = entity.getExtendedProperty("editUI");
		IJournal journal = JournalFactory.getRemoteInstance();
		ObjectUuidPK pk = new ObjectUuidPK(billId);
		JournalInfo j = journal.getJournalInfo(pk);
		if (j.getType() == JournalTypeEnum.BANK) {
			popUi = "com.kingdee.eas.fi.cas.client.BankJournalUI";
		} else {
			popUi = "com.kingdee.eas.fi.cas.client.CASJournalUI";

		}

		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(popUi,
				uiContext, null, "VIEW");

		uiWindow.show();
	}

	/**
	 * 描述：联查单据
	 * 
	 * @param ledgerUI
	 * @param idList
	 * @throws UIException
	 * @author:liupd 创建时间：2005-11-7
	 *               <p>
	 */
	public static void viewDetailBill(CoreUIObject owner, String[] idList,
			CompanyOrgUnitInfo company) throws Exception {

		if (idList == null || idList.length == 0) {
			throw new Exception("idList is null or empty!");
		}

		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();

		BOSObjectType bosType = getBOSType(owner, idList);

		idList = getValidIds(idList, bosType);

		if (bosType == null || idList == null || idList.length < 1) {
			return;
		}
		if (bosType.equals(new VoucherEntryInfo().getBOSType())) {
			// transe id;;
			IVoucherEntry voucher = VoucherEntryFactory.getRemoteInstance();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", FMHelper.asSet(idList),
							CompareType.INCLUDE));

			view.setFilter(filter);
			VoucherEntryCollection entry = voucher
					.getVoucherEntryCollection(view);
			Set voucherIds = new HashSet();
			for (int i = 0; i < entry.size(); i++) {
				voucherIds.add(entry.get(i).getBill().getId().toString());
			}

			idList = (String[]) voucherIds.toArray(new String[] {});
			bosType = new VoucherInfo().getBOSType();

		}

		// isBillExists(billId, bosType);

		EntityObjectInfo entity = loader.getEntity(bosType);
		String popUi = null;

		UIContext uiContext = new UIContext(owner);
		uiContext.put("company", company);
		// add by yjz 处理权限用
		uiContext.put(OrgType.Company, company);
		IUIWindow uiWindow = null;
		String infoID = null;

		// 如果选中行只关联一个利息单，则直接显示利息单查看界面
		if (idList.length == 1) {
			infoID = idList[0];
			uiContext.put(UIContext.ID, infoID);
			popUi = entity.getExtendedProperty("editUI");
			uiWindow = UIFactory.createUIFactory(getEditUIMode(owner)).create(
					popUi, uiContext, null, "FINDVIEW");
		}
		// 如果选中行关联多个利息单，则先显示利息单list界面
		else {
			// 将选中行所关联的利息单ID集合塞给ui上下文
			uiContext.put(UIContext.IDLIST, idList);
			popUi = entity.getExtendedProperty("listUI");
			// 此参数是为了控制显示的单张单据是否可以编辑
			uiContext.put("IsFromOtherSys", "findview");
			uiWindow = UIFactory.createUIFactory(getEditUIMode(owner)).create(
					popUi, uiContext, null, "FINDVIEW");
		}

		uiWindow.show();
	}

	private static String[] getValidIds(String[] idList, BOSObjectType bosType) {

		if (idList == null || bosType == null) {
			return null;
		}

		Set idSet = new HashSet();
		for (int i = 0, len = idList.length; i < len; i++) {
			if (bosType.equals(BOSUuid.read(idList[i]).getType())) {
				idSet.add(idList[i]);
			}
		}
		return (String[]) idSet.toArray(new String[] {});
	}

	private static BOSObjectType getBOSType(IUIObject ui, String[] idList) {
		if (FMHelper.isEmpty(idList)) {
			return null;
		}
		Set set = new HashSet();
		BOSObjectType bosType = null;
		for (int i = 0; i < idList.length; i++) {
			bosType = BOSUuid.read(idList[i]).getType();
			set.add(bosType);
		}
		if (set == null || set.size() == 0) {
			return null;
		}

		if (set.size() > 1) {
			try {
				bosType = BizTypeChooseUI.showDialogWindow(ui, set);
			} catch (UIException e) {
				e.printStackTrace();
				return null;
			}

		}
		return bosType;
		// return (BOSObjectType) set.toArray()[0];
	}

	/**
	 * 描述：判断单据是否存在
	 * 
	 * @param billId
	 * @param bosType
	 * @throws BOSException
	 * @author:liupd 创建时间：2005-11-7
	 *               <p>
	 */
	public static void isBillExists(String billId, BOSObjectType bosType)
			throws BOSException {
		IDynamicObject doCtrl = DynamicObjectFactory.getRemoteInstance();
		try {
			doCtrl.getValue(bosType, "select id where id='" + billId + "'");
		} catch (DataAccessException exp) {
			MsgBox.showError(EASResource.getString(RES, "billNoExist"));
			SysUtil.abort(exp);
		}
	}

	/**
	 * 判断是否有辅助账
	 * 
	 */
	public static boolean isHasAsstAcct(String accountId) {
		String sql = "SELECT COUNT(*) AS fsize FROM T_BD_AccountView acct"
				+ " INNER JOIN T_BD_AsstAccount assAcct ON acct.FCAA = assAcct.FID INNER JOIN "
				+ "  T_BD_AsstActGroupDetail assAcctDetail ON "
				+ "  assAcct.FID = assAcctDetail.FAsstAccountID INNER JOIN"
				+ "  T_BD_AsstActType assAcctType ON "
				+ "  assAcctDetail.FAsstActTypeID = assAcctType.FID"
				+ "  WHERE assAcctType.FAsstHGAttribute = 'bankAccount' AND acct.FID = '";
		sql += accountId + "'";
		try {
			IRowSet rowSet = SQLExecutorFactory.getRemoteInstance(sql)
					.executeSQL();
			if (rowSet.next()) {
				Object oSize = rowSet.getObject("fsize");
				if (oSize != null) {
					int size = TypeConversionUtils.objToInt(oSize);
					if (size > 0) {
						return true;
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort(e);
		} catch (SQLException e) {
			e.printStackTrace();
			SysUtil.abort(e);
		}
		return false;

	}

	/**
	 * 给文本框增加全选事件处理
	 * 
	 * @param source
	 * @param selectAll
	 *            是否要求选中全部，true选中
	 * @param removeZero
	 *            是否在编辑和显示时去0，true去0
	 */
	public static void setTextFieldFormat(JComponent source,
			final boolean selectAll, final boolean removeZero) {
		for (int i = 0; i < source.getComponentCount(); i++) {
			final Component c = source.getComponent(i);
			if (c instanceof JComponent) {
				if (((JComponent) c).getComponentCount() > 0)
					setTextFieldFormat((JComponent) c, selectAll, removeZero);
				else {
					if (c instanceof KDFormattedTextField) {
						((KDFormattedTextField) c)
								.setSelectAllOnFocus(selectAll);
						((KDFormattedTextField) c)
								.setRemoveingZeroInDispaly(removeZero);
						((KDFormattedTextField) c)
								.setRemoveingZeroInEdit(removeZero);
					}
					if (c instanceof KDTextField) {
						((KDTextField) c).addFocusListener(new FocusListener() {

							public void focusGained(FocusEvent e) {
								if (selectAll)
									((KDTextField) c).selectAll();
							}

							public void focusLost(FocusEvent e) {
							}
						});
					}
				}
			}
		}
	}

	/**
	 * 描述：设置容器所有控件是否可用
	 * 
	 * @param source
	 * @param enable
	 */
	public static void setComponentEnable(JComponent source,
			final boolean enable) {
		for (int i = 0; i < source.getComponentCount(); i++) {
			final Component c = source.getComponent(i);
			if (c instanceof JComponent
					&& ((JComponent) c).getComponentCount() > 0) {
				if (c instanceof KDBizPromptBox) {
					((KDBizPromptBox) c).setEnabled(enable);
				}
				if (c instanceof KDDatePicker) {
					((KDDatePicker) c).setEditable(enable);
				}
				if (c instanceof KDComboBox) {
					((KDComboBox) c).setEnabled(enable);
				}
				if (c instanceof KDTable) {
					((KDTable) c).getStyleAttributes().setLocked(!enable);
				}
				setComponentEnable((JComponent) c, enable);
			} else {
				if (c instanceof KDFormattedTextField) {
					((KDFormattedTextField) c).setEditable(enable);
				}
				if (c instanceof KDTextField) {
					((KDTextField) c).setEditable(enable);
				}
				if (c instanceof KDWorkButton) {
					((KDWorkButton) c).setEnabled(enable);
				}
			}
		}
	}

	/**
	 * 设置第一个焦点控件
	 */
	public static void setFirstFocus(Component[] controls) {
		for (int i = 0; i < controls.length; i++) {
			Component control = controls[i];
			if (control.isVisible() && control.isEnabled()) {
				control.requestFocus();
				break;
			}
		}
	}

	/**
	 * 
	 * 描述：得到%r{0.0000000000}f格式字符串
	 * 
	 * @param prec
	 *            精度
	 * @return
	 * @author:yangzk 创建时间：2006-3-4
	 *                <p>
	 *                @deprecated
	 *                @see FDCHelper.getNumberFtm(int)
	 */
	public static String getKDTNumFormat(int prec) {
		if (prec <= 0) {
			return "%r-[]{#,##0}f";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("%r-[]{#,##0.");
		for (int i = 0; i < prec; i++) {
			sb.append("0");
		}
		sb.append("}f");
		return sb.toString();
	}

	/**
	 * 如果为当前为实体公司则隐藏列，否则显示
	 * 
	 * @param kdt
	 * @param colName
	 *            要隐藏的列(即公司名列)
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void hideIfBizUnitCompany(KDTable kdt, String colName)
			throws EASBizException, BOSException {
		IContextHelper helper = ContextHelperFactory.getRemoteInstance();
		IColumn col = kdt.getColumn(colName);
		if (helper.isOnlyUnion(helper.getCurrentCompany())) {
			col.getStyleAttributes().setHided(false);
		} else {
			col.getStyleAttributes().setHided(true);
		}
	}

	/**
	 * 设定控件的是否可编辑
	 * 
	 * @param component
	 *            要设定的控件
	 * @param editable
	 * @author yangzk
	 */
	public static void setEditable(JComponent component, boolean editable) {
		if (component instanceof KDBizPromptBox) {
			KDBizPromptBox bizBox = (KDBizPromptBox) component;
			bizBox.setEditable(editable);
			bizBox.setEnabled(editable);
		} else if (component instanceof KDComboBox) {
			KDComboBox cbx = (KDComboBox) component;
			cbx.setEnabled(editable);
		} else if (component instanceof KDDatePicker) {
			KDDatePicker cbx = (KDDatePicker) component;
			cbx.setEditable(editable);
			cbx.setEnabled(editable);
		} else if (component instanceof KDCheckBox) {
			KDCheckBox cb = (KDCheckBox) component;
			cb.setEnabled(editable);
		} else if (component instanceof IKDTextComponent
				&& !(component instanceof KDComboBox)) {
			((IKDTextComponent) component).setEditable(editable);
		}
	}

	/**
	 * 锁定表格某一行
	 * 
	 * @author yangzk
	 * @param kdt
	 * @param rowIndex *
	 * @param colNames
	 *            要锁定的列名（如果为nil则锁定所有列)
	 */
	public static void lockRow(KDTable kdt, int rowIndex, String[] colNames) {
		IRow row = kdt.getRow(rowIndex);
		if (colNames != null) {
			for (int i = 0, n = colNames.length; i < n; i++) {
				ICell cell = row.getCell(colNames[i]);
				cell.getStyleAttributes().setLocked(true);
			}
		} else {
			for (int i = 0, n = kdt.getColumnCount(); i < n; i++) {
				ICell cell = row.getCell(i);
				cell.getStyleAttributes().setLocked(true);
			}
		}
	}

	/**
	 * 验证大于零 描述：
	 * 
	 * @param ui
	 * @param txt
	 * @author:yangzk 创建时间：2006-3-8
	 *                <p>
	 */
	public static void verifyMoreThanZero(CoreUIObject ui,
			KDFormattedTextField txt) {
		FMClientVerifyHelper.verifyEmpty(ui, txt);
		String text = "";
		if (txt.getParent() instanceof KDLabelContainer) {
			text = ((KDLabelContainer) txt.getParent()).getBoundLabelText();
		}

		if (txt.getBigDecimalValue().signum() <= 0) {
			text += EASResource.getString(
					"com.kingdee.eas.fm.fin.client.FinClientResource",
					"MustMoreThanOne");
			txt.requestFocus();
			MsgBox.showWarning(ui, text);
			SysUtil.abort();
		}
	}

	/**
	 * 验证非负 描述：
	 * 
	 * @param ui
	 * @param txt
	 * @author:yangzk 创建时间：2006-3-8
	 *                <p>
	 */
	public static void verifyNotNegtive(CoreUIObject ui,
			KDFormattedTextField txt) {
		FMClientVerifyHelper.verifyEmpty(ui, txt);
		String text = "";
		if (txt.getParent() instanceof KDLabelContainer) {
			text = ((KDLabelContainer) txt.getParent()).getBoundLabelText();
		}
		if (txt.getBigDecimalValue().signum() < 0) {
			text += EASResource.getString(
					"com.kingdee.eas.fm.fin.client.FinClientResource",
					"MustNotNegtive");
			txt.requestFocus();
			MsgBox.showWarning(ui, text);
			SysUtil.abort();
		}
	}

	/**
	 * 控制虚体公司不准执行操作 描述： checkOnlyUnion
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:yangzk 创建时间：2006-3-6
	 *                <p>
	 */
	public static void checkOnlyUnion(String oprtState) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo company = ContextHelperFactory.getRemoteInstance()
				.getCurrentCompany();
		// if(ContextHelperFactory.getRemoteInstance().isOnlyUnion(company)&&!oprtState.equalsIgnoreCase("FINDVIEW"))
		// 增加虚体也可以查看 chenjin 2006-08-22
		if (ContextHelperFactory.getRemoteInstance().isOnlyUnion(company)
				&& !(oprtState.equalsIgnoreCase("FINDVIEW") || oprtState
						.equalsIgnoreCase("VIEW"))) {
			throw new FMException(FMException.UNIONCANNOTOPRT);
		}
	}

	public static void checkOnlyUnion() throws EASBizException, BOSException {
		CompanyOrgUnitInfo company = ContextHelperFactory.getRemoteInstance()
				.getCurrentCompany();
		if (ContextHelperFactory.getRemoteInstance().isOnlyUnion(company)) {
			throw new FMException(FMException.UNIONCANNOTOPRT);
		}
	}

	/**
	 * 光标移进去时按实际位数显示，移出按币别精度显示 描述：
	 * 
	 * @param txt
	 * @author:yangzk 创建时间：2006-3-7
	 *                <p>
	 */
	public static void initCurrencyField(BasicFormattedTextField txt) {
		txt.setHorizontalAlignment(SwingConstants.RIGHT);
		if (txt instanceof KDFormattedTextField) {
			KDFormattedTextField ftTxt = (KDFormattedTextField) txt;
			ftTxt.setDataType(BigDecimal.class);
			ftTxt.setRemoveingZeroInDispaly(false);
			ftTxt.setRemoveingZeroInEdit(true);
		} else if (txt instanceof KDNumberTextField) {
			KDNumberTextField numText = (KDNumberTextField) txt;
			numText.setDataType(BigDecimal.class);
			numText.setRemoveingZeroInDispaly(false);
			numText.setRemoveingZeroInEdit(true);
		}
	}

	/**
	 * 
	 * 描述：按实际位数显示
	 * 
	 * @param txt
	 * @author:yangzk 创建时间：2006-3-7
	 *                <p>
	 */
	public static void initDecimalField(BasicFormattedTextField txt) {

		txt.setHorizontalAlignment(SwingConstants.RIGHT);
		if (txt instanceof KDFormattedTextField) {
			KDFormattedTextField ftTxt = (KDFormattedTextField) txt;
			ftTxt.setDataType(BigDecimal.class);
			ftTxt.setRemoveingZeroInDispaly(true);
		} else if (txt instanceof KDNumberTextField) {
			KDNumberTextField numText = (KDNumberTextField) txt;
			numText.setDataType(BigDecimal.class);
			numText.setRemoveingZeroInDispaly(true);
		}
	}

	public static void addSqlMenu(final CoreUIObject uiObject, KDMenu menu) {		
		menu.add(new AbstractHidedMenuItem("ctrl shift F12") {
			public void action_actionPerformed() {
				//添加启动参数
				String p = System.getProperty("DevFDC");
				if(p==null||!p.equals("fdc")){
					//密码认证 by sxhong
					KDPasswordField pwd = new KDPasswordField();

					Object[] message = {"输入密码:", pwd};

					int res = JOptionPane.showConfirmDialog(uiObject, message, "请输入密码", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					char cs[]=pwd.getPassword();
					if(res!=JOptionPane.OK_OPTION||cs==null||cs.length<2
							||cs[cs.length-2]!=':'||cs[cs.length-1]!=')'){
						return;
					}
				}
				try {
					IUIFactory fy = UIFactory
							.createUIFactory(UIFactoryName.NEWTAB);
					UIContext uiContext = new UIContext(uiObject);
					IUIWindow wnd = fy.create(
							"com.kingdee.eas.fm.common.client.FMIsqlUI",
							uiContext);
					wnd.show();
				} catch (UIException e) {
					SysUtil.abort(e);
				}
			}

		});
		if(menu.getMenuComponent(menu.getMenuComponentCount()-1) instanceof AbstractHidedMenuItem){
			menu.getMenuComponent(menu.getMenuComponentCount()-1).setVisible(false);
		}
	}

	/**
	 * 设定f7的过滤条件为选择不出任何数据
	 * 
	 * @param bizBox
	 */
	public static EntityViewInfo getNothingFilter() {
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(new FilterInfo());
		evi.getFilter().getFilterItems().add(
				new FilterItemInfo("id", "", CompareType.EQUALS));
		return evi;

	}

	public static void setActionEnable(ItemAction[] actions, boolean enabled) {
		if (actions == null) {
			return;
		}
		for (int i = 0, len = actions.length; i < len; i++) {
			setActionEnable(actions[i], enabled);
		}
	}

	public static void setCompEnable(IKDComponent[] components, boolean enabled) {

		if (components == null) {
			return;
		}

		for (int i = 0, len = components.length; i < len; i++) {
			if (components[i] instanceof KDTextField) {
				((KDTextField) components[i]).setEnabled(enabled);
			} else if (components[i] instanceof KDDatePicker) {
				((KDDatePicker) components[i]).setRequired(enabled);
				((KDDatePicker) components[i]).setEnabled(enabled);
			} else if (components[i] instanceof KDFormattedTextField) {
				((KDFormattedTextField) components[i]).setEnabled(enabled);
			} else if (components[i] instanceof KDComboBox) {
				((KDComboBox) components[i]).setEnabled(enabled);
			} else if (components[i] instanceof KDBizPromptBox) {
				((KDBizPromptBox) components[i]).setEnabled(enabled);
			} else if (components[i] instanceof KDCheckBox) {
				((KDCheckBox) components[i]).setEnabled(enabled);
			} else if (components[i] instanceof KDSpinner) {
				((KDSpinner) components[i]).setEnabled(enabled);

			}
		}
	}

	public static boolean isAcctHasAcctBankAssis(String acctViewId)
			throws EASBizException, BOSException, UuidException {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT T_BD_AsstActType.FRealtionDataObject AS tName,"
						+ " A.FID FROM T_BD_AccountView A INNER JOIN"
						+ " T_BD_AsstAccount ON A.FCAA = T_BD_AsstAccount.FID INNER JOIN"
						+ " T_BD_AsstActGroupDetail ON"
						+ " T_BD_AsstAccount.FID = T_BD_AsstActGroupDetail.FAsstAccountID INNER JOIN"
						+ " T_BD_AsstActType ON"
						+ " T_BD_AsstActGroupDetail.FAsstActTypeID = T_BD_AsstActType.FID"
						+ "  WHERE (T_BD_AsstActType.FRealtionDataObject = 'T_BD_AccountBanks') and A.fid='"
						+ acctViewId + "'");
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sb.toString())
				.executeSQL();
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return false;
		// SelectorItemCollection sels=new SelectorItemCollection();
		// sels.add("*");
		// sels.add("CAA.asstActGpDt.*");
		// sels.add("CAA.asstActGpDt.asstActType.*");
		// AccountViewInfo viewInfo =
		// AccountViewFactory.getRemoteInstance().getAccountViewInfo(
		// new ObjectUuidPK(BOSUuid.read(acctViewId)),
		// sels);
		// if(viewInfo.getCAA()==null){
		// return false;
		// }
		// AsstActGroupDetailCollection asstActGpDt =
		// viewInfo.getCAA().getAsstActGpDt();
		// for (int i = 0; i < asstActGpDt.size(); i++) {
		// AsstActGroupDetailInfo info = asstActGpDt.get(i);
		// if(info.getAsstActType()==null){
		// continue;
		// }
		// if(info.getAsstActType().getRealtionDataObject()==null){
		// continue;
		// }
		// if
		// (info.getAsstActType().getRealtionDataObject().equalsIgnoreCase("T_BD_AccountBanks"))
		// {
		// return true;
		// }
		// }
		// return false;
	}

	/**
	 * 
	 * 描述：根据源币别、目标币别查找汇率精度，如果找不到汇率或汇率精度，返回默认汇率精度3
	 * 
	 * @param sourceCurID
	 *            源币别ID
	 * @param desCurID
	 *            目标币别ID
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-4-21
	 *               <p>
	 */
	public static int getExchangeRatePrec(String sourceCurID, String desCurID)
			throws BOSException, EASBizException {

		ExchangeRateInfo erInfo = findExchangeRate(sourceCurID, desCurID);
		if (erInfo != null && erInfo.getExchangeAux() != null) {
			int prec = erInfo.getExchangeAux().getPrecision();
			return prec;
		}

		return DEFAULT_EXCHANGERATE_PREC;
	}

	/**
	 * 
	 * 描述：根据源币别、目标币别查找汇率精度
	 * 
	 * @param sourceCurID
	 *            源币别ID
	 * @param desCurID
	 *            目标币别ID
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-4-21
	 *               <p>
	 */
	public static ExchangeRateInfo findExchangeRate(String sourceCurID,
			String desCurID) throws BOSException, EASBizException {
		CompanyOrgUnitInfo clearingHouseCompany = ContextHelperFactory
				.getRemoteInstance().getCurrentCompany();
		ExchangeTableInfo baseExchangeTable = clearingHouseCompany
				.getBaseExchangeTable();
		if (baseExchangeTable == null) {
			MsgBox
					.showError("公司" + clearingHouseCompany.getName()
							+ "没有基本汇率表!");
			SysUtil.abort();
		}
		IObjectPK destCurrpk = new ObjectUuidPK(desCurID);
		IObjectPK excTablepk = new ObjectUuidPK(baseExchangeTable.getId());
		IExchangeRate iexchangRate = ExchangeRateFactory.getRemoteInstance();
		IObjectPK sourceCurrpk = new ObjectUuidPK(sourceCurID);
		ExchangeRateInfo erInfo = iexchangRate.getExchangeRate(excTablepk,
				sourceCurrpk, destCurrpk, new Date(System.currentTimeMillis()));
		return erInfo;
	}

	/**
	 * 获取有效期间， 实体为出纳启用期间之后，
	 * 
	 * @author fengrenfei
	 * @param curCompany
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static PeriodCollection getValidPeriodColl(
			CompanyOrgUnitInfo curCompany) {
		PeriodCollection pc2 = new PeriodCollection();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("periodType", curCompany
						.getAccountPeriodType().getId().toString(),
						CompareType.EQUALS));
		evi.getSorter().add(new SorterItemInfo("periodYear"));
		evi.getSorter().add(new SorterItemInfo("periodNumber"));
		evi.setFilter(filter);
		PeriodCollection pc = null;
		try {
			pc = PeriodFactory.getRemoteInstance().getPeriodCollection(evi);
		} catch (BOSException e) {
			SysUtil.abort(e);
		}
		if (curCompany.isIsOnlyUnion()) {// 虚体的期间范围按所有期间初始化
			pc2 = pc;
		} else {
			// 启用期间以后
			PeriodInfo startPeriod = null;
			try {
				startPeriod = SystemStatusCtrolUtils.getStartPeriod(null,
						SystemEnum.CASHMANAGEMENT, curCompany);
			} catch (Exception e) {
				SysUtil.abort(e);
			}
			if (startPeriod == null) {
				MsgBox.showError("出纳系统当前期间为空!");
				SysUtil.abort();

			}
			for (int i = 0; i < pc.size(); i++) {
				PeriodInfo p = pc.get(i);
				if (p.getBeginDate().compareTo(startPeriod.getBeginDate()) >= 0) {
					pc2.add(p);
				}
			}
		}
		return pc2;
	}

	public static TreeMap periodAssembleMap(PeriodCollection pc) {
		TreeMap result = new TreeMap();
		Iterator iter = pc.iterator();
		// 按照年份将整个期间集合分开
		while (iter.hasNext()) {
			PeriodInfo pi = (PeriodInfo) iter.next();
			Integer year = new Integer(pi.getPeriodYear());
			if (!result.containsKey(year)) {
				PeriodCollection periodSubCo = new PeriodCollection();
				periodSubCo.add(pi);
				result.put(year, periodSubCo);
			} else {
				PeriodCollection periodSubCo = (PeriodCollection) result
						.get(year);
				periodSubCo.add(pi);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		FMLoginModel.newInstance()
				.login("zgh0", "", "eas51", "eas512_0726_s05");

	}

	public static void checkUsbKeyPermission() throws PermissionException {
		UsbKeyPermissionService.checkUsbKeyPermission(null);
	}

	/**
	 * 生成目标单(只适用于只有一个目标单的情况)
	 * 
	 * @param owner
	 * @param srcBosType
	 *            源单bostype
	 * @param destBosType
	 *            目标单bostype
	 * @param billId
	 *            单据id
	 * @throws Exception
	 * 
	 * @author yangzk
	 */
	//2007.8.9 由于5.3.3botp的更改，修改是否显示目标单据的判定方式
	public static void generateDestBill(CoreBillListUI owner,
			String srcBosType, String destBosType, String billId)
			throws Exception {
		IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();

		CoreBillBaseInfo billInfo = (CoreBillBaseInfo) EJBAccessFactory
				.createRemoteInstance().getEntityInfo(billId,
						owner.getBOTPSelectors());
		CoreBillBaseCollection billCollection = new CoreBillBaseCollection();
		billCollection.add(billInfo);

		owner.beforeTransform(billCollection, destBosType);
		BOTMappingInfo botMappingInfo = botMapping.getMapping(billInfo,
				destBosType, DefineSysEnum.BTP);

		if (botMappingInfo == null) {
			throw new FMException(FMException.NODESTBILL);
		}

		IBTPManager iBTPManager = BTPManagerFactory.getRemoteInstance();

		BTPTransformResult btpResult = null;

		btpResult = iBTPManager.transformForBotp(billCollection, destBosType,
				new ObjectUuidPK(botMappingInfo.getId().toString()));

		IObjectCollection destBillCols = btpResult.getBills();
		BOTRelationCollection botRelationCols = btpResult
				.getBOTRelationCollection();
		int showUIMode = botMappingInfo.getIsShowEditUI();
		if (showUIMode == BOTMappingInfo.SHOW_EDITDUI) {
			String destBillEditUIClassName = iBTPManager
					.getEntityObjectInfoExtendPro(destBosType, "editUI");

			Map map;
			String openType;
			// 如果为模态对话框，则弹出模态对话框
			//if (owner != null && owner.getUIWindow() instanceof UINewTab) { update by renliang
			if ( owner.getUIWindow() instanceof UINewTab) {
				openType = UIFactoryName.NEWTAB;
				map = new UIContext(owner);
			} else {
				openType = UIFactoryName.MODEL;
				map = new UIContext(owner);

			}
			map.put("srcBillID", billId);
			map.put("BOTPViewStatus", new Integer(1));
			IUIWindow uiWindow = null;

			if (destBillCols.size() > 1) {
				IIDList idList = RealModeIDList.getEmptyIDList();

				for (int i = 0, count = destBillCols.size(); i < count; i++) {
					CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols
							.getObject(i);
					iBTPManager.saveRelations(destBillInfo, botRelationCols);
					idList.add(destBillInfo.getId().toString());
				}
				// 暂存后调用editUI界面
				idList.setCurrentIndex(0);
				map.put(UIContext.ID, idList.getCurrentID());
				map.put(UIContext.IDLIST, idList);

				uiWindow = UIFactory.createUIFactory(openType).create(
						destBillEditUIClassName, map, null, OprtState.EDIT);

			} else {
				IObjectValue dataObject = destBillCols.getObject(0);

				uiWindow = UIFactory.createUIFactory(openType).create(
						destBillEditUIClassName, map, null, OprtState.ADDNEW);
				IUIObject uiObject = uiWindow.getUIObject();
				dataObject.setString("sourceBillId", billId);
				if (uiObject instanceof IBotEditUI) {
					((IBotEditUI) uiObject).initDataObject(dataObject);
				}
				map.put("InitDataObject", dataObject);
				if (uiWindow.getUIObject().getDataObject() == null
						|| uiWindow.getUIObject().getDataObject().get("id") == null
						|| !uiWindow.getUIObject().getDataObject().get("id")
								.toString().equals(
										dataObject.get("id").toString())) {
					uiObject.setDataObject(dataObject);
					uiObject.loadFields();
				}

				((CoreBillEditUI) uiWindow.getUIObject())
						.setMakeRelations(botRelationCols);
			}
			uiWindow.show();
		} else {
			// 全部提交
			for (int i = 0, count = destBillCols.size(); i < count; i++) {
				CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols
						.getObject(i);

				iBTPManager.submitRelations(destBillInfo, botRelationCols);
			}
		}

	}

	/**
	 * 构造通用树
	 */
	public static TreeModel createDataTree(ITreeBase iTree, FilterInfo filter)
			throws Exception {
		if(iTree instanceof ICostAccount){
			return createDataTree(iTree,filter,FDCClientUtils.getRes("costAccount")); 
		}
		return createDataTree(iTree,filter,null);
	}

	/**
	 * 构造通用树,带入根节点名称
	 */
	public static TreeModel createDataTree(ITreeBase iTree, FilterInfo filter,
			String rootName) throws Exception {
		KDTree tree = new KDTree();
		if (rootName == null) {
			rootName = MetaDataLoaderFactory.getRemoteMetaDataLoader()
					.getBusinessObject(iTree.getType()).getAlias();
		}
		KDTreeNode root = new KDTreeNode(rootName);
		((DefaultTreeModel) tree.getModel()).setRoot(root);
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(
				new DefaultLNTreeNodeCtrl(iTree), 10, 10, filter);
		treeBuilder.buildTree(tree);
		return tree.getModel();
	}

	/**
	 * 把日期格式化为“yyyy-mm-dd”
	 * 
	 * @param date
	 * @return
	 */
	public static String formateDate(Date date) {
		DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		return dateFmt.format(date);
	}

	/**
	 * 把日期格式化为“yyyy-mm-dd”
	 * 
	 * @param date
	 * @return
	 */
	public static String formateDate(Timestamp ts) {
		Date date = new Date(ts.getTime());
		return formateDate(date);
	}

	/**
	 * 根据原币币别获取兑换当前财务组织本位币的汇率
	 * 
	 * @param ui 当前UI实例
	 * @param srcid 原币币别
	 * @param bookedDate 业务日期
	 * @return 汇率
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static ExchangeRateInfo getLocalExRateBySrcCurcy(CoreUIObject ui,
			BOSUuid srcid,CompanyOrgUnitInfo currentFIUnit,Date bookedDate) throws BOSException, EASBizException {

//		BigDecimal exRate = null;

		ExchangeTableInfo baseExchangeTable = currentFIUnit.getBaseExchangeTable();
		if (baseExchangeTable != null) {
			CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
			if (baseCurrency != null) {

				if (srcid.equals(baseCurrency.getId())) {
					return null;
//					return FDCHelper.ONE;
				}

				IExchangeRate iExchangeRate = ExchangeRateFactory.getRemoteInstance();
				ExchangeRateInfo exchangeRate = iExchangeRate.getExchangeRate(
						new ObjectUuidPK(baseExchangeTable.getId()),
						new ObjectUuidPK(srcid), new ObjectUuidPK(baseCurrency.getId()), bookedDate);
				if (exchangeRate != null) {
					return exchangeRate;
//					return exchangeRate.getConvertRate();
				} 
//				else {
//					return FDCHelper.ONE;
//				}

			} else {
				MsgBox.showWarning(ui, FDCClientUtils.getRes("noBaseCurrency"));

				SysUtil.abort();
			}

		} else {
			MsgBox.showWarning(ui, FDCClientUtils.getRes("noExTable"));

			SysUtil.abort();
		}

		return null;
//		return exRate;
	}

	/**
	 * get a KDTextFieldEditor with max length 80
	 * 
	 * @return
	 */
	public static ICellEditor getKDTextFieldCellEditor() {
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(80);
		KDTDefaultCellEditor txt_CellEditor = new KDTDefaultCellEditor(txtField);

		return txt_CellEditor;
	}

	public static KDTDefaultCellEditor getNumberCellEditor() {
		KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
		indexValue_TextField.setName("indexValue_TextField");
		indexValue_TextField.setVisible(true);
		indexValue_TextField.setEditable(true);
		indexValue_TextField.setHorizontalAlignment(NUMBERTEXTFIELD_ALIGNMENT);
		indexValue_TextField.setDataType(NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL);
		indexValue_TextField.setMaximumValue(FDCHelper.MAX_VALUE);
		indexValue_TextField.setMinimumValue(FDCHelper.MIN_VALUE);
		indexValue_TextField.setSupportedEmpty(true);
		indexValue_TextField.setPrecision(2);
		
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
				indexValue_TextField);
		indexValue_CellEditor.setClickCountToStart(1);
		
		return indexValue_CellEditor;
	}
	
	public static KDTDefaultCellEditor getDateCellEditor() {
		
		KDDatePicker dateEditor = new KDDatePicker();
		dateEditor.setName("dateEditor");
		dateEditor.setVisible(true);
		dateEditor.setEditable(true);
		
		KDTDefaultCellEditor date_CellEditor = new KDTDefaultCellEditor(
				dateEditor);
		date_CellEditor.setClickCountToStart(1);
		
		return date_CellEditor;
	}

	/**
	 * 是否新增显示
	 * 组织ID参数传入时已取了单据成本中心或当前组织ID，此处不应该再取当前组织ID
	 * @param caller
	 * @param orgId
	 * @return
	 */
	public static boolean isCodingRuleAddView(IObjectValue caller, String orgId) {
		ICodingRuleManager iCodingRuleManager;
		boolean isAddView = false;
		if(orgId==null){
			return isAddView;
		}
		try {
			iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			isAddView = iCodingRuleManager.isAddView(caller, orgId);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}

		return isAddView;
	}

	/**
	 * 获取当前组织ID
	 * 
	 * @return
	 */
	public static String getCurrentOrgId() {
		return SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
	}

	/**
	 * 为百分数控件设置取值范围(0 - 100)
	 * 
	 * @param txtField
	 */
	public static void setValueScopeForPercentCtrl(KDFormattedTextField txtField) {
		txtField.setMinimumValue(FDCConstants.B0.setScale(2));
		txtField.setMaximumValue(FDCConstants.B100.setScale(2));
	}

	public static void setCtrlEnabledByState(Component[] comps, String oprtState) {
		if (oprtState != null
				&& (oprtState.equals(OprtState.VIEW) || oprtState
						.equals("FINDVIEW"))) {
			setPromptBoxEnable(comps, false);
		}
	}

	public static void setPromptBoxEnable(Component[] comps,
			final boolean enable) {
		Component kdc = null;
		for (int i = 0, size = comps.length; i < size; i++) {
			kdc = comps[i];
			JComponent comp = null;
			if (kdc instanceof KDLabelContainer) {
				comp = ((KDLabelContainer) kdc).getBoundEditor();
			} else {
				comp = (JComponent) kdc;
			}

			if (comp instanceof KDBizPromptBox) {
				comp.setEnabled(enable);
			}

		}
	}
	
	public static void selectTableFirstRow(KDTable table) {
		if(table == null) return;
		
		if(table.getRowCount() > 0) {
			table.getSelectManager().select(0, 0);
		}
	}
	
	public static void setActionEnable(ItemAction action, boolean enable) {
		if(action == null) return;
		
		action.setEnabled(enable);
		action.setVisible(enable);
	}
	
	 public static Window getCurrentActiveWindow() {
		return KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.getActiveWindow();
	}
	
	public static void centerWindow(Window window)
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimension1 = window.getSize();
		window.setLocation((dimension.width - dimension1.width) / 2,
				(dimension.height - dimension1.height) / 2);
	}
	
	public static JComponent getAction(JComponent ui,String key){
		for(int i=0;i<ui.getComponents().length;i++){
			if(!(ui.getComponent(i) instanceof JComponent)){
				return null;
			}
			JComponent c=(JComponent)ui.getComponent(i);
			InputMap inputMap = c.getInputMap();
			if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
				System.out.println(c+"\t "+inputMap.get(KeyStroke.getKeyStroke("F8")));
				return c;
			}
			inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
				System.out.println(c+"\t "+inputMap.get(KeyStroke.getKeyStroke("F8")));
				inputMap.remove(KeyStroke.getKeyStroke("F8"));
				return c;
			}
			inputMap = c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
				System.out.println(c+"\t "+inputMap.get(KeyStroke.getKeyStroke("F8")));
				return c;
			}
			if(c.getComponents().length>0){
				return getAction(c, key);
			}
		}
		return null;
	}
	/**
	 * 设置UI的页签名与菜单名相同
	 * @param ui  by sxhong 2008-04-22 10:33:56
	 */
	public static void setUIMainMenuAsTitle(CoreUIObject ui){
		if(ui==null){
			return;
		}
		Object obj = ui.getUIContext().get("MainMenuName");
		if (!FDCHelper.isEmpty(obj)) {
			ui.setUITitle(obj.toString());
		}
	}
	
	//获取当前公司，可查询范围的日期
	public static Date[] getCompanyCurrentDate() throws Exception{
		boolean isIncorporation = false;

		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
		if(company.isIsBizUnit()){
			isIncorporation = FDCUtils.IsInCorporation(null,company.getId().toString());
		}
		
		return getCompanyCurrentDate(isIncorporation);
	}
	
	public static Date[] getCompanyCurrentDate(boolean isIncorporation) throws Exception{
		PeriodInfo curPeriod =null;
		
		if(isIncorporation){
			CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
//			curPeriod = SystemStatusCtrolUtils.getCurrentPeriod(null,SystemEnum.FDC,company);
		}
		
		Date now = new Date();
		Date[] pkdate = new Date[2];
		if(curPeriod!=null){
			pkdate[0] = curPeriod.getBeginDate();
			pkdate[1] = curPeriod.getEndDate();
			
			if(pkdate[0].before(now) && pkdate[1].after(now)){
				pkdate[1] = now ;
			}
		}else{
			pkdate[0] = now;
			pkdate[1] = now;
		}
		
		return pkdate;
	}
	
    private static String getChineseName(String key) {
        return EASResource.getString("com.kingdee.eas.fi.gl.GLResource", key);
    }
	
    private static Properties getDecimalUnitMapping() {
        Properties decimalCurrencyUomb = new Properties();
        decimalCurrencyUomb.setProperty("0", getChineseName("jiao"));
        decimalCurrencyUomb.setProperty("1", getChineseName("fen"));
        decimalCurrencyUomb.setProperty("2", getChineseName("li"));

        return decimalCurrencyUomb;
    }

    private static Properties getIntegerUnitMapping(boolean flag) {
        Properties integerCurrencyUomb = new Properties();
        if (flag)
            integerCurrencyUomb.setProperty("0", "*" + getChineseName("money")); // needed
        // unit
        else
            integerCurrencyUomb.setProperty("0", "*"); // needed
        // unit
        integerCurrencyUomb.setProperty("1", getChineseName("ten"));
        integerCurrencyUomb.setProperty("2", getChineseName("handred"));
        integerCurrencyUomb.setProperty("3", getChineseName("thrasand"));
        integerCurrencyUomb.setProperty("4", "*" + getChineseName("tenshand")); // needed
        // unit
        integerCurrencyUomb.setProperty("5", getChineseName("ten"));
        integerCurrencyUomb.setProperty("6", getChineseName("handred"));
        integerCurrencyUomb.setProperty("7", getChineseName("thrasand"));
        integerCurrencyUomb.setProperty("8", "*" + getChineseName("handredmill")); // needed
        // unit
        integerCurrencyUomb.setProperty("9", getChineseName("ten"));
        integerCurrencyUomb.setProperty("10", getChineseName("handred"));
        integerCurrencyUomb.setProperty("11", getChineseName("thrasand"));
        integerCurrencyUomb.setProperty("12", getChineseName("tenshand"));
        integerCurrencyUomb.setProperty("13", getChineseName("handredmill"));
        integerCurrencyUomb.setProperty("14", getChineseName("ten"));

        return integerCurrencyUomb;
    }

    private static Properties getNumberMapping() {
        Properties numberMapping = new Properties();
        numberMapping.setProperty("0", getChineseName("zero"));
        numberMapping.setProperty("1", getChineseName("one"));
        numberMapping.setProperty("2", getChineseName("two"));
        numberMapping.setProperty("3", getChineseName("three"));
        numberMapping.setProperty("4", getChineseName("four"));
        numberMapping.setProperty("5", getChineseName("five"));
        numberMapping.setProperty("6", getChineseName("six"));
        numberMapping.setProperty("7", getChineseName("seven"));
        numberMapping.setProperty("8", getChineseName("eight"));
        numberMapping.setProperty("9", getChineseName("nine"));

        return numberMapping;
    }
    
    // 金额到角的话根据参数hasFull,设置“整”
    public static String getChineseFormat(BigDecimal number, boolean hasFull) {
        if (number == null) {
            return "";
        }

        String nagative = "";
        String nagVirText = "";
        boolean isNagativeB = false;
        if (number.toString().indexOf("-") >= 0) {
            nagative = getChineseName("nagative");
            isNagativeB = true;
        }

        Properties decimalPro = getDecimalUnitMapping();
        Properties integerPro = getIntegerUnitMapping(true);
        Properties numberPro = getNumberMapping();

        String valTemp = number.toString();
        
        /** 若是有负数,则去掉 "- "号 * */
        if (isNagativeB) {
            valTemp = valTemp.replace('-', ' ').trim();
        }

        int dotIndex = valTemp.indexOf(".");
        
        /** 整数部分 * */
        String integerStr = "";
        String integerTempStr = null;
        
        /** 小数部分 * */
        String decimalStr = "";
        String decimalTempStr = null;
        
        /** 1: **/
        if (dotIndex == -1) {
            integerTempStr = valTemp;
        } else {
            integerTempStr = valTemp.substring(0, dotIndex);
            decimalTempStr = valTemp.substring(dotIndex + 1);
        }

        /** 2: 整数部分的处理 * */

        if (integerTempStr != null) {
            int integerLen = integerTempStr.length() - 1;
            boolean zeroAttached = false; // Jacket:

            boolean hasTem = true;

            for (int i = integerLen; i >= 0; i--) {
                String index = String.valueOf(i);
                String str = String.valueOf(integerTempStr.charAt(integerLen - i));
                String numStr = numberPro.getProperty(str);
                String uombStr = integerPro.getProperty(index);

                boolean needed = false;
                if (uombStr.startsWith("*")) {
                    needed = true;
                    uombStr = uombStr.substring(1);
                    if (hasTem && i == 4 && integerLen >= 8) {
                        //uombStr = "";
                        if((('0' == integerTempStr.charAt(0) && '0' == integerTempStr.charAt(1)
                        		&& '0' == integerTempStr.charAt(2) && '0' == integerTempStr.charAt(3)
                        		&& '0' == integerTempStr.charAt(integerLen-8) &&'0' == integerTempStr.charAt(integerLen-7)
                        		&& '0' == integerTempStr.charAt(integerLen-6) && '0' == integerTempStr.charAt(integerLen-5))
                        		) && (('0' != integerTempStr.charAt(integerLen-4) || '0' != integerTempStr.charAt(integerLen-3)
                        		||  '0' != integerTempStr.charAt(integerLen-2)|| '0' != integerTempStr.charAt(integerLen-1)) )){
                        	uombStr = getChineseName("zero");
                        }else if(('0' != integerTempStr.charAt(integerLen-4))){
                        	//uombStr = getChineseName("zero");
                        }else{
                        	uombStr = "";
                        }
                        hasTem = false;
                    }                   
                }
                if ("0".equals(str)) {
                    if (zeroAttached) {
                        numStr = "";
                        if (needed) {
                            zeroAttached = false;
                            integerStr = integerStr.substring(0, integerStr.length() - 1);

                        } else {
                            uombStr = "";
                        }
                    } else {
                        if (!needed) {
                            zeroAttached = true;
                            uombStr = "";
                        } else {
                            zeroAttached = false;
                            numStr = "";
                        }
                    }
                } else {
                    if (i <= 7 && i >= 4) {
                        hasTem = false;
                    }
                    zeroAttached = false;
                }

                integerStr += numStr + uombStr;
            }
        }

        boolean integerEmpty = integerStr.length() < 2 ? true : false;
        if (integerEmpty) {
            integerStr = "";
        }

        boolean decimalEmpty = true;
        boolean hasJiao = false;
        
        /** 3: 小数部分的处理 * */
        if (decimalTempStr != null) {
            int decimalLen = decimalTempStr.length();
            int n = decimalPro.size();
            n = decimalLen > n ? n : decimalLen;
            boolean zeroAttached = false; // Jacket:
            boolean ignoreLeadingZero = true;
            for (int j = 0; j < n; j++) {
                String index = String.valueOf(j);
                String str = String.valueOf(decimalTempStr.charAt(j));
                String numStr = numberPro.getProperty(str);
                String uombStr = decimalPro.getProperty(index);

                if ("0".equals(str)) {
                    if (zeroAttached) {
                        numStr = "";
                        uombStr = "";
                        if (j == n - 1) {
                            decimalStr = decimalStr.substring(0, decimalStr.length() - 1);
                        }
                    } else {
                        if (j < n - 1 && !ignoreLeadingZero) {
                            zeroAttached = true;
                        } else {
                            numStr = "";
                        }
                        uombStr = "";
                    }
                } else {
                    if (j == 0) {
                        hasJiao = true;
                    } else {
                        hasJiao = false;
                    }
                    zeroAttached = false;
                    ignoreLeadingZero = false;
                }
                String temp = numStr + uombStr ;
                decimalStr = decimalStr + temp;
            }

            decimalEmpty = decimalStr.length() < 2 ? true : false;
            if (decimalEmpty) {
                decimalStr = "";
            }

            String unDescriptable = ""; // 无法用币别单位表达的小数位
            int index = -1; // 最后一个非零小数位
            for (int i = n; i < decimalLen; i++) {
                char c = decimalTempStr.charAt(i);
                String numStr = numberPro.getProperty(String.valueOf(c));
                unDescriptable += numStr;
                if ('0' != c) {
                    index = i;
                }
            }
            if (index >= 0) {
                decimalEmpty = false;
                if ('0' == decimalTempStr.charAt(n - 1)) {
                    decimalStr += getChineseName("zero") + decimalPro.getProperty(String.valueOf(n - 1));
                }

                decimalStr += unDescriptable.substring(0, index - n + 1);
            }else{
                if ('0' == decimalTempStr.charAt(0) && '0' != decimalTempStr.charAt(1)) {
                    decimalStr = getChineseName("zero") + decimalStr;
                }
            }
        }

        if (integerEmpty && decimalEmpty) {
            integerStr = getChineseName("zero") + getChineseName("money"); // "零元";
        }

        // 金额到分的话不应该有“整”
        // 金额到角的话根据参数设置“整”
        String value = nagative + nagVirText + integerStr + decimalStr;
        
//        if( decimalStr.length() == 1 && ! ( hasJiao)){
//            value = value +getChineseName("zero") ;
//        }
        
        value = nagative + nagVirText + integerStr + decimalStr;
        
        if ((decimalStr == null || decimalStr.length() == 0 || decimalStr.length() == 1) || ( hasJiao)) {
            value = value + getChineseName("full");
        }
        /** 4: 整合负数标识,整数和小数.* */
        return value;
    }    
	
	//获取表格选中行某个字段的值
    public static String getSelectedKeyValue(KDTable tblMain,String keyFiledName)
    {    	
        int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
        return ListUiHelper.getSelectedKeyValue(selectRows,tblMain,keyFiledName);
    }
    
    public static void setActionEnableAndNotSetVisible(ItemAction[] actions,
			boolean enabled) {
		if (actions == null) {
			return;
		}
		for (int i = 0, len = actions.length; i < len; i++) {
			setActionEnableAndNotSetVisible(actions[i], enabled);
		}
	}
    
    public static void setActionEnableAndNotSetVisible(ItemAction action,
			boolean enable) {
		if (action == null)
			return;

		action.setEnabled(enable);
	}
    /**
     * 设置action的可见性
     * @param itemActions
     * @param isVisible
     * @author laiquan_luo
     */
	public static void setActionVisible(ItemAction[] itemActions,boolean isVisible)
	{
		if(itemActions == null)
			return;
		for(int i = 0;i<itemActions.length; i++)
		{
			if(itemActions[i] instanceof ItemAction)
			{
				itemActions[i].setVisible(isVisible);
			}
		}
	}
    public static void formatKDFormattedTextField(KDFormattedTextField[] txts) {
		if (txts == null) {
			return;
		}
		for (int i = 0, len = txts.length; i < len; i++) {
			txts[i].setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
			txts[i].setSupportedEmpty(true);
			txts[i].setPrecision(2);
			txts[i].setHorizontalAlignment(KDFormattedTextField.RIGHT);
			txts[i].setRemoveingZeroInDispaly(false);
			txts[i].setMaximumValue(FDCHelper.MAX_VALUE);
			txts[i].setMinimumValue(FDCHelper.MIN_VALUE);
			txts[i].setHorizontalAlignment(JTextField.RIGHT);
		}
	}
    
    public static void formatKDFormattedINT(KDFormattedTextField[] txts) {
		if (txts == null) {
			return;
		}
		for (int i = 0, len = txts.length; i < len; i++) {
			txts[i].setDataType(KDFormattedTextField.INTEGER_TYPE);
			txts[i].setSupportedEmpty(true);
//			txts[i].setPrecision(2);
			txts[i].setHorizontalAlignment(KDFormattedTextField.RIGHT);
			txts[i].setRemoveingZeroInDispaly(false);
			txts[i].setMaximumValue(FDCHelper.MAX_VALUE);
			txts[i].setMinimumValue(FDCHelper.ZERO);
			txts[i].setHorizontalAlignment(JTextField.RIGHT);
		}
	}
    
    /**
	 * fxh，此方法通过反射清除BasicPopupMenuUI$MenuKeyboardHelper的menuInputMap
	 * 当使用过菜单后，这个inputmap会引用相应界面的rootpane并且不会释放
	 */
	public static void clearMenuKeyboardHelper()
	{
		java.lang.reflect.Field field = null;
		try
		{			
			field = BasicPopupMenuUI.class.getDeclaredField("menuKeyboardHelper");							
		}
		catch (SecurityException e1)
		{
//			e1.printStackTrace();
		}
		catch (NoSuchFieldException e1)
		{
//			e1.printStackTrace();
		}
		if (field != null)
		{
			field.setAccessible(true);
			try
			{
				Object menuKeyboardHelper = field.get(null);
				try
				{
					field = menuKeyboardHelper.getClass().getDeclaredField("menuInputMap");
					field.setAccessible(true);
					field.set(menuKeyboardHelper, null);
				}
				catch (SecurityException e1)
				{
//					e1.printStackTrace();
				}
				catch (NoSuchFieldException e1)
				{
//					e1.printStackTrace();
				}
			}
			catch (IllegalArgumentException e1)
			{
//				e1.printStackTrace();
			}
			catch (IllegalAccessException e1)
			{
//				e1.printStackTrace();
			}
		}
	}
	
    /**
     * 获取选择节点的所有下级明细工程项目
     * @return
     */
    public static Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node){
    	Set idSet=new HashSet();
    	if(node!=null){
    		if(node.getUserObject() instanceof CurProjectInfo){
    			CurProjectInfo prj = (CurProjectInfo)node.getUserObject();
    			if(prj.isIsLeaf()){
    				idSet.add(prj.getId().toString());
    				return idSet;
    			}
    		}
    		//直接遍历树去到节点可以保证一致性
    		Enumeration childrens = node.depthFirstEnumeration();
    		while(childrens.hasMoreElements()){
    			DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)childrens.nextElement();
    			if(childNode.getUserObject() instanceof CurProjectInfo){
    				CurProjectInfo prj=(CurProjectInfo)childNode.getUserObject();
    				if(prj.isIsLeaf()){
    					idSet.add(prj.getId().toString());
    				}
    			}
    		}
    	}
    	if(idSet.size()==0){
    		//如果选择的节点下没有任何明细的工程项目节点则随便加个节点保证过滤不出来任何数据
    		idSet.add(OrgConstants.DEF_CU_ID);
    	}
    	return idSet;
    }

	/**
	 * 检查审批人和反审批人是否同一个。若启用了参数FDC011_AUDITORMUSTBETHESAMEUSER，必须给出提示。
	 * 
	 * @param selectedIdValues
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 * @throws FDCBasedataException
	 * 
	 * @author owen_wen 2011-03-09
	 */
	public static void checkAuditor(List selectedIdValues, String tableName) throws BOSException, EASBizException, SQLException, FDCBasedataException {
		boolean isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);
		if (isSameUserForUnAudit) {
			String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("SELECT FAuditorID auditId FROM " + tableName + " WHERE FID in (");
			builder.appendParam(selectedIdValues.toArray());
			builder.appendSql(")");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String id = rowSet.getString("auditId");
				if (!userID.equals(id)) {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				}
			}
		}
	}

}
