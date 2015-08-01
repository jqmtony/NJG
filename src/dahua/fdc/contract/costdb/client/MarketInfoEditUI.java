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
	
	//��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;
	
	private FileGetter fg;
	//����ʱɾ���ĸ���id
	RealModeIDList idList = new RealModeIDList();
	//���ϴ��ĸ���id
	RealModeIDList idListAdd = new RealModeIDList();
	//����
	private boolean isAttachChange = false;
	//����֯����CU
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
    	//�����޸�ʱ��
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

            //У��storeFields()�Ƿ��׳��쳣
//            Exception err = verifyStoreFields();

            //storeFields()�׳��쳣����editdata�иı䣬ѯ���Ƿ񱣴��˳�
            if (isModify()) {
//                editdata�иı�
                int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Save_Exit"));

                if (result == KDOptionPane.YES_OPTION) {

                        try {
                            if(!isModifySave()||!actionSave.isEnabled())
                            {
                                actionSubmit.setDaemonRun(false);
                                //actionSubmit.actionPerformed(null);
                                //by jakcy 2005-1-6 �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
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
//		//����������а���������Ϣ������������
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
        //��������ťʱɾ�����нڵ�����
        removeAllAttch();
		this.actionRemove.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.setUITitle("�ļ� - ����");
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        this.setUITitle("�ļ� - �༭");
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
    	//ɾ��ʱͬʱɾ����Ӧ����
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
		//��ʼ����������
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
		//��ʼ�����ֿؼ�
		//���������
		Map map = this.getUIContext();
		//����������а���������Ϣ������������
		if(map != null && map.containsKey("type")){
			Object object = map.get("type");
			if(object instanceof MarketInfoTypeInfo){
				MarketInfoTypeInfo type = (MarketInfoTypeInfo)object;
				this.prmtType.setValue(type);
			}
		}

		//���ø�������
//		if(editData.getId()!=null){
//	        String attch = getAttachmentName(editData.getId().toString());
////	        this.txtAttachment.setText(attch);
//		}
//		handleCodingRule();
//		editData.setNumber(this.txtNumber.getText().toString());
//		this.getNumberByCodingRule(editData,currentOrg.getId().toString());
		//���ߺ������ڲ��� �ȿ���ѡ���� �ֿ����ֹ�¼��
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
    	//��õ�ǰ��֯�������¼���֯��ids
    	Set childLongNumberSet = new HashSet();
    	try {
    		childLongNumberSet = getDownOrgIdSet(longNumber);
    	} catch (BOSException e) {
    		e.printStackTrace();
    	}
    	childLongNumberSet.add(this.currentOrg.getId().toString());
		//���г��������ͽ��й���
		EntityViewInfo typeView = this.prmtType.getEntityViewInfo();
		FilterInfo typeFilter = new FilterInfo();
		typeView = new EntityViewInfo();
		//typeFilter = new FilterInfo();
        //�����¼�CU����������
		typeFilter.getFilterItems().add(
                new FilterItemInfo("org.id", childLongNumberSet, CompareType.INCLUDE));
    	//�û���֯Ȩ�޸���
		typeFilter.getFilterItems().add(
				new FilterItemInfo("org.id",authorizedOrgs,CompareType.INCLUDE));

        //���Ž��������� ������ϵͳԤ������
		typeFilter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.SYS_CU_ID, CompareType.EQUALS));
		typeFilter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.DEF_CU_ID, CompareType.EQUALS));
		typeFilter.setMaskString("(#0 and #1) or #2 or #3");
		//ֱ���ϼ�CU����������
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
		//����F7�ؼ���ѯ
