package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

public class IvySendImpl extends IvySend {

	private IvyConnectionConfig connectionConfig;
	private Ivy bus = null;
	private boolean connected = false;

	@Override
	protected Push<String> send() {
		return new Push<String>() {

			public void push(String ivyMessage) {
				if (IvySendImpl.this.connected) {
					try {
						// System.out.println(ivyMessage);
						IvySendImpl.this.bus.sendMsg(ivyMessage);

						// System.out.println("sent"+i );
					} catch (IvyException e) {
						e.printStackTrace();
					}
				}

			}

		};
	}

	@Override
	protected Pull<IvyConnectionStatus> connectionStatus() {
		return new Pull<IvyConnectionStatus>() {

			public IvyConnectionStatus pull() {
				IvyConnectionStatus status = new IvyConnectionStatus(
						IvySendImpl.this.connectionConfig.getBroadCastAdress(),
						IvySendImpl.this.connectionConfig.getPort(),
						IvySendImpl.this.connectionConfig.getActorName(),
						IvySendImpl.this.connectionConfig
								.getHelloWorldMessage(),
						IvySendImpl.this.connected);
				return status;
			}
		};
	}

	@Override
	protected Push<IvyConnectionConfig> connect() {
		return new Push<IvyConnectionConfig>() {

			public void push(IvyConnectionConfig config) {
				if (IvySendImpl.this.connected) {
					IvySendImpl.this.bus.stop();
					IvySendImpl.this.bus = null;
					IvySendImpl.this.connected = false;
				}

				IvySendImpl.this.bus = new Ivy(config.getActorName(),
						config.getHelloWorldMessage(), null);
				try {
					IvySendImpl.this.connectionConfig = config;

					String domainBus = config.getBroadCastAdress() + ":"
							+ config.getPort();

					IvySendImpl.this.bus.start(domainBus);

					// System.out.println("connected "+domainBus );
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					IvySendImpl.this.connected = true;
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
				IvySendImpl.this.bus.stop();
				IvySendImpl.this.bus = null;
				IvySendImpl.this.connected = false;
			}
		};
	}

}
