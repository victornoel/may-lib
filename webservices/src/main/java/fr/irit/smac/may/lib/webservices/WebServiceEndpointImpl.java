package fr.irit.smac.may.lib.webservices;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * Implements the WebServiceEndpoint using CXF
 * 
 * @author victor
 *
 * @param <I>
 */
public class WebServiceEndpointImpl<I> extends WebServiceEndpoint<I> {

	private final String url;
	@SuppressWarnings("rawtypes")
	private final Class interfaze;

	/**
	 * 
	 * @param url the url to publish the service to (http://address:port/name)
	 * @param interfaze the class of the interface I
	 */
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
