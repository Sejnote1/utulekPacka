--
-- PostgreSQL database dump
--

\restrict 5MHf9HiSQtzeMCptPRrNczgNQmfhW5SY7Ry1cskUKkgDvpcp2QboeusnIky8w7x

-- Dumped from database version 15.17
-- Dumped by pg_dump version 15.17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.veterinarni_zaznam DROP CONSTRAINT fkrnc2amub4wbukmxypsxcrl211;
ALTER TABLE ONLY public.adopce DROP CONSTRAINT fkp5tfcoaj1elocya8uph5c4qlj;
ALTER TABLE ONLY public.veterinarni_zaznam DROP CONSTRAINT fko8kyk780he28ryj8foj2yklvg;
ALTER TABLE ONLY public.zvire DROP CONSTRAINT fkng1mfugbc1j6jspegk74d203q;
ALTER TABLE ONLY public.adopce_zamitnuti DROP CONSTRAINT fkmwxgyjj3w5u37o9epk3pd7oyw;
ALTER TABLE ONLY public.uzivatel DROP CONSTRAINT fklbbyui19ek6cvxedh2tmipuqq;
ALTER TABLE ONLY public.adopce DROP CONSTRAINT fkfjqkjetyrgjmp7fdhqrefrrj2;
ALTER TABLE ONLY public.adopce_zamitnuti DROP CONSTRAINT fk9h9g202320fenbll4q9q61flc;
ALTER TABLE ONLY public.plemeno DROP CONSTRAINT fk97yfls6g16uckkvv5qn6r3in1;
ALTER TABLE ONLY public.zvire DROP CONSTRAINT fk7ghc915lde420tjsy02ytdufb;
ALTER TABLE ONLY public.zvire DROP CONSTRAINT zvire_pkey;
ALTER TABLE ONLY public.zajemce DROP CONSTRAINT zajemce_pkey;
ALTER TABLE ONLY public.veterinarni_zaznam DROP CONSTRAINT veterinarni_zaznam_pkey;
ALTER TABLE ONLY public.uzivatel DROP CONSTRAINT uzivatel_pkey;
ALTER TABLE ONLY public.uzivatel DROP CONSTRAINT uk_s2v7f7l45u02sbasp9f5ci8fr;
ALTER TABLE ONLY public.adopce_zamitnuti DROP CONSTRAINT uk1ig6c0pjd02yi9fgrhgh3sqtx;
ALTER TABLE ONLY public.status_zvirete DROP CONSTRAINT status_zvirete_pkey;
ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
ALTER TABLE ONLY public.plemeno DROP CONSTRAINT plemeno_pkey;
ALTER TABLE ONLY public.duvod_zamitnuti DROP CONSTRAINT duvod_zamitnuti_pkey;
ALTER TABLE ONLY public.druh DROP CONSTRAINT druh_pkey;
ALTER TABLE ONLY public.adopce_zamitnuti DROP CONSTRAINT adopce_zamitnuti_pkey;
ALTER TABLE ONLY public.adopce DROP CONSTRAINT adopce_pkey;
ALTER TABLE public.zvire ALTER COLUMN id_zvire DROP DEFAULT;
ALTER TABLE public.zajemce ALTER COLUMN id_zajemce DROP DEFAULT;
ALTER TABLE public.veterinarni_zaznam ALTER COLUMN id_zaznam DROP DEFAULT;
ALTER TABLE public.uzivatel ALTER COLUMN id_uzivatel DROP DEFAULT;
ALTER TABLE public.status_zvirete ALTER COLUMN id_status DROP DEFAULT;
ALTER TABLE public.role ALTER COLUMN id_role DROP DEFAULT;
ALTER TABLE public.plemeno ALTER COLUMN id_plemeno DROP DEFAULT;
ALTER TABLE public.duvod_zamitnuti ALTER COLUMN id_duvod DROP DEFAULT;
ALTER TABLE public.druh ALTER COLUMN id_druh DROP DEFAULT;
ALTER TABLE public.adopce_zamitnuti ALTER COLUMN id_zamitnuti DROP DEFAULT;
ALTER TABLE public.adopce ALTER COLUMN id_adopce DROP DEFAULT;
DROP SEQUENCE public.zvire_id_zvire_seq;
DROP TABLE public.zvire;
DROP SEQUENCE public.zajemce_id_zajemce_seq;
DROP TABLE public.zajemce;
DROP SEQUENCE public.veterinarni_zaznam_id_zaznam_seq;
DROP TABLE public.veterinarni_zaznam;
DROP SEQUENCE public.uzivatel_id_uzivatel_seq;
DROP TABLE public.uzivatel;
DROP SEQUENCE public.status_zvirete_id_status_seq;
DROP TABLE public.status_zvirete;
DROP SEQUENCE public.role_id_role_seq;
DROP TABLE public.role;
DROP SEQUENCE public.plemeno_id_plemeno_seq;
DROP TABLE public.plemeno;
DROP SEQUENCE public.duvod_zamitnuti_id_duvod_seq;
DROP TABLE public.duvod_zamitnuti;
DROP SEQUENCE public.druh_id_druh_seq;
DROP TABLE public.druh;
DROP SEQUENCE public.adopce_zamitnuti_id_zamitnuti_seq;
DROP TABLE public.adopce_zamitnuti;
DROP SEQUENCE public.adopce_id_adopce_seq;
DROP TABLE public.adopce;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: adopce; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.adopce (
    id_adopce integer NOT NULL,
    datum_schvaleni date,
    datum_zadosti date NOT NULL,
    stav character varying(50) NOT NULL,
    id_zajemce integer NOT NULL,
    id_zvire integer NOT NULL
);


