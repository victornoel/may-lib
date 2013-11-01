package fr.irit.smac.may.lib.components.distribution.ivy;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;

public class IvyBusImpl extends IvyBus {

	Logger logger = LoggerFactory.getLogger(IvyBusImpl.class);
	
	private final IvyConnectionConfig connectionConfig;
	
	private Ivy bus;

	//private IvyMessageListener listener;

	protected boolean connected;

	public IvyBusImpl(IvyConnectionConfig config) {
		this.connectionConfig = config;
	}
	
	@Override
	protected void start() {
		super.start();


		this.bus = new Ivy(connectionConfig.getActorName(),
				connectionConfig.getHelloWorldMessage(), null);
		try {
			String domainBus = connectionConfig.getBroadCastAdress() + ":"
					+ connectionConfig.getPort();
			this.bus.start(domainBus);
			this.connected = true;
			
			logger.info("connected on {}",domainBus);
		} catch (IvyException ie) {
			logger.error("error on connection",ie);
		}
	}

	@Override
	protected Do make_disconnect() {
		return new Do() {
			public void doIt() {
				IvyBusImpl.this.bus.stop();
				IvyBusImpl.this.bus = null;
				IvyBusImpl.this.connected = false;
			}
		};
	}

	@Override
	protected Push<String> make_send() {
		return new Push<String>() {
			public void push(String ivyMessage) {
				if (IvyBusImpl.this.connected) {
					try {
						logger.debug("sending message \"{}\"", ivyMessage);
						IvyBusImpl.this.bus.sendMsg(ivyMessage);
					} catch (IvyException e) {
						logger.error("error while sending message",e);
					}
				}

			}

		};
	}

	@Override
	protected Bind make_bindMsg() {
		return new Bind() {
			public int bind(String regex, final Push<List<String>> callback) {
				
				IvyMessageListener listener = new IvyMessageListener() {
					public void receive(IvyClient client, final String[] args) {
						logger.debug("receiving message \"{}\"", Arrays.toString(args));
						requires().exec().execute(new Runnable() {
							public void run() {
								logger.debug("calling callback for message \"{}\"", Arrays.toString(args));
								callback.push(Arrays.asList(args));
							}
						});
					}
				};
				
				try {
					logger.debug("binding regexp \"{}\"", regex);
					int bindId = IvyBusImpl.this.bus.bindMsg(regex, listener);
					return bindId;
				} catch (IvyException e) {
					logger.error("error while binding",e);
				}
				// TODO better, use an exception
				return -1;
			}
		};
	}

	@Override
	protected Push<Integer> make_unBindMsg() {
		return new Push<Integer>() {
			public void push(Integer thing) {
				try {
					IvyBusImpl.this.bus.unBindMsg(thing);
				} catch (IvyException e) {
					logger.error("error while unbinding",e);
				}
			}
		};
	}
}
