-- =============================
-- TABELA: secretariat
-- =============================
CREATE TABLE secretariat (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    description VARCHAR(45),
    uuid VARCHAR(45) DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================
-- TABELA: project
-- =============================
CREATE TABLE project (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    secretariat_id BIGINT NOT NULL,
    name VARCHAR(45) NOT NULL,
    status VARCHAR(45) NOT NULL,
    uuid VARCHAR(45) DEFAULT gen_random_uuid(),
    expected_start TIMESTAMP,
    expected_therm TIMESTAMP,
    start_actual TIMESTAMP,
    thermal_actual TIMESTAMP,
    days_late INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_project_secretariat
            FOREIGN KEY (secretariat_id)
            REFERENCES secretariat(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

-- =============================
-- TABELA: accountable
-- =============================
CREATE TABLE accountable (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
    role VARCHAR(45) NOT NULL,
    secretariat_id BIGINT NOT NULL,
    uuid VARCHAR(45) DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_accountable_secretariat
            FOREIGN KEY (secretariat_id)
            REFERENCES secretariat(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

-- =============================
-- TABELA DE RELAÇÃO: project_has_accountable
-- =============================
CREATE TABLE project_has_accountable (
    project_id BIGINT NOT NULL,
    accountable_id BIGINT NOT NULL,

    PRIMARY KEY (project_id, accountable_id),

    FOREIGN KEY (project_id)
        REFERENCES project(id)
        ON DELETE CASCADE,

    FOREIGN KEY (accountable_id)
        REFERENCES accountable(id)
        ON DELETE CASCADE
);