ALTER TABLE public.adopce OWNER TO utulek;

--
-- Name: adopce_id_adopce_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.adopce_id_adopce_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.adopce_id_adopce_seq OWNER TO utulek;

--
-- Name: adopce_id_adopce_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.adopce_id_adopce_seq OWNED BY public.adopce.id_adopce;


--
-- Name: adopce_zamitnuti; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.adopce_zamitnuti (
    id_zamitnuti integer NOT NULL,
    id_adopce integer NOT NULL,
    id_duvod integer NOT NULL
);


ALTER TABLE public.adopce_zamitnuti OWNER TO utulek;

--
-- Name: adopce_zamitnuti_id_zamitnuti_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.adopce_zamitnuti_id_zamitnuti_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.adopce_zamitnuti_id_zamitnuti_seq OWNER TO utulek;

--
-- Name: adopce_zamitnuti_id_zamitnuti_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.adopce_zamitnuti_id_zamitnuti_seq OWNED BY public.adopce_zamitnuti.id_zamitnuti;


--
-- Name: druh; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.druh (
    id_druh integer NOT NULL,
    nazev character varying(50) NOT NULL
);


ALTER TABLE public.druh OWNER TO utulek;

--
-- Name: druh_id_druh_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.druh_id_druh_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.druh_id_druh_seq OWNER TO utulek;

--
-- Name: druh_id_druh_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.druh_id_druh_seq OWNED BY public.druh.id_druh;


--
-- Name: duvod_zamitnuti; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.duvod_zamitnuti (
    id_duvod integer NOT NULL,
    popis text NOT NULL
);


ALTER TABLE public.duvod_zamitnuti OWNER TO utulek;

--
-- Name: duvod_zamitnuti_id_duvod_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.duvod_zamitnuti_id_duvod_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.duvod_zamitnuti_id_duvod_seq OWNER TO utulek;

--
-- Name: duvod_zamitnuti_id_duvod_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.duvod_zamitnuti_id_duvod_seq OWNED BY public.duvod_zamitnuti.id_duvod;


--
-- Name: plemeno; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.plemeno (
    id_plemeno integer NOT NULL,
    nazev character varying(50) NOT NULL,
    id_druh integer NOT NULL
);


ALTER TABLE public.plemeno OWNER TO utulek;

--
-- Name: plemeno_id_plemeno_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.plemeno_id_plemeno_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.plemeno_id_plemeno_seq OWNER TO utulek;

--
-- Name: plemeno_id_plemeno_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.plemeno_id_plemeno_seq OWNED BY public.plemeno.id_plemeno;


