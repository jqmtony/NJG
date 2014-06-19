/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RptParamConst;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProjectIndexDataRptMainUI extends AbstractProjectIndexDataRptMainUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectIndexDataRptMainUI.class);
    
    /**
     * output class constructor
     */
    public ProjectIndexDataRptMainUI() throws Exception
    {
        super();
    }

    public void onLoad()throws Exception{
    	super.onLoad();
    	FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionPrint");
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    }
    
    protected boolean isShowDimensionNavigator()
    {
    	return false;
    }
    
    protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
		return new ProjectIndexDataFilterUI(this, this.actionOnLoad);
	}

	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		return ProjectIndexDataRptFacadeFactory.getRemoteInstance();
	}

	protected RptParams getParamsForInit() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected RptParams getParamsForRequest() {
		
		EntityViewInfo result = getQueryDialog().getEntityViewInfoResult();
		
		if(result != null && result.getFilter() != null && result.getFilter().getFilterItems().size() > 0) {
			params.setObject(RptParamConst.KEY_CUSTOMFILTER, result.toString());
		}
		
		return this.params;
	}

	protected void onBeforeQuery() throws Exception {
		/* TODO 自动生成方法存根 */
		
	}

	protected void onAfterQuery() throws Exception {
		/* TODO 自动生成方法存根 */
/*		System.out.println("sxhong");
		if(tblMain.getRowCount()==1&&tblMain.getRow(0).getCell(0).getValue().equals("指标没有数据.")){
			UserPreferenceData upd=tHelper.getUserDataFromDB();
			tHelper.applyConfigFromData(this.getTableForOA(),(UserCustomConfigItemData)upd.getTables().values().iterator().next());
		}*/
	}

	protected KDTable getTableForPrintSetting() {
		
		return tblMain;
	}

	protected void preparePrintPageHeader(HeadFootModel header) {
		/* TODO 自动生成方法存根 */
		
	}

	protected Map preparePrintVariantMap() {
		/* TODO 自动生成方法存根 */
		return null;
	}

    protected void query()throws Exception{
    	this.tblMain.removeHeadRows();
    	this.tblMain.removeRows();
    	this.tblMain.removeColumns();
    	
    	IRow headRow=this.tblMain.addHeadRow();
    	IRow headVerRow=this.tblMain.addHeadRow();
    	IColumn colum=this.tblMain.addColumn();
    	colum.setKey("id");
    	colum.getStyleAttributes().setHided(true);
    	headRow.getCell("id").setValue("id");
    	colum=this.tblMain.addColumn();
    	colum.setKey("typeId");
    	colum.getStyleAttributes().setHided(true);
    	headRow.getCell("typeId").setValue("typeId");
    	colum=this.tblMain.addColumn();
    	colum.setKey("type");
    	headRow.getCell("type").setValue("指标类型");
    	colum=this.tblMain.addColumn();
    	colum.setKey("number");
    	headRow.getCell("number").setValue("指标编码");
    	colum=this.tblMain.addColumn();
    	colum.setKey("name");
    	headRow.getCell("name").setValue("指标名称");
    	colum=this.tblMain.addColumn();
    	colum.setKey("measureUnit");
    	headRow.getCell("measureUnit").setValue("单位");
    	colum=this.tblMain.addColumn();
    	colum.setKey("description");
    	headRow.getCell("description").setValue("说明");
    	
    	String[] projIds = (String[]) params.getObject(RptParamConst.KEY_PROJECTIDS);
    	String[] proTypeIds = (String[]) params.getObject(RptParamConst.KEY_PRODCUTTYPEIDS);
    	String[] tarTypeIds = (String[]) params.getObject(RptParamConst.KEY_TARGETTYPEIDS);
    	boolean whole_proj = params.getBoolean(RptParamConst.KEY_WHOLEPROJ);
    	ProjectStageEnum stage = (ProjectStageEnum) params.getObject(RptParamConst.KEY_PROJECTSTAGE);
    	String stageValue = stage.getValue();

		if(getQueryDialog().getEntityViewInfoResult() != null && getQueryDialog().getEntityViewInfoResult().getFilter() != null && getQueryDialog().getEntityViewInfoResult().getFilter().getFilterItems().size() > 0) {
			params.setObject(RptParamConst.KEY_CUSTOMFILTER, getQueryDialog().getEntityViewInfoResult().toString());
		}
    	String customFilter = (String) params.getObject(RptParamConst.KEY_CUSTOMFILTER);
    	
    	boolean isProTypeNotEmpty = !FDCHelper.isEmpty(proTypeIds);
		boolean isTarTypeNotEmpty = !FDCHelper.isEmpty(tarTypeIds);
		
		Set proOrOrgIdSet = FDCHelper.getSetByArray(projIds);

		String proOrOrgInCaulse = FDCHelper.idListToInClause(proOrOrgIdSet);
		String proTypeInCaulse = null;
		String tarTypeIdInCaulse = null;
		
		if (isProTypeNotEmpty){
			proTypeInCaulse = FDCHelper.idListToInClause(FDCHelper.getSetByArray(proTypeIds));
		}
		if (isTarTypeNotEmpty){
			tarTypeIdInCaulse = FDCHelper.idListToInClause(FDCHelper.getSetByArray(tarTypeIds));
		}
    	FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select a.fid id,a.fname_l2 name,a.fnumber number,a.FDescription_l2 description,b.fname_l2 measureUnit,c.fname_l2 as type,c.fid typeId");
		sql.appendSql(" from t_fdc_apportiontype a left outer join T_BD_MeasureUnit b on a.FMeasureUnitID = b.fid");
		sql.appendSql(" left outer join T_FDC_TargetType c on c.fid = a.FTargetTypeID");
		sql.appendSql(" where a.FIsEnabled = 1 and c.fisEnabled=1 and a.fid != '"+FDCConstants.AIM_COST_PERCENT_ID+"'");
		if (isTarTypeNotEmpty)
		{
			sql.appendSql(" and a.FTargetTypeID in(");
			sql.appendSql(tarTypeIdInCaulse);
			sql.appendSql(")");
		}
		sql.appendSql(" order by a.fnumber ");
		IRowSet rs = sql.executeQuery();
		Map rowMap=new HashMap();
		while(rs.next()){
			IRow row=this.tblMain.addRow();
			row.getCell("id").setValue(rs.getString("id"));
			row.getCell("number").setValue(rs.getString("number"));
			row.getCell("name").setValue(rs.getString("name"));
			row.getCell("typeId").setValue(rs.getString("typeId"));
			row.getCell("type").setValue(rs.getString("type"));
			row.getCell("measureUnit").setValue(rs.getString("measureUnit"));
			row.getCell("description").setValue(rs.getString("description"));
			rowMap.put(rs.getString("id"), row);
		}
		Map coloumMap=new HashMap();
		
		sql = new FDCSQLBuilder();
		sql.appendSql(" select stage.fname_l2 stage,data.FVerNo ver,(case when data.FProductTypeID is null then 'whole_proj' else product.fid end) productTypeId,(case when data.FProductTypeID is null then '整个工程' else to_char(product.fname_l2) end) productName,entries.FApportionTypeID typeId,entries.FIndexValue value");
		sql.appendSql(" from T_FDC_ProjectIndexData data inner join T_FDC_ProjectIndexDataEntry entries on data.fid = entries.fparentid");
		sql.appendSql(" left join t_fdc_productType product on product.fid = data.FProductTypeID");
		sql.appendSql(" left join T_FDC_MeasureStage stage on stage.fid = data.FMeasureStageID");
		sql.appendSql(" where data.faimMeasureID is null and data.FProjectStage='"+stageValue+"'");
		sql.appendSql(" and data.FProjOrOrgID in(");
		sql.appendSql(proOrOrgInCaulse);
		sql.appendSql(")");
		if (isProTypeNotEmpty)
		{
			sql.appendSql(" and data.FProductTypeID in(");
			sql.appendSql(proTypeInCaulse);
			sql.appendSql(") ");
		} else if (whole_proj)
		{
			sql.appendSql(" and data.FProductTypeID is null ");
		}
		if (customFilter != null)
		{
			customFilter = customFilter.replaceAll("fname", "fname_l2");
			customFilter = customFilter.replaceFirst("WHERE", " AND");
			sql.appendSql(customFilter);
		}
		sql.appendSql(" order by product.fnumber,data.FVerNo ");
		rs = sql.executeQuery();
		while(rs.next()){
			IRow row=(IRow) rowMap.get(rs.getString("typeId"));
			if(coloumMap.get(rs.getString("productTypeId")+rs.getString("ver"))!=null){
				if(row!=null){
					row.getCell(rs.getString("productTypeId")+rs.getString("ver")).setValue(rs.getBigDecimal("value"));
				}
			}else{
				if(row!=null){
					colum=this.tblMain.addColumn();
					colum.setWidth(150);
			    	colum.setKey(rs.getString("productTypeId")+rs.getString("ver"));
			    	colum.setExpressions(rs.getString("productTypeId"));
			    	
			    	headRow.getCell(rs.getString("productTypeId")+rs.getString("ver")).setValue(rs.getString("productName"));
			    	if(rs.getString("stage")!=null){
			    		headVerRow.getCell(rs.getString("productTypeId")+rs.getString("ver")).setValue(rs.getString("ver")+"（"+rs.getString("stage")+"）");
			    	}else{
			    		headVerRow.getCell(rs.getString("productTypeId")+rs.getString("ver")).setValue(rs.getString("ver"));
			    	}
			    	row.getCell(rs.getString("productTypeId")+rs.getString("ver")).setValue(rs.getBigDecimal("value"));
					coloumMap.put(rs.getString("productTypeId")+rs.getString("ver"), colum);
					
//					CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{rs.getString("productTypeId")+rs.getString("ver")});
					
				}
			}
		}
		int merger=0;
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			if(this.tblMain.getColumnKey(i).indexOf("V")>0){
				if(i>0){
					boolean isMerge=true;
					if(!this.tblMain.getColumn(i).getExpressions().equals(this.tblMain.getColumn(i-1).getExpressions())){
						isMerge=false;
						merger=i;
					}
					if(isMerge){
						this.tblMain.getHeadMergeManager().mergeBlock(0, merger, 0, i);
					}
				}
//				CRMClientHelper.getFootRow(this.tblMain, new String[]{this.tblMain.getColumnKey(i)});
			}else{
				this.tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
			}
		}
		mergerTable(this.tblMain,new String[]{"typeId"},new String[]{"type"});
		this.tblMain.getViewManager().freezeColumn(7);
    }
    private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
    private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
}