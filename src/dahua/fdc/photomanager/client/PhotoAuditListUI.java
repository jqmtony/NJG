/**
 * output package name
 */
package com.kingdee.eas.fdc.photomanager.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.layout.table2.TableLayout2;
import com.kingdee.bos.ctrl.common.ui.textfield.SearchTextField;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UINewFrameFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.ForecastChangeVisFactory;
import com.kingdee.eas.fdc.aimcost.client.ForecastChangeVisEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.photomanager.PhotoAuditCollection;
import com.kingdee.eas.fdc.photomanager.PhotoAuditFactory;
import com.kingdee.eas.fdc.photomanager.PhotoAuditInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.rpts.snapshot.manage.client.SnapshotRunUI;
import com.kingdee.eas.rpts.snapshot.manage.client.icon.SSIcons;
import com.kingdee.eas.rpts.snapshot.manage.client.mview.MViewUIConfig;
import com.kingdee.eas.rpts.snapshot.manage.exception.SnapshotException;
import com.kingdee.eas.rpts.snapshot.manage.service.ISnapshotRelativeService;
import com.kingdee.eas.rpts.snapshot.manage.service.SnapshotRelativeService;
import com.kingdee.eas.rpts.sumreport.util.ExtMsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PhotoAuditListUI extends AbstractPhotoAuditListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PhotoAuditListUI.class);
	private final static String CANTAUDIT = "cantAudit";

	private static final String CANTEDIT = "cantEdit";
	protected String _niceNote = "";
    protected SearchTextField _tfSearch;
	private ISnapshotRelativeService _serv = SnapshotRelativeService.getInst();
	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	
	private KDTreeNode mailNode = null;
	private KDPanel _panel = null;
    /**
     * output class constructor
     */
    public PhotoAuditListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	buildTree();
    	initControl();
    	
    	kDTree1.expandRow(0);
    	kDTree1.expandRow(2);
    }
    
    private void buildTree() throws BOSException, SQLException{
    	KDTreeNode rootNode = new KDTreeNode("快照文件");
    	rootNode.setUserObject("all");
    	((DefaultTreeModel) kDTree1.getModel()).setRoot(rootNode);
    	
    	loadTreeForRptName(addTreeNode(rootNode, "个人文件夹", SSIcons.createIcon("tree_mycollection.gif")));
    	KDTreeNode Node = addTreeNode(rootNode, "公共文件夹", null);
    	
    	loadTreeForRptName(addTreeNode(Node, "未审批",SSIcons.FILE_NORMAL_ICON));
    	loadTreeForRptName(addTreeNode(Node, "审批中",SSIcons.FILE_NORMAL_ICON));
    	loadTreeForRptName(addTreeNode(Node, "已审批",SSIcons.FILE_NORMAL_ICON));
    	
    	mailNode = new KDTreeNode("收件箱");
    	mailNode.setTextColor(Color.RED);
    	mailNode.setCustomIcon(SSIcons.createIcon("tree_mailbox.gif"));
    	rootNode.add(mailNode);
    	
    	loadTreeForRptName(mailNode);
    }
    
    private KDTreeNode addTreeNode(KDTreeNode node,String name,Icon img){
    	KDTreeNode detailNode = new KDTreeNode(name);
    	if(img!=null)
    		detailNode.setCustomIcon(img);
    	node.add(detailNode);
    	return detailNode;
    }
    
    private void initControl(){
    	this.setUITitle("快照中心");
    	kDTable1.getSelectManager().setSelectMode(10);
    	kDTable1.setEnabled(false);
    	this.btnAddNew.setText("发起流程");
    	this.btnAddNew.setToolTipText("发起流程");
    	this.actionAudit.setVisible(false);
    	this.actionUnAudit.setVisible(false);
    	
    	initSearch();
    	
    	TableLayout2 ly = new TableLayout2(2, 3);
        ly.setFixedWidth(0, 300);
        ly.setFixedWidth(1, 3);
        ly.setRatableWidth(2, 110);
        ly.setFixedHeight(0, 20);
        ly.setRatableHeight(1, 500);
        ly.setColsSpacing(TableLayout2.ALL, 20);
        ly.setRowSpacing(0, 6);
        _panel = new KDPanel(ly);
        _panel.add(_tfSearch, TableLayout2.param(0, 0));
        _panel.add(kDTable1, TableLayout2.param(1, 0, 1, 2));
        kDContainer1.getContentPane().add(_panel, BorderLayout.CENTER);
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		this.btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		ForecastChangeVisEditUI.setEnableAcion(new ItemAction[]{actionEdit,actionLocate,actionQuery,actionAttachment,actionRefresh,actionCreateTo,actionTraceDown,actionTraceUp,actionPrint,actionPrintPreview});
		actionAttachment.setVisible(false);
		btnAttachment.setVisible(false);
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	PhotoAuditInfo info = getSelectInfo();
    	checkBeforeAuditOrUnAudit(info,FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
    	super.actionUnAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		setSaveActionStatus(info);
    }  
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	PhotoAuditInfo info = getSelectInfo();
		checkBeforeAuditOrUnAudit(info,FDCBillStateEnum.SUBMITTED, CANTAUDIT);
    	super.actionAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		setSaveActionStatus(info);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	PhotoAuditInfo info = getSelectInfo();
    	checkBeforeEditOrRemove(info,CANTEDIT);
    	super.actionEdit_actionPerformed(e);
    	setSaveActionStatus(info);   
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	PhotoAuditInfo info = getSelectInfo();
    	checkBeforeEditOrRemove(info,CANTEDIT);
    	super.actionRemove_actionPerformed(e);
    }
    
    private void checkBeforeEditOrRemove(PhotoAuditInfo info,String warning) throws BOSException {
		FDCClientUtils.checkBillInWorkflow(this,info.getId().toString());
		FDCBillStateEnum state = info.getStatus();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	private void checkBeforeAuditOrUnAudit(PhotoAuditInfo info,FDCBillStateEnum state, String warning) throws Exception{
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this,info.getId().toString());

		boolean b = info != null
				&& info.getStatus() != null
				&& info.getStatus().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

	}
    
	private void setSaveActionStatus(PhotoAuditInfo info) {
		try {
			refresh(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	private PhotoAuditInfo getSelectInfo() throws EASBizException, BOSException{
    	checkSelected();
    	String id = this.getSelectedKeyValue();
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("id");
    	sic.add("status");
    	return PhotoAuditFactory.getRemoteInstance().getPhotoAuditInfo(new ObjectUuidPK(id),sic);
    }
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int index = kDTable1.getSelectManager().getActiveRowIndex();
		if(index !=-1)
			openUI(index);
	}
    
	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		super.kDTable1_tableClicked(e);
		if(e.getClickCount() == 2&&e.getRowIndex()!=-1){
			openUI(e.getRowIndex());
		}
	}
	
	private void openUI(int rowIndex) throws SnapshotException, BOSException{
		String snapshotID = UIRuleUtil.getString(kDTable1.getCell(rowIndex, 0).getValue());
		byte data[] = _serv.getSnapshotData(snapshotID);
		
		if(data == null || data.length == 0){
			ExtMsgBox.showInfo(this, "\u5FEB\u7167\u6570\u636E\u5DF2\u88AB\u5220\u9664");
			return;
		}
		
		String oql = "select number,id,sourceBillId where sourceBillId='"+snapshotID+"'";
    	PhotoAuditCollection photoAuditCollection = PhotoAuditFactory.getRemoteInstance().getPhotoAuditCollection(oql);
    	String billId = photoAuditCollection.size()>0?photoAuditCollection.get(0).getId().toString():null;
    	
    	IUIWindow uiWindow = null;
    	Map uiContext = new UIContext(this);
    	if(billId!=null){
    		uiContext.put("ID", billId);
    		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PhotoAuditEditUI.class.getName(), uiContext, null, OprtState.VIEW);
    		uiWindow.show();
    	}else{
    		uiContext.put("MainMenuName",kDTable1.getCell(rowIndex,2).getValue()); 
    		uiContext.put("SNAPSHOT_DATA", data);
    		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SnapshotRunUI.class.getName(), uiContext, null, OprtState.VIEW);
    		((SnapshotRunUI)uiWindow.getUIObject()).runEXT();
    		uiWindow.show();
    	}
		
	}
	
	
	private void loadTreeForRptName(KDTreeNode mailNode) throws BOSException, SQLException{
		StringBuffer  buf = new StringBuffer();
		BOSUuid userId= SysContext.getSysContext().getCurrentUserInfo().getId();
		if(mailNode.getText().equals("收件箱")){
			buf.append(" select  distinct U.fname_l2,S.Freportid from T_ext_ssshortcutsbox S ");
			buf.append(" left join T_ext_sssnapshot so on so.fid=s.fsnapshotid");
			buf.append(" LEFT OUTER JOIN T_BAS_DefineReport U on S.Freportid = U.Fid WHERE so.fid is not null and S.Faddressee ='"+userId+"' and U.fname_l2 is not null");
		}else if(mailNode.getText().equals("个人文件夹")){
			buf.append("SELECT distinct *");
	        buf.append(" FROM ((Select distinct U.fname_l2,S.Freportid");
	        buf.append(" From T_ext_sssnapshot S ");
	        buf.append(" LEFT OUTER JOIN T_BAS_DefineReport U on S.Freportid = U.Fid");
	        buf.append(" WHERE S.Fcreatorid ='"+userId+"' And S.FlocationType = 0");
	        buf.append(")Union All(");
	        buf.append("Select distinct U.fname_l2,Sc.Freportid");
	        buf.append(" From T_ext_ssshortcut sc ");
	        buf.append(" LEFT OUTER JOIN T_BAS_DefineReport U on sc.FReportid = U.Fid");
	        buf.append(" WHERE sc.fuserid = '"+userId+"' And Sc.FlocationType = 0");
	        buf.append(")) TT where TT.fname_l2 is not null ");
		}else if(mailNode.getText().indexOf("审批")>=0){
			buf.append(" select distinct u.fname_l2,S.Freportid from T_ext_sssnapshot S ");
			buf.append(" LEFT OUTER JOIN T_BAS_DefineReport U on S.Freportid = U.Fid");
			buf.append(" WHERE  S.FlocationType = 1 and u.fname_l2 is not null");
		}
        IRowSet rowset = new FDCSQLBuilder().appendSql(buf.toString()).executeQuery();
        while(rowset.next()){
        	KDTreeNode detail = new KDTreeNode(rowset.getString(1));
        	detail.setUserObject(rowset.getString(2));
        	detail.setCustomIcon(EASResource.getIcon("imgTbtn_stipenddot"));
        	mailNode.add(detail);
        }
	}
    
    protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.kDTree1_valueChanged(e);
    	DefaultKingdeeTreeNode node = getSelectedTreeNode();
    	if(node==null)
    		return;
    	kDTable1.removeRows();
		kDTable1.removeColumns();
		this.actionAddNew.setEnabled(false);
		
    	if(node.getText().equals("收件箱")||(node.getParent()!=null&&node.getParent().toString().equals("收件箱"))){
    		initMailTable();
    		loadMainData((node.getUserObject()!=null&&!isChinese(node.getUserObject().toString()))?node.getUserObject().toString():null);
    	}
    	
    	if(node.getText().equals("个人文件夹")||(node.getParent()!=null&&node.getParent().toString().equals("个人文件夹"))){
    		initPersonTable();
    		loadPersonTableData((node.getUserObject()!=null&&!isChinese(node.getUserObject().toString()))?node.getUserObject().toString():null);
    	}
    	
    	if(node.getText().equals("公共文件夹")||(node.getParent()!=null&&node.getParent().toString().equals("公共文件夹"))||(node.getParent()!=null&&node.getParent().toString().indexOf("审批")>=0)){
    		initCommFolderTable(); 
    		if(node.getText().equals("公共文件夹"))
    			loadCommFolderTableData(null,null);
    		if(node.getText().indexOf("审批")>=0)
    			loadCommFolderTableData(null,node.getText());
    		
    		if(node.getParent()!=null&&node.getParent().toString().indexOf("审批")>=0){
    			loadCommFolderTableData(node.getUserObject().toString(),node.getParent().toString());
    		}
    		
    		this.actionAddNew.setEnabled(true);
    	}
    }
    
    protected void initSearch()
    {
        _tfSearch = new SearchTextField(_niceNote) {

            public void clearAction(SearchTextField srcComp)
            {
                srcComp.setText(null);
            }

            public void searchAction(SearchTextField srcComp)
            {
                filter(srcComp.getText());
            }
        };
        _tfSearch.setSize(new Dimension(300, 20));
        _tfSearch.setFocusTraversalKeysEnabled(false);
    }
    
    protected void filter(String text)
    {
    	if(kDTable1.getColumnCount()<3)
    		return;
    	for (int i = 0; i < kDTable1.getRowCount(); i++) {
    		IRow row = kDTable1.getRow(i);
    		
    		StyleAttributes styleAttributes = row.getStyleAttributes();
    		styleAttributes.setHided(false);
			String name = UIRuleUtil.getString(row.getCell(2).getValue());
			if(name.indexOf(text)<0)
				styleAttributes.setHided(true);
		}
    }
    
	private void loadCommFolderTableData(String string,String type) throws BOSException, SQLException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("Select ");
		sqlBuf.append(" S.Fid as cFid, rpto.Fname_l2 ,s.fname as shotname,aud.CFStatus, S.Fcreatorid as creatorid, S.Fcreatetime as createtime, S.Freportperiod as reportperiod, S.Fweekbegin as weekbegin, S.Fbegindate as begindate, S.Fenddate enddate,");
		sqlBuf.append(" O.fname_l2 as orgname, U.Fname_l2 As Name,S.Freportid as reportid, S.flastupdateuserid As updatorId, U1.fname_l2 as updator, S.FLASTUPDATETIME As updateTime,aud.FCreateTime,creat.fname_l2,aud.fid");
		sqlBuf.append(" From T_ext_sssnapshot S LEFT outer JOIN T_pm_user U On S.Fcreatorid = U.Fid  left outer join T_pm_user U1 on S.flastupdateuserid = U1.fid LEFT OUTER JOIN T_Org_BaseUnit O On S.ForgId = O.fid ");
		sqlBuf.append(" left OUTER join CT_PHO_PhotoAudit aud on aud.FSourceBillID=S.Fid");
		sqlBuf.append(" LEFT OUTER JOIN T_BAS_DefineReport rpto on S.Freportid = rpto.Fid");
		sqlBuf.append(" left outer join T_pm_user creat on creat.fid = aud.FLastUpdateUserID");
		sqlBuf.append(" WHERE S.FlocationType = 1  ");
		if(string!=null){
			sqlBuf.append(" And S.FReportid ='").append(string).append("'");
		}
		if(type!=null){
			if(type.equals("审批中"))
				sqlBuf.append(" and aud.CFStatus='2SUBMITTED'");
			if(type.equals("已审批"))
				sqlBuf.append(" and aud.CFStatus='4AUDITTED'");
			if(type.equals("未审批"))
				sqlBuf.append(" and (aud.CFStatus='1SAVED' or aud.CFStatus is null)");
		}
		sqlBuf.append(" and rpto.Fname_l2 is not null Order by createtime desc");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sqlBuf.toString()).executeQuery();
        while(rowset.next()){
        	IRow addRow = kDTable1.addRow();
        	addRow.getCell(0).setValue(rowset.getString(1));
        	addRow.getCell(1).setValue(rowset.getString(2));
        	addRow.getCell(2).setValue(rowset.getString(3));
        	addRow.getCell(3).setValue(rowset.getString(4)); 
        	addRow.getCell(4).setValue(rowset.getString(12));
        	addRow.getCell(5).setValue(rowset.getString(6));
        	addRow.getCell(6).setValue(rowset.getString(11));
        	addRow.getCell(7).setValue(rowset.getString(18));
        	addRow.getCell(8).setValue(rowset.getString(17));
        	addRow.getCell(9).setValue(rowset.getString(19));
        	
        	if(rowset.getString(3)!=null)
        		addRow.getCell(3).setValue(FDCBillStateEnum.getEnum(rowset.getString(4)));
        }
	}
	
	private void initCommFolderTable() {
        kDTable1.addColumns(10);
        kDTable1.getColumn(0).setWidth(100);
        kDTable1.getColumn(1).setWidth(100);
        kDTable1.getColumn(2).setWidth(400);
        kDTable1.getColumn(3).setWidth(80);
        kDTable1.getColumn(4).setWidth(128);
        kDTable1.getColumn(5).setWidth(128);
        kDTable1.getColumn(0).getStyleAttributes().setHided(true);
        kDTable1.getColumn(9).getStyleAttributes().setHided(true);
        kDTable1.getColumn(1).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(4).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(3).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(2).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(5).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(6).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(7).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(8).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(1).getStyleAttributes().setBold(true);
        kDTable1.getColumn(2).getStyleAttributes().setBold(true);
        tableAppearance();
        IRow header = kDTable1.addHeadRow();
        header.getCell(0).setValue("id");
        header.getCell(1).setValue("报表名称");
        header.getCell(2).setValue("\u540D\u79F0");
        header.getCell(3).setValue("审批状态");
        header.getCell(4).setValue("\u521B\u5EFA\u8005");
        header.getCell(5).setValue("\u521B\u5EFA\u65F6\u95F4");
        header.getCell(6).setValue("\u521B\u5EFA\u7EC4\u7EC7");
        header.getCell(7).setValue("\u4FEE\u6539\u4EBA");
        header.getCell(8).setValue("\u4FEE\u6539\u65F6\u95F4");
        kDTable1.getSelectManager().setSelectMode(10);
        kDTable1.getColumn(5).getStyleAttributes().setNumberFormat("yyyy-MM-dd  HH:mm");
        kDTable1.getColumn(8).getStyleAttributes().setNumberFormat("yyyy-MM-dd  HH:mm");
	}

	private void loadPersonTableData(String rptId) throws BOSException, SQLException{
    	BOSUuid userId= SysContext.getSysContext().getCurrentUserInfo().getId();
    	StringBuffer buf = new StringBuffer();
        buf.append("SELECT pr.fname_l2,TT.*");
        buf.append(" FROM ((Select S.Fid,S.Fname,S.Fcreatorid,S.Fcreatetime,S.Freportperiod,S.Fweekbegin,S.Fbegindate,S.Fenddate,");
        buf.append("U.Fname_l2 As Name, -5 As Type,S.Freportid, '' As Fsnapshotid,S.forgid, O.fname_l2 as orgName, ");
        buf.append(" S.Fcreatetime as sendTime, O.fname_l2 as sender , S.FLASTUPDATETIME As updateTime ");
        buf.append("From T_ext_sssnapshot S Left Outer Join T_pm_user U On S.Fcreatorid = U.Fid Left Outer Join T_Org_BaseUnit O On S.ForgID = O.Fid");
        buf.append(" WHERE S.Fcreatorid = '"+userId+"' And S.FlocationType = 0 ");
        if(rptId!=null)
        	buf.append(" and S.FReportid ='").append(rptId).append("'");
        buf.append(")Union All(");
        buf.append("Select Sc.Fid, Sc.Fname, Sc.Fcreatorid,Sc.Fcollectiontime As Fcreatetime,Sc.Freportperiod,Sc.Fweekbegin,Sc.Fbegindate,Sc.Fenddate,");
        buf.append("U.Fname_l2 As Name, sc.FsourceType As Type,Sc.Freportid,Sc.Fsnapshotid, Sc.forgid,O.fname_l2 as orgName,");
        buf.append(" Sc.Fsendtime as sendTime, U2.fname_l2 as sender, FSendTime as updateTime ");
        buf.append(" From T_ext_ssshortcut Sc Left Outer Join T_pm_user U On Sc.Fcreatorid = U.Fid ");
        buf.append(" Left Outer Join T_Org_BaseUnit O On Sc.ForgID = O.Fid ");
        buf.append(" Left Outer Join T_pm_user U2 On Sc.Fsenderid = U2.Fid ");
        buf.append(" WHERE Sc.fuserid = '"+userId+"'  And Sc.FlocationType = 0 ");
        if(rptId!=null)
        	buf.append(" and Sc.FReportid ='").append(rptId).append("'");
        buf.append(")) TT left join T_BAS_DefineReport pr on pr.fid=TT.Freportid where pr.fid is not null Order By pr.fname_l2,TT.Fcreatetime Desc");
        IRowSet rowset = new FDCSQLBuilder().appendSql(buf.toString()).executeQuery();
        while(rowset.next()){
        	IRow addRow = kDTable1.addRow();
        	addRow.getCell(0).setValue(rowset.getString(2));
        	addRow.getCell(1).setValue(rowset.getString(1));
        	addRow.getCell(2).setValue(rowset.getString(3));
        	addRow.getCell(3).setValue(rowset.getString(10));
        	addRow.getCell(4).setValue(rowset.getString(5));
        	addRow.getCell(7).setValue(rowset.getString(17));
        	addRow.getCell(8).setValue(rowset.getString(15));
        	addRow.getCell(9).setValue(rowset.getString(16));
        	addRow.getCell(10).setValue(rowset.getString(18));
        }
    }
    
    private void initPersonTable()
    {
        kDTable1.addColumns(11);
        kDTable1.getColumn(0).setWidth(100);
        kDTable1.getColumn(1).setWidth(100);
        kDTable1.getColumn(2).setWidth(309);
        kDTable1.getColumn(3).setWidth(128);
        kDTable1.getColumn(4).setWidth(128);
        kDTable1.getColumn(5).setWidth(128);
        kDTable1.getColumn(6).setWidth(128);
        kDTable1.getColumn(7).setWidth(128);
        kDTable1.getColumn(8).setWidth(128);
        kDTable1.getColumn(9).setWidth(128);
        kDTable1.getColumn(10).setWidth(128);
        tableAppearance();
        kDTable1.getColumn(1).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(2).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(6).getStyleAttributes().setHided(true);
        kDTable1.getColumn(7).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(3).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(4).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(5).getStyleAttributes().setHided(true);
        kDTable1.getColumn(8).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(9).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(10).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(0).getStyleAttributes().setHided(true);
        kDTable1.getColumn(1).getStyleAttributes().setBold(true);
        kDTable1.getColumn(2).getStyleAttributes().setBold(true);
        IRow header = kDTable1.addHeadRow();
        header.getCell(0).setValue("id");
        header.getCell(1).setValue("报表名称");
        header.getCell(2).setValue("\u540D\u79F0");
        header.getCell(3).setValue("\u521B\u5EFA\u8005");
        header.getCell(4).setValue("\u521B\u5EFA\u65F6\u95F4");
        header.getCell(5).setValue("\u6C47\u603B\u7C7B\u578B");
        header.getCell(6).setValue("\u6C47\u603B\u671F\u95F4\u63CF\u8FF0");
        header.getCell(7).setValue("\u53D1\u9001\u8005");
        header.getCell(8).setValue("\u521B\u5EFA\u7EC4\u7EC7");
        header.getCell(9).setValue("\u53D1\u9001\u65F6\u95F4");
        header.getCell(10).setValue("\u4FEE\u6539\u65F6\u95F4");
        kDTable1.getSelectManager().setSelectMode(10);
        kDTable1.getColumn(4).getStyleAttributes().setNumberFormat("yyyy-MM-dd  HH:mm");
        kDTable1.getColumn(7).getStyleAttributes().setNumberFormat("yyyy-MM-dd  HH:mm");
        kDTable1.getColumn(10).getStyleAttributes().setNumberFormat("yyyy-MM-dd  HH:mm");
    }

    
    private void loadMainData(String rptId) throws BOSException, SQLException{
    	StringBuffer  buf = new StringBuffer();
        buf.append(" select S.fsnapshotid,U.fname_l2,S.Fname,US.Fname_l2,S.FSharetime As UserName from T_ext_ssshortcutsbox S");
        buf.append(" LEFT OUTER JOIN T_BAS_DefineReport U on S.Freportid = U.Fid ");//
        buf.append(" left join T_ext_sssnapshot so on so.fid=s.fsnapshotid");
        buf.append(" LEFT OUTER JOIN T_pm_user US On S.FsenderID = US.Fid WHERE so.fid is not null and S.Faddressee ='"+SysContext.getSysContext().getCurrentUserInfo().getId()+"' and U.fname_l2 is not null");
        if(rptId!=null)
        	buf.append(" and S.Freportid='").append(rptId).append("'");
        buf.append(" order by U.fname_l2,S.FSharetime DESC");
        IRowSet rowset = new FDCSQLBuilder().appendSql(buf.toString()).executeQuery();
        while(rowset.next()){
        	IRow addRow = kDTable1.addRow();
        	addRow.getCell(0).setValue(rowset.getString(1));
        	addRow.getCell(1).setValue(rowset.getString(2));
        	addRow.getCell(2).setValue(rowset.getString(3));
        	addRow.getCell(3).setValue(rowset.getString(4));
        	addRow.getCell(4).setValue(rowset.getString(5));
        }
        kDTable1.setRowCount(rowset.size());
    }
    
	public DefaultKingdeeTreeNode getSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
	}
	
	private void initMailTable(){
		kDTable1.addColumns(5);
        kDTable1.getColumn(0).setWidth(100);
        kDTable1.getColumn(1).setWidth(100);
        kDTable1.getColumn(2).setWidth(600);
        kDTable1.getColumn(3).setWidth(128);
        kDTable1.getColumn(4).setWidth(128);
        
        IRow header = kDTable1.addHeadRow();
        header.getCell(0).setValue("ID");
        header.getCell(1).setValue("报表名称");
        header.getCell(2).setValue("\u540D\u79F0");
        header.getCell(3).setValue("\u53D1\u9001\u8005");
        header.getCell(4).setValue("\u53D1\u9001\u65F6\u95F4");
        tableAppearance();
        kDTable1.getColumn(0).getStyleAttributes().setHided(true);
        kDTable1.getColumn(1).getStyleAttributes().setBold(true);
        kDTable1.getColumn(2).getStyleAttributes().setBold(true);
        kDTable1.getColumn(1).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(2).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(3).getStyleAttributes().setLocked(true);
        kDTable1.getColumn(4).getStyleAttributes().setNumberFormat("yyyy-MM-dd  HH:mm");
	}

    protected void tableAppearance()
    {
    	kDTable1.getStyleAttributes().setBorderColor(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.TOP, MViewUIConfig.BODY_LINE_BORDER);
    	kDTable1.getStyleAttributes().setBorderColor(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.RIGHT, MViewUIConfig.BODY_LINE_BORDER);
    	kDTable1.getStyleAttributes().setBorderColor(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.BOTTOM, MViewUIConfig.BODY_LINE_BORDER);
    	kDTable1.getStyleAttributes().setBorderColor(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.LEFT, MViewUIConfig.BODY_LINE_BORDER);
    	kDTable1.getStyleAttributes().setBorderLineStyle(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.TOP, LineStyle.SINGLE_LINE);
    	kDTable1.getStyleAttributes().setBorderLineStyle(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.RIGHT, LineStyle.SINGLE_LINE);
    	kDTable1.getStyleAttributes().setBorderLineStyle(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.BOTTOM, LineStyle.SINGLE_LINE);
    	kDTable1.getStyleAttributes().setBorderLineStyle(com.kingdee.bos.ctrl.kdf.util.style.Styles.Position.LEFT, LineStyle.SINGLE_LINE);
    	kDTable1.getStyleAttributes().setFont(MViewUIConfig.Song_Ti);
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAddNew_actionPerformed(e);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {//提前过滤界面
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			
			int selectIndex = this.kDTable1.getSelectManager().getActiveRowIndex();
			if(selectIndex==-1)
			{
				MsgBox.showWarning("请先选择要发起流程的快照！");
				SysUtil.abort();
			}
			uiContext.put("rptId", this.kDTable1.getCell(selectIndex, 0).getValue().toString());
			uiContext.put("rptName", this.kDTable1.getCell(selectIndex, 2).getValue().toString());
		}
    }
    
    protected String getEditUIModal() {
    	return "com.kingdee.eas.base.uiframe.client.UINewTabFactory";
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected String getEditUIName() {
    	return super.getEditUIName();
    }
    
	public boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i<ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
	
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.photomanager.PhotoAuditFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.photomanager.PhotoAuditInfo objectValue = new com.kingdee.eas.fdc.photomanager.PhotoAuditInfo();
		
        return objectValue;
    }

}