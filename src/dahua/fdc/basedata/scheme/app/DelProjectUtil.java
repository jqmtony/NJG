/**
 * 
 */
package com.kingdee.eas.fdc.basedata.scheme.app;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author jinxp
 *
 */
public class DelProjectUtil {
	
    private static Logger logger =
              Logger.getLogger("com.kingdee.eas.fdc.basedata.scheme.app.DelProjectUtil");

    
	protected static Map _updateData(Context ctx, Map param) throws BOSException {
		
		//获取全部项目部的当前期间
		if(param!=null && param.get("orgUnit")!=null){
			 delProject(ctx, param);
		}else
			if(param!=null && param.get("All")!=null){
//				 delAll(ctx, param);
			}
		else {
			
		}
		
		return null;
	}
	
	//删除某工程项目相关表格数据
	protected static void delProject(Context ctx, Map param) throws BOSException {
		OrgUnitInfo orgUnit = (OrgUnitInfo) param.get("orgUnit");

		IRowSet rs = null;
		String sql ="select KSQL_COL_TABNAME from KSQL_USERCOLUMNS where KSQL_COL_NAME ='FProjectOrgId' and KSQL_COL_TABNAME like 'T_FDC_%'" +
					"		order by KSQL_COL_TABNAME";
		
		rs = DbUtil.executeQuery(ctx, sql);
		try {
			while (rs.next()) {
				String tableName = rs.getString("KSQL_COL_TABNAME");
				if("T_FDC_DEDUCTVIEW".equalsIgnoreCase(tableName)
						|| "T_FDC_REALCOLLECTBILLVIEW".equalsIgnoreCase(tableName)){
					continue ;
				}
				
				if(inTableCols(tableName)){
					continue;
				}
				
				String delSql = "delete from "+tableName+" where FProjectOrgId = ?";
				
				DbUtil.execute(ctx, delSql,new Object[]{orgUnit.getId().toString()});
			}
		} catch (SQLException e) {
			e.printStackTrace();
			 throw new BOSException(e);
		}

	}
	
