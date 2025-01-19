import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Navbar from "./Navbar.jsx";

const SavingAccount = () => {
    const [savingAccounts, setSavingAccounts] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const fetchSavingAccounts = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/savingaccount/byUser?userId=1');
                const data = await response.json();
                setSavingAccounts(data);
            } catch (error) {
                console.error('Error fetching saving accounts:', error);
            }
        };

        fetchSavingAccounts();
    }, []);

    return (
        <div>
            < Navbar />


            {/* Saving Account Table */}
            <div className="container mx-auto my-8">
                <h2 className="text-2xl font-bold mb-6">Saving Accounts</h2>
                <table className="min-w-full table-auto border-collapse border border-gray-200">
                    <thead>
                    <tr className="bg-gray-100">
                        <th className="border px-4 py-2 text-left">ID</th>
                        <th className="border px-4 py-2 text-left">Account ID</th>
                        <th className="border px-4 py-2 text-left">User</th>
                        <th className="border px-4 py-2 text-left">Balance</th>
                        <th className="border px-4 py-2 text-left">Created Date</th>
                        <th className="border px-4 py-2 text-left">Interest Rate</th>
                    </tr>
                    </thead>
                    <tbody>
                    {savingAccounts.map((account) => (
                        <tr key={account.id} className="hover:bg-gray-50">
                            <td className="border px-4 py-2">{account.id}</td>
                            <td className="border px-4 py-2">{account.account.id}</td>
                            <td className="border px-4 py-2">{account.account.user.username}</td>
                            <td className="border px-4 py-2">{account.balance}</td>
                            <td className="border px-4 py-2">{new Date(account.createdDate).toLocaleString()}</td>
                            <td className="border px-4 py-2">{account.interestRate}%</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default SavingAccount;
