-- src/main/resources/db/migration/V2__Create_Hypertable.sql


CREATE EXTENSION IF NOT EXISTS timescaledb;

SELECT create_hypertable('stock_ticks', by_range('timestamp'));
