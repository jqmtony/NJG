/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.report.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.report.ProgramContractReportFacadeFactory;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProgramContractReportUI extends AbstractProgramContractReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgramContractReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    
    /**
     * output class constructor
     */
    public ProgramContractReportUI() throws Exception
    {
        super();
//        tblMain.checkParsed();
//        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
        setUITitle("合约规划跟踪单");
     // treeMain
       
    }

    @Override
    public void onLoad() throws Exception {
    	
    	getUIContext().put("RPTFilter",new RptParams());
    	super.onLoad();
    	isOnLoad=true;
		setShowDialogOnLoad(false);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		isOnLoad=false;
		buildProjectTree();
		treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
	            public void valueChanged(TreeSelectionEvent e) {
	                try {
	                    treeMain_valueChanged(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
		tblMain.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent kdtmouseevent) {
				tblMain_tableClicked(kdtmouseevent);
			}
		});
		tblMain.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent kdteditevent) {
				tblMain_editStopped(kdteditevent);
			}
		});
    }
    
    public void tblMain_tableClicked(KDTMouseEvent e) {
    	int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (colIndex == tblMain.getColumnIndex("name") || e.getClickCount() == 1) {
			ICell cell = tblMain.getCell(rowIndex, colIndex);
			if (cell != null) {
				Object value = cell.getValue();
				if (value != null) {
					if (value instanceof CellTreeNode) {
						CellTreeNode node = (CellTreeNode) value;
						node.doTreeClick(tblMain, tblMain.getCell(rowIndex, colIndex));
					}
				}
			}
		}
    }
    
    public void tblMain_editStopped(KDTEditEvent kdteditevent) {
    	int rowIndex = kdteditevent.getRowIndex();
    	int colIndex = kdteditevent.getColIndex();
    	if(colIndex == tblMain.getColumnIndex("sgtRealDate")){
    		dateOverdue(rowIndex,colIndex,"sgtDate","sgtOverdue");
    	}else if(colIndex == tblMain.getColumnIndex("csRealDate")){
    		dateOverdue(rowIndex,colIndex,"csDate","csOverdue");
    	}else if(colIndex == tblMain.getColumnIndex("startRealDate")){
    		dateOverdue(rowIndex,colIndex,"startDate","startOverdue");
    	}else if(colIndex == tblMain.getColumnIndex("endRealDate")){
    		dateOverdue(rowIndex,colIndex,"endDate","endOverdue");
    	}else {
    		dateOverdue(rowIndex,colIndex,"csendDate","csendOverdue");
    	}
    }

	private void dateOverdue(int rowIndex,int colIndex,String planCol,String overdue) {
		if(tblMain.getCell(rowIndex,colIndex).getValue() == null){
			tblMain.getCell(rowIndex,overdue).setValue(null);
			tblMain.getCell(rowIndex,overdue).getStyleAttributes().setBackground(Color.white);
			return;
		}
		Timestamp planDate = (Timestamp)tblMain.getCell(rowIndex,planCol).getValue();
		if(planDate == null){
			tblMain.getCell(rowIndex,overdue).getStyleAttributes().setBackground(Color.red);
		}else if(planDate.compareTo(new Timestamp(((Date)tblMain.getCell(rowIndex,colIndex).getValue()).getTime()))>=0){
			tblMain.getCell(rowIndex,overdue).setValue("否");
			tblMain.getCell(rowIndex,overdue).getStyleAttributes().setBackground(Color.white);
		}else{
			tblMain.getCell(rowIndex,overdue).setValue("是");
			tblMain.getCell(rowIndex,overdue).getStyleAttributes().setBackground(Color.red);
		}
	}
    
    public void buildProjectTree() throws Exception {
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());
		}
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
//    	if(node!=null){
//    		String allSpIdStr = FDCTreeHelper.getStringFromSet(getAllObjectIdMap(node).keySet());
//    		params.setObject("project", allSpIdStr);
//    	}else{
//    		params.setObject("project", null);
//    	}
    	tblMain.removeRows();
    	if(node != null && node.getUserObject() instanceof CurProjectInfo){
    		CurProjectInfo objectInfo = (CurProjectInfo) node.getUserObject();
    		params.setObject("project", objectInfo.getId().toString());
    		query();
    	}
