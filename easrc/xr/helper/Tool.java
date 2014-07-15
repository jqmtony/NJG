package com.kingdee.eas.xr.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.client.f7.AdminF7;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fi.fa.manage.client.FaBillTraceUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class Tool {
	
	/**
	 * �����²������½�����
	 */
	public static void checkGroupAddNew() {
		AdminOrgUnitInfo adminInfo = SysContext.getSysContext().getCurrentAdminUnit();
		if(adminInfo.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			MsgBox.showWarning("Ŀǰ��֯Ϊ{"+adminInfo.getName()+"}���������½����ݣ�");SysUtil.abort();
		}
	}
	/**
	* ��ʼ��������F7
	* 
	* @param coreui
	*            ����UI
	* @param prmtPerson
	*            ������F7�ؼ�
	* @param hasCUFilter
	*            �Ƿ�CU����
	* @throws BOSException
	* @throws Exception
	*/
	public static void initPersonF7(CoreUIObject coreui,KDBizPromptBox prmtPerson, boolean hasCUFilter)throws BOSException, Exception {
		prmtPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		HashMap map = new HashMap();
		map.put("All_Admins", "YES");
		map.put("DEFAULT_SHOW_ALL", "AAA");
		
		PersonPromptBox select = new PersonPromptBox(coreui, map);
		prmtPerson.setSelector(select);
		prmtPerson.setHasCUDefaultFilter(hasCUFilter);
		prmtPerson.setDisplayFormat("$name$");
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$");
		FilterInfo filter = new FilterInfo();
		PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
		filter = new FilterInfo();
		EntityViewInfo viewInfo = new EntityViewInfo();
		if(person!=null)
			filter.getFilterItems().add(new FilterItemInfo("id", person.getId().toString()));
		viewInfo.setFilter(filter);
		prmtPerson.setEntityViewInfo(viewInfo);
	}
	public static PersonInfo getUser(){
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		PersonInfo perInfo = null;
		if (userInfo!=null) {
			perInfo= userInfo.getPerson();
		}
		return perInfo;
	}
    /**
     * �������жϵ����Ƿ��ڹ������� client
     * ����ʱ�䣺2009-1-8 <p>
     */
    public static boolean isRunningWorkflow(String objId) throws BOSException {
        boolean hasWorkflow = false;
        IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if ("open.running".equals(procInsts[i].getState())) {
                hasWorkflow = true;
                break;
            }
        }
        return hasWorkflow;
    }
	/**
     * ��������ʾ���²����
     * 
     * @param idList
     * @param traceType
     * @throws Exception
     * @author add by shieli
     */
    public static void showTraceUI(CoreUI aCoreUI, HashMap idList, int traceType) throws Exception {
        UIContext uiContext = new UIContext(aCoreUI);
        uiContext.put("IDList", idList);
        uiContext.put(UIContext.OWNER, aCoreUI);
        String typeName = UIFactoryName.MODEL;
        IUIFactory uiFactory = UIFactory.createUIFactory(typeName);
        IUIWindow newWindow = uiFactory.create(FaBillTraceUI.class.getName(), uiContext, null);
        newWindow.show();
    }
    /**
     * �ں�������Ϣ
     * @param table
     * @param coloum
     * @param mergeColoum
     */
    public static void mergerTable(KDTable table, String coloum[], String mergeColoum[])
    {
        int merger = 0;
        for(int i = 0; i < table.getRowCount(); i++)
            if(i > 0)
            {
                boolean isMerge = true;
                for(int j = 0; j < coloum.length; j++)
                {
                    Object curRow = table.getRow(i).getCell(coloum[j]).getValue();
                    Object lastRow = table.getRow(i - 1).getCell(coloum[j]).getValue();
                    if(getString(curRow).equals("") || getString(lastRow).equals("") || !getString(curRow).equals(getString(lastRow)))
                    {
                        isMerge = false;
                        merger = i;
                    }
                }

                if(isMerge)
                {
                    for(int j = 0; j < mergeColoum.length; j++)
                        table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));

                }
            }

    }
    
    private static String getString(Object value)
    {
        if(value == null)
            return "";
        if(value != null && value.toString().trim().equals(""))
            return "";
        else
            return value.toString();
    }
	/**
	 * �������β���F7,ֻ��ѡ����ϸ�ڵ�
	 *
	 * @param bizPromptBox
	 * @param ui
	 */
	public static void setRespDeptF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui, String cuId) {
		bizPromptBox
				.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");

		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);
		FilterInfo filter = new FilterInfo();

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		fic.add(new FilterItemInfo("isSealUp", new Integer(0)));

		if(cuId != null){
			fic.add(new FilterItemInfo("CU.id", cuId));
		}

		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);

		AdminF7 f7 = new AdminF7(ui);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setIsCUFilter(false);
		f7.setRootUnitID(cuId);
		if(cuId != null) f7.setCurrentCUID(cuId);

		bizPromptBox.setSelector(f7);
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));

		// ֻ��ѡ����ϸ�ģ����Ӽ�������

