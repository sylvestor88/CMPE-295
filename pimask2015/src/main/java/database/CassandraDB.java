package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraDB {

	private Session session;
	private Cluster cluster;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CassandraDB.class);

	public Session connect() {

		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("pimaskapp");

		Metadata metadata = cluster.getMetadata();
		LOGGER.info("Connected to cluster: " + metadata.getClusterName()
				+ " with partitioner: " + metadata.getPartitioner());
		
		return session;
	}
}
