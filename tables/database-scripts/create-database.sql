-- Role: personalizer

-- DROP ROLE personalizer;

CREATE ROLE personalizer LOGIN
  ENCRYPTED PASSWORD 'md58e1a3b0d3a7ef9ca665db246cec11bb2'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

-- Database: personalizer

-- DROP DATABASE personalizer;

CREATE DATABASE personalizer
  WITH OWNER = personalizer
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_GB.UTF-8'
       LC_CTYPE = 'en_GB.UTF-8'
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE personalizer TO personalizer;
REVOKE ALL ON DATABASE personalizer FROM public;
