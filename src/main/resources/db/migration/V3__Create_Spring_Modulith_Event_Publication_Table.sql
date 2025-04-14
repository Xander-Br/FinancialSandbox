-- src/main/resources/db/migration/V3__Create_Spring_Modulith_Event_Publication_Table.sql

CREATE TABLE event_publication (
                                   id UUID NOT NULL,
                                   completion_date TIMESTAMP WITH TIME ZONE, -- Use TIMESTAMPTZ
                                   event_type VARCHAR(512) NOT NULL,
                                   listener_id VARCHAR(512) NOT NULL,
                                   publication_date TIMESTAMP WITH TIME ZONE NOT NULL, -- Use TIMESTAMPTZ
                                   serialized_event VARCHAR(4000) NOT NULL, -- Or TEXT if events can be larger
                                   PRIMARY KEY (id)
);

CREATE INDEX event_publication_listener_id_idx ON event_publication (listener_id);

CREATE INDEX event_publication_completion_date_idx ON event_publication (completion_date);