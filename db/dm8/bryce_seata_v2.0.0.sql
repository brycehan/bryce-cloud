drop database if exists bryce_seata;
create database bryce_seata default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use bryce_seata;

-- https://github.com/apache/incubator-seata/tree/v2.0.0/script/server/db

-- -------------------------------- The script used when storeMode is 'db' --------------------------------

-- the table to store GlobalSession data
CREATE TABLE "SEATA"."GLOBAL_TABLE"
(
    "XID"                       VARCHAR2(128) NOT NULL,
    "TRANSACTION_ID"            BIGINT,
    "STATUS"                    TINYINT       NOT NULL,
    "APPLICATION_ID"            VARCHAR2(32),
    "TRANSACTION_SERVICE_GROUP" VARCHAR2(32),
    "TRANSACTION_NAME"          VARCHAR2(128),
    "TIMEOUT"                   INT,
    "BEGIN_TIME"                BIGINT,
    "APPLICATION_DATA"          VARCHAR2(2000),
    "GMT_CREATE"                TIMESTAMP(0),
    "GMT_MODIFIED"              TIMESTAMP(0),
    PRIMARY KEY ("XID")
);

CREATE  INDEX "IDX_GMT_MODIFIED_STATUS" ON "SEATA"."GLOBAL_TABLE"("GMT_MODIFIED" ASC,"STATUS" ASC);
CREATE  INDEX "IDX_TRANSACTION_ID" ON "SEATA"."GLOBAL_TABLE"("TRANSACTION_ID" ASC);


-- the table to store BranchSession data
CREATE TABLE "SEATA"."BRANCH_TABLE"
(
    "BRANCH_ID"         BIGINT        NOT NULL,
    "XID"               VARCHAR2(128) NOT NULL,
    "TRANSACTION_ID"    BIGINT,
    "RESOURCE_GROUP_ID" VARCHAR2(32),
    "RESOURCE_ID"       VARCHAR2(256),
    "BRANCH_TYPE"       VARCHAR2(8),
    "STATUS"            TINYINT,
    "CLIENT_ID"         VARCHAR2(64),
    "APPLICATION_DATA"  VARCHAR2(2000),
    "GMT_CREATE" TIMESTAMP(0),
    "GMT_MODIFIED" TIMESTAMP(0),
    PRIMARY KEY ("BRANCH_ID")
);

CREATE INDEX "IDX_XID" ON "SEATA"."BRANCH_TABLE"("XID" ASC);


-- the table to store lock data
CREATE TABLE "SEATA"."LOCK_TABLE"
(
    "ROW_KEY"        VARCHAR2(128) NOT NULL,
    "XID"            VARCHAR2(128),
    "TRANSACTION_ID" BIGINT,
    "BRANCH_ID"      BIGINT        NOT NULL,
    "RESOURCE_ID"    VARCHAR2(256),
    "TABLE_NAME"     VARCHAR2(32),
    "PK"             VARCHAR2(36),
    "STATUS"         TINYINT       NOT NULL DEFAULT 0,
    "GMT_CREATE"     TIMESTAMP(0),
    "GMT_MODIFIED"   TIMESTAMP(0),
    PRIMARY KEY ("ROW_KEY")
);

COMMENT ON COLUMN "SEATA"."LOCK_TABLE"."STATUS" IS '0:locked ,1:rollbacking';

CREATE INDEX "IDX_BRANCH_ID" ON "SEATA"."LOCK_TABLE" ("BRANCH_ID" ASC);
CREATE INDEX "IDX_STATUS" ON "SEATA"."LOCK_TABLE" ("STATUS" ASC);

CREATE TABLE "SEATA"."DISTRIBUTED_LOCK"
(
    "LOCK_KEY"   VARCHAR2(20) NOT NULL,
    "LOCK_VALUE" VARCHAR2(20) NOT NULL,
    "EXPIRE"     BIGINT       NOT NULL,
    PRIMARY KEY ("LOCK_KEY")
);

INSERT INTO "SEATA"."DISTRIBUTED_LOCK" ("LOCK_KEY", "LOCK_VALUE", "EXPIRE") VALUES ('AsyncCommitting', ' ', 0);
INSERT INTO "SEATA"."DISTRIBUTED_LOCK" ("LOCK_KEY", "LOCK_VALUE", "EXPIRE") VALUES ('RetryCommitting', ' ', 0);
INSERT INTO "SEATA"."DISTRIBUTED_LOCK" ("LOCK_KEY", "LOCK_VALUE", "EXPIRE") VALUES ('RetryRollbacking', ' ', 0);
INSERT INTO "SEATA"."DISTRIBUTED_LOCK" ("LOCK_KEY", "LOCK_VALUE", "EXPIRE") VALUES ('TxTimeoutCheck', ' ', 0);