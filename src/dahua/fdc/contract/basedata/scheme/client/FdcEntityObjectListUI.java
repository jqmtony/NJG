/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.analysis.util.editor.StyledEditor;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecuteOption;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataTypeList;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.view.IBriefViewTreeNode;
import com.kingdee.bos.metadata.view.MetaDataBriefInfo;
import com.kingdee.bos.metadata.view.filters.BotpBillFilter;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.CryptoUtil;
import com.kingdee.eas.base.botp.client.BOTExportOptionUI;
import com.kingdee.eas.base.permission.Administrator;
import com.kingdee.eas.base.permission.UserException;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SubSystemUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FdcZeroBaseRender;
import com.kingdee.eas.fdc.basedata.scheme.FdcColumnInfo;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectFactory;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectUtil;
import com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection;
import com.kingdee.eas.fdc.basedata.scheme.FdcPropertyFactory;
import com.kingdee.eas.fdc.basedata.scheme.FdcPropertyInfo;
import com.kingdee.eas.fdc.basedata.util.FdcClassUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcFieldUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcParamUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fi.gl.client.ClosePeriodUI;
import com.kingdee.eas.fm.common.client.FMIsqlUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class FdcEntityObjectListUI extends AbstractFdcEntityObjectListUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2525899832083406524L;

	private static final Logger logger = CoreUIObject.getLogger(FdcEntityObjectListUI.class);

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	public final String ACTIONKEY_SYNCHRONIZE_EAS_MD = "SYNCHRONIZE_EAS_MD";

	// ��һ�δ���ʱ��
	private boolean isFirstQuery = true;

	// ��ǰ�����ز����Լ���
	private FdcPropertyCollection curCols = null;

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��ϵͳ_���ز�
	 */
	public static String SUBSYSTEM_FDC = "com.kingdee.eas.fdc";

	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��ϵͳ_���ز�_��Ŀ��Ӫ
	 */
	public static String SUBSYSTEM_FDC_REPM = "com.kingdee.eas.fdcrepm";

	/**
	 * ��ϵͳ_���ز�_Ӫ������
	 */
	public static String SUBSYSTEM_FDC_REMK = "com.kingdee.eas.fdcremk";

	/**
	 * ��ϵͳ_���ز�_CRM_����
	 */
	public static String SUBSYSTEM_FDC_CRM_BASEDATA = "com.kingdee.eas.fdc.crm.basedata.crm";

	/**
	 * ��ϵͳ_���ز�_��Ŀ��Ӫ_�б�
	 */
	public static List SUBSYSTEM_FDC_REPM_LIST = new ArrayList();

	/**
	 * ��ϵͳ_���ز�_Ӫ������_�б�
	 */
	public static List SUBSYSTEM_FDC_REMK_LIST = new ArrayList();

	/**
	 * ��ϵͳ_���ز�_CRM_����_�б�
	 */
	public static List SUBSYSTEM_FDC_CRM_BASEDATA_LIST = new ArrayList();

	/**
	 * ��ϵͳ_ӳ��
	 */
	public static Map SUBSYSTEM_MAP = new LinkedHashMap();

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	static {
		// ��ϵͳ_���ز�_��Ŀ��Ӫ_�б�
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.aimcost");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.basedata");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.contract");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.costdb");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.finance");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.invite");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.material");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.measure");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.migrate");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.pm");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.repm");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.schedule");
		SUBSYSTEM_FDC_REPM_LIST.add("com.kingdee.eas.fdc.supply");

		// ��ϵͳ_���ز�_Ӫ������_�б�
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.basecrm");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.crm_basedata");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.csm");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.customerservice");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.insider");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.market");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.merch");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.propertymgmt");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.remk");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.sales");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.sellhouse");
		SUBSYSTEM_FDC_REMK_LIST.add("com.kingdee.eas.fdc.tenancy");

		// ��ϵͳ_���ز�_CRM_����_�б�
		SUBSYSTEM_FDC_CRM_BASEDATA_LIST.add("com.kingdee.eas.fdc.crm.basedata");

		// ��ϵͳ_ӳ��
		SUBSYSTEM_MAP.put(SUBSYSTEM_FDC_REPM, SUBSYSTEM_FDC_REPM_LIST);
		SUBSYSTEM_MAP.put(SUBSYSTEM_FDC_REMK, SUBSYSTEM_FDC_REMK_LIST);
		SUBSYSTEM_MAP.put(SUBSYSTEM_FDC_CRM_BASEDATA, SUBSYSTEM_FDC_CRM_BASEDATA_LIST);
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * output class constructor
	 */
	public FdcEntityObjectListUI() throws Exception {
		super();
	}

	/**
	 * �������ݵ�����
	 */
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * ���Ӽ�����
	 */
	protected void attachListeners() {
		ActionMap entryActionMap = this.getActionMap();

		// ACTIONKEY_SYNCHRONIZE_EAS_MD
		entryActionMap.put(ACTIONKEY_SYNCHRONIZE_EAS_MD, this.synchronizeEasMDListener);
	}

	/**
	 * ��ж������
	 */
	protected void detachListeners() {
		ActionMap entryActionMap = this.getActionMap();
		entryActionMap.put(ACTIONKEY_SYNCHRONIZE_EAS_MD, null);
	}

	/**
	 *output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * @return
	 */
	protected String getEditUIName() {
		// return FdcEntityObjectEditUI.class.getName();

		return null;
	}

	protected EditShortcutListener synchronizeEasMDListener = new EditShortcutListener(ACTIONKEY_SYNCHRONIZE_EAS_MD); //

	// updateLongname
	class EditShortcutListener extends AbstractAction {
		String shortcut;

		EditShortcutListener(String sc) {
			shortcut = sc;
		}

		public void actionPerformed(ActionEvent evt) {
			if (ACTIONKEY_SYNCHRONIZE_EAS_MD.equals(shortcut)) {
				try {
					actionSynchronizeEasMD_actionPerformed(null);
				} catch (Exception e) {
					e.printStackTrace();
					FdcEntityObjectListUI.this.handleException(e);
				}
			}
		}
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		// super.tblMain_tableClicked(e);

		// ˫��
		if (e.getClickCount() == 2) {
			// ��ѯ���ݱ�
			queryDataTable();
		}
	}

	/**
	 * ��ѯ���ݱ�
	 * 
	 * @throws Exception
	 */
	protected void queryDataTable() throws Exception {
		// ȡ�����ݿ����
		String dataTableName = (String) FDCTableHelper.getSelectCellValue(this, getMainTable(), "dataTable.name");
		String sql = null;
		try {
			// ���ɲ�ѯSQL
			sql = FdcEntityObjectUtil.generateQuerySql(null, dataTableName, curCols, null);
		} catch (EASBizException e) {
			if ("10-EC_00001".equals(e.getCode())) {
				// �������ݿ����Ƿ���ڱ�ʶ
				FdcEntityObjectUtil.updateIsExist(null, dataTableName);

				// ���ñ���У����ݿ����Ƿ���ڱ�ʶ
				KDTable table = getMainTable();
				IRow row = KDTableUtil.getSelectedRow(table);
				FDCTableHelper.setCellValue(row, "dataTable.isExist", Boolean.FALSE);

				table = getDetailTable();
				FDCTableHelper.setColumnValue(table, "column.isExist", Boolean.FALSE);
			}

			// �����׳��쳣
			throw e;
		}

		if (FdcStringUtil.isNotEmpty(sql)) {
			sql += ";";

			// ����UI������ʾ
			Map uiContext = this.getUIContext();
			String uiClassname = FdcIsqlUI.class.getName();
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(uiClassname, uiContext, null,
					OprtState.VIEW);
			FMIsqlUI uiObject = (FMIsqlUI) uiWindow.getUIObject();
			StyledEditor txtScript = (StyledEditor) FdcFieldUtil.getValue(uiObject, "txtScript", true);
			txtScript.setText(sql);
			uiWindow.show();

			// ִ��SQL
			uiObject.actionExecute_actionPerformed(null);
		}
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);

		// ˢ�·�¼
		refreshDetail();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		// super.treeMain_valueChanged(e);

		// ����ʵ����ͼ
		dealWithEntityViewInfo(mainQuery, false);

		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (treeNode == null) {
			return;
		}

		// if (treeNode.isRoot()) {
		// // ���ڵ����
		// FDCClientHelper.setActionEnable(actionSynchronizeMD, false);
		// } else {
		// FDCClientHelper.setActionEnable(actionSynchronizeMD, true);
		// }
		
		// TreeNode[] nodePath = treeNode.getPath();
		// if (FdcArrayUtil.isNotEmpty(nodePath) && 2 == nodePath.length) {
		// FDCClientHelper.setActionEnable(actionSynchronizeMD, true);
		// } else {
		// // ��һ��Ҷ�ӽڵ㣬����
		// FDCClientHelper.setActionEnable(actionSynchronizeMD, false);
		// }

		MetaDataBriefInfo briefInfo = (MetaDataBriefInfo) treeNode.getUserObject();
		if (treeNode.isRoot() || briefInfo.getMetaDataType().equals(MetaDataTypeList.ENTITY)) {
			// ���ڵ��Ҷ�ӽڵ㣬���á�ͬ��Ԫ���ݡ�����
			FDCClientHelper.setActionEnable(actionSynchronizeMD, false);
		} else if (briefInfo.isPackage()) {
			FDCClientHelper.setActionEnable(actionSynchronizeMD, true);
		}

		if (treeNode.isRoot() && isFirstQuery) {
			FilterInfo filterInfo = new FilterInfo();
			mainQuery.setFilter(filterInfo);

			super.execQuery();
			refresh(null);
			KDTableUtil.setSelectedRow(tblMain, 0);

			isFirstQuery = false;
			return;
		}

		super.execQuery();
		refresh(null);
		KDTableUtil.setSelectedRow(tblMain, 0);
	}

	/**
	 * @return
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	private DefineSysEnum getDefineSys() {
		if ("DAP".equals(getUIContext().get(UIContext.UICLASSPARAM))) {
			return DefineSysEnum.DAP;
		} else if ("BTP".equals(getUIContext().get(UIContext.UICLASSPARAM))) {
			return DefineSysEnum.BTP;
		} else {
			return null;
		}
	}

	public String getUIHandlerClassName() {
		// return "com.kingdee.eas.fdc.basedata.scheme.app.FdcEntityObjectListUIHandler";
		return null;
	}

	/**
	 * ��������ʼ��������
	 * 
	 * @param treeNode
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-21
	 */
	private FilterInfo initFilterItem(DefaultKingdeeTreeNode treeNode) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		MetaDataBriefInfo briefInfo = (MetaDataBriefInfo) treeNode.getUserObject();

		List list = new ArrayList();
		if (briefInfo.getMetaDataType().equals(MetaDataTypeList.ENTITY)) {
			String fullName = briefInfo.getFullName();

			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("fullName", fullName, CompareType.EQUALS));
			list.add(briefInfo);
		} else if (briefInfo.isPackage()) {
			String packgeName = briefInfo.getPackgeName();
			List subSystemList = (List) SUBSYSTEM_MAP.get(packgeName);

			if (FdcCollectionUtil.isNotEmpty(subSystemList)) {
				// ��ʼ��������_��ϵͳ����
				initFilterItem_subSystemMap(filter, subSystemList);
			} else {
				FilterItemCollection filterItems = filter.getFilterItems();
				filterItems.add(new FilterItemInfo("fullName", packgeName + ".%", CompareType.LIKE));
			}
		}

		return filter;
	}

	/**
	 * ��������ʼ��������_��ϵͳ����
	 * 
	 * @param filter
	 * @param subSystemList
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-21
	 */
	private void initFilterItem_subSystemMap(FilterInfo filter, List subSystemList) {
		int size = subSystemList.size();
		String subSystemName = null;

		FilterItemCollection filterItems = filter.getFilterItems();
		String maskString = "(";
		for (int i = 0; i < size; i++) {
			subSystemName = (String) subSystemList.get(i);
			filterItems.add(new FilterItemInfo("fullName", subSystemName + ".%", CompareType.LIKE));

			if (i != 0) {
				maskString += " or ";
			}
			maskString += "#" + i;
		}
		maskString += ")";

		filter.setMaskString(maskString);
	}

	/**
	 * ������ȱ���ָ�����������㣬�ҳ����е�Ҷ�ӽ��
	 * 
	 * @param treeNode
	 * @return
	 */
	protected static void initEntityBriefList(List list, DefaultKingdeeTreeNode treeNode) {
		if (treeNode.getParent() == null) {
			return;
		}
		int count = treeNode.getChildCount();

		for (int i = 0; i < count; i++) {
			DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) treeNode.getChildAt(i);
			MetaDataBriefInfo childBriefInfo = (MetaDataBriefInfo) childNode.getUserObject();

			if (childBriefInfo.getMetaDataType().equals(MetaDataTypeList.ENTITY)) {
				list.add(childBriefInfo);
			}

			if (childBriefInfo.isPackage()) {
				initEntityBriefList(list, childNode);
			}
		}
	}

	public boolean isPrepareInit() {
		return true;
	}

	public boolean isPrepareActionAddNew() {
		return true;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * RPC��ʼ��
	 */
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		RequestContext request = (RequestContext) clientHanlder.getRequestContext();
		request.put("FdcEntityObjectListUI_init_mdbViewName", BotpBillFilter.VIEWNAME);
		//
		return clientHanlder;
	}

	/**
	 * @throws Exception
	 */
	public void onLoad() throws Exception {
		// ֻ��ȡ���Ų���
		fetchInitParam();

		// ��ȡ��ʼ����������
		fetchInitData();

		super.onLoad();

		// ��ͷ����ؼ��������
		initHeadStyle();

		// ��������
		initTableStyle();

		detachListeners();
		// ע���ݼ�
		registeShortKey();
		attachListeners();

		if (getDefineSys() != null) {
			// ���ñ���
			this.setUITitle(getDefineSys().getAlias());
		}

		if (getDefineSys() != null) {
			// ����ض�Ϊĳ��ϵͳ��������ʾ����ϵͳ��
			IColumn col = this.tblMain.getColumn("defineSys");
			col.getStyleAttributes().setHided(true);
		}

		if (DefineSysEnum.BTP.equals(getDefineSys())) {
			// ����ض�Ϊ����ת��ƽ̨������ʾ��˾��
			IColumn col = this.tblMain.getColumn("org.name");
			col.getStyleAttributes().setHided(true);
		}

		// initTree();

		// ˢ�±���
		refreshUITitle();

		// ���ÿؼ���ť״̬
		setButtonStatus();

	}

	/**
	 * @throws Exception
	 */
	public void onShow() throws Exception {
		super.onShow();

		// Ĭ��ѡ���һ��
		treeMain.setSelectionRow(0);

	}

	// �����������
	protected Map fetchInitParam() throws Exception {
		// ����ֻ��ȡ���Ų���
		Map param = FdcParamUtil.getDefaultFdcParam(null);

		return param;

	}

	protected void fetchInitData() throws Exception {

	}

	/**
	 * ���ø���ť��������ͼ��״̬
	 */
	protected void initTree() {
		// RPC��ȡ��ϵͳ����
		IBriefViewTreeNode vtn = SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_subsystemEntity",
				MetaDataTypeList.ENTITY);

		// ��RPC
		IBriefViewTreeNode rvtn = SubSystemUtils.getRemoveEmptyPackageNode(vtn);
		// ��RPC
		DefaultKingdeeTreeNode root = SubSystemUtils.getKDTreeNode(rvtn);

		((KingdeeTreeModel) treeMain.getModel()).setRoot(root);
	}

	/**
	 * ���ø���ť��������ͼ��״̬
	 */
	protected void initWorkButton() {
		super.initWorkButton();

		FDCClientHelper.setActionEnV(actionAddNew, false);
		FDCClientHelper.setActionEnV(actionEdit, false);
		FDCClientHelper.setActionEnV(actionRemove, false);
		// FDCClientHelper.setActionEnV(actionView, false);
		FDCClientHelper.setActionEnV(actionCancelCancel, false);
		FDCClientHelper.setActionEnV(actionCancel, false);
		FDCClientHelper.setActionEnV(actionPrintPreview, false);
		FDCClientHelper.setActionEnV(actionPrint, false);
		FDCClientHelper.setActionEnV(actionSegment, false);

		// ��ʱ������
		FDCClientHelper.setActionEnV(actionRefresh, false);
		FDCClientHelper.setActionEnV(actionQuery, true);
		FDCClientHelper.setActionEnV(actionLocate, true);

		FDCClientHelper.setActionEnV(actionSynchronizeMD, true);
		// ��ʱ������
		FDCClientHelper.setActionEnV(actionSynchronizeEasMD, false);
		FDCClientHelper.setActionEnV(actionSynchronizeBaseMD, true);
		FDCClientHelper.setActionEnV(actionSynchronizeFdcMD, true);
	}

	// �����������
	protected void initHeadStyle() throws Exception {

	}

	protected void initTableStyle() throws Exception {
		tblMain.setColumnMoveable(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		// ���������Է��㸴��
		tblMain.getStyleAttributes().setLocked(false);

		kdtEntries.setColumnMoveable(true);
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		// ���������Է��㸴��
		kdtEntries.getStyleAttributes().setLocked(false);

		FDCTableHelper.setRender(kdtEntries, "column.length", FdcZeroBaseRender.getInstance());
		FDCTableHelper.setRender(kdtEntries, "column.scale", FdcZeroBaseRender.getInstance());
		FDCTableHelper.setRender(kdtEntries, "column.precision", FdcZeroBaseRender.getInstance());

		// ��ģʽ��ҳ
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		tblMain.getDataRequestManager().setPageRowCount(100);

		// ��ϸ������ݲ��࣬������ģʽ��ҳ
		// kdtEntries.getDataRequestManager().setDataRequestMode(
		// KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		// kdtEntries.getDataRequestManager().setPageRowCount(100);

		// ͬ���ֵ�ʱ�����±�־λ"�Ƿ����"�����������⣬��ʱ�����ر����"�Ƿ����"��
		// tblMain.getColumn("dataTable.isExist").getStyleAttributes().setHided(true);
		// kdtEntries.getColumn("column.isExist").getStyleAttributes().setHided(true);
	}

	/**
	 * ע���ݼ�
	 * 
	 * @throws Exception
	 */
	protected void registeShortKey() throws Exception {
		InputMap imEntry = this.getInputMap(ClosePeriodUI.WHEN_IN_FOCUSED_WINDOW);
		// ACTIONKEY_SYNCHRONIZE_EAS_MD
		KeyStroke ctrl_shift_f12 = KeyStroke
				.getKeyStroke(KeyEvent.VK_F12, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK);
		imEntry.put(ctrl_shift_f12, ACTIONKEY_SYNCHRONIZE_EAS_MD);
	}

	public KDTable getMainTable() {
		return super.getMainTable();
	}

	public KDTable getDetailTable() {
		return this.kdtEntries;
	}

	protected void refreshUITitle() {
	}

	protected void setButtonStatus() {
	}

	/**
	 * @param uiContext
	 * @param e
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		if (e.getSource().equals(btnAddNew)) {
			// �����������ѡ����һ�����ݣ�������ΪԴ���ݴ���
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

			// ����treeNodeΪ�յ��жϴ���
			if (treeNode == null)
				return;

			MetaDataBriefInfo briefInfo = (MetaDataBriefInfo) treeNode.getUserObject();

			EntityObjectInfo srcEntity = SubSystemUtils.getEntityObjectInfo(briefInfo);

			if (srcEntity != null) {
				uiContext.put("srcEntity", srcEntity);
			}

			if (getDefineSys() != null) {
				uiContext.put("defineSys", getDefineSys());
			}
		}
	}

	public boolean isPrepareActionDisable() {
		return false;
	}

	public boolean isPrepareActionEnable() {
		return false;
	}

	public void initUIContentLayout() {
		super.initUIContentLayout();
	}

	public boolean isPrepareActionRemove() {
		return false;
	}

	// ��λ
	protected String[] getLocateNames() {
		// String[] locateNames = new String[7];
		//
		// locateNames[0] = "fullName";
		// locateNames[1] = IFWEntityStruct.dataBase_Name;
		// locateNames[2] = "alias";
		// locateNames[4] = "fullBosTypeStr";
		// locateNames[3] = "bosTypeStr";
		// locateNames[5] = "dataTable.name";
		// locateNames[6] = "dataTable.isExist";

		String[] locateNames = new String[] { "fullName", "name", "alias", "fullBosTypeStr", "bosTypeStr",
				"dataTable.name", "dataTable.isExist" };

		return locateNames;
	}

	//
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
		RequestContext request = super.prepareActionEdit(itemAction);
		// Queryˢ����Ҫ�Ĳ���
		RequestContext requestQuery = this.prepareQueryContext(null);
		request.putAll(requestQuery);
		// ��Ҫ���ݵĲ���׼��
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		// IN
		String mappingId = row.getCell(getKeyFieldName()).getValue().toString();
		String currentCU = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		request.put("FdcEntityObjectListUI_ActionEdit_mappingId", mappingId);
		request.put("FdcEntityObjectListUI_ActionEdit_currentCU", currentCU);

		return request;
	}

	public boolean isPrepareActionEdit() {
		return true;
	}

	public boolean isPrepareActionView() {
		return true;
	}

	public RequestContext prepareActionView(IItemAction itemAction) throws Exception {
		checkSelected();

		RequestContext request = super.prepareActionView(itemAction);

		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()));
		request.put("FdcEntityObjectListUI_ActionView_mappingId", pk);

		return request;
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		// super.actionView_actionPerformed(e);

		// ��ѯ���ݱ�
		queryDataTable();
	}

	protected boolean isOrderForClickTableHead() {
		return false;
	}

	public boolean isPrepareActionImportRule() {
		return false;
	}

	public boolean isPrepareActionExportRule() {
		return false;
	}

	/**
	 * output actionExport_actionPerformed method
	 */
	public void actionExportRule_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		Map uiContext = new UIContext(this);
		uiContext.put("kdtable", this.tblMain);
		uiContext.put("idList", this.getSelectedIdValues());
		//
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BOTExportOptionUI.class.getName(),
				uiContext);
		uiWindow.show();
	}

	/**
	 * @return
	 */
	protected String getQueryFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	protected IObjectPK getSelectedTreeKeyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.framework.client.TreeListUI#getTreeInterface()
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.framework.client.TreeDetailListUI#getBizInterface()
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return FdcEntityObjectFactory.getRemoteInstance();
	}

	/**
	 * ˢ�·�¼
	 * 
	 * @throws Exception
	 */
	protected void refreshDetail() throws Exception {
		// ˢ�·�¼���
		refreshDetailTable();
		// ˢ�·�¼��ť
		refreshDetailButtons();
	}

	/**
	 * ˢ�·�¼���
	 * 
	 * @throws Exception
	 */
	protected void refreshDetailTable() throws Exception {
		KDTable tbl = getMainTable();
		String billId = getSelectedKeyValue(tbl);

		if (FdcStringUtil.isNotBlank(billId)) {
			// ȡ�÷�¼����
			curCols = getEntryCollection(billId);
			// ����¼���
			fillDetailTable(curCols);
		} else {
			curCols = null;
			// ��շ�¼���
			getDetailTable().removeRows();
		}

	}

	/**
	 * ˢ�·�¼��ť
	 * 
	 * @throws Exception
	 */
	protected void refreshDetailButtons() throws Exception {
	}

	/**
	 * ȡ�÷�¼����
	 * 
	 * @param billId
	 * @return
	 * @throws Exception
	 */
	protected FdcPropertyCollection getEntryCollection(String billId) throws Exception {
		FdcPropertyCollection cols = null;

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("id");
		selector.add("name");
		selector.add("alias");
		selector.add("mappingField.id");
		selector.add("mappingField.name");
		selector.add("mappingField.sqlType");
		selector.add("mappingField.length");
		selector.add("mappingField.scale");
		selector.add("mappingField.precision");
		selector.add("mappingField.isNullable");
		selector.add("mappingField.defaultValue");
		selector.add("mappingField.isMultilingual");
		selector.add("mappingField.isExist");
		selector.add("linkProperty.id");
		selector.add("linkProperty.fullName");
		selector.add("linkProperty.name");
		selector.add("linkProperty.alias");
		selector.add("linkProperty.bosTypeStr");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("bill.id", billId);

		cols = FdcPropertyFactory.getRemoteInstance().getFdcPropertyCollection(view);

		return cols;
	}

	/**
	 * ����¼���
	 * 
	 * @param clos
	 * @throws Exception
	 */
	protected void fillDetailTable(FdcPropertyCollection clos) throws Exception {
		KDTable tbl = getDetailTable();
		// ��շ�¼���
		tbl.removeRows();

		FdcPropertyInfo entryInfo = null;
		IRow row = null;
		for (Iterator iterator = clos.iterator(); iterator.hasNext();) {
			entryInfo = (FdcPropertyInfo) iterator.next();
			row = tbl.addRow();

			// ���ӷ�¼�����
			addDetailTableLine(row, entryInfo);
		}
	}

	/**
	 * ���ӷ�¼�����
	 * 
	 * @param row
	 * @param entryInfo
	 * @throws Exception
	 */
	protected void addDetailTableLine(IRow row, FdcPropertyInfo entryInfo) throws Exception {
		row.setUserObject(entryInfo);

		row.getCell("property.id").setValue(entryInfo.getId());
		row.getCell("property.name").setValue(entryInfo.getName());
		row.getCell("property.alias").setValue(entryInfo.getAlias());

		FdcColumnInfo columnInfo = entryInfo.getMappingField();
		if (null != columnInfo) {
			row.getCell("column.id").setValue(columnInfo.getId());
			row.getCell("column.name").setValue(columnInfo.getName());

			row.getCell("column.sqlType").setValue(columnInfo.getSqlType());
			row.getCell("column.length").setValue(FDCNumberHelper.toBigDecimal(columnInfo.getLength() + ""));
			row.getCell("column.scale").setValue(FDCNumberHelper.toBigDecimal(columnInfo.getScale() + ""));
			row.getCell("column.precision").setValue(FDCNumberHelper.toBigDecimal(columnInfo.getPrecision() + ""));

			row.getCell("column.isNullable").setValue(Boolean.valueOf(columnInfo.isIsNullable()));
			row.getCell("column.defaultValue").setValue(columnInfo.getDefaultValue());
			row.getCell("column.isMultilingual").setValue(Boolean.valueOf(columnInfo.isIsMultilingual()));
			row.getCell("column.isExist").setValue(Boolean.valueOf(columnInfo.isIsExist()));
		}
		FdcEntityObjectInfo linkPropertyInfo = entryInfo.getLinkProperty();
		if (null != linkPropertyInfo) {
			row.getCell("linkProperty.id").setValue(linkPropertyInfo.getId());
			row.getCell("linkProperty.fullName").setValue(linkPropertyInfo.getFullName());
			row.getCell("linkProperty.name").setValue(linkPropertyInfo.getName());
			row.getCell("linkProperty.alias").setValue(linkPropertyInfo.getAlias());
			row.getCell("linkProperty.bosTypeStr").setValue(linkPropertyInfo.getBosTypeStr());
		}

		row.getCell("property.relationshipUrlName").setValue(entryInfo.getRelationshipUrlName());
	}

	/**
	 * ��ȡ��ǰѡ���е�����
	 * 
	 * @return ���ص�ǰѡ���е�����������ǰѡ����Ϊ�գ����ߵ�ǰѡ���е�������Ϊ�գ��򷵻�null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		String keyValueStr = null;

		IRow row = getSelectedRow(table);
		if (row == null) {
			return null;
		}

		ICell cell = row.getCell(getKeyFieldName());
		if (cell == null) {
			MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
			SysUtil.abort();
		}

		Object keyValue = cell.getValue();
		if (keyValue != null) {
			keyValueStr = keyValue.toString();
		}

		return keyValueStr;
	}

	/**
	 * ��ȡ��ǰѡ���е�����
	 * 
	 * @return ���ص�ǰѡ���е�����������ǰѡ����Ϊ�գ����ߵ�ǰѡ���е�������Ϊ�գ��򷵻�null
	 */
	protected IRow getSelectedRow(KDTable table) {
		IRow row = null;

		KDTSelectBlock selectBlock = table.getSelectManager().get();
		if (selectBlock != null) {
			int rowIndex = selectBlock.getTop();
			row = table.getRow(rowIndex);
		}

		return row;
	}

	public void actionSynchronizeMD_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		// ���ѡ��ڵ�
		checkSelectNode(treeNode);

		// ����Ƿ�Administrator��¼
		checkIsLogin();

		int result = MsgBox.showConfirm2(this, "ͬ��Ԫ���ݽ����ѽϳ���ʱ�䣬�Ƿ������");
		if (AdvMsgBox.OK_OPTION == result) {
			MetaDataBriefInfo briefInfo = (MetaDataBriefInfo) treeNode.getUserObject();
			String fullName = null;
			boolean isPackage = false;
			if (briefInfo.getMetaDataType().equals(MetaDataTypeList.ENTITY)) {
				fullName = briefInfo.getFullName();
				isPackage = false;
			} else if (briefInfo.isPackage()) {
				fullName = briefInfo.getPackgeName();
				// ѡ��"���ز�_��Ŀ��Ӫ"��"���ز�_Ӫ������"��ֱ��ͬ�����з��ز���Ԫ����
				if (SUBSYSTEM_FDC_REPM.equals(fullName) || SUBSYSTEM_FDC_REPM.equals(fullName)) {
					fullName = SUBSYSTEM_FDC;
				}

				isPackage = true;
			}

			long startTime = System.currentTimeMillis();
			FdcEntityObjectFactory.getRemoteInstance().synchronizeMD(fullName, isPackage);
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) * 1.0 / 1000;

			MsgBox.showInfo(this, "ͬ����ɣ���ʱ" + time + "��");
			this.treeMain_valueChanged(null);
		}

	}

	public void actionSynchronizeEasMD_actionPerformed(ActionEvent e) throws Exception {
		// ����Ƿ�Administrator��¼
		checkIsLogin();

		int result = MsgBox.showConfirm2(this, "�Ӹ��ڵ�ͬ��Ԫ���ݣ��ڵ����û������п��ܵ����ڴ�������Ƿ������");
		if (AdvMsgBox.OK_OPTION == result) {
			long startTime = System.currentTimeMillis();
			FdcEntityObjectFactory.getRemoteInstance().synchronizeEasMD();
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) * 1.0 / 1000;

			MsgBox.showInfo(this, "ͬ����ɣ���ʱ" + time + "��");
			this.treeMain_valueChanged(null);
		}
	}

	public void actionSynchronizeBaseMD_actionPerformed(ActionEvent e) throws Exception {
		// ����Ƿ�Administrator��¼
		checkIsLogin();

		int result = MsgBox.showConfirm2(this, "ͬ��Ԫ���ݽ����ѽϳ���ʱ�䣬�Ƿ������");
		if (AdvMsgBox.OK_OPTION == result) {
			long startTime = System.currentTimeMillis();
			FdcEntityObjectFactory.getRemoteInstance().synchronizeBaseMD();
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) * 1.0 / 1000;

			MsgBox.showInfo(this, "ͬ����ɣ���ʱ" + time + "��");
			this.treeMain_valueChanged(null);
		}
	}

	public void actionSynchronizeFdcMD_actionPerformed(ActionEvent e) throws Exception {
		// ����Ƿ�Administrator��¼
		checkIsLogin();

		int result = MsgBox.showConfirm2(this, "ͬ��Ԫ���ݽ����ѽϳ���ʱ�䣬�Ƿ������");
		if (AdvMsgBox.OK_OPTION == result) {
			long startTime = System.currentTimeMillis();
			FdcEntityObjectFactory.getRemoteInstance().synchronizeFdcMD();
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) * 1.0 / 1000;

			MsgBox.showInfo(this, "ͬ����ɣ���ʱ" + time + "��");
			this.treeMain_valueChanged(null);
		}
	}

	/**
	 * ���ѡ��ڵ�
	 * 
	 * @throws Exception
	 */
	public void checkSelectNode(DefaultKingdeeTreeNode treeNode) throws Exception {
		if (treeNode == null) {
			MsgBox.showWarning(this, "��ѡ�����νڵ�");
			SysUtil.abort();
		}

		if (treeNode.isRoot()) {
			// ���ڵ����
			FDCClientHelper.setActionEnable(actionSynchronizeMD, false);
			MsgBox.showWarning(this, "�Ӹ��ڵ�ͬ��Ԫ���ݣ��ڵ����û������п�������崻�����ֹ������");
			SysUtil.abort();
		} else {
			FDCClientHelper.setActionEnable(actionSynchronizeMD, true);
		}
	}

	/**
	 * ����Ƿ�Administrator��¼
	 * 
	 * @throws Exception
	 */
	public void checkIsLogin() throws Exception {
		boolean flag = isLogin();
		if (!flag) {
			abort();
		}
	}

	/**
	 * �Ƿ��¼
	 * 
	 * @return
	 * @throws EASBizException
	 */
	public boolean isLogin() throws EASBizException {
		// UserInfo userInfo = ContextHelperFactory.getRemoteInstance().getCurrentUser();
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		if (com.kingdee.eas.base.permission.Administrator.isSuperCUAdmin(new ObjectUuidPK(userInfo.getId()))) {
			return true;
		}

		if (Administrator.ID.equals(userInfo.getId().toString())) {
			return true;
		}

		try {
			userInfo = UserFactory.getRemoteInstance().getUserByID(new ObjectUuidPK(Administrator.ID));
		} catch (BOSException e) {
			logger.error("", e);
		}

		JPasswordField pwd = new JPasswordField();

		Object[] message = {
				com.kingdee.eas.util.client.EASResource.getString(
						"com.kingdee.eas.fm.common.COMMONAutoGenerateResource", "49_FMIsqlUI"), pwd };

		int res = JOptionPane.showConfirmDialog(this, message, " ", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res != JOptionPane.OK_OPTION) {
			return false;
		}

		// String userPassword = JOptionPane.showInputDialog(this, "���������Ա����!", "");
		String logonPassword = new String(pwd.getPassword());

		if (matchPassword(userInfo, logonPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * ƥ������
	 * 
	 * @param userInfo
	 * @param password
	 * @return
	 * @throws EASBizException
	 */
	public static boolean matchPassword(UserInfo userInfo, String password) throws EASBizException {
		if (StringUtils.isEmpty(password)) {
			return StringUtils.isEmpty(userInfo.getPassword());
		} else {
			return encrypt(userInfo.getId().toString(), password).equals(userInfo.getPassword());
		}
	}

	/**
	 * ����
	 * 
	 * @param userID
	 * @param password
	 * @return
	 * @throws EASBizException
	 */
	public static String encrypt(String userID, String password) throws EASBizException {

		try {
			return CryptoUtil.encrypt(userID + password.trim());
		} catch (Exception e) {
			throw new UserException(UserException.ENCRYPT_FAIL);
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// �����νṹ��ط���
	// /////////////////////////////////////////////////////////////////////////

	// ��ȡѡ�е�Ԫ����
	protected MetaDataBriefInfo getSelectedMetaDataBriefInfo() {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (treeNode != null && treeNode.getUserObject() instanceof MetaDataBriefInfo) {
			return (MetaDataBriefInfo) treeNode.getUserObject();
		}

		return null;
	}

	// ��ȡѡ�е�ʵ��
	protected MetaDataBriefInfo getSelectedEntityInfo() {
		return getSelectedMetaDataBriefInfo();
	}

	// ��ѯ����ʱ,����cu,table,type�Ĺ���
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, getQueryEntityViewInfo(viewInfo));
		QueryExecuteOption queryExecuteOption = queryExecutor.option();
		queryExecuteOption.isAutoIgnoreZero = true;
		queryExecuteOption.isAutoTranslateBoolean = true;
		queryExecuteOption.isAutoTranslateEnum = true;

		// ��ӡSQL�����������⣬��ʱ���ε� by skyiter_wang 2015-05-21
		String sql = null;
		try {
			if (logger.isDebugEnabled()) {
				sql = queryExecutor.getSQL();
				String classSimpleName = FdcClassUtil.getSimpleName(this.getClass());

				logger.info("================================================================");
				logger.info(classSimpleName + ".getQueryExecutor()��sql:" + sql);
				logger.info(classSimpleName + ".getQueryExecutor()��viewInfo:" + viewInfo);
				logger.info("================================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return queryExecutor;
	}

	protected EntityViewInfo getQueryEntityViewInfo(EntityViewInfo viewInfo) {
		EntityViewInfo view = (EntityViewInfo) viewInfo.clone();

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		try {
			// Ĭ�Ϲ���
			FilterInfo defaultFilter = getDefaultFilterForQuery();
			// ������
			FilterInfo treeFilter = getSubSystemTreeQuery();
			// ��ǰ����
			FilterInfo currentFilter = viewInfo.getFilter();

			filter.mergeFilter(defaultFilter, "and");
			filter.mergeFilter(treeFilter, "and");
			filter.mergeFilter(currentFilter, "and");
		} catch (Exception e) {
			e.printStackTrace();
			this.handUIException(e);
		}

		// ����ʵ����ͼ
		dealWithEntityViewInfo(view, true);

		return view;
	}

	/**
	 * ����ʵ����ͼ
	 * 
	 * @param view
	 * @param setIdIsNull
	 */
	protected void dealWithEntityViewInfo(EntityViewInfo view, boolean setIdIsNull) {
		// �������·�¼���ʱ��ֻ��ѯʵ�壬���ݲ��Ǻܶ࣬����ʱ������
		if (true) {
			return;
		}

		if (setIdIsNull) {
			FilterInfo filter = view.getFilter();
			filter = (FilterInfo) FdcObjectUtil.defaultIfNull(filter, new FilterInfo());
			view.setFilter(filter);
			FilterItemCollection filterItems = filter.getFilterItems();

			// û���κι���������ѡ����ڵ㣬��ѯ���̫�࣬Ӱ�����ܣ��ʲ���ʾ�κν��
			boolean flag = false;
			if (filterItems.isEmpty()) {
				filterItems.add(new FilterItemInfo("id", null));

				flag = true;
			} else if (filterItems.size() == 1) {
				FilterItemInfo item0 = filterItems.get(0);
				if (null == item0) {
					item0 = new FilterItemInfo("id", null);
					flag = true;
				} else if ("fullName like 'com.kingdee.eas.%'".equalsIgnoreCase(item0.toString())) {
					item0 = new FilterItemInfo("id", null);
					flag = true;
				}

				filterItems.set(0, item0);
			}
			if (flag) {
				// MsgBox.showWarning(this, "û���κι���������ѡ����ڵ㣬��ѯ���̫�࣬Ӱ�����ܣ��ʲ���ʾ�κν����");
				// SysUtil.abort();
			}
		} else {
			FilterInfo filter = view.getFilter();
			filter = (FilterInfo) FdcObjectUtil.defaultIfNull(filter, new FilterInfo());
			view.setFilter(filter);
			FilterItemCollection filterItems = filter.getFilterItems();

			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (treeNode == null) {
				return;
			}
			MetaDataBriefInfo briefInfo = (MetaDataBriefInfo) treeNode.getUserObject();
			int size = filterItems.size();
			if (briefInfo.isPackage() && size >= 1) {
				FilterItemInfo item0 = filterItems.get(0);
				if ((null != item0) && "id IS NULL".equalsIgnoreCase(item0.toString())) {
					// ѡ��easʱ��������һ�������������
					if (briefInfo.getPackgeName().equalsIgnoreCase("com.kingdee.eas") && size >= 2) {
						item0 = new FilterItemInfo("id", null, CompareType.NOTEQUALS);
					}
					// ��eas
					else {
						item0 = new FilterItemInfo("id", null, CompareType.NOTEQUALS);
					}
				}
				filterItems.set(0, item0);
			}
		}

	}

	// �S��CU����
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected FilterInfo getBizOrgFilter(EntityViewInfo viewInfo) throws Exception {
		FilterInfo filter = new FilterInfo();

		// // ��ҵ��Χ����֯
		// Set orgSet = EcUtils.getAuthorizedOrgs(OrgType.Transport);
		// if (orgSet != null) {
		// filter.getFilterItems().add(new FilterItemInfo("projectOrg.id", orgSet,
		// CompareType.INCLUDE));
		// }
		//
		// if (viewInfo.getFilter() != null) {
		// filter.mergeFilter(viewInfo.getFilter(), "and");
		// }

		return filter;
	}

	// Ĭ�Ͻ��е�ǰCU�Ĺ��ˡ���������ء�
	protected FilterInfo getDefaultFilterForQuery() {
		// super.getDefaultFilterForQuery();
		FilterInfo filter = new FilterInfo();
		return filter;
	}

	// �����ϵͳ������
	protected FilterInfo getSubSystemTreeQuery() {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		FilterInfo filterInfo = null;
		try {
			filterInfo = initFilterItem(treeNode);
		} catch (Exception e) {
			this.handUIException(e);
		}

		return filterInfo;
	}

	// ����ʱ������ѡ���������ϸ�ڵ�
	public boolean mustSelectDetail() {
		return true;
	}

	// /////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////

}