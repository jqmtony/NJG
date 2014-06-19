/*
 * @(#)FDCClientHelper.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:���ز��ͻ��˸�����
 * 
 * @author liupd date:2006-10-19
 *         <p>
 * @version EAS5.2
 */
public class FDCClientHelper {
	
	//ͼ�곣��
	/** ͼ�� �� ���� */
	public static final Icon ICON_AUDIT = EASResource.getIcon("imgTbtn_audit");

	/** ͼ�� �� ������ */
	public static final Icon ICON_UNAUDIT = EASResource.getIcon("imgTbtn_unaudit");

	/** ͼ�� �� ������ */
	public static final Icon ICON_CALCULATOR = EASResource.getIcon("imgTbtn_counter");

	/** ͼ�� �� ���� */
	public static final Icon ICON_SAVE = EASResource.getIcon("imgTbtn_save");

	/** ͼ�� �� �ύ */
	public static final Icon ICON_SUBMIT = EASResource.getIcon("imgTbtn_submit");

	/** ͼ�� �� ��һ */
	public static final Icon ICON_FIRST = EASResource.getIcon("imgTbtn_first");

	/** ͼ�� �� ��һ */
	public static final Icon ICON_PREVIOUS = EASResource.getIcon("imgTbtn_previous");

	/** ͼ�� �� ��һ */
	public static final Icon ICON_NEXT = EASResource.getIcon("imgTbtn_next");

	/** ͼ�� �� ��� */
	public static final Icon ICON_LAST = EASResource.getIcon("imgTbtn_last");

	/** ͼ�� �� ˢ�� */
	public static final Icon ICON_REFRESH = EASResource.getIcon("imgTbtn_refresh");

	/** ͼ�� �� �Զ����� */
	public static final Icon ICON_AUTOCOUNT = EASResource.getIcon("imgTbtn_autocount");

	/** ͼ�� �� ��� */
	public static final Icon ICON_SPLIT = EASResource.getIcon("imgTbtn_split");

	/** ͼ�� �� �ƶ� */
	public static final Icon ICON_MOVE = EASResource.getIcon("imgTbtn_move");
	
	private static final int NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL = KDFormattedTextField.BIGDECIMAL_TYPE;

	public static final int NUMBERTEXTFIELD_ALIGNMENT = JTextField.RIGHT;

	public final static Color KDTABLE_TOTAL_BG_COLOR = new Color(0xF6F6BF);
	
	public final static Color KDTABLE_COMMON_BG_COLOR = new Color(0xFCFBDF);

	public final static Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(0xF5F5E6);
	
	public final static Color KDTABLE_DISABLE_BG_COLOR =	new Color(0xE8E8E3);

	// ��ʽ����������
	public final static String KDTABLE_NUMBER_FTM = "%-.2n";

	// public final static String kdTablePercentFtm = "%{0.00%}f";
	public final static String KDTABLE_PERCENT_FTM = "%r{0.00}p";

	public final static String KDTABLE_DATE_FMT = "yyyy-MM-dd";

	public final static String ACTUAL_DIGIT_FMT = "#,##0.###########";

	// ����ıұ���Ϣ
	public final static HashMap mapPrecOfCurrency = new HashMap(32);
	
	//����Ļ��ʾ�����Ϣ
	protected static Map mapPrecOfExrate = new HashMap(32);

	// С������ɫ
	public static final int SUBTOTAL = 0xF5F5E6;

	// �ܼ�����ɫ
	public static final int TOTAL = 0xF6F6B6;

	// �����¼��ɫ
	public static final int PASTRECORD = 0xFFEA67;

	/** ������Դ·�� */
	public static final String RES = "com.kingdee.eas.fm.common.client.FMCommonClientResource";

	/** Ĭ�ϻ��ʱұ� */
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
	 * ���ݱұ�ID��ȡ�ұ���� <p>
	 * ��private����Ϊpublic�����ⲿʹ�á� Modified by Owen_wen 2010-09-15
	 * @param id �ұ�ID
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
	 * ��ȡС����ʽ
	 * 
	 * @param scale
	 *            С��λ��
	 * @param needkilobit
	 *            �Ƿ���ʾǧ��λ
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
	 * ��ʼ����֯ѡ���
	 * 
	 * @param bgTypeInfo
	 */
	public static void initOrgF7(BgTypeInfo bgTypeInfo,
			KDBizPromptBox bizPromptOrgUnit) {
		initOrgF7(bgTypeInfo, bizPromptOrgUnit, false);
	}

	/**
	 * ��ʼ����֯ѡ���
	 * 
	 * @param bgTypeInfo
	 */
	public static void initOrgF7(BgTypeInfo bgTypeInfo,
			KDBizPromptBox bizPromptOrgUnit, boolean isMultiChoose) {
		// ���Ԥ����֯F7
		bizPromptOrgUnit.setData(null);
		bizPromptOrgUnit.setEnabled(true);
		bizPromptOrgUnit.setEnabledMultiSelection(isMultiChoose);

		// TODO δ���
		OrgType[] orgType = { OrgType.Company };
		OrgTreeF7PromptBox box = new OrgTreeF7PromptBox(null, orgType);

		bizPromptOrgUnit.setEditFormat("$name$");
		bizPromptOrgUnit.setDisplayFormat("$name$");
		bizPromptOrgUnit.setSelector(box);
		box.show();
	}

	/**
	 * �����ڼ��� ����Ԥ��ʵ���ķ�¼����
	 */
	public static DefaultKingdeeTreeNode getPeriodTreeRoot(
			BgEntryCollection bgEntries) {
		// TODO ȷ���Ѿ���������
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();

		Iterator iter = bgEntries.iterator();

		while (iter.hasNext()) {
			BgEntryInfo bgEntryInfo = (BgEntryInfo) iter.next();
		}

		return root;
	}

