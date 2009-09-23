/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

import imaple.ex.CoreyIsAFaggotException;
import imaple.ex.ICantCompileException;
import imaple.ex.YouAreTooFuckedException;
import imaple.handlers.IVHandler;
import imaple.handlers.LoginStatusHandler;
import imaple.handlers.PingHandler;
import imaple.network.input.ByteArrayByteStream;
import imaple.network.input.GenericSeekableLittleEndianAccessor;
import imaple.network.input.SeekableLittleEndianAccessor;
import java.net.InetSocketAddress;
import java.util.HashMap;
import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;

/**
 *
 * @author David
 */
public class NIOHandler extends IoHandlerAdapter {
	private static final HashMap<RecvOps, JPacketHandler> handlers =
			new HashMap<RecvOps, JPacketHandler>();
	private IoSession yourMomsSession = null; //Your mom doesnt have a session yet!!

	public NIOHandler() {
		handlers.put(RecvOps.PING, new PingHandler());
                handlers.put(RecvOps.LOGIN_STATUS, new LoginStatusHandler());
                handlers.put(RecvOps.IV, new IVHandler());
	}

	public IoSession connectTo(InetSocketAddress addr)
			throws YouAreTooFuckedException {
		SocketConnector con = new SocketConnector();
		SocketConnectorConfig conf = new SocketConnectorConfig();
		con.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new PacketCryptFactory()));
		conf.setConnectTimeout(1000);
		ConnectFuture cf = con.connect(addr, this, conf);
        cf.join();

        if (!cf.isConnected()) {
            throw new YouAreTooFuckedException("Connection didn't happen -> " + addr.getAddress().getHostAddress() + ":"
                                  + addr.getHostName() + ":" + addr.getPort());
        }
		yourMomsSession = cf.getSession();
		return yourMomsSession;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	public void close() {
		if (yourMomsSession != null) {
			yourMomsSession.close();
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] data = (byte[]) message;
		SeekableLittleEndianAccessor slea = new GenericSeekableLittleEndianAccessor(new ByteArrayByteStream(data));
		int packetID = slea.readShort();
		JPacketHandler handler = handlers.get(RecvOps.getFor(packetID));
		if (handler != null) {
			handler.handle(slea);
		} else {
                    System.out.println("There is no handler specified for the packet header: "+RecvOps.getFor(packetID));
                }

                System.out.println("Recieved Packet: "+data);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		Packet p = (Packet) message;
		if (p.getPenis() != null) {
			p.runPenis();
		}
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.err.println("Session closed.");
	}

	public void sendPacket(byte[] data)
			throws CoreyIsAFaggotException, ICantCompileException {
		sendPacket(Packet.wrap(data));
                System.out.println("Sent Packet: "+data);
	}

	public void sendPacket(Packet packet)
			throws CoreyIsAFaggotException, ICantCompileException {
		if (yourMomsSession == null) {
			throw new CoreyIsAFaggotException("Corey is a faggot and your mom's session does not exist..");
		}
		if (packet == null) {
			throw new ICantCompileException("Null Packet, Check again faggot");
		}
		yourMomsSession.write(packet);
	}

	public boolean isConnected() {
		if (yourMomsSession == null) {
			return false;
		}
		return yourMomsSession.isConnected();
	}
}