--
-- Name: role; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.role (
    id_role integer NOT NULL,
    nazev character varying(50) NOT NULL
);


ALTER TABLE public.role OWNER TO utulek;

--
-- Name: role_id_role_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.role_id_role_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_role_seq OWNER TO utulek;

--
-- Name: role_id_role_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.role_id_role_seq OWNED BY public.role.id_role;


--
-- Name: status_zvirete; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.status_zvirete (
    id_status integer NOT NULL,
    stav character varying(50) NOT NULL
);


ALTER TABLE public.status_zvirete OWNER TO utulek;

--
-- Name: status_zvirete_id_status_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.status_zvirete_id_status_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.status_zvirete_id_status_seq OWNER TO utulek;

--
-- Name: status_zvirete_id_status_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.status_zvirete_id_status_seq OWNED BY public.status_zvirete.id_status;


--
-- Name: uzivatel; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.uzivatel (
    id_uzivatel integer NOT NULL,
    email character varying(50) NOT NULL,
    heslo_hash character varying(255) NOT NULL,
    jmeno character varying(50) NOT NULL,
    prijmeni character varying(50) NOT NULL,
    id_role integer NOT NULL
);


ALTER TABLE public.uzivatel OWNER TO utulek;

--
-- Name: uzivatel_id_uzivatel_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.uzivatel_id_uzivatel_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.uzivatel_id_uzivatel_seq OWNER TO utulek;

--
-- Name: uzivatel_id_uzivatel_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.uzivatel_id_uzivatel_seq OWNED BY public.uzivatel.id_uzivatel;


--
-- Name: veterinarni_zaznam; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.veterinarni_zaznam (
    id_zaznam integer NOT NULL,
    datum date NOT NULL,
    popis character varying(50),
    id_uzivatel integer NOT NULL,
    id_zvire integer NOT NULL
);


ALTER TABLE public.veterinarni_zaznam OWNER TO utulek;

--
-- Name: veterinarni_zaznam_id_zaznam_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.veterinarni_zaznam_id_zaznam_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.veterinarni_zaznam_id_zaznam_seq OWNER TO utulek;

--
-- Name: veterinarni_zaznam_id_zaznam_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.veterinarni_zaznam_id_zaznam_seq OWNED BY public.veterinarni_zaznam.id_zaznam;


--
-- Name: zajemce; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.zajemce (
    id_zajemce integer NOT NULL,
    cislo_popisne integer,
    email character varying(50),
    jmeno character varying(50) NOT NULL,
    ma_jina_zvirata boolean,
    mesto character varying(50),
    prijmeni character varying(50) NOT NULL,
    psc character varying(50),
    schvalen boolean,
    telefon character varying(50),
    typ_bydleni character varying(50),
    ulice character varying(50)
);


ALTER TABLE public.zajemce OWNER TO utulek;

--
-- Name: zajemce_id_zajemce_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.zajemce_id_zajemce_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zajemce_id_zajemce_seq OWNER TO utulek;

--
-- Name: zajemce_id_zajemce_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.zajemce_id_zajemce_seq OWNED BY public.zajemce.id_zajemce;


--
-- Name: zvire; Type: TABLE; Schema: public; Owner: utulek
--

CREATE TABLE public.zvire (
    id_zvire integer NOT NULL,
    datum_narozeni date,
    datum_prijeti date NOT NULL,
    jmeno character varying(50),
    pohlavi character varying(1),
    povaha character varying(50),
    id_plemeno integer NOT NULL,
    id_status integer NOT NULL
);


ALTER TABLE public.zvire OWNER TO utulek;

--
-- Name: zvire_id_zvire_seq; Type: SEQUENCE; Schema: public; Owner: utulek
--

CREATE SEQUENCE public.zvire_id_zvire_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zvire_id_zvire_seq OWNER TO utulek;

--
-- Name: zvire_id_zvire_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: utulek
--

ALTER SEQUENCE public.zvire_id_zvire_seq OWNED BY public.zvire.id_zvire;


--
-- Name: adopce id_adopce; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce ALTER COLUMN id_adopce SET DEFAULT nextval('public.adopce_id_adopce_seq'::regclass);