//		if (bizPromptBox.getClientProperty("RespDeptDataChangeLisenter") == null) {
//			RespDeptDataChangeLisenter ls = new RespDeptDataChangeLisenter(ui);
//			bizPromptBox.addDataChangeListener(ls);
//			bizPromptBox.putClientProperty("RespDeptDataChangeLisenter", ls);
//		}
	}
	/**
	 * �������β���F7,ֻ��ѡ����ϸ�ڵ�
	 *
	 * @param bizPromptBox
	 * @param ui
	 */
	public static void setRespCompanytF7(KDBizPromptBox bizPromptBox,
			CoreUIObject ui, String cuId) {
		bizPromptBox
		.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");
		
		EntityViewInfo view = new EntityViewInfo();
		
		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);
		FilterInfo filter = new FilterInfo();
		
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		fic.add(new FilterItemInfo("isSealUp", new Integer(0)));
		fic.add(new FilterItemInfo("isCompanyOrgUnit", new Integer(1)));
		
		if(cuId != null){
			fic.add(new FilterItemInfo("CU.id", cuId));
		}
		
		view.setFilter(filter);
		bizPromptBox.setEntityViewInfo(view);
		
		CompanyF7 f7 = new CompanyF7(ui);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setIsCUFilter(false);
		f7.setRootUnitID(cuId);
		
		if(cuId != null) f7.setCurrentCUID(cuId);
		
		bizPromptBox.setSelector(f7);
		SelectorItemCollection sic = bizPromptBox.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			bizPromptBox.setSelectorCollection(sic);
		}
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("displayName"));
		
		// ֻ��ѡ����ϸ�ģ����Ӽ�������
		
