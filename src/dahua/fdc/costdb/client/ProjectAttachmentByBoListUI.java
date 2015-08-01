/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.data.engine.script.beanshell.function.dataobject.query.GETFILTER;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.client.AttachmentEditUI;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.client.ScanDeviceSelectUI;
import com.kingdee.eas.base.attachment.client.scan.ReadScanFile;
import com.kingdee.eas.base.attachment.client.scan.ScanHelper;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.UICreator;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.myeas.ToolBarStyleEnum;
import com.kingdee.eas.base.uiframe.client.UIFrameContext;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.client.DynCostSnapShotFilterUI;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectPromptUI;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionAddAlreadyFile;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionAddNewAttch;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionAddScan;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionDisplayFunction;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionDownload;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionSelectScan;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionSetDriver;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionSetScan;
import com.kingdee.eas.fdc.costdb.client.AbstractProjectAttachmentByBoListUI.ActionViewContent;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.client.AuditCancelFilterPanel;
import com.kingdee.eas.fi.gl.client.AuditCancelUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 描述:房地产项目概况查询
 * @author jackwang  date:2007-6-7 <p>
 * @version EAS5.3
 */
public class ProjectAttachmentByBoListUI extends AbstractProjectAttachmentByBoListUI
{
    
    Logger logger = Logger.getLogger("com.kingdee.eas.fdc.costdb.client.ProjectAttachmentByBoListUI");
    
    private FileGetter fg;
    
    private AttachmentUIContextInfo attachmentUIContext;
    
    int sort = KDTSortManager.SORT_ASCEND;
    
    static String scanSource = null;

	private CustomerQueryPanel filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	FDCCustomerParams para = new FDCCustomerParams();
    /**
     * output class constructor
     */
    public ProjectAttachmentByBoListUI() throws Exception
    {
        super();
        initCtrls();
//        super.menuItemDownload.setEnabled(true);
//        super.menuItemViewContent.setEnabled(true);
    }

    private void initCtrls() {
        setUITitle("项目概况");        
        btnRefresh.setVisible(false);
        btnPrint.setVisible(false);
        btnPrintPreview.setVisible(false);
        btnLocate.setVisible(false);
        btnQuery.setVisible(false);
//        btnViewContent.setEnabled(true);
//        btnViewContent.setIcon(EASResource.getIcon("imgTbtn_open"));
//        btnDownload.setIcon(EASResource.getIcon("imgTbtn_dcdwj"));
//        btnDownload.setEnabled(true);
        
        this.btnAddAttch.setIcon(EASResource.getIcon("imgTbtn_new"));
        this.btnAddAttch.setAction(actionAddNew);   
        this.menuItemViewContent.setText(resHelper.getString("btnViewContent.text"));
        
        KDMenuItem addNewMenuItem = new KDMenuItem(Resrcs.getString("AddAttachMenuItem"));
        addNewMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(
                actionAddNew, new Class[] { IItemAction.class }, getServiceContext()));
        addNewMenuItem.setIcon(EASResource.getIcon("imgTbtn_new"));
        
        KDMenuItem addAlreadyMenuItem = new KDMenuItem(Resrcs.getString("AddAttachMenuItem"));
        addAlreadyMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(
                actionAddAlreadyFile, new Class[] { IItemAction.class }, getServiceContext()));
        addAlreadyMenuItem.setIcon(EASResource.getIcon("imgTbtn_adduction"));
        
        KDMenuItem addScanMenuItem = new KDMenuItem(Resrcs.getString("AddAttachMenuItem"));
        addScanMenuItem.setAction((IItemAction)ActionProxyFactory.getProxy(
                this.actionAddScan, new Class[] { IItemAction.class }, getServiceContext()));
        addScanMenuItem.setIcon(EASResource.getIcon("imgTbtn_adduction"));
        
        this.btnAddAttch.addAssistMenuItem(addNewMenuItem);
        this.btnAddAttch.addAssistMenuItem(addAlreadyMenuItem);
        this.btnAddAttch.addAssistMenuItem(addScanMenuItem);
        this.btnAddAttch.setToolTipText(resHelper.getString("btnAddAttch.toolTipText"));
        
        ToolBarStyleEnum toolBarStyleEnum = (ToolBarStyleEnum) UIFrameContext
        .getInstance().getProperty(UIFrameContext.MYEAS_TOOLBARSTYLE);
        if (toolBarStyleEnum == null
                || toolBarStyleEnum.equals(ToolBarStyleEnum.IconText)) {
            this.btnAddAttch.setIsControlByParent(false);
            this.btnAddAttch.setTextIconDisStyle(ITextIconDisplayStyle.ONLY_ICON);
        }
        else
        {
            this.btnAddAttch.setIsControlByParent(true);
        }
        //
        this.btnAddAttch.setVisible(false);
        this.btnEdit.setVisible(false);
        this.btnRemove.setVisible(false);
        this.menuItemAddNew.setVisible(false);
        this.menuItemEdit.setVisible(false);
        this.menuItemRemove.setVisible(false);
    }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /* （非 Javadoc）
     * @see com.kingdee.eas.framework.client.ListUI#getEditUIName()
     */
    protected String getEditUIName() {
        return AttachmentEditUI.class.getName();
    }

    /* （非 Javadoc）
     * @see com.kingdee.eas.framework.client.ListUI#getBizInterface()
     */
    protected ICoreBase getBizInterface() throws Exception {
        return AttachmentFactory.getRemoteInstance();
    }
    
    
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
        super.prepareUIContext(uiContext, e);
        uiContext.put("boID", this.attachmentUIContext.getBoID());
        String code = this.attachmentUIContext.getCode();
        if(code != null && !code.equals("") ) {		
            uiContext.put("code",code);
        }
    }
  

