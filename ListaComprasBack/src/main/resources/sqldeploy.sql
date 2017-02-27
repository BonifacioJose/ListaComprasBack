drop table if exists lista_compra_produto;
drop table if exists lista_compra;
drop table if exists produto;

create table lista_compra (
id bigserial not null primary key,
nome varchar(255),
dataCadastro timestamp
);

create table produto(
id bigserial not null primary key,
nome varchar(255),
valor numeric(15,2),
urlImagem varchar(255)
);

create table lista_compra_produto (
idlistacompra bigint references lista_compra(id),
idproduto bigint references produto(id),
quantidade int,
valorProduto numeric(15,2)
);

insert into lista_compra values (1, 'Lista 1', '2017-02-27 11:18:47');

insert into produto values (1, 'Relógio Cassio', 169.99, 'https://http2.mlstatic.com/D_Q_NP_15605-MLB20106657699_062014-T.webp');
insert into produto values (2, 'Caderno Tipo Moleskine', 19.80, 'https://http2.mlstatic.com/D_Q_NP_732021-MLB20699711577_052016-T.webp');
insert into produto values (3, 'Medidor de Pressão', 69.00, 'https://http2.mlstatic.com/D_Q_NP_917115-MLB25180470234_112016-T.webp');
insert into produto values (4, '24 Canetas Color Gel Glitter', 39.90, 'https://http2.mlstatic.com/D_Q_NP_991215-MLB25183089007_112016-T.webp');
insert into produto values (5, 'Relógio Parede', 44.99, 'https://http2.mlstatic.com/D_Q_NP_831615-MLB25272908455_012017-T.webp');
insert into produto values (6, 'Anel Masculino', 129.99, 'https://http2.mlstatic.com/D_Q_NP_601601-MLB20356759557_072015-T.webp');
insert into produto values (7, 'Moden Wireless', 119.90, 'https://http2.mlstatic.com/D_Q_NP_191115-MLB25203765347_122016-T.webp');
insert into produto values (8, 'Cordão de Ouro', 189.80, 'https://http2.mlstatic.com/D_Q_NP_23075-MLB20241631676_022015-T.webp');

insert into lista_compra_produto values (1, 1, 2, 200.00);
insert into lista_compra_produto values (1, 3, 5, 333.00);