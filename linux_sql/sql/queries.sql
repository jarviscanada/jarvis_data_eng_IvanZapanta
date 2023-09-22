--1.
-- Create a new table to store CPU number groups
CREATE TABLE cpu_number_group (
                                  cpu_number INT2 NOT NULL,
                                  id INT4 NOT NULL,
                                  total_mem INT4 NULL
);

-- Insert data into the cpu_number_group table by grouping and sorting
INSERT INTO cpu_number_group (cpu_number, id, total_mem)
SELECT
    cpu_number,
    id,
    total_mem
FROM
    host_info
ORDER BY
    total_mem DESC; -- Sort the data by total memory in descending order within each CPU group

--2.
-- This query selects specific information from two tables, host_usage and host_info, and calculates an average metric.
SELECT
    h.host_id,
    hi.hostname AS host_name,
    -- Calculate a rounded timestamp by truncating the hour part of the timestamp and adding a rounded minute interval.
    date_trunc('hour', h."timestamp") + (date_part('minute', h."timestamp")::int / 5 * interval '5 min') AS rounded_timestamp,
    -- Calculate and round the average used memory percentage
    ROUND(AVG(hi.total_mem - h.memory_free)) AS avg_used_mem_percentage
FROM
    host_usage h
        JOIN
    host_info hi ON h.host_id = hi.id
GROUP BY
    h.host_id,
    hi.hostname,
    rounded_timestamp
ORDER BY
    h.host_id,
    rounded_timestamp;

-- 3.
-- Common Table Expression (CTE) to calculate rounded data and count data points within 5-minute intervals
WITH rounded_data AS (
    SELECT
        host_id,
        -- Calculate a rounded timestamp by truncating the seconds and milliseconds and keeping only minutes.
        date_trunc('minute', "timestamp") AS rounded_timestamp,
        COUNT(*) AS num_insertions
    FROM
        host_usage
    GROUP BY
        host_id,
        rounded_timestamp
)
SELECT
    rd.host_id,
    rd.rounded_timestamp,
    num_insertions AS num_data_points, -- Include the num_data_points column
    CASE
        WHEN num_insertions < 3 THEN 'Failure'
        ELSE 'Healthy'
        END AS host_status
FROM
    rounded_data rd
ORDER BY
    rd.host_id,
    rd.rounded_timestamp;





