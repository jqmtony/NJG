/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostCollection;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEcostFactory;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryCollection;
import com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointEntryFactory;
import com.kingdee.eas.fdc.costindexdb.database.FieldType;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostAccountPriceIndexEditUI extends AbstractCostAccountPriceIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountPriceIndexEditUI.class);
    private Set<String> pointSets = null;
    /**
     * output class constructor
     */
    public CostAccountPriceIndexEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTable();
    	actionSubmit.setVisible(false);
    	btnSave.setIcon(btnSubmit.getIcon());
    	btnSave.setText(btnSubmit.getText());
    	btnSave.setToolTipText(btnSubmit.getToolTipText());
    	String oql="select pointName where parent.id in(select fid from CT_COS_BaseAndSinglePoint where CFIsLatest='1' and CFProjectId='"+editData.getCurProject().getId().toString()+"')";
    	BaseAndSinglePointEntryCollection entrys=BaseAndSinglePointEntryFactory.getRemoteInstance().getBaseAndSinglePointEntryCollection(oql);
    	BaseAndSinglePointEcostCollection costs=BaseAndSinglePointEcostFactory.getRemoteInstance().getBaseAndSinglePointEcostCollection(oql);
    	pointSets = new HashSet<String>();
    	for(int i = entrys.size()-1; i >= 0; i--) {
    		pointSets.add(entrys.get(i).getPointName().trim());
		}
    	for(int i = costs.size()-1; i >= 0; i--) {
    		pointSets.add(costs.get(i).getPointName().trim());
		}
    }
    
    public void initTable(){
    	kdtEntrys.getColumn("fcontent").getStyleAttributes().setLocked(true);
    	kDContainer1.getContentPane().remove(kdtEntrys_detailPanel);
    	kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
    	KDWorkButton addLine = new KDWorkButton("新增行");
		KDWorkButton insetLine = new KDWorkButton("插入行");
		KDWorkButton removeLine = new KDWorkButton("删除行");
		addLine.setName("addLine");
		insetLine.setName("insetLine");
		removeLine.setName("removeLine");
		addLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		insetLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		removeLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		kDContainer1.addButton(addLine);
		kDContainer1.addButton(insetLine);
		kDContainer1.addButton(removeLine);
		MyActionListener baseAL = new MyActionListener(kdtEntrys);
		addLine.addActionListener(baseAL);
		insetLine.addActionListener(baseAL);
		removeLine.addActionListener(baseAL);
		kdtEntrys.addKDTEditListener(new KDTEditAdapter(){
    		public void editStopped(KDTEditEvent e) {
    			kdtEntry_editStopped(e);
    		}
    	});
		
    }

	private void setContentStyle() {
		for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
			if(FieldType.COMPUTE.equals(kdtEntrys.getCell(i,"fieldType").getValue())){
    			kdtEntrys.getCell(i,"fcontent").getStyleAttributes().setBackground(kdtEntrys.getRequiredColor());
    			kdtEntrys.getCell(i,"fcontent").getStyleAttributes().setLocked(false);
    		}
		}
	}
    
    public void kdtEntry_editStopped(KDTEditEvent e) {
    	int rowIndex = e.getRowIndex();
    	int colIndex = e.getColIndex();
//    	if(colIndex==kdtEntrys.getColumnIndex("fieldHide") && (Boolean)kdtEntrys.getCell(rowIndex,colIndex).getValue()){
//    		kdtEntrys.getCell(rowIndex,"fieldInput").setValue(Boolean.FALSE);
//    	}else if(colIndex==kdtEntrys.getColumnIndex("fieldInput") && (Boolean)kdtEntrys.getCell(rowIndex,colIndex).getValue()){
//    		kdtEntrys.getCell(rowIndex,"fieldHide").setValue(Boolean.FALSE);
//    	}
    	if(colIndex==kdtEntrys.getColumnIndex("fieldType") && kdtEntrys.getCell(rowIndex,colIndex).getValue()!=null){
    		if(FieldType.COMPUTE.equals(kdtEntrys.getCell(rowIndex,colIndex).getValue())){
    			kdtEntrys.getCell(rowIndex,"fcontent").getStyleAttributes().setBackground(kdtEntrys.getRequiredColor());
    			kdtEntrys.getCell(rowIndex,"fcontent").getStyleAttributes().setLocked(false);
    		}
    	}
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	setContentStyle();
    }
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	for(int i = 0; i < kdtEntrys.getRowCount3(); i++) {
    		if(FDCHelper.isEmpty(kdtEntrys.getCell(i,"fieldName").getValue())){
    			MsgBox.showInfo("第"+(i+1)+"行字段名称不能为空！");
    			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("fieldName"));
    			SysUtil.abort();
    		}
    		if(kdtEntrys.getCell(i,"fieldType").getValue() == null){
    			MsgBox.showInfo("第"+(i+1)+"行字段类型不能为空！");
    			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("fieldType"));
    			SysUtil.abort();
    		}else if(FieldType.COMPUTE.equals(kdtEntrys.getCell(i,"fieldType").getValue())){
    			String fcontent = (String)kdtEntrys.getCell(i,"fcontent").getValue();
    			if(FDCHelper.isEmpty(fcontent)){
    				MsgBox.showInfo("第"+(i+1)+"行公式内容不能为空！");
        			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("fcontent"));
        			SysUtil.abort();
    			}
    			int index1 = fcontent.indexOf('/');
    			if(index1 != -1){
    				String fenzi = fcontent.substring(0,index1);
    				String fenmu = fcontent.substring(index1+1);
    				if(!pointSets.contains(fenzi)){
    					MsgBox.showInfo("第"+(i+1)+"行公式内容中的\""+fenzi+"\"与单项要素基本要素无匹配项！");
            			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("fcontent"));
            			SysUtil.abort();
    				}
    				if(!pointSets.contains(fenmu)){
    					MsgBox.showInfo("第"+(i+1)+"行公式内容中的\""+fenmu+"\"与单项要素基本要素无匹配项！");
            			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("fcontent"));
            			SysUtil.abort();
    				}
    			}else{
    				MsgBox.showInfo("第"+(i+1)+"行公式内容不符合要求！");
        			kdtEntrys.getEditManager().editCellAt(i,kdtEntrys.getColumnIndex("fcontent"));
        			SysUtil.abort();
    			}
    		}
		}
    }
    
    class MyActionListener implements ActionListener{
    	private KDTable table;
    	
    	public MyActionListener() {
		}
    	public MyActionListener(KDTable tab) {
    		table = tab;
		}
    	public void actionPerformed(ActionEvent e) {
    		String type = ((KDWorkButton)e.getSource()).getName();
    		IRow row = null;
    		if("addLine".equals(type)){
    			row = table.addRow();
    			row.getCell("fieldHide").setValue(Boolean.FALSE);
    			row.getCell("fieldInput").setValue(Boolean.FALSE);
    		}else if("insetLine".equals(type)){
    			if(table.getSelectManager().size() > 0) {
    	            int top = table.getSelectManager().get().getTop();
    	            if(isTableColumnSelected(table))
    	            	row = table.addRow();
    	            else
    	            	row = table.addRow(top);
    	        } else {
    	        	row = table.addRow();
    	        }
    			row.getCell("fieldHide").setValue(Boolean.FALSE);
    			row.getCell("fieldInput").setValue(Boolean.FALSE);
    		}else if("removeLine".equals(type)){
    			if(table.getSelectManager().size() == 0){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        int top = table.getSelectManager().get().getTop();
    	        if(table.getRow(top) == null){
    	            MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
    	            return;
    	        }
    	        table.removeRow(top);
    		}
    	}
    }
    
    protected final boolean isTableColumnSelected(KDTable table){
        if(table.getSelectManager().size() > 0) {
            KDTSelectBlock block = table.getSelectManager().get();
            if(block.getMode() == 4 || block.getMode() == 8)
                return true;
        }
        return false;
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    	setContentStyle();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        DefaultKingdeeTreeNode costNode=(DefaultKingdeeTreeNode)getUIContext().get("costAccountNode");
        objectValue.setCostAccount((CostAccountInfo)costNode.getUserObject());
        objectValue.setCurProject((CurProjectInfo)getUIContext().get("curProjectNode"));
        return objectValue;
    }

}