	// /**
	// * ��������ڼ���յ��ڼ��ʼ���ڼ����ĸ�
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
	// // ����Ԥ���ڼ�ڵ�����
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
	 * �õ���ǰѡ�е����ڵ���ڼ�ڵ�
	 * 
	 * @return
	 */
	public static BgPeriodNode getCurrentBgPeriodNode(KDTree treeBgPeriod) {
		// �õ��û���ǰѡ�еĽڵ�,���õ����Ӧ��Ԥ���ڼ�
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
	 * �õ���ǰ��������ĩ�ڼ�ڵ�
	 * 
	 * @return
	 */
	public static BgPeriodNode getEndBgPeriodNode(KDTree treeBgPeriod) {
		// �õ��û���ǰѡ�еĽڵ�,���õ����Ӧ��Ԥ���ڼ�
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
	 * ����Ԥ��ģ����������
	 * 
	 */

	// public static void initBgFormTable(BgTemplateInfo bgTpInfo, KDTable
	// kdTable)
	// {
	// initBgFormTable_distribute(bgTpInfo,kdTable);
	// // ��ͷ�ں�ģʽ
	// kdTable.getHeadMergeManager().mergeBlock(0, 0, kdTable.getHeadRowCount()
	// - 1,
	// kdTable.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
	// }
	// /**
	// * ����Ԥ��ģ����������(�ֽ���)
	// *
	// */
	// public static void initBgFormTable_distribute(BgTemplateInfo bgTpInfo,
	// KDTable kdTable) {
	// // ����������
	// clearTable(kdTable);
	// System.out.println("id:" + bgTpInfo.getId());
	// // �������б�ͷ
	// IRow mainHeadRow = kdTable.addHeadRow();
	// IRow multiCurrencyHeadRow = null;
	//
	// // ��ʼ��Ԥ����Ŀ���Ԥ����Ŀ��
	// initBgItemColumnAndRow(bgTpInfo, kdTable);
	//
	// // ����Ԥ��Ҫ����
	// int columnCursor = kdTable.getColumnCount();
	// //Iterator bgElementIter = bgTpInfo.getBgTpColumns().iterator();
	// BgTemplateColumnCollection tpCols = bgTpInfo.getBgTpColumns();
	//		
	// // ��ʼ���ұ���Ϣ
	// boolean isMultiCurrency = bgTpInfo.getRefCurrencies().size() > 1;
	// CurrencyInfo soloCurrency = null;
	//
	// if (!isMultiCurrency) {
	// if (bgTpInfo.getRefCurrencies().size() > 0) {
	// //�п���û�б���
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
	// // ���ö�ұ���
	// if (bgElementInfo.getDataType().equals(BgDataTypeEnum.Amount) &&
	// isMultiCurrency) {
	// // �����û��������ұ���,����һ��
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
	// // ������ұ��趨��Ӧ�ľ���
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm(cyInfo.getId()));
	//
	// //���ö���ģʽ
	// ic.getStyleAttributes().getAlignment().setHorzionAlign(Alignment.RIGHT);
	//
	// ic.setMergeable(false);
	// ic.setKey(FMHelper.getBgElementKey(bgElementInfo, cyInfo));
	// mainHeadRow.getCell(columnCursor).setValue(bgElementInfo.getName());
	// multiCurrencyHeadRow.getCell(columnCursor).setValue(cyInfo.getName());
	// columnCursor++;
	// }
	// }
	// // �����ö�ұ���
	// else {
	// IColumn ic = kdTable.addColumn(columnCursor);
	//
	// // ������ұ��趨��Ӧ�ľ���
	// if (soloCurrency != null) {
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm(soloCurrency.getId()));
	// }else{
	// ic.getStyleAttributes().setNumberFormat(getNumberFtm());
	// }
	//
	// // ���ö���ģʽ
	// ic.getStyleAttributes().getAlignment().setHorzionAlign(Alignment.RIGHT);
	// ic.setMergeable(false);
	// ic.setKey(bgElementInfo.getNumber());
	// mainHeadRow.getCell(columnCursor).setValue(bgElementInfo.getName());
	// columnCursor++;
	// }
	// }
	//
	// // ��������˶�ұ���,����һ��������ͷ���ں�
	// if (multiCurrencyHeadRow != null) {
	// for (int i = 0; i < kdTable.getColumnCount(); i++) {
	// // Ӧ�ò������ǿ�
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
	// // ��ȡ�û����ӵ�ά�ȼ���
	// ReferencedDimensionCollection visibleRefDims = bgTpInfo.getVisibleDims();
	// Iterator visibleRefDimsIter = visibleRefDims.iterator();
	//
	// int columnCursor = 0;
	//
	// while (visibleRefDimsIter.hasNext()) {
	// //��ô��ж�Ӧ��ά��
	// ReferencedDimensionInfo refDimInfo = (ReferencedDimensionInfo)
	// visibleRefDimsIter.next();
	// String refName = refDimInfo.getBgDimension().getName();
	//
	// // ����һ��
	// IColumn ic = null;
	//
	// if (kdTable.getColumnIndex(refName) == -1) { //�����ڽ����ӵ���
	// ic = kdTable.addColumn(columnCursor);
	// ic.setKey(refName);
	// } else { //���д���
	// ic = kdTable.getColumn(refName);
	// }
	//
	// // ���ñ�ͷ����
	// kdTable.getHeadRow(0).getCell(columnCursor).setValue(refDimInfo.getBgDimension()
	// .getAlias(SysContext.getSysContext()
	// .getLocale()));
	//
	// // Ԥ����Ŀ�ĸ��Ӳ����Ա༭
	// ic.getStyleAttributes().getProtection().setLocked(true);
	//
	// // ���α��һ
	// columnCursor++;
	// }
	//
	// // ����ά����Ϣ�ж��м�����ʵ��Ԥ����Ŀ�õ�
	// int itemColumnsNum = visibleRefDims.size();
	//
	// // �������
	// Iterator iter = bgTpInfo.getBgTpRows().iterator();
	//
	// while (iter.hasNext()) {
	// // �ӵ������л�ȡinfo
	// BgTemplateRowInfo bgTpRowInfo = (BgTemplateRowInfo) iter.next();
	//
	// // ����һ��
	// IRow ir = kdTable.addRow();
	//
	// // userObject��һ���ַ���
	// ir.setUserObject(FMHelper.getBgItemsKey(
	// new Object[] {
	// bgTpRowInfo.getBgItem1(), bgTpRowInfo.getBgItem2(),
	// bgTpRowInfo.getBgItem3(),
	// bgTpRowInfo.getBgItem4(), bgTpRowInfo.getBgItem5(),
	// bgTpRowInfo.getBgItem6()
	// }));
	//
	// // ��ȡ��ͷ�еĵ�Ԫ�������õ�Ԫ��ֵ
	// ICell cell;
	//
	// // ����Ԥ����Ŀ����Ϣ
	// for (int i = 0; i < itemColumnsNum; i++) {
	// cell = ir.getCell(i);
	//
	// // ��ȡbgItem��key��ʹ��getValue�ķ�ʽȥѰֵ
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
	// // �Ե�һ����һ���,�������ͻ�
	// if (itemColumnsNum == 1) {
	// treeFormTable(kdTable);
	// }
	//
	// // ��Ҫ�ں�
	// kdTable.getMergeManager().mergeBlock(0, 0, kdTable.getRowCount() - 1,
	// itemColumnsNum - 1,
	// KDTMergeManager.FREE_ROW_MERGE);
	// }