//		if (bizPromptBox.getClientProperty("RespDeptDataChangeLisenter") == null) {
//			RespDeptDataChangeLisenter ls = new RespDeptDataChangeLisenter(ui);
//			bizPromptBox.addDataChangeListener(ls);
//			bizPromptBox.putClientProperty("RespDeptDataChangeLisenter", ls);
//		}
	}

	/**
	 * ������Ա
	 * @param box
	 * @param owner
	 * @param cuId
	 */
	public static void setPersonF7(KDPromptBox box, CoreUIObject owner, String cuId) {
		setPersonF7(box,owner,cuId,true);
	}
	public static void setPersonF7(KDPromptBox box, CoreUIObject owner, String cuId,boolean isSingle) {
		HashMap param = new HashMap();

		if(cuId!=null){
			param.put(PersonF7UI.CU_ID, cuId);
		}else{
			param.put(PersonF7UI.ALL_ADMIN, "YES");
		}

        ((KDBizPromptBox)box).setHasCUDefaultFilter(false);
        PersonPromptBox pmt = new PersonPromptBox(owner,param);
        pmt.setIsSingleSelect(isSingle);
//        map.put(PersonF7UI.ALL_ADMIN, "YES");

        box.setSelector(pmt);

        //ͨ��F7�ǿ������������,ֻҪbizPromptBox.setEntityViewInfo(view)��ȷ JinXP  20071217
        if (cuId!=null) {

            EntityViewInfo view = new EntityViewInfo();

    		SorterItemCollection sorc = view.getSorter();
    		SorterItemInfo sort = new SorterItemInfo("number");
    		sorc.add(sort);

            FilterInfo filter = new FilterInfo();
            view.setFilter(filter);
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("CU2.id", cuId));
            fic.add(new FilterItemInfo("CU2.id", null,CompareType.NOTEQUALS ));
            fic.add(new FilterItemInfo("CU.id", cuId));
            filter.setMaskString("(#0 and #1) or #2 ");

            ((KDBizPromptBox)box).setEntityViewInfo(view);
        }else{
            //��ֹ����������ͨ��F7��ѡ�������޷���������
//            ((KDBizPromptBox)box).setQueryInfo(null);
        }
	}
	
	public static String getOprtState(String oprtState){
		String state = null;
		if (oprtState.equals(OprtState.ADDNEW)) {
			state = "����";
		}else if (oprtState.equals(OprtState.EDIT)) {
			state = "�޸�";
		}else if (oprtState.equals(OprtState.VIEW)) {
			state = "�鿴";
		}
		return state;
	}
	
	 /**
	 *  ���ְԱ_��ְ��� ��Ҫְλ�Ĳ�����Ϣ
	 * @return
	 */
	public static AdminOrgUnitInfo getPosiMemByDeptUser(PersonInfo person) {
		
		AdminOrgUnitInfo workDept = null;
		PositionMemberCollection positMems = getPositionMemberByUser(person);
		
		if (positMems == null)	
			return workDept;
		
		for (int i = 0; i < positMems.size(); i++) {
			if (positMems.get(i).isIsPrimary()) {
				workDept = positMems.get(i).getPosition().getAdminOrgUnit();
				return workDept;
			}
		}
		return workDept;
	}
	/**
	 * �����û� ���ְԱ_��ְ��� ְλ�б�
	 * 
	 * @param person
	 * 
	 * @return
	 */
	public static  PositionMemberCollection getPositionMemberByUser(PersonInfo person) {
		
		try {
			String oql = "select *, person.*, position.adminOrgUnit.* where person.id = '" + person.getId() + "'" ;
			return PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(oql);
		} catch (Exception e1) {
		}
		return null;
	}
	/**
	 * ListUI����״̬����
	 * @param table table����
	 * @param stateName table����ʾ״̬������
	 * @param listUIName ��ǰUI����
	 */
	public static void controlStateListUI(KDTable table,String stateName,ListUI listUIName,String btnName){
		ArrayList blocks = table.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();		
			int bottom = block.getBottom();
			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
					if(btnName.equals("�޸�")&&Boolean.TRUE.equals(table.getRow(rowIndex).getCell(stateName).getValue())){
						MsgBox.showInfo(listUIName, "����״̬�µĻ������ϲ����޸ģ�");
						SysUtil.abort();
					}else if(btnName.equals("ɾ��")&&Boolean.TRUE.equals(table.getRow(rowIndex).getCell(stateName).getValue())){
						MsgBox.showInfo(listUIName, "����״̬�µĻ������ϲ���ɾ����");
						SysUtil.abort();
					}else if(btnName.equals("����")&&Boolean.TRUE.equals(table.getRow(rowIndex).getCell(stateName).getValue())){
						MsgBox.showInfo(listUIName, "����״̬�µĻ������ϲ������ã�");
						SysUtil.abort();
					}else if(btnName.equals("����")&&Boolean.FALSE.equals(table.getRow(rowIndex).getCell(stateName).getValue())){
						MsgBox.showInfo(listUIName, "����״̬�µĻ������ϲ��ܽ��ã�");
						SysUtil.abort();
					}
			}
		}
	}
	public static void coStateEditUI(KDCheckBox kdbox,String btnName){
		if ("�޸�".equals(btnName)&&kdbox.isSelected()) {
			MsgBox.showInfo("����״̬�µĻ������ϲ��ܽ����޸ģ�");
			SysUtil.abort();
		}
		if ("ɾ��".equals(btnName)&&kdbox.isSelected()) {
			MsgBox.showInfo("����״̬�µĻ������ϲ��ܽ���ɾ����");
			SysUtil.abort();
		}
	}
	/**
	 * �ɲ��ŵõ�������֯
	 */
	public static CompanyOrgUnitInfo getComOrgByAdminOrg(Context ctx,
			AdminOrgUnitInfo adminOrgUnitInfo) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo costCenterOrgUnitInfo = null;
		AdminOrgUnitInfo parentCost = null;
		parentCost = adminOrgUnitInfo;
		do {
			if (parentCost == null)
				break;
			if (parentCost.isIsCompanyOrgUnit()) {
				String id = parentCost.getId().toString();
				if (id == null)
					continue;
				ICompanyOrgUnit iCompanyOrgUnit = null;
				if(ctx!=null)
					iCompanyOrgUnit = CompanyOrgUnitFactory.getLocalInstance(ctx);
				else
					iCompanyOrgUnit = CompanyOrgUnitFactory.getRemoteInstance();
				costCenterOrgUnitInfo = (CompanyOrgUnitInfo) iCompanyOrgUnit.getValue(new ObjectUuidPK(id));
				if(costCenterOrgUnitInfo.isIsBizUnit()){
                	break;
                }
			}
			parentCost = parentCost.getParent();
			if (parentCost != null) {
				String id = parentCost.getId().toString();
				if (id != null) {
					IAdminOrgUnit iAdmin = null;
					if (ctx!=null) 
						iAdmin = AdminOrgUnitFactory.getLocalInstance(ctx);
					else
						iAdmin = AdminOrgUnitFactory.getRemoteInstance();
					parentCost = (AdminOrgUnitInfo) iAdmin.getValue(new ObjectUuidPK(id));
				}
			}
		} while (true);
		return costCenterOrgUnitInfo;
	}
	
}
