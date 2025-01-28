import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import Navbar from "./Navbar";
import {useSelector} from "react-redux"; // Importing the Navbar component

const Transaction = () => {
    const [amount, setAmount] = useState("");
    const [transactionType, setTransactionType] = useState("withdraw");
    const [message, setMessage] = useState("");
    const [accountIds, setAccountIds] = useState([]);
    const [selectedAccountId, setSelectedAccountId] = useState(null); // To store selected accountId
    const [transactions, setTransactions] = useState([]);
    const location = useLocation();
    const { id, email } = useSelector((state) => state.user);
    const handleAmountChange = (event) => {
        setAmount(event.target.value);
    };

    const handleTransactionTypeChange = (event) => {
        setTransactionType(event.target.value);
    };

    const handleAccountChange = (event) => {
        setSelectedAccountId(event.target.value);
    };
    const handleTransaction = async () => {
        if (!amount || isNaN(amount) || amount <= 0) {
            setMessage("Please enter a valid amount.");
            return;
        }

        if (!selectedAccountId) {
            setMessage("Please select an account.");
            return;
        }

        const endpoint =
            transactionType === "withdraw"
                ? `http://localhost:8080/api/transactions/withdraw/${selectedAccountId}?amount=${amount}`
                : `http://localhost:8080/api/transactions/deposit/${selectedAccountId}?amount=${amount}`;

        try {
            const response = await fetch(endpoint, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                setMessage(`${transactionType === "withdraw" ? "Withdrawal" : "Deposit"} successful.`);
            } else {
                const errorMessage = await response.text(); // Read error message from server
                setMessage(`Transaction failed: ${errorMessage}`);
            }
        } catch (error) {
            setMessage("Error processing transaction. Please try again.");
            console.error("Transaction error:", error);
        }
    };
    useEffect(() => {
        // Fetch account IDs when the component mounts
        const fetchAccountIds = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/savingaccount/savings/accountIds?userId='+id);
                const data = await response.json();
                console.log("this are trans ",data)
                setAccountIds(data); // Populate the account IDs in state
                setSelectedAccountId(data[0]); // Set the first account ID as default
            } catch (error) {
                console.error("Error fetching account IDs:", error);
                setMessage("Failed to load account IDs.");
            }
        };

        const fetchTransactionData = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/transactions/trasaction/user?userID='+id);
                const data = await response.json();
                console.log("Fetched transaction data:", data);
                setTransactions(data);
                // You can also update the state or perform further operations with the data here.
            } catch (error) {
                console.error("Error fetching transaction data:", error);
                // Optionally, handle the error by updating the UI or state.
            }
        };

        fetchTransactionData();
        fetchAccountIds();
    }, []); // Empty dependency array ensures this runs only once on mount





    return (
        <div>
            <Navbar /> {/* Including Navbar here */}

            <div className="container mx-auto my-8">
                <h2 className="text-2xl font-bold mb-6">Account Transaction</h2>

                <div className="mb-4">
                    <label htmlFor="transactionType" className="block text-lg font-medium mb-2">Transaction Type</label>
                    <select
                        id="transactionType"
                        value={transactionType}
                        onChange={handleTransactionTypeChange}
                        className="w-full p-2 border border-gray-300 rounded-md"
                    >
                        <option value="withdraw">Withdraw</option>
                        <option value="deposit">Deposit</option>
                    </select>
                </div>

                {/* Account ID Dropdown */}
                <div className="mb-4">
                    <label htmlFor="accountId" className="block text-lg font-medium mb-2">Select Account</label>
                    <select
                        id="accountId"
                        value={selectedAccountId}
                        onChange={handleAccountChange}
                        className="w-full p-2 border border-gray-300 rounded-md"
                    >
                        {accountIds.map((accountId) => (
                            <option key={accountId} value={accountId}>
                                Account ID: {accountId}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="mb-4">
                    <label htmlFor="amount" className="block text-lg font-medium mb-2">Amount</label>
                    <input
                        type="number"
                        id="amount"
                        value={amount}
                        onChange={handleAmountChange}
                        className="w-full p-2 border border-gray-300 rounded-md"
                    />
                </div>

                <button
                    onClick={handleTransaction}
                    className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                >
                    Perform Transaction
                </button>

                {message && <p className="mt-4 text-lg font-semibold">{message}</p>}
            </div>
            <div className="p-6">
                <h2 className="text-2xl font-bold mb-4">Transaction Table</h2>
                <table className="table-auto w-full border-collapse border border-gray-300">
                    <thead>
                    <tr className="bg-gray-200">
                        <th className="border border-gray-300 px-4 py-2">ID</th>
                        <th className="border border-gray-300 px-4 py-2">Account ID</th>
                        <th className="border border-gray-300 px-4 py-2">Amount</th>
                        <th className="border border-gray-300 px-4 py-2">Type</th>
                        <th className="border border-gray-300 px-4 py-2">Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {transactions.map((transaction, index) => (
                        <tr
                            key={transaction.id}
                            className={index % 2 === 0 ? "bg-gray-50" : "bg-white"}
                        >
                            <td className="border border-gray-300 px-4 py-2 text-center">
                                {transaction.id}
                            </td>
                            <td className="border border-gray-300 px-4 py-2 text-center">
                                {transaction.accountId}
                            </td>
                            <td className="border border-gray-300 px-4 py-2 text-center">
                                {transaction.amount}
                            </td>
                            <td className="border border-gray-300 px-4 py-2 text-center">
                                {transaction.type}
                            </td>
                            <td className="border border-gray-300 px-4 py-2 text-center">
                                {new Date(transaction.date).toLocaleString()}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Transaction;
