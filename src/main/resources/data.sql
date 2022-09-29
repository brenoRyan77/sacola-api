INSERT INTO restaurante (id, cep, complemento, nome) VALUES
(1L, '58056540', 'Rua Comerciante Joao Rodrigues de Lima', 'Restaurante 1'),
(2L, '58056568', 'Rua Jose Batista Filho', 'Restaurante 2');

INSERT INTO cliente (id, cep, complemento, nome) VALUES
(1L, '58088775', 'Vila Sao Joao Batista', 'Breno');

INSERT INTO produto (id, disponivel, nome, valor_unitario, restaurante_id) VALUES
(1L, true, 'Acai', 5.0, 1L),
(2L, true, 'Pastel', 6.0, 1L),
(3L, true, 'Coxinha', 7.0, 2L);

INSERT INTO sacola (id, fechada, forma_pagamento, valores_total, cliente_id) VALUES
(1L, false, 0, 0, 1L);