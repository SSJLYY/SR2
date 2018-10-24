package com.bp.alps.facades.service.impl;

import com.bp.alps.core.enums.BuyerPoint;
import com.bp.alps.facades.data.category.CategoryListData;
import com.bp.alps.facades.data.abstractOrder.EntryData;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.facades.populators.EnumPupulator;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.type.TypeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;


@UnitTest
public class DefaultTestOpportunity
{
	@Mock
	private TypeService typeService;

	@Mock
	private EnumerationValueModel enumerationValueModel;

	private ApplicationContext applicationContext;

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);

		applicationContext = new ClassPathXmlApplicationContext(
				"classpath:alpssalefacades-test-spring.xml",
				"classpath:converters-spring.xml");
	}

	@Test
	public void testcase() throws Exception
	{
		List<String> stringList = new ArrayList<>();
		stringList.add("brand");
		stringList.add("power");
		stringList.add("xxx");
		List<BuyerPoint> buyerPoints = stringList.stream().map(s -> BuyerPoint.valueOf(s))
				.filter(bp -> bp!=null)
				.collect(Collectors.toList());
	}

	@Test
	public void testcase2() throws Exception
	{
		HybrisEnumValue value = BuyerPoint.valueOf("brand");
		EnumPupulator enumPupulator = new EnumPupulator();

		Mockito.when(typeService.getEnumerationValue(value)).thenReturn(enumerationValueModel);
		setPrivatFieldMock(enumPupulator,"typeService", typeService);

		Mockito.when(enumerationValueModel.getCode()).thenReturn("123");
		Mockito.when(enumerationValueModel.getName()).thenReturn("123123");

		OptionData testReturn = new OptionData();
		enumPupulator.populate(value,testReturn);
		//System.out.println(testReturn.getId());
	}

	protected void setPrivatFieldMock(Object mainObj, String fieldName, Object mockObj){
		try
		{
			Field field = mainObj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(mainObj,mockObj);
		}catch (Exception e){
		}
	}

	@Test
	public void testCase3(){
		CategoryModel model = Mockito.mock(CategoryModel.class);
		Mockito.when(model.getCode()).thenReturn("1234");

		CategoryModel model2 = Mockito.mock(CategoryModel.class);
		List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
		categoryModels.add(model2);
		Mockito.when(model.getSupercategories()).thenReturn(categoryModels);
		Mockito.when(model2.getCode()).thenReturn("123");
		Mockito.when(model2.getSupercategories()).thenReturn(new ArrayList<>());
		String aa = getCategoryHierarchyCode(model,"");
		//System.out.println(aa);
	}

	@Test
	public void testCase4(){

	}

	protected String getCategoryHierarchyCode(final CategoryModel model,final String oldCode){
		String code = model.getCode();
		if(StringUtils.isNotBlank(oldCode)){
			code = code+ "/" + oldCode;
		}
		if(CollectionUtils.isNotEmpty(model.getSupercategories())){
			return getCategoryHierarchyCode(model.getSupercategories().get(0),code);
		}
		return code;
	}

	protected List getObjectValue(final String key, final QuotationData quotaionData){
		try
		{
			Field field = quotaionData.getClass().getDeclaredField(key);
			field.setAccessible(true);
			Object value = field.get(quotaionData);
			field.setAccessible(false);
			if(value instanceof List){
				return (List) value;
			}
		}catch (Exception e){
		}
		return new ArrayList();
	}

	@Test
	public void testCase5(){
		QuotationData quotaionData = new QuotationData();
		EntryData entryData = new EntryData();
		entryData.setCode("123");
		quotaionData.setOptionalProduct(Collections.singletonList(entryData));

		List<EntryData> entryDataList = getObjectValue("optionalProduct", quotaionData);
		System.out.println(entryDataList.size());
	}

	@Test
	public void testCase6(){
		System.out.println(Locale.CHINA);
		System.out.println(Currency.getInstance(Locale.CHINA).getCurrencyCode());
	}
}
