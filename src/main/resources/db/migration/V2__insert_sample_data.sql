-- =====================================================
-- SEED DE DADOS - KANBAN | Postgres
-- Equilibrado: 10 Secretarias / 50 Projetos / 40 Accountables
-- =====================================================

-- Limpando tabelas antes (opcional)
TRUNCATE TABLE project_has_accountable CASCADE;
TRUNCATE TABLE project CASCADE;
TRUNCATE TABLE accountable CASCADE;
TRUNCATE TABLE secretariat CASCADE;
-- Reiniciar as sequências
ALTER SEQUENCE secretariat_id_seq RESTART WITH 1;
ALTER SEQUENCE project_id_seq RESTART WITH 1;
ALTER SEQUENCE accountable_id_seq RESTART WITH 1;

-- =====================================================
-- INSERTS: SECRETARIAS DE GOVERNO
-- =====================================================
INSERT INTO secretariat (name, description, created_at)
VALUES
('Secretaria de Saúde', 'Gestão da saúde pública', NOW()),
('Secretaria de Educação', 'Gestão do ensino público', NOW()),
('Secretaria de Segurança Pública', 'Proteção e segurança da população', NOW()),
('Secretaria de Agricultura', 'Desenvolvimento agropecuário', NOW()),
('Secretaria de Cultura', 'Ações culturais e artísticas', NOW()),
('Secretaria de Obras', 'Infraestrutura e construção pública', NOW()),
('Secretaria de Transporte', 'Mobilidade e logística urbana', NOW()),
('Secretaria de Meio Ambiente', 'Sustentabilidade e conservação', NOW()),
('Secretaria de Turismo', 'Promoção do turismo local', NOW()),
('Secretaria de Assistência Social', 'Apoio a famílias e inclusão social', NOW());

-- =====================================================
-- INSERTS: PROJECTOS POR SECRETARIA (5 POR SECRETARIA)
-- =====================================================
INSERT INTO project (secretariat_id, name, status, expected_start, created_at)
VALUES
-- Saúde (1-5)
(1,'Reforma do Hospital Central','Em andamento','2025-01-10',NOW()),
(1,'Ampliação de postos de saúde','Em planejamento','2025-02-01',NOW()),
(1,'Programa de vacinação rural','Concluído','2024-11-01',NOW()),
(1,'Digitalização de prontuários','Em andamento','2025-03-15',NOW()),
(1,'Mutirão de cirurgias','Atrasado','2025-01-20',NOW()),

-- Educação (6-10)
(2,'Reforma de escolas públicas','Em andamento','2025-03-01',NOW()),
(2,'Capacitação de professores','Concluído','2024-12-01',NOW()),
(2,'Implantação de ensino técnico','Em planejamento','2025-06-10',NOW()),
(2,'Projeto merenda saudável','Em andamento','2025-03-20',NOW()),
(2,'Bibliotecas comunitárias','Atrasado','2025-02-10',NOW()),

-- Segurança Pública (11-15)
(3,'Câmeras de vigilância urbana','Em andamento','2025-04-01',NOW()),
(3,'Reforço policial em áreas críticas','Concluído','2024-10-25',NOW()),
(3,'Modernização de viaturas','Em planejamento','2025-05-01',NOW()),
(3,'Patrulha escolar','Em andamento','2025-03-10',NOW()),
(3,'Sistema digital de ocorrências','Atrasado','2025-01-22',NOW()),

-- Agricultura (16-20)
(4,'Programa agro familiar','Em andamento','2025-04-01',NOW()),
(4,'Controle de pragas','Concluído','2024-12-15',NOW()),
(4,'Feiras do produtor','Em planejamento','2025-06-01',NOW()),
(4,'Expansão de irrigação','Atrasado','2025-01-05',NOW()),
(4,'Cadastro rural digital','Em andamento','2025-02-20',NOW()),

-- Cultura (21-25)
(5,'Festival musical','Em planejamento','2025-08-01',NOW()),
(5,'Restauração de museus','Em andamento','2025-03-01',NOW()),
(5,'Cinema para todos','Concluído','2024-11-15',NOW()),
(5,'Biblioteca digital cultural','Atrasado','2025-02-10',NOW()),
(5,'Casa do artista regional','Em andamento','2025-04-05',NOW()),

-- Obras (26-30)
(6,'Pavimentação urbana','Em andamento','2025-03-12',NOW()),
(6,'Construção de pontes','Em planejamento','2025-08-05',NOW()),
(6,'Reforma de prédios públicos','Atrasado','2025-02-18',NOW()),
(6,'Drenagem de áreas alagadas','Concluído','2024-10-12',NOW()),
(6,'Acessibilidade urbana','Em andamento','2025-03-22',NOW()),

-- Transporte (31-35)
(7,'Modernização de terminais','Atrasado','2025-01-03',NOW()),
(7,'Faixas exclusivas ônibus','Em andamento','2025-04-08',NOW()),
(7,'Ciclovias urbanas','Concluído','2024-09-01',NOW()),
(7,'Bilhetagem digital','Em andamento','2025-03-14',NOW()),
(7,'Fiscalização de trânsito','Em planejamento','2025-05-22',NOW()),

-- Meio Ambiente (36-40)
(8,'Coleta seletiva','Em andamento','2025-03-10',NOW()),
(8,'Reflorestamento urbano','Em planejamento','2025-07-18',NOW()),
(8,'Campanha de reciclagem','Concluído','2024-12-10',NOW()),
(8,'Controle de queimadas','Atrasado','2025-01-10',NOW()),
(8,'Tratamento de esgoto','Em andamento','2025-04-10',NOW()),

