--
-- PostgreSQL database dump
--

-- Dumped from database version 11.4
-- Dumped by pg_dump version 11.4

-- Started on 2019-08-12 20:38:23

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

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 200 (class 1259 OID 24626)
-- Name: activities; Type: TABLE; Schema: public; Owner: yurii
--

CREATE TABLE public.activities
(
    id          bigint                 NOT NULL,
    name        character varying(255) NOT NULL,
    description text,
    start_time  timestamp without time zone,
    end_time    timestamp without time zone,
    duration    bigint,
    importance  character varying(50),
    status      character varying(50)
);


ALTER TABLE public.activities
    OWNER TO yurii;

--
-- TOC entry 199 (class 1259 OID 24624)
-- Name: activities_id_seq; Type: SEQUENCE; Schema: public; Owner: yurii
--

CREATE SEQUENCE public.activities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.activities_id_seq
    OWNER TO yurii;

--
-- TOC entry 2850 (class 0 OID 0)
-- Dependencies: 199
-- Name: activities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yurii
--

ALTER SEQUENCE public.activities_id_seq OWNED BY public.activities.id;


--
-- TOC entry 203 (class 1259 OID 24650)
-- Name: activity_requests; Type: TABLE; Schema: public; Owner: yurii
--

CREATE TABLE public.activity_requests
(
    id           bigint NOT NULL,
    activity_id  bigint,
    user_id      bigint,
    request_date timestamp without time zone,
    action       character varying(30),
    status       character varying(50)
);


ALTER TABLE public.activity_requests
    OWNER TO yurii;

--
-- TOC entry 202 (class 1259 OID 24648)
-- Name: activity_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: yurii
--

CREATE SEQUENCE public.activity_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.activity_requests_id_seq
    OWNER TO yurii;

--
-- TOC entry 2851 (class 0 OID 0)
-- Dependencies: 202
-- Name: activity_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yurii
--

ALTER SEQUENCE public.activity_requests_id_seq OWNED BY public.activity_requests.id;


--
-- TOC entry 198 (class 1259 OID 24613)
-- Name: user_authorities; Type: TABLE; Schema: public; Owner: yurii
--

CREATE TABLE public.user_authorities
(
    user_id     bigint NOT NULL,
    authorities character varying(50)
);


ALTER TABLE public.user_authorities
    OWNER TO yurii;

--
-- TOC entry 197 (class 1259 OID 16490)
-- Name: users; Type: TABLE; Schema: public; Owner: yurii
--

CREATE TABLE public.users
(
    id         bigint                 NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name  character varying(100) NOT NULL,
    password   character varying(32)  NOT NULL,
    username   character varying(100) NOT NULL
);


ALTER TABLE public.users
    OWNER TO yurii;

--
-- TOC entry 201 (class 1259 OID 24635)
-- Name: users_activities; Type: TABLE; Schema: public; Owner: yurii
--

CREATE TABLE public.users_activities
(
    user_id     bigint NOT NULL,
    activity_id bigint NOT NULL
);


ALTER TABLE public.users_activities
    OWNER TO yurii;

--
-- TOC entry 196 (class 1259 OID 16488)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: yurii
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq
    OWNER TO yurii;

--
-- TOC entry 2852 (class 0 OID 0)
-- Dependencies: 196
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: yurii
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 2707 (class 2604 OID 24629)
-- Name: activities id; Type: DEFAULT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.activities
    ALTER COLUMN id SET DEFAULT nextval('public.activities_id_seq'::regclass);


--
-- TOC entry 2708 (class 2604 OID 24653)
-- Name: activity_requests id; Type: DEFAULT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.activity_requests
    ALTER COLUMN id SET DEFAULT nextval('public.activity_requests_id_seq'::regclass);


--
-- TOC entry 2706 (class 2604 OID 16493)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.users
    ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 2714 (class 2606 OID 24634)
-- Name: activities activities_pkey; Type: CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);


--
-- TOC entry 2718 (class 2606 OID 24655)
-- Name: activity_requests activity_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.activity_requests
    ADD CONSTRAINT activity_requests_pkey PRIMARY KEY (id);


--
-- TOC entry 2716 (class 2606 OID 32806)
-- Name: users_activities users_activities_pk; Type: CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.users_activities
    ADD CONSTRAINT users_activities_pk PRIMARY KEY (user_id, activity_id);


--
-- TOC entry 2711 (class 2606 OID 16497)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 2709 (class 1259 OID 16494)
-- Name: users_id_uindex; Type: INDEX; Schema: public; Owner: yurii
--

CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);


--
-- TOC entry 2712 (class 1259 OID 16495)
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: yurii
--

CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);


--
-- TOC entry 2722 (class 2606 OID 32807)
-- Name: activity_requests activity_requests_activities_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.activity_requests
    ADD CONSTRAINT activity_requests_activities_id_fk FOREIGN KEY (activity_id) REFERENCES public.activities (id) ON DELETE CASCADE;


--
-- TOC entry 2723 (class 2606 OID 32822)
-- Name: activity_requests activity_requests_users_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.activity_requests
    ADD CONSTRAINT activity_requests_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE;


--
-- TOC entry 2719 (class 2606 OID 24671)
-- Name: user_authorities user_authorities_users_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.user_authorities
    ADD CONSTRAINT user_authorities_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2720 (class 2606 OID 32812)
-- Name: users_activities users_activities_activities_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.users_activities
    ADD CONSTRAINT users_activities_activities_id_fk FOREIGN KEY (activity_id) REFERENCES public.activities (id) ON DELETE CASCADE;


--
-- TOC entry 2721 (class 2606 OID 32817)
-- Name: users_activities users_activities_users_id_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: yurii
--

ALTER TABLE ONLY public.users_activities
    ADD CONSTRAINT users_activities_users_id_fk_2 FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE;

--
-- Insert default admin user
--

INSERT INTO public.users
VALUES (nextval('public.users_id_seq'), 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin');

--
-- Insert admin role to default admin user
--

INSERT INTO public.user_authorities
VALUES ((SELECT id FROM public.users WHERE username = 'admin'), 'ADMIN');
INSERT INTO public.user_authorities
VALUES ((SELECT id FROM public.users WHERE username = 'admin'), 'USER');

-- Completed on 2019-08-12 20:38:23

--
-- PostgreSQL database dump complete
--