//    	else{
//    		tblMain.removeRows();
//    	}
    }
    protected Map getAllObjectIdMap(TreeNode treeNode) {
		Map idMap = new HashMap();
		fillTreeNodeIdMap(idMap, treeNode);
		return idMap;
	}
    protected void fillTreeNodeIdMap(Map idMap, TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo objectInfo = (CurProjectInfo) thisNode.getUserObject();
			idMap.put(objectInfo.getId().toString(), thisNode);
		}
		int childCount = treeNode.getChildCount();

		while (childCount > 0) {
			fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1));
			childCount--;
		}
	}
 


    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionChart_actionPerformed
     */
    public void actionChart_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionChart_actionPerformed(e);
    }

	@Override
	protected RptParams getParamsForInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommRptBase getRemoteInstance() throws BOSException {
		// TODO Auto-generated method stub
		return ProgramContractReportFacadeFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getTableForPrintSetting() {
		// TODO Auto-generated method stub
		return tblMain;
	}

	@Override
	protected void query() {
//		if(isOnLoad) 
//			return;
//		tblMain.removeRows();
//		MsgBox.showInfo("query()");
		execQueryWithoutProgress();
	}
	
	private void execQueryWithProgress(){
		Window win = SwingUtilities.getWindowAncestor(this);
		LongTimeDialog dialog = null;
		if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
        	 public Object exec()throws Exception{
        		 RptParams rpt = getRemoteInstance().createTempTable(params);
                 RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                 if(tblMain.getHeadRow(0)==null)
                    KDTableUtil.setHeader(header, tblMain);
                 tblMain.getHeadRow(0).setHeight(30);
                 RptParams resultRpt = getRemoteInstance().query(params);
     			 RptRowSet rs = (RptRowSet)resultRpt.getObject("rowset");
     			 if(rs.getRowCount()>0)
     				 KDTableUtil.insertRows(rs, 0, tblMain);
              	 return resultRpt;
             }
             public void afterExec(Object result)throws Exception{
            	 
             }
        });
        dialog.show();
	}
	
	private void execQueryWithoutProgress(){
		try {
			RptParams rpt = getRemoteInstance().createTempTable(params);
			RptTableHeader header = (RptTableHeader)rpt.getObject("header");
			if(tblMain.getHeadRow(0)==null){
				KDTableUtil.setHeader(header, tblMain);
				initTable();
			}
			RptParams resultRpt = getRemoteInstance().query(params);
			RptRowSet rs = (RptRowSet)resultRpt.getObject("rowset");
			if(rs.getRowCount()>0){
				KDTableUtil.insertRows(rs, 0, tblMain);
				rebuildTable();
			}	
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
	}

	private void initTable() {
		tblMain.getColumn("changeRate").getStyleAttributes().setNumberFormat("0.00%");
		tblMain.getColumn("sgtDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("csDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("startDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("endDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("csendDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		//amount
		tblMain.getColumn("planAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		tblMain.getColumn("planAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
		tblMain.getColumn("contralAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		tblMain.getColumn("contralAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
		//date
		KDDatePicker ywDate_DatePicker = new KDDatePicker();
		KDTDefaultCellEditor ywDate_CellEditor = new KDTDefaultCellEditor(ywDate_DatePicker);
		tblMain.getColumn("sgtRealDate").setEditor(ywDate_CellEditor);
		tblMain.getColumn("csRealDate").setEditor(ywDate_CellEditor);
		tblMain.getColumn("startRealDate").setEditor(ywDate_CellEditor);
		tblMain.getColumn("endRealDate").setEditor(ywDate_CellEditor);
		tblMain.getColumn("csendRealDate").setEditor(ywDate_CellEditor);
		tblMain.getColumn("sgtRealDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("csRealDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("startRealDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("endRealDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		tblMain.getColumn("csendRealDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	}
	
	public void rebuildTable(){
		tblMain.getColumn("sgtRealDate").getStyleAttributes().setLocked(false);
		tblMain.getColumn("csRealDate").getStyleAttributes().setLocked(false);
		tblMain.getColumn("startRealDate").getStyleAttributes().setLocked(false);
		tblMain.getColumn("endRealDate").getStyleAttributes().setLocked(false);
		tblMain.getColumn("csendRealDate").getStyleAttributes().setLocked(false);
		int rowCount = tblMain.getRowCount();
		IRow row = null;
		String name = null;
		for (int i = 0; i < rowCount; i++) {
			row = tblMain.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			name = getCellValue(tblMain, i, "name");
			if (name != null && name.trim().length() > 0) {
				row.getCell("name").setValue(createCellTreeNode(name,level,isLeaf(getCellValue(tblMain,i,"longNumber"),tblMain,"headNumber")));
			}
		}
	}
	
	private String getCellValue(KDTable table, int rowIndex, String colName) {
		if (table == null || colName == null)
			return null;
		ICell cell = table.getCell(rowIndex, colName);
		if (cell == null)
			return null;
		Object value = cell.getValue();
		String result = null;
		if (value instanceof CellTreeNode)
			result = (String) ((CellTreeNode) value).getValue();
		else
			result = value == null ? "" : value.toString();
		return result == null ? null : result.trim();
	}

	private boolean isLeaf(String parentLongNumber, KDTable table, String colHeadNumber) {
		boolean result = true;
		if (parentLongNumber == null || parentLongNumber.length() == 0)
			return result;
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String number = getCellValue(table, i, colHeadNumber);
			if (number == null)
				continue;
			if (number.startsWith(parentLongNumber)) {
				result = false;
				break;
			}
		}
		return result;
	}
	private CellTreeNode createCellTreeNode(String name, int level, boolean isLeaf) {
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(name);
		treeNode.setTreeLevel(level);
		treeNode.setCollapse(false);
		treeNode.setHasChildren(!isLeaf);
		return treeNode;
	}
	
	@Override
	public void tableDataRequest(KDTDataRequestEvent kre) {
		MsgBox.showInfo("tableDataRequest()");
		if(isQuery) return;
		isQuery=true;
		execQueryWithoutProgress();	
			
        isQuery = false;
	}

}