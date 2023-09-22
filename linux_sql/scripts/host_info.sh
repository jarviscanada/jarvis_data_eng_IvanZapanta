#!/bin/sh

# Setup and validate arguments (again, don't copy comments)
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Check # of args
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

# save the number of CPUs, machine statistics in MB, and current machine hostname to variables
hostname=$(hostname -f)
lscpu_out=`lscpu`
vmstat_mb=$(vmstat --unit M)

# Retrieve hardware specification variables
# xargs is a trick to trim leading and trailing white spaces
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | grep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | grep "Model name:" | awk '{print $3}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | grep "CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | grep "L2 cache:" | awk '{print $3}' | tr -d 'K' | xargs)

total_mem=$(echo "$vmstat_mb" | awk '{print $4}' | tail -1 | xargs)
# Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(vmstat -t 1 2 | awk '{print $18, $19}' | tail -1 | xargs -I{} date -d "{}" "+%Y-%m-%d %H:%M:%S")

# Subquery to find matching id in host_info table
#host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

# PSQL command: Inserts server usage data into host_usage table
# Note: be careful with double and single quotes
insert_stmt="INSERT INTO host_info("timestamp", hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem) VALUES('$timestamp', '$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem);"

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?