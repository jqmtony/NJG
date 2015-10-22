package com.kingdee.eas.fdc.contract.report.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ProgramContractReportFacadeControllerBean extends AbstractProgramContractReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.report.app.ProgramContractReportFacadeControllerBean");
    
    @Override
    protected RptParams _init(Context ctx, RptParams params) throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._init(ctx, params);
    }
    
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    
    @Override
    protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
    	RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"level",100,true);
	    initColoum(header,col,"longNumber",100,true);
	    initColoum(header,col,"headNumber",100,true);
	    initColoum(header,col,"name",300,false);
	    initColoum(header,col,"hyType",100,false);
	    initColoum(header,col,"planAmount",150,false);
	    initColoum(header,col,"changeRate",100,false);
	    initColoum(header,col,"contralAmount",150,false);
	    // real date
	    initColoum(header,col,"sgtDate",100,false);
	    initColoum(header,col,"sgtRealDate",120,false);
	    initColoum(header,col,"sgtOverdue",70,false);
	    initColoum(header,col,"sgtPlanDate",100,false);
	    initColoum(header,col,"csDate",100,false);
	    initColoum(header,col,"csRealDate",110,false);
	    initColoum(header,col,"csOverdue",70,false);
	    initColoum(header,col,"csPlanDate",100,false);
	    initColoum(header,col,"startDate",80,false);
	    initColoum(header,col,"startRealDate",80,false);
	    initColoum(header,col,"startOverdue",70,false);
	    initColoum(header,col,"startPlanDate",80,false);
	    initColoum(header,col,"endDate",80,false);
	    initColoum(header,col,"endRealDate",80,false);
	    initColoum(header,col,"endOverdue",70,false);
	    initColoum(header,col,"endPlanDate",80,false);
	    initColoum(header,col,"csendDate",110,false);
	    initColoum(header,col,"csendRealDate",120,false);
	    initColoum(header,col,"csendOverdue",70,false);
	    initColoum(header,col,"csendPlanDate",110,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","level","longNumber","headNumber","�滮��ͬ����","��Լ����","�滮���","Ԥ�������","�ɹ����ƽ��","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ",
	    			"ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ"
	    			,"ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ","ҵ�����첿��/��λ"
	    		},{
	    			"id","level","longNumber","headNumber","�滮��ͬ����","��Լ����","�滮���","Ԥ�������","�ɹ����ƽ��","��Ʋ�","��Ʋ�","��Ʋ�","��Ʋ�","�ɱ�����/��",
	    			"�ɱ�����/��","�ɱ�����/��","�ɱ�����/��","���̲�","���̲�","���̲�","���̲�","���̲�","���̲�","���̲�","���̲�","���Ϲ���˾","���Ϲ���˾","���Ϲ���˾","���Ϲ���˾"
	    		},{
	    			"id","level","longNumber","headNumber","�滮��ͬ����","��Լ����","�滮���","Ԥ�������","�ɹ����ƽ��","ʩ��ͼ����ʱ��","ʵ��ʩ��ͼ����ʱ��","�Ƿ�����","�ƻ�ʩ��ͼ����ʱ��","��ͬǩ��ʱ��","ʵ�ʺ�ͬǩ��ʱ��",
	    			"�Ƿ�����","�ƻ���ͬǩ��ʱ��","����ʱ��","ʵ�ʿ���ʱ��","�Ƿ�����","�ƻ�����ʱ��","����ʱ��","ʵ�ʿ���ʱ��","�Ƿ�����","�ƻ�����ʱ��","��ͬǩ�����ʱ��","ʵ�ʺ�ͬǩ�����ʱ��","�Ƿ�����","�ƻ���ͬǩ�����ʱ��"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
    }
    
    @Override
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException {
    	RptRowSet rowSet = executeQuery(getSql(ctx,params),null,ctx);
    	params.setObject("rowset", rowSet);
    	return params;
    }
    
    protected String getSql(Context ctx,RptParams params){
    	StringBuffer sb = new StringBuffer();
    	String project = (String) params.getObject("project");
    	sb.append(" select pcont.fid,pcont.FLEVEL,pcont.FLONGNUMBER,pcparent.FLONGNUMBER,pcont.fname_l2,pcType.CFHyType,pcont.Famount,pcont.FreservedChangeRate,pcont.FcontrolAmount,pcont.CFSgtDate,pcont.CFContSignDate ");
    	//pcont.CFStartDate,pcont.CFEndDate,pcont.CFCsendDate
    	sb.append(" from T_CON_ProgrammingContract pcont left join T_CON_Programming program on pcont.FPROGRAMMINGID=program.fid ");
    	sb.append("left join T_CON_ProgrammingContract pcparent on pcparent.fid=pcont.fparentid ");
    	sb.append("left join CT_CON_PcType pcType on pcType.fid=pcont.CFHyTypeID where program.FIsLatest=1 ");//where program.fisLatest=1 
    	if(project == null){
    		sb.append("and program.fprojectid is null order by pcont.FLONGNUMBER");
    	}else{
    		sb.append("and program.fprojectid='"+project+"' order by pcont.FLONGNUMBER");
    	}
    	return sb.toString();
    }
    
    
    
}