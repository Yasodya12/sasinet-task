import React from 'react';
import { useSelector } from 'react-redux';

const Dashboard = () => {
    // Access userId from the Redux store
    const { id, email } = useSelector((state) => state.user);
    console.log("user ID:", id);
    console.log("email:", email);
    return (
        <div className="dashboard">
            <h1>Welcome to the Dashboard</h1>
            {id ? (
                <p>Your User ID: {id}</p>
            ) : (
                <p>Loading User ID...</p>
            )}
        </div>
    );
};

export default Dashboard;