//    private String getExtraCode() {
//        return this.attachmentUIContext.getCode();
//    }
    
    private void addExtraFilterItem(FilterInfo fi) throws BOSException, ParserException {
        FilterItemCollection fic = fi.getFilterItems();
        if(!StringUtils.isEmpty(this.attachmentUIContext.getCode())) {
            fic.add(new FilterItemInfo("number",this.attachmentUIContext.getCode(),CompareType.EQUALS));
        } 
        if(!StringUtils.isEmpty(this.attachmentUIContext.getNumber()))
        {
            FilterInfo filterNoNumber = new FilterInfo("number not in( '"+ this.attachmentUIContext.getNumber().trim()+"')  or number is null");
            fi.mergeFilter(filterNoNumber,"AND");
        }
    }
    
    private void addExtraFilterItemCollection(FilterItemCollection fic) {
        if(this.attachmentUIContext.getFilterItemCol() == null) return;
        FilterItemCollection efic= this.attachmentUIContext.getFilterItemCol();
        int size=efic.size();
        for(int i=0;i<size;i++) {
            fic.add(efic.get(i));
        }
    }
    
    /* （非 Javadoc）
     * @see com.kingdee.eas.framework.client.ListUI#actionRemove_actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
        checkSelected();
        if(confirmRemove()) {
            try  {
                AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
                IIDList idList = attachmentClientManager.getIdInTable(this.tblMain,this.getKeyFieldName());
                attachmentClientManager.deleteAssoAttachment(this.attachmentUIContext.getBoID(),idList);
            } catch (ObjectNotFoundException onfe) {
                logger.info("",onfe);
                refreshList();
                throw new EASBizException(EASBizException.CHECKEXIST);
            }
            refresh(e);
        }
    }
    
    private IBoAttchAsso getIBAA() throws Exception {
        return BoAttchAssoFactory.getRemoteInstance();
    }
    
    private FileGetter getFileGetter() throws Exception {
        if(fg==null) fg=new FileGetter((IAttachment)getBizInterface(),AttachmentFtpFacadeFactory.getRemoteInstance());
        return fg;
    }
    
    /* （非 Javadoc）
     * @see com.kingdee.eas.base.attachment.client.AbstractAttachmentByBoListUI#actionDownload_actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionDownload_actionPerformed(ActionEvent e) throws Exception {
        checkSelected();
        AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
        getFileGetter().downloadAttachment(attachmentClientManager.getIdInTable(this.tblMain,this.getKeyFieldName()),this);
    }
    
    /**
     * 主要是修改成为点击以后可以直接就进行选择文件，然后上传文件的过程。
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
        //1、先弹出文件选择的列表，选择文件
        try
        {
            AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
            attachmentClientManager.showUploadFilesUI(this,this.attachmentUIContext);
            this.execQuery();
        }
        catch(BOSException be)
        {
            MsgBox.showError(this,Resrcs.getString("uploadFileFail"));
        }
    }
    
    /* （非 Javadoc）
     * @see com.kingdee.eas.base.attachment.client.AbstractAttachmentByBoListUI#actionViewContent_actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionViewContent_actionPerformed(ActionEvent e)
            throws Exception {
        checkSelected();
        getFileGetter().viewAttachment(getSelectedKeyValue(),this);
    }
    
    
    /* （非 Javadoc）
     * @see com.kingdee.eas.base.attachment.client.AbstractAttachmentByBoListUI#actionAddAlreadyFile_actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionAddAlreadyFile_actionPerformed(ActionEvent e)
            throws Exception {
        // TODO 自动生成方法存根
//        AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
//        attachmentClientManager.showAssoAttachmentUI(this,this.attachmentUIContext);
//        String sqlStr = this.mainQuery.getFilter().toString();
//        this.refreshList();
    }

    
    public void onLoad() throws Exception {
      super.onLoad();
//      initDefaultFilter();
      FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionPrint");
      setButtonEnabled(false);
      
//      this.scanSource = null;
      this.addSrcFunction();
      this.btnQuery.setVisible(true);
      this.btnQuery.setEnabled(true);
      this.menuEdit.setVisible(false);
      
      actionAddNewAttch.setEnabled(false);
      actionRemove.setEnabled(false);
      actionEdit.setEnabled(false);
    }
    private boolean isFirstIn = true;
	protected boolean initDefaultFilter() {
		return true;
	}
	protected EntityViewInfo getInitDefaultSolution() {

		EntityViewInfo entityViewInfo = new EntityViewInfo();
		try {
			this.getFilterUI().onLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityViewInfo.setFilter(this.getFilterUI().getFilterInfo());
		return entityViewInfo;
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		// TODO 自动生成方法存根
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.setShowTable(false);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}
	
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ProjectAttachmentFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	
	protected void execQuery() {
		HashMap hm;
		try {
			hm = ((ProjectAttachmentFilterUI)getFilterUI()).getResult();
			if(hm.get("projectIds")==null){
				if(mainQuery!=null&&mainQuery.getFilter()!=null){
					FilterInfo filter=mainQuery.getFilter();
					for(int i=0;i<filter.getFilterItems().size();i++){
						FilterItemInfo item=filter.getFilterItems().get(i);
						if(item.getPropertyName()!=null&&item.getPropertyName().equals("boAttchAsso.boID")&&item.getCompareValue()!=null){
							hm.put("projectIds",item.getCompareValue());
						}
					}
				}
			}
			if(hm.size()==0){
				return;
			}
			FilterInfo fi=new FilterInfo();;
			this.attachmentUIContext = new AttachmentUIContextInfo();
			this.attachmentUIContext.setBoID((String) hm.get("projectIds"));// = info;
			this.attachmentUIContext.setEdit(false);
			fi.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", this.attachmentUIContext.getBoID(), CompareType.EQUALS));
			FilterItemCollection fic = fi.getFilterItems();
			addExtraFilterItemCollection(fic);
			this.mainQuery = this.getEntityViewInfo(this.mainQuery);
			this.mainQuery.setFilter(fi);
			addExtraFilterItemCollection(fic);
			
			addExtraFilterItem(fi);
		} catch (Exception e) {
			this.handUIException(e);
		}
		super.execQuery();	
	}
    protected void openConditionDialog(ProjectAttachmentFilterUI myPanel) throws Exception {
        if (this.getConditionDialog().show()) {
//	    	HashMap hm = getFilterPanel().getResult();
	    	HashMap hm = ((ProjectAttachmentFilterUI)getFilterUI()).getResult();
	        this.attachmentUIContext = new AttachmentUIContextInfo();
	        this.attachmentUIContext.setBoID((String) hm.get("projectIds"));// = info;
	        this.attachmentUIContext.setEdit(false);
	        FilterInfo fi = new FilterInfo();
	        FilterItemCollection fic = fi.getFilterItems();
	        fic.add(new FilterItemInfo("boAttchAsso.boID", this.attachmentUIContext.getBoID(), CompareType.EQUALS));
	        addExtraFilterItemCollection(fic);
	        this.mainQuery = this.getEntityViewInfo(this.mainQuery);
	        this.mainQuery.setFilter(fi);
	        addExtraFilterItemCollection(fic);
	        
	        //不需要使用编号进行过滤
	        try {
	            addExtraFilterItem(fi);
	        } catch (BOSException e) {
	            // TODO 自动生成 catch 块
	            logger.info("",e);
	        } catch (ParserException e) {
	            // TODO 自动生成 catch 块
	            logger.info("",e);
	        }
	            this.tblMain.removeRows();
        } else {
            SysUtil.abort();
        }
    }
    /* （非 Javadoc）
     * @see com.kingdee.eas.framework.client.ListUI#getDefaultFilterForQuery()
     */
    protected FilterInfo getDefaultFilterForQuery()  {

        FilterInfo fi = new FilterInfo();
        return fi;
    }
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
//    	if(isFirstIn){
//    		openConditionDialog(getFilterPanel());
//    	}
    	super.actionQuery_actionPerformed(e);
    }
    public CommonQueryDialog getConditionDialog() throws Exception {
//        if (conditionDialog == null) {
//            conditionDialog = new CommonQueryDialog();
//            conditionDialog.setOwner(this);
//            MetaDataPK mainQueryPK = new MetaDataPK("com.kingdee.eas.base.attachment.app", "AttachmentQuery");
//            conditionDialog.setQueryObjectPK(mainQueryPK);
//            conditionDialog.setShowFilter(false);
//            conditionDialog.setShowSorter(false);
//            conditionDialog.setWidth(429);
//            conditionDialog.setHeight(356);
//            conditionDialog.addUserPanel(getFilterPanel());
//            conditionDialog.setTitle(getFilterPanel().getUITitle());
//            conditionDialog.setParentUIClassName("com.kingdee.eas.fdc.costdb.client.ProjectAttachmentByBoListUI");
//            conditionDialog.setTitle("项目概况查询");
//            conditionDialog.setDisVisiableDefaultView(true);
//        }
        ///
		if (conditionDialog != null) {
			return conditionDialog;
		}
		conditionDialog = new CommonQueryDialog();
        if (this.getUIWindow() == null)
        {
        	conditionDialog.setOwner((Component) getUIContext().get(UIContext.OWNERWINDOW));
        }
        else
        {
        	conditionDialog.setOwner(this);
        }
        conditionDialog.setUiObject(this);
        conditionDialog.setParentUIClassName(this.getClass().getName());
        conditionDialog.setTitle(this.getUITitle() + " - " + EASResource.getString(FrameWorkClientUtils.strResource + "Query_Filter"));        
	    MetaDataPK mainQueryPK = new MetaDataPK("com.kingdee.eas.base.attachment.app", "AttachmentQuery");
	    conditionDialog.setQueryObjectPK(mainQueryPK);
		conditionDialog.setShowFilter(false);
		conditionDialog.setShowSorter(false);
		conditionDialog.setWidth(429);
		conditionDialog.setHeight(356);
		conditionDialog.setDisVisiableDefaultView(true);		
		conditionDialog.getCommonQueryParam().setUiObject(null);//.isShowTable();
		try {
			conditionDialog.addUserPanel(getFilterPanel());
		} catch (Exception e) {
		}
        return conditionDialog;
    }
    private CommonQueryDialog conditionDialog = null;

    private ProjectAttachmentFilterUI projectAttachmentFilterUI = null;
    public ProjectAttachmentFilterUI getFilterPanel() throws Exception {
        if (projectAttachmentFilterUI == null) {
        	projectAttachmentFilterUI = new ProjectAttachmentFilterUI();      
        }
        return projectAttachmentFilterUI;
    }
    private void addSrcFunction()
    {
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ctrl N"), "actionAddNew");
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ctrl K"), "actionAddAlreadyFile");
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ctrl B"), "actionAddScan");
        this.getActionMap().put("actionAddNew", actionAddNew );
        this.getActionMap().put("actionAddAlreadyFile", actionAddAlreadyFile );
        this.getActionMap().put("actionAddScan", actionAddScan );
    }
    protected void setButtonEnabled(boolean enabled)
    {
    	if(attachmentUIContext==null){
    		return;
    	}
        if(this.attachmentUIContext.isEdit())
        {
            this.btnAddNew.setEnabled(true);
            this.btnAddAttch.setEnabled(true);
            this.btnEdit.setEnabled(true);
            this.btnRemove.setEnabled(true);
        }
        else
        {
            this.btnAddNew.setEnabled(false);
            this.btnAddAttch.setEnabled(false);
            this.btnEdit.setEnabled(false);
            this.btnRemove.setEnabled(false);
        }
    }
	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}
    
    
    public void mergeContainAction(KDContainer cont, boolean isEdit) {
        actionViewContent.setEnabled(true);
        actioinDownload.setEnabled(true);
        if (isEdit) {
            cont.addButton(btnAddAttch);
            cont.addButton(btnEdit);
            cont.addButton(btnRemove);
            cont.addButton(btnView);
            cont.addButton(btnDownload);
            cont.addButton(btnViewContent);
        } else {
            cont.addButton(btnView);
            cont.addButton(btnDownload);
            cont.addButton(btnViewContent);
        }
    }

    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
        boolean isMy = false;
        if (isOrderForClickTableHead() && e.getType() == KDTStyleConstants.HEAD_ROW)
        {
            String columnName = tblMain.getColumn(e.getColIndex()).getFieldName();
            if("size".equalsIgnoreCase(columnName))
            {
                SorterItemInfo sortName = new SorterItemInfo("sizeInByte");
                if (sort == KDTSortManager.SORT_ASCEND)
                {
                    sort = KDTSortManager.SORT_DESCEND;
                    sortName.setSortType(SortType.DESCEND);
                }
                else
                {
                    sort = KDTSortManager.SORT_ASCEND;
                    sortName.setSortType(SortType.ASCEND);
                }
                
                if(this.mainQuery.getSorter() != null)
                {
                    this.mainQuery.getSorter().clear();
                }
                this.mainQuery.getSorter().add(sortName);
                this.tblMain.refresh();
                isMy = true;
            }
        }
        if(!isMy)
        {
            super.tblMain_tableClicked(e);
        }
    }
    
    public KDTable getTable()
    {
        return this.tblMain;
    }
    
    
    /**
     * output initUIContentLayout method
     */
    public void setKDLayout()
    {
//        this.setBounds(new Rectangle(10, 10, 660, 445));
//        this.setLayout(new KDLayout());
//        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 660, 445));
//        tblMain.setBounds(new Rectangle(0, 0, 640, 425));
//        this.add(tblMain, new KDLayout.Constraints(0, 0, 640, 425, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

    }
    
    public static String getDllFileName()
    {
        String filePath = System.getProperty("easclient.root");
        if(filePath.endsWith(File.separator))
        {
            return filePath + "jtwain.dll";
        }
        else
        {
            return filePath + File.separator +  "jtwain.dll";
        }
    }
    
