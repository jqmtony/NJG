/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.report.groupDynamic.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.aimcost.report.groupDynamic.GroupDynamicReportFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class GroupDynamicReportUI extends AbstractGroupDynamicReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(GroupDynamicReportUI.class);
    boolean isQuery = false;
    /**
     * output class constructor
     */
    public GroupDynamicReportUI() throws Exception
    {
        super();
        kDTable1.getDataRequestManager().addDataRequestListener(this);
		kDTable1.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		kDTable1.getStyleAttributes().setLocked(true);
		kDTable1.checkParsed();
		enableExportExcel(kDTable1);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
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
		return null;
	}

	@Override
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new GroupDynamicReportFilterUI();
	}

	@Override
	protected ICommRptBase getRemoteInstance() throws BOSException {
		return GroupDynamicReportFacadeFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getTableForPrintSetting() {
		return kDTable1;
	}

	@Override
	protected void query() {
		kDTable1.removeColumns();
		kDTable1.removeRows();
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		if(isQuery)
			return;
		isQuery = true;
		Window win = SwingUtilities.getWindowAncestor(this);
		LongTimeDialog dialog = null;
		if (win instanceof Frame) {
			dialog = new LongTimeDialog((Frame) win);
		} else if (win instanceof Dialog) {
			dialog = new LongTimeDialog((Dialog) win);
		}
		if (dialog == null) {
			dialog = new LongTimeDialog(new Frame());
		}
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				RptParams resultRpt = getRemoteInstance().query(params);
				return resultRpt;
			}
			public void afterExec(Object result) throws Exception {
				RptParams rpt = getRemoteInstance().createTempTable(params);
				RptTableHeader header = (RptTableHeader) rpt.getObject("header");
				KDTableUtil.setHeader(header, kDTable1); 
				
				Map preMonthMap = (Map) ((RptParams) result).getObject("preMap");
				Map curMonthMap = (Map) ((RptParams) result).getObject("curMap");
				
				Set<String> preKeySet = preMonthMap.keySet();
				Set<String> curKeySet = curMonthMap.keySet();
				HashSet set = new HashSet();
				for(Iterator it = preKeySet.iterator();it.hasNext();) {
					set.add(it.next().toString());
				}
				for(Iterator it = curKeySet.iterator();it.hasNext();) {
					set.add(it.next().toString());
				}
				
				for(Iterator it = set.iterator(); it.hasNext();) {
					String key = it.next().toString();
					Map detailMap = (Map) preMonthMap.get(key);
					if(detailMap != null) {
						IRow addRow = kDTable1.addRow();
						addRow.getCell("companyName").setValue(detailMap.get("company"));
						addRow.getCell("faimCost").setValue(detailMap.get("aimCost"));
						addRow.getCell("fdynamicCost").setValue(detailMap.get("dynamicCost"));
						addRow.getCell("fdiffAmount").setValue(detailMap.get("diffAmount"));
						addRow.getCell("fdiffRate").setValue(detailMap.get("diffRate"));
						
						Map curDetailMap = (Map) curMonthMap.get(key);
						if(curDetailMap != null) {
							addRow.getCell("saimCost").setValue(curDetailMap.get("aimCost"));
							addRow.getCell("sdynamicCost").setValue(curDetailMap.get("dynamicCost"));
							addRow.getCell("sdiffAmount").setValue(curDetailMap.get("diffAmount"));
							addRow.getCell("sdiffRate").setValue(curDetailMap.get("diffRate"));
						}
					} else {
						Map curDetailMap = (Map) curMonthMap.get(key);
						if(curDetailMap != null) {
							IRow addRow = kDTable1.addRow();
							addRow.getCell("companyName").setValue(curDetailMap.get("company"));
							addRow.getCell("saimCost").setValue(curDetailMap.get("aimCost"));
							addRow.getCell("sdynamicCost").setValue(curDetailMap.get("dynamicCost"));
							addRow.getCell("sdiffAmount").setValue(curDetailMap.get("diffAmount"));
							addRow.getCell("sdiffRate").setValue(curDetailMap.get("diffRate"));
						}
					}

				}
			}
		});
		dialog.show();
		isQuery = false;
	}
}