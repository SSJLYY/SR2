package com.bp.webservices.test.webservices;

import com.bp.webservices.data.UserData;
import com.bp.webservices.facades.impl.DefaultSampleFacades;
import com.bp.webservices.services.SampleUserServices;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.licence.Licence;
import de.hybris.platform.licence.internal.ValidationResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;


@UnitTest
//@RunWith(PowerMockRunner.class) //mock static
//@PrepareForTest({Config.class})  //mock static
//@PowerMockIgnore( {"javax.management.*"})//mock static
public class TestUnitForWebservices
{
	private ApplicationContext applicationContext;

	@Mock
	private SampleUserServices xxxxx;

	@Mock
	private Licence licence;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		UserModel userModel = new UserModel();
		userModel.setName("frist last");
		userModel.setAddresses(new ArrayList<>());

		//PowerMockito.mockStatic(Config.class);
		//PowerMockito.when(Config.getParameter("aa.bb.cc")).thenReturn("123444");
		Mockito.when(xxxxx.getUserById("user1")).thenReturn(userModel);

		applicationContext = new ClassPathXmlApplicationContext(
				"classpath:alpssalewebservices-test-spring.xml",
				"classpath:converters-spring.xml");
	}

	@Test
	public void testunitx(){
		DefaultSampleFacades sampleFacades = (DefaultSampleFacades)applicationContext.getBean("alpssalewebservicesDefaultSampleFacades");
		sampleFacades.setSampleUserService(xxxxx);
		UserData userData = sampleFacades.getUser("user1");

		System.out.println(userData.getLastName());
	}
}
