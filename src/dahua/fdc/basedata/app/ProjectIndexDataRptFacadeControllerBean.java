package com.kingdee.eas.fdc.basedata.app;

import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RptParamConst;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.app.ContextUtil;

public class ProjectIndexDataRptFacadeControllerBean extends
		AbstractProjectIndexDataRptFacadeControllerBean
{
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectIndexDataRptFacadeControllerBean");

	/**
	 * ׼����������(����������)
	 */

	protected SchemaSource readySchemaSource(RptParams params, Context ctx)
			throws BOSException, EASBizException
	{
		SchemaSource ss = new SchemaSource();

		String[] companyIds = (String[]) params
				.getObject(RptParamConst.KEY_COMPANYIDS);
		String[] projIds = (String[]) params
				.getObject(RptParamConst.KEY_PROJECTIDS);
		String[] proTypeIds = (String[]) params
				.getObject(RptParamConst.KEY_PRODCUTTYPEIDS);
		String[] tarTypeIds = (String[]) params
				.getObject(RptParamConst.KEY_TARGETTYPEIDS);
		boolean whole_proj = params.getBoolean(RptParamConst.KEY_WHOLEPROJ);
		ProjectStageEnum stage = (ProjectStageEnum) params
				.getObject(RptParamConst.KEY_PROJECTSTAGE);
		String stageValue = stage.getValue();

		String customFilter = (String) params
				.getObject(RptParamConst.KEY_CUSTOMFILTER);

		String loc = getLoc(ctx);

		StringBuffer sql = new StringBuffer();

		Set proOrOrgIdSet = FDCHelper.getSetByArray(projIds);

		String proOrOrgInCaulse = FDCHelper.idListToInClause(proOrOrgIdSet);
		String proTypeInCaulse = null;
		String tarTypeIdInCaulse = null;

		//��ʵ��
		/*
		 * �˴���sql�������( case when a.FVerNo is null then N' ' else a.FVerNo end) as FVerName
		 * ������Ϊ�˻�ȡ�汾��
		 */
		sql
				.append("select a.FProjOrOrgID,( case when a.FVerNo is null then N' ' else a.FVerNo end) as FVerName, (case when a.FProductTypeID is null then 'whole_proj' else a.FProductTypeID end) FProductTypeID, entries.FApportionTypeID, entries.FTargetTypeID, entries.FIndexValue  ");
		sql.append("	from T_FDC_ProjectIndexData a 																	\r\n");
		sql
				.append("	inner join T_FDC_ProjectIndexDataEntry entries on a.fid = entries.fparentid 					\r\n");
		sql
				.append("	inner join T_FDC_ApportionType apportionType on entries.FApportionTypeID = apportionType.fid 	\r\n");
		sql
				.append("	left outer join T_FDC_TargetType d on entries.FTargetTypeID = d.FID 									\r\n");
		sql.append("	where apportionType.FIsEnabled = 1 and FProjectStage = '")
				.append(stageValue).append("' 		\r\n");

		//����Զ���������û��ָ���汾����Ĭ�ϲ�ѯ���°汾���ݣ������ѯָ���汾����
		if (customFilter == null
				|| (customFilter.indexOf("FVerNo") == -1 && customFilter
						.indexOf("FVerName") == -1))
		{
			sql.append("and a.FIsLatestVer = 1");
		}

		sql.append(" and a.FProjOrOrgID in(");

		sql.append(proOrOrgInCaulse);
		sql.append(")");

		boolean isProTypeNotEmpty = !FDCHelper.isEmpty(proTypeIds);
		boolean isTarTypeNotEmpty = !FDCHelper.isEmpty(tarTypeIds);

		if (isProTypeNotEmpty)
		{
			sql.append(" and a.FProductTypeID in(");
			proTypeInCaulse = FDCHelper.idListToInClause(FDCHelper
					.getSetByArray(proTypeIds));
			sql.append(proTypeInCaulse);
			sql.append(") ");
		} else if (whole_proj)
		{
			sql.append(" and a.FProductTypeID is null ");
		}

		if (isTarTypeNotEmpty)
		{
			sql.append("and entries.FTargetTypeID in(");
			tarTypeIdInCaulse = FDCHelper.idListToInClause(FDCHelper
					.getSetByArray(tarTypeIds));
			sql.append(tarTypeIdInCaulse);
			sql.append(") ");
		}
		//    	sql.append(" order by d.fnumber, c.fnumber"); //olap�淶��������orderby��orderҪ��xml�ж��壬�ҽ���ά��������

		if (customFilter != null)
		{
			customFilter = customFilter.replaceAll("fname", "fname_" + loc);
			customFilter = customFilter.replaceFirst("WHERE", " AND");
			sql.append(customFilter);
		}

		ss.setDataItem("Fact", sql.toString(), null);

		//��֯��������Ŀ
		sql.setLength(0);
		sql.append("select fid, fname_");
		sql.append(loc);
		sql.append(" fname, fnumber from t_org_baseunit where fid in(");
		sql.append(proOrOrgInCaulse);
		sql.append(")");
		sql.append(" union ");
		sql.append("select fid, fname_");
		sql.append(loc);
		sql.append(" fname, fnumber from t_fdc_curProject where fid in(");
		sql.append(proOrOrgInCaulse);
		sql.append(")");
		//    	sql.append(" order by fnumber");
		ss.setDataItem("Project", sql.toString(), null);

		//��Ʒ����
		sql.setLength(0);

		if (isProTypeNotEmpty)
		{
			sql.append("select fid, fname_");
			sql.append(loc);
			sql.append(" fname, fnumber from t_fdc_productType");
			sql.append(" where fid in(");
			sql.append(proTypeInCaulse);
			sql.append(")");
		} else if (whole_proj)
		{
			sql
					.append("select 'whole_proj' fid, '��������' fname, 'whole_proj_num' fnumber ");
		} else
		{
			sql.append("select fid, fname_");
			sql.append(loc);
			sql.append(" fname, fnumber from t_fdc_productType");
		}
		//    	sql.append(" order by fnumber");
		ss.setDataItem("ProductType", sql.toString(), null);

		//ָ��

		sql.setLength(0);
		sql.append("select a.fid, a.fname_");
		sql.append(loc);
		sql
				.append(" fname, a.fnumber, a.FDescription_")
				.append(loc)
				.append(" FDescription, b.fname_")
				.append(loc)
				.append(
						" measureUnit from t_fdc_apportiontype a left outer join T_BD_MeasureUnit b on a.FMeasureUnitID = b.fid");
		sql.append(" inner join T_Org_CtrlUnit cu on a.fcontrolUnitId=cu.fid ");
		sql.append(" where a.FIsEnabled = 1 and a.fid != '").append(
				FDCConstants.AIM_COST_PERCENT_ID).append("'");
		if (isTarTypeNotEmpty)
		{
			sql.append(" and a.FTargetTypeID in(");
			sql.append(tarTypeIdInCaulse);
			sql.append(")");
		}
		final CtrlUnitInfo cu = ContextUtil.getCurrentCtrlUnit(ctx);
		sql.append(" and (cu.fid='").append(OrgConstants.SYS_CU_ID).append("'");
		if (cu != null && cu.getLongNumber() != null)
		{
			sql.append(" or cu.flongNumber in (");
			String splits[] = cu.getLongNumber().split("!");
			String innerSql = null;
			for (int i = 0; i < splits.length; i++)
			{
				if (innerSql == null)
				{
					innerSql = splits[i];
				} else
				{
					innerSql += "!" + splits[i];
				}
				sql.append("'" + innerSql + "'");
				if (i != splits.length - 1)
				{
					sql.append(",");
				}
			}
			sql.append("))");
		}
		//    	sql.append(" order by a.fnumber");
		ss.setDataItem("Index", sql.toString(), null);

		
		//ͨ��sql���Ĳ�ѯ����ȡ�汾����䵽�汾������
		sql.setLength(0);
		sql.append(" select distinct (case when FVerNo is null then N' ' else FVerNo end) as FID,FVerNo as FVerName from T_FDC_ProjectIndexData ");
		
		ss.setDataItem("VerName",sql.toString(),null);
		
		
		
		//ָ������
		sql.setLength(0);
		sql.append("select fid, fname_");
		sql.append(loc);
		sql.append(" fname, fnumber from t_fdc_targettype");
		if (isTarTypeNotEmpty)
		{
			sql.append(" where fid in(");
			sql.append(tarTypeIdInCaulse);
			sql.append(")");
		}
		//    	sql.append(" order by fnumber");
		ss.setDataItem("IndexType", sql.toString(), null);

		StringBuffer mdx = new StringBuffer();
		//    	mdx.append("with member [IndexType].[С��] as 'sum([IndexType].members)' " )
		//    	.append("member [Measures].[description] as 'Index.currentmember.properties(\"description\")',caption=\"˵��\"")
		//    	.append("\n select [Project].members * [ProductType].members *  {[Measures].members,[Measures].[description]} on columns,")
		//    	.append(" non fact empty {[IndexType].members,{[IndexType].[С��]}} * [Index].members dimension properties [Index].[������λ] on rows")
		//		.append("\n from Fact");    	

		/*
		 * �˴���sql������� *{[VerName].members}
		 * ������Ϊ�˻�ȡ�汾��
		 */
		mdx
				.append(
						"with member [IndexType].[С��] as 'sum([IndexType].members)' ")
				.append(
						"select [Project].members * [ProductType].members  *  {[Measures].members} on columns,"
								+ " non fact empty {[IndexType].members,{[IndexType].[С��]}} *{[VerName].members}* [Index].members dimension properties  [Index].[������λ],[Index].[description] on rows ")
				//    	.append("\n select [Project].members * [ProductType].members *  {[Measures].members,[Measures].[description]} on columns,")
				//    	.append(" non fact empty {[IndexType].members,{[IndexType].[С��]}} * [Index].members dimension properties [Index].[������λ] on rows")
				.append("\n from Fact");

		//    	StringBuffer mdx = new StringBuffer();
		//    	mdx.append("with member [IndexType].[С��] as 'sum([IndexType].members)' member [Measures].[description] as 'Index.currentmember.properties(\"description\")',caption=\"˵��\",solve_order=2")
		//    	.append("\n select [Project].members * [ProductType].members * {[Measures].members,[Measures].[description]} on columns, non fact empty {[IndexType].members,{[IndexType].[С��]}} * [Index].members dimension properties [Index].[������λ] on rows")
		//		.append("\n from Fact");

		ss.setCaller(ProjectIndexDataRptFacadeControllerBean.class);
		ss.setFilename("ProjectIndexData.xml");
		ss.setMdx(mdx.toString());

		return ss;
	}
}