import React, { useEffect, useState } from "react";
import "./Dashboard.scss";
import NavBar from "../../component/NavBar/NavBar";
import TraderList from "../../component/TraderList/TraderList";
// import TraderListData from "../../component/TraderList/TraderListData.json";
import axios from "axios";
import {
  createTraderUrl,
  deleteTraderUrl,
  tradersUrl,
} from "../../util/constants";
import { Button, Modal, Form, Input, DatePicker } from "antd";

function Dashboard(props) {
  const [form] = Form.useForm();
  const [state, setState] = useState({
    isModalVisible: false,
    traders: [],
    firstName: "",
    lastName: "",
    dob: null,
    country: "",
    email: "",
  });

  const getTraders = async () => {
    const response = await axios.get(tradersUrl);
    if (response && response.data) {
      setState({
        ...state,
        traders: response.data,
      });
    }
  };

  const showModal = () => {
    setState({
      ...state,
      isModalVisible: true,
    });
  };

  const handleOk = async () => {
    try {
      // validate fields - antd
      await form.validateFields();

      const formattedDob = state.dob
        ? state.dob.toISOString().split("T")[0]
        : null;

      const paramURL = `/firstname/${state.firstName}/lastname/${state.lastName}/dob/${formattedDob}/country/${state.country}/email/${state.email}`;

      const response = await axios.post(createTraderUrl + paramURL, {});

      if (response && response.data) {
        await getTraders();
      }

      // reset form and close modal
      form.resetFields();
      setState((prevState) => ({
        ...prevState,
        isModalVisible: false,
      }));
    } catch (error) {
      console.error("Error adding trader", error);
    }
  };

  const onInputChange = (field, value) => {
    setState({
      ...state,
      [field]: value,
    });
  };

  const handleCancel = () => {
    form.resetFields();
    setState((prevState) => ({
      ...prevState,
      isModalVisible: false,
    }));
  };

  useEffect(() => {
    getTraders();
  }, []);

  const onTraderDelete = async (id) => {
    const deleteUrl = deleteTraderUrl.replace("traderId", id);

    try {
      const response = await axios.delete(deleteUrl);
      if (response && response.status === 200) {
        console.log(`Trader ${id} is deleted.`);
        await getTraders();
      }
    } catch (error) {
      console.error("Error deleting trader", error);
    }
  };

  return (
    <div className="dashboard">
      <NavBar />
      <div className="dashboard-content">
        <div className="title">
          Dashboard
          <div className="add-trader-button">
            <Button type="primary" onClick={showModal}>
              Add New Trader
            </Button>
            <Modal
              title="Add New Trader"
              open={state.isModalVisible}
              onOk={handleOk}
              onCancel={handleCancel}
              okText="Submit"
            >
              <Form layout="vertical" form={form} onFinish={handleOk}>
                <Form.Item
                  label="First Name"
                  name="firstName"
                  rules={[
                    { required: true, message: "Please input the first name!" },
                    {
                      type: "string",
                      message: "First name must be a valid string!",
                    },
                  ]}
                >
                  <Input
                    onChange={(event) =>
                      onInputChange("firstName", event.target.value)
                    }
                    placeholder="John"
                  />
                </Form.Item>

                <Form.Item
                  label="Last Name"
                  name="lastName"
                  rules={[
                    { required: true, message: "Please input the first name!" },
                    {
                      type: "string",
                      message: "Last name must be a valid string!",
                    },
                  ]}
                >
                  <Input
                    onChange={(event) =>
                      onInputChange("lastName", event.target.value)
                    }
                    placeholder="Doe"
                  />
                </Form.Item>

                <Form.Item
                  label="Email"
                  name="email"
                  rules={[
                    { required: true, message: "Please input the email!" },
                    {
                      pattern: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                      message: "Please input a valid email address!",
                    },
                  ]}
                >
                  <Input
                    onChange={(event) =>
                      onInputChange("email", event.target.value)
                    }
                    placeholder="email@example.com"
                  />
                </Form.Item>

                <Form.Item
                  label="Country"
                  name="country"
                  rules={[
                    { required: true, message: "Please input the country!" },
                    {
                      type: "string",
                      message: "Country must be a valid string!",
                    },
                  ]}
                >
                  <Input
                    onChange={(event) =>
                      onInputChange("country", event.target.value)
                    }
                    placeholder="USA"
                  />
                </Form.Item>

                <Form.Item
                  label="Date of Birth"
                  name="dob"
                  rules={[
                    {
                      required: true,
                      message: "Please select your date of birth!",
                    },
                    { type: "date", message: "Please select a valid date!" },
                  ]}
                >
                  <DatePicker
                    onChange={(date) => onInputChange("dob", date)}
                    style={{ width: "100%" }}
                    format="YYYY-MM-DD"
                  />
                </Form.Item>
              </Form>
            </Modal>
          </div>
        </div>
        <TraderList
          onTraderDeleteClick={onTraderDelete}
          traders={state.traders}
        />
      </div>
    </div>
  );
}

export default Dashboard;
