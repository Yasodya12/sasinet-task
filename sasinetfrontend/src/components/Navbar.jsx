import React from "react";
import { Link, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";

const Navbar = () => {
    const location = useLocation();
    const { email } = useSelector((state) => state.user); // Access user email from Redux store

    return (
        <nav className="bg-gradient-to-r from-blue-600 to-purple-600 shadow-md">
            <div className="container mx-auto px-4 py-3 flex justify-between items-center">
                {/* Logo */}
                <div className="text-white font-bold text-2xl">
                    <Link to="/">Dashboard</Link>
                </div>

                {/* Navigation Links */}
                <ul className="hidden md:flex space-x-6 text-white font-medium">
                    <li>
                        <Link
                            to="/home"
                            className={`px-4 py-2 rounded-lg ${
                                location.pathname === "/account" || location.pathname === "/home"
                                    ? "bg-white text-blue-600"
                                    : "hover:bg-white hover:text-blue-600"
                            }`}
                        >
                            Account
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/loan-account"
                            className={`px-4 py-2 rounded-lg ${
                                location.pathname === "/loan-account"
                                    ? "bg-white text-blue-600"
                                    : "hover:bg-white hover:text-blue-600"
                            }`}
                        >
                            Loan Account
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/fixed-deposit-account"
                            className={`px-4 py-2 rounded-lg ${
                                location.pathname === "/fixed-deposit-account"
                                    ? "bg-white text-blue-600"
                                    : "hover:bg-white hover:text-blue-600"
                            }`}
                        >
                            Fixed Deposit Account
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/saving-account"
                            className={`px-4 py-2 rounded-lg ${
                                location.pathname === "/saving-account"
                                    ? "bg-white text-blue-600"
                                    : "hover:bg-white hover:text-blue-600"
                            }`}
                        >
                            Saving Account
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/transaction"
                            className={`px-4 py-2 rounded-lg ${
                                location.pathname === "/transaction"
                                    ? "bg-white text-blue-600"
                                    : "hover:bg-white hover:text-blue-600"
                            }`}
                        >
                            Transaction
                        </Link>
                    </li>
                </ul>

                {/* User Profile Section */}
                <div className="relative">
                    <button className="flex items-center space-x-2 bg-white text-blue-600 px-4 py-2 rounded-lg shadow-md hover:bg-gray-200">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-6 w-6"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                            strokeWidth={2}
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="M5.121 17.804A10.95 10.95 0 0112 15c2.635 0 5.05.93 6.879 2.804m-6.879-4.804a4 4 0 100-8 4 4 0 000 8z"
                            />
                        </svg>
                        <span>{email ? email : "Guest"}</span>
                    </button>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
