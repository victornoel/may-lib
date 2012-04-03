package fr.irit.smac.may.lib.components.meta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;

public class BufferImpl<I> extends Buffer<I> {
	
	@SuppressWarnings("rawtypes")
	private Class clazz;
	
	private final Queue<Runnable> q = new LinkedBlockingQueue<Runnable>();
	
	/**
	 * 
	 * If I is actually MyPortInterface, then this class should be used
	 * with new BufferImpl(MyPortInterface.class)
	 * Just omit type parameters of the port interface
	 * 
	 * @param clazz is the class of the concrete I
	 */
	public BufferImpl(@SuppressWarnings("rawtypes") Class clazz) {
		this.clazz = clazz;
	}
	
	@Override
	protected Do make_release() {
		return new Do() {
			public void doIt() {
				synchronized (q) {
					for(Runnable r: q) {
						r.run();
					}
					q.clear();
				}
			}
		};
	}

	@Override
	@SuppressWarnings("unchecked")
	protected I make_port() {
		InvocationHandler handler = new InvocationHandler() {
			public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
				q.add(new Runnable() {
					public void run() {
						try {
							method.invoke(realPort(), args);
						} catch (IllegalAccessException e) {
							throw new RuntimeException("should not happen",e);
						} catch (IllegalArgumentException e) {
							throw new RuntimeException("should not happen",e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException("should not happen",e);
						}
					}
				});
				return null;
			}
		};
//		return (Actuators) Proxy.newProxyInstance(
//				realActuators().getClass().getClassLoader(),
//                new Class[] { realActuators().getClass().getInterfaces()[0] },
//                handler);
		return (I) Proxy.newProxyInstance(
				clazz.getClassLoader(),
				new Class[] { clazz },
				handler);
	}
	
	public static void main(String[] args) {
		Component<Push<String>> test = new BufferImpl<Push<String>>(Push.class).newComponent(new Buffer.Bridge<Push<String>>() {
			public Push<String> realPort() {
				return new Push<String>() {
					public void push(String thing) {
						System.out.println(thing);
					}
				};
			}
		});
		
		test.start();
		
		System.out.println("before calls");
		
		test.port().push("a");
		test.port().push("b");
		test.port().push("a");
		test.port().push("a");
		
		System.out.println("after calls, before release");
		
		test.release().doIt();
		
		System.out.println("after release");
	}

}
