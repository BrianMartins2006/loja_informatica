CREATE DATABASE IF NOT EXISTS loja_informatica;
USE loja_informatica;

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    pergunta_seguranca VARCHAR(255),
    resposta_hash VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao varchar(100)
);

CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE SET NULL
);

-- Categorias
INSERT INTO categoria (nome, descricao) VALUES
('Periféricos', 'Mouses, teclados e outros dispositivos de entrada.'),
('Hardware', 'Peças internas como processadores, placas-mãe e memória RAM.'),
('Monitores', 'Monitores de vídeo e telas profissionais.'),
('Armazenamento', 'Discos rígidos, SSDs e dispositivos externos.'),
('Acessórios', 'Suportes, cabos e outros itens complementares.'),
('Impressoras', 'Impressoras e suprimentos de impressão.');

-- Produtos
INSERT INTO produto (nome, preco, estoque, categoria_id) VALUES
('Mouse Logitech M90', 49.90, 30, 1),
('Teclado Mecânico Redragon', 289.00, 15, 1),
('Placa de Vídeo RTX 4060 8GB', 2599.99, 5, 2),
('HD 1TB Seagate', 299.90, 20, 4),
('SSD Kingston 480GB', 329.90, 18, 4),
('Monitor LG 24” IPS', 799.00, 10, 3),
('Headset Gamer HyperX', 459.90, 12, 5);

-- Vendas
INSERT INTO venda (produto_id, quantidade) VALUES
(1, 2),  -- 2 Mouses
(3, 1),  -- 1 Placa de Vídeo
(5, 3),  -- 3 SSDs
(6, 1),  -- 1 Monitor
(2, 2);  -- 2 Teclados

-- Usuario inicial
INSERT INTO users (email, senha_hash, pergunta_seguranca, resposta_hash)
VALUES('adm@gmail.com', '$2a$10$/oo2YPwGn8lc5TBkemFJ1ukFCOYjP5E6CcRc5RufHzoLSeOt1Xg62', 'Qual o nome do professor da diciplina?','$2a$10$gwGlhnlB8DEoOkSVOQYAhOCpYbmo7xLpawrhXAAUBYimAXeanMYJS');

select * from users;

-- usuario inicial
-- adm@gmail.com
-- Senha adm123
-- Resposta de recuperação: emerson