-- Turismo (41-45)
(9,'Rota histórica municipal','Em andamento','2025-03-01',NOW()),
(9,'Calendário festivo','Concluído','2024-12-22',NOW()),
(9,'Sinalização turística','Atrasado','2025-02-12',NOW()),
(9,'Centro de informações turísticas','Em planejamento','2025-07-11',NOW()),
(9,'Marketing de turismo digital','Em andamento','2025-04-03',NOW()),

-- Assistência Social (46-50)
(10,'Apoio a famílias vulneráveis','Em andamento','2025-03-12',NOW()),
(10,'Centro de acolhimento','Em planejamento','2025-06-05',NOW()),
(10,'Cartão alimentação','Concluído','2024-11-11',NOW()),
(10,'Abrigos emergenciais','Atrasado','2025-01-18',NOW()),
(10,'Inclusão digital idosos','Em andamento','2025-03-20',NOW());

-- =====================================================
-- INSERTS: RESPONSÁVEIS (40)
-- Cada um vinculado a uma secretaria
-- =====================================================
INSERT INTO accountable (name, email, role, secretariat_id, created_at)
VALUES
('Maria Silva','maria.silva@gov.br','Coordenadora',1,NOW()),
('João Santos','joao.santos@gov.br','Analista',1,NOW()),
('Ana Oliveira','ana.oliveira@gov.br','Gerente',2,NOW()),
('Carlos Pereira','carlos.pereira@gov.br','Analista',2,NOW()),
('Lucas Almeida','lucas.almeida@gov.br','Supervisor',3,NOW()),
('Fernanda Costa','fernanda.costa@gov.br','Coordenadora',3,NOW()),
('Ricardo Souza','ricardo.souza@gov.br','Engenheiro',4,NOW()),
('Juliana Mendes','juliana.mendes@gov.br','Analista',4,NOW()),
('Pedro Henrique','pedro.henrique@gov.br','Produtor cultural',5,NOW()),
('Beatriz Lima','beatriz.lima@gov.br','Assessora',5,NOW()),
('Rafael Gomes','rafael.gomes@gov.br','Engenheiro',6,NOW()),
('Carolina Dias','carolina.dias@gov.br','Coordenadora',6,NOW()),
('Carlos Matos','carlos.matos@gov.br','Planejador',7,NOW()),
('Amanda Paulo','amanda.paulo@gov.br','Analista',7,NOW()),
('Tiago Prado','tiago.prado@gov.br','Tec. Ambiental',8,NOW()),
('Larissa Neves','larissa.neves@gov.br','Coordenadora',8,NOW()),
('Daniel Rocha','daniel.rocha@gov.br','Turismólogo',9,NOW()),
('Vanessa Souza','vanessa.souza@gov.br','Analista',9,NOW()),
('Eduardo Campos','eduardo.campos@gov.br','Assistente social',10,NOW()),
('Natália Farias','natalia.farias@gov.br','Coordenadora',10,NOW()),
-- 20 restantes (repetindo secretarias aleatoriamente)
('Gabriel Luz','gabriel.luz@gov.br','Analista',1,NOW()),
('Paula Rosa','paula.rosa@gov.br','Gerente',2,NOW()),
('Rodrigo Freitas','rodrigo.freitas@gov.br','Supervisor',3,NOW()),
('Jessica Cruz','jessica.cruz@gov.br','Analista',4,NOW()),
('Marcelo Torres','marcelo.torres@gov.br','Eng. Civil',6,NOW()),
('Sara Ribeiro','sara.ribeiro@gov.br','Coordenadora',7,NOW()),
('Vinícius Braga','vinicius.braga@gov.br','Analista',8,NOW()),
('Mariana Luz','mariana.luz@gov.br','Produtora',5,NOW()),
('José Miguel','jose.miguel@gov.br','Consultor',9,NOW()),
('Patrícia Alves','patricia.alves@gov.br','Assistente',10,NOW()),
('Fábio Porto','fabio.porto@gov.br','Analista',1,NOW()),
('Camila Soares','camila.soares@gov.br','Gerente',3,NOW()),
('Leandro Matias','leandro.matias@gov.br','Fiscal',6,NOW()),
('Roberta Luz','roberta.luz@gov.br','Analista',4,NOW()),
('Felipe Duarte','felipe.duarte@gov.br','Coordenador',7,NOW()),
('Alessandra Melo','alessandra.melo@gov.br','Analista',8,NOW()),
('Igor Lisboa','igor.lisboa@gov.br','Geógrafo',9,NOW()),
('Joana Prado','joana.prado@gov.br','Assistente social',10,NOW()),
('Murilo Ramos','murilo.ramos@gov.br','Analista',2,NOW()),
('Helena Neri','helena.neri@gov.br','Supervisora',5,NOW());

-- =====================================================
-- RELAÇÃO: PROJETOS X RESPONSÁVEIS
-- Distribuição com 2 por projeto em média
-- (Randomizada de forma coerente)
-- =====================================================
INSERT INTO project_has_accountable (project_id, accountable_id)
SELECT p.id, a.id
FROM project p
JOIN accountable a ON a.secretariat_id = p.secretariat_id
WHERE a.id % 2 = p.id % 2;
