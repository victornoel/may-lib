package fr.irit.smac.may.lib.webservices;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import fr.irit.smac.may.lib.interfaces.RemoteCall;

/**
 * Implements the WebServiceClient using CXF
 * 
 * @author victor
 *
 * @param <I>
 */
public class WebServiceClientImpl<I> extends WebServiceClient<I> {

	@SuppressWarnings("rawtypes")
	private final Class interfaze;
	private final String name;

	@Override
	protected void start() {
		super.start();
	}

	/**
	 * 
	 * @param interfaze the class of the interface I
	 * @param name the name of the service (will be appended to the url when calling)
	 * 
	 * TODO this last point must be improved
	 */
	public WebServiceClientImpl(@SuppressWarnings("rawtypes") Class interfaze,
			String name) {
		this.interfaze = interfaze;
		this.name = name;
	}

	@Override
	protected RemoteCall<I, String> make_service() {
		return new RemoteCall<I, String>() {
			public I call(String to) {
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				// factory.getInInterceptors().add(new LoggingInInterceptor());
				// factory.getOutInterceptors().add(new
				// LoggingOutInterceptor());
				factory.setAddress(to + "/" + name);
				@SuppressWarnings("unchecked")
				I client = factory.create((Class<I>) interfaze);
				return client;
			}
		};
	}
}