//  先检查有没有指定的文件否则不执行
    private boolean checkHaveFile()
    {
        String fileName = getDllFileName();
        logger.debug("jtwain.dll file path is:" + fileName);
        File file = new File(fileName);
        boolean hasFile = file.exists(); 
        if(!hasFile)
        {
            MsgBox.showError(this,Resrcs.getString("ScanServerSettingNotRight"));
        }
        return hasFile;
    }
    
    private boolean isWindow()
    {
        if (System.getProperty("os.name").indexOf("Windows") == -1) {
            MsgBox.showInfo(Resrcs.getString("NotWindowsOS"));
            return false;
        }
        return true;
    }

    public void actionAddScan_actionPerformed(ActionEvent e) throws Exception {
        // TODO 自动生成方法存根
        super.actionAddScan_actionPerformed(e);
        
        if(!this.isWindow()) return;
        
        if(!this.checkHaveFile())return ;
        
        
        if(this.scanSource == null)
        {
            String[] source = ReadScanFile.getScanSource();
            if( source == null || source.length == 0 || StringUtils.isEmpty(source[0]))
            {
                MsgBox.showError(this,Resrcs.getString("ScanDriverNotInstall"));
                return;
            }
            else if(source.length != 1)
            {
                UIContext uictx = new UIContext(this);
                String uiname = ScanDeviceSelectUI.class.getName();
                String uimode = UIFactoryName.MODEL;
                UICreator.create(uiname, uimode, uictx);
            }
            else
            {
                this.scanSource = source[0];
            }
        }
        
        if(StringUtils.isEmpty(this.scanSource)) return;
        File scanFile = ReadScanFile.getScanFile(this.scanSource,this);
        
        if(scanFile == null)
        {
            MsgBox.showError(this,Resrcs.getString("ReadScanError"));
            return;
        }
        
        try
        {
            logger.info(" file path is:" + scanFile.getPath());
            File tempFile = new File(System.getProperty("user.home") + File.separator + ScanHelper.genFileName());
            scanFile.renameTo(tempFile);
            
            File[] scanFiles = new File[1];
            scanFiles[0] = tempFile;
            AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
            attachmentClientManager.uploadFile(scanFiles,this,this.attachmentUIContext);
            this.execQuery();
            tempFile.delete();
        }
        catch(BOSException be)
        {
            MsgBox.showError(this,Resrcs.getString("uploadFileFail"));
        }
    }
    protected String getEditUIModal()
    {
            return UIFactoryName.MODEL;
    }
}