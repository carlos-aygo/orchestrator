CREATE TABLE flows
(
    id         BIGSERIAL PRIMARY KEY  NOT NULL,
    name       character varying(255) NOT NULL,
    flow       character varying(255) NOT NULL,
    conditions JSONB                  NOT NULL,
    metadata   JSONB                  NOT NULL
);

CREATE TABLE send_emails
(
    id           BIGSERIAL PRIMARY KEY  NOT NULL,
    reference_id BIGINT                 NOT NULL,
    from_mail    character varying(255) NOT NULL,
    to_mail      character varying(255) NOT NULL,
    subject      character varying(255) NOT NULL,
    content      character varying(255) NOT NULL,
    scheduled_at timestamp(0) without time zone NOT NULL
);