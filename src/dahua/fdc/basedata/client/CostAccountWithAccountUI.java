/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.LanguageInfo;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.UIHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountTools;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.ObjectValueRenderImpl;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostAccountWithAccountUI extends AbstractCostAccountWithAccountUI {
	private static final String COL_ID = "id";

	private static final String COL_CURPROJECT_NAME = "curProject.name";

	private static final String COL_FULLORGUNIT_NAME = "fullOrgUnit.name";

	private static final String COL_INVOICEACCOUNT_NAME = "invoiceAccount.name";

	private static final String COL_INVOICEACCOUNT = "invoiceAccount";

	private static final String COL_COSTACCOUNT = "costAccount";

	private static final String COL_ACCOUNT = "account";

	private static final String COL_COSTACCOUNT_NAME = "costAccountName";

	private static final String COL_DESCRIPTION = "description";

	private static final String COL_ACCOUNT_NAME = "accountName";

	private static final Logger logger = CoreUIObject.getLogger(CostAccountWithAccountUI.class);

	private CostAccountWithAccountCollection costAccountWithAccountCollection;
	private final static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	
	private boolean isInvoiceMgr = false;
	/**
	 * output class constructor
	 */
	public CostAccountWithAccountUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("costAccount.*"));
		sic.add(new SelectorItemInfo("costAccount.curProject.name"));
		sic.add(new SelectorItemInfo("costAccount.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("account.*"));
		return sic;
	}

	public void onLoad() throws Exception {
		tblMain.checkParsed();
		// ���ñ�¼table��Ԫ����ɫΪ��¼��
		this.tblMain.getColumn(COL_COSTACCOUNT).getStyleAttributes().setBackground(tblMain.getRequiredColor());
		this.tblMain.getColumn(COL_ACCOUNT).getStyleAttributes().setBackground(tblMain.getRequiredColor());
//		this.tblMain.getColumn(COL_INVOICEACCOUNT).getStyleAttributes().setBackground(tblMain.getRequiredColor());
		this.tblMain.getColumn(COL_FULLORGUNIT_NAME).getStyleAttributes().setLocked(true);
		this.tblMain.getColumn(COL_CURPROJECT_NAME).getStyleAttributes().setLocked(true);
		this.tblMain.getColumn(COL_COSTACCOUNT_NAME).getStyleAttributes().setLocked(true);
		this.tblMain.getColumn(COL_ACCOUNT_NAME).getStyleAttributes().setLocked(true);
		this.tblMain.getColumn(COL_INVOICEACCOUNT_NAME).getStyleAttributes().setLocked(true);
		
		/*** �ж��Ƿ����ø���ģʽһ�廯��Ʊ����  -by neo***/
		String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		boolean isInvoiceMgr = FDCUtils.getBooleanValue4FDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_INVOICEMRG);
		
		if(!isInvoiceMgr){
			this.tblMain.getColumn(COL_INVOICEACCOUNT).getStyleAttributes().setHided(true);
			this.tblMain.getColumn(COL_INVOICEACCOUNT_NAME).getStyleAttributes().setHided(true);
		}
		
		super.onLoad();
		initWorkButton();
		buildProjectTree();
		//loadData(new FilterInfo());
		refreshCostAccountWithAccountList((DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent());
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if (this.tblMain.getRowCount() > 0) {
			this.tblMain.getSelectManager().select(0, 0);
		} else {
			this.btnSave.setEnabled(false);
		}
		//���ſ������ü���ģ��
		/*if ((SysContext.getSysContext().getCurrentFIUnit() != null && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
				|| (SysContext.getSysContext().getCurrentCostUnit() != null && SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())
				||OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddLine.setEnabled(true);
			this.btnDeleteLine.setEnabled(true);
			this.btnInsertLine.setEnabled(true);
			this.btnSave.setEnabled(true);
			this.menuAddLine.setEnabled(true);
			this.menuDeleteLine.setEnabled(true);
			this.menuInsertLine.setEnabled(true);
			this.menuSave.setEnabled(true);
			actionImport.setEnabled(false);
			
			if(SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()&&SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()){
				actionImport.setEnabled(true);
			}
		} else {

			this.btnAddLine.setEnabled(false);
			this.btnDeleteLine.setEnabled(false);
			this.btnInsertLine.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuAddLine.setEnabled(false);
			this.menuDeleteLine.setEnabled(false);
			this.menuInsertLine.setEnabled(false);
			this.menuSave.setEnabled(false);
			actionImport.setEnabled(false);
		}*/
		
		currentSelectNode = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		/*tblMain.setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {
				isModify=true;
			}
		});*/
		setTblMainEditStatus();
		//����Ĭ��ѡ���һ�У���������ʼ����ֱ�ӱ��汨��
		this.treeMain.setSelectionRow(0);
	}


	
	/**
	 * ���ø���ť��������ͼ��״̬
	 */
	protected void initWorkButton() {
		this.actionSave.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_save"));
		this.actionAddLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		this.actionInsertLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_insert"));
		this.actionDeleteLine.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		actionImport.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_importcyclostyle"));
		// this.actionCancel.putValue(Action.SMALL_ICON,
		// EASResource.getIcon("imgTbtn_quit"));
	}
	private void setTblMainEditStatus(){
		this.tblMain.setEditable(this.btnSave.isEnabled());
	}
	/**
	 * �������ݡ�
	 */
	private void loadData(FilterInfo filter) throws Exception {
		Locale locale = SysContext.getSysContext().getLocale();
		this.tblMain.removeRows();
		//ͳһ����editor sxhong
		tblMain.getColumn(COL_COSTACCOUNT).setEditor(new KDTDefaultCellEditor(createCostAccountF7()));
		tblMain.getColumn(COL_ACCOUNT).setEditor(new KDTDefaultCellEditor(createAccountViewF7()));
		tblMain.getColumn(COL_INVOICEACCOUNT).setEditor(new KDTDefaultCellEditor(createAccountViewF7()));
		// ����costAccount�к�account�е���ʾ��ʽ
		// ����һ��ObjectValueRender
		ObjectValueRenderImpl costAvr = new ObjectValueRenderImpl();
		ObjectValueRenderImpl accountAvr = new ObjectValueRenderImpl();
		ObjectValueRenderImpl invoiceAvr = new ObjectValueRenderImpl();
		// ������ʾ��ʽ����ʽͬf7��
		// avr.setFormat(new BizDataFormat("$id$-$name$"));
		costAvr.setFormat(new BizDataFormat("$longNumber$"));
		// ��render�󶨵����ϣ�Ҳ�ɰ󶨵��л�Ԫ���ϣ�
		tblMain.getColumn(COL_COSTACCOUNT).setRenderer(costAvr); // �趨�ɱ���Ŀ��ʾ��ʽ
		accountAvr.setFormat(new BizDataFormat("$number$"));
		tblMain.getColumn(COL_ACCOUNT).setRenderer(accountAvr); // �趨��ƿ�Ŀ��ʾ��ʽ
		invoiceAvr.setFormat(new BizDataFormat("$number$"));
		tblMain.getColumn(COL_INVOICEACCOUNT).setRenderer(invoiceAvr);	// �趨��Ʊ��Ŀ��ʾ��ʽ
		KDTextField textField = new KDTextField();//���ó���
	    textField.setMaxLength(255);
		tblMain.getColumn(COL_DESCRIPTION).setEditor(new KDTDefaultCellEditor(textField));
		// ��ȡ��������
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSorter().add(new SorterItemInfo("costAccount.longnumber"));
		evi.getSelector().add("*");
		evi.getSelector().add("costAccount.name");
		evi.getSelector().add("costAccount.longNumber");
		evi.getSelector().add("costAccount.number");
		evi.getSelector().add("costAccount.curProject.name");
		evi.getSelector().add("costAccount.curProject.displayName");
		evi.getSelector().add("costAccount.fullOrgUnit.name");
		evi.getSelector().add("costAccount.fullOrgUnit.displayName");
		evi.getSelector().add("costAccount.curProject.fullOrgUnit.name");
		evi.getSelector().add("costAccount.curProject.fullOrgUnit.displayName");
		evi.getSelector().add("account.name");
		evi.getSelector().add("account.number");
		evi.getSelector().add("account.longNumber");
		evi.getSelector().add("invoiceAccount.name");
		evi.getSelector().add("invoiceAccount.number");
		evi.getSelector().add("invoiceAccount.longNumber");
//		������֯����
		if(filter.getFilterItems().size() == 0){
			filter.getFilterItems().add(new FilterItemInfo("costAccount.fullOrgUnit.id",SysContext.getSysContext().getCurrentFIUnit().getId().toString()));		
			filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.fullOrgUnit.id",SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("costAccount.curProject.isEnabled",Boolean.TRUE));
			
			filter.setMaskString("#0 or (#1 and #2)");
			
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentFIUnit().getId().toString())) {
//				setImportButtonStatus(true,false);
				setImportButtonStatus(true,true);
			}else{
//				setImportButtonStatus(false,true);
				setImportButtonStatus(false,false);
			}
		}
		evi.setFilter(filter);
		costAccountWithAccountCollection = iCostAccountWithAccount.getCostAccountWithAccountCollection(evi);
		if (costAccountWithAccountCollection.size() > 0) {
			IRow row;
			CostAccountWithAccountInfo costAccountWithAccountInfo;
			ICell costCell, accountCell, invoiceCell;
//			JTextField txtEnable1, txtEnable2;
//			KDTDefaultCellEditor ceEnable1, ceEnable2;
			for (int i = 0; i < costAccountWithAccountCollection.size(); i++) {
				costAccountWithAccountInfo = costAccountWithAccountCollection.get(i);
				row = this.tblMain.addRow();
				// ���óɱ���Ŀf7cell
				costCell = row.getCell(COL_COSTACCOUNT);

				costCell.getStyleAttributes().setLocked(false);
				costCell.setValue(costAccountWithAccountInfo.getCostAccount());
//				txtEnable1 = new JTextField();
//				ceEnable1 = new KDTDefaultCellEditor(txtEnable1);
//				ceEnable1 = new KDTDefaultCellEditor(createProjectF7());
//				cellValueAttribute1.setEditor(ceEnable1);
				// ���óɱ���Ŀ����cell
				row.getCell(COL_COSTACCOUNT_NAME).setValue(costAccountWithAccountInfo.getCostAccount().getName(locale));
				// ���û�ƿ�Ŀf7cell
				accountCell = row.getCell(COL_ACCOUNT);
				accountCell.getStyleAttributes().setLocked(false);
				accountCell.setValue(costAccountWithAccountInfo.getAccount());
//				txtEnable2 = new JTextField();
//				ceEnable2 = new KDTDefaultCellEditor(txtEnable2);
//				ceEnable2 = new KDTDefaultCellEditor(createCostCentertF7());
//				cellValueAttribute2.setEditor(ceEnable2);
				// ���û�ƿ�Ŀ����cell
				if (costAccountWithAccountInfo.getAccount() != null) {
					row.getCell(COL_ACCOUNT_NAME).setValue(costAccountWithAccountInfo.getAccount().getName(locale));
				}
				//���÷�Ʊ��Ŀ������
				invoiceCell = row.getCell(COL_INVOICEACCOUNT);
				invoiceCell.getStyleAttributes().setLocked(false);
				invoiceCell.setValue(costAccountWithAccountInfo.getInvoiceAccount());
				if(costAccountWithAccountInfo.getInvoiceAccount() != null)
					row.getCell(COL_INVOICEACCOUNT_NAME).setValue(costAccountWithAccountInfo.getInvoiceAccount().getName(locale));
				else 
					row.getCell(COL_INVOICEACCOUNT_NAME).setValue(null);
				
				// ����������֯��
				if (costAccountWithAccountInfo.getCostAccount().getFullOrgUnit() != null) {
					String temp = costAccountWithAccountInfo.getCostAccount().getFullOrgUnit().getDisplayName(locale);
					if(temp!=null&&temp.trim().length()>0){
						temp=temp.replaceAll("_", "/");
					}
					row.getCell(COL_FULLORGUNIT_NAME).setValue(temp);
				}else{
					String temp = costAccountWithAccountInfo.getCostAccount().getCurProject().getFullOrgUnit().getDisplayName(locale);
					if(temp!=null&&temp.trim().length()>0){
						temp=temp.replaceAll("_", "/");
					}
					row.getCell(COL_FULLORGUNIT_NAME).setValue(temp);
				}
				// �������ڹ�����Ŀ��
				if (costAccountWithAccountInfo.getCostAccount().getCurProject() != null) {
					String displayName = costAccountWithAccountInfo.getCostAccount().getCurProject().getDisplayName(locale);
					if(displayName!=null&&displayName.trim().length()>0){
						displayName=displayName.replaceAll("_", "/");
					}
					row.getCell(COL_CURPROJECT_NAME).setValue(displayName);
				}
				// ��������cell
				row.getCell(COL_DESCRIPTION).setValue(costAccountWithAccountInfo.getDescription(locale));
				//
				row.getCell(COL_ID).setValue(costAccountWithAccountInfo.getId().toString());
				row.setUserObject(costAccountWithAccountInfo);
			}
		}
		isModify=false;
	}

	private KDBizPromptBox createCostAccountF7() throws BOSException {
		KDBizPromptBox f7 = new KDBizPromptBox(){
			protected String valueToString(Object o) {
				String s=super.valueToString(o);
				if(!FDCHelper.isEmpty(s)&&o instanceof IObjectValue){
					return s.replaceAll("!", "\\.");
				}
				return s;
			}
		};
		
		f7.setEditable(true);
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7.setEditFormat("$longNumber$");
		f7.setCommitFormat("$longNumber$");
		f7.setDisplayFormat("$longNumber$");
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.displayName");
		sic.add("curProject.id");
		sic.add("fullOrgUnit.displayName");
		sic.add("fullOrgUnit.id");
		sic.add("curProject.fullOrgUnit.displayName");
		f7.setSelectorCollection(sic);
		EntityViewInfo view = f7.getEntityViewInfo();
		if(view == null){
			view = new EntityViewInfo();
		}
		if(view.getFilter()==null)	view.setFilter(new FilterInfo());
//		view.getSelector().add("curProject.displayName");
//		view.getSelector().add("fullOrgUnit.displayName");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) node.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				if(OrgConstants.DEF_CU_ID.equals(((OrgStructureInfo)projTreeNodeInfo).getUnit().getId().toString())){
					view.getFilter().appendFilterItem("fullOrgUnit.id",OrgConstants.DEF_CU_ID);
					f7.setEntityViewInfo(view);
				}
			}
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();

				view.getFilter().appendFilterItem("curProject.id",id.toString());
				f7.setEntityViewInfo(view);
			}
		}else{
			view.getFilter().appendFilterItem("fullOrgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			f7.setEntityViewInfo(view);
		}
		return f7;
	}

	private KDBizPromptBox createAccountViewF7() throws BOSException {
		KDBizPromptBox f7 = new KDBizPromptBox();
		FilterInfo filter = new FilterInfo();			
		filter.getFilterItems().add( new FilterItemInfo("isCFreeze", new Integer(0)));
//		filter.getFilterItems().add( new FilterItemInfo("isLeaf", new Integer(1)));
		
		AccountPromptBox xx = new AccountPromptBox(this,SysContext.getSysContext().getCurrentFIUnit(),filter,false,true);
		f7.setSelector(xx);
		f7.setEditable(false);
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setDisplayFormat("$number$");
//		f7.setDisplayFormat("$name$");
//		// f7.setQueryInfo("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
//		f7.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
//		f7.setEditable(true);
		f7.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
	    SelectorItemCollection sic = new SelectorItemCollection();
	    f7.setSelectorCollection(sic);

	    FilterInfo f=new FilterInfo();
	    EntityViewInfo view=new EntityViewInfo();
	    f.getFilterItems().add( new FilterItemInfo("isCFreeze", new Integer(0)));
//		f.getFilterItems().add( new FilterItemInfo("isLeaf", new Integer(1)));
//		filter.getFilterItems().add( new FilterItemInfo("isARAPAccount", new Integer(1)));
//		f.getFilterItems().add(new FilterItemInfo("destCu", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		AccountTableInfo tableInfo=null;
		CompanyOrgUnitInfo fiOrgInfo = SysContext.getSysContext().getCurrentFIUnit();
    	if(fiOrgInfo != null && fiOrgInfo.getAccountTable() != null){
    		tableInfo = fiOrgInfo.getAccountTable();
    	}
    	else{
			tableInfo = AccountTools.getDefaultAccountTableByCU(SysContext.getSysContext().getCurrentCtrlUnit());
    	}
    	if(tableInfo!=null&&tableInfo.getId()!=null){
    		f.appendFilterItem("accountTableID.id", tableInfo.getId().toString());
    	}
    	if(SysContext.getSysContext().getCurrentFIUnit()!=null&&SysContext.getSysContext().getCurrentFIUnit().getId()!=null){
    		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    		
//    		f.getFilterItems().add(new FilterItemInfo("companyID.id", SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
    		/*******��������ȷ����Ҫ���˳����Ļ�ƿ�Ŀ�Ƕ�Ӧ������Ŀ���ڲ�����֯�ġ� -by neo***/
    		if(node == null)
    			f.getFilterItems().add(new FilterItemInfo("companyID.id", SysContext.getSysContext().getCurrentFIUnit().getId().toString()));
    		else
    			if(node.getUserObject() instanceof CurProjectInfo){//��֯��û�й�����Ŀʱ�����ǹ�����Ŀ���ʹ��� by hpw 2009-07-23
	    			f.getFilterItems().add(
	    				new FilterItemInfo("companyID.id", ((CurProjectInfo)node.getUserObject()).getFullOrgUnit().getId().toString(),
	    						CompareType.EQUALS));
    			}
    	}
	    view.setFilter(f);
	    f7.setEntityViewInfo(view);
	    f7.setEditable(true);
		return f7;
	}

	/**
	 * output tblMain_editStopped method
	 */
	protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		changeByEdit(e);
	}

	private void changeByEdit(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		IColumn col = table.getColumn(e.getColIndex());
		IRow row = table.getRow(e.getRowIndex());
		if ((col.getKey().trim().toLowerCase().equals(COL_COSTACCOUNT.toLowerCase()) && (row.getCell(COL_COSTACCOUNT).getValue() != null))) 
		{
			/**
			 * ����������ɱ���Ŀ���룬��û���޸�ֵ������¿����ǲ���Ҫ������ʾ��
			 * ����ɱ���Ŀ����û�з����ı�Ͳ��ñ���
			 */
			if(row.getCell(COL_COSTACCOUNT_NAME).getValue() != null)
			{
				if(!row.getCell(COL_COSTACCOUNT_NAME).getValue().equals(((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getName()))
				{
					row.getCell(COL_COSTACCOUNT_NAME).setValue(((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getName());
					isModify=true;
				}
			}
			else
			{
				row.getCell(COL_COSTACCOUNT_NAME).setValue(((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getName());
				isModify=true;
			}
			
			if (((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getFullOrgUnit() != null) {
				String displayName = ((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getFullOrgUnit().getDisplayName(SysContext.getSysContext().getLocale());
				if(displayName!=null&&displayName.trim().length()>0){
					displayName=displayName.replaceAll("_", "/");
				}
				row.getCell(COL_FULLORGUNIT_NAME).setValue(displayName);
			} else {
				row.getCell(COL_FULLORGUNIT_NAME).setValue(null);
			}
			if (((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getCurProject() != null) {
				CurProjectInfo curProject = ((CostAccountInfo) row.getCell(COL_COSTACCOUNT).getValue()).getCurProject();
				String displayName = curProject.getDisplayName(SysContext.getSysContext().getLocale());
				if(displayName!=null&&displayName.trim().length()>0){
					displayName=displayName.replaceAll("_", "/");
				}
				/**
				 * ����������ɱ���Ŀ���룬��û���޸�ֵ������¿����ǲ���Ҫ������ʾ��
				 * �����ǰ��Ŀ����û�з����ı�Ͳ��ñ���
				 */
				if(row.getCell(COL_CURPROJECT_NAME).getValue() != null)
				{
					if(!row.getCell(COL_CURPROJECT_NAME).getValue().equals(displayName))
					{
						row.getCell(COL_CURPROJECT_NAME).setValue(displayName);
						isModify=true;
					}
				}
				else
				{
					row.getCell(COL_CURPROJECT_NAME).setValue(displayName);
					isModify=true;
				}
				if(FDCHelper.isEmpty(row.getCell(COL_FULLORGUNIT_NAME).getValue())
						&&curProject.getFullOrgUnit()!=null){
					displayName=curProject.getFullOrgUnit().getDisplayName();
					if(displayName!=null&&displayName.trim().length()>0){
						displayName=displayName.replaceAll("_", "/");
					}
					
					row.getCell(COL_FULLORGUNIT_NAME).setValue(displayName);
				}
			} else {
				row.getCell(COL_CURPROJECT_NAME).setValue(null);
			}
		}
		
		/**
		 * �����������ƿ�Ŀ����û���޸�ֵ������¿����ǲ���Ҫ������ʾ��
		 * ����ɱ���Ŀ����û�з����ı�Ͳ��ñ���
		 */
		if ((col.getKey().trim().toLowerCase().equals(COL_ACCOUNT.toLowerCase()) && (row.getCell(COL_ACCOUNT).getValue() != null))) {
			if(row.getCell(COL_ACCOUNT_NAME).getValue() != null)
			{
				if(!row.getCell(COL_ACCOUNT_NAME).getValue().equals(((AccountViewInfo) row.getCell(COL_ACCOUNT).getValue()).getName()))
				{
					row.getCell(COL_ACCOUNT_NAME).setValue(((AccountViewInfo) row.getCell(COL_ACCOUNT).getValue()).getName());
					isModify=true;
				}
			}
			else
			{
				row.getCell(COL_ACCOUNT_NAME).setValue(((AccountViewInfo) row.getCell(COL_ACCOUNT).getValue()).getName());
				isModify=true;
			}
		}
		/**
		 * �����������Ʊ��Ŀ����û���޸�ֵ������¿����ǲ���Ҫ������ʾ��
		 * ����ɱ���Ŀ����û�з����ı�Ͳ��ñ���
		 */
		if ((col.getKey().trim().toLowerCase().equals(COL_INVOICEACCOUNT.toLowerCase()) && (row.getCell(COL_INVOICEACCOUNT).getValue() != null))) {
			if(row.getCell(COL_INVOICEACCOUNT_NAME).getValue() != null)
			{
				if(!row.getCell(COL_INVOICEACCOUNT_NAME).getValue().equals(((AccountViewInfo) row.getCell(COL_INVOICEACCOUNT).getValue()).getName()))
				{
					row.getCell(COL_INVOICEACCOUNT_NAME).setValue(((AccountViewInfo) row.getCell(COL_INVOICEACCOUNT).getValue()).getName());
					isModify=true;
				}
			}
			else
			{
				row.getCell(COL_INVOICEACCOUNT_NAME).setValue(((AccountViewInfo) row.getCell(COL_INVOICEACCOUNT).getValue()).getName());
				isModify=true;
			}
		}
	}

	/**
	 * output actionAddLine_actionPerformed method
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		this.tblMain.checkParsed();
		int index = -1;
		index = this.tblMain.getRowCount();
		IRow row;
		if (index != -1) {
			row = tblMain.addRow(index);
		} else {
			row = tblMain.addRow();
		}
		//
		ICell cellValueAttribute = row.getCell(COL_COSTACCOUNT);
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		JTextField txtEnable = new JTextField();
		// txtEnable.setRequestFocusEnabled(true);
		// txtEnable.getColorModel().getRed(1);

		KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createCostAccountF7());
		// ceEnable.get
		cellValueAttribute.setEditor(ceEnable);
		// /*cellValueAttribute*/.getKDT
		//
		cellValueAttribute = row.getCell(COL_ACCOUNT);
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		txtEnable = new JTextField();
		ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createAccountViewF7());
		cellValueAttribute.setEditor(ceEnable);
		//
		cellValueAttribute = row.getCell(COL_INVOICEACCOUNT);
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		txtEnable = new JTextField();
		ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createAccountViewF7());
		cellValueAttribute.setEditor(ceEnable);
		
		row.getCell(COL_COSTACCOUNT_NAME).getStyleAttributes().setLocked(true);
		row.getCell(COL_ACCOUNT_NAME).getStyleAttributes().setLocked(true);
		row.getCell(COL_INVOICEACCOUNT_NAME).getStyleAttributes().setLocked(true);
		row.getCell(COL_DESCRIPTION).getStyleAttributes().setLocked(false);

		// ��������ݶ���ȷ���˵�ǰtblMain������,����水ť
		if (costAccountWithAccountCollection.size() == 0) {// ��ǰ����������Ϊ��ʱ����Ҫִ�м����,��Ϊ��ʱ���水ťĬ��Ϊ�ҵ���
			this.btnSave.setEnabled(true);
		}
		isModify = true;
	}

	/**
	 * output actionInsertLine_actionPerformed method
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		int i = -1;
		i = this.tblMain.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(resource, "Selected_Insert"));
			return;
		}
		IRow row;
		row = tblMain.addRow(i);
		//
		ICell cellValueAttribute = row.getCell(COL_COSTACCOUNT);
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		JTextField txtEnable = new JTextField();
		KDTDefaultCellEditor ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createCostAccountF7());
		cellValueAttribute.setEditor(ceEnable);
		//
		cellValueAttribute = row.getCell(COL_ACCOUNT);
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		txtEnable = new JTextField();
		ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createAccountViewF7());
		cellValueAttribute.setEditor(ceEnable);
		//
		cellValueAttribute = row.getCell(COL_INVOICEACCOUNT);
		cellValueAttribute.getStyleAttributes().setLocked(false);
		cellValueAttribute.setValue(null);
		txtEnable = new JTextField();
		ceEnable = new KDTDefaultCellEditor(txtEnable);
		ceEnable = new KDTDefaultCellEditor(createAccountViewF7());
		cellValueAttribute.setEditor(ceEnable);
		
		row.getCell(COL_COSTACCOUNT_NAME).getStyleAttributes().setLocked(true);
		row.getCell(COL_ACCOUNT_NAME).getStyleAttributes().setLocked(true);
		row.getCell(COL_INVOICEACCOUNT_NAME).getStyleAttributes().setLocked(true);
		row.getCell(COL_DESCRIPTION).getStyleAttributes().setLocked(false);
		isModify = true;
	}

	/**
	 * output actionDeleteLine_actionPerformed method
	 */
	public void actionDeleteLine_actionPerformed(ActionEvent e) throws Exception {

		int i = -1;
		i = this.tblMain.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(resource, "Selected_Delete"));
			return;
		}
		checkRef(tblMain);
		this.tblMain.removeRow(i);
		// �����ǰtblMain��û���˼�¼,���ҳ�ʼ����������Ϊ��(��Ϊ��ʱ������,��������м�¼),��û�б�������,�ҵ����水ť
		if (!(this.tblMain.getRowCount() > 0) && (!(costAccountWithAccountCollection.size() > 0))) {
			this.btnSave.setEnabled(false);
		}else{			
			isModify = true;
			this.btnSave.setEnabled(true);
		}
	}

	private CostAccountWithAccountCollection beforeSubmit() {
		CostAccountWithAccountCollection costAccountWithAccountCollection = new CostAccountWithAccountCollection();
		String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		if (this.tblMain.getRowCount() >= 0) {
			CostAccountWithAccountInfo costAccountWithAccountInfo;
			KDBizMultiLangBox solutionNameTextField = new KDBizMultiLangBox();
			List langList = KDBizMultiLangBox.getLanguageList();
			for (int j = 0; j < langList.size(); j++) {
				if (SysContext.getSysContext().getLocale().toString().equalsIgnoreCase(((LanguageInfo) (langList.get(j))).getLocale().toString()))
					solutionNameTextField.setSelectedLanguage((LanguageInfo) langList.get(j));
			}
			for (int i = 0; i < this.tblMain.getRowCount(); i++) {
				costAccountWithAccountInfo = new CostAccountWithAccountInfo();
				costAccountWithAccountInfo.setCostAccount((CostAccountInfo) this.tblMain.getRow(i).getCell(COL_COSTACCOUNT).getValue());
				costAccountWithAccountInfo.setAccount((AccountViewInfo) this.tblMain.getRow(i).getCell(COL_ACCOUNT).getValue());
				
				/********�ж��Ƿ����ø���ģʽһ�廯��Ʊ����************/
//				try {
//					if(FDCUtils.getDefaultFDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_INVOICEMRG))
				//���淢Ʊ��Ŀ��Ϣ��"�Ƿ����ø���ģʽһ�廯��Ʊ����"����û�й�ϵ��BUG  by cassiel_peng 2010-04-07
						costAccountWithAccountInfo.setInvoiceAccount(
								(AccountViewInfo) this.tblMain.getRow(i).getCell(COL_INVOICEACCOUNT).getValue());
//				} catch (EASBizException e) {
//					e.printStackTrace();
//				} catch (BOSException e) {
//					e.printStackTrace();
//				}
				if (this.tblMain.getRow(i).getCell(COL_ID).getValue() != null) {
					costAccountWithAccountInfo.setId(BOSUuid.read(this.tblMain.getRow(i).getCell(COL_ID).getValue().toString()));
				}
				if (this.tblMain.getRow(i).getCell(COL_DESCRIPTION).getValue() != null) {
					solutionNameTextField.getEditor().setItem(this.tblMain.getRow(i).getCell(COL_DESCRIPTION).getValue().toString());
					UIHelper.storeMultiLangFields(solutionNameTextField, costAccountWithAccountInfo, COL_DESCRIPTION);
				}
				costAccountWithAccountCollection.add(costAccountWithAccountInfo);
			}
		}
		return costAccountWithAccountCollection;
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		saveData();
	}
	private void saveData() throws Exception {
		if (verifyInput()) {// ���������Ϣ
			CostAccountWithAccountCollection costAccountWithAccountCollection = beforeSubmit();
			ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
			iCostAccountWithAccount.submitAll(costAccountWithAccountCollection,currentSelectNode);
			// �ɹ���ʾ
			MsgBox.showInfo(com.kingdee.eas.util.client.EASResource.getString(resource, "Save_Successed"));
			isModify=false;
		}
	}

	/**
	 * ���������Ϣ
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean verifyInput() throws EASBizException, BOSException {
		// CostAccountCollection cac = new CostAccountCollection();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			if (this.tblMain.getRow(i).getCell(COL_COSTACCOUNT).getValue() == null) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(resource, "Turn") + (i + 1)
						+ com.kingdee.eas.util.client.EASResource.getString(resource, "Line")
						+ com.kingdee.eas.util.client.EASResource.getString(resource, "CostAccount_IsNull"));
				return false;
			} else if (this.tblMain.getRow(i).getCell(COL_ACCOUNT).getValue() == null) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(resource, "Turn") + (i + 1)
						+ com.kingdee.eas.util.client.EASResource.getString(resource, "Line")
						+ com.kingdee.eas.util.client.EASResource.getString(resource, "AccountView_IsNull"));
				return false;
			}
		}
		//TODO �˽�����ȡ��
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = i+1; j < this.tblMain.getRowCount(); j++) {
				if (((CostAccountInfo)this.tblMain.getRow(i).getCell(COL_COSTACCOUNT).getValue()).getId().toString().equals(((CostAccountInfo)this.tblMain.getRow(j).getCell(COL_COSTACCOUNT).getValue()).getId().toString())) {
					String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
					if (FDCUtils.getDefaultFDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_INVOICEMRG)) {
						MsgBox
								.showError(com.kingdee.eas.util.client.EASResource.getString(resource,
										"CostAccountWithAccount_InvoiceExist"));
					} else {
						MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(resource, "CostAccountWithAccount_Exist"));
					}
					return false;
				}
			}
		}
		return true;
	}

	
	private boolean checkRef(KDTable table) throws EASBizException, BOSException {
		boolean hasRef=false;
		int rowNum=table.getSelectManager().getActiveRowIndex();
		Object obj=table.getRow(rowNum).getUserObject();
		if(obj instanceof CostAccountWithAccountInfo){
			CostAccountWithAccountInfo cwa=(CostAccountWithAccountInfo)obj;
			if(cwa.getCostAccount().getId()==null||cwa.getAccount().getId()==null){
				return false;
			}
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("costAccount.id", cwa.getCostAccount().getId().toString());
			filter.appendFilterItem("accountView.id", cwa.getAccount().getId().toString());
			
			if(PaymentSplitEntryFactory.getRemoteInstance().exists(filter)){
				hasRef=true;
				MsgBox.showError(this, com.kingdee.eas.util.client.EASResource.getString(resource, "ProjectWithCostCenter_Delete_Referenced"));
				SysUtil.abort();
			}
		}
		return hasRef;
	}
	
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		MsgBox.showInfo(this, "�Ѵ��ڵĳɱ���Ŀ���������룻��ƿ�Ŀ������ϸ��Ŀ�Ľ�������Ϊ��Ӧ��ƿ�Ŀ�¼��ĵ�һ����ϸ��ƿ�Ŀ");
		Set idSet = null;

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) node.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				idSet = FDCClientUtils.getProjIdsOfCostOrg(((OrgStructureInfo)projTreeNodeInfo).getUnit(),true);
			}
			// ѡ�������Ŀ��ȡ����Ŀ���¼���Ŀ������У��µ�������ĿID
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				idSet = FDCClientUtils.genProjectIdSet(projTreeNodeInfo.getId(),true);
			}
		}else{
			idSet = FDCClientUtils.getProjIdsOfCostOrg((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit(),true);
		}
		if(idSet != null && idSet.size()>0){
			CostAccountWithAccountFactory.getRemoteInstance().importGroupData((HashSet)idSet);
			refreshCostAccountWithAccountList((DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent());
		}
	}
	
	private boolean isModify=false;
	protected boolean checkBeforeWindowClosing() {
		boolean isClose=true;
		if (isModify) {
			int ret = MsgBox.showConfirm3(this, "�������޸ģ��Ƿ񱣴棿");
			if (ret==MsgBox.YES) {
				btnSave.doClick();
			} else if(ret==MsgBox.CANCEL){
				isClose=false;
			}
		}
		return isClose;
	}

	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		
		isModify=true;
	}

	private void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
	}
	
	private DefaultKingdeeTreeNode lastSelectNode = null;
	private String currentSelectNode = null;
	private boolean isFirstIn = true;
	/**
	 * output treeMain_mouseClicked method
	 */
	protected void treeMain_mouseClicked(java.awt.event.MouseEvent e)	throws Exception {
	}
	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		if(!isFirstIn){
			isFirstIn = true;
			return;
		}
		if(isModify) {
			int result = MsgBox.showConfirm2New(this, "�������޸ģ��Ƿ񱣴棿");
			if(result == MsgBox.YES) {
				saveData();
				if(isModify){
					isFirstIn = false;
					treeMain.setSelectionPath(e.getOldLeadSelectionPath());
				}else{
					isFirstIn = true;
					refreshCostAccountWithAccountList((DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent());
				}
			}else{
				isFirstIn = true;
				refreshCostAccountWithAccountList((DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent());
			}
		}else{
			isFirstIn = true;
			refreshCostAccountWithAccountList((DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent());
		}
		
		setTblMainEditStatus();
	}
	
	private void refreshCostAccountWithAccountList(DefaultKingdeeTreeNode node) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if(node == null){
			node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		}
		/*
		 * ������Ŀ��
		 */
		if (node != null && node.getUserObject() instanceof CoreBaseInfo) {
			
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) node.getUserObject();
			//ѡ����ǳɱ����ģ�ȡ�óɱ����ļ��¼��ɱ����ģ�����У��µ����п�Ŀ
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				FullOrgUnitInfo orgUnit = ((OrgStructureInfo)projTreeNodeInfo).getUnit();

				BOSUuid id = orgUnit.getId();
				currentSelectNode = id.toString();
				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("costAccount.curProject.fullOrgUnit.id", idSet,
						CompareType.INCLUDE));
				
				if (OrgConstants.DEF_CU_ID.equals(orgUnit.getId().toString())) {
					setImportButtonStatus(true,true);
					loadData(new FilterInfo());
					return;
				}
//				else if (orgUnit.isIsCompanyOrgUnit())
//				{
//					//����ǹ�˾���Ҹù�˾Ϊʵ�������֯ʱ��Ҳ�ɵ��뼯��ģ��
//					String companyId = orgUnit.getId().toString();
//
//					FDCSQLBuilder builder=new FDCSQLBuilder();
//					builder.appendSql("select fid from T_ORG_Company where FID = ? ");
//					builder.addParam(companyId);
//					builder.appendSql(" and FIsBizUnit = ? ");
//					builder.addParam(Boolean.TRUE);
//					
//					IRowSet result =builder.executeQuery();
//
//					if(result.size() > 0)
//					{
//						setImportButtonStatus(true,true);
//					}
//					else
//					{
//						setImportButtonStatus(false,false);
//					}
//				}
				else{
//					setImportButtonStatus(false,true);
					setImportButtonStatus(false,false);
				}
			
			}
			// ѡ�������Ŀ��ȡ����Ŀ���¼���Ŀ������У��µ����п�Ŀ
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				CurProjectInfo pInfo = (CurProjectInfo)projTreeNodeInfo;
				BOSUuid id = projTreeNodeInfo.getId();
				currentSelectNode = id.toString();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("costAccount.curProject.id", idSet,
						CompareType.INCLUDE));
				if(pInfo.isIsLeaf()){
					setImportButtonStatus(true,true);
				}else{
					setImportButtonStatus(false,false);
				}				
			}
			filterItems.add(new FilterItemInfo("costAccount.curProject.isEnabled", Boolean.TRUE));
			filter.setMaskString("#0 and #1");
		}
		loadData(filter);
	}
	
	private void setImportButtonStatus(boolean canDo,boolean canImport){	
			this.btnAddLine.setEnabled(canDo);
			this.btnDeleteLine.setEnabled(canDo);
			this.btnInsertLine.setEnabled(canDo);
			this.btnSave.setEnabled(canDo);
			this.menuAddLine.setEnabled(canDo);
			this.menuDeleteLine.setEnabled(canDo);
			this.menuInsertLine.setEnabled(canDo);
			this.menuSave.setEnabled(canDo);
			actionImport.setEnabled(canImport);
	}
}