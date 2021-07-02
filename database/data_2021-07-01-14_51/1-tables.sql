use bd_trocencheres
go

create table dbo.CATEGORIES
(
    no_categorie int identity
        constraint PK_CATEGORIES
            primary key,
    libelle      varchar(30) not null
)
go

create table dbo.UTILISATEURS
(
    no_utilisateur int identity
        constraint PK_UTILISATEUR
            primary key,
    pseudo         varchar(30) not null UNIQUE,
    nom            varchar(50) not null,
    prenom         varchar(50) not null,
    email          varchar(100) not null UNIQUE,
    telephone      varchar(15),
    rue            varchar(100) not null,
    code_postal    varchar(10) not null,
    ville          varchar(60) not null,
    mot_de_passe   varchar(30) not null,
    credit         int         not null,
    administrateur bit         not null
)
go

create table dbo.ARTICLES_VENDUS
(
    no_article          int identity
        constraint PK_ARTICLES_VENDUS
            primary key,
    nom_article         varchar(30)  not null,
    description         varchar(300) not null,
    date_debut_encheres date         not null,
    date_fin_encheres   date         not null,
    prix_initial        int,
    prix_vente          int,
    no_utilisateur      int          not null
        constraint FK_VENTES_UTILISATEURS
            references dbo.UTILISATEURS,
    no_categorie        int          not null
        constraint FK_ARTICLES_VENDUS_CATEGORIES
            references dbo.CATEGORIES
)
go

create table dbo.ENCHERES
(
    no_utilisateur  int      not null
        constraint FK_ENCHERES_UTILISATEURS
            references dbo.UTILISATEURS,
    no_article      int      not null
        constraint FK_ENCHERES_ARTICLES_VENDUS
            references dbo.ARTICLES_VENDUS,
    date_enchere    datetime not null,
    montant_enchere int      not null,
    constraint PK_ENCHERES
        primary key (no_utilisateur, no_article)
)
go

create table dbo.RETRAITS
(
    no_article  int         not null
        constraint PK_RETRAITS
            primary key
        constraint FK_RETRAITS_ARTICLES_VENDUS
            references dbo.ARTICLES_VENDUS,
    rue         varchar(30) not null,
    code_postal varchar(15) not null,
    ville       varchar(30) not null
)
go


