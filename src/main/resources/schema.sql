create table if not exists public.files
(
    id         bigserial             not null primary key,
    maxDepth       int not null,
    height int                  not null,
    width int not null
    );