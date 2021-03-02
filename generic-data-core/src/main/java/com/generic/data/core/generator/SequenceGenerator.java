package com.generic.data.core.generator;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author praveen.kumar <br>
 *         {@link SequenceGenerator} is utility class to generate unique id in
 *         distributed environment. <br>
 *         This class should be used as a Singleton.</br>
 *         <br>
 *         Make sure that you create and reuse a Single instance of
 *         SequenceGenerator per node in your distributed system cluster.</br>
 *         <br>
 *         SequenceGenerator uses the system’s MAC address to create a unique
 *         identifier for the Node. You can also supply a NodeID to the sequence
 *         generator. That will guarantee uniqueness.</br>
 *         <ul>
 *         ALGORITHM:-
 *         </ul>
 *         Let’s now understand how it works. Let’s say it’s June 9, 2020
 *         10:00:00 AM GMT. The epoch timeStamp for this particular time is
 *         1528538400000. First of all, we adjust our timeStamp with respect to
 *         the custom epoch-
 *         <li>currentTimestamp = 1528538400000 - 1420070400000 // 108468000000
 *         (Adjust for custom epoch)</li> Now, the first 41 bits of the ID
 *         (after the signed bit) will be filled with the epoch timeStamp. Let’s
 *         do that using a left-shift.
 *         <li>id = currentTimestamp << (10 + 12)</li> Next, we take the
 *         configured node ID and fill the next 10 bits with the node ID. Let’s
 *         say that the nodeId is 786.
 *         <li>id |= nodeId << 12</li> Finally, we fill the last 12 bits with
 *         the local counter. Considering the counter’s next value is 3450, i.e.
 *         sequence = 3450, the final ID is obtained like so-
 *         <li>id |= sequence // 454947766275219456</li>
 */
public class SequenceGenerator {

	
	private static final Logger logger = LogManager.getLogger(SequenceGenerator.class);
	private static final int NODE_ID_BITS = 10;
	private static final int SEQUENCE_BITS = 12;

	private static final int MAX_NODE_ID = (int) (Math.pow(2, NODE_ID_BITS) - 1);
	private static final int MAX_SEQUENCE = (int) (Math.pow(2, SEQUENCE_BITS) - 1);

	// Custom Epoch (January 1, 2021 Midnight UTC = 2021-01-01T00:00:00Z)
	private static final long CUSTOM_EPOCH = 1420070400000L;

	private final int nodeId;

	private volatile long lastTimestamp = -1L;
	private volatile long sequence = 0L;

	// Create SequenceGenerator with a nodeId
	public SequenceGenerator(int nodeId) {
		if (nodeId < 0 || nodeId > MAX_NODE_ID) {
			throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, MAX_NODE_ID));
		}
		logger.info("NodeId[{}] has been generated successfuly.", nodeId);
		this.nodeId = nodeId;
		
	}

	// Let SequenceGenerator generate a nodeId
	public SequenceGenerator() {
		this.nodeId = createNodeId();
	}

	public synchronized long nextId() {
		long currentTimestamp = timestamp();

		if (currentTimestamp < lastTimestamp) {
			throw new IllegalStateException("Invalid System Clock!");
		}

		if (currentTimestamp == lastTimestamp) {
			sequence = (sequence + 1) & MAX_SEQUENCE;
			if (sequence == 0) {
				// Sequence Exhausted, wait till next millisecond.
				currentTimestamp = waitNextMillis(currentTimestamp);
			}
		} else {
			// reset sequence to start with zero for the next millisecond
			sequence = 0;
		}

		lastTimestamp = currentTimestamp;

		long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS);
		id |= (nodeId << SEQUENCE_BITS);
		id |= sequence;
		return id;
	}

	// Get current timeStamp in milliseconds, adjust for the custom epoch.
	private static long timestamp() {
		return Instant.now().toEpochMilli() - CUSTOM_EPOCH;
	}

	// Block and wait till next millisecond
	private long waitNextMillis(long currentTimestamp) {
		while (currentTimestamp == lastTimestamp) {
			currentTimestamp = timestamp();
		}
		return currentTimestamp;
	}

	private int createNodeId() {
		int genratedNodeId;
		try {
			StringBuilder sb = new StringBuilder();
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				byte[] mac = networkInterface.getHardwareAddress();
				if (mac != null) {
					for (int i = 0; i < mac.length; i++) {
						sb.append(String.format("%02X", mac[i]));
					}
				}
			}
			genratedNodeId = sb.toString().hashCode();
		} catch (Exception ex) {
			genratedNodeId = (new SecureRandom().nextInt());
		}
		genratedNodeId = genratedNodeId & MAX_NODE_ID;
		return genratedNodeId;
	}

}
