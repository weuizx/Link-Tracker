CREATE TABLE IF NOT EXISTS public.client (
    id bigserial primary key,
    tg_chat_id bigint NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.link (
    id bigserial primary key,
    url varchar(255) NOT NULL UNIQUE,
    last_check_datetime timestamp with time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS public.link_to_client (
    id bigserial primary key,
    client_id bigint NOT NULL references public.client(id),
    link_id bigint NOT NULL references public.link(id)
);

ALTER TABLE IF EXISTS ONLY public.link_to_client
    ADD CONSTRAINT link_to_client_unique_key UNIQUE (client_id, link_id);
