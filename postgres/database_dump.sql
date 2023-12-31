PGDMP     %    ;            	    {         	   simbir_go    14.5    14.5 4    5           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            6           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            7           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            8           1262    49556 	   simbir_go    DATABASE     f   CREATE DATABASE simbir_go WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE simbir_go;
                postgres    false            �            1259    49557    users    TABLE     �   CREATE TABLE public.users (
    id_user bigint NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    is_admin boolean NOT NULL,
    balance double precision
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    49562    User_user_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id_user ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."User_user_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    209            �            1259    49650 	   blacklist    TABLE     j   CREATE TABLE public.blacklist (
    id_blacklist bigint NOT NULL,
    token character varying NOT NULL
);
    DROP TABLE public.blacklist;
       public         heap    postgres    false            �            1259    49649    blacklist_id_blacklist_seq    SEQUENCE     �   ALTER TABLE public.blacklist ALTER COLUMN id_blacklist ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.blacklist_id_blacklist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    224            �            1259    49563    colors    TABLE     c   CREATE TABLE public.colors (
    id_color bigint NOT NULL,
    color character varying NOT NULL
);
    DROP TABLE public.colors;
       public         heap    postgres    false            �            1259    49568    colors_id_color_seq    SEQUENCE     �   ALTER TABLE public.colors ALTER COLUMN id_color ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.colors_id_color_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    211            �            1259    49569 
   price_type    TABLE     q   CREATE TABLE public.price_type (
    id_price_type bigint NOT NULL,
    price_type character varying NOT NULL
);
    DROP TABLE public.price_type;
       public         heap    postgres    false            �            1259    49574    rent_types_id_rent_type_seq    SEQUENCE     �   ALTER TABLE public.price_type ALTER COLUMN id_price_type ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rent_types_id_rent_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    213            �            1259    49575    rents    TABLE     E  CREATE TABLE public.rents (
    id_rent bigint NOT NULL,
    time_start timestamp with time zone NOT NULL,
    time_end timestamp with time zone,
    id_transport bigint NOT NULL,
    id_user bigint NOT NULL,
    id_price_type bigint NOT NULL,
    final_price double precision,
    price_of_unit double precision NOT NULL
);
    DROP TABLE public.rents;
       public         heap    postgres    false            �            1259    49578    rents_id_rent_seq    SEQUENCE     �   ALTER TABLE public.rents ALTER COLUMN id_rent ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rents_id_rent_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    49579    transport_models    TABLE     n   CREATE TABLE public.transport_models (
    id_transport_model bigint NOT NULL,
    model character varying
);
 $   DROP TABLE public.transport_models;
       public         heap    postgres    false            �            1259    49584 &   transport_model_id_transport_model_seq    SEQUENCE     �   ALTER TABLE public.transport_models ALTER COLUMN id_transport_model ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.transport_model_id_transport_model_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    49585    transport_types    TABLE     ~   CREATE TABLE public.transport_types (
    id_transport_type bigint NOT NULL,
    transport_type character varying NOT NULL
);
 #   DROP TABLE public.transport_types;
       public         heap    postgres    false            �            1259    49590 %   transport_types_id_transport_type_seq    SEQUENCE     �   ALTER TABLE public.transport_types ALTER COLUMN id_transport_type ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.transport_types_id_transport_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    49591 
   transports    TABLE     �  CREATE TABLE public.transports (
    id_transport bigint NOT NULL,
    can_be_rented boolean NOT NULL,
    id_model bigint NOT NULL,
    id_color bigint NOT NULL,
    identifier character varying NOT NULL,
    description character varying,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    id_owner bigint NOT NULL,
    id_transport_type bigint NOT NULL,
    day_price double precision,
    minute_price double precision
);
    DROP TABLE public.transports;
       public         heap    postgres    false            �            1259    49596    transports_id_transport_seq    SEQUENCE     �   ALTER TABLE public.transports ALTER COLUMN id_transport ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.transports_id_transport_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            2          0    49650 	   blacklist 
   TABLE DATA           8   COPY public.blacklist (id_blacklist, token) FROM stdin;
    public          postgres    false    224   �?       %          0    49563    colors 
   TABLE DATA           1   COPY public.colors (id_color, color) FROM stdin;
    public          postgres    false    211   V@       '          0    49569 
   price_type 
   TABLE DATA           ?   COPY public.price_type (id_price_type, price_type) FROM stdin;
    public          postgres    false    213   �@       )          0    49575    rents 
   TABLE DATA           �   COPY public.rents (id_rent, time_start, time_end, id_transport, id_user, id_price_type, final_price, price_of_unit) FROM stdin;
    public          postgres    false    215   �@       +          0    49579    transport_models 
   TABLE DATA           E   COPY public.transport_models (id_transport_model, model) FROM stdin;
    public          postgres    false    217   A       -          0    49585    transport_types 
   TABLE DATA           L   COPY public.transport_types (id_transport_type, transport_type) FROM stdin;
    public          postgres    false    219   FA       /          0    49591 
   transports 
   TABLE DATA           �   COPY public.transports (id_transport, can_be_rented, id_model, id_color, identifier, description, latitude, longitude, id_owner, id_transport_type, day_price, minute_price) FROM stdin;
    public          postgres    false    221   tA       #          0    49557    users 
   TABLE DATA           O   COPY public.users (id_user, username, password, is_admin, balance) FROM stdin;
    public          postgres    false    209   �A       9           0    0    User_user_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."User_user_id_seq"', 6, true);
          public          postgres    false    210            :           0    0    blacklist_id_blacklist_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.blacklist_id_blacklist_seq', 1, true);
          public          postgres    false    223            ;           0    0    colors_id_color_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.colors_id_color_seq', 3, true);
          public          postgres    false    212            <           0    0    rent_types_id_rent_type_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.rent_types_id_rent_type_seq', 2, true);
          public          postgres    false    214            =           0    0    rents_id_rent_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.rents_id_rent_seq', 3, true);
          public          postgres    false    216            >           0    0 &   transport_model_id_transport_model_seq    SEQUENCE SET     T   SELECT pg_catalog.setval('public.transport_model_id_transport_model_seq', 3, true);
          public          postgres    false    218            ?           0    0 %   transport_types_id_transport_type_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.transport_types_id_transport_type_seq', 3, true);
          public          postgres    false    220            @           0    0    transports_id_transport_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.transports_id_transport_seq', 12, true);
          public          postgres    false    222            �           2606    49598    users User_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id_user);
 ;   ALTER TABLE ONLY public.users DROP CONSTRAINT "User_pkey";
       public            postgres    false    209            �           2606    49600 !   users User_username_username1_key 
   CONSTRAINT     u   ALTER TABLE ONLY public.users
    ADD CONSTRAINT "User_username_username1_key" UNIQUE (username) INCLUDE (username);
 M   ALTER TABLE ONLY public.users DROP CONSTRAINT "User_username_username1_key";
       public            postgres    false    209            �           2606    49656    blacklist blacklist_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.blacklist
    ADD CONSTRAINT blacklist_pkey PRIMARY KEY (id_blacklist);
 B   ALTER TABLE ONLY public.blacklist DROP CONSTRAINT blacklist_pkey;
       public            postgres    false    224            �           2606    49602    colors colors_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.colors
    ADD CONSTRAINT colors_pkey PRIMARY KEY (id_color);
 <   ALTER TABLE ONLY public.colors DROP CONSTRAINT colors_pkey;
       public            postgres    false    211            �           2606    49604    price_type rent_types_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.price_type
    ADD CONSTRAINT rent_types_pkey PRIMARY KEY (id_price_type);
 D   ALTER TABLE ONLY public.price_type DROP CONSTRAINT rent_types_pkey;
       public            postgres    false    213            �           2606    49606    rents rents_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_pkey PRIMARY KEY (id_rent);
 :   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_pkey;
       public            postgres    false    215            �           2606    49608 %   transport_models transport_model_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.transport_models
    ADD CONSTRAINT transport_model_pkey PRIMARY KEY (id_transport_model);
 O   ALTER TABLE ONLY public.transport_models DROP CONSTRAINT transport_model_pkey;
       public            postgres    false    217            �           2606    49610 $   transport_types transport_types_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.transport_types
    ADD CONSTRAINT transport_types_pkey PRIMARY KEY (id_transport_type);
 N   ALTER TABLE ONLY public.transport_types DROP CONSTRAINT transport_types_pkey;
       public            postgres    false    219            �           2606    49612    transports transports_pkey 
   CONSTRAINT     y   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_pkey PRIMARY KEY (id_transport) INCLUDE (id_transport);
 D   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_pkey;
       public            postgres    false    221            �           2606    49613    rents rents_id_price_type_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_id_price_type_fkey FOREIGN KEY (id_price_type) REFERENCES public.price_type(id_price_type) NOT VALID;
 H   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_id_price_type_fkey;
       public          postgres    false    213    3206    215            �           2606    49618    rents rents_id_transport_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_id_transport_fkey FOREIGN KEY (id_transport) REFERENCES public.transports(id_transport) NOT VALID;
 G   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_id_transport_fkey;
       public          postgres    false    3214    221    215            �           2606    49623    rents rents_id_user_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_id_user_fkey FOREIGN KEY (id_user) REFERENCES public.users(id_user) NOT VALID;
 B   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_id_user_fkey;
       public          postgres    false    209    3200    215            �           2606    49628 #   transports transports_id_color_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_id_color_fkey FOREIGN KEY (id_color) REFERENCES public.colors(id_color) NOT VALID;
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_id_color_fkey;
       public          postgres    false    211    221    3204            �           2606    49633 #   transports transports_id_model_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_id_model_fkey FOREIGN KEY (id_model) REFERENCES public.transport_models(id_transport_model) NOT VALID;
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_id_model_fkey;
       public          postgres    false    221    3210    217            �           2606    49638 #   transports transports_id_owner_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_id_owner_fkey FOREIGN KEY (id_owner) REFERENCES public.users(id_user) NOT VALID;
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_id_owner_fkey;
       public          postgres    false    3200    221    209            �           2606    49643 ,   transports transports_id_transport_type_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_id_transport_type_fkey FOREIGN KEY (id_transport_type) REFERENCES public.transport_types(id_transport_type) NOT VALID;
 V   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_id_transport_type_fkey;
       public          postgres    false    219    221    3212            2   �   x��K�0 е���1��?�F%1@��؁�`��=���&�#�m_r�/�X��/]��?]����,��l�#�23`ӍԴsڠ���H�*��C�y纳W�-61gZ�'���$ߍj��Ԋ�'��J�,�      %      x�3�,JM�2�L�IL��2�І\1z\\\ j��      '      x�3����+-I-�2�tI�,����� I�      )   X   x�}�1� ����N�����G�ҥRe����b0�D��pe�	k�q`�)�@���Őn��z���`b5�|�6��3 w��T��7�      +      x�3�t4�2�	�2�\1z\\\ '`n      -      x�3�tN,�2�t��N�2S�\1z\\\ R�      /   {   x���=�0Й�)Т���џ�[�҄2�$]�^ 0<@�D��^��ڀ#��1����%(��?ue���`#Q�(�h0�h�Pu4�����Q�=�C�R���fr]�s��Zp�      #   ^   x�U�1
�0D�z�0��&�ؤ0a�h��k�N��1�e�T,�G�4�4�*��ҶXn�R�c%����ѽTE8�a9}��?��#��)!0     