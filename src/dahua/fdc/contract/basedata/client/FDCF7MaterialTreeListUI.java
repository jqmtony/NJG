/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupFactory;
import com.kingdee.eas.basedata.master.material.client.Common;
import com.kingdee.eas.basedata.master.material.client.MaterialEditUI;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class FDCF7MaterialTreeListUI extends AbstractFDCF7MaterialTreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCF7MaterialTreeListUI.class);

    private boolean isCancel = false;
	private FilterInfo f7filter = null;  // �û�����Ĺ���������ɵ�Filter
	private FilterInfo initFilter = null;  // �������½�MaterialPromptSelectorʱ�������filter    
	private FilterInfo defaultFilterInfo = null;  // D��������ϵ�CU��֯��������
	
    /**
     * ���ز��Զ���������ұ��F7����ѡ��UI����F7ѡ������ʱʹ�á�
     * @author owen_wen 
     */
    public FDCF7MaterialTreeListUI() throws Exception
    {
        super();
        this.defaultObjectName = "f7MaterialQuery";
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	if (getUIContext().get("filter") != null)
    		this.initFilter = (FilterInfo) getUIContext().get("filter");
    	
    	this.txtValue.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (e.getOppositeComponent() != null) {
					if (!StringUtils.isEmpty(txtValue.getText())) {
						try {
							btnFastQuery_actionPerformed(null);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
    }
    
    protected String getQueryFieldName() {
		return "materialGroup.id";
	}
    
    // ��ȡѡ�нڵ����ݡ�
    private TreeBaseInfo getSelectTreeInfo()
    {
        KDTreeNode treeNode = getSelectedTreeNode();
        if (treeNode.getUserObject() instanceof TreeBaseInfo)
        {
            return (TreeBaseInfo) treeNode.getUserObject();
        }
        else
        {
            return null;
        }
    }    
    
	protected void initWorkButton() {
		super.initWorkButton();
		btnFastQuery.setIcon(EASResource.getIcon("imgTbtn_filter"));
		chkLike.setSelected(true);
	}
 
	 /**
	 * ��ȡD��������ϵĹ�������, DataBaseDControllerBean�Ѿ���ʵ�ַ���, ֱ�ӵ���getDatabaseDFilter����
	 * <p>
	 * ������ʾ������CU���������Ϻͷ����CU�µ�����
	 * 
	 * @author owen_wen 
	 * @return ��CU��֯���˺������,
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private FilterInfo getInnerFilterInfo() throws EASBizException, BOSException{
		// �ӳٳ�ʼ��
		if (defaultFilterInfo != null) 
			return defaultFilterInfo;
		
		IObjectPK ctrlUnitPK = getBizCUPK(); 	
		defaultFilterInfo = MaterialFactory.getRemoteInstance().getDatabaseDFilter(ctrlUnitPK, this.getKeyFieldName(), "CU.id");
		return defaultFilterInfo;
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// ����Ҫ����table ��change�¼�
	}
	
	private void setTreeFilterForInclude(FilterInfo filter){
         // �Գ�������д�����CU���˺ϲ���
         TreeBaseInfo tree = this.getSelectTreeInfo();
         String longNumber = tree == null ? "" : tree.getLongNumber();
         FilterInfo tempFilter = new FilterInfo();
         if (longNumber != null && longNumber.length() > 0){          	
         		tempFilter.getFilterItems().add(
         				new FilterItemInfo("materialgroup.longnumber", longNumber
                    + IFWEntityStruct.tree_LongNumber_Split + IFWEntityStruct.LIKE, CompareType.LIKE));

         		tempFilter.getFilterItems().add(
         				new FilterItemInfo("materialgroup.longnumber", longNumber, CompareType.EQUALS));
         		tempFilter.setMaskString("#0 OR #1");
         }
         
         FilterInfo cuFilter = new FilterInfo();
         try{
             if (FilterUtility.hasFilterItem(cuFilter)){
                 cuFilter.mergeFilter(tempFilter, "AND");
             }
             else{
                 cuFilter = tempFilter;
             }
         }catch (BOSException e){
             logger.error("merge tree error for inlude child !", e);
			handUIExceptionAndAbort(e);
         }

         if (filter != null && filter.getFilterItems().size() > 0){
             try{
                 if (cuFilter != null && cuFilter.getFilterItems().size() > 0){
                     filter.mergeFilter(cuFilter, "AND");
                 }
             }catch (BOSException e){
                 logger.error("merge tree error for inlude child !", e);
				handUIExceptionAndAbort(e);
             }
         }else{
             filter = (FilterInfo) cuFilter.clone();
         }
         mainQuery.setFilter(filter);
	}
    
	/**
	 * �����ѯ�ӽڵ����ݵĲ�ѯ������
	 * Ĭ��ʵ��Ϊ��EntityViewInfo�����һ��getQueryFieldName()=treeNodeInfo�Ĺ������� �̳����������
	 */
	protected void buildTreeFilter() {
		KDTreeNode treeNode = getSelectedTreeNode();

		if (treeNode == null) {
			return;
		}

		if (mainQuery.getFilter() == null) {
			mainQuery.setFilter(new FilterInfo());
		}
		
		removeQueryFilterAndSorter(mainQuery);	// �����mainQuery֮ǰ�Ĺ�������, ��������Ϲ�������	
        try {
        	mainQuery.getFilter().mergeFilter(getInnerFilterInfo(), "and"); //����ԭ���Ĺ�������
        	setTreeFilterForInclude(mainQuery.getFilter());   // �����Ϸ�����ʱ��Ҫ��������Ҷ���µ�����
        	
        	//�������Filter,����
			if (this.initFilter == null){
				this.initFilter = (FilterInfo) getUIContext().get("filter");
			}
			if (this.initFilter != null) //�п��ܵ����߲�����initFilter�����Ҫ���ǿ��ж�
				mainQuery.getFilter().mergeFilter(this.initFilter, "and");
			
			if (this.f7filter != null) {
				mainQuery.getFilter().mergeFilter(this.f7filter, "and");
			}			
        } catch (Exception e) {
        	logger.info(e.getMessage(), e);
        	handUIExceptionAndAbort(e);
		} 
	}
	
	public Object getData() throws BOSException{
		List idList = this.getSelectedIdValues();
		Set idSet = new HashSet(idList);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo _filter = new FilterInfo();
		view.setFilter(_filter);
		view.getSelector().add("*");
		view.getSelector().add("baseUnit.*");
		_filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		MaterialCollection materialList = MaterialFactory.getRemoteInstance().getMaterialCollection(view); 
		
		disposeUIWindow();
		return materialList.toArray();
	}
	
	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
	
	protected void btnQuit_actionPerformed(ActionEvent e) throws Exception {
		disposeUIWindow();
    	setCancel(true);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return MaterialGroupFactory.getRemoteInstance();
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
	     execQuery();
	     setSelectFirstRow(this.tblMain);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return MaterialFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return MaterialEditUI.class.getName();
	}

	protected String getRootName() {
		return EASResource.getString(Common.resClassName, "materialgroup");
	}
	
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	if (e.getClickCount() == 2) {
            if (e.getType() == 0) {
                return;
            }            
            confirm();
    	}
    }
    
    protected void btnFastQuery_actionPerformed(ActionEvent e) throws Exception {
    	String[] colNames = {"", "number", "name", "model", "helpCode", "baseUnit.name", "MaterialGroup.name","MaterialGroup.number",
    			"shortName", "alias", "foreignname",};
    	
    	//����ʵ�������൱�����֮ǰ�Ĳ�ѯ����
    	f7filter = new FilterInfo();
    	boolean isLikeQuery = this.chkLike.isSelected();
    	if (isLikeQuery){ //ģ����ѯ
    		switch (this.cobKey.getSelectedIndex()) {
			
    		case 0: // ���ϱ��룫��������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[1], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[2], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				f7filter.setMaskString("#0 or #1");
				break;
			
			case 1: // ���ϱ���
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[1], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				break;

			case 2: // ��������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[2], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				break;
				
			case 3: // ����ͺ�
				addFilter4LikeFastQuery(colNames[3]);
				break;
				
			case 4: //������
				addFilter4LikeFastQuery(colNames[4]);
				break;
				
			case 5: //����������λ
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[5], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				break;
				
			case 6: //����������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[6], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				break;
				
			case 7: //���������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[7], "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
				break;
				
			case 8: //���
				addFilter4LikeFastQuery(colNames[8]);
				break;
				
			case 9: //����
				addFilter4LikeFastQuery(colNames[9]);
				break;
				
			case 10: //��������
				addFilter4LikeFastQuery(colNames[10]);
				break;
			
			default:
				break;
			}
    	}else{ //��ȷ��ѯ
    		switch (this.cobKey.getSelectedIndex()) {
			
    		case 0: // ���ϱ��룫��������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[1],  this.txtValue.getText().trim()));
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[2], this.txtValue.getText().trim()));
				f7filter.setMaskString("#0 or #1");
				break;
			
			case 1: // ���ϱ���
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[1], this.txtValue.getText().trim()));
				break;

			case 2: // ��������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[2], this.txtValue.getText().trim()));
				break;
				
			case 3: // ����ͺ�
				addFilter4NotLikeFastQuery(colNames[3]);
				break;
				
			case 4: //������
				addFilter4NotLikeFastQuery(colNames[4]);
				break;
				
			case 5: //����������λ
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[5], this.txtValue.getText().trim()));
				break;
				
			case 6: //����������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[6], this.txtValue.getText().trim()));
				break;
				
			case 7: //���������
				f7filter.getFilterItems().add(new FilterItemInfo(colNames[7], this.txtValue.getText().trim()));
				break;
				
			case 8: //���
				addFilter4NotLikeFastQuery(colNames[8]);
				break;
				
			case 9: //����
				addFilter4NotLikeFastQuery(colNames[9]);
				break;
				
			case 10: //��������
				addFilter4NotLikeFastQuery(colNames[10]);
				break;
			
			default:
				break;
			}
    	}
        buildTreeFilter();
        execQuery();
        setSelectFirstRow(this.tblMain);
    }
    
    /**
     * Ϊ���ٲ�ѯ��ӹ���������<p>
     * ģ����ѯ�������ѯ����Ϊ�գ���Ҫ�������
     * ��ģ����ѯ�������ѯ����Ϊ�գ���Ҫ�������Ϊ�ռ�¼
     * @param colName ����
     * @author owen_wen 2010-9-1
     */
    private void addFilter4LikeFastQuery(String colName){    	
    	if (this.txtValue.getText().trim().length() > 0)
			f7filter.getFilterItems().add(new FilterItemInfo(colName, "%"+ this.txtValue.getText().trim() +"%", CompareType.LIKE));
    }
    
    /**
     * Ϊ���ٲ�ѯ��ӹ���������<p>
     * ��ģ����ѯ�������ѯ����Ϊ�գ���Ҫ�������Ϊ�ռ�¼
     * @param colName ����
     * @author owen_wen 2010-9-1
     */
    private void addFilter4NotLikeFastQuery(String colName){
    	if (this.txtValue.getText().trim().length() > 0)
			f7filter.getFilterItems().add(new FilterItemInfo(colName, this.txtValue.getText().trim()));
		else
			f7filter.getFilterItems().add(new FilterItemInfo(colName, null, CompareType.IS));
    }

	private void confirm() throws Exception {
		checkSelected();
    	getData();
    	setCancel(false);
	}
	
	public boolean isCancel() {
    	return isCancel;
    }
	

    protected IObjectPK getBizCUPK()
    {
        Object object = getUIContext().get(getMainBizOrgType());
        OrgUnitInfo currentBizOrg = 
            object == null ? SysContext.getSysContext().getCurrentCtrlUnit() : (OrgUnitInfo)object ;
        return new ObjectUuidPK(currentBizOrg.getCU().getId());
    }
}