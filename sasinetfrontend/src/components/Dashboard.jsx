import {useEffect, useState} from 'react';
import { useSelector } from 'react-redux';
import Navbar from './Navbar';
import AccountTable from "./AccountTable.jsx";
import axios from "axios";  // Assuming you have a Navbar component


const Dashboard = () => {
    // Access userId from the Redux store


    // State for the form fields
    const [accountType, setAccountType] = useState('');
    const [balance, setBalance] = useState('');
    const [message, setMessage] = useState('');
    const [messageColor, setMessageColor] = useState('text-red-600'); // Default message color is red

    // Handle form submission


    const { id, email,token } = useSelector((state) => state.user);
    const [accounts, setAccounts] = useState([]);



    const handleCreateAccount = async (e) => {
        e.preventDefault();

        // Make sure userId is available
        if (!id) {
            setMessage('User is not logged in.');
            setMessageColor('text-red-600');
            return;
        }

        const requestData = {
            type: accountType,
            balance: parseFloat(balance),
        };

        try {
            const response = await fetch(`http://localhost:8080/api/accounts/create/${id}`, {
                method: 'POST',
                'headers': {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                const result = await response.json();
                setMessage('Account created successfully!');
                setMessageColor('text-green-600');  // Change the message color to green for success
                alert('Account created successfully!');
                console.log(result);  // Log the response to the console for debugging
            } else {
                const error = await response.json();
                setMessage(`Error: ${error.message}`);
                setMessageColor('text-red-600');
            }
        } catch (error) {
            setMessage(`Request failed: ${error.message}`);
            setMessageColor('text-red-600');
        }
    };





    // const fetchUserLoans = async () => {
    //     try {
    //         const response = await fetch('http://localhost:8080/api/accounts/accountBySer/'+id);
    //         const data = await response.json();
    //         setLoans(data); // Set the fetched data to state
    //     } catch (error) {
    //         console.error('Error fetching loans:', error);
    //         setError('Failed to fetch loan details');
    //     }
    // };


    // Fetch accounts on component mount
    useEffect(() => {
        const fetchAccounts = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/accounts/accountBySer/1');

                const data = await response.json();
                console.log(data)
                setAccounts(data);
            } catch (error) {
                console.error('Error fetching accounts:', error);
            }
        };

        if (id) {
            fetchAccounts();
        }
    }, [id],handleCreateAccount);


//     const fetchTasks = async () => {
//
// // Fetch tasks from the server
//
//         try {
//             const response = await fetch(`http://localhost:8080/api/accounts/accountBySer/${id}`, {
//
//                 method:'GET',
//                 headers: {
//                     'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGV4YW1wbGUuY29tIiwidXNlck5hbWUiOiJqb2huX2RvZSIsInJvbGUiOlsiQURNSU4iXSwiZXhwIjoxNzMwMzYwNDk5fQ.JGl0sALsuKKqDTJ9UHhBovkXJXktXKlVqS4UdjMiEBw`, // Use 'Bearer' followed by a space and your token
//                     'Content-Type': 'application/json' // Optional, set based on your API needs
//                 }
//                 // Since you are not sending any body, you can skip the 'body' field
//             });
//
//             if (!response.ok) throw new Error('Failed to fetch tasks: ' + response.statusText);
//
//
//
//             setAccounts(response.data);
//         } catch (err) {
//             setMessage(`Request failed: ${error.message}`);
//             setMessageColor('text-red-600');
//         }
//     };
//
//     useEffect(() => {
//         fetchTasks(); // Fetch tasks when component mounts
//     }, []);





    return (
        <div>
            {/* Add Navbar */}
            <Navbar />

            <div className="container mx-auto p-4">
                <h2 className="text-2xl font-semibold mb-4">Account Creation</h2>

                <form onSubmit={handleCreateAccount} className="space-y-4">
                    <div>
                        <label htmlFor="accountType" className="block text-sm font-medium text-gray-700">Account Type</label>
                        <select
                            id="accountType"
                            value={accountType}
                            onChange={(e) => setAccountType(e.target.value)}
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
                            required
                        >
                            <option value="" disabled>Select an account type</option>
                            <option value="SAVING">SAVING</option>
                            <option value="FIXEDDEPOSITE">FIXEDDEPOSITE</option>
                            <option value="LOAN">LOAN</option>
                        </select>
                    </div>

                    <div>
                        <label htmlFor="balance" className="block text-sm font-medium text-gray-700">Initial Deposit</label>
                        <input
                            id="balance"
                            type="number"
                            value={balance}
                            onChange={(e) => setBalance(e.target.value)}
                            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className="w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        Create Account
                    </button>
                </form>

                {message && (
                    <div className={`mt-4 text-center text-lg ${messageColor}`}>
                        {message}
                    </div>
                )}

                <h3 className="text-2xl font-semibold text-gray-800 my-4">User Accounts</h3>

                <AccountTable accounts={accounts} />
            </div>
        </div>
    );
};

export default Dashboard;
