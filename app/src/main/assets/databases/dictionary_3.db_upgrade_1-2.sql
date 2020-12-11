-- add a FullNames column to Employees
ALTER TABLE "dictionary" RENAME TO 'Employees_ME_TMP';
CREATE TABLE "dictionary" (
"_id" int NOT NULL,
"Word" varchar(20) NOT NULL,
PRIMARY KEY ("_id")
);
CREATE TABLE "favourite" (
"Word" varchar(2000) NOT NULL
);
INSERT INTO "dictionary"  ("_id", "Word") SELECT "_id", "Word" FROM "Employees_ME_TMP";
DROP TABLE "Employees_ME_TMP";