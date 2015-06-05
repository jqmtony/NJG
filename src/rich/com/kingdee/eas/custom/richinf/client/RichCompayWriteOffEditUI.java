/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.custom.richinf.IRichCompayWriteOff;
import com.kingdee.eas.custom.richinf.RichExamedCollection;
import com.kingdee.eas.custom.richinf.RichExamedFactory;
import com.kingdee.eas.custom.richinf.RichExamedInfo;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.eas.fi.ar.OtherBillFactory;
import com.kingdee.eas.fi.ar.OtherBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RichCompayWriteOffEditUI extends AbstractRichCompayWriteOffEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichCompayWriteOffEditUI.class);
    
    /**
     * output class constructor
     */
    public RichCompayWriteOffEditUI() throws Exception
    {
        super();
        prmtsales.addDataChangeListener(new DataChangeListener(){
        	public void dataChanged(DataChangeEvent dce) {
        		prmtsales_DataChaged(dce);
        	}
        });
        prmtkpCustomer.addDataChangeListener(new DataChangeListener(){
        	public void dataChanged(DataChangeEvent dce) {
        		kdCustomer_DataChaged(dce);
        	}
        });
    }
    
    protected void kdCustomer_DataChaged(DataChangeEvent dce) {
    	//�������Աû��ѡ���򲻽��в���
    	if(prmtsales.getValue() != null ){
    		if(dce.getNewValue() == null){
    			kdtDjEntry.removeRows();
//    			Set<String> customerids = checkOutRichExamed(null);
    			Set<String> customerids = checkOutRichExamedByOql(null);
    			kdtFpEntry.removeRows();
    			checkOutFPByOql(customerids);
    		}else if(!dce.getNewValue().equals(dce.getOldValue())){
    			kdtDjEntry.removeRows();
//    			Set<String> customerids = checkOutRichExamed(dce.getNewValue());
    			Set<String> customerids = checkOutRichExamedByOql(dce.getNewValue());
    			kdtFpEntry.removeRows();
//    			checkOutFP(customerids);
    			checkOutFPByOql(customerids);
    		}
    		
    	}
	}

	@Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	initEntry();
    	//�ڲ��ĺ�������Ҫ�����ⲿ�ͻ�
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	evi.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("IsInternalCompany","1"));
    	prmtkpCustomer.setEntityViewInfo(evi);
    	
    	//���ݵ�ǰ��֯����Ա��
    	if(couInfo != null){
    		EntityViewInfo evi1 = new EntityViewInfo();
    		FilterInfo filter1 = new FilterInfo();
    		evi1.setFilter(filter1);
    		filter1.getFilterItems().add(new FilterItemInfo("AdminOrgUnit.longNumber",couInfo.getLongNumber()+"%",CompareType.LIKE));
    		prmtsales.setEntityViewInfo(evi1);
    		
    	}
    	
    }
	
	
    CompanyOrgUnitInfo couInfo = SysContext.getSysContext().getCurrentFIUnit();
    
    void prmtsales_DataChaged(DataChangeEvent dce){
    	if(dce.getNewValue()==null ){
    		kdtDjEntry.removeRows();
    		kdtFpEntry.removeRows();
    	}else if(!dce.getNewValue().equals(dce.getOldValue())){
    		//��������Ա�Զ��������쵥�ͷ�Ʊ
			kdtDjEntry.removeRows();
			Set<String> customerids = checkOutRichExamedByOql(dce.getNewValue());
			kdtFpEntry.removeRows();
//			checkOutFP(customerids);
			checkOutFPByOql(customerids);
		}
    	
    }
    
    public void setPersonF7(KDBizPromptBox box){
    	CtrlUnitInfo cuinfo = SysContext.getSysContext().getCurrentCtrlUnit();
    	String cuId = null;
    	if(cuinfo != null)
    		cuId = cuinfo.getId().toString();
    	HashMap param = new HashMap();
        if(cuId != null)
            param.put("CU_ID", cuId);
        else
            param.put("All_Admins", "YES");
        box.setHasCUDefaultFilter(false);
        PersonPromptBox pmt = new PersonPromptBox(this, param);
        pmt.setIsSingleSelect(true);
        box.setSelector(pmt);
        if(cuId != null){
            EntityViewInfo view = new EntityViewInfo();
            SorterItemCollection sorc = view.getSorter();
            SorterItemInfo sort = new SorterItemInfo("number");
            sorc.add(sort);
            FilterInfo filter = new FilterInfo();
            view.setFilter(filter);
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("CU2.id", cuId));
            fic.add(new FilterItemInfo("CU2.id", null, CompareType.NOTEQUALS));
            fic.add(new FilterItemInfo("CU.id", cuId));
            filter.setMaskString("(#0 and #1) or #2 ");
            box.setEntityViewInfo(view);
        }
    }
    
    private void checkOutFP(Set<String> customerids) {
    	//���쵥�ͷ�Ʊͨ����Ʊ��λ����
		if(customerids.size() > 0){
			EntityViewInfo evi1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			evi1.setFilter(filter1);
			SelectorItemCollection sic1 = new SelectorItemCollection();
			sic1.add("id");
			sic1.add("name");
			sic1.add("number");
			sic1.add("ldNo");
			sic1.add("yhxAmount");
			sic1.add("TotalAmount");
			sic1.add("asstactid");
			sic1.add("asstactname");
			sic1.add("asstactnumber");
			sic1.add("Company.id");
			sic1.add("Company.name");
			sic1.add("Company.number");
			evi1.setSelector(sic1);
			filter1.getFilterItems().add(new FilterItemInfo("asstactid",customerids,CompareType.INCLUDE));
			if(couInfo != null){
				filter1.getFilterItems().add(new FilterItemInfo("Company.id",couInfo.getId().toString()));
				filter1.setMaskString("#0 and #1");
			}
			try {
				OtherBillCollection obColl = OtherBillFactory.getRemoteInstance().getOtherBillCollection(evi1);
				IRow irow = null;
				OtherBillInfo obinfo = null;
				CustomerInfo customer = null;
				for(int i=obColl.size()-1; i>=0; i--){
					irow = kdtFpEntry.addRow();
					obinfo = obColl.get(i);
					customer = new CustomerInfo();
					customer.setId(BOSUuid.read(obinfo.getAsstActID()));
					customer.setName(obinfo.getAsstActName());
					customer.setNumber(obinfo.getAsstActNumber());
					irow.getCell("fpNo").setValue(obinfo);
					irow.getCell("kpUnit").setValue(customer);
					irow.getCell("kpCompany").setValue(obinfo.getCompany());
					irow.getCell("fpAmount").setValue(obinfo.getTotalAmount());
					irow.getCell("yhxAmount").setValue(obinfo.get("yhxAmount"));
				}
			} catch (BOSException e) {
				handUIException(e);
			}
		}
    }
    private Set<String> checkOutRichExamedByOql(Object obj) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("select id,name,number,ldNumber,amount,yhxamount,kpUnit.id,kpUnit.name,kpUnit.number,djCompany.id,djCompany.name,djCompany.number");
    	sb.append(" where kpUnit.IsInternalCompany=1 and ");
    	if(obj == null) {
    		sb.append("sales.id='");
    		sb.append(((PersonInfo)prmtsales.getValue()).getId().toString());
    		sb.append("'");
    	}else if(obj instanceof PersonInfo){
    		sb.append("sales.id='");
    		sb.append(((PersonInfo)obj).getId().toString());
    		sb.append("'");
    		if(prmtkpCustomer.getValue() != null){
    			sb.append(" and kpUnit.id='");
        		sb.append(((CustomerInfo)prmtkpCustomer.getValue()).getId().toString());
        		sb.append("'");
			}
    	}else if(obj instanceof CustomerInfo){
    		sb.append("sales.id='");
    		sb.append(((PersonInfo)prmtsales.getValue()).getId().toString());
    		sb.append("' and kpUnit.id='");
    		sb.append(((CustomerInfo)prmtkpCustomer.getValue()).getId().toString());
    		sb.append("'");
		}
    	
    	if(couInfo != null){
    		sb.append(" and kpCompany.id='");
    		sb.append(couInfo.getId().toString());
    		sb.append("' order by kpUnit.name");
		}else{
			sb.append(" order by kpUnit.name");
		}
    	
    	Set<String> customerids = new HashSet<String>();
		try {
			RichExamedCollection reColl = RichExamedFactory.getRemoteInstance().getRichExamedCollection(sb.toString());
			IRow row = null;
			RichExamedInfo info = null;
			CustomerInfo customer = null;
			for(int i=reColl.size()-1; i>=0; i--){
				info = reColl.get(i);
				row = kdtDjEntry.addRow();
				customer = info.getKpUnit();
				row.getCell("kpUnit").setValue(customer);
				row.getCell("djjg").setValue(info.getDjCompany());
				row.getCell("djNo").setValue(info);
				row.getCell("ldNo").setValue(info.getLdNumber());
				row.getCell("jsAmount").setValue(info.getAmount());
				row.getCell("dj_yhx").setValue(info.getYhxAmount());
				customerids.add(customer.getId().toString());
			}
			
		} catch (BOSException e) {
			handUIException(e);
		}
		return customerids;
    }
    
    private void checkOutFPByOql(Set<String> customerids) {
    	//���쵥�ͷ�Ʊͨ����Ʊ��λ����
		if(customerids.size() > 0){
			StringBuffer sb = new StringBuffer();
	    	sb.append("select id,name,number,ldNo,TotalAmount,yhxamount,asstactid,asstactname,asstactnumber,Company.id,Company.name,Company.number");
	    	sb.append(" where asstactid in (");
	    	Object[] it = customerids.toArray();
	    	for(int i=it.length-1; i>=0; i--){
	    		sb.append("'");
	    		sb.append((String)it[i]);
	    		if(i==0){
	    			sb.append("' ");
	    		}else
	    			sb.append("', ");
	    	}
	    	sb.append(")");
	    	if(couInfo != null){
	    		sb.append(" and Company.id='");
	    		sb.append(couInfo.getId().toString());
	    		sb.append("' order by asstactname");
			}else {
				sb.append(" order by asstactname");
			}
	    	
			try {
				OtherBillCollection obColl = OtherBillFactory.getRemoteInstance().getOtherBillCollection(sb.toString());
				IRow irow = null;
				OtherBillInfo obinfo = null;
				CustomerInfo customer = null;
				for(int i=obColl.size()-1; i>=0; i--){
					irow = kdtFpEntry.addRow();
					obinfo = obColl.get(i);
					customer = new CustomerInfo();
					customer.setId(BOSUuid.read(obinfo.getAsstActID()));
					customer.setName(obinfo.getAsstActName());
					customer.setNumber(obinfo.getAsstActNumber());
					irow.getCell("fpNo").setValue(obinfo);
					irow.getCell("kpUnit").setValue(customer);
					irow.getCell("kpCompany").setValue(obinfo.getCompany());
					irow.getCell("fpAmount").setValue(obinfo.getTotalAmount());
					irow.getCell("yhxAmount").setValue(obinfo.get("yhxAmount"));
				}
			} catch (BOSException e) {
				handUIException(e);
			}
		}
    }
    
	private Set<String> checkOutRichExamed(Object obj) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("ldNumber");
		sic.add("amount");
		sic.add("yhxamount");
		sic.add("kpUnit.id");
		sic.add("kpUnit.name");
		sic.add("kpUnit.number");
		sic.add("djCompany.id");
		sic.add("djCompany.name");
		sic.add("djCompany.number");
		evi.setSelector(sic);
		//������Ա�ı�ʱ���жϿ�Ʊ̧ͷ����ѡ�У����ѡ�У�����Ͽ�Ʊ̧ͷ����֮��Ȼ
		//����Ʊ̧ͷ�ı�ʱ������Ա�϶���ѡ�еģ����򲻽��к�������
		if(obj == null) { //only customer change null
			filter.getFilterItems().add(new FilterItemInfo("sales.id",((PersonInfo)prmtsales.getValue()).getId().toString()));
		}else if(obj instanceof PersonInfo){
			filter.getFilterItems().add(new FilterItemInfo("sales.id",((PersonInfo)obj).getId().toString()));
			if(prmtkpCustomer.getValue() != null){
				filter.getFilterItems().add(new FilterItemInfo("kpUnit.id",((CustomerInfo)prmtkpCustomer.getValue()).getId().toString()));
			}
		}else if(obj instanceof CustomerInfo){
			filter.getFilterItems().add(new FilterItemInfo("sales.id",((PersonInfo)prmtsales.getValue()).getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("kpUnit.id",((CustomerInfo)obj).getId().toString()));
		}
		//���쵥�Ŀ�Ʊ������Ӧ��ǰ������֯
		if(couInfo != null){
			filter.getFilterItems().add(new FilterItemInfo("kpCompany.id",couInfo.getId().toString()));
		}
		if(filter.getFilterItems().size() == 2) {
			filter.setMaskString("#0 and #1");
		}else if(filter.getFilterItems().size() == 3){
			filter.setMaskString("#0 and #1 and #2");
		}
		//filter.getFilterItems().add(new FilterItemInfo("yhxAmount",CompareType.EMPTY));
		Set<String> customerids = new HashSet<String>();
		try {
			RichExamedCollection reColl = RichExamedFactory.getRemoteInstance().getRichExamedCollection(evi);
			IRow row = null;
			RichExamedInfo info = null;
			CustomerInfo customer = null;
			for(int i=reColl.size()-1; i>=0; i--){
				info = reColl.get(i);
				row = kdtDjEntry.addRow();
				customer = info.getKpUnit();
				row.getCell("kpUnit").setValue(customer);
				row.getCell("djjg").setValue(info.getDjCompany());
				row.getCell("djNo").setValue(info);
				row.getCell("ldNo").setValue(info.getLdNumber());
				row.getCell("jsAmount").setValue(info.getAmount());
				row.getCell("dj_yhx").setValue(info.getYhxAmount());
				customerids.add(customer.getId().toString());
			}
			
		} catch (BOSException e) {
			handUIException(e);
		}
		return customerids;
	}
    
    public void initEntry() {
		kdtDjEntry_detailPanel.setTitle("���쵥");
    	kdtFpEntry_detailPanel.setTitle("��Ʊ��¼");
    	kdtDjEntry_detailPanel.getInsertLineButton().setVisible(false);
    	kdtFpEntry_detailPanel.getInsertLineButton().setVisible(false);
    	kdtDjEntry_detailPanel.getAddNewLineButton().setVisible(false);
    	kdtFpEntry_detailPanel.getAddNewLineButton().setVisible(false);
    	kdtFpEntry.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	kdtDjEntry.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	kdtFpEntry.getColumn("whxAmount").getStyleAttributes().setHided(true);
    	//���õ��쵥����
    	kdtDjEntry.getColumn("kpUnit").getStyleAttributes().setLocked(true);
    	kdtDjEntry.getColumn("djjg").getStyleAttributes().setLocked(true);
    	kdtDjEntry.getColumn("djNo").getStyleAttributes().setLocked(true);
    	kdtDjEntry.getColumn("dj_yhx").getStyleAttributes().setLocked(true);
    	kdtDjEntry.getColumn("bencihx").getStyleAttributes().setLocked(true);
    	//���÷�Ʊ����
    	kdtFpEntry.getColumn("fpNo").getStyleAttributes().setLocked(true);
    	kdtFpEntry.getColumn("kpUnit").getStyleAttributes().setLocked(true);
    	kdtFpEntry.getColumn("kpCompany").getStyleAttributes().setLocked(true);
    	kdtFpEntry.getColumn("fpAmount").getStyleAttributes().setLocked(true);
    	kdtFpEntry.getColumn("bencihx").getStyleAttributes().setLocked(true);
    	
	}

    IRichCompayWriteOff ir = (IRichCompayWriteOff)getBizInterface();
	//����
    @Override
    protected void kdb_hx_actionPerformed(ActionEvent e) throws Exception {
    	checkSaveState();
    	//������޷�Ʊ�͵��쵥
    	Set<Integer> djIndexs = new HashSet<Integer>();
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = kdtDjEntry.getSelectManager();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			djIndexs.add(j);
			}
		}
    	if(djIndexs.size() == 0){
    		MsgBox.showInfo("������ѡ��һ�����쵥��");
    		SysUtil.abort();
    	}
    	int rowIndex = -1;
    	BigDecimal amount = null;
    	BigDecimal yhx = null;
    	String customer = null;
    	Set<String> dj_customer = new HashSet<String>();
    	RichExamedCollection reColl = new RichExamedCollection();
    	for(Iterator<Integer> it = djIndexs.iterator(); it.hasNext();){
    		rowIndex = it.next();
    		amount = new BigDecimal((String)kdtDjEntry.getCell(rowIndex,"jsAmount").getValue());
    		yhx = (BigDecimal)kdtDjEntry.getCell(rowIndex,"dj_yhx").getValue();
    		if(amount!=null && yhx!=null && amount.compareTo(yhx)==0){
    			MsgBox.showInfo("��"+(rowIndex+1)+"�е��쵥��ȫ��������������ѡ��");
    			SysUtil.abort();
    		}
    		customer = ((CustomerInfo)kdtDjEntry.getCell(rowIndex,"kpUnit").getValue()).getId().toString();
    		reColl.add((RichExamedInfo)kdtDjEntry.getCell(rowIndex,"djNo").getValue());
    		dj_customer.add(customer);
    	}
    	Set<Integer> fpIndexs = new HashSet<Integer>();
    	selectManger = kdtFpEntry.getSelectManager();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			fpIndexs.add(j);
			}
		}
    	if(fpIndexs.size() == 0){
    		MsgBox.showInfo("������ѡ��һ����Ʊ��");
    		SysUtil.abort();
    	}
    	Set<String> fp_customer = new HashSet<String>();
    	OtherBillCollection obColl = new OtherBillCollection();
    	for(Iterator<Integer> it = fpIndexs.iterator(); it.hasNext();){
    		rowIndex = it.next();
    		amount = (BigDecimal)kdtFpEntry.getCell(rowIndex,"fpAmount").getValue();
    		yhx = (BigDecimal)kdtFpEntry.getCell(rowIndex,"yhxAmount").getValue();
    		if(amount!=null && yhx!=null && amount.compareTo(yhx)==0){
    			MsgBox.showInfo("��"+(rowIndex+1)+"�з�Ʊ��ȫ��������������ѡ��");
    			SysUtil.abort();
    		}
    		customer = ((CustomerInfo)kdtFpEntry.getCell(rowIndex,"kpUnit").getValue()).getId().toString();
    		obColl.add((OtherBillInfo)kdtFpEntry.getCell(rowIndex,"fpNo").getValue());
    		fp_customer.add(customer);
    	}
    	fp_customer.addAll(dj_customer);
    	if(fp_customer.size() != dj_customer.size()){
    		MsgBox.showInfo("���쵥�뷢Ʊ��ƥ�䣡����ϸ��飡");
			SysUtil.abort();
    	}
    	int flag = MsgBox.showConfirm2("�ֹ��������Ƿ������");
    	if(flag == MsgBox.OK) {
    		if(ir.aboutHxAndFanHx(obColl,reColl,1,editData)){
    			MsgBox.showInfo("�ֹ������ɹ���");
    			flashTable();
    			
    		}
    	}
    }
  
    //������
    @Override
    protected void kDButton1_actionPerformed(ActionEvent e) throws Exception {
    	checkSaveState();
    	//������޷�Ʊ�͵��쵥
    	Set<Integer> djIndexs = new HashSet<Integer>();
    	KDTSelectBlock selectBlock = null;
    	KDTSelectManager selectManger = kdtDjEntry.getSelectManager();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			djIndexs.add(j);
			}
		}
    	if(djIndexs.size() == 0){
    		MsgBox.showInfo("������ѡ��һ�����쵥��");
    		SysUtil.abort();
    	}
    	
    	int rowIndex = -1;
    	BigDecimal yhx = null;
    	String customer = null;
    	Set<String> dj_customer = new HashSet<String>();
    	RichExamedCollection reColl = new RichExamedCollection();
    	for(Iterator<Integer> it = djIndexs.iterator(); it.hasNext();){
    		rowIndex = it.next();
//    		yhx = (BigDecimal)kdtDjEntry.getCell(rowIndex,"dj_yhx").getValue();
    		yhx = (BigDecimal)kdtDjEntry.getCell(rowIndex,"bencihx").getValue();
    		if(yhx==null || yhx.compareTo(BigDecimal.ZERO)==0){
    			MsgBox.showInfo("��"+(rowIndex+1)+"�е��쵥δ���к�����������ѡ��");
    			SysUtil.abort();
    		}
    		customer = ((CustomerInfo)kdtDjEntry.getCell(rowIndex,"kpUnit").getValue()).getId().toString();
    		dj_customer.add(customer);
    		reColl.add((RichExamedInfo)kdtDjEntry.getCell(rowIndex,"djNo").getValue());
    	}
    	Set<Integer> fpIndexs = new HashSet<Integer>();
    	selectManger = kdtFpEntry.getSelectManager();
    	for (int i = 0; i < selectManger.size(); i++) {
    		selectBlock = selectManger.get(i);
    		for (int j = selectBlock.getBeginRow(); j <=selectBlock.getEndRow(); j++) {
    			fpIndexs.add(j);
			}
		}
    	if(fpIndexs.size() == 0){
    		MsgBox.showInfo("������ѡ��һ����Ʊ��");
    		SysUtil.abort();
    	}
    	Set<String> fp_customer = new HashSet<String>();
    	OtherBillCollection obColl = new OtherBillCollection();
    	for(Iterator<Integer> it = fpIndexs.iterator(); it.hasNext();){
    		rowIndex = it.next();
//    		yhx = (BigDecimal)kdtFpEntry.getCell(rowIndex,"yhxAmount").getValue();
    		yhx = (BigDecimal)kdtFpEntry.getCell(rowIndex,"bencihx").getValue();
    		if(yhx==null || yhx.compareTo(BigDecimal.ZERO)==0){
    			MsgBox.showInfo("��"+(rowIndex+1)+"�з�Ʊδ���к�����������ѡ��");
    			SysUtil.abort();
    		}
    		customer = ((CustomerInfo)kdtFpEntry.getCell(rowIndex,"kpUnit").getValue()).getId().toString();
    		fp_customer.add(customer);
    		obColl.add((OtherBillInfo)kdtFpEntry.getCell(rowIndex,"fpNo").getValue());
    	}
    	fp_customer.addAll(dj_customer);
    	if(fp_customer.size() != dj_customer.size()){
    		MsgBox.showInfo("���쵥�뷢Ʊ��ƥ�䣡����ϸ��飡");
			SysUtil.abort();
    	}
    	int flag = MsgBox.showConfirm2("���������Ƿ������");
    	if(flag == MsgBox.OK) {
    		ir.aboutHxAndFanHx(obColl,reColl,-1,editData);
    		MsgBox.showInfo("�������ɹ���");
    		flashTable();
    	}
    }
    
   @Override
	protected void autoHX_actionPerformed(ActionEvent e) throws Exception {
	   checkSaveState();
		 //У���Ƿ�߱��Զ�������������ͻ�ȷ�ϲ���
	   	Map<String,BigDecimal> dj_whx =  new HashMap<String,BigDecimal>(); 
	   	Map<String,BigDecimal> fp_whx =  new HashMap<String,BigDecimal>();
//	   	Map<String,Integer> djIdRow =  new HashMap<String,Integer>();
//	   	Map<String,Integer> fpIdRow =  new HashMap<String,Integer>();
	   	BigDecimal amount = null;
	   	BigDecimal yhx = null;
	   	BigDecimal whx = null;
	   	String customerId = null;
	   	
	   	RichExamedCollection reColl = new RichExamedCollection();
	   	for(int i=kdtDjEntry.getRowCount3()-1; i>=0; i--){
	   		amount = new BigDecimal((String)kdtDjEntry.getCell(i,"jsAmount").getValue());
	   		yhx = (BigDecimal)kdtDjEntry.getCell(i,"dj_yhx").getValue();
	   		customerId = ((CustomerInfo)kdtDjEntry.getCell(i,"kpUnit").getValue()).getId().toString();
	   		if(yhx == null) {
	   			whx = amount;
	   		}else if(amount.compareTo(yhx) > 0){
	   			whx = amount.subtract(yhx);
	   		}else{
	   			continue;
	   		}
	   		if(dj_whx.get(customerId) != null){
	   			dj_whx.put(customerId,whx.add(dj_whx.get(customerId)));
	   		}else{
	   			dj_whx.put(customerId,whx);
	   		}
	   		reColl.add((RichExamedInfo)kdtDjEntry.getCell(i,"djNo").getValue());
//	   		djIdRow.put(djId,i);
	   	}
	   	
	   	whx = null;
	   	OtherBillCollection obColl = new OtherBillCollection();
	   	for(int i=kdtFpEntry.getRowCount3()-1; i>=0; i--){
	   		amount = (BigDecimal)kdtFpEntry.getCell(i,"fpAmount").getValue();
	   		yhx = (BigDecimal)kdtFpEntry.getCell(i,"yhxAmount").getValue();
	   		customerId = ((CustomerInfo)kdtFpEntry.getCell(i,"kpUnit").getValue()).getId().toString();
	   		if(yhx == null) {
	   			whx = amount;
	   		}else if(amount.compareTo(yhx) > 0){
	   			whx = amount.subtract(yhx);
	   		}else{
	   			continue;
	   		}
	   		if(fp_whx.get(customerId) != null){
	   			fp_whx.put(customerId,whx.add(fp_whx.get(customerId)));
	   		}else{
	   			fp_whx.put(customerId,whx);
	   		}
	   		obColl.add((OtherBillInfo)kdtFpEntry.getCell(i,"fpNo").getValue());
//	   		djId = obInfo.getId().toString();
//	   		fpIdRow.put(djId,i);
	   	}
	   	
	   	int cuont = 0;
	   	for(Iterator<String> it = fp_whx.keySet().iterator(); it.hasNext();) {
	   		if(!dj_whx.containsKey(it.next())){
	   			cuont++;
	   		}
	   	}
	   	if(cuont == fp_whx.size()){
	   		MsgBox.showInfo("û�����Ӧ�ķ�Ʊ�͵��쵥�����ܽ����Զ�����������");
	   		SysUtil.abort();
	   	}
	   	int flag = MsgBox.showConfirm2("�Զ��������Ƿ������");
    	if(flag == MsgBox.OK) {
    		ir.aboutHxAndFanHx(obColl,reColl,0,editData);
    		MsgBox.showInfo("�Զ������ɹ���");
    		flashTable();
    	}
	}
   
   private void checkSaveState(){
	   if(getOprtState().equals(OprtState.VIEW)){
	   		SysUtil.abort();
	   }
	   if(isModify()) {
		   int result = MsgBox.showConfirm2("�����б䶯�����ȱ��浥�ݣ�\n������ȷ������ť���浥�ݣ��Ա����������");
		   if(result == MsgBox.OK){
			   actionSave.setDaemonRun(false);
               ActionEvent event = new ActionEvent(btnSave, 1001, btnSave.getActionCommand());
               actionSave.actionPerformed(event);
               if(actionSave.isInvokeFailed())
                   SysUtil.abort();
		   }
		   else {
			   SysUtil.abort();
		   }
	   }
   }
   
   private void flashTable(){
	   try {
//		super.onLoad();
		loadData();
//		loadFields();
//		onShow();
	} catch (Exception e) {
		handUIException(e);
	}
	   
	   
   }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    @Override
    public void kdtDjEntry_Changed(int rowIndex, int colIndex) throws Exception {
    }
    
    @Override
    public void kdtFpEntry_Changed(int rowIndex, int colIndex) throws Exception {
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichCompayWriteOffFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo objectValue = new com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setBizDate(new Date());
        return objectValue;
    }

}