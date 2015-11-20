CREATE	keyspace IF NOT EXISTS pimaskapp with replication = { 'class': 'SimpleStrategy', 'replication_factor': 3};

CREATE TABLE IF NOT EXISTS pimaskapp.connected_devices (
	device_id UUID PRIMARY KEY,
	device_name text,
	device_ip text,
	notification boolean
);

CREATE TABLE IF NOT EXISTS pimaskapp.users (
	userid text PRIMARY KEY,
	first_name text,
	last_name text,
	notification boolean,
);