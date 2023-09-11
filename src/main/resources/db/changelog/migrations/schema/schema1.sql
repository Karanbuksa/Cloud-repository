-- changeset hw:1
CREATE TABLE public.CLOUD_USERS
(
    id       SERIAL PRIMARY KEY,
    name     text,
    login    text,
    password text
);
-- rollback drop table public.CUSTOMERS

-- changeset hw:2
CREATE INDEX CLOUD_USERS_ID
    ON public.CLOUD_USERS (id);
-- rollback drop index id on public.CUSTOMERS