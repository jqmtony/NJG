/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.IIncomeAccountFacade;
import com.kingdee.eas.fdc.basedata.IncomeAccountFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class IncomeAccountAssignUI extends AbstractIncomeAccountAssignUI
{
    private static final Logger logger = CoreUIObject.getLogger(IncomeAccountAssignUI.class);
    
    private static final Color canntSelectColor = new Color(0xFEFED3);
    private String curOrgId = null;
    private boolean isProject = false; 
    
    /**
     * output class constructor
     */
    public IncomeAccountAssignUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	tblCuForAssigned.checkParsed();
    	tblCuForAssigned.getColumn("longNumber").setWidth(230);
    	tblCuForAssigned.getColumn("Name").setWidth(300);
    	FDCTableHelper.disableDelete(tblCuForAssigned);
    	fillTable();
    	
    	this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		this.btnAllDisselect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
    	if(isProject){
    		//PT029255:֧���û�����ѡ��
//    		this.btnAllSelect.setEnabled(false);
//    		this.btnAllDisselect.setEnabled(false);
    	}
    	tblCuForAssigned.getColumn("longNumber").getStyleAttributes().setLocked(false);
    }
    /**
     * output actionAssign_actionPerformed
     */
    public void actionAssign_actionPerformed(ActionEvent e) throws Exception
    {
    	//super.actionAssign_actionPerformed(e);
    	Set set = new HashSet();
    	IRow row;
        for(int i=0,count=this.tblCuForAssigned.getRowCount();i<count;i++){
        	row = this.tblCuForAssigned.getRow(i);
        	if(((Boolean)row.getCell("selected").getValue()).booleanValue()){
        		String nodeId = row.getCell("id").getValue().toString();
        		set.add(nodeId);
        	}
        }
        if(set.size() == 0){
        	MsgBox.showWarning("û�з���Ŀ��");
        	return;
        }
        IIncomeAccountFacade iIncomeAccountFacade = IncomeAccountFacadeFactory.getRemoteInstance();
        List errorlist = iIncomeAccountFacade.assignToNextLevel(new ObjectStringPK(curOrgId),set,isProject);
        if(errorlist == null || errorlist.size() == 0){
        	MsgBox.showInfo("�������");
        }else{
        	showErrorInfo(errorlist);
        }
    }
    
    public void fillTable() throws Exception {
    	tblCuForAssigned.removeRows();
    	//������֯����������Ŀ��
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder(false);
		treeBuilder.setDevPrjFilter(false);//������Ŀ
		KDTree tree = new KDTree();
		treeBuilder.build(this, tree, this.actionOnLoad);
		//��ȡ��ǰ��֯�ڵ�
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) tree.getModel().getRoot();
		OrgStructureInfo oui = (OrgStructureInfo) root.getUserObject();
		FullOrgUnitInfo info = oui.getUnit();
		curOrgId = info.getId().toString();
		//������֯����������Ŀ��
		fillNode(tblCuForAssigned, root);
	}
    
	public void fillNode(KDTable table, DefaultKingdeeTreeNode parentNode) throws BOSException {
		CellTreeNode cellTreeNode=null;
		IRow row = null;
		int level = 0;
		boolean isLeaf = false;
		
		for (int i = 0; i < parentNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) parentNode.getChildAt(i);
			if (node.getUserObject() instanceof CurProjectInfo) {
				isProject = true;
				lblCuForAssigned.setTitle("������Ĺ�����Ŀ");
				row = table.addRow();
				cellTreeNode=new CellTreeNode();
				CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
				row.setUserObject(projectInfo);
				row.getCell("id").setValue(projectInfo.getId().toString());
				row.getCell("selected").setValue(Boolean.valueOf(true));
//				row.getCell("longNumber").setValue(projectInfo.getLongNumber().trim().replaceAll("!", "\\."));
				row.getCell("Name").setValue(projectInfo.getName());
				
				cellTreeNode.setValue(projectInfo.getLongNumber().trim().replaceAll("!", "\\."));
				level = projectInfo.getLevel()-1;
				isLeaf = projectInfo.isIsLeaf();
				
				cellTreeNode.setTreeLevel(level);
				
				if(isLeaf){
					cellTreeNode.setHasChildren(false);
					if(level>1){
						row.getStyleAttributes().setHided(true);
					}
				}else{
					if(level<=1){
						if(level==0){
							cellTreeNode.setCollapse(false);
						}else{
							cellTreeNode.setCollapse(true);
						}
					}else{
						cellTreeNode.setCollapse(true);//�Ƿ�ֻ���ظ����
						row.getStyleAttributes().setHided(true);
					}
					cellTreeNode.addClickListener(new NodeClickListener(){
						public void doClick(CellTreeNode source, ICell cell,
								int type) {
							tblCuForAssigned.revalidate();
							
						}
					});
					cellTreeNode.setHasChildren(true);
					row.getStyleAttributes().setBackground(canntSelectColor);
				}
				row.getCell("longNumber").setValue(cellTreeNode);
				if (!node.isLeaf()) {
					this.fillNode(table,node);
				}
			}else{				
				isProject = false;
				lblCuForAssigned.setTitle("���������֯��Ԫ");
				row = table.addRow();
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				FullOrgUnitInfo info = oui.getUnit();
				row.setUserObject(info);
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("selected").setValue(Boolean.valueOf(false));
				row.getCell("longNumber").setValue(info.getLongNumber().trim().replaceAll("!", "\\."));
				row.getCell("Name").setValue(info.getName());
				row.getStyleAttributes().setBackground(canntSelectColor);
			}
		}
	}
	
	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractIncomeAccountAssignUI#actionAllDisselect_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAllDisselect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblCuForAssigned.getRowCount(); i++) {
			this.tblCuForAssigned.getRow(i).getCell("selected").setValue(Boolean.valueOf(false));
		}
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractIncomeAccountAssignUI#actionAllSelect_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblCuForAssigned.getRowCount(); i++) {
			this.tblCuForAssigned.getRow(i).getCell("selected").setValue(Boolean.valueOf(true));
		}
	}

	private void showErrorInfo(List errorlist) {
		StringBuffer errorStr = new StringBuffer();
		String sep = "\n";
		for (Iterator iter = errorlist.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			errorStr.append(element);
			errorStr.append(sep);
		}
		String title = "���ֿ�Ŀ�������ϼ��Ѿ������ݣ�δ����ɹ��������Ѿ�����ɹ���δ����ɹ��Ŀ�Ŀ��鿴��ϸ��Ϣ";
		
		String error = errorStr.toString();
		error = error.replace('!', '.');
		MsgBox.showDetailAndOK(this, title, error, 1);
	}
	protected void tblCuForAssigned_editValueChanged(KDTEditEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.tblCuForAssigned_editValueChanged(e);
		if(e.getColIndex()==tblCuForAssigned.getColumnIndex("selected")){
			Boolean old=(Boolean)e.getOldValue();
			Boolean now=(Boolean)e.getValue();
			if(isProject){
				selectProject(e.getRowIndex(),old.booleanValue(),now.booleanValue());
			}else{
				//��˾ֻ���ڵ�������
			}
		}
	}
	public void selectProject(int row, boolean old, boolean now){
    	if(old==now) return;
    	tblCuForAssigned.getCell(row, "selected").setValue(Boolean.valueOf(now));
		CurProjectInfo infoSelect=(CurProjectInfo)tblCuForAssigned.getRow(row).getUserObject();
		CurProjectInfo info=null;
		int level=infoSelect.getLevel();
		//�¼�
    	for(int i=row+1;i<tblCuForAssigned.getRowCount();i++){
    		info = (CurProjectInfo)tblCuForAssigned.getRow(i).getUserObject();
    		if(info.getLevel()>level){
    			tblCuForAssigned.getCell(i, "selected").setValue(Boolean.valueOf(now));
    			
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
        		info = (CurProjectInfo)tblCuForAssigned.getRow(i).getUserObject();
        		if(info.getLevel()==parentLevel){
        			ICell cell = tblCuForAssigned.getCell(i, "selected");
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
        	for(int i=row+1;i<tblCuForAssigned.getRowCount();i++){
        		info = (CurProjectInfo)tblCuForAssigned.getRow(i).getUserObject();
        		if(info.getLevel()==level){
        			ICell cell = tblCuForAssigned.getCell(i, "selected");
        			if(cell.getValue()==Boolean.TRUE) {
        				hasSelected=true;
        				break;
        			}else if(info.getLevel()<level){
        				break;
        			}
        		}
        	}
    		//�������
        	
        	if(!hasSelected){
            	for(int i=row-1;i>=0;i--){
            		info = (CurProjectInfo)tblCuForAssigned.getRow(i).getUserObject();
            		if(info.getLevel()==level){
            			ICell cell = tblCuForAssigned.getCell(i, "selected");
            			if(cell.getValue()==Boolean.TRUE) {
            				hasSelected=true;
            				break;
    					}
            		}else if(info.getLevel()<level){
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
            		info = (CurProjectInfo)tblCuForAssigned.getRow(j).getUserObject();
            		if(info.getLevel()==parentLevel){
            			ICell cell = tblCuForAssigned.getCell(j, "selected");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
            		}
            	}
    		
        	}

    	}
		
    }

}