package fr.irit.smac.may.lib.components.distribution.ivy;

import java.util.ArrayList;
import java.util.List;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

public class IvyReceiveImpl extends IvyReceive {

	private IvyConnectionConfig connectionConfig;
	private Ivy bus = null;
	private boolean connected = false;

	private IvyMessageListener listener;

	@Override
	protected Pull<IvyConnectionStatus> connectionStatus() {
		return new Pull<IvyConnectionStatus>() {

			public IvyConnectionStatus pull() {
				IvyConnectionStatus status = new IvyConnectionStatus(
						IvyReceiveImpl.this.connectionConfig
								.getBroadCastAdress(),
						IvyReceiveImpl.this.connectionConfig.getPort(),
						IvyReceiveImpl.this.connectionConfig.getActorName(),
						IvyReceiveImpl.this.connectionConfig
								.getHelloWorldMessage(),
						IvyReceiveImpl.this.connected);
				return status;
			}
		};
	}

	@Override
	protected Push<String> bindMsg() {
		return new Push<String>() {

			public void push(String thing) {
				IvyReceiveImpl.this.listener = new IvyMessageListener() {

					public void receive(IvyClient client, String[] args) {
						List<String> list = new ArrayList<String>(args.length);
						for (int i = 0; i < args.length; i++) {
							list.add(args[i]);
						}
						IvyReceiveImpl.this.receive().push(list);

					}
				};

				try {
					IvyReceiveImpl.this.bus.bindMsg(thing, listener);
				} catch (IvyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	protected Push<IvyConnectionConfig> connect() {
		return new Push<IvyConnectionConfig>() {

			public void push(IvyConnectionConfig config) {
				if (IvyReceiveImpl.this.connected) {
					IvyReceiveImpl.this.bus.stop();
					IvyReceiveImpl.this.bus = null;
					IvyReceiveImpl.this.connected = false;
				}

				IvyReceiveImpl.this.bus = new Ivy(config.getActorName(),
						config.getHelloWorldMessage(), null);
				try {
					IvyReceiveImpl.this.connectionConfig = config;

					String domainBus = config.getBroadCastAdress() + ":"
							+ config.getPort();

					IvyReceiveImpl.this.bus.start(domainBus);

					// System.out.println("connected "+domainBus );
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					IvyReceiveImpl.this.connected = true;
				} catch (IvyException ie) {
					System.out.println("Error : " + ie.getMessage());
				}

			}

		};
	}

	@Override
	protected Do disconnect() {
		return new Do() {

			public void doIt() {
				IvyReceiveImpl.this.bus.stop();
				IvyReceiveImpl.this.bus = null;
				IvyReceiveImpl.this.connected = false;
			}
		};
	}

}