--
-- Name: adopce_zamitnuti id_zamitnuti; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce_zamitnuti ALTER COLUMN id_zamitnuti SET DEFAULT nextval('public.adopce_zamitnuti_id_zamitnuti_seq'::regclass);


--
-- Name: druh id_druh; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.druh ALTER COLUMN id_druh SET DEFAULT nextval('public.druh_id_druh_seq'::regclass);


--
-- Name: duvod_zamitnuti id_duvod; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.duvod_zamitnuti ALTER COLUMN id_duvod SET DEFAULT nextval('public.duvod_zamitnuti_id_duvod_seq'::regclass);


--
-- Name: plemeno id_plemeno; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.plemeno ALTER COLUMN id_plemeno SET DEFAULT nextval('public.plemeno_id_plemeno_seq'::regclass);


--
-- Name: role id_role; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.role ALTER COLUMN id_role SET DEFAULT nextval('public.role_id_role_seq'::regclass);


--
-- Name: status_zvirete id_status; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.status_zvirete ALTER COLUMN id_status SET DEFAULT nextval('public.status_zvirete_id_status_seq'::regclass);


--
-- Name: uzivatel id_uzivatel; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.uzivatel ALTER COLUMN id_uzivatel SET DEFAULT nextval('public.uzivatel_id_uzivatel_seq'::regclass);


--
-- Name: veterinarni_zaznam id_zaznam; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.veterinarni_zaznam ALTER COLUMN id_zaznam SET DEFAULT nextval('public.veterinarni_zaznam_id_zaznam_seq'::regclass);


--
-- Name: zajemce id_zajemce; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.zajemce ALTER COLUMN id_zajemce SET DEFAULT nextval('public.zajemce_id_zajemce_seq'::regclass);


--
-- Name: zvire id_zvire; Type: DEFAULT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.zvire ALTER COLUMN id_zvire SET DEFAULT nextval('public.zvire_id_zvire_seq'::regclass);


--
-- Data for Name: adopce; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.adopce (id_adopce, datum_schvaleni, datum_zadosti, stav, id_zajemce, id_zvire) FROM stdin;
1	\N	2026-04-09	Probíhá	1	1
\.


--
-- Data for Name: adopce_zamitnuti; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.adopce_zamitnuti (id_zamitnuti, id_adopce, id_duvod) FROM stdin;
\.


--
-- Data for Name: druh; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.druh (id_druh, nazev) FROM stdin;
1	Pes
2	Kočka
3	Králík
\.


--
-- Data for Name: duvod_zamitnuti; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.duvod_zamitnuti (id_duvod, popis) FROM stdin;
1	Nevhodné bytové podmínky
2	Přítomnost malých dětí (zvíře nevhodné)
3	Jiná zvířata v domácnosti (zvíře nevhodné)
4	Nedostatečné zkušenosti s daným druhem
5	Nezájem zájemce po bližším seznámení
6	Jiný důvod
\.


--
-- Data for Name: plemeno; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.plemeno (id_plemeno, nazev, id_druh) FROM stdin;
1	Labrador	1
2	Německý ovčák	1
3	Zlatý retrívr	1
4	Pudl	1
5	Buldok	1
6	Kříženec	1
7	Perská	2
8	Siamská	2
9	Britská krátkosrstá	2
10	Kříženec	2
11	Bílý novozélandský	3
12	Zakrslý	3
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.role (id_role, nazev) FROM stdin;
1	Recepční
2	Veterinář
3	Administrátor
\.


--
-- Data for Name: status_zvirete; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.status_zvirete (id_status, stav) FROM stdin;
1	Přijato
2	V léčení
3	K adopci
4	Rezervováno
5	Adoptováno
\.


