/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.LanguageInfo;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.UIHelper;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.HisProjCostEntriesCollection;
import com.kingdee.eas.fdc.basedata.HisProjCostEntriesInfo;
import com.kingdee.eas.fdc.basedata.HisProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.HisProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.HisProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.HisProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.HisProjectCollection;
import com.kingdee.eas.fdc.basedata.HisProjectFactory;
import com.kingdee.eas.fdc.basedata.HisProjectInfo;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.IHisProject;
import com.kingdee.eas.fdc.basedata.IProjectFacade;
import com.kingdee.eas.fdc.basedata.ProjectFacadeFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:������Ŀ�汾�޶�
 * 
 * @author jackwang date:2006-8-9
 *         <p>
 * @version EAS5.1
 */
public class ProjectVersionRedactUI extends AbstractProjectVersionRedactUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectVersionRedactUI.class);

	private HisProjectInfo hisProjectPre = null;

	private HisProjectInfo hisProjectNext = null;

	private HisProjectCollection hisProjectCollection = null;

	private CurProjectInfo curProjectInfo = null;

	private CurProjectInfo curProjectInfoSave = null;

	private HisProjectInfo reCache = null;

	private String curProjectID = null;

	private int curVersion = 1;

	/**
	 * output class constructor
	 */
	public ProjectVersionRedactUI() throws Exception {
		super();
	}

	/**
	 * ����:��д����,��Ӱ�ťͼ��
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnFirst.setIcon(EASResource.getIcon("imgTbtn_first"));
		this.btnPrev.setIcon(EASResource.getIcon("imgTbtn_previous"));
		this.btnNext.setIcon(EASResource.getIcon("imgTbtn_next"));
		this.btnLast.setIcon(EASResource.getIcon("imgTbtn_last"));
	}

	/**
	 * ���������ÿؼ�״̬
	 */
	private void initComponentStatus() {
		this.tblCostEntries.checkParsed();
		// ��ʼ���ɱ���¼
		this.tblCostEntries.getColumn("apportionType").setWidth(120);
		this.tblCostEntries.getColumn("apportionType").getStyleAttributes().setLocked(true);
		this.tblCostEntries.getColumn("valueOld").setWidth(146);
		this.tblCostEntries.getColumn("valueOld").getStyleAttributes().setLocked(true);
		this.tblCostEntries.getColumn("valueNew").setWidth(146);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		initComponentStatus();// ��ʼ�����ÿؼ�״̬
		if (this.getUIContext().get(UIContext.ID) != null) {
			curProjectID = getUIContext().get(UIContext.ID).toString();
		}
		this.curProjectInfo = getCurProjectInfo();// ��ȡ��ǰ�����Ĺ�����Ŀ
		this.reCache = transFromCurToHis(curProjectInfo);// �ӵ�ǰ������Ŀ���Ƴ�һ������
		// this.hisProjectInfo = hisProjectCache; //������Ǽ���������ʷ��¼
		this.hisProjectCollection = getHisProjectCollection();// ��ʱ�����������ʷ�汾
		if (hisProjectCollection.size() != 0) {
			curVersion = this.hisProjectCollection.size() - 1;// ��ʼ��Ϊ��ǰ�汾����
			// this.hisProjectLast =
			// hisProjectCollection.get(curVersion);////��ʼ������ʱ�������µ�ǰ������Ŀ����Ϣ
			// this.hisProjectFirst = hisProjectCollection.get(0);//����İ汾
		}
		loadData();
	}

	/**
	 * ����:��������
	 * 
	 * @throws BOSException
	 */
	private void loadData() throws BOSException {
		FDCHelper.formatTableNumber(this.tblCostEntries, "valueOld");
		FDCHelper.formatTableNumber(this.tblCostEntries, "valueNew");

		if (hisProjectCollection != null && hisProjectCollection.size() > 1) {// ��һ��������ʷ�汾
			hisProjectNext = hisProjectCollection.get(curVersion);
			hisProjectPre = hisProjectCollection.get(curVersion - 1);// ���������ʷ������Ŀ,ȡ����(createTime���)��һ���汾Ϊ��ǰ"��һ�汾"
			this.reCache = hisProjectNext;
			bindHisProjectToUI(hisProjectNext);
			bindHisProjectToRUI(hisProjectPre);
		} else if (hisProjectCollection != null && hisProjectCollection.size() == 1) {
			hisProjectPre = null;
			hisProjectNext = hisProjectCollection.get(curVersion);
			bindHisProjectToUI(hisProjectNext);// �Ƿ�Ӧ������ɱ���¼�ġ���һ�汾���У�����������������������������
			// for(int i = 0;i<this.tblCostEntries.getRowCount();i++){
			// this.tblCostEntries.getRow(i).getCell("valueOld").setValue(null);
			// }
			// bindHisProjectToRUI(hisProjectNext);
		}
		HashSet lnUps = new HashSet();
		for (int i = 0; i < this.tblCostEntries.getRowCount(); i++) {
			lnUps.add(((ApportionTypeInfo) this.tblCostEntries.getRow(i).getCell("apportionType").getValue()).getId());
		}
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", lnUps, CompareType.NOTINCLUDE));
		filterInfo.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true), CompareType.EQUALS));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filterInfo);
		ApportionTypeCollection otherAdd = ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(evi);
		if (otherAdd.size() != 0) {
			for (int i = 0; i < otherAdd.size(); i++) {
				IRow row = this.tblCostEntries.addRow();
				row.getCell("apportionType").setValue(otherAdd.get(i));
				row.getCell("valueNew").setValue(FDCHelper.ZERO);
			}
		}
		refreshButton();// ˢ�°汾�л���ť
	}

	/**
	 * ����:������ʷ�汾��Ϣ��ҳ��
	 */
	private void bindHisProjectToUI(HisProjectInfo hisProjectInfo) {
		// �����ʼ��bindDataToUI()
		String tmp = hisProjectInfo.getLongNumber();
		tmp = tmp.replace('!', '.');
		if (tmp.indexOf(".") > 0) {
			this.txtParentNumber.setText(tmp.substring(0, tmp.lastIndexOf(".")));// //????????û��parent
		}
		this.txtLongNumber.setText(tmp);
		this.txtNumber.setText(hisProjectInfo.getNumber());
		this.bizLandDeveloper.setValue(hisProjectInfo.getLandDeveloper());
		this.bizName.setSelectedItemData(hisProjectInfo.getName(SysContext.getSysContext().getLocale()));
		this.pkStartDate.setValue(hisProjectInfo.getStartDate());
		this.txtSortNo.setValue(new Integer(hisProjectInfo.getSortNo()));
		if ((hisProjectCollection != null) && (hisProjectCollection.size() != 0)) {// �м�¼
			for (int i = 0; i < hisProjectInfo.getHisProjCostEntries().size(); i++) {
				if (hisProjectInfo.getHisProjCostEntries().get(i).getApportionType().isIsEnabled()) {
					IRow row = this.tblCostEntries.addRow();
					row.getCell("apportionType").setValue(hisProjectInfo.getHisProjCostEntries().get(i).getApportionType());
					row.getCell("valueNew").setValue(hisProjectInfo.getHisProjCostEntries().get(i).getValue());
				}
			}
		}
		// this.tblCostEntries.getRowCount();
		// this.tblCostEntries.getRow(1).getStyleAttributes().setHided(true);
		// this.tblCostEntries.getRowCount();
		// �����Զ�λ
		List langList = KDBizMultiLangBox.getLanguageList();
		for (int i = 0; i < langList.size(); i++) {
			if (SysContext.getSysContext().getLocale().toString().equalsIgnoreCase(((LanguageInfo) (langList.get(i))).getLocale().toString()))
				bizVersionName.setSelectedLanguage((LanguageInfo) langList.get(i));
		}
		// bizVersionName.getSelectedItem();
		txtVersionNumber.setText(hisProjectInfo.getVersionNumber());
		bizVersionName.setSelectedItemData(hisProjectInfo.getVersionName(SysContext.getSysContext().getLocale()));
		this.txtDescription.setSelectedItemData(hisProjectInfo.getDescription(SysContext.getSysContext().getLocale()));
		// if(hisProjectInfo.isOnlyApplyObjCost()){
		this.kdOnlyApplyObjCost.setSelected(hisProjectInfo.isOnlyApplyObjCost());
		// }
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		if (!checkData()) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.VERSIONSAVE_CANNOT_SAVE);
		}
		// ������֤
		// updateHisCostEntriesData();//���������湤����Ŀ�汾�ĳɱ���¼��Ϣ
		updateCurCostEntriesData();// ���µ�ǰ������Ŀ�ĳɱ���¼��Ϣ
		HisProjectInfo hisProjectInfoSave = transFromCurToHis(curProjectInfoSave);// Ҳ����ֱ�Ӵӽ�����·�¼���ݣ��ɲο�����updateCurCostEntriesData()
		UIHelper.storeMultiLangFields(this.bizVersionName, hisProjectInfoSave, "versionName");
		UIHelper.storeMultiLangFields(this.bizName, hisProjectInfoSave, "name");
		UIHelper.storeMultiLangFields(this.txtDescription, hisProjectInfoSave, "description");
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizVersionName, hisProjectInfoSave, "versionName");
		if (flag) {
			throw new FDCBasedataException(FDCBasedataException.VERSIONSAVE_NAME_ISNULL);
		}
		// ������Ŀ��ɱ�
		hisProjectInfoSave.setOnlyApplyObjCost(this.kdOnlyApplyObjCost.isSelected());
		IProjectFacade iProjectFacade = ProjectFacadeFactory.getRemoteInstance();
		// �ڰ汾��
		if (this.hisProjectCollection.size() != 0) {
			int o = this.hisProjectCollection.size();
			String beforeNumber = this.hisProjectCollection.get(o - 1).getVersionNumber();
			if (beforeNumber != null && beforeNumber.trim().length() != 0) {
				String num = beforeNumber.substring(1, beforeNumber.lastIndexOf('.'));
				int i = (new Integer(num)).intValue();
				i += 1;
				hisProjectInfoSave.setVersionNumber("V" + i + ".0");
			} else {
				hisProjectInfoSave.setVersionNumber("V2.0");
			}
		}

		// �༭��ʷ�汾
		iProjectFacade.updateHisProjectInfo(this.trans(hisProjectInfoSave));// ��������
		// ���µ�ǰ������Ŀ
		// ICurProject iCurProject = CurProjectFactory.getRemoteInstance();
		// iProjectFacade.updateCurProjectInfo(curProjectInfo);//������Ŀ�޸Ķ���,������������һ����ʷ����
		this.tblCostEntries.removeRows();
		this.curProjectInfo = this.curProjectInfoSave;
		onLoad();

	}

	/**
	 * ����:���ݵ�ǰ����ֵ,���������湤����Ŀ�汾�ĳɱ���¼
	 */
	private void updateHisCostEntriesData() {
		// HisProjCostEntriesCollection hisProjCostEntriesCollection = new
		// HisProjCostEntriesCollection();
		// HisProjCostEntriesInfo hisProjCostEntriesInfo;
		// for(int i = 0;i<this.tblCostEntries.getRowCount();i++){
		// hisProjCostEntriesInfo = new HisProjCostEntriesInfo();
		// hisProjCostEntriesInfo.setApportionType((ApportionTypeInfo)(tblCostEntries.getRow(i).getCell("apportionType").getValue()));
		// if (this.tblCostEntries.getRow(i).getCell("valueNew").getValue() ==
		// null
		// ||
		// "".equals(this.tblCostEntries.getRow(i).getCell("valueNew").getValue().toString().trim()))
		// {
		// hisProjCostEntriesInfo.setValue(FDCHelper.ZERO);
		// } else {
		// hisProjCostEntriesInfo.setValue(new
		// BigDecimal(tblCostEntries.getRow(i).getCell("valueNew").getValue().toString()));
		// }
		// hisProjCostEntriesInfo.setHisProject(hisProjectInfo);
		// hisProjCostEntriesCollection.add(hisProjCostEntriesInfo);
		// }
		// for(int i = 0;i<hisProjectInfo.getHisProjCostEntries().size();i++){
		// hisProjectInfo.getHisProjCostEntries().removeObject(i);
		// }
		// hisProjectInfo.getHisProjCostEntries().addCollection(hisProjCostEntriesCollection);
	}

	/**
	 * ����:���ݵ�ǰ����ֵ,ˢ��curProjectInfo�ĳɱ���¼
	 */
	private void updateCurCostEntriesData() {
		CurProjCostEntriesInfo curProjCostEntriesInfo;
		CurProjCostEntriesCollection temp = new CurProjCostEntriesCollection();
		for (int i = 0; i < this.tblCostEntries.getRowCount(); i++) {
			boolean flag = false;
			for (int j = 0; j < curProjectInfoSave.getCurProjCostEntries().size(); j++) {
				if (((ApportionTypeInfo) this.tblCostEntries.getRow(i).getCell("apportionType").getValue()).getId()
						.equals(curProjectInfoSave.getCurProjCostEntries().get(j).getApportionType().getId())) {
					if (this.tblCostEntries.getRow(i).getCell("valueNew").getValue() != null) {
						curProjectInfoSave.getCurProjCostEntries().get(j).setValue(new BigDecimal(this.tblCostEntries.getRow(i).getCell("valueNew").getValue().toString()));
					} else {
						curProjectInfoSave.getCurProjCostEntries().get(j).setValue(FDCHelper.ZERO);
					}
					flag = true;
					continue;
				}
			}
			if (!flag) {// û�ܶ��Ϻţ�֤����ǰ�ɱ���¼������û�����ѡ��
				curProjCostEntriesInfo = new CurProjCostEntriesInfo();
				curProjCostEntriesInfo.setApportionType((ApportionTypeInfo) this.tblCostEntries.getRow(i).getCell("apportionType").getValue());
				if (this.tblCostEntries.getRow(i).getCell("valueNew").getValue() != null) {
					curProjCostEntriesInfo.setValue(new BigDecimal(this.tblCostEntries.getRow(i).getCell("valueNew").getValue().toString()));
				} else {
					curProjCostEntriesInfo.setValue(FDCHelper.ZERO);
				}
				temp.add(curProjCostEntriesInfo);
			}
		}
		curProjectInfoSave.getCurProjCostEntries().addObjectCollection(temp);
	}

	/**
	 * 
	 * ����������ʱ�ж�
	 * 
	 * @return
	 * @author:jackwang ����ʱ�䣺2006-8-26
	 *                  <p>
	 */
	private boolean checkData() {
		// ������ָ�����ݡ��롰��һ�汾ָ�������ݽ��бȽϣ�û�б仯������ʾָ������û���޸ģ����ܱ���
		boolean flag = false;
		this.curProjectInfoSave = (CurProjectInfo) curProjectInfo.clone();
		if (this.tblCostEntries.getRowCount() != 0) {
//			if (this.tblCostEntries.getRowCount() != this.curProjectInfoSave.getCurProjCostEntries().size()) {
//				flag = true;
//			} else {
			int m = this.tblCostEntries.getRowCount();
			int n = curProjectInfoSave.getCurProjCostEntries().size();
			if(m>=n){
				BigDecimal valueBigDecimal, tblBigDecimal;
				for (int i = 0; i < this.tblCostEntries.getRowCount(); i++) {
					for (int j = 0; j < curProjectInfoSave.getCurProjCostEntries().size(); j++) {
						if (((ApportionTypeInfo) this.tblCostEntries.getRow(i).getCell("apportionType").getValue()).getId().equals(
								curProjectInfoSave.getCurProjCostEntries().get(j).getApportionType().getId())) {
							valueBigDecimal = curProjectInfoSave.getCurProjCostEntries().get(j).getValue();
							tblBigDecimal = new BigDecimal(this.tblCostEntries.getRow(i).getCell("valueNew").getValue().toString());
							if (valueBigDecimal.compareTo(tblBigDecimal) != 0) {
								flag = true;
								break;
							}
						}
					}
				}
			}
			if(m<n){
				BigDecimal valueBigDecimal, tblBigDecimal;
				for (int i = 0; i < curProjectInfoSave.getCurProjCostEntries().size(); i++) {
					for (int j = 0; j <this.tblCostEntries.getRowCount() ; j++) {
						if (((ApportionTypeInfo) this.tblCostEntries.getRow(j).getCell("apportionType").getValue()).getId().equals(
								curProjectInfoSave.getCurProjCostEntries().get(i).getApportionType().getId())) {
							valueBigDecimal = curProjectInfoSave.getCurProjCostEntries().get(i).getValue();
							tblBigDecimal = new BigDecimal(this.tblCostEntries.getRow(j).getCell("valueNew").getValue().toString());
							if (valueBigDecimal.compareTo(tblBigDecimal) != 0) {
								flag = true;
								break;
							}
						}
					}
				}
			}
				
//			}
		}
		return flag;
	}

	/**
	 * 
	 * ����������һ��һʱ�ж�
	 * 
	 * @param curProjectInfo
	 * @return
	 * @author:jackwang ����ʱ�䣺2006-8-26
	 *                  <p>
	 */
	private boolean checkData(HisProjectInfo hisProjectInfo) {
		// �����桱�롰��ǰ�ұ߷�¼�����ݽ��бȽϣ��仯������ʾָ���������޸ģ�ѯ�ʱ���

		boolean flag = false;
		if (this.tblCostEntries.getRowCount() != 0) {
			int num = 0;
			for (int i = 0; i < hisProjectInfo.getHisProjCostEntries().size(); i++) {
				if (hisProjectInfo.getHisProjCostEntries().get(i).getApportionType().isIsEnabled())
					num++;
			}
			if (this.tblCostEntries.getRowCount() != num) {
				flag = true;
			} else {
				BigDecimal valueBigDecimal, tblBigDecimal;
				for (int i = 0; i < this.tblCostEntries.getRowCount(); i++) {
					for (int j = 0; j < hisProjectInfo.getHisProjCostEntries().size(); j++) {
						if (((ApportionTypeInfo) this.tblCostEntries.getRow(i).getCell("apportionType").getValue()).getId().equals(
								hisProjectInfo.getHisProjCostEntries().get(j).getApportionType().getId())) {
							valueBigDecimal = hisProjectInfo.getHisProjCostEntries().get(j).getValue();
							if (this.tblCostEntries.getRow(i).getCell("valueNew").getValue() != null) {
								tblBigDecimal = new BigDecimal(this.tblCostEntries.getRow(i).getCell("valueNew").getValue().toString());
							} else {
								tblBigDecimal = FDCHelper.ZERO;
							}
							if (valueBigDecimal.compareTo(tblBigDecimal) != 0) {
								flag = true;
								break;
							}
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		return sic;
	}

	/**
	 * output actionFirst_actionPerformed method
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		if (checkData(reCache)) { // �б仯
			if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Version_Change_Save")))) {
				// ������
				this.tblCostEntries.removeRows();
				curVersion = 1;
				this.loadData();
			} else {
				// ����
				this.actionSave_actionPerformed(e);
			}
		} else {// �ޱ仯
			this.tblCostEntries.removeRows();
			curVersion = 1;
			this.loadData();
		}
	}

	/**
	 * output actionPrev_actionPerformed method
	 */
	public void actionPrev_actionPerformed(ActionEvent e) throws Exception {
		if (checkData(reCache)) { // �б仯
			if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Version_Change_Save")))) {
				// ������
				this.tblCostEntries.removeRows();
				curVersion--;
				// this.hisProjectSave = this.hisProjectInfo;
				this.loadData();
			} else {
				// ����
				this.actionSave_actionPerformed(e);
			}
		} else {// �ޱ仯
			this.tblCostEntries.removeRows();
			curVersion--;
			// this.hisProjectSave = this.hisProjectInfo;
			this.loadData();
		}
	}

	/**
	 * output actionNext_actionPerformed method
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		if (checkData(reCache)) { // �б仯
			if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Version_Change_Save")))) {
				// ������
				this.tblCostEntries.removeRows();
				curVersion++;
				this.loadData();
			} else {
				// ����
				this.actionSave_actionPerformed(e);
			}
		} else {// �ޱ仯
			this.tblCostEntries.removeRows();
			curVersion++;
			this.loadData();
		}
	}

	/**
	 * output actionLast_actionPerformed method
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		if (checkData(reCache)) { // �б仯
			if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Version_Change_Save")))) {
				// ������
				this.tblCostEntries.removeRows();
				curVersion = this.hisProjectCollection.size() - 1;
				this.loadData();
			} else {
				// ����
				this.actionSave_actionPerformed(e);
			}
		} else {// �ޱ仯
			this.tblCostEntries.removeRows();
			curVersion = this.hisProjectCollection.size() - 1;
			this.loadData();
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output txtNumber_focusLost method
	 */
	protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception {
		super.txtNumber_focusLost(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * ���ص�ǰ������Ŀ����
	 * 
	 * @throws BOSException
	 */
	private CurProjectInfo getCurProjectInfo() throws BOSException {
		// ���ص�ǰ�汾,��curProject��ȡ
		ICurProject iCurProject;
		CurProjectCollection curProjectCollection = null;
		iCurProject = CurProjectFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSelector().add("*");
		evi.getSelector().add("landDeveloper.*");
		evi.getSelector().add("fullOrgUnit.*");
		evi.getSelector().add("curProjProductEntries.*");
		evi.getSelector().add("curProjProductEntries.curProjProEntrApporData.*");
		evi.getSelector().add("curProjCostEntries.*");
		evi.getSelector().add("curProjCostEntries.apportionType.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", this.curProjectID));
		evi.setFilter(filter);
		
//		SorterItemInfo sii = new SorterItemInfo("createTime");
//		sii.setSortType(SortType.DESCEND);
//		evi.getSorter().add(sii);
		
		curProjectCollection = iCurProject.getCurProjectCollection(evi);
		if (curProjectCollection != null) {
			curProjectInfo = curProjectCollection.get(0);
		}
		return curProjectInfo;
	}

	/**
	 * ������ʷ�汾����
	 * 
	 * @return
	 * @throws BOSException
	 */
	private HisProjectCollection getHisProjectCollection() throws BOSException {
		HisProjectCollection hisPC = null;
		IHisProject iHisProject;
		iHisProject = HisProjectFactory.getRemoteInstance();
		EntityViewInfo hisEvi = new EntityViewInfo();
		hisEvi.getSelector().add("*");
		hisEvi.getSelector().add("landDeveloper.*");
		hisEvi.getSelector().add("fullOrgUnit.*");
		hisEvi.getSelector().add("hisProjProductEntries.*");
		hisEvi.getSelector().add("hisProjProductEntries.hisProjProEntrApporData.*");
		hisEvi.getSelector().add("hisProjCostEntries.*");
		hisEvi.getSelector().add("hisProjCostEntries.apportionType.*");
		FilterInfo hisFilter = new FilterInfo();
		hisFilter.getFilterItems().add(new FilterItemInfo("curProject.id", this.curProjectID));
		hisEvi.setFilter(hisFilter);
		SorterItemInfo sii = new SorterItemInfo("createTime");
		sii.setSortType(SortType.ASCEND);
		hisEvi.getSorter().add(sii);
		hisPC = iHisProject.getHisProjectCollection(hisEvi);
		return hisPC;
	}

	/**
	 * �󶨵�ǰ�汾���ݵ������У�ֻ���¡�����ָ�����ݡ���
	 * 
	 */
	private void bindHisProjectToRUI(HisProjectInfo hisProjectInfo) {
		if (hisProjectInfo.getHisProjCostEntries().size() > 0) {
			HisProjCostEntriesCollection hpcec = new HisProjCostEntriesCollection();
			for (int i = 0; i < hisProjectInfo.getHisProjCostEntries().size(); i++) {
				boolean flag = false;
				for (int j = 0; j < this.tblCostEntries.getRowCount(); j++) {// �Ժ�����
					if (hisProjectInfo.getHisProjCostEntries().get(i).getApportionType().getId()
							.equals(((ApportionTypeInfo) this.tblCostEntries.getRow(j).getCell("apportionType").getValue()).getId())) {
						this.tblCostEntries.getRow(j).getCell("valueOld").setValue(hisProjectInfo.getHisProjCostEntries().get(i).getValue());
						flag = true;
						continue;
					}
				}
				if (!flag) {// û�ܶ��Ϻţ�֤����ǰ�ɱ���¼table��û�����ѡ��
					if (hisProjectInfo.getHisProjCostEntries().get(i).getApportionType().isIsEnabled())
						hpcec.add(hisProjectInfo.getHisProjCostEntries().get(i));
				}
			}
			// �����û���Ϻŵģ����������ȥ
			int star = this.tblCostEntries.getRowCount();
			for (int i = 0; i < hpcec.size(); i++) {
				IRow row = this.tblCostEntries.addRow(star + i);
				row.getCell("apportionType").setValue(hpcec.get(i).getApportionType());
				row.getCell("valueOld").setValue(hpcec.get(i).getValue());
			}
		}
	}

	private HisProjectInfo trans(HisProjectInfo temp) {
		HisProjectInfo hisProjectInfo = new HisProjectInfo();
		hisProjectInfo.setName(temp.getName(SysContext.getSysContext().getLocale()), SysContext.getSysContext().getLocale());
		hisProjectInfo.setNumber(temp.getNumber());
		hisProjectInfo.setLongNumber(temp.getLongNumber());
		hisProjectInfo.setLandDeveloper(temp.getLandDeveloper());
		hisProjectInfo.setSortNo(temp.getSortNo());
		hisProjectInfo.setStartDate(temp.getStartDate());
		hisProjectInfo.setFullOrgUnit(temp.getFullOrgUnit());
		hisProjectInfo.setVersionName(temp.getVersionName());
		hisProjectInfo.setVersionNumber(temp.getVersionNumber());
		hisProjectInfo.setOnlyApplyObjCost(temp.isOnlyApplyObjCost());
		hisProjectInfo.setDescription(temp.getDescription(SysContext.getSysContext().getLocale()), SysContext.getSysContext().getLocale());
		hisProjectInfo.setParent(null);
		hisProjectInfo.setCurProject(curProjectInfo);
		// �ɱ���¼
		HisProjCostEntriesCollection hisProjCostEntriesCollection = new HisProjCostEntriesCollection();
		HisProjCostEntriesCollection costCollectionTemp = temp.getHisProjCostEntries();

		HisProjCostEntriesInfo hisProjCostEntriesInfo;
		if (costCollectionTemp.size() > 0) {
			for (int i = 0; i < costCollectionTemp.size(); i++) {
				hisProjCostEntriesInfo = new HisProjCostEntriesInfo();
				hisProjCostEntriesInfo.setApportionType(costCollectionTemp.get(i).getApportionType());
				hisProjCostEntriesInfo.setValue(costCollectionTemp.get(i).getValue());
				hisProjCostEntriesInfo.setHisProject(hisProjectInfo);
				hisProjCostEntriesCollection.add(hisProjCostEntriesInfo);
			}
			hisProjectInfo.getHisProjCostEntries().addCollection(hisProjCostEntriesCollection);
		}
		// ��Ʒ��¼
		HisProjProductEntriesCollection hisProjProductEntriesCollection = new HisProjProductEntriesCollection();
		HisProjProductEntriesCollection productCollectionTemp = temp.getHisProjProductEntries();
		HisProjProductEntriesInfo hisprojProductEntriesInfo;
		HisProjProEntrApporDataCollection hisProjProEntrApporDataCollection;
		HisProjProEntrApporDataInfo hisProjProEntrApporDataInfo;
		if (productCollectionTemp.size() > 0) {
			for (int i = 0; i < productCollectionTemp.size(); i++) {
				hisprojProductEntriesInfo = new HisProjProductEntriesInfo();
				hisProjProEntrApporDataCollection = new HisProjProEntrApporDataCollection();
				for (int j = 0; j < productCollectionTemp.get(i).getHisProjProEntrApporData().size(); j++) {
					hisProjProEntrApporDataInfo = new HisProjProEntrApporDataInfo();
					hisProjProEntrApporDataInfo.setApportionType(productCollectionTemp.get(i).getHisProjProEntrApporData().get(j).getApportionType());
					hisProjProEntrApporDataInfo.setValue(productCollectionTemp.get(i).getHisProjProEntrApporData().get(j).getValue());

					hisProjProEntrApporDataCollection.add(hisProjProEntrApporDataInfo);
				}
				hisprojProductEntriesInfo.getHisProjProEntrApporData().addCollection(hisProjProEntrApporDataCollection);
				hisprojProductEntriesInfo.setHisProject(hisProjectInfo);
				hisprojProductEntriesInfo.setIsAccObj(productCollectionTemp.get(i).isIsAccObj());
				hisprojProductEntriesInfo.setProductType(productCollectionTemp.get(i).getProductType());
				hisProjProductEntriesCollection.add(hisprojProductEntriesInfo);
			}
			hisProjectInfo.getHisProjProductEntries().addCollection(hisProjProductEntriesCollection);
		}
		return hisProjectInfo;
	}

	private HisProjectInfo transFromCurToHis(CurProjectInfo curProjectInfo) {
		HisProjectInfo hisProjectInfo = new HisProjectInfo();
		// ������Ϣ
		hisProjectInfo.setNumber(curProjectInfo.getNumber());
		hisProjectInfo.setName(curProjectInfo.getName());
		hisProjectInfo.setLongNumber(curProjectInfo.getLongNumber());
		hisProjectInfo.setLandDeveloper(curProjectInfo.getLandDeveloper());
		hisProjectInfo.setSortNo(curProjectInfo.getSortNo());
		hisProjectInfo.setStartDate(curProjectInfo.getStartDate());
		hisProjectInfo.setFullOrgUnit(curProjectInfo.getFullOrgUnit());
		// �����
		hisProjectInfo.setParent(null);// ///////////////????????????????????????????????
		// ��ǰ������Ŀ
		hisProjectInfo.setCurProject(curProjectInfo);
		// �ɱ���¼
		HisProjCostEntriesCollection hisProjCostEntriesCollection = new HisProjCostEntriesCollection();
		HisProjCostEntriesInfo hisProjCostEntriesInfo;
		if (curProjectInfo.getCurProjCostEntries().size() > 0) {
			for (int i = 0; i < curProjectInfo.getCurProjCostEntries().size(); i++) {
				hisProjCostEntriesInfo = new HisProjCostEntriesInfo();
				hisProjCostEntriesInfo.setApportionType(curProjectInfo.getCurProjCostEntries().get(i).getApportionType());
				hisProjCostEntriesInfo.setValue(curProjectInfo.getCurProjCostEntries().get(i).getValue());
				hisProjCostEntriesInfo.setHisProject(hisProjectInfo);
				hisProjCostEntriesCollection.add(hisProjCostEntriesInfo);
			}
			hisProjectInfo.getHisProjCostEntries().addCollection(hisProjCostEntriesCollection);
		}
		// ��Ʒ��¼
		HisProjProductEntriesCollection hisProjProductEntriesCollection = new HisProjProductEntriesCollection();
		HisProjProductEntriesInfo hisprojProductEntriesInfo;
		HisProjProEntrApporDataCollection hisProjProEntrApporDataCollection;
		HisProjProEntrApporDataInfo hisProjProEntrApporDataInfo;
		if (curProjectInfo.getCurProjProductEntries().size() > 0) {
			for (int i = 0; i < curProjectInfo.getCurProjProductEntries().size(); i++) {
				hisprojProductEntriesInfo = new HisProjProductEntriesInfo();
				hisProjProEntrApporDataCollection = new HisProjProEntrApporDataCollection();
				for (int j = 0; j < curProjectInfo.getCurProjProductEntries().get(i).getCurProjProEntrApporData().size(); j++) {
					hisProjProEntrApporDataInfo = new HisProjProEntrApporDataInfo();
					hisProjProEntrApporDataInfo.setApportionType(curProjectInfo.getCurProjProductEntries().get(i).getCurProjProEntrApporData().get(j).getApportionType());
					hisProjProEntrApporDataInfo.setValue(curProjectInfo.getCurProjProductEntries().get(i).getCurProjProEntrApporData().get(j).getValue());
					hisProjProEntrApporDataCollection.add(hisProjProEntrApporDataInfo);
				}
				hisprojProductEntriesInfo.getHisProjProEntrApporData().addCollection(hisProjProEntrApporDataCollection);
				hisprojProductEntriesInfo.setHisProject(hisProjectInfo);
				hisprojProductEntriesInfo.setIsAccObj(curProjectInfo.getCurProjProductEntries().get(i).isIsAccObj());
				hisprojProductEntriesInfo.setProductType(curProjectInfo.getCurProjProductEntries().get(i).getProductType());
				hisProjProductEntriesCollection.add(hisprojProductEntriesInfo);
			}
			hisProjectInfo.getHisProjProductEntries().addCollection(hisProjProductEntriesCollection);
		}
		return hisProjectInfo;
	}

	/**
	 * ˢ�°汾�л���ť
	 * 
	 */
	private void refreshButton() {
		if (hisProjectCollection != null && hisProjectCollection.size() <= 2) {
			this.btnFirst.setEnabled(false);
			this.btnPrev.setEnabled(false);
			this.btnNext.setEnabled(false);
			this.btnLast.setEnabled(false);
		} else {
			if (curVersion == 1) {
				this.btnFirst.setEnabled(false);
				this.btnPrev.setEnabled(false);
				this.btnNext.setEnabled(true);
				this.btnLast.setEnabled(true);
			} else if (curVersion == this.hisProjectCollection.size() - 1) {
				this.btnFirst.setEnabled(true);
				this.btnPrev.setEnabled(true);
				this.btnNext.setEnabled(false);
				this.btnLast.setEnabled(false);
			} else {
				this.btnFirst.setEnabled(true);
				this.btnPrev.setEnabled(true);
				this.btnNext.setEnabled(true);
				this.btnLast.setEnabled(true);
			}
		}
	}
}