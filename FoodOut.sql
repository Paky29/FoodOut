drop database if exists foodout;
create database foodout;
use foodout;

create table Ristorante(
	codiceRistorante int auto_increment primary key,
    nome char(30) not null,
    provincia char(30) not null,
    citta char(30) not null,
    via varchar(50) not null,
    civico int unsigned,
    info varchar(100) not null,
    spesaMinima dec(4,2) unsigned not null,
    tassoConsegne dec(4,2) unsigned not null
);

create table Tipologia(
	nome char(20) primary key
);

create table AppartenenzaRT(
	codRis_fk int not null,
    nomeTip_fk char(20) not null,
    primary key(codRis_fk,nomeTip_fk),
    foreign key (codRis_fk) references Ristorante(codiceRistorante)
    on update cascade
    on delete cascade,
    foreign key (nomeTip_fk) references Tipologia(nome)
    on update cascade
    on delete cascade
);

create table Giorno(
	nome char(10) primary key
);

create table Disponibilita(
	codRis_fk int not null,
    nomeG_fk char(10) not null,
    oraApertura time not null,
    oraChiusura time not null,
    primary key (codRis_fk, nomeG_fk),
    foreign key (codRis_fk) references Ristorante(codiceRistorante)
    on update cascade
    on delete cascade,
    foreign key (nomeG_fk) references Giorno(nome)
    on update cascade
    on delete restrict,
    check (oraChiusura>oraApertura)
);

create table Categoria(
	nome char(20) primary key
);

create table Prodotto(
	codiceProdotto int auto_increment primary key,
    nome char(30) not null,
    ingredienti varchar(100),
    genere char(20) not null,
    info varchar(50),
    prezzo dec(5,2) unsigned not null,
    sconto int unsigned not null,
    valido boolean not null,
    codRis_fk int not null,
    foreign key (codRis_fk) references Ristorante(codiceRistorante)
    on update cascade
    on delete cascade,
    nomeC_fk char(20),
    foreign key (nomeC_fk) references Categoria(nome)
    on update cascade
    on delete set null
);

create table Menu(
	codiceMenu int auto_increment primary key,
    nome char(20) not null,
    prezzo dec(5,2) unsigned not null,
    sconto int unsigned not null,
    valido boolean not null
);

create table AppartenenzaPM(
	codMenu_fk int not null,
    codProd_fk int not null,
    primary key(codMenu_fk, codProd_fk),
    foreign key (codMenu_fk) references Menu(codiceMenu)
    on update cascade
    on delete cascade,
    foreign key (codProd_fk) references Prodotto(codiceProdotto)
    on update cascade
    on delete restrict
);

create table Rider(
	codiceRider int auto_increment primary key,
    email char(50) not null unique,
    pw char(100) not null,
    veicolo char(20) not null, 
    citta char(20) not null
);

create table LavoroRider(
	codRider_fk int not null,
    nomeG_fk char(10) not null,
    oraInizio time not null,
    oraFine time not null,
    primary key (codRider_fk, nomeG_fk),
    foreign key (codRider_fk) references Rider(codiceRider)
    on update cascade
    on delete cascade,
    foreign key (nomeG_fk) references Giorno(nome)
    on update cascade
    on delete restrict,
    check(oraFine>oraInizio)
);

create table Utente(
	codiceUtente int auto_increment primary key,
    nome char(30) not null,
    cognome char(40) not null,
    email varchar(50) not null unique,
    pw char(100) not null,
    saldo dec(7,2) unsigned not null default 0,
    provincia char(30) not null,
    citta char(30) not null,
    via varchar(50) not null,
    civico int unsigned,
    interesse varchar(100) 
);

create table Ordine(
	codiceOrdine int auto_increment primary key,
    dataOrdine date not null,
    totale dec(6,4) unsigned not null,
    nota varchar(50),
    oraPartenza time,
    oraArrivo time,
    metodoPagamento char(10) not null,
    giudizio char(30),
    voto int check(voto>=1 AND voto<=5),
    codRider_fk int,
    codUtente_fk int not null,
    foreign key (codRider_fk) references Rider(codiceRider)
    on update cascade
    on delete set null,
    foreign key (codUtente_fk) references Utente(codiceUtente)
    on update cascade
    on delete cascade,
    check(oraArrivo>oraPartenza)
);

create table Preferenza(
	codUtente_fk int not null,
    codRis_fk int not null,
    primary key(codUtente_fk, codRis_fk),
    foreign key (codUtente_fk) references Utente(codiceUtente)
    on update cascade
    on delete cascade,
    foreign key (codRis_fk) references Ristorante(codiceRistorante)
    on update cascade
    on delete cascade
);

create table ComposizioneOP(
	codOrd_fk int not null,
    codProd_fk int,
    quantita int unsigned not null,
    primary key(codOrd_fk, codProd_fk),
    foreign key (codOrd_fk) references Ordine(codiceOrdine)
    on update cascade
    on delete cascade,
    foreign key (codProd_fk) references Prodotto(codiceProdotto)
    on update cascade
    on delete restrict
);

create table ComposizioneOM(
	codOrd_fk int not null,
    codMenu_fk int,
    quantita int unsigned not null,
	primary key(codOrd_fk, codMenu_fk),
    foreign key (codOrd_fk) references Ordine(codiceOrdine)
    on update cascade
    on delete cascade,
    foreign key (codMenu_fk) references Menu(codiceMenu)
    on update cascade
    on delete restrict
);





