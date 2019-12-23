CREATE TABLE IF NOT EXISTS audit(
    audit_id  INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    event_uuid VARCHAR(36) NOT NULL,
    event_name VARCHAR(36) NOT NULL,
    PRIMARY KEY (audit_id)
);

CREATE TABLE IF NOT EXISTS audit_outbox_events (
  stored_event_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  event_id VARCHAR(36) NOT NULL,
  event_aggregate_id VARCHAR(128) NOT NULL,
  event_timestamp BIGINT NOT NULL,
  event_type VARCHAR(255) NOT NULL,
  event_payload TEXT NOT NULL,
  PRIMARY KEY (stored_event_id)
);

-- TODO Create index