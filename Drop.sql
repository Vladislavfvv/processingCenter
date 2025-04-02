
DROP TABLE IF EXISTS processingCenterSchema.transaction;
DROP TABLE IF EXISTS processingCenterSchema.card;
DROP TABLE IF EXISTS processingCenterSchema.account;
DROP TABLE IF EXISTS processingCenterSchema.terminal;
DROP TABLE IF EXISTS processingCenterSchema.card_status;
DROP TABLE IF EXISTS processingCenterSchema.payment_system;
DROP TABLE IF EXISTS processingCenterSchema.currency;
DROP TABLE IF EXISTS processingCenterSchema.issuing_bank;
DROP TABLE IF EXISTS processingCenterSchema.sales_point;
DROP TABLE IF EXISTS processingCenterSchema.acquiring_bank;
DROP TABLE IF EXISTS processingCenterSchema.response_code;
DROP TABLE IF EXISTS processingCenterSchema.transaction_type;
DROP TABLE IF EXISTS processingCenterSchema.merchant_category_code;



DROP SCHEMA IF EXISTS processingCenterSchema;




SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'processingCenter'
  AND pid <> pg_backend_pid();


DROP DATABASE IF EXISTS processingCenter;