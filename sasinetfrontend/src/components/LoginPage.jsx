import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useDispatch} from "react-redux";
import {setUser} from "../userSlice.js";


export const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/users/login', {
                username, // Send username as confirmPassword
                password,
            });

            if (response.status === 200) {
                // Assuming the login is successful, extract the user info from response
                const { id, email } = response.data;  // Extract data from response
                dispatch(setUser({ id, email }));


                // Navigate to home or dashboard page
                navigate('/home'); // Modify this route as per your requirements
            }
        } catch (err) {
            // Handle error if login fails
            setError('Login failed. Please check your credentials.');
            console.error(err);
        }
    };


    return (
        <div className="flex justify-center items-center h-screen bg-gray-100">
            <form onSubmit={handleSubmit} className="bg-white p-8 rounded-lg shadow-lg max-w-sm w-full">
                <h2 className="text-3xl font-semibold mb-6 text-center text-blue-600">Login</h2>

                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="mb-4 p-3 border border-gray-300 rounded w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="mb-4 p-3 border border-gray-300 rounded w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                />

                {error && <p className="text-red-500 text-center mb-4">{error}</p>}

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white p-3 rounded hover:bg-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    Login
                </button>

                <div className="mt-4 text-center">
                    <span className="text-sm text-gray-500">Don't have an account? </span>
                    <a href="/register" className="text-sm text-blue-600 hover:underline">Sign up</a>
                </div>
            </form>
        </div>
    );
};

export default LoginPage;