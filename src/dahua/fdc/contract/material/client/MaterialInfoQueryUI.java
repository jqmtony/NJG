/*
 * @(#)MaterialInfoQueryUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * @description     材料信息库条件查询<p>
 * @author  		杜仕超
 * @version 		EAS 7.0
 * @see 
 */
public class MaterialInfoQueryUI extends AbstractMaterialInfoQueryUI
{
	/**缺省版本标识*/
    private static final long serialVersionUID = -519065646603942603L;
	/**定义资源文件路径*/
	private static final String RESOURC_FILE_PATH = "com/kingdee/eas/fdc/material/MaterialIndexResource";
	/**定义查询主界面 */
	private CommonQueryDialog commonQueryDialog = null;
	
	/**
	 * @description  初始化查询界面，返回true则先弹出查询界面，否则先弹出listUI
	 * @author       杜仕超
	 * @createDate   2010年11月18日 21时44分55秒
	 * @param
	 * @return 		 true
	 * @version 	 EAS7.0
	 * @see
	 */
	protected boolean initDefaultFilter() {

		return true;
	}
	
	 /**
	  * 
	  * @description    执行查询	
	  * @author		   	杜仕超
	  * @createDate		2010-11-21
	  * @param	
	  * @return					
	  * @version		EAS7.0
	  * @see
	  */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK IMPK,EntityViewInfo viewInfo) { 
		
		/*获取当前登录的CU.id的值*/
		FilterItemInfo  cuIdInfo  = new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim());
		EntityViewInfo ev = new EntityViewInfo(); 	
		FilterInfo filter = new FilterInfo();
		/*将viewInfo 转成字符串 再截取,替换*/
		String sqlStrAll = viewInfo.toString().trim(); 
		/*截取最后的排序条件*/
		String orderBy = sqlStrAll.substring(sqlStrAll.indexOf("ORDER BY"),sqlStrAll.length());
		String sqlStr = "";
		/*判断字符串sqlStrAll 中是否有 "AND (CU.id" 子串 如果有就截取它前面的串出来*/
		if(-1 != sqlStrAll.indexOf("AND (CU.id")){
			 sqlStr = sqlStrAll.substring(0,sqlStrAll.indexOf("AND (CU.id"));
		}
		/*去掉过滤页签中报价日期    时，分，钞*/
		if(sqlStr != null && sqlStr.indexOf("quoteTime = {TS '") != -1){ 
		    
				String oldStr = sqlStr.substring(sqlStr.indexOf("quoteTime = {TS '"),sqlStr.indexOf("quoteTime = {TS '")+39).toString().trim();
				/*获取当前日期*/
				String oldDate = oldStr.substring(oldStr.length()-21, oldStr.length()-11);
				/*String oldTime = oldStr.substring(oldStr.length()-11,oldStr.length()-2);*/
				String newTime = " 00:00:00";			
				/*分别获取当前日期的年,月,日*/
				int oldYYYY = new Integer(oldDate.substring(0, oldDate.length()-6)).intValue();
				int oldMM = new Integer(oldDate.substring(oldDate.length()-5, oldDate.length()-3)).intValue();
				int oldDD = new Integer(oldDate.substring(oldDate.length()-2, oldDate.length())).intValue();
				/*实例化一个日历 并把当前日期设入进去*/
				Calendar cal = Calendar.getInstance() ;
				cal.set(oldYYYY, oldMM,oldDD); 	    
				/*获取当前日期的下一天并分别获取出新日期的年,月,日*/
			    cal.add(Calendar.DAY_OF_MONTH, +1); 
			    int newYYYY = cal.get(Calendar.YEAR);
			    int newMM = cal.get(Calendar.MONTH);
			    int newDD = cal.get(Calendar.DATE);  
			    String newDate = new Integer(newYYYY).toString()+"-"+ new Integer(newMM).toString()+"-"+ new Integer(newDD).toString(); 
				/*分别截取出以当前所选时间的前,中,后三部分再合并*/
				String startStr = sqlStr.substring(0,sqlStr.indexOf("quoteTime = {TS '"));
				String endStr = sqlStr.substring(sqlStr.indexOf("quoteTime = {TS '")+39,sqlStr.length());
				String middleStr = "(quoteTime >= {TS '"+oldDate+" "+newTime+"'} AND quoteTime < {TS '"+newDate+" "+newTime+"'})";
				String sql = startStr + middleStr + endStr;
				/*FilterItemCollection sqlStr = arg1.getFilter().getFilterItems();
				for(Iterator it = sqlStr.iterator();it.hasNext();){ */
				try {
					ev = new EntityViewInfo(sql);   
					/*把CU.id的值添加到后面作为过滤条件*/
					filter.getFilterItems().add(cuIdInfo);
					ev.getFilter().mergeFilter(filter,"AND"); 
					/*加上最后的Order By 排序条件*/
					ev = new EntityViewInfo(ev.toString()+ orderBy );
					return super.getQueryExecutor(IMPK, ev); 
				} catch (Exception e) {
					handUIException(e);
				}
		}
		/*把CU.id的值添加到后面作为过滤条件*/
		filter.getFilterItems().add(cuIdInfo);
		try {
			/*判断是否为空*/
			if(null == sqlStr || "".equals(sqlStr)){
				/*为空时添加过滤条件并加上最后的Order By 排序条件*/
				ev.setFilter(filter); 
				ev = new EntityViewInfo(ev.toString()+ orderBy );
			}else{
				/*不为空时添加不同的过滤条件*/
				ev = new EntityViewInfo(sqlStr);
				ev.getFilter().mergeFilter(filter,"AND");
				ev = new EntityViewInfo(ev.toString()+ orderBy );
			}
		} catch (Exception e) {
			/*使用基类的处理异常*/
			handUIException(e);
		}
		return super.getQueryExecutor(IMPK, ev); 
	}
	/**
	 * 
	 * @description		初始化表格字段显示格式
	 * @author			杜仕超
	 * @createDate		2010-12-6
	 * @param	
	 * @return					
	 * @version			EAS7.0	
	 * @see
	 */
	private void initFormat() {

		/*隐藏id列*/
		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
		/*设置日期显示格式*/
		this.tblMain.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-mm-dd");
		this.tblMain.getColumn("validDate").getStyleAttributes().setNumberFormat("yyyy-mm-dd");
		this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-mm-dd");
		this.tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat("yyyy-mm-dd"); 
		/*设置单价为数字显示格式并靠右对齐*/
		this.tblMain.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("price").getStyleAttributes().setNumberFormat("#,###.00");  
		/*灰按钮*/
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false); 
	}
	
	/**
	 * 
	 * @description		onShow
	 * @author			杜仕超
	 * @createDate		2010-11-18
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	public void onShow() throws Exception {
		
			super.onShow();
			/*格式化*/
			this.initFormat();
			/*调用自设showState方法*/
			this.showState();
	}
	
	/**
	 * 
	 * @description		状态显示设置为核准或未核准两种状态
	 * @author			杜仕超
	 * @createDate		2010-11-29
	 * @param	
	 * @return			void		
	 * @version			EAS7.0
	 * @see
	 */
	private void showState() {
		/*获取table 所有行并状态显示设置为核准或未核准两种状态*/
		 int rowCount = this.tblMain.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				/*非空判断*/
				if (null != this.tblMain.getRow(i).getCell("state").getValue()) {
					
					String state = this.tblMain.getRow(i).getCell("state").getValue().toString();
					/*比较值是否相等,是就源设置为核准状态 不相等就设置为未核准 状态*/
					if (EASResource.getString(RESOURC_FILE_PATH, "isPing").equals(state) || EASResource.getString(RESOURC_FILE_PATH, "herz").equals(state)) {
						state = EASResource.getString(RESOURC_FILE_PATH, "herz");
					} else {
						state = EASResource.getString(RESOURC_FILE_PATH, "notHerz");
					}
					/*重新设值状态*/
					this.tblMain.getRow(i).getCell("state").setValue(state);
				}
		} 
	}
		 
	/**
	 * @description		 点击 表 事件 
	 * @author			 杜仕超
	 * @createDate 		 2010年11月18日 21时45分10秒
	 * @param
	 * @return			 
	 * @version			 EAS7.0
	 * @see
	 */
	protected void tblMain_tableClicked(	com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)throws Exception {
			
		super.tblMain_tableClicked(e);
		/*调用自设showState方法*/
		this.showState();
	}

	/**
	 * @description		 初始化查询主界面
	 * @author			 杜仕超
	 * @createDate 		 2010年11月18日 21时45分10秒
	 * @param
	 * @return			 
	 * @version			 EAS7.0
	 * @see
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (null !=commonQueryDialog) {
			/*去掉排序选项*/
			commonQueryDialog.setShowSorter(false);
			/*去掉表格设置选项*/
	    	commonQueryDialog.setUiObject(null);
    		return commonQueryDialog;
    	} 
    	commonQueryDialog = super.initCommonQueryDialog();
    	
    	commonQueryDialog.setShowSorter(false);
    	/*去掉表格设置选项*/
    	commonQueryDialog.setUiObject(null);
    	try {
    		/*调用基类方法*/
			commonQueryDialog.init();
			commonQueryDialog.getCommonQueryParam().setDirty(false);
		} catch (UIException e) {
			this.handleException(e);
		}
    	return commonQueryDialog;
	}
    
    /**
     * output class constructor
     */
    public MaterialInfoQueryUI() throws Exception
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
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
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
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }
    /**
     * output actoingetBizInterface_actionPerformed
     */

	protected ICoreBase getBizInterface() throws Exception {
		return MaterialInfoFactory.getRemoteInstance();
	} 
	/**
	 * output actoingetEditUIName_actionPerformed
	 */
	protected String getEditUIName() {
		return null;
	}
	 
	/**
     * output actoingetEditUIModal_actionPerformed
     */
	protected String getEditUIModal() {
			return super.getEditUIModal();
	} 
}