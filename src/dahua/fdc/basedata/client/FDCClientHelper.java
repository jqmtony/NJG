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
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
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
import java.util.LinkedHashMap;
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

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
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
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.BasicFormattedTextField;
import com.kingdee.bos.ctrl.swing.IKDComponent;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDButton;
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
import com.kingdee.bos.ctrl.swing.KDTextArea;
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
import com.kingdee.bos.metadata.util.MetaDataLoader;
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
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
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
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.MeasureIndexCollection;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.basedata.util.FdcClassUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcFieldUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMetaDataUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMethodUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcReflectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcSqlUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
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
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.fm.common.FMLoginModel;
import com.kingdee.eas.fm.common.IContextHelper;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.fm.common.client.BizTypeChooseUI;
import com.kingdee.eas.fm.common.client.ExcelFileFilter;
import com.kingdee.eas.fm.common.client.FMClientVerifyHelper;
import com.kingdee.eas.fm.nt.client.IBotEditUI;
import com.kingdee.eas.framework.BillBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.FrameWorkUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.client.BillEditUI;
import com.kingdee.eas.framework.client.CoreBillEditUI;
import com.kingdee.eas.framework.client.CoreBillListUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.ListUI;
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
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.util.UuidException;
import com.kingdee.util.db.SQLUtils;

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
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getNumberFtm(BOSUuid currencyId) {
		return "%r-[ ]0." + getPrecOfCurrency(currencyId) + "n";
	}

	public static String getNumberFtm(int precision) {
		return "%r-[ ]0." + precision + "n";
	}

	public static String getNumberFtm() {
		return "%r-[ ]{###,###,###.##}15.2n";
	}
	
	private static Logger logger = Logger.getLogger(FDCClientHelper.class);

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
					mapPrecOfExrate = ContractFacadeFactory.getRemoteInstance().getExRatePre(exTable,localCurId);
				}
			} catch (Exception e) {
				// @AbortException
				logger.error(e.getMessage(), e);
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
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
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
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
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

	public static void clearTable(KDTable kdTable) {
		kdTable.removeHeadRows();
		kdTable.removeRows();
		kdTable.removeColumns();
	}


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

		return selectBlock != null ? selectBlock.getTop() : -1;
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
			// @AbortException
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
			// @AbortException
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
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			// @AbortException
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SysUtil.abort();
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
			ICodingRuleManager codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			result = codingRuleManager.isExist(info, companyId);
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
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
				FDCConstants.MAX_YEAR, 1);
		spnBeginYear.setModel(yModel);

		SpinnerNumberModel yModel1 = new SpinnerNumberModel(thisYear, thisYear,
				FDCConstants.MAX_YEAR, 1);
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
				FDCConstants.MAX_YEAR, 1);
		spnBeginYear.setModel(yModel);

		SpinnerNumberModel yModel1 = new SpinnerNumberModel(thisYear, 2000,
				FDCConstants.MAX_YEAR, 1);
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
			if(tblMain.getRow(i) == null || tblMain.getRow(i).getCell(indexOfCurPre) == null 
					|| tblMain.getRow(i).getCell(indexOfCurPre).getValue() == null ){
				continue ;	
			}
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
	public static void initComboMeasureStage(KDComboBox comboMeasureStage, boolean isEdit) throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);// 一失足成千古恨
		if (isEdit) {
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		}
		view.getSorter().add(new SorterItemInfo("number"));
		MeasureStageCollection measureStageCollection = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
		if (measureStageCollection == null || measureStageCollection.size() == 0) {
			FDCMsgBox.showWarning("没有启用测算阶段!");
			SysUtil.abort();
		}
		comboMeasureStage.addItems(measureStageCollection.toArray());
	}

	/**
	 * 初始化未测算过的测算阶段
	 */
	public static void initComboMeasureStage(KDComboBox comboMeasureStage, boolean isEdit, BigDecimal fetchMeaStaMaxNumber)
			throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		MeasureStageCollection measureStageCollection = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
		if(measureStageCollection==null||measureStageCollection.size()==0){
			FDCMsgBox.showWarning("没有启用测算阶段!");
			SysUtil.abort();
		}
		BigDecimal tempNumber = FDCHelper.ZERO;
		if (isEdit) {
			for (int i = 0; i < measureStageCollection.size(); i++) {
				MeasureStageInfo measureStageInfo = measureStageCollection.get(i);
				String number = measureStageInfo.getNumber();
				if (number != null) {
					tempNumber = new BigDecimal(number);
					if (fetchMeaStaMaxNumber.compareTo(tempNumber) >= 0) {
						measureStageCollection.remove(measureStageInfo);
						i--;
					}
				}
			}
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
		} catch (BOSException e) {
			logger.error(e.getMessage(), e);
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
			// @AbortException
			logger.error(e1.getMessage(), e1);
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
			// @AbortException
			logger.error(e1.getMessage(), e1);
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
		if (number.compareTo(FDCConstants.MAX_VALUE) > 0) {
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
				// @AbortException
				logger.error(e.getMessage(), e);
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
			logger.error(exp.getMessage(), exp);
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
				if (c instanceof KDButton) {
					((KDButton) c).setEnabled(enable);
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
				if (c instanceof KDButton) {
					((KDButton) c).setEnabled(enable);
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
					logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
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
				logger.error(e.getMessage(), e);
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
	
	public static KDTDefaultCellEditor getNumberCellEditor(int precesion) {
		KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
		indexValue_TextField.setName("indexValue_TextField");
		indexValue_TextField.setVisible(true);
		indexValue_TextField.setEditable(true);
		indexValue_TextField.setHorizontalAlignment(NUMBERTEXTFIELD_ALIGNMENT);
		indexValue_TextField.setDataType(NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL);
		indexValue_TextField.setMaximumValue(FDCHelper.MAX_VALUE);
		indexValue_TextField.setMinimumValue(FDCHelper.MIN_VALUE);
		indexValue_TextField.setSupportedEmpty(true);
		indexValue_TextField.setPrecision(precesion);
		
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
			SysUtil.abort();
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
				return c;
			}
			inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
				inputMap.remove(KeyStroke.getKeyStroke("F8"));
				return c;
			}
			inputMap = c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
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
			curPeriod = SystemStatusCtrolUtils.getCurrentPeriod(null,SystemEnum.FDC,company);
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
        return FDCHelper.getChineseFormat(number , hasFull);
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
	
	/**
	 * 描述：设置action的可见性
	 * 
	 * @param action
	 * @param isVisible
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-12
	 */
	public static void setActionVisible(ItemAction action, boolean visible) {
		if (action == null) {
			return;
		}

		action.setVisible(visible);
	}
	
	/**
	 * 描述：设置action是否可见可用
	 * 
	 * @param actions
	 * @param enabled
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 */
	public static void setActionEnV(ItemAction[] actions, boolean enabled) {
		if (actions == null) {
			return;
		}
		for (int i = 0, len = actions.length; i < len; i++) {
			setActionEnV(actions[i], enabled);
		}
	}

	/**
	 * 描述：设置action是否可见可用
	 * 
	 * @param action
	 * @param enable
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 */
	public static void setActionEnV(ItemAction action, boolean enable) {
		if (action == null)
			return;

		action.setEnabled(enable);
		action.setVisible(enable);
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
		try {
			field = BasicPopupMenuUI.class.getDeclaredField("menuKeyboardHelper");
			if (field != null) {
				field.setAccessible(true);
				Object menuKeyboardHelper = field.get(null);
				field = menuKeyboardHelper.getClass().getDeclaredField("menuInputMap");
				field.setAccessible(true);
				field.set(menuKeyboardHelper, null);
			}
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
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
	
	/**
	 * 检查审批人和反审批人是否同一个。若启用了参数FDC011_AUDITORMUSTBETHESAMEUSER，必须给出提示。
	 * 
	 * R140109-0016
	 * 
	 * @author zhaoqin
	 * @date 2014/01/15
	 */
	public static void checkAuditor(List selectedIdValues, Map auditMap) throws BOSException, EASBizException, SQLException, FDCBasedataException {
		if(null == selectedIdValues || selectedIdValues.size() == 0)
			return;
		
		boolean isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);
		if (isSameUserForUnAudit) {
			String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();

			for (int i = 0; i < selectedIdValues.size(); i++) {
				String idKey = (String) selectedIdValues.get(i);
				MultiApproveInfo info = (MultiApproveInfo) auditMap.get(idKey);
				if (null == info) {
					checkAuditor(selectedIdValues, "t_con_ContractBill");
				} else {
					String auditId = (String) info.getCreator().get("id");
					if (!userID.equals(auditId)) {
						throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
					}
				}

			}
		}
	}

	/**
	 * 取得文本单元格编辑器
	 * 
	 * @param maxLength
	 *            最大长度
	 * @return
	 */
	public static ICellEditor getKDTextFieldCellEditor(int maxLength) {
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(maxLength);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(txtField);
	
		return cellEditor;
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 根据ListUI取得对应的EntityObjectInfo('实体对象信息')
	 * <p>
	 * 调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param listUI
	 * @return
	 * @throws Exception
	 */
	public static EntityObjectInfo getEntity(ListUI listUI) throws Exception {
		return _getEntity(listUI);
	}

	/**
	 * 根据EditUI取得对应的EntityObjectInfo('实体对象信息')
	 * <p>
	 * 调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param editUI
	 * @return
	 * @throws Exception
	 */
	public static EntityObjectInfo getEntity(EditUI editUI) throws Exception {
		return _getEntity(editUI);
	}

	/**
	 * 根据CoreUI取得对应的EntityObjectInfo('实体对象信息')
	 * <p>
	 * 调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param listUI
	 * @return
	 * @throws Exception
	 */
	private static EntityObjectInfo _getEntity(CoreUI coreUI) throws Exception {
		EntityObjectInfo entityObjectInfo = null;

		ICoreBase coreBase = (ICoreBase) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getBizInterface", true);
		if (null != coreBase) {
			BOSObjectType bosType = coreBase.getType();
			entityObjectInfo = MetaDataLoader.getEntity(null, bosType);
		}

		return entityObjectInfo;
	}

	/**
	 * 根据ListUI取得对应的EntityObjectInfo('实体对象信息')名称
	 * <p>
	 * 调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param listUI
	 * @return
	 */
	public static String getEntityName(ListUI listUI) {
		return _getEntityName(listUI);
	}

	/**
	 * 根据EditUI取得对应的EntityObjectInfo('实体对象信息')名称
	 * <p>
	 * 调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param editUI
	 * @return
	 */
	public static String getEntityName(EditUI editUI) {
		return _getEntityName(editUI);
	}

	/**
	 * 匹配、取得实体名称
	 * <p>
	 * 1、如果取得失败，则根据UI名称正则匹配<br>
	 * 2、如果匹配失败，则调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param editUI
	 * @return
	 */
	public static String matchGetEntityName(EditUI editUI) {
		String entityName = null;

		// 从CoreUI中匹配对应的实体名称
		entityName = _matchEntityName(editUI);
		// 如果匹配失败，则根据CoreUI取得对应的EntityObjectInfo('实体对象信息')名称
		if (null == entityName) {
			entityName = _getEntityName(editUI);
		}

		return entityName;
	}

	/**
	 * 取得、匹配实体名称
	 * <p>
	 * 1、调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 2、如果取得失败，则根据UI名称正则匹配<br>
	 * 
	 * @param listUI
	 * @return
	 */
	public static String getMatchEntityName(ListUI listUI) {
		String entityName = null;

		// 根据ListUI取得对应的EntityObjectInfo('实体对象信息')
		entityName = _getEntityName(listUI);
		// 如果取得失败，则根据UI名称正则匹配
		if (null == entityName) {
			entityName = _matchEntityName(listUI);
		}

		return entityName;
	}

	/**
	 * 取得、匹配实体名称
	 * <p>
	 * 1、调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 2、如果取得失败，则根据UI名称正则匹配<br>
	 * 
	 * @param editUI
	 * @return
	 */
	public static String getMatchEntityName(EditUI editUI) {
		String entityName = null;

		// 根据EditUI取得对应的EntityObjectInfo('实体对象信息')
		entityName = _getEntityName(editUI);
		// 如果取得失败，则根据UI名称正则匹配
		if (null == entityName) {
			entityName = _matchEntityName(editUI);
		}

		return entityName;
	}

	/**
	 * 匹配、取得实体名称
	 * <p>
	 * 1、如果取得失败，则根据UI名称正则匹配<br>
	 * 2、如果匹配失败，则调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param listUI
	 * @return
	 */
	public static String matchGetEntityName(ListUI listUI) {
		String entityName = null;

		// 从CoreUI中匹配对应的实体名称
		entityName = _matchEntityName(listUI);
		// 如果匹配失败，则根据CoreUI取得对应的EntityObjectInfo('实体对象信息')名称
		if (null == entityName) {
			entityName = _getEntityName(listUI);
		}

		return entityName;
	}

	/**
	 * 从CoreUI中匹配对应的实体名称
	 * 
	 * <p>
	 * 根据UI名称正则匹配<br>
	 * 
	 * @param coreUI
	 * @return
	 */
	public static String matchEntityName(CoreUI coreUI) {
		String entityName = null;

		// 从CoreUI中匹配对应的实体名称
		entityName = _matchEntityName(coreUI);

		return entityName;
	}

	/**
	 * 根据CoreUI取得对应的EntityObjectInfo('实体对象信息')名称
	 * <p>
	 * 调用getBizInterface()方法，然后取得对应的EntityObjectInfo('实体对象信息')<br>
	 * 
	 * @param coreUI
	 * @return
	 */
	private static String _getEntityName(CoreUI coreUI) {
		String entityName = null;

		// 根据ListUI取得对应的EntityObjectInfo('实体对象信息')
		EntityObjectInfo entityObjectInfo = null;
		try {
			entityObjectInfo = _getEntity(coreUI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != entityObjectInfo) {
			entityName = entityObjectInfo.getName();
		}

		return entityName;
	}

	/**
	 * 从CoreUI中匹配对应的实体名称
	 * 
	 * <p>
	 * 根据UI名称正则匹配<br>
	 * 
	 * @param coreUI
	 * @return
	 */
	private static String _matchEntityName(CoreUI coreUI) {
		String entityName = null;

		// 取得UI类名
		Class uiClass = coreUI.getClass();
		// 取得简单类名
		String uiClassSimpleName = FdcClassUtil.getSimpleName(uiClass);

		if (coreUI instanceof ListUI || coreUI instanceof BillEditUI) {
			ICoreBase coreBase = (ICoreBase) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getBizInterface", true);

			if (null != coreBase) {
				BOSObjectType bosType = coreBase.getType();
				EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(null, bosType);
				entityName = entityObjectInfo.getName();

				return entityName;
			}
		}

		String endsWithSuffix = null;
		// 通常是ListUI
		if (coreUI instanceof ListUI && uiClassSimpleName.endsWith(FDCConstants.LIST_UI_SUFFIX)) {
			endsWithSuffix = FDCConstants.LIST_UI_SUFFIX;
		}
		// EditUI
		else if (coreUI instanceof EditUI && uiClassSimpleName.endsWith(FDCConstants.EDIT_UI_SUFFIX)) {
			endsWithSuffix = FDCConstants.EDIT_UI_SUFFIX;
		}
		// UI
		else if (uiClassSimpleName.endsWith(FDCConstants.UI_SUFFIX)) {
			endsWithSuffix = FDCConstants.UI_SUFFIX;
		}

		// 提取成功
		if (FdcStringUtil.isNotBlank(endsWithSuffix)) {
			int uiClassSimpleNameLength = uiClassSimpleName.length();
			int suffixLength = endsWithSuffix.length();

			entityName = uiClassSimpleName.substring(0, uiClassSimpleNameLength - suffixLength);
			// 取得UI对应的model包名
			String modelPackageName = getModelPackageName(coreUI);
			String entityFullName = modelPackageName + "." + entityName;

			try {
				Class classz = Class.forName(entityFullName);

				// 继承自EAS基类
				if (!BillBase.class.isAssignableFrom(classz)) {
					entityName = null;
				}
			} catch (ClassNotFoundException e) {
				// e.printStackTrace();
				entityName = null;
			}
		}

		return entityName;
	}

	/**
	 * 取得UI对应的model包名
	 * 
	 * @param coreUI
	 * @return
	 */
	public static String getModelPackageName(CoreUI coreUI) {
		String modelPackageName = null;

		// 取得UI类名
		Class uiClass = coreUI.getClass();

		// 取得UI所在模块包名
		String clientPackageName = uiClass.getPackage().getName();
		int clientIndex = clientPackageName.lastIndexOf(".client");
		modelPackageName = clientPackageName.substring(0, clientIndex);

		return modelPackageName;
	}

	/**
	 * 匹配套打
	 * 
	 * @param listUI
	 * @return 匹配套打后的消息
	 */
	public static String matchTD(ListUI listUI) {
		return _matchTD(listUI);
	}

	/**
	 * 匹配套打
	 * 
	 * @param editUI
	 * @return 匹配套打后的消息
	 */
	public static String matchTD(EditUI editUI) {
		return _matchTD(editUI);
	}

	/**
	 * 匹配套打
	 * 
	 * @param coreUI
	 * @return 匹配套打后的消息
	 */
	private static String _matchTD(CoreUI coreUI) {
		// 匹配套打后的消息
		String tdMsg = null;

		// 旧套打Query元数据
		IMetaDataPK oldTDQueryPK = (IMetaDataPK) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getTDQueryPK", true);
		// 旧套打模板文件名
		String oldTDFileName = (String) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getTDFileName", true);

		// 匹配结果Map
		Map matchResultMap = null;
		// 套打Query元数据
		IMetaDataPK tDQueryPK = null;
		// 套打模板文件名
		String tDFileName = null;
		// 套打消息
		String tdMsgByUIName = null;
		String tdMsgByMainQueryPK = null;
		String tdMsgByEntityName = null;

		// 如果是ListUI，则优先根据 UI名称 匹配
		if (coreUI instanceof ListUI) {
			// 根据 UI名称 匹配套打
			matchResultMap = _matchTDByUIName(coreUI, oldTDQueryPK, oldTDFileName);
			if (null != matchResultMap) {
				tDQueryPK = (IMetaDataPK) matchResultMap.get("tDQueryPK");
				tDFileName = (String) matchResultMap.get("tDFileName");
				tdMsgByUIName = (String) matchResultMap.get("tdMsg");
			}
			// 根据 MainQueryPK 匹配套打
			if (null == tDQueryPK) {
				matchResultMap = _matchTDByMainQueryPK(coreUI, oldTDQueryPK, oldTDFileName);

				if (null != matchResultMap) {
					tDQueryPK = (IMetaDataPK) matchResultMap.get("tDQueryPK");
					tDFileName = (String) matchResultMap.get("tDFileName");
					tdMsgByMainQueryPK = (String) matchResultMap.get("tdMsg");
				}
			}
		}
		// 如果是EditUI，则优先根据 MainQueryPK 匹配(通过反射机制，匹配对应ListUI的套打信息)
		else {
			// 根据 MainQueryPK 匹配套打
			matchResultMap = _matchTDByMainQueryPK(coreUI, oldTDQueryPK, oldTDFileName);
			if (null != matchResultMap) {
				tDQueryPK = (IMetaDataPK) matchResultMap.get("tDQueryPK");
				tDFileName = (String) matchResultMap.get("tDFileName");
				tdMsgByMainQueryPK = (String) matchResultMap.get("tdMsg");
			}
			// 根据 UI名称 匹配套打
			if (null == tDQueryPK) {
				matchResultMap = _matchTDByUIName(coreUI, oldTDQueryPK, oldTDFileName);
				if (null != matchResultMap) {
					tDQueryPK = (IMetaDataPK) matchResultMap.get("tDQueryPK");
					tDFileName = (String) matchResultMap.get("tDFileName");
					tdMsgByUIName = (String) matchResultMap.get("tdMsg");
				}
			}
		}
		// 根据 Entity名称 匹配套打
		if (null == tDQueryPK) {
			matchResultMap = _matchTDByEntityName(coreUI, oldTDQueryPK, oldTDFileName);

			if (null != matchResultMap) {
				tDQueryPK = (IMetaDataPK) matchResultMap.get("tDQueryPK");
				tDFileName = (String) matchResultMap.get("tDFileName");
				tdMsgByEntityName = (String) matchResultMap.get("tdMsg");
			}
		}

		// 没有匹配到任何套打模板，给出提示信息
		if (null == tDQueryPK) {
			if (!StringUtils.isEmpty(tdMsgByUIName)) {
				tdMsg = tdMsgByUIName;
			} else if (!StringUtils.isEmpty(tdMsgByMainQueryPK)) {
				tdMsg = tdMsgByMainQueryPK;
			} else if (!StringUtils.isEmpty(tdMsgByEntityName)) {
				tdMsg = tdMsgByEntityName;
			}

			if (null != oldTDQueryPK || !StringUtils.isEmpty(oldTDFileName)) {
				StringBuffer stringBuffer = new StringBuffer();

				stringBuffer.append("原套打信息有误：").append("\n");
				stringBuffer.append("模板：").append((null != oldTDFileName) ? oldTDFileName : "").append("\n");
				stringBuffer.append("元数据：").append((null != oldTDQueryPK) ? oldTDQueryPK.getFullName() : "").append(
						"\n\n\n");

				tdMsg = stringBuffer.toString() + tdMsg;
			}
		}

		FdcMethodUtil.invokeMethod(coreUI, "setTDQueryPK", new Class[] { IMetaDataPK.class },
				new Object[] { tDQueryPK }, true);
		FdcMethodUtil.invokeMethod(coreUI, "setTDFileName", new Class[] { String.class }, new Object[] { tDFileName },
				true);

		return tdMsg;
	}

	/**
	 * 根据 UI名称 匹配套打
	 * 
	 * @param coreUI
	 * @param oldTDQueryPK
	 * @param oldTDFileName
	 * @return
	 */
	private static Map _matchTDByUIName(CoreUI coreUI, IMetaDataPK oldTDQueryPK, String oldTDFileName) {
		Map map = new HashMap();

		// 从CoreUI中匹配对应的实体名称
		String entityName = FDCClientHelper.matchEntityName(coreUI);
		// 取得UI所在模块包名
		String modelPackageName = FDCClientHelper.getModelPackageName(coreUI);

		map = _loadTD(entityName, modelPackageName, oldTDQueryPK, oldTDFileName);

		return map;
	}

	/**
	 * 根据 MainQueryPK 匹配套打
	 * 
	 * @param coreUI
	 * @param oldTDQueryPK
	 * @param oldTDFileName
	 * @return
	 */
	private static Map _matchTDByMainQueryPK(CoreUI coreUI, IMetaDataPK oldTDQueryPK, String oldTDFileName) {
		Map map = new HashMap();

		ListUI listUI = null;
		IMetaDataPK mainQueryPK = null;

		if (coreUI instanceof ListUI) {
			listUI = (ListUI) coreUI;
		} else {
			String uiClassName = coreUI.getClass().getName();
			String listUIClassName = null;

			// EditUI
			if (coreUI instanceof EditUI && uiClassName.endsWith(FDCConstants.EDIT_UI_SUFFIX)) {
				listUIClassName = uiClassName.replaceFirst(FDCConstants.EDIT_UI_SUFFIX, FDCConstants.LIST_UI_SUFFIX);
			}

			try {
				listUI = (ListUI) Class.forName(listUIClassName).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (null == listUI) {
				// 从CoreUI中匹配对应的实体名称
				String entityName = FDCClientHelper.matchEntityName(coreUI);

				// 取得UI所在模块包名
				String modelPackageName = FDCClientHelper.getModelPackageName(coreUI);
				// 取得UI对应的client包名
				String clientPackageName = modelPackageName + ".client";

				listUIClassName = clientPackageName + "." + entityName + FDCConstants.LIST_UI_SUFFIX;

				try {
					listUI = (ListUI) Class.forName(listUIClassName).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != listUI) {
				String editUIClassName = (String) FdcMethodUtil.invokeNoParameterMethod(listUI, "getEditUIName", true);
				// EditUI名称必须匹配
				if (!coreUI.getClass().getName().equals(editUIClassName)) {
					listUI = null;
				} else {
					// 如果EditUI中的TD为空，则从对应的ListUI中间取
					if (null == oldTDQueryPK) {
						// 旧套打Query元数据
						oldTDQueryPK = (IMetaDataPK) FdcMethodUtil
								.invokeNoParameterMethod(listUI, "getTDQueryPK", true);
					}
					if (StringUtils.isEmpty(oldTDFileName)) {
						// 旧套打模板文件名
						oldTDFileName = (String) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDFileName", true);
					}
				}
			}
		}

		if (null != listUI) {
			mainQueryPK = listUI.getMainQueryPK();
		}

		if (null != mainQueryPK) {
			// 从CoreUI中匹配对应的实体名称
			String entityName = mainQueryPK.getName();
			if (entityName.endsWith("Query")) {
				entityName = entityName.substring(0, entityName.lastIndexOf("Query"));
			}

			// 取得mainQueryPK对应的app包名
			String appPackageName = mainQueryPK.getPackage();
			// 取得mainQueryPK所在模块包名
			String modelPackageName = appPackageName.substring(0, appPackageName.lastIndexOf(".app"));

			map = _loadTD(entityName, modelPackageName, oldTDQueryPK, oldTDFileName);
		}

		return map;
	}

	/**
	 * 根据 Entity名称 匹配套打
	 * 
	 * @param coreUI
	 * @param oldTDQueryPK
	 * @param oldTDFileName
	 * @return
	 */
	private static Map _matchTDByEntityName(CoreUI coreUI, IMetaDataPK oldTDQueryPK, String oldTDFileName) {
		Map map = new HashMap();

		EntityObjectInfo entityObjectInfo = null;
		try {
			entityObjectInfo = _getEntity(coreUI);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != entityObjectInfo) {
			String entityName = entityObjectInfo.getName();
			String modelPackageName = entityObjectInfo.getPackage();

			map = _loadTD(entityName, modelPackageName, oldTDQueryPK, oldTDFileName);
		}

		return map;
	}

	/**
	 * 加载套打
	 * 
	 * @param entityName
	 * @param modelPackageName
	 * @param oldTDQueryPK
	 * @param oldTDFileName
	 * @return
	 */
	private static Map _loadTD(String entityName, String modelPackageName, IMetaDataPK oldTDQueryPK,
			String oldTDFileName) {
		Map map = new HashMap();

		String tdQuerySimpleNamePattern = null;
		String tdQuerySimpleName = null;
		String tdQueryFullName = null;
		QueryInfo tdQueryInfo = null;

		// 套打Query元数据
		IMetaDataPK tDQueryPK = null;
		// 套打模板文件名
		String tDFileName = null;
		// 推荐套打Query元数据
		IMetaDataPK suggestTDQueryPK = null;
		// 推荐模板文件名
		String suggestTDFileName = null;
		// 套打消息
		String tdMsg = null;

		int easPakageNameLength = FDCConstants.EAS_PAKAGE_NAME.length();
		int arrLength = (null != entityName) ? FDCConstants.TDQUERY_SIMPLE_NAME_PATTERN_ARR.length : 0;
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrLength; i++) {
			tdQuerySimpleNamePattern = FDCConstants.TDQUERY_SIMPLE_NAME_PATTERN_ARR[i];
			// 此处有待验证???
			tdQuerySimpleName = tdQuerySimpleNamePattern.replaceFirst("\\{0\\}", entityName);
			tdQueryFullName = modelPackageName + ".app." + tdQuerySimpleName;

			// 推荐套打Query元数据
			// TDQueryPK格式：new
			// MetaDataPK("com.kingdee.eas.ec.account.aim.app.AimDirectBillPrintQuery")
			suggestTDQueryPK = new MetaDataPK(tdQueryFullName);

			// 如果子类有重写，则保留原来的
			if (null != oldTDQueryPK) {
				tDQueryPK = oldTDQueryPK;
			} else {
				tDQueryPK = suggestTDQueryPK;
			}
			// 加载元数据
			tdQueryInfo = MetaDataLoader.getQuery(null, tDQueryPK);

			// 推荐模板文件名
			suggestTDFileName = modelPackageName.substring(easPakageNameLength) + "." + entityName;
			suggestTDFileName = suggestTDFileName.replaceAll("\\.", "/");
			// TDFileName格式："/bim/ec/account/aim/app/AimDirectBill"
			suggestTDFileName = FDCConstants.TD_FILE_NAME_PREFIX + suggestTDFileName;

			// 如果子类有重写，则保留原来的
			if (!StringUtils.isEmpty(oldTDFileName)) {
				tDFileName = oldTDFileName;
			} else {
				tDFileName = suggestTDFileName;
			}

			if (null == tdQueryInfo) {
				if (0 == i) {
					stringBuffer.append("请参照以下格式配置套打信息：").append("\n");
					stringBuffer.append("模板：").append(suggestTDFileName).append("\n\n");
					stringBuffer.append("元数据：").append(suggestTDQueryPK.getFullName()).append("\n");
				} else if (0 != (arrLength - 1)) {
					stringBuffer.append("\n或：");
					stringBuffer.append(suggestTDQueryPK.getFullName()).append("\n");
				}
			} else {
				// 找到套打Query，停止加载
				break;
			}
		}

		if (null == tdQueryInfo) {
			// 重置套打信息
			tDQueryPK = null;
			tDFileName = null;

			tdMsg = stringBuffer.toString();
		}

		map.put("tDQueryPK", tDQueryPK);
		map.put("tDFileName", tDFileName);
		map.put("tdMsg", tdMsg);

		return map;
	}

	/**
	 * 校验套打
	 * 
	 * @param listUI
	 * @param tDQueryPK
	 * @param tDFileName
	 * @param tdMsg
	 */
	public static void checkTD(ListUI listUI, IMetaDataPK tDQueryPK, String tDFileName, String tdMsg) {
		_checkTD(listUI, tDQueryPK, tDFileName, tdMsg);
	}

	/**
	 * 校验套打
	 * 
	 * @param editUI
	 * @param tDQueryPK
	 * @param tDFileName
	 * @param tdMsg
	 */
	public static void checkTD(EditUI editUI, IMetaDataPK tDQueryPK, String tDFileName, String tdMsg) {
		_checkTD(editUI, tDQueryPK, tDFileName, tdMsg);
	}

	/**
	 * 校验套打
	 * 
	 * @param coreUI
	 * @param tDQueryPK
	 * @param tDFileName
	 * @param tdMsg
	 */
	private static void _checkTD(CoreUI coreUI, IMetaDataPK tDQueryPK, String tDFileName, String tdMsg) {
		// 提示配置套打信息
		if (tDQueryPK == null || StringUtils.isEmpty(tDFileName)) {
			String msg = null;

			if (!StringUtils.isEmpty(tdMsg)) {
				msg = tdMsg;
			} else {
				msg = "不提供套打功能";
			}

			MsgBox.showWarning(coreUI, msg);
			SysUtil.abort();
		}
	}
	
	/**
	 * 取得CoreUI对应的套打查询PK
	 * 
	 * @param coreUI
	 * @return
	 */
	public static IMetaDataPK getPrintQueryPK(CoreUI coreUI) {
		IMetaDataPK tdQueryPK = null;

		ListUI listUI = null;
		EditUI editUI = null;
		if (coreUI instanceof FDCBillListUI) {
			listUI = (FDCBillListUI) coreUI;
			// 取得套打查询PK
			tdQueryPK = (IMetaDataPK) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDQueryPK", true);
			// 如果没有，则自动匹配套打模板
			if (null == tdQueryPK) {
				FDCClientHelper.matchTD(listUI);
				// 取得套打查询PK
				tdQueryPK = (IMetaDataPK) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDQueryPK", true);
			}
		}
		if (coreUI instanceof FDCBillEditUI) {
			editUI = (FDCBillEditUI) coreUI;
			// 取得套打查询PK
			tdQueryPK = (IMetaDataPK) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDQueryPK", true);
			// 如果没有，则自动匹配套打模板
			if (null == tdQueryPK) {
				FDCClientHelper.matchTD(editUI);
				// 取得套打查询PK
				tdQueryPK = (IMetaDataPK) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDQueryPK", true);
			}
		}

		return tdQueryPK;
	}

	/**
	 * 取得CoreUI对应的套打模板文件名
	 * 
	 * @param coreUI
	 * @return
	 */
	public static String getTDFileName(CoreUI coreUI) {
		String tdFileName = null;

		ListUI listUI = null;
		EditUI editUI = null;
		if (coreUI instanceof FDCBillListUI) {
			listUI = (FDCBillListUI) coreUI;
			// 取得套打模板文件名
			tdFileName = (String) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDFileName", true);
			// 如果没有，则自动匹配套打模板
			if (null == tdFileName) {
				FDCClientHelper.matchTD(listUI);
				// 取得套打模板文件名
				tdFileName = (String) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDFileName", true);
			}
		}
		if (coreUI instanceof FDCBillEditUI) {
			editUI = (FDCBillEditUI) coreUI;
			// 取得套打模板文件名
			tdFileName = (String) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDFileName", true);
			// 如果没有，则自动匹配套打模板
			if (null == tdFileName) {
				FDCClientHelper.matchTD(editUI);
				// 取得套打模板文件名
				tdFileName = (String) FdcMethodUtil.invokeNoParameterMethod(listUI, "getTDFileName", true);
			}
		}

		return tdFileName;
	}

	/**
	 * 初始化UI类参数Map，并返回
	 * <p>
	 * 1、其中key存放参数名，value存放参数值数组<br>
	 * 2、以";"分割解析不同参数，以"="分割解析键值对<br>
	 * 3、相同键的值，按照先后顺序存放在数组中<br>
	 * 
	 * @param ui
	 *            UI对象
	 * @return UI类参数Map
	 */
	public static Map initUIClassParamMap(IUIObject ui) {
		Map map = new LinkedHashMap();

		Map uiContext = ui.getUIContext();
		map = (Map) uiContext.get(FDCConstants.UI_CLASS_PARAM_MAP);
		if (null == map) {
			map = new LinkedHashMap();

			// 设置UI类参数Map到UI上下文中
			uiContext.put(FDCConstants.UI_CLASS_PARAM_MAP, map);
		}
		String uiClassParam = (String) uiContext.get(FDCConstants.UI_CLASS_PARAM);

		// 已经初始化
		if (FdcMapUtil.isEmpty(map)
		// UI类参数不为空
				&& FdcStringUtil.isNotBlank(uiClassParam)) {
			// 用";"分割符解析UI类参数
			String[] paramArr = (String[]) uiClassParam.split(";");

			if (FdcArrayUtil.isNotEmpty(paramArr)) {
				int length = paramArr.length;
				String param = null;
				String key = null;
				String value = null;
				int len = -1;
				int index = -1;
				Map paramMap = new LinkedHashMap();
				List valueList = null;

				for (int i = 0; i < length; i++) {
					param = paramArr[i];
					len = param.length();
					index = param.indexOf("=");

					if (-1 != index) {
						// 取得键值
						if (len - 1 == index) {
							key = param;
							value = "";
						} else {
							key = param.substring(0, index);
							value = param.substring(index + 1);
						}

						// 汇总相同的键的值到同一List中
						valueList = (List) paramMap.get(key);
						if (null == valueList) {
							valueList = new ArrayList();
							paramMap.put(key, valueList);
						}
						valueList.add(value);
					}
				}

				Set paramSet = paramMap.keySet();
				String[] valueArr = null;
				for (Iterator iterator = paramSet.iterator(); iterator.hasNext();) {
					key = (String) iterator.next();
					valueList = (List) paramMap.get(key);
					// 将值List转换成值Array
					valueArr = (String[]) valueList.toArray(new String[0]);

					map.put(key, valueArr);
				}

			}
		}

		return map;
	}

	/**
	 * 返回UI类参数Map
	 * <p>
	 * 1、其中key存放参数名，value存放参数值数组<br>
	 * 
	 * @param ui
	 * @return
	 */
	public static Map getUIClassParamMap(IUIObject ui) {
		return initUIClassParamMap(ui);
	}

	/**
	 * 返回UI类参数Url
	 * 
	 * @param ui
	 * @return
	 */
	public static String getUIClassParamUrl(IUIObject ui) {
		String uiClassParam = null;

		Map uiContext = ui.getUIContext();
		uiClassParam = (String) uiContext.get(FDCConstants.UI_CLASS_PARAM);

		return uiClassParam;
	}

	/**
	 * 返回指定名称的UI类参数
	 * 
	 * @param ui
	 * @param key
	 * @return
	 */
	public static String[] getUIClassParam(IUIObject ui, String key) {
		String[] valueArr = null;

		// 返回UI类参数Map
		Map map = getUIClassParamMap(ui);
		valueArr = (String[]) map.get(key);

		return valueArr;
	}

	/**
	 * 附加UI类参数，并返回UI类参数Map
	 * <p>
	 * 1、其中key存放参数名，value存放参数值数组<br>
	 * 
	 * @param ui
	 * @return
	 */
	public static Map appendUIClassParam(IUIObject ui, String key, String value) {
		Map map = new LinkedHashMap();

		Map uiContext = ui.getUIContext();
		String uiClassParam = (String) uiContext.get(FDCConstants.UI_CLASS_PARAM);
		map = initUIClassParamMap(ui);
		String param = key + "=" + value;

		uiClassParam = FdcObjectUtil.defaultIfNull(uiClassParam, "");
		// 设置UI类参数
		if (FdcStringUtil.isNotBlank(uiClassParam)) {
			uiClassParam += ";" + param;
		} else {
			uiClassParam += param;
		}
		uiContext.put(FDCConstants.UI_CLASS_PARAM, uiClassParam);

		// 设置UI类参数Map
		String[] valueArr = (String[]) map.get(key);
		valueArr = (String[]) FdcObjectUtil.defaultIfNull(valueArr, new String[0]);
		valueArr = (String[]) FdcArrayUtil.add(valueArr, value, new String[0]);
		map.put(key, valueArr);

		return map;
	}

	/**
	 * 是否可见
	 * 
	 * @param component
	 *            组件
	 * @return
	 */
	public static boolean isVisible(Component component) {
		return (null != component && component.isVisible());
	}

	/**
	 * 是否可用
	 * 
	 * @param component
	 *            组件
	 * @return
	 */
	public static boolean isEnabled(Component component) {
		return (null != component && component.isEnabled());
	}

	/**
	 * 是否可见、可用
	 * 
	 * @param component
	 *            组件
	 * @return
	 */
	public static boolean isVisEn(Component component) {
		return (null != component && component.isVisible() && component.isEnabled());
	}

	/**
	 * 设置背景颜色
	 * @param table
	 */
	public static void setEnableColumnColor(KDTable table) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (table.getColumn(i).getStyleAttributes().isLocked()) {
				table.getColumn(i).getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
			}
		}
	}

	public static SelectorItemCollection getSelectorItemCollection() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		return sic;
	}

	/**
	 * 取得文本框
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param propertyName
	 *            属性名
	 * @return
	 */
	public static KDTextField getKDTextField(CoreUI coreUI, String propertyName) {
		KDTextField txtField = null;

		Component component = coreUI.getDataBinder().getComponetByField(propertyName);
		if (component instanceof KDTextField) {
			txtField = (KDTextField) component;
		}

		return txtField;
	}

	/**
	 * 取得文本框 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param propertyNameArr
	 *            属性名 数组
	 * @return
	 */
	public static List getKDTextFieldList(CoreUI coreUI, String[] propertyNameArr) {
		List list = new ArrayList();

		String propertyName = null;
		KDTextField txtField = null;
		for (int i = 0, length = propertyNameArr.length; i < length; i++) {
			propertyName = propertyNameArr[i];
			txtField = getKDTextField(coreUI, propertyName);
			if (null != txtField) {
				list.add(txtField);
			}
		}

		return list;
	}

	/**
	 * 取得整形/浮点型输入框
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param propertyName
	 *            属性名
	 * @return
	 */
	public static KDFormattedTextField getKDFormattedTextField(CoreUI coreUI, String propertyName) {
		KDFormattedTextField txtField = null;

		Component component = coreUI.getDataBinder().getComponetByField(propertyName);
		if (component instanceof KDFormattedTextField) {
			txtField = (KDFormattedTextField) component;
		}

		return txtField;
	}

	/**
	 * 取得整形/浮点型输入框 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param propertyNameArr
	 *            属性名 数组
	 * @return
	 */
	public static List getKDFormattedTextFieldList(CoreUI coreUI, String[] propertyNameArr) {
		List list = new ArrayList();

		String propertyName = null;
		KDFormattedTextField txtField = null;
		for (int i = 0, length = propertyNameArr.length; i < length; i++) {
			propertyName = propertyNameArr[i];
			txtField = getKDFormattedTextField(coreUI, propertyName);
			if (null != txtField) {
				list.add(txtField);
			}
		}

		return list;
	}

	/**
	 * 取得F7控件
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param propertyName
	 *            属性名
	 * @return
	 */
	public static KDBizPromptBox getKDBizPromptBox(CoreUI coreUI, String propertyName) {
		KDBizPromptBox bizPromptBox = null;

		Component component = coreUI.getDataBinder().getComponetByField(propertyName);
		if (component instanceof KDBizPromptBox) {
			bizPromptBox = (KDBizPromptBox) component;
		}

		return bizPromptBox;
	}

	/**
	 * 取得F7控件 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param propertyNameArr
	 *            属性名 数组
	 * @return
	 */
	public static List getKDBizPromptBoxList(CoreUI coreUI, String[] propertyNameArr) {
		List list = new ArrayList();

		String propertyName = null;
		KDBizPromptBox bizPromptBox = null;
		for (int i = 0, length = propertyNameArr.length; i < length; i++) {
			propertyName = propertyNameArr[i];
			bizPromptBox = getKDBizPromptBox(coreUI, propertyName);
			if (null != bizPromptBox) {
				list.add(bizPromptBox);
			}
		}

		return list;
	}

	/**
	 * 设置整形/浮点型输入框 数组 精度
	 * <p>
	 * 默认是四舍五入
	 * 
	 * @param formattedTextFields
	 *            整形/浮点型输入框 数组
	 * @param scale
	 *            精度
	 */
	public static void setScale(KDFormattedTextField[] formattedTextFields, int scale) {
		for (int i = 0, len = formattedTextFields.length; i < len; i++) {
			setScale(formattedTextFields[i], scale);
		}
	}

	/**
	 * 设置整形/浮点型输入框 精度
	 * <p>
	 * 默认是四舍五入
	 * 
	 * @param formattedTextField
	 *            整形/浮点型输入框
	 * @param scale
	 *            精度
	 */
	public static void setScale(KDFormattedTextField formattedTextField, int scale) {
		BigDecimal value = formattedTextField.getBigDecimalValue();
		if (null != value) {
			// 由于 BigDecimal 对象是不可变的，因此setScale的调用不会 导致初始对象被修改，
			// 这与使用名为 setX 变异字段 X 方法的常规约定相反。
			// 相反，setScale 返回具有适当标度的对象；返回的对象不一定是新分配的。
			value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
			formattedTextField.setValue(value);
		}
	}

	/**
	 * 取得业务接口
	 * 
	 * @param coreUI
	 *            核心UI
	 * @return
	 */
	public static ICoreBase getBizInterface(CoreUI coreUI) {
		ICoreBase bizInterface = (ICoreBase) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getBizInterface", true);

		return bizInterface;
	}

	/**
	 * 取得业务接口对应的BOSType对象
	 * 
	 * @param coreUI
	 *            核心UI
	 * @return
	 */
	public static BOSObjectType getBizType(CoreUI coreUI) {
		BOSObjectType bizType = (BOSObjectType) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getBizType", true);

		return bizType;
	}

	/**
	 * 取得业务接口对应的BOSType对象字符串
	 * 
	 * @param coreUI
	 *            核心UI
	 * @return
	 */
	public static String getEntityBOSType(CoreUI coreUI) {
		String entityBOSType = (String) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getEntityBOSType", true);

		return entityBOSType;
	}

	/**
	 * 取得编辑界面名称
	 * 
	 * @param coreUI
	 *            核心UI
	 * @return
	 */
	public static String getEditUIName(CoreUI coreUI) {
		String editUIName = (String) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getEditUIName", true);

		return editUIName;
	}

	/**
	 * 取得编辑界面默认打开方式
	 * 
	 * @param coreUI
	 *            核心UI
	 * @return
	 */
	public static String getEditUIModal(CoreUI coreUI) {
		String editUIModal = (String) FdcMethodUtil.invokeNoParameterMethod(coreUI, "getEditUIModal", true);

		return editUIModal;
	}

	/**
	 * 取得核心UI中 表格 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List getTableList(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List tableList = new ArrayList();

		// 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
		List fieldList = FdcReflectUtil.getAllFieldList(coreUI.getClass(), lastSuperclass);
		if (FdcCollectionUtil.isNotEmpty(fieldList)) {
			Field field = null;
			Object value = null;
			for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
				field = (Field) iterator.next();

				// 取得字段值
				value = FdcFieldUtil.getValue(coreUI, field, isIncludeNotPublic);
				if (value instanceof KDTable) {
					tableList.add(value);
				}
			}
		}

		return tableList;
	}

	/**
	 * 取得核心UI中 多行文本域 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List getTextAreaList(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List textAreaList = new ArrayList();

		// 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
		List fieldList = FdcReflectUtil.getAllFieldList(coreUI.getClass(), lastSuperclass);
		if (FdcCollectionUtil.isNotEmpty(fieldList)) {
			Field field = null;
			Object value = null;
			for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
				field = (Field) iterator.next();

				// 取得字段值
				value = FdcFieldUtil.getValue(coreUI, field, isIncludeNotPublic);
				if (value instanceof KDTextArea) {
					textAreaList.add(value);
				}
			}
		}

		return textAreaList;
	}

	/**
	 * 取得核心UI中 文本框 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List getTextFieldList(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List textFieldList = new ArrayList();

		// 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
		List fieldList = FdcReflectUtil.getAllFieldList(coreUI.getClass(), lastSuperclass);
		if (FdcCollectionUtil.isNotEmpty(fieldList)) {
			Field field = null;
			Object value = null;
			for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
				field = (Field) iterator.next();

				// 取得字段值
				value = FdcFieldUtil.getValue(coreUI, field, isIncludeNotPublic);
				if (value instanceof KDTextField) {
					textFieldList.add(value);
				}
			}
		}

		return textFieldList;
	}

	/**
	 * 取得核心UI中 格式化文本框 列表
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List getFormattedTextFieldList(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List formattedTextFieldList = new ArrayList();

		// 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
		List fieldList = FdcReflectUtil.getAllFieldList(coreUI.getClass(), lastSuperclass);
		if (FdcCollectionUtil.isNotEmpty(fieldList)) {
			Field field = null;
			Object value = null;
			for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
				field = (Field) iterator.next();

				// 取得字段值
				value = FdcFieldUtil.getValue(coreUI, field, isIncludeNotPublic);
				if (value instanceof KDFormattedTextField) {
					formattedTextFieldList.add(value);
				}
			}
		}

		return formattedTextFieldList;
	}

	/**
	 * 锁定核心UI中 表格
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List lockTable(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List tableList = null;

		// 界面的操作状态。
		String oprtState = coreUI.getOprtState();
		boolean flag = false;
		if (!OprtState.VIEW.equals(oprtState)) {
			flag = true;
		}
		// 取得容器中表格列表
		tableList = FDCClientHelper.getTableList(coreUI, AbstractFDCBillEditUI.class, true);
		KDTable table = null;
		for (Iterator iterator = tableList.iterator(); iterator.hasNext();) {
			table = (KDTable) iterator.next();
			// 查看状态下要锁定表格
			table.getStyleAttributes().setLocked(!flag);
		}

		return tableList;
	}

	/**
	 * 锁定核心UI中 多行文本域
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List lockTextArea(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List textAreaList = null;

		// 界面的操作状态。
		String oprtState = coreUI.getOprtState();
		boolean flag = false;
		if (!OprtState.VIEW.equals(oprtState)) {
			flag = true;
		}
		// 取得核心UI中多行文本域列表
		textAreaList = FDCClientHelper.getTextAreaList(coreUI, AbstractFDCBillEditUI.class, true);
		KDTextArea textArea = null;
		for (Iterator iterator = textAreaList.iterator(); iterator.hasNext();) {
			textArea = (KDTextArea) iterator.next();
			// 查看状态下要锁定多行文本域
			textArea.setEditable(!flag);
		}

		return textAreaList;
	}

	/**
	 * 锁定核心UI中 文本框
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List lockTextField(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List textFieldList = null;

		// 界面的操作状态。
		String oprtState = coreUI.getOprtState();
		boolean flag = false;
		if (!OprtState.VIEW.equals(oprtState)) {
			flag = true;
		}
		// 取得核心UI中文本框列表
		textFieldList = FDCClientHelper.getTextFieldList(coreUI, AbstractFDCBillEditUI.class, true);
		KDTextField textField = null;
		for (Iterator iterator = textFieldList.iterator(); iterator.hasNext();) {
			textField = (KDTextField) iterator.next();
			// 查看状态下要锁定文本框
			textField.setEditable(!flag);
		}

		return textFieldList;
	}

	/**
	 * 锁定核心UI中 格式化文本框
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List lockFormattedTextField(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) {
		List formattedFormattedTextFieldList = null;

		// 界面的操作状态。
		String oprtState = coreUI.getOprtState();
		boolean flag = false;
		if (!OprtState.VIEW.equals(oprtState)) {
			flag = true;
		}
		// 取得核心UI中格式化文本框列表
		formattedFormattedTextFieldList = FDCClientHelper.getFormattedTextFieldList(coreUI,
				AbstractFDCBillEditUI.class, true);
		KDFormattedTextField formattedFormattedTextField = null;
		for (Iterator iterator = formattedFormattedTextFieldList.iterator(); iterator.hasNext();) {
			formattedFormattedTextField = (KDFormattedTextField) iterator.next();
			// 查看状态下要锁定格式化文本框
			formattedFormattedTextField.setEditable(!flag);
		}

		return formattedFormattedTextFieldList;
	}

	/**
	 * 锁定核心UI中 组件
	 * <p>
	 * 1、暂时只锁定：表格控件，编辑类控件
	 * 
	 * @param coreUI
	 *            核心UI
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static List lockComponent(CoreUI coreUI, Class lastSuperclass, boolean isIncludeNotPublic) throws Exception {
		List componentList = new ArrayList();

		// 界面的操作状态。
		String oprtState = coreUI.getOprtState();
		boolean flag = false;
		if (!OprtState.VIEW.equals(oprtState)) {
			flag = true;
		}

		// 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
		List fieldList = FdcReflectUtil.getAllFieldList(coreUI.getClass(), lastSuperclass);
		if (FdcCollectionUtil.isNotEmpty(fieldList)) {
			Field field = null;
			Object value = null;
			KDTable table = null;
			// IKDTextComponent textComponent = null;

			for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
				field = (Field) iterator.next();
				// 取得字段值
				value = FdcFieldUtil.getValue(coreUI, field, isIncludeNotPublic);

				// 表格控件
				if (value instanceof KDTable) {
					table = (KDTable) value;
					table.getStyleAttributes().setLocked(!flag);

					componentList.add(value);
				}
				// // 编辑类控件
				// else if (value instanceof IKDTextComponent) {
				// textComponent = (IKDTextComponent) value;
				// textComponent.setEnabled(flag);
				//
				// componentList.add(value);
				// }
			}
		}

		return componentList;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 是否大于
	 * 
	 * @param ui
	 *            UI对象
	 * @param kdfTextField1
	 *            格式化输入框1
	 * @param kdfTextField2
	 *            格式化输入框2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isGreaterThan(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull) {
		boolean flag = false;

		BigDecimal value1 = kdfTextField1.getBigDecimalValue();
		BigDecimal value2 = kdfTextField2.getBigDecimalValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value1 && null != value2 && FDCNumberHelper.isGreaterThan(value1, value2)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isGreaterThan(value1, value2)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param kdfTextField1
	 *            格式化输入框1
	 * @param kdfTextField2
	 *            格式化输入框2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isLessThan(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull) {
		boolean flag = false;

		BigDecimal value1 = kdfTextField1.getBigDecimalValue();
		BigDecimal value2 = kdfTextField2.getBigDecimalValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value1 && null != value2 && FDCNumberHelper.isLessThan(value1, value2)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isLessThan(value1, value2)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param kdfTextField1
	 *            格式化输入框1
	 * @param kdfTextField2
	 *            格式化输入框2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isEqual(CoreUIObject ui, KDFormattedTextField kdfTextField1,
			KDFormattedTextField kdfTextField2, boolean isIgnoreNull) {
		boolean flag = false;

		BigDecimal value1 = kdfTextField1.getBigDecimalValue();
		BigDecimal value2 = kdfTextField2.getBigDecimalValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value1 && null != value2 && FDCNumberHelper.isEqual(value1, value2)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isEqual(value1, value2)) {
				flag = true;
			}
		}

		return flag;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 是否大于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param row
	 *            行
	 * @param key1
	 *            列名1
	 * @param key2
	 *            列名2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isGreaterThan(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull) {
		boolean flag = false;

		Object value1 = row.getCell(key1).getValue();
		Object value2 = row.getCell(key2).getValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value1 && null != value2 && FDCNumberHelper.isGreaterThan(value1, value2)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isGreaterThan(value1, value2)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否大于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param key1
	 *            列名1
	 * @param key2
	 *            列名2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static List isGreaterThan(CoreUIObject ui, KDTable table, String key1, String key2, boolean isIgnoreNull) {
		List flagList = new ArrayList();

		boolean flag = false;
		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = isGreaterThan(ui, table, row, key1, key2, isIgnoreNull);

			flagList.add(Boolean.valueOf(flag));
		}

		return flagList;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param row
	 *            行
	 * @param key1
	 *            列名1
	 * @param key2
	 *            列名2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isLessThan(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull) {
		boolean flag = false;

		Object value1 = row.getCell(key1).getValue();
		Object value2 = row.getCell(key2).getValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value1 && null != value2 && FDCNumberHelper.isLessThan(value1, value2)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isLessThan(value1, value2)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param key1
	 *            列名1
	 * @param key2
	 *            列名2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static List isLessThan(CoreUIObject ui, KDTable table, String key1, String key2, boolean isIgnoreNull) {
		List flagList = new ArrayList();

		boolean flag = false;
		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = isLessThan(ui, table, row, key1, key2, isIgnoreNull);

			flagList.add(Boolean.valueOf(flag));
		}

		return flagList;
	}

	/**
	 * 是否等于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param row
	 *            行
	 * @param key1
	 *            列名1
	 * @param key2
	 *            列名2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isEqual(CoreUIObject ui, KDTable table, IRow row, String key1, String key2,
			boolean isIgnoreNull) {
		boolean flag = false;

		Object value1 = row.getCell(key1).getValue();
		Object value2 = row.getCell(key2).getValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value1 && null != value2 && FDCNumberHelper.isEqual(value1, value2)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isEqual(value1, value2)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否等于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param key1
	 *            列名1
	 * @param key2
	 *            列名2
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static List isEqual(CoreUIObject ui, KDTable table, String key1, String key2, boolean isIgnoreNull) {
		List flagList = new ArrayList();

		boolean flag = false;
		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = isEqual(ui, table, row, key1, key2, isIgnoreNull);

			flagList.add(Boolean.valueOf(flag));
		}

		return flagList;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 是否大于
	 * 
	 * @param ui
	 *            UI对象
	 * @param kdfTextField
	 *            格式化输入框
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isGreaterThan(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull) {
		boolean flag = false;

		BigDecimal value = kdfTextField.getBigDecimalValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value && null != compareValue && FDCNumberHelper.isGreaterThan(value, compareValue)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isGreaterThan(value, compareValue)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param kdfTextField
	 *            格式化输入框
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isLessThan(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull) {
		boolean flag = false;

		BigDecimal value = kdfTextField.getBigDecimalValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value && null != compareValue && FDCNumberHelper.isLessThan(value, compareValue)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isLessThan(value, compareValue)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param kdfTextField
	 *            格式化输入框
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isEqual(CoreUIObject ui, KDFormattedTextField kdfTextField, Object compareValue,
			boolean isIgnoreNull) {
		boolean flag = false;

		BigDecimal value = kdfTextField.getBigDecimalValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value && null != compareValue && FDCNumberHelper.isEqual(value, compareValue)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isEqual(value, compareValue)) {
				flag = true;
			}
		}

		return flag;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 是否大于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param row
	 *            行
	 * @param key
	 *            列名
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isGreaterThan(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull) {
		boolean flag = false;

		Object value = row.getCell(key).getValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value && null != compareValue && FDCNumberHelper.isGreaterThan(value, compareValue)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isGreaterThan(value, compareValue)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否大于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param key
	 *            列名
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static List isGreaterThan(CoreUIObject ui, KDTable table, String key, Object compareValue,
			boolean isIgnoreNull) {
		List flagList = new ArrayList();

		boolean flag = false;
		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = isGreaterThan(ui, table, row, key, compareValue, isIgnoreNull);

			flagList.add(Boolean.valueOf(flag));
		}

		return flagList;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param row
	 *            行
	 * @param key
	 *            列名
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isLessThan(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull) {
		boolean flag = false;

		Object value = row.getCell(key).getValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value && null != compareValue && FDCNumberHelper.isLessThan(value, compareValue)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isLessThan(value, compareValue)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否小于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param key
	 *            列名
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static List isLessThan(CoreUIObject ui, KDTable table, String key, Object compareValue, boolean isIgnoreNull) {
		List flagList = new ArrayList();

		boolean flag = false;
		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = isLessThan(ui, table, row, key, compareValue, isIgnoreNull);

			flagList.add(Boolean.valueOf(flag));
		}

		return flagList;
	}

	/**
	 * 是否等于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param row
	 *            行
	 * @param key
	 *            列名
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static boolean isEqual(CoreUIObject ui, KDTable table, IRow row, String key, Object compareValue,
			boolean isIgnoreNull) {
		boolean flag = false;

		Object value = row.getCell(key).getValue();
		String msg = null;

		if (isIgnoreNull) {
			if (null != value && null != compareValue && FDCNumberHelper.isEqual(value, compareValue)) {
				flag = true;
			}
		} else {
			if (FDCNumberHelper.isEqual(value, compareValue)) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 是否等于
	 * 
	 * @param ui
	 *            UI对象
	 * @param table
	 *            表格
	 * @param key
	 *            列名
	 * @param compareValue
	 *            比较值
	 * @param ignoreNull
	 *            是否忽略空值
	 * @return
	 */
	public static List isEqual(CoreUIObject ui, KDTable table, String key, Object compareValue, boolean isIgnoreNull) {
		List flagList = new ArrayList();

		boolean flag = false;
		IRow row = null;
		for (int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
			row = table.getRow(i);
			flag = isEqual(ui, table, row, key, compareValue, isIgnoreNull);

			flagList.add(Boolean.valueOf(flag));
		}

		return flagList;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 导出Ksql
	 * 
	 * @param comp
	 *            组件；不能为空
	 * @param bosType
	 *            Bos类型；不能为空
	 * @param idList
	 *            ID列表；可以为空
	 * @throws EASBizException
	 */
	public static void exportSql(Component comp, BOSObjectType bosType, List idList, boolean ifExist,
			String keyField) throws EASBizException {
		if (null == bosType) {
			FDCUtils.throwEASBizException("入口参数bosType(Bos类型)不能为空");
		}

		Context ctx = null;
		String bosTypeStr = bosType.toString();
		EntityObjectInfo entityObjectInfo = null;
		try {
			entityObjectInfo = FdcMetaDataUtil.getEntityObjectInfo(ctx, bosTypeStr);
		} finally {
			if (null == entityObjectInfo) {
				FDCUtils.throwEASBizException("Bos类型" + bosTypeStr + "对应的实体不存在");
			}
		}

		DataTableInfo dataTableInfo = entityObjectInfo.getTable();
		if (null == dataTableInfo) {
			String msg = "Bos类型{0}，有对应的实体：{1}{2}，但没有对应的数据表";
			String name = entityObjectInfo.getName();
			String alias = FdcObjectUtil.defaultIfNull(entityObjectInfo.getAlias(), "");
			Object[] params = new Object[] { bosTypeStr, name, alias };

			FDCUtils.throwEASBizException(msg, params);
		}

		String tableName = dataTableInfo.getName();

		// 导出Ksql
		exportSql(comp, tableName, idList, ifExist, keyField);
	}

	/**
	 * 导出Ksql
	 * 
	 * @param comp
	 *            组件；不能为空
	 * @param tableName
	 *            数据表名；不能为空
	 * @param idList
	 *            ID列表；可以为空
	 * @throws EASBizException
	 */
	public static void exportSql(Component comp, String tableName, List idList, boolean ifExist, String keyField)
			throws EASBizException {
		if (FdcStringUtil.isBlank(tableName)) {
			FDCUtils.throwEASBizException("入口参数tableName(数据表名)不能为空");
		}

		String sql = "SELECT * FROM " + tableName;
		if (FdcCollectionUtil.isNotEmpty(idList)) {
			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(idList);

			String[] columnValues = (String[])idList.toArray(new String[0]);
			String sqlFrag = FdcSqlUtil.generateInFrag("FID", columnValues, "WHERE");
			sql += sqlFrag;
		}

		// 导出Ksql
		exportSql(comp, tableName, sql, ifExist, keyField);
	}

	/**
	 * 导出Ksql
	 * 
	 * @param comp
	 *            组件；不能为空
	 * @param tableName
	 *            数据表名；不能为空
	 * @param sql
	 *            SQL语句；不能为空
	 * @throws EASBizException
	 */
	public static void exportSql(Component comp, String tableName, String sql, boolean ifExist, String keyField)
			throws EASBizException {
		if (FdcStringUtil.isBlank(tableName)) {
			FDCUtils.throwEASBizException("入口参数tableName(数据表名)不能为空");
		}
		if (FdcStringUtil.isBlank(sql)) {
			FDCUtils.throwEASBizException("入口参数sql(SQL语句)不能为空");
		}

		KDFileChooser chooser = new KDFileChooser();
		int result;
		BufferedWriter out = null;
		try {
			result = chooser.showSaveDialog(comp);
			if (result == KDFileChooser.APPROVE_OPTION) {
				File tempFile = chooser.getSelectedFile();
				if (tempFile.exists()) {
					tempFile.delete();
				}
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "utf-8"));
			} else
				return;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ISQLExecutor sqlExe = SQLExecutorFactory.getRemoteInstance(sql);

		ResultSet resut = null;
		try {
			resut = sqlExe.executeSQL();
			// 转换成插入KSQL语句
			StringBuffer sqlSb = FdcSqlUtil.transferToInsertKsql(tableName, resut, ifExist, keyField);

			out.write(sqlSb.toString());
			out.flush();
			out.close();
			System.out.println("this is the sql to export:" + sqlSb);
			MsgBox.showInfo(comp, "导出脚本成功");
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showInfo(comp, "导出脚本失败\n" + e.toString());
		} finally {
			SQLUtils.cleanup(resut);

			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得Action对应控件的文本
	 * 
	 * @param e
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-19
	 */
	public static String getActionText(ActionEvent e) {
		String text = null;
		
		Object source = e.getSource();
		if (source instanceof KDWorkButton) {
			KDWorkButton kdWorkButton = (KDWorkButton) source;
			text = kdWorkButton.getText();
		}
		
		return text;
	}

	/**
	 * 描述：取得Action命令
	 * 
	 * @param e
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-19
	 */
	public static String getActionCommand(ActionEvent e) {
		String command = e.getActionCommand();

		return command;
	}

	/**
	 * 描述：取得Action短命令
	 * 
	 * @param e
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-19
	 */
	public static String getActionShortCommand(ActionEvent e) {
		String shortCommand = null;
		
		String command = (null != e) ? e.getActionCommand() : null;
		if (null != command) {
			int lastIndex = command.lastIndexOf(".");
			int beginIndex = command.indexOf("$", lastIndex);
			shortCommand = command.substring(beginIndex + 1);
		}
	
		return shortCommand;
	}

	/**
	 * 描述：是否是指定Action动作
	 * 
	 * @param e
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-19
	 */
	public static boolean isAction(ActionEvent e, String actionShortCommand) {
		boolean flag = false;

		String shortCommand = getActionShortCommand(e);
		flag = actionShortCommand.equals(shortCommand);

		return flag;
	}

	/**
	 * 描述：是否是保存动作
	 * 
	 * @param e
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-19
	 */
	public static boolean isSaveAction(ActionEvent e) {
		boolean flag = isAction(e, "ActionSave");

		return flag;
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 检查单据是否在工作流中
	 * 
	 * @param ui
	 * @param id
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id) {
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}
		int i = 0;
		for (int n = procInsts.length; i < n; i++)
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i].getState()))
				instInfo = procInsts[i];

		if (instInfo != null) {
			MsgBox.showWarning(ui, EASResource
					.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_BindWfInstance"));
			SysUtil.abort();
		}
	}

	/**
	 * 检查单据是否在工作流中,提示自定义
	 * 
	 * @param ui
	 * @param id
	 */
	public static void checkBillInWorkflow(CoreUIObject ui, String id, String msg) {
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}
		int i = 0;
		for (int n = procInsts.length; i < n; i++)
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i].getState()))
				instInfo = procInsts[i];

		if (instInfo != null) {
			MsgBox.showWarning(ui, msg);
			SysUtil.abort();
		}
	}

	/**
	 * 描述：判断单据是否在工作流中
	 * 
	 * @param id
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-8-28
	 */
	public static boolean isBillInWorkflow(String id) {
		boolean flag = false;

		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		} catch (BOSException e) {
			// @AbortException
			ExceptionHandler.handle(e);
		}

		for (int i = 0, n = procInsts.length; i < n; i++) {
			// 流程挂起时也显示流程图
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i].getState())) {
				instInfo = procInsts[i];
				break;
			}
		}
		if (instInfo != null) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 获取选中行Id
	 * 
	 * @param table
	 * @param keyFieldName
	 * @return
	 */
	public static List getSelectedIdValues(KDTable table, String keyFieldName) {
		List ids = new ArrayList();
		int selectedRows[] = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectedRows.length; i++)
			if (table.getCell(selectedRows[i], keyFieldName) != null) {
				String id = (String) table.getCell(selectedRows[i], keyFieldName).getValue();
				ids.add(id);
			}

		return ids;
	}
	
    /**
	 * 描述：获取选中行字段值列表
	 * 
	 * @param table
	 * @param keyFieldName
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-30
	 */
	public static List getSelectedObjectValues(KDTable table, String keyFieldName) {
		List valueList = new ArrayList();
		if (null == table.getColumn(keyFieldName)) {
			return valueList;
		}

		int selectedRows[] = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectedRows.length; i++) {
			Object obj = table.getCell(selectedRows[i], keyFieldName).getValue();
			valueList.add(obj);
		}

		return valueList;
	}

	public static Set listToSet(List idList) {
		Set idSet = new HashSet(idList);

		return idSet;
	}

	public static Set getSetByArray(String array[]) {
		Set set = new HashSet();
		if (array == null)
			return set;
		for (int i = 0; i < array.length; i++)
			set.add(array[i]);

		return set;
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	public static void showOprtOK(Component comp) {
		MsgBox.showWarning(comp, EASResource.getString(FDCConstants.FdcResource, "oprateOK"));
	}
	
	/**
	 * 描述：是否进行了双击
	 * 
	 * @param e
	 *            鼠标事件
	 * @param whichButton
	 *            哪个键双击。-1时为任意键双击
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-1-8
	 */
	public static boolean isDoubleClick(KDTMouseEvent e, int whichButton) {
		boolean flag = false;

		if (e.getType() == 1 && e.getClickCount() == 2) {
			flag = true;
		} else {
			return flag;
		}

		if (e.getButton() == -1) {
			return flag;
		} else if (e.getButton() != whichButton) {
			flag = false;
		}

		return flag;
	}
    
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
