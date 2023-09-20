# Introduction
This project aimed to create a system for the Jarvis Linux Cluster Administration (LCA) team to efficiently record and monitor hardware specifications and real-time resource usage within their CentOS 7 Linux cluster. The users of this project are likely system administrators and IT professionals responsible for managing and optimizing the Linux cluster.

Key technologies used in this project include:

- Linux Command Lines and Bash Scripts
- PostgreSQL
- Crontab
- GitHub and Git
- Docker

# Quick Start
Follow these steps to quickly set up and use the Linux Cluster Monitoring Agent:

- Start a psql instance using psql_docker.sh
```bash
./scripts/psql_docker.sh start
```
- Create tables using ddl.sql
```bash
vim sql/ddl.sql
```
- Insert hardware specs data into the DB using host_info.sh
```bash
bash scripts/host_info.sh localhost 5432 host_agent postgres password
```
- Insert hardware usage data into the DB using host_usage.sh
```bash
bash scripts/host_usage.sh localhost 5432 host_agent postgres password
```
- Crontab setup
```bash
# Edit the crontab configuration
crontab -e
```

# Implemenation
The Linux Cluster Monitoring Agent has been implemented using various technologies and tools to ensure efficient data collection and management. Here are the key components of the implementation:

- **Linux Command Lines and Bash Scripts**:
The core functionality of the monitoring agent relies on Linux command lines and custom Bash scripts. These scripts were developed to capture hardware specifications and monitor resource usage on the Linux cluster.

- **PostgreSQL**:
For storing and managing the collected data, PostgreSQL was chosen as the Relational Database Management System (RDBMS).

- **Crontab**:
Automation plays a crucial role in ensuring timely data collection without manual intervention. To achieve this, Cron jobs were implemented to automate the execution of the monitoring agent's scripts. This approach guarantees consistent data collection at predefined intervals, providing up-to-date insights into the cluster's health and performance.

- **GitHub and Git**:
GitHub served as the version control system for managing the source code. Git allows for collaborative development, code tracking, and easy rollbacks in case of issues or updates.

- **Docker**:
Docker was employed to leverage the provisioning of the PostgreSQL database.

By combining these technologies and tools, the Linux Cluster Monitoring Agent delivers an efficient and scalable solution for monitoring and managing a Linux cluster's performance and resource utilization.

## Architecture
![Linux Monitoring Agent Architecture](./assets/linux_cluster.png)
- Data persistence is ensured through the use of a psql instance.
- Server usage data is gathered by the bash agent, which subsequently inserts this data into the psql instance. The agent comprises two bash scripts designed for execution on each host, server, or node:
  - host_info.sh is responsible for gathering hardware information from the host and then storing it in the database. This script is executed only once, during the installation process.
  - host_usage.sh continually collects the current host usage metrics, including CPU and memory statistics, and inserts them into the database. This script is scheduled to run at regular intervals, typically triggered by a cron job, such as once per minute.

## Scripts
**Shell script description and usage** (assuming user is on the linux_sql directory)
- **psql_docker.sh**: This script is used to start/stop the psql container
```bash
# Script usage
# ./scripts/psql_docker.sh start|stop|create [db_username][db_password]

# Example
# Provision and start PostgreSQL instance with Docker
./scripts/psql_docker.sh start
```
- **host_info.sh**: This script collects hardware specification data and then inserts the data into the psql instance.
```bash
# Script usage
# bash scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# Example
bash scripts/host_info.sh localhost 5432 host_agent postgres password
```

- **host_usage.sh**: This script collects server usage data and then inserts the data into the psql database.
```bash
# Script usage
# bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

# Example
bash scripts/host_usage.sh localhost 5432 host_agent postgres password
```

- **ddl.sql**: This file creates two tables (host_info & host_usage) automatically as sample input data.
```bash
# Initialize database and tables
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```

- **crontab**: Use to automate tasks.
```bash
# Run to edit crontab jobs
crontab -e

# Verify that the crontab was successfully created by listing crontab jobs
crontab -ls

# Verify that the script is running as intended by checking the log file
cat /tmp/host_usage.sh
```

- **queries.sql**: this sql file solves the following business problem:
  - The first query creates a table to store CPU number groups and populate it with data from the host_info table, organizing and sorting the data by total memory usage within each CPU group.

  - The second query calculates and rounds the average used memory percentage for hosts by combining data from the host_usage and host_info tables. It aims to provide insights into memory usage patterns over time.

  - The third query counts the data points within 5-minute intervals from the host_usage table. It then classifies hosts as "Healthy" or "Failure" based on the number of data points collected within these intervals, helping monitor host data consistency.

## Database Modeling

**Host Info Table `host_info`**

This table stores the information about the host's hardware specifications.

| Column name      | Description                                  |
|------------------|:---------------------------------------------|
| id               | Host id                                      |
| hostname         | Name of the system host                      |
| cpu_number       | Number of CPUs or cores                      |
| cpu_architecture | CPU architecture                             |
| cpu_model        | CPU model or name                            |
| cpu_mhz          | CPU clock speed in MHz                       |
| l2_cache         | Size of L2 cache in kB                       |
| total_mem        | Total memory available on the system in kB   |
| timestamp        | Current time in `2019-11-26 14:40:19` format |


**Host Info Table `host_usage`**

This table stores the resource usage data of the hosts over time.

| Column name    | Description                                  |
|----------------|:---------------------------------------------|
| timestamp      | Current time in `2019-11-26 14:40:19` format |
| host_id        | Unique identifier for the host or system     |
| memory_free    | Amount of free memory available on the host  |
| cpu_idle       | Percentage of CPU idle time                  |
| cpu_kernel     | Percentage of CPU time spent in the kernel   |
| disk_io        | Blocks received from a block device          |
| disk_available | Amount of disk space available on the system |

# Test

To test bash scripts:
- Using Debug Mode:
```bash
# To debug add -x option after the bash script
bash -x ./scripts/host_usage.sh
```

- Check for Syntax Errors
- Include Error Handling
  - Using conditional statements and exit codes to manage unexpected situations.
- Use of Version Control
  - Employ version control to track changes and revert to previous versions if necessary.

To test DDL:

- Test with Sample Data:
  Create sample input data and test it with various scenarios to ensure the script behaves as expected (e.g., ensuring the hostname is unique).
- Check if tables are created in current database


To test crontab:
Logging:
Create a logging to capture important information and errors. This can achieve this by redirecting the script's output to a log file.
```bash
bash path/to/location > /tmp/host_usage.log
```



# Deployment
The app was deployed by configuring the host_usage.sh script with execute permissions using the **chmod +x** command, allowing it to be executed as an executable within a Linux environment. Following this, the deployment involved scheduling the script's execution using **crontab**, which automated the process of collecting server usage data or executing other specified tasks at predefined intervals, all without requiring manual intervention.

# Improvements
- **Resource Allocation Alerts**: Implement resource allocation alerts to notify administrators when certain resource thresholds (e.g., CPU or memory usage) are exceeded, allowing them to take timely action to prevent performance issues.


- **Regular Backups**: Set up automated backups of configuration files and critical data to prevent data loss in case of hardware failures or accidental deletions.


- **Resource Cleanup Automation**: Develop scripts or tools to automate resource cleanup tasks, such as removing unused or idle resources, to optimize resource utilization and free up cluster capacity.