	//删除所有工程项目相关表格数据
	protected static void delAll(Context ctx, Map param) throws BOSException {

		IRowSet rs = null;
		String sql =" select KSQL_TABNAME from KSQL_USERTABLES where KSQL_TABNAME like 'T_FDC_%' " +
					" order by KSQL_TABNAME ";
		
		rs = DbUtil.executeQuery(ctx, sql);
		try {
			while (rs.next()) {
				String tableName = rs.getString("KSQL_TABNAME");
				if("T_FDC_DEDUCTVIEW".equalsIgnoreCase(tableName)
						|| "T_FDC_RealCollectEntryView".equalsIgnoreCase(tableName)
						|| "T_FDC_RealCollectItemView".equalsIgnoreCase(tableName)
						||"T_FDC_VIEWMainTainSEO".equalsIgnoreCase(tableName)
						|| "T_FDC_REALCOLLECTBILLVIEW".equalsIgnoreCase(tableName)
							||"T_FDC_MainTainNoteBillEOView".equalsIgnoreCase(tableName)
						||"T_FDC_AgentMainTainBillEOView".equalsIgnoreCase(tableName)){
					
					continue ;
				}
				if(inTableCols(tableName)){
					continue;
				}
				String delSql = "TRUNCATE TABLE  "+tableName ;
				
				DbUtil.execute(ctx, delSql);
				
				logger.info(delSql);
				System.out.println(delSql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			 throw new BOSException(e);
		}
	}
	
	
	protected static boolean inTableCols(String tabName) throws BOSException {
		int len = TableCols.length;
		for(int i=0;i<len;i++){
			if(tabName.equalsIgnoreCase(TableCols[i])){
				return true;
			}
		}
		return false;
	}




public static final String[] TableCols = {  
	"T_FDC_Acceptance",
	"T_FDC_AcceptanceEntry",
	"T_FDC_AccidentBill",
	"T_FDC_AccidentLevel",
	"T_FDC_AccidentType",
	"T_FDC_AdjustReason",
	"T_FDC_AffaireRecordBill",
	"T_FDC_AffairType",
	"T_FDC_AuditType",
	"T_FDC_BillType",
	"T_FDC_BlockAcceptance",
	"T_FDC_BlockAcceptanceEntry",
	"T_FDC_Certificate",
	"T_FDC_ChangeType",
	"T_FDC_CheckGroup",
	"T_FDC_CheckGroupBill",
	"T_FDC_CheckItem",
	"T_FDC_CheckItemEntry",
	"T_FDC_CheckLevel",
	"T_FDC_CheckStage",
	"T_FDC_CheckTemplate",
	"T_FDC_CheckTemplateEntry",
	"T_FDC_CheckType",
	"T_FDC_ChideRecordBill",
	"T_FDC_ClaimType",
	"T_FDC_ComDangerSource",
	"T_FDC_ComDangerSourceEntry",
	"T_FDC_ContractAffairDetailType",
	"T_FDC_ContractAffairType",
	"T_FDC_ContractHeft",
	"T_FDC_ContractType",
	"T_FDC_ContractTypeDetail",
	"T_FDC_ControlItem",
	"T_FDC_Controlity",
	"T_FDC_ContructUnit",
	"T_FDC_ContructUnitCollarer",
	"T_FDC_CostAccount",
	"T_FDC_CostAccountType",
	"T_FDC_Criterion",
	"T_FDC_DADBConfig",
	"T_FDC_DangerSource",
	"T_FDC_DisputeType",
	"T_FDC_Dispution",
	"T_FDC_DisqualiHandle",
	"T_FDC_DisqualiHandleEntry",
	"T_FDC_DisqualiVerify",
	"T_FDC_EcSegment",
	"T_FDC_EducationBill",
	"T_FDC_EducationBillEntr",
	"T_FDC_EduOtherBill",
	"T_FDC_EduOtherBillEntry",
	"T_FDC_EffectDegree",
	"T_FDC_EffectLevel",
	"T_FDC_EffectScale",
	"T_FDC_EffectTimes",
	"T_FDC_EffectType",
	"T_FDC_EnterpriseKind",
	"T_FDC_EquipmentType",
	"T_FDC_EstimateItem",
	"T_FDC_EstimateItemType",
	"T_FDC_EstimateResult",
	"T_FDC_EstimateTemplate",
	"T_FDC_EvalueMode",
	"T_FDC_EvalueModeEntry",
	"T_FDC_EvalueTarget",
	"T_FDC_ExamineItem",
	"T_FDC_ExamineItemType",
	"T_FDC_ExamineTemplate",
	"T_FDC_ExamineType",
	"T_FDC_FactorEstimateBill",
	"T_FDC_FactorEstimateBillEntry",
	"T_FDC_FactorGreatBill",
	"T_FDC_FactorGreatBillEntry",
	"T_FDC_FactorInquiryBill",
	"T_FDC_FactorInquiryBillEntry",
	"T_FDC_FactorItem",
	"T_FDC_FieldConfig",
	"T_FDC_FileContent",
	"T_FDC_FiPayMode",
	"T_FDC_FiPayout",
	"T_FDC_HmAppraiseType",
	"T_FDC_HmCheckType",
	"T_FDC_IndexItem",
	"T_FDC_IndexLib",
	"T_FDC_IndustryType",
	"T_FDC_Jobs",
	"T_FDC_Judgment",
	"T_FDC_Lot",
	"T_FDC_LotNumber",
	"T_FDC_ManageDept",
	"T_FDC_MaterAcceptance",
	"T_FDC_MaterAcceptanceEntry",
	"T_FDC_NormalProjListing",
	"T_FDC_Occupation",
	"T_FDC_OginaizeBS",
	"T_FDC_OtherAccount",
	"T_FDC_PayItem",
	"T_FDC_PayOrReType",
	"T_FDC_PersonType",
	"T_FDC_PhysicaExamBill" ,
	"T_FDC_ProDangerEnding",
	"T_FDC_ProDangerSource",
	"T_FDC_Project",
	"T_FDC_ProjectBS",
	"T_FDC_ProjectBSType",
	"T_FDC_ProjectCheckType",
	"T_FDC_ProjectClass",
	"T_FDC_ProjectDangerSource",
	"T_FDC_ProjectDangerSourceEntry",
	"T_FDC_ProjectDataListMana",
	"T_FDC_ProjectDataMa",
	"T_FDC_ProjectEntry",
	"T_FDC_ProjectPersonEntry",
	"T_FDC_ProjectPic",
	"T_FDC_ProjectRepair",
	"T_FDC_ProjectReturnWork",
	"T_FDC_ProjectTalkBack",
	"T_FDC_ProjectType",
	"T_FDC_ProjectUnit",
	"T_FDC_ProMetricToolMan",
	"T_FDC_ProMetricToolManEntry",
	"T_FDC_ProTargetResultEntry",
	"T_FDC_QmAccidentLevel",
	"T_FDC_QmCheckGroup",
	"T_FDC_QmCheckLog",
	"T_FDC_QmCheckLogEntry",
	"T_FDC_QmDealType",
	"T_FDC_QmDisquaReason",
	"T_FDC_QmInspectProject",
	"T_FDC_QmInspectType",
	"T_FDC_QmProfessional",
	"T_FDC_QmStandAcceptance",
	"T_FDC_QmStandAcceptanceEntry",
	"T_FDC_QmStandardBill",
	"T_FDC_QmStandardDeclare",
	"T_FDC_QmStandardDeclareEntry",
	"T_FDC_QmStandardResult",
	"T_FDC_QmStandardTarget",
	"T_FDC_QmStandDisclosure",
	"T_FDC_QseCheckBillEntry",
	"T_FDC_QseOperationBill",
	"T_FDC_QsePlanBill",
	"T_FDC_QsePlanEntries",
	"T_FDC_QualityAccident",
	"T_FDC_QualityAccidentEntry",
	"T_FDC_QualityAssign",
	"T_FDC_QualityBillEnEntry",
	"T_FDC_QualityCheckBill",
	"T_FDC_QualityCheckBillEntry",
	"T_FDC_QualityCheckPlan",
	"T_FDC_QualityCheckPlanEntry",
	"T_FDC_QualityCheckType",
	"T_FDC_QualityInspect",
	"T_FDC_QualityInspectEntry",
	"T_FDC_QualityMaintainBill",
	"T_FDC_QualityPoint",
	"T_FDC_QualityProblem",
	"T_FDC_QualityProblemEntry",
	"T_FDC_QualityProcess",
	"T_FDC_QualityProcessEntry",
	"T_FDC_QualityProType",
	"T_FDC_QualityTarget",
	"T_FDC_QualityTargetEntry",
	"T_FDC_QualityTargetProcess",
	"T_FDC_QualityTargetProcessEntry",
	"T_FDC_QualityTargetResult",
	"T_FDC_QualityTargetResultEntry",
	"T_FDC_QuestRecord",
	"T_FDC_QuestRecordEntry",
	"T_FDC_QueType",
	"T_FDC_RectifyNotice",
	"T_FDC_RectifyPenalty",
	"T_FDC_RectifyReply",
	"T_FDC_RectifyReplyEntry",
	"T_FDC_RectifyStop",
	"T_FDC_RectifyVerify",
	"T_FDC_RejectNoticeBill",
	"T_FDC_RejectNoticeBillEntry",
	"T_FDC_RelationPerson",
	"T_FDC_Resource",
	"T_FDC_ResourceItem",
	"T_FDC_ResourceType",
	"T_FDC_ResumeAbility",
	"T_FDC_ReworkBill",
	"T_FDC_RiskAffect",
	"T_FDC_RiskCopingstrategy",
	"T_FDC_RiskLevel",
	"T_FDC_RiskLevelEntry",
	"T_FDC_RiskType",
	"T_FDC_SafeCheckBill",
	"T_FDC_SafetyAcceptance",
	"T_FDC_SafetyAcceptanceEntry",
	"T_FDC_SafetyAccident",
	"T_FDC_SafetyAccidentEntry",
	"T_FDC_SafetyAppraisal",
	"T_FDC_SafetyAppraisalEntry",
	"T_FDC_SafetyCheckBill",
	"T_FDC_SafetyCheckBillEntry",
	"T_FDC_SafetyCheckPlan",
	"T_FDC_SafetyCheckPlanEntry",
	"T_FDC_SafetyCheckResultEntry",
	"T_FDC_SafetyCheckType",
	"T_FDC_SafetyEquEntry",
	"T_FDC_SafetyInspect",
	"T_FDC_SafetyInspectEntry",
	"T_FDC_SafetyProcess",
	"T_FDC_SafetyProcessEntry",
	"T_FDC_SafetyRespEntry",
	"T_FDC_SafetyTarget",
	"T_FDC_SafetyTargetEntry",
	"T_FDC_SafetyTargetItem",
	"T_FDC_SafetyTargetItemEntry",
	"T_FDC_SafetyTargetProcess",
	"T_FDC_SafetyTargetProcessEntry",
	"T_FDC_SafetyTargetResult",
	"T_FDC_SafetyTargetResultEntry",
	"T_FDC_SafetyTraining",
	"T_FDC_SafetyTrainingEntry",
	"T_FDC_SafetyValidateBill",
	"T_FDC_SafetyValidateBillEntry",
	"T_FDC_SafetyWoundedEntry",
	"T_FDC_ScoreStandard",
	"T_FDC_ScoreStandardEntry",
	"T_FDC_StandAcceptance",
	"T_FDC_StandardAtlasDataMan",
	"T_FDC_StandardAtlasDataManEntry",
	"T_FDC_StandardBill",
	"T_FDC_StandardDeclare",
	"T_FDC_StandardResult",
	"T_FDC_StandardTarget",
	"T_FDC_StandDisclosure",
	"T_FDC_StructureType",
	"T_FDC_SupplierLevel",
	"T_FDC_SupplierType",
	"T_FDC_SystemControl",
	"T_FDC_TechDisclosure",
	"T_FDC_TechDisclosureEntry",
	"T_FDC_TalkbackBill",
	"T_FDC_Target",
	"T_FDC_TemplateItem",
	"T_FDC_TemplateItemType",
	"T_FDC_Version",
	"T_FDC_Warehouse",
	"T_FDC_WorkArea",
	"T_FDC_WorkProceAcceptance",
	"T_FDC_WorkProceAcceptanceEntry",
	"T_FDC_WorkTeam",
	"T_FDC_YearTalkBack",
	"T_FDC_YearTalkBackEntry",
	
	//投标
	"T_FDC_Problem",
	"T_FDC_ProblemEntry",
	"T_FDC_ProCl",
	
	//投标
	"T_FDC_TdTechStandType",
	"T_FDC_TdPosition",
	"T_FDC_TdPattern",
	"T_FDC_TdIntroType",
	"T_FDC_TdDocReviewType",
	"T_FDC_TdContractType",
	"T_FDC_TdBidBondType",
	"T_FDC_RegionalMarketEntrys",
	"T_FDC_RegionalMarket2Entry",
	"T_FDC_RegionalMarket",
	"T_FDC_ProjectUsage",
	"T_TB_ProjectType",
	"T_FDC_ProjectStructureType",
	"T_FDC_TenderWasteProvition",
	"T_FDC_TenderUnitPredictPrice",
	"T_FDC_TenderReportBill",
	"T_FDC_TenderPlan",
	"T_FDC_TenderKeyEvent",
	"T_FDC_TenderGroupMember",
	"T_FDC_TenderExceptionProcess",
	"T_FDC_TenderDocAppraisalTender",
	"T_FDC_TenderDocAppraisalEntry",
	"T_FDC_TenderDocAppraisal",
	"T_FDC_TenderCluesListPerson",
	"T_FDC_TenderClues",
	"T_FDC_ProjectApprovalRqt",
	"T_FDC_IntroductionEntry",
	"T_FDC_Introduction",
	"T_FDC_DocReviewEntryTransaction",
	"T_FDC_DocReviewEntryTenders",
	"T_FDC_DocReviewEntryGroupMember",
	"T_FDC_DocReviewEntryDocuments",
	"T_FDC_DocReviewEntryAnnulment",
	"T_FDC_DocReview",
	"T_FDC_ConstructionProceed",
	"T_FDC_CompetencyApplyPassMan",
	"T_FDC_CompetencyApply",
	"T_FDC_BidSummaryManDiploma",
	"T_FDC_BidSummary",
	"T_FDC_BidRegisterMan",
	"T_FDC_BidRegister",
	"T_FDC_BidOpenRecord",
	"T_FDC_BidBond"
	
	} ;
}
