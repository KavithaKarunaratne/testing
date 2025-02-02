import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import HomeButton from "./HomeButton";

const VendorLogin = () => {
  const [inputValues, setInputValues] = useState({
    email: "",
    password: "",
  });
  const [notification, setNotification] = useState({type: "", message: ""});

  const handleChange = (e) => {
    const {name, value} = e.target;
    setInputValues((prevState) => ({...prevState, [name]: value}));
  };

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const vendorLoginDTO = {
      email: inputValues.email,
      password: inputValues.password,
    };

    try {
      const response = await fetch("http://localhost:8080/api/vendor/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(vendorLoginDTO),
      });

      const message = await response.text(); // Get the string response from the backend

      if (response.ok) {
        if (message.includes("successful")) {
          setNotification({
            type: "success",
            message, // Show the success message from backend
          });
          navigate("/add-tickets");
        } else {
          setNotification({
            type: "error",
            message, // Show the success message from backend
          });
        }
      } else {
        setNotification({
          type: "error",
          message: `Error: ${message}`, // Show the error message from backend
        });
      }
    } catch (error) {
      console.error("Error:", error);
      setNotification({
        type: "error",
        message: "An unexpected error occurred.",
      });
    }

    // Clear notification after 5 seconds
    setTimeout(() => setNotification({type: "", message: ""}), 5000);
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-purple-50">
      {/* Notification Bar */}
      {notification.message && (
        <div
          className={`fixed top-0 left-0 w-full p-4 text-center text-white ${
            notification.type === "success" ? "bg-green-500" : "bg-red-500"
          }`}
        >
          {notification.message}
        </div>
      )}
      <HomeButton />

      {/* Form */}
      <form className="w-full max-w-md p-6" onSubmit={handleSubmit}>
        <h2 className="text-2xl font-bold mb-4 text-center">Vendor Login</h2>

        <div className="mb-4">
          <label
            htmlFor="email"
            className="block text-gray-700 font-medium mb-2"
          >
            Email
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={inputValues.email}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-purple-200"
            required
          />
        </div>

        <div className="mb-4">
          <label
            htmlFor="password"
            className="block text-gray-700 font-medium mb-2"
          >
            Password
          </label>
          <input
            type="password"
            id="password"
            name="password"
            value={inputValues.password}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-purple-200"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-purple-500 text-white py-2 rounded-md hover:bg-purple-600 focus:ring focus:ring-purple-300"
        >
          Login
        </button>
      </form>
    </div>
  );
};

export default VendorLogin;
