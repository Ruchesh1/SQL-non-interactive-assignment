-- Add below your SQL statements. 
-- You can create intermediate views (as needed). Remember to drop these views after you have populated the result tables.
-- You can use the "\i a2.sql" command in psql to execute the SQL commands in this file.

-- Query 1 statements
INSERT INTO query1 (
SELECT co.cid AS c1id, co.cname AS c1name, co1.cid AS c2id, co1.cname AS c2name
FROM country co, neighbour ne, country co1
WHERE co.cid = ne.country AND ne.neighbor = co1.cid AND co1.height IN (SELECT max(height) FROM country)
ORDER BY c1name ASC
)

-- Query 2 statements
INSERT INTO query2 (
SELECT co.cid AS cid, co.cname AS cname
FROM country co
WHERE co.cid NOT IN (SELECT oa.cid FROM oceanAccess oa)
ORDER BY cname ASC 
)


-- Query 3 statements
INSERT INTO query3 (
select c.cid AS c1id, c.cname AS c1name, c1.cid AS  c2id, c1.cname AS c2name 
from country c, neighbour n, country c1 
where c.cid = n.country AND n.neighbor = c1.cid AND c.cid NOT IN (select oa.cid FROM oceanAccess oa);
)

-- Query 4 statements
INSERT INTO query4 (
SELECT DISTINCT c.cname AS cname, o.oname AS oname
FROM country c, neighbour n, oceanAccess oa, ocean o
WHERE (c.cid = oa.cid AND oa.oid = o.oid) or (n.country = c.cid AND n.neighbor = oa.cid AND oa.oid = o.oid) 
ORDER BY cname ASC, oname DESC
)

-- Query 5 statements
INSERT INTO query5 (
SELECT c.cid AS cid, c.cname AS cname, avg(h.hdi_score) AS avghdi
FROM country c, hdi h
WHERE c.cid = h.cid AND h.year >= 2009 AND h.year <= 2013
GROUP BY c.cid, c.cname
ORDER BY avghdi DESC LIMIT 10;
)

-- Query 6 statements
INSERT INTO query6 (
SELECT c.cid AS cid, c.cname AS cname
FROM country c, hdi h, hdi h1
WHERE c.cid = h.cid AND c.cid = h1.cid AND h.year >= 2009 AND h.year <= 2013 AND h1.year >= 2010 AND h1.year <= 2014 AND h.hdi_score > h1.hdi_score
GROUP BY c.cname, c.cid
ORDER BY cname ASC
)


-- Query 7 statements

select rid, rname, sum(population * rpercentage/100) AS followers 
FROM country,religion 
WHERE country.cid = religion.cid 
GROUP BY rid,rname 
order by followers DESC

-- Query 8 statements

select c.cname AS clname, c1.cname AS c2name, l.lname  AS  lname 
from country c, country c1, neighbour n, language l 
where c.cid = n.country AND n.neighbor = c1.cid AND 
group by c.cname, c1.cname, l.lname;



-- Query 9 statements

select c.cname AS cname, MAX(c.height + o.depth) AS  totalspan 
from country c, oceanAccess oa, ocean o 
where c.cid = oa.cid AND oa.oid = o.oid 
group by c.cname 
order by totalspan DESC LIMIT 1;



-- Query 10 statements

SELECT c.cname AS cname, SUM(n.length) as borderlength
FROM neighbour n, country c, country c1
WHERE n.country = c.cid AND n.neighbor = c1.cid
GROUP BY c.cname ORDER BY borderlength DESC LIMIT 1;

