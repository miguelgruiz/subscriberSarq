/* POBLANDO DB */
insert into CUSTODIAN (id, comment, hash, proc, uri, version) values (null, 'Esto es un comentario', '00000001', 'P1', 'http://ejemplo1.es', 'v1.0.10');
insert into CUSTODIAN (id, comment, hash, proc, uri, version) values (null, 'Esto es un comentario', '00000002', 'P2', 'http://ejemplo2.es', 'v2.3.18');
insert into LINEAGE (id, hash, hash_origin, uri, uri_origin) values (null, '00000002', '00000001', 'http://ejemplo2.es', 'http://ejemplo1.es');
insert into CUSTODIAN (id, comment, hash, proc, uri, version) values (null, 'Esto es un comentario', '00000003', 'P3', 'http://ejemplo3.es', 'v3.11.0');
insert into LINEAGE (id, hash, hash_origin, uri, uri_origin) values (null, '00000003', '00000001', 'http://ejemplo3.es', 'http://ejemplo1.es');
insert into LINEAGE (id, hash, hash_origin, uri, uri_origin) values (null, '00000003', '00000002', 'http://ejemplo3.es', 'http://ejemplo2.es');





