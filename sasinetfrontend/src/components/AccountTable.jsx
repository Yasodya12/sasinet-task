import React from 'react';

const AccountTable = ({ accounts }) => {
    return (
        <div className="my-6 overflow-x-auto bg-white shadow-md rounded-lg">
            <table className="min-w-full table-auto">
                <thead>
                <tr className="bg-gray-100 border-b">
                    <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">Account ID</th>
                    <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">Account Type</th>
                    <th className="px-4 py-2 text-left text-sm font-medium text-gray-600">Balance</th>
                </tr>
                </thead>
                <tbody>
                {accounts.length > 0 ? (
                    accounts.map((account) => (
                        <tr key={account.id} className="border-b hover:bg-gray-50">
                            <td className="px-4 py-2 text-sm text-gray-700">{account.id}</td>
                            <td className="px-4 py-2 text-sm text-gray-700">{account.type}</td>
                            <td className="px-4 py-2 text-sm text-gray-700">{account.balance.toFixed(2)}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="3" className="px-4 py-2 text-sm text-center text-gray-500">
                            No accounts found.
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default AccountTable;
