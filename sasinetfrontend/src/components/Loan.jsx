import React, { useState, useEffect } from 'react';
import Navbar from './Navbar'; // Import Navbar component

const Loan = () => {
    const [accountId, setAccountId] = useState('');
    const [repaymentAmount, setRepaymentAmount] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [loans, setLoans] = useState([]); // To hold the fetched loan data

    // Fetch user loans from the API
    const fetchUserLoans = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/accounts/byUser/1');
            const data = await response.json();
            setLoans(data); // Set the fetched data to state
        } catch (error) {
            console.error('Error fetching loans:', error);
            setError('Failed to fetch loan details');
        }
    };

    // Run the fetch when the component mounts
    useEffect(() => {
        fetchUserLoans();
    }, []);

    const handleRepayLoan = async (e) => {
        e.preventDefault();

        if (!accountId || !repaymentAmount) {
            setError('Please fill in all fields');
            return;
        }

        // Add validation for repayment amount (e.g., check if the amount is positive)
        if (repaymentAmount <= 0) {
            setError('Repayment amount must be greater than 0');
            return;
        }

        setLoading(true);

        try {
            const response = await fetch(
                `http://localhost:8080/api/transactions/loan/repay?accountId=${accountId}&repaymentAmount=${repaymentAmount}`,
                { method: 'POST' }
            );

            const data = await response.json();
            setMessage(`Loan repaid successfully! Transaction ID: ${data.id}`);
            setError('');
        } catch (error) {
            setMessage('');
            setError('Failed to repay loan. Please try again.');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Navbar />
            <div className="max-w-7xl mx-auto p-6">
                <div className="bg-white p-6 rounded-lg shadow-lg">
                    <h2 className="text-3xl font-semibold text-center mb-6">Repay Loan</h2>

                    {message && <p className="text-green-500 text-center">{message}</p>}
                    {error && <p className="text-red-500 text-center">{error}</p>}

                    <form onSubmit={handleRepayLoan} className="space-y-4">
                        <div className="mb-4">
                            <label htmlFor="accountId" className="block text-sm font-medium text-gray-700">
                                Loan Account ID
                            </label>
                            <input
                                type="number"
                                id="accountId"
                                value={accountId}
                                onChange={(e) => setAccountId(e.target.value)}
                                className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                                required
                            />
                        </div>

                        <div className="mb-4">
                            <label htmlFor="repaymentAmount" className="block text-sm font-medium text-gray-700">
                                Repayment Amount
                            </label>
                            <input
                                type="number"
                                id="repaymentAmount"
                                value={repaymentAmount}
                                onChange={(e) => setRepaymentAmount(e.target.value)}
                                className="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                                required
                            />
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-indigo-600 text-white py-2 rounded-md hover:bg-indigo-700 focus:outline-none"
                            disabled={loading}
                        >
                            {loading ? 'Processing...' : 'Repay Loan'}
                        </button>
                    </form>

                    {/* Table to display loan details */}
                    <h3 className="text-xl font-semibold text-center mt-8 mb-4">Loan Details</h3>
                    <div className="overflow-x-auto">
                        <table className="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                            <thead className="bg-indigo-600 text-white">
                            <tr>
                                <th className="py-2 px-4 text-left">Account ID</th>
                                <th className="py-2 px-4 text-left">Loan Amount</th>
                                <th className="py-2 px-4 text-left">Interest Rate (%)</th>
                                <th className="py-2 px-4 text-left">Remaining Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            {loans.length > 0 ? (
                                loans.map((loan) => (
                                    <tr key={loan.id}>
                                        <td className="py-2 px-4 border-b">{loan.account.id}</td>
                                        <td className="py-2 px-4 border-b">{loan.loanAmount}</td>
                                        <td className="py-2 px-4 border-b">{loan.interestRate}%</td>
                                        <td className="py-2 px-4 border-b">{loan.remainingAmount}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="4" className="py-2 px-4 text-center text-gray-500">
                                        No loan details available
                                    </td>
                                </tr>
                            )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Loan;
