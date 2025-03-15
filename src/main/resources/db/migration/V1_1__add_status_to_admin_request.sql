BEGIN;

-- Вспомогательный тип для статуса заявки на администратора
CREATE TYPE request_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

ALTER TABLE admin_requests ADD status request_status NOT NULL DEFAULT 'PENDING';

COMMIT;
