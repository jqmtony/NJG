/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.Rectangle;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.TreeUI;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.util.ControlUtilities;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.uiframe.client.MainMenuLeaf;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICtrlUnit;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionFactory;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.costdb.MarketInfoFactory;
import com.kingdee.eas.fdc.costdb.MarketInfoInfo;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class MarketInfoEditUI extends AbstractMarketInfoEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketInfoEditUI.class);
    
	private OrgUnitInfo currentOrg = SysContext.getSysContext()
		.getCurrentOrgUnit();
	private AbstractObjectValue old = null;
	
	//获取有权限的组织
	protected Set authorizedOrgs = null;
	
	private FileGetter fg;
	//保存时删除的附件id
	RealModeIDList idList = new RealModeIDList();
	//新上传的附件id
	RealModeIDList idListAdd = new RealModeIDList();
	//附件
	private boolean isAttachChange = false;
	//本组织所在CU
	private Set upCU = new HashSet();
    /**
     * output class constructor
     */
    public MarketInfoEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }



    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	verifyInput(e);
    	//更新修改时间
    	this.pkLastUpdateDate.setValue(new Timestamp(new Date().getTime()));
        super.actionSubmit_actionPerformed(e);
        if(idList.size() != 0){
        	removeAttachment(editData.getId().toString(),idList);
        }
        
        isAttachChange = false;
    }
   
    public boolean checkBeforeWindowClosing() {
    	if (hasWorkThreadRunning()) {
			return false;
		}    	
        String boID = editData.getId().toString();

            //校验storeFields()是否抛出异常
//            Exception err = verifyStoreFields();

            //storeFields()抛出异常或者editdata有改变，询问是否保存退出
            if (isModify()) {
//                editdata有改变
                int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Save_Exit"));

                if (result == KDOptionPane.YES_OPTION) {

                        try {
                            if(!isModifySave()||!actionSave.isEnabled())
                            {
                                actionSubmit.setDaemonRun(false);
                                //actionSubmit.actionPerformed(null);
                                //by jakcy 2005-1-6 会出空指针错，因为beforeAction()会使用ActionEvent。
                                ActionEvent event = new ActionEvent(btnSubmit ,
                                    ActionEvent.ACTION_PERFORMED ,
                                    btnSubmit.getActionCommand());
                                actionSubmit.actionPerformed(event);
                                return !actionSubmit.isInvokeFailed();
    //                            actionSubmit_actionPerformed(event);
                            }
                            else
                            {
                                actionSave.setDaemonRun(false);
                                ActionEvent event = new ActionEvent(btnSave ,
                                    ActionEvent.ACTION_PERFORMED ,
                                    btnSave.getActionCommand());
                                actionSave.actionPerformed(event);
                                return !actionSave.isInvokeFailed();
                            }

                            //return true;
                        } catch (Exception exc) {
//                            handUIException(exc);
                            return false;
                        }

                }else if (result == KDOptionPane.NO_OPTION) {
                    //stopTempSave();
                	try {
						this.removeAttachment(boID,idListAdd);
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
                    return true;
                } else {
                    return false;
                }
            }else{
                //stopTempSave();
                return true;
            }
    }

      /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
//        handleCodingRule();
//		Map map = this.getUIContext();
//		//如果上下文中包含类型信息，则设置类型
//		if(map != null && map.containsKey("type")){
//			Object object = map.get("type");
//			if(object instanceof MarketInfoTypeInfo){
//				MarketInfoTypeInfo type = (MarketInfoTypeInfo)object;
//				this.prmtType.setValue(type);
//			}
//		}
//		this.txtAttachment.setEnabled(false);
//		this.actionAttachment.setEnabled(true);
//		this.actionAttachment.setVisible(true);
        //点新增按钮时删除所有节点数据
        removeAllAttch();
		this.actionRemove.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.setUITitle("文件 - 新增");
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        this.setUITitle("文件 - 编辑");
        this.txtOrgUnit.setEnabled(false);
        this.pkCreateTime.setEnabled(false);
        this.pkLastUpdateDate.setEnabled(false);
        this.btnAttchment.setEnabled(true);
        this.actionRemoveAttachment.setEnabled(true);
        this.btnAttchment.setVisible(true);
        this.actionAttachment.setVisible(true);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	//删除时同时删除相应附件
    	String boID = editData.getId().toString();
        super.actionRemove_actionPerformed(e);
        this.removeAllAttach(boID);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
        
//        String attch = getAttachmentName(editData.getId().toString());
//        this.txtAttachment.setText(attch);
        
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		MarketInfoInfo objectValue = new MarketInfoInfo();
		//初始化部分数据
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(new Date().getTime()));
		objectValue.setLastUpdateTime(new Timestamp(new Date().getTime()));
		objectValue.setOrg(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
//		oldData = objectValue;
		objectValue.setDescription("");
		objectValue.setName("");
		objectValue.setPersonName("");
		objectValue.setPersonOrg("");
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketInfoFactory.getRemoteInstance();
	}


	public void onLoad() throws Exception {
//		if(!MarketHelper.checkOrgPerm(this,this.currentOrg.getId().toString()))
//			this.abort();
		getCtrlOrg();
		super.onLoad();
		//初始化部分控件
		//获得上下文
		Map map = this.getUIContext();
		//如果上下文中包含类型信息，则设置类型
		if(map != null && map.containsKey("type")){
			Object object = map.get("type");
			if(object instanceof MarketInfoTypeInfo){
				MarketInfoTypeInfo type = (MarketInfoTypeInfo)object;
				this.prmtType.setValue(type);
			}
		}

		//设置附件名称
//		if(editData.getId()!=null){
//	        String attch = getAttachmentName(editData.getId().toString());
////	        this.txtAttachment.setText(attch);
//		}
//		handleCodingRule();
//		editData.setNumber(this.txtNumber.getText().toString());
//		this.getNumberByCodingRule(editData,currentOrg.getId().toString());
		//作者和其所在部门 既可以选择获得 又可以手工录入
		ExtendParser parserPerson = new ExtendParser(prmtPerson);
		prmtPerson.setCommitParser(parserPerson);
		ExtendParser parserOrg = new ExtendParser(prmtPosition);
		prmtPosition.setCommitParser(parserOrg);
		this.btnSave.setVisible(false);
		this.btnSave.setEnabled(false);
		this.btnPageSetup.setVisible(false);
		this.btnPageSetup.setEnabled(false);
//		EntityViewInfo view = this.prmtPerson.getEntityViewInfo();
//		FilterInfo filter = null;
//		EntityViewInfo viewInfo = this.prmtPosition.getEntityViewInfo();
//		FilterInfo filterPosition = null;
		FullOrgUnitInfo fullOrg = this.currentOrg.castToFullOrgUnitInfo();
		getUpCU(fullOrg);
    	String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
    	//获得当前组织的所有下级组织的ids
    	Set childLongNumberSet = new HashSet();
    	try {
    		childLongNumberSet = getDownOrgIdSet(longNumber);
    	} catch (BOSException e) {
    		e.printStackTrace();
    	}
    	childLongNumberSet.add(this.currentOrg.getId().toString());
		//对市场资料类型进行过滤
		EntityViewInfo typeView = this.prmtType.getEntityViewInfo();
		FilterInfo typeFilter = new FilterInfo();
		typeView = new EntityViewInfo();
		//typeFilter = new FilterInfo();
        //所有下级CU建立的类型
		typeFilter.getFilterItems().add(
                new FilterItemInfo("org.id", childLongNumberSet, CompareType.INCLUDE));
    	//用户组织权限隔离
		typeFilter.getFilterItems().add(
				new FilterItemInfo("org.id",authorizedOrgs,CompareType.INCLUDE));

        //集团建立的类型 并且是系统预设类型
		typeFilter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.SYS_CU_ID, CompareType.EQUALS));
		typeFilter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.DEF_CU_ID, CompareType.EQUALS));
		typeFilter.setMaskString("(#0 and #1) or #2 or #3");
		//直接上级CU建立的类型
		if(upCU.size() != 0){
			typeFilter.getFilterItems().add(
	                new FilterItemInfo("org.id", upCU, CompareType.INCLUDE));
			typeFilter.setMaskString("(#0 and #1) or #2 or #3 or #4");
		}
		typeView.setFilter(typeFilter);
		this.prmtType.setEntityViewInfo(typeView);
