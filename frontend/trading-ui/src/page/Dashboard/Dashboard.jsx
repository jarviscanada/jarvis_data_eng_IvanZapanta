import React from "react";
import "./Dashboard.scss";
import NavBar from "../../component/Navbar/NavBar";

function Dashboard(props) {
  return (
    <div className="dashboard">
      <NavBar />
      <div className="dashboard-content">Dashboard Content</div>
    </div>
  );
}

export default Dashboard;