	/**
	 * ������
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
	// * �õ�Ĭ�ϵ���֯��λ
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
	 * ����ѡ��
	 * 
	 * @param cbo
	 * @param anObject
	 */
	static public void setSelectObject(KDComboBox cbo, Object anObject) {
		if (CoreBaseInfo.class.isInstance(anObject)) {
			CoreBaseInfo newObject = (CoreBaseInfo) anObject;

			for (int i = 0; i < cbo.getItemCount(); i++) {
				Object obj = cbo.getItemAt(i);
				// yangzk 2006-3-9���������ж�
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
	// * ����Ԥ�㱨��ģ���ʼ��Ԥ����
	// *
	// * @param bgRptTpInfo
	// * @param kdtBgFormData
	// */
	// public static void initBgRptFormTable(BgRptTemplateInfo bgRptTpInfo,
	// KDTable kdTable) {
	// // ���ԭ�е�����
	// clearTable(kdTable);
	//
	// // �������б�ͷ
	// IRow mainHeadRow = kdTable.addHeadRow();
	//
	// // ��ʼ��Ԥ����Ŀ���Ԥ����Ŀ��
	// initBgRptItemColumnAndRow(bgRptTpInfo, kdTable);
	//
	// // ����Ԥ��Ҫ����
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
	// // �����ǵ�һ�ұ�
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
	// * ��ʼ��Ԥ�㱨��ı�����Ŀ
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
	// // ����һ��
	// int columnCursor = 0;
	// IColumn ic = kdTable.addColumn(columnCursor);
	// ic.setKey("");
	//
	// // TODO ���ñ�ͷ����
	// kdTable.getHeadRow(0).getCell(columnCursor).setValue("������Ŀ");
	//
	// // �������
	// Iterator iter = bgRptTpInfo.getBgRptTpRows().iterator();
	//
	// while (iter.hasNext()) {
	// // �ӵ������л�ȡinfo
	// BgRptTemplateRowInfo bgRptTpRowInfo = (BgRptTemplateRowInfo) iter.next();
	//
	// // ����һ��
	// IRow ir = kdTable.addRow();
	//
	// // ��Ԥ�㱨���ж������
	// ir.setUserObject(bgRptTpRowInfo);
	//
	// // ��ȡ��ͷ�еĵ�Ԫ�������õ�Ԫ��ֵ
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
	// // ��ЩԤ����Ŀ,���ǲ����Ա༭��
	// kdTable.getColumn(0).getStyleAttributes().getProtection().setLocked(true);
	// }

	/**
	 * ��ȡ���Ķ���ѡ��ʱ��,�����е�����
	 * 
	 * @param table
	 * @return
	 */
	public static Set getSelectedRows(KDTable table) {
		ArrayList arrayList = table.getSelectManager().getBlocks();

		TreeSet set = new TreeSet();

		for (int i = 0, size = arrayList.size(); i < size; i++) {
			// ��ȡһ��ѡ���
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
	 * ��ȡѡ�����,����Ƕ�ѡ,�򷵻ص�һ��
	 * 
	 * @param table
	 * @return
	 */
	public static int getSelectedRow(KDTable table) {

		KDTSelectBlock selectBlock = table.getSelectManager().get();
		return selectBlock.getTop();
	}

	/**
	 * У������������Ƿ����
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
	 * У��������Ƿ�Ϊ����
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
	 * У��������Ƿ�Ϊ����
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
	 * У��������Ƿ�Ϊ�������������㣩
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
	// * У��������Ƿ�Ϊ�����������㣩
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
	 * ��ȡDecimalFormat
	 */
	public static DecimalFormat getDecimalFormat() {
		DecimalFormat format = new DecimalFormat("#.##");
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);

		return format;
	}

	/**
	 * ��ȡDecimalFormat
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
	// * У���û�����֯ѡ���Ƿ���ȷ,���Ƿ����¼���֯
	// */
	// public static void checkSubOrgs(Component com, String[] lNumber) {
	// int size = lNumber.length;
	//
	// //�������֯�����Ĳ���
	// int[] qty = new int[size - 1];
	//
	// //Ĭ����֯���ѡ������
	// int[] dd = new int[6];
	//
	// Vector[] vec = new Vector[6];
	//
	// for (int i = 0; i < 6; i++) {
	// vec[i] = new Vector(4);
	// }
	//
	// //���������֯longNumber�����ļ���
	// String superNumber = lNumber[size - 1];
	// int superQty = superNumber.split("!").length;
	//
	// for (int i = 0; i < qty.length; i++) {
	// String number = lNumber[i];
	// qty[i] = number.split("!").length;
	//
	// if (qty[i] <= superQty) {
	// //ѡ����֯����ƽ�����ϼ�
	// String strErr = EASResource.getString(
	// "com.kingdee.eas.ma.bg.client.BgSchemeResource",
	// "pleaseSelectedSubOrg");
	// MsgBox.showWarning(com, strErr);
	//
	// SysUtil.abort();
	// }
	//
	// if (number.indexOf(superNumber) == -1) {
	// //ѡ�����֯���в����ڸ���֯���¼���֯
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
	// * �����¼���֯���ϣ�ѡ������֯�����ж�ѡ������֯�Ƿ�Ϊ�¼���֯
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
	// //����о������ж���һ��
	// isExist = true;
	// break;
	// }
	// }
	// if (!isExist) {
	// //����һ�鶼û��˵�������¼���֯,�ҵ�һ�������¼���֯�ļ�������
	// isYes = true;
	// break;
	// }
	// }
	// return isYes;
	// }
	//

	/**
	 * ����Text�ռ�ı������<br>
	 * ���ñ������ĵص������ initDataStatus()����,
	 * 
	 * @author fengrenfei �޸Ĵ˷����� ��������ң�
	 * 
	 * @param info
	 *            ҵ�����
	 * @param txtField
	 *            �ı���
	 * @param companyId
	 *            ��˾Id
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
			// �����˱����������༭
			txtField.setEditable(false);
			txtField.setEnabled(false);
			String number = getDispNumber(info, companyId);
			if (!FMHelper.isEmpty(number)) {
				info.setString("number", number);
				txtField.setText(number);
			}
		} else {
			// �����������ȥ���ɹ����Ա༭
			txtField.setEnabled(true);
			txtField.setEditable(true);
		}
	}

	/**
	 * ��ȡ������ʾʱ�ı���(�����ǶϺ�֧��)
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
					&& !codingRuleManager.isUseIntermitNumber(info, companyId)) {// ������ʾ�Ҳ��ǶϺ�֧��
				number = codingRuleManager.getNumber(info, companyId);
			}
		} catch (BOSException e) {
		} catch (CodingRuleException e) {
		} catch (EASBizException e) {
		}

		return number;
	}

	/**
	 * �����пռ�ı������
	 * 
	 * @param info
	 *            ʵ�����
	 * @param col
	 *            ��
	 * @param companyId
	 *            ��Ӧ��ҵ��˾
	 */
	public static void initNumber(CoreBaseInfo info, IColumn col,
			String companyId) {
		boolean rst = hasNumber(info, companyId);

		if (!rst) {
			// �����������ȡ���ɹ����Ա༭
			col.getStyleAttributes().setLocked(false);

		} else {
			// �����˱����������༭
			col.getStyleAttributes().setLocked(true);
			col.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		}
	}

	/**
	 * ���ݱ�������ȡ���룬���쳣��ʾ�ġ�
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
	 * ���ݱ�������ȡ���룬���쳣��ʾ�ġ�
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
	 * ��ʼ�����ݼƻ���������
	 */
	public static void initSpinAfterNow(KDSpinner spnBeginYear,
			KDSpinner spnBeginMonth, KDSpinner spnEndYear, KDSpinner spnEndMonth) {
		// ��ʼ��spinner
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
		// ��ʼ��spinner
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
	 * ��������ʼ�����
	 * @author:liupd
	 * ����ʱ�䣺2006-8-3 <p>
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
			
			//����ƻ�
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
		//Table�������֮������ԭ�ҵľ���
		tblMain.getDataRequestManager().addDataFillListener(listener);	
	}
	
	//����ԭ�ҽ��ľ���
	protected static void tblMainAfterDataFill(KDTDataRequestEvent e,KDTable tblMain,CoreUI ui) {
		int first = e.getFirstRow();
		int count = e.getLastRow() - e.getFirstRow() + 1;
		
		int indexOfCurPre = tblMain.getColumnIndex("currency.precision");
		int indexOfCurId = tblMain.getColumnIndex("currency.id");
		if(indexOfCurPre<0){
			return ;
		}
		//����һ��i<tblMain.getRowCount()���ж� ȷ��tblMain.getRow(i)����Ϊnull
		for (int i = first; i < first + count && i< tblMain.getRowCount(); i++) {	
			Object  obj  = tblMain.getRow(i).getCell(indexOfCurPre).getValue();
			if(!(obj instanceof Integer)){
				continue ;
			}
			Integer curPre = (Integer) tblMain.getRow(i).getCell(indexOfCurPre).getValue();
			
			int pre  = curPre.intValue();
			//���ڿ���Ϊ�յ����
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
	 * ��ȡ���б�ѡ�е���
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
	 * ��������Ĭ��ѡ�нڵ�
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
	 * ����Ĭ�Ͻڵ�Ϊ��ǰ��˾
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
	 * ��ȡ�ұ�༭��
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
	 * ��˾����ʼ��
	 * 
	 * @param tree
	 *            ���ؼ�
	 * @param targetUI
	 *            �������
	 * @param action
	 *            Ȩ��action
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
		// // TODO ����ֻ��ȡ��˾��name
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
			FDCMsgBox.showWarning("û�����óɱ������ָ̯����������!");
			SysUtil.abort();
		}
		comboMeasureIndex.addItems(measureIndexCollection.toArray());

	}
	
	/**
	 * ��ʼ������׶�
	 * 
	 * @param comBoMeasureStage
	 *            �ؼ�
	 */
	public static void initComboMeasureStage(KDComboBox comboMeasureStage,boolean isEdit) throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);//һʧ���ǧ�ź�
		if(isEdit){
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		}
		view.getSorter().add(new SorterItemInfo("number"));
		MeasureStageCollection measureStageCollection = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
		if(measureStageCollection==null||measureStageCollection.size()==0){
			FDCMsgBox.showWarning("û�����ò���׶�!");
			SysUtil.abort();
		}
		comboMeasureStage.addItems(measureStageCollection.toArray());

	}
	
	/**
	 * ��ʼ���ұ��б�
	 * 
	 * @param comboCurrency
	 *            �ұ�ؼ�
	 * @param isSelBaseCurr
	 *            �Ƿ�ѡ�е�ǰ��˾��λ��
	 */
	public static void initComboCurrency(KDComboBox comboCurrency,
			boolean isSelBaseCurr) throws EASBizException, BOSException {
		CurrencyCollection currencyCollection = (CurrencyCollection)ActionCache.get("FDCBillEditUIHandler.currencyCollection");
		// ��ʼ���ұ��б�
		if(currencyCollection==null){
			ICurrency iCurrency = CurrencyFactory.getRemoteInstance();
			currencyCollection = iCurrency.getCurrencyCollection(true);
		}
		comboCurrency.addItems(currencyCollection.toArray());

	}

	/**
	 * ��ʼ���ұ��б�
	 */
	public static void initComboCurrency(KDComboBox comboCurrency)
			throws EASBizException, BOSException {
		initComboCurrency(comboCurrency, false);
	}

	/**
	 * �Ե�Ԫ����бұ��ʽ��
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
	 * ���н��бұ��ʽ��
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
	 * ��ȡ��˾��λ��
	 */
	public static CurrencyInfo getBaseCurrency(CompanyOrgUnitInfo company)
			throws EASBizException, BOSException {
		CurrencyInfo currency = ContextHelperFactory.getRemoteInstance()
				.getCompanyBaseCurrency(company);
		return currency;
	}

	/**
	 * У���Ƿ�ѡ����
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
	 * ��ʾ����ɹ�
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
	 * ��ʾ����ɹ�
	 */
	public static void showSubmitMsg(CoreUI ui, String s) {
		ui.setMessageText(s);
		ui.showMessage();
	}

	/**
	 * ��ȡ�ܼ���
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
	 * ���������˻���ȡ��˾
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
	 * ���ݹ�����������Ҫ�ǹ�˾��ID���飬��ù�˾��Collection
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
	 * ���ݾ����Query������������������ݼ�
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
	// * ��֤��������״̬
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
	 * ���ݱұ��ʽ����Table�� ĳ�е����ݸ�ʽ
	 * 
	 * @param tblMain
	 * @param columnKey
	 * @param currencyID
	 */
	public static void setNumberFormat(KDTable tblMain, String columnKey,
			String currencyID) {
		// ���ý�����
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
	 * ��������ʵ��λ����ʽ��, ��ʽ��֮�󣬵�Ԫ���ֵΪString���ͣ��з���
	 * 
	 * @param cell
	 *            table cell
	 * @author:liupd ����ʱ�䣺2005-11-8
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
	 * ��������ʵ��λ����ʽ�����
	 * 
	 * @param table
	 * @param columKey
	 * @author:liupd ����ʱ�䣺2005-11-8
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
	 * ���F7�ؼ�
	 */
	public static void clearF7(KDBizPromptBox f7Item) {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", "null"));
		ev.setFilter(filter);
		f7Item.setEntityViewInfo(ev);
	}

	/**
	 * ���ݱұ�����ĳ��Ԫ������ݸ�ʽ
	 * 
	 * @param row
	 * @param columnKey
	 * @param currencyID
	 */
	public static void setNumberFormat(IRow row, String columnKey,
			String currencyID) {
		// ���ý�����
		String numberFormat = getNumberFtm(BOSUuid.read(currencyID));
		StyleAttributes styleAttributes = row.getCell(columnKey)
				.getStyleAttributes();
		styleAttributes.setNumberFormat(numberFormat);
		styleAttributes.setHorizontalAlign(HorizontalAlignment.RIGHT);

	}

	/**
	 * 
	 * ����������Table�����ݵ�Excel
	 * 
	 * @param ui
	 *            ��ǰUI
	 * @param table
	 *            Ҫ�������ݵ�KDTable
	 * @throws Exception
	 *             liupd ����ʱ�䣺2005-5-17
	 */
	public static void exportToExcel(Component ui, KDTable table)
			throws Exception {
		String filePath = null;

		// �����ļ�ѡ���
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
			// �û�ȡ���ˣ�����
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
	 * ���� if flag==true ���������У����򲻵���������
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

		// �����ļ�ѡ���
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
			// �û�ȡ���ˣ�����
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
				// ������ļ�����ӵ��������ȥ
				if (files[i].isFile()) {
					// data.add(files[i]);
					if (files[i].getName().equals(totalFileName)) {
						// MsgBox.showWarning(ui, EASResource.getString(RES,
						// "FileExisted"));
						status = MsgBox.showConfirm2(ui, EASResource.getString(
								RES, "FileExisted"));
						// ȡ��
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
	 * ����
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
	// * ���ݹ�����������ü�Ϣ�����Collection
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
	 * WorkButtonִ��ĳ�����ʱ����ʾִ�гɹ���Ϣ
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
		MsgBox.showError(ui, "����" + action.getExtendProperty("Name"));
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
		 * <Field><Name>company </Name> <Alias>���˹�˾ </Alias> <Description/>
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
			return "�ַ���";
		}
		if (dataType.equals(DataType.STRING)) {
			return "�ַ���";
		}

		if (dataType.equals(DataType.DATE)) {
			return "����";
		}
		if (dataType.equals(DataType.TIME)) {
			return "����";
		}
		if (dataType.equals(DataType.TIMESTAMP)) {
			return "����";
		}
		if (dataType.equals(DataType.DECIMAL)) {
			return "������";
		}
		if (dataType.equals(DataType.BOOLEAN)) {
			return "����";
		}

		return dataType.toString();

	}

	/**
	 * ���������ݲ���״̬����UI Title ע�⣺ʹ�ô˷�����Ҫ��֤UIԪ����Title�Ķ��壬���ܰ������༭�������鿴���ȣ� �����ʽ�ƻ�ģ���UI
	 * Title Ӧ�ö���Ϊ�����ʽ�ƻ�ģ�塱�������ǡ��ʽ�ƻ�ģ�� - �༭�� FMClientHelper.updateUITitle(this,
	 * resHelper.getString("this.title"));
	 * 
	 * @param UI����
	 * @author:liupd ����ʱ�䣺2005-8-15
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
	 * ���ݵ���id��ȡ���ɵ�ƾ֤����
	 * 
	 * @param billId
	 * @return
	 * @throws BOSException
	 */
	public static VoucherCollection getVoucherCollection(String billId)
			throws BOSException {
		// ͨ��botp��ת����Ϣ�²鵽���㵥ת����ƾ֤�ļ���
		String[] srcEntriesID = new String[] { billId };
		IBTPManager iBTPManager = BTPManagerFactory.getRemoteInstance();
		// ��ΰ�� �޸� 2005-06-20 ��ʼ
		// �޸�BUG(BT023830)���״���������
		// Map map = iBTPManager.getDestEntries("", "", srcEntriesID);

		Map map = iBTPManager.getDestEntries(BOSUuid.read(billId).getType()
				.toString(), new VoucherInfo().getBOSType().toString(),
				srcEntriesID);

		VoucherCollection colVoucher = null;
		// ��ΰ�� �޸� 2005-06-20 ����
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
	 * ͨ������id�ҵ����ݵ�number
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
	 * ������remove virtual companys
	 * 
	 * @param fullOrgUnits
	 *            FullOrgUnitInfo Array
	 * @return FullOrgUnitInfo Array that have removed virtual companys
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2005-8-25
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
	 * �鿴��Ҫ�ύ������
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
			// ��ʾUI
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
			// MessageBox("û�ж�Ӧ����");
		}

	}

	/**
	 * submitWF ������
	 * 
	 * @param ui
	 * @param bosType
	 * @param opertion
	 * @throws Exception
	 * @author:liupd ����ʱ�䣺2005-9-6
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
			// ��ʾUI
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
			// MessageBox("û�ж�Ӧ����");
		}

	}

	/**
	 * �鿴���������
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
			// MessageBox("û�ж�Ӧ����ʵ��");
		} else {
			// ��ʾUI
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
        	//���û������ʱ���̣��ж��Ƿ��п���չ�ֵ�����ͼ��չ��
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
	 * �������鿴�༶�������
	 * 
	 * @param ui
	 * @param id
	 * @throws BOSException
	 * @author:wangweidong ����ʱ�䣺2006-3-30
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
	 * �������鿴����������һ��������
	 * 
	 * @param ui
	 * @param id
	 * @throws BOSException
	 * @author:wangweidong ����ʱ�䣺2006-3-30
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
	 * ��ȡ�༭����������������Ĵ򿪷�ʽ��ҳǩ���´��ڡ�ģ̬���ڣ�
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
	 * ��������Table Cell��BigDecimalֵ�����ڰѼ���������ֵ����Cell�����������ֵ��У��
	 * 
	 * @param ui
	 *            ��ǰUI
	 * @param cell
	 *            Table Cell
	 * @param number
	 *            BigDecimal Value
	 * @author:liupd ����ʱ�䣺2005-9-1
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
	 * ��������������Ƿ񳬹����ֵ
	 * 
	 * @param ui
	 *            ��ǰUI
	 * @param number
	 *            Ҫ���������
	 * @return true - exceed, false - not exceed
	 * @author:liupd ����ʱ�䣺2005-9-1
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
	 * ��������ʽ����Դ���������argsΪnull�򳤶�Ϊ0����ȡ��Դ�����أ�����ȡ��Դ��������args���и�ʽ���󷵻ء�
	 * 
	 * @param resPath
	 *            ��Դ·��
	 * @param resName
	 *            ��Դ������
	 * @param args
	 *            ����
	 * @return ��ʽ�������Դ
	 * @author:liupd ����ʱ�䣺2005-9-1
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
	 * ��������ʽ����Դ���������argsΪnull�򳤶�Ϊ0����ȡ��Դ�����أ�����ȡ��Դ��������args���и�ʽ���󷵻ء�
	 * 
	 * @param resPath
	 *            ��Դ·��
	 * @param resName
	 *            ��Դ������
	 * @param args
	 *            ����
	 * @return ��ʽ�������Դ
	 * @author:liupd ����ʱ�䣺2005-9-1
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
	 * ���������ToolBar�϶���ķָ���
	 * 
	 * @param toolBar
	 * @author:liupd ����ʱ�䣺2005-9-5
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
	 * ������
	 * 
	 * @param menu
	 * @author:wangweidong ����ʱ�䣺2006-9-8
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
	 * ����������Table��ĳЩ���Ի�����
	 * 
	 * @param tHelper
	 * @author:liupd ����ʱ�䣺2005-9-8
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
	 * ���������ŵ�������
	 * 
	 * @param owner
	 * @param billId
	 * @param company
	 * @throws Exception
	 * @author jxd ����ʱ�䣺2005-11-8
	 */
	public static void viewDetailBill(CoreUIObject owner, String billId,
			CompanyOrgUnitInfo company) throws Exception {
		String[] idList = new String[1];
		idList[0] = billId;
		viewDetailBill(owner, idList, company);
	}

	/**
	 * �����ռ��ʵ�һ��ʵ���Ӧ�������棬���⴦��
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

		// ���ѡ����ֻ����һ����Ϣ������ֱ����ʾ��Ϣ���鿴����
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
	 * ���������鵥��
	 * 
	 * @param ledgerUI
	 * @param idList
	 * @throws UIException
	 * @author:liupd ����ʱ�䣺2005-11-7
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
		// add by yjz ����Ȩ����
		uiContext.put(OrgType.Company, company);
		IUIWindow uiWindow = null;
		String infoID = null;

		// ���ѡ����ֻ����һ����Ϣ������ֱ����ʾ��Ϣ���鿴����
		if (idList.length == 1) {
			infoID = idList[0];
			uiContext.put(UIContext.ID, infoID);
			popUi = entity.getExtendedProperty("editUI");
			uiWindow = UIFactory.createUIFactory(getEditUIMode(owner)).create(
					popUi, uiContext, null, "FINDVIEW");
		}
		// ���ѡ���й��������Ϣ����������ʾ��Ϣ��list����
		else {
			// ��ѡ��������������Ϣ��ID��������ui������
			uiContext.put(UIContext.IDLIST, idList);
			popUi = entity.getExtendedProperty("listUI");
			// �˲�����Ϊ�˿�����ʾ�ĵ��ŵ����Ƿ���Ա༭
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
	 * �������жϵ����Ƿ����
	 * 
	 * @param billId
	 * @param bosType
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2005-11-7
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
	 * �ж��Ƿ��и�����
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
	 * ���ı�������ȫѡ�¼�����
	 * 
	 * @param source
	 * @param selectAll
	 *            �Ƿ�Ҫ��ѡ��ȫ����trueѡ��
	 * @param removeZero
	 *            �Ƿ��ڱ༭����ʾʱȥ0��trueȥ0
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
	 * �����������������пؼ��Ƿ����
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
	 * ���õ�һ������ؼ�
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
	 * �������õ�%r{0.0000000000}f��ʽ�ַ���
	 * 
	 * @param prec
	 *            ����
	 * @return
	 * @author:yangzk ����ʱ�䣺2006-3-4
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
	 * ���Ϊ��ǰΪʵ�幫˾�������У�������ʾ
	 * 
	 * @param kdt
	 * @param colName
	 *            Ҫ���ص���(����˾����)
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
	 * �趨�ؼ����Ƿ�ɱ༭
	 * 
	 * @param component
	 *            Ҫ�趨�Ŀؼ�
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
	 * �������ĳһ��
	 * 
	 * @author yangzk
	 * @param kdt
	 * @param rowIndex *
	 * @param colNames
	 *            Ҫ���������������Ϊnil������������)
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
	 * ��֤������ ������
	 * 
	 * @param ui
	 * @param txt
	 * @author:yangzk ����ʱ�䣺2006-3-8
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
	 * ��֤�Ǹ� ������
	 * 
	 * @param ui
	 * @param txt
	 * @author:yangzk ����ʱ�䣺2006-3-8
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
	 * �������幫˾��׼ִ�в��� ������ checkOnlyUnion
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:yangzk ����ʱ�䣺2006-3-6
	 *                <p>
	 */
	public static void checkOnlyUnion(String oprtState) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo company = ContextHelperFactory.getRemoteInstance()
				.getCurrentCompany();
		// if(ContextHelperFactory.getRemoteInstance().isOnlyUnion(company)&&!oprtState.equalsIgnoreCase("FINDVIEW"))
		// ��������Ҳ���Բ鿴 chenjin 2006-08-22
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
	 * ����ƽ�ȥʱ��ʵ��λ����ʾ���Ƴ����ұ𾫶���ʾ ������
	 * 
	 * @param txt
	 * @author:yangzk ����ʱ�䣺2006-3-7
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
	 * ��������ʵ��λ����ʾ
	 * 
	 * @param txt
	 * @author:yangzk ����ʱ�䣺2006-3-7
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
				//�����������
				String p = System.getProperty("DevFDC");
				if(p==null||!p.equals("fdc")){
					//������֤ by sxhong
					KDPasswordField pwd = new KDPasswordField();

					Object[] message = {"��������:", pwd};

					int res = JOptionPane.showConfirmDialog(uiObject, message, "����������", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
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
	 * �趨f7�Ĺ�������Ϊѡ�񲻳��κ�����
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
	 * ����������Դ�ұ�Ŀ��ұ���һ��ʾ��ȣ�����Ҳ������ʻ���ʾ��ȣ�����Ĭ�ϻ��ʾ���3
	 * 
	 * @param sourceCurID
	 *            Դ�ұ�ID
	 * @param desCurID
	 *            Ŀ��ұ�ID
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-4-21
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
	 * ����������Դ�ұ�Ŀ��ұ���һ��ʾ���
	 * 
	 * @param sourceCurID
	 *            Դ�ұ�ID
	 * @param desCurID
	 *            Ŀ��ұ�ID
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-4-21
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
					.showError("��˾" + clearingHouseCompany.getName()
							+ "û�л������ʱ�!");
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
	 * ��ȡ��Ч�ڼ䣬 ʵ��Ϊ���������ڼ�֮��
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
		if (curCompany.isIsOnlyUnion()) {// ������ڼ䷶Χ�������ڼ��ʼ��
			pc2 = pc;
		} else {
			// �����ڼ��Ժ�
			PeriodInfo startPeriod = null;
			try {
				startPeriod = SystemStatusCtrolUtils.getStartPeriod(null,
						SystemEnum.CASHMANAGEMENT, curCompany);
			} catch (Exception e) {
				SysUtil.abort(e);
			}
			if (startPeriod == null) {
				MsgBox.showError("����ϵͳ��ǰ�ڼ�Ϊ��!");
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
		// ������ݽ������ڼ伯�Ϸֿ�
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
	 * ����Ŀ�굥(ֻ������ֻ��һ��Ŀ�굥�����)
	 * 
	 * @param owner
	 * @param srcBosType
	 *            Դ��bostype
	 * @param destBosType
	 *            Ŀ�굥bostype
	 * @param billId
	 *            ����id
	 * @throws Exception
	 * 
	 * @author yangzk
	 */
	//2007.8.9 ����5.3.3botp�ĸ��ģ��޸��Ƿ���ʾĿ�굥�ݵ��ж���ʽ
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
			// ���Ϊģ̬�Ի����򵯳�ģ̬�Ի���
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
				// �ݴ�����editUI����
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
			// ȫ���ύ
			for (int i = 0, count = destBillCols.size(); i < count; i++) {
				CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols
						.getObject(i);

				iBTPManager.submitRelations(destBillInfo, botRelationCols);
			}
		}

	}

	/**
	 * ����ͨ����
	 */
	public static TreeModel createDataTree(ITreeBase iTree, FilterInfo filter)
			throws Exception {
		if(iTree instanceof ICostAccount){
			return createDataTree(iTree,filter,FDCClientUtils.getRes("costAccount")); 
		}
		return createDataTree(iTree,filter,null);
	}

	/**
	 * ����ͨ����,������ڵ�����
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
	 * �����ڸ�ʽ��Ϊ��yyyy-mm-dd��
	 * 
	 * @param date
	 * @return
	 */
	public static String formateDate(Date date) {
		DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		return dateFmt.format(date);
	}

	/**
	 * �����ڸ�ʽ��Ϊ��yyyy-mm-dd��
	 * 
	 * @param date
	 * @return
	 */
	public static String formateDate(Timestamp ts) {
		Date date = new Date(ts.getTime());
		return formateDate(date);
	}

	/**
	 * ����ԭ�ұұ��ȡ�һ���ǰ������֯��λ�ҵĻ���
	 * 
	 * @param ui ��ǰUIʵ��
	 * @param srcid ԭ�ұұ�
	 * @param bookedDate ҵ������
	 * @return ����
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
	 * �Ƿ�������ʾ
	 * ��֯ID��������ʱ��ȡ�˵��ݳɱ����Ļ�ǰ��֯ID���˴���Ӧ����ȡ��ǰ��֯ID
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
	 * ��ȡ��ǰ��֯ID
	 * 
	 * @return
	 */
	public static String getCurrentOrgId() {
		return SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
	}

	/**
	 * Ϊ�ٷ����ؼ�����ȡֵ��Χ(0 - 100)
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
	 * ����UI��ҳǩ����˵�����ͬ
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
	
	//��ȡ��ǰ��˾���ɲ�ѯ��Χ������
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
    
    // ���ǵĻ����ݲ���hasFull,���á�����
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
        
        /** �����и���,��ȥ�� "- "�� * */
        if (isNagativeB) {
            valTemp = valTemp.replace('-', ' ').trim();
        }

        int dotIndex = valTemp.indexOf(".");
        
        /** �������� * */
        String integerStr = "";
        String integerTempStr = null;
        
        /** С������ * */
        String decimalStr = "";
        String decimalTempStr = null;
        
        /** 1: **/
        if (dotIndex == -1) {
            integerTempStr = valTemp;
        } else {
            integerTempStr = valTemp.substring(0, dotIndex);
            decimalTempStr = valTemp.substring(dotIndex + 1);
        }

        /** 2: �������ֵĴ��� * */

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
        
        /** 3: С�����ֵĴ��� * */
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

            String unDescriptable = ""; // �޷��ñұ�λ����С��λ
            int index = -1; // ���һ������С��λ
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
            integerStr = getChineseName("zero") + getChineseName("money"); // "��Ԫ";
        }

        // ���ֵĻ���Ӧ���С�����
        // ���ǵĻ����ݲ������á�����
        String value = nagative + nagVirText + integerStr + decimalStr;
        
//        if( decimalStr.length() == 1 && ! ( hasJiao)){
//            value = value +getChineseName("zero") ;
//        }
        
        value = nagative + nagVirText + integerStr + decimalStr;
        
        if ((decimalStr == null || decimalStr.length() == 0 || decimalStr.length() == 1) || ( hasJiao)) {
            value = value + getChineseName("full");
        }
        /** 4: ���ϸ�����ʶ,������С��.* */
        return value;
    }    
	
	//��ȡ���ѡ����ĳ���ֶε�ֵ
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
     * ����action�Ŀɼ���
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
	 * fxh���˷���ͨ���������BasicPopupMenuUI$MenuKeyboardHelper��menuInputMap
	 * ��ʹ�ù��˵������inputmap��������Ӧ�����rootpane���Ҳ����ͷ�
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
     * ��ȡѡ��ڵ�������¼���ϸ������Ŀ
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
    		//ֱ�ӱ�����ȥ���ڵ���Ա�֤һ����
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
    		//���ѡ��Ľڵ���û���κ���ϸ�Ĺ�����Ŀ�ڵ������Ӹ��ڵ㱣֤���˲������κ�����
    		idSet.add(OrgConstants.DEF_CU_ID);
    	}
    	return idSet;
    }

	/**
	 * ��������˺ͷ��������Ƿ�ͬһ�����������˲���FDC011_AUDITORMUSTBETHESAMEUSER�����������ʾ��
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
