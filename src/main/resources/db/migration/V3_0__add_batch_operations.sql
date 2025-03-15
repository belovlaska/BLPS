BEGIN;

-- Вспомогательный тип для статуса операции
CREATE TYPE operation_status AS ENUM ('IN_QUEUE', 'IN_PROGRESS', 'SUCCESS', 'FAILED');

-- Таблица операции пакетной обработки
CREATE TABLE batch_operations (
                                id SERIAL PRIMARY KEY,
                                status operation_status NOT NULL DEFAULT 'IN_QUEUE'::operation_status,
                                added_count INTEGER NOT NULL DEFAULT 0,
                                updated_count INTEGER NOT NULL DEFAULT 0,
                                deleted_count INTEGER NOT NULL DEFAULT 0,
                                error_message TEXT,
                                created_by INTEGER NOT NULL REFERENCES users(id),
                                created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Индексы для оптимизации поиска и выборок
CREATE INDEX idx_batch_operations_status ON batch_operations(status);
CREATE INDEX idx_batch_operations_created_at ON batch_operations(created_at);
CREATE INDEX idx_batch_operations_created_by ON batch_operations(created_by);

-- Ограничение внешних ключей для таблиц
ALTER TABLE batch_operations ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id);

COMMIT;
