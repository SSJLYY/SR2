package com.bp.alps.core.quotation.impl;

import com.bp.alps.core.dao.QuotationDao;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.model.*;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.quotation.QuotationFactory;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.core.service.impl.DefaultAlpssalecoreService;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceHandleOrderStrategy;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QuotationServiceImpl extends DefaultAlpssalecoreService implements QuotationService
{
	@Resource
	private QuotationFactory quotationFactory;

	@Resource
	private ModelService modelService;

	@Resource
	private QuotationDao quotationDao;

	@Resource
	private AlpsCommerceHandleOrderStrategy alpsCommerceHandleOrderStrategy;

	public void setQuotationStatusToConfrim(QuotationInfoModel quotationInfoModel){
		SalesOrderAttributeModel salesOrderAttributeModel = quotationInfoModel.getSalesAttribute();
		if(salesOrderAttributeModel!=null)
		{
			quotationDao.setQuotationStatusToUnconfirmByOpportunity(salesOrderAttributeModel.getOpportunity());
			quotationInfoModel.setStatus(OrderStatus.CONFIRM);
			modelService.save(quotationInfoModel);
		}
	}

	public List<QuotationInfoModel> getQuotationList(final String oppoCode, final String followOppoCode){
		Map<String,Object> paramenter = new HashMap<>();
		if(StringUtils.isNotBlank(oppoCode)){
			paramenter.put(OpportunityModel._TYPECODE+OpportunityModel.CODE,oppoCode);
		}
		if(StringUtils.isNotBlank(followOppoCode)){
			paramenter.put(FollowOpportunityModel._TYPECODE+ FollowOpportunityModel.CODE,followOppoCode);
		}
		return quotationDao.getQuotationListByParameter(paramenter);
	}

	public QuotationInfoModel createQuotaion(){
		return (QuotationInfoModel)alpsCommerceHandleOrderStrategy.initializeOrderByType(QuotationInfoModel.class);
	}

	public AlpsCommerceResult placeQuotation(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter){
		if(alpsCommercePlaceOrderParameter.getOrder()!=null && alpsCommercePlaceOrderParameter.getOrder() instanceof QuotationInfoModel)
		{
			saveSubsidiaryTable((QuotationInfoModel)alpsCommercePlaceOrderParameter.getOrder());
		}
		return alpsCommerceHandleOrderStrategy.placeOrder(alpsCommercePlaceOrderParameter);
	}

	public QuotationInfoModel getQuotationInfoByCode(final String code){
		return quotationDao.getQuotaionInfoByCode(code);
	}

	public QuotationInfoModel getConfirmQuotation(final String oppoCode){
		return quotationDao.getConfirmQuotation(oppoCode);
	}

	public void saveQuoaion(QuotationInfoModel quotation){
		modelService.save(quotation);
		saveSubsidiaryTable(quotation);
	}

	protected void saveSubsidiaryTable(QuotationInfoModel quotation){
		if(quotation.getSalesAttribute()!=null)
		{
			List<Object> changeAttribute = new LinkedList<>();
			changeAttribute.add(quotation.getSalesAttribute());
			if(quotation.getSalesAttribute().getFinanceInfo()!=null)
			{
				changeAttribute.add(quotation.getSalesAttribute().getFinanceInfo());
			}
			if(quotation.getSalesAttribute().getUsedcarInfo()!=null)
			{
				changeAttribute.add(quotation.getSalesAttribute().getUsedcarInfo());
			}
			if(quotation.getSalesAttribute().getLicensePlateInfo()!=null)
			{
				changeAttribute.add(quotation.getSalesAttribute().getLicensePlateInfo());
			}
			modelService.saveAll(changeAttribute);
		}
	}

	public void saveUsedCarInfo(UsedcarInfoModel usedcarInfoModel){
		modelService.save(usedcarInfoModel);
	}

	public void saveFinanceInfo(FinanceInfoModel financeInfoModel){
		modelService.save(financeInfoModel);
	}

	@Override
	public void saveLicensePlateInfo(LicensePlateInfoModel licensePlateInfoModel)
	{
		modelService.save(licensePlateInfoModel);
	}
}