//		this.prmtPerson.setEntityViewInfo(view);
//		this.prmtPosition.setEntityViewInfo(viewInfo);
		
		//ΪprmtPerson���ֵ�仯������
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
		
		//��ö�Ӧ���и���������treeAttachment
		List list = this.getAllAttachment(editData.getId().toString());
		insertAttachment(list);
		
	}
	/**
	 * ��ʼ�����ֿؼ�
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
		//�Ǳ���֯�µ���Ϣ�������޸�ɾ��
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
			this.setUITitle("�ļ� - ����");
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if(this.getOprtState()=="VIEW"){
			this.setUITitle("�ļ� - �鿴");
			this.actionRemove.setEnabled(false);
			this.btnAttchment.setEnabled(false);
			this.actionRemoveAttachment.setEnabled(false);
		}
		if(this.getOprtState()=="EDIT"){
			this.setUITitle("�ļ� - �༭");
			this.actionEdit.setEnabled(false);
//			this.actionAttachment.setEnabled(true);
			this.btnAttchment.setEnabled(true);
			this.actionRemoveAttachment.setEnabled(true);
		}
	}
	//prmtPerson�ؼ�ֵ�仯����  ���߱仯����
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
	//���ظ���getSelectors()������ʵ��F7�ؼ��ȿ�ѡ���ã��ֿ��ֹ�¼��
	//ͬʱ���ݿ����ʱ��Ӧ��ԭ�е��������Ը�Ϊ������Ҫ��ʾ���ֶ�����
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
	//��ñ�����
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	//��¼���������ʾУ��
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(this.txtOrgUnit.getText()==null || "".equals(this.txtOrgUnit.getText())){
			MsgBox.showWarning(this,"��֯����Ϊ�գ�");
			SysUtil.abort();
		}
		if((this.txtNumber.getText()==null || "".equals(this.txtNumber.getText()))
				&& this.txtNumber.isEnabled()){
			MsgBox.showWarning(this,"���ϱ�Ų���Ϊ�գ�");
			SysUtil.abort();
		}
		if(this.txtName.getText()==null || "".equals(this.txtName.getText())) {
			MsgBox.showWarning(this,"�������Ʋ���Ϊ�գ�");
			SysUtil.abort();
		}
		if(this.prmtType.getValue()==null){
			MsgBox.showWarning(this,"�������Ͳ���Ϊ�գ�");
			SysUtil.abort();
		}
		//���ֻ��500��
		String description = txtDescription.getText();
		if(description.toCharArray().length>500){
			MsgBox.showWarning(this,"���ϼ�鲻�ܳ���500�֣�");
			SysUtil.abort();
		}
		//������¼
		if(this.treeAttachment.getRowCount()==0){
			MsgBox.showWarning(this,"��������Ϊ�գ�");
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
			//�����ظ���֤
			if (getBizInterface().exists(filterInfo)) {
//				throw new FDCInviteException(
//						FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
				MsgBox.showWarning(this,"���ϱ�Ų����ظ���");
				SysUtil.abort();
			}
		}

		filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("name",this.txtName.getText().trim()));
		if(editData.getId()!=null)
			filterInfo.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		if (getBizInterface().exists(filterInfo)) {
			MsgBox.showWarning(this,"�������Ʋ����ظ���");
			SysUtil.abort();
		}
	}
	/**
	 * ��õ�ǰ���ݶ�Ӧ�ĸ����� �������ʱ�á�|������
	 * @param id ��ǰ����id
	 * @return
	 * @throws Exception
	 */
	protected String getAttachmentName(String id) throws Exception{
        String attch = "";
        //����ж������ʱ���á�|��������
//        if(!"".equals(this.txtAttachment.getText())){
//        	attch+=" | ";
//        }
        //��õ�ǰid��Ӧ�ĸ���
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
            			//����ж������ʱ���á�|��������
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
	 * ����������
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
		 * 2008-09-27�����������״̬��ֱ�ӷ���
		 * Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
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
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
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
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"�������ñ��뵽�ؼ�
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
			//����������а���������Ϣ������������
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
		//������������仯
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
	//�ϴ�����
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
		//����Ҫɾ���ĸ���id���浽idList�У�����ʱɾ����
		checkSelectedAttachment();		
//		String id = this.getSelectedAttachmentId();
//		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) this.treeAttachment
//			.getLastSelectedPathComponent();
		//ȷ��ɾ������(ֻɾ���ڵ����ݣ���δ��ɾ�����ݿ������ݣ�����ʱ��ɾ����)
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
			MsgBox.showWarning(this,"��ѡ�񸽼���");
			SysUtil.abort();
		}
    }
    //���ѡ�и���id
    protected String getSelectedAttachmentId(){    	
		String attachmentId = "";
		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) this.treeAttachment
			.getLastSelectedPathComponent();
		if(selectNode == null){
			MsgBox.showWarning(this,"��ѡ�񸽼���");
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
	//ֻɾ���ڵ����� ����ɾ������������
	public void removeAllAttch(){
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeAttachment
			.getModel().getRoot();
		this.treeAttachment.removeAllChildrenFromParent(node);
	}
	protected void insertAttachment(List list){
		//��ɾ������Ҷ�ӽڵ�
		removeAllAttch();
		//�������ӽڵ����ȥ
		if(list == null){
			return;
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) ((KingdeeTreeModel) this.treeAttachment
				.getModel()).getRoot();
		for(int i = 0;i < list.size();i++){
			//ȡ��ǰ���ϵ����и�����д��treeAttachment��
			if(list.get(i) instanceof AttachmentInfo){
				AttachmentInfo info = (AttachmentInfo)list.get(i);
				addNode(info,root);
			}

		}  
		treeAttachment.expandAllNodes(true, (TreeNode) treeAttachment
				.getModel().getRoot());
	}

	protected void treeAttach_keyPressed(KeyEvent e) throws Exception {
		//�����¼� ��DEL �� DELETE�� ɾ������
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
	//˫��ֱ�Ӵ򿪸�������
	protected void treeAttach_mouseClicked(MouseEvent e) throws Exception {
		//�����������һ�е����棬�򲻴���
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
	 * ������һ�����²��֣�getClosestPathForLocation��Ϊ�����һ��</br><br>
	 * ���ڷ���Ϊnull</br>
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

			// �����λ�ó������һ�У���ûѡ�ϡ�
			// ע����������һ�����²��֣�closestPath��Ϊ�����һ�С�
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
	 * �򿪸���
	 * @param attachmentId ����ID
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
     * ɾ��ȷ�϶Ի����Ĭ��ʵ�֣��̳����������
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
     * ɾ������
     * @param boID �������ID 
     * @param idList ����ids
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
	//ɾ������ʱ��ɾ����Ӧ����
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
	//��õ�ǰ��֯�����¼�����Ԫid
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