--
-- Data for Name: uzivatel; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.uzivatel (id_uzivatel, email, heslo_hash, jmeno, prijmeni, id_role) FROM stdin;
1	recepce@example.com	$2a$10$BPEwIkyXprIjIHBCfZTcD.XCwU4ESidRU3EMWRUZ5wVzcfRNxkNTS	Jan	Novák	1
2	vet@example.com	$2a$10$I1AdoS.Q9moulE.lb8U31eyK4pfX0t1b.PvCVo9g1Iq.tAT28n4wi	Petra	Svobodová	2
3	admin@example.com	$2a$10$eixXn9ARYMEBrV2C/JbjHuxtHODjUgdh9bdpiLh9x6Bv1t7kGGPhW	Admin	Admin	3
\.


--
-- Data for Name: veterinarni_zaznam; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.veterinarni_zaznam (id_zaznam, datum, popis, id_uzivatel, id_zvire) FROM stdin;
1	2026-04-09	Pejsek byl vyléčen, dietka pomohla\r\n	2	1
\.


--
-- Data for Name: zajemce; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.zajemce (id_zajemce, cislo_popisne, email, jmeno, ma_jina_zvirata, mesto, prijmeni, psc, schvalen, telefon, typ_bydleni, ulice) FROM stdin;
1	23	pepazdepa@depo.cz	Pepa	f	Lisa hosra	Zdepa	52131kakakaks	\N	123456789	Dům se zahradou	Pepova
\.


--
-- Data for Name: zvire; Type: TABLE DATA; Schema: public; Owner: utulek
--

COPY public.zvire (id_zvire, datum_narozeni, datum_prijeti, jmeno, pohlavi, povaha, id_plemeno, id_status) FROM stdin;
1	2024-10-25	2026-04-05	Bingo	M	Přátelský, hravý, společenský	3	3
\.


--
-- Name: adopce_id_adopce_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.adopce_id_adopce_seq', 1, true);


--
-- Name: adopce_zamitnuti_id_zamitnuti_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.adopce_zamitnuti_id_zamitnuti_seq', 1, false);


--
-- Name: druh_id_druh_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.druh_id_druh_seq', 3, true);


--
-- Name: duvod_zamitnuti_id_duvod_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.duvod_zamitnuti_id_duvod_seq', 6, true);


--
-- Name: plemeno_id_plemeno_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.plemeno_id_plemeno_seq', 12, true);


--
-- Name: role_id_role_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.role_id_role_seq', 3, true);


--
-- Name: status_zvirete_id_status_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.status_zvirete_id_status_seq', 5, true);


--
-- Name: uzivatel_id_uzivatel_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.uzivatel_id_uzivatel_seq', 3, true);


--
-- Name: veterinarni_zaznam_id_zaznam_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.veterinarni_zaznam_id_zaznam_seq', 1, true);


--
-- Name: zajemce_id_zajemce_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.zajemce_id_zajemce_seq', 1, true);


--
-- Name: zvire_id_zvire_seq; Type: SEQUENCE SET; Schema: public; Owner: utulek
--

SELECT pg_catalog.setval('public.zvire_id_zvire_seq', 1, true);


--
-- Name: adopce adopce_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce
    ADD CONSTRAINT adopce_pkey PRIMARY KEY (id_adopce);


--
-- Name: adopce_zamitnuti adopce_zamitnuti_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce_zamitnuti
    ADD CONSTRAINT adopce_zamitnuti_pkey PRIMARY KEY (id_zamitnuti);


--
-- Name: druh druh_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.druh
    ADD CONSTRAINT druh_pkey PRIMARY KEY (id_druh);


--
-- Name: duvod_zamitnuti duvod_zamitnuti_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.duvod_zamitnuti
    ADD CONSTRAINT duvod_zamitnuti_pkey PRIMARY KEY (id_duvod);


--
-- Name: plemeno plemeno_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.plemeno
    ADD CONSTRAINT plemeno_pkey PRIMARY KEY (id_plemeno);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id_role);


--
-- Name: status_zvirete status_zvirete_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.status_zvirete
    ADD CONSTRAINT status_zvirete_pkey PRIMARY KEY (id_status);


--
-- Name: adopce_zamitnuti uk1ig6c0pjd02yi9fgrhgh3sqtx; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce_zamitnuti
    ADD CONSTRAINT uk1ig6c0pjd02yi9fgrhgh3sqtx UNIQUE (id_adopce, id_duvod);


