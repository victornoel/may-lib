package fr.irit.smac.may.lib.webservices;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import fr.irit.smac.may.lib.interfaces.RemoteCall;

public class WebServiceClientImpl<I> extends WebServiceClient<I> {

	@SuppressWarnings("rawtypes")
	private final Class interfaze;
	private final String name;

	@Override
	protected void start() {
		super.start();
	}

	public WebServiceClientImpl(@SuppressWarnings("rawtypes") Class interfaze,
			String name) {
		this.interfaze = interfaze;
		this.name = name;
	}

	@Override
	protected RemoteCall<I, String> service() {
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
