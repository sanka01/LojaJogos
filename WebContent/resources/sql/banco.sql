create database lojajogos
    with owner topicos;

create table if not exists usuario
(
    id    serial   not null
        constraint usuario_pk
            primary key,
    nome  char(60) not null,
    login char(90) not null,
    senha char(30) not null,
    tipo  integer default 1
);

alter table usuario
    owner to topicos;

create unique index if not exists usuario_id_uindex
    on usuario (id);

create table if not exists jogo
(
    id            serial           not null
        constraint jogo_pk
            primary key,
    nome          char(60)         not null,
    descricao     char(500),
    tipo          char(30),
    valor         double precision not null,
    desconto      double precision,
    desenvolvedor char(60),
    genero        integer,
    idioma        integer
);

alter table jogo
    owner to topicos;

create unique index if not exists jogo_id_uindex
    on jogo (id);

create unique index if not exists jogo_nome_uindex
    on jogo (nome);

create table if not exists biblioteca_jogo
(
    jogo      integer
        constraint biblioteca_jogo_jogo_id_fk
            references jogo,
    perfil    integer
        constraint biblioteca_jogo_usuario_id_fk
            references usuario,
    instalado boolean,
    id        serial not null
        constraint biblioteca_jogo_pk
            primary key
);

alter table biblioteca_jogo
    owner to topicos;

create unique index if not exists biblioteca_jogo_id_uindex
    on biblioteca_jogo (id);

create table if not exists pedido
(
    valor_total double precision,
    usuario     integer
        constraint pedido_usuario_id_fk
            references usuario,
    data        date,
    status      integer,
    id          serial not null
        constraint pedido_pk
            primary key
);

alter table pedido
    owner to topicos;

create unique index if not exists pedido_id_uindex
    on pedido (id);

create table if not exists item
(
    id       serial not null
        constraint item_pk
            primary key,
    jogo     integer
        constraint item_jogo_id_fk
            references jogo,
    valor    double precision,
    carrinho integer
);

alter table item
    owner to topicos;

create unique index if not exists item_id_uindex
    on item (id);

