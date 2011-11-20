package fr.irit.smac.may.lib.webservices;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class WebServiceEndpointImpl<I> extends WebServiceEndpoint<I> {

	private final String url;
	@SuppressWarnings("rawtypes")
	private final Class interfaze;

	public WebServiceEndpointImpl(String url,
			@SuppressWarnings("rawtypes") Class interfaze) {
		this.url = url;
		this.interfaze = interfaze;
	}

	@Override
	protected void start() {
		super.start();
		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
		svrFactory.setServiceClass(interfaze);
		svrFactory.setAddress(url);
		svrFactory.setServiceBean(service());
		// svrFactory.getInInterceptors().add(new LoggingInInterceptor());
		// svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
		svrFactory.create();
	}
}