--
-- Name: uzivatel uk_s2v7f7l45u02sbasp9f5ci8fr; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.uzivatel
    ADD CONSTRAINT uk_s2v7f7l45u02sbasp9f5ci8fr UNIQUE (email);


--
-- Name: uzivatel uzivatel_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.uzivatel
    ADD CONSTRAINT uzivatel_pkey PRIMARY KEY (id_uzivatel);


--
-- Name: veterinarni_zaznam veterinarni_zaznam_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.veterinarni_zaznam
    ADD CONSTRAINT veterinarni_zaznam_pkey PRIMARY KEY (id_zaznam);


--
-- Name: zajemce zajemce_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.zajemce
    ADD CONSTRAINT zajemce_pkey PRIMARY KEY (id_zajemce);


--
-- Name: zvire zvire_pkey; Type: CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.zvire
    ADD CONSTRAINT zvire_pkey PRIMARY KEY (id_zvire);


--
-- Name: zvire fk7ghc915lde420tjsy02ytdufb; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.zvire
    ADD CONSTRAINT fk7ghc915lde420tjsy02ytdufb FOREIGN KEY (id_status) REFERENCES public.status_zvirete(id_status);


--
-- Name: plemeno fk97yfls6g16uckkvv5qn6r3in1; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.plemeno
    ADD CONSTRAINT fk97yfls6g16uckkvv5qn6r3in1 FOREIGN KEY (id_druh) REFERENCES public.druh(id_druh);


--
-- Name: adopce_zamitnuti fk9h9g202320fenbll4q9q61flc; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce_zamitnuti
    ADD CONSTRAINT fk9h9g202320fenbll4q9q61flc FOREIGN KEY (id_duvod) REFERENCES public.duvod_zamitnuti(id_duvod);


--
-- Name: adopce fkfjqkjetyrgjmp7fdhqrefrrj2; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce
    ADD CONSTRAINT fkfjqkjetyrgjmp7fdhqrefrrj2 FOREIGN KEY (id_zajemce) REFERENCES public.zajemce(id_zajemce);


--
-- Name: uzivatel fklbbyui19ek6cvxedh2tmipuqq; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.uzivatel
    ADD CONSTRAINT fklbbyui19ek6cvxedh2tmipuqq FOREIGN KEY (id_role) REFERENCES public.role(id_role);


--
-- Name: adopce_zamitnuti fkmwxgyjj3w5u37o9epk3pd7oyw; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce_zamitnuti
    ADD CONSTRAINT fkmwxgyjj3w5u37o9epk3pd7oyw FOREIGN KEY (id_adopce) REFERENCES public.adopce(id_adopce);


--
-- Name: zvire fkng1mfugbc1j6jspegk74d203q; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.zvire
    ADD CONSTRAINT fkng1mfugbc1j6jspegk74d203q FOREIGN KEY (id_plemeno) REFERENCES public.plemeno(id_plemeno);


--
-- Name: veterinarni_zaznam fko8kyk780he28ryj8foj2yklvg; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.veterinarni_zaznam
    ADD CONSTRAINT fko8kyk780he28ryj8foj2yklvg FOREIGN KEY (id_zvire) REFERENCES public.zvire(id_zvire);


--
-- Name: adopce fkp5tfcoaj1elocya8uph5c4qlj; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.adopce
    ADD CONSTRAINT fkp5tfcoaj1elocya8uph5c4qlj FOREIGN KEY (id_zvire) REFERENCES public.zvire(id_zvire);


--
-- Name: veterinarni_zaznam fkrnc2amub4wbukmxypsxcrl211; Type: FK CONSTRAINT; Schema: public; Owner: utulek
--

ALTER TABLE ONLY public.veterinarni_zaznam
    ADD CONSTRAINT fkrnc2amub4wbukmxypsxcrl211 FOREIGN KEY (id_uzivatel) REFERENCES public.uzivatel(id_uzivatel);


--
-- PostgreSQL database dump complete
--

\unrestrict 5MHf9HiSQtzeMCptPRrNczgNQmfhW5SY7Ry1cskUKkgDvpcp2QboeusnIky8w7x

