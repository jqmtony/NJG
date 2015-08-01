/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDButtonGroup;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ICostAccountFacade;
import com.kingdee.eas.fdc.basedata.QueryFieldConditionEnum;
import com.kingdee.eas.fdc.basedata.util.CostAccountHelper;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * �ɱ���Ŀ�������
 */
public class CostAccountNewAssignUI extends AbstractCostAccountNewAssignUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountNewAssignUI.class);
    
    private Set curOrgIds = new TreeSet();

    private Set curProjectIds = new TreeSet();
    // ��Ŀ����
    private Map acctInfoMaps = new HashMap();
    /**
     * ������ 
     */
    private boolean assigned = false;
    
    private static final Color canntSelectColor = new Color(0xFEFED3);
    
	CompanyOrgUnitInfo currentCompany = null;

    CompanyOrgUnitCollection companyOrgUnit = null;
    
	ArrayList checkList = new ArrayList();
    
    public CostAccountNewAssignUI() throws Exception
    {
        super();
        initCtrlActive();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		
		loadCompanyForAssigned();
		
		initLisenter();
		
		this.loadCostAccountByCtrls();
		setIsUpperAllowCAAllSelect(true);
	}

	/**
	 * �ؼ���״̬
	 */
	private void initCtrlActive() {
		
		this.tblCompanyForAssigned.checkParsed();
        this.tblAccountForAssigned.checkParsed();
        
		KDButtonGroup kdgroup = new KDButtonGroup();
		
		kdgroup.add(this.rbNotAssigned);
		kdgroup.add(this.rbAssigned);
		
		rbAssigned.setEnabled(true);
		rbNotAssigned.setEnabled(true);
		
		btnAllSelect.addAssistMenuItem(this.actionSelectAllCA);
		btnAllDisSelect.addAssistMenuItem(this.actionDisSelectAllCA);
		btnAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));// ���䰴ť		
	}
	
	protected void tblCompanyForAssigned_tableSelectChanged(KDTSelectEvent e) throws Exception {
		int rowIndex = tblCompanyForAssigned.getSelectManager().getActiveRowIndex();
		this.tblCompanyForAssigned.getRow(rowIndex);		
	}
	

	/**
	 * ע�����
	 */
	private void initLisenter() {
		
		this.tblCompanyForAssigned.addKDTEditListener(new KDTEditAdapter(){
			public void editValueChanged(KDTEditEvent e) {
				KDTable tblCompany = (KDTable) e.getSource();
				IRow row = tblCompany.getRow(e.getRowIndex());
				
				String id = row.getCell("id").getValue().toString();

				if(row.getCell("selected").getValue().toString().equals("false")){
					row.getCell("selected").setValue(Boolean.valueOf(true));
					
				}else{
					//��ʾ��ȡ��ɫѡ����
					row.getCell("selected").setValue(Boolean.valueOf(false));
				}
				
				if(curProjectIds != null && !curProjectIds.isEmpty()){
					curProjectIds.clear();
				}
				if(curOrgIds != null && !curOrgIds.isEmpty()){
					curOrgIds.clear();
				}
				
				if(e.getColIndex()==tblCompanyForAssigned.getColumnIndex("selected")){
					Boolean old=(Boolean)e.getOldValue();
					Boolean now=(Boolean)e.getValue();
					if(assigned){
						selectOrgNodesInAssagned(e.getRowIndex(), old.booleanValue(), now.booleanValue());
					}else{
						selectOrgNodesInNotAssagned(e.getRowIndex(),old.booleanValue(),now.booleanValue());
					}
				}
				try {
					boolean isAllSelected = false;					
					int rowCount = tblCompanyForAssigned.getRowCount();
					for(int i=0;i<rowCount;i++){
						IRow tempRow = tblCompanyForAssigned.getRow(i);
						if(((Boolean)tempRow.getCell("selected").getValue()).booleanValue()){
							isAllSelected = true ;
						}
					}
					if(!isAllSelected){
						assigned= false;
						rbNotAssigned.setSelected(true);
						btnAssign.setText("����");
						loadCostAccountByCtrls();
					}
					
					loadCostAccountSelected();
				} catch (SQLException e1) {
					// @AbortException
					e1.printStackTrace();
				}
			}
		});
		
		this.tblAccountForAssigned.addKDTEditListener(new KDTEditAdapter() {
			public void editValueChanged(KDTEditEvent e) {
				if(e.getColIndex()==tblAccountForAssigned.getColumnIndex("selected")){
					Boolean old=(Boolean)e.getOldValue();
					Boolean now=(Boolean)e.getValue();
					//TODO
					if(assigned){
						selectNoneCostActNodes(e.getRowIndex(),old.booleanValue(),now.booleanValue());
					}else{
						selectCostActNodes(e.getRowIndex(),old.booleanValue(),now.booleanValue());
					}
				}
			}
		});		
	}
	
	/**
	 * ���سɱ���Ŀ�����˵õ����ݵķ���
	 * ʵʱ��ѯ����ʱû��ʹ�ã����û�ƿ�Ŀ����ʵ�� 
	 */
	private void loadCostAccountDatas() {
	
		fillCostAccountId();
		
		this.tblAccountForAssigned.removeRows();
		
		String costAccountText = this.txtCostAccountNumAndName.getText();
		
		if(curOrgIds.isEmpty() && curProjectIds.isEmpty()){
			return ;
		}
		
		QueryFieldConditionEnum queryConditon = (QueryFieldConditionEnum) this.cbxCostAccountQueryField.getSelectedItem();
		
		IRowSet rowSet = CostAccountHelper.getCostAccountList(curOrgIds,curProjectIds ,assigned ,queryConditon ,costAccountText);
		
		setAssigedListStyle();

		if (rowSet != null &&  rowSet.size() != 0) {
			IRow row=null;
			try {
				rowSet.beforeFirst();
								
				while(rowSet.next()){
					CostAccountInfo costAccountInfo = new CostAccountInfo();
					
					String fid = rowSet.getString("fid");
					String fname_l2 = rowSet.getString("fname_l2");
					
					String FcreateOrg = rowSet.getString("FcreateOrg");
					String flongnumber = rowSet.getString("flongnumber");
					
					boolean fisCanadd = rowSet.getBoolean("fisCanadd");
					boolean fisLeaf = rowSet.getBoolean("fisleaf");
					//boolean assigned = rowSet.getBoolean("assigned");
					
					int flevel = rowSet.getInt("flevel");

					costAccountInfo.setId(BOSUuid.read(fid));
					costAccountInfo.setName(fname_l2);
					
					costAccountInfo.setLongNumber(flongnumber);
					costAccountInfo.setIsCanAdd(fisCanadd);
					
					costAccountInfo.setIsLeaf(fisLeaf);
					costAccountInfo.setLevel(flevel);
					
					row = this.tblAccountForAssigned.addRow();
					
					//node=new CellTreeNode();
					
					row.getCell("selected").setValue(Boolean.valueOf(false));
					row.getCell("accountName").setValue(fname_l2);
					row.getCell("id").setValue(fid);
					if(FcreateOrg !=null){
						row.getCell("createOrg").setValue(FcreateOrg);
					}
					row.getCell("account").setValue(flongnumber.replace('!', '.'));
					//���������¼���Ŀ
					row.getCell("isUpperAllowCA").setValue(Boolean.valueOf(fisCanadd));
					
					row.setUserObject(costAccountInfo);
					row.getStyleAttributes().setBackground(canntSelectColor);
				}
				rowSet.beforeFirst();				
			} catch (SQLException e) {
				// @AbortException
				e.printStackTrace();
			}
		}
	}

	List costAccountList = new ArrayList();
	
	/**
	 * �Կؼ���ʽ������
	 */
	protected void loadCostAccountByCtrls() {	
		this.tblAccountForAssigned.removeRows();
		IRowSet rowSet = CostAccountHelper.getAllCostAccountList();		
		setAssigedListStyle();
		setCostAccountFillToRows(rowSet);
	}
	
	protected void loadCostAccountSelected() throws SQLException{
		boolean isSelected = fillCostAccountId();
		notSelectedCompanyAction(isSelected);		
		setRowsState(isSelected);
	}

	/**
	 * ����������û��ѡ��˾����Ŀʱ���������п�Ŀ
	 * @param isSelected
	 */
	private void notSelectedCompanyAction(boolean isSelected) {
		for(int i=0;i<this.tblAccountForAssigned.getRowCount();i++){
			IRow tempRow = this.tblAccountForAssigned.getRow(i);
			if(!isSelected && this.assigned){
				tempRow.getStyleAttributes().setHided(true);
			}else{
				tempRow.getStyleAttributes().setHided(false);			
			}
		}
	}
	
	protected Set getIdsSetsByRowSet(IRowSet rowSet) throws SQLException {		
		Set ids = new HashSet();
		if(rowSet != null){
			rowSet.beforeFirst();
			
			while(rowSet.next()){
				ids.add(rowSet.getString("flongNumber"));
				// �����Ҫ���������ٷ�װ�ɶ��󴫰� by hpw
				acctInfoMaps.put(rowSet.getString("flongNumber"), rowSet.getString("fisCanadd"));
			}
			rowSet.beforeFirst();
			return ids;			
		}
		return null ;
	}

	private void setCostAccountFillToRows(IRowSet rowSet) {
		if (rowSet != null &&  rowSet.size() != 0) {
			IRow row=null;
			try {
				rowSet.beforeFirst();
								
				while(rowSet.next()){
					CostAccountInfo costAccountInfo = new CostAccountInfo();
					
					String fid = rowSet.getString("fid");
					String fname_l2 = rowSet.getString("fname_l2");
					
					String FcreateOrg = rowSet.getString("FcreateOrg");
					String flongnumber = rowSet.getString("flongnumber");
					
					boolean fisCanadd = rowSet.getBoolean("fisCanadd");
					boolean fisLeaf = rowSet.getBoolean("fisleaf");
					//boolean assigned = rowSet.getBoolean("assigned");
					
					int flevel = rowSet.getInt("flevel");

					costAccountInfo.setId(BOSUuid.read(fid));
					costAccountInfo.setName(fname_l2);
					
					costAccountInfo.setLongNumber(flongnumber);
					costAccountInfo.setIsCanAdd(fisCanadd);
					
					costAccountInfo.setIsLeaf(fisLeaf);
					costAccountInfo.setLevel(flevel);
					
					row = this.tblAccountForAssigned.addRow();
					
					row.getCell("selected").setValue(Boolean.valueOf(false));
					row.getCell("accountName").setValue(fname_l2);
					row.getCell("id").setValue(fid);
					if(FcreateOrg !=null){
						row.getCell("createOrg").setValue(FcreateOrg);
					}
					row.getCell("account").setValue(flongnumber.replace('!', '.'));
					row.getCell("account").getStyleAttributes().setLocked(true);
					//���������¼���Ŀ
					row.getCell("isUpperAllowCA").setValue(Boolean.valueOf(fisCanadd));
					
					row.setUserObject(costAccountInfo);
					row.getStyleAttributes().setBackground(canntSelectColor);
					
					costAccountList.add(costAccountInfo);
				}
				rowSet.beforeFirst();				
			} catch (SQLException e) {
				// @AbortException
				e.printStackTrace();
			}
		}
	}
	

	protected void setRowsState(boolean isSelected ) throws SQLException {
		if(!isSelected && this.assigned){
			return ;
		}
		
		QueryFieldConditionEnum queryConditon = (QueryFieldConditionEnum) this.cbxCostAccountQueryField.getSelectedItem();
		String costAccountText = this.txtCostAccountNumAndName.getText();
		
		IRowSet rowSet = CostAccountHelper.getCostAccountList(curOrgIds,curProjectIds ,assigned ,queryConditon ,costAccountText);
		
		int rowCount = tblAccountForAssigned.getRowCount();
		
		Set ids = this.getIdsSetsByRowSet(rowSet);
		
		for(int i=0;i<rowCount;i++){
			IRow tempRow = tblAccountForAssigned.getRow(i);
			
			CostAccountInfo costAccount = (CostAccountInfo) tempRow.getUserObject();
			if(costAccount != null){
				
				if(!ids.contains(costAccount.getLongNumber())){
					tempRow.getStyleAttributes().setHided(true);
					if(((Boolean)tempRow.getCell("selected").getValue()).booleanValue()){
						tempRow.getCell("selected").setValue(Boolean.valueOf(false));
					}
				}else{
					tempRow.getStyleAttributes().setHided(false);
				}
				
				String id = costAccount.getId().toString();
				if (acctInfoMaps.get(id) != null) {
					tempRow.getCell("isUpperAllowCA").setValue(Boolean.valueOf(costAccount.getLongNumber()));
				}
			}
		}
	}
	
	
	/**
	 * �Ƿ�ѡ���˹�˾����Ŀ�����ռ���˾����ĿID
	 */
	private boolean fillCostAccountId() {
		int rowCount = tblCompanyForAssigned.getRowCount();
		boolean isSelected = false ;
		for(int i=0;i<rowCount;i++){
			IRow tempRow = tblCompanyForAssigned.getRow(i);
			/**
			 * ��֯�������¼����Զ�ѡ������⵱����ʱ δ��ʾ����֯����Ŀ��ĿҲ������ by hpw 2012.3.1
			 */
			if (tempRow.getStyleAttributes().isHided()) {
				continue;
			}
			if(((Boolean)tempRow.getCell("selected").getValue()).booleanValue()){
				String rowId = tempRow.getCell("id").getValue().toString();
				isSelected = true ;
				if(BOSUuid.read(rowId).getType().equals((new CurProjectInfo()).getBOSType())){
					curProjectIds.add(rowId);
					
				}else if(BOSUuid.read(rowId).getType().equals((new FullOrgUnitInfo()).getBOSType())){
					curOrgIds.add(rowId);
				}
			}
		}
		
		return isSelected ;
	}
	
	
	private void setAssigedListStyle() {		
		tblAccountForAssigned.checkParsed();// table����!
		tblAccountForAssigned.getColumn("selected").getStyleAttributes().setLocked(false);
		tblAccountForAssigned.getColumn("account").getStyleAttributes().setLocked(true);
	}
	
	/**
	 * ֻѡ�ϼ�����֯
	 * @param row
	 * @param old
	 * @param now
	 */
	public void selectOrgNodesInNotAssagned(int row, boolean old, boolean now){
    	if(old==now) 
    		return;
    	this.tblCompanyForAssigned.getCell(row, "selected").setValue(Boolean.valueOf(now));
		Integer integer = (Integer) tblCompanyForAssigned.getRow(row).getCell("nodeLevel").getValue();
		
		//IRow selectedRow = tblCompanyForAssigned.getRow(row);
		
		int level= integer.intValue();
		//����ϼ�ȡ�����¼�Ҫ��ȡ��Ϣ������ϼ���ѡ���¼�����ѡ��
		//�¼�
		if(!now){
			for (int i = row + 1; i < tblCompanyForAssigned.getRowCount(); i++) {
				Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();				
				if(nextLevel.intValue()>level){
					tblCompanyForAssigned.getCell(i, "selected").setValue(Boolean.valueOf(now));					
				}else{
					break;
				}
			}
		}
		
		//�ϼ�
		int parentLevel=level-1;
		if(now){
			for(int i=row-1;i>=0;i--){
				if(parentLevel==0){
					break;
				}
				Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
				
				if(nextLevel.intValue() ==parentLevel){
					ICell cell = tblCompanyForAssigned.getCell(i, "selected");
					if(cell.getValue()!=Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					}else{
						break;
					}
				}
			}
		}else{
			//��ѡ��,���ͬ���Ƿ���ѡ���
			boolean hasSelected=false;
			//�������
			for(int i=row+1;i<tblCompanyForAssigned.getRowCount();i++){
				Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
				
				if(nextLevel.intValue()==level){
					ICell cell = tblCompanyForAssigned.getCell(i, "selected");
					if(cell.getValue()==Boolean.TRUE) {
						hasSelected=true;
						break;
					}else if(nextLevel.intValue()<level){
						break;
					}
				}
			}
			//�������
			if(!hasSelected){
				for(int i=row-1;i>=0;i--){
					Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
					if(nextLevel.intValue()==level){
						ICell cell = tblCompanyForAssigned.getCell(i, "selected");
						if(cell.getValue()==Boolean.TRUE) {
							hasSelected=true;
							break;
						}
					}else if(nextLevel.intValue()<level){
						row=i;
						break;
					}
				}
			}
			
			if(!hasSelected){
				//���ø���
				parentLevel=level-1;
				for(int j=row;j>=0;j--){
					if(parentLevel==0){
						break;
					}
					Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(j).getCell("nodeLevel").getValue();
					
					if(nextLevel.intValue()== parentLevel){
						
						boolean setParent = false ;
            			
            			//�ж��¼��Ƿ���ѡ�е�
            			for(int idx=j+1;idx<tblCompanyForAssigned.getRowCount();idx++){
            				Integer currentLevel = (Integer) tblCompanyForAssigned.getRow(idx).getCell("nodeLevel").getValue();
            				Boolean isSelected = (Boolean) tblCompanyForAssigned.getCell(idx, "selected").getValue();
            	    		
            	    		if(currentLevel.intValue() > parentLevel ){
            	    			if(isSelected.booleanValue()){
            	    				setParent = true ;
            	    				break ;
            	    			}
            	    		}else{
            	    			break;
            	    		}
            	    	}
            			
            			ICell cell = tblCompanyForAssigned.getCell(j, "selected");
            			if(!setParent){
            				cell.setValue(Boolean.FALSE);
            			}
						parentLevel--;

					}
				}
			}
		}
    }
	

	/**
	 * ֻѡ�¼���Ŀ
	 * @param row
	 * @param old
	 * @param now
	 */
	public void selectOrgNodesInAssagned(int row, boolean old, boolean now){
    	if(old==now) 
    		return;
    	this.tblCompanyForAssigned.getCell(row, "selected").setValue(Boolean.valueOf(now));
		Integer integer = (Integer) tblCompanyForAssigned.getRow(row).getCell("nodeLevel").getValue();
		
		//IRow selectedRow = tblCompanyForAssigned.getRow(row);
		
		int level= integer.intValue();
		
		//���ѡ�����¼�����֯��Ҫ��ѡ�еģ����ȡ�����¼���ȡ��
		//�¼�
		for(int i=row+1;i<tblCompanyForAssigned.getRowCount();i++){
			
			Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
			
			if(nextLevel.intValue()>level){
				if(now){
					tblCompanyForAssigned.getCell(i, "selected").setValue(Boolean.valueOf(now));
				}
				
			}else{
				break;
			}
		}
		
//		//�ϼ�
		int parentLevel=level-1;
		if(now){
//			for(int i=row-1;i>=0;i--){
//				if(parentLevel==0){
//					break;
//				}
//				Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
//				
//				if(nextLevel.intValue() ==parentLevel){
//					ICell cell = tblCompanyForAssigned.getCell(i, "selected");
//					if(cell.getValue()!=Boolean.TRUE) {
//						cell.setValue(Boolean.TRUE);
//						parentLevel--;
//					}else{
//						break;
//					}
//				}
//			}
		}else{
			//��ѡ��,���ͬ���Ƿ���ѡ���
			//			boolean hasSelected=false;
			//			//�������
			//			for(int i=row+1;i<tblCompanyForAssigned.getRowCount();i++){
			//				Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
			//				
			//				if(nextLevel.intValue()==level){
			//					ICell cell = tblCompanyForAssigned.getCell(i, "selected");
			//					if(cell.getValue()==Boolean.TRUE) {
			//						hasSelected=true;
			//						break;
			//					}else if(nextLevel.intValue()<level){
			//						break;
			//					}
			//				}
			//			}
			//�������
//			if(!hasSelected){
//				for(int i=row-1;i>=0;i--){
//					Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(i).getCell("nodeLevel").getValue();
//					if(nextLevel.intValue()==level){
//						ICell cell = tblCompanyForAssigned.getCell(i, "selected");
//						if(cell.getValue()==Boolean.TRUE) {
//							hasSelected=true;
//							break;
//						}
//					}else if(nextLevel.intValue()<level){
//						row=i;
//						break;
//					}
//				}
//			}

			//			if(!hasSelected){
				//���ø���
				parentLevel=level-1;
				for(int j=row;j>=0;j--){
					if(parentLevel==0){
						break;
					}
					Integer nextLevel = (Integer) tblCompanyForAssigned.getRow(j).getCell("nodeLevel").getValue();
					
					if(nextLevel.intValue()== parentLevel){
						ICell cell = tblCompanyForAssigned.getCell(j, "selected");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
					}
				}
			//			}
		}
    }
	
	
	//��ѡ��ҵ���߼�
	public void selectCostActNodes(int row, boolean old, boolean now){
    	if(old==now) 
    		return;
    	tblAccountForAssigned.getCell(row, "selected").setValue(Boolean.valueOf(now));
		CostAccountInfo acctSelect=(CostAccountInfo)tblAccountForAssigned.getRow(row).getUserObject();
		CostAccountInfo acct=null;
		int level=acctSelect.getLevel();
		//�¼�
    	for(int i=row+1;i<tblAccountForAssigned.getRowCount();i++){
    		acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
    		if(acct.getLevel()>level){
    			if(!Boolean.valueOf(now).booleanValue()){
    				tblAccountForAssigned.getCell(i, "selected").setValue(Boolean.valueOf(now));
    			}
    		}else{
    			break;
    		}
    	}
    	
    	//�ϼ�
    	int parentLevel=level-1;
    	if(now){
        	for(int i=row-1;i>=0;i--){
        		if(parentLevel==0){
        			break;
        		}
        		acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
        		if(acct.getLevel()==parentLevel){
        			ICell cell = tblAccountForAssigned.getCell(i, "selected");
        			if(cell.getValue()!=Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					}else{
						break;
					}
        		}
        	}
    	}else{
    		//��ѡ��,���ͬ���Ƿ���ѡ���
    		boolean hasSelected=false;
    		//�������
        	for(int i=row+1;i<tblAccountForAssigned.getRowCount();i++){
        		acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
        		if(acct.getLevel()==level){
        			ICell cell = tblAccountForAssigned.getCell(i, "selected");
        			if(cell.getValue()==Boolean.TRUE) {
        				hasSelected=true;
        				break;
        			}else if(acct.getLevel()<level){
        				break;
        			}
        		}
        	}
    		//�������
        	if(!hasSelected){
            	for(int i=row-1;i>=0;i--){
            		acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
            		if(acct.getLevel()==level){
            			ICell cell = tblAccountForAssigned.getCell(i, "selected");
            			if(cell.getValue()==Boolean.TRUE) {
            				hasSelected=true;
            				break;
    					}
            		}else if(acct.getLevel()<level){
            			row=i;
            			break;
            		}
            	}
        	}
        	
        	if(!hasSelected){
    			//���ø���
    			parentLevel=level-1;
            	for(int j=row;j>=0;j--){
            		if(parentLevel==0){
            			break;
            		}
            		acct = (CostAccountInfo)tblAccountForAssigned.getRow(j).getUserObject();
            		if(acct.getLevel()==parentLevel){
            			
            			boolean setParent = false ;
            			
            			//�ж��¼��Ƿ���ѡ�е�
            			for(int idx=j+1;idx<tblAccountForAssigned.getRowCount();idx++){
            				CostAccountInfo temps = (CostAccountInfo)tblAccountForAssigned.getRow(idx).getUserObject();
            	    		Boolean isSelected = (Boolean) tblAccountForAssigned.getCell(idx, "selected").getValue();
            	    		
            	    		if(temps.getLevel() > parentLevel ){
            	    			if(isSelected.booleanValue()){
            	    				setParent = true ;
            	    				break ;
            	    			}
            	    		}else{
            	    			break;
            	    		}
            	    	}
            			
            			ICell cell = tblAccountForAssigned.getCell(j, "selected");
            			if(!setParent){
            				cell.setValue(Boolean.FALSE);
            			}
						parentLevel--;
            		}
            	}
        	}
    	}
    }
	
	/**
	 * �ڷ�����ʱ
	 * @param row
	 * @param old
	 * @param now
	 */
	public void selectNoneCostActNodes(int row, boolean old, boolean now){
		if(old==now) 
			return;
		tblAccountForAssigned.getCell(row, "selected").setValue(Boolean.valueOf(now));
		CostAccountInfo acctSelect=(CostAccountInfo)tblAccountForAssigned.getRow(row).getUserObject();
		CostAccountInfo acct=null;
		int level=acctSelect.getLevel();
		boolean isAll = true;
		//�¼�
		for(int i=row+1;i<tblAccountForAssigned.getRowCount();i++){
			acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
			Boolean nextSelect = (Boolean)tblAccountForAssigned.getCell(i, "selected").getValue();
			if(acct.getLevel()>level){
				//ֻ�����¼�
				tblAccountForAssigned.getCell(i, "selected").setValue(Boolean.valueOf(now));
			}else if(acct.getLevel()==level){
				//��ͬ��ʱ���� by hpw
				if(!nextSelect.booleanValue()){
					isAll=false;
				}
				break;
				
			}else{
				break;
			}
		}
		
		//�ϼ�
		int parentLevel=level-1;
		if(now&&isAll){
			for(int i=row-1;i>=0;i--){
				if(parentLevel==0){
					break;
				}
				acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
				if(acct.getLevel()==parentLevel){
					ICell cell = tblAccountForAssigned.getCell(i, "selected");
					if(cell.getValue()!=Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					}else{
						break;
					}
				}else{
					break;
				}
			}
		}else{
			//��ѡ��,���ͬ���Ƿ���ѡ���
			boolean hasSelected=false;
			//�������
			for(int i=row+1;i<tblAccountForAssigned.getRowCount();i++){
				acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
				if(acct.getLevel()==level){
					ICell cell = tblAccountForAssigned.getCell(i, "selected");
					if(cell.getValue()==Boolean.TRUE) {
						hasSelected=true;
						break;
					}else if(acct.getLevel()<level){
						break;
					}
				}
			}
			//�������
			if(!hasSelected){
				for(int i=row-1;i>=0;i--){
					acct = (CostAccountInfo)tblAccountForAssigned.getRow(i).getUserObject();
					if(acct.getLevel()==level){
						ICell cell = tblAccountForAssigned.getCell(i, "selected");
						if(cell.getValue()==Boolean.TRUE) {
							hasSelected=true;
							break;
						}
					}else if(acct.getLevel()<level){
						row=i;
						break;
					}
				}
			}
			
			if(!hasSelected){
				//���ø���
				parentLevel=level-1;
				for(int j=row;j>=0;j--){
					if(parentLevel==0){
						break;
					}
					acct = (CostAccountInfo)tblAccountForAssigned.getRow(j).getUserObject();
					if(acct.getLevel()==parentLevel){
						
						ICell cell = tblAccountForAssigned.getCell(j, "selected");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * װ���¼�������֯���¼�������Ŀ
	 * @param ��ǰ��¼����֯
	 * @author du_yan
	 */
	private void loadCompanyForAssigned(){
		
		Map param = this.getUIContext();
		
		KDTree orgTree = (KDTree) param.get("orgTree");
		
		getChilds(orgTree);
		
	}
	
	private Map companyMap = new HashMap();
	
	/**
	 * @param node
	 */
	public void buildDataToList(DefaultKingdeeTreeNode node){
		
		IRow row = tblCompanyForAssigned.addRow();
		
		if (node.getUserObject() instanceof CurProjectInfo){
			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
			row.getCell("id").setValue(project.getId().toString());
			
			row.getCell("selected").setValue(Boolean.valueOf(false));
			row.getCell("companyNumber").setValue(project.getNumber().trim().replaceAll("!", "\\."));
			
			row.getCell("companyNumber").setUserObject(project);
			row.getCell("companyName").setValue(project.getName());
			
			row.getCell("fullOrgUnit").setValue(project.getFullOrgUnit().getId().toString());
			
			row.getCell("nodeLevel").setValue(new Integer(node.getLevel()));
			
			row.setUserObject(project);
			
			companyMap.put(project.getId().toString(), project);
			
		}else if(node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
			
			row.getCell("id").setValue(org.getUnit().getId().toString());
			
			row.getCell("selected").setValue(Boolean.valueOf(false));
			row.getCell("companyNumber").setValue(org.getLongNumber().trim().replaceAll("!", "\\."));
			
			row.getCell("companyNumber").setUserObject(org.getUnit());
			row.getCell("companyName").setValue(org.getUnit().getName());
			
			row.getCell("nodeLevel").setValue(new Integer(node.getLevel()));
			
			row.setUserObject(org.getUnit());
			
			companyMap.put(org.getUnit().getId().toString(), org.getUnit());
		}
	}
	
	
	/**
	 * @param orgTree
	 */
	private void getChilds(KDTree orgTree){
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) orgTree.getModel().getRoot();
		
		visitAllNodes(orgTree,root);
	}
	
	 /**
	 * @param tree
	 * @param node
	 */
	public void visitAllNodes(KDTree tree,TreeNode node) {
		 tree.makeVisible(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(node)));
		 
		 if (node.getChildCount() >= 0) {
			 for (Enumeration e = node.children(); e.hasMoreElements();) {
				 DefaultKingdeeTreeNode subNode = (DefaultKingdeeTreeNode) e.nextElement();
				 buildDataToList(subNode);
				 visitAllNodes(tree,subNode);
			 }
		 }
	 }

    /*
     * 
     * @see com.kingdee.eas.fdc.basedata.client.AbstractCostAccountNewAssignUI#btnQueryOrg_actionPerformed(java.awt.event.ActionEvent)
     */
    protected void btnQueryOrg_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	QueryFieldConditionEnum queryContion = (QueryFieldConditionEnum) 
    									this.cbxCompanyOrgQueryField.getSelectedItem();
    	int count = this.tblCompanyForAssigned.getRowCount();
    	String txtCondition = this.txtCompanyOrgNumAndName.getText();
    	
    	for(int i=0;i<count;i++){
    		IRow row = tblCompanyForAssigned.getRow(i);
    		if(StringUtils.isEmpty(txtCondition)){
    			row.getStyleAttributes().setHided(false);
        	}else if(QueryFieldConditionEnum.NAME_QUERY.equals(queryContion)){
        		String nameStr = row.getCell("companyName").getValue().toString();
        		
        		if(nameStr.indexOf(txtCondition) != -1){
        			row.getStyleAttributes().setHided(false);
        		}else{
        			row.getStyleAttributes().setHided(true);
        		}
        		
        	}else if(QueryFieldConditionEnum.NUM_QUERY.equals(queryContion)){
        		String numberStr = row.getCell("companyNumber").getValue().toString();
        		
        		if(numberStr.indexOf(txtCondition) != -1){
        			row.getStyleAttributes().setHided(false);
        		}else{
        			row.getStyleAttributes().setHided(true);
        		}
        		
        	}else if(QueryFieldConditionEnum.NUM_NAME_QUEYR.equals(queryContion)){
        		
        		String numberStr = row.getCell("companyNumber").getValue().toString();
        		String nameStr = row.getCell("companyName").getValue().toString();
        		
        		
        		if(nameStr.indexOf(txtCondition) != -1 || numberStr.indexOf(txtCondition) != -1){
        			row.getStyleAttributes().setHided(false);
        		}else{
        			row.getStyleAttributes().setHided(true);
        		}
        	}
    	}
    	//this.loadCompanyForAssigned();    	
    }
    
    protected void btnQueryCostAccount_actionPerformed(ActionEvent e) throws Exception {
    	//this.loadCostAccountDatas();    	
    	this.loadCostAccountSelected();
    }

	/**
	 * ����򷴷��䣬����һ��action
	 */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {        
    	if (this.curOrgIds.isEmpty() && this.curProjectIds.isEmpty()) {
			MsgBox.showInfo(this, "��ѡ��֯������Ŀ");
			return;
		}
    	
        Map costAccountSet = new TreeMap();//ѡ�еĳɱ���ĿMap��key��longNumber��value�ǿ�Ŀid
		Set assagnedCostAccountSet = new HashSet();//ѡ�еĳɱ���Ŀid��
		Map isAddOrgMap = new HashMap(); //�Ƿ����������¼�Map��key��longNumber��value��booleanֵ
        
        for (int i = 0, rowSize = tblAccountForAssigned.getRowCount(); i < rowSize; i++) {
			IRow row = tblAccountForAssigned.getRow(i);
        	boolean selected = ((Boolean) row.getCell("selected").getValue()).booleanValue();
			if (selected && !row.getStyleAttributes().isHided()) {
				costAccountSet.put(row.getCell("account").getValue().toString().replace('.', '!'), row.getCell("id").getValue().toString());
				assagnedCostAccountSet.add(row.getCell("id").getValue().toString());
				CostAccountInfo costActInfo = (CostAccountInfo) row.getUserObject();
				if (costActInfo != null) {
					String longNumber = costActInfo.getLongNumber();
					isAddOrgMap.put(longNumber, row.getCell("isUpperAllowCA").getValue());
				}
        	}
        }
        
        if(costAccountSet.isEmpty()){
        	MsgBox.showInfo(this, "��ѡ��ɱ���Ŀ");
        	return ;
        }
        
        ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
        if (this.assigned) {//�������Ŀ
			StringBuffer errorDetails = new StringBuffer();
			int refType =  0 ;
			
			List errorList = iCostAccountFacade.disAssignAccountBatch(curOrgIds, curProjectIds, costAccountSet);
			Map cantDelMap = (Map) errorList.get(0);
			StringBuffer str = new StringBuffer();
			str.append("���¿�Ŀ�ѷ������ݣ�δ�����䣺");
			if (cantDelMap.keySet() == null || cantDelMap.keySet().size() == 0) {
				FDCMsgBox.showConfirm2("������ɹ�");
			}
			for (Iterator ite = cantDelMap.keySet().iterator(); ite.hasNext();) {
				String name = (String) ite.next();
				str.append(name + "" + cantDelMap.get(name).toString() + ";");
			}
			MsgBox.showDetailAndOK(this, "������ɹ�����鿴��ϸ��Ϣ", str.toString(), 1);

			//			for (int i = 0; errorList != null && i < errorList.size(); i++) {
			//				String error = (String) errorList.get(i);
			//				
			//				String [] errorStr = error.split("_");
			//				
			//				errorDetails.append(errorStr[3]).append("��Ŀ��");
			//				
			//				if("REF3".equals(errorStr[2])){
			//					refType = 3 ;
			//				}else if("REF1".equals(errorStr[2])){
			//					refType = 1 ;
			//				}else if("REF2".equals(errorStr[2])){
			//					refType = 2;					
			//				}
			//				
			//				if(errorStr[0].equals("ORG")){
			//					errorDetails.append("��֯");					
			//				}else if(errorStr[0].equals("PRG")){
			//					errorDetails.append("��Ŀ");
			//				}
			//				errorDetails.append(this.companyMap.get(errorStr[1]));
			//				errorDetails.append("�ϵ�ҵ���������ò���������").append("\r\n");
			//				//				if (companyMap.get(errorStr[1]) == null) {
			//				//					// msgList.add("ORG"+"_"+orgId+"_"+"REF1"+"_"+cai.getName());
			//				//					errorDetails = new StringBuffer("��Ŀ�ѷ�����ȥ���ȷ������¼���ǰ��Ŀ�ٽ��з����������");
			//				//				}
			//			}
			//			
			//			String msg = "";
			//			switch (refType) {
			//			case 1:
			//				msg = "�п�Ŀ����Ŀ�ϵ�ҵ���������ò���������" ;
			//				break;
			//			case 2:
			//				msg = "��Ŀ�ѷ�����ȥ���ȷ������¼���ǰ��Ŀ�ٽ��з����������";
			//				break;
			//			case 3:
			//				msg = "��Ŀ�´��ڷǼ����½�����ϸ�Ŀ�Ŀ������ִ�д˲�����";
			//				break;
			//			default:
			//				break;
			//			}
			//			
			//			if(errorList != null && !errorList.isEmpty()){
			//				MsgBox.showDetailAndOK(this, msg ,errorDetails.toString() , errorList.size());
			//			}else{
			//				MsgBox.showInfo(this, "������ɹ���");
			//			}			
		} else {// �����Ŀ
			List errorList = iCostAccountFacade.assignCostAccountBatch(curOrgIds, curProjectIds, assagnedCostAccountSet,isAddOrgMap);
			
			
			showErrorList2MsgBox(errorList);			
		}
		
		loadCostAccountSelected();
		actionAllDisselect_actionPerformed(e);

    }

	private void showErrorList2MsgBox(List errorList) {
		StringBuffer errorDetails = new StringBuffer();
		for (int i = 0; errorList != null && i < errorList.size(); i++) {
			String error = (String) errorList.get(i);
			
			String [] errorStr = error.split("_");
			if(errorStr[0].equals("ORG")){
				errorDetails.append("����֯");
			}else if(errorStr[0].equals("PRG")){
				errorDetails.append("����Ŀ");
			}
			errorDetails.append(this.companyMap.get(errorStr[2]));
			errorDetails.append("���ڱ����������ͬ�Ŀ�Ŀ").append("\r\n");
		}
		
		if(errorList != null && !errorList.isEmpty()){
			MsgBox.showDetailAndOK(this, "��֯���ڱ����������ͬ�Ŀ�Ŀ",errorDetails.toString() , 1);
		}else{
			MsgBox.showInfo(this, "����ɹ���");			
		}
	}
	

	/**
	 * ������ѷ��䡱
	 */
    public void actionAssigned_actionPerformed(ActionEvent e) throws Exception
    {
    	assigned= true ;
		//loadCostAccountDatas();
		this.btnAssign.setText("������");
		for (int i = 0; i < tblCompanyForAssigned.getRowCount(); i++) {
			tblCompanyForAssigned.getCell(i, "selected").setValue(Boolean.FALSE);
		}
        loadCostAccountSelected();
    }

    /**
	 * �����δ���䡱
	 */
    public void actionNotAssigned_actionPerformed(ActionEvent e) throws Exception
    {
    	assigned= false;
    	for (int i = 0; i < tblCompanyForAssigned.getRowCount(); i++) {
			tblCompanyForAssigned.getCell(i, "selected").setValue(Boolean.FALSE);
		}
		loadCostAccountSelected();
		this.btnAssign.setText("����");
        setIsUpperAllowCAAllSelect(true);
    }

    private void setIsUpperAllowCAAllSelect(boolean isSelect){
    	for (int i = 0; i < this.tblAccountForAssigned.getRowCount(); i++) {
			this.tblAccountForAssigned.getRow(i).getCell("isUpperAllowCA").setValue(Boolean.valueOf(isSelect));
		}
    }
    /**
     * ȫѡ������Ŀ�Ŀ
     */
    public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception
    {
		for (int i = 0; i < this.tblAccountForAssigned.getRowCount(); i++) {
			this.tblAccountForAssigned.getRow(i).getCell("selected").setValue(Boolean.valueOf(true));
		}
    }

    /**
     * ȫ�屻 ����Ŀ�Ŀ
     */
    public void actionAllDisselect_actionPerformed(ActionEvent e) throws Exception
    {
		for (int i = 0; i < this.tblAccountForAssigned.getRowCount(); i++) {
			this.tblAccountForAssigned.getRow(i).getCell("selected").setValue(Boolean.valueOf(false));
		}
    }

    /**
     * ȫѡ������Ĳ�����֯��������Ŀ
     */
    public void actionSelectAllOU_actionPerformed(ActionEvent e) throws Exception
    {
		IRow row = null ;

		if(this.curOrgIds != null && !this.curOrgIds.isEmpty()){
			this.curOrgIds.clear();
		}
		if(this.curProjectIds != null && !this.curProjectIds.isEmpty()){
			this.curProjectIds.clear();
		}
		
		for (int i = 0, count = tblCompanyForAssigned.getRowCount(); i < count; i++) {
			row = tblCompanyForAssigned.getRow(i);
			if (row.getStyleAttributes().isHided()) {
				continue;
			} else {
				row.getCell("selected").setValue(Boolean.valueOf(true));
			}
			String id  =row.getCell("id").getValue().toString();
			
			if(BOSUuid.read(id).getType().equals((new CurProjectInfo()).getBOSType())){
				if(!curProjectIds.contains(id)){
					curProjectIds.add(id);
				}
				
			}else if(BOSUuid.read(id).getType().equals((new FullOrgUnitInfo()).getBOSType())){
				if(!curOrgIds.contains(id)){
					curOrgIds.add(id);
				}
			}
		}
		//loadCostAccountDatas();
		
		loadCostAccountSelected();
    }

    /**
     * ȫ�������Ĳ�����֯��������Ŀ
     */
    public void actionDisselectAllOU_actionPerformed(ActionEvent e) throws Exception
    {
		IRow row;
		curOrgIds=new HashSet();
		//tblAccountForAssigned.removeRows();
		for (int i = 0, count = tblCompanyForAssigned.getRowCount(); i < count; i++) {
			row = tblCompanyForAssigned.getRow(i);
			if (row.getStyleAttributes().isHided()) {
				continue;
			} else {
				row.getCell("selected").setValue(Boolean.valueOf(false));
			}
		}
		if(curOrgIds != null && !curOrgIds.isEmpty()){
			curOrgIds.clear();
		}
		if(curProjectIds != null && !curProjectIds.isEmpty()){
			curProjectIds.clear();
		}
		//loadCostAccountDatas();
		loadCostAccountSelected();
    }

   public void actionSelectAllCA_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = null;
    	for (int i = 0, count = this.tblAccountForAssigned.getRowCount(); i < count; i++) {
			row = tblAccountForAssigned.getRow(i);
			if (row.getStyleAttributes().isHided()) {
				continue;
			} else {
				row.getCell("isUpperAllowCA").setValue(Boolean.valueOf(true));
			}
		}
    }

    public void actionDisSelectAllCA_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = null;
    	for (int i = 0, count = this.tblAccountForAssigned.getRowCount(); i < count; i++) {
			row = tblAccountForAssigned.getRow(i);
			if (row.getStyleAttributes().isHided()) {
				continue;
			} else {
				row.getCell("isUpperAllowCA").setValue(Boolean.valueOf(false));
			}
		}
    }
}