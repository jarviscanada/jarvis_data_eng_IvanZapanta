import React from "react";
import { Table } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "antd/dist/antd.css";
import "./TraderList.scss";
import { useState, useEffect } from "react";
// import TraderListData from "./TraderListData.json";
import { faTrashAlt as deleteIcon } from "@fortawesome/free-solid-svg-icons";

function TraderList(props) {
  // Initialization of columns for table
  const columns = [
    {
      title: "First Name",
      dataIndex: "firstName",
      key: "firstName",
      sorter: (a, b) => a.firstName.localeCompare(b.firstName), // Sort by first name (antd)
    },
    {
      title: "Last Name",
      dataIndex: "lastName",
      key: "lastName",
      sorter: (a, b) => a.lastName.localeCompare(b.lastName), // Sort by last name (antd)
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Date of Birth",
      dataIndex: "dob",
      key: "dob",
      sorter: (a, b) => new Date(a.dob) - new Date(b.dob), // Sort by date of birth
    },
    {
      title: "Country",
      dataIndex: "country",
      key: "country",
    },
    {
      title: "Actions",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div className="trader-delete-icon">
          <FontAwesomeIcon
            icon={deleteIcon}
            onClick={() => props.onTraderDeleteClick(record.id)}
          />
        </div>
      ),
    },
  ];

  const [tableColumns, setTableColumns] = useState(columns);
  // const [dataSource, setDataSource] = useState([]);

  // useEffect(() => {
  //   const dataSource = TraderListData;
  //   setDataSource(dataSource);
  // });

  return (
    // <Table dataSource={dataSource} columns={tableColumns} pagination={false} />
    <Table
      dataSource={props.traders}
      columns={tableColumns}
      pagination={false}
      rowKey="id"
    />
  );
}

export default TraderList;