//		if(view !=null ){
//			filter = view.getFilter();
//		}else{
//			view = new EntityViewInfo();
//			filter = new FilterInfo();
//		}
//		if(viewInfo != null){
//			filterPosition = viewInfo.getFilter();
//		}else{
//			viewInfo =  new EntityViewInfo();
//			filterPosition = new FilterInfo();
//		}
//		filter.getFilterItems().add(
//				new FilterItemInfo("type.id",SysContext.getSysContext().getCurrentOrgUnit().getId()));
//		view.setFilter(filter);
//		filterPosition.getFilterItems().add(
//				new FilterItemInfo("type.id",SysContext.getSysContext().getCurrentOrgUnit().getId()));
//		
//		viewInfo.setFilter(filterPosition);
		//过滤F7控件查询
//		this.prmtPerson.setEntityViewInfo(view);
//		this.prmtPosition.setEntityViewInfo(viewInfo);
		
		//为prmtPerson添加值变化监听器
		this.prmtPerson.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				try {
					prmtPerson_dataChanged(eventObj);
				} catch (Exception e) {
					handUIException(e);
				}
			}
			
		});
//		this.txtAttachment.setEditable(false);
		initCtrl();
//		this.prmtPerson.setText(editData.getPersonName());
//		this.prmtPosition.setText(editData.getPersonOrg());
//		AbstractObjectValue old = new MarketInfoInfo();
		
		setNumberTextEnabled();
		
		//获得对应所有附件，加入treeAttachment
		List list = this.getAllAttachment(editData.getId().toString());
		insertAttachment(list);
		
	}
	/**
	 * 初始化部分控件
	 *
	 */
	private void initCtrl(){
		this.txtOrgUnit.setEditable(false);
		this.btnRemoveAttachment.setText("");
		this.btnViewContent.setText("");
		this.toolBar.remove(this.btnRemoveAttachment);
		this.toolBar.remove(this.btnViewContent);
//		this.actionAttachment.setEnabled(true);
//		this.actionAttachment.setVisible(true);
		this.menuView.setVisible(false);
		this.menuView.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionFirst.setEnabled(false);
		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);
		this.actionPre.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionNext.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.txtDescription.setMaxLength(1000);
		KDTreeNode root = new KDTreeNode("");
		treeAttachment.setModel(new KingdeeTreeModel(root));
		treeAttachment.expandAllNodes(true, (TreeNode) treeAttachment
				.getModel().getRoot());
		this.treeAttachment.setRootVisible(false);		
		this.treeAttachment.setRowHeight(20);
		this.actionViewContent.setEnabled(true);
		this.kDTreeView.setShowButton(false);
		this.kDTreeView.setShowControlPanel(true);
		this.btnRemoveAttachment.setIcon(EASResource.getIcon("imgTree_delete"));
		this.kDTreeView.getControlPane().add(btnViewContent);
		this.kDTreeView.getControlPane().add(btnRemoveAttachment);
		//非本组织下的信息不允许修改删除
		String orgId = "";
		String currentOrgId = currentOrg.getId().toString();
		if(editData.getOrg()!=null){
			orgId = editData.getOrg().getId().toString();
		}
		if(orgId.equals(currentOrgId)){
			this.actionRemove.setEnabled(true);
			this.actionEdit.setEnabled(true);
//			this.actionAttachment.setEnabled(true);
//			this.actionAddNew.setEnabled(true);
		}else{
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
//			this.actionAttachment.setEnabled(false);
//			this.actionAddNew.setEnabled(false);
		}
		if(this.getOprtState()=="ADDNEW"){
			this.setUITitle("文件 - 新增");
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if(this.getOprtState()=="VIEW"){
			this.setUITitle("文件 - 查看");
			this.actionRemove.setEnabled(false);
			this.btnAttchment.setEnabled(false);
			this.actionRemoveAttachment.setEnabled(false);
		}
		if(this.getOprtState()=="EDIT"){
			this.setUITitle("文件 - 编辑");
			this.actionEdit.setEnabled(false);
//			this.actionAttachment.setEnabled(true);
			this.btnAttchment.setEnabled(true);
			this.actionRemoveAttachment.setEnabled(true);
		}
	}
	//prmtPerson控件值变化监听  作者变化监听
	protected void prmtPerson_dataChanged(DataChangeEvent e) throws Exception{
		String id = null;
		if(e.getNewValue()!=null && e.getNewValue() instanceof PersonInfo){
			AdminOrgUnitInfo adminOrg = null;
			PersonInfo person = (PersonInfo)e.getNewValue();
			if(person!=null){
				id = person.getId().toString();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.getSelector().add("*");
				filter.getFilterItems().add(new FilterItemInfo("person.id",id));
				view.setFilter(filter);
				PositionMemberCollection coll = PositionMemberFactory
					.getRemoteInstance().getPositionMemberCollection(view);
				for(int i = 0; i < coll.size();i++){
					PositionMemberInfo positionMember = coll.get(i);
					if(positionMember.isIsPrimary()){
						if(positionMember.getPosition() != null){
							String positionId = positionMember.getPosition().getId().toString();
							PositionInfo position = PositionFactory.getRemoteInstance()
								.getPositionInfo(new ObjectUuidPK(BOSUuid.read(positionId)));
							if(position.getAdminOrgUnit()!=null){
								String orgId = position.getAdminOrgUnit().getId().toString();
								adminOrg = AdminOrgUnitFactory.getRemoteInstance()
									.getAdminOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(orgId)));
							}
						}						
					}
				}
				this.prmtPosition.setValue(adminOrg);
				
			}

		}else{
			this.prmtPosition.setValue(null);
		}
	}
	//重载父类getSelectors()方法，实现F7控件既可选择获得，又可手工录入
	//同时数据库设计时，应把原有的连接属性改为本身所要显示的字段属性
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("personName"));
        sic.add(new SelectorItemInfo("personOrg"));
        sic.add(new SelectorItemInfo("type.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("attchment"));
        sic.add(new SelectorItemInfo("org.*"));
        sic.add(new SelectorItemInfo("isGroupFile"));
        return sic;
    }  
	protected  void fetchInitData() throws Exception{
		
	}

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}
	//获得编码项
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	//必录项和字数显示校验
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(this.txtOrgUnit.getText()==null || "".equals(this.txtOrgUnit.getText())){
			MsgBox.showWarning(this,"组织不能为空！");
			SysUtil.abort();
		}
		if((this.txtNumber.getText()==null || "".equals(this.txtNumber.getText()))
				&& this.txtNumber.isEnabled()){
			MsgBox.showWarning(this,"资料编号不能为空！");
			SysUtil.abort();
		}
		if(this.txtName.getText()==null || "".equals(this.txtName.getText())) {
			MsgBox.showWarning(this,"资料名称不能为空！");
			SysUtil.abort();
		}
		if(this.prmtType.getValue()==null){
			MsgBox.showWarning(this,"资料类型不能为空！");
			SysUtil.abort();
		}
		//简介只能500字
		String description = txtDescription.getText();
		if(description.toCharArray().length>500){
			MsgBox.showWarning(this,"资料简介不能超过500字！");
			SysUtil.abort();
		}
		//附件必录
		if(this.treeAttachment.getRowCount()==0){
			MsgBox.showWarning(this,"附件不能为空！");
			SysUtil.abort();
		}
		FilterInfo filterInfo = new FilterInfo();
//		filterInfo
//				.appendFilterItem("number", this.txtNumber.getText().trim());
		if(this.txtNumber.getText() != null && !this.txtNumber.getText().equals("")){
			filterInfo.getFilterItems().add(new FilterItemInfo("number",this.txtNumber.getText().trim()));
			if(editData.getId()!=null)
				filterInfo.getFilterItems().add(
						new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
			//编码重复验证
			if (getBizInterface().exists(filterInfo)) {
//				throw new FDCInviteException(
//						FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
				MsgBox.showWarning(this,"资料编号不能重复！");
				SysUtil.abort();
			}
		}

		filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("name",this.txtName.getText().trim()));
		if(editData.getId()!=null)
			filterInfo.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		if (getBizInterface().exists(filterInfo)) {
			MsgBox.showWarning(this,"资料名称不能重复！");
			SysUtil.abort();
		}
	}
	/**
	 * 获得当前单据对应的附件名 多个附件时用“|”隔开
	 * @param id 当前单据id
	 * @return
	 * @throws Exception
	 */
	protected String getAttachmentName(String id) throws Exception{
        String attch = "";
        //如果有多个附件时，用“|”隔开。
//        if(!"".equals(this.txtAttachment.getText())){
//        	attch+=" | ";
//        }
        //获得当前id对应的附件
//        String id = editData.getId().toString();
        if(id != null){
        	EntityViewInfo viewAttch = new EntityViewInfo();
        	viewAttch.getSelector().add("*");
        	viewAttch.getSelector().add("attachment.*");
        	FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(
        			new FilterItemInfo("boID",id));
        	viewAttch.setFilter(filter);
            BoAttchAssoCollection coll = BoAttchAssoFactory
        		.getRemoteInstance().getBoAttchAssoCollection(viewAttch);
            if(coll != null ){
            	for(int i =0;i<coll.size();i++){
            		BoAttchAssoInfo info = coll.get(i);
            		if(info.getAttachment()!=null){
            			attch +=info.getAttachment().getName()+"."+info.getAttachment().getSimpleName();
            			//如果有多个附件时，用“|”隔开。
            			if(!"".equals(attch)){
            				attch+=" | ";
            			}
            		}
            	}
            }
        }
        return attch;
	}
	/**
	 * 处理编码规则
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {


		//String currentOrgId = FDCClientHelper.getCurrentOrgId();
		if(editData.getOrg() == null){
			return;
		}
		String currentOrgId = editData.getOrg().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回
		 * 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRule只提供了获取编码的功能，没有提供设置到控件的功能，实现此方法将根据编码规则的"是否新增显示"属性设置编码到控件
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		editData.setNumber(number);
		getNumberCtrl().setText(number);
	}

	public void loadFields() {
		String orgId = "";
		String currentOrgId = currentOrg.getId().toString();
		if(editData.getOrg()!=null){
			orgId = editData.getOrg().getId().toString();
		}
		if(orgId.equals(currentOrgId)){
			this.actionRemove.setEnabled(true);
			this.actionEdit.setEnabled(true);
//			this.actionAttachment.setEnabled(true);
		}else{
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
//			this.actionAttachment.setEnabled(false);
		}
//		String temp = "";
//		try {
//			temp = getAttachmentName(editData.getId().toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.txtAttachment.setText(temp);
		super.loadFields();
//		this.actionAttachment.setEnabled(true);
//		this.actionAttachment.setVisible(true);
		if(STATUS_ADDNEW.equals(this.getOprtState())){
	        try {
				handleCodingRule();
			} catch (CodingRuleException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			Map map = this.getUIContext();
			//如果上下文中包含类型信息，则设置类型
			if(map != null && map.containsKey("type")){
				Object object = map.get("type");
				if(object instanceof MarketInfoTypeInfo){
					MarketInfoTypeInfo type = (MarketInfoTypeInfo)object;
					this.prmtType.setValue(type);
				}
			}
			editData.setNumber(this.txtNumber.getText());
			editData.setType((MarketInfoTypeInfo)this.prmtType.getValue());
			old = (MarketInfoInfo)editData.clone();
//			this.txtAttachment.setEnabled(false);
//			this.actionAttachment.setEnabled(true);
//			this.actionAttachment.setVisible(true);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.removeAllAttch();
		}
		if(STATUS_VIEW.equals(this.getOprtState())){
			List list;
			try {
				list = this.getAllAttachment(editData.getId().toString());
				this.insertAttachment(list);
			} catch (BOSException e) {
				e.printStackTrace();
			}

		}
	}
	protected void setNumberTextEnabled() {

		if(getNumberCtrl() != null) {
			boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, this.currentOrg.getId().toString());
			getNumberCtrl().setEnabled(isAllowModify);
			getNumberCtrl().setEditable(isAllowModify);

		}
	}
	public boolean isModify() {

		try {
		ControlUtilities.checkFocusAndCommit();
		} catch (ParseException e) {
			handleControlException();
			abort();
		}

		if (OprtState.VIEW.equals(getOprtState())) {
			return false;
		}
		//如果附件发生变化
		if(isAttachChange){
			return true;
		}
		try {
			storeFields();
		} catch (Exception exc) {
			return false;
		}
		if(STATUS_ADDNEW.equals(getOprtState())){
			 return !ObjectValueForEditUIUtil.objectValueEquals(old,
					 editData);
		}

//		return false;
		return super.isModify();
	}
	//上传附件
	public void actionUploadAttachment_actionPerformed(ActionEvent e) throws Exception {
		String id = "";
		if(editData.getId() != null){
			id = editData.getId().toString();
		}
		List before = getAllAttachment(id);
		AttachmentUIContextInfo attachmentUIContext = new AttachmentUIContextInfo();
		attachmentUIContext.setBoID(id);
		attachmentUIContext.setCode("");
        AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
        attachmentClientManager.showUploadFilesUI(this,attachmentUIContext);
        List list = getAllAttachment(id);
        if((list!= null && before == null && list.size() >0) 
        		|| (before != null && list != null && list.size() > before.size())){
        	insertAttachment(list);
        }        
        if(before != null && before.size() != 0 && list.size() != 0 && list.size() > before.size() 
        		&& list.get(list.size()-1)!=null 
        		&& list.get(list.size()-1) instanceof AttachmentInfo){
        	int add = list.size() - before.size();
        	for(int i = add;i > 0;i--){
            	AttachmentInfo info = (AttachmentInfo)list.get(list.size()-i);
            	idListAdd.add(info.getId().toString());
            	
        	}
        	isAttachChange = true;
        }else if(before == null && list != null && list.size() > 0){
        	for(int i = 0; i < list.size();i++){
            	AttachmentInfo info = (AttachmentInfo)list.get(i);
            	idListAdd.add(info.getId().toString());
        	}
        	isAttachChange = true;
        }
	}

	public void actionRemoveAttachment_actionPerformed(ActionEvent e) throws Exception {
		//把需要删除的附件id保存到idList中，保存时删除。
		checkSelectedAttachment();		
//		String id = this.getSelectedAttachmentId();
//		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) this.treeAttachment
//			.getLastSelectedPathComponent();
		//确认删除附件(只删除节点数据，而未真删除数据库中数据，保存时真删除。)
        if(confirmRemove()) {
    		TreePath[] tps = this.treeAttachment.getSelectionPaths();
    		if (tps == null)
    			return;
    		for (int i = 0; i < tps.length; i++) {
    			TreePath tp = tps[i];
    			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tp
    					.getLastPathComponent();
    			while (node.getParent() != null
    					&& node.getParent().getChildCount() == 1
    					&& node.getParent().getParent() != null) {
    				tp = tp.getParentPath();
    				node = (DefaultKingdeeTreeNode) tp.getLastPathComponent();
    			}
    			if (node.getParent() != null
    					&& node.getParent().getChildCount() == 1
    					&& node.getParent().getParent() != null)
    				node = (DefaultKingdeeTreeNode) node.getParent();

    			((KingdeeTreeModel) treeAttachment.getModel())
    					.removeNodeFromParent(node);// .nodeStructureChanged(parent);
    			AttachmentInfo info ;
    			if(node.getUserObject() instanceof AttachmentInfo){
    				info = (AttachmentInfo)node.getUserObject();
    				idList.add(info.getId().toString());
    				isAttachChange = true;
    			}    			
    		}
        	
        }
//        List list = getAllAttachment(editData.getId().toString());
//        insertAttachment(list);
	}
	protected void checkSelectedAttachment()
    {
        if (this.treeAttachment.getRowCount() == 0 )
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) this.treeAttachment
			.getLastSelectedPathComponent();
		if(selectNode == null){
			MsgBox.showWarning(this,"请选择附件！");
			SysUtil.abort();
		}
    }
    //获得选中附件id
    protected String getSelectedAttachmentId(){    	
		String attachmentId = "";
		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) this.treeAttachment
			.getLastSelectedPathComponent();
		if(selectNode == null){
			MsgBox.showWarning(this,"请选择附件！");
			SysUtil.abort();
		}
		if(selectNode != null && selectNode.getUserObject() instanceof AttachmentInfo){
			AttachmentInfo attachmentInfo = (AttachmentInfo)selectNode.getUserObject();
			attachmentId = attachmentInfo.getId().toString();
		}
		return attachmentId;
	}
	protected List getAllAttachment(String id) throws BOSException{
		List list = new ArrayList();
		if(id != null){
	        EntityViewInfo viewAttch = new EntityViewInfo();
	        viewAttch.getSelector().add("*");
	        viewAttch.getSelector().add("attachment.*");
	        FilterInfo filter = new FilterInfo();
	        filter.getFilterItems().add(
	        	new FilterItemInfo("boID",id));
	        viewAttch.setFilter(filter);
	        viewAttch.getSorter().add(new SorterItemInfo("attachment.createTime"));
	        BoAttchAssoCollection coll = BoAttchAssoFactory
	        	.getRemoteInstance().getBoAttchAssoCollection(viewAttch);
	        if(coll == null || coll.size() ==0){
	        	return null;
	        }else{
	        	for(int i =0;i<coll.size();i++){
		            BoAttchAssoInfo info = coll.get(i);
		            if(info != null && info.getAttachment()!=null){
		            	list.add(info.getAttachment());
		            }
		        }
	        }
		}
		return list;
	}
	public void addNode(AttachmentInfo attch,DefaultKingdeeTreeNode root){		
		DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode(attch,
                null, false, false);
		String displayName = attch.getName();
		if(!attch.getSimpleName().equals("")){
			displayName += "."+attch.getSimpleName();
		}
		node.setText(displayName);
		((KingdeeTreeModel) treeAttachment.getModel()).insertNodeInto(
				node, root, ((KingdeeTreeModel) treeAttachment.getModel()).getChildCount(root));
		
	}
	//只删除节点数据 而不删除真正的数据
	public void removeAllAttch(){
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeAttachment
			.getModel().getRoot();
		this.treeAttachment.removeAllChildrenFromParent(node);
	}
	protected void insertAttachment(List list){
		//先删除所有叶子节点
		removeAllAttch();
		//把所有子节点加上去
		if(list == null){
			return;
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) ((KingdeeTreeModel) this.treeAttachment
				.getModel()).getRoot();
		for(int i = 0;i < list.size();i++){
			//取当前资料的所有附件，写入treeAttachment中
			if(list.get(i) instanceof AttachmentInfo){
				AttachmentInfo info = (AttachmentInfo)list.get(i);
				addNode(info,root);
			}

		}  
		treeAttachment.expandAllNodes(true, (TreeNode) treeAttachment
				.getModel().getRoot());
	}

	protected void treeAttach_keyPressed(KeyEvent e) throws Exception {
		//键盘事件 按DEL 或 DELETE键 删除附件
		if(e.getSource()==this.treeAttachment 
				&& (e.getKeyCode() == KeyEvent.VK_DECIMAL ||e.getKeyCode() == KeyEvent.VK_DELETE)){
			checkSelectedAttachment();
	        if(confirmRemove()) {
	    		TreePath[] tps = this.treeAttachment.getSelectionPaths();
	    		if (tps == null)
	    			return;
	    		for (int i = 0; i < tps.length; i++) {
	    			TreePath tp = tps[i];
	    			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tp
	    					.getLastPathComponent();
	    			while (node.getParent() != null
	    					&& node.getParent().getChildCount() == 1
	    					&& node.getParent().getParent() != null) {
	    				tp = tp.getParentPath();
	    				node = (DefaultKingdeeTreeNode) tp.getLastPathComponent();
	    			}
	    			if (node.getParent() != null
	    					&& node.getParent().getChildCount() == 1
	    					&& node.getParent().getParent() != null)
	    				node = (DefaultKingdeeTreeNode) node.getParent();

	    			((KingdeeTreeModel) treeAttachment.getModel())
	    					.removeNodeFromParent(node);// .nodeStructureChanged(parent);
	    			AttachmentInfo info ;
	    			if(node.getUserObject() instanceof AttachmentInfo){
	    				info = (AttachmentInfo)node.getUserObject();
	    				idList.add(info.getId().toString());
	    				isAttachChange = true;
	    			}    			
	    		}
	        	
	        }
		}

	}
	//双击直接打开附件内容
	protected void treeAttach_mouseClicked(MouseEvent e) throws Exception {
		//如果鼠标在最后一行的下面，则不处理
		if (getClosestPath(e.getX(), e.getY()) == null
				|| this.treeAttachment.getPathForLocation(e.getX(), e.getY()) == null) {
			return;
		}
		String selectAttachId = "";
		if (e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			if (this.treeAttachment.getLastSelectedPathComponent() instanceof DefaultKingdeeTreeNode) {
				selectAttachId = this.getSelectedAttachmentId();
				viewAttachmentContent(selectAttachId);
			}
		}
	}
	/**
	 * <br>
	 * 点击最后一行以下部分，getClosestPathForLocation认为是最后一行</br><br>
	 * 现在返回为null</br>
	 * 
	 * @see javax.swing.JTree#getClosestPathForLocation(int, int)
	 */
	public TreePath getClosestPath(int x, int y)
	{
		TreeUI ui = this.treeAttachment.getUI();
		if (ui != null)
		{
			TreePath closestPath = ui.getClosestPathForLocation(this.treeAttachment, x, y);
			if (closestPath == null)
			{
				return null;
			}

			Rectangle closestPathRect = this.treeAttachment.getPathBounds(closestPath);

			// 鼠标点击位置超过最后一行，算没选上。
			// 注：若点击最后一行以下部分，closestPath认为是最后一行。
			if (y > closestPathRect.y + closestPathRect.height)
			{
				return null;
			}
			else
			{
				return closestPath;
			}
		}
		return null;
	}
	/**
	 * 打开附件
	 * @param attachmentId 附件ID
	 * @throws Exception
	 */
	private void viewAttachmentContent(String attachmentId) throws Exception{
		if(attachmentId.equals("")){
			return;
		}
		getFileGetter().viewAttachment(attachmentId,this);
	}
    private FileGetter getFileGetter() throws Exception {
        if(fg==null) fg=new FileGetter((IAttachment)AttachmentFactory.getRemoteInstance(),AttachmentFtpFacadeFactory.getRemoteInstance());
        return fg;
    }
    
    /**
     * 删除确认对话框的默认实现，继承类可以重载
     *
     * @return
     */
    protected boolean confirmRemove()
    {
        if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FrameWorkClientUtils.strResource
                + "Confirm_Delete"))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * 删除附件
     * @param boID 对象关联ID 
     * @param idList 附件ids
     * @throws EASBizException
     * @throws BOSException
     */
    protected void removeAttachment(String boID,IIDList idList) throws EASBizException, BOSException {
        AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
        attachmentClientManager.deleteAssoAttachment(boID,idList);
    }

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		removeAttachment(editData.getId().toString(),idList);
		super.actionSave_actionPerformed(e);		
	}
	//删除单据时候删除相应附件
    protected void removeAllAttach(String boID){
        RealModeIDList idListAll = new RealModeIDList();
		List list;
		try {
			list = this.getAllAttachment(boID);
			if(list == null){
				return;
			}
			for(int i =0 ; i< list.size(); i++){
				AttachmentInfo info;
				if(list.get(i) instanceof AttachmentInfo){
					info = (AttachmentInfo)list.get(i);
					idListAll.add(info.getId().toString());
				}
			}
			if(idListAll.size() != 0){
				try {
					removeAttachment(boID,idListAll);
				} catch (EASBizException e) {
					e.printStackTrace();
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

    }

	public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedAttachment();
		String selectAttachId = "";
		if (this.treeAttachment.getLastSelectedPathComponent() instanceof DefaultKingdeeTreeNode) {
			selectAttachId = this.getSelectedAttachmentId();
			viewAttachmentContent(selectAttachId);
		}
	}
    protected void getUpCU(FullOrgUnitInfo orgUnit){
    	upCU.clear();
    	if(orgUnit == null){
    		return;
    	}
		if(orgUnit.isIsCU()){
			upCU.add(orgUnit.getId().toString());
			return;
		}
    	if(orgUnit.getParent() != null){

    		String id = orgUnit.getParent().getId().toString();
    		try {
				FullOrgUnitInfo info = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(id)));
				if(info.isIsCU()){
					upCU.add(info.getId().toString());
					return;
				}else{
					getUpCU(info);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}
    	}
    }
	//获得当前组织所有下级管理单元id
	protected Set getDownOrgIdSet(String longNumber) throws BOSException {
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber",longNumber + "!%",
						CompareType.LIKE));
		orgView.setFilter(orgFilter);
		ICtrlUnit iCtrlUnit = CtrlUnitFactory.getRemoteInstance();
		CtrlUnitCollection ctrlUnitColl = iCtrlUnit.getCtrlUnitCollection(orgView);
		Set orgIdSet = new HashSet(ctrlUnitColl.size());
		for (int i = 0, n = ctrlUnitColl.size(); i < n; i++) {
			orgIdSet.add(ctrlUnitColl.get(i).getId().toString());
		}
		return orgIdSet;
	}
    private void getCtrlOrg(){
		authorizedOrgs = new HashSet();
		Map orgs;
		try {
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}

    }
}