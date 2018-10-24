/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.bp.alps.after.sale.initialdata.setup;

import static com.bp.alps.after.sale.initialdata.constants.AlpsaftersaleinitialdataConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.commerceservices.dataimport.impl.CoreDataImportService;
import de.hybris.platform.commerceservices.dataimport.impl.SampleDataImportService;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.CoreDataImportedEvent;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bp.alps.after.sale.initialdata.constants.AlpsaftersaleinitialdataConstants;
import com.bp.alps.after.sale.initialdata.service.AlpsaftersaleinitialdataService;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import org.springframework.beans.factory.annotation.Required;


@SystemSetup(extension = AlpsaftersaleinitialdataConstants.EXTENSIONNAME)
public class AlpsaftersaleinitialdataSystemSetup extends AbstractSystemSetup
{
	private CoreDataImportService coreDataImportService;
	private SampleDataImportService sampleDataImportService;
	private final AlpsaftersaleinitialdataService alpsaftersaleinitialdataService;

	private static final String IMPORT_CORE_DATA = "importCoreData";
	private static final String IMPORT_SAMPLE_DATA = "importSampleData";
	private static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

	public static final String alpsContentCatalog = "alpsAfterSale";
	public static final String alpsProductCatalog = "alps";
	public static final String storeName = "aftersale";

	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

		params.add(createBooleanSystemSetupParameter(IMPORT_CORE_DATA, "Import Core Data", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
		params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));
		// Add more Parameters here as you require

		return params;
	}

	public AlpsaftersaleinitialdataSystemSetup(final AlpsaftersaleinitialdataService alpsaftersaleinitialdataService)
	{
		this.alpsaftersaleinitialdataService = alpsaftersaleinitialdataService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		alpsaftersaleinitialdataService.createLogo(PLATFORM_LOGO_CODE);
	}

	@SystemSetup(type = SystemSetup.Type.PROJECT, process = SystemSetup.Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		final List<ImportData> importData = new ArrayList<ImportData>();

		final ImportData electronicsImportData = new ImportData();
		electronicsImportData.setProductCatalogName(alpsProductCatalog);
		electronicsImportData.setContentCatalogNames(Arrays.asList(alpsContentCatalog));
		electronicsImportData.setStoreNames(Arrays.asList(storeName));
		importData.add(electronicsImportData);

		getCoreDataImportService().execute(this, context, importData);
		getEventService().publishEvent(new CoreDataImportedEvent(context, importData));

		getSampleDataImportService().execute(this, context, importData);
		getEventService().publishEvent(new SampleDataImportedEvent(context, importData));
	}

	private InputStream getImageStream()
	{
		return AlpsaftersaleinitialdataSystemSetup.class.getResourceAsStream("/alpsaftersaleinitialdata/sap-hybris-platform.png");
	}

	public CoreDataImportService getCoreDataImportService()
	{
		return coreDataImportService;
	}

	@Required
	public void setCoreDataImportService(final CoreDataImportService coreDataImportService)
	{
		this.coreDataImportService = coreDataImportService;
	}

	public SampleDataImportService getSampleDataImportService()
	{
		return sampleDataImportService;
	}

	@Required
	public void setSampleDataImportService(final SampleDataImportService sampleDataImportService)
	{
		this.sampleDataImportService = sampleDataImportService;
	}
}
