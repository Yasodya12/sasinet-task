import React, { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "./Navbar.jsx";
import {useSelector} from "react-redux";

const FixedDepositsTable = () => {
    const [transactions, setTransactions] = useState([]);
    const { id, email } = useSelector((state) => state.user);
    useEffect(() => {
        // Fetch data from the API
        axios
            .get("http://localhost:8080/api/transactions/details?userId="+id)
            .then((response) => {
                setTransactions(response.data);
            })
            .catch((error) => {
                console.error("Error fetching data:", error);
            });
    }, []);

    return (
        <div className="bg-gradient-to-r from-blue-50 to-purple-50 min-h-screen">
            <Navbar/>
            <div className="container mx-auto py-10">
                <h2 className="text-3xl font-bold text-center text-blue-800 mb-8">
                    Fixed Deposit Transactions
                </h2>
                <div className="overflow-x-auto bg-white shadow-md rounded-lg">
                    <table className="table-auto w-full border-collapse">
                        <thead className="bg-gradient-to-r from-blue-600 to-purple-600 text-white">
                        <tr>
                            <th className="px-6 py-3 text-left">Transaction ID</th>
                            <th className="px-6 py-3 text-left">Account ID</th>
                            <th className="px-6 py-3 text-left">Deposit Amount</th>
                            <th className="px-6 py-3 text-left">Interest Rate (%)</th>
                            <th className="px-6 py-3 text-left">Total</th>
                            <th className="px-6 py-3 text-left">Start Date</th>
                            <th className="px-6 py-3 text-left">Maturity Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        {transactions.length > 0 ? (
                            transactions.map((transaction) => (
                                <tr
                                    key={transaction.id}
                                    className="border-b hover:bg-gray-100 transition duration-300"
                                >
                                    <td className="px-6 py-4">{transaction.id}</td>
                                    <td className="px-6 py-4">{transaction.account.id}</td>
                                    <td className="px-6 py-4">{transaction.depositAmount.toFixed(2)}</td>
                                    <td className="px-6 py-4">{transaction.interestRate}</td>
                                    <td className="px-6 py-4">{transaction.total.toFixed(2)}</td>
                                    <td className="px-6 py-4">
                                        {new Date(transaction.startDate).toLocaleDateString()}
                                    </td>
                                    <td className="px-6 py-4">
                                        {new Date(transaction.maturityDate).toLocaleDateString()}
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td
                                    colSpan="7"
                                    className="text-center text-gray-500 px-6 py-4"
                                >
                                    No transactions found.
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default FixedDepositsTable;
