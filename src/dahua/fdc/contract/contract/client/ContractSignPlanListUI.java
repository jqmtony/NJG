/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.list.exception.ObjectNotFoundException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractSignPlanFactory;
import com.kingdee.eas.fdc.contract.ContractSignPlanInfo;
import com.kingdee.eas.fdc.contract.IContractSignPlan;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractSignPlanListUI extends AbstractContractSignPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractSignPlanListUI.class);
    private Set authorizedOrgs = null;
    /**
     * output class constructor
     */
    public ContractSignPlanListUI() throws Exception
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
    
    protected ICoreBase getRemoteInterface() throws BOSException 
    {
		return ContractSignPlanFactory.getRemoteInstance();
	}
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception 
    {
    	FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		
    	super.actionAddNew_actionPerformed(e);
    }
    
    //����������ʵ�ֶ�Button��״̬���¡�����Ҳ�����������С�
    protected void updateButtonStatus()
    {
    	
    }
	protected void audit(List ids) throws Exception 
	{
		/**
    	 * ������󻥳�,����Ƿ����������˶���
    	 */
    	//��ȡ��ѡ�����еĶ����ID
    	IIDList selectIDs = new RealModeIDList();
    	String removeID = null;
    	
    	for (int i = 0; i < ids.size(); i++)
    	{
    		selectIDs.add((String) ids.get(i));
    	}
    	
    	/**
    	 * ��ÿ��ѡ�еĶ����ȼ�������,��������ɹ�,�ٽ��д���.����������ʧ�ܵ�ԭ��
    	 */
    	for (int i = selectIDs.size() - 1; i >= 0; i--)
    	{
    		//��Ҫ�����Ķ������¼�������,�Ա�ʵ�����绥��
    		try
    		{
    			this.setOprtState("AUDIT");
    			this.pubFireVOChangeListener(selectIDs.getLastID());
    		}
    		catch (Throwable ex)
    		{
    			ex.printStackTrace();
    			continue;
    		}
    	}
    	
    	//����Զ�̷��������������Ķ���
    	try
    	{
    		((IContractSignPlan)getBizInterface()).audit(ids);
    		
    		this.actionAudit.setEnabled(false);
    		this.actionUnAudit.setEnabled(true);
    	}
    	catch (ObjectNotFoundException onfe)
    	{
    		handUIExceptionAndAbort(onfe);
    	}
    	catch (Throwable e1)
    	{
    		e1.printStackTrace();
    	}
    	finally
    	{
    		try
    		{
    			//�����ͷŻ������
    			this.setOprtState("RELEASEALL");
    			this.pubFireVOChangeListener(selectIDs.getLastID());
    			for (int i = selectIDs.size() - 1; i >= 0; i--)
    			{
    				this.pubFireVOChangeListener(selectIDs.getLastID());
    				removeID = selectIDs.getLastID();
    				selectIDs.remove(removeID, true);
    			}
    		}
    		catch (Throwable e1)
    		{
    			e1.printStackTrace();
    		}
    	}
	}

	protected void unAudit(List ids) throws Exception 
	{
		/**
    	 * ������󻥳�,����Ƿ����������˶���
    	 */
    	//��ȡ��ѡ�����еĶ����ID
    	IIDList selectIDs = new RealModeIDList();
    	String removeID = null;
    	
    	for (int i = 0; i < ids.size(); i++)
    	{
    		selectIDs.add((String) ids.get(i));
    	}
    	
    	/**
    	 * ��ÿ��ѡ�еĶ����ȼ�������,��������ɹ�,�ٽ��д���.����������ʧ�ܵ�ԭ��
    	 */
    	for (int i = selectIDs.size() - 1; i >= 0; i--)
    	{
    		//��Ҫ�����Ķ������¼�������,�Ա�ʵ�����绥��
    		try
    		{
    			this.setOprtState("AUDIT");
    			this.pubFireVOChangeListener(selectIDs.getLastID());
    		}
    		catch (Throwable ex)
    		{
    			ex.printStackTrace();
    			continue;
    		}
    	}
    	
    	//����Զ�̷��������������Ķ���
    	try
    	{
    		((IContractSignPlan)getBizInterface()).unAudit(ids);
    		
    		this.actionAudit.setEnabled(true);
    		this.actionUnAudit.setEnabled(false);
    	}
    	catch (ObjectNotFoundException onfe)
    	{
    		handUIExceptionAndAbort(onfe);
    	}
    	catch (Throwable e1)
    	{
    		e1.printStackTrace();
    	}
    	finally
    	{
    		try
    		{
    			//�����ͷŻ������
    			this.setOprtState("RELEASEALL");
    			this.pubFireVOChangeListener(selectIDs.getLastID());
    			for (int i = selectIDs.size() - 1; i >= 0; i--)
    			{
    				this.pubFireVOChangeListener(selectIDs.getLastID());
    				removeID = selectIDs.getLastID();
    				selectIDs.remove(removeID, true);
    			}
    		}
    		catch (Throwable e1)
    		{
    			e1.printStackTrace();
    		}
    	}
	}
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return ContractSignPlanEditUI.class.getName();
	}
	protected FilterInfo getTreeFilter() throws Exception 
	{
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		//��õ�ǰ�û��µ���֯��Χids
		authorizedOrgs = null;
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
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
		}
		/*
		 * ������Ŀ��
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();
			//ѡ����ǳɱ����ģ�ȡ�óɱ����ļ��¼��ɱ����ģ�����У��µ����к�ͬ
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				filterItems.add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				filterItems.add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				
				filterItems.add(new FilterItemInfo("orgUnit.id", authorizedOrgs,
						CompareType.INCLUDE));
			}

			// ѡ�������Ŀ��ȡ����Ŀ���¼���Ŀ������У��µ����к�ͬ
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("project.id", idSet,
						CompareType.INCLUDE));
			}
		}
		
		return filter;
	}
	/**
	 * 
	 * ����������ߵ���ѡ��仯ʱ��ȱʡ�������ṩĬ��ʵ�֣���ͬ״̬Ϊ��ˣ�������Ը��ǣ����û��������ҲҪ����һ��new
	 * FilterInfo()������ֱ�ӷ���null��
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-9-5
	 *               <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("project.isEnabled", Boolean.TRUE));
		
		return filter;
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) 
	{
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {

			/*
			 * �ѵ�ǰѡ�еĹ�����Ŀ�ͺ�ͬ���ʹ���EditUI
			 */
			if(uiContext.get("curProject") == null)
			{
				CurProjectInfo project = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
				uiContext.put("curProject", project);
			}
			else
			{
				MsgBox.showError("curProject���Ѿ���Ӧ��");
				SysUtil.abort();
			}
		}
	}
	
	public void onLoad() throws Exception 
	{
		super.onLoad();
		
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception 
	{
		super.tblMain_tableSelectChanged(e);
		
		int index = tblMain.getSelectManager().getActiveRowIndex();
        
        IRow row = tblMain.getRow(index);
        IObjectPK pk = new ObjectUuidPK(row.getCell("id").getValue().toString());
        
        ContractSignPlanInfo planInfo = ContractSignPlanFactory.getRemoteInstance().getContractSignPlanInfo(pk);
        
        if( FDCBillStateEnum.SUBMITTED.equals(planInfo.getState()))
        {
        	this.actionAudit.setEnabled(true);
        	this.actionUnAudit.setEnabled(false);
        	
        	return ;
        }
        
        if( FDCBillStateEnum.AUDITTED.equals(planInfo.getState()))
        {
        	this.actionAudit.setEnabled(false);
        	this.actionUnAudit.setEnabled(true);
        	
        	return ;
        }
       
        if(!planInfo.getState().equals(FDCBillStateEnum.SUBMITTED) && !planInfo.getState().equals(FDCBillStateEnum.AUDITTED))
        {
        	this.actionAudit.setEnabled(false);
        	this.actionUnAudit.setEnabled(false);
        }
